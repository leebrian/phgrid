    

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
java.util.HashSet,
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
gov.cdc.ncphi.phgrid.gridviewer.jsphelper.GridViewerJSPHelper,
gov.cdc.ncphi.phgrid.gridviewer.jsphelper.TimeTrack,
gov.cdc.ncphi.phgrid.gridviewer.ServerMetadata,
java.util.ArrayList" %>
    


<html xmlns="http://www.w3.org/1999/xhtml" xmlns:v="urn:schemas-microsoft-com:vml">
  <head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>Grid Viewer Server Selection</title>
<LINK href="PCDA_files/pcda.css" type="text/css" rel="stylesheet">

<script src="http://code.jquery.com/jquery-latest.js"></script>
   <jsp:useBean id="gvjh" scope="session" class="gov.cdc.ncphi.phgrid.gridviewer.jsphelper.GridViewerJSPHelper" />
    <jsp:useBean id="gjsp" scope="session" class="gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean" /> 
	<jsp:useBean id="ssjh" scope="session" class="gov.cdc.ncphi.phgrid.gridviewer.jsphelper.ServerSelectorJSPHelper" />
  <script>
  $(document).ready(function(){
    $('#startdate').datepicker();
    $('#enddate').datepicker();
  });
  var startTimer = new Date();
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

             <%=ssjh.getContainsArray() %>

             function selectCheck(selec, type){
            		checkboxArray=document.getElementsByName("serverKeys");

            		for(x in checkboxArray)
            		{
            	               
            			var checkbox = checkboxArray[x];
            			var serverName = checkbox.getAttribute('value');
            	var val=selec.options[selec.selectedIndex].value
            			var queryString = serverName+type+val;
            			if (containsArray[queryString])
            			{
            				checkbox.removeAttribute('disabled');
            			}
            			else
            			{
            				checkbox.setAttribute('disabled','disabled');
            				checkbox.checked = false;
            			}
            		}
            	 }
         </script>  
         
 
  </head>

  <body>
<center>

<DIV id="wrapper">
<DIV id="pcdaHeader">
<H1>Grid Viewer</H1>AMDS data in polygons.</DIV></DIV>


<div style="width: 650px;">
<FORM onSubmit="return setZL(this.submit);" METHOD="POST" name="mainForm" >

<SELECT NAME="latestStateID" onclick="selectCheck(this, 'state');">
	
<%=ssjh.getStateSelectList() %>
	
		
</SELECT>
<BR>
<link rel="stylesheet" href="http://dev.jquery.com/view/tags/ui/latest/themes/flora/flora.datepicker.css" type="text/css" media="screen" title="Flora (Default)">
<script src="http://dev.jquery.com/view/tags/ui/latest/ui/ui.datepicker.js"></script>
<input type="text" id="startdate" name="startdate" value="04/16/2008" style="width:200px;"/>
<input type="text" id="enddate" name="enddate" value="05/15/2008" style="width:200px;"/>
<input type="hidden" name="mostRecentStateID" value =""/>
<input type="hidden" name="mostRecentZip3ID" value =""/>
<input type="hidden" name="mostRecentbase" value ="0"/>
<input type="hidden" name="mostRecentgreenYellow" value ="1000"/>
<input type="hidden" name="mostRecentyellowRed" value ="2000"/>
<input type="hidden" name="mostRecentRegionName" value="USA"/>
<input type="hidden" name="userZoom"/>
<input type="hidden" name="passInKey" value="07C9BF61BC67C2FCFA767E39B36E8B90" />
<INPUT TYPE="submit" id="submitbutton" value="submit" style="display:display;" />

<BR>
<SELECT NAME="Classifier">
 <%=ssjh.getClassifiers() %>

</SELECT>
<div id="servers" style="width: 300px"> 
Selected Servers <BR>
<input TYPE="checkbox" NAME="serverKeys"
	  CHECKED="true" VALUE="localAMDS"> <a href="srvmeta.jsp?serverName=localAMDS">localAMDS</a><br> <input TYPE="checkbox" NAME="serverKeys"
	  VALUE="remoteAMDS"> <a href="srvmeta.jsp?serverName=remoteAMDS">remoteAMDS</a><br> 
</div>
<div id="displayType" style="width: 300px">
Display Type: 

<SELECT NAME="displayType">
<OPTION VALUE="0">Pinpoint</OPTION>
<OPTION VALUE="1" >Polygons</OPTION>
</SELECT>
</div>
<BR>
        <label><input type="checkbox" id="adjCheck" onclick="return onShowHiddenClick(this, event);" /> 
        Customizable Legend</label>  
         <div id="adjustableLegend" style="display:none;"><br>
  blank &lt;= <input type="text" id="base" name="base" value="0" style="width:50px;"/>

&lt; green &lt;= <input type="text" id="greenYellow" name="greenYellow" value="1000" style="width:50px;"/>
&lt; yellow &lt;= <input type="text" id="yellowRed" name="yellowRed" value="2000" style="width:50px;"/> &lt; red </div>


</FORM>
<script>

function setZL(theButton) {
	document.mainForm.submitbutton.value="Loading...";
	document.mainForm.submitbutton.disabled=true;
	document.mainForm.userZoom.value=map.getZoom();
	return true; 
	}


</script>

</div>
</center>
  </body>
</html>


