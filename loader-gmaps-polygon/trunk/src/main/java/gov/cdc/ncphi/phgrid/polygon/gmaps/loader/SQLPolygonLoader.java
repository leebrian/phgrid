package gov.cdc.ncphi.phgrid.polygon.gmaps.loader;



import java.io.FileInputStream;
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

public class SQLPolygonLoader implements PolygonLoader {
	
	private String url, db, driver, user, pass, tablename;
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
				tablename = defaultProps.getProperty("tablename");
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
	
	public void loadData(List<DatabasePolygon> polygons) throws SQLException
	{
		boolean returnable = false;
		 PreparedStatement st = con.prepareStatement("Insert into " + tablename + " Values(?, ?, ?)");
         //int val = st.executeUpdate("INSERT into countypolygons VALUES('barcounty', 'big string of numbers')");
    	 
    	//System.out.println("No of zipcode polygons '" + zipList.size() + "'.");
 		logger.debug("No of zipcode polygons '" + polygons.size() + "'.");
 		Iterator<DatabasePolygon> it = polygons.iterator();
 		String printable = "";
 		checkTable();
 		DatabasePolygon dp;
 		List<String> polystrings;
 		String polystring = "";
 		while(it.hasNext()) {
 			dp = it.next();
 			 polystrings = dp.getPolygonStrings();
 			if (polystrings != null)
 			{
 				Iterator<String> polyIter = polystrings.iterator();
 				polystring = "";
 				while (polyIter.hasNext())
 				{
 					polystring = polyIter.next();
 					st.setString(1, dp.getLocationName());
		 			st.setString(2, dp.getType());
		 			st.setString(3, polystring);
		 			int val = st.executeUpdate();
 				}
					
 			}
 			
 		}
		
	}
 		
 	private void checkTable()
 	{
 		String query = "select count (*) from " + tablename + ";";
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
 		String createQuery = "create table "+tablename+" (regionName text, regionType text, locationString text);";
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
    	
    	
    		SQLPolygonLoader spl = new SQLPolygonLoader();
    		spl.createConnection();
    		spl.checkTable();
    	
		

	}
 	
}
