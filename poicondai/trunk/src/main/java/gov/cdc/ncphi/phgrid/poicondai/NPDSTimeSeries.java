package gov.cdc.ncphi.phgrid.poicondai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * This is a collection of generic datecounts without any 
 * sort of region designation.  It's meant to be an objectification
 * of the "Type A" (IE, old style) return from NPDS. 
 * @author Peter White <Peter.White@bearingpoint.com>
 *
 */
public class NPDSTimeSeries {
	private List<DateCount> countList;
	public NPDSTimeSeries()
	{
		countList = new ArrayList<DateCount>();
	}
	public List<DateCount> getCountList()
	{
		return countList;
	}
	public void addDateCount(DateCount dc)
	{
		countList.add(dc);
	}
	public void setCountList (List<DateCount> cList)
	{
		countList = cList;
	}
	public String toString()
	{
		String returnable = null;
		Iterator<DateCount> iter = countList.iterator();
		StringBuffer sb = new StringBuffer();
		sb.append("NPDSTime Series Details - \n");
		while (iter.hasNext())
		{
			DateCount dc = iter.next();
			if (dc.getDate() != null)
				sb.append("date: " +dc.getDate().getTime().toString() + ", ");
			sb.append("count:  " + dc.getCount() + "; ");
				
		}
		returnable = sb.toString();
		return returnable;
	}
}
