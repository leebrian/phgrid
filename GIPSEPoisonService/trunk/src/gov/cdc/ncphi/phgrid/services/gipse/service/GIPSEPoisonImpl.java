package gov.cdc.ncphi.phgrid.services.gipse.service;

import edu.pitt.rods.service.npds.client.CaseCountServiceDAO;
import edu.pitt.rods.service.npds.client.CaseCountServiceDAOImpl;
import edu.pitt.rods.service.npds.client.encoding.NewDataSet;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequest;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsDates;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsGeoRegions;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocations;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequestRequestCharacteristics;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseObservationSet;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseObservationSetObservation;
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseQueryCharacteristics;
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
import gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponseResponseCharacteristics;
import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponse;
import gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponseMetadataRecordIntervalsSupportedInterval;
import gov.cdc.ncphi.phgrid.services.gipse.common.AxisUtils;
import gov.cdc.ncphi.phgrid.services.gipse.common.GIPSEPoisonConstants;
import gov.cdc.ncphi.phgrid.services.gipse.service.GIPSEPoisonConfiguration;
import gov.cdc.ncphi.phgrid.services.gipse.beans.IndicatorValue;
import gov.cdc.ncphi.phgrid.services.gipse.beans.Observation;


import java.io.File;
import java.io.IOException;
import java.rmi.RemoteException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.3
 * 
 */
public class GIPSEPoisonImpl extends GIPSEPoisonImplBase {
	private static final Logger LOGGER = Logger
	.getLogger(GIPSEPoisonImpl.class);
	private static MetadataQueryResponse response = null;
	private static Properties defaultProps, stateByZip3Properties;
	
	
	public GIPSEPoisonImpl() throws RemoteException {
		super();
		try 
		{
			defaultProps = 	GIPSEPoisonServicePropertiesManager.getServiceProperties();
			//defaultProps.load(FileUtils.openInputStream(new File(GIPSEPoisonConstants.GIPSEPOISON_PROPERTIES_FILE_NAME)));
			stateByZip3Properties = GIPSEPoisonServicePropertiesManager.getStateByZip3Properties();
		}
		catch(Exception e)
		{
			LOGGER.error(e.getMessage(), e);
		}
	}
	public static SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	private static final String[] states = { "AL", "AK", "AS", "AZ", "AR",
			"CA", "CO", "CT", "DE", "DC", "FM", "FL", "GA", "GU", "HI", "ID",
			"IL", "IN", "IA", "KS", "KY", "LA", "ME", "MH", "MD", "MA", "MI",
			"MN", "MS", "MO", "MT", "NE", "NV", "NH", "NJ", "NM", "NY", "NC",
			"ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR", "RI", "SC", "SD",
			"TN", "TX", "UT", "VT", "VI", "VA", "WA", "WV", "WI", "WY" };
	private static String defaultInterval = null;

		
	public gov.cdc.ncphi.phgrid.gipse.message.MetadataQueryResponse queryMetadata(gov.cdc.ncphi.phgrid.gipse.message.MetadataQuery query) throws RemoteException {
   try{
	  if (response == null) {// only parse the response back once
			LOGGER.info(GIPSEPoisonConfiguration.getConfiguration()
					.getEtcDirectoryPath()
					+ File.separator + "MetadataResponseExample.xml");

			String metadataAsString = FileUtils.readFileToString(new File(
					GIPSEPoisonConfiguration.getConfiguration()
							.getEtcDirectoryPath()
							+ File.separator + "MetadataResponseExample.xml"));

			response = (MetadataQueryResponse) AxisUtils.deserializeAxisObject(
					metadataAsString, MetadataQueryResponse.class);
		}
		LOGGER.info(response);
		return response;
   }catch (Exception e)
   {
	   LOGGER.error(e.getMessage(), e);
	   throw new RemoteException(e.getMessage());
   }
    //throw new RemoteException("Not yet implemented");
  }

