/**
 * CaseCountWebService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.ciber;

public interface CaseCountWebService extends javax.xml.rpc.Service {

/**
 * CaseCount per day based upon CallType and Output Type
 */
    public java.lang.String getCaseCountWebServiceSoap12Address();

    public com.ciber.CaseCountWebServiceSoap getCaseCountWebServiceSoap12() throws javax.xml.rpc.ServiceException;

    public com.ciber.CaseCountWebServiceSoap getCaseCountWebServiceSoap12(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
    public java.lang.String getCaseCountWebServiceSoapAddress();

    public com.ciber.CaseCountWebServiceSoap getCaseCountWebServiceSoap() throws javax.xml.rpc.ServiceException;

    public com.ciber.CaseCountWebServiceSoap getCaseCountWebServiceSoap(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
