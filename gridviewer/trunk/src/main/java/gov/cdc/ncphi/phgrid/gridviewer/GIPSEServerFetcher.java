package gov.cdc.ncphi.phgrid.gridviewer;

import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion;
import gov.cdc.ncphi.phgrid.services.gipse.client.GIPSEServiceClient;
import gov.cdc.ncphi.phgrid.services.gipse.common.AxisUtils;
import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.axis.types.URI.MalformedURIException;
import org.apache.log4j.Logger;

import gov.cdc.ncphi.phgrid.gipse.message.MetadataQuery;
import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponse;

public class GIPSEServerFetcher {

    private static GIPSEServerFetcher fetcher = null;
    private static String PROPERTIES_FILE = "/gridviewer.properties", SERVERTABLENAME = "tablename", SERVERLISTURL = "serverListURL",
            SERVERSOURCE = "serversource";
    private Properties defaultProps;
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger logger = Logger.getLogger(GIPSEServerFetcher.class);
    private static String serverListURL, serversource, url, driver, user, pass, tablename;
    private static GeneralCacheAdministrator cacheAdmin = Cacher.getCache();

    private GIPSEServerFetcher() {
        try {
            defaultProps = new Properties();
            InputStream is = getClass().getResourceAsStream(PROPERTIES_FILE);
            if (is == null) {
                logger.error("Could not read " + PROPERTIES_FILE);
            }
            defaultProps.load(is);
            serverListURL = defaultProps.getProperty(SERVERLISTURL);
            serversource = defaultProps.getProperty(SERVERSOURCE);
            url = defaultProps.getProperty("url");
            driver = defaultProps.getProperty("driver");
            user = defaultProps.getProperty("user");
            pass = defaultProps.getProperty("pass");
            tablename = defaultProps.getProperty(SERVERTABLENAME);

        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public Map<String, ServerMetadata> getServers() {
        Map<String, ServerMetadata> returnable = new HashMap<String, ServerMetadata>();
        try {
            // Get from the cache
            logger.debug("searching cache for Server List");
            returnable = (Map<String, ServerMetadata>) cacheAdmin.getFromCache("SERVERLIST");
        } catch (NeedsRefreshException nre) {
            try {
                // Get the value (probably by calling an EJB)
                logger.debug("cache miss, attempting to load server list from web");
                logger.debug("server source was " + serversource);
                if (serversource == null || serversource.equals("URL")) {
                    try {

                        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(serverListURL).openStream()));
                        String line = reader.readLine();

                        while (line != null) {
                            if (line.contains("~~")) {
                                String urls[] = line.split("~~");
                                for (int k = 0; k < urls.length; k++) {

                                    String serverUrl = urls[k];
                                    if (serverUrl.charAt(0) != '!') {
                                        String itemSplit[] = serverUrl.split("~");

                                        if (itemSplit.length == 2) {
                                            logger.debug("Pulling server data from server " + itemSplit[0] + " and url " + itemSplit[1]);
                                            ServerMetadata sm = new ServerMetadata();
                                            sm.setServerName(itemSplit[0]);
                                            sm.setServerUrl(itemSplit[1]);
                                            returnable.put(sm.getServerName(), sm);
                                        }
                                    }
                                }
                            }
                            line = reader.readLine();
                        }
                    } catch (IOException ioe) {
                        logger.error(ioe.getMessage(), ioe);
                    }
                } else if (serversource.equals("DB")) {
                    Connection con = null;
                    PreparedStatement prep = null;
                    ResultSet rs = null;

                    String sql = "select servername, serverurl from " + tablename;

                    logger.debug("pulling server list");
                    try {
                        Class.forName(driver);
                        con = DriverManager.getConnection(url, user, pass);
                        prep = con.prepareStatement(sql);
                        rs = prep.executeQuery();
                        while (rs.next()) {
                            ServerMetadata sm = new ServerMetadata();
                            sm.setServerName(rs.getString(1));
                            sm.setServerUrl(rs.getString(2));
                            logger.trace("Server Name: " + sm.getServerName() + " Server URL: " + sm.getServerUrl());
                            returnable.put(sm.getServerName(), sm);
                        }
                        rs.close();
                        con.close();
                    } catch (ClassNotFoundException e) {
                        logger.error(e.toString(), e);
                    } catch (SQLException e) {
                        logger.error(e.toString(), e);
                    }
                    logger.debug("returning list of size " + returnable.size());

                }

                populateMetadata(returnable);
                // Store in the cache
                cacheAdmin.putInCache("SERVERLIST", returnable);
            } catch (Exception ex) {
                // We have the current content if we want fail-over.
                logger.warn("attempt to get the server list apparently failed, now using cache failover which may be empty!");
                returnable = (Map<String, ServerMetadata>) nre.getCacheContent();
                // It is essential that cancelUpdate is called if the
                // cached content is not rebuilt
                cacheAdmin.cancelUpdate("SERVERLIST");
            }
        }

        return returnable;
    }

