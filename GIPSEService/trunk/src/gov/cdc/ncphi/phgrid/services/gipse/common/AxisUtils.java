package gov.cdc.ncphi.phgrid.services.gipse.common;

import java.io.StringWriter;
import java.lang.reflect.Method;

import javax.xml.namespace.QName;

import org.apache.axis.Message;
import org.apache.axis.MessageContext;
import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.ser.BeanSerializer;
import org.apache.axis.server.AxisServer;
import org.xml.sax.helpers.AttributesImpl;

/**
 * Utility class for serializing/deserializing Axis beans generated with wsdl2java to/from xml. 
 * Inspired by blog post: http://bwithers.wordpress.com/2006/07/29/serializing-an-axis-javabean-object-to-xml/
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.28 2252-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 *
 */
public class AxisUtils {
	/**
	 * never instantiate since all methods are static.
	 */
private AxisUtils(){}

/**
 * Serialize an axis bean into an xml string.
 * @param axisObject must be an wsdl2java generated bean
 * @param removeNamespaces
 * @param prettyPrint
 * @return xml string
 * @throws Exception
 */
public static final String serializeAxisObject(final Object axisObject, 
		final boolean removeNamespaces,
        final boolean prettyPrint) throws Exception{	
	final StringWriter outStr = new StringWriter();
	final TypeDesc typeDesc = getAxisTypeDesc(axisObject);
	QName qname = typeDesc.getXmlType();
	String lname = qname.getLocalPart();
	if (lname.startsWith(">") && lname.length() > 1){
		lname = lname.substring(1);
	}
	
	if (removeNamespaces){
		qname = new QName(lname);
	}else{
		qname = new QName(qname.getNamespaceURI(), lname);
	}
	
	final AxisServer server = new AxisServer();
	final BeanSerializer ser = new BeanSerializer(axisObject.getClass(), qname, typeDesc);
	final SerializationContext ctx = new SerializationContext(outStr, new MessageContext(server));
	ctx.setSendDecl(false);
	ctx.setDoMultiRefs(false);
	ctx.setPretty(prettyPrint);
	ser.serialize(qname, new AttributesImpl(), axisObject, ctx);
	
	String xml = outStr.toString();
	
	if (removeNamespaces)
	{
		// remove any namespace attributes
		xml = xml.replaceAll(" xmlns[:=].*?\".*?\"", "")
		.replaceAll(" xsi:type=\".*?\"", "")
		.replaceAll("ns[0-9]*:","");
	}
	
	return xml;
}

private static final String SOAP_START = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\"><soapenv:Header /><soapenv:Body>";
private static final String SOAP_START_XSI = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\"><soapenv:Header /><soapenv:Body>";
private static final String SOAP_END = "</soapenv:Body></soapenv:Envelope>";


public static Object deserializeAxisObject(String xml, Class clazz) throws Exception {
	Object axisObject = null;
	try {
		xml = xml.replaceAll("<\\?.*\\?>","");//replace any xml headers that may be in your xml fragment (since we're wrapping it with new header/footer the xml header would be in the wrong place
		Message message = new Message(SOAP_START + xml + SOAP_END);
		axisObject = message.getSOAPEnvelope().getFirstBody().getObjectValue(clazz);
	}
	catch (Exception e) {
	// most likely namespace error due to removed namespaces
		Message message = new Message(SOAP_START_XSI + xml + SOAP_END);
		axisObject = message.getSOAPEnvelope().getFirstBody().getObjectValue(clazz);
	}
	return axisObject;
}

/**
 * Finds the axis TypeDesc based on the reflecting on the object passed in.
 * @param obj Must be a wsdl2java generated bean
 * @return appropriate typedesc retrieved from calling the getTypeDesc method on the passed in object
 * @throws Exception
 */
private static TypeDesc getAxisTypeDesc(final Object axisObject) throws Exception{
    final Class objClass = axisObject.getClass();
    final Method methodGetTypeDesc = objClass.getMethod("getTypeDesc", new Class[] {});
    final TypeDesc typeDesc = (TypeDesc) methodGetTypeDesc.invoke(axisObject, new Object[] {});
    return typeDesc;
}


}
