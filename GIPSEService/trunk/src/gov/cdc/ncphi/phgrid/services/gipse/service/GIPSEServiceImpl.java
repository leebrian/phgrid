package gov.cdc.ncphi.phgrid.services.gipse.service;

import gov.cdc.ncphi.phgrid.gipse.dao.ObservationDAO;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequest;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsAges;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsAgesAge;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsDataSources;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsDates;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsGeoRegions;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocations;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsServiceAreas;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsSuppressValues;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestRequestCharacteristics;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseObservationSet;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseObservationSetObservation;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristics;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsAges;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsAgesAge;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsDataSources;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsDates;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsGeoRegions;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegion;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocations;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionType;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsIndicators;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsIndicatorsIndicator;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsServiceAreas;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsServiceAreasServiceArea;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristicsSuppressValues;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseResponseCharacteristics;
import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponse;
import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordIntervalsSupportedInterval;
import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordSuppressValues;
import gov.cdc.ncphi.phgrid.services.gipse.common.AxisUtils;
import gov.cdc.ncphi.phgrid.services.gipse.service.dao.Observation;
import gov.cdc.ncphi.phgrid.services.gipse.service.dao.QueryParameters;
import gov.cdc.ncphi.phgrid.utils.DAOConfig;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;

import org.apache.log4j.Logger;

