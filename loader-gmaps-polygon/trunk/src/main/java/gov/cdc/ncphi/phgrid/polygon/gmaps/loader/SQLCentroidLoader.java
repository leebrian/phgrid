package gov.cdc.ncphi.phgrid.polygon.gmaps.loader;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

public class SQLCentroidLoader {
	private String url, db, driver, user, pass, centroidtablename;
	private Connection con;
	private static final String PROPERTIES_FILE = "/loader-gmap-polygon.properties";
	private static final Logger logger = Logger.getLogger(SQLPolygonLoader.class);
	 public void createConnection()
	    {
	    	logger.debug("Inserting values into database table!");
	    	try {
	    		Properties defaultProps = new Properties();
		        InputStream is = getClass().getResourceAsStream(PROPERTIES_FILE);
		        if (is == null) logger.error("Could not read " + PROPERTIES_FILE);
		        defaultProps.load(is);
				String url = defaultProps.getProperty("url");
				String db = defaultProps.getProperty("db");
				String driver = defaultProps.getProperty("driver");
				String user = defaultProps.getProperty("user");
				String pass = defaultProps.getProperty("pass");
				centroidtablename = defaultProps.getProperty("centroidtablename");
		          Class.forName(driver);
		          con = DriverManager.getConnection(url+db, user, pass);
		           
		          
		          
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {			
				logger.error(e.getMessage());
				e.printStackTrace();
			}	catch (SQLException s){
				logger.error(s.getMessage());
				s.printStackTrace();
	        }  catch (ClassNotFoundException e) {			
				logger.error(e.getMessage());
				e.printStackTrace();
			}	
	    }
	
	public void loadData(List<DatabaseCentroid> centroids) throws SQLException
	{
		boolean returnable = false;
		 PreparedStatement st = con.prepareStatement("Insert into " + centroidtablename + " Values(?, ?, ?, ?)");
         //int val = st.executeUpdate("INSERT into countypolygons VALUES('barcounty', 'big string of numbers')");
    	 
    	//System.out.println("No of zipcode polygons '" + zipList.size() + "'.");
 		logger.debug("No of zipcode centroids '" + centroids.size() + "'.");
 		Iterator<DatabaseCentroid> it = centroids.iterator();
 		String printable = "";
 		checkTable();
 		DatabaseCentroid dc;
 		
 		String polystring = "";
 		while(it.hasNext()) {
 			dc = it.next();
 			 if (dc != null)
 			 {
 					st.setString(1, dc.getLocation());
		 			st.setString(2, dc.getType());
		 			st.setString(3, dc.getCentroidLongitude());
		 			st.setString(4, dc.getCentroidLatitude());
		 			int val = st.executeUpdate();
 			}
					
 		}
 			
 		
		
	}
 		
 	private void checkTable()
 	{
 		String query = "select count (*) from " + centroidtablename + ";";
 		//String dropTable = "drop table "+tablename + ";";
 		Statement stmt = null;
 		ResultSet rs = null;
 		try
 		{
 		stmt = con.createStatement();
 		rs = stmt.executeQuery(query);
 		}catch (SQLException e)
 		{
 			logger.error(e.getLocalizedMessage());
 			//try to create table
 			createTable();
 		}
 		

 	}
 	
 	private void createTable()
 	{
 		//String createQuery = "create table "+tablename+" (regionName varchar, regionType varchar, locationString varchar, cenlongitude varchar, cenlatitude varchar);";
 		String createQuery = "create table "+centroidtablename+" (regionName text, regionType text, regionLongitude text, regionLatitude text);";
 		Statement stmt = null;
	 	ResultSet rs = null;
 		try
 		{
 			stmt = con.createStatement();
 	 		rs = stmt.executeQuery(createQuery);
 		}catch (SQLException e)
 		{
 			logger.error(e.getLocalizedMessage());
 		}
 	}
 	
	public static void main(String[] args){
		String filename = null;
    	
    	
    		SQLCentroidLoader scl = new SQLCentroidLoader();
    		scl.createConnection();
    		scl.checkTable();
    	
		

	}

}
