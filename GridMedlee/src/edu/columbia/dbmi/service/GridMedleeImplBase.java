package edu.columbia.dbmi.service;

import edu.columbia.dbmi.service.globus.resource.GridMedleeResource;
import  edu.columbia.dbmi.service.GridMedleeConfiguration;

import java.rmi.RemoteException;

import javax.naming.InitialContext;
import javax.xml.namespace.QName;

import org.apache.axis.MessageContext;
import org.globus.wsrf.Constants;
import org.globus.wsrf.ResourceContext;
import org.globus.wsrf.ResourceContextException;
import org.globus.wsrf.ResourceException;
import org.globus.wsrf.ResourceHome;
import org.globus.wsrf.ResourceProperty;
import org.globus.wsrf.ResourcePropertySet;


/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * Provides some simple accessors for the Impl.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public abstract class GridMedleeImplBase {
	
	public GridMedleeImplBase() throws RemoteException {
	
	}
	
	public GridMedleeConfiguration getConfiguration() throws Exception {
		return GridMedleeConfiguration.getConfiguration();
	}
	
	
	public edu.columbia.dbmi.service.globus.resource.GridMedleeResourceHome getResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("home");
		return (edu.columbia.dbmi.service.globus.resource.GridMedleeResourceHome)resource;
	}

	
	
	
	public edu.columbia.dbmi.context.service.globus.resource.GridMedleeContextResourceHome getGridMedleeContextResourceHome() throws Exception {
		ResourceHome resource = getResourceHome("gridMedleeContextHome");
		return (edu.columbia.dbmi.context.service.globus.resource.GridMedleeContextResourceHome)resource;
	}
	
	
	protected ResourceHome getResourceHome(String resourceKey) throws Exception {
		MessageContext ctx = MessageContext.getCurrentContext();

		ResourceHome resourceHome = null;
		
		String servicePath = ctx.getTargetService();

		String jndiName = Constants.JNDI_SERVICES_BASE_NAME + servicePath + "/" + resourceKey;
		try {
			javax.naming.Context initialContext = new InitialContext();
			resourceHome = (ResourceHome) initialContext.lookup(jndiName);
		} catch (Exception e) {
			throw new Exception("Unable to instantiate resource home. : " + resourceKey, e);
		}

		return resourceHome;
	}


}

