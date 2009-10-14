package gov.cdc.ncphi.phgrid.polygon.gmaps.loader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class KMLSimplePolygonParser implements PolygonParser{
	private static final Logger logger = Logger.getLogger(KMLSimplePolygonParser.class);
	List<DatabasePolygon> mypolygons;
	private static String startString;
	private static String polygonType;
	Map<String, String> stateList;
	Properties prop;
	private static final String PROPERTIES_FILE = "/stateList.properties";

	public KMLSimplePolygonParser( String kmlFilterString, String polygonType){
		mypolygons = new ArrayList<DatabasePolygon>();
		
		startString = kmlFilterString;
		this.polygonType = polygonType;
		
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
	    		stateList.put(prop.getProperty(stateabbr), stateabbr);
	    	}
	       	} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {			
			logger.error(e.getMessage());
			e.printStackTrace();
        }  	
	}
	public void runExample(String fileName) {
		parseDocument(fileName);
		printData();
	}
	
	public List<DatabasePolygon> parseReturn (String fileName)
	{
		parseDocument(fileName);
		return mypolygons;
	}
	private void printData(){
		
		System.out.println("No of county polygons '" + mypolygons.size() + "'.");
		logger.info("No of county polygons '" + mypolygons.size() + "'.");
		Iterator it = mypolygons.iterator();
		String printable = "";
		while(it.hasNext()) {
			printable = it.next().toString();
			System.out.println(printable);
			logger.debug(printable);
		}
	}
	 public void parseDocument(String fileName) {

		 
		  try {
		  File file = new File(fileName);
		  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		  DocumentBuilder db = dbf.newDocumentBuilder();
		  Document doc = db.parse(file);
		  doc.getDocumentElement().normalize();
		  logger.debug("Root element " + doc.getDocumentElement().getNodeName());
		  NodeList nodeLst = doc.getElementsByTagName("Placemark");
		  logger.debug("Information of all employees");

		  for (int s = 0; s < nodeLst.getLength(); s++) {

		    Node fstNode = nodeLst.item(s);
		    
		    if (fstNode.getNodeType() == Node.ELEMENT_NODE) {
		  DatabasePolygon polly = new DatabasePolygon();
		  polly.setType(polygonType);
		  List<String> pollyStrings = new ArrayList<String>();
		           Element fstElmnt = (Element) fstNode;
		      NodeList fstNmElmntLst = fstElmnt.getElementsByTagName("name");
		      Element fstNmElmnt = (Element) fstNmElmntLst.item(0);
		      NodeList fstNm = fstNmElmnt.getChildNodes();
		      logger.debug("name : "  + ((Node) fstNm.item(0)).getNodeValue());
		    /*  if (polygonType.equals("state"))
		      {
		    	  String loc = ((Node) fstNm.item(0)).getNodeValue();
		    	  String locabbr = stateList.get(loc.toUpperCase());
		    	  logger.debug("added name as locabbr: " + locabbr);
		    	  polly.setLocationName(locabbr);
		      }
		      else
		      {*/
		    	  polly.setLocationName(((Node) fstNm.item(0)).getNodeValue());
		      //}
		      NodeList outBndElmntLst = fstElmnt.getElementsByTagName("outerBoundaryIs");
		      for (int k = 0; k< outBndElmntLst.getLength(); k++)
		      {
		    	  Node fstOBNode = outBndElmntLst.item(k);
		    	  if (fstOBNode.getNodeType() == Node.ELEMENT_NODE){
		    		  Element fstOBElmnt = (Element) fstOBNode;
		    		  NodeList fstOBElmntLst = fstOBElmnt.getElementsByTagName("coordinates");
		    		  Element fstCoordElmnt = (Element) fstOBElmntLst.item(0);
		    		  NodeList fstCoord = fstCoordElmnt.getChildNodes();
		    		  logger.debug("coordinate size: " + ((Node) fstCoord.item(0)).getNodeValue().length());
		    		  pollyStrings.add(((Node) fstCoord.item(0)).getNodeValue());
		    	  }
		      }
		      polly.setPolygonStrings(pollyStrings);
		      mypolygons.add(polly);
		      }

		  }
		  } catch (Exception e) {
		    logger.error(e.getMessage(), e);
		  }
		 }
		public static void main(String[] args){
			String filename = null;
	    	if(args.length >0)
	    	{filename = args[0];
	    	
	    		KMLSimplePolygonParser kzpl = new KMLSimplePolygonParser("zip5", "zip5");
	    		kzpl.runExample(filename);
	    	}
	    	
	    	else
	    	{
	    		System.out.println("Please enter the filename to parse");
	    	}
			

		}
	    

}
