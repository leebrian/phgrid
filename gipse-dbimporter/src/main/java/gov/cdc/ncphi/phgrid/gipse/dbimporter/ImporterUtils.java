package gov.cdc.ncphi.phgrid.gipse.dbimporter;


import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.math.random.RandomData;
import org.apache.commons.math.random.RandomDataImpl;
import org.apache.log4j.Logger;

import twitter4j.Twitter;

/**
 * Helper methods for use in the importer application.
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.12 1455-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
public class ImporterUtils {
	private static final Logger LOGGER = Logger.getLogger(ImporterUtils.class);
	
	private static Properties importerProps = new Properties();
	private static Properties datasourcesProps = new Properties();
	private static Properties conditionsProps = new Properties();
	
	static{
		try {
			importerProps.load(ImporterUtils.class.getResourceAsStream("/" + GIPSEConstants.PROPERTIES_FILE_NAME));
		} catch (Throwable e) {
			LOGGER.error("Problem loading properties file </" + GIPSEConstants.PROPERTIES_FILE_NAME +">. Application will likely not run properly without any properties.",e);
		}
		try {
			datasourcesProps.load(ImporterUtils.class.getResourceAsStream("/" + GIPSEConstants.DATA_SOURCES_PROPERTIES_FILE_NAME));
		} catch (Throwable e) {
			LOGGER.error("Problem loading properties file </" + GIPSEConstants.DATA_SOURCES_PROPERTIES_FILE_NAME +">. All data sources will report as unknown.",e);
		}
		try {
			conditionsProps.load(ImporterUtils.class.getResourceAsStream("/" + GIPSEConstants.CONDITIONS_PROPERTIES_FILE_NAME));
		} catch (Throwable e) {
			LOGGER.error("Problem loading properties file </" + GIPSEConstants.CONDITIONS_PROPERTIES_FILE_NAME +">. All conditions will report as unnknown.",e);
		}
	}
	
	/**
	 * @return The program properties.
	 * @see GIPSEConstants for property names.
	 */
	public static final Properties getProperties(){
		return importerProps;
	}
	
	/**
	 * Checks against the gipse.dbimporter.datasources properties to determine the appropriate source id.
	 * @param sourceName
	 * @return appropriate code for the specified source name
	 * @throws DBImporterException if the source name does not match a key in the gipse.dbimporter.datasources properties or the value doesn't parse to an int
	 */
	public static final int determineDataSourceId(String sourceName) throws DBImporterException{
		String sourceIdAsString = datasourcesProps.getProperty(sourceName);
		try {
			return Integer.parseInt(sourceIdAsString);
		} catch (NumberFormatException e) {
			LOGGER.warn("sourceName <" + sourceName + "> is not a configured data source. Please check properties.");
			throw new DBImporterException("sourceName <" + sourceName + "> is not a configured data source. Please check properties.",e);
		}
	}
	
	/**
	 * Checks against the gipse.dbimporter.conditions properties to determine the appropriate condition id.
	 * @param conditionName
	 * @return appropriate condition id for the specified condition name
	 * @throws DBImporterException if the source name does not match a key in the gipse.dbimporter.conditions properties or the value doesn't parse to an int
	 */
	public static final int determineConditionId(String conditionName) throws DBImporterException{
		String conditionIdAsString = conditionsProps.getProperty(conditionName);
		try {
			return Integer.parseInt(conditionIdAsString);
		} catch (NumberFormatException e) {
			LOGGER.warn("conditionName <" + conditionName + "> is not a configured condition. Please check properties.");
			throw new DBImporterException("conditionName <" + conditionName + "> is not a configured condition. Please check properties.",e);
		}
	}
	
	/**
	 * Parse the count string into an int. If the gipse.dbimpoter.reset.random property is set to a number, 
	 * the count is compared to the reset random value. If they two values match, then the count is set to a random number between 0 and 100.
	 * @param count an integer in string form
	 * @return the count in int form
	 * @throws DBImporterException thrown if the count does not parse to a valid integer
	 */
	public static final int determineCount(String count) throws DBImporterException{
		int returnCount,resetRandomInt = Integer.MIN_VALUE;
		String resetRandomString = importerProps.getProperty(GIPSEConstants.PROPERTY_RESET_RANDOM);
		if (StringUtils.isNotBlank(resetRandomString)){
			try {
				resetRandomInt = Integer.parseInt(resetRandomString);
			} catch (NumberFormatException e) {
				LOGGER.warn("resetRandomString <" + resetRandomString +"> does not parse to an integer.");
			}
		}
		
		try {
			returnCount = Integer.parseInt(count);
			if (returnCount == resetRandomInt){
				RandomData randomData = new RandomDataImpl();
				returnCount = randomData.nextInt(0,100);
			}
		} catch (NumberFormatException e) {
			throw new DBImporterException("count <" + count +"> did not parse properly to an integer.",e);
		}
		
		return returnCount;
	}
	
	/**
	 * Posts a status update to twitter if the GIPSEConstnants.PROPERTY_TWITTER_USER is not null.
	 * @param message Only the first 140 characters are used. Everything else is chopped off.
	 */
	public static final void updateTwitter(String message){
		try {
			String twitterUser = getProperties().getProperty(GIPSEConstants.PROPERTY_TWITTER_USER);
			if (twitterUser != null){
				Twitter twitter = new Twitter(twitterUser,getProperties().getProperty(GIPSEConstants.PROPERTY_TWITTER_PASS));
				twitter.updateStatus(StringUtils.left(message,140));
			}
		} catch (Throwable e) {
			LOGGER.warn("[Method:updateTwitter] Problem occurred. Swallowed so as to not disrupt processing.",e);

		}
	}

}
