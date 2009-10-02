<%@ page contentType="text/html; charset=utf-8"
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
         org.apache.commons.lang.StringEscapeUtils,
         java.util.ArrayList" %>
<%!    private static final Logger logger = Logger.getLogger("gov.cdc.ncphi.polygon.gmaps.gmap-pane_jsp");
    private static final String stateType = "state";
    private static final String zip3type = "zip3";
    private static final String zip5type = "zip5";
    private Properties keyProp;
    private String mapString = "";
    private static final String PROPERTIES_FILE = "/gridviewer.properties";

    public void getNPDSStatePolygons(GoogleJSPBean gjsp, RegionSelectionBean rsb, GridViewerJSPHelper gvjh,
            Calendar startDate, Calendar endDate, String classifiers, String indicators, Set<String> sendServerSet,
            boolean addQuery) {

        gvjh.getNPDSPolygons(gjsp, rsb, startDate, endDate, classifiers, indicators, sendServerSet, "state", "USA", addQuery);
    }

    private void getNPDSZip3Polygons(GoogleJSPBean gjsp, RegionSelectionBean rsb, GridViewerJSPHelper gvjh, String state,
            Calendar startDate, Calendar endDate, String classifiers, String indicators, Set<String> sendServerSet,
            boolean addQuery) {

        gvjh.getNPDSPolygons(gjsp, rsb, startDate, endDate, classifiers, indicators, sendServerSet, "zip3", state, addQuery);

    }

    private void getNPDSZip5Polygons(GoogleJSPBean gjsp, RegionSelectionBean rsb, GridViewerJSPHelper gvjh, String zip3,
            Calendar startDate, Calendar endDate, String classifiers, String indicators, Set<String> sendServerSet,
            boolean addQuery) {
        {

            gvjh.getNPDSPolygons(gjsp, rsb, startDate, endDate, classifiers, indicators, sendServerSet, "zip5", zip3, addQuery);
        }
    }
%>
<jsp:useBean id="gvjh" scope="session" class="gov.cdc.ncphi.phgrid.gridviewer.jsphelper.GridViewerJSPHelper" />
<jsp:useBean id="gjsp" scope="session" class="gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean" />
<jsp:useBean id="rsb" scope="application" class="gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean" />
<jsp:useBean id="ssjh" scope="session" class="gov.cdc.ncphi.phgrid.gridviewer.jsphelper.ServerSelectorJSPHelper" />

