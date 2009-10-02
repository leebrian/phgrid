package gov.cdc.ncphi.phgrid.polygon.gmaps;

public interface PolygonColorProcessor {
	ColorBean getColor(GmapPolygon polygon);
	
	boolean setFillColorRange(Double lowerBound, Double upperBound, String color);
	boolean removeFillColorRange(Double lowerBound, Double upperBound);
	boolean removeAllColorRanges();
}
