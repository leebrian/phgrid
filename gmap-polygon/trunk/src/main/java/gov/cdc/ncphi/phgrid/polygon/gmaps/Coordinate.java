package gov.cdc.ncphi.phgrid.polygon.gmaps;

import java.io.Serializable;

public class Coordinate implements Serializable {
	
	public Coordinate(String latitude, String longitude)
	{
		this.latitude = latitude;
		this.longitude = longitude;
	}
 private String longitude;
public String getLongitude() {
	return longitude;
}
public void setLongitude(String longitude) {
	this.longitude = longitude;
}
public String getLatitude() {
	return latitude;
}
public void setLatitude(String latitude) {
	this.latitude = latitude;
}
private String latitude;

}
