package gov.cdc.ncphi.phgrid.polygon.gmaps;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class StandardPolygonPopupHandler implements PolygonPopupHandler {

	private static final Logger logger = Logger.getLogger(StandardPolygonPopupHandler.class);
	private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	
	private String dateList = "|";
	private String valueList = "";
	public String getPopupString(String position, GmapPolygon polygon, String passInKey, int index) {
		String returnable =  "GEvent.addListener(polygon" + position + ", \"click\", function(latlng) {\n";
	   	   returnable += "map.openInfoWindowHtml(latlng, '"+polygon.getRegionName() +" <br> "+ polygon.getTotalCount()+ " total cases <br> " +
	   	   this.getMapImageString(polygon)+ "')\n";
		   returnable += "});\n";
		   returnable += "map.addOverlay(polygon"+position+");\n";
		return returnable;
	}

	public boolean setFormatString() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private String getMapImageString (GmapPolygon poly)
	{
		String returnable = "";
		String titlePrefix = (String) poly.getPopupInfo();
		if (poly.getTotalCount() >0)
		{
			Map<String, Integer> timeSeriesMap = new HashMap<String, Integer>();
			Iterator<List<TimeSeries>> tsIter = poly.getTimeSeries().values().iterator();
			while(tsIter.hasNext())
			{
				Iterator<TimeSeries> tsi = tsIter.next().iterator();
				while(tsi.hasNext())
				{
					TimeSeries ts = tsi.next();
					if(!timeSeriesMap.containsKey(ts.getCountDate()))
					{
						timeSeriesMap.put(df.format(ts.getCountDate().getTime()), Integer.valueOf(ts.getCount()));
					}
					else
					{
						int increment = timeSeriesMap.remove(df.format(ts.getCountDate().getTime())).intValue();
						increment += ts.getCount();
						timeSeriesMap.put(df.format(ts.getCountDate().getTime()), Integer.valueOf(increment));					
					}
				}
			}
			Calendar increaser = Calendar.getInstance();
			increaser.setTime(poly.getStartDate().getTime());
			ArrayList<Integer> countList = new ArrayList<Integer>();
			
			while (df.format(increaser.getTime()).compareTo(df.format(poly.getEndDate().getTime())) <= 0)
			{
//				logger.trace("adding zero count for "+ df.format(current));
				if (!timeSeriesMap.containsKey(df.format(increaser)))
				{
					countList.add(Integer.valueOf(0));
				}
				else
				{
					countList.add(timeSeriesMap.get(df.format(increaser)));
				}
				increaser.add(Calendar.DAY_OF_YEAR, 1);
			}    
			Integer counts[] = new Integer[countList.size()];
			countList.toArray(counts);
		dateList = "|";
		valueList = ""; 
		String tableTitle = "";
		double converter = 0;
		double highestCount = poly.getHighestCount();
		if (highestCount > 0)
			converter = 100/highestCount;
		if (counts.length < 180)
		{
		for (int k=0; k<counts.length; k++)
		{
			double convertdoub = counts[k].doubleValue();
			
			convertdoub = convertdoub*converter;
			Double conv = new Double(convertdoub);
			int converted = conv.intValue();
			if (converted>=100)
				converted=99;
			valueList = valueList + converted + ",";
			dateList = dateList + "*|";
			
		}
		
		tableTitle = titlePrefix + "+by+Day";
		}
		else
		{
			getWeeklyString (counts, poly.getHighestCount());
			tableTitle= titlePrefix+"+by+Week";
			
		}
		
		
		if (valueList.length() >0 )
		{
			SimpleDateFormat dfo = new SimpleDateFormat("MM/dd");
			String startDateString = dfo.format(poly.getStartDate().getTime());
			String endDateString = dfo.format(poly.getEndDate().getTime());
		 returnable = "<img src=\"http://chart.apis.google.com/chart?chs=300x150&amp;chd=t:"
					+ valueList.substring(0,valueList.length()-1)
					+ "&cht=lc&chbh=5,5&chxt=x,y&chco=ff1122&chxr=1,0,"+ highestCount +"&chtt=" + tableTitle +"|"
					+ startDateString + "+to+" +endDateString 
					+ "&chxl=0:" + dateList.substring(0,dateList.length()-2)+"\" height=150 width=300/>";
		}
		else 
		{
			logger.error("The ValueList was empty, something didn't get written");
		}
		}
		
		return returnable;
	}
	private void getWeeklyString(Integer[] counts, int chartHighestValue)
	{

		List<Integer> weekPacker = new ArrayList<Integer>();
		int highestWeekValue = 0;
		int j=0;

		int tempCount = 0;
		while (j < counts.length)
		{
			
			if ((j+1)%7 == 0 || j == counts.length)//new week or end of list
			{
				logger.debug("ended a week, adding the values: " + tempCount);
				//check highest week value
				if (highestWeekValue < tempCount)
					highestWeekValue = tempCount;
				//insert value
				weekPacker.add(new Integer(tempCount));
				//reset tempcount
				tempCount = 0;
			}
			else //not a week
			{
				tempCount = tempCount + counts[j].intValue();
			}
			j++;
		}
		
		double converter = 25;
		logger.debug("highestWeekValue = " + highestWeekValue);
		chartHighestValue = highestWeekValue;
		double hundred = 100;
		double highestWeekValued = highestWeekValue;
		if (highestWeekValued >0)
			 converter = hundred/highestWeekValued;
		logger.debug("converter = " + converter);
		for (int k=0; k<weekPacker.size(); k++)
		{
			double converted = weekPacker.get(k).intValue();
			
			converted = converted*converter;
			Double doubleww = new Double(converted);
			int convertint = doubleww.intValue();
			logger.debug("converted is " + converted + ", convertint is " + convertint);
			if (converted>=100)
				converted=99;
			valueList = valueList + convertint + ",";
			dateList = dateList + "*|";
		}
		
	}

}
