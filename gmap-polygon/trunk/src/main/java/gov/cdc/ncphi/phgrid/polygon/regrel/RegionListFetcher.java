package gov.cdc.ncphi.phgrid.polygon.regrel;

import gov.cdc.ncphi.phgrid.polygon.gmaps.Coordinate;
import gov.cdc.ncphi.phgrid.polygon.gmaps.PolygonConnectionPool;
import gov.cdc.ncphi.phgrid.polygon.gmaps.RegionPolygonFetcher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * This class is used to fetch lists of the LocationRelation
 * class.  It creates a connection to the database using 
 * a properties file upon instantiation.
 * @author Peter White
 *
 */
public class RegionListFetcher 
{ 
	private static final Logger logger = Logger.getLogger(RegionListFetcher.class);
	
	Connection con;
	String tableName, sql, nameSql, singSql, regionSql;
	RegionPolygonFetcher rpf;
	static RegionListFetcher rlf = null;
	private RegionListFetcher()
	{
		RegionRelationConnectionPool rrcp = new RegionRelationConnectionPool();
		
		con = rrcp.getConnection();
		tableName = rrcp.getTableName();
		sql = "select regionid, regionparent from " + tableName + " where regionparent = ? and regiontype = ?";
		nameSql = "select regionparent from " + tableName + " where regionid = ?";
		regionSql = "select regionid, regionparent, regiontype from " + tableName + " where regionid = ? and regiontype = ?";
		rpf = RegionPolygonFetcher.getFetcher();
	}
	
	public static RegionListFetcher getRegionListFetcher()
	{
		if (rlf == null)
		{
			rlf = new RegionListFetcher();
		}
		return rlf;
	}
	/**
	 * This class takes the region type and region parent 
	 * and returns a list of RegionRelations.
	 * 
	 * @param regionType 
	 * @param regionParent
	 * @return a list of RegionRelation classes
	 */
	public List<RegionRelation> getListByParent(String regionType, String regionParent)
	{
		List<RegionRelation> returnable = new ArrayList<RegionRelation>();
		try 
		{
			PreparedStatement prep = con.prepareStatement(sql);
			prep.setString(1, regionParent);
			prep.setString(2, regionType);
			ResultSet rs = prep.executeQuery();
			while (rs.next())
			{
				RegionRelation rr = new RegionRelation();
				rr.setRegionType(regionType);
				rr.setRegionParent(regionParent);
				rr.setRegionName(rs.getString(1));
				logger.debug(rr.toString());
				returnable.add(rr);
			}
			
		}catch (SQLException e)
		{
			logger.error(e.getMessage(), e.fillInStackTrace());
			e.printStackTrace();
		}
		return returnable;
	}
	/**
	 * returns the parent of a given regionName
	 * @param regionName
	 * @return
	 */
	public String getRegionParent(String regionName)
	{
		String returnable = null;
		try 
		{
			PreparedStatement prep = con.prepareStatement(nameSql);
			prep.setString(1, regionName);
			ResultSet rs = prep.executeQuery();
			if(rs.next())
			{
				returnable = rs.getString(1);
			}
			
		}catch (SQLException e)
		{
			logger.error(e.getMessage(), e.fillInStackTrace());
			e.printStackTrace();
		}
		return returnable;
	}
	
	public RegionData getRegionDataForRegion(String regionName, String regionType)
	{
		RegionData returnable = null;
		try 
		{
			PreparedStatement prep = con.prepareStatement(regionSql);
			prep.setString(1, regionName);
			prep.setString(2, regionType);
			ResultSet rs = prep.executeQuery();
			if(rs.next())
			{
				returnable = new RegionData();
				returnable.setRegionName(rs.getString(1));
				returnable.setRegionParent(rs.getString(2));
				returnable.setRegionType(rs.getString(3));
			}
			//get centroid coordinates
			returnable.setCentroid(rpf.getCentroid(returnable.getRegionName(), returnable.getRegionType()));
			//get encoded google string for polygon
			
			
			
		}catch (SQLException e)
		{
			logger.error(e.getMessage(), e.fillInStackTrace());
			e.printStackTrace();
		}
		return returnable;
		
	}
	
	public List<RegionData> getRegionDataForRegions(String regionParent, String regionType)
	{
		List<RegionData> returnable = new ArrayList<RegionData>();
		List<RegionRelation> regions = getListByParent(regionType, regionParent);
		Iterator<RegionRelation> regIter = regions.iterator();
		while (regIter.hasNext())
		{
			RegionRelation regrel = regIter.next();
			RegionData regdat = new RegionData();
			regdat.setRegionName(regrel.getRegionName());
			regdat.setRegionParent(regrel.getRegionParent());
			regdat.setRegionType(regrel.getRegionType());
			regdat.setCentroid(rpf.getCentroid(regdat.getRegionName(), regdat.getRegionType()));
			returnable.add(regdat);
		}
		return returnable;
	}
}