  /**
	 * 
	 * @param request
	 * @param observation
	 * @throws Exception
	 */
  private static void prepareResponse(GIPSEQueryRequest request,
			GIPSEQueryResponse response) throws Exception {
		populateResponseCharacteristics(request, response);
		populateQueryCharacteristics(request, response);
	}
	
	/**
	 * Populate the response characteristics based on the values in the request.
	 * 
	 * @param request
	 * @throws Exception
	 */
	private static final void populateResponseCharacteristics(
			GIPSEQueryRequest request, GIPSEQueryResponse response)
			throws Exception {
		GIPSEQueryRequestRequestCharacteristics requestChars = request
				.getRequestCharacteristics();
		GIPSEQueryResponseResponseCharacteristics responseChars = new GIPSEQueryResponseResponseCharacteristics();
		responseChars.setRequestDateTime(requestChars.getRequestDateTime());
		responseChars.setResponseDateTime(Calendar.getInstance());
		responseChars.setRequestingUser(requestChars.getRequestingUser());
		responseChars.setRequestingOrganization(requestChars
				.getRequestingOrganization());
		responseChars.setDataSourceOID(getMetadataQueryResponse()
				.getMetadataRecord().getDataSourceOID());
		responseChars.setDataSourceTimeZone(TimeZone.getDefault().getID());
		response.setResponseCharacteristics(responseChars);
	}
	
	/**
	 * The metadataquery response is stored as a static var to reduce overhead
	 * of reading and deserializing metadata for every request.
	 * 
	 * @return a non-null response
	 * @throws Exception
	 *             if anything goes wrong parsing the metadata query response
	 */
	private static final MetadataQueryResponse getMetadataQueryResponse()
			throws Exception {
		if (response == null) {// only parse the response back once
			LOGGER.info(GIPSEPoisonConfiguration.getConfiguration()
					.getEtcDirectoryPath()
					+ File.separator + "MetadataResponseExample.xml");

			String metadataAsString = FileUtils.readFileToString(new File(
					GIPSEPoisonConfiguration.getConfiguration()
							.getEtcDirectoryPath()
							+ File.separator + "MetadataResponseExample.xml"));

			response = (MetadataQueryResponse) AxisUtils.deserializeAxisObject(
					metadataAsString, MetadataQueryResponse.class);
		}
		LOGGER.info(response);
		return response;
	}
	
	/*
	 * NEED TO MODIFY THIS METHOD TO MATCH NEW NPDS REQ.
	 */
	private static final void populateQueryCharacteristics(
			GIPSEQueryRequest request, GIPSEQueryResponse response)
			throws Exception {
		GIPSEQueryResponseQueryCharacteristics queryChars = new GIPSEQueryResponseQueryCharacteristics();

		try {
			populateInterval(request, queryChars);

			populateSuppressValues(request, queryChars);

			populateIndicators(request, queryChars);

			populateGeoRegions(request, queryChars);

			populateServiceAreas(request, queryChars);

		} catch (Exception e) {
			LOGGER.info("populateQueryCharacterstics:" + e.getStackTrace());
		}

		response.setQueryCharacteristics(queryChars);
	}

	/**
	 * Determines the interval element that should go in the response. If the
	 * request does not specify an interval then the service's default is used.
	 * 
	 * @param request
	 * @param queryChars
	 * @throws Exception
	 */
	private static final void populateInterval(GIPSEQueryRequest request,
			GIPSEQueryResponseQueryCharacteristics queryChars) throws Exception {
		GIPSEQueryResponseQueryCharacteristicsDates responseDates = new GIPSEQueryResponseQueryCharacteristicsDates();
		GIPSEQueryRequestQueryCharacteristicsDates requestDates = request
				.getQueryCharacteristics().getDates();
		String requestInterval = requestDates.getInterval();
		if (requestInterval == null) {
			requestInterval = determineDefaultInterval();
		}
		responseDates.setInterval(requestInterval);
		responseDates.setEnd(requestDates.getEnd());
		responseDates.setStart(requestDates.getStart());

		queryChars.setDates(responseDates);

	}

