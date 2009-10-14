package gov.cdc.ncphi.phgrid.gipse.dbimporter;

import java.util.Date;

import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Transfer object for results information on an importer task or job.
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.13 0926-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
public class ImporterJobResults {
	private String description;
	private long recordsInserted = 0;
	private long recordsUpdated = 0;
	private long recordsDeleted = 0;
	private long recordsTotal = 0;
	private long recordsSkipped = 0;
	private Date startDate;
	private Date completeDate;
	
	/**
	 * Defaults the start date to now.
	 * @param description
	 */
	public ImporterJobResults(String description){
		this(description, new Date());
	}
	
	public ImporterJobResults(String description, Date startDate) {
		this.description = description;
		this.startDate = startDate;
	}
	
	/**
	 * Add the provided results counts to this instance. Description is ignored. start/completeDate is ignored.
	 * @param results
	 */
	public void addResults(ImporterJobResults results){
		recordsInserted += results.getRecordsInserted();
		recordsUpdated += results.getRecordsUpdated();
		recordsDeleted += results.getRecordsDeleted();
		recordsTotal += results.getRecordsTotal();
		recordsSkipped += results.getRecordsSkipped();
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getRecordsInserted() {
		return recordsInserted;
	}
	public void setRecordsInserted(long recordsInserted) {
		this.recordsInserted = recordsInserted;
	}
	public long getRecordsUpdated() {
		return recordsUpdated;
	}
	public void setRecordsUpdated(long recordsUpdated) {
		this.recordsUpdated = recordsUpdated;
	}
	public long getRecordsDeleted() {
		return recordsDeleted;
	}
	public void setRecordsDeleted(long recordsDeleted) {
		this.recordsDeleted = recordsDeleted;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getCompleteDate() {
		return completeDate;
	}
	public void setCompleteDate(Date completeDate) {
		this.completeDate = completeDate;
	}
	
	public long getRecordsTotal() {
		return recordsTotal;
	}

	public void setRecordsTotal(long recordsTotal) {
		this.recordsTotal = recordsTotal;
	}
	
	public long getRecordsSkipped() {
		return recordsSkipped;
	}

	public void setRecordsSkipped(long recordsSkipped) {
		this.recordsSkipped = recordsSkipped;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
}
