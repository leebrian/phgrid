package gov.cdc.ncphi.phgrid.polygon.gmaps;

import gov.cdc.ncphi.phgrid.polygon.jsphelper.C2FlotArrayJSPBean;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionData;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionListFetcher;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionRelation;

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

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
	
    extends TestCase
{
	private static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy/zzz");
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
    	//CountyPolygonFetcher cpf = new CountyPolygonFetcher();
    	//GmapPolygon cp = cpf.getCountyPolygon("Denver", "Colorado");
    	
        //assertTrue( cp.getRegionName() != null );
        //assertTrue(cp.getRegionType().equals("county"));
        
        RegionPolygonFetcher zpf = RegionPolygonFetcher.getFetcher();
        Collection<GmapPolygon> zp = zpf.getRegionPolygon("81637", "zip5");
        assertFalse(zp.isEmpty());
        Iterator<GmapPolygon> iter = zp.iterator();
        GmapPolygon polly = null;
        while (iter.hasNext())
        {
        	polly = iter.next();
        assertTrue( polly.getRegionName().equals("81637"));
        assertTrue(polly.getRegionType().equals("zip5"));
        }

        RegionPolygonFetcher zpf3 = RegionPolygonFetcher.getFetcher();
        Collection<GmapPolygon> zp3 = zpf3.getRegionPolygon("013", "zip3");
        assertFalse(zp3.isEmpty());
        iter = zp3.iterator();
        
        while (iter.hasNext())
        {
        	polly= iter.next();
        	assertTrue( polly.getRegionName().equals("013"));
        	assertTrue(polly.getRegionType().equals("zip3"));
        }
        
        RegionPolygonFetcher zpfs = RegionPolygonFetcher.getFetcher();
        Collection<GmapPolygon> zp3s = zpfs.getRegionPolygon("WY", "state");
        assertFalse(zp3s.isEmpty());
        iter = zp3s.iterator();
        
        while (iter.hasNext())
        {
        	polly= iter.next();
        	assertTrue( polly.getRegionName().equals("WY"));
        	assertTrue(polly.getRegionType().equals("state"));
        }
        
        
        GoogleMapStringGenerator gmsg = GoogleMapStringGenerator.getStringGenerator();
        PolygonColorProcessor cb = new StandardPolygonColorProcessor();
        PolygonPopupHandler pph = new StandardPolygonPopupHandler();
        String polygonText = gmsg.getPolygonString(polly, "1", cb, pph, "Biscuity", 0);
        assertTrue(polygonText != null && !polygonText.equals(""));
        
        
        //check color range processor
        polly.setTotalCount(0);
        ColorBean retcb = cb.getColor(polly);
        assertTrue(retcb.getFillColor().equals("#ffffff"));
        polly.setTotalCount(4);
        retcb = cb.getColor(polly);
        assertTrue(retcb.getFillColor().equals("#66ff00"));
        polly.setTotalCount(450503);
        retcb = cb.getColor(polly);
        assertTrue(retcb.getFillColor().equals("#f33f00"));
        //check removing
        assertTrue(cb.removeFillColorRange(java.lang.Double.NEGATIVE_INFINITY, new Double(0)));
        assertFalse(cb.removeFillColorRange(new Double(1), new Double(11))); 
        //check adding
        assertFalse(cb.setFillColorRange(new Double(3), new Double (11), "#66ff99"));
        assertTrue(cb.setFillColorRange(new Double(-50), new Double (-20), "#66ff99"));
        assertFalse(cb.setFillColorRange(new Double(-40), new Double (-15), "#66ff88"));
        assertTrue(cb.setFillColorRange(new Double (-10), new Double(-3), "#66ff88"));
        polly.setTotalCount(-30);
        retcb = cb.getColor(polly);
        assertTrue(retcb.getFillColor().equals("#66ff99"));
        polly.setTotalCount(-17);
        retcb = cb.getColor(polly);
        assertTrue(retcb.getFillColor().equals("#ffffff"));
        polly.setTotalCount(-6);
        retcb = cb.getColor(polly);
        assertTrue(retcb.getFillColor().equals("#66ff88"));
        
        //test region list
        RegionListFetcher rlf =  RegionListFetcher.getRegionListFetcher();
        List<RegionRelation> rr = rlf.getListByParent("state", "USA");
        assertTrue(rr.size() >0);
        
        //test regionselectionbean
        RegionSelectionBean rsb = new RegionSelectionBean();
        Map<String, String> stateMap = rsb.getStateList();
        assertTrue(stateMap.size()>0);
        Collection <RegionRelation> rsbrr = rsb.getRegionListByParentAndType("zip3", "ME");
        assertTrue(rsbrr.size() >0);
        
        //retest gjspbean fetching things by region relation
        GoogleJSPBean gjsp = new GoogleJSPBean();
        Collection<GmapPolygon> gpolyrr = gjsp.getPolygonCollectionForRegionRelations(rsbrr);
        assertTrue(gpolyrr.size() > 0 );
      
        rsbrr = rsb.getRegionListByParentAndType("state", "USA");
        gpolyrr = gjsp.getPolygonCollectionForRegionRelations(rsbrr);
        assertTrue(gpolyrr.size() >0);
        
        String parent  = rsb.getRegionParent("300");
        assertTrue(parent!=null && !parent.equals(""));
        
        //test the C2FlotArrayJSPBean
        
        C2FlotArrayJSPBean c2fb = new C2FlotArrayJSPBean();
        Map<String, List<TimeSeries>> polymap = new HashMap<String, List<TimeSeries>>();
        polymap.put("testServer", getArrayList());
        Date date = new Date();
		try {
			date = df.parse("12/13/2008/gmt");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_YEAR, C2FlotArrayJSPBean.C2SPAN);
		polly.setStartDate(cal);
		cal.add(Calendar.DAY_OF_YEAR, 8);
		polly.setEndDate(cal);
        polly.setTimeSeries(polymap);
        String result = c2fb.getArrayString(polly);
        assertTrue (result != null);
    }
    
    private List<TimeSeries> getArrayList()
    {
    	List<TimeSeries> returnable = new ArrayList<TimeSeries>();
    	
    	Date date = null;
    	Calendar cal = Calendar.getInstance();
    	try {
    		date = df.parse("12/13/2008/gmt");
    		cal.setTime(date);
    		for (int k = 0; k<30; k++)
    		{
    			cal.add(Calendar.DAY_OF_YEAR, 1);
    			returnable.add(getTimeSeries(cal, 2 + (4*(k%2))));
    		}
    		cal.add(Calendar.DAY_OF_YEAR, 1);
        	returnable.add(getTimeSeries(cal, 4));
        	cal.add(Calendar.DAY_OF_YEAR, 1);
        	returnable.add(getTimeSeries(cal, 4));
        	cal.add(Calendar.DAY_OF_YEAR, 1);
        	returnable.add(getTimeSeries(cal, 4));
        	cal.add(Calendar.DAY_OF_YEAR, 1);
        	returnable.add(getTimeSeries(cal, 4));
        	cal.add(Calendar.DAY_OF_YEAR, 1);
        	returnable.add(getTimeSeries(cal, 6));
        	cal.add(Calendar.DAY_OF_YEAR, 1);
        	returnable.add(getTimeSeries(cal, 7));
        	cal.add(Calendar.DAY_OF_YEAR, 1);
        	returnable.add(getTimeSeries(cal, 50));
        	cal.add(Calendar.DAY_OF_YEAR, 1);
        	returnable.add(getTimeSeries(cal, 0));

    	
    	} catch (ParseException e) {
    		e.printStackTrace();
    	}
    	
    	return returnable;
    	
    }
    
    public void testRegionData()
    {
    	RegionListFetcher rlf = RegionListFetcher.getRegionListFetcher();
    	RegionData rd = rlf.getRegionDataForRegion("AK", "state");
    	assertTrue(rd.getRegionName() != null);
    	assertTrue(rd.getRegionType() != null);
    	assertTrue(rd.getRegionParent() != null);
    	assertTrue(rd.getCentroid() != null);
    	
    }
    
    public void testRegionMultiData()
    {
    	RegionListFetcher rlf = RegionListFetcher.getRegionListFetcher();
    	List<RegionData> rdl = rlf.getRegionDataForRegions("AK", "zip3");
    	assertFalse(rdl.isEmpty());
    	RegionData rd = rdl.get(0);
    	assertTrue(rd.getRegionName() != null);
    	assertTrue(rd.getRegionType() != null);
    	assertTrue(rd.getRegionParent() != null);
    	assertTrue(rd.getCentroid() != null);
    }
    private TimeSeries getTimeSeries(Calendar cal , int count)
    {
    	TimeSeries returnable = null;
    	Calendar putCal = Calendar.getInstance();
		putCal.setTime(cal.getTime());
		returnable = new TimeSeries();
		returnable.setCount(count);
		returnable.setCountDate(putCal);
	return returnable;
       
    }
}
