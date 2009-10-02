package gov.cdc.ncphi.phgrid.polygon.gmaps;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

public class StandardPinpointTypeProcessor implements PinpointTypeProcessor {
	private static final Logger logger = Logger.getLogger(StandardPinpointTypeProcessor.class);

	SortedSet<PinpointRange> pinpointRanges;
	private static final String borderColor = "#000000";
	private String normalWeight = "1";
	private String normalShade = "0.2";
	private String defaultColor = "#ffffff";
	private static final Double minfin = java.lang.Double.NEGATIVE_INFINITY;
	private static final Double maxfin = java.lang.Double.POSITIVE_INFINITY;
	
	public StandardPinpointTypeProcessor()
	{
		pinpointRanges = new TreeSet<PinpointRange>(new PinpointComparator());

	/*    var iconData = {
	    		  "wht-blank": { width: 24, height: 34 },
	    		  "grn-blank": { width: 24, height: 34 },
	    		  "ylw-blank": { width: 24, height: 34 },
	    		  "red-blank": { width: 24, height: 34 },
	    		  "flag-shadow": { width: 40, height: 30 }
	    		    		}; */
			setPinpointRange(minfin, new Double(0.0), "http://maps.google.com/mapfiles/kml/paddle/wht-blank.png");
			setPinpointRange(new Double(0.0), new Double(1000.0), "http://maps.google.com/mapfiles/kml/paddle/grn-blank.png");
			setPinpointRange(new Double(1000.0), new Double(2000.0), "http://maps.google.com/mapfiles/kml/paddle/ylw-blank.png");
			setPinpointRange(new Double(2000.0), maxfin, "http://maps.google.com/mapfiles/kml/paddle/red-blank.png");
		
		
	}
	
	public String getPinpointType(GmapPolygon polygon) {
		String returnable =null;
		returnable = getPinpointForCount(polygon.getTotalCount());
		//cBean.setFillColor(getColorForCount(polygon.getTotalCount()));
		//cBean.setBorderStrength(normalWeight);
		//cBean.setFillStrength(normalShade);
		return returnable ; 
	}
	

	private String getPinpointForCount(double count)
	{
		logger.info("finding color for" + count);
		String returnable = defaultColor;
		Double dCount = new Double(count);
		boolean foundColor = false;
		Iterator<PinpointRange> colorIter = pinpointRanges.iterator();
		logger.trace(pinpointRanges.size() + " items in pinpoint range iterator");
		while (colorIter.hasNext() && !foundColor)
		{
			PinpointRange cr = colorIter.next();
			logger.trace("comparing " + dCount + " to lowerbound " + cr.lowEnd + " to upperbound " + cr.highEnd);
				if (dCount.compareTo(cr.lowEnd) > 0 && dCount.compareTo(cr.highEnd) <= 0)
				{
					logger.trace("The pinpoint found is " + cr.pinpointType);
					returnable = cr.pinpointType;
					foundColor = true;
				}		
		}
		return returnable;
	}
	public boolean removePinpointRange(Double lowerBound, Double upperBound) {
		logger.debug("removing pinpoint range with lowerbound " + lowerBound + " and upperbound " + upperBound);
		if (lowerBound == null || upperBound == null)
		{
				return false;			
		}
		Iterator<PinpointRange> pinpointIter = pinpointRanges.iterator();
		PinpointRange removeKey = new PinpointRange(lowerBound, upperBound, null);
		while (pinpointIter.hasNext())
		{
			PinpointRange cr = pinpointIter.next();
			if (removeKey.lowEnd.compareTo(cr.lowEnd) == 0 && removeKey.highEnd.compareTo(cr.highEnd) == 0)
			{
				logger.trace("removing color range lower " + cr.lowEnd + "removing colorRange upper " + cr.highEnd);
				pinpointRanges.remove(cr);
				return true;
			}
		}
		logger.info("Returning false in color range remove");
		return false;
	}
	public boolean setPinpointRange(Double lowerBound, Double upperBound,
			String color) {		
		//check for bad conditions.		
		logger.debug("adding color range with lowerbound " + lowerBound + " upperbound " + upperBound + " and color " + color);
		if (lowerBound == null || upperBound == null || lowerBound.compareTo(upperBound) > 0)
			return false;	
		//now see if you can add it, and if so, add it
		PinpointRange addable = new PinpointRange(lowerBound, upperBound, color);
		if (pinpointRanges.size() ==0)
		{
			pinpointRanges.add(addable);
			return true;
		}		
		Iterator<PinpointRange> crIter = pinpointRanges.iterator();
		PinpointRange recent = null;
		while (crIter.hasNext())
		{
			PinpointRange current = crIter.next();
			//check and see if it's below current item
				if (addable.highEnd.compareTo(current.lowEnd) <= 0 )
				{
					if(recent == null || recent.highEnd.compareTo(addable.lowEnd) <=0)
					{
						pinpointRanges.add(addable);
						return true;
						
					}
				}
			//check and see if it's above current item if this is the last item
				if (!crIter.hasNext())
				{
					if (addable.lowEnd.compareTo(current.highEnd) >= 0)
					{
						pinpointRanges.add(addable);
						return true;
					}
				}
			recent = current;
		}
		
		return false;
	}
	public boolean removeAllPinpointRanges() {
		
		pinpointRanges.clear();
			return true;

	}
	
	private class PinpointRange{
		PinpointRange(Double lowerBound, Double upperBound, String pinpointType)
		{
			this.lowEnd = lowerBound;
			this.highEnd = upperBound;
			this.pinpointType = pinpointType;
		}
		public Double lowEnd;
		public Double highEnd;
		public String pinpointType;
	}
	private class PinpointComparator implements Comparator {

		public int compare(Object arg0, Object arg1) {
			int returnable =0;
			PinpointRange c0 = (PinpointRange) arg0;
			PinpointRange c1 = (PinpointRange) arg1;
			if (c0.highEnd.equals(c1.highEnd) && c0.lowEnd.equals(c1.lowEnd))
				return 0;
			if (c0.highEnd.compareTo(c1.lowEnd) <= 0 )
				return -1;
			if (c0.lowEnd.compareTo(c1.highEnd) >=0 )
				return 1;
			return returnable;
		}
		
	}
	

}
