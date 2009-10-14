
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
java.util.Map,
java.util.Collection,
java.util.Calendar,
java.util.Date,
java.text.SimpleDateFormat,
java.io.FileNotFoundException,
gov.cdc.ncphi.phgrid.polygon.gmaps.CountyPolygonFetcher,
gov.cdc.ncphi.phgrid.polygon.gmaps.GmapPolygon,
gov.cdc.ncphi.phgrid.polygon.gmaps.RegionPolygonFetcher,
gov.cdc.ncphi.phgrid.polygon.gmaps.GoogleMapStringGenerator,
gov.cdc.ncphi.phgrid.polygon.gmaps.StandardPolygonColorProcessor,
gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean,
gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean,
gov.cdc.ncphi.phgrid.polygon.regrel.RegionRelation,
gov.cdc.ncphi.phgrid.polygon.gmaps.StandardGmapPolygon,
gov.cdc.ncphi.phgrid.npdsgmaps.NPDSGmapTimeSeriesFetcher,
gov.cdc.ncphi.phgrid.npdsgmaps.GmapPolygonTimeSeriesLoader,
gov.cdc.ncphi.phgrid.npdsgmaps.jsphelper.NPDSGmapJSPBean, 
gov.cdc.ncphi.phgrid.npdsgmaps.jsphelper.TimeTrack,
gov.cdc.ncphi.phgrid.poicondai.NPDSTimeSeriesByRegion,
java.util.ArrayList" %>


    <%!
   
    private static final Logger logger = Logger.getLogger("gov.cdc.ncphi.polygon.gmaps.gmap-pane_jsp");
 	private static final String stateType = "state";
 	private static final String zip3type = "zip3";
 	private static final String zip5type = "zip5";
 	private Properties keyProp;
	private String mapString = "";
	private static final String MAPKEY = "googleMapKey";
	private static final String PROPERTIES_FILE = "/npdsgmaps-web.properties";
 	//private static final String mapString = "ABQIAAAARg1d7vQnI0FEwC7mH2zP2xQDlapOSsKn1i9Dkcc7EVBsqDtyyxSKSTgSSPFY9jGEMa6hpsS09NfGqQ";
	//private static final String mapString = "ABQIAAAARg1d7vQnI0FEwC7mH2zP2xTWPB1U_UQBCDmKy2DTPtk_9G4w1BQGrALAeBmj9fMe8Uk-wM1ggZBHng";
	

	
	private String initProp()
	{
		if (mapString ==null || mapString.equals(""))
		{
			try {
				keyProp = new Properties();
				InputStream is = getClass().getResourceAsStream(PROPERTIES_FILE);
		        if (is == null) logger.error("Could not read " + PROPERTIES_FILE);
		        keyProp.load(is);
		       	mapString = keyProp.getProperty(MAPKEY);
				} catch (FileNotFoundException e) {
					logger.error(e.getMessage());
					//e.printStackTrace();
				} catch (IOException e) {			
					logger.error(e.getMessage());
					//e.printStackTrace();
				}
		}
		return mapString;
	}
	
	
	private void setListRegionalPolygons(String regionType, String regionID, GoogleJSPBean gjsp, RegionSelectionBean rsb)
	{
		Collection<RegionRelation> crr = rsb.getRegionListByParentAndType(regionType, regionID);
			Collection<GmapPolygon> col = gjsp.getPolygonCollectionForRegionRelations(crr);
			gjsp.setPolygonCollection(col);
			//mapString = gjsp.getMap();
	}
	
	private void getNPDSStatePolygons(GoogleJSPBean gjsp, RegionSelectionBean rsb, NPDSGmapJSPBean ngjb, 
			Calendar startDate, Calendar endDate, String clinEffect)
	{	
	 ngjb.fetchAndLoadPolygonsForStates(gjsp, rsb,
		 startDate, endDate, clinEffect);
	
	}
	
	private void getNPDSZip3Polygons (GoogleJSPBean gjsp, RegionSelectionBean rsb, NPDSGmapJSPBean ngjb, String state,
			Calendar startDate, Calendar endDate, String clinEffect)
	{
	
	 ngjb.fetchAndLoadPolygonsForZip3(gjsp, 
			rsb, state,  startDate, endDate, 
		clinEffect);
	
	}
	
	private void getNPDSZip5Polygons (GoogleJSPBean gjsp, RegionSelectionBean rsb, NPDSGmapJSPBean ngjb, String zip3,
			Calendar startDate, Calendar endDate, String clinEffect)
	{
		{
		
		 ngjb.fetchAndLoadPolygonsForZip5(gjsp, 
				rsb, zip3, startDate, endDate, clinEffect);
		}
	}
	

    %>
