package gov.cdc.ncphi.phgrid.polygon.gmaps;

public interface PolygonPopupHandler {
	public String getPopupString(String position, GmapPolygon polygon, String passInKey, int colIndex);
	public boolean setFormatString();
}
