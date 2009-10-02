(function () {
    $(function(){
        regionDao.getServers(sessionid, function(response){
            if(response == null) {
                return;
            }
            $.each(response, function(index, value){
                //add checkboxs for server
                var serverName = this.serverName;
                var cb = document.createElement("input");
                var link = document.createElement("a");
                var div = document.createElement("div");
                var br = document.createElement("br");
                if(serverName != "random") {
                    cb.type = "checkbox";
                    cb.name = "serverKeys";
                    cb.id = "chk"+serverName;
                    cb.value = serverName;///this.serverUrl;
                    cb.checked = true;
                    cb.setAttribute("checked","checked");
                    cb.setAttribute('defaultChecked', 'defaultChecked');
                    $("#servers").append(cb);
                    link.setAttribute("class",'thickbox');
                    //link.href = "serverMetadata.jsp?serverName="+serverName+"&TB_iframe=true&height=450&width=660";
                    link.href = "javascript://";
                    link.setAttribute("onClick","tb_show('Metadata "+serverName+"','serverMetadata.jsp?serverName="+serverName+"&TB_iframe=true&height=450&width=660','');return false;");
                    link.onclick = function() {
                        tb_show("Metadata "+serverName,"serverMetadata.jsp?serverName="+serverName+"&TB_iframe=true&height=450&width=660","");return false;
                    };
                    link.innerHTML = " "+serverName+" ";
                    $("#servers").append(link);
                    var text = document.createElement("span");
                    text.innerHTML = "&nbsp;&nbsp;";
                    $("#servers").append(text);

                    link = document.createElement("a");
                    link.href = "javascript://";
                    link.setAttribute("onClick", "$('#divS-"+serverName+"').toggle();");
                    link.onclick = function(){
                        $('#divS-'+serverName).toggle();
                    };
                    link.innerHTML = "[show datasources]";
                    $("#servers").append(link);

                    div.id = "divS-"+serverName;
                    div.className = "datasource";
                    $("#servers").append(div);
                    $("#servers").append(br);

                    if(this.mrt != undefined) {
                        if(this.mrt.dataSourceOID != null) {
                            $.each(this.mrt.dataSourceOID, function(){
                                var cb = document.createElement("input");
                                cb.type = "checkbox";
                                //cb.name = "datasource";
                                cb.name = "chk"+serverName;
                                cb.id = "chk"+this;
                                cb.value = this;
                                cb.checked = true;
                                cb.setAttribute("checked","checked");
                                cb.setAttribute('defaultChecked', 'defaultChecked');
                                var text = document.createTextNode(" "+this+" ");
                                $("#divS-"+serverName).append(cb);
                                $("#divS-"+serverName).append(text);
                            });
                        }

                        $("#servers").append(br).append(br);

                        if(this.mrt.serviceAreasSupported != null) {
                            $.each(this.mrt.serviceAreasSupported.serviceArea, function(){
                                if(this != undefined) {
                                    $("#serviceAreas").addOption(this.name, this.name);
                                }
                            });
                            if(!$("#serviceAreas").containsOption("")) {
                                $("#serviceAreas").addOption("","All").sortOptions().selectOptions("");
                            }
                        }
                        if(this.mrt.ageCodingsSupported != null) {
                            $.each(this.mrt.ageCodingsSupported.age, function(){
                                if(this != undefined) {
                                    $("#ages").addOption(this.name, this.name);
                                }
                            });

                            if(!$("#ages").containsOption("")) {
                                $("#ages").addOption("","All").sortOptions().selectOptions("");
                            }
                        }
                    /*
                   * logic to build later, use existing server-side logic for now
                if(this.mrt.indicatorsSupported != null) {
                    $.each(this.mrt.indicatorsSupported.indicator, function(){
                        // if(this != undefined) {
                        var index = classifiers.indexOf(this.classifier);
                        if(index != -1) {
                            classifiers.push(this.classifier);
                        }
                        if(!$("#indicators").containsOption(this.name)) {
                            $("#indicators").addOption(this.name+"~"+this.classifier, this.name, false);
                        }
                        if(!$("#classifiers").containsOption(this.classifier)) {
                            $("#classifiers").addOption(this.classifier, this.classifier, false);
                        }
                    // }
                    });
                    if(!$("#indicators").containsOption("")) {
                        $("#indicators").addOption("","Select an Option");
                    }
                    $("#indicators").sortOptions().selectOptions("");
                    if(!$("#classifiers").containsOption("")) {
                        $("#classifiers").addOption("","Select an Option");
                    }
                    $("#classifiers").sortOptions().selectOptions("");
                }
                   */
                    }
                }
            });
        });

        //stuff in build for brainy
        try{
            document.title = "Gridviewer v."+buildLabel;
            $("h1:first").html("Gridviewer <span style='color:#999;font-size:.5em;font-weight:normal;'>v."+buildLabel+"</span>")
        }catch(err){}


        //if postback == true
        if(postBack){
            //set controls
            $("#startdate").val(startDate);
            $("#enddate").val(endDate);
            $("#latestStateID").selectOptions(latestStateID);
            $("#ages").selectOptions(age);
            $("#serviceAreas").selectOptions(serviceAreas);
            $("#ClinicalEffect").selectedValues(ClinicalEffect);
            $("#Classifier").selectOptions(Classifier);

            var _serverList = [];
            sk = serverKeys;
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
                                server.dataSources.push(dsValue);
                            });
                        }
                        _serverList.push(server);
                    });
                } else {
                    var server = new Object();
                    server.dataSources = [];
                    server.serverName = sk[0];
                    var ds = $(document).getUrlParam("chk"+sk[0]);
                    if(ds != null){
                            //loop thru datasource & add to facilities
                            $.each(ds, function(dsIndex, dsValue) {
                                server.dataSources.push(dsValue);
                            });
                        }
                    _serverList.push(server);
                }
            }
            serverKeys = _serverList;
            //run
            getMarkers();
        }


        
    });

})();