<%         String passInKey = request.getParameter("passInKey");
if (passInKey == null) 
{
	passInKey = (String) request.getAttribute("passInKey");
	request.setAttribute("passInKey", null);
}
		
if (passInKey != null && passInKey.equals(session.getId()))
{ %>

<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Quicksilver</title>
<LINK href="../PCDA_files/pcda.css" type="text/css" rel="stylesheet">

<script src="http://code.jquery.com/jquery-latest.js"></script>
  
  <script>
  $(document).ready(function(){
    $('#startdate').datepicker();
    $('#enddate').datepicker();
  });
  </script>
           <script type="text/javascript">  
   
             function onShowHiddenClick(sender, event)  
             {  
                 $sender = $(sender);  
   
                 if($sender.is(':checked'))  
                 {  
                     $('div#adjustableLegend').show();
                       
                 }  
                 else  
                 {  
                     $('div#adjustableLegend').hide(); 
                 }  
   
             }  
         </script>  
  <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=<%=initProp()%>"
            type="text/javascript"></script>
  

    

    <jsp:useBean id="ngjb" scope="session" class="gov.cdc.ncphi.phgrid.npdsgmaps.jsphelper.NPDSGmapJSPBean" />
    <jsp:useBean id="gjsp" scope="session" class="gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean" /> 
	<jsp:useBean id="rsb" scope="application" class="gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean" />

         <% 
	Date startCSP = new Date();
         response.setHeader("Pragma","no-cache");
     	 String latestStateID = "ALL";
     	String mostRecentStateID = "";

     	 String latestZip3ID = "ALL";
         String mostRecentZip3ID = "";

     	String latestZip5ID = "ALL";
         String issueString = "";
         String startDateString = request.getParameter("startdate");
         String endDateString = request.getParameter("enddate");
         SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
         Calendar startDate = Calendar.getInstance();
         Calendar endDate = Calendar.getInstance();
         String loadClinKey = request.getParameter("ClinicalEffect");
         String sendClinKey = "";
         String displayTypeString = request.getParameter("displayType");
         int displayType = 0;
       	 int base = 0;
         int greenYellow = 1000;
         int yellowRed = 2000;
         String userZoom = request.getParameter("userZoom");
         String mostRecentRegionName = request.getParameter("mostRecentRegionName");
         String currentRegionName;
         
         if(mostRecentRegionName != null && mostRecentRegionName.length() > 5)
         {
        	 mostRecentRegionName = null;
         }
         if (userZoom !=null && userZoom.length() >2)
         {
        	 userZoom = null;
         }
         if (loadClinKey!= null && loadClinKey.length() == 3 &&!loadClinKey.equals("all")  && !loadClinKey.equals(""))
         {
        	 sendClinKey = loadClinKey;
         }
         Calendar today = Calendar.getInstance();
         if (startDateString == null || startDateString.equals("") || startDateString.length() != 10)
         {
        	 today.set(Calendar.DAY_OF_WEEK, 1);
        	 startDateString = (today.get(Calendar.MONTH) + 1) + "/" + today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.YEAR);
         }
       	Date startDt = df.parse(startDateString);
       	if (startDt != null)
	       	{
       		startDate.setTime(startDt);
	       	}

         if (endDateString == null || endDateString.equals("") || endDateString.length() != 10)
         	{
        	 today.set(Calendar.DAY_OF_WEEK, 7);
        	 endDateString = (today.get(Calendar.MONTH) + 1) + "/" + today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.YEAR);
             
         	}

       	Date endDt = df.parse(endDateString);
         	if (endDt != null)
         		{
         		endDate.setTime(endDt);
         		}
         	
         if (startDate.compareTo(endDate) >0)
         { endDate.setTime(startDate.getTime());
         	endDateString = startDateString;
         }

  	    	RegionPolygonFetcher rpf = RegionPolygonFetcher.getFetcher();
         
         if (request.getParameter("latestStateID") != null && request.getParameter("latestStateID").length() == 2)
        	  latestStateID = (String) request.getParameter("latestStateID");
         if (request.getParameter("latestZip3ID") !=null && request.getParameter("latestZip3ID").length() == 3)
        	 latestZip3ID = (String) request.getParameter("latestZip3ID");
         if (request.getParameter("latestZip5ID") != null && request.getParameter("latestZip5ID").length() ==5)
        	 latestZip5ID = (String) request.getParameter("latestZip5ID");
         if (request.getParameter("mostRecentStateID") != null && request.getParameter("mostRecentStateID").length() ==2)
        	 mostRecentStateID = (String) request.getParameter("mostRecentStateID");
         if (request.getParameter("mostRecentZip3ID") != null && request.getParameter("mostRecentZip3ID").length() == 3)
        	 mostRecentZip3ID = (String) request.getParameter("mostRecentZip3ID");
         if (displayTypeString  != null)
        	 displayType = Integer.parseInt(displayTypeString);
         
       if (latestStateID.equals("ALL"))
    		   {	
    	   //setListRegionalPolygons("state", "USA", gjsp, rsb);
    	   getNPDSStatePolygons(gjsp, rsb, ngjb, startDate, endDate, sendClinKey);
    	  latestZip3ID = "ALL";
    	  latestZip5ID= "ALL";
    	  currentRegionName = "USA";
    	  gjsp.setFocus("country", "USA");
   			   }
      else if (latestZip3ID.equals("ALL")|| !latestStateID.equals(mostRecentStateID))
    		   {
    	  mostRecentStateID = latestStateID;
    	 getNPDSZip3Polygons(gjsp, rsb, ngjb, latestStateID, startDate, endDate, sendClinKey);
    	  latestZip3ID = "ALL";
    	   latestZip5ID = "ALL";
    	   currentRegionName = latestStateID;
    	   gjsp.setFocus("state", latestStateID);
    		   }
       else if (latestZip5ID.equals("ALL")|| !latestZip3ID.equals(mostRecentZip3ID))
       {
    	   mostRecentZip3ID = latestZip3ID;
    	   getNPDSZip5Polygons(gjsp, rsb, ngjb, latestZip3ID, startDate, endDate, sendClinKey);
    	   gjsp.setFocus("zip3", latestZip3ID);
    	   currentRegionName = latestZip3ID;
    	   latestZip5ID = "ALL";
       }
       else
       {
    	   getNPDSZip5Polygons(gjsp, rsb, ngjb, latestZip3ID, startDate, endDate, sendClinKey);
    	   Collection<GmapPolygon> pollist = rpf.getRegionPolygon(latestZip5ID, "zip5");
    	   gjsp.setPolygonCollection(pollist);
    	   currentRegionName = latestZip5ID;
    	   gjsp.setFocus("zip5", latestZip5ID);
       }
     
       try
       {
       if (gjsp.adjustLegend(request.getParameter("base"), request.getParameter("greenYellow"), request.getParameter("yellowRed")))
       {
    	      base = Integer.parseInt(request.getParameter("base"));
    	      greenYellow = Integer.parseInt(request.getParameter("greenYellow"));
    	      yellowRed = Integer.parseInt(request.getParameter("yellowRed"));
       }
       else if  (request.getParameter("mostRecentbase") != null  
			   && request.getParameter("mostRecentgreenYellow") != null 
			   && request.getParameter("mostRecentyellowRed") != null)
       {
    	   
    		 base = Integer.parseInt(request.getParameter("mostRecentbase"));
     	      greenYellow = Integer.parseInt(request.getParameter("mostRecentgreenYellow"));
     	      yellowRed = Integer.parseInt(request.getParameter("mostRecentyellowRed"));

    	   
       }
       } catch(Exception e)
       {
    	   issueString = "There was an issue converting the legend values";
       }
       if (currentRegionName != null && mostRecentRegionName != null && currentRegionName.equals(mostRecentRegionName))
       {
    	   gjsp.setZoom(userZoom);
       }
       else
       {
    	   gjsp.setZoom(null);
       }
          	%>
          	          	
          	

      	          	

  </head>

  <body onload="runMap()" onunload="GUnload()">
