package gov.cdc.ncphi.phgrid.polygon.jsphelper;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import gov.cdc.ncphi.phgrid.polygon.gmaps.GmapPolygon;
import gov.cdc.ncphi.phgrid.polygon.gmaps.TimeSeries;

/**
 * This will allow the FlotPlot.jsp page to easily collect
 * the arrays based on the necessary javascript arrays for polygons with
 * C2-compliant time series (30-day preceding counts for averaging)
 */
public class C2FlotArrayJSPBean {
	public static final int C2LENGTH = 29;
	public static final int C2SPAN = 30;
	private static final int NUMSTDEV = 4;
	private static final double STDEVFLOOR = 1;
	private static final Logger logger = Logger.getLogger(C2FlotArrayJSPBean.class);
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	/**
	 * 
	 * 
	 * @return a string with the arrays to be used by the flot
	 * chart in flotplot.jsp.  d1 being outliers, d2 being data,
	 * and d3 being averages
	 */
	public String getArrayString(GmapPolygon gpol)
	{
		
		String returnable = "";
		
		//loadPolygonZeroValues(gpol);
		//fetch the time series
		Iterator<List<TimeSeries>> tsIter = gpol.getTimeSeries().values().iterator();
		Map<String, Integer> tsMap = new HashMap<String, Integer>();
		while(tsIter.hasNext())
		{
			Iterator<TimeSeries> tsi = tsIter.next().iterator();
			while(tsi.hasNext())
			{
				TimeSeries times = tsi.next();
				if(!tsMap.containsKey(times.getCountDate()))
				{
					tsMap.put(df.format(times.getCountDate().getTime()), Integer.valueOf(times.getCount()));
				}
				else
				{
					int increment = tsMap.remove(df.format(times.getCountDate().getTime())).intValue();
					increment += times.getCount();
					tsMap.put(df.format(times.getCountDate().getTime()), Integer.valueOf(increment));					
				}
			}
		}
		
		Calendar increaser = Calendar.getInstance();
		increaser.setTime(gpol.getStartDate().getTime());
		increaser.add(Calendar.DAY_OF_YEAR, -(C2FlotArrayJSPBean.C2SPAN  ));
		List<TimeSeries> ts = new ArrayList<TimeSeries>();
		
		while (df.format(increaser.getTime()).compareTo(df.format(gpol.getEndDate().getTime())) <= 0)
		{
//			
			TimeSeries tseries =  new TimeSeries();
			Calendar cDate = Calendar.getInstance();
			cDate.setTime(increaser.getTime());
			if (!tsMap.containsKey(df.format(increaser.getTime())))
			{
				logger.trace("adding zero count for "+ df.format(increaser.getTime()));
				tseries.setCountDate(cDate);
				tseries.setCount(0);
				ts.add(tseries);
			}
			else
			{
				logger.trace("adding count of " + tsMap.get(df.format(increaser.getTime())).intValue() +" for "+ df.format(increaser.getTime())); 
				tseries.setCountDate(cDate);
				tseries.setCount(tsMap.get(df.format(increaser.getTime())).intValue());
				ts.add(tseries);
			}
			increaser.add(Calendar.DAY_OF_YEAR, 1);
		}    
				
		if (ts != null && ts.size() >=C2SPAN ) // C2 requires 30 days of data for a rolling average
		{
			
			String counts = "[ ", averages ="[ ", outliers = "[ ";
		//build arrays of averages and means
			List<TimeSeries> subList = null;
			double mean = 0, stdev  = 0, count = 0;
			Calendar countDate = null;
			

		for(int k=0; k<ts.size() - C2SPAN; k++)
		{
			subList = ts.subList(k, k+C2LENGTH);
			
			mean = getAverageCount(subList);
			stdev = getStandardDeviation(subList, mean);
			
			countDate = ts.get(k+C2SPAN).getCountDate();
			count = ts.get(k+C2SPAN).getCount();
			
			logger.debug("evaluated SDev and Average from " + subList.get(0).getCountDate().getTime() +" to " + 
					subList.get(C2LENGTH -1).getCountDate().getTime() + " for count on "  + countDate.getTime());
			//add the the values to the appropriate arrays
			counts += getArrayElem (countDate, count);
			averages += getArrayElem (countDate, mean);
			if (stdev > STDEVFLOOR) // && Math.abs((stdev * NUMSTDEV)- mean) < Math.abs(count - mean)) // count is more than NUMSTDEVs outside the mean... it's an outlier
			{
				if ((count > mean && ((stdev * NUMSTDEV)+ mean) < count) || count < mean && (mean -(stdev * NUMSTDEV)) > count)
				{
				logger.debug(" Math.abs((stdev * NUMSTDEV)- mean) was " + Math.abs((stdev * NUMSTDEV)- mean)+ 
						"and count minus mean was  " +Math.abs(count - mean) +  " so we added it to d1 ");
				outliers += getArrayElem (countDate, count);
				}
			}
		}
		counts  = capString(counts);
		averages = capString(averages);
		outliers = capString (outliers);
		//put the strings together
		returnable += "d1 = " + outliers + "\n";
		returnable += "d2 = " + counts + "\n" ;
		returnable += "d3 = " + averages + "\n";
			logger.debug("returnable is " + returnable);
		}
		else
		{
			logger.error("Either the polygon's time series was null, or there were less than 30 days worth of data");
		}
		
		return returnable;
	}
	
