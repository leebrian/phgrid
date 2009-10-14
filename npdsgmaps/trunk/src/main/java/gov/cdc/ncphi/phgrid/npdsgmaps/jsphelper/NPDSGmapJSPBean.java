package gov.cdc.ncphi.phgrid.npdsgmaps.jsphelper;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.log4j.Logger;

import gov.cdc.ncphi.phgrid.npdsgmaps.GmapPolygonTimeSeriesLoader;
import gov.cdc.ncphi.phgrid.npdsgmaps.NPDSGmapAuth;
import gov.cdc.ncphi.phgrid.npdsgmaps.NPDSGmapTimeSeriesFetcher;
import gov.cdc.ncphi.phgrid.poicondai.NPDSTimeSeriesByRegion;
import gov.cdc.ncphi.phgrid.polygon.gmaps.GmapPolygon;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionRelation;

public class NPDSGmapJSPBean {
	private static final Logger logger = Logger.getLogger(NPDSGmapJSPBean.class);
	private static final String startTime = "0";
	private static final String endTime = "24";
	private static final String TotalCallVolume = "HECV";
	private static final String ClinicalEffectCV = "CE";
	private static Properties clinEffectProps;
	private static String PROPERTIES_FILE = "/ClinicalEffect.properties";
	private Map <String, String> clinEffectsMap;
	private NPDSGmapTimeSeriesFetcher ngtsf;
	private List<TimeTrack> timeTracks;
	
