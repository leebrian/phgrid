package gov.cdc.ncphi.phgrid.services.gipse.common.dao;

import java.sql.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Contains the query parameters used to locate gipse records.
 * @project PHGrid (Apache v2.0 license)
 * @author Brian Alexander Lee (brianalee@gmail.com)
 * @created May 29, 2009 1:58:16 PM
 * 
 *
 */
public class QueryParameters {
	private Date startDate;
	private Date endDate;
	private String [] states;
	private String classifier;
	private String [] indicators;
	private String [] zip3s;
	private String [] zip5s;
	
	public QueryParameters(){
		super();
	}
	
	public QueryParameters(String classifier, Date endDate, String[] indicators,
			Date startDate, String[] states, String[] zip3s, String[] zip5s) {
		super();
		this.classifier = classifier;
		this.endDate = endDate;
		this.indicators = indicators;
		this.startDate = startDate;
		this.states = states;
		this.zip3s = zip3s;
		this.zip5s = zip5s;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
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
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	
	

}
