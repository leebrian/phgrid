package gov.cdc.ncphi.phgrid.polygon.gmaps;

import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class StandardGmapPolygon implements java.io.Serializable, GmapPolygon{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7206419557178997406L;

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("Standard Polygon Details - ");
		sb.append("Region Name: " + getRegionName());
		sb.append(", ");
		sb.append("Region Type: " + getRegionType());
		if (getCoordinates() != null)
		{
		sb.append(", ");
		sb.append("Number of Coordinates: " + getCoordinates().size());
		}
		if (getCentroid() != null)
		{
		sb.append(", ");
		sb.append("Centroid: " + getCentroid().getLatitude() + ", " + getCentroid().getLongitude());
		}
		sb.append(".");
		
		return sb.toString();
	}
	public Coordinate getCentroid() {
		return centroid;
	}
	
	
	private Coordinate centroid;
	private List<Coordinate> coordinates;
	private String titlePrefix;
	private String regionName;
	private String regionType;
	private Map<String, List<TimeSeries>> timeSeries;
	private int totalCount = 0;
	private int highestCount= 0;
	private Calendar startDate;
	private Calendar endDate;
	
	public int getHighestCount() {
		return highestCount;
	}
	public void setHighestCount(int highestCount) {
		this.highestCount = highestCount;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public void setCoordinates(List<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}
	public void setPopupInfo(Object popupInfo) {
		String titlePrefix = (String) popupInfo;
		this.titlePrefix = titlePrefix;
	}
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	public void setTimeSeries(Map<String, List<TimeSeries>> timeSeries) {
		this.timeSeries = timeSeries;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public void setCentroid(Coordinate coordinate)
	{
		this.centroid = coordinate;
	}
	public List<Coordinate> getCoordinates() {
		return coordinates;
	}
	public String getPopupInfo() {
		return titlePrefix;
	}
	public String getRegionName() {
		return regionName;
	}
	public String getRegionType() {
		return regionType;
	}
	public Map<String, List<TimeSeries>> getTimeSeries() {
		return timeSeries;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public Calendar getEndDate() {
		return endDate;
	}
	public Calendar getStartDate() {
		return startDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}

}
