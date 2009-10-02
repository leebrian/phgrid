package gov.cdc.ncphi.phgrid.polygon.gmaps;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

public class GoogleMapStringGenerator {
	
	private static final Logger logger = Logger.getLogger(GoogleMapStringGenerator.class);
	private static GoogleMapStringGenerator gmsg;
	private static Map<Integer, String> stringMap;
	private GoogleMapStringGenerator()
	{
		stringMap = new HashMap<Integer,String>();
	}
	public String getPolygonString(GmapPolygon polygon, String position, PolygonColorProcessor pcproc, 
			PolygonPopupHandler polyproc, String passInKey, int collectionIndex)
	{
		String returnable = "";
		
     		List<Coordinate> coorlist =  polygon.getCoordinates();
      		
      		
      		returnable = "var polygon"+position+" = new GPolygon([";
      		String coorString = "";
      		if (stringMap.containsKey(new Integer(coorlist.hashCode())))
      				{
      			coorString = stringMap.get(new Integer(coorlist.hashCode()));
      				}
      		else{
      		Coordinate coor;
      		Iterator<Coordinate> coorEnum = coorlist.iterator();
      		
      		while(coorEnum.hasNext())
      		{
   		   coor = coorEnum.next();
   		   coorString += "new GLatLng(" + coor.getLatitude() + ", " + coor.getLongitude() + "),\n";
   	   }  
      		stringMap.put(new Integer(coorlist.hashCode()), coorString);
      		}
      		returnable += coorString;
      		ColorBean cb = pcproc.getColor(polygon);

   	   returnable += "], \""+ cb.getBorderColor() + "\", "+ cb.getBorderStrength() + ", 1, \""+cb.getFillColor()+"\", "+cb.getFillStrength()+");\n";
   	   returnable += "GEvent.addListener(polygon" + position + ", \"click\", function(latlng) {\n";
   	   returnable += "map.openInfoWindowHtml(latlng, ";
   	   returnable += polyproc.getPopupString(position, polygon, passInKey, collectionIndex);
   	   returnable += ")\n});\n";
	   returnable += "map.addOverlay(polygon"+position+");\n";
	   logger.debug("polygon text: " + returnable);
		return returnable;
		
	}
	
	public static GoogleMapStringGenerator getStringGenerator()
	{
		if (gmsg == null)
		{
			gmsg = new GoogleMapStringGenerator();
		}
		return gmsg;
	}

}
