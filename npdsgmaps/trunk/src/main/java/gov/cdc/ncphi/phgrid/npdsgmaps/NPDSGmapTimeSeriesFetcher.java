package gov.cdc.ncphi.phgrid.npdsgmaps;

import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import gov.cdc.ncphi.phgrid.polygon.jsphelper.C2FlotArrayJSPBean;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean;

import gov.cdc.ncphi.phgrid.poicondai.NPDSTimeSeries;
import gov.cdc.ncphi.phgrid.poicondai.NPDSTimeSeriesByRegion;
import gov.cdc.ncphi.phgrid.poicondai.NPDSTimeServiceFetcher;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionRelation;
/**
 * This is the class that will fetch NPDS Data for given regional collections
 * @author Peter White <peter.white@bearingpoint.com>
 *
 */
public class NPDSGmapTimeSeriesFetcher {

	private static final Logger logger = Logger.getLogger(NPDSGmapTimeSeriesFetcher.class);
	private NPDSTimeServiceFetcher ntsf;
	private C2FlotArrayJSPBean c2f;
	private static final String ZIP3TYPE = "zip3";
	private static final String ZIP5TYPE = "zip5";
	private static final String AAPCenter = "97";
	private static final String OtherCenters = "1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,19,20,21,22,23,24,25,27,28,30,31,33,35,36,39,40,44,53,54,61,65,69,72,75,78,81,83,84,86,87,88,89,90,91,94,95,97,100";
	private static final String COCenter = "8";
	private Map<String, NPDSTimeSeriesByRegion> timeSeriesMap;
	/**
	 * This is the constructor
	 */
	public NPDSGmapTimeSeriesFetcher()
	{
		ntsf = new NPDSTimeServiceFetcher();
		timeSeriesMap = new HashMap<String, NPDSTimeSeriesByRegion>();
		
		
	}
	public NPDSTimeSeriesByRegion GetNPDSTimeSeriesForRegion(String regionType, 
			Collection<RegionRelation> crr, 
			String centerList, 
			String zipList, 
			Calendar startDate, 
			Calendar endDate, 
			String startTime, 
			String endTime, 
			String clinicalEffectList, 
			String callVolumeType, 
			String ceCombinationType)
	{
		NPDSTimeSeriesByRegion returnable = null;
		if (regionType.equalsIgnoreCase("state"))
			returnable = GetTimeSeriesByState(crr, 
					startDate, 
					endDate, 
					startTime, 
					endTime, 
					clinicalEffectList, 
					callVolumeType, 
					ceCombinationType);
		else if (regionType.equalsIgnoreCase("zip3"))
		{
			returnable = GetTimeSeriesByZip3(crr, 
					zipList,
					startDate, 
					endDate, 
					startTime, 
					endTime, 
					clinicalEffectList, 
					callVolumeType, 
					ceCombinationType);
		}
		else if (regionType.equalsIgnoreCase("zip5"))
		{
			returnable = GetTimeSeriesByZip5(crr, zipList,
			startDate, 
			endDate, 
			startTime, 
			endTime, 
			clinicalEffectList, 
			callVolumeType, 
			ceCombinationType);
		}
		return returnable;
	}
	public NPDSTimeSeriesByRegion GetTimeSeriesByState(
			Collection<RegionRelation> crr, 
			Calendar startDatePass, 
			Calendar endDate, 
			String startTime, 
			String endTime, 
			String clinicalEffectList, 
			String callVolumeType, 
			String ceCombinationType)
	{
		NPDSTimeSeriesByRegion returnable = null;
		String hashString = startDatePass.getTime().toString() + endDate.getTime().toString() + startTime + endTime+ clinicalEffectList +ceCombinationType; 
		if (timeSeriesMap.containsKey(hashString))
		{
			return timeSeriesMap.get(hashString);
		}
		
		String stateList = "";
		
		//pull 30 days prior for c2 averaging
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(startDatePass.getTime());
		startDate.add(Calendar.DAY_OF_YEAR, -(C2FlotArrayJSPBean.C2SPAN  ));
		/*Iterator<RegionRelation> iter = crr.iterator();
		while (iter.hasNext())
		{
			RegionRelation rr = iter.next();
			stateList = stateList+ rr.getRegionName() + ",";
		}
		
		stateList = stateList.substring(0, stateList.length()-1);
		logger.debug("stateList = " + stateList);*/
		
		returnable = ntsf.getStateResults(AAPCenter, 
				stateList, "", startDate, endDate, 
				startTime, endTime, clinicalEffectList, 
				callVolumeType, ceCombinationType);
		timeSeriesMap.put(hashString, returnable);
		return returnable;
	}
	public NPDSTimeSeriesByRegion GetTimeSeriesByZip3(
			Collection<RegionRelation> crr, 
			String state,
			Calendar startDatePass, 
			Calendar endDate, 
			String startTime, 
			String endTime, 
			String clinicalEffectList, 
			String callVolumeType, 
			String ceCombinationType)
	{
		NPDSTimeSeriesByRegion returnable = null;
		String hashString = state+startDatePass.getTime().toString() + endDate.getTime().toString() + startTime + endTime+ clinicalEffectList +ceCombinationType; 
		if (timeSeriesMap.containsKey(hashString))
		{
			return timeSeriesMap.get(hashString);
		}
			String zipList = "";
			Calendar startDate = Calendar.getInstance();
			startDate.setTime(startDatePass.getTime());
			startDate.add(Calendar.DAY_OF_YEAR, -(C2FlotArrayJSPBean.C2SPAN ));
		returnable = ntsf.getZipcodeResults("",
				state, 
				zipList, 
				startDate, 
				endDate, 
				startTime, 
				endTime, 
				clinicalEffectList, 
				callVolumeType, 
				ceCombinationType);
		//zero-fill empty states' zip3s
		timeSeriesMap.put(hashString, returnable);
		return returnable;
	}
	
