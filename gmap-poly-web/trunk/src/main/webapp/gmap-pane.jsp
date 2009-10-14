
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
	private static String latestStateID = "ALL";
	private static String mostRecentStateID = null;
	private static String stateType = "state";
	private static String latestZip3ID = "ALL";
	private static String mostRecentZip3ID = null;
	private static String zip3type = "zip3";
	private static String latestZip5ID = "ALL";
	private static String zip5type = "zip5";
	//private static final String mapString = "ABQIAAAARg1d7vQnI0FEwC7mH2zP2xQDlapOSsKn1i9Dkcc7EVBsqDtyyxSKSTgSSPFY9jGEMa6hpsS09NfGqQ";
	private static final String mapString = "ABQIAAAARg1d7vQnI0FEwC7mH2zP2xTWPB1U_UQBCDmKy2DTPtk_9G4w1BQGrALAeBmj9fMe8Uk-wM1ggZBHng";
	private void setListRegionalPolygons(String regionType, String regionID, GoogleJSPBean gjsp, RegionSelectionBean rsb)
	{
		Collection<RegionRelation> crr = rsb.getRegionListByParentAndType(regionType, regionID);
			Collection<GmapPolygon> col = gjsp.getPolygonCollectionForRegionRelations(crr);
			gjsp.setPolygonCollection(col);  
	}
    %>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Google Maps UI Pane</title>
<LINK href="PCDA_files/pcda.css" type="text/css" rel="stylesheet">
    <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=<%=mapString%>"
            type="text/javascript"></script>
    <script type="text/javascript" defer="defer">
    
    <jsp:useBean id="gjsp" scope="session" class="gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean" /> 
	<jsp:useBean id="rsb" scope="application" class="gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean" />



    
   

         <% 

         
         
  	   	RegionPolygonFetcher rpf = RegionPolygonFetcher.getFetcher();
         
         if (request.getParameter("latestStateID") != null)
        	  latestStateID = (String) request.getParameter("latestStateID");
         if (request.getParameter("latestZip3ID") !=null)
        	 latestZip3ID = (String) request.getParameter("latestZip3ID");
         if (request.getParameter("latestZip5ID") != null)
        	 latestZip5ID = (String) request.getParameter("latestZip5ID");
         
       if (latestStateID.equals("ALL"))
    		   {	
    	   setListRegionalPolygons("state", "USA", gjsp, rsb);
    	   
    	  latestZip3ID = "ALL";
    	  latestZip5ID= "ALL";
    	  gjsp.setFocus("country", "USA");
   			   }
      else if (latestZip3ID.equals("ALL")|| !latestStateID.equals(mostRecentStateID))
    		   {
    	  mostRecentStateID = latestStateID;
    	   setListRegionalPolygons("zip3", latestStateID, gjsp, rsb);
    	   latestZip3ID = "ALL";
    	   latestZip5ID = "ALL";
    	   gjsp.setFocus("state", latestStateID);
    		   }
       else if (latestZip5ID.equals("ALL")|| !latestZip3ID.equals(mostRecentZip3ID))
       {
    	   mostRecentZip3ID = latestZip3ID;
    	   setListRegionalPolygons("zip5", latestZip3ID, gjsp, rsb);
    	   gjsp.setFocus("zip3", latestZip3ID);
    	   latestZip5ID = "ALL";
       }
       else
       {
    	   Collection<GmapPolygon> pollist = rpf.getRegionPolygon(latestZip5ID, "zip5");
    	   gjsp.setPolygonCollection(pollist);
    	   gjsp.setFocus("zip5", latestZip5ID);
       }
          	%>
          	          	
          	

          	
    </script>
  </head>

  <body onload="initialize()" onunload="GUnload()">
<center>

<DIV id="wrapper">
<DIV id="pcdaHeader">
<H1>Gmap-polygon</H1>Example polygon mapper.</DIV></DIV>



<FORM METHOD="POST">

<SELECT NAME="latestStateID">
	
<% 
	Set<String> sortKeys = new TreeSet<String>();
	
	sortKeys.addAll(rsb.getStateList().keySet());
	Iterator<String> sortEnum =  sortKeys.iterator();
	String statename = "";
	String stateid = ""; 
	
	
	%>
	<Option Value = "ALL" <% if (latestStateID != null && latestStateID.equals("ALL")){ %> selected="yes"<% }%>>All States</Option>
<%
	while (sortEnum.hasNext())
	{
		stateid = sortEnum.next();
		statename = rsb.getStateList().get(stateid);
		
		%> <Option Value = "<%=stateid%>" <% if (latestStateID != null && stateid.equals(latestStateID)){ %> selected="yes"<% }%>><%=stateid%> - <%=statename%> </Option>
		<%
	}
	if (!latestStateID.equals("ALL"))
	{		
%>
</SELECT>
<SELECT NAME="latestZip3ID">

<%
	Collection<RegionRelation> zip3List =  rsb.getRegionListByParentAndType("zip3", latestStateID);
	Iterator<RegionRelation> zip3iter = zip3List.iterator();
	String zip3ID = "";
	%>
	<Option Value = "ALL" <% if (latestZip3ID != null && latestZip3ID.equals("ALL")){ %> selected="yes"<% }%>>All Zip 3</Option>
<%
while (zip3iter.hasNext())
{
	zip3ID = zip3iter.next().getRegionName();
	
	%> <Option Value = "<%=zip3ID%>" <% if (latestZip3ID != null && zip3ID.equals(latestZip3ID)){ %> selected="yes"<% }%>><%=zip3ID%></Option>
	<%
}
	}
	if (!latestZip3ID.equals("ALL"))
	{
		%>
</SELECT>
		<SELECT NAME="latestZip5ID">

		<%
			Collection<RegionRelation> zip5List =  rsb.getRegionListByParentAndType("zip5", latestZip3ID);
			Iterator<RegionRelation> zip5iter = zip5List.iterator();
			String zip5ID = "";
			%>
			<Option Value = "ALL" <% if (latestZip5ID != null && latestZip5ID.equals("ALL")){ %> selected="yes"<% }%>>All Zip 5</Option>
		<%
		while (zip5iter.hasNext())
		{
			zip5ID = zip5iter.next().getRegionName();
			
			%> <Option Value = "<%=zip5ID%>" <% if (latestZip5ID != null && zip5ID.equals(latestZip5ID)){ %> selected="yes"<% }%>><%=zip5ID%></Option>
			<%
		}
			}
%>
</SELECT>
<INPUT TYPE="submit" id="submitbutton" value="submit" style="display:display;"/>
</FORM>
<div id="map_canvas" style="width: 600px; height: 500px"></div>
<script type="text/javascript">
<%=gjsp.getMap()%>
</script>
</center>
  </body>
</html>

