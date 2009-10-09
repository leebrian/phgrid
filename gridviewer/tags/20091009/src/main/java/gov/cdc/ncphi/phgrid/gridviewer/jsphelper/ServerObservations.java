package gov.cdc.ncphi.phgrid.gridviewer.jsphelper;

import java.util.List;

import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseObservationSetObservation;
import gov.cdc.ncphi.phgrid.gridviewer.ServerMetadata;

public class ServerObservations {
	private ServerMetadata servMeta;
	private List<GIPSEQueryResponseObservationSetObservation> observations;
        
	public ServerMetadata getServMeta() {
		return servMeta;
	}
	public void setServMeta(ServerMetadata servMeta) {
		this.servMeta = servMeta;
	}
	public List<GIPSEQueryResponseObservationSetObservation> getObservations() {
		return observations;
	}
	public void setObservations(
			List<GIPSEQueryResponseObservationSetObservation> observations) {
		this.observations = observations;
	}
}
