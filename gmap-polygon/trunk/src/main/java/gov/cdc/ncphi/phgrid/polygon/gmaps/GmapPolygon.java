package gov.cdc.ncphi.phgrid.polygon.gmaps;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public interface GmapPolygon extends Serializable {
 String getRegionName();
 String getRegionType();
 List<Coordinate> getCoordinates();
 int getTotalCount();
 Object getPopupInfo();
 Map<String, List<TimeSeries>> getTimeSeries();
 Coordinate getCentroid();
 int getHighestCount();
 void setPopupInfo(Object popupInfo);
 void setTotalCount(int totalCount);
 void setHighestCount(int highestCount);
 void setTimeSeries(Map<String, List<TimeSeries>> timeSeries);
 void setStartDate(Calendar startDate);
 void setEndDate(Calendar endDate);
 Calendar getStartDate();
 Calendar getEndDate();

}
