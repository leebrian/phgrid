package gov.cdc.ncphi.phgrid.services.gipse.beans;

import org.apache.commons.lang.builder.ToStringBuilder;
import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Observation implements Serializable {

	private static final long serialVersionUID = 1L;
	private Calendar startDate;
	private Calendar endDate;
	private int startHour=0;
	private int endHour=24;
	private String[] zip3s;
	private String[] zip5s;
	private String[] states;
	private String[] indicators; 
	private String[] orgLists;
	private int smalledValueReported;
	private String classifier;
	private String callVolumeType="HECV"; //
	
	//response object properties
	private Calendar occurrenceDate;
	private int count;
	private String location;
	
	
	
	public int getStartHour() {
		return startHour;
	}
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	public int getEndHour() {
		return endHour;
	}
	public void setEndHour(int endHour) {
		this.endHour = endHour;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public int getSmalledValueReported() {
		return smalledValueReported;
	}
	public void setSmalledValueReported(int smalledValueReported) {
		this.smalledValueReported = smalledValueReported;
	}
	public Calendar getOccurrenceDate() {
		return occurrenceDate;
	}
	public void setOccurrenceDate(Calendar occurrenceDate) {
		this.occurrenceDate = occurrenceDate;
	}
	public String[] getOrgLists() {
		return orgLists;
	}
	public void setOrgLists(String[] orgLists) {
		this.orgLists = orgLists;
	}

	public String getCallVolumeType() {
		return callVolumeType;
	}

	public void setCallVolumeType(String callVolumeType) {
		this.callVolumeType = callVolumeType;
	}
	public Calendar getStartDate() {
		return startDate;
	}
	public void setStartDate(Calendar startDate) {
		this.startDate = startDate;
	}
	public Calendar getEndDate() {
		return endDate;
	}
	public void setEndDate(Calendar endDate) {
		this.endDate = endDate;
	}
	
	public String[] getZip3s() {
		return zip3s;
	}
	public void setZip3s(String[] zip3s) {
		this.zip3s = zip3s;
	}
	public String[] getZip5s() {
		return zip5s;
	}
	public void setZip5s(String[] zip5s) {
		this.zip5s = zip5s;
	}
	public String[] getStates() {
		return states;
	}
	public void setStates(String[] states) {
		this.states = states;
	}
	public String getClassifier() {
		return classifier;
	}
	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}
	public String[] getIndicators() {
		return indicators;
	}
	public void setIndicators(String[] indicators) {
		this.indicators = indicators;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public String getZip3sString() {
		if(zip3s != null)
	       return arrString(zip3s, ",");
		else
			return "";
	}
	public String getZip5sString() {
		if(zip5s != null)
	       return arrString(zip5s, ",");
		else
			return "";
	}
	public String getStatesString() {
		if(states != null)
	       return arrString(states, ",");
		else
			return "";
	}
	public String getIndicatorsString() {
		if(indicators != null)
	       return arrString(indicators, ",");
		else
			return "";
	}
	public String getOrgListsString() {
		if(orgLists != null)
	       return arrString(orgLists, ",");
		else
			return "";
	}
	
	
	public static String arrString(String[] a, String separator) {
	    StringBuffer result = new StringBuffer();
	    if (a.length > 0) {
	        result.append(a[0]);
	        for (int i=1; i<a.length; i++) {
	            result.append(separator);
	            result.append(a[i]);
	        }
	    }
	    return result.toString();
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}


}
