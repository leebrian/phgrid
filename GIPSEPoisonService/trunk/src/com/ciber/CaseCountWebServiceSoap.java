/**
 * CaseCountWebServiceSoap.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package com.ciber;

public interface CaseCountWebServiceSoap extends java.rmi.Remote {
    public com.ciber.PerDayCaseCountResponsePerDayCaseCountResult perDayCaseCount(java.lang.String systemUserName, java.lang.String systemPWD, java.lang.String userName, java.lang.String orgList, java.lang.String stateList, java.lang.String zipList, java.util.Calendar startDate, java.util.Calendar endDate, java.lang.String startHour, java.lang.String endHour, java.lang.String clinicalEffect, java.lang.String callVolumeType, java.lang.String booleanOperator, java.lang.String outputType) throws java.rmi.RemoteException;
}
