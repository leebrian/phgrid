package test.gov.cdc.ncphi.phgrid.gipse.services;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import com.ibatis.sqlmap.client.SqlMapClient;

import gov.cdc.ncphi.phgrid.gipse.dao.ObservationDAO;
import gov.cdc.ncphi.phgrid.gipse.dao.ObservationDAOImpl;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequest;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse;
import gov.cdc.ncphi.phgrid.gipse.message.MetadataQuery;
import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponse;
import gov.cdc.ncphi.phgrid.services.gipse.client.GIPSEServiceClient;
import gov.cdc.ncphi.phgrid.services.gipse.common.GIPSEServiceConstants;
import gov.cdc.ncphi.phgrid.services.gipse.common.AxisUtils;
import gov.cdc.ncphi.phgrid.services.gipse.service.dao.DatabaseManager;
import gov.cdc.ncphi.phgrid.services.gipse.service.dao.QueryParameters;
import gov.cdc.ncphi.phgrid.utils.DAOConfig;
import junit.framework.TestCase;

/**
 *
 * @project PHGrid (Apache License v2.0
 * @created May 29, 2009 9:52:43 PM
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
public class TestGIPSEService extends TestCase {
	
	private static final Logger LOGGER = Logger.getLogger(TestGIPSEService.class);
	
	public void testIbatisSqlMapClient() throws Exception{
		assertNotNull("SqlMapClient should not be null",DatabaseManager.getSqlMap());
	}
	
	public void testIbatisQuery() throws Exception{
		QueryParameters params = new QueryParameters();
		params.setStartDate(new java.sql.Date(new java.util.Date().getTime()-100000000l));
		params.setEndDate(new java.sql.Date(new java.util.Date().getTime()));
		params.setStates(new String[]{"RI","MA","CT","GA"});
		params.setClassifier("BioSense");
		params.setIndicators(new String[]{"Fever","Flavor"});
		SqlMapClient client = DatabaseManager.getSqlMap();
		List results = client.queryForList(ObservationDAOImpl.IBATIS_BASIC_QUERY, params);
		assertNotNull("results should not be null",results);
		LOGGER.debug("results <" + results.toString() + ">");
	}
	
	public void testIbatisQueryEmpty() throws Exception{
		QueryParameters params = new QueryParameters();
		params.setStartDate(new java.sql.Date(new java.util.Date().getTime()-100000000l));
		params.setEndDate(new java.sql.Date(new java.util.Date().getTime()));
		params.setStates(new String[]{"XX"});
		params.setClassifier("BioSense");
		params.setIndicators(new String[]{"Fever","Flavor"});
		SqlMapClient client = DatabaseManager.getSqlMap();
		List results = client.queryForList(ObservationDAOImpl.IBATIS_BASIC_QUERY, params);
		assertNotNull("results should not be null",results);
		assertEquals("results size should be zero",results.size(),0);
		LOGGER.debug("results <" + results.toString() + ">");
		
	}
	
	public void testMetadataQuery() throws Exception{
		  MetadataQuery query = new MetadataQuery();
		  MetadataQueryResponse response = buildServiceClient().queryMetadata(query);
		  assertNotNull("MetadataQueryResponse should not be null",response);
		  LOGGER.debug(AxisUtils.serializeAxisObject(response, false, true));
		
	}
	
	public void testServiceQueryByState() throws Exception{
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-StateQuery.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
		System.out.println("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
	}
	
	public void testServiceQueryByStateByAge() throws Exception{
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-StateAgeQuery.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
		System.out.println("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
	}
	
	public void testServiceQueryByStateByServiceArea() throws Exception{
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-StateServiceAreaQuery.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
		System.out.println("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
	}
	
	public void testServiceQueryByStateByAgeByServiceArea() throws Exception{
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-StateAgeServiceAreaQuery.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
	}
	
	public void testServiceQueryByStateWithDataSourceFilter() throws Exception{
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-StateDataSourceFilterQuery.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
	}
	
	public void testServiceQueryByZip3() throws Exception{
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-Zip3Query.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
		
	}
	
	public void testServiceQueryByZip3ByAge() throws Exception{
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-Zip3AgeQuery.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
		System.out.println("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
	}
	
	public void testServiceQueryByZip3ByServiceArea() throws Exception{
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-Zip3ServiceAreaQuery.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
		System.out.println("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
}
	
	public void testServiceQueryByZip3ByAgeByServiceArea() throws Exception{
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-Zip3AgeServiceAreaQuery.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
		System.out.println("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
	}
	
	public void testServiceQueryByZip5() throws Exception{
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-Zip5Query.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");	
		System.out.println("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
		
	}
	
	public void testServiceQueryByZip5ByAge() throws Exception{
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-Zip5AgeQuery.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
		System.out.println("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
	}
	
	public void testServiceQueryByZip5ByServiceArea() throws Exception{
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-Zip5ServiceAreaQuery.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
	}
	
	public void testServiceQueryByZip5ByAgeByServiceArea() throws Exception{
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-Zip5AgeServiceAreaQuery.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
	}
	
	private static final GIPSEServiceClient buildServiceClient() throws IOException{
		Properties props = new Properties();
		props.load(FileUtils.openInputStream(new File(GIPSEServiceConstants.GIPSE_PROPERTIES_FILE_NAME)));
		GIPSEServiceClient client = new GIPSEServiceClient(props.getProperty(GIPSEServiceConstants.PROPERTY_SERVICE_URL));
		client.setAnonymousPrefered(false);
		  
		return client;
		
	}
	
	public void testServiceWithProblem() throws Exception {
		String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-Lee.xml"));
		assertNotNull("Reading the test xml file should not be null", queryAsString);
		GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
		assertNotNull("Creating Axis request object should not be null", query);
		GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
		assertNotNull("We should have some response",response);
		LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
		System.out.println("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
	}


	
	public void testIbatisBasicQuery() throws Exception {
		QueryParameters params = new QueryParameters();
		Calendar startDate = Calendar.getInstance();
		startDate.set(Calendar.DAY_OF_MONTH, 01);
		startDate.set(Calendar.MONTH, Calendar.JANUARY);
		startDate.set(Calendar.YEAR, 2008);
		params.setStartDate(new java.sql.Date(startDate.getTimeInMillis()));
		Calendar endDate = Calendar.getInstance();
		endDate.set(Calendar.DAY_OF_MONTH, 01);
		endDate.set(Calendar.MONTH, Calendar.JANUARY);
		endDate.set(Calendar.YEAR, 2010);
		params.setEndDate(new java.sql.Date(endDate.getTimeInMillis()));
		//params.setStates(new String[]{"MI"});
		params.setZip3s(new String[] {"300"});
		params.setClassifier("BioSense");
		params.setIndicators(new String[]{"Asthma"});
		//SqlMapClient client = DatabaseManager.getSqlMap();
		 ObservationDAO client = (ObservationDAO) DAOConfig.getDaoManager().getDao(ObservationDAO.class);

		List results = client.runAggregateQuery(params);
		assertNotNull("results should not be null",results);
		System.out.println("got " + results.size() + " results!");
		for (Object o : results) {
			System.out.println("obs  = " + o);
		}
	}
//	public void testServiceQueryByState() throws Exception{
//	String queryAsString = IOUtils.toString(this.getClass().getResourceAsStream("/GIPSEQueryRequest-ForTest-StateQuery.xml"));
//	assertNotNull("Reading the test xml file should not be null", queryAsString);
//	GIPSEQueryRequest query = (GIPSEQueryRequest) AxisUtils.deserializeAxisObject(queryAsString, GIPSEQueryRequest.class);
//	assertNotNull("Creating Axis request object should not be null", query);
//	GIPSEQueryResponse response = buildServiceClient().queryGIPSE(query);
//	assertNotNull("We should have some response",response);
//	LOGGER.debug("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
//	System.out.println("response serialized for testing<" + AxisUtils.serializeAxisObject(response, true, true) + ">");
//}
//

}