	/**
	 * NOT CURRENTLY SUPPORT IN POICONDAI SERVICE Populates the suppress values
	 * element based on the request and service metadata. If the request doesn't
	 * have suppress values, and the service metadata value is not null then the
	 * service metadata value is used. If the request smallest value is larger
	 * than the metadata value, then the metadata value is used (request can't
	 * be more lenient than the service).
	 * 
	 * @param request
	 * @param queryChars
	 * @throws Exception
	 */
	private static final void populateSuppressValues(GIPSEQueryRequest request,
			GIPSEQueryResponseQueryCharacteristics queryChars) throws Exception {

		/*
		 * NOT SUPPORTED YET
		 * 
		 * AMDSQueryRequestQueryCharacteristicsSuppressValues
		 * requestSuppressValues =
		 * request.getQueryCharacteristics().getSuppressValues();
		 * MetadataQueryResponseMetadataRecordSuppressValues
		 * metadataSuppressValues =
		 * getMetadataQueryResponse().getMetadataRecord().getSuppressValues();
		 * AMDSQueryResponseQueryCharacteristicsSuppressValues
		 * responseSuppressValues = new
		 * AMDSQueryResponseQueryCharacteristicsSuppressValues();
		 * 
		 * 
		 * if (requestSuppressValues == null){ if (metadataSuppressValues !=
		 * null){
		 * responseSuppressValues.setSmallestValueReported(metadataSuppressValues
		 * .getSmallestValueReported());
		 * observation.setSmalledValueReported(metadataSuppressValues
		 * .getSmallestValueReported().intValue());
		 * 
		 * } }else{ if
		 * (requestSuppressValues.getSmallestValueReported().compareTo
		 * (metadataSuppressValues.getSmallestValueReported()) > 0){
		 * responseSuppressValues
		 * .setSmallestValueReported(metadataSuppressValues
		 * .getSmallestValueReported());
		 * observation.setSmalledValueReported(metadataSuppressValues
		 * .getSmallestValueReported().intValue());
		 * 
		 * }else{
		 * responseSuppressValues.setSmallestValueReported(requestSuppressValues
		 * .getSmallestValueReported());
		 * observation.setSmalledValueReported(metadataSuppressValues
		 * .getSmallestValueReported().intValue());
		 * 
		 * } } if (responseSuppressValues.getSmallestValueReported() != null){
		 * queryChars.setSuppressValues(responseSuppressValues); }
		 */

	}

	/**
	 * Populates the indicators in response based on request.
	 * 
	 * @param request
	 * @param queryChars
	 * @param observation
	 * @throws Exception
	 */
	private static final void populateIndicators(GIPSEQueryRequest request,
			GIPSEQueryResponseQueryCharacteristics queryChars) throws Exception {
		GIPSEQueryResponseQueryCharacteristicsIndicators responseIndicators = new GIPSEQueryResponseQueryCharacteristicsIndicators();
		GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator[] requestIndicatorArray = request
				.getQueryCharacteristics().getIndicators().getIndicator();
		GIPSEQueryResponseQueryCharacteristicsIndicatorsIndicator[] responseIndicatorArray = new GIPSEQueryResponseQueryCharacteristicsIndicatorsIndicator[requestIndicatorArray.length];

		int i = 0;
		for (GIPSEQueryRequestQueryCharacteristicsIndicatorsIndicator requestIndicator : requestIndicatorArray) {
			GIPSEQueryResponseQueryCharacteristicsIndicatorsIndicator responseIndicator = new GIPSEQueryResponseQueryCharacteristicsIndicatorsIndicator();
			responseIndicator.setClassifier(requestIndicator.getClassifier());
			//TODO:  FIND A BETTER WAY TO HANDLE SPACES
			responseIndicator.setName(requestIndicator.getName().replace(' ', '~'));
			responseIndicatorArray[i++] = responseIndicator;
		}

		responseIndicators.setIndicator(responseIndicatorArray);
		queryChars.setIndicators(responseIndicators);
	}

