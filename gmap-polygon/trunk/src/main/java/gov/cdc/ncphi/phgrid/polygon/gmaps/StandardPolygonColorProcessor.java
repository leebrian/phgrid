package gov.cdc.ncphi.phgrid.polygon.gmaps;

import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

import org.apache.log4j.Logger;

public class StandardPolygonColorProcessor implements PolygonColorProcessor {
	private static final Logger logger = Logger.getLogger(StandardPolygonColorProcessor.class);

	SortedSet<ColorRange> colorRanges;
	private static final String borderColor = "#000000";
	private String normalWeight = "1";
	private String normalShade = "0.2";
	private String defaultColor = "#ffffff";
	private static final Double minfin = java.lang.Double.NEGATIVE_INFINITY;
	private static final Double maxfin = java.lang.Double.POSITIVE_INFINITY;
	
	public StandardPolygonColorProcessor()
	{
		colorRanges = new TreeSet<ColorRange>(new ColorRangeComparator());

			setFillColorRange(minfin, new Double(0.0), "#ffffff");
			setFillColorRange(new Double(0.0), new Double(1000.0), "#66ff00");
			setFillColorRange(new Double(1000.0), new Double(2000.0), "#ffff00");
			setFillColorRange(new Double(2000.0), maxfin, "#f33f00");
		
		
	}
	
	public ColorBean getColor(GmapPolygon polygon) {
		ColorBean cBean = new ColorBean();
		cBean.setBorderColor(borderColor);
		cBean.setFillColor(getColorForCount(polygon.getTotalCount()));
		cBean.setBorderStrength(normalWeight);
		cBean.setFillStrength(normalShade);
		return cBean; 
	}
	

	private String getColorForCount(double count)
	{
		logger.info("finding color for" + count);
		String returnable = defaultColor;
		Double dCount = new Double(count);
		boolean foundColor = false;
		Iterator<ColorRange> colorIter = colorRanges.iterator();
		logger.info(colorRanges.size() + " items in color range iterator");
		while (colorIter.hasNext() && !foundColor)
		{
			ColorRange cr = colorIter.next();
			logger.debug("comparing " + dCount + " to lowerbound " + cr.lowEnd + " to upperbound " + cr.highEnd);
				if (dCount.compareTo(cr.lowEnd) > 0 && dCount.compareTo(cr.highEnd) <= 0)
				{
					logger.debug("The color found is " + cr.color);
					returnable = cr.color;
					foundColor = true;
				}		
		}
		return returnable;
	}
	public boolean removeFillColorRange(Double lowerBound, Double upperBound) {
		logger.debug("removing color range with lowerbound " + lowerBound + " and upperbound " + upperBound);
		if (lowerBound == null || upperBound == null)
		{
				return false;			
		}
		Iterator<ColorRange> colorIter = colorRanges.iterator();
		ColorRange removeKey = new ColorRange(lowerBound, upperBound, null);
		while (colorIter.hasNext())
		{
			ColorRange cr = colorIter.next();
			if (removeKey.lowEnd.compareTo(cr.lowEnd) == 0 && removeKey.highEnd.compareTo(cr.highEnd) == 0)
			{
				logger.info("removing color range lower " + cr.lowEnd + "removing colorRange upper " + cr.highEnd);
				colorRanges.remove(cr);
				return true;
			}
		}
		logger.info("Returning false in color range remove");
		return false;
	}
	public boolean setFillColorRange(Double lowerBound, Double upperBound,
			String color) {		
		//check for bad conditions.		
		logger.debug("adding color range with lowerbound " + lowerBound + " upperbound " + upperBound + " and color " + color);
		if (lowerBound == null || upperBound == null || lowerBound.compareTo(upperBound) > 0)
			return false;	
		//now see if you can add it, and if so, add it
		ColorRange addable = new ColorRange(lowerBound, upperBound, color);
		if (colorRanges.size() ==0)
		{
			colorRanges.add(addable);
			return true;
		}		
		Iterator<ColorRange> crIter = colorRanges.iterator();
		ColorRange recent = null;
		while (crIter.hasNext())
		{
			ColorRange current = crIter.next();
			//check and see if it's below current item
				if (addable.highEnd.compareTo(current.lowEnd) <= 0 )
				{
					if(recent == null || recent.highEnd.compareTo(addable.lowEnd) <=0)
					{
						colorRanges.add(addable);
						return true;
						
					}
				}
			//check and see if it's above current item if this is the last item
				if (!crIter.hasNext())
				{
					if (addable.lowEnd.compareTo(current.highEnd) >= 0)
					{
						colorRanges.add(addable);
						return true;
					}
				}
			recent = current;
		}
		
		return false;
	}
	public boolean removeAllColorRanges() {
		
		colorRanges.clear();
			return true;

	}
	
	private class ColorRange{
		ColorRange(Double lowerBound, Double upperBound, String color)
		{
			this.lowEnd = lowerBound;
			this.highEnd = upperBound;
			this.color = color;
		}
		public Double lowEnd;
		public Double highEnd;
		public String color;
	}
	private class ColorRangeComparator implements Comparator {

		public int compare(Object arg0, Object arg1) {
			int returnable =0;
			ColorRange c0 = (ColorRange) arg0;
			ColorRange c1 = (ColorRange) arg1;
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