<center>

<DIV id="wrapper">
<DIV id="pcdaHeader">
<H1>Quicksilver</H1>NPDS data in polygons.</DIV></DIV>


<div style="width: 650px;">
<FORM onSubmit="return setZL(this.submit);" METHOD="POST" name="mainForm" >

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
<BR>
<link rel="stylesheet" href="http://dev.jquery.com/view/tags/ui/latest/themes/flora/flora.datepicker.css" type="text/css" media="screen" title="Flora (Default)">
<script src="http://dev.jquery.com/view/tags/ui/latest/ui/ui.datepicker.js"></script>
<input type="text" id="startdate" name="startdate" value="<%=startDateString%>" style="width:200px;"/>
<input type="text" id="enddate" name="enddate" value="<%=endDateString%>" style="width:200px;"/>
<input type="hidden" name="mostRecentStateID" value ="<%=mostRecentStateID %>"/>
<input type="hidden" name="mostRecentZip3ID" value ="<%=mostRecentZip3ID %>"/>
<input type="hidden" name="mostRecentbase" value ="<%=base %>"/>
<input type="hidden" name="mostRecentgreenYellow" value ="<%=greenYellow %>"/>
<input type="hidden" name="mostRecentyellowRed" value ="<%=yellowRed %>"/>
<input type="hidden" name="mostRecentRegionName" value="<%= currentRegionName%>"/>
<input type="hidden" name="userZoom"/>
<input type="hidden" name="passInKey" value="<%=session.getId()%>" />
<INPUT TYPE="submit" id="submitbutton" value="submit" style="display:display;" />
<BR>
<SELECT NAME="ClinicalEffect">
<OPTION VALUE="all">All Clinical Effects </OPTION>
<% 
	//Set sortKeys = new TreeSet();
	//sortKeys.addAll(prop.keySet());
	Map<String, String> clinMap = ngjb.getClinicalEffects();
	
	Set<String> clinMapKeys = clinMap.keySet();
	Iterator<String> clinEnum = clinMapKeys.iterator();
