package gov.cdc.ncphi.phgrid.npdsgmaps;


import gov.cdc.ncphi.phgrid.poicondai.DateCount;
import gov.cdc.ncphi.phgrid.poicondai.NPDSTimeSeries;
import gov.cdc.ncphi.phgrid.poicondai.NPDSTimeSeriesByRegion;
import gov.cdc.ncphi.phgrid.polygon.gmaps.GmapPolygon;
import gov.cdc.ncphi.phgrid.polygon.gmaps.TimeSeries;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.C2FlotArrayJSPBean;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import java.util.SortedSet;
import java.util.TreeSet;



import org.apache.log4j.Logger;


public class GmapPolygonTimeSeriesLoader {
	private static final Logger logger = Logger.getLogger(GmapPolygonTimeSeriesLoader.class);
	private static Properties defaultProps, propReverse;
	private static String PROPERTIES_FILE = "/stateList-diffcap.properties";
	private static DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	/**
 * Constructor
 * 
 * Will load up a state list properties file for translating from 
 * npds states to gmap states
 */
	public GmapPolygonTimeSeriesLoader()
	{
	try {
		defaultProps = new Properties();
		propReverse = new Properties();
        InputStream is = getClass().getResourceAsStream(PROPERTIES_FILE);
        if (is == null) logger.error("Could not read " + PROPERTIES_FILE);
        defaultProps.load(is);
       
        Iterator<Object> iter = defaultProps.keySet().iterator();
        while (iter.hasNext())
        {
        	String key = (String) iter.next();
        	String value = (String) defaultProps.get(key);
        	logger.debug("inserting value " + value + " and key " + key);
        	propReverse.setProperty(key, value);
        }
		
			
	} catch (FileNotFoundException e) {
		logger.error(e.getMessage());
		//e.printStackTrace();
	} catch (IOException e) {			
		logger.error(e.getMessage());
		//e.printStackTrace();
	}
	}
	
	/**
	 * This will take the NPDS Time series from a the NPDS Time Service (collected earlier)
	 * and move the time series data into the appropriate GmapPolygons (also collected earlier)
	 * @param gmapolys
	 * @param npdsdata
	 */
	public void loadGmapPolygonForStates(Collection<GmapPolygon> gmapolys, NPDSTimeSeriesByRegion npdsdata, Calendar startDate)
	{
		logger.debug("entering loadGmapPolygonForStates");
		if(gmapolys != null && npdsdata != null)
		{
		Iterator<GmapPolygon> iter = gmapolys.iterator();
		Map <String, NPDSTimeSeries> npdmap = npdsdata.getAll();
		
		while (iter.hasNext())
		{
			GmapPolygon polly = iter.next();
			
			String stateFull = GetFullNameforState(polly.getRegionName());
			//NPDSTimeSeries npts = npdmap.get(stateFull);
			NPDSTimeSeries npts = npdmap.get(polly.getRegionName());
			if (npts !=null)
			{
			List<DateCount> dcList = npts.getCountList();
			if(dcList != null)
			{
				List<TimeSeries> tees = polly.getTimeSeries();
				//List<TimeSeries> tees = new ArrayList<TimeSeries>();
				
				int totalCount = 0, highestCount = 0;
				if (tees == null)
				{
					tees = new ArrayList<TimeSeries>();
				}
				tees.clear();
				int k=0;
				Iterator<DateCount> dcIter = dcList.iterator();
				while (dcIter.hasNext())
				{
					
					DateCount dc = dcIter.next();
					TimeSeries ts = new TimeSeries();
					ts.setCountDate(dc.getDate());
					ts.setCount(dc.getCount());
					tees.add(ts);
					if( dc.getDate().compareTo(startDate) >=0)
					{
						logger.debug("adding count for " + dc.getDate().getTime() + "to the state totals");
						if(highestCount < dc.getCount())
						{
							highestCount = dc.getCount();
						}
						totalCount += dc.getCount();
					}
					k++;
				}
				polly.setTimeSeries(tees);
				polly.setTotalCount(totalCount);
				polly.setHighestCount(highestCount);
			//	polly.setPopupInfo(polly.getRegionName().replace(' ', '+'));
				polly.setPopupInfo(stateFull.replace(' ', '+'));
				//gmapolys.remove(polly);
				//gmapolys.add(polly);
				logger.debug("added "+k+"counts to state "+stateFull + " with total count " + totalCount);
				}
			}
		}
	}else 
		logger.error("either the polygons or the npds data was null");
	}
	
