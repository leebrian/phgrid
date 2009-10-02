package gov.cdc.ncphi.phgrid.polygon.gmaps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class GoogleMapPinpointStringGenerator {
	
	private static final Logger logger = Logger.getLogger(GoogleMapPinpointStringGenerator.class);
	private static GoogleMapPinpointStringGenerator gmsg;
	private static Map<Integer, String> stringMap;
	private GoogleMapPinpointStringGenerator()
	{
		stringMap = new HashMap<Integer,String>();
	}
	public String getPinpointString(GmapPolygon polygon, String position, PinpointTypeProcessor pcproc, PolygonPopupHandler polyproc, String passInKey, int collectionIndex)
	{
		String returnable = "";
		
     		    		
		returnable += "pinPoint" + getPosition(position, collectionIndex) + " = new GIcon(baseIcon); \n";
		returnable += "pinPoint"+getPosition(position, collectionIndex)+".image = \"" + pcproc.getPinpointType(polygon) + "\"; \n";
		returnable += "markerOptions = { icon:pinPoint"+getPosition(position, collectionIndex)+" }; \n";
		returnable += "var marker" + getPosition(position, collectionIndex) +"= new GMarker(new GLatLng(" + polygon.getCentroid().getLatitude()+ " , " + polygon.getCentroid().getLongitude() +" ), markerOptions); \n ";
		returnable += "GEvent.addListener(marker"+getPosition(position, collectionIndex) + " , \"click\", function() { \n " ;
		returnable += "   marker"+getPosition(position, collectionIndex)+".openInfoWindowHtml("+polyproc.getPopupString(position, polygon, passInKey, collectionIndex)+"); \n";
		returnable += "}); \n";
		returnable += "map.addOverlay(marker"+getPosition(position, collectionIndex)+"); \n \n";
      		
		
	   logger.trace("polygon text: " + returnable);
		return returnable;
		
	}
	
	public static GoogleMapPinpointStringGenerator getStringGenerator()
	{
		if (gmsg == null)
		{
			gmsg = new GoogleMapPinpointStringGenerator();
		}
		return gmsg;
	}

	public String getPosition(String position, int index )
	{
		String returnable = "";
		returnable = index + "i" + position;
		return returnable;
	}
}