/** 
 * Main service implementation for GIPSE service.
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class GIPSEServiceImpl extends GIPSEServiceImplBase {
	private static final Logger LOGGER = Logger.getLogger(GIPSEServiceImpl.class);
	
	private static MetadataQueryResponse response = null;
	  
	  private static String defaultInterval = null;
	  private static ObservationDAO dao = (ObservationDAO) DAOConfig.getDaoManager().getDao(ObservationDAO.class);

	
	public GIPSEServiceImpl() throws RemoteException {
		super();
	}
	
	/**
	 * The metadataquery response is stored as a static var to reduce overhead of reading and deserializing metadata for every request.
	 * @return a non-null response
	 * @throws Exception if anything goes wrong parsing the metadata query response
	 */
	private static final MetadataQueryResponse getMetadataQueryResponse() throws Exception{
		if (response == null){//only parse the response back once
			String metadataAsString = FileUtils.readFileToString(new File(GIPSEServiceConfiguration.getConfiguration().getEtcDirectoryPath() + File.separator + "MetadataResponseExample.xml"));
			response = (MetadataQueryResponse)AxisUtils.deserializeAxisObject(metadataAsString, MetadataQueryResponse.class);
		}
		return response;
	}
	  
	  /**
	   * Lazy load the defaultInterval property based on the metadata record for the service.
	   * @return
	   * @throws Exception
	   */
	  private static final String determineDefaultInterval() throws Exception{
		  if (defaultInterval == null){
			  MetadataQueryResponse metadata = getMetadataQueryResponse();
			  MetadataQueryResponseMetadataRecordIntervalsSupportedInterval[] intervals = metadata.getMetadataRecord().getIntervalsSupported().getInterval();
			  for (MetadataQueryResponseMetadataRecordIntervalsSupportedInterval interval : intervals){
				  boolean isDefault = interval.getIsDefault();
				  if (isDefault){
					  defaultInterval = interval.getName();
					  break;
				  }
			  }
			//if for some reason, the service is misconfigured then default to "day". 
			  if (defaultInterval == null){
				  defaultInterval = "day";
				  LOGGER.debug("ServiceMetadata is misconfigured, so default interval is set to \"day\"");
			  }
		  }
		  return defaultInterval;
	  }
	
  public gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponse queryMetadata(gov.cdc.ncphi.phgrid.gipse.message.MetadataQuery query) throws RemoteException {
	  try{
		  if (LOGGER.isDebugEnabled()){
			  LOGGER.debug("Metadata request" + AxisUtils.serializeAxisObject(query, true, true));
		  }
		  
		  return getMetadataQueryResponse();
	  }catch (Exception e){
		  LOGGER.error("[queryMetadata]" + e.getMessage(),e);
		  throw new RemoteException(e.getMessage(),e);
	  }
  }

  /**
   * Note, does not validate input, so make sure you populate request properly or you'll get lots of exceptions (think NPEs). Also does not support
   * multi-classifier queries so stick with only one classifier (which makes sense since most services should only really support one classifier for now).
   * Also only supports one region type query (either query by state, query by zip3 or query by zip5 so don't send in multiple region types yet).
   * @param query
   * @return
   * @throws RemoteException
   */
  public gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse queryGIPSE(gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequest query) throws RemoteException {
     try {
		  if (LOGGER.isDebugEnabled()){
			  LOGGER.debug("gipse query" + AxisUtils.serializeAxisObject(query, true, true));
		  }
  
		  GIPSEQueryResponse response = new GIPSEQueryResponse();
		  prepareResponse(query, response);
		  if (LOGGER.isDebugEnabled()){
			  LOGGER.debug("gipse response-pre query" + AxisUtils.serializeAxisObject(response, true, true));
		  }
		  
		  List<Observation> sqlResults = runQuery(query);

		  populateResponseWithObservations(response, sqlResults);

		  if (LOGGER.isDebugEnabled()){
			LOGGER.debug("gipse response about to be returned" + AxisUtils.serializeAxisObject(response, true, true));
		  }
		return response;
	} catch (Exception e) {
		  LOGGER.error("[queryGIPSE]" + e.getMessage(),e);
		  throw new RemoteException(e.getMessage(),e);
	}
  }
  
  /**
   * Sets the observation set within the response based on the list of observations passed in.
   * @param response
   * @param observationList
   */
  private static void populateResponseWithObservations(GIPSEQueryResponse response, List<Observation> observationList){
	  GIPSEQueryResponseObservationSet observationSet = new GIPSEQueryResponseObservationSet();
	  GIPSEQueryResponseObservationSetObservation[] observationArray = new GIPSEQueryResponseObservationSetObservation[observationList.size()];
	  int i=0;
	  for (Observation observation : observationList){
		  GIPSEQueryResponseObservationSetObservation observationForResult = new GIPSEQueryResponseObservationSetObservation();
		  observationForResult.setStart(observation.getObservationDate());
		  if (StringUtils.isNotEmpty(observation.getZip5())){
			  observationForResult.setLocation(observation.getZip5());  
		  }else if(StringUtils.isNotEmpty(observation.getZip3())){
			  observationForResult.setLocation(observation.getZip3());
		  }else{
			  observationForResult.setLocation(observation.getState());
		  }
		  observationForResult.setIndicator(observation.getIndicator());
		  observationForResult.setAge(observation.getAge());
		  observationForResult.setServiceArea(observation.getServiceArea());
		  observationForResult.setValue(Integer.toString(observation.getValue()));
		  observationArray[i++] = observationForResult;
	  }
	  
	  observationSet.setObservation(observationArray);	  
	  
	  response.setObservationSet(observationSet);
  }
  
  /**
   * Determines the appropriate query type to run, executes the query and returns a non-null List of Observation objects.
   * @param request a request object - used to determine query parameters
   * @return List of gov.cdc.ncphi.phgrid.services.gipse.common.dao.Observation objects, doesn't use generics because ibatis doesn't use them.
   * @throws IOException 
   * @throws SQLException 
   */
  private static List<Observation> runQuery(GIPSEQueryRequest request) throws Exception{
	  QueryParameters parameters = buildQueryParameters(request);
	  List<Observation>  returnList = dao.runAggregateQuery(parameters);
	  return returnList;
}
  
  /**
   * MSC - This is the old implementation for runQuery. 
   */
