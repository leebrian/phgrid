package gov.cdc.ncphi.phgrid.polygon.regrel;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class RegionRelationConnectionPool 
{
	private Connection con;
	private static String PROPERTIES_FILE = "/gmap-polygon.properties";
	private String  tableName;
	private Properties defaultProps;
	private static final Logger logger = Logger.getLogger(RegionRelationConnectionPool.class);
	

	
	public Connection getConnection()
	{
		Connection returnable = null;
		if (con != null)
			returnable =  con;
		else
		{
		   	try {
				defaultProps = new Properties();
		        InputStream is = getClass().getResourceAsStream(PROPERTIES_FILE);
		        if (is == null) logger.error("Could not read " + PROPERTIES_FILE);
		        defaultProps.load(is);
				//defaultProps.load(in);
				//in.close();
				String url = defaultProps.getProperty("url");
				String db = defaultProps.getProperty("db");
				String driver = defaultProps.getProperty("driver");
				String user = defaultProps.getProperty("user");
				String pass = defaultProps.getProperty("pass");
				tableName = defaultProps.getProperty("listtablename");
		          Class.forName(driver);
		          con = DriverManager.getConnection(url+db, user, pass);
		          returnable = con;
		           
		          
		          
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
		return returnable;
	}
	public String getTableName()
	{
		return tableName;
	}
}
