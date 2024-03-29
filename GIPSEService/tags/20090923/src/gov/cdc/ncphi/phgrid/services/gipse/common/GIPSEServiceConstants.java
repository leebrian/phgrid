package gov.cdc.ncphi.phgrid.services.gipse.common;

import javax.xml.namespace.QName;


/**
 * Constants class that extends the introduce managed constants.  Developers can add constants to this file.
 * @project PHGrid (Apache License v2.0
 * @created May 29, 2009 1:09:53 AM
 * @author Brian Alexander Lee (brianalee@gmail.com)
 * @created by Introduce Toolkit version 1.3
 */
public interface GIPSEServiceConstants extends GIPSEServiceConstantsBase {
	
	public static final String GIPSE_PROPERTIES_FILE_NAME = "gipse.properties";
	
	public static final String PROPERTY_JDBC_URL = "db.jdbc.url";
	public static final String PROPERTY_JDBC_DRIVER = "db.jdbc.driver";
	public static final String PROPERTY_JDBC_USER = "db.jdbc.user";
	public static final String PROPERTY_JDBC_PASS = "db.jdbc.password";
	public static final String PROPERTY_GIPSE_TABLE = "db.gipse.table";
	
	public static final String PROPERTY_SERVICE_URL = "service.url";
	
	/**
	 * IBATIS specific, values must sync with gipse-query.xml config file.
	 */
	public static final String IBATIS_STATE_QUERY = "selectObservationsByState";
	public static final String IBATIS_STATE_AGE_QUERY = "selectObservationsByStateByAge";
	public static final String IBATIS_STATE_SERVICE_AREA_QUERY = "selectObservationsByStateByServiceArea";
	public static final String IBATIS_STATE_AGE_SERVICE_AREA_QUERY = "selectObservationsByStateByAgeByServiceArea";
	
	/**
	 * IBATIS specific, values must sync with gipse-query.xml config file.
	 */
	public static final String IBATIS_ZIP3_QUERY = "selectObservationsByZip3";
	public static final String IBATIS_ZIP3_AGE_QUERY = "selectObservationsByZip3ByAge";
	public static final String IBATIS_ZIP3_SERVICE_AREA_QUERY = "selectObservationsByZip3ByServiceArea";
	public static final String IBATIS_ZIP3_AGE_SERVICE_AREA_QUERY = "selectObservationsByZip3ByAgeByServiceArea";
	
	/**
	 * IBATIS specific, values must sync with gipse-query.xml config file.
	 */
	public static final String IBATIS_ZIP5_QUERY = "selectObservationsByZip5";
	public static final String IBATIS_ZIP5_AGE_QUERY = "selectObservationsByZip5ByAge";
	public static final String IBATIS_ZIP5_SERVICE_AREA_QUERY = "selectObservationsByZip5ByServiceArea";
	public static final String IBATIS_ZIP5_AGE_SERVICE_AREA_QUERY = "selectObservationsByZip5ByAgeByServiceArea";
	
}
