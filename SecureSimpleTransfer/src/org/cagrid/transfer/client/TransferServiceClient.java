package org.cagrid.transfer.client;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.util.Properties;

import org.apache.axis.client.Stub;
import org.apache.axis.message.addressing.EndpointReferenceType;
import org.apache.axis.types.URI.MalformedURIException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.cagrid.transfer.common.TransferServiceI;
import org.cagrid.transfer.context.client.TransferServiceContextClient;
import org.cagrid.transfer.context.client.helper.TransferClientHelper;
import org.cagrid.transfer.context.stubs.types.TransferServiceContextReference;
import org.cagrid.transfer.descriptor.DataDescriptor;
import org.cagrid.transfer.descriptor.Status;
import org.globus.gsi.GlobusCredential;
import org.globus.gsi.GlobusCredentialException;

/**
 * This class is auto-generated, DO NOT EDIT GENERATED GRID SERVICE ACCESS METHODS.
 *
 * This client is generated automatically by Introduce to provide a clean unwrapped API to the
 * service.
 *
 * On construction the class instance will contact the remote service and retrieve it's security
 * metadata description which it will use to configure the Stub specifically for each method call.
 * 
 * @created by Introduce Toolkit version 1.2
 */

/***
 * 
 * This caGrid Transfer client class acts as a wrapper around the lower level caGrid Transfer 
 * implementation. 
 * 
 * 
 * @author Felicia Rosemond (frosemond@gmail.com)
 *
 */
public class TransferServiceClient extends TransferServiceClientBase implements TransferServiceI {	
	static final Log logger = LogFactory.getLog(TransferServiceClient.class);
	
	private static String UPLOAD_DIRECTORY_TAG = "uploadDirectory";
	private static String DOWNLOAD_DIRECTORY_TAG = "downloadDirectory";
	private static String URL_TAG = "url";
	
	private static String LOG4J_PROPERTIES_FILE = "log4j.properties";
		
	private static GlobusCredential creds = null;
	private static TransferServiceClient client = null;
	private static String uploadDirectory= null;
	private static String downloadDirectory = null;
	private static String url = null;
	
	
	public enum Commands {upload, download, list, create, delete, remove};
	
	/***
	 * This is an Introduce Toolkit method that creates a new TransferServiceClient 
	 * with just a url.
	 * 
	 * @param url
	 * @throws MalformedURIException
	 * @throws RemoteException
	 */
	public TransferServiceClient(String url) throws MalformedURIException, RemoteException {
		this(url,null);	
	}

	
	/***
	 * This is an Introduce Toolkit method that creates a new TransferServiceClient 
	 * with a url and globus credentials.
	 * 
	 * 
	 * @param url
	 * @param proxy
	 * @throws MalformedURIException
	 * @throws RemoteException
	 */
	public TransferServiceClient(String url, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(url,proxy);
	}
	
	
	/***
	 * This is an Introduce Toolkit method that creates a new TransferServiceClient with a endpoint reference
	 * 
	 * @param epr
	 * @throws MalformedURIException
	 * @throws RemoteException
	 */
	public TransferServiceClient(EndpointReferenceType epr) throws MalformedURIException, RemoteException {
	   	this(epr,null);
	}
	
	
	/***
	 * This is an Introduce Toolkit method that creates a new TransferServiceClient with a endpoint reference 
	 * and globus credentials
	 * 
	 * @param epr
	 * @param proxy
	 * @throws MalformedURIException
	 * @throws RemoteException
	 */
	public TransferServiceClient(EndpointReferenceType epr, GlobusCredential proxy) throws MalformedURIException, RemoteException {
	   	super(epr,proxy);
	}

	

	
	public static String getUploadDirectory() {
		return uploadDirectory;
	}

	
	public static void setUploadDirectory(String uploadDirectory) {
		TransferServiceClient.uploadDirectory = uploadDirectory;
		logger.info("Upload Directory = "+ uploadDirectory);
	}

	
	public static String getDownloadDirectory() {
		return downloadDirectory;
	}

	
	public static void setDownloadDirectory(String downloadDirectory) {
		TransferServiceClient.downloadDirectory = downloadDirectory;
		logger.info("Download Directory = "+ downloadDirectory);
	}

	
	public static String getUrl() {
		return url;
	}

	
	public static void setUrl(String url) {
		TransferServiceClient.url = url;
		logger.info("url = "+ url);

	}

	
	public static TransferServiceClient getClient() {
		return client;
	}

