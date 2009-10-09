var map;
var mgr;
var allmarkers = [];
var classifiers = [];
var indicators = [];
var queries = [];
var callStartTime;
var callEndTime;
var showSparklines=true;

function getMarkers() {
    var state = $("#latestStateID").selectedValues()[0];
    var regionType;

    if(state.toLowerCase() == "all") {
        regionType = "state";
        state = "USA";
    } else {
        regionType = "zip3";
    }

    regionDao.getAjaxRegions(regionType, state, function(regions) {
        if (regions != null && typeof regions == 'object') {
            getObservations(regionType, state, regions);
        }
    });
}
function clearMap() {
    if(mgr != undefined && mgr != null) {
        mgr.clearMarkers();
        $("#queries").html("");
        $("#divConsole").html("");
        allmarkers=[];
        queries=[];
    }
}

function getObservations(regionType, region, regions) {

    var startDate = Date.parse($("#startdate").val());
    var endDate = Date.parse($("#enddate").val());
    var indicatorName = $("#ClinicalEffect").selectedValues()[0]?$("#ClinicalEffect").selectedValues()[0]:"";
    var classifier = $("#Classifier").selectedValues()[0]?$("#Classifier").selectedValues()[0]:"";
    var age = $("#ages").selectedValues()[0]?$("#ages").selectedValues()[0]:"";
    var serviceAreas = $("#serviceAreas").selectedValues()[0]?$("#serviceAreas").selectedValues()[0]:"";

    //loop through all the checkboxes for datasources
    var serverList = [];
    $("#servers input[type='checkbox']:checked").each(function() {
        var server = new Object();
        server.dataSources = [];
        if($(this).attr("name")=="serverKeys") {
            server.serverName = $(this).val();
            $("#divS-"+$(this).val()+" input[type='checkbox']:checked").each(function() {
                var dataSource = $(this).val();
                server.dataSources.push(dataSource);
            });
            serverList.push(server);
        }
    });

    //unique color for resultset
    var _color = random_color('hex');
    allmarkers=[];
    callStartTime = Date.now();
    observationDao.getMultipleRegionalObservations(region, regionType, startDate, endDate, indicatorName, classifier, serverList, age, serviceAreas,
        function(data){
            callEndTime = Date.now();
            var seconds = getSeconds(callEndTime.getTime(),callStartTime.getTime());
            var serviceSeconds = getSeconds(data.serviceCallResponseTime.getTime(), data.serviceCallRequestTime.getTime());
            $("#divConsole").append("Service response execution time: ["+region+"] <span style='font-weight:bold;font-size:1.25em;'>"+serviceSeconds+"</span><br/>");
            $("#divConsole").append("Server response execution time: ["+region+"] <span style='font-weight:bold;font-size:1.25em;'>"+seconds+"</span><br/>");
            var lowBound = $("#lowBound").val()?$("#lowBound").val():0;
            var midBound = $("#midBound").val()?$("#midBound").val():1000;
            var upBound = $("#upBound").val()?$("#upBound").val():2000;

            var observations = data.regionalObservations;
            $.each(observations, function(index, observation){
                if(observation != undefined) {
                    var count = 0;
                    if(observation.observations != undefined) {
                        for( var o=0;o< observation.observations.length;o++ ) {
                            for(var p=0;p< observation.observations[o].observations.length;p++) {
                                if(observation.observations[o].observations[p].value != null) {
                                    count = count+parseInt(observation.observations[o].observations[p].value);
                                }
                            }
                        }
                    }
                    var title = "Region: " + observation.regionData.regionParent + " " + observation.regionData.regionName + " Count:" + count;
                    var _label = "";
                    var starColor = "";
                    var body = "<h1>" + observation.regionData.regionParent + "</h1><br/><p>Count: "+count+"</p>";
                    if(count <= lowBound){
                        starColor = "#ffffff";
                    } else if(count >lowBound && count <= midBound) {
                        starColor = "#00CC00";
                    } else if(count <= upBound && count > midBound) {
                        starColor = "#FFFF66";
                    } else if(count > upBound) {
                        starColor = "#990000";
                    }

                    var icon = createIcon(_color, _label, starColor);
                    if(observation.regionData.centroid != null){
                        var marker = createMarker(new GLatLng(observation.regionData.centroid.latitude, observation.regionData.centroid.longitude),
                            icon, title, body, observation.regionData.regionName, map, count, {
                                startDate:startDate,
                                endDate:endDate,
                                indicator: indicatorName,
                                classifier: classifier,
                                age:age,
                                region: observation.regionData.regionName,
                                regionParent: region,
                                regionType:regionType,
                                regions:regions,
                                serviceAreas:serviceAreas,
                                serverList:serverList,
                                flotUrl: $("form").serialize()
                            });
                        allmarkers.push({
                            marker:marker,
                            regionType:regionType,
                            region:observation.regionData.regionName,
                            startDatebtn:startDate,
                            endDate:endDate,
                            totalQueryCount:count,
                            observation:observation
                        });
                        mgr.addMarker(marker, 0, 10);
                    }
                }
                if((observations.length-1)==index) {
                    var pageEndTime = Date.now();
                    var seconds = getSeconds(pageEndTime.getTime(),callStartTime.getTime());
                    $("#divConsole").append("Page complete render execution time: <span style='font-weight:bold;font-size:1.25em;'>"+seconds+"</span><br/><br/>");
                    $("#btnAjax").val("Run new query");//.removeAttr("disabled");

                    if(showSparklines) {
                        generateSparklines(0,"");
                    }
                }
            });
        });

    queries.push({
        allmarkers:allmarkers,
        color:_color,
        query:{
            startDate:startDate,
            endDate:endDate,
            indicator: indicatorName,
            classifier: classifier,
            age:age,
            regionParent: region,
            regionType:regionType,
            regions:regions,
            serviceAreas:serviceAreas,
            serverList:serverList
        }
    });
    //updateMarkerManager();
    updateQueries();
}
function generateSparklines(queryArrayIndex, sparkLineType) {
    if(sparkLineType == "-") {
        alert('Please select a chart type.');
        return false;
    }
    var _index = queries.length-1;
    if(queryArrayIndex>0) {
        _index = queryArrayIndex;
    }
    var lineType = "line";
    if(sparkLineType!="") {
        lineType = sparkLineType;
    }
    var markers = queries[_index].allmarkers;

    $.each(markers, function(i, marker){
        var region = markers[i].region;
        var observation = marker.observation;
        var values = [];
        if(observation.observations != null) {
            for( var o=0;o< observation.observations.length;o++ ) {
                for(var p=0;p< observation.observations[o].observations.length;p++) {
                    if(observation.observations[o].observations[p].value != null) {
                        values.push(observation.observations[o].observations[p].value);
                    } else {
                        values.push(0);
                    }
                }
            }
            if(values.length>2){
                $('#dS-'+_index+"-"+region).sparkline(values, {
                    type: lineType,
                    spotColour: '#990000',
                    barColor: 'green'
                } );
            }
            else {
                $('#dS-'+_index+"-"+region).html("&nbsp;");
            }
        }
        else {
            $('#dS-'+_index+"-"+region).html("&nbsp;");
        }
    });
    return true;
}

