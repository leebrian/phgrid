package gov.cdc.ncphi.phgrid.jsp;

import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseObservationSetObservation;
import java.text.ParseException;
import java.util.Arrays;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import gov.cdc.ncphi.phgrid.gridviewer.jsphelper.PHMapper;
import gov.cdc.ncphi.phgrid.gridviewer.jsphelper.RegionalObservations;
import gov.cdc.ncphi.phgrid.gridviewer.jsphelper.ServerDataSources;
import gov.cdc.ncphi.phgrid.gridviewer.jsphelper.ServerObservations;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.ServletOutputStream;

public class Export extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException,
            IOException {
        char delimiter = ',';

        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "inline;filename=\"data.csv\"");

        StringBuffer out = new StringBuffer();

        //parameters
        String regionParent = request.getParameter("latestStateID");
        String regionType = request.getParameter("state");
        if (regionParent.toLowerCase().equals("all")) {
            regionType = "state";
            regionParent = "USA";
        } else {
            regionType = "zip3";
        }
        SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date startDate = new Date();
        try {
            startDate = df.parse(request.getParameter("startdate").replace("%2F", "-"));
        } catch (ParseException ex) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, ex);
        }
        Date endDate = new Date();
        try {
            endDate = df.parse(request.getParameter("enddate").replace("%2F", "-"));
        } catch (ParseException ex) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, ex);
        }
        String indicatorName = request.getParameter("ClinicalEffect");
        String classifier = request.getParameter("Classifier");
        List<ServerDataSources> serverList = new ArrayList<ServerDataSources>();

        //loop through servers
        String[] serverKeys = request.getParameterValues("serverKeys");
        for (String s : serverKeys) {
            ServerDataSources server = new ServerDataSources();
            server.setServerName(s);

            //get datasources per server
            if (request.getParameter("chk" + s) != null) {
                List<String> dataSources = Arrays.asList(request.getParameterValues("chk" + s));
                server.setDataSources(dataSources);
            }
            serverList.add(server);
        }
        String ageRange = request.getParameter("ages");
        String serviceArea = request.getParameter("serviceAreas");

        PHMapper phMapper = new PHMapper();
        List<RegionalObservations> observations = phMapper.getMultipleRegionalObservations(regionParent,
                regionType,
                startDate,
                endDate, indicatorName,
                classifier, serverList,
                ageRange, serviceArea);

        out.append("\"Region\",\"Type\",\"Location\",\"Date\",\"Indicator\",\"Count\"\n");

        if (observations != null) {
            for (RegionalObservations observation : observations) {
                List<ServerObservations> _observations = observation.getObservations();
                if (_observations != null) {
                    for (ServerObservations _observation : _observations) {
                        List<GIPSEQueryResponseObservationSetObservation> __observations = _observation.getObservations();
                        if (__observations != null) {


                            for (GIPSEQueryResponseObservationSetObservation __observation : __observations) {

                                if (__observation != null) {
                                    //region
                                    out.append("\"");
                                    out.append(regionParent != null ? regionParent.replaceAll("\"", "\\\"") : "");
                                    out.append("\"" + delimiter);

                                    //region type
                                    out.append("\"");
                                    out.append(regionType != null ? regionType.replaceAll("\"", "\\\"") : "");
                                    out.append("\"" + delimiter);

                                    //location
                                    out.append("\"");
                                    out.append(__observation.getLocation() != null ? __observation.getLocation().replaceAll("\"", "\\\"") : "");
                                    out.append("\"" + delimiter);

                                    //date
                                    out.append("\"");
                                    out.append(df.format(__observation.getStart()) != null ? df.format(__observation.getStart()).replaceAll("\"", "\\\"") : "");
                                    out.append("\"" + delimiter);

                                    //indicator
                                    out.append("\"");
                                    out.append(__observation.getIndicator() != null ? __observation.getIndicator().replaceAll("\"", "\\\"") : "");
                                    out.append("\"" + delimiter);

                                    //count
                                    out.append("\"");
                                    out.append(__observation.getValue() != null ? __observation.getValue().replaceAll("\"", "\\\"") : "");
                                    out.append("\"\n");
                                }
                            }
                        }
                    }
                }
            }
        }
        try {
            byte[] outBytes = out.toString().getBytes();
            ServletOutputStream op = response.getOutputStream();
            op.write(outBytes);
            op.flush();
            op.close();
        }
        catch (Exception ex) {
            Logger.getLogger(Export.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