	private static final void populateServiceAreas(GIPSEQueryRequest request,
			GIPSEQueryResponseQueryCharacteristics queryChars) throws Exception {
		GIPSEQueryResponseQueryCharacteristicsServiceAreas responseServiceAreas = new GIPSEQueryResponseQueryCharacteristicsServiceAreas();
		GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea[] requestServiceAreaArray = request
				.getQueryCharacteristics().getServiceAreas().getServiceArea();
		GIPSEQueryResponseQueryCharacteristicsServiceAreasServiceArea[] responseServiceAreaArray = new GIPSEQueryResponseQueryCharacteristicsServiceAreasServiceArea[requestServiceAreaArray.length];

		int i = 0;
		for (GIPSEQueryRequestQueryCharacteristicsServiceAreasServiceArea requestServiceArea : requestServiceAreaArray) {
			GIPSEQueryResponseQueryCharacteristicsServiceAreasServiceArea responseServiceArea = new GIPSEQueryResponseQueryCharacteristicsServiceAreasServiceArea();
			responseServiceArea.setName(requestServiceArea.getName());
			responseServiceAreaArray[i++] = responseServiceArea;
		}

		responseServiceAreas.setServiceArea(responseServiceAreaArray);
		queryChars.setServiceAreas(responseServiceAreas);
	}

	/**
	 * Populated the georegions in the response based on the query passed in.
	 * 
	 * @param request
	 * @param queryChars
	 * @param observation
	 * @throws Exception
	 */
	private static final void populateGeoRegions(GIPSEQueryRequest request,
			GIPSEQueryResponseQueryCharacteristics queryChars) throws Exception {
		GIPSEQueryRequestQueryCharacteristicsGeoRegions requestGeoRegions = request
				.getQueryCharacteristics().getGeoRegions();
		GIPSEQueryResponseQueryCharacteristicsGeoRegions responseGeoRegions = new GIPSEQueryResponseQueryCharacteristicsGeoRegions();
		GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion[] requestGeoRegionArray = requestGeoRegions
				.getGeoRegion();
		GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegion[] responseGeoRegionArray = new GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegion[requestGeoRegionArray.length];

		int i = 0;// loop through all the georegions and copy them into response
					// georegions
		for (GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegion requestGeoRegion : requestGeoRegionArray) {
			GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegion responseGeoRegion = new GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegion();

			GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionType responseGeoRegionType = GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionType
					.fromValue(requestGeoRegion.getType().getValue());
			responseGeoRegion.setType(responseGeoRegionType);
			responseGeoRegion.setValue(requestGeoRegion.getValue());

			GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocations requestGeoRegionSpecificLocations = requestGeoRegion
					.getSpecificLocations();
			if (requestGeoRegionSpecificLocations != null) {// supported
															// locations might
															// be null
				GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocations responseGeoRegionSpecificLocations = new GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocations();
				GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation[] requestGeoRegionSpecificLocationGeoLocationArray = requestGeoRegionSpecificLocations
						.getGeoLocation();
				GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation[] responseGeoRegionSpecificLocationGeoLocationArray = new GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation[requestGeoRegionSpecificLocationGeoLocationArray.length];

				int j = 0;// loop through all the geolocations and copy them
							// into response geolocations
				for (GIPSEQueryRequestQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation requestGeoLocation : requestGeoRegionSpecificLocationGeoLocationArray) {
					GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation responseGeoLocation = new GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation();
					GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType responseGeoLocationType = GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType
							.fromValue(requestGeoLocation.getType().getValue());
					responseGeoLocation.setType(responseGeoLocationType);
					responseGeoLocation.setValue(requestGeoLocation.getValue());
					responseGeoRegionSpecificLocationGeoLocationArray[j++] = responseGeoLocation;
				}
				responseGeoRegionSpecificLocations
						.setGeoLocation(responseGeoRegionSpecificLocationGeoLocationArray);
				responseGeoRegion.setSpecificLocations(responseGeoRegionSpecificLocations);
			}
			responseGeoRegionArray[i++] = responseGeoRegion;
		}
		responseGeoRegions.setGeoRegion(responseGeoRegionArray);
		queryChars.setGeoRegions(responseGeoRegions);
	}
	
