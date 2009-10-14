package gov.cdc.ncphi.phgrid.gipse.dbimporter;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Transfer object for a specific GIPSE extract record.
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.13 1008-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
public class GIPSERecord {
	int dataSourceId;
	Date date;//using sql date because we want to map to just the month, day, year
	String zip5;
	String state;
	int condition;
	long count;
	
	
	public GIPSERecord(int dataSourceId, Date date, String zip5, String state,
			int condition, long count) {
		super();
		this.dataSourceId = dataSourceId;
		this.date = date;
		this.zip5 = zip5;
		this.state = state;
		this.condition = condition;
		this.count = count;
	}
	
	public int getDataSourceId() {
		return dataSourceId;
	}
	public void setDataSourceId(int dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getZip5() {
		return zip5;
	}
	public void setZip5(String zip5) {
		this.zip5 = zip5;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public int getCondition() {
		return condition;
	}
	public void setCondition(int condition) {
		this.condition = condition;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	
	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
}