function updateMarkerManager() {
    if(mgr != null)
        mgr.refresh();
}
function toggleMarkerManager() {
    if(mgr != null) {
        mgr.toggle();
        mgr.isHidden()?$("#btnToggleMgr").html("Show Markers"):$("#btnToggleMgr").html("Hide Markers");
    }
}
function hideMarkersperQuery(_index) {
    $.each(queries[_index].allmarkers, function(i, v){
        this.marker.isHidden()?this.marker.show():this.marker.hide();
    });
}
function updateQueries() {    
    var parent = document.createElement("div");
    var div = document.createElement("div");
    var lbl = document.createElement("label");

    var index = queries.length-1;
    var m = queries[index];

    parent.className = "queryBox";

    div = document.createElement("div");
    div.style.backgroundColor = m.color;
    div.style.width = "32px";
    div.style.height = "28px";
    div.style.styleFloat = "left";
    div.style.cssFloat = "left";
    div.style.styleFloat = "left";
    div.style.cssFloat = "left";
    div.style.borderWidth = "1px";
    div.style.borderColor = "#999999";
    div.style.borderStyle = "solid";
    div.style.cursor = "pointer";
    div.setAttribute("onClick","hideMarkersperQuery("+index+");");
    div.onclick = new Function( "hideMarkersperQuery( " + index + " );" );
    parent.appendChild(div);


    var divState = document.createElement("div");
    divState.style.fontSize = "1.75em";
    divState.style.marginLeft = "4px";
    divState.style.marginTop = "5px";
    divState.style.color = "#fff";
    if(m.query != undefined){
        divState.innerHTML = m.query.regionParent.toString().replace("USA","US");
    }
    div.appendChild(divState);

    //region filter box
    var divBoxes = document.createElement("div");
    divBoxes.style.marginLeft = "36px";
    divBoxes.style.styleFloat = "left";

    var divRegionQuery = document.createElement("div");
    divRegionQuery.className = "regionQuery";
    divRegionQuery.id = "rQ-"+index;
    divRegionQuery.style.display = "none";

    var fsZip3 = document.createElement("div"); //fieldset
    fsZip3.className = "zipQuery";
    fsZip3.id = "fS-"+index;

    var divLink = document.createElement("div");
    divLink.className = "zipDiv";

    var linkShow = document.createElement("a"); //legend
    if(m.query != undefined){
        linkShow.innerHTML = m.query.regionType.toLowerCase().replace("zip3"," Show Zip 3 ")
        .replace("state"," Show States ").replace("zip5"," Show Zip 5 ");
    }
    linkShow.setAttribute("href", "javascript://");
    linkShow.id = "lG-"+index;
    linkShow.className = "zipLink";
    linkShow.onclick = function(){
        $("#rQ"+$(this).attr("id").replace("lG","") +" , "+
            "#lS"+$(this).attr("id").replace("lG","")).toggle();

        $("#rQ"+$(this).attr("id").replace("lG","")).is(':visible')?
        $("#lG"+$(this).attr("id").replace("lG","")).html($("#lG"+$(this).attr("id").replace("lG","")).html().replace("Show","Hide"))
        :
        $("#lG"+$(this).attr("id").replace("lG","")).html($("#lG"+$(this).attr("id").replace("lG","")).html().replace("Hide","Show"));
    };
    divLink.appendChild(linkShow);


    var fsFilters = document.createElement("div");
    //fsFilters.style.display = "none";
    var uTitle = "dF-"+new Date().getTime();

    var linkShowFilters = document.createElement("a");
    linkShowFilters.setAttribute("href","javascript://");
    linkShowFilters.className = "zipLink";
    linkShowFilters.innerHTML = "Show Filters";
    linkShowFilters.setAttribute("onClick","$('#"+uTitle+"').toggle();");
    linkShowFilters.onclick = function(){
        $('#'+uTitle).toggle();
        $('#'+uTitle).is(':visible')?$(this).html("Hide Filters"):$(this).html("Show Filters");
    };

    divLink.appendChild(document.createTextNode(" | "));
    divLink.appendChild(linkShowFilters);
    
    //insert export download link
    divLink.appendChild(document.createTextNode(" | "));
    var linkExport = document.createElement("a");
    linkExport.setAttribute("href","export/?"+$("form").serialize());
    linkExport.setAttribute("target","iDownload");
    linkExport.className = "zipLink";
    linkExport.innerHTML = "Download";
    divLink.appendChild(linkExport);

    //insert export download data
    divLink.appendChild(document.createTextNode(" | "));
    var linkDataTable = document.createElement("a");
    linkDataTable.id = "dT-"+index;
    linkDataTable.setAttribute("href","javascript://");
    linkDataTable.onclick = function(){
        createDataTable($(this).attr("id").replace("dT-",""),true);
    };
    linkDataTable.className = "zipLink thickbox";
    linkDataTable.innerHTML = "Display Data";
    divLink.appendChild(linkDataTable);

    //share
    divLink.appendChild(document.createTextNode(" | "));
    var linkShare = document.createElement("a");
    linkShare.id = "lShare-"+index;
    linkShare.setAttribute("href","javascript://");
    //linkShare.className = "shareLink";
    linkShare.innerHTML = "Share";
    linkShare.setAttribute("onClick","$('#dShare-"+index+"').toggle();");
    linkShare.onclick = function(){
        $('#dShare-'+index).toggle();
    };
    divLink.appendChild(linkShare);


    //insert export download data
    divLink.appendChild(document.createTextNode(" | "));
    var linkEmail = document.createElement("a");
    linkEmail.id = "lEmail-"+index;
    linkEmail.setAttribute("href","");
    linkEmail.className = "shareLink";
    linkEmail.innerHTML = "Email";
    divLink.appendChild(linkEmail);
    var domain = document.location.protocol+"//"+document.location.host.toLowerCase();
    getShortUrl(domain+'/gridviewer/?'+$("form").serialize(), addShareLink, index);
    divLink.appendChild(linkEmail);


    divBoxes.appendChild(divLink);

    
    fsZip3.style.padding = "4px";
    fsZip3.style.marginTop = "-4px";
    
    if(m.query != undefined) {
        $.each(m.query.regions, function(i, v){
            var divCb = document.createElement("div");
            var divSparkline = document.createElement("div");
            var cb = document.createElement("input");
            lbl = document.createElement("label");
            var txt = document.createTextNode(" "+this+" ");

            lbl.setAttribute("for","cb-"+this)
            cb.setAttribute("type", "checkbox");
            cb.setAttribute("onClick","hideZip3(this,"+index+");");
            cb.onclick = new Function( "hideZip3( this, " + index + " );" );
            cb.name = this;
            cb.setAttribute("value",this);
            cb.checked = true;
            cb.setAttribute("checked", "checked");
            cb.setAttribute('defaultChecked', 'defaultChecked');
            
            lbl.appendChild(cb);
            lbl.appendChild(txt);

            if(showSparklines) {
                //insert line type select 
                if(i==0) {
                    var divSelect = document.createElement("div");
                    divSelect.className = "lineTypeSelect";
                    divSelect.id = "lS-"+index;
                    divSelect.style.display = "none";
                    
                    var sel = document.createElement("select");
                    var opt = document.createElement("option");
                    opt.value = "-";
                    opt.appendChild(document.createTextNode("select"));
                    sel.appendChild(opt);
                    opt = document.createElement("option");
                    opt.value = "line";
                    opt.appendChild(document.createTextNode("line"));
                    sel.appendChild(opt);
                    opt = document.createElement("option");
                    opt.value = "bar";
                    opt.appendChild(document.createTextNode("bar"));
                    sel.appendChild(opt);
                    opt = document.createElement("option");
                    opt.value = "box";
                    opt.appendChild(document.createTextNode("box"));
                    sel.appendChild(opt);
                    opt = document.createElement("option");
                    opt.value = "discrete";
                    opt.appendChild(document.createTextNode("discrete"));
                    sel.appendChild(opt);
                    opt = document.createElement("option");
                    opt.value = "pie";
                    opt.appendChild(document.createTextNode("pie"));
                    sel.appendChild(opt);
                    opt = document.createElement("option");
                    opt.value = "tristate";
                    opt.appendChild(document.createTextNode("tristate"));
                    sel.appendChild(opt);
                    opt = document.createElement("option");
                    opt.value = "bullet";
                    opt.appendChild(document.createTextNode("bullet"));
                    sel.appendChild(opt);

                    sel.id = "sel-"+index;
                    sel.onchange = function(){
                        generateSparklines($(this).attr("id").replace("sel-",""),$(this).selectedValues()[0]);
                    }
                    divSelect.appendChild(sel);
                    fsZip3.appendChild(divSelect);
                }
            }
            //insert sparkline div here...
            divSparkline.id = "dS-"+index+"-"+this;
            divSparkline.className = "sparkChart";
            divSparkline.innerHTML = "&nbsp;";
            divCb.appendChild(lbl);
            divCb.className = "sparkZip";

            divRegionQuery.appendChild(divCb);
            divRegionQuery.appendChild(divSparkline);

            var br = document.createElement("br");
            br.className = "c";
            divRegionQuery.appendChild(br);
        });
    }
    fsZip3.appendChild(divRegionQuery);
    divBoxes.appendChild(fsZip3);
    divBoxes.appendChild(document.createElement("br"));

    //fsFilters.appendChild(divLink);
    
    var divFilters = document.createElement("div");
    divFilters.style.display = "none";
    divFilters.setAttribute("id",uTitle);
    if(m.query != undefined){
        var serversHTML="Servers: [";
        $("#servers input[type='checkbox']:checked").each(function() {
            if($(this).attr("name")=="serverKeys") {
                serversHTML +=$(this).val()+" ";
                $("#divS-"+$(this).val()+" input[type='checkbox']:checked").each(function() {
                    serversHTML += $(this).val();
                });
                serversHTML +=" ";
            }
        });
        serversHTML+="]";
        divFilters.innerHTML = "Dates: [" + $("#startdate").val() + " - " + $("#enddate").val()+"]<br/> Age: ["
        +m.query.age.replace("","All")+"]<br/> Disposition: ["+ m.query.serviceAreas.replace("","All")+"]<br/>"
        +"Classifiers: ["+ m.query.classifier +"]<br/> Clinical Effects: ["+ m.query.indicator +"]<br/> "+serversHTML;
    }
    fsFilters.appendChild(divFilters);
    divBoxes.appendChild(fsFilters);

    var divShare = document.createElement("div");
    divShare.style.display = "none";
    divShare.style.padding = "5px";
    divShare.setAttribute("id","dShare-"+index);

    //facebook
    var img = document.createElement("img");
    img.src = "images/link-facebook.gif";
    img.style.border = "0";
    var a = document.createElement("a");
    a.id = "facebook-"+index;
    a.className = "linkShare";
    a.href = "#";
    a.target= "facebook";
    a.onclick = function() {
        onclick=window.open('','facebook','width=642,height=436,left=0,top=0,resizable,scrollbars=yes');
    }
    a.appendChild(img);
    divShare.appendChild(a);
    divShare.appendChild(document.createTextNode(" "));


    //twitter
    img = document.createElement("img");
    img.src = "images/link-twitter.gif";
    img.style.border = "0";
    a = document.createElement("a");
    a.id = "twitter-"+index;
    a.className = "linkShare";
    a.href = "#";
    a.target= "twitter";
    a.onclick = function() {
        onclick=window.open('','twitter','width=642,height=436,left=0,top=0,resizable,scrollbars=yes');
    }
    a.appendChild(img);
    divShare.appendChild(a);
    divShare.appendChild(document.createTextNode(" "));

     //delicious
    img = document.createElement("img");
    img.src = "images/link-delicious.gif";
    img.style.border = "0";
    a = document.createElement("a");
    a.id = "delicious-"+index;
    a.className = "linkShare";
    a.href = "#";
    a.target= "delicious";
    a.onclick = function() {
        onclick=window.open('','delicious','width=642,height=436,left=0,top=0,resizable,scrollbars=yes');
    }
    a.appendChild(img);
    divShare.appendChild(a);
    divShare.appendChild(document.createTextNode(" "));
    
     //digg
    img = document.createElement("img");
    img.src = "images/link-digg.gif";
    img.style.border = "0";
    a = document.createElement("a");
    a.id = "digg-"+index;
    a.className = "linkShare";
    a.href = "#";
    a.target= "digg";
    a.onclick = function() {
        onclick=window.open('','digg','width=642,height=436,left=0,top=0,resizable,scrollbars=yes');
    }
    a.appendChild(img);
    divShare.appendChild(a);
    divShare.appendChild(document.createTextNode(" "));
    
    divBoxes.appendChild(divShare);

    parent.appendChild(divBoxes);
    //end region filter box

    $("#queries").append(parent).append(document.createElement("br"));
}
function addShareLink(url, _ref){
    var mailto = "mailto:?subject=Gridviewer Data&body=Hi,%0A%0AHere is some data from the NCPHI Gridviewer Beta application.%0A%0APlease visit this link: "+url+".";
    $("#lEmail-"+_ref).attr("href",mailto).fadeIn();
    $("a").removeAttr("bitly");

    //share links
    var _title = "NCPHI Gridviewer Link";
    var title = encodeURIComponent(_title);
    var bitlyurl = encodeURIComponent(url);
    $("#facebook-"+_ref).attr("href","http://www.facebook.com/sharer.php?u="+bitlyurl+"&t="+title);
    $("#twitter-"+_ref).attr("href","http://twitter.com/home?status=Interesting data from CDC "+bitlyurl);
    $("#delicious-"+_ref).attr("href","http://del.icio.us/post?v=4&jump=close&url="+bitlyurl+"+&title="+title);
    $("#digg-"+_ref).attr("href","http://digg.com/submit?phase=2&url="+bitlyurl+"&title="+title);

    
}
function getShortUrl(_url, callback, _ref) {
    url = encodeURIComponent(_url);
    $.getJSON(bitly.url(url), function(data){
        callback(data.results[_url].shortUrl, _ref);
    });
}