	public NPDSTimeSeriesByRegion GetTimeSeriesByZip5(
			Collection<RegionRelation> crr, 
			String zip3, 
			Calendar startDatePass, 
			Calendar endDate, 
			String startTime, 
			String endTime, 
			String clinicalEffectList, 
			String callVolumeType, 
			String ceCombinationType)
	{
		String hashString = zip3+startDatePass.getTime().toString() + endDate.getTime().toString() + startTime + endTime+ clinicalEffectList +ceCombinationType; 
		if (timeSeriesMap.containsKey(hashString))
		{
			return timeSeriesMap.get(hashString);
		}
		Calendar startDate = Calendar.getInstance();
		startDate.setTime(startDatePass.getTime());
		NPDSTimeSeriesByRegion returnable = null;
		startDate.add(Calendar.DAY_OF_YEAR, -(C2FlotArrayJSPBean.C2SPAN ));
		if (zip3 != null)
		{
			String zipList = "";
			String state = "";
			RegionSelectionBean rsb = new RegionSelectionBean();
			state = rsb.getRegionParent(zip3);	
		
			if (state !=null)
			{
		logger.debug("selecting zip5 data for state: " + state);
		returnable = ntsf.getZipcodeResults("",
				state, 
				zipList, 
				startDate, 
				endDate, 
				startTime, 
				endTime, 
				clinicalEffectList, 
				callVolumeType, 
				ceCombinationType);
		if (returnable != null)
		{
			// go through and limit zipcodes to given zip5s

			logger.debug("trimming the zip5s to the ones given in the range");
			Iterator<RegionRelation> crrIter = crr.iterator();
			RegionRelation rr = null;
			Set<String> crrSet = new TreeSet<String>();
			while(crrIter.hasNext())
			{
				rr = crrIter.next();
				crrSet.add(rr.getRegionName());
			}

			Map<String, NPDSTimeSeries> tsMap =  returnable.getAll();
			Set<String> keyset = tsMap.keySet();
			Iterator <String> iter = keyset.iterator();
			String currentKey = null;
			Set<String> removeSet = new TreeSet<String>();
			while (iter.hasNext())
			{
				currentKey = iter.next();
				if (!crrSet.contains(currentKey))
				{
					removeSet.add(currentKey);
				}
			}
			iter = removeSet.iterator();
			while (iter.hasNext())
			{
				tsMap.remove(iter.next());
			}
		}
			}
			}
		timeSeriesMap.put(hashString, returnable);
		return returnable;
	}
}
