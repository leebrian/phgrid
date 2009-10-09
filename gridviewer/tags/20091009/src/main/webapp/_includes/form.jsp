<form class="selections" method="POST" name="mainForm">
    <input type="hidden" name="postBack" id="postBack" value="true" />
    <input type="hidden" name="userZoom" id="userZoom" />
    <!--
    <input type="hidden" name="mostRecentStateID" id="mostRecentStateID" value ="<%=mostRecentStateID%>"/>
    <input type="hidden" name="mostRecentZip3ID" id="mostRecentZip3ID" value ="<%=mostRecentZip3ID%>"/>
    <input type="hidden" name="mostRecentbase" id="mostRecentbase" value ="<%=base%>"/>
    <input type="hidden" name="mostRecentgreenYellow" id="mostRecentgreenYellow" value ="<%=greenYellow%>"/>
    <input type="hidden" name="mostRecentyellowRed" id="mostRecentyellowRed" value ="<%=yellowRed%>"/>
    <input type="hidden" name="mostRecentRegionName" id="mostRecentRegionName" value="<%= currentRegionName%>"/>
    <input type="hidden" name="addQuery" id="addQuery" />
    <input type="hidden" name="passInKey" id="passInKey" value="<%=session.getId()%>" />
    -->
    <fieldset>
        <legend>Selection Criteria</legend>
        <div class="showHide"><a href="javascript://" id="linkShowHide">minimize</a> </div>
        <div id="filters"></div>
        <ol id="options">
            <li>
                <div class="dates">
                    <label>Date Range</label>
                    <input type="text" id="startdate" name="startdate" style="border:0px;border-bottom:1px solid #ccc;width:100px" />
                    <input type="text" id="enddate" name="enddate" style="border:0px;border-bottom:1px solid #ccc;width:100px" />
                </div>
            </li>

            <li>
                <div>
                    <label for="latestStateID">Location</label>
                    <select id="latestStateID" name="latestStateID">
                        <%
            Set<String> sortKeys = new TreeSet<String>();
            String statename = "";
            String stateid = "";
                        %>
                        <option Value="ALL">All States</option>
                        <%=ssjh.getRegionSelectList(latestStateID, "state", "USA")%>
                    </select>
                </div>
            <li id="liZips">
                <div id="divZips">
                    <label for="latestZip3ID" id="lblZip">Zips</label>
                    <select name="latestZip3ID" id="latestZip3ID">
                        <%
            Collection<RegionRelation> zip3List = rsb.getRegionListByParentAndType("zip3", latestStateID);
            Iterator<RegionRelation> zip3iter = zip3List.iterator();
            String zip3ID = "";
                        %>
                        <option Value="ALL">All Zip 3</option>
                        <%=ssjh.getRegionSelectList(latestZip3ID, "zip3", latestStateID)%>
                    </select> &nbsp;&nbsp;
                    <select name="latestZip5ID" id="latestZip5ID">
                        <%
            Collection<RegionRelation> zip5List = rsb.getRegionListByParentAndType("zip5", latestZip3ID);
            Iterator<RegionRelation> zip5iter = zip5List.iterator();
            String zip5ID = "";
                        %>
                        <option Value="ALL">All Zip 5</option>
                        <%
            while (zip5iter.hasNext()) {
                zip5ID = zip5iter.next().getRegionName();
                        %><option Value = "<%=zip5ID%>"><%=zip5ID%></option>
                        <%}%>
                    </select>
                </div>
            </li>
            <li>
                <label for="ages">Age</label>
                <select name="ages" id="ages"></select>
            </li>

            <li>
                <label for="serviceAreas">Dispositions</label>
                <select name="serviceAreas" id="serviceAreas"></select>
            </li>

            <li>
                <label for="Classifier">Classifiers</label>
                <!--  <select name="classifiers" id="classifiers"></select> -->
                <select name="Classifier" id="Classifier" onchange="selCond(this);">
                    <%=ssjh.getClassifiers(loadClassifier)%>
                </select>
            </li>

            <li>
                <label for="ClinicalEffect">Indicators</label>
                <!--<select id="indicators" name="indicators"></select>
                 onchange="selectConCheck(this);"
                -->
                <select id="ClinicalEffect" name="ClinicalEffect">
                    <%=ssjh.getIndicatorString(loadClassifier, loadClinKey)%>
                </select>

            </li>
            <li>
                <label>Services</label>
                <div id="servers" style="margin-left:100px;">
                </div>
            </li>
            <!--
                        <li>
                            <label for="displayType">Display</label>
                            <select NAME="displayType">
                                <option VALUE="0">Pinpoint</option>
                                <option VALUE="1">Polygons</option>
                            </select>
                        </li>
            -->
            <li>
                <div class="buttons">
                    <input type="button" id="btnAjax" value="Run Query" /> &nbsp;&nbsp;
                    &nbsp;
                    <div style="float:right;margin-right:10px;">
                        <button type="button" id="btnRefresh" onclick="clearMap();" style="display:none;">Clear map</button>&nbsp;
                        <button type="button" id="btnToggleMgr" style="display:none;">Hide Markers</button>
                    </div>
                    <br/>
                </div>
            </li>
        </ol>
    </fieldset>
</form>
