package gov.cdc.ncphi.phgrid.npdsgmaps.jsphelper;

public class TimeTrack {
	private String trackerType;
	private long trackerTime;
	TimeTrack(String inTrackerType, long inTrackerTime)
	{
		this.trackerTime = inTrackerTime;
		this.trackerType = inTrackerType;
	}
	public String getTrackerType() {
		return trackerType;
	}
	public void setTrackerType(String trackerType) {
		this.trackerType = trackerType;
	}
	public long getTrackerTime() {
		return trackerTime;
	}
	public void setTrackerTime(long trackerTime) {
		this.trackerTime = trackerTime;
	}
	
}
