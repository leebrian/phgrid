<%@ include file="taglibs.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="phm" scope="application" class="gov.cdc.ncphi.phgrid.gridviewer.jsphelper.PHMapper" />
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <title>Gridviewer</title>
        <link href="styles/import.css" rel="stylesheet" type="text/css" />
        <script src="scripts/jquery-1.3.2.min.js" type="text/javascript"></script>
        <script src="http://ajax.googleapis.com/ajax/libs/jqueryui/1.7.1/jquery-ui.js" type="text/javascript"></script>
        <script src="http://maps.google.com/maps?file=api&amp;v=2&amp;key=<%=phm.getGoogleMapKey()%>" type="text/javascript"></script>
        <script src="scripts/daterangepicker.js" type="text/javascript"></script>
        <script src="scripts/markermanager.js" type="text/javascript"></script>
        <script src="scripts/jquery.selectboxes.js" type="text/javascript"></script>
        <script src="scripts/thickbox.js" type="text/javascript"></script>
        <script src="scripts/gridviewer.js" type="text/javascript"></script>
        <script src="scripts/gridviewer.current.js" type="text/javascript"></script>
        <script src="/gridviewer/dwr/interface/regionDao.js" type="text/javascript"></script>
        <script src="/gridviewer/dwr/interface/observationDao.js" type="text/javascript"></script>
        <script src="/gridviewer/dwr/engine.js" type="text/javascript"></script>
        <script src="scripts/initialize.js" type="text/javascript"></script>
        <script src="scripts/jquery.tablesorter.js" type="text/javascript"></script>
        <script src="scripts/regionCalls.js" type="text/javascript"></script>
        <script src="scripts/icon.js" type="text/javascript"></script>
        <script src="scripts/mapiconmaker.js" type="text/javascript"></script>
        <script src="scripts/sliderEvents.js" type="text/javascript"></script>
        <script src="scripts/color.js" type="text/javascript"></script>
        <script src="scripts/jquery.sparkline.min.js" type="text/javascript"></script>
        <script src="scripts/jquery.metadata.js" type="text/javascript"></script>
        <script src="scripts/jquery.getUrlParam.js" type="text/javascript"></script>

        <script type="text/javascript">
            var startDate = "<c:out value='${param.startdate}' />";
            var endDate = "<c:out value='${param.enddate}' />";
            var latestStateID = "<c:out value='${param.latestStateID}' />"?"<c:out value='${param.latestStateID}' />":"ALL";
            var age = "<c:out value='${param.age}' />";
            var serviceAreas = "<c:out value='${param.serviceAreas}' />"?"<c:out value='${param.serviceAreas}' />":"ALL";
            var latestZip3ID = "<c:out value='${param.latestZip3ID}' />";
            var latestZip5ID = "<c:out value='${param.latestZip5ID}' />";
            var ClinicalEffect = "<c:out value='${param.ClinicalEffect}' />"?"<c:out value='${param.ClinicalEffect}' />":"0";
            var Classifier = "<c:out value='${param.Classifier}' />"?"<c:out value='${param.Classifier}' />":"BioSense";
            var serverKeys = "<c:out value='${param.serverKeys}' />";

            var postBack =  "<c:out value='${param.postBack}' />"?true:false;
            var mostRecentRegionName = "<c:out value='${param.mostRecentRegionName}' />"?"<c:out value='${param.mostRecentRegionName}' />":"BioSense";
            var addQuery = "<c:out value='${param.addQuery}' />"?true:false;
            var displayType = "<c:out value='${param.displayType}' />"?"<c:out value='${param.displayType}' />":"0";
            var userZoom = "<c:out value='${param.userZoom}' />"?"<c:out value='${param.userZoom}' />":4;

            var map;
            var sessionid = "<c:out value='${pageContext.session.id}' />";
            var buildLabel = "<%=phm.getBuildLabel()%>";
            if(buildLabel == "null"){buildLabel="";}
        </script>
    </head>
    <body>
