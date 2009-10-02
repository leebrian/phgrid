package gov.cdc.ncphi.phgrid.polygon.regrel;

import java.io.Serializable;

/**
 * A bean for storing a region and it's parent.  
 * 
 * Example, regionType zip3 with regionName'303' has the parent 'GA'
 * These are going to be generated from a database list 
 * using the LocationListFetcher
 * @author Peter White
 *
 */
public class RegionRelation implements Serializable, Comparable<RegionRelation>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4712317929110408750L;

	public String getRegionType() {
		return regionType;
	}
	public void setRegionType(String regionType) {
		this.regionType = regionType;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}
	public String getRegionParent() {
		return regionParent;
	}
	public void setRegionParent(String regionParent) {
		this.regionParent = regionParent;
	}
	private String regionType;
	private String regionName;
	private String regionParent;
	public String toString()
	{
		StringBuffer sb = new StringBuffer();
		sb.append("Region Relation Details - ");
		sb.append("Region Name: " + getRegionName());
		sb.append(", ");
		sb.append("Region Type: " + getRegionType());
		sb.append(", ");
		sb.append("Region Parent: " + getRegionParent());
				
		return sb.toString();
	}

	public int compareTo(RegionRelation o) {
		return (this.getRegionName().compareTo(o.getRegionName()));

	}
}