	public NPDSGmapJSPBean()
	{
		ngtsf = new NPDSGmapTimeSeriesFetcher();
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

	public String fetchAndLoadPolygonsForStates(
			GoogleJSPBean gjsp, 
			RegionSelectionBean rsb,
			Calendar startDate,
			Calendar endDate,
			String clinEffects)
	{
		timeTracks.clear();
		Date startFetch = new Date();
		String returnable = "";
		//get state polygons
		Collection<RegionRelation> crr = rsb.getRegionListByParentAndType("state", "USA");
		Collection<GmapPolygon> col = gjsp.getPolygonCollectionForRegionRelations(crr);
		gjsp.setPolygonCollection(col);  
		//get state data
		String callVolumeType = TotalCallVolume;
		if (clinEffects != null && !clinEffects.equals(""))
		{
			callVolumeType = ClinicalEffectCV;
		}
		Date startServiceQuery = new Date();
		//NPDSGmapTimeSeriesFetcher ngtsf = new NPDSGmapTimeSeriesFetcher();
		NPDSTimeSeriesByRegion tsr = ngtsf.GetTimeSeriesByState(
				crr, startDate, endDate, startTime, 
				endTime, clinEffects, callVolumeType, 
				""); 
		Date endServiceQuery = new Date();
		
		//add state data to polygons
		GmapPolygonTimeSeriesLoader gptsl = new GmapPolygonTimeSeriesLoader();
		gptsl.loadGmapPolygonForStates(col, tsr, startDate);
		gjsp.setPolygonCollection(col);
		//Iterator<GmapPolygon> iter = col.iterator();
    	//returnable = gjsp.getMap();
    	//logger.debug(returnable);
		Date endFetch = new Date();
		
		long query = (endServiceQuery.getTime() - startServiceQuery.getTime());
		long fetch = (endFetch.getTime() - startFetch.getTime() - query);
		timeTracks.add(new TimeTrack("State ServerSide processing", fetch));
		timeTracks.add(new TimeTrack("State Service Query", query));
    	
    	return returnable;
	}
	
	public List<TimeTrack> getTimeTracks()
	{
		return timeTracks;
	}
	
	public String fetchAndLoadPolygonsForZip3(
			GoogleJSPBean gjsp, 
			RegionSelectionBean rsb,
			String state,
			Calendar startDate,
			Calendar endDate,
			String clinEffects)
	{
		timeTracks.clear();
		Date startFetch = new Date();
		String returnable = "";
		//get zip3 polygons
		Collection<RegionRelation> crr = rsb.getRegionListByParentAndType("zip3", state);
		Collection<GmapPolygon> col = gjsp.getPolygonCollectionForRegionRelations(crr);
		gjsp.setPolygonCollection(col);  
		//get zip3 data
		String callVolumeType = TotalCallVolume;
		if (clinEffects != null && !clinEffects.equals(""))
		{
			callVolumeType = ClinicalEffectCV;
		}
		//NPDSGmapTimeSeriesFetcher ngtsf = new NPDSGmapTimeSeriesFetcher();
		
		Date startServiceQuery = new Date();
		NPDSTimeSeriesByRegion tsr = ngtsf.GetTimeSeriesByZip3( 
				crr, state, startDate, endDate, startTime, endTime,
				clinEffects, callVolumeType,"");
		//add state data to polygons
		Date endServiceQuery = new Date();
		
		GmapPolygonTimeSeriesLoader gptsl = new GmapPolygonTimeSeriesLoader();
		gptsl.loadGmapPolygonForZip3(col, tsr, startDate, endDate);
		gjsp.setPolygonCollection(col);
		//gjsp.setFocus("state", state);
    	//returnable = gjsp.getMap();
    	//logger.debug(returnable);
		Date endFetch = new Date();
		
		long query = (endServiceQuery.getTime() - startServiceQuery.getTime());
		long fetch = (endFetch.getTime() - startFetch.getTime() - query);
		timeTracks.add(new TimeTrack("Zip3 ServerSide processing", fetch));
		timeTracks.add(new TimeTrack("Zip3 Service Query", query));
    	
    	return returnable;
	}
	
	
	public String fetchAndLoadPolygonsForZip5(
			GoogleJSPBean gjsp, 
			RegionSelectionBean rsb,
			String zip3,
			Calendar startDate,
			Calendar endDate,
			String clinEffects)
	{
		timeTracks.clear();
		Date startFetch = new Date();
		String returnable = "";
		//get state polygons
		Collection<RegionRelation> crr = rsb.getRegionListByParentAndType("zip5", zip3);
		Collection<GmapPolygon> col = gjsp.getPolygonCollectionForRegionRelations(crr);
		gjsp.setPolygonCollection(col);  
		//get state data
		String callVolumeType = TotalCallVolume;
		if (clinEffects != null && !clinEffects.equals(""))
		{
			callVolumeType = ClinicalEffectCV;
		}
		//NPDSGmapTimeSeriesFetcher ngtsf = new NPDSGmapTimeSeriesFetcher();
		
		Date startServiceQuery = new Date();
		NPDSTimeSeriesByRegion tsr = ngtsf.GetTimeSeriesByZip5(crr,
				zip3, startDate, endDate, startTime, endTime,
				clinEffects, callVolumeType, "");
		Date endServiceQuery = new Date();
		//add state data to polygons
		GmapPolygonTimeSeriesLoader gptsl = new GmapPolygonTimeSeriesLoader();
		gptsl.loadGmapPolygonForZip5(col, tsr, startDate, endDate);
		gjsp.setPolygonCollection(col);
		//Iterator<GmapPolygon> iter = col.iterator();
	//gjsp.setFocus("zip3", zip3);
    //	returnable = gjsp.getMap();
    //	logger.debug(returnable);

		Date endFetch = new Date();
		
		long query = (endServiceQuery.getTime() - startServiceQuery.getTime());
		long fetch = (endFetch.getTime() - startFetch.getTime() - query);
		timeTracks.add(new TimeTrack("Zip5 ServerSide processing", fetch));
		timeTracks.add(new TimeTrack("Zip5 Service Query", query));
    	return returnable;
	}
	
	public Map<String, String> getClinicalEffects()
	{
		return clinEffectsMap;
	}
	
	public boolean authenticate(String username, String password)	{
		boolean returnable = false;

		NPDSGmapAuth authy = new NPDSGmapAuth();
		
		if (authy.checkAuth(username, password))
		{return true;}
		return returnable;
	}
	


}
