package edu.columbia.dbmi.service;

import java.rmi.RemoteException;

/** 
 * TODO:I am the service side implementation class.  IMPLEMENT AND DOCUMENT ME
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class GridMedleeImpl extends GridMedleeImplBase {

	
	public GridMedleeImpl() throws RemoteException {
		super();
	}
	
  public edu.columbia.dbmi.context.stubs.types.GridMedleeContextReference invokeGridMedlee(edu.columbia.dbmi.medlee.MedleeInvocationConfiguration medleeInvocationConfiguration,edu.columbia.dbmi.medlee.MedleeInputDocuments medleeInputDocuments) throws RemoteException {
		org.apache.axis.message.addressing.EndpointReferenceType epr = new org.apache.axis.message.addressing.EndpointReferenceType();
		edu.columbia.dbmi.context.service.globus.resource.GridMedleeContextResourceHome home = null;
		org.globus.wsrf.ResourceKey resourceKey = null;
		org.apache.axis.MessageContext ctx = org.apache.axis.MessageContext.getCurrentContext();
		String servicePath = ctx.getTargetService();
		String homeName = org.globus.wsrf.Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/" + "gridMedleeContextHome";

		try {
			javax.naming.Context initialContext = new javax.naming.InitialContext();
			home = (edu.columbia.dbmi.context.service.globus.resource.GridMedleeContextResourceHome) initialContext.lookup(homeName);
			resourceKey = home.createResource();
			
			//  Grab the newly created resource
			edu.columbia.dbmi.context.service.globus.resource.GridMedleeContextResource thisResource = (edu.columbia.dbmi.context.service.globus.resource.GridMedleeContextResource)home.find(resourceKey);
			//  This is where the creator of this resource type can set whatever needs
			//  to be set on the resource so that it can function appropriatly  for instance
			//  if you want the resouce to only have the query string then there is where you would
			//  give it the query string.
			
			
			// sample of setting creator only security.  This will only allow the caller that created
			// this resource to be able to use it.
			//thisResource.setSecurityDescriptor(gov.nih.nci.cagrid.introduce.servicetools.security.SecurityUtils.createCreatorOnlyResourceSecurityDescriptor());
			
			thisResource.setInputDocuments(medleeInputDocuments);
            thisResource.setMedleeConf(medleeInvocationConfiguration);
            thisResource.invoke();

			String transportURL = (String) ctx.getProperty(org.apache.axis.MessageContext.TRANS_URL);
			transportURL = transportURL.substring(0,transportURL.lastIndexOf('/') +1 );
			transportURL += "GridMedleeContext";
			epr = org.globus.wsrf.utils.AddressingUtils.createEndpointReference(transportURL,resourceKey);
		} catch (Exception e) {
			throw new RemoteException("Error looking up GridMedleeContext home:" + e.getMessage(), e);
		}

		//return the typed EPR
		edu.columbia.dbmi.context.stubs.types.GridMedleeContextReference ref = new edu.columbia.dbmi.context.stubs.types.GridMedleeContextReference();
		ref.setEndpointReference(epr);

		return ref;
  }

}

