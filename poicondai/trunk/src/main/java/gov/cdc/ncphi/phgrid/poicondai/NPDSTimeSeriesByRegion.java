package gov.cdc.ncphi.phgrid.poicondai;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
/**
 * This is a collection of date counts indexed by region.  
 * It will contain results from "Type B" and "Type C" returns
 * from the NPDS Webservice.
 * @author Peter White <peter.white@bearingpoint.com>
 */

public class NPDSTimeSeriesByRegion {
	Map<String, NPDSTimeSeries> timeSeriesByRegion;
	public NPDSTimeSeriesByRegion()
	{
		timeSeriesByRegion = new HashMap<String, NPDSTimeSeries>();
	}
	public NPDSTimeSeries getCountListForRegion(String region)
	{
		return timeSeriesByRegion.get(region);	
	}
	public Map<String, NPDSTimeSeries> getAll()
	{
		return timeSeriesByRegion;
	}
	public void AddDateCount(String region, DateCount dc)
	{
		NPDSTimeSeries ser = timeSeriesByRegion.get(region);
		if (ser != null)
		{
			ser.addDateCount(dc);
		}
		else 
		{
			ser = new NPDSTimeSeries();
			ser.addDateCount(dc);
			timeSeriesByRegion.put(region, ser);
		}
	}
	
	public String toString()
	{
		String returnable = null;
		Set<String> keyset = timeSeriesByRegion.keySet();
		Iterator<String> keyIter = keyset.iterator();
		StringBuffer sb = new StringBuffer();
		sb.append("NPDSTime Series by Region Details - \n");
		while (keyIter.hasNext())
		{
			String key = keyIter.next();
			sb.append("\nRegion: " + key + "\n");
		NPDSTimeSeries ts = timeSeriesByRegion.get(key);
		sb.append(ts.toString());
		}
		returnable = sb.toString();
		
		return returnable;
	}
}
