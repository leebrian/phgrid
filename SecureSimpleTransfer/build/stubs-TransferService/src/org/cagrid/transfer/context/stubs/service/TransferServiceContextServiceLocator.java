/**
 * TransferServiceContextServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.cagrid.transfer.context.stubs.service;

public class TransferServiceContextServiceLocator extends org.apache.axis.client.Service implements org.cagrid.transfer.context.stubs.service.TransferServiceContextService {

    public TransferServiceContextServiceLocator() {
    }


    public TransferServiceContextServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TransferServiceContextServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TransferServiceContextPortTypePort
    private java.lang.String TransferServiceContextPortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getTransferServiceContextPortTypePortAddress() {
        return TransferServiceContextPortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TransferServiceContextPortTypePortWSDDServiceName = "TransferServiceContextPortTypePort";

    public java.lang.String getTransferServiceContextPortTypePortWSDDServiceName() {
        return TransferServiceContextPortTypePortWSDDServiceName;
    }

    public void setTransferServiceContextPortTypePortWSDDServiceName(java.lang.String name) {
        TransferServiceContextPortTypePortWSDDServiceName = name;
    }

    public org.cagrid.transfer.context.stubs.TransferServiceContextPortType getTransferServiceContextPortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TransferServiceContextPortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTransferServiceContextPortTypePort(endpoint);
    }

    public org.cagrid.transfer.context.stubs.TransferServiceContextPortType getTransferServiceContextPortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.cagrid.transfer.context.stubs.bindings.TransferServiceContextPortTypeSOAPBindingStub _stub = new org.cagrid.transfer.context.stubs.bindings.TransferServiceContextPortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getTransferServiceContextPortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTransferServiceContextPortTypePortEndpointAddress(java.lang.String address) {
        TransferServiceContextPortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.cagrid.transfer.context.stubs.TransferServiceContextPortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.cagrid.transfer.context.stubs.bindings.TransferServiceContextPortTypeSOAPBindingStub _stub = new org.cagrid.transfer.context.stubs.bindings.TransferServiceContextPortTypeSOAPBindingStub(new java.net.URL(TransferServiceContextPortTypePort_address), this);
                _stub.setPortName(getTransferServiceContextPortTypePortWSDDServiceName());
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
        if ("TransferServiceContextPortTypePort".equals(inputPortName)) {
            return getTransferServiceContextPortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://transfer.cagrid.org/TransferService/Context/service", "TransferServiceContextService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://transfer.cagrid.org/TransferService/Context/service", "TransferServiceContextPortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("TransferServiceContextPortTypePort".equals(portName)) {
            setTransferServiceContextPortTypePortEndpointAddress(address);
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
