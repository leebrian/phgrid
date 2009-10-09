package gov.cdc.ncphi.phgrid.gridviewer.jsphelper;

import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion;
import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegionSpecificLocationsGeoLocation;
import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordIndicatorsSupportedIndicator;
import gov.cdc.ncphi.phgrid.gridviewer.GIPSEServerFetcher;
import gov.cdc.ncphi.phgrid.gridviewer.ServerMetadata;
import gov.cdc.ncphi.phgrid.polygon.jsphelper.RegionSelectionBean;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionRelation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import org.directwebremoting.WebContext;
import org.directwebremoting.WebContextFactory;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;

public class ServerSelectorJSPHelper {

    private static final Logger logger = Logger.getLogger(ServerSelectorJSPHelper.class);
    private static RegionSelectionBean rsb;

    public ServerSelectorJSPHelper() {
        rsb = new RegionSelectionBean();
    }

    public List<String> getAjaxRegions(String regionType, String regionParent) {
        List<String> regions = new ArrayList<String>();

        Iterator<String> regionList = getRegions(regionType, regionParent, getAllServerMetadata()).iterator();
        while (regionList.hasNext()) {
            String region = regionList.next();
            regions.add(region);
        }
        return regions;
    }

    public String getRegionSelectList(String selectedRegion, String regionType, String regionParent) {
        String returnable = "";
        Iterator<String> regionList = getRegions(regionType, regionParent, getAllServerMetadata()).iterator();
        while (regionList.hasNext()) {
            String region = regionList.next();
            returnable += "<Option ";
            if (region.equals(selectedRegion)) {
                returnable += " selected = \"true\" ";
            }
            returnable += "Value = \"" + region + "\"> " + region;
            if (regionType.equals("state")) {
                returnable += " - " + rsb.getStateList().get(region);

            }
            returnable += " </Option> \n";
        }
        logger.trace("returning option List " + returnable);
        return returnable;
    }

    public String getClassifiers(String selectedClassifier) {
        String returnable = "";
        Iterator<String> classifierList = getClassifiers(getAllServerMetadata()).iterator();

        while (classifierList.hasNext()) {
            String classifier = classifierList.next();
            returnable += "<Option Value = \"" + classifier + "\" ";
            if (selectedClassifier != null && selectedClassifier.length() < 25 && classifier.equals(selectedClassifier)) {
                returnable += "selected = \"true\" ";
            }
            returnable += "> " + classifier + " </Option> \n";
        }
        logger.trace("returning classifier List " + returnable);
        return returnable;
    }

    public String getIndicatorString(String selecIndicator, String indKey) {
        String returnable = "";
        if (selecIndicator == null || selecIndicator.equals("")) {
            Iterator<String> classifierList = getClassifiers(getAllServerMetadata()).iterator();
            if (classifierList != null && classifierList.hasNext()) {
                selecIndicator = classifierList.next();
            }
        }
        logger.debug("assembling indicator string  for " + selecIndicator);
        Iterator<String> conditionList = getIndicators(getAllServerMetadata(), selecIndicator).iterator();

        while (conditionList.hasNext()) {
            String condition = conditionList.next();
            returnable += "<Option ";
            if (condition.equals(indKey)) {
                returnable += " selected=\"true\"";
            }
            returnable += "Value = \"" + condition + "\"> " + condition + " </Option> \n";
        }
        logger.trace("returning condition List " + returnable);
        return returnable;
    }

    public String getConditionContainsArray() {
        String returnable = "var conditionContainsArray = new Array(); \n";
        Map<String, ServerMetadata> servMap = getServers("~");
        Iterator<String> servIter = servMap.keySet().iterator();
        while (servIter.hasNext()) {
            String serverName = servIter.next();
            ServerMetadata servMeta = servMap.get(serverName);
            ArrayList<ServerMetadata> singleServeMetadata = new ArrayList<ServerMetadata>();
            singleServeMetadata.add(servMeta);
            Iterator<String> classifierList = getClassifiers(singleServeMetadata).iterator();

            while (classifierList.hasNext()) {
                String classifier = classifierList.next();
                Iterator<String> conditionList = getIndicators(getAllServerMetadata(), classifier).iterator();
                returnable += "conditionContainsArray['" + serverName + classifier + "all" + "'] = 1; \n";
                while (conditionList.hasNext()) {
                    String condition = conditionList.next();
                    returnable += "conditionContainsArray['" + serverName + classifier + condition + "'] = 1; \n";

                }
            }

        }
        logger.trace("returning contains List " + returnable);
        return returnable;
    }

