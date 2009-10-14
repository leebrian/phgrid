package gov.cdc.ncphi.phgrid.gipse.dbimporter;


import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import twitter4j.Twitter;
import twitter4j.TwitterException;

import junit.framework.TestCase;
import junit.framework.Test;
import junit.framework.TestSuite;

/**
 * Basic tests for dbimporter project.
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.11 1445-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
public class AppTest extends TestCase {
	
	private static final Logger LOGGER = Logger.getLogger(AppTest.class);
	
	
	public static Test suite(){
		return new TestSuite(AppTest.class);
	}
	
	/**
	 * Tests that the necessary properties are present
	 * @throws IOException if there's a problem reading properties
	 */
	public void testProperties() throws IOException {
		LOGGER.debug("importerProps:" + ImporterUtils.getProperties());
		assertNotNull("Properties file <" + GIPSEConstants.PROPERTIES_FILE_NAME + "> should not be null", ImporterUtils.getProperties());
		assertNotNull("Property <" + GIPSEConstants.PROPERTY_DRIVER + "> should not be null", ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_DRIVER));
		assertNotNull("Property <" + GIPSEConstants.PROPERTY_PASS + "> should not be null", ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_PASS));
		assertNotNull("Property <" + GIPSEConstants.PROPERTY_URL + "> should not be null", ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_URL));
		assertNotNull("Property <" + GIPSEConstants.PROPERTY_USER + "> should not be null", ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_USER));
		assertNotNull("Property <" + GIPSEConstants.PROPERTY_WORKING_DIRECTORY + "> should not be null", ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_WORKING_DIRECTORY));
	}
	
	/**
	 * Tests that I can read the working directory
	 */
	public void testWorkingFolder(){
		File workingDir = new File(ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_WORKING_DIRECTORY));
		assertTrue("workingDir <" + ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_WORKING_DIRECTORY) + ">should exist, if not, please specify property <" + GIPSEConstants.PROPERTY_WORKING_DIRECTORY +">",workingDir.exists());
		assertTrue("workingDir <" + ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_WORKING_DIRECTORY) + ">should be writable",workingDir.exists());
	}
	
	/**
	 * Tests that db parameters are valid (e.g. can I connect to specified db). 
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public void testDBConnectivity() throws ClassNotFoundException, SQLException{
		Connection conn = DBUtils.getConnection();
		assertNotNull("Connection should not be null.",conn);
		conn.close();
	}
	
	/**
	 * Tests that we can execute a select against db.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void testReadFromDB() throws ClassNotFoundException, SQLException{
		Connection conn = DBUtils.getConnection();
		DBUtils.selectRecord(new GIPSERecord(0, new Date(),"","",0,0), conn);//this won't find anything, but shouldn't throw an error
		conn.close();
		assertTrue(true);
		
	}
	
	/**
	 * Tests the line parsing routines for BioSense extract lines.
	 */
	public void testBuildRecordFromBioSenseCV(){
		String line = "DOD,01/20/2009,01821,MA,19,\"Outpatient, Final Diagnosis\",1,Syndrome,92,Chronic Disease,999";
		GIPSERecord record = GIPSERecordBuilder.buildGIPSERecordFromBioSenseExtract(line);
		assertEquals(record.getState(),"MA");
		assertEquals(record.getZip5(),"01821");
	}
	
	/**
	 * Tests that the twitter id and password are valid.
	 * @throws TwitterException 
	 */
	public void testTwitterCredentials(){
		String twitterUser = ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_TWITTER_USER);
		if (StringUtils.isNotEmpty(twitterUser)){
			try {
				Twitter twitter = new Twitter(twitterUser,ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_TWITTER_PASS));
				assertNotNull("twitter credentials should be valid.",twitter.verifyCredentials());
			} catch (TwitterException e) {
				String exceptionMessage = "Test failed because of twitter exception <" + e.getMessage() + ">"; 
				LOGGER.warn(exceptionMessage, e);
				assertTrue(exceptionMessage, false);

			}
		}
	}
	
	/*
	 * This record isn't always in db, so can't always check for it.
	public void testSelectRecordFromDB() throws ClassNotFoundException, SQLException{
		String line = "DOD,01/20/2009,01821,MA,19,\"Outpatient, Final Diagnosis\",1,Syndrome,92,Chronic Disease,999";
		GIPSERecord record = GIPSERecordBuilder.buildGIPSERecordFromBioSenseExtract(line);
		LOGGER.debug("record<" + record.toString());
		Connection conn = DBUtils.getConnection();
		GIPSERecord selectedRecord = DBUtils.selectRecord(record, conn);
		LOGGER.debug("selectedRecord<" + selectedRecord.toString());
		conn.close();
	}
	*/
}
