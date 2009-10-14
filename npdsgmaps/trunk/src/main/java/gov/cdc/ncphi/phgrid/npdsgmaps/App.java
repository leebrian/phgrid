package gov.cdc.ncphi.phgrid.npdsgmaps;

import gov.cdc.ncphi.phgrid.npdsgmaps.jsphelper.NPDSGmapJSPBean;
import gov.cdc.ncphi.phgrid.poicondai.NPDSTimeSeriesByRegion;
import gov.cdc.ncphi.phgrid.polygon.gmaps.GmapPolygon;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionRelation;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger logger = Logger.getLogger(App.class);
	
    public static void main( String[] args )
    {
    	Calendar startDate = Calendar.getInstance();
    	startDate.set(2008, 6, 7);
    	Calendar endDate = Calendar.getInstance();
    	endDate.set(2008, 7, 26);
    	//get state polygons
    	GoogleJSPBean gjsp = new GoogleJSPBean();
    	RegionSelectionBean rsb = new RegionSelectionBean();


    	Collection<RegionRelation> crr = rsb.getRegionListByParentAndType("state", "USA");
		Collection<GmapPolygon> col = gjsp.getPolygonCollectionForRegionRelations(crr);
		gjsp.setPolygonCollection(col); 
    	//get state data
    	
		NPDSGmapTimeSeriesFetcher ngtsf = new NPDSGmapTimeSeriesFetcher();
		NPDSTimeSeriesByRegion tsr = ngtsf.GetNPDSTimeSeriesForRegion("state", 
				crr, "8", "", startDate, endDate, "5", "7",
				"", "TCV", "");
    	//add state data to polygons
    	GmapPolygonTimeSeriesLoader gptsl = new GmapPolygonTimeSeriesLoader();
    	gptsl.loadGmapPolygonForStates(col, tsr, startDate);
    	boolean somethingHadSomething = false;
    	Iterator<GmapPolygon> iter = col.iterator();
    	while (iter.hasNext())
    	{
    		GmapPolygon polly = iter.next();
    		if (polly.getTimeSeries() != null && 
    				polly.getTimeSeries().size()>0)
    			somethingHadSomething = true;
    	}

    	
    	NPDSGmapJSPBean ngjb= new NPDSGmapJSPBean();
		ngjb.fetchAndLoadPolygonsForStates(gjsp, rsb,
			 startDate, endDate, 
			"");
		String returnable = gjsp.getMap("Biscuity", 1);
		
		Writer output = null;
	    File file = new File("jspoutput.txt");
	    try{
	    output = new BufferedWriter(new FileWriter(file));
	    output.write(returnable);
	    output.close();
	    }catch(IOException e)
	    {
	    	logger.error(e);
	    }
	    logger.debug("Your file has been written");    
    }
}
