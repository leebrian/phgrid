package edu.columbia.dbmi.common;

import javax.xml.namespace.QName;


public interface GridMedleeConstants {
	public static final String SERVICE_NS = "http://dbmi.columbia.edu/GridMedlee";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "GridMedleeKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "GridMedleeResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
