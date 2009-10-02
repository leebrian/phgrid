function loadScript(n) {
    var se = document.createElement('script');
    se.src = n+".js";
    document.getElementsByTagName('head')[0].appendChild(se);
}
/*
 * order matters!!!
 */
var scripts = ["scripts/daterangepicker","scripts/markermanager","scripts/jquery.selectboxes","scripts/thickbox","scripts/gridviewer","scripts/gridviewer.current","/gridviewer/dwr/interface/regionDao","/gridviewer/dwr/interface/observationDao","/gridviewer/dwr/engine","scripts/initialize","scripts/jquery.tablesorter","scripts/regionCalls","scripts/icon","scripts/mapiconmaker","scripts/sliderEvents","scripts/color","scripts/jquery.sparkline.min","scripts/jquery.metadata"];

$(function(){

    $.each(scripts, function(index, value) {
        loadScript(value);
    });

});