function phSlide(event, ui) {
    var minutes = 1000*60;
    var hours = minutes*60;
    var days = hours*24;
    var weeks = hours*7;
    var months = days * 31;
    var years = days*365;
    
    $.each(queries, function(index, value){
        var diff = this.query.startDate.getTime();
        var sliderPoint = ui.value;
    });
}

function getDiff(unit, diff) {
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
    }
    return diff;
}