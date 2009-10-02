package gov.cdc.ncphi.phgrid.polygon.gmaps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
/**
 * 
 * A class that fetches region polygons from a given table (listed by the properties file) 
 * @author Peter White
 *
 */
public class RegionPolygonFetcher {

	private static RegionPolygonFetcher myFetcher;
	private static Map<String, List<GmapPolygon>> myPolygons;
	private static final Logger logger = Logger.getLogger(RegionPolygonFetcher.class);
	
	Connection con;
	String centroidTableName, tableName, sql, censql;
	
	/**
	 * Generic constructor.  Uses the PolygonConnectionPool to get a connection and prepares the SQL statements.
	 * 
	 */
	private RegionPolygonFetcher()
	{
		myPolygons = new HashMap<String, List<GmapPolygon>>();
		PolygonConnectionPool pcp = new PolygonConnectionPool();
		
		con = pcp.getConnection();
		tableName = pcp.getTableName();
		centroidTableName = pcp.getCentroidTableName();
		//sql = "select regionname, regiontype, locationstring, cenlongitude, cenlatitude from "+ tableName +" where regionname = ? and regiontype = ?";
		sql = "select regionname, regiontype, locationstring from " + tableName + " where regionname = ? and regiontype = ?";
		censql = "select regionname, regiontype, regionlongitude, regionlatitude from " + centroidTableName + " where regionname = ? and regiontype = ?";
	}
	public static RegionPolygonFetcher getFetcher()
	{
		if (myFetcher == null)
		{
			myFetcher = new RegionPolygonFetcher();
		}
		return myFetcher;
	}
	/**
	 * Fetches the region polygons for a given regionName and regionType
	 * @param regionName
	 * @param regionType
	 * @return
	 */
	public List<GmapPolygon> getRegionPolygon(String regionName, String regionType)
	{
		List<GmapPolygon> returnable = new ArrayList<GmapPolygon>();
		//StandardGmapPolygon returnable = new StandardGmapPolygon();
		if (myPolygons.containsKey(regionName))
			return myPolygons.get(regionName);
		try 
		{
			Coordinate centroid = getCentroid(regionName, regionType);
		
			PreparedStatement prep = con.prepareStatement(sql);
			prep.setString(1, regionName);
			prep.setString(2, regionType);
			
			
			ResultSet rs = prep.executeQuery();
			while(rs.next())
			{
				StandardGmapPolygon polly = new StandardGmapPolygon();
				polly.setRegionType(regionType);
				polly.setRegionName(regionName);
				polly.setCentroid(centroid);
				String coordinateString = rs.getString(3);
				//returnable.setCentroid(new Coordinate(rs.getString(5), rs.getString(4)));
				List<Coordinate> coordinateList = new ArrayList<Coordinate>();
				if (coordinateString != null && !coordinateString.equals(""))
				{
					logger.debug("setting coordstring for " + regionName +" of length " + coordinateString.length());
					String[] coords = coordinateString.split(" ");
					for (int j = 1; j < coords.length; j++)
					{
						//logger.debug("longlat for " + j + " = " + coords[j]);
					String[] longlat = coords[j].split(",");
					String longit = longlat[0];
					String latit = longlat[1];
					Coordinate coord = new Coordinate(latit, longit);
					coordinateList.add(coord);
					}
				}
				polly.setCoordinates(coordinateList);
				returnable.add(polly);
			}
			myPolygons.put(regionName, returnable);
			/*else
			{
				returnable.setCoordinates(new ArrayList<Coordinate>());
			}*/
			
		}catch (SQLException e)
		{
			logger.error(e.getMessage(), e.fillInStackTrace());
			e.printStackTrace();
		}
		return returnable;
	}
	
	public Coordinate getCentroid(String regionName, String regionType)
	{
		Coordinate returnable = null;
		try{
			PreparedStatement cenprep = con.prepareStatement(censql);
			cenprep.setString(1, regionName);
			cenprep.setString(2, regionType);
			
			ResultSet cenrs = cenprep.executeQuery();
			while 
				(cenrs.next())
			{
				returnable = new Coordinate(cenrs.getString(4), cenrs.getString(3));
			}
		}catch (SQLException e)
		{
			logger.error(e.getMessage(), e.fillInStackTrace());
			e.printStackTrace();
		}
		
		return returnable;
	}
}
