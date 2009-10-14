package edu.columbia.dbmi.context.common;

import javax.xml.namespace.QName;


public interface GridMedleeContextConstants {
	public static final String SERVICE_NS = "http://dbmi.columbia.edu/GridMedlee/Context";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "GridMedleeContextKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "GridMedleeContextResourceProperties");

	//Service level metadata (exposed as resouce properties)
	public static final QName CURRENTTIME = new QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "CurrentTime");
	public static final QName TERMINATIONTIME = new QName("http://docs.oasis-open.org/wsrf/2004/06/wsrf-WS-ResourceLifetime-1.2-draft-01.xsd", "TerminationTime");
	public static final QName STATUS = new QName("http://dbmi.columbia.edu/GridMedlee/medlee", "Status");
	
}
