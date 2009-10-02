package gov.cdc.ncphi.phgrid.gridviewer.jsphelper;

import java.util.List;

public class C2FlotArray {
private List<DateCount> outlierArray;
private List<DateCount> averageArray;
private List<DateCount> valueArray;
public List<DateCount> getOutlierArray() {
	return outlierArray;
}
public void setOutlierArray(List<DateCount> outlierArray) {
	this.outlierArray = outlierArray;
}
public List<DateCount> getAverageArray() {
	return averageArray;
}
public void setAverageArray(List<DateCount> averageArray) {
	this.averageArray = averageArray;
}
public List<DateCount> getValueArray() {
	return valueArray;
}
public void setValueArray(List<DateCount> valueArray) {
	this.valueArray = valueArray;
}
}
