package gov.cdc.ncphi.phgrid.polygon.regrel;

import gov.cdc.ncphi.phgrid.polygon.gmaps.Coordinate;

public class RegionData {
	String regionName;
	String regionType;
	String regionParent;
	Coordinate centroid;
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getRegionType() {
		return regionType;
	}
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	public String getRegionParent() {
		return regionParent;
	}
	public void setRegionParent(String regionParent) {
		this.regionParent = regionParent;
	}
	public void setCentroid (Coordinate centroid)
	{
		this.centroid = centroid;
	}
	public Coordinate getCentroid()
	{
		return centroid;
	}
}