	public static void setClient(String url) {
		try {
			client = new TransferServiceClient(url, creds);
		} catch (MalformedURIException e) {
			logger.error("setClient: An exception has occured, the URL is not correct. Please check and try again.");
		} catch (RemoteException e) {
			logger.error("setClient: An exception has occured, there was a problem connecting to "+ url);
		}
	}		

	
	public static GlobusCredential getCreds() {
		return creds;
	}

	
	public static void setCreds() throws GlobusCredentialException {
		creds = GlobusCredential.getDefaultCredential();
	}

	/***
	 * This method calls the service's viewDirectory method and outputs the listing
	 * in a  human-readable form
	 * 
	 * 
	 * @param directory
	 * @throws Exception
	 */
	public static void listFiles(String directory) throws Exception
	{
		logger.info("enter listFiles");
		String[] fileList = client.viewDirectory(directory);
		
		
		if (!directory.equals("."))
			System.out.println("file listing for "+ directory + "/");
		
		for (int i=0; i < fileList.length; i++) {
			System.out.println(fileList[i]);
		}
		
		logger.info("exit listFiles");
	}
	
	
	
	public static boolean createDirectory (String directory) throws Exception
	{
		logger.info("enter createDirectory");		
		boolean rt = client.makeDirectory(directory);
		logger.info("exit createDirectory");
		return rt;	
	}

	
	public static boolean removeDirectory (String directory) throws Exception
	{
		logger.info("enter removeDirectory");
		boolean rt = client.deleteDirectory(directory);
		logger.info("exit removeDirectory");
		return rt;
	}
	
	
	public static boolean removeFile (String filename, String directory) throws Exception
	{
		logger.info("enter removeFile");
		boolean rt = client.deleteFile(filename, directory);
		logger.info("exit removeFile");
		return rt;
	}
	
	/***
	 * This method uploads a named file from the file system 
	 * 
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public static void uploadFile(String filename, String directory) throws Exception
	{
		logger.info("enter uploadFile");
		TransferServiceContextReference ref12 = TransferServiceClient.getClient().storeFile(filename, directory);
		
		// use the EPR from the reference to create a client to talk to my resource
		logger.info("creating transfer context client");
		TransferServiceContextClient tclient12 = new TransferServiceContextClient(ref12.getEndpointReference(), creds);
		
		// use the helper to get the output stream that I can upload my data with
		logger.info("creating DataDescriptor");
		DataDescriptor namedfileDD = new DataDescriptor(null, filename);
		
		//add all of the ways to send a message - this works in a block together
		File namedfileDataToSend = new File(getUploadDirectory() + "/"+ filename);
		logger.info("File length = "+ namedfileDataToSend.length());
		InputStream namedfileIS = new FileInputStream(namedfileDataToSend);
		
		logger.info("uploadFile: putting data");
		TransferClientHelper.putData(namedfileIS, namedfileDataToSend.length(), tclient12.getDataTransferDescriptor(), creds);
	
		tclient12.setStatus(Status.Staged);			
		
		logger.info("exit uploadFile");
	}
	

	/***
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public static void downloadFile(String filename, String directory) throws Exception
	{
		logger.info("enter downloadFile");
		TransferServiceContextReference ref3 = client.retrieveFile(filename, directory);
		
		// use the EPR from the reference to create a client to talk to my resource
		logger.info("creating context client");
		TransferServiceContextClient tclient3 = new TransferServiceContextClient(ref3.getEndpointReference(), creds);
		
		//use the TransferClientHelper to get an InputStream to the data
		logger.info("calling getData");
		InputStream stream3 = TransferClientHelper.getData(tclient3.getDataTransferDescriptor(), creds);	
        
        //read input stream and write data out to a actual file
        logger.info("creating file "+ getDownloadDirectory() + "/"+ filename);                
        FileOutputStream data3 = new FileOutputStream(new File(getDownloadDirectory()+ "/" + filename));
        
        logger.info("writing file: "+  getDownloadDirectory() + "/"+ filename);
        ccl.util.FileUtil.copy(stream3, data3);	
        
        logger.info("exit downloadFile");
	}
	

	/***
	 * 
	 * @param text
	 * @throws Exception
	 */
	public static void uploadText(String text, String directory) throws Exception
	{
		logger.info("enter uploadText");
		
		
		logger.info("incoming text = "+ text);
		
		TransferServiceContextReference ref1 = client.storeData(directory);
		
		// use the EPR from the reference to create a client to talk to my resource
		TransferServiceContextClient tclient1 = new TransferServiceContextClient(ref1.getEndpointReference(), creds);
		
		// use the helper to get the output stream that I can upload my data with
		DataDescriptor dd = new DataDescriptor(null, "Byte Data");
		
		//add all of the ways to send a message - this works in a block together
		String myDataToSend = new String("this is a of my secure simple transfer service");
		InputStream is = new ByteArrayInputStream(myDataToSend.getBytes());
		TransferClientHelper.putData(is, myDataToSend.length(), tclient1.getDataTransferDescriptor(), creds);
		tclient1.setStatus(Status.Staged);	
		
		logger.info("exit uploadText");
	}
	
