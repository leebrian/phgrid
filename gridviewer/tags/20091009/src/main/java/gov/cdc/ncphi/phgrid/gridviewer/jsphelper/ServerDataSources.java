package gov.cdc.ncphi.phgrid.gridviewer.jsphelper;

import java.util.List;

public class ServerDataSources {
	String serverName;
	List<String> dataSources;
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	public List<String> getDataSources() {
		return dataSources;
	}
	public void setDataSources(List<String> dataSources) {
		this.dataSources = dataSources;
	}
	
}
