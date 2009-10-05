package gov.cdc.ncphi.phgrid.services.gipse.beans;

import java.util.Properties;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import org.apache.log4j.Logger;

public class IndicatorValue {

	private int indicatorId;
	public static Properties indicators;
	private static String PROPERTIES_FILE = "/ClinicalEffectValues.properties";
	private static final Logger logger = Logger.getLogger(IndicatorValue.class);
	
	
	public IndicatorValue( String search) {
	        try {   
	    		indicators = new Properties();
		InputStream inputStream = getClass().getResourceAsStream(PROPERTIES_FILE);
        if (inputStream != null) {
        	indicators.load(inputStream);
        	this.setIndicatorId(Integer.valueOf(getValue(search)));
        }
        else
        	System.out.println("Could not read " + PROPERTIES_FILE);
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
			//e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			this.setIndicatorId(0);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static String getValue(String indicatorSearchTerm) {
		return indicators.getProperty(indicatorSearchTerm.replace(" ", ""), "0");
	}
	public int getIndicatorId() {
		return indicatorId;
	}
	private void setIndicatorId(int indicatorId) {
		this.indicatorId = indicatorId;
	}
}
