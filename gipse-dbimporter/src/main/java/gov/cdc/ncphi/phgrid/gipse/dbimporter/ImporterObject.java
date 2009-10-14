package gov.cdc.ncphi.phgrid.gipse.dbimporter;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * Business logic class for all the db importer work.
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.13 0925-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
public class ImporterObject {
	private static final Logger LOGGER = Logger.getLogger(ImporterObject.class);
	
	public ImporterJobResults processGIPSEExtractFile(File extractFile, Connection conn) throws IOException{
		ImporterJobResults results = new ImporterJobResults("Import of file: <" + extractFile.getName() + ">");
		long recordsInserted = 0;
		long recordsUpdated = 0;
		long recordsDeleted = 0;
		long recordsTotal = 0;
		long recordsSkipped = 0;
		if (extractFile.getName().endsWith("csv")){
			LOGGER.info("Processing CSV file<" + extractFile.getName() +">, format should be: src, dateofevent,patientvarzip,patientstate,bucketid (ignored),bucketname (ignored),itemdetailid (ignored),itemdetaildescription(ignored), category_value_uid, category_value_text, count");
			BufferedReader reader = null;
			try{
			reader = new BufferedReader(new FileReader(extractFile));
			String line = null;
			boolean onFirstLine = true;
			
			while ((line = reader.readLine()) != null){
				if(onFirstLine){//if we're the first line check for headers
					if (line.startsWith("src,dateofevent") || line.startsWith("src,dateofvisit")){
						onFirstLine = false;
						continue;//skip this line
					}
				}
				recordsTotal++;
				GIPSERecord record = GIPSERecordBuilder.buildGIPSERecordFromBioSenseExtract(line);
				if (record != null){//don't try to insert if the build fails (means record didn't parse or something)
					//first check to see if it exists, if it exists update, don't insert
					/*
					try {
						if (DBUtils.insertRecord(record, conn) > 0){
							recordsInserted++;
						}
					} catch (SQLException e) {//if insert fails, try update. This is 20% faster than checking to see if the record exists
						try {
							if (DBUtils.updateRecord(record, conn) > 0){
								recordsUpdated++;
							}else{//increment skipped if the update fails
								recordsSkipped++;
							}
						} catch (SQLException e1) {
							recordsSkipped++;
							LOGGER.warn("[Method:buildGIPSERecord] Problem inserting/updating record<" +record.toString() +".",e1);
						}
					}
					*/
					try {
						long countOfRecord = DBUtils.selectCount(record, conn);
						//GIPSERecord selectedRecord = DBUtils.selectRecord(record, conn);
						//long countOfRecord = selectedRecord.getCount();
						if (countOfRecord > -1){
							if (LOGGER.isDebugEnabled()){
								LOGGER.debug(recordsTotal + "selectedRecord.getCount()<" + countOfRecord + ">;record.getCount()<" + record.getCount() +">");
							}
							if (countOfRecord != record.getCount()){//only update if the count has changed
								if (DBUtils.updateRecord(record, conn) > 0){
									recordsUpdated++;
									if (LOGGER.isDebugEnabled()){
										LOGGER.debug(recordsTotal +"Record updated successfully<" + record.toString() +">");
									}
								}else{//increment skipped if the update fails
									recordsSkipped++;
									if (LOGGER.isEnabledFor(Level.WARN)){
										LOGGER.warn(recordsTotal + "Record skipped because update failed.<" + record.toString() +">");
									}
								}
							}else{
								recordsSkipped++;
								if (LOGGER.isDebugEnabled()){
									LOGGER.debug(recordsTotal + "Record skipped counts don't match.<" + record.toString() +">");
								}
							}
						}else{
							if (DBUtils.insertRecord(record, conn) > 0){
								recordsInserted++;
								if (LOGGER.isDebugEnabled()){
									LOGGER.debug(recordsTotal + "Record inserted successfully<" + record.toString() +">");
								}
							}else{
								recordsSkipped++;
								if (LOGGER.isEnabledFor(Level.WARN)){
									LOGGER.warn(recordsTotal + "Record skipped because insert failed.<" + record.toString() +">");
								}
							}
						}
					} catch (SQLException e) {
						recordsSkipped++;
						LOGGER.warn("[Method:processGIPSEExtractFile] Problem inserting/updating record<" +record.toString() +".",e);
					}
				}else{
					recordsSkipped++;//logging accomplished by builder
				}
			}
			//determine if CSV has headers
			}finally{
				if (reader != null){
					reader.close();
				}
			}
			
			
		}else{
			throw new RuntimeException("XML extract files not supported yet.");
		}
		
		results.setRecordsInserted(recordsInserted);
		results.setRecordsUpdated(recordsUpdated);
		results.setRecordsDeleted(recordsDeleted);
		results.setRecordsTotal(recordsTotal);
		results.setRecordsSkipped(recordsSkipped);
		results.setCompleteDate(new Date());
		
		return results;
	}
}
