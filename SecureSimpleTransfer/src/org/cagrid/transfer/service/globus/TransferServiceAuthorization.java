package org.cagrid.transfer.service.globus;


import java.rmi.RemoteException;
import javax.security.auth.Subject;
import javax.xml.namespace.QName;
import javax.xml.rpc.handler.MessageContext;

import org.globus.gsi.GlobusCredential;
import org.globus.gsi.gssapi.GlobusGSSCredentialImpl;
import org.globus.gsi.jaas.JaasGssUtil;
import org.globus.wsrf.impl.security.authentication.Constants;
import org.globus.wsrf.impl.security.authorization.exceptions.AuthorizationException;
import org.globus.wsrf.impl.security.authorization.exceptions.CloseException;
import org.globus.wsrf.impl.security.authorization.exceptions.InitializeException;
import org.globus.wsrf.impl.security.authorization.exceptions.InvalidPolicyException;
import org.globus.wsrf.security.authorization.PDP;
import org.globus.wsrf.security.authorization.PDPConfig;
import org.w3c.dom.Node;

/** 
 * DO NOT EDIT:  This class is autogenerated!
 *
 * This is a PDP for use with the globus authorization callout.
 * This class will have a authorize<methodName> method for each method on this grid service.
 * The method is responsibe for making any authorization callouts required to satisfy the 
 * authorization requirements placed on each method call.  Each method will either return
 * apon a successful authorization or will throw an exception apon a failed authorization.
 * 
 * @created by Introduce Toolkit version 1.2
 * 
 */
public class TransferServiceAuthorization implements PDP {

	public static final String SERVICE_NAMESPACE = "http://transfer.cagrid.org/TransferService";
	
	
	public TransferServiceAuthorization() {
	}
	
	protected String getServiceNamespace(){
		return SERVICE_NAMESPACE;
	}
	
	public static String getCallerIdentity() {
		String caller = org.globus.wsrf.security.SecurityManager.getManager().getCaller();
		if ((caller == null) || (caller.equals("<anonymous>"))) {
			return null;
		} else {
			return caller;
		}
	}
	
	public static GlobusCredential getInvocationCredential() {
        org.apache.axis.MessageContext ctx = org.apache.axis.MessageContext.getCurrentContext();
        Subject subject = (Subject) ctx.getProperty(Constants.INVOCATION_SUBJECT);
        GlobusGSSCredentialImpl credential = (GlobusGSSCredentialImpl) JaasGssUtil.getCredential(subject);
        return credential.getGlobusCredential();
    }
					
	public static void authorizeGetServiceSecurityMetadata() throws RemoteException {
		
		
	}
					
	public static void authorizeStoreFile() throws RemoteException {
		
		
	}
					
	public static void authorizeRetrieveFile() throws RemoteException {
		
		
	}
					
	public static void authorizeStoreData() throws RemoteException {
		
		
	}
					
	public static void authorizeRetrieveData() throws RemoteException {
		
		
	}
					
	public static void authorizeViewDirectory() throws RemoteException {
		
		
	}
					
	public static void authorizeMakeDirectory() throws RemoteException {
		
		
	}
					
	public static void authorizeDeleteDirectory() throws RemoteException {
		
		
	}
					
	public static void authorizeDeleteFile() throws RemoteException {
		
		
	}
	
	
	public boolean isPermitted(Subject peerSubject, MessageContext context, QName operation)
		throws AuthorizationException {
		
		if(!operation.getNamespaceURI().equals(getServiceNamespace())){
		  return false;
		}
		if(operation.getLocalPart().equals("getServiceSecurityMetadata")){
			try{
				authorizeGetServiceSecurityMetadata();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("storeFile")){
			try{
				authorizeStoreFile();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("retrieveFile")){
			try{
				authorizeRetrieveFile();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("storeData")){
			try{
				authorizeStoreData();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("retrieveData")){
			try{
				authorizeRetrieveData();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("viewDirectory")){
			try{
				authorizeViewDirectory();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("makeDirectory")){
			try{
				authorizeMakeDirectory();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("deleteDirectory")){
			try{
				authorizeDeleteDirectory();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} else if(operation.getLocalPart().equals("deleteFile")){
			try{
				authorizeDeleteFile();
				return true;
			} catch (Exception e){
				e.printStackTrace();
				return false;
			}
		} 		
		return false;
	}
	

	public Node getPolicy(Node query) throws InvalidPolicyException {
		return null;
	}


	public String[] getPolicyNames() {
		return null;
	}


	public Node setPolicy(Node policy) throws InvalidPolicyException {
		return null;
	}


	public void close() throws CloseException {


	}


	public void initialize(PDPConfig config, String name, String id) throws InitializeException {

	}
	
	
}
