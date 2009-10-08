package gov.cdc.ncphi.phgrid.gridviewer.jsphelper;

import gov.cdc.ncphi.phgrid.gridviewer.AgeRange;
import gov.cdc.ncphi.phgrid.gridviewer.Cacher;
import gov.cdc.ncphi.phgrid.gridviewer.GIPSEDataFetcher;
import gov.cdc.ncphi.phgrid.gridviewer.Indicator;
import gov.cdc.ncphi.phgrid.gridviewer.ServerMetadata;
import gov.cdc.ncphi.phgrid.gridviewer.ServiceArea;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionData;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionListFetcher;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

import com.opensymphony.oscache.general.GeneralCacheAdministrator;

public class PHMapper {

    private static final Logger logger = Logger.getLogger(PHMapper.class);
    private static GeneralCacheAdministrator cacheAdmin = Cacher.getCache();
    private static final String PROPERTIES_FILE = "/gridviewer.properties";
    private static final String MAPKEY = "googleMapKey";
    private static final String BUILD_LABEL = "build.label";
    private GridViewerJSPHelper gvjh = null;
    private RegionListFetcher rlf = null;
    private GIPSEDataFetcher gdf = null;
    private PHC2FlotArrayJSPBean cfab = null;
    private static String googleMapKey = null;
    private static String buildLabel = null;

    public void init() {
        if (gvjh == null) {
            gvjh = new GridViewerJSPHelper();
        }
        if (rlf == null) {
            rlf = RegionListFetcher.getRegionListFetcher();
        }
        if (gdf == null) {
            gdf = GIPSEDataFetcher.getFetcher();
        }
        if (cfab == null) {
            cfab = new PHC2FlotArrayJSPBean();
        }
    }

    public String getGoogleMapKey() {
        if (googleMapKey == null || googleMapKey.equals("")) {
            try {
                Properties keyProp = new Properties();
                InputStream is = getClass().getResourceAsStream(PROPERTIES_FILE);
                if (is == null) {
                    logger.error("Could not read " + PROPERTIES_FILE);
                }
                keyProp.load(is);
                googleMapKey = keyProp.getProperty(MAPKEY);
            } catch (FileNotFoundException e) {
                logger.error(e.getMessage());
            } catch (IOException e) {
                logger.error(e.getMessage());
            }
        }
        return googleMapKey;
    }

    public String getBuildLabel() {
        if (buildLabel == null || buildLabel.equals("")) {
            try {
                Properties keyProp = new Properties();
                InputStream is = getClass().getResourceAsStream(PROPERTIES_FILE);
                if (is == null) {
                    logger.error("Could not read " + PROPERTIES_FILE);
                }
                keyProp.load(is);
                buildLabel = keyProp.getProperty(BUILD_LABEL);
                logger.info("BUILD LABEL: " + buildLabel);
            } catch (FileNotFoundException e) {
                logger.error("BUILD LABEL ERROR: " + e.getMessage());
            } catch (IOException e) {
                logger.error("BUILD LABEL ERROR: " + e.getMessage());
            }
        }
        return buildLabel;
    }

    public RegionalObservations getRegionalObservations(String regionName, String regionType,
            Date startDate, Date endDate, String indicatorName, String classifier,
            List<ServerDataSources> serverList, String ageRange, String serviceArea) {

        init();
        RegionalObservations returnable = new RegionalObservations();
        //get region data
        RegionData rl = rlf.getRegionDataForRegion(regionName, regionType);
        //get observations, first by turning serverlist into a collection of ServerMetadata
        returnable.setRegionData(rl);
        Calendar startDateCal = Calendar.getInstance();
        startDateCal.setTime(startDate);
        Calendar endDateCal = Calendar.getInstance();
        endDateCal.setTime(endDate);
        Collection<ServerMetadata> servMeta = gvjh.getServerSetWithDataSources(serverList);
        //now pull observations using other parameters.
        Indicator indicator = makeIndicator(indicatorName, classifier);
        List<AgeRange> arangeList = new ArrayList<AgeRange>();
        if (ageRange != null && !ageRange.equals("")) {
            arangeList.add(makeAgeRange(classifier, ageRange));
        }
        List<ServiceArea> sareaList = new ArrayList<ServiceArea>();
        if (serviceArea != null && !serviceArea.equals("")) {
            sareaList.add(makeServiceArea(classifier, serviceArea));
        }
        returnable.setObservations(gdf.fetchDataForRegion(rl, startDateCal, endDateCal, indicator, servMeta, arangeList, sareaList));
        return returnable;
    }

    public PHMapResponse getMultipleRegionalObservations(String regionParent, String regionType,
            Date startDate, Date endDate, String indicatorName, String classifier,
            List<ServerDataSources> serverList, String ageRange, String serviceArea) {
        init();
        PHMapResponse response = new PHMapResponse();

        List<RegionalObservations> returnable = new ArrayList<RegionalObservations>();
        List<RegionData> rdl = rlf.getRegionDataForRegions(regionParent, regionType);

        Calendar startDateCal = Calendar.getInstance();
        startDateCal.setTime(startDate);
        Calendar endDateCal = Calendar.getInstance();
        endDateCal.setTime(endDate);
        Collection<ServerMetadata> servMeta = gvjh.getServerSetWithDataSources(serverList);
        Indicator indicator = makeIndicator(indicatorName, classifier);
        List<AgeRange> arangeList = new ArrayList<AgeRange>();
        if (ageRange != null && !ageRange.equals("")) {
            arangeList.add(makeAgeRange(classifier, ageRange));
        }
        List<ServiceArea> sareaList = new ArrayList<ServiceArea>();
        if (serviceArea != null && !serviceArea.equals("")) {
            sareaList.add(makeServiceArea(classifier, serviceArea));
        }
        //service request timestamp
        response.setServiceCallRequestTime(new Date());
        returnable = gdf.fetchRegionalObservationsByGroup(rdl, startDateCal, endDateCal,
                indicator, servMeta, arangeList, sareaList);
        //service response timestamp
        response.setRegionalObservations(returnable);
        response.setServiceCallResponseTime(new Date());
        return response;
    }

    public C2FlotArray getC2FlotArray(String regionName, String regionType,
            Date startDate, Date endDate, String indicatorName, String classifier,
            List<ServerDataSources> serverList, String ageRange, String serviceArea) {

        C2FlotArray returnable = new C2FlotArray();
        //attempt to fix averages for flot plots
        Calendar c = Calendar.getInstance();
        c.setTime(startDate);
        c.add(Calendar.DAY_OF_MONTH, -30);
        RegionalObservations ro = getRegionalObservations(regionName, regionType,
                c.getTime(), endDate, indicatorName, classifier,
                serverList, ageRange, serviceArea);
        returnable = cfab.getArrayString(ro, startDate, endDate);

        return returnable;
    }

    private Indicator makeIndicator(String indicatorName, String classifier) {
        Indicator indicator = new Indicator();
        indicator.setClassifier(classifier);
        indicator.setName(indicatorName);
        return indicator;
    }

    private AgeRange makeAgeRange(String classifier, String ageRange) {
        AgeRange aRange = new AgeRange();
        aRange.setAgeRangeClassifier(classifier);
        aRange.setAgeRange(ageRange);
        return aRange;
    }

    private ServiceArea makeServiceArea(String classifier, String serviceArea) {
        ServiceArea sarea = new ServiceArea();
        sarea.setServiceAreaClassifier(classifier);
        sarea.setServiceArea(serviceArea);
        return sarea;
    }
}
