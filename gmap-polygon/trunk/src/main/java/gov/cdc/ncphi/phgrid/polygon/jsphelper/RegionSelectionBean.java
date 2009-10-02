package gov.cdc.ncphi.phgrid.polygon.jsphelper;

import gov.cdc.ncphi.phgrid.polygon.regrel.RegionListFetcher;
import gov.cdc.ncphi.phgrid.polygon.regrel.RegionRelation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import org.apache.log4j.Logger;

/**
 * This is a bean used to pull lists of regions, and manage
 * selected regions
 * @author Peter White
 *
 */
public class RegionSelectionBean {

	private Properties prop;
	private static String PROPERTIES_FILE = "/stateList.properties";
	private static final Logger logger = Logger.getLogger(RegionSelectionBean.class);
	private HashMap<String, String> stateList;
	/**
	 * The constructor of the RegionSelectionBean
	 * uses the properties file to make an arraylist of statenames"
	 */
	public RegionSelectionBean()
	{
		stateList = new HashMap<String, String>();
		try {
			prop = new Properties();
	        InputStream is = getClass().getResourceAsStream(PROPERTIES_FILE);
	        if (is == null) logger.error("Could not read " + PROPERTIES_FILE);
	        prop.load(is);
	        Set sortKeys = new TreeSet();
	    	sortKeys.addAll(prop.keySet());
	    	Iterator stateEnum =  sortKeys.iterator();
	    	String stateabbr = "";
	    	while (stateEnum.hasNext())
	    	{
	    		stateabbr = (String) stateEnum.next();
	    		stateList.put(stateabbr, prop.getProperty(stateabbr));
	    	}
	       	} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {			
			logger.error(e.getMessage());
			e.printStackTrace();
        }  	
	}
	
	/**
	 * This method returns all the regionrelations of regiontype keyed by regionparent
	 * 
	 * @param regionType
	 * @param regionParent
	 * @return
	 */
public Collection<RegionRelation> getRegionListByParentAndType(String regionType, String regionParent)
{
	//Collection<RegionRelation> returnable = new ArrayList<RegionRelation>();
	RegionListFetcher rlf = RegionListFetcher.getRegionListFetcher();
	return rlf.getListByParent(regionType, regionParent);
	
	//return returnable;
}

/**
 * returns the list of current states keyed by their abbreviations
 * @return
 */
public HashMap<String,String> getStateList()
{
	return stateList;
}

/**
 * returns the parent of a given regionName.
 * @param regionName
 * @return
 */
public String getRegionParent(String regionName)
{
	RegionListFetcher rlf = RegionListFetcher.getRegionListFetcher();
	return rlf.getRegionParent(regionName);
}
	

}
