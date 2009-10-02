package gov.cdc.ncphi.phgrid.polygon.gmaps;

public interface PinpointTypeProcessor {
	String getPinpointType(GmapPolygon polygon);
	
	boolean setPinpointRange(Double lowerBound, Double upperBound, String color);
	boolean removePinpointRange(Double lowerBound, Double upperBound);
	boolean removeAllPinpointRanges();
}