<%
            Date startCSP = new Date();
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
            String loadClassifier = request.getParameter("Classifier");
            String sendClinKey = "";
            String sendClassifier = "";
            boolean addQuery = false;
            String addQueryStr = StringEscapeUtils.escapeHtml(request.getParameter("addQuery"));
            if (addQueryStr != null && addQueryStr.equals("true")) {
                addQuery = true;
            }
            int base = 0;
            int greenYellow = 1000;
            int yellowRed = 2000;
            String userZoom = StringEscapeUtils.escapeHtml(request.getParameter("userZoom"));
            String displayTypeString = request.getParameter("displayType");
            int displayType = 0;
            String mostRecentRegionName = request.getParameter("mostRecentRegionName");
            String currentRegionName;
            Set<String> sendServerSet = new HashSet<String>();
            String[] sendServerKeys;
            if (mostRecentRegionName != null && mostRecentRegionName.length() > 5) {
                mostRecentRegionName = null;
            }
            if (userZoom != null && userZoom.length() > 2) {
                userZoom = null;
            }
            if (loadClinKey != null && loadClinKey.length() < 25 && !loadClinKey.equals("ALL") && !loadClinKey.equals("")) {
                sendClinKey = StringEscapeUtils.escapeHtml(loadClinKey);
            }


            if (loadClassifier != null && loadClassifier.length() < 25 && !loadClassifier.equals("")) {
                sendClassifier = StringEscapeUtils.escapeHtml(loadClassifier);
            }
            Calendar today = Calendar.getInstance();



            if (startDateString == null || startDateString.equals("") || startDateString.length() != 10) {
                today.set(Calendar.DAY_OF_WEEK, 1);
                startDateString = (today.get(Calendar.MONTH) + 1) + "/" + today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.YEAR);
            }
            Date startDt = df.parse(StringEscapeUtils.escapeHtml(startDateString));
            if (startDt != null) {
                startDate.setTime(startDt);
            }

            if (endDateString == null || endDateString.equals("") || endDateString.length() != 10) {
                today.set(Calendar.DAY_OF_WEEK, 7);
                endDateString = (today.get(Calendar.MONTH) + 1) + "/" + today.get(Calendar.DAY_OF_MONTH) + "/" + today.get(Calendar.YEAR);

            }
            Date endDt = df.parse(StringEscapeUtils.escapeHtml(endDateString));
            if (endDt != null) {
                endDate.setTime(endDt);
            }
            if (startDate.compareTo(endDate) > 0) {
                endDate.setTime(startDate.getTime());
                endDateString = startDateString;
            }

            RegionPolygonFetcher rpf = RegionPolygonFetcher.getFetcher();

            if (request.getParameterValues("serverKeys") != null && request.getParameterValues("serverKeys").length > 0) {
                sendServerKeys = request.getParameterValues("serverKeys");
                for (int k = 0; k < sendServerKeys.length; k++) {
                    sendServerSet.add(StringEscapeUtils.escapeHtml(sendServerKeys[k]));
                }
            }
            if (request.getParameter("latestStateID") != null && request.getParameter("latestStateID").length() == 2) {
                latestStateID = StringEscapeUtils.escapeHtml((String) request.getParameter("latestStateID"));
            }


            if (request.getParameter("latestZip3ID") != null && request.getParameter("latestZip3ID").length() == 3) {
                latestZip3ID = StringEscapeUtils.escapeHtml((String) request.getParameter("latestZip3ID"));
            }


            if (request.getParameter("latestZip5ID") != null && request.getParameter("latestZip5ID").length() == 5) {
                latestZip5ID = StringEscapeUtils.escapeHtml((String) request.getParameter("latestZip5ID"));
            }


            if (request.getParameter("mostRecentStateID") != null && request.getParameter("mostRecentStateID").length() == 2) {
                mostRecentStateID = StringEscapeUtils.escapeHtml((String) request.getParameter("mostRecentStateID"));
            }


            if (request.getParameter("mostRecentZip3ID") != null && request.getParameter("mostRecentZip3ID").length() == 3) {
                mostRecentZip3ID = StringEscapeUtils.escapeHtml((String) request.getParameter("mostRecentZip3ID"));
            }



            if (displayTypeString != null) {
                displayType = Integer.parseInt(StringEscapeUtils.escapeHtml(displayTypeString));
            }


            if (latestStateID.equals("ALL")) {
                getNPDSStatePolygons(gjsp, rsb, gvjh, startDate, endDate, loadClassifier, loadClinKey, sendServerSet, addQuery);
                latestZip3ID = "ALL";
                latestZip5ID = "ALL";
                currentRegionName = "USA";
                gjsp.setFocus("country", "USA");
            } else if (latestZip3ID.equals("ALL") || !latestStateID.equals(mostRecentStateID)) {
                mostRecentStateID = latestStateID;
                getNPDSZip3Polygons(gjsp, rsb, gvjh, latestStateID, startDate, endDate, loadClassifier, loadClinKey, sendServerSet, addQuery);
                latestZip3ID = "ALL";
                latestZip5ID = "ALL";
                currentRegionName = latestStateID;
                gjsp.setFocus("state", latestStateID);
            } else if (latestZip5ID.equals("ALL") || !latestZip3ID.equals(mostRecentZip3ID)) {
                mostRecentZip3ID = latestZip3ID;
                getNPDSZip5Polygons(gjsp, rsb, gvjh, latestZip3ID, startDate, endDate, loadClassifier, loadClinKey, sendServerSet, addQuery);
                gjsp.setFocus("zip3", latestZip3ID);
                currentRegionName = latestZip3ID;
                latestZip5ID = "ALL";
            } else {
                getNPDSZip5Polygons(gjsp, rsb, gvjh, latestZip3ID, startDate, endDate, loadClassifier, loadClinKey, sendServerSet, addQuery);
                Collection<GmapPolygon> pollist = rpf.getRegionPolygon(latestZip5ID, "zip5");
                currentRegionName = latestZip5ID;
                gjsp.setFocus("zip5", latestZip5ID);
            }
            try {
                if (gjsp.adjustLegend(request.getParameter("base"), request.getParameter("greenYellow"), request.getParameter("yellowRed"))) {
                    base = Integer.parseInt(request.getParameter("base"));
                    greenYellow = Integer.parseInt(request.getParameter("greenYellow"));
                    yellowRed = Integer.parseInt(request.getParameter("yellowRed"));
                } else if (request.getParameter("mostRecentbase") != null && request.getParameter("mostRecentgreenYellow") != null && request.getParameter("mostRecentyellowRed") != null) {

                    base = Integer.parseInt(request.getParameter("mostRecentbase"));
                    greenYellow = Integer.parseInt(request.getParameter("mostRecentgreenYellow"));
                    yellowRed = Integer.parseInt(request.getParameter("mostRecentyellowRed"));


                }
            } catch (Exception e) {
                issueString = "There was an issue converting the legend values";
            }
            if (currentRegionName != null && mostRecentRegionName != null && currentRegionName.equals(mostRecentRegionName)) {
                gjsp.setZoom(userZoom);
            } else {
                gjsp.setZoom(null);
            }
