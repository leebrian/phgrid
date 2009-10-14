package edu.columbia.dbmi.context.client;

import java.io.InputStream;
import java.rmi.RemoteException;

import javax.xml.namespace.QName;

import org.apache.axis.EngineConfiguration;
import org.apache.axis.client.AxisClient;
import org.apache.axis.client.Stub;
import org.apache.axis.configuration.FileProvider;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;

import org.oasis.wsrf.properties.GetResourcePropertyResponse;

import org.globus.gsi.GlobusCredential;

import edu.columbia.dbmi.context.stubs.GridMedleeContextPortType;
import edu.columbia.dbmi.context.stubs.service.GridMedleeContextServiceAddressingLocator;
import edu.columbia.dbmi.context.common.GridMedleeContextI;
import gov.nih.nci.cagrid.introduce.security.client.ServiceSecurityClient;

/**
 * This class is autogenerated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.2
 */
public class GridMedleeContextClient extends GridMedleeContextClientBase implements GridMedleeContextI {	

	public GridMedleeContextClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	public GridMedleeContextClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	public GridMedleeContextClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	public GridMedleeContextClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	public static void usage(){
		System.out.println(GridMedleeContextClient.class.getName() + " -url <service url>");
	}
	
	public static void main(String [] args){
	    System.out.println("Running the Grid Service Client");
		try{
		if(!(args.length < 2)){
			if(args[0].equals("-url")){
			  GridMedleeContextClient client = new GridMedleeContextClient(args[1]);
			  // place client calls here if you want to use this main as a
			  // test....
			} else {
				usage();
				System.exit(1);
			}
		} else {
			usage();
			System.exit(1);
		}
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}

  public org.oasis.wsrf.lifetime.DestroyResponse destroy(org.oasis.wsrf.lifetime.Destroy params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"destroy");
    return portType.destroy(params);
    }
  }

  public org.oasis.wsrf.lifetime.SetTerminationTimeResponse setTerminationTime(org.oasis.wsrf.lifetime.SetTerminationTime params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"setTerminationTime");
    return portType.setTerminationTime(params);
    }
  }

  public org.oasis.wsn.SubscribeResponse subscribe(org.oasis.wsn.Subscribe params) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"subscribe");
    return portType.subscribe(params);
    }
  }

  public edu.columbia.dbmi.medlee.StatusOptions getStatus() throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getStatus");
    edu.columbia.dbmi.context.stubs.GetStatusRequest params = new edu.columbia.dbmi.context.stubs.GetStatusRequest();
    edu.columbia.dbmi.context.stubs.GetStatusResponse boxedResult = portType.getStatus(params);
    return boxedResult.getStatus();
    }
  }

  public edu.columbia.dbmi.medlee.MedleeOutputDocuments getOutputDocuments() throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"getOutputDocuments");
    edu.columbia.dbmi.context.stubs.GetOutputDocumentsRequest params = new edu.columbia.dbmi.context.stubs.GetOutputDocumentsRequest();
    edu.columbia.dbmi.context.stubs.GetOutputDocumentsResponse boxedResult = portType.getOutputDocuments(params);
    return boxedResult.getMedleeOutputDocuments();
    }
  }

}