	public String GetFullNameforState(String abbrev)
	{
		logger.debug("stateName = " + abbrev);
		String stateFull = (String) defaultProps.get(abbrev);
		logger.debug("stateFull = " + stateFull);
		return stateFull;
	}
		
	public void loadGmapPolygonForZip3(
			Collection<GmapPolygon> gmapolys, 
			NPDSTimeSeriesByRegion npdsdata, 
			Calendar startDate, 
			Calendar endDate ) 
	{
		logger.debug("entering loadGmapPolygonForzip3");
		if(gmapolys != null )
		{
			
		//flush out all time series graphs of zip3s

			Iterator<GmapPolygon> clearIter = gmapolys.iterator();
			while (clearIter.hasNext())
			{
				GmapPolygon polly = clearIter.next();
				 polly.setHighestCount(0);
				 polly.setTotalCount(0);
				String pollyZip3 = polly.getRegionName();
				polly.setPopupInfo(pollyZip3);
				
				
			} 
			//combine all npdsdata into time series by zip3
			if (npdsdata != null)
			{
		
		Map <String, NPDSTimeSeries> npdmap = npdsdata.getAll();
		Map <String, HashMap<String, Integer>> tsMap = new HashMap<String, HashMap<String, Integer>>();
		Set <String> zip5set = npdmap.keySet();
		Iterator<String> zip5iter = zip5set.iterator();
		String key = "", zip3 = "";
		Date current = null;
		Date dendDate = endDate.getTime();
		Calendar zeroFillStart = Calendar.getInstance();
		zeroFillStart.setTime(startDate.getTime());
		zeroFillStart.add(Calendar.DAY_OF_YEAR, -(C2FlotArrayJSPBean.C2SPAN  ));
		while (zip5iter.hasNext())
		{
			key = zip5iter.next();
			NPDSTimeSeries ts = npdmap.get(key);
			zip3 = key.substring(0, key.length() -2);
			logger.debug ("zip3 is " + zip3);
			HashMap <String, Integer> zip3counts = tsMap.get(zip3);
			if (zip3counts == null)
				//create a new map and load it with 0 counters
				{
					
					logger.debug ("Creating new set of zerocounts filling from dates " + df.format(zeroFillStart.getTime()) + " to " + df.format((endDate.getTime())));
					zip3counts = new HashMap<String, Integer>();
					current = zeroFillStart.getTime();
					while (current.compareTo(dendDate) <= 0)
					{
						logger.debug("adding zero count for "+ df.format(current));
						zip3counts.put(df.format(current), new Integer(0));
						Calendar increaser = Calendar.getInstance();
						increaser.setTime(current);
						increaser.add(Calendar.DAY_OF_YEAR, 1);
						current = increaser.getTime();
					}
				}
			List<DateCount> dcList  = ts.getCountList();
			Iterator<DateCount> dcIter = dcList.iterator();
			
			while (dcIter.hasNext())
			{
				DateCount dc = dcIter.next();
				String dformat = df.format(dc.getDate().getTime());
				logger.debug("Looking for "+ dformat +" in the map");
				Integer count = zip3counts.get(dformat);
				
				if (count != null)
				{
					logger.debug ("Found the mapping for " + dformat);
					int iCount = count.intValue();
					iCount = iCount + dc.getCount();
					zip3counts.put(dformat, iCount);
				}
				else 
				{
					logger.debug ("for some reason could not find mapping for " + dformat);
					zip3counts.put(dformat, new Integer(dc.getCount()));
				}
				
			}
			tsMap.put(zip3, zip3counts);
		}
		
		
		
		Iterator<GmapPolygon> iter = gmapolys.iterator();
		while (iter.hasNext())
		{
			GmapPolygon polly = iter.next();
			String pollyZip3 = polly.getRegionName();
			Map<String, Integer> zip3Map = tsMap.get(pollyZip3);
			int highestCount = 0, totalCount = 0;
			if (zip3Map !=null)
			{
				List<TimeSeries> timeserlist = new ArrayList<TimeSeries>();
				Set<String> keySet = zip3Map.keySet() ;
				
				SortedSet<Date> sKeySet = new TreeSet<Date>();
				Iterator<String> keyIter = keySet.iterator();
				while(keyIter.hasNext())
				{
					try{
					sKeySet.add(df.parse(keyIter.next()));
					}catch(ParseException e)
					{
						logger.error(e);
					}
					//sKeySet.addAll(keySet);
					
				}
				
				Iterator<Date> calIter = sKeySet.iterator();
				logger.debug("size of sKeySet for " + pollyZip3 + " is " + sKeySet.size());
				while (calIter.hasNext())
				{
					TimeSeries ts = new TimeSeries();
					Date cal = calIter.next();
					Calendar setCal = Calendar.getInstance();
					setCal.setTime(cal);
					ts.setCountDate(setCal);
					Integer count = zip3Map.get(df.format(cal));
					if (count == null)
						{count = new Integer(0);
						logger.debug ("Count was null?  polygon: " + pollyZip3 + " and date = " + df.format(setCal.getTime())  );
						}
					ts.setCount(count);
					timeserlist.add(ts);
					logger.debug("added date " + df.format(ts.getCountDate().getTime()) + " with count " + ts.getCount());
					//only bump highestCount or totalCount if this is part of the value range)
					if(setCal.compareTo(startDate) >=0 )
					{
					if (ts.getCount() > highestCount)
						highestCount = ts.getCount();
					totalCount = totalCount + ts.getCount();
					}
				}
				polly.setHighestCount(highestCount);
				polly.setPopupInfo(pollyZip3);
				polly.setTimeSeries(timeserlist);
				polly.setTotalCount(totalCount);
				logger.debug("adding items with total count " + totalCount + " to polygon for zip3 " + pollyZip3 );
			}
		}
			}

	}else 
		logger.error("polygons were null in the zip 3 loader");
	}
	