    public String getConditionArray() {
        logger.debug("assembling Condition Array");
        String returnable = "var aMember = [";
        Iterator<String> classifierList = getClassifiers(getAllServerMetadata()).iterator();

        while (classifierList.hasNext()) {
            String classifier = classifierList.next();
            Iterator<String> conditionList = getIndicators(getAllServerMetadata(), classifier).iterator();
            //returnable += "[\"" + classifier + "\", \"all\", \" All Classifiers \"], \n";
            while (conditionList.hasNext()) {
                String condition = conditionList.next();
                returnable += "[\"" + classifier + "\", \"" + condition +
                        "\", \"" + condition + "\"],\n";

            }
        }
        String adjust = returnable.substring(0, returnable.length() - 1);
        returnable = adjust + "];\n\n";
        return returnable;
    }

    public String getContainsArray() {
        String returnable = "var containsArray = new Array(); \n";
        Map<String, ServerMetadata> servMap = getServers("~");
        Iterator<String> servIter = servMap.keySet().iterator();
        while (servIter.hasNext()) {
            String serverName = servIter.next();
            ServerMetadata servMeta = servMap.get(serverName);
            returnable += "containsArray['" + serverName + "state" + "ALL" + "'] = 1; \n";
            returnable += "containsArray['" + serverName + "zip3" + "ALL" + "'] = 1; \n";
            returnable += "containsArray['" + serverName + "zip5" + "ALL" + "'] = 1; \n";
            if (servMeta.getMrt() != null && servMeta.getMrt().getGeoRegionsSupported() != null && servMeta.getMrt().getGeoRegionsSupported().getGeoRegion() != null) {
                MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion gIter[] = servMeta.getMrt().getGeoRegionsSupported().getGeoRegion();

                for (int k = 0; k < gIter.length; k++) {
                    MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion geode = gIter[k];
                    String regionType = "";
                    String regionName = "";
                    if (geode.getType() != null && geode.getValue() != null && geode.getType().getValue() != null) {
                        regionType = geode.getType().getValue();
                        regionName = geode.getValue();
                        if (geode.getSpecificLocations() != null && geode.getSpecificLocations().getGeoLocation() != null) {
                            MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegionSpecificLocationsGeoLocation specloc[] = geode.getSpecificLocations().getGeoLocation();
                            String specRegionType = "";
                            String specRegionName = "";
                            for (int j = 0; j < specloc.length; j++) {
                                if (specloc[j].getType() != null && specloc[j].getValue() != null && specloc[j].getType().getValue() != null) {
                                    specRegionType = specloc[j].getType().getValue();
                                    specRegionName = specloc[j].getValue();
                                    returnable += "containsArray['" + serverName + regionType + regionName +
                                            specRegionType + specRegionName + "'] = 1; \n";
                                }
                            }
                        } else {
                            returnable += "containsArray['" + serverName + regionType + regionName + "'] = 1; \n";
                        }
                    }

                }
            } else {
                logger.warn("No geo-regions found in the metadata");
            }
        }
        logger.trace("returning contains List " + returnable);
        return returnable;
    }

    public Set<String> getRegions(String regionType, String regionParent, Collection<ServerMetadata> servMeta) {

        Set<String> returnable = new TreeSet<String>();

        Iterator<ServerMetadata> servIter = servMeta.iterator();
        logger.debug("fetching regions for type " + regionType);
        while (servIter.hasNext()) {
            ServerMetadata meta = servIter.next();
            if (meta.getMrt() != null && meta.getMrt().getGeoRegionsSupported() != null &&
                    meta.getMrt().getGeoRegionsSupported().getGeoRegion() != null) {
                MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion gIter[] = meta.getMrt().getGeoRegionsSupported().getGeoRegion();
                for (int k = 0; k < gIter.length; k++) {
                    MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegion geode = gIter[k];
                    if (geode != null && geode.getType().getValue().equals(regionType)) {
                        returnable.add(geode.getValue());
                        logger.trace("adding region " + geode.getValue());
                    } else if (geode.getSpecificLocations() != null && geode.getSpecificLocations().getGeoLocation() != null) {
                        MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegionSpecificLocationsGeoLocation specLocs[] =
                                geode.getSpecificLocations().getGeoLocation();
                        for (int j = 0; j < specLocs.length; j++) {
                            MetadataQueryResponseMetadataRecordGeoRegionsSupportedGeoRegionSpecificLocationsGeoLocation specLoc = specLocs[j];
                            if (specLoc.getType().getValue().equals(regionType)) {
                                if (specLoc.getValue().equals("*")) {
                                    //fetch all the regions for a given region type
                                    Collection<RegionRelation> regrel = rsb.getRegionListByParentAndType(regionType, regionParent);
                                    Iterator<RegionRelation> regIter = regrel.iterator();
                                    while (regIter.hasNext()) {
                                        returnable.add(regIter.next().getRegionName());
                                    }
                                }
                            }
                        }
                    }
                }
            }

        }
        return returnable;
    }

