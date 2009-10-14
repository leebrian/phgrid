package gov.cdc.ncphi.phgrid.npdsgmaps;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.log4j.Logger;



public class NPDSGmapAuth {
	private static final Logger logger = Logger.getLogger(NPDSGmapAuth.class);
	private static Properties defaultProps;
	private static final String PROPERTIES_FILE = "/npdsgmaps.properties";
	private static String tableName, url, db, driver, user, pass;
	public NPDSGmapAuth()
	{

		   	try {
				defaultProps = new Properties();
		        InputStream is = getClass().getResourceAsStream(PROPERTIES_FILE);
		        if (is == null) logger.error("Could not read " + PROPERTIES_FILE);
		        defaultProps.load(is);
				url = defaultProps.getProperty("url");
				db = defaultProps.getProperty("db");
				driver = defaultProps.getProperty("driver");
				user = defaultProps.getProperty("user");
				pass = defaultProps.getProperty("pass");
				tableName = defaultProps.getProperty("tablename");
		        logger.debug("loaded NPDSGmapAuth with url "
		        		+ url + " db " + db + " driver " + driver + " tablename " + tableName );
				
		           
		          
		          
			} catch (FileNotFoundException e) {
				logger.error(e.getMessage());
				e.printStackTrace();
			} catch (IOException e) {			
				logger.error(e.getMessage());
				e.printStackTrace();
				
			}	
		

	}
	public boolean checkAuth(String username, String password)
	{
		Connection con = null;
		PreparedStatement prep = null;
		ResultSet rs = null;
		
		String sql = "select userid from " + tableName + " where userid = ? and password = ?";
		try{
			String hashedPassword = null;
			
			hashedPassword = new String(org.apache.commons.codec.binary.Hex.encodeHex(
							java.security.MessageDigest.getInstance("SHA1").digest(
								(password).getBytes())));

		logger.debug("pulling for username " + username + " and hashed password " + hashedPassword);
		Class.forName(driver);
         con = DriverManager.getConnection(url+db, user, pass);
         prep = con.prepareStatement(sql);
		prep.setString(1, username);
		prep.setString(2, hashedPassword);
		rs = prep.executeQuery();
		
		
		return rs.next();
			
		} catch (Exception e) {			
			logger.error("error Pulling Query", e);
        }		
		finally
		{
			try{
				if (prep != null)
				{
					prep.close();
				}
				if (rs != null)
					{
						rs.close();
					}
				if(con != null && !con.isClosed())
				{
					con.close();
				}
				
					
			}catch (SQLException e) {			
				logger.error("error Closing connection/preparedstatement/resultset", e);
	        }
		}
		return false;
	}
	public String makeSHA1Hash(String input)
	throws NoSuchAlgorithmException
{
	MessageDigest md = MessageDigest.getInstance("SHA1");
	md.reset();
	byte[] buffer = input.getBytes();
	md.update(buffer);
	byte[] digest = md.digest();
	
	String hexStr = "";
	for (int i = 0; i < digest.length; i++) {
		hexStr +=  Integer.toString( ( digest[i] & 0xff ) + 0x100, 16).substring( 1 );
	}
	return hexStr;
}
}
