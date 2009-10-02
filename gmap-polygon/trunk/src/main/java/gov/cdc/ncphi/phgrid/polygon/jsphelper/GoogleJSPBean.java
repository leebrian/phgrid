package gov.cdc.ncphi.phgrid.polygon.jsphelper;

import gov.cdc.ncphi.phgrid.polygon.gmaps.C2AverageFlotChartProcesser;
import gov.cdc.ncphi.phgrid.polygon.gmaps.GmapPolygon;
import gov.cdc.ncphi.phgrid.polygon.gmaps.GoogleMapEncodingStringGenerator;
import gov.cdc.ncphi.phgrid.polygon.gmaps.GoogleMapPinpointStringGenerator;
import gov.cdc.ncphi.phgrid.polygon.gmaps.PinpointTypeProcessor;
import gov.cdc.ncphi.phgrid.polygon.gmaps.PolygonColorProcessor;
import gov.cdc.ncphi.phgrid.polygon.gmaps.PolygonPopupHandler;
import gov.cdc.ncphi.phgrid.polygon.gmaps.RegionPolygonFetcher;
import gov.cdc.ncphi.phgrid.polygon.gmaps.StandardPinpointTypeProcessor;
import gov.cdc.ncphi.phgrid.polygon.gmaps.StandardPolygonColorProcessor;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionRelation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;
/**
 * This is a session bean that will help do most of the initialization and storage
 * for the given calling JSP pages.
 * @author Peter White
 *
 */
public class GoogleJSPBean implements Serializable {

	private static final long serialVersionUID = 1L;
	private PolygonPopupHandler popHandle;
	private PolygonColorProcessor colorHandle;
	private PinpointTypeProcessor pinpointHandle;
	private static Collection<Collection<GmapPolygon>> polygonCollection;
	private static GoogleMapEncodingStringGenerator gmsg;
	private static GoogleMapPinpointStringGenerator gpsg;
	private GmapPolygon focus;
	private static final Double minfin = java.lang.Double.NEGATIVE_INFINITY;
	private static final Double maxfin = java.lang.Double.POSITIVE_INFINITY;
	private static final Logger logger = Logger.getLogger(GoogleJSPBean.class);
	private String baseS, greenYellowS, yellowRedS;
	private String userZoom;
	public static final int POLYGON = 0;
	public static final int PINPOINT = 1;
	/**
	 * Constructor 
	 */
	public GoogleJSPBean(){
		popHandle = new C2AverageFlotChartProcesser();
		colorHandle = new StandardPolygonColorProcessor();
		pinpointHandle = new StandardPinpointTypeProcessor();
		gmsg = GoogleMapEncodingStringGenerator.getStringGenerator();
		gpsg = GoogleMapPinpointStringGenerator.getStringGenerator();
		userZoom = null;
	}
	/**
	 *
	 * @return The standard popup handler for the given googlejspbean. 
	 * Default is "Standard PolygonPopupHandler"
	 */
	public PolygonPopupHandler getPopHandle() {
		return popHandle;
	}
	/**
	 * Sets an updated popup handler
	 * @param popHandle
	 */
	public void setPopHandle(PolygonPopupHandler popHandle) {
		this.popHandle = popHandle;
	}
	/**
	 * 
	 * @return returns the standard color handler for the given googlejspbean
	 */
	public PolygonColorProcessor getColorHandle() {
		return colorHandle;
	}
	/**
	 * sets a new or modified color handler
	 * @param colorHandle
	 */
	public void setColorHandle(PolygonColorProcessor colorHandle) {
		this.colorHandle = colorHandle;
	}
	/**
	 * 
	 * @return the main internal collection of polygons.
	 */
	public Collection<Collection<GmapPolygon>> getPolygonCollection() {
		return polygonCollection;
	}
	/**
	 * sets the given polygon collection
	 * @param polygonCollection
	 */
	public void setPolygonCollection(Collection<Collection<GmapPolygon>> polygonCollection) {
		this.polygonCollection = polygonCollection;
	}

