package gov.cdc.ncphi.phgrid.polygon.gmaps;

public class TimeSeries implements Comparable{
private java.util.Calendar countDate;
private int count;
public java.util.Calendar getCountDate() {
	return countDate;
}
public void setCountDate(java.util.Calendar countDate) {
	this.countDate = countDate;
}
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}
public int compareTo(Object arg0) {
	TimeSeries comparatorSeries = (TimeSeries) arg0;
	return countDate.compareTo(comparatorSeries.getCountDate());
}
}
