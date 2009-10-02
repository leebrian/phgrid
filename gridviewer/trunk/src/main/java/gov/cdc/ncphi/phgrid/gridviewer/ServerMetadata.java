package gov.cdc.ncphi.phgrid.gridviewer;

import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecord ;


import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class ServerMetadata implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public MetadataQueryResponseMetadataRecord getMrt() {
		return mrt;
	}
	public void setMrt(MetadataQueryResponseMetadataRecord mrt) {
		this.mrt = mrt;
	}
	private String serverName;
	private String serverUrl;
	private List<String> dataSources;
	public List<String> getDataSources() {
		return dataSources;
	}
	public void setDataSources(List<String> dataSources) {
		this.dataSources = dataSources;
	}
	private MetadataQueryResponseMetadataRecord mrt;
	
	public String getServerUrl() {
		return serverUrl;
	}
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public String getServerName() {
		return serverName;
	}

	
}
