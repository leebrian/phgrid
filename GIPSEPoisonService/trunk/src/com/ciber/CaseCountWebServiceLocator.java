/**
 * CaseCountWebServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package com.ciber;

public class CaseCountWebServiceLocator extends org.apache.axis.client.Service implements com.ciber.CaseCountWebService {

/**
 * CaseCount per day based upon CallType and Output Type
 */

    public CaseCountWebServiceLocator() {
    }


    public CaseCountWebServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public CaseCountWebServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for CaseCountWebServiceSoap
    private java.lang.String CaseCountWebServiceSoap_address = "http://12.193.64.222:83/LinkWebService/CaseCount.asmx";

    public java.lang.String getCaseCountWebServiceSoapAddress() {
        return CaseCountWebServiceSoap_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CaseCountWebServiceSoapWSDDServiceName = "CaseCountWebServiceSoap";

    public java.lang.String getCaseCountWebServiceSoapWSDDServiceName() {
        return CaseCountWebServiceSoapWSDDServiceName;
    }

    public void setCaseCountWebServiceSoapWSDDServiceName(java.lang.String name) {
        CaseCountWebServiceSoapWSDDServiceName = name;
    }

    public com.ciber.CaseCountWebServiceSoap getCaseCountWebServiceSoap() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CaseCountWebServiceSoap_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCaseCountWebServiceSoap(endpoint);
    }

    public com.ciber.CaseCountWebServiceSoap getCaseCountWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ciber.CaseCountWebServiceSoapStub _stub = new com.ciber.CaseCountWebServiceSoapStub(portAddress, this);
            _stub.setPortName(getCaseCountWebServiceSoapWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCaseCountWebServiceSoapEndpointAddress(java.lang.String address) {
        CaseCountWebServiceSoap_address = address;
    }


    // Use to get a proxy class for CaseCountWebServiceSoap12
    private java.lang.String CaseCountWebServiceSoap12_address = "http://12.193.64.222:83/LinkWebService/CaseCount.asmx";

    public java.lang.String getCaseCountWebServiceSoap12Address() {
        return CaseCountWebServiceSoap12_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String CaseCountWebServiceSoap12WSDDServiceName = "CaseCountWebServiceSoap12";

    public java.lang.String getCaseCountWebServiceSoap12WSDDServiceName() {
        return CaseCountWebServiceSoap12WSDDServiceName;
    }

    public void setCaseCountWebServiceSoap12WSDDServiceName(java.lang.String name) {
        CaseCountWebServiceSoap12WSDDServiceName = name;
    }

    public com.ciber.CaseCountWebServiceSoap getCaseCountWebServiceSoap12() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(CaseCountWebServiceSoap12_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getCaseCountWebServiceSoap12(endpoint);
    }

    public com.ciber.CaseCountWebServiceSoap getCaseCountWebServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            com.ciber.CaseCountWebServiceSoap12Stub _stub = new com.ciber.CaseCountWebServiceSoap12Stub(portAddress, this);
            _stub.setPortName(getCaseCountWebServiceSoap12WSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setCaseCountWebServiceSoap12EndpointAddress(java.lang.String address) {
        CaseCountWebServiceSoap12_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     * This service has multiple ports for a given interface;
     * the proxy implementation returned may be indeterminate.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (com.ciber.CaseCountWebServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ciber.CaseCountWebServiceSoapStub _stub = new com.ciber.CaseCountWebServiceSoapStub(new java.net.URL(CaseCountWebServiceSoap_address), this);
                _stub.setPortName(getCaseCountWebServiceSoapWSDDServiceName());
                return _stub;
            }
            if (com.ciber.CaseCountWebServiceSoap.class.isAssignableFrom(serviceEndpointInterface)) {
                com.ciber.CaseCountWebServiceSoap12Stub _stub = new com.ciber.CaseCountWebServiceSoap12Stub(new java.net.URL(CaseCountWebServiceSoap12_address), this);
                _stub.setPortName(getCaseCountWebServiceSoap12WSDDServiceName());
                return _stub;
            }
        }
        catch (java.lang.Throwable t) {
            throw new javax.xml.rpc.ServiceException(t);
        }
        throw new javax.xml.rpc.ServiceException("There is no stub implementation for the interface:  " + (serviceEndpointInterface == null ? "null" : serviceEndpointInterface.getName()));
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(javax.xml.namespace.QName portName, Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        if (portName == null) {
            return getPort(serviceEndpointInterface);
        }
        java.lang.String inputPortName = portName.getLocalPart();
        if ("CaseCountWebServiceSoap".equals(inputPortName)) {
            return getCaseCountWebServiceSoap();
        }
        else if ("CaseCountWebServiceSoap12".equals(inputPortName)) {
            return getCaseCountWebServiceSoap12();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://ciber.com/", "CaseCountWebService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://ciber.com/", "CaseCountWebServiceSoap"));
            ports.add(new javax.xml.namespace.QName("http://ciber.com/", "CaseCountWebServiceSoap12"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("CaseCountWebServiceSoap".equals(portName)) {
            setCaseCountWebServiceSoapEndpointAddress(address);
        }
        if ("CaseCountWebServiceSoap12".equals(portName)) {
            setCaseCountWebServiceSoap12EndpointAddress(address);
        }
        else { // Unknown Port Name
            throw new javax.xml.rpc.ServiceException(" Cannot set Endpoint Address for Unknown Port" + portName);
        }
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(javax.xml.namespace.QName portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        setEndpointAddress(portName.getLocalPart(), address);
    }

}
