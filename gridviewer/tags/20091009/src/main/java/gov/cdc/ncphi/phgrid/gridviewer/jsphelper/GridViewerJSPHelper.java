package gov.cdc.ncphi.phgrid.gridviewer.jsphelper;


import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse;
import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion;
import gov.cdc.ncphi.phgrid.gridviewer.GIPSEDataFetcher;
import gov.cdc.ncphi.phgrid.gridviewer.GIPSEServerFetcher;
import gov.cdc.ncphi.phgrid.gridviewer.Cacher;
import gov.cdc.ncphi.phgrid.gridviewer.Indicator;
import gov.cdc.ncphi.phgrid.gridviewer.ServerMetadata;
import gov.cdc.ncphi.phgrid.polygon.gmaps.GmapPolygon;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionData;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionListFetcher;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionRelation;
import gov.cdc.ncphi.phgrid.services.gipse.common.AxisUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class GridViewerJSPHelper {
	private static final Logger logger = Logger.getLogger(GridViewerJSPHelper.class);
	private static Properties clinEffectProps;
	private static String PROPERTIES_FILE = "/ClinicalEffect.properties";
	private Map <String, String> clinEffectsMap;
	private List<TimeTrack> timeTracks;
	private static GeneralCacheAdministrator cacheAdmin = Cacher.getCache();
	
	public GridViewerJSPHelper()
	{
		try {
		clinEffectProps = new Properties();
		InputStream is = getClass().getResourceAsStream(PROPERTIES_FILE);
        if (is == null) logger.error("Could not read " + PROPERTIES_FILE);
        clinEffectProps.load(is);
        Set<Object> keySet = clinEffectProps.keySet();
        Iterator<Object> iter = keySet.iterator();
        String key = "";
        clinEffectsMap = new TreeMap<String, String>();
        timeTracks  = new ArrayList<TimeTrack>();
        while(iter.hasNext())
        {
        	key = (String) iter.next();
        	clinEffectsMap.put((String) clinEffectProps.getProperty(key), key );
        }
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {			
			logger.error(e.getMessage());
			//e.printStackTrace();
		}

	}


	
	public String getNPDSPolygons(GoogleJSPBean gjsp, RegionSelectionBean rsb, 
			Calendar startDate, Calendar endDate, String classifiers, String indicators, Set<String> sendServerSet,
			String regionType, String regionParent, boolean addQuery)
	{
		Collection<RegionRelation> regions = rsb.getRegionListByParentAndType(regionType, regionParent);
		
		Collection<GmapPolygon> col = gjsp.getPolygonCollectionForRegionRelations(regions);
		Collection<Indicator> indicatorCol = new ArrayList<Indicator>();
		Indicator indy = new Indicator();
		indy.setClassifier(classifiers);
		indy.setName(indicators);
		indicatorCol.add(indy);
		
		return fetchAndLoadPolygonsForRegion(gjsp, regions, col, startDate, 
				endDate, indicatorCol, BigInteger.ONE, BigInteger.ZERO, 
				BigInteger.ZERO, "Supressed", sendServerSet, addQuery);
	}
	
	public String fetchAndLoadPolygonsForRegion(
			GoogleJSPBean gjsp,
			Collection<RegionRelation> regions, 
			Collection<GmapPolygon> col,
			Calendar startDate,
			Calendar endDate,
			Collection<Indicator> indicators,
			BigInteger pageSize,
			BigInteger pageNumber,
			BigInteger suppressValue,
			String suppressReportAs,
			Set<String> serverList,
			boolean addQuery)
	{
		timeTracks.clear();
		Date startTrack = new Date();
		String returnable = "";
		GIPSEDataFetcher adf = GIPSEDataFetcher.getFetcher();
		Date startQuery = new Date();
		Collection<Collection<GmapPolygon>> colcol = gjsp.getPolygonCollection();
		if (colcol == null)
		{
			colcol = new ArrayList<Collection<GmapPolygon>>();
		}
		if(!serverList.isEmpty())
		{
			if (!addQuery)
			{
				clearMap(gjsp, colcol);
			}
		Collection<GmapPolygon> resps = adf.fetchDataForRegions(regions, startDate, endDate, 
				indicators, pageSize, pageNumber, suppressValue, suppressReportAs, getServerSet(serverList), col );
		colcol.add(col);
		
		}
		else
		{
			clearMap(gjsp, colcol);
		}
		Date endQuery = new Date();
		gjsp.setPolygonCollection(colcol);
		Date endTrack = new Date();
		long query = (endQuery.getTime() - startQuery.getTime());
		long fetch = (endTrack.getTime() - startTrack.getTime() - query);
		timeTracks.add(new TimeTrack("ServerSide processing", fetch));
		timeTracks.add(new TimeTrack("Service Query", query));
    	return returnable;
	}
	
	public void clearMap(GoogleJSPBean gjsp, Collection<Collection<GmapPolygon>> colcol)
	{
		//clear the collection and add a new empty set (empty sets will still allow for iterators and the like)
		colcol.clear();
		colcol.add(new ArrayList<GmapPolygon>());
		gjsp.setPolygonCollection(colcol);
	}
	

	public Map<String, ServerMetadata> getServers()
	{
		GIPSEServerFetcher asf = GIPSEServerFetcher.getServerFetcher();
		Map<String, ServerMetadata>   returnable = asf.getServers();
		return returnable;
	}
	public List<TimeTrack> getTimeTracks()
	{
		return timeTracks;
	}
	
	public Collection<RegionRelation> getRegions(String regionType, String regionParent, Set<String> serverList)
	{
		
		Set<RegionRelation> returnable = new TreeSet <RegionRelation> ();
		Collection<ServerMetadata> servMeta = getServerSet(serverList);
		Iterator<ServerMetadata> servIter = servMeta.iterator();
		logger.debug("fetching regions for type " + regionType);
		while(servIter.hasNext())
		{
			ServerMetadata meta = servIter.next();
			if (meta.getMrt() != null && meta.getMrt().getGeoRegionsSupported() != null && 
					meta.getMrt().getGeoRegionsSupported().getGeoRegion() != null)
			{
			MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion gIter[] = meta.getMrt().getGeoRegionsSupported().getGeoRegion();
			for (int k=0; k<gIter.length; k++)
			{
				RegionRelation regrel = new RegionRelation();
				MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion geode = gIter[k];
				if (geode != null && geode.getType().getValue().equals(regionType))
				{
					regrel.setRegionName(geode.getValue());
					regrel.setRegionParent(regionParent);
					regrel.setRegionType(regionType);
					returnable.add(regrel);
					logger.trace("adding region " + geode.getValue());
				}
			}
			}
			
		}
		return returnable;
	}
	public Collection<ServerMetadata> getServerSet(Set<String> serverList)
	{
		Collection<ServerMetadata> returnable = new ArrayList<ServerMetadata>();
		if (serverList != null)
		{
		Map<String, ServerMetadata> serverMap = this.getServers();
		Iterator<String> serverIter = serverList.iterator();
		while( serverIter.hasNext())
			{
			String serverKey = serverIter.next();
			returnable.add(serverMap.get(serverKey));
			}
		}
		return returnable;
			
	}
	
	public Collection<ServerMetadata> getServerSetWithDataSources(List<ServerDataSources> serverList)
	{
		Collection<ServerMetadata> returnable = new ArrayList<ServerMetadata>();
		if (serverList != null)
		{
		Map<String, ServerMetadata> serverMap = this.getServers();
		Iterator<ServerDataSources> serverIter = serverList.iterator();
		while( serverIter.hasNext())
			{
			ServerDataSources serverKey = serverIter.next();
			logger.trace("adding serverKey " + serverKey + " to serverMetadata list");
			ServerMetadata serverMet = serverMap.get(serverKey.getServerName());
			serverMet.setDataSources(serverKey.getDataSources());
			if(serverMet.getDataSources() ==null || serverMet.getDataSources().isEmpty())
			{
				logger.warn("Datasources were empty");
			}
			else 
			{
				logger.debug("Number of Datasources: " + serverMet.getDataSources().size());
			}
			returnable.add(serverMet);
			}
		}
		else {
			logger.warn("serverList was null in getServerSetWithDataSources");
		}
		return returnable;
			
	}
	
	
	public void clearCache()
	{
		cacheAdmin.flushAll();
		logger.debug("Cleared Cache");
	}
}
