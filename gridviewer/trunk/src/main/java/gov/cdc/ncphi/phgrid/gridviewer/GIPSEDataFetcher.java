package gov.cdc.ncphi.phgrid.gridviewer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;




import org.apache.axis.types.URI.MalformedURIException;
import org.apache.log4j.Logger;

import com.opensymphony.oscache.base.NeedsRefreshException;
import com.opensymphony.oscache.general.GeneralCacheAdministrator;


import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristics;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsAges;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsAgesAge;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsDataSources;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsDates;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsFacilitiesFacility;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsGeoRegions;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsIndicators;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsPageData;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsServiceAreas;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsSuppressValues;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestRequestCharacteristics;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseObservationSetObservation;
import gov.cdc.ncphi.phgrid.gridviewer.jsphelper.RegionalObservations;
import gov.cdc.ncphi.phgrid.gridviewer.jsphelper.ServerObservations;
import gov.cdc.ncphi.phgrid.polygon.gmaps.GmapPolygon;
import gov.cdc.ncphi.phgrid.polygon.gmaps.TimeSeries;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionData;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionRelation;
import gov.cdc.ncphi.phgrid.services.gipse.client.GIPSEServiceClient;
import gov.cdc.ncphi.phgrid.services.gipse.common.AxisUtils;

public class GIPSEDataFetcher {

    private static final Logger logger = Logger.getLogger(GIPSEDataFetcher.class);
    private static final String USERNAME = "GRIDVIEWER";
    //private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
    private static String PROPERTIES_FILE = "/gridviewer.properties";
    private Properties defaultProps;
    private String requestingUser, requestingOrganization;
    private static GIPSEDataFetcher fetchy = null;
    private static GeneralCacheAdministrator cacheAdmin = Cacher.getCache();