function createDataTable(_index, _clearTable) {
    var html = "";
    var markers = queries[_index].allmarkers;
    var _parent = queries[_index].query.regionParent?queries[_index].query.regionParent:"";
    var _type = queries[_index].query.regionType?queries[_index].query.regionType:"";
    $.each(markers, function(i, marker){
        //var region = markers[i].region;
        var observation = marker.observation;
        if(observation.observations != null) {
            for( var o=0;o< observation.observations.length;o++ ) {
                for(var p=0;p< observation.observations[o].observations.length;p++) {
                    if(observation.observations[o].observations[p].value != null) {
                        var _d = formatDateforDataTable(observation.observations[o].observations[p].start);
                        html+="<tr><td>"+_parent+"</td><td>"+_type+"</td><td>"
                        +observation.observations[o].observations[p].location+"</td><td>"
                        +_d+"</td><td>"+observation.observations[o].observations[p].value+"</td></tr>";
                    }
                }
            }
        }
    });

    if(_clearTable!=null && _clearTable==true) {
        $("table.tablesorter>tbody").html("");
    }
    
    $("table.tablesorter>tbody").append(html);
    $("table.tablesorter").trigger("update");
    var rows = $("table.tablesorter>tbody>tr").length;
    if(rows>1) {
        var sorting = [[2,1],[0,0]];
        $("table.tablesorter").trigger("sorton",[sorting]);
    } else {
        $("table.tablesorter").hide();
    }
    if(_clearTable==true) {
        //show it....
        tb_show("Data results for "+_parent+" ["+rows+" Records]","#TB_inline?height=400&width=700&inlineId=dTable","");
        return false;
    } else {
        return true;
    }
}
function createDataTableAllQueries(){
    $("table.tablesorter>tbody").html("");

    $.each(queries, function(index, value) {
        createDataTable(index,false);
        if((queries.length-1)==index) {
            var rows = $("table.tablesorter>tbody>tr").length;
            tb_show("Data results for All ["+rows+" Records]","#TB_inline?height=400&width=700&inlineId=dTable","");
            return false;
        }
    });
}
function formatDateforDataTable(date){
    var d  = date.getDate();
    var day = (d < 10) ? '0' + d : d;
    var m = date.getMonth() + 1;
    var month = (m < 10) ? '0' + m : m;
    var yy = date.getYear();
    var year = (yy < 1000) ? yy + 1900 : yy;
    return month + "-" + day + "-" + year;

}
function hideZip3(cb, arrayIndex){
    $(cb).is(':checked')?toggleMarkerfromManager(true, cb, arrayIndex):toggleMarkerfromManager(false, cb, arrayIndex);
}
function toggleMarkerfromManager(showMarker, cb, arrayIndex){
    var region = $(cb).attr("name");

    $.each(queries[arrayIndex].allmarkers, function(_index, _marker){
        if(_marker.region == region)
            showMarker?_marker.marker.show():_marker.marker.hide();
    });
}
function calculateSliderInterval(unit, startDate, endDate) {
    start = Date.parse(startDate);
    end = Date.parse(endDate);
    var minutes = 1000*60;
    var hours = minutes*60;
    var days = hours*24;
    var weeks = hours*7;
    var months = days * 31;
    var years = days*365;
    if (!start || !end) {
        return null;
    }
    var diff = end.getTime() - start.getTime();
    switch (unit.toLowerCase()) {
        case "ms"     : {
            diff = diff; break;
        };
        case "i"      : {
            diff = diff / minutes; break;
        };
        case "h"      : {
            diff = diff / hours; break;
        };
        case "d"      : {
            diff = diff / days; break;
        };
        case "w"      : {
            diff = diff / weeks; break;
        };
        case "m"      : {
            diff = diff / months; break;
        };
        case "y"      : {
            diff = diff / years; break;
        };
    };
    return diff;
}

/*
 * IMPLEMENT LATER FOR SLIDER
$(function(){

    var months = Math.round(calculateSliderInterval("m",$("#startdate").val(), $("#enddate").val()));
    var years = Math.round(calculateSliderInterval("y",$("#enddate").val(), $("#enddate").val()));
    var increment;
    var unit;
    if(years > 5) {
        increment = years;
        unit = "years";
    } else if(years==1) {
        increment = months;
        unit = "months";
    }
});
 */
function getSeconds(_startDate, _endDate) {
    var t = ( (_endDate?_endDate:0)-(_startDate?_startDate:0) )/1000;
    return t.toString().replace("-","");
}
(function () {
    $(function(){
        if (GBrowserIsCompatible()) {
            map = new GMap2(document.getElementById("map"));
            map.addControl(new GLargeMapControl());
            map.addControl(new GOverviewMapControl());
            map.setCenter(new GLatLng(39.078908, -94.54834), 4);
            map.enableDoubleClickZoom();
            mgr = new MarkerManager(map, {
                trackMarkers: true
            });
        }

        //load up data table
        $("table").tablesorter();
    });
})();