String clinKey = "";
String clinVal = "";
	while (clinEnum.hasNext())
	{
		clinVal = (String) clinEnum.next();
		clinKey = clinMap.get(clinVal);
		%> <Option Value = "<%=clinKey%>" <% if (loadClinKey != null && clinKey.equals(loadClinKey)){ %> selected="yes"<% }%>><%=clinKey%> - <%=clinVal%> </Option>
  <%}
	  %>

</SELECT>
<div id="displayType" style="width: 300px">
Display Type: 
<SELECT NAME="displayType">
<OPTION VALUE="0">Pinpoint</OPTION>
<OPTION VALUE="1" <%if (displayType == 1) {%> selected ="yes" <%} %>>Polygons</OPTION>
</SELECT>
</div>
<BR>
        <label><input type="checkbox" id="adjCheck" onclick="return onShowHiddenClick(this, event);" /> 
        Customizable Legend</label>  
         <div id="adjustableLegend" style="display:none;"><br>
  blank &lt;= <input type="text" id="base" name="base" value="<%=base%>" style="width:50px;"/>
&lt; green &lt;= <input type="text" id="greenYellow" name="greenYellow" value="<%=greenYellow%>" style="width:50px;"/>
&lt; yellow &lt;= <input type="text" id="yellowRed" name="yellowRed" value="<%=yellowRed%>" style="width:50px;"/> &lt; red </div>
</FORM>

<div id="map_canvas" style="width: 600px; height: 500px"></div>
	

<script type="text/javascript" defer="defer">
<%=gjsp.getMap(session.getId(), displayType) %>

function setZL(theButton) {
	document.mainForm.submitbutton.value="Loading...";
	document.mainForm.submitbutton.disabled=true;
	document.mainForm.userZoom.value=map.getZoom();
	return true; 
	}
</script>

<div id="legend_canvas" style="width: 600px;">
<table style="background-color:white" cellspacing="0" cellpadding="1" border="1" width="250px" align="right">
  <tr>
    <th>Color</th>
    <th>Range</th>
  </tr>
  <tr>
    <td bgcolor="#ffffff" >&nbsp;</td>
    <td><%=base%> calls</td>
  </tr>
  <tr>
    <td bgcolor="#66ff80" >&nbsp;</td>
    <td>between <%=base+1%> and <%=greenYellow%> calls</td>
  </tr>
    <tr>
    <td bgcolor="#ffff80" >&nbsp;</td>
    <td>between <%=greenYellow+1%> and <%=yellowRed%> calls</td>
  </tr>
  <tr>
  <td bgcolor="#ff9999" >&nbsp;</td>
  <td>more than <%=yellowRed %> calls</td>
</tr>
</table>
<br>
<div>
<p>For more information or help, please click <a href="http://sites.google.com/site/phgrid/active-projects/national-poison-data-aggregation-system/quicksilverhelp" target="_blank" >here</a></p>
<br> <%=issueString %>
</div>
</div>
</div>
<br> 
<br>
<br>
<div id="countCanvas" style="width: 600px; height: 500px">
<%
Iterator<TimeTrack> timeIter = ngjb.getTimeTracks().iterator();
long otherTime = 0;
Date endCSP = new Date();
while (timeIter.hasNext())
{
	TimeTrack track = timeIter.next();
	otherTime += track.getTrackerTime();
	%>
	<%=track.getTrackerType() %> took <%=track.getTrackerTime() %> ms <br>
	<%
}
long cspTime = (endCSP.getTime() - startCSP.getTime() - otherTime); 
%>
<script>
var clientMS = <%=cspTime%>; 
</script>
<div id="clientMS" >Client Side Processing took <%=cspTime %> ms</div>
</div>
</center>
  </body>
</html>
<%}else
{ %> ERROR CSRF <%

}%>

