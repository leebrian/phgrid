$(function(){

    $("#filters").html("");

    //bind controls
    $("#btnAjax").bind("click", function(event){
        requestHandler(event);
    });
    $("#btnToggleMgr").bind("click", function(){
        toggleMarkerManager();
    });
    $("#chkLegend").bind("click", function(){
        $('#legend').is(':visible')?$('#legend').hide():$('#legend').show();
    });
    $("#linkShowHide, form.selections>fieldset>legend").bind("click", function(){
        
        $("#options").is(":visible")?$("#options").hide():$("#options").show();
        if($("form.selections>fieldset>legend").html()=="Show Criteria") {
            $("form.selections>fieldset>legend").html("Hide Criteria");
            $("#linkShowHide").html("hide criteria");
            $("#filters").show();
        } else {
            $("form.selections>fieldset>legend").html("Show Criteria");
            $("#linkShowHide").html("show criteria");
            $("#filters").hide();
        }
    //mapResize();
    });
    $("#addquerybutton", function(){
        // setAddQuery();
        });

    if(latestStateID.toLowerCase() == "all" ) {
        $("#liZips").hide();
    } 

    //bind to unload
    $(window).unload( function () {
        GUnload();
    } );

    $("form").bind("submit", function(event){
        validateForm(event);
    });
    
    $("#startdate").val(Date.today().add(-1).months().toString("MM/d/yyyy"));
    $("#enddate").val(Date.today().toString("MM/d/yyyy"));
    
    $('.dates > input').daterangepicker();

    if($("#slider") != undefined){
        $("#slider").slider({
            value:100,
            min: 0,
            max: 500,
            step: 50,
            slide: function(event, ui) {
                $("#amount").val(ui.value);
                phSlide(event, ui);
            }
        });
        $("#amount").val($("#slider").slider("value"));
    }
});
function requestHandler() {
    $("#btnRefresh").show();
    $("#btnToggleMgr").show();
    $("#pinLegend").show();
    $("#divAllDataRecords").show();
    $("#btnAjax").val("Processing...");
    getMarkers();
}

function validateForm(event) {
    //event.preventDefault();
    $("#userZoom").val(map.getZoom());
        
    return true;
}
function mapResize()
{
    var w = $(window);
    var H = w.height();
    var W = w.width();
    var _h = $("#options").is(":visible")?H-350:H-175;
    $('#mBox').css( {
        width: W-26,
        height: _h
    } );
}
function tableCellWrap(_innerHtml) {
    return "<td>"+_innerHtml+"</td>";
}

var bitly = {
    login:"phgrid",
    apiKey:"R_761453c786014b15530d8e740c1e4d7f",
    url: function(_url) {
        return "http://api.bit.ly/shorten?version=2.0.1&longUrl="+_url+"&login="+this.login+"&apiKey="+this.apiKey+"&format=json&callback=?";
    }
    };