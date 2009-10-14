package gov.cdc.ncphi.phgrid.polygon.gmaps.loader;



import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;


public class FlatFilePolygonParser extends DefaultHandler implements PolygonParser{
	private static final Logger logger = Logger.getLogger(FlatFilePolygonParser.class);
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
	
	
	public FlatFilePolygonParser(  String polygonType){
		mypolygons = new ArrayList<DatabasePolygon>();
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

			File inputFile = new File(fileName);
			FileReader rd = new FileReader(inputFile);
			logger.debug("reading file of length "+ inputFile.length());
			char[] buf = new char[(int)inputFile.length()];
			rd.read(buf);
			String patternStr = new String(buf);
			parseString(patternStr);

			//parse the file and also register this class for call backs
			
		}catch(IOException se) {
			logger.error(se.getMessage());
			se.printStackTrace();
		}
	}

	/**
	 * Iterate through the list and print
	 * the contents
	 */
	private void printData(){
		
		System.out.println("No of polygons '" + mypolygons.size() + "'.");
		logger.debug("No of polygons '" + mypolygons.size() + "'.");
		Iterator<DatabasePolygon> it = mypolygons.iterator();
		String printable = "";
		while(it.hasNext()) {
			printable = it.next().toString();
			System.out.println(printable);
			logger.debug(printable);
		}
	}
	

	//Event Handlers
	public void parseString(String patternStr)
	{
		String [] lineSplit = null;
		String rsplit = null;
		if (patternStr != null)
		{
		String[] rowSplit = patternStr.split("\n");
		logger.debug("number of rows: "+rowSplit.length);
		for (int k = 0; k<rowSplit.length; k++)
		{
			if(rowSplit[k] != null)
				{
				rsplit = rowSplit[k];
				logger.debug("pricessing row :" + rsplit);
				lineSplit = rsplit.split(":");
				}
			if (lineSplit.length >1)
			{
				logger.debug("linesplit length :" + lineSplit.length);
				logger.debug("adding area " + lineSplit[0]);
				logger.debug("with polygon string length " + lineSplit[0]);
				DatabasePolygon dp = new DatabasePolygon();
				dp.setLocationName(lineSplit[0]);
				dp.setType(polygonType);
				List<String> polygonStrings = new ArrayList<String>();
				polygonStrings.add(" " + lineSplit[1]);
				dp.setPolygonStrings(polygonStrings);
				mypolygons.add(dp);
			}
		}
		}
		else 
			logger.error("empty file, apparently");
	}
	
	public static void main(String[] args){
		String filename = null;
    	if(args.length >0)
    	{filename = args[0];
    	
    		FlatFilePolygonParser kzpl = new FlatFilePolygonParser("zip3");
    		kzpl.runExample(filename);
    	}
    	
    	else
    	{
    		System.out.println("Please enter the filename to parse");
    	}
		

	}
    
}
