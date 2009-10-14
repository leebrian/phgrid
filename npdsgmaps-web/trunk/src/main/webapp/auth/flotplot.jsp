 <%@ page contentType="text/html; 
        charset=utf-8"
        import="org.apache.log4j.Logger,
        gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean,
        gov.cdc.ncphi.phgrid.polygon.jsphelper.C2FlotArrayJSPBean,
        java.util.Collection,
        gov.cdc.ncphi.phgrid.polygon.gmaps.GmapPolygon,
        java.util.Iterator"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
  <jsp:useBean id="gjsp" scope="session" class="gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean" /> 
<jsp:useBean id="c2f" scope="application" class="gov.cdc.ncphi.phgrid.polygon.jsphelper.C2FlotArrayJSPBean" />

<%
	response.setHeader("Pragma","no-cache");
	String regionName = request.getParameter("regionName");
	Collection<GmapPolygon> col = gjsp.getPolygonCollection();
	Iterator<GmapPolygon> iter = col.iterator();
	boolean found  = false;
	GmapPolygon polly = null;
	String arrayString = null;
	while(iter.hasNext() && !found)
	{
		polly = iter.next();
		if (polly.getRegionName().equals(regionName))
		{
			found = true;			
		}
	}
	if(found)
	{
		arrayString  = c2f.getArrayString(polly);
	}
        String passInKey = request.getParameter("passInKey");
	if (passInKey == null) 
	{
		passInKey = (String) request.getAttribute("passInKey");
		request.setAttribute("passInKey", null);
	}
			
	if (passInKey != null && passInKey.equals(session.getId()))
	{ %>
<html>
 <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Quicksilver Flot Plot</title>
    <link href="layout.css" rel="stylesheet" type="text/css"></link>
    <!--[if IE]><script language="javascript" type="text/javascript" src="flot/excanvas.pack.js"></script><![endif]-->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script language="javascript" type="text/javascript" src="flot/jquery.flot.js"></script>

  </head>

    <body>
	<table> <tr> <td>
    <div id="placeholder" style="width:340px;height:160px;"></div> </td> 
    
    </tr>
    <tr><td>   
    <table><tr><td>
    <p id="overviewLegend" style="margin-right:3px;width:150px "></p> </td> <td>
    <div id="overview" style="width:180px;height:50px"></div></td></tr>
    </table>
    </td> 
    </tr>
    </table>
<script id="source" language="javascript" type="text/javascript">

$(function () {

	<%if (found)
	{%> <%=arrayString%> <%
		
	}
	else
	{%>
	var d1 = [[1255564800000, 50]];
    var d2 = [[1255392000000, 5],
              [1255478400000, 10],
              [1255564800000, 50], 
              [1255651200000, 6],
              [1255737600000, 3],
              [1255824000000, 12]
               ];

 	//this line is the averages
    var d3 = [[1255392000000, 5],
              [1255478400000, 5],
              [1255564800000, 5], 
              [1255651200000, 5],
              [1255737600000, 6],
              [1255824000000, 6]
               ];
    <%} %>

    var options = {
            xaxis: { mode: "time" },
   			selection: { mode: "x" },
   			grid: { hoverable: true},
   			legend: { show: true, container: $("#overviewLegend") }  			
    };
    
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
    
});
</script>

 </body>
</html>
<%} else
{%> Error CSRF <%}%>