    public static GIPSEServerFetcher getServerFetcher() {
        if (fetcher == null) {
            fetcher = new GIPSEServerFetcher();
        }
        return fetcher;
    }

    public static void refreshMetadata(Map<String, ServerMetadata> smMap) {
        populateMetadata(smMap);
    }

    private static void populateMetadata(Map<String, ServerMetadata> smMap) {
        //ClientDataProvider cdp = new ClientDataProvider();

        Iterator<ServerMetadata> iter = smMap.values().iterator();
        int k = 0;
        while (iter.hasNext()) {
            ServerMetadata sm = iter.next();

            String urlToService = sm.getServerUrl();
            GIPSEServiceClient cdp;
            try {

                cdp = new GIPSEServiceClient(urlToService);
                MetadataQuery query = new MetadataQuery();
                MetadataQueryResponse mqrt = null;
                try {
                    // Get from the cache
                    logger.debug("searching cache for METADATA " + urlToService);
                    mqrt = (MetadataQueryResponse) cacheAdmin.getFromCache("METADATA" + urlToService);
                } catch (NeedsRefreshException nre) {
                    try {
                        // Get the value (probably by calling an EJB)
                        logger.debug("cache miss, attempting to open service client to " + urlToService);
                        mqrt = cdp.queryMetadata(query);
                        // Store in the cache
                        cacheAdmin.putInCache("METADATA" + urlToService, mqrt);
                    } catch (Exception ex) {
                        // We have the current content if we want fail-over.
                        logger.error("received the following error trying to fetch Metadata for " + urlToService + "\n" + ex.getMessage(), ex);
                        logger.warn("attempt to get metadata from METADATA" + urlToService + " apparently failed, now using cache failover which may be empty!");
                        mqrt = (MetadataQueryResponse) nre.getCacheContent();
                        // It is essential that cancelUpdate is called if the
                        // cached content is not rebuilt
                        cacheAdmin.cancelUpdate("METADATA" + urlToService);
                    }
                }


                // mqrt = cdp.queryMetadata(query);
                if (mqrt != null) {
                    logger.trace(AxisUtils.serializeAxisObject(mqrt, false, true));
                    logger.trace("adding metadata record with start date of " + df.format(mqrt.getMetadataRecord().getTimePeriodSupported().getStart().getTime()) +
                            "and end date of " + df.format(mqrt.getMetadataRecord().getTimePeriodSupported().getEnd().getTime()));
                    MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion[] geoIter = mqrt.getMetadataRecord().getGeoRegionsSupported().getGeoRegion();
                    for (int l = 0; l < geoIter.length; l++) {
                        MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion nexty = geoIter[l];
                        logger.trace("Supported geolocation type: " + nexty.getType().getValue() + " and region " + nexty.getValue());
                        if (nexty.getSpecificLocations() != null && nexty.getSpecificLocations().getGeoLocation() != null && nexty.getSpecificLocations().getGeoLocation().length > 0) {
                            for (int v = 0; v < nexty.getSpecificLocations().getGeoLocation().length; v++) {
                                logger.trace("There is also a specific location of " + nexty.getSpecificLocations().getGeoLocation()[v].getType().getValue() +
                                        ", and type " + nexty.getSpecificLocations().getGeoLocation()[v].getValue());
                            }
                        }
                    }

                    sm.setMrt(mqrt.getMetadataRecord());
                } else {
                    logger.error("metadata was null!");
                }
            } catch (MalformedURIException e) {
                logger.error(e.getMessage(), e);
            } catch (RemoteException e) {
                logger.error(e.getMessage(), e);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
            k++;
        }

    }
}
