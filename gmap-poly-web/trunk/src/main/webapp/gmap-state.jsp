
    <%@ page contentType="text/html; 
        charset=utf-8"
        import="org.apache.log4j.Logger,
java.lang.Integer,
java.util.Properties,
java.io.InputStream, 
java.io.IOException,
java.util.Iterator,
java.util.TreeSet,
java.util.Set,
java.util.Collection,
gov.cdc.ncphi.phgrid.polygon.gmaps.CountyPolygonFetcher,
gov.cdc.ncphi.phgrid.polygon.gmaps.GmapPolygon,
gov.cdc.ncphi.phgrid.polygon.gmaps.RegionPolygonFetcher,
gov.cdc.ncphi.phgrid.polygon.gmaps.GoogleMapStringGenerator,
gov.cdc.ncphi.phgrid.polygon.gmaps.StandardPolygonColorProcessor,
gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean,
gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean,
gov.cdc.ncphi.phgrid.polygon.regrel.RegionRelation,
gov.cdc.ncphi.phgrid.polygon.gmaps.StandardGmapPolygon,
java.util.ArrayList" %>


    <%!
   
    private static final Logger logger = Logger.getLogger("gov.cdc.ncphi.polygon.gmaps.gmap-pane_jsp");
	//private static String latestRegionType = "zip3";
	//private static String latestRegionID = "CO";
	private static String latestStateID = "CO";
	private static String stateType = "state";
	

    %>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Google Maps UI Pane</title>
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=ABQIAAAARg1d7vQnI0FEwC7mH2zP2xRmIRUcjIFFAoiB0Vkpm0Yeqc1TWBSMVvnl906CAmtGaL3RjDt4GTq93w"
            type="text/javascript"></script>
    <script type="text/javascript">
    <jsp:useBean id="gjsp" scope="session" class="gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean" /> 
	<jsp:useBean id="rsb" scope="application" class="gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean" />



    
    function initialize() {
      if (GBrowserIsCompatible()) {
        var map = new GMap2(document.getElementById("map_canvas"));
        map.setCenter(new GLatLng(38.451299, -106.021040), 3);    
        map.addControl(new GSmallMapControl());

         <% 
         if (request.getParameter("latestStateID") != null)
        	  latestStateID = (String) request.getParameter("latestStateID");
         
         
	
    	  // setListRegionalPolygons("state", "USA", gjsp, rsb);
    	  RegionPolygonFetcher rpf = new RegionPolygonFetcher();
    	 Collection<GmapPolygon> col = rpf.getRegionPolygon(latestStateID, "state");
    	 gjsp.setPolygonCollection(col);
    	 %>
    		   <%=gjsp.getPolygons()%>
      }
    }   
    </script>
  </head>
<FORM METHOD="POST">

<SELECT NAME="latestStateID">
	
<% 
	Set<String> sortKeys = new TreeSet<String>();
	
	sortKeys.addAll(rsb.getStateList().keySet());
	Iterator<String> sortEnum =  sortKeys.iterator();
	String statename = "";
	String stateid = ""; 

	while (sortEnum.hasNext())
	{
		stateid = sortEnum.next();
		statename = rsb.getStateList().get(stateid);		
		%> <Option Value = "<%=stateid%>" <% if (latestStateID != null && stateid.equals(latestStateID)){ %> selected="yes"<% }%>><%=stateid%> - <%=statename%> </Option>
		<%
	}
%>
</SELECT>
<INPUT TYPE="submit" id="submitbutton" value="submit" style="display:display;"/>
</FORM>
  <body onload="initialize()" onunload="GUnload()">
    <div id="map_canvas" style="width: 500px; height: 300px"></div>
	


  </body>
</html>

