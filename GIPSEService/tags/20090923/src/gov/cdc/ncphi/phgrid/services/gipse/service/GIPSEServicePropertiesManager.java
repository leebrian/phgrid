/**
 *
 * @project PHGrid (Apache License v2.0
 * @created May 29, 2009 12:55:22 AM
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
package gov.cdc.ncphi.phgrid.services.gipse.service;

import gov.cdc.ncphi.phgrid.services.gipse.common.GIPSEServiceConstants;

import java.io.File;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * Singleton class that manages access to gipse.properties.
 * @project PHGrid (Apache License v2.0
 * @created May 29, 2009 12:55:22 AM
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
public class GIPSEServicePropertiesManager {
	private static final Logger LOGGER = Logger.getLogger(GIPSEServicePropertiesManager.class);
	
	private GIPSEServicePropertiesManager(){}//private con, never instantiate
	
	private static Properties serviceProperties = null;
	
	public static final Properties getServiceProperties() throws Exception{
		if (serviceProperties != null){
			return serviceProperties;
		}else{
			try{
				serviceProperties = new Properties();
				serviceProperties.load(FileUtils.openInputStream(new File(GIPSEServiceConfiguration.getConfiguration().getEtcDirectoryPath() + File.separator + GIPSEServiceConstants.GIPSE_PROPERTIES_FILE_NAME)));
				return serviceProperties;
			}catch (Exception e){
				String errorMessage = "Error instantiating service properties! Message <" + e.getMessage() + ">";
				LOGGER.error(errorMessage,e);
				throw new Exception(errorMessage,e);
			}
		}
		
	}
}
