package gov.cdc.ncphi.phgrid.services.gipse.service.test;


import edu.pitt.rods.service.npds.client.CaseCountServiceDAO;
import edu.pitt.rods.service.npds.client.CaseCountServiceDAOImpl;
import edu.pitt.rods.service.npds.client.encoding.NewDataSet;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequest;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristics;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse;
import gov.cdc.ncphi.phgrid.gipse.message.MetadataQuery;
import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponse;
import gov.cdc.ncphi.phgrid.services.gipse.beans.Observation;
import gov.cdc.ncphi.phgrid.services.gipse.client.GIPSEPoisonClient;
import gov.cdc.ncphi.phgrid.services.gipse.common.AxisUtils;
import gov.cdc.ncphi.phgrid.services.gipse.common.GIPSEPoisonConstants;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.xalan.lib.sql.QueryParameter;



import junit.framework.TestCase;

public class TestGIPSEPoison extends TestCase {

	private static final Logger LOGGER = Logger.getLogger(TestGIPSEPoison.class);
	
	public void testMetadataQuery() throws Exception{
		  MetadataQuery query = new MetadataQuery();
		  MetadataQueryResponse response = buildServiceClient().queryMetadata(query);
		  assertNotNull("MetadataQueryResponse should not be null",response);
		  LOGGER.debug(AxisUtils.serializeAxisObject(response, false, true));
		
	}
	
	/*public void testGIPSEQuery() throws Exception{
		
		GIPSEQueryRequest query = new GIPSEQueryRequest();

		params.setStartDate(new java.sql.Date(new java.util.Date().getTime()-100000000l));
		params.setEndDate(new java.sql.Date(new java.util.Date().getTime()));
		params.setStates(new String[]{"RI","MA","CT","GA"});
		params.setClassifier("BioSense");
		params.setIndicators(new String[]{"Fever","Flavor"});
		SqlMapClient client = DatabaseManager.getSqlMap();
		List results = client.queryForList(GIPSEServiceConstants.IBATIS_STATE_QUERY, params);
		LOGGER.debug("results <" + results.toString() + ">");
		assertNotNull("results should not be null",results);
	}*/
	
	public void testNPDSClient() throws Exception{
		CaseCountServiceDAO dao = new CaseCountServiceDAOImpl();
		NewDataSet newDataSet = new NewDataSet();
		Observation observation = new Observation();
		List<Observation> returnList = null;
		
		Calendar startDate = Calendar.getInstance();
    	startDate.set(2008, 6, 7);
    	Calendar endDate = Calendar.getInstance();
    	endDate.set(2008, 7, 26);
    	Properties props = new Properties();
		props.load(FileUtils.openInputStream(new File(GIPSEPoisonConstants.GIPSEPOISON_PROPERTIES_FILE_NAME)));
    	String systemUser = props.getProperty(GIPSEPoisonConstants.NPDSUSERNAME);
		newDataSet = dao.extract(systemUser, 
				"8", 
				"CO",
				"", 
				startDate,
				endDate, 
				"0", 
				"24", 
				"",
				"TCV", 
				"", 
				"A");
		LOGGER.debug("newDataSet has " + newDataSet.getTable().size() + " items in the table");
		
		assertNotNull("newDataSet should not be null", newDataSet);
	}
	
	public void testServiceQueryState() throws Exception
	{
		GIPSEQueryRequest query = new GIPSEQueryRequest();
		GIPSEQueryResponse gqr = null;
	//	gqr = buildServiceClient().queryGIPSE(query);
		
		
		//assertNotNull("GIPSEQueryResponse should not be null", gqr);
	}
	
	private static final GIPSEPoisonClient buildServiceClient() throws Exception{
		Properties props = new Properties();
		props.load(FileUtils.openInputStream(new File(GIPSEPoisonConstants.GIPSEPOISON_PROPERTIES_FILE_NAME)));
		GIPSEPoisonClient client = new GIPSEPoisonClient(props.getProperty(GIPSEPoisonConstants.PROPERTY_SERVICE_URL));

		client.setAnonymousPrefered(false);
		  
		return client;
		
	}
}
