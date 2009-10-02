package gov.cdc.ncphi.phgrid.gridviewer.jsphelper;

import java.util.Comparator;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseObservationSetObservation;

/**
 *
 * @author cmcilvoy
 */
public class GIPSEQueryResponseObservationSetObservationComparator implements Comparator<GIPSEQueryResponseObservationSetObservation> {

    boolean ascending = true;
    public GIPSEQueryResponseObservationSetObservationComparator(boolean ascending) {
        this.ascending = ascending;
    }

    public int compare(GIPSEQueryResponseObservationSetObservation arg0, GIPSEQueryResponseObservationSetObservation arg1) {
        return ascending ? 
            arg0.getLocation().compareTo(arg1.getLocation())
                : arg1.getLocation().compareTo(arg0.getLocation());
    }
    
}