	/***
	 * 
	 * @param filename
	 * @throws Exception
	 */
	public static void downloadTextToFile(String filename) throws Exception
	{
		logger.info("exit downloadTextToFile");
		
		logger.info("creating context reference");
		TransferServiceContextReference ref2 = client.retrieveData();
		
		// use the EPR from the reference to create a client to talk to my resource
		logger.info("creating client2");
		TransferServiceContextClient tclient2 = new TransferServiceContextClient(ref2.getEndpointReference(), creds);
		
		//use the TransferClientHelper to get an InputStream to the data
		logger.info("creating stream2");
        InputStream stream2 = TransferClientHelper.getData(tclient2.getDataTransferDescriptor(), creds);
        
        //read input stream and write data out to a actual file               
        logger.info("calling writeToFile");
        ccl.util.FileUtil.writeFile(getDownloadDirectory()+"/"+filename , stream2.toString());
        
        logger.info("exit downloadTextToFile");
	}

	/***
	 * 
	 * @return
	 * @throws Exception
	 */
	public static String downloadText() throws Exception
	{
		logger.info("enter downloadText");
		
		logger.info("creating context reference");
		TransferServiceContextReference ref2 = client.retrieveData();
		
		// use the EPR from the reference to create a client to talk to my resource
		logger.info("creating client2");
		TransferServiceContextClient tclient2 = new TransferServiceContextClient(ref2.getEndpointReference(), creds);
		
		//use the TransferClientHelper to get an InputStream to the data
		logger.info("creating stream2");
        InputStream stream2 = TransferClientHelper.getData(tclient2.getDataTransferDescriptor(), creds);
        
        logger.info("exit downloadText");
        
        return stream2.toString();

	}	

	
	/***
	 * This method loads the properties for the client from properties file names 
	 * @return
	 */
	public static boolean loadProperties(String file) {
		logger.info("entering loadProperties");
		Properties props = new Properties();
		try {
			props.load(new FileInputStream(file));
			
			setUploadDirectory(props.getProperty(UPLOAD_DIRECTORY_TAG));
			setDownloadDirectory(props.getProperty(DOWNLOAD_DIRECTORY_TAG));			
			setUrl(props.getProperty(URL_TAG));		
			
			
			if (getUploadDirectory() == null || getDownloadDirectory() == null  || getUrl() == null)
			{
				logger.info("loadProperties: An error has occurred reading the properties in the properties file.");
				return false;
			}
		} catch (IOException e) {
			logger.error("loadProperties: An error has occurred reading the properties file.  Please place the properties file in the same directory as the jar");
		} 
			
		
		logger.info("exiting loadProperties");
		return true;
	}
	
	
	/***
	 * This function initializes the system it determines if the user has
	 * the proper credentials and sets the service url
	 * @return
	 * @throws GlobusCredentialException
	 */
	public static boolean init(String file) throws GlobusCredentialException {
		logger.info("entering init");	
		
		logger.info("initializing client");
		if (loadProperties(file))
		{
			setCreds();
			
			setClient(getUrl());
			
			logger.info("exiting init");
			
			logger.info("iitializing logging system");
			
			logger.info("exiting init - true");
			return true;
		} else {
			logger.info("exiting init - false");
			return false;
		}	
	}
		
	
	/****
	 * This method describes in detail the command that are accepted and 
	 * processed from the user
	 */
	public static void usage()
	{
		System.out.println("The following commands are acceptable:");
		System.out.println("upload file [directory]");
		System.out.println("download file [directory]");
		System.out.println("list directory");
		System.out.println("create directory");
		System.out.println("delete file [directory]");
		System.out.println("remove directory");	
	}
	
	
	
	/***
	 * This main method is an example of how to use the 
	 * API for this class
	 * @param args
	 */
	public static void main(String [] args){	    
	    try {
	    	if(!(args.length < 2))
	    	{	
				if (TransferServiceClient.init("client.properties"))
				{			
					String command = args[0];
					if (command.equalsIgnoreCase("upload")) {
						if (!(args.length > 2))
						{
							TransferServiceClient.uploadFile(args[1], null);
						}
						else {
							TransferServiceClient.uploadFile(args[1], args[2]);		
						}						
					} else if (command.equalsIgnoreCase("download")) {
						if (!(args.length > 2))
						{
							TransferServiceClient.downloadFile(args[1], null);
						}
						else {
							TransferServiceClient.downloadFile(args[1], args[2]);		
						}																		
					} else if (command.equalsIgnoreCase("create")) {
						TransferServiceClient.createDirectory(args[1]);
					} else if (command.equalsIgnoreCase("delete")) {	
						if (!(args.length > 2))
						{
							TransferServiceClient.removeFile(args[1], null);
						}
						else {
							TransferServiceClient.removeFile(args[1], args[2]);		
						}																		
					} else if (command.equalsIgnoreCase("list")) {
						TransferServiceClient.listFiles(args[1]);
					} else if (command.equalsIgnoreCase("remove")) {
						TransferServiceClient.removeDirectory(args[1]);
					} else if (command.equalsIgnoreCase("uploadText")) {
						TransferServiceClient.uploadText("bare bones test", null);
					} else {
						System.out.println ("Error: Unrecognized command");
					}
				}				
			} 
	    	else 
			{
				usage();
				System.exit(1);
			}	
		} 
	    catch (Exception e) 
	    {
			System.out.println("main: An error has occurred, error message = "+ e);
		}
		
	}

  

