/**
 * TransferServiceContextPortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.cagrid.transfer.context.stubs;

public interface TransferServiceContextPortType extends java.rmi.Remote {
    public org.cagrid.transfer.context.stubs.GetDataTransferDescriptorResponse getDataTransferDescriptor(org.cagrid.transfer.context.stubs.GetDataTransferDescriptorRequest parameters) throws java.rmi.RemoteException;
    public org.cagrid.transfer.context.stubs.GetStatusResponse getStatus(org.cagrid.transfer.context.stubs.GetStatusRequest parameters) throws java.rmi.RemoteException;
    public org.cagrid.transfer.context.stubs.SetStatusResponse setStatus(org.cagrid.transfer.context.stubs.SetStatusRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse getServiceSecurityMetadata(gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest parameters) throws java.rmi.RemoteException;
    public org.oasis.wsrf.lifetime.DestroyResponse destroy(org.oasis.wsrf.lifetime.Destroy destroyRequest) throws java.rmi.RemoteException, org.oasis.wsrf.lifetime.ResourceNotDestroyedFaultType, org.oasis.wsrf.lifetime.ResourceUnknownFaultType;
    public org.oasis.wsrf.lifetime.SetTerminationTimeResponse setTerminationTime(org.oasis.wsrf.lifetime.SetTerminationTime setTerminationTimeRequest) throws java.rmi.RemoteException, org.oasis.wsrf.lifetime.UnableToSetTerminationTimeFaultType, org.oasis.wsrf.lifetime.ResourceUnknownFaultType, org.oasis.wsrf.lifetime.TerminationTimeChangeRejectedFaultType;
    public org.oasis.wsn.SubscribeResponse subscribe(org.oasis.wsn.Subscribe subscribeRequest) throws java.rmi.RemoteException, org.oasis.wsn.TopicNotSupportedFaultType, org.oasis.wsn.InvalidTopicExpressionFaultType, org.oasis.wsn.SubscribeCreationFailedFaultType, org.oasis.wsn.ResourceUnknownFaultType, org.oasis.wsn.TopicPathDialectUnknownFaultType;
}
