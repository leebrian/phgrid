package gov.cdc.ncphi.phgrid.polygon.gmaps.loader;

import java.sql.SQLException;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger logger = Logger.getLogger(App.class);

	
    public static void main( String[] args )
    {
    	//KMLCentroidParser cp = new KMLCentroidParser("state", "state");
    	//loadCentroid("../geodata/statecentroid.kml", cp);
    	//cp = new KMLCentroidParser("zip3", "zip3");
    	//loadCentroid("../geodata/zip3centroid.kml", cp);
    	//KMLCentroidParser cp = new KMLCentroidParser ("zip5", "zip5");
    	//loadCentroid("../geodata/zip5centroid.kml", cp);
    	//cp = new KMLCentroidParser("zip5", "zip5");
    	//loadCentroid("../geodata/zip5centroid.kml", cp);
    	PolygonParser pp = new KMLSimplePolygonParser("zip5","zip5");
    	loadPolygon("../geodata/zip5polygon.kml", pp);
    	//loadPolygon ("../geodata/statepolygon_bal.flat", pp);
    	//pp = new KMLPolygonParser("zip_poly", "zip5");
    	//loadPolygon ("../geodata/zip5polygon.kml", pp);
    	//pp = new KMLSimplePolygonParser("zip3", "zip3");
    	//loadPolygon("../geodata/zip3polygon.kml", pp);
    	//pp = new KMLSimplePolygonParser("state", "state");
    	//loadPolygon("../geodata/stategen.kml", pp);
    	//loadPolygon("dtl_st", "state", "../geodata/statepolygon.kml");
    	//loadPolygon ("zip3", "zip3", "../geodata/zip3polygon.kml");
    	//loadPolygon ("zip_poly", "zip5", "../geodata/zip5polygon.kml");
    	//loadPolygon("zip3", "zip3", "./zip3part.kml");
    		
    }
    
    private static void loadPolygon (String polygonfileName, PolygonParser pp)
    {
		
		List<DatabasePolygon> polist = pp.parseReturn(polygonfileName);
		PolygonLoader pl = new SQLPolygonLoader();
		pl.createConnection();
		try
		{pl.loadData(polist);
		
		}catch (SQLException e)
 		{
			logger.error("Error loading " + polygonfileName	);
			logger.error(e.getMessage());
 			logger.error(e.getLocalizedMessage());
 		}
		logger.debug("completed parse and insert");
    }
    
    private static void loadCentroid (String centroidfileName, KMLCentroidParser cp)
    {
    	List<DatabaseCentroid> cenlist = cp.parseReturn(centroidfileName);
    	SQLCentroidLoader cl = new SQLCentroidLoader();
    	cl.createConnection();
    	try 
    	{
    		cl.loadData(cenlist);
    	}
    	catch (SQLException e)
 		{
			logger.error("Error loading " + centroidfileName	);
			logger.error(e.getMessage());
 			logger.error(e.getLocalizedMessage());
 		}
		logger.debug("completed parse and insert");
    }
    
}