	public void loadGmapPolygonForZip5(
			Collection<GmapPolygon> gmapolys, 
			NPDSTimeSeriesByRegion npdsdata, 
			Calendar startDate, 
			Calendar endDate ) 
	{
		logger.debug("entering loadGmapPolygonForzip5");
		if(gmapolys != null)
		{

				
				//first, clear all old counts
				
				Iterator<GmapPolygon> clearIter = gmapolys.iterator();
				while (clearIter.hasNext())
				{
					GmapPolygon polly = clearIter.next();
					 polly.setHighestCount(0);
					 polly.setTotalCount(0);
					polly.setPopupInfo(polly.getRegionName());
					
					
				} 
			
		//first, combine all npdsdata into time series by zip3
		
			if (npdsdata != null)
			{
		Map <String, NPDSTimeSeries> npdmap = npdsdata.getAll();
		Map <String, HashMap<String, Integer>> tsMap = new HashMap<String, HashMap<String, Integer>>();
		Set <String> zip5set = npdmap.keySet();
		Iterator<String> zip5iter = zip5set.iterator();
		String key = "";
		Date current = null;
		Calendar zeroFillStart = Calendar.getInstance();
		zeroFillStart.setTime(startDate.getTime());
		zeroFillStart.add(Calendar.DAY_OF_YEAR, -(C2FlotArrayJSPBean.C2SPAN  ));
		Date dendDate = endDate.getTime();
		while (zip5iter.hasNext())
		{
			key = zip5iter.next();
			logger.debug("zip5 is " + key);
			NPDSTimeSeries ts = npdmap.get(key);
			HashMap <String, Integer> zip5counts = tsMap.get(key);
			if (zip5counts == null)
				//create a new map and load it with 0 counters
				{
					logger.debug ("Creating new set of zerocounts filling from dates " + df.format(startDate.getTime()) + " to " + df.format((endDate.getTime())));
					zip5counts = new HashMap<String, Integer>();
					//current = startDate.getTime();
					current = zeroFillStart.getTime();
					while (current.compareTo(dendDate) <= 0)
					{
						logger.debug("adding zero count for "+ df.format(current));
						zip5counts.put(df.format(current), new Integer(0));
						Calendar increaser = Calendar.getInstance();
						increaser.setTime(current);
						increaser.add(Calendar.DAY_OF_YEAR, 1);
						current = increaser.getTime();
					}
				}
			List<DateCount> dcList  = ts.getCountList();
			Iterator<DateCount> dcIter = dcList.iterator();
			while (dcIter.hasNext())
			{
				DateCount dc = dcIter.next();
				String dformat = df.format(dc.getDate().getTime());
				logger.debug("Looking for "+ dformat +" in the map");
				Integer count = zip5counts.get(dformat);
				
				if (count != null)
				{
					logger.debug ("Found the mapping for " + dformat);
					int iCount = count.intValue();
					iCount = iCount + dc.getCount();
					zip5counts.put(dformat, iCount);
				}
				else 
				{
					logger.debug ("for some reason could not find mapping for " + dformat);
					zip5counts.put(dformat, new Integer(dc.getCount()));
				}
			}
			tsMap.put(key, zip5counts);
		}
		
		
		
		Iterator<GmapPolygon> iter = gmapolys.iterator();
		while (iter.hasNext())
		{
			GmapPolygon polly = iter.next();
			String pollyZip5 = polly.getRegionName();
			Map<String, Integer> zip5Map = tsMap.get(pollyZip5);
			int highestCount = 0, totalCount = 0;
			if (zip5Map !=null)
			{
				List<TimeSeries> timeserlist = new ArrayList<TimeSeries>();
				Set<String> keySet = zip5Map.keySet() ;
				
				SortedSet<Date> sKeySet = new TreeSet<Date>();
				Iterator<String> keyIter = keySet.iterator();
				while(keyIter.hasNext())
				{
					try{
					sKeySet.add(df.parse(keyIter.next()));
					}catch(ParseException e)
					{
						logger.error(e);
					}
					//sKeySet.addAll(keySet);
					
				}
				
				Iterator<Date> calIter = sKeySet.iterator();
				logger.debug("size of sKeySet for " + pollyZip5 + " is " + sKeySet.size());
				while (calIter.hasNext())
				{
					TimeSeries ts = new TimeSeries();
					Date cal = calIter.next();
					Calendar setCal = Calendar.getInstance();
					setCal.setTime(cal);
					ts.setCountDate(setCal);
					Integer count = zip5Map.get(df.format(cal));
					if (count == null)
						{count = new Integer(0);
						logger.debug ("Count was null?  polygon: " + pollyZip5 + " and date = " + df.format(setCal.getTime())  );
						}
					ts.setCount(count);
					timeserlist.add(ts);
					logger.debug("added date " + df.format(ts.getCountDate().getTime()) + " with count " + ts.getCount());
					if( setCal.compareTo(startDate) >=0)
					{
						logger.debug("Aggregating zip5 totalCounts for date " + setCal.getTime() );
					if (ts.getCount() > highestCount)
						highestCount = ts.getCount();
					totalCount = totalCount + ts.getCount();
					}
				}
				polly.setHighestCount(highestCount);
				polly.setPopupInfo(pollyZip5);
				polly.setTimeSeries(timeserlist);
				polly.setTotalCount(totalCount);
				logger.debug("adding items with total count " + totalCount + " to polygon for zip3 " + pollyZip5 );
			}
		}
			}
	}else 
		logger.error("polygons were null in zip5 loader");
	}
		
	

	
}