	/**
	 * This function fetches a collection of polygons for a given collection of Region Relations 
	 * (all zip3s in a state, all zip5s in a zip3, etc)
	 * @param regrel
	 * @return
	 */
	public Collection<GmapPolygon> getPolygonCollectionForRegionRelations(Collection<RegionRelation> regrel)
	{
		Collection <GmapPolygon> returnable = new ArrayList<GmapPolygon>();
		if (regrel != null)
		{
			Iterator<RegionRelation> iter = regrel.iterator();
			RegionRelation focus = null;
			RegionPolygonFetcher rpf = RegionPolygonFetcher.getFetcher();
			
			while(iter.hasNext())
			{
				focus = iter.next();
				Collection<GmapPolygon> gpolys = rpf.getRegionPolygon(focus.getRegionName(), focus.getRegionType());
				returnable.addAll(gpolys);
			}
		}
		return returnable;
	}
	/**
	 * returns the javascript for a set of polygons.
	 * @return
	 */
	public String getPolygons(String passInKey)
	{

		String returnable = "";
		if (polygonCollection != null)
		{
		Iterator<Collection<GmapPolygon>> colIter = polygonCollection.iterator();
		int j=0;
		while (colIter.hasNext())
		{Iterator <GmapPolygon> iter = colIter.next().iterator();
		int k = 0;
		while (iter.hasNext())
		{
			GmapPolygon poly = iter.next();
			returnable += gmsg.getPolygonString(poly, k+"", colorHandle, popHandle, passInKey, j) + "\n";
			k++;
		}
		j++;
		}
		}
		return returnable;
	}
	
	/**
	 * returns the javascript for a set of polygons.
	 * @return
	 */
	public String getPinpoints(String passInKey)
	{

		String returnable = "";

		 // Create a base icon for all of our markers that specifies the
        // shadow, icon dimensions, etc.
        returnable += "var baseIcon = new GIcon(G_DEFAULT_ICON); \n";
        returnable += "baseIcon.shadow = \"http://www.google.com/mapfiles/shadow50.png\"; \n";
        returnable += "baseIcon.iconSize = new GSize(20, 34); \n";
        returnable += "baseIcon.shadowSize = new GSize(37, 34); \n";
        returnable += "baseIcon.iconAnchor = new GPoint(9, 34); \n";
        returnable += "baseIcon.infoWindowAnchor = new GPoint(9, 2); \n\n";

		
		if (polygonCollection != null)
		{
			Set<String> nameSet = new TreeSet<String>();
			if (polygonCollection != null)
			{
			Iterator<Collection<GmapPolygon>> colIter = polygonCollection.iterator();
			int j=0;
			while (colIter.hasNext())
			{Iterator <GmapPolygon> iter = colIter.next().iterator();
		int k = 0;
		while (iter.hasNext())
		{
			GmapPolygon poly = iter.next();
			//only make one pinpoint per region/type
			if (!nameSet.contains(poly.getRegionName() + poly.getRegionName()))
			{
			returnable += gpsg.getPinpointString(poly, k+"", pinpointHandle, popHandle, passInKey, j) + "\n";
			nameSet.add(poly.getRegionName() + poly.getRegionName());
			k++;
			}
			
		}
		j++;
		}
			}
		}
		return returnable;
	}
	/**
	 * Returns the entire map text for the given focus and the stored set of polygons.
	 * @return
	 */
	public String getMap(String passInKey, int displayType)
	{
        String cenLat = "38.451299";
        String cenLong = "-106.021040";
        String cenZoom = "3";
        
        if (focus != null && focus.getCentroid() != null)
        {
        	cenLat = focus.getCentroid().getLatitude();
        	cenLong = focus.getCentroid().getLongitude();
        	cenZoom = getZoom(focus);
        }
		String returnable = "var map;\n" +
				"function initialize() {\n" +
				"setTimeout(\"runMap()\",1000);\n" +
				"}\n" +
				"function runMap() {\n" +
    	      "if (GBrowserIsCompatible()) { \n" +
    	       "map = new GMap2(document.getElementById(\"map_canvas\"));\n" +
    	        "map.setCenter(new GLatLng(" + cenLat + ", "+ cenLong +"), " + cenZoom +");\n" +    
    	        "map.addControl(new GLargeMapControl()); \n" +
    	       // "map.enableScrollWheelZoom(); \n" +
    	        " var overlayControl = new GOverviewMapControl(); \n" +
    	        " map.addControl(overlayControl) \n";

		
		switch (displayType){
		case(POLYGON):
			returnable += getPinpoints(passInKey);break;
		case(PINPOINT):
			returnable += getPolygons(passInKey);break;
		default:
			returnable += getPinpoints(passInKey);break;
		}
		returnable +="GEvent.addListener(map , \"load\", function() { \n";
	returnable += "var the_div = getElementById(\"countMS\"); \n";
	returnable += "var new_date = new Date(); \n";
	returnable += "var countTime = parseInt(clientMS) + (newDate - startDate) \n";
	returnable += " the_div.innerHTML = \"Client side processing took \" + countTime + \" ms\"; \n ";
	returnable += "}); \n";
		returnable += "\n}\n}";
		return returnable;
	}
	
