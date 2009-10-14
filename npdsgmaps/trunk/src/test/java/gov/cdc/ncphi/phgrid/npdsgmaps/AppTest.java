package gov.cdc.ncphi.phgrid.npdsgmaps;

import gov.cdc.ncphi.phgrid.npdsgmaps.jsphelper.NPDSGmapJSPBean;
import gov.cdc.ncphi.phgrid.poicondai.NPDSTimeSeriesByRegion;
import gov.cdc.ncphi.phgrid.poicondai.NPDSTimeServiceFetcher;
import gov.cdc.ncphi.phgrid.polygon.gmaps.GmapPolygon;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionRelation;

import java.util.Calendar;
import java.util.Collection;
import java.util.Iterator;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	Calendar startDate= Calendar.getInstance();
    	startDate.set(2008, 7, 7);
    	Calendar endDate = Calendar.getInstance();
    	endDate.set(2008, 7, 15);
    	

    	//get state polygons
    	GoogleJSPBean gjsp = new GoogleJSPBean();
    	RegionSelectionBean rsb = new RegionSelectionBean();


    	Collection<RegionRelation> crr = rsb.getRegionListByParentAndType("state", "USA");
		Collection<GmapPolygon> col = gjsp.getPolygonCollectionForRegionRelations(crr);
		gjsp.setPolygonCollection(col);  
		assertTrue(col.size()>0);
    	//get state data
    	
		
		NPDSGmapTimeSeriesFetcher ngtsf = new NPDSGmapTimeSeriesFetcher();
		NPDSTimeSeriesByRegion tsr = ngtsf.GetNPDSTimeSeriesForRegion("state", 
				crr, "97", "", startDate, endDate, "5", "7",
				"", "TCV", "");
		assertTrue(tsr.getAll().size()>0);
		

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
    	assertTrue(somethingHadSomething);
    	
    	NPDSGmapJSPBean ngjb= new NPDSGmapJSPBean();
		ngjb.fetchAndLoadPolygonsForStates(gjsp, rsb,
			 startDate, endDate, 
			"");
		
		// get zip3 data 
		crr = rsb.getRegionListByParentAndType("zip3", "CO");
		assertTrue(crr.size() >0);
		 tsr = ngtsf.GetTimeSeriesByZip3(crr, 
				 "CO", startDate, endDate, "5", "7", 
				 "", "TCV", "");
		assertTrue(tsr.getAll().size()>0);
		
		 col = gjsp.getPolygonCollectionForRegionRelations(crr);
		gptsl.loadGmapPolygonForZip3(col, tsr, startDate, endDate);
		somethingHadSomething = false;
    	 iter = col.iterator();
    	while (iter.hasNext())
    	{
    		GmapPolygon polly = iter.next();
    		if (polly.getTimeSeries() != null && 
    				polly.getTimeSeries().size()>0)
    			somethingHadSomething = true;
    	}
    	assertTrue(somethingHadSomething);
    	
    	ngjb.fetchAndLoadPolygonsForZip3(
    			gjsp, rsb, "CO",
    			 startDate, endDate,
    			"");
		
		crr = rsb.getRegionListByParentAndType("zip5", "800");
		assertTrue(crr.size() >0);
		 tsr = ngtsf.GetTimeSeriesByZip5(crr, 
				 "800", startDate, endDate,"5", "7","", "TCV", "");
		assertTrue(tsr.getAll().size()>0);
		col= gjsp.getPolygonCollectionForRegionRelations(crr);
		gptsl.loadGmapPolygonForZip5(col, tsr, startDate, endDate);
		
		somethingHadSomething = false;
   	 iter = col.iterator();
   	while (iter.hasNext())
   	{
   		GmapPolygon polly = iter.next();
   		if (polly.getTimeSeries() != null && 
   				polly.getTimeSeries().size()>0)
   			somethingHadSomething = true;
   	}
   	assertTrue(somethingHadSomething);

   	ngjb.fetchAndLoadPolygonsForZip5(gjsp, 
   			rsb, "800", startDate, endDate,  "");
   	ngjb.fetchAndLoadPolygonsForZip5(gjsp, 
   			rsb, "800", startDate, endDate,  "391"); 
   	//test the authentication classes
  /* 	NPDSGmapAuth nga = new NPDSGmapAuth();
   	boolean test = nga.checkAuth("peter", "password");
   	assertTrue(test);
   	boolean testfail = nga.checkAuth("peter", "notPassword");
   	assertFalse(testfail); */
    } 
}