    public Set<String> getClassifiers(Collection<ServerMetadata> servMeta) {

        Set<String> returnable = new TreeSet<String>();

        Iterator<ServerMetadata> servIter = servMeta.iterator();
        logger.debug("fetching classifiers");
        while (servIter.hasNext()) {
            ServerMetadata meta = servIter.next();
            if (meta.getMrt() != null && meta.getMrt().getIndicatorsSupported() != null &&
                    meta.getMrt().getIndicatorsSupported().getIndicator() != null) {
                MetadataQueryResponseMetadataRecordIndicatorsSupportedIndicator indicator[] = meta.getMrt().getIndicatorsSupported().getIndicator();
                for (int k = 0; k < indicator.length; k++) {

                    MetadataQueryResponseMetadataRecordIndicatorsSupportedIndicator conde = indicator[k];
                    if (conde != null) {
                        returnable.add(conde.getClassifier());
                        logger.trace("adding classifier " + conde.getClassifier());
                    }
                }
            }

        }
        return returnable;
    }

    public Set<String> getIndicators(Collection<ServerMetadata> servMeta, String selecClassifier) {

        Set<String> returnable = new TreeSet<String>();

        Iterator<ServerMetadata> servIter = servMeta.iterator();
        logger.debug("fetching indicators for type " + selecClassifier);
        while (servIter.hasNext()) {
            ServerMetadata meta = servIter.next();
            if (meta.getMrt() != null && meta.getMrt().getIndicatorsSupported() != null &&
                    meta.getMrt().getIndicatorsSupported().getIndicator() != null) {
                MetadataQueryResponseMetadataRecordIndicatorsSupportedIndicator indicator[] = meta.getMrt().getIndicatorsSupported().getIndicator();
                for (int k = 0; k < indicator.length; k++) {
                    MetadataQueryResponseMetadataRecordIndicatorsSupportedIndicator conde = indicator[k];
                    if (conde != null && conde.getClassifier().equals(selecClassifier)) {
                        returnable.add(conde.getName());
                        logger.trace("adding Indicator " + conde.getName());
                    }
                }
            }
        }
        return returnable;
    }

    public Map<String, ServerMetadata> getServers(String sessionId) {
        if (sessionId != null && !sessionId.equals("~")) {
            WebContext ctx = WebContextFactory.get();
            if (ctx != null) {
                HttpServletRequest request = ctx.getHttpServletRequest();
                if (request != null) {
                    if (sessionId != null && !sessionId.equals(request.getSession().getId())) {
                        return null;
                    }
                }
            }
        }
        GIPSEServerFetcher asf = GIPSEServerFetcher.getServerFetcher();
        Map<String, ServerMetadata> returnable = asf.getServers();

        try {
            ServerMetadata sm = new ServerMetadata();
            sm.setServerName("random");
            sm.setServerUrl(String.valueOf(System.currentTimeMillis() + Math.random()));
            returnable.put("random", sm);
        } catch (Exception e) {
        }
        return returnable;
    }

    public Collection<ServerMetadata> getAllServerMetadata() {
        Collection<ServerMetadata> returnable = new ArrayList<ServerMetadata>();

        Map<String, ServerMetadata> serverList = getServers("~");
        if (serverList != null && serverList.keySet() != null) {

            Iterator<String> keyIter = serverList.keySet().iterator();
            while (keyIter.hasNext()) {
                String serverKey = keyIter.next();
                returnable.add(serverList.get(serverKey));
            }
        }
        return returnable;

    }
}
