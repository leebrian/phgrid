package gov.cdc.ncphi.phgrid.poicondai;

import java.io.Serializable;
import java.util.Calendar;
/**
 * The Generic Unit for Time Series returned by poicondai,
 * it contains a calendar and a count. 
 * @author Peter White <peter.white@bearingpoint.com>
 *
 */
public class DateCount implements Serializable {
/**
	 * 
	 */
	private static final long serialVersionUID = 5943927251518887727L;
	
Calendar date;
int count;

public DateCount(Calendar date, int count)
{
	this.date = date;
	this.count = count;
}

public Calendar getDate() {
	return date;
}
public void setDate(Calendar date) {
	this.date = date;
}
public int getCount() {
	return count;
}
public void setCount(int count) {
	this.count = count;
}

}
