<%@ page contentType="text/html; charset=utf-8"
         import="org.apache.log4j.Logger,
         java.lang.Integer,
         java.lang.StringBuffer,
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
<%@ include file="../_includes/taglibs.jsp" %>
<%
            String startDateJSP = StringEscapeUtils.escapeHtml(request.getParameter("startdate"));
            if (startDateJSP == null) {
                startDateJSP = "";
            }
            String endDateJSP = StringEscapeUtils.escapeHtml(request.getParameter("enddate"));
            if (endDateJSP == null) {
                endDateJSP = "";
            }
            String displayTypeJSP = StringEscapeUtils.escapeHtml(request.getParameter("displayType"));
            if (displayTypeJSP == null) {
                displayTypeJSP = "";
            }
            String ClinicalEffectJSP = StringEscapeUtils.escapeHtml(request.getParameter("ClinicalEffect"));
            if (ClinicalEffectJSP == null) {
                ClinicalEffectJSP = "";
            }
            String latestStateIDJSP = StringEscapeUtils.escapeHtml(request.getParameter("latestStateID"));
            if (latestStateIDJSP == null) {
                latestStateIDJSP = "";
            }
            String latestZip3IDJSP = StringEscapeUtils.escapeHtml(request.getParameter("latestZip3ID"));
            if (latestZip3IDJSP == null) {
                latestZip3IDJSP = "";
            }
            String latestZip5IDJSP = StringEscapeUtils.escapeHtml(request.getParameter("latestZip5ID"));
            if (latestZip5IDJSP == null) {
                latestZip5IDJSP = "";
            }
            String ClassifierJSP = StringEscapeUtils.escapeHtml(request.getParameter("Classifier"));
            if (ClassifierJSP == null) {
                ClassifierJSP = "";
            }
            /* * */
            StringBuffer serverKeys = new StringBuffer();
            String[] requestSK = request.getParameterValues("serverKeys");
            int count = requestSK.length;
            if (count > 0) {
                serverKeys.append("\"" + StringEscapeUtils.escapeHtml(requestSK[0]) + "\"");

                for (int i = 1; i < requestSK.length; i++) {
                    serverKeys.append(",");
                    serverKeys.append("\"" + StringEscapeUtils.escapeHtml(requestSK[i]) + "\"");
                }
            }

            String agesJSP = StringEscapeUtils.escapeHtml(request.getParameter("age"));
            if (agesJSP == null) {
                agesJSP = "";
            }
            String zipJSP = StringEscapeUtils.escapeHtml(request.getParameter("zip"));
            if (zipJSP == null) {
                zipJSP = "";
            }
            String serviceAreasJSP = StringEscapeUtils.escapeHtml(request.getParameter("serviceAreas"));
            if (serviceAreasJSP == null) {
                serviceAreasJSP = "";
            }
            String cnt = StringEscapeUtils.escapeHtml(request.getParameter("count"));
            if (cnt == null) {
                cnt = "";
            }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Gridviewer</title>
        <link href="layout.css" rel="stylesheet" type="text/css" />
        <!--[if IE]><script language="javascript" type="text/javascript" src="flot/excanvas.pack.js"></script><![endif]-->
        <script src="scripts/jquery-1.3.2.min.js" type="text/javascript"></script>
        <script src="scripts/date.js" type="text/javascript"></script>
        <script type="text/javascript" src="flot/jquery.flot.js"></script>
        <script type='text/javascript' src='/gridviewer/dwr/interface/regionDao.js'></script>
        <script type='text/javascript' src='/gridviewer/dwr/interface/observationDao.js'></script>
        <script type='text/javascript' src='/gridviewer/dwr/engine.js'></script>
        <script type='text/javascript' src='scripts/jquery.getUrlParam.js'></script>

        <script type="text/javascript">
            var startDateOriginal = "<%=startDateJSP%>"?Date.parse("<%=startDateJSP%>"):"";
            var startDate = "<%=startDateJSP%>"?Date.parse("<%=startDateJSP%>").add({  months: -1 }):(-1).months().fromNow();
            var endDate = "<%=endDateJSP%>"?Date.parse("<%=endDateJSP%>"):Date.today();
            var displayType = "<%=displayTypeJSP%>"?"<%=displayTypeJSP%>":"0";
            var indicatorName = "<%=ClinicalEffectJSP%>"?"<%=ClinicalEffectJSP%>":"0";
            var latestStateID = "<%=latestStateIDJSP%>"?"<%=latestStateIDJSP%>":"ALL";
            var latestZip3ID = "<%=latestZip3IDJSP%>";
            var latestZip5ID = "<%=latestZip5IDJSP%>";
            var classifier = "<%=ClassifierJSP%>"?"<%=ClassifierJSP%>":"BioSense";
            var age = "<c:out value='${param.ages}' />"?"<c:out value='${param.ages}' />":"";
            var serviceAreas = "<c:out value='${param.serviceAreas}' />";
            var serverKeys=[];
                <c:choose>
                <c:when test="${not empty paramValues.serverKeys}">
                <c:forEach items="${paramValues.serverKeys}" var="serverKey">
                serverKeys.push("<c:out value='${serverKey}' />");
                </c:forEach>
                </c:when>
                <c:otherwise></c:otherwise>
                </c:choose>

        
                var zip = "<c:out value='${param.zip}' />";
            var sk = [<%=serverKeys.toString()%>];//$(document).getUrlParam("serverKeys");
            var serverList = [];

            //get sub-datasources
            if(sk != null) {
                if(sk.toString().indexOf(',')>0) {
                    $.each(sk, function(index,value){
                        var server = new Object();
                        server.dataSources = [];
                        server.serverName = value;
                        var ds = $(document).getUrlParam("chk"+value);
                        if(ds != null){
                            //loop thru datasource & add to facilities
                            $.each(ds, function(dsIndex, dsValue) {
                                //var facilities = new Object();
                                //facilities.facilityClassifier = classifier;
                                //facilities.facilityName = dsValue;
                                server.dataSources.push(dsValue);
                            });
                        }
                        serverList.push(server);
                    });
                } else {
                    var server = new Object();
                    server.dataSources = [];
                    server.serverName = sk[0];
                    var ds = $(document).getUrlParam("chk"+sk[0]);
                    if(ds != null){
                        //loop thru datasource & add to facilities
                        $.each(ds, function(dsIndex, dsValue) {
                            //var facilities = new Object();
                            //facilities.facilityClassifier = classifier;
                            //facilities.facilityName = dsValue;
                            server.dataSources.push(dsValue);
                        });
                    }
                    serverList.push(server);
                }
            }
        </script>
    </head>
    <body>
        <table align="center" width="95%"><tr><td width="100%">
                    <h3>Region: <%=zipJSP%></h3>
                    <p>Count: <%=cnt%></p>
                    <div align="center">
                        <div id='processing' style='text-align:center;'><br/><br/><img src='images/loadingAnimation.gif' border='0' /></div>
                        <div id="placeholder" style="width:340px;height:160px;display:none;"></div>
                    </div>
                </td>
            </tr>
            <tr><td>
                    <table><tr><td>
                                <p id="overviewLegend" style="margin-right:3px;width:150px;display:none;"></p> </td> <td>
                                <div id="overview" style="width:180px;height:50px;display:none;"></div></td></tr>
                    </table>
                </td>
            </tr>
        </table>
        <script id="source" language="javascript" type="text/javascript">
            $(function () {
                getPlotData();
            });
            var options = {
                xaxis: { mode: "time" },
                selection: { mode: "x" },
                grid: { hoverable: true},
                legend: { show: true, container: $("#overviewLegend") }
            };
            function populateChart(d1, d2, d3) {
                $.plot($("#placeholder"), [
                    {
                        data: d2,
                        lines:  { show: true }
                    },         {
                        data: d3,
                        lines:  { show: true }
                    },         {
                        data: d1,
                        points: { show: true }
                    } ], options);
                var plot = $.plot($("#placeholder"), [
                    {
                        data: d2,
                        lines: { show: true },
                        label: "Case Count"
                    },         {
                        data: d3,
                        lines: { show: true },
                        label: "Average"
                    },		 {
                        data: d1,
                        points: { show: true },
                        label: "Outlier"
                    }], options);

                var overview = $.plot($("#overview"), [
                    {
                        data: d2,
                        lines:  { show: true }
                    },		{
                        data: d3,
                        lines:  { show: true }
                    },{
                        data: d1,
                        points: { show: true }
                    }                  ], {

                    shadowSize: 0,
                    xaxis: { ticks: [], mode: "time" },
                    yaxis: { ticks: []},
                    selection: { mode: "x" }
                });

                // now connect the two

                $("#placeholder").bind("plotselected", function (event, ranges) {
                    // do the zooming
                    plot = $.plot($("#placeholder"), [
                        {
                            data: d2,
                            lines:  { show: true },
                            label: "Case Count"
                        },         {
                            data: d3,
                            lines:  { show: true },
                            label: "Average"
                        },
                        {
                            data: d1,
                            points: { show: true },
                            label: "Outlier"
                        } ],
                    $.extend(true, {}, options, {
                        xaxis: { min: ranges.xaxis.from, max: ranges.xaxis.to }
                    }));

                    // don't fire event on the overview to prevent eternal loop
                    overview.setSelection(ranges, true);
                });

                $("#overview").bind("plotselected", function (event, ranges) {
                    plot.setSelection(ranges);
                });

                function showTooltip(x, y, contents) {
                    $('<div id="tooltip">' + contents + '</div>').css( {
                        position: 'absolute',
                        display: 'none',
                        top: y + 5,
                        left: x + 5,
                        border: '1px solid #fdd',
                        padding: '2px',
                        'background-color': '#fee',
                        opacity: 0.80
                    }).appendTo("body").fadeIn(200);
                }

                var previousPoint = null;
                $("#placeholder").bind("plothover", function (event, pos, item) {
                    $("#x").text(pos.x.toFixed(2));
                    $("#y").text(pos.y.toFixed(2));


                    if (item) {
                        if (previousPoint != item.datapoint) {
                            previousPoint = item.datapoint;

                            $("#tooltip").remove();
                            var x = item.datapoint[0].toFixed(2),
                            y = item.datapoint[1].toFixed(2);
                            var parsx = parseInt(x)
                            var xd = new Date(parsx);
                            var xm = xd.getUTCMonth();
                            var xday = xd.getUTCDate();
                            var xYr = xd.getUTCFullYear();
                            xm++;
                            showTooltip(item.pageX, item.pageY,
                            item.series.label + " for " + xm + "/" + xday + "/" + xYr + " = " + y);
                        }

                    }
                });
            }


            function getPlotData() {
                var regionType = "zip3";
                if(zip != null && zip.length==2) {
                    regionType = "state";
                }
                console.log(startDate);
                observationDao.getC2FlotArray(zip, regionType, startDate, endDate, indicatorName, classifier, serverList, age, serviceAreas,
                function(data){

                    var d1 = [[1255564800000, 50]];
                    var d2 = [[1255392000000, 5],
                        [1255478400000, 10],
                        [1255564800000, 50],
                        [1255651200000, 6],
                        [1255737600000, 3],
                        [1255824000000, 12]];
                    var d3 = [[1255392000000, 5],
                        [1255478400000, 5],
                        [1255564800000, 5],
                        [1255651200000, 5],
                        [1255737600000, 6],
                        [1255824000000, 6]];

                    if(data != undefined && data != null) {
                        var d1=[];
                        $.each(data.outlierArray.slice(), function(index, value){
                            d1.push([value.date.getTime(),value.count]);
                        });
                        var d2=[];
                        $.each(data.valueArray.slice(), function(index, value){
                            if(value.date>=startDateOriginal) {
                                //console.log("VALUE DATES: "+startDate.getMonth()+"-"+value.date.getMonth()+" ");
                                d2.push([value.date.getTime(),value.count]);
                            }
                        });
                        var d3=[];
                        $.each(data.averageArray.slice(), function(index, value){
                            d3.push([value.date.getTime(),value.count]);
                            // console.log("AVG DATES: "+startDate.getMonth()+"-"+value.date.getMonth()+" ");
                        });

                    }

                    //$("#processing", parent.document.body).fadeOut(function(){
                    $("#processing").fadeOut(function(){
                        $("#placeholder, #overview, #overviewLegend").fadeIn();
                    });
                    populateChart(d1, d2, d3);
                });
            }
        </script>

        <%@ include file="_includes/footer.jsp" %>
