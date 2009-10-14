package edu.columbia.dbmi.context.service;

import java.rmi.RemoteException;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class GridMedleeContextImpl extends GridMedleeContextImplBase {

	
	public GridMedleeContextImpl() throws RemoteException {
		super();
	}
	
  public edu.columbia.dbmi.medlee.StatusOptions getStatus() throws RemoteException {
      try {
          return getResourceHome().getAddressedResource().getStatus();
      } catch (Exception e) {
          e.printStackTrace();
          throw new RemoteException("Error retrieving medlee process status");
      }
  }

  public edu.columbia.dbmi.medlee.MedleeOutputDocuments getOutputDocuments() throws RemoteException {
    try {
        return getResourceHome().getAddressedResource().getOutputDocuments();
    } catch (Exception e) {
        e.printStackTrace();
        throw new RemoteException("Error retrieving medlee output documents");
    }
  }

}

