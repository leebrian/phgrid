var iconOptions = {
    width: "32",
    height: "32",
    primaryColor: "ff0000",
    cornerColor: "ffffff",
    strokeColor: "000000"
};

function changeColor(colorHex) {
    iconOptions.primaryColor = colorHex;
    updateImage();
}

function changeCornerColor(colorHex) {
    iconOptions.cornerColor = colorHex;
    updateImage();
}

function createOptionsCode(optionNames) {
    var codeString = "";
    for (var i = 0; i < optionNames.length; i++) {
        var optionName = optionNames[i];
        var chars = "\"";
        if (optionName == "width" || optionName == "height" || optionName == "labelSize" || optionName == "addStar") chars = "";
        codeString += "iconOptions." + optionName + " = " + chars + iconOptions[optionName] + chars + ";\n";
    }
    return codeString;
}

function createIcon(_color, _label, _starColor) {
    //map.clearOverlays();
    //iconOptions.width = document.getElementById("iconWidth").value;
    //iconOptions.height = document.getElementById("iconHeight").value;
    iconOptions.primaryColor = _color;//document.getElementById("primaryColorPickerField").value;
    //iconOptions.cornerColor = document.getElementById("cornerColorPickerField").value;
    //iconOptions.strokeColor = document.getElementById("strokeColorPickerField").value;
    iconOptions.starPrimaryColor = _starColor;//document.getElementById("starPrimaryColorPickerField").value;
    iconOptions.starStrokeColor = "#999999";//document.getElementById("starStrokeColorPickerField").value;
    iconOptions.label = _label;//document.getElementById("label").value;
    //iconOptions.labelColor = document.getElementById("labelColorPickerField").value;
    //iconOptions.labelSize = document.getElementById("labelSize").value;
    iconOptions.addStar = true;//document.getElementById("iconAddStar").checked;
    //iconOptions.shape = document.getElementById("flatIconShapeSelect").value;

    var iconType = "labeledmarker";//document.getElementById("iconTypeSelect").value;
    if (iconType == "marker") {
        var markerOptionNames = ["width", "height", "primaryColor", "cornerColor", "strokeColor"];
        var icon = MapIconMaker.createMarkerIcon(iconOptions);
        var codeString = "var iconOptions = {};\n";
        codeString += createOptionsCode(markerOptionNames);
        codeString += "var icon = MapIconMaker.createMarkerIcon(iconOptions);";
    } else if (iconType == "labeledmarker") {
        var markerOptionNames = ["primaryColor", "strokeColor", "label", "labelColor", "addStar", "starPrimaryColor", "starStrokeColor"];
        var icon = MapIconMaker.createLabeledMarkerIcon(iconOptions);
        var codeString = "var iconOptions = {};\n";
        codeString += createOptionsCode(markerOptionNames);
        codeString += "var icon = MapIconMaker.createLabeledMarkerIcon(iconOptions);";
    //document.getElementById("iconCode").innerHTML = codeString;
    } else if (iconType == "flat") {
        var markerOptionNames = ["width", "height", "primaryColor", "label", "labelSize", "labelColor", "shape"];
        var icon = MapIconMaker.createFlatIcon(iconOptions);
        var codeString = "var iconOptions = {};\n";
        codeString += createOptionsCode(markerOptionNames);
        codeString += "var icon = MapIconMaker.createFlatIcon(iconOptions);";
    //document.getElementById("iconCode").innerHTML = codeString;
    }

    //document.getElementById("marker").innerHTML = "";
    //var img = document.createElement("img");
    //img.setAttribute("src", icon.image);
    //document.getElementById("marker").appendChild(img);

    return icon;
}

function createMarker(point, icon, title, body, regionName, map, count, query) {
    var marker = new GMarker(point, {
        icon: icon,
        title:title,
        draggable:false
    });
    marker.query = query;
    marker.map = map;
    GEvent.addListener(marker, "click", function() {

        //map.openInfoWindowHtml(marker.getLatLng());
        // var html = getFlotData(marker);
        // console.log(html)
        //   marker.openInfoWindowHtml(html);
        /*
            marker.openExtInfoWindow(
              map,
              "custom_info_window_red",
              "<div>Loading...</div>",
              {
                ajaxUrl: "flot.jsp",
                beakOffset: 3
              }
            );
        */
       tb_show("Region Chart","flot.jsp?zip="+regionName+"&count="+count+"&"+
            query.flotUrl+"&TB_iframe=true&height=375&width=450","");
/*
 *old marker window
        marker.openInfoWindowHtml("<h1>"+regionName+"</h1><br/><div style='font-size:1.25em;font-weight:bold;'>"+count+
            " Total cases</div><br /><center><div id='processing' style='text-align:center;'><img src='images/loadingAnimation.gif' border='0' /></div></center><iframe src=\"flot.jsp?zip="+regionName+"&"+
            query.flotUrl
            +"\" width=\"400\" height=\"300\" frameborder=\"0\"><br/>");
*/
    });
    return marker;
}
