package gov.cdc.ncphi.phgrid.gridviewer.jsphelper;

public class DateCount {
	java.util.Date date;
	public java.util.Date getDate() {
		return date;
	}
	public void setDate(java.util.Date date) {
		this.date = date;
	}
	public double getCount() {
		return count;
	}
	public void setCount(double count) {
		this.count = count;
	}
	double count;
	DateCount(java.util.Date date, double count)
	{
		this.count = count;
		this.date = date;
	}
}
