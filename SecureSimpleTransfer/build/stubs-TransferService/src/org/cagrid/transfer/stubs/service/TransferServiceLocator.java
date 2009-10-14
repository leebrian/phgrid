/**
 * TransferServiceLocator.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.cagrid.transfer.stubs.service;

public class TransferServiceLocator extends org.apache.axis.client.Service implements org.cagrid.transfer.stubs.service.TransferService {

    public TransferServiceLocator() {
    }


    public TransferServiceLocator(org.apache.axis.EngineConfiguration config) {
        super(config);
    }

    public TransferServiceLocator(java.lang.String wsdlLoc, javax.xml.namespace.QName sName) throws javax.xml.rpc.ServiceException {
        super(wsdlLoc, sName);
    }

    // Use to get a proxy class for TransferServicePortTypePort
    private java.lang.String TransferServicePortTypePort_address = "http://localhost:8080/wsrf/services/";

    public java.lang.String getTransferServicePortTypePortAddress() {
        return TransferServicePortTypePort_address;
    }

    // The WSDD service name defaults to the port name.
    private java.lang.String TransferServicePortTypePortWSDDServiceName = "TransferServicePortTypePort";

    public java.lang.String getTransferServicePortTypePortWSDDServiceName() {
        return TransferServicePortTypePortWSDDServiceName;
    }

    public void setTransferServicePortTypePortWSDDServiceName(java.lang.String name) {
        TransferServicePortTypePortWSDDServiceName = name;
    }

    public org.cagrid.transfer.stubs.TransferServicePortType getTransferServicePortTypePort() throws javax.xml.rpc.ServiceException {
       java.net.URL endpoint;
        try {
            endpoint = new java.net.URL(TransferServicePortTypePort_address);
        }
        catch (java.net.MalformedURLException e) {
            throw new javax.xml.rpc.ServiceException(e);
        }
        return getTransferServicePortTypePort(endpoint);
    }

    public org.cagrid.transfer.stubs.TransferServicePortType getTransferServicePortTypePort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException {
        try {
            org.cagrid.transfer.stubs.bindings.TransferServicePortTypeSOAPBindingStub _stub = new org.cagrid.transfer.stubs.bindings.TransferServicePortTypeSOAPBindingStub(portAddress, this);
            _stub.setPortName(getTransferServicePortTypePortWSDDServiceName());
            return _stub;
        }
        catch (org.apache.axis.AxisFault e) {
            return null;
        }
    }

    public void setTransferServicePortTypePortEndpointAddress(java.lang.String address) {
        TransferServicePortTypePort_address = address;
    }

    /**
     * For the given interface, get the stub implementation.
     * If this service has no port for the given interface,
     * then ServiceException is thrown.
     */
    public java.rmi.Remote getPort(Class serviceEndpointInterface) throws javax.xml.rpc.ServiceException {
        try {
            if (org.cagrid.transfer.stubs.TransferServicePortType.class.isAssignableFrom(serviceEndpointInterface)) {
                org.cagrid.transfer.stubs.bindings.TransferServicePortTypeSOAPBindingStub _stub = new org.cagrid.transfer.stubs.bindings.TransferServicePortTypeSOAPBindingStub(new java.net.URL(TransferServicePortTypePort_address), this);
                _stub.setPortName(getTransferServicePortTypePortWSDDServiceName());
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
        if ("TransferServicePortTypePort".equals(inputPortName)) {
            return getTransferServicePortTypePort();
        }
        else  {
            java.rmi.Remote _stub = getPort(serviceEndpointInterface);
            ((org.apache.axis.client.Stub) _stub).setPortName(portName);
            return _stub;
        }
    }

    public javax.xml.namespace.QName getServiceName() {
        return new javax.xml.namespace.QName("http://transfer.cagrid.org/TransferService/service", "TransferService");
    }

    private java.util.HashSet ports = null;

    public java.util.Iterator getPorts() {
        if (ports == null) {
            ports = new java.util.HashSet();
            ports.add(new javax.xml.namespace.QName("http://transfer.cagrid.org/TransferService/service", "TransferServicePortTypePort"));
        }
        return ports.iterator();
    }

    /**
    * Set the endpoint address for the specified port name.
    */
    public void setEndpointAddress(java.lang.String portName, java.lang.String address) throws javax.xml.rpc.ServiceException {
        if ("TransferServicePortTypePort".equals(portName)) {
            setTransferServicePortTypePortEndpointAddress(address);
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
