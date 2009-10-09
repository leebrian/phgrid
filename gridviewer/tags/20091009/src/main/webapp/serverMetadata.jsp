<%@ page contentType="text/html; 
         charset=utf-8"
         import="org.apache.log4j.Logger,
         gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean,
         gov.cdc.ncphi.phgrid.polygon.jsphelper.C2FlotArrayJSPBean,
         gov.cdc.ncphi.phgrid.gridviewer.ServerMetadata,
         java.util.Collection,
         gov.cdc.ncphi.phgrid.polygon.gmaps.GmapPolygon,
         java.util.Iterator,
         gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecord,
         gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordIndicatorsSupportedIndicator,
         gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion,
         gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegionSpecificLocationsGeoLocation,
         gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordTimePeriodSupported,
         org.apache.commons.lang.StringEscapeUtils"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<jsp:useBean id="gjsp" scope="session" class="gov.cdc.ncphi.phgrid.polygon.jsphelper.GoogleJSPBean" /> 
<jsp:useBean id="c2f" scope="application" class="gov.cdc.ncphi.phgrid.polygon.jsphelper.C2FlotArrayJSPBean" />
<jsp:useBean id="gvjh" scope="session" class="gov.cdc.ncphi.phgrid.gridviewer.jsphelper.GridViewerJSPHelper" />
<%
            response.setHeader("Pragma", "no-cache");

            String serverName = StringEscapeUtils.escapeHtml(request.getParameter("serverName"));
            if (serverName == null) {
                serverName = "";
            }
            ServerMetadata srvMeta = gvjh.getServers().get(serverName);
            //Collection<GmapPolygon> col = gjsp.getPolygonCollection();
            //Iterator<GmapPolygon> iter = col.iterator();
            if (srvMeta != null) {%>
<%@ include file="_includes/header.jsp" %>
<a name="top"></a>
<h1>Server Metadata: <%=serverName%> <sup>*</sup></h1>
<br />
<p>Supported: <a href="#indicators">Indicators</a>&nbsp;|&nbsp;<a href="#ages">Ages</a>&nbsp;|&nbsp;<a href="#serviceareas">Service Areas</a>&nbsp;|&nbsp;<a href="#datasources">Data Sources</a>&nbsp;|&nbsp;<a href="#locations">Locations</a>&nbsp;|&nbsp;<a href="#time">Time</a></p>

<br /><br />
<%
            MetadataQueryResponseMetadataRecord metRec = srvMeta.getMrt();
%>
<a name="indicators"></a>
<h2>Indicators</h2>
<table cellpadding="5" cellspacing="0" width="80%" align="center">
    <thead>
        <tr>
            <th align="left">Classifier</th><th align="left">Indicator</th></tr>
    </thead>
    <tbody>
        <% if (metRec.getIndicatorsSupported() != null && metRec.getIndicatorsSupported().getIndicator() != null) {
                        MetadataQueryResponseMetadataRecordIndicatorsSupportedIndicator cs[] = metRec.getIndicatorsSupported().getIndicator();
                        for (int k = 0; k < cs.length; k++) {
                            MetadataQueryResponseMetadataRecordIndicatorsSupportedIndicator indy = cs[k];
        %> <tr><td>Classifier = <%=indy.getClassifier()%></td><td><%=indy.getName()%></td></tr> <%
                    }
                }%>
    </tbody>
</table>
<a href="#top">top</a>
<a name="ages"></a>
<h2>Ages</h2>
<table cellpadding="5" cellspacing="0" width="80%" align="center">
    <thead>
        <tr>
            <th align="left">Classifier</th><th align="left">Age Range</th></tr>
    </thead>
    <tbody>
        <% if (metRec.getAgeCodingsSupported() != null && metRec.getAgeCodingsSupported().getAge() != null) {
                        MetadataQueryResponseMetadataRecordAgeCodingsSupportedAge ag[] = metRec.getAgeCodingsSupported().getAge();
                        for (int k = 0; k < ag.length; k++) {
                        	MetadataQueryResponseMetadataRecordAgeCodingsSupportedAge aggy = ag[k];
        %> <tr><td>Classifier = <%=aggy.getClassifier()%></td><td><%=aggy.getName()%></td></tr> <%
                    }
                }%>
    </tbody>
</table>

<a href="#top">top</a>

<a name="serviceareas"></a>
<h2>Service Areas</h2>
<table cellpadding="5" cellspacing="0" width="80%" align="center">
    <thead>
        <tr>
            <th align="left">Service Area</th></tr>
    </thead>
    <tbody>
        <% if (metRec.getServiceAreasSupported() != null && metRec.getServiceAreasSupported().getServiceArea() != null) {
                MetadataQueryResponseMetadataRecordServiceAreasSupportedServiceArea sa[] = metRec.getServiceAreasSupported().getServiceArea();
                        for (int k = 0; k < sa.length; k++) {
                        	MetadataQueryResponseMetadataRecordServiceAreasSupportedServiceArea sary = sa[k];
        %> <tr><td><%=sary.getName()%></td></tr> <%
                    }
                }%>
    </tbody>
</table>

<a href="#top">top</a>

<a name="datasources"></a>
<h2>Data Sources</h2>
<table cellpadding="5" cellspacing="0" width="80%" align="center">
    <thead>
        <tr>
            <th align="left">Data Source</th></tr>
    </thead>
    <tbody>
        <% if (metRec.getDataSourceOID() != null ) {
                String ds[] = metRec.getDataSourceOID();
                        for (int k = 0; k < ds.length; k++) {
                        	String dsi = ds[k];
        %> <tr><td><%=dsi%></td></tr> <%
                    }
                }%>
    </tbody>
</table>

<a href="#top">top</a>

<br /><br />
<a name="locations"></a>
<h2>Locations</h2>
<table cellpadding="5" cellspacing="0" width="80%" align="center">
    <thead>
        <tr>
            <th align="left">Type</th><th align="left">Value</th></tr>
    </thead>
    <tbody>
        <%
                    if (metRec.getGeoRegionsSupported() != null && metRec.getGeoRegionsSupported().getGeoRegion() != null) {
                        MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion gs[] =
                                metRec.getGeoRegionsSupported().getGeoRegion();
                        for (int k = 0; k < gs.length; k++) {
                            MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion geoLoc = gs[k];
        %> <tr><td><%=geoLoc.getType().getValue()%>, Location = <%= geoLoc.getValue()%></td><td><%
                    if (geoLoc.getSpecificLocations() != null && geoLoc.getSpecificLocations().getGeoLocation() != null) {
                        MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegionSpecificLocationsGeoLocation sl[] =
                                geoLoc.getSpecificLocations().getGeoLocation();
                        for (int j = 0; j < sl.length; j++) {
                            MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegionSpecificLocationsGeoLocation specLoc = sl[j];
                %> Specific Location Type = <%=specLoc.getType().getValue()%>, Location = <%=specLoc.getValue()%></td></tr> <%
                                }
                            }
                        }
                    }

                %>
    </tbody>
</table>
<a href="#top">top</a>
<br /><br />

<a name="time"></a>
<h2>Time Periods</H2>
<table cellpadding="5" cellspacing="0" width="80%" align="center">
    <thead>
        <tr>
            <th align="left">From</th><th align="left">To</th></tr>
    </thead>
    <tbody>
        <%
                MetadataQueryResponseMetadataRecordTimePeriodSupported ts = metRec.getTimePeriodSupported();
        %><tr><td><%= ts.getStart().getTime()%></td><td><%=ts.getEnd().getTime()%></td></tr>

    </tbody>
</table>
<br/>
<br/>
<br/>
<a href="#top">top</a>
<br/>
<br/>
<p>* Cell Suppression: no values smaller than <%=metRec.getSuppressValues().getSmallestValueReported()%> reported.</p>
</body>

<%@page import="gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordAgeCodingsSupportedAge"%>
<%@page import="gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordFacilitiesSupportedFacility"%>
<%@page import="gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordServiceAreasSupportedServiceArea"%></html>
<%}%>
       <!--

<%=System.currentTimeMillis() +  Math.random()%>
-->