package gov.cdc.ncphi.phgrid.services.gipse.service;

import gov.cdc.ncphi.phgrid.services.gipse.common.GIPSEPoisonConstants;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;



public class GIPSEPoisonServicePropertiesManager {

private static final Logger LOGGER = Logger.getLogger(GIPSEPoisonServicePropertiesManager.class);
	
	private GIPSEPoisonServicePropertiesManager(){}//private con, never instantiate
	
	private static Properties serviceProperties = null;
	private static Properties stateByZip3Properties  = null;
	
	public static final Properties getServiceProperties() throws Exception{
		if (serviceProperties != null){
			return serviceProperties;
		}else{
			try{
				serviceProperties = new Properties();
				serviceProperties.load(FileUtils.openInputStream(new File(GIPSEPoisonConfiguration.getConfiguration().getEtcDirectoryPath() + File.separator + GIPSEPoisonConstants.GIPSEPOISON_PROPERTIES_FILE_NAME)));
				return serviceProperties;
			}catch (Exception e){
				String errorMessage = "Error instantiating service properties! Message <" + e.getMessage() + ">";
				LOGGER.error(errorMessage,e);
				throw new Exception(errorMessage,e);
			}
		}
		
	}
	
	public static final Properties getStateByZip3Properties() throws Exception{
		if (stateByZip3Properties != null){
			return stateByZip3Properties;
		}else{
			try{
				stateByZip3Properties = new Properties();
				stateByZip3Properties.load(FileUtils.openInputStream(new File(GIPSEPoisonConfiguration.getConfiguration().getEtcDirectoryPath() + File.separator + GIPSEPoisonConstants.ZIP3BYSTATE_PROPERTIES_FILE_NAME)));
				return stateByZip3Properties;
			}catch (Exception e){
				String errorMessage = "Error instantiating service properties! Message <" + e.getMessage() + ">";
				LOGGER.error(errorMessage,e);
				throw new Exception(errorMessage,e);
			}
		} 
	}
}
