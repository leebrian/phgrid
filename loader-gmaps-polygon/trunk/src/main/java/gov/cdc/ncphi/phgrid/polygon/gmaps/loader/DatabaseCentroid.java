package gov.cdc.ncphi.phgrid.polygon.gmaps.loader;

public class DatabaseCentroid {
	private String location;
	private String centroidLongitude;
	private String centroidLatitude;
	private String type;
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getCentroidLongitude() {
		return centroidLongitude;
	}
	public void setCentroidLongitude(String centroidLongitude) {
		this.centroidLongitude = centroidLongitude;
	}
	public String getCentroidLatitude() {
		return centroidLatitude;
	}
	public void setCentroidLatitude(String centroidLatitude) {
		this.centroidLatitude = centroidLatitude;
	}
	

		public String toString() {
			StringBuffer sb = new StringBuffer();
			sb.append("Database Centroid Details - ");
			sb.append("Location: " + getLocation());
			sb.append(", ");
			sb.append("Type: " + getType());
			sb.append(", ");
			sb.append("Longitude: " + getCentroidLongitude());
			sb.append(", ");
			sb.append("Latitude: " + getCentroidLatitude());
			sb.append(".");
			
			return sb.toString();
		}

	
}
