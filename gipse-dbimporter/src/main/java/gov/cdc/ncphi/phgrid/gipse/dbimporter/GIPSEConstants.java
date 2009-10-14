package gov.cdc.ncphi.phgrid.gipse.dbimporter;

/**
 * Class for holding constants used by the db importer project. Abstract as it is never instantiated.
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.11 1545-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 *
 */
public abstract class GIPSEConstants {
	public static final String PROPERTIES_FILE_NAME = "gipse.dbimporter.properties";
	public static final String DATA_SOURCES_PROPERTIES_FILE_NAME = "gipse.dbimporter.datasources.properties";
	public static final String CONDITIONS_PROPERTIES_FILE_NAME = "gipse.dbimporter.conditions.properties";
	public static final String PROPERTY_URL = "gipse.dbimporter.db.url";
	public static final String PROPERTY_DRIVER = "gipse.dbimporter.db.driver";
	public static final String PROPERTY_USER = "gipse.dbimporter.db.user";
	public static final String PROPERTY_PASS = "gipse.dbimporter.db.pass";
	public static final String PROPERTY_WORKING_DIRECTORY = "gipse.dbimporter.working.directory";
	public static final String PROPERTY_TWITTER_USER = "gipse.dbimporter.twitter.user";
	public static final String PROPERTY_TWITTER_PASS = "gipse.dbimporter.twitter.pass";
	public static final String PROPERTY_RESET_RANDOM = "gipse.dbimporter.resetrandom";
	public static final String PROPERTY_ENVIRONMENT_LABEL = "gipse.dbimporter.environment.label";
	
	public static final String IMPORT_JOBS_STATUS_STARTED = "started";
	public static final String IMPORT_JOBS_STATUS_ACTIVE = "active";
	public static final String IMPORT_JOBS_STATUS_COMPLETE = "complete";
	public static final String IMPORT_JOBS_STATUS_ERROR = "error";
}