	private double getAverageCount(List<TimeSeries> avspan)
	{
		double returnable = 0;
		if (avspan != null && avspan.size() >1) //otherwise, we get div by 0, and that is bad.
		{
		    double sum = 0;  // sum of all the elements
		    for (int i=0; i<avspan.size(); i++) {
		        sum += avspan.get(i).getCount();
		       // logger.debug("date was " +avspan.get(i).getCountDate().getTime() + " and count was " + avspan.get(i).getCount());
		    }
		    returnable =  sum / avspan.size();
		}
		else
		{
			logger.error("avspan was null or had less than two values for average count");
		}
		
		return returnable;
		    
		
	}
	
	private double getStandardDeviation (List<TimeSeries> avspan, double mean)
	{
		double returnable = 0;
		// sd is sqrt of sum of (values-mean) squared divided by n - 1
	      // Calculate the mean
		if(avspan != null && avspan.size() >1)
		{
	      final int n = avspan.size();
	   // sd is sqrt of sum of (values-mean) squared divided by n - 1
	      // Calculate the mean
	      //double mean = 0;
	     //final int n = data.length;

	      // calculate the sum of squares
	      double sum = 0;
	      for ( int i=0; i<n; i++ )
	         {
	         final double v = avspan.get(i).getCount() - mean;
	         sum += v * v;
	         }
	      returnable =  Math.sqrt( sum / ( n - 1 ) );
	      logger.debug("Standard deviation is " + returnable);
	      }
		else 
		{
			logger.error("avspan was null or had less than two values for standard deviation");
		}



		
		return returnable;
	}
	
	private String getArrayElem(Calendar countDate, double value)
	{
		String returnable = "";
		Date date = null;
		if (countDate!=null)
		{
			DecimalFormat df = new DecimalFormat("###.##");
			SimpleDateFormat daf = new SimpleDateFormat("MM/dd/yyyy/zzz");
	    	String parseString = "" +(countDate.get(Calendar.MONTH)+1)+"/"+(countDate.get(Calendar.DAY_OF_MONTH))+"/"+
    		countDate.get(Calendar.YEAR)+"/gmt";
	    	try {
	    		
	    		date = daf.parse(parseString);
	    	} catch (ParseException e) {
	    		logger.error("improperly parsed string", e);
	    	}
			//date = countDate.getTime();
			//logger.debug("ParseString was " + parseString + " and date is " + date + " and millis are " + date.getTime());
			//logger.debug("Original date was " + countDate.getTime());
			
			returnable = "[ " + date.getTime() + ", " + df.format(value) + "],";
		}
		else
		{
			logger.error("Calendar was null in javascript array element builder");
		}
		return returnable;
	}
	
	private String capString (String capper)
	{
		if (capper != null)
		{
			capper  = capper.substring(0, capper.length()-1) + "];";
		}
		return capper;
	}
	
	/*private void loadPolygonZeroValues(GmapPolygon polly)
	{
		if(polly != null && polly.getTotalCount() >0 && polly.getTimeSeries() != null &&
				polly.getStartDate() != null && polly.getEndDate() != null)
		{
			Date startDate = polly.getStartDate().getTime();
			Date endDate = polly.getEndDate().getTime();
			Calendar increaser = Calendar.getInstance();
			Set <String> countSet = new HashSet<String>();
			List<TimeSeries> tsList = polly.getTimeSeries();
			Iterator<TimeSeries> dcIter = tsList.iterator();
			while (dcIter.hasNext())
			{
				TimeSeries ts  = dcIter.next();
				countSet.add(df.format(ts.getCountDate().getTime()));
			}
			increaser.setTime(startDate);
			increaser.add(Calendar.DAY_OF_YEAR, -(C2FlotArrayJSPBean.C2SPAN  ));
			startDate = increaser.getTime();
			while (startDate.compareTo(endDate) <= 0)
			{
//				logger.trace("adding zero count for "+ df.format(current));
				if (!countSet.contains(df.format(startDate.getTime())))
				{
					TimeSeries zero = new TimeSeries();
					Calendar cDate = Calendar.getInstance();
					cDate.setTime(startDate);
					zero.setCountDate(cDate);
					zero.setCount(0);
					tsList.add(zero);
				}
				
				increaser.setTime(startDate);
				increaser.add(Calendar.DAY_OF_YEAR, 1);
				startDate = increaser.getTime();
			}
			
			
		}
	}*/
}
