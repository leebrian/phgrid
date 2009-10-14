package gov.cdc.ncphi.phgrid.poicondai;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.rpc.ServiceException;

import org.apache.axis.message.MessageElement;
import org.apache.log4j.Logger;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.ciber.CaseCountWebService;
import com.ciber.CaseCountWebServiceLocator;
import com.ciber.CaseCountWebServiceSoap;
import com.ciber.PerDayCaseCountResponsePerDayCaseCountResult;

import gov.cdc.ncphi.phgrid.poicondai.jaxb.typea.NewDataSet;



/**
 * Hello world!
 *
 */
public class App 
{
	private static final Logger logger = Logger.getLogger(App.class);
	public static void main(String[] args)
	{
		/*
    	Calendar startDate = Calendar.getInstance();
    	startDate.set(2008, 6, 7);
    	Calendar endDate = Calendar.getInstance();
    	endDate.set(2008, 7, 26);
		NPDSTimeServiceFetcher ntsf = new NPDSTimeServiceFetcher();
		NPDSTimeSeriesByRegion npdsts = ntsf.getStateResults(
				"8", 
				"CO,UT,DC", 
				"", 
				startDate, 
				endDate, 
				"10", 
				"11", 
				"", 
				"TCV", 
				"");
		if (npdsts != null)
		logger.debug(npdsts.toString());
		else
			logger.error("nothing came back, apparently");
			*/
		App app = new App();
		app.GetAllStatesFor2008();
	}
	
	
	//counts of all clinical effects by state from jan1 2008 to dec 31, 2008
	
