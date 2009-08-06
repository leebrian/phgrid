package gov.cdc.ncphi.phgrid.services.gipse.service.dao;

import java.sql.Date;
import org.apache.commons.lang.builder.ToStringBuilder;

/**
 * Transfer object for gipse store records.
 * @project PHGrid (Apache v2.0 license)
 * @author Brian Alexander Lee (brianalee@gmail.com)
 * @created May 29, 2009 10:59:41 AM
 */
public class Observation {
	private Date observationDate;
	private String zip3,zip5,state,classifier,indicator,age,serviceArea;
	private int value;
	
	public Observation(){
		super();
	}
	
	public Observation(Date observationDate, String zip3, String zip5,
			String state, String classifier, String indicator, int value,String age, String serviceArea) {
		super();
		this.observationDate = observationDate;
		this.zip3 = zip3;
		this.zip5 = zip5;
		this.state = state;
		this.classifier = classifier;
		this.indicator = indicator;
		this.value = value;
		this.age = age;
		this.serviceArea = serviceArea;
	}
	
	
	
	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getServiceArea() {
		return serviceArea;
	}

	public void setServiceArea(String serviceArea) {
		this.serviceArea = serviceArea;
	}

	public Date getObservationDate() {
		return observationDate;
	}
	public void setObservationDate(Date observationDate) {
		this.observationDate = observationDate;
	}
	public String getZip3() {
		return zip3;
	}
	public void setZip3(String zip3) {
		this.zip3 = zip3;
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
	public String getClassifier() {
		return classifier;
	}
	public void setClassifier(String classifier) {
		this.classifier = classifier;
	}
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result
				+ ((classifier == null) ? 0 : classifier.hashCode());
		result = prime * result
				+ ((indicator == null) ? 0 : indicator.hashCode());
		result = prime * result
				+ ((observationDate == null) ? 0 : observationDate.hashCode());
		result = prime * result
				+ ((serviceArea == null) ? 0 : serviceArea.hashCode());
		result = prime * result + ((state == null) ? 0 : state.hashCode());
		result = prime * result + value;
		result = prime * result + ((zip3 == null) ? 0 : zip3.hashCode());
		result = prime * result + ((zip5 == null) ? 0 : zip5.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Observation other = (Observation) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (classifier == null) {
			if (other.classifier != null)
				return false;
		} else if (!classifier.equals(other.classifier))
			return false;
		if (indicator == null) {
			if (other.indicator != null)
				return false;
		} else if (!indicator.equals(other.indicator))
			return false;
		if (observationDate == null) {
			if (other.observationDate != null)
				return false;
		} else if (!observationDate.equals(other.observationDate))
			return false;
		if (serviceArea == null) {
			if (other.serviceArea != null)
				return false;
		} else if (!serviceArea.equals(other.serviceArea))
			return false;
		if (state == null) {
			if (other.state != null)
				return false;
		} else if (!state.equals(other.state))
			return false;
		if (value != other.value)
			return false;
		if (zip3 == null) {
			if (other.zip3 != null)
				return false;
		} else if (!zip3.equals(other.zip3))
			return false;
		if (zip5 == null) {
			if (other.zip5 != null)
				return false;
		} else if (!zip5.equals(other.zip5))
			return false;
		return true;
	}

	public String toString(){
		return ToStringBuilder.reflectionToString(this);
	}
	
	
}
