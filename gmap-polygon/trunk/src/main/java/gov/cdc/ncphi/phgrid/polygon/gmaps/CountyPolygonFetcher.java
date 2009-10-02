package gov.cdc.ncphi.phgrid.polygon.gmaps;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

public class CountyPolygonFetcher {

	private static final Logger logger = Logger.getLogger(CountyPolygonFetcher.class);
	
	Connection con;
	String sql = "select countyname, statename, countypolystring from countypolygons where countyname = ? and statename = ?";
	public CountyPolygonFetcher()
	{
		PolygonConnectionPool pcp = new PolygonConnectionPool();
		con = pcp.getConnection();
		
	}
	public StandardGmapPolygon getCountyPolygon(String county, String state)
	{
		StandardGmapPolygon returnable = new StandardGmapPolygon();
		try 
		{
			PreparedStatement prep = con.prepareStatement(sql);
			prep.setString(1, county);
			prep.setString(2, state);
			ResultSet rs = prep.executeQuery();
			rs.next();
			String countyName = rs.getString(1);
			String stateName = rs.getString(2);
			returnable.setRegionName(countyName + ", " + stateName);
			returnable.setRegionType("county");	
			String coordinateString = rs.getString(3);
			List<Coordinate> coordinateList = new ArrayList<Coordinate>();
			if (coordinateString != null && !coordinateString.equals(""))
			{
				
				logger.info("coordinateString = -" + coordinateString + "-");
				String[] coords = coordinateString.split(" ");
				//NOTE: you must skip the first item, as it is a whitespace
				for (int j = 1; j < coords.length; j++)
				{
					logger.info("longlat for " + j + " = " + coords[j]);
				String[] longlat = coords[j].split(",");
				String longit = longlat[0];
				String latit = longlat[1];
				Coordinate coord = new Coordinate(latit, longit);
				coordinateList.add(coord);
				}
			}
			returnable.setCoordinates(coordinateList);
		}catch (SQLException e)
		{
			logger.error(e.getMessage(), e.fillInStackTrace());
			e.printStackTrace();
		}
		return returnable;
	}
}
