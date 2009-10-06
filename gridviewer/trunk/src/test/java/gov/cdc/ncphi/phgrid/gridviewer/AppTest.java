package gov.cdc.ncphi.phgrid.gridviewer;



import gov.cdc.ncphi.phgrid.polygon.gmaps.GmapPolygon;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionRelation;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequest;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse;
import gov.cdc.ncphi.phgrid.gridviewer.jsphelper.C2FlotArray;
import gov.cdc.ncphi.phgrid.gridviewer.jsphelper.GridViewerJSPHelper;
import gov.cdc.ncphi.phgrid.gridviewer.jsphelper.PHMapper;
import gov.cdc.ncphi.phgrid.gridviewer.jsphelper.PHMapResponse;
import gov.cdc.ncphi.phgrid.gridviewer.jsphelper.RegionalObservations;
import gov.cdc.ncphi.phgrid.gridviewer.jsphelper.ServerDataSources;
import gov.cdc.ncphi.phgrid.gridviewer.jsphelper.ServerSelectorJSPHelper;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

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
    	/*
    	 * Test the metadata pulls for the given servers
    	 */
    	GIPSEServerFetcher fetch = GIPSEServerFetcher.getServerFetcher();
    	Map <String, ServerMetadata> metList = fetch.getServers();
    	assertTrue(metList.size() > 0);    	
    	
    	/*
    	 * Fetch data from the first server using some of the given metadata
    	 * 
    	 */
    	
    	ServerMetadata met = metList.values().iterator().next();
    	GIPSEDataFetcher adf = GIPSEDataFetcher.getFetcher();
		Calendar startDate = met.getMrt().getTimePeriodSupported().getStart();
		Calendar endDate = met.getMrt().getTimePeriodSupported().getEnd();
    	RegionSelectionBean rsb = new RegionSelectionBean();
    	//load all states
    	Collection<RegionRelation> crr = rsb.getRegionListByParentAndType("state", "USA");
    	Collection<Indicator> indicators = new ArrayList<Indicator>();
    	Indicator indy = new Indicator();
    	indy.setClassifier(met.getMrt().getIndicatorsSupported().getIndicator(0).getClassifier());
    	indy.setName(met.getMrt().getIndicatorsSupported().getIndicator(0).getName());
    	indicators.add(indy);
    	Collection <ServerMetadata >serverCol = new ArrayList<ServerMetadata>();
    	serverCol.add(met);
    	GoogleJSPBean gjsp = new GoogleJSPBean();
		Collection<GmapPolygon> col = gjsp.getPolygonCollectionForRegionRelations(crr);
		Collection <GmapPolygon> respCol = adf.fetchDataForRegions(crr,
    			startDate,
    			endDate, 
    			indicators , 
    			BigInteger.valueOf(1), 
    			BigInteger.valueOf(10), 
    			BigInteger.ZERO, 
    			"supressed", 
    			serverCol, col);
		
		assertTrue(respCol.size() >0);
		
		/*
		 * Load resulting data into polygons/
		 */
		
		//GoogleJSPBean gjsp = new GoogleJSPBean();
		//Collection<GmapPolygon> col = gjsp.getPolygonCollectionForRegionRelations(crr);
		Collection<Collection<GmapPolygon>> colcol = gjsp.getPolygonCollection();
		if (colcol == null)
		{
			colcol = new ArrayList<Collection<GmapPolygon>>();
		}
		colcol.add(col);
		gjsp.setPolygonCollection(colcol);  
		assertTrue(col.size()>0); 
		

		//adf.LoadPolygons(respCol.iterator().next(), col, startDate, endDate);
		
		ServerSelectorJSPHelper ssjh = new ServerSelectorJSPHelper();
		String containsArray = ssjh.getContainsArray();
		assertTrue(containsArray.length() >0);
		
		/*
		 * Now use GVJH to query GIPSE and load polygons
		 */
		
		GridViewerJSPHelper gvjh = new GridViewerJSPHelper();
		Set<String> serverStr = new TreeSet<String>();
		Iterator<ServerMetadata> servIter = serverCol.iterator();
		List<ServerDataSources> preServerArr = new ArrayList<ServerDataSources>();
		while (servIter.hasNext())
		{
			ServerDataSources serv = new ServerDataSources();
			serv.setServerName(servIter.next().getServerName());
			preServerArr.add(serv);
			
		}
		
		gvjh.getNPDSPolygons(gjsp, rsb, startDate, endDate, "BioSense", "Fever", serverStr, "state", "USA", false);

		PHMapper phMapper = new PHMapper();
		phMapper.init();
		RegionalObservations ro = phMapper.getRegionalObservations("AK",
				"state", 
				startDate.getTime(), endDate.getTime(), 
				indy.getName(), indy.getClassifier(), preServerArr, null, null);
		assertTrue(ro != null);
		assertTrue(ro.getObservations() != null);
		
		Iterator<ServerMetadata> servIter2 = serverCol.iterator();
		List<ServerDataSources> preServerArr2 = new ArrayList<ServerDataSources>();
		while (servIter2.hasNext())
		{
			ServerDataSources serv = new ServerDataSources();
			serv.setServerName(servIter2.next().getServerName());
			preServerArr2.add(serv);
			
		}
		
		PHMapResponse phmapResponse = phMapper.getMultipleRegionalObservations("USA", "state",
				startDate.getTime(), endDate.getTime(), indy.getName(), indy.getClassifier(), preServerArr2, null, null);
		List<RegionalObservations> rol =  phmapResponse.getRegionalObservations();
		assertTrue (rol.size()>0);
		assertTrue (rol.get(0) != null);
		assertTrue (rol.get(0).getObservations() != null);
		
		C2FlotArray flotray = phMapper.getC2FlotArray("AK",
				"state", 
				startDate.getTime(), endDate.getTime(), 
				indy.getName(), indy.getClassifier(), preServerArr, null, null);
		
		assertTrue (flotray.getAverageArray()!= null && flotray.getAverageArray().size() >0);
    }
}
