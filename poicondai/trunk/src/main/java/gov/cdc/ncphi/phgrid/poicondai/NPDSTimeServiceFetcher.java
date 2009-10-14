package gov.cdc.ncphi.phgrid.poicondai;

import org.apache.axis.message.MessageElement;
import org.apache.log4j.Logger;

import com.ciber.CaseCountWebServiceLocator;
import com.ciber.CaseCountWebServiceSoap;
import com.ciber.PerDayCaseCountResponsePerDayCaseCountResult;

import gov.cdc.ncphi.phgrid.poicondai.jaxb.typea.NewDataSet;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import javax.xml.rpc.ServiceException;

/**
 * This will fetch and digest results from the NPDS Service
 * using the generated AXIS and JAXB code, and then package
 * them into the appropriate TimeSeries or TimeSeriesByRegion
 * classes
 * @author Peter White <Peter.White@bearingpoint.com>
 *
 */
public class NPDSTimeServiceFetcher {
	private static final Logger logger = Logger.getLogger(NPDSTimeServiceFetcher.class);
	private static Properties defaultProps;
	private static String PROPERTIES_FILE = "/poicondai.properties";
	private String systemUserName = null, systemPassword = null, systemURL= null, systemUser = null;
	private CaseCountWebServiceSoap port = null;
	public NPDSTimeServiceFetcher()
	{
		// prepare to initialize AXIS Service
		
		//get properties and passwords
		try {
			defaultProps = new Properties();
	        InputStream is = getClass().getResourceAsStream(PROPERTIES_FILE);
	        if (is == null) logger.error("Could not read " + PROPERTIES_FILE);
	        defaultProps.load(is);
	        systemUser = defaultProps.getProperty("user");
	        systemPassword = defaultProps.getProperty("pass");
	        systemUserName = defaultProps.getProperty("systemuser");
	        systemURL = defaultProps.getProperty("url");
	        CaseCountWebServiceLocator service = new CaseCountWebServiceLocator();
			if (systemURL==null || systemURL.equals(""))
				port = service.getCaseCountWebServiceSoap();
			else
				port = service.getCaseCountWebServiceSoap(new URL(systemURL));
				
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {			
			logger.error(e.getMessage());
			//e.printStackTrace();
		}catch(ServiceException e)
    	{
    		logger.error(e);
    	}
		
	}
	/**
	 * This returns "Type A" results from the NPDS Service (just date and count for the given data)
	 * @param centerList
	 * @param stateList
	 * @param zipList
	 * @param startDate
	 * @param endDate
	 * @param startTime
	 * @param clinicalEffectList
	 * @param callVolumeType
	 * @param ceCombinationType
	 * @return
	 */
	public NPDSTimeSeries getGenericResults ( 
			String centerList,
    		String stateList, 
    		String zipList,
    		Calendar startDate, 
    		Calendar endDate, 
    		String startTime,
    		String endTime,
    		String clinicalEffectList,
    		String callVolumeType, 
    		String ceCombinationType)
	{
		NPDSTimeSeries returnable = null;
		try{
			PerDayCaseCountResponsePerDayCaseCountResult result = 
	    		port.perDayCaseCount(
	    			systemUser,
	    			systemPassword, 
	    			systemUserName, 
	    			centerList, 
	    			stateList, 
	    			zipList, 
	    			startDate, 
	    			endDate, 
	    			startTime, 
	    			endTime, 
	    			clinicalEffectList,
	    			callVolumeType,
	    			ceCombinationType, 
	    			"A");
	    	
	    	  JAXBContext context = JAXBContext.newInstance("gov.cdc.ncphi.phgrid.poicondai.jaxb.typea");
	          Unmarshaller unmarshaller = context.createUnmarshaller();
	          MessageElement[] mearray= result.get_any();
	      	if (mearray != null && mearray.length >1)
	      		{
	      		MessageElement secondItem = mearray[1];
	      		gov.cdc.ncphi.phgrid.poicondai.jaxb.typea.NewDataSet nds = (gov.cdc.ncphi.phgrid.poicondai.jaxb.typea.NewDataSet)
	      		unmarshaller.unmarshal(secondItem.getFirstChild());
	      		java.util.List<NewDataSet.Table> ndsl = nds.list();
	      		java.util.Iterator<NewDataSet.Table> iter = ndsl.iterator();
	      		returnable = new NPDSTimeSeries();
	      		while(iter.hasNext())
	      		{
	      			NewDataSet.Table table = iter.next();
	      			logger.debug("StartDateTime: " + table.getStartDateTime() + "Total: " + table.getTotalCount());
	      			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
	      			Date d = df.parse(table.getStartDateTime());
	      			Calendar date = Calendar.getInstance();
	      			date.setTime(d);
	      			returnable.addDateCount(new DateCount(date, (table.getTotalCount()).intValue()));
	      		}
	      		}
		}catch(RemoteException e)
    	{
    		logger.error(e);
    	}
    	catch(Exception e)
    	{
    		logger.error(e);
    	}
		return returnable;
	}
	/**
	 * This returns "Type B" results from the NPDS Service (date/time and count grouped by zipcode)
	 * @param centerList
	 * @param stateList
	 * @param zipList
	 * @param startDate
	 * @param endDate
	 * @param startTime
	 * @param endTime
	 * @param clinicalEffectList
	 * @param callVolumeType
	 * @param ceCombinationType
	 * @return
	 */
	public NPDSTimeSeriesByRegion getZipcodeResults(
			String centerList,
    		String stateList, 
    		String zipList,
    		Calendar startDate, 
    		Calendar endDate, 
    		String startTime,
    		String endTime,
    		String clinicalEffectList,
    		String callVolumeType, 
    		String ceCombinationType)
	{
		NPDSTimeSeriesByRegion returnable = null;
		try{
		PerDayCaseCountResponsePerDayCaseCountResult result = 
    		port.perDayCaseCount(
    			systemUser, 
    			systemPassword, 
    			systemUserName, 
    			centerList, 
    			stateList, 
    			zipList, 
    			startDate, 
    			endDate, 
    			startTime, 
    			endTime, 
    			clinicalEffectList,
    			callVolumeType,
    			ceCombinationType, 
    			"B");
    			
    	
    	  JAXBContext context = JAXBContext.newInstance("gov.cdc.ncphi.phgrid.poicondai.jaxb.typeb");
          Unmarshaller unmarshaller = context.createUnmarshaller();
          MessageElement[] mearray= result.get_any();
      	if (mearray != null && mearray.length >1)
      		{
      		MessageElement secondItem = mearray[1];
      		gov.cdc.ncphi.phgrid.poicondai.jaxb.typeb.NewDataSet nds = (gov.cdc.ncphi.phgrid.poicondai.jaxb.typeb.NewDataSet)
      		unmarshaller.unmarshal(secondItem.getFirstChild());
      		java.util.List<gov.cdc.ncphi.phgrid.poicondai.jaxb.typeb.NewDataSet.Table> ndsl = nds.getTable();
      		java.util.Iterator<gov.cdc.ncphi.phgrid.poicondai.jaxb.typeb.NewDataSet.Table> iter = ndsl.iterator();
      		returnable = new NPDSTimeSeriesByRegion();
      		String zipcodeName = "";
      		while(iter.hasNext())
      		{
      			gov.cdc.ncphi.phgrid.poicondai.jaxb.typeb.NewDataSet.Table table = iter.next();
      			logger.debug("StartDateTime: " + table.getStartDateTime() + " Total: " + table.getTotalCount() + " zipcode " + table.getZipcode());
      			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
      			Date d = df.parse(table.getStartDateTime());
      			Calendar date = Calendar.getInstance();
      			date.setTime(d);
      			zipcodeName = table.getZipcode();
      			returnable.AddDateCount(zipcodeName, new DateCount(date, table.getTotalCount().intValue()));
      		}
      		}
	}catch(RemoteException e)
	{
		logger.error(e);
	}
	catch(Exception e)
	{
		logger.error(e);
	}
		return returnable;
	}
	
	/**
	 * This returns "Type C" results from the NPDS Service (Date/time and counts grouped by state)
	 * @param centerList
	 * @param stateList
	 * @param zipList
	 * @param startDate
	 * @param endDate
	 * @param startTime
	 * @param endTime
	 * @param clinicalEffectList
	 * @param callVolumeType
	 * @param ceCombinationType
	 * @return
	 */
	public NPDSTimeSeriesByRegion getStateResults(
			String centerList,
    		String stateList, 
    		String zipList,
    		Calendar startDate, 
    		Calendar endDate, 
    		String startTime,
    		String endTime,
    		String clinicalEffectList,
    		String callVolumeType, 
    		String ceCombinationType)
	{
		NPDSTimeSeriesByRegion returnable = null;
		try{
		PerDayCaseCountResponsePerDayCaseCountResult result = 
    		port.perDayCaseCount(
    			systemUser, 
    			systemPassword, 
    			systemUserName, 
    			centerList, 
    			stateList, 
    			zipList, 
    			startDate, 
    			endDate, 
    			startTime, 
    			endTime, 
    			clinicalEffectList,
    			callVolumeType,
    			ceCombinationType, 
    			"C");
    			
    	
    	  JAXBContext context = JAXBContext.newInstance("gov.cdc.ncphi.phgrid.poicondai.jaxb.typec");
          Unmarshaller unmarshaller = context.createUnmarshaller();
          MessageElement[] mearray= result.get_any();
      	if (mearray != null && mearray.length >1)
      		{
      		MessageElement secondItem = mearray[1];
      		gov.cdc.ncphi.phgrid.poicondai.jaxb.typec.NewDataSet nds = (gov.cdc.ncphi.phgrid.poicondai.jaxb.typec.NewDataSet)
      		unmarshaller.unmarshal(secondItem.getFirstChild());
      		java.util.List<gov.cdc.ncphi.phgrid.poicondai.jaxb.typec.NewDataSet.Table> ndsl = nds.getTable();
      		java.util.Iterator<gov.cdc.ncphi.phgrid.poicondai.jaxb.typec.NewDataSet.Table> iter = ndsl.iterator();
      		returnable = new NPDSTimeSeriesByRegion();
      		String stateName = "";
      		while(iter.hasNext())
      		{
      			gov.cdc.ncphi.phgrid.poicondai.jaxb.typec.NewDataSet.Table table = iter.next();
      			logger.debug("StartDateTime: " + table.getStartDateTime() + " Total: " + table.getTotalCount() + " State " + table.getStateName());
      			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
      			Date d = df.parse(table.getStartDateTime());
      			Calendar date = Calendar.getInstance();
      			date.setTime(d);
      			stateName = table.getStateName();
      			returnable.AddDateCount(stateName, new DateCount(date, table.getTotalCount().intValue()));
      		}
      		}
	}catch(RemoteException e)
	{
		logger.error(e);
	}
	catch(Exception e)
	{
		logger.error(e);
	}
		return returnable;
	}
	
}
