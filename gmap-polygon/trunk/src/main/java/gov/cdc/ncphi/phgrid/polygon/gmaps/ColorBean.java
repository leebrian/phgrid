package gov.cdc.ncphi.phgrid.polygon.gmaps;

public class ColorBean implements java.io.Serializable {

	private static final long serialVersionUID = -4772948401054934400L;
private String borderColor;
private String borderStrength;
private String fillColor;
private String fillStrength;
/**
 * 
 * @return a String containing the border color in an HTML hex key format (#ffffff).
 */
public String getBorderColor()  {
	return borderColor;
}
/**
 * Sets the default color of borders of polygons to be returned.
 * color is an HTML hex key format (#ffffff) 
 * @param borderColor
 */
public void setBorderColor(String borderColor) {
	this.borderColor = borderColor;
}
/**
 * 
 * @return a String representing the strength (in pixel width) of polygon borders
 */
public String getBorderStrength() {
	return borderStrength;
}
/**
 * Sets the strength of the polygon borders in pixels.
 * @param borderStrength
 */
public void setBorderStrength(String borderStrength) {
	this.borderStrength = borderStrength;
}
/**
 * 
 * @return returns the fill color of polygons in html hex key format (#ffffff)
 */
public String getFillColor() {
	return fillColor;
}
/**
 * 
 * @param fillColor sets the fill color of polygons in html hex key format (#ffffff)
 */
public void setFillColor(String fillColor) {
	this.fillColor = fillColor;
}
/** 
 * 
 * @return the fill strength for a polygon in decimal format (0.2)
 */
public String getFillStrength() {
	return fillStrength;
}

/**
 * 
 * @param fillStrength sets the fill strength for a polygon in decimal format (0.2)
 */
public void setFillStrength(String fillStrength) {
	this.fillStrength = fillStrength;
}


}
