package gov.cdc.ncphi.phgrid.gipse.dbimporter;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

/**
 * Methods for accessing and updating the database.
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.12 1449-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
public class DBUtils {
	private static final Logger LOGGER = Logger.getLogger(DBUtils.class);
	
	private static final String INSERT_JOB_SQL = "INSERT INTO gipse_import_jobs (job_description, status) VALUES (?,?);";
	private static final String INSERT_JOB_SQL_POSTGRES = "INSERT INTO gipse_import_jobs (job_description, status) VALUES (?,?);SELECT currval('gipse_import_jobs_sequence');";
	private static final String UPDATE_JOB_SQL = "UPDATE gipse_import_jobs SET job_description = ?, status= ?, records_inserted = ?, records_updated = ?, records_deleted = ? WHERE job_id = ?;";
	
	private static final String SELECT_GIPSE_COUNT_OF_RECORD = "SELECT value FROM gipse_store WHERE date = ? AND zip5 = ? AND state = ? AND indicator_id = ?;";
	private static final String SELECT_GIPSE_RECORD = "SELECT * FROM gipse_store WHERE date = ? AND zip5 = ? AND state = ? AND indicator_id = ?;";
	private static final String INSERT_GIPSE_RECORD = "INSERT INTO gipse_store (date, zip5, state, indicator_id, count, source_oid) VALUES(?,?,?,?,?,?);";
	private static final String UPDATE_GIPSE_RECORD = "UPDATE gipse_store SET count = ? WHERE date = ? AND zip5 = ? AND state = ? AND indicator_id = ?;";
	
	
	
	/**
	 * @return a connection using default project properties. Make sure you close it after using.
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public static final Connection getConnection() throws ClassNotFoundException, SQLException{
		Connection conn = null;
		Class.forName(ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_DRIVER));
		conn = DriverManager.getConnection(ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_URL),
				ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_USER),
				ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_PASS));
		return conn;
	}
	
	/**
	 * Queries the gipse_store table and returns the matching record. 
	 * @param targetRecord Only the PK needs to be set (date, zip5, state, condition).
	 * @param conn 
	 * @return
	 * @throws SQLException
	 */
	public static final GIPSERecord selectRecord(GIPSERecord targetRecord, Connection conn) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		GIPSERecord resultRecord = null;
		
		try{
			ps = conn.prepareStatement(SELECT_GIPSE_RECORD);
			ps.setDate(1, new java.sql.Date(targetRecord.getDate().getTime()));
			ps.setString(2, targetRecord.getZip5());
			ps.setString(3, targetRecord.getState());
			ps.setInt(4, targetRecord.getCondition());
			rs = ps.executeQuery();
			while (rs.next()){
				resultRecord = new GIPSERecord(rs.getInt("source_oid"),
						rs.getDate("date"),
						rs.getString("zip5"),
						rs.getString("state"),
						rs.getInt("indicator_id"),
						rs.getLong("value"));
			}
			
			return resultRecord;
			
		}finally{
			if (ps != null){
				ps.close();//closes ResultSets too
			}
		}
	}
	
	/**
	 * Selects only the count field from a record.
	 * @param targetRecord - uses the PK fields (date, state, zip, condition) only
	 * @param conn an open connection, will not be closed
	 * @return count of the record, -1 if no record is found
	 * @throws SQLException
	 */
	public static final long selectCount(GIPSERecord targetRecord, Connection conn) throws SQLException{
		long countOfRecord = -1l;
		PreparedStatement ps = null;
		ResultSet rs = null;
		
		try{
			ps = conn.prepareStatement(SELECT_GIPSE_COUNT_OF_RECORD);
			ps.setDate(1, new java.sql.Date(targetRecord.getDate().getTime()));
			ps.setString(2, targetRecord.getZip5());
			ps.setString(3, targetRecord.getState());
			ps.setInt(4, targetRecord.getCondition());
			rs = ps.executeQuery();
			while (rs.next()){
				countOfRecord = rs.getLong(1);
			}
			
			return countOfRecord;
			
		}finally{
			if (ps != null){
				ps.close();//closes ResultSets too
			}
		}
	}
	
	/**
	 * Inserts a new record into gipse_store.
	 * @param targetRecord
	 * @param conn
	 * @return 1 for a successful update, else 0.
	 * @throws SQLException
	 */
	public static final int insertRecord(GIPSERecord targetRecord, Connection conn) throws SQLException{
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement(INSERT_GIPSE_RECORD);
			ps.setDate(1, new java.sql.Date(targetRecord.getDate().getTime()));
			ps.setString(2, targetRecord.getZip5());
			ps.setString(3, targetRecord.getState());
			ps.setInt(4, targetRecord.getCondition());
			ps.setLong(5, targetRecord.getCount());
			ps.setInt(6, targetRecord.getDataSourceId());
			int returnValue = ps.executeUpdate();
			if (!conn.getAutoCommit()){
				conn.commit();
			}
			return returnValue;
			
		}finally{
			if (ps != null){
				ps.close();//closes ResultSets too
			}
		}
	}
	
	/**
	 * Updates the count of a record based.
	 * @param targetRecord
	 * @param conn
	 * @return 1 for a successful update, else 0.
	 * @throws SQLException
	 */
	public static final int updateRecord(GIPSERecord targetRecord, Connection conn) throws SQLException{
		PreparedStatement ps = null;
		
		try{
			ps = conn.prepareStatement(UPDATE_GIPSE_RECORD);
			ps.setLong(1, targetRecord.getCount());
			ps.setDate(2, new java.sql.Date(targetRecord.getDate().getTime()));
			ps.setString(3, targetRecord.getZip5());
			ps.setString(4, targetRecord.getState());
			ps.setInt(5, targetRecord.getCondition());
			int returnValue = ps.executeUpdate();
			if (!conn.getAutoCommit()){
				conn.commit();
			}
			return returnValue;
			
		}finally{
			if (ps != null){
				ps.close();//closes ResultSets too
			}
		}
	}
	
	/**
	 * Insert an initial record in gipse_import_jobs to note the beginning of an import job.
	 * @param jobId the id of the job to be persisted (null if new)
	 * @param results 
	 * @param status the status of the running job
	 * @param conn an open connection (will be committed after update)
	 * @return the jobId
	 * @throws SQLException
	 */
	public static final long persistImportJob(long jobId, ImporterJobResults results, String status, Connection conn) throws SQLException{
		PreparedStatement ps = null;
		ResultSet rs = null;
		long returnValue = jobId;
		
		try{
			if (GIPSEConstants.IMPORT_JOBS_STATUS_STARTED.equals(status)){//insert for a new job
				if (LOGGER.isDebugEnabled()){
					LOGGER.debug("inserting new job<" + results.toString() + ">");
				}
				//postgres doesn't support return generated keys, so we process differently
				if (ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_URL).contains("postgres")){
					ps = conn.prepareStatement(INSERT_JOB_SQL_POSTGRES);
					ps.setString(1, results.getDescription());
					ps.setString(2, status);
					ps.execute();
					if (ps.getUpdateCount() ==1 && ps.getMoreResults()){
						ResultSet rs2 = ps.getResultSet();
						while (rs2.next()){
							returnValue = rs2.getInt(1);
						}
					}
				}else{
					ps = conn.prepareStatement(INSERT_JOB_SQL, PreparedStatement.RETURN_GENERATED_KEYS);
					ps.setString(1, results.getDescription());
					ps.setString(2, status);
					ps.execute();
					rs = ps.getGeneratedKeys();
					while (rs.next()){
						returnValue = rs.getLong(1);
					}
				}
			}else{//update for existing job
				if (LOGGER.isDebugEnabled()){
					LOGGER.debug("updating existing jobId<" + jobId + "> with results<" + results.toString() + ">");
				}
				ps = conn.prepareStatement(UPDATE_JOB_SQL);
				ps.setString(1, results.getDescription());
				ps.setString(2, status);
				ps.setLong(3, results.getRecordsInserted());
				ps.setLong(4, results.getRecordsUpdated());
				ps.setLong(5, results.getRecordsDeleted());
				ps.setLong(6, jobId);
				ps.execute();
			}
			if (!conn.getAutoCommit()){
				conn.commit();
			}
			return returnValue;
		}finally{
			if (ps != null){
				ps.close();//closes ResultSets too
			}
		}
	}

}
