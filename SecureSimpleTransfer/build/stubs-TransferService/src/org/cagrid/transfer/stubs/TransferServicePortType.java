/**
 * TransferServicePortType.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.2RC2 Nov 14, 2006 (10:23:53 EST) WSDL2Java emitter.
 */

package org.cagrid.transfer.stubs;

public interface TransferServicePortType extends java.rmi.Remote {
    public org.cagrid.transfer.stubs.StoreFileResponse storeFile(org.cagrid.transfer.stubs.StoreFileRequest parameters) throws java.rmi.RemoteException;
    public org.cagrid.transfer.stubs.RetrieveFileResponse retrieveFile(org.cagrid.transfer.stubs.RetrieveFileRequest parameters) throws java.rmi.RemoteException;
    public org.cagrid.transfer.stubs.StoreDataResponse storeData(org.cagrid.transfer.stubs.StoreDataRequest parameters) throws java.rmi.RemoteException;
    public org.cagrid.transfer.stubs.RetrieveDataResponse retrieveData(org.cagrid.transfer.stubs.RetrieveDataRequest parameters) throws java.rmi.RemoteException;
    public org.cagrid.transfer.stubs.ViewDirectoryResponse viewDirectory(org.cagrid.transfer.stubs.ViewDirectoryRequest parameters) throws java.rmi.RemoteException;
    public org.cagrid.transfer.stubs.CreateDirectoryResponse createDirectory(org.cagrid.transfer.stubs.CreateDirectoryRequest parameters) throws java.rmi.RemoteException;
    public org.cagrid.transfer.stubs.RemovedirectoryResponse removedirectory(org.cagrid.transfer.stubs.RemovedirectoryRequest parameters) throws java.rmi.RemoteException;
    public org.cagrid.transfer.stubs.RemoveFileResponse removeFile(org.cagrid.transfer.stubs.RemoveFileRequest parameters) throws java.rmi.RemoteException;
    public gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataResponse getServiceSecurityMetadata(gov.nih.nci.cagrid.introduce.security.stubs.GetServiceSecurityMetadataRequest parameters) throws java.rmi.RemoteException;
}