	/**
	 * A way for the jsp map to set the focus of the map being drawn.  It will retreive a 
	 * polygon for the given type and name and use that centroid when loading the map text.
	 * @param regionType
	 * @param regionName
	 * @return
	 */
	
	public boolean setFocus(String regionType, String regionName)
	{
		boolean returnable = false;
		RegionPolygonFetcher rpf = RegionPolygonFetcher.getFetcher();
		List <GmapPolygon> fetch = rpf.getRegionPolygon(regionName, regionType);
		if(fetch.size() > 0)
		{
			focus = fetch.get(0);
			returnable = true;
		}

		return returnable;
	}
	
	public boolean adjustLegend(String baseString, String greenYellowString, String yellowRedString)
	{
		Double base, greenYellow, yellowRed;
		boolean returnable = false;
		if (baseString != null &&  greenYellowString != null && yellowRedString != null)
		{
		 try{
		       	  base = Double.valueOf(baseString);		     
		     	  greenYellow = Double.valueOf(greenYellowString);
		       	  yellowRed = Double.valueOf(yellowRedString);
		       	if(base < greenYellow && greenYellow < yellowRed)
				{
					colorHandle.removeAllColorRanges();
					colorHandle.setFillColorRange(minfin, base, "#ffffff");
					colorHandle.setFillColorRange( base, greenYellow, "#66ff00");
					colorHandle.setFillColorRange(greenYellow, yellowRed, "#ffff00");
					colorHandle.setFillColorRange(yellowRed, maxfin, "#f33f00");
					 returnable = true;
					 
					 pinpointHandle.removeAllPinpointRanges();
					 pinpointHandle.setPinpointRange(minfin, base, "http://maps.google.com/mapfiles/kml/paddle/wht-blank.png");
					 pinpointHandle.setPinpointRange(base, greenYellow, "http://maps.google.com/mapfiles/kml/paddle/grn-blank.png");
					 pinpointHandle.setPinpointRange(greenYellow, yellowRed, "http://maps.google.com/mapfiles/kml/paddle/ylw-blank.png");
					 pinpointHandle.setPinpointRange(yellowRed, maxfin, "http://maps.google.com/mapfiles/kml/paddle/red-blank.png");

				}
		       }catch (NumberFormatException e)
			      {
			    	  logger.error("Number format exception", e);
			      }

		}	
		return returnable;
		
	}
	
	/**
	 * Returns the zoom level for a particular gpolygon.
	 * @param zoomer
	 * @return
	 */
	private String getZoom (GmapPolygon zoomer)
	{
		String returnable  = "4";
		if (userZoom == null)
		{
			if (zoomer != null)
			{
				if (zoomer.getRegionType().equals("state"))
					returnable = "6";
				else if (zoomer.getRegionType().equals("zip3"))
					returnable = "9";
				else if (zoomer.getRegionType().equals("zip5"))
					returnable = "10";
			}
		}
		else
		{
			returnable = userZoom;
		}
		return returnable;
	}
	
	/**
	 * A way for UIs to set the fixed zoom of a given map
	 * @param userZoom
	 * @return
	 */
	public boolean setZoom(String setZoom)
	{
		boolean returnable = false;
		if (setZoom == null)
		{
			userZoom = null;
			returnable = true;
		}
		else if (setZoom.length() <=2)
		{
			try{
				//see if it is a parseable int
				Integer.parseInt(setZoom);
				userZoom = setZoom;
				returnable = true;
			}catch (NumberFormatException e)
			{
				logger.error("Could not format passed-in zoom", e);
			}			
		}
		//otherwise, value does not get set
		
		return returnable;
	}
	
}