  public org.cagrid.transfer.context.stubs.types.TransferServiceContextReference storeFile(java.lang.String filename,java.lang.String directory) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"storeFile");
    org.cagrid.transfer.stubs.StoreFileRequest params = new org.cagrid.transfer.stubs.StoreFileRequest();
    params.setFilename(filename);
    params.setDirectory(directory);
    org.cagrid.transfer.stubs.StoreFileResponse boxedResult = portType.storeFile(params);
    return boxedResult.getTransferServiceContextReference();
    }
  }

  public org.cagrid.transfer.context.stubs.types.TransferServiceContextReference retrieveFile(java.lang.String filename,java.lang.String directory) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"retrieveFile");
    org.cagrid.transfer.stubs.RetrieveFileRequest params = new org.cagrid.transfer.stubs.RetrieveFileRequest();
    params.setFilename(filename);
    params.setDirectory(directory);
    org.cagrid.transfer.stubs.RetrieveFileResponse boxedResult = portType.retrieveFile(params);
    return boxedResult.getTransferServiceContextReference();
    }
  }

  public org.cagrid.transfer.context.stubs.types.TransferServiceContextReference storeData(java.lang.String directory) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"storeData");
    org.cagrid.transfer.stubs.StoreDataRequest params = new org.cagrid.transfer.stubs.StoreDataRequest();
    params.setDirectory(directory);
    org.cagrid.transfer.stubs.StoreDataResponse boxedResult = portType.storeData(params);
    return boxedResult.getTransferServiceContextReference();
    }
  }

  public org.cagrid.transfer.context.stubs.types.TransferServiceContextReference retrieveData() throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"retrieveData");
    org.cagrid.transfer.stubs.RetrieveDataRequest params = new org.cagrid.transfer.stubs.RetrieveDataRequest();
    org.cagrid.transfer.stubs.RetrieveDataResponse boxedResult = portType.retrieveData(params);
    return boxedResult.getTransferServiceContextReference();
    }
  }

  public java.lang.String[] viewDirectory(java.lang.String directory) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"viewDirectory");
    org.cagrid.transfer.stubs.ViewDirectoryRequest params = new org.cagrid.transfer.stubs.ViewDirectoryRequest();
    params.setDirectory(directory);
    org.cagrid.transfer.stubs.ViewDirectoryResponse boxedResult = portType.viewDirectory(params);
    return boxedResult.getResponse();
    }
  }

  public boolean makeDirectory(java.lang.String directory) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"makeDirectory");
    org.cagrid.transfer.stubs.MakeDirectoryRequest params = new org.cagrid.transfer.stubs.MakeDirectoryRequest();
    params.setDirectory(directory);
    org.cagrid.transfer.stubs.MakeDirectoryResponse boxedResult = portType.makeDirectory(params);
    return boxedResult.isResponse();
    }
  }

  public boolean deleteDirectory(java.lang.String directory) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"deleteDirectory");
    org.cagrid.transfer.stubs.DeleteDirectoryRequest params = new org.cagrid.transfer.stubs.DeleteDirectoryRequest();
    params.setDirectory(directory);
    org.cagrid.transfer.stubs.DeleteDirectoryResponse boxedResult = portType.deleteDirectory(params);
    return boxedResult.isResponse();
    }
  }

  public boolean deleteFile(java.lang.String filename,java.lang.String directory) throws RemoteException {
    synchronized(portTypeMutex){
      configureStubSecurity((Stub)portType,"deleteFile");
    org.cagrid.transfer.stubs.DeleteFileRequest params = new org.cagrid.transfer.stubs.DeleteFileRequest();
    params.setFilename(filename);
    params.setDirectory(directory);
    org.cagrid.transfer.stubs.DeleteFileResponse boxedResult = portType.deleteFile(params);
    return boxedResult.isResponse();
    }
  }

}
