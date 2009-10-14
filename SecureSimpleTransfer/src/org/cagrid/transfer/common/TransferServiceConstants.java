package org.cagrid.transfer.common;

import javax.xml.namespace.QName;


public interface TransferServiceConstants {
	public static final String SERVICE_NS = "http://transfer.cagrid.org/TransferService";
	public static final QName RESOURCE_KEY = new QName(SERVICE_NS, "TransferServiceKey");
	public static final QName RESOURCE_PROPERTY_SET = new QName(SERVICE_NS, "TransferServiceResourceProperties");

	//Service level metadata (exposed as resouce properties)
	
}