	/**
	 * Lazy load the defaultInterval property based on the metadata record for
	 * the service.
	 * 
	 * @return
	 * @throws Exception
	 */
	private static final String determineDefaultInterval() throws Exception {
		if (defaultInterval == null) {
			MetadataQueryResponse metadata = getMetadataQueryResponse();
			MetadataQueryResponseMetadataRecordIntervalsSupportedInterval[] intervals = metadata
					.getMetadataRecord().getIntervalsSupported().getInterval();
			for (MetadataQueryResponseMetadataRecordIntervalsSupportedInterval interval : intervals) {
				boolean isDefault = false;// interval.getIsDefault();
				if (isDefault) {
					defaultInterval = interval.getName();
					break;
				}
			}
			// if for some reason, the service is misconfigured then default to
			// "day".
			if (defaultInterval == null) {
				defaultInterval = "day";
				LOGGER
						.debug("ServiceMetadata is misconfigured, so default interval is set to \"day\"");
			}
		}
		return defaultInterval;
	}


	private static List<Observation> runQuery(GIPSEQueryResponse response)
		throws Exception {
	Observation observation = buildQueryParameters(response);
	List<Observation> returnList = null;
	Observation record;
	
	CaseCountServiceDAO dao = new CaseCountServiceDAOImpl();
	
	NewDataSet newDataSet = new NewDataSet();
	String _indicatorIds = "";
	if(observation.getCallVolumeType().equals(GIPSEPoisonConstants.CLINEFFECT_CALL_VOLUME_TYPE)) {
		for(String indicator : observation.getIndicators()) {
			IndicatorValue indicators = new IndicatorValue(indicator);
			_indicatorIds += String.valueOf(indicators.getIndicatorId()); 
		}
	}

	String systemUser = defaultProps.getProperty(GIPSEPoisonConstants.NPDSUSERNAME);

	//extract data for state
	
	if(observation.getStates().length >0 || observation.getZip3s().length >0 || observation.getZip5s().length >0)
	{
		if (observation.getStates().length >0)
		{

			newDataSet = dao.extract(systemUser, 
					GIPSEPoisonConstants.NPDSNATIONALCENTER,
					observation.getStatesString(),
					"", 
					observation.getStartDate(),
					observation.getEndDate(), 
					String.valueOf(observation.getStartHour()), 
					String.valueOf(observation.getEndHour()), 
					_indicatorIds,
					observation.getCallVolumeType(), 
					"", 
					GIPSEPoisonConstants.NPDSSTATETYPE);
	
	if (newDataSet != null) {
		returnList = new LinkedList<Observation>();
	
		for (NewDataSet.Table t : newDataSet.getTable()) {
			record = new Observation();
			String sd = t.getStartDateTime();
	
			Calendar startCal = Calendar.getInstance();
			try {
				startCal.setTime(df.parse(sd));
				record.setOccurrenceDate(startCal);
			} catch (Exception e) {
				System.out.print("Unable to parse date from return record.");
			}
	
			if (observation.getStates().length > 0) {
				record.setLocation(t.getStateName());
			} 
	
			try {
				String[] indMod = new String[observation.getIndicators().length];
				for (int k=0; k<observation.getIndicators().length; k++)
				{
					indMod[k]=observation.getIndicators()[k].replace('~', ' ');
				}
				record.setIndicators(indMod);
			} catch (Exception e) {
				LOGGER.info("Unable to get indicators.");
			}
	
			record.setCount(t.getTotalCount());
	
			// add to list
			returnList.add(record);
		}
		}
		}
		//will have to make a different call for zip5 and zip3 calls
		if (observation.getZip3s().length >0 || observation.getZip5s().length >0)
		{
			 String zipStateString = "";
			 Set<String> zip3Set = new HashSet<String>();
			 Set<String> zip5Set = new HashSet<String>();
			  Set<String> stateSet = new HashSet<String>();
			  for (int k=0; k<observation.getZip3s().length; k++)
			  {
				  if (observation.getZip3s()[k] != null && !observation.getZip3s()[k].equals(""))
				  {
					  stateSet.add((String)stateByZip3Properties.get(observation.getZip3s()[k]));
					  zip3Set.add(observation.getZip3s()[k]);
				  }
			  }
			  for (int q=0; q<observation.getZip5s().length; q++)
			  {
				  if (observation.getZip5s()[q] != null && !observation.getZip5s()[q].equals(""))
				  {
					  zip5Set.add(observation.getZip5s()[q]);
					  String zip5toZip3 = observation.getZip5s()[q].substring(0, 3);
					  stateSet.add((String) stateByZip3Properties.get(zip5toZip3));
				  }
			  }
			  Iterator<String> stateIter = stateSet.iterator();
			  while (stateIter.hasNext())
			  {
				  zipStateString += stateIter.next() + ",";
			  }
			  if (!zipStateString.equals(""))
			  {
				  zipStateString = zipStateString.substring(0, zipStateString.length()-1);
			  }
			  LOGGER.debug("using state string: " + zipStateString);
			
			newDataSet = dao.extract(systemUser, 
					GIPSEPoisonConstants.NPDSNATIONALCENTER,
					zipStateString,
					"", 
					observation.getStartDate(),
					observation.getEndDate(), 
					String.valueOf(observation.getStartHour()), 
					String.valueOf(observation.getEndHour()), 
					_indicatorIds,
					observation.getCallVolumeType(), 
					"", 
					GIPSEPoisonConstants.NPDSZIPTYPE);
			
			
			
			if (newDataSet != null) {
				if (returnList == null)
				{
				returnList = new LinkedList<Observation>();
				}
				Map<String, Observation> obsMap = new HashMap<String, Observation>();
				if(observation.getZip3s().length >0)
				{	   			
					// iterate over data set, aggregate zip3 counts for zip3s requested
					for (NewDataSet.Table t : newDataSet.getTable()) {
						if(t.getZipcode()!= null && (zip3Set.contains(t.getZipcode().toString().substring(0,3)) || zip5Set.contains(t.getZipcode().toString())))
						{
						record = new Observation();
						String sd = t.getStartDateTime();
						
						Calendar startCal = Calendar.getInstance();
						try {
							startCal.setTime(df.parse(sd));
							record.setOccurrenceDate(startCal);
						} catch (Exception e) {
							System.out.print("Unable to parse date from return record.");
						}
										
							String indString = "";
						try {
							
							String[] indMod = new String[observation.getIndicators().length];
							for (int k=0; k<observation.getIndicators().length; k++)
							{
								indMod[k]=observation.getIndicators()[k].replace('~', ' ');
								indString += observation.getIndicators()[k].replace('~', ' ');
							}
							record.setIndicators(indMod);
						} catch (Exception e) {
							LOGGER.info("Unable to get indicators.");
						}
				
						record.setCount(t.getTotalCount());
						if (zip3Set.contains(t.getZipcode().toString().substring(0,3)))
						{
						record.setLocation(t.getZipcode().toString().substring(0,3));
						String hashKey = record.getOccurrenceDate().getTimeInMillis() + record.getLocation() + indString;
						// add to list
						if (obsMap.containsKey(hashKey))
						{
							Observation oldRecord = obsMap.remove(hashKey);
							int count = oldRecord.getCount();
							count += record.getCount();
							record.setCount(count);
							obsMap.put(hashKey, record);						
						}
						else
						{
							obsMap.put(hashKey, record);
						}
						}
						if (zip5Set.contains(t.getZipcode().toString()))
						{
							record.setLocation(t.getZipcode().toString());
							returnList.add(record);
						}
						}
						//returnList.add(record);
					}
					
				// place zip3 counts in observation set
					returnList.addAll(obsMap.values());
				}
				if(observation.getZip5s().length >0)
				{
					
					
				}

				}
			
		}
		
	} else {
		LOGGER.info("No Data returned for this query");
		System.out.println("No data returned for query");
	}
	return returnList;
	} 
	
		
	
