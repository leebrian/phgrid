package gov.cdc.ncphi.phgrid.polygon.gmaps.loader;





import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class KMLPolygonParser extends DefaultHandler implements PolygonParser{
	private static final Logger logger = Logger.getLogger(KMLPolygonParser.class);
	List<DatabasePolygon> mypolygons;
	
	private String tempSVal;
	private String outerBoundVal;
	private String startString;
	private String polygonType;
	private String lastOuterBound;
	private List<String> coordStringList;
	private int start;
	private int end;
	
	//to maintain context
	private DatabasePolygon tempCP;
	
	
	public KMLPolygonParser( String kmlFilterString, String polygonType){
		mypolygons = new ArrayList<DatabasePolygon>();
		startString = kmlFilterString;
		this.polygonType = polygonType;
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
	
	private void parseDocument(String fileName) {
		
		//get a factory
		SAXParserFactory spf = SAXParserFactory.newInstance();
		try {
		
			//get a new instance of parser
			SAXParser sp = spf.newSAXParser();
			File inputFile = new File(fileName);
			//parse the file and also register this class for call backs
			sp.parse(inputFile, this);
			
		}catch(SAXException se) {
			logger.error(se.getMessage());
			se.printStackTrace();
		}catch(ParserConfigurationException pce) {
			logger.error(pce.getMessage());
			pce.printStackTrace();
		}catch (IOException ie) {
			logger.error(ie.getMessage());
			ie.printStackTrace();
		}
	}

	/**
	 * Iterate through the list and print
	 * the contents
	 */
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
	

	//Event Handlers
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		//reset
		tempSVal = "";
		if(qName.equalsIgnoreCase("Placemark")) {
			logger.debug("creating new polygon");
			//create a new instance of zipcode
			tempCP = new DatabasePolygon();	
			tempCP.setType(polygonType);
			coordStringList = new ArrayList<String>();
		}
	}
	

	public void characters(char[] ch, int start, int length) throws SAXException {
		tempSVal = new String(ch,start,length);
	}
	
	public void endElement(String uri, String localName, String qName) throws SAXException {

		if(qName.equalsIgnoreCase("Placemark")) {
			//add it to the list
			logger.debug("polygon to the list");
			tempCP.setPolygonStrings(coordStringList);
			mypolygons.add(tempCP);
			
		}else if (qName.equalsIgnoreCase("name")) {
			if (!tempSVal.equalsIgnoreCase(startString))
			{tempCP.setLocationName(tempSVal);
			logger.debug("setting name to "+ tempSVal);}
		}else if (qName.equalsIgnoreCase("coordinates")) {
			outerBoundVal = tempSVal;
			//tempCP.setPolygonStrings(coordStringList);
			logger.debug("setting outer bound coordinates of length " + outerBoundVal.length());
		}else if (qName.equalsIgnoreCase("outerBoundaryIs"))
		{
			//tempCP.setPolygonString(outerBoundVal);
			coordStringList.add(outerBoundVal);
			logger.debug("setting coordinate number " + coordStringList.size());
			//logger.debug("setting coordinates of length " + outerBoundVal.length());
		}
	/*	else if(qName.equalsIgnoreCase("MultiGeometry"));
		{
			logger.debug("multihit");
			
			if (tempCP != null && lastOuterBound != null)
				{logger.debug("setting the last outer bound of length " + lastOuterBound.length());
			tempCP.setPolygonString(lastOuterBound);}
			
			
		} */
	}
	
	public static void main(String[] args){
		String filename = null;
    	if(args.length >0)
    	{filename = args[0];
    	
    		KMLPolygonParser kzpl = new KMLPolygonParser("zip3", "zip3");
    		kzpl.runExample(filename);
    	}
    	
    	else
    	{
    		System.out.println("Please enter the filename to parse");
    	}
		

	}
    
}