	public void GetAllStatesFor2008()
	{
		Calendar startDate = Calendar.getInstance();
    	startDate.set(2008, 0, 1);
    	Calendar endDate = Calendar.getInstance();
    	endDate.set(2008, 11, 31);
		NPDSTimeServiceFetcher ntsf = new NPDSTimeServiceFetcher();
		NPDSTimeSeriesByRegion npdsts = ntsf.getStateResults(
				"8", 
				"AL,AK,AZ,AR,CA,CO,CT,DE,DC,FL,GA,HI,ID,IL,IN,IA,KS,KY,LA,ME,MD,MA,MI,MN,MS,MO,MT,NE,NV,NH,NJ,NM,NY,NC,ND,OH,OK,OR,PA,PR,RI,SC,SD,TN,TX,UT,VT,VA,WA,WV,WI,WY", 
				"", 
				startDate, 
				endDate, 
				"1", 
				"24", 
				"334", 
				"CE", 
				"");
		if (npdsts != null)
			//logger.debug(npdsts.toString());
			logger.debug(toCSV(npdsts));
		else
			logger.error("nothing came back, apparently");
	}
	
	
	private String toCSV(NPDSTimeSeriesByRegion npdsts)
	{
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
		
		String returnable = "";
		Map<String, NPDSTimeSeries> m = npdsts.getAll();
		Set<String> keySet = m.keySet();
		Iterator<String> iter = keySet.iterator();
		while (iter.hasNext())
		{
			String key = iter.next();
			returnable = returnable + "\n\n"+ key + "\n\n";
			NPDSTimeSeries npdts = m.get(key);
			List<DateCount> countList  = npdts.getCountList();
			Iterator<DateCount> countiter = countList.iterator();
			while (countiter.hasNext())
			{
				DateCount dc = countiter.next();
				returnable = returnable + df.format(dc.getDate().getTime()) + "," + dc.getCount() + "\n";				
			}
		}
	     try{
	    	    // Create file 
	    	    FileWriter fstream = new FileWriter("out.txt");
	    	        BufferedWriter out = new BufferedWriter(fstream);
	    	    out.write(returnable);
	    	    //Close the output stream
	    	    out.close();
	    	    }catch (Exception e){//Catch exception if any
	    	      System.err.println("Error: " + e.getMessage());
	    	    }
		return returnable;
	}
	//    public static void main( String[] args )
//    {
//    	String systemUser = null, systemPassword = null, systemUserName = null, outputType = null;
//    	if(args.length >0)
//    	{
//    		
//    		systemUser = args[0];
//    		systemPassword = args[1];
//    		systemUserName = args[2];
//    		outputType = args[3];
//    	
//    	if (systemUser != null && systemPassword != null && systemUserName != null)
//    	{
//    	CaseCountWebServiceLocator service = new CaseCountWebServiceLocator();
//    	try
//    	{
//    	CaseCountWebServiceSoap port = service.getCaseCountWebServiceSoap();
//    	Calendar startDate = Calendar.getInstance();
//    	startDate.set(2008, 6, 7);
//    	Calendar endDate = Calendar.getInstance();
//    	endDate.set(2008, 7, 26);
//    	PerDayCaseCountResponsePerDayCaseCountResult result = 
//    		port.perDayCaseCount(
//    			systemUserName, 
//    			systemPassword, 
//    			systemUser, 
//    			"8", 
//    			"CO", 
//    			"", 
//    			startDate, 
//    			endDate, 
//    			"10", 
//    			"12", 
//    			"",
//    			"TCV", "", outputType);
//    	
//    	  JAXBContext context = JAXBContext.newInstance("gov.cdc.ncphi.phgrid.poicondai.jaxb.typea");
//
//          Unmarshaller unmarshaller = context.createUnmarshaller();
//
//          // check if we have an error
//
//          //ElementNSImpl me = (ElementNSImpl) caseCountDataResult.getAny();
//
//
//
//          //NodeList errorNodes = result.getElementsByTagName("Error");
//        /*  if (errorNodes.getLength() > 0) {
//              NodeList errorMessages = me.getElementsByTagName("Column1");
//              String errorMessage = errorMessages.item(0).getFirstChild().toString();
//              logger.error("NPDS Link Service Error - " + errorMessage);
//              throw new CaseCountServiceException("NPDS Link Service Error - " + errorMessage);
//          }*/
//
//
//          // get the diffgram root element i.e., the second MessageElement in the response
//         // Node withoutDiff = me.getChildNodes().item(0);
//         // logger.debug(withoutDiff);
//
//
//          // turn it into an object using JaxB
//        // NewDataSet item = (NewDataSet) unmarshaller.unmarshal(withoutDiff);
//
//    	MessageElement[] mearray= result.get_any();
//    	if (mearray != null && mearray.length >1)
//    		{MessageElement item = mearray[0];
//    		MessageElement secondItem = mearray[1];
//    		gov.cdc.ncphi.phgrid.poicondai.jaxb.typea.NewDataSet nds = (gov.cdc.ncphi.phgrid.poicondai.jaxb.typea.NewDataSet)
//    		unmarshaller.unmarshal(secondItem.getFirstChild());
//    		java.util.List<NewDataSet.Table> ndsl = nds.list();
//    		java.util.Iterator<NewDataSet.Table> iter = ndsl.iterator();
//    		//System.out.println(item.getAsString());
//    		//System.out.println(secondItem.getAsString());
//    		while(iter.hasNext())
//    		{
//    			NewDataSet.Table table = iter.next();
//    			logger.debug("StartDateTime: " + table.getStartDateTime() + "Total: " + table.getTotalCount());
//    		}
//    		}
//    //	System.out.println(result.toString());
//    //	System.out.println(ele.length);
//    //	for (int k=0; k<ele.length; k++)
//   // 	{
//    //		MessageElement mess = ele[k];
//    		
//    //	}
//    	}catch(ServiceException e)
//    	{
//    		logger.error(e);
//    	}
//    	catch(RemoteException e)
//    	{
//    		logger.error(e);
//    	}
//    	catch(Exception e)
//    	{
//    		logger.error(e);
//    	}
//    	}
//    	}
//    	else
//    	{
//    		System.out.println("Please enter the system user, system password, and system user name as variables for this test");
//    	}
//    }
}