//SqlMapClient client = DatabaseManager.getSqlMap();
//  List<Observation> returnList = null;
//  
//  QueryParameters parameters = buildQueryParameters(request);
  //returnList = client.queryForList(GIPSEServiceConstants.IBATIS_BASIC_QUERY,  parameters);
	  //zip5 query
//	  if (parameters.getZip5s() != null && parameters.getZip5s().length >0){
//		  if (parameters.getAges() != null && parameters.getAges().length > 0
//				  && parameters.getServiceAreas() != null && parameters.getServiceAreas().length > 0){
//			  if (LOGGER.isDebugEnabled()){
//				  LOGGER.debug("Running zip5 query by age by service area with these parameters:" + parameters);
//			  }
//			  returnList = client.queryForList(GIPSEServiceConstants.IBATIS_ZIP5_AGE_SERVICE_AREA_QUERY, parameters);
//		  }else if (parameters.getAges() != null && parameters.getAges().length > 0){
//			  if (LOGGER.isDebugEnabled()){
//				  LOGGER.debug("Running zip5 query by age with these parameters:" + parameters);
//			  }
//			  returnList = client.queryForList(GIPSEServiceConstants.IBATIS_ZIP5_AGE_QUERY, parameters);
//		  }else if(parameters.getServiceAreas() != null && parameters.getServiceAreas().length > 0){
//			  if (LOGGER.isDebugEnabled()){
//				  LOGGER.debug("Running zip5 query by service area with these parameters:" + parameters);
//			  }
//			  returnList = client.queryForList(GIPSEServiceConstants.IBATIS_ZIP5_SERVICE_AREA_QUERY, parameters);
//		  }else{
//			  if (LOGGER.isDebugEnabled()){
//				  LOGGER.debug("Running zip5 query with these parameters:" + parameters);
//			  }
//			  returnList = client.queryForList(GIPSEServiceConstants.IBATIS_ZIP5_QUERY, parameters);
//		  }
//	  //zip3 query
//	  }else if (parameters.getZip3s() != null && parameters.getZip3s().length >0){
//		  if (parameters.getAges() != null && parameters.getAges().length > 0
//				  && parameters.getServiceAreas() != null && parameters.getServiceAreas().length > 0){
//			  if (LOGGER.isDebugEnabled()){
//				  LOGGER.debug("Running zip3 query by age by service area with these parameters:" + parameters);
//			  }
//			  returnList = client.queryForList(GIPSEServiceConstants.IBATIS_ZIP3_AGE_SERVICE_AREA_QUERY, parameters);
//		  }else if (parameters.getAges() != null && parameters.getAges().length > 0){
//			  if (LOGGER.isDebugEnabled()){
//				  LOGGER.debug("Running zip3 query by age with these parameters:" + parameters);
//			  }
//			  returnList = client.queryForList(GIPSEServiceConstants.IBATIS_ZIP3_AGE_QUERY, parameters);
//		  }else if(parameters.getServiceAreas() != null && parameters.getServiceAreas().length > 0){
//			  if (LOGGER.isDebugEnabled()){
//				  LOGGER.debug("Running zip3 query by service area with these parameters:" + parameters);
//			  }
//			  returnList = client.queryForList(GIPSEServiceConstants.IBATIS_ZIP3_SERVICE_AREA_QUERY, parameters);
//		  }else{
//			  if (LOGGER.isDebugEnabled()){
//				  LOGGER.debug("Running zip3 query with these parameters:" + parameters);
//			  }
//			  returnList = client.queryForList(GIPSEServiceConstants.IBATIS_ZIP3_QUERY, parameters);
//		  }
//	  //state query
//	  }else if(parameters.getStates() != null && parameters.getStates().length >0){
//		  if (parameters.getAges() != null && parameters.getAges().length > 0
//				  && parameters.getServiceAreas() != null && parameters.getServiceAreas().length > 0){
//			  if (LOGGER.isDebugEnabled()){
//				  LOGGER.debug("Running state query by age by service area with these parameters:" + parameters);
//			  }
//			  returnList = client.queryForList(GIPSEServiceConstants.IBATIS_STATE_AGE_SERVICE_AREA_QUERY, parameters);
//		  }else if (parameters.getAges() != null && parameters.getAges().length > 0){
//			  if (LOGGER.isDebugEnabled()){
//				  LOGGER.debug("Running state query by age with these parameters:" + parameters);
//			  }
//			  returnList = client.queryForList(GIPSEServiceConstants.IBATIS_STATE_AGE_QUERY, parameters);
//		  }else if(parameters.getServiceAreas() != null && parameters.getServiceAreas().length > 0){
//			  if (LOGGER.isDebugEnabled()){
//				  LOGGER.debug("Running state query by service area with these parameters:" + parameters);
//			  }
//			  returnList = client.queryForList(GIPSEServiceConstants.IBATIS_STATE_SERVICE_AREA_QUERY, parameters);
//		  }else{
//			  if (LOGGER.isDebugEnabled()){
//				  LOGGER.debug("Running state query with these parameters:" + parameters);
//			  }
//			  returnList = client.queryForList(GIPSEServiceConstants.IBATIS_STATE_QUERY, parameters);
//		  }
//	  }else{
//		  LOGGER.warn("The query parameter is not properly set up, empty list returned. QueryParameters<" + parameters +">");
//		  returnList = new LinkedList<Observation>();
//	  }
//
//	  if (LOGGER.isDebugEnabled()){
//		LOGGER.debug("query results" + returnList);
//	  }
//	  
//	  return returnList;
//  }
  
  /**
   * Builds the ibatis query parameters based on submitted query.
   * @param request
   * @return
 * @throws Exception 
   */
  private static QueryParameters buildQueryParameters(GIPSEQueryRequest request) throws Exception{
	  QueryParameters parameters = new QueryParameters();
	  
	  parameters.setStartDate(new java.sql.Date(request.getQueryCharacteristics().getDates().getStart().getTime()));
	  parameters.setEndDate(new java.sql.Date(request.getQueryCharacteristics().getDates().getEnd().getTime()));
	  
	  GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator[] indicatorArray = request.getQueryCharacteristics().getIndicators().getIndicator();
	  String [] queryIndicatorArray = new String[indicatorArray.length];
	  int i = 0;
	  for (GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator indicator : indicatorArray){
		  queryIndicatorArray[i++] = indicator.getName();
	  }
	  
	  parameters.setClassifier(indicatorArray[0].getClassifier());
	  parameters.setIndicators(queryIndicatorArray);
	  
	  List<String> stateList = new LinkedList<String>();
	  List<String> zip3List = new LinkedList<String>();
	  List<String> zip5List = new LinkedList<String>();
	  GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion[] geoRegionArray = request.getQueryCharacteristics().getGeoRegions().getGeoRegion();
	  for (GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion geoRegion : geoRegionArray){
		  String regionType = geoRegion.getType().getValue();
		  String regionValue = geoRegion.getValue();
		  regionValue = regionValue.replace('*', '%');
		  if (regionType.equals(GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType._state)){
			  stateList.add(regionValue);
		  }else if(regionType.equals(GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType._zip3)){
			  zip3List.add(regionValue);
		  }else if(regionType.equals(GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType._zip5)){
			  zip5List.add(regionValue);
		  }else if(regionType.equals(GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionType._country)){
			  //ignore for now, doesn't affect query
		  }else{
			  String errorMessage = "Unknown GeoRegion type<" + regionType +">.";
			  LOGGER.error(errorMessage);
			  throw new IllegalArgumentException(errorMessage);
		  }
		  
		  GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocations specificLocations = geoRegion.getSpecificLocations();
		  if (specificLocations != null){
			  GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation[] locationArray = specificLocations.getGeoLocation();
			  for (GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation location : locationArray){
				  String locationType = location.getType().getValue();
				  String locationValue = location.getValue();
				  locationValue = locationValue.replace('*', '%');
				  if (locationType.equals(GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType._state)){
					  stateList.add(locationValue);
				  }else if(locationType.equals(GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType._zip3)){
					  zip3List.add(locationValue);
				  }else if(locationType.equals(GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType._zip5)){
					  zip5List.add(locationValue);
				  }else if(locationType.equals(GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType._country)){
					  //ignore for now, doesn't affect query
				  }else{
					  String errorMessage = "Unknown GeoLocation type<" + locationType +">.";
					  LOGGER.error(errorMessage);
					  throw new IllegalArgumentException(errorMessage);
				  }
				  
			  }
		  }
	  }
	  

	  List<String> dataSourceList = new LinkedList<String>();
	  GIPSEQueryRequestQueryCharacteristicsDataSources dataSources = request.getQueryCharacteristics().getDataSources();
	  if (dataSources != null){
		  String[] dataSourceArray =  dataSources.getDataSourceOID();
		  for (String dataSource : dataSourceArray){
			  dataSourceList.add(dataSource);
		  }
	  }
	  
	  List<String> ageList = new LinkedList<String>();
	  GIPSEQueryRequestQueryCharacteristicsAges ages = request.getQueryCharacteristics().getAges();
	  if (ages != null){
		  GIPSEQueryRequestQueryCharacteristicsAgesAge[] ageArray = ages.getAge();
		  for (GIPSEQueryRequestQueryCharacteristicsAgesAge age : ageArray){
			  ageList.add(age.getName());
		  }
	  }
	  
	  List<String> serviceAreaList = new LinkedList<String>();
	  GIPSEQueryRequestQueryCharacteristicsServiceAreas serviceAreas = request.getQueryCharacteristics().getServiceAreas();
	  if (serviceAreas != null){
		  GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea[] serviceAreaArray = serviceAreas.getServiceArea();
		  for (GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea serviceArea : serviceAreaArray){
			  serviceAreaList.add(serviceArea.getName());
		  }
	  }
	  
	  
	  
	  parameters.setStates((String[]) stateList.toArray(new String[0]));
	  parameters.setZip3s((String[]) zip3List.toArray(new String[0]));
	  parameters.setZip5s((String[]) zip5List.toArray(new String[0]));
	  parameters.setDataSources((String[]) dataSourceList.toArray(new String[0]));
	  parameters.setServiceAreas((String[]) serviceAreaList.toArray(new String[0]));
	  parameters.setAges((String[]) ageList.toArray(new String[0]));
	  
	  return parameters;
  }
  
  /**
   * 
   * @param request
 * @throws Exception 
   */
  private static void prepareResponse(GIPSEQueryRequest request,GIPSEQueryResponse response) throws Exception{
	  populateResponseCharacteristics(request, response);
	  populateQueryCharacteristics(request,response);
  }
  
  /**
   * Populate the response characteristics based on the values in the request. 
   * @param request
   * @throws Exception
   */
  private static final void populateResponseCharacteristics(GIPSEQueryRequest request,GIPSEQueryResponse response) throws Exception{
	  GIPSEQueryRequestRequestCharacteristics requestChars = request.getRequestCharacteristics();
	  GIPSEQueryResponseResponseCharacteristics responseChars = new GIPSEQueryResponseResponseCharacteristics();
	  responseChars.setRequestDateTime(requestChars.getRequestDateTime());
	  responseChars.setResponseDateTime(Calendar.getInstance());
	  responseChars.setRequestingUser(requestChars.getRequestingUser());
	  responseChars.setRequestingOrganization(requestChars.getRequestingOrganization());
	  responseChars.setDataSourceOID(getMetadataQueryResponse().getMetadataRecord().getDataSourceOID());
	  responseChars.setDataSourceTimeZone(TimeZone.getDefault().getID());
	  response.setResponseCharacteristics(responseChars);
  }
  
  /**
   * Populates the Response query characteristics with either the request query characteristics or the request's query id (if it is not null)
   * @param request
   * @param response
   * @throws Exception
   */
  private static final void populateQueryCharacteristics(GIPSEQueryRequest request,GIPSEQueryResponse response) throws Exception{
	  GIPSEQueryResponseQueryCharacteristics queryChars = new GIPSEQueryResponseQueryCharacteristics();
	  String queryID = request.getQueryCharacteristics().getQueryID();
	  if (queryID != null){
		  queryChars.setQueryID(queryID);
	  }else{
		  populateInterval(request, queryChars);
		  populateSuppressValues(request, queryChars);
		  //ignore paging for now
		  populateIndicators(request, queryChars);
		  populateGeoRegions(request,queryChars);
		  populateDataSources(request,queryChars);
		  populateServiceAreas(request, queryChars);
		  populateAges(request,queryChars);
	  }
	  response.setQueryCharacteristics(queryChars);
	  
  }   
  
  /**
   * Populates the ages in response based on request (if <Ages> is not null).
   * @param request
   * @param queryChars
   * @throws Exception
   */
  private static final void populateAges(GIPSEQueryRequest request, GIPSEQueryResponseQueryCharacteristics queryChars) throws Exception{
	  GIPSEQueryRequestQueryCharacteristicsAges requestAges = request.getQueryCharacteristics().getAges();
	  if (requestAges != null){
		  GIPSEQueryResponseQueryCharacteristicsAges responseAges = new GIPSEQueryResponseQueryCharacteristicsAges();
		  GIPSEQueryRequestQueryCharacteristicsAgesAge[] requestAgeArray = requestAges.getAge();
		  if (requestAgeArray != null){
			  GIPSEQueryResponseQueryCharacteristicsAgesAge[] responseAgeArray = new GIPSEQueryResponseQueryCharacteristicsAgesAge[requestAgeArray.length];
			  int i=0;
			  for (GIPSEQueryRequestQueryCharacteristicsAgesAge requestAge : requestAgeArray){
				  GIPSEQueryResponseQueryCharacteristicsAgesAge responseAge =
					  new GIPSEQueryResponseQueryCharacteristicsAgesAge(requestAge.getClassifier(),requestAge.getName());
				  responseAgeArray[i++] = responseAge;
			  }
			  responseAges.setAge(responseAgeArray);
			  queryChars.setAges(responseAges);
		  }
	  }
  }
  
  /**
   * Populates the service areas in response based on request (if <ServiceAreas> is not null).
   * @param request
   * @param queryChars
   * @throws Exception
   */
  private static final void populateServiceAreas(GIPSEQueryRequest request, GIPSEQueryResponseQueryCharacteristics queryChars) throws Exception{
	  GIPSEQueryRequestQueryCharacteristicsServiceAreas requestServiceAreas = request.getQueryCharacteristics().getServiceAreas();
	  if (requestServiceAreas != null){
		  GIPSEQueryResponseQueryCharacteristicsServiceAreas responseServiceAreas = new GIPSEQueryResponseQueryCharacteristicsServiceAreas();
		  GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea[] requestServiceAreaArray = requestServiceAreas.getServiceArea();
		  if (requestServiceAreaArray != null){
			  GIPSEQueryResponseQueryCharacteristicsServiceAreasServiceArea[] responseServiceAreaArray = new GIPSEQueryResponseQueryCharacteristicsServiceAreasServiceArea[requestServiceAreaArray.length];
			  int i=0;
			  for (GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea requestServiceArea : requestServiceAreaArray){
				  GIPSEQueryResponseQueryCharacteristicsServiceAreasServiceArea responseServiceArea =
					  new GIPSEQueryResponseQueryCharacteristicsServiceAreasServiceArea(requestServiceArea.getCodeset(),requestServiceArea.getName());
				  responseServiceAreaArray[i++] = responseServiceArea;
			  }
			  responseServiceAreas.setServiceArea(responseServiceAreaArray);
			  queryChars.setServiceAreas(responseServiceAreas);
		  }
	  }
  }
  
  /**
   * Populates the data sources in response based on request (if <DataSources> is specified in the request).
   * @param request
   * @param queryChars
   * @throws Exception
   */
  private static final void populateDataSources(GIPSEQueryRequest request, GIPSEQueryResponseQueryCharacteristics queryChars) throws Exception{
	  GIPSEQueryRequestQueryCharacteristicsDataSources requestDataSources = request.getQueryCharacteristics().getDataSources();
	  if (requestDataSources != null){
		  GIPSEQueryResponseQueryCharacteristicsDataSources dataSources = new GIPSEQueryResponseQueryCharacteristicsDataSources();
		  dataSources.setDataSourceOID(requestDataSources.getDataSourceOID());
		  queryChars.setDataSources(dataSources);
	  }
  }
  
  /**
   * Determines the interval element that should go in the response. If the request does not specify an interval then the service's default is used.
   * @param request
   * @param queryChars
   * @throws Exception
   */
  private static final void populateInterval(GIPSEQueryRequest request,GIPSEQueryResponseQueryCharacteristics queryChars) throws Exception{
	  GIPSEQueryResponseQueryCharacteristicsDates responseDates = new GIPSEQueryResponseQueryCharacteristicsDates();
	  GIPSEQueryRequestQueryCharacteristicsDates requestDates = request.getQueryCharacteristics().getDates();
	  String requestInterval = requestDates.getInterval();
	  if (requestInterval == null){
		  requestInterval = determineDefaultInterval();
	  }
	  responseDates.setInterval(requestInterval);
	  responseDates.setEnd(requestDates.getEnd());
	  responseDates.setStart(requestDates.getStart());
	  queryChars.setDates(responseDates);
	  
  }
  
  /**
   * Populates the suppress values element based on the request and service metadata. If the request doesn't have suppress values, 
   * and the service metadata value is not null then the service metadata value is used. If the request smallest value is larger than the metadata
   * value, then the metadata value is used (request can't be more lenient than the service).
   * @param request
   * @param queryChars
   * @throws Exception
   */
  private static final void populateSuppressValues(GIPSEQueryRequest request,GIPSEQueryResponseQueryCharacteristics queryChars) throws Exception{
	  GIPSEQueryRequestQueryCharacteristicsSuppressValues requestSuppressValues = request.getQueryCharacteristics().getSuppressValues();
	  MetadataQueryResponseMetadataRecordSuppressValues metadataSuppressValues = getMetadataQueryResponse().getMetadataRecord().getSuppressValues();
	  GIPSEQueryResponseQueryCharacteristicsSuppressValues responseSuppressValues = new GIPSEQueryResponseQueryCharacteristicsSuppressValues();
	  if (requestSuppressValues == null){
		  if (metadataSuppressValues != null){
			  responseSuppressValues.setSmallestValueReported(metadataSuppressValues.getSmallestValueReported());
		  }
	  }else{
		  if (requestSuppressValues.getSmallestValueReported().compareTo(metadataSuppressValues.getSmallestValueReported()) > 0){
			  responseSuppressValues.setSmallestValueReported(metadataSuppressValues.getSmallestValueReported());
		  }else{
			  responseSuppressValues.setSmallestValueReported(requestSuppressValues.getSmallestValueReported());		  
		  }
	  }
	  if (responseSuppressValues.getSmallestValueReported() != null){
		  queryChars.setSuppressValues(responseSuppressValues);
	  }
	  
  }
  
  /**
   * Populates the indicators in response based on request.
   * @param request
   * @param queryChars
   * @throws Exception
   */
  private static final void populateIndicators(GIPSEQueryRequest request, GIPSEQueryResponseQueryCharacteristics queryChars) throws Exception{
	  GIPSEQueryResponseQueryCharacteristicsIndicators responseIndicators = new GIPSEQueryResponseQueryCharacteristicsIndicators();
	  GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator[] requestIndicatorArray = request.getQueryCharacteristics().getIndicators().getIndicator();
	  GIPSEQueryResponseQueryCharacteristicsIndicatorsIndicator[] responseIndicatorArray = new GIPSEQueryResponseQueryCharacteristicsIndicatorsIndicator[requestIndicatorArray.length];
	  int i = 0;
	  for (GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator requestIndicator : requestIndicatorArray){
		  GIPSEQueryResponseQueryCharacteristicsIndicatorsIndicator responseIndicator = new GIPSEQueryResponseQueryCharacteristicsIndicatorsIndicator();
		  responseIndicator.setClassifier(requestIndicator.getClassifier());
		  responseIndicator.setName(requestIndicator.getName());
		  responseIndicatorArray[i++] = responseIndicator;
	  }
	  responseIndicators.setIndicator(responseIndicatorArray);
	  queryChars.setIndicators(responseIndicators);
  }
  
  /**
   * Populated the georegions in the response based on the query passed in.
   * @param request
   * @param queryChars
   * @throws Exception
   */
  private static final void populateGeoRegions(GIPSEQueryRequest request, GIPSEQueryResponseQueryCharacteristics queryChars) throws Exception{
	  GIPSEQueryRequestQueryCharacteristicsGeoRegions requestGeoRegions = request.getQueryCharacteristics().getGeoRegions();
	  if (requestGeoRegions != null){
		  GIPSEQueryResponseQueryCharacteristicsGeoRegions responseGeoRegions = new GIPSEQueryResponseQueryCharacteristicsGeoRegions();
		  GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion[] requestGeoRegionArray = requestGeoRegions.getGeoRegion();
		  GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegion[] responseGeoRegionArray = new GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegion[requestGeoRegionArray.length];
		  
		  int i=0;//loop through all the georegions and copy them into response georegions
		  for (GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion requestGeoRegion : requestGeoRegionArray){
			  GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegion responseGeoRegion = new GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegion();
			  GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionType responseGeoRegionType = 
				  GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionType.fromValue(requestGeoRegion.getType().getValue());
			  responseGeoRegion.setType(responseGeoRegionType);
			  responseGeoRegion.setValue(requestGeoRegion.getValue());
			  
			  GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocations requestGeoRegionSpecificLocations = requestGeoRegion.getSpecificLocations();
			  if (requestGeoRegionSpecificLocations != null){//supported locations might be null
				  GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocations responseGeoRegionSpecificLocations = new GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocations();
				  GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation[] requestGeoRegionSpecificLocationGeoLocationArray =  requestGeoRegionSpecificLocations.getGeoLocation();
				  GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation[] responseGeoRegionSpecificLocationGeoLocationArray = new GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation[requestGeoRegionSpecificLocationGeoLocationArray.length];
				  int j=0;//loop through all the geolocations and copy them into response geolocations
				  for (GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation requestGeoLocation : requestGeoRegionSpecificLocationGeoLocationArray){
					  GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation responseGeoLocation = new GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation();
					  GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType responseGeoLocationType = 
						  GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType.fromValue(requestGeoLocation.getType().getValue());
					  responseGeoLocation.setType(responseGeoLocationType);
					  responseGeoLocation.setValue(requestGeoLocation.getValue());
					  responseGeoRegionSpecificLocationGeoLocationArray[j++] = responseGeoLocation;
				  }
				  responseGeoRegionSpecificLocations.setGeoLocation(responseGeoRegionSpecificLocationGeoLocationArray);
				  responseGeoRegion.setSpecificLocations(responseGeoRegionSpecificLocations);
			  }
			  responseGeoRegionArray[i++] = responseGeoRegion;
		  }
		  responseGeoRegions.setGeoRegion(responseGeoRegionArray);
		  queryChars.setGeoRegions(responseGeoRegions);
	  }
  }

}

