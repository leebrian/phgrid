package gov.cdc.ncphi.phgrid.gipse.dbimporter;


import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

/**
 * Builder object to create GIPSERecord TOs from various sources.
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.13 1017-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
public class GIPSERecordBuilder {
	private static final Logger LOGGER = Logger.getLogger(GIPSERecordBuilder.class);
	
	/**
	 * Expects a comma delimited String with 11 tokens in format: 
	 * src, 
	 * dateofevent,
	 * patientvarzip,
	 * patientstate,
	 * bucketid (ignored),
	 * bucketname (ignored),
	 * itemdetailid, used to determine condition uniqueness (since name can be reused)
	 * itemdetaildescription(ignored), 
	 * category_value_uid(ignored), 
	 * category_value_text, 
	 * count");
	 * @return Populated TO or null if an error occurs.
	 */
	public static final GIPSERecord buildGIPSERecordFromBioSenseExtract(String gipseRecord){
		GIPSERecord returnRecord = null;
		String [] recordSegments = gipseRecord.split("(,)(?=(?:[^\"']|[\"|'][^\"']*\")*$)");//don't worry about commas inside quotes
		if (recordSegments.length != 11){
			LOGGER.warn("[Method:buildGIPSERecordFromBioSenseExtract] Skipping Record because it doesn't have 11 tokens separated by commas (it has<" + recordSegments.length + ">). Record: <" + gipseRecord + ">");
		}else{
			returnRecord = buildGIPSERecordFromArray(new String[]{recordSegments[0],recordSegments[1],recordSegments[2],recordSegments[3],recordSegments[6], recordSegments[9],recordSegments[10]});
		}
		
		return returnRecord;
	}
	
	/**
	 * Expects a comma delimited String with 7 tokens in format:
	 * src,
	 * dateofevent,
	 * zip5,
	 * state,
	 * condition_type,
	 * condition_name,
	 * count
	 * @param gipseRecord
	 * @return Populated TO or null if an error occurs.
	 */
	public static final GIPSERecord buildGIPSERecordFromCSV(String gipseRecord){
		GIPSERecord returnRecord = null;
		String [] recordSegments = gipseRecord.split("^([^\"]|\"[^\"]*\")*?(,)");//don't worry about commas inside quotes
		if (recordSegments.length != 7){
			LOGGER.warn("[Method:buildGIPSERecordFromCSV] Skipping Record because it doesn't have 7 tokens separated by commas. Record: <" + gipseRecord + ">");
		}else{
			returnRecord = buildGIPSERecordFromArray(recordSegments);
		}
		return returnRecord;
	}
	
	/**
	 * Builds an GIPSERecord from a string array. 
	 * @param gipseRecord expects 7 elements in the array (src, dateofevent, zip5, state, condition_type, condition_name, count)
	 * @return Populated TO or null if an error occurs.
	 */
	public static final GIPSERecord buildGIPSERecordFromArray(String [] gipseRecord){
		return buildGIPSERecord(gipseRecord[0],//dataSource 
				gipseRecord[1],//dateOfEvent
				gipseRecord[2],//zip5
				gipseRecord[3],//state
				gipseRecord[4],//conditionType
				gipseRecord[5],//conditionName
				gipseRecord[6]);//count
		
	}
	
	/**
	 * Builds an GIPSERecord based on all string parameters. Strings will be processed according to configuration rules.
	 * @param dataSource This will be matched against gipse.dbimporter.datasources to convert name to a db source_oid (int type)
	 * @param dateOfEvent Expected format is m/dd/yyyy
	 * @param zip5 This will stay a string (since zips have leading zeros)
	 * @param state This should be a two digit state code
	 * @param conditionType This will be matched against gipse.dbimporter.conditions prepended to conditionName as a key to look up the condition_id
	 * @param conditionName This will be matched against gipse.dbimporter.conditions to convert name to a db condition_id (int type)
	 * @param count This will be converted to a long. If it matches the gipse.dbimporter.resetrandom property value, it will be set to a random int between 0 and 100.
	 * @return Populated TO or null if an error occurs.
	 */
	public static final GIPSERecord buildGIPSERecord(String dataSource, String dateOfEvent, String zip5, String state, String conditionType, String conditionName, String count){
		GIPSERecord returnRecord = null;
		
		SimpleDateFormat sdf = new SimpleDateFormat("M/dd/yyyy");
		try {
			String conditionLookup = conditionType + conditionName;
			conditionLookup = conditionLookup.replace("\"","");
			returnRecord = new GIPSERecord(ImporterUtils.determineDataSourceId(dataSource),
					sdf.parse(dateOfEvent),
					zip5,
					state,
					ImporterUtils.determineConditionId(conditionLookup),
					ImporterUtils.determineCount(count));
		} catch (Exception e) {
			LOGGER.warn("[Method:buildGIPSERecord]Throwable occurred while building GIPSERecord. Record import will be ignored. dateSource<" 
					+ dataSource +">, date<" + dateOfEvent +">,zip5<" + zip5 + ">,state<" + state +">, condition<" + conditionName +">, count<" + count + ">",e);
		}
		
		return returnRecord;
		
	}

}