	private static Observation buildQueryParameters(GIPSEQueryResponse response)
	throws Exception {

Observation observation = new Observation();

// start / end date intervals
if (response.getQueryCharacteristics().getDates().getStart() != null) {
	Calendar startDate = Calendar.getInstance();
	startDate.setTimeInMillis(response.getQueryCharacteristics()
			.getDates().getStart().getTime());
	observation.setStartDate(startDate);
	observation.setStartHour(0);//startDate.get(Calendar.HOUR));
} 
if (response.getQueryCharacteristics().getDates().getEnd() != null) {
	Calendar endDate = Calendar.getInstance();// new SimpleTimeZone(0, "GMT")
	endDate.setTimeInMillis(response.getQueryCharacteristics().getDates().getEnd().getTime());
	observation.setEndDate(endDate);
	observation.setEndHour(24);
	// observation.setEndHour(endDate.get(Calendar.HOUR));
	// System.out.print("END HOUR:"+endDate.get(Calendar.HOUR));
} 

// indicators=clinical effects
if (response.getQueryCharacteristics().getIndicators().getIndicator() != null) {
	GIPSEQueryResponseQueryCharacteristicsIndicatorsIndicator[] indicatorArray = response
			.getQueryCharacteristics().getIndicators().getIndicator();
	String[] queryIndicatorArray = new String[indicatorArray.length];
	int i = 0;
	boolean hasAllIndicators = false;
	for (GIPSEQueryResponseQueryCharacteristicsIndicatorsIndicator indicator : indicatorArray) {
		if (indicator.getClassifier().toUpperCase().equals("NPDS")) {

			//gov.cdc.ncphi.phgrid.services.gipse.beans.IndicatorValue indicators = new gov.cdc.ncphi.phgrid.services.gipse.beans.IndicatorValue(indicator.getName());
			if(indicator.getName().equals(GIPSEPoisonConstants.ALL_CALLS_INDICATOR))
			{
				hasAllIndicators = true;
			}
			queryIndicatorArray[i++] = indicator.getName();
			
		}
	}
	
	if (hasAllIndicators)
	{
		observation.setCallVolumeType(GIPSEPoisonConstants.TOTAL_CALL_VOLUME_TYPE);
	}
	else
	{
		observation.setCallVolumeType(GIPSEPoisonConstants.CLINEFFECT_CALL_VOLUME_TYPE);
		observation.setIndicators(queryIndicatorArray);
	}
}

// geo-regions
if (response.getQueryCharacteristics().getGeoRegions().getGeoRegion() != null) {
	Set<String> stateList = new HashSet<String>();
	Set<String> zip3List = new HashSet<String>();
	Set<String> zip5List = new HashSet<String>();
	GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegion[] geoRegionArray = response
			.getQueryCharacteristics().getGeoRegions().getGeoRegion();
	for (GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegion geoRegion : geoRegionArray) {
		String regionType = geoRegion.getType().getValue();
		String regionValue = geoRegion.getValue();
		if (regionType
				.equals(GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionType._state)) {
			if (regionValue.equals("*")) {
				if (observation.getStates().length == 0) {
					observation.setStates(states);
				}
			} else {
				stateList.add(regionValue);
			}
		} else if (regionType
				.equals(GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionType._zip3)) {
			zip3List.add(regionValue);
		} else if (regionType
				.equals(GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionType._zip5)) {
			zip5List.add(regionValue);
		} else if (regionType
				.equals(GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionType._country)) {
			// ignore for now, doesn't affect query
		} else {
			String errorMessage = "Unknown GeoRegion type<"
					+ regionType + ">.";
			LOGGER.error(errorMessage);
			throw new IllegalArgumentException(errorMessage);
		}

		GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocations specificLocations = geoRegion
				.getSpecificLocations();
		if (specificLocations != null) {
			GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation[] locationArray = specificLocations
					.getGeoLocation();
			for (GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocation location : locationArray) {
				String locationType = location.getType().getValue();
				String locationValue = location.getValue();
				locationValue = locationValue.replace('*', '%');
				if (locationType
						.equals(GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType._state)) {
					stateList.add(locationValue);
				} else if (locationType
						.equals(GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType._zip3)) {
					zip3List.add(locationValue);
				} else if (locationType
						.equals(GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType._zip5)) {
					zip5List.add(locationValue);
				} else if (locationType
						.equals(GIPSEQueryResponseQueryCharacteristicsGeoRegionsGeoRegionSpecificLocationsGeoLocationType._country)) {
					// ignore for now, doesn't affect query
				} else {
					String errorMessage = "Unknown GeoLocation type<"
							+ locationType + ">.";
					LOGGER.error(errorMessage);
					throw new IllegalArgumentException(errorMessage);
				}

			}
		}
	}

	observation.setStates((String[]) stateList.toArray(new String[0]));
	observation.setZip3s((String[]) zip3List.toArray(new String[0]));
	observation.setZip5s((String[]) zip5List.toArray(new String[0]));
}
try {
	if (response.getQueryCharacteristics().getServiceAreas() != null) {
		GIPSEQueryResponseQueryCharacteristicsServiceAreasServiceArea[] serviceAreaArray = response
				.getQueryCharacteristics().getServiceAreas()
				.getServiceArea();
		String[] queryServiceAreaArray = new String[serviceAreaArray.length];
		int i = 0;
		for (GIPSEQueryResponseQueryCharacteristicsServiceAreasServiceArea serviceArea : serviceAreaArray) {
			queryServiceAreaArray[i++] = serviceArea.getName();
		}
		observation.setOrgLists(queryServiceAreaArray);
	}
} catch (Exception e) {
	LOGGER.info("Query Response service Areas Error:" + e);
}

return observation;
}

