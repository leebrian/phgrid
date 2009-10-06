package gov.cdc.ncphi.phgrid.gridviewer.jsphelper;

import java.util.List;

import gov.cdc.ncphi.phgrid.polygon.regrel.RegionData;
import java.util.Date;

public class RegionalObservations{

    private RegionData regionData;
    private List<ServerObservations> serverObservations;

    public RegionData getRegionData() {
        return regionData;
    }

    public void setRegionData(RegionData regionData) {
        this.regionData = regionData;
    }

    public List<ServerObservations> getObservations() {
        return serverObservations;
    }

    public void setObservations(
            List<ServerObservations> observations) {
        this.serverObservations = observations;
    }

    
}
