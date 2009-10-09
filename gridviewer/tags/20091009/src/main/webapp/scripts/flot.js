var previousPoint = null;
var d1 = [[1255564800000, 50]];
var d2 = [[1255392000000, 5],
[1255478400000, 10],
[1255564800000, 50],
[1255651200000, 6],
[1255737600000, 3],
[1255824000000, 12]
];
var d3 = [[1255392000000, 5],
[1255478400000, 5],
[1255564800000, 5],
[1255651200000, 5],
[1255737600000, 6],
[1255824000000, 6]
];
var options = {
    xaxis: {
        mode: "time"
    },
    selection: {
        mode: "x"
    },
    grid: {
        hoverable: true
    },
    legend: {
        show: true,
        container: $("#overviewLegend")
    }
};

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
function getFlotData(marker) {
    return getPlotData(marker, marker.query);
}
function getPlotData(marker, query) {
    var region = query.region;
    var regionType = query.regionType;
    var startDate = query.startDate;
    var endDate = query.endDate;
    var indicatorName = query.indicator;
    var classifier = query.classifier;
    var serverList = query.serverList;
    var age = query.age;
    var serviceAreas = query.serviceAreas;

    /**/
    observationDao.getC2FlotArray(region, regionType, startDate, endDate, indicatorName, classifier, serverList, age, serviceAreas,
        function(data){
        // var d1 = [];
        // var d2 = [];
        // var d3 = [];
        // d1 = data.outlierArrayString.replace(/"/g,'').replace(/;/g,'');
        // d2 = data.valueArrayString.replace(/"/g,'').replace(/;/g,'');
        // d3 = data.averageArrayString.replace(/"/g,'').replace(/;/g,'');

        //call populate chart

        });
    return populateChart(marker);
}

function populateChart(marker){

var html = "<table><tr><td><div id=\"placeholder\" style=\"width:340px;height:160px;\"></div></td></tr><tr><td><table><tr><td><p id=\"overviewLegend\" style=\"margin-right:3px;width:150px\"></p></td><td><div id=\"overview\" style=\"width:180px;height:50px\"></div></td></tr></table></td></tr></table>";

var placeholder = document.createElement("div");
placeholder.style.width = "340px";
placeholder.style.height = "160px";

    $.plot($("#placeholder"), [
    {
        data: d2,
        lines:  {
            show: true
        }
    },         {
        data: d3,
        lines:  {
            show: true
        }
    },         {
        data: d1,
        points: {
            show: true
        }
    } ], options);


    var plot = $.plot($("#placeholder"), [
    {
        data: d2,
        lines: {
            show: true
        },
        label: "Case Count"
    },         {
        data: d3,
        lines: {
            show: true
        },
        label: "Average"
    },		 {
        data: d1,
        points: {
            show: true
        },
        label: "Outlier"
    }], options);

    var overview = $.plot($("#overview"), [
    {
        data: d2,
        lines:  {
            show: true
        }
    },		{
        data: d3,
        lines:  { 
            show: true
        }
    },{
        data: d1,
        points: { 
            show: true
        }
    }                  ], {

        shadowSize: 0,
        xaxis: {
            ticks: [],
            mode: "time"
        },
        yaxis: { 
            ticks: []
        },
        selection: { 
            mode: "x"
        }
    });

    //************
    //bindin'
    /*  */
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
    $("#placeholder").bind("plotselected", function (event, ranges) {
        // do the zooming
        plot = $.plot($("#placeholder"), [
        {
            data: d2,
            lines:  {
                show: true
            },
            label: "Case Count"
        },         {
            data: d3,
            lines:  {
                show: true
            },
            label: "Average"
        },
        {
            data: d1,
            points: {
                show: true
            },
            label: "Outlier"
        } ]
        ,
        $.extend(true, {}, options, {
            xaxis: {
                min: ranges.xaxis.from,
                max: ranges.xaxis.to
            }
        })
        );
        // don't fire event on the overview to prevent eternal loop
        overview.setSelection(ranges, true);
    });

    $("#overview").bind("plotselected", function (event, ranges) {
        plot.setSelection(ranges);
    });

    return $("#flot").html();

}




