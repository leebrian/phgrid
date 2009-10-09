function selectCheck(selec, type, latestState, latestZip3){
    checkboxArray=document.getElementsByName("serverKeys");

    for(var x=0; x<checkboxArray.length; x++)
    {
        var checkbox = checkboxArray[x];
        var serverName = checkbox.value;
        var val=selec.options[selec.selectedIndex].value
        var queryString = serverName+type+val;
        var queryString2 = serverName+'countryUS'+type+'*';
        var queryString3 = serverName+'state'+latestState+type+'*';
        var queryString4 = serverName+'zip3'+latestZip3+type+'*';
        if (containsArray[queryString] ||
            containsArray[queryString2] ||
            containsArray[queryString3] ||
            containsArray[queryString4])
            {
            checkbox.removeAttribute('disabled');
        }
        else
        {
            checkbox.disabled= true;
            checkbox.checked = false;
        }
    }
}

function selectConCheck(selec, classifiers){
    checkboxArray=document.getElementsByName("serverKeys");

    for(var x=0; x<checkboxArray.length; x++)
    {
        var checkbox = checkboxArray[x];
        var serverName = checkbox.value;
        var val=selec.options[selec.selectedIndex].value
        var type = document.getElementById('Classifier').value;
        var queryString = serverName+type+val;
        if (conditionContainsArray[queryString])
        {
            checkbox.removeAttribute('disabled');
        }
        else
        {
            checkbox.disabled=true;
            checkbox.checked = false;
        }
    }
}

function selCond(theSel){
    theForm = theSel.form;
    opt = theForm.ClinicalEffect.options;
    opt.length = 0;
    if(theSel.value=="") return;
    for(i=0;i<aMember.length;i++){
        if(aMember[i][0]==theSel.value){
            tValue = aMember[i][1];
            tName = aMember[i][2];
            for(j=0;j<opt.length;j++){
                if(opt[j].value==tValue) tValue="";
            }
            if(tValue>""){
                opt[opt.length] = new Option(tName, tValue);
            }
        }
    }
}

function setAddQuery(){
    try{
    document.mainForm.addQuery.value="true";
    }catch(e){}
}