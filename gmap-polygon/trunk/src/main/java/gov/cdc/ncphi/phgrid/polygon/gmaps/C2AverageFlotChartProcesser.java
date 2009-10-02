package gov.cdc.ncphi.phgrid.polygon.gmaps;

import org.apache.log4j.Logger;

public class C2AverageFlotChartProcesser implements PolygonPopupHandler {

	private String flotPage = "flotplot.jsp";
	private static final Logger logger = Logger.getLogger(C2AverageFlotChartProcesser.class);
	public String getPopupString(String position, GmapPolygon polygon, String passInKey, int colIndex) {
		//String returnable =  "GEvent.addListener(polygon" + position + ", \"click\", function(latlng) {\n";
		
		//returnable += "map.openInfoWindowHtml(latlng, '"+ polygon.getRegionType()+ ": "+polygon.getRegionName() +" <br> "+ polygon.getTotalCount()+ " total cases <br> ";
	   	   //this.getMapImageString(polygon)+ "')\n";
	   	String returnable =   "'"+ polygon.getRegionType()+ ": "+polygon.getRegionName() +" <br> "+ polygon.getTotalCount()+ " total cases <br> ";
		if (polygon.getTotalCount() > 0 )
	   	   {
	   	   returnable += "<iframe src=\""+flotPage + "?regionName="+polygon.getRegionName()+"&passInKey="+passInKey+
	   	   "&index=" + colIndex + "\" width=\"400\" height=\"300\" frameborder=\"0\"> <br> ";
	   	   }
	   	  returnable += "'";
		return returnable;
		
		/*
		 * <iframe src="included.html" width="450" height="400" frameborder="0">
<a href="included.html">Hmm, you are using a very old browser.
Click here to go directly to included content.</a>
</iframe>
		 */
	
	}

	public boolean setFormatString() {
		// TODO Auto-generated method stub
		return false;
		

	}
	public void setFlotPage(String inFlotPage)
	{
		flotPage = inFlotPage;
	}

}
