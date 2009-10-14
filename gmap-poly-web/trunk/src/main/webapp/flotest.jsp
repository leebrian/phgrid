    <%@ page contentType="text/html; 
        charset=utf-8"
        import="java.lang.Integer,
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
java.util.ArrayList" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
 <head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <title>Flot Examples</title>
    <link href="layout.css" rel="stylesheet" type="text/css"></link>
    <!--[if IE]><script language="javascript" type="text/javascript" src="flot/excanvas.pack.js"></script><![endif]-->
    <script src="http://code.jquery.com/jquery-latest.js"></script>
    <script language="javascript" type="text/javascript" src="flot/jquery.flot.js"></script>

  </head>
    <body>
    <%SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy/zzz");
    String dateString = "10/13/2009/gmt";
    Date date = df.parse(dateString);
    long millis = date.getTime();
    dateString = "10/14/2009/gmt";
  date = df.parse(dateString);
    
    long millis2 = date.getTime();
    %>
    <h1>Flot Examples</h1>

    <div id="placeholder" style="width:600px;height:300px;"></div>

    <p>This is the Flot Test.  I am trying to get two arrays, one of values, one of averages
    displaying with a zoom feature, hovering tooltip, legend, and finally, 
    mark the one big obvious outliers as a different color.  </p>
    <p>  Millis for Oct 13 is <%=millis %> vs 1255392000000 </p>
    <p> Millis for Oct 14 is <%=millis2 %> vs 1255478400000</p>
    
    <div id="overview" style="margin-left:50px;margin-top:20px;width:400px;height:50px"></div>

<script id="source" language="javascript" type="text/javascript">
$(function () {

	/*var d1 = [[1255564800000, 50]];
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
               ]; */

               d1 = [ [ 1255996800000, 8],[ 1256083200000, 9]];
               d2 = [ [ 1255478400000, 4],[ 1255564800000, 4],[ 1255651200000, 5],[ 1255737600000, 5],[ 1255824000000, 6],[ 1255910400000, 7],[ 1255996800000, 8],[ 1256083200000, 9]];
               d3 = [ [ 1255478400000, 3.93],[ 1255564800000, 4.07],[ 1255651200000, 4],[ 1255737600000, 4.07],[ 1255824000000, 4.03],[ 1255910400000, 4.14],[ 1255996800000, 4.14],[ 1256083200000, 4.31]];
          

    var options = {
            xaxis: { mode: "time" },
   			selection: { mode: "x" },
   			grid: { hoverable: true}
    };
    
    $.plot($("#placeholder"), [ 
                               {
            data: d1,
            points: { show: true }
        },        {
            data: d2,
            lines:  { show: true }
        },         {
            data: d3,
            lines:  { show: true }
        } ], options);


    var plot = $.plot($("#placeholder"), [ 
                                          {
                                              data: d1,
                                              points: { show: true },
                                          	  label: "Outlier"
                                          },        {
                                              data: d2,
                                              lines: { show: true },
                                              label: "Case Count"
                                          },         {
                                              data: d3,
                                              lines: { show: true },
                                              label: "Average"
                                          } ], options);

    var overview = $.plot($("#overview"), [{
        data: d1,
        points: { show: true }
    },        {
        data: d2,
        lines:  { show: true }
    },         {
        data: d3,
        lines:  { show: true }
    } ], {
        lines: { show: true, lineWidth: 1 },
        shadowSize: 0,
        xaxis: { ticks: [], mode: "time" },
        yaxis: { ticks: [], min: 0, max: 55},
        selection: { mode: "x" }
    });

 // now connect the two
    
    $("#placeholder").bind("plotselected", function (event, ranges) {
        // do the zooming
        plot = $.plot($("#placeholder"), [ 
                                          {
                                              data: d1,
                                              points: { show: true },
                                      	  label: "Outlier"
                                          },        {
                                              data: d2,
                                              lines:  { show: true },
                                          	  label: "Case Count"
                                          },         {
                                              data: d3,
                                              lines:  { show: true },
                                          	  label: "Average"
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