	/**
	 * Sets the observation set within the response based on the list of
	 * observations passed in.
	 * 
	 * @param response
	 * @param observationList
	 */
	private static void populateResponseWithObservations(
			GIPSEQueryResponse response, List<Observation> observationList) {
		GIPSEQueryResponseObservationSet observationSet = new GIPSEQueryResponseObservationSet();
		GIPSEQueryResponseObservationSetObservation[] observationArray = new GIPSEQueryResponseObservationSetObservation[observationList
				.size()];
		int i = 0;
		for (Observation observation : observationList) {
			GIPSEQueryResponseObservationSetObservation observationForResult = new GIPSEQueryResponseObservationSetObservation();
			try {
				observationForResult.setStart(observation.getOccurrenceDate().getTime());
			} catch (Exception e) {
				LOGGER.info("Start date response error.");
			}
			observationForResult.setLocation(observation.getLocation());

			observationForResult.setIndicator(observation.getIndicatorsString());

			observationForResult.setValue(Integer.toString(observation.getCount()));

			observationArray[i++] = observationForResult;
		}

		observationSet.setObservation(observationArray);

		response.setObservationSet(observationSet);
	}
	
  public gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryResponse queryGIPSE(gov.cdc.ncphi.phgrid.gipse.message.GIPSEQueryRequest query) throws RemoteException {
	  try {
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("amds request"
						+ AxisUtils.serializeAxisObject(query, true, true));
			}

			GIPSEQueryResponse response = new GIPSEQueryResponse();
			prepareResponse(query, response);
			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("amds response-pre query"
						+ AxisUtils.serializeAxisObject(response, true, true));
			}

			List<Observation> sqlResults = runQuery(response);

			populateResponseWithObservations(response, sqlResults);

			if (LOGGER.isDebugEnabled()) {
				LOGGER.debug("amds response about to be returned"
						+ AxisUtils.serializeAxisObject(response, true, true));
			}
			return response;
		} catch (Exception e) {
			LOGGER.error("[queryAMDS]" + e.getMessage(), e);
			throw new RemoteException(e.getMessage(), e);
		} 
	  
	  //throw new RemoteException ("Not Yet Implemented");
  }



}

