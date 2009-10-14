/**
 * GridMedleeContextServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package edu.columbia.dbmi.context.stubs.service;

public class GridMedleeContextServiceLocator extends org.apache.axis.client.Service implements edu.columbia.dbmi.context.stubs.service.GridMedleeContextService {

    public GridMedleeContextServiceLocator() {
    }


    public GridMedleeContextServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public GridMedleeContextServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for GridMedleeContextPortTypePort
    private java.lang.String GridMedleeContextPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getGridMedleeContextPortTypePortAddress() {
        return GridMedleeContextPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String GridMedleeContextPortTypePortWSDDServiceName = "GridMedleeContextPortTypePort";

    public java.lang.String getGridMedleeContextPortTypePortWSDDServiceName() {
        return GridMedleeContextPortTypePortWSDDServiceName;
    }

    public void setGridMedleeContextPortTypePortWSDDServiceName(java.lang.String name) {
        GridMedleeContextPortTypePortWSDDServiceName = name;
    }

    public edu.columbia.dbmi.context.stubs.GridMedleeContextPortType getGridMedleeContextPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(GridMedleeContextPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getGridMedleeContextPortTypePort(endpoint);
    }

    public edu.columbia.dbmi.context.stubs.GridMedleeContextPortType getGridMedleeContextPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            edu.columbia.dbmi.context.stubs.bindings.GridMedleeContextPortTypeSOAPBindingStub _stub = new edu.columbia.dbmi.context.stubs.bindings.GridMedleeContextPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getGridMedleeContextPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setGridMedleeContextPortTypePortEndpointAddress(java.lang.String address) {
        GridMedleeContextPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (edu.columbia.dbmi.context.stubs.GridMedleeContextPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                edu.columbia.dbmi.context.stubs.bindings.GridMedleeContextPortTypeSOAPBindingStub _stub = new edu.columbia.dbmi.context.stubs.bindings.GridMedleeContextPortTypeSOAPBindingStub(new java.net.URL(GridMedleeContextPortTypePort_address), this);
                _stub.setPortName(getGridMedleeContextPortTypePortWSDDServiceName());
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
        if ("GridMedleeContextPortTypePort".equals(inputPortName)) {
            return getGridMedleeContextPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/Context/service", "GridMedleeContextService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://dbmi.columbia.edu/GridMedlee/Context/service", "GridMedleeContextPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("GridMedleeContextPortTypePort".equals(portName)) {
            setGridMedleeContextPortTypePortEndpointAddress(address);
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