%>

<%@ include file="_includes/header.jsp" %>
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
    <%=ssjh.getContainsArray()%>

    <%=ssjh.getConditionContainsArray()%>

    <%=ssjh.getConditionArray()%>

</script>

<H1>Grid Viewer</H1>
<div>
    <div id="map_canvas"><div id="map" style="width:100%;height:500px;border:1px solid #ddd;"></div>
        <div style="margin-top:5px;">
            <fieldset><legend>Console</legend>
                <div id="divConsole" style="padding:5px;"></div></fieldset>
        </div>
    </div>
    <div id="nav">
        <%@ include file="_includes/form.jsp" %>
        <div class="demo">
            <p id="status">&nbsp;</p>
            <p>
                <label for="amount" style="display:none;"></label>
                <input type="text" id="amount" style="border:0;color:#f6931f;font-weight:bold;display:none;" />
            </p>
            <!---->
            <br/>
            <div id="slider" style="display:none;"></div>
            <div id="queries"></div>
            <br/><br/>

            <div style="width:400px;">
                <br/>
                    <div id="divAllDataRecords" style="display:none"><a href="javascript://" onclick="createDataTableAllQueries();">Display All Data Results</a></div>
<br/>
                <fieldset id="pinLegend">
                    <legend>Customizeable Legend</legend>
                    <div id="customLegend">

                        <br/>
                        <img src="images/star_n.png" border="0" width="12" />
                        &lt;= <input type="text" id="lowBound" name="lowBound" value="0" size="4" />
                        &lt; <img src="images/star_0.png" border="0" width="12" /> &lt;=
                        <input type="text" id="midBound" name="midBound" value="1000" size="4"/>
                        &lt; <img src="images/star_1000.png" border="0" width="12" /> &lt;=
                        <input type="text" id="upBound" name="upBound" value="2000" size="4"/>
                        &lt; <img src="images/star_2000.png" width="12" border="0"/>
                    </div>
                </fieldset>
            </div>

        </div>
    </div>
</div>
<br class="c" />
<br class="c" />

<div id="dTable" style="display:none">
    <table class="tablesorter" cellspacing="1">
        <thead>
            <tr>
                <th>Region</th>
                <th>Region Type</th>
                <th>Location</th>
                <th class="{sorter: 'date'}">Date</th>
                <th>Count</th>
            </tr>
        </thead>
        <tbody>
        </tbody>
    </table>
</div>

<iframe style="width:1px;height:1px;display:none;" src="blank.html" id="iDownload" />

<script type="text/javascript">
    <%=gjsp.getMap(session.getId(), displayType)%>
</script>
<%@ include file="_includes/footer.jsp" %>