    private GIPSEDataFetcher() {
        try {
            defaultProps = new Properties();
            InputStream is = getClass().getResourceAsStream(PROPERTIES_FILE);
            if (is == null) {
                logger.error("Could not read " + PROPERTIES_FILE);
            }
            defaultProps.load(is);
            requestingUser = defaultProps.getProperty("requestingUser");
            requestingOrganization = defaultProps.getProperty("requestingOrganization");
            logger.debug("The requestingUser was " + requestingUser + " and the " +
                    "requesting Orginization was " + requestingOrganization);


        } catch (FileNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }

    public List<ServerObservations> fetchDataForRegion(RegionData region,
            Calendar startDate, Calendar endDate, Indicator indicator, Collection<ServerMetadata> serverList,
            List<AgeRange> ageRanges, List<ServiceArea> serviceAreas) {
        List<ServerObservations> returnable = null;
        if (region != null && region.getRegionName() != null) {
            RegionRelation rr = new RegionRelation();
            rr.setRegionName(region.getRegionName());
            rr.setRegionParent(region.getRegionParent());
            rr.setRegionType(region.getRegionType());
            Collection<RegionRelation> crr = new ArrayList<RegionRelation>();
            crr.add(rr);
            Collection<Indicator> indarr = new ArrayList<Indicator>();
            indarr.add(indicator);
            returnable = fetchObservations(crr, startDate, endDate, indarr, BigInteger.ONE, BigInteger.ZERO,
                    BigInteger.ZERO, "Supressed", serverList, ageRanges, serviceAreas);
        }
        return returnable;
    }

    public List<RegionalObservations> fetchRegionalObservationsByGroup(List<RegionData> rdl,
            Calendar startDate, Calendar endDate, Indicator indicator,
            Collection<ServerMetadata> serverList, List<AgeRange> ageRanges, List<ServiceArea> serviceAreas) {
        List<RegionalObservations> returnable = new ArrayList<RegionalObservations>();
        Iterator<RegionData> rdlIter = rdl.iterator();
        Collection<RegionRelation> crr = new ArrayList<RegionRelation>();
        Map<String, RegionalObservations> obsMap = new HashMap<String, RegionalObservations>();
        Collection<Indicator> indarr = new ArrayList<Indicator>();
        indarr.add(indicator);
        while (rdlIter.hasNext()) {
            RegionData region = rdlIter.next();
            RegionRelation rr = new RegionRelation();
            rr.setRegionName(region.getRegionName());
            rr.setRegionParent(region.getRegionParent());
            rr.setRegionType(region.getRegionType());
            crr.add(rr);
            RegionalObservations ro = new RegionalObservations();
            ro.setRegionData(region);
            obsMap.put(region.getRegionName(), ro);

        }
        List<ServerObservations> servObsList = fetchObservations(crr, startDate, endDate, indarr, BigInteger.ONE, BigInteger.ZERO,
                BigInteger.ZERO, "Supressed", serverList, ageRanges, serviceAreas);
        Iterator<ServerObservations> obsIter = servObsList.iterator();
        while (obsIter.hasNext()) {
            ServerObservations servObs = obsIter.next();
            ServerMetadata servMeta = servObs.getServMeta();
            List<GIPSEQueryResponseObservationSetObservation> obsList = servObs.getObservations();
            Map<String, List<GIPSEQueryResponseObservationSetObservation>> obsByRegionMap =
                    new HashMap<String, List<GIPSEQueryResponseObservationSetObservation>>();
            Iterator<GIPSEQueryResponseObservationSetObservation> obsListIter = obsList.iterator();
            while (obsListIter.hasNext()) {
                GIPSEQueryResponseObservationSetObservation obs = obsListIter.next();
                if (obsByRegionMap.containsKey(obs.getLocation())) {
                    obsByRegionMap.get(obs.getLocation()).add(obs);
                } else {
                    List<GIPSEQueryResponseObservationSetObservation> obsarr =
                            new ArrayList<GIPSEQueryResponseObservationSetObservation>();
                    obsarr.add(obs);
                    obsByRegionMap.put(obs.getLocation(), obsarr);
                }
            }
            Iterator<String> regionIter = obsByRegionMap.keySet().iterator();
            while (regionIter.hasNext()) {
                String region = regionIter.next();
                List<GIPSEQueryResponseObservationSetObservation> obscol = obsByRegionMap.get(region);
                RegionalObservations ro = obsMap.remove(region);
                ServerObservations so = new ServerObservations();
                so.setServMeta(servMeta);
                so.setObservations(obscol);
                List<ServerObservations> listSo = ro.getObservations();
                if (listSo == null) {
                    listSo = new ArrayList<ServerObservations>();
                }
                listSo.add(so);
                ro.setObservations(listSo);
                obsMap.put(region, ro);
            }
        }
        returnable.addAll(obsMap.values());
        return returnable;

    }

    public List<ServerObservations> fetchObservations(
            Collection<RegionRelation> crr, Calendar startDate,
            Calendar endDate, Collection<Indicator> indicators, BigInteger pageSize,
            BigInteger pageNumber, BigInteger suppressValue, String suppressReportAs,
            Collection<ServerMetadata> serverList, Collection<AgeRange> ageRanges, Collection<ServiceArea> serviceAreas) {
        List<ServerObservations> returnable =
                new ArrayList<ServerObservations>();
        if (crr.size() > 0) {

            gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequest request = new gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequest();
            GIPSEQueryRequestRequestCharacteristics requestCharacteristics = new GIPSEQueryRequestRequestCharacteristics();
            requestCharacteristics.setRequestDateTime(Calendar.getInstance());
            requestCharacteristics.setRequestingOrganization(requestingOrganization);
            requestCharacteristics.setRequestingUser(requestingUser);
            request.setRequestCharacteristics(requestCharacteristics);
            GIPSEQueryRequestQueryCharacteristics queryCharacteristics = new GIPSEQueryRequestQueryCharacteristics();

            //set dates
            GIPSEQueryRequestQueryCharacteristicsDates dates = new GIPSEQueryRequestQueryCharacteristicsDates();
            dates.setStart(startDate.getTime());
            dates.setEnd(endDate.getTime());
            queryCharacteristics.setDates(dates);

            //set Geo Regions
            //iterate through crr and add them to the geoRegion request

            GIPSEQueryRequestQueryCharacteristicsGeoRegions geoRegions = new GIPSEQueryRequestQueryCharacteristicsGeoRegions();
            Iterator<RegionRelation> geoIter = crr.iterator();

            Collection<GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion> geoRegionCol = new ArrayList<GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion>();
            while (geoIter.hasNext()) {
                RegionRelation rr = geoIter.next();
                GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion gr = new GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion();
                GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType type = this.loadRegionType(rr.getRegionType());
                gr.setValue(rr.getRegionName());
                gr.setType(type);
                geoRegionCol.add(gr);
                //TODO:  Add convention for "region: all" that will use the specific location feature of GIPSE
            }
            GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion[] geoRegion = new GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion[geoRegionCol.size()];
            geoRegion = geoRegionCol.toArray(geoRegion);
            geoRegions.setGeoRegion(geoRegion);
            queryCharacteristics.setGeoRegions(geoRegions);

            //set indicators

            Iterator<Indicator> indIter = indicators.iterator();
            Collection<GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator> indCol = new ArrayList<GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator>();
            while (indIter.hasNext()) {
                Indicator indy = indIter.next();
                GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator ind = new GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator();
                ind.setClassifier(indy.getClassifier());
                ind.setName(indy.getName());
                indCol.add(ind);
            }

            GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator indicator[] = new GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator[indCol.size()];
            indicator = indCol.toArray(indicator);
            GIPSEQueryRequestQueryCharacteristicsIndicators indicatorArr = new GIPSEQueryRequestQueryCharacteristicsIndicators();
            indicatorArr.setIndicator(indicator);
            queryCharacteristics.setIndicators(indicatorArr);

            //set page data (optional)
            if (pageSize != null || pageNumber != null) {
                GIPSEQueryRequestQueryCharacteristicsPageData pageData = new GIPSEQueryRequestQueryCharacteristicsPageData();
                //TODO:  double check this when it becomes enabled
                pageData.setPageNumber(pageNumber);
                pageData.setPageSize(pageSize);
                queryCharacteristics.setPageData(pageData);
            }


            //set suppress values
            if (suppressValue != null && suppressReportAs != null) {
                GIPSEQueryRequestQueryCharacteristicsSuppressValues suppressValues = new GIPSEQueryRequestQueryCharacteristicsSuppressValues();
                suppressValues.setSmallestValueReported(suppressValue);
                suppressValues.setReportAs(suppressReportAs);
                queryCharacteristics.setSuppressValues(suppressValues);
            }


            //set ages (optional)
            if (ageRanges != null && !ageRanges.isEmpty()) {
                Iterator<AgeRange> ageiter = ageRanges.iterator();
                Collection<GIPSEQueryRequestQueryCharacteristicsAgesAge> ageCol = new ArrayList<GIPSEQueryRequestQueryCharacteristicsAgesAge>();
                while (ageiter.hasNext()) {
                    AgeRange agernge = ageiter.next();
                    GIPSEQueryRequestQueryCharacteristicsAgesAge age = new GIPSEQueryRequestQueryCharacteristicsAgesAge();
                    age.setClassifier(agernge.getAgeRangeClassifier());
                    age.setName(agernge.getAgeRange());
                    ageCol.add(age);
                }
                GIPSEQueryRequestQueryCharacteristicsAges ages = new GIPSEQueryRequestQueryCharacteristicsAges();
                GIPSEQueryRequestQueryCharacteristicsAgesAge[] agearr = new GIPSEQueryRequestQueryCharacteristicsAgesAge[ageCol.size()];
                agearr = ageCol.toArray(agearr);

                ages.setAge(agearr);
                queryCharacteristics.setAges(ages);
            }
            //set service areas (optional)
            if (serviceAreas != null && !serviceAreas.isEmpty()) {
                Iterator<ServiceArea> servAreaIter = serviceAreas.iterator();
                Collection<GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea> areaCol = new ArrayList<GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea>();
                while (servAreaIter.hasNext()) {
                    ServiceArea servar = servAreaIter.next();
                    GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea area = new GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea();
                    area.setCodeset(servar.getServiceAreaClassifier());
                    area.setName(servar.getServiceArea());
                    areaCol.add(area);
                }
                GIPSEQueryRequestQueryCharacteristicsServiceAreas servAreas = new GIPSEQueryRequestQueryCharacteristicsServiceAreas();
                GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea[] servAreaArr = new GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea[areaCol.size()];
                servAreaArr = areaCol.toArray(servAreaArr);
                servAreas.setServiceArea(servAreaArr);
                queryCharacteristics.setServiceAreas(servAreas);
            }



            request.setQueryCharacteristics(queryCharacteristics);

            String requestXML = "";

            Iterator<ServerMetadata> serviter = serverList.iterator();
            try {
                requestXML = AxisUtils.serializeAxisObject(request, false, true);
                logger.trace(requestXML);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }


            logger.debug("ServerList size: " + serverList.size());
            while (serviter.hasNext()) {
                ServerMetadata queryer = serviter.next();
                gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse art = null;
                String urlToService = queryer.getServerUrl();

                //set facilities (optional)
                List<String> dataSourceList = queryer.getDataSources();
                if (dataSourceList != null && !dataSourceList.isEmpty()) {
                    Iterator<String> dataSourceIter = dataSourceList.iterator();

                    //Collection<GIPSEQueryRequestQueryCharacteristicsFacilitiesFacility> facilityCol= new ArrayList<GIPSEQueryRequestQueryCharacteristicsFacilitiesFacility>();
                    Collection<String> dataSourceCol = new ArrayList<String>();
                    while (dataSourceIter.hasNext()) {
                        String dataSource = dataSourceIter.next();
                        dataSourceCol.add(dataSource);

                    }
                    //GIPSEQueryRequestQueryCharacteristicsFacilitiesFacility[] facilityarr = new GIPSEQueryRequestQueryCharacteristicsFacilitiesFacility[facilityCol.size()];
                    String[] dataSourceOID = new String[dataSourceCol.size()];
                    //GIPSEQueryRequestQueryCharacteristicsFacilities facilities = new GIPSEQueryRequestQueryCharacteristicsFacilities();
                    dataSourceOID = dataSourceCol.toArray(dataSourceOID);
                    GIPSEQueryRequestQueryCharacteristicsDataSources dataSources = new GIPSEQueryRequestQueryCharacteristicsDataSources();
                    dataSources.setDataSourceOID(dataSourceOID);
                    queryCharacteristics.setDataSources(dataSources);
                    logger.debug("Added " + queryCharacteristics.getDataSources().getDataSourceOID().length + " dataSources to queryCharacteristics");
                }
                logger.trace(urlToService);
                String queryHash = buildQueryHashString(request, urlToService);
                try {
                    // Get from the cache
                    logger.debug("searching cache for " + queryHash);
                    art = (gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse) cacheAdmin.getFromCache(queryHash);
                } catch (NeedsRefreshException nre) {
                    try {
                        // Get the value (probably by calling an EJB)
                        logger.debug("cache miss, attempting to open service client to " + urlToService);
                        art = GIPSEQuery(urlToService, request);
                        // Store in the cache
                        cacheAdmin.putInCache(queryHash, art);
                    } catch (Exception ex) {
                        // We have the current content if we want fail-over.
                        logger.warn("attempt to get metadata from QUERY to " + urlToService + " apparently failed, now using cache failover which may be empty!");
                        art = (gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse) nre.getCacheContent();
                        // It is essential that cancelUpdate is called if the
                        // cached content is not rebuilt
                        cacheAdmin.cancelUpdate(queryHash);
                    }
                }

                //gov.cdc.ncphi.phgrid.amds.message.AMDSQueryResponse art = AMDSQuery(queryer.getServerUrl(), request);
                if (art != null) {
                    String responseXML = "";
                    try {
                        responseXML = AxisUtils.serializeAxisObject(art, false, true);
                        logger.trace(responseXML);
                    } catch (Exception e) {
                        logger.error(e.getMessage(), e);
                    }
                    ServerObservations so = new ServerObservations();
                    so.setServMeta(queryer);
                    List<GIPSEQueryResponseObservationSetObservation> obsList = new ArrayList<GIPSEQueryResponseObservationSetObservation>();
                    if (art != null && art.getObservationSet() != null && art.getObservationSet().getObservation() != null) {
                        GIPSEQueryResponseObservationSetObservation[] obsArr = art.getObservationSet().getObservation();
                        for (int k = 0; k < obsArr.length; k++) {
                            obsList.add(obsArr[k]);
                        }
                    }
                    so.setObservations(obsList);
                    returnable.add(so);
                    //returnable.add(art);
                } else {
                    logger.info("response was null");

                }
            }
        }
        return returnable;
    }

    public Collection<GmapPolygon> fetchDataForRegions(
            Collection<RegionRelation> crr, Calendar startDate,
            Calendar endDate, Collection<Indicator> indicators, BigInteger pageSize,
            BigInteger pageNumber, BigInteger suppressValue, String suppressReportAs,
            Collection<ServerMetadata> serverList, Collection<GmapPolygon> col) {
        Collection<GmapPolygon> returnable = new ArrayList<GmapPolygon>();
        List<ServerObservations> servObs = fetchObservations(crr, startDate, endDate, indicators, pageSize, pageNumber, suppressValue, suppressReportAs, serverList, null, null);
        //first, clear all old counts

        Iterator<GmapPolygon> clearIter = col.iterator();
        while (clearIter.hasNext()) {
            GmapPolygon polly = clearIter.next();
            polly.setHighestCount(0);
            polly.setTotalCount(0);
            polly.setPopupInfo(polly.getRegionName());
            polly.setTimeSeries(null);

        }

        Iterator<ServerObservations> obsIter = servObs.iterator();
        while (obsIter.hasNext()) {
            ServerObservations obs = obsIter.next();
            LoadPolygons(obs.getServMeta().getServerName(), obs.getObservations(), col, startDate, endDate);
        }
        returnable = col;
        return returnable;
    }

    public gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse GIPSEQuery(String serverURL, gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequest request) {
        gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse returnable = new gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse();
        GIPSEServiceClient client;
        try {
            client = new GIPSEServiceClient(serverURL);
            gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse aqr = client.queryGIPSE(request);
            if (aqr != null) {
                returnable = aqr;
            }
        } catch (MalformedURIException e) {
            logger.error(e.getMessage(), e);
        } catch (RemoteException e) {
            logger.error(e.getMessage(), e);
        }

        return returnable;
    }

    public void LoadPolygons(String serverName, List<GIPSEQueryResponseObservationSetObservation> observationSet, Collection<GmapPolygon> col, Calendar startDate, Calendar endDate) {
        logger.debug("entering loadPolygons");
        if (col != null) {
            //first, combine all npdsdata into time
            Map<String, Map<String, Integer>> countMap = new HashMap<String, Map<String, Integer>>();

            if (observationSet != null) {
                Iterator<GIPSEQueryResponseObservationSetObservation> observationIter = observationSet.iterator();
                while (observationIter.hasNext()) {
                    GIPSEQueryResponseObservationSetObservation obs = observationIter.next();
                    String countDate = df.format(obs.getStart());
                    String location = obs.getLocation();
                    String value = obs.getValue();
                    BigInteger bi = new BigInteger(value);
                    if (bi.intValue() > 0) {
                        logger.trace("location " + location + " had a count " + bi.intValue() + " on " + countDate);
                        Map<String, Integer> timeMap = countMap.remove(location);
                        if (timeMap == null) {
                            timeMap = new HashMap<String, Integer>();
                        }
                        Integer intCount = timeMap.remove(countDate);
                        if (intCount == null) {
                            intCount = Integer.valueOf(0);
                        }
                        int biCount = bi.intValue();
                        int intAdder = intCount.intValue();
                        int iCount = biCount + intAdder;
                        timeMap.put(countDate, new Integer(iCount));
                        countMap.put(location, timeMap);
                        logger.trace("Now putting updated count " + iCount + " into location " + location + " and date " + countDate);

                    }
                }
            }




            Iterator<GmapPolygon> fillIter = col.iterator();
            int totalCount = 0;
            int highestCount = 0;

            while (fillIter.hasNext()) {

                GmapPolygon polly = fillIter.next();
                Map<String, List<TimeSeries>> tsMap = polly.getTimeSeries();
                if (tsMap == null) {
                    tsMap = new HashMap<String, List<TimeSeries>>();
                }
                totalCount = polly.getTotalCount();
                highestCount = polly.getHighestCount();
                int iCount = 0;
                List<TimeSeries> tsList = new ArrayList<TimeSeries>();
                if (countMap.containsKey(polly.getRegionName())) {
                    logger.trace("loading " + polly.getRegionName() + "with counts");

                    Map<String, Integer> dateCountMap = countMap.get(polly.getRegionName());
                    if (dateCountMap != null) {
                        Iterator<String> dateIter = dateCountMap.keySet().iterator();
                        while (dateIter.hasNext()) {
                            iCount = 0;
                            String dateString = dateIter.next();
                            Integer dateInt = dateCountMap.get(dateString);
                            iCount = dateInt.intValue();
                            totalCount = totalCount + iCount;
                            if (iCount > highestCount) {
                                highestCount = iCount;
                            }
                            TimeSeries ts = new TimeSeries();
                            ts.setCount(iCount);
                            Calendar countDate = Calendar.getInstance();
                            try {
                                countDate.setTime(df.parse(dateString));
                            } catch (ParseException e) {
                                logger.error(e.getMessage(), e);
                            }
                            ts.setCountDate(countDate);
                            tsList.add(ts);
                        }
                        logger.trace("adding timeSeries with total count " + totalCount);

                        tsMap.put(serverName, tsList);
                        polly.setTimeSeries(tsMap);
                        polly.setTotalCount(totalCount);
                        polly.setHighestCount(highestCount);
                        polly.setStartDate(startDate);
                        polly.setEndDate(endDate);
                    }
                }
                polly.setPopupInfo(polly.getRegionName());
            }
        } else {
            logger.error("count set was  null in loader");
        }
    }

    private GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType loadRegionType(String type) {
        logger.trace("loading regionType for type " + type);
        GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType returnable = GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType.country;
        if (type.equals("state")) {
            returnable = GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType.state;
        } else if (type.equals("zip3")) {
            returnable = GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType.zip3;
        } else if (type.equals("zip5")) {
            returnable = GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType.zip5;
        } else if (type.equals("county")) {
            returnable = GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType.county;
        }
        return returnable;
    }

    public static GIPSEDataFetcher getFetcher() {
        if (fetchy == null) {
            fetchy = new GIPSEDataFetcher();
        }
        return fetchy;
    }

    private String buildQueryHashString(gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequest request, String serverUrl) {

        String returnable = "";
        //returnable += "QUERY" + serverUrl;
        returnable += df.format(request.getQueryCharacteristics().getDates().getStart());
        returnable += df.format(request.getQueryCharacteristics().getDates().getEnd());
        GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion[] geoList =
                request.getQueryCharacteristics().getGeoRegions().getGeoRegion();
        for (int k = 0; k < geoList.length; k++) {
            //returnable += geoList[k].getType().getValue() + geoList[k].getValue();
            returnable += geoList[k].getValue();
        }
        //returnable += geoList.toString();
        GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator[] indList =
                request.getQueryCharacteristics().getIndicators().getIndicator();
        for (int k = 0; k < indList.length; k++) {
            returnable += indList[k].getClassifier() + indList[k].getName();
        }
        if (request.getQueryCharacteristics().getAges() != null && request.getQueryCharacteristics().getAges().getAge() != null) {
            GIPSEQueryRequestQueryCharacteristicsAgesAge[] ageList = request.getQueryCharacteristics().getAges().getAge();
            for (int k = 0; k < ageList.length; k++) {
                returnable += ageList[k].getClassifier() + ageList[k].getName();
            }
        }
        if (request.getQueryCharacteristics().getFacilities() != null && request.getQueryCharacteristics().getFacilities().getFacility() != null) {
            GIPSEQueryRequestQueryCharacteristicsFacilitiesFacility[] facList = request.getQueryCharacteristics().getFacilities().getFacility();
            for (int k = 0; k < facList.length; k++) {
                returnable += facList[k].getCodeset() + facList[k].getName();
            }
        }
        if (request.getQueryCharacteristics().getServiceAreas() != null && request.getQueryCharacteristics().getServiceAreas().getServiceArea() != null) {
            GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea[] sAreaList = request.getQueryCharacteristics().getServiceAreas().getServiceArea();
            for (int k = 0; k < sAreaList.length; k++) {
                returnable += sAreaList[k].getCodeset() + sAreaList[k].getName();
            }
        }
        if (request.getQueryCharacteristics().getDataSources() != null && request.getQueryCharacteristics().getDataSources().getDataSourceOID() != null) {
            String[] dataSources = request.getQueryCharacteristics().getDataSources().getDataSourceOID();
            for (int k = 0; k < dataSources.length; k++) {
                returnable += dataSources[k];
            }
        }
        return returnable;
    }
}




