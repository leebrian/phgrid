package gov.cdc.ncphi.phgrid.polygon.gmaps.loader;

import java.util.Iterator;
import java.util.List;

public class DatabasePolygon {
	private String locationName;
	private List<String> polygonStrings;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLocationName() {
		return locationName;
	}
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
	public List<String> getPolygonStrings() {
		return polygonStrings;
	}
	public void setPolygonStrings(List<String> polygonStrings) {
		this.polygonStrings = polygonStrings;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Standard Polygon Details - ");
		sb.append(", ");
		sb.append("Location: " + getLocationName());
		sb.append(", ");
		sb.append("Type: " + getType());
		if(getPolygonStrings() != null)
		{
		sb.append(", ");
		sb.append("Number of Polygons: " + getPolygonStrings().size());
		Iterator<String> iter = getPolygonStrings().iterator();
		String polygonString;
		while (iter.hasNext())
		{
			polygonString = iter.next();
			if (polygonString != null)
			{
			sb.append(", ");
			sb.append("polygon length: " + polygonString.length());
			}
		}
		}
		sb.append(".");
		
		return sb.toString();
	}
}
