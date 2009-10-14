package org.cagrid.transfer.service;

import java.io.File;
import java.rmi.RemoteException;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.cagrid.transfer.context.service.globus.resource.TransferServiceContextResource;
import org.cagrid.transfer.context.service.helper.DataStagedCallback;
import org.cagrid.transfer.context.service.helper.TransferServiceHelper;
import org.cagrid.transfer.descriptor.DataDescriptor;

/****
 * 
 * 
 * 
 * @author Felicia Rosemond (frosemond@gmail.com)
 *
 */
public class TransferServiceImpl extends TransferServiceImplBase {
	static final Log logger = LogFactory.getLog(TransferServiceImpl.class);
	
	
  /***
   * This method is the constructor for this class.  The configured variables are
   * sent to the logger for documentation purposes.
   *  
   * @throws RemoteException
   */
  public TransferServiceImpl() throws RemoteException {
		super();
		
		logger.info("Starting the TransferService Implementation");
		
		/***
		 * the paths for these are stored in the jndi-config.xml 
		 * in the service deployment directory.  
		 */
		
		try {
			logger.info("etc path directory = "
					+ TransferServiceConfiguration.getConfiguration()
							.getEtcDirectoryPath());
			logger.info("storage directory = "
					+ TransferServiceConfiguration.getConfiguration()
							.getStorageDirectory());
			logger.info("servlet path name = "
					+ TransferServiceConfiguration.getConfiguration()
							.getTransferServletPathName());

        } catch (Exception e) {
           logger.error("A problem has occurred the following error was returned: "+ e.getStackTrace().toString());
           throw new RemoteException("A problem has occurred the following error was returned: "+ e.getStackTrace().toString());
        }
	}
	
  
  /***
   * This method stores a file on the server in the configured storage directory.
   * The uploaded file is initially uploaded with a system generated name but the
   * file is saved with the name sent in the method call and the system generated
   * file is deleted,
   * 
   *
   * @param filename
   * @return
   * @throws RemoteException
   */
  public org.cagrid.transfer.context.stubs.types.TransferServiceContextReference storeFile(java.lang.String filename,java.lang.String directory) throws RemoteException {
		try {

			// create a data descriptor
			logger.info("setting data descriptor");
			DataDescriptor dd = new DataDescriptor(null, "UserData");

			// this is here to keep Introduce from barfing that filename needs to of type final
			// to be used inside of the DataStagedCallback routine
			final String file = filename;
			final String dir = directory;
			
			// create a callback to handle the data
			logger.info("setting the data staged callback function");
			DataStagedCallback dsc = new DataStagedCallback() {

				public void dataStaged(TransferServiceContextResource resource) {

					logger.info("setting delete on destroy equal to false");
					resource.getDataStorageDescriptor().setDeleteOnDestroy(false);
					logger.info("finshed setting delete on destroy equal to false");
					File dataFileUserSentMe = new File(resource
							.getDataStorageDescriptor().getLocation());

					// display file name
					logger.info("received file "
							+ dataFileUserSentMe.toString());

					// copy file to file system
					try {
						
						if (dir == null) {
							ccl.util.FileUtil.copy(dataFileUserSentMe
								.getAbsolutePath(),
								TransferServiceConfiguration.getConfiguration()
										.getStorageDirectory()
										+ "/" + file);
						} else {
							if (!ccl.util.FileUtil.existsDir(dir))
							{
								ccl.util.FileUtil.md(TransferServiceConfiguration.getConfiguration()
											.getStorageDirectory()
											+ "/" + dir );
							}
							
							ccl.util.FileUtil.copy(dataFileUserSentMe
									.getAbsolutePath(),
									TransferServiceConfiguration.getConfiguration()
											.getStorageDirectory()
											+ "/" + dir + "/" + file);
							
						}
		
						//delete system generated file name
						ccl.util.FileUtil.delete(dataFileUserSentMe.toString());
						
					} catch (Exception e) {
				           logger.error("A problem has occurred the following error was returned: "+ e.getStackTrace().toString());
					}
				}
			};

			// create the transfer resource that will handle receiving the data
			// and
			// return the reference to the user
			return TransferServiceHelper.createTransferContext(dd, dsc);

		} catch (Exception e) {
	           logger.error("A problem has occurred the following error was returned: "+ e.getMessage());
	           throw new RemoteException("A problem has occurred the following error was returned: "+ e.getStackTrace().toString());
		}
}

  
  /***
   * This method retrieves a named file from the configured storage directory on the server. 
   * 
   * 
   * @param filename
   * @return
   * @throws RemoteException
   */  
  public org.cagrid.transfer.context.stubs.types.TransferServiceContextReference retrieveFile(java.lang.String filename,java.lang.String directory) throws RemoteException {
	  
	    File data = null;
		try {
			logger.info("inside of retrieveData");
			
			// create path to file
			if (directory == null)  {
				data = new File(TransferServiceConfiguration
					.getConfiguration().getStorageDirectory()
					+ "/" + filename);
			} else {
				
				if (ccl.util.FileUtil.existsDir(TransferServiceConfiguration
						.getConfiguration().getStorageDirectory()
						+ "/" + directory))
				{
				data = new File(TransferServiceConfiguration
						.getConfiguration().getStorageDirectory()
						+ "/" + directory + "/" + filename);
				} else {
					logger.error("A problem has occurred retrieving"+ filename+ "from "+ directory+ "the directory does not exist" );
			        throw new RemoteException("A problem has occurred retrieving"+ filename+ "from "+ directory+ "the directory does not exist" );	  					
				}
			}
			// create a descriptor for that data
			logger.info("creating data descriptor");
			DataDescriptor dd = new DataDescriptor(null, "My Data");

			// create the transfer resource that will handle delivering the data
			// and return the reference to the user
			logger.info("creating transfer context");
			return TransferServiceHelper.createTransferContext(data, dd, true);
		} catch (Exception e) {
	           logger.error("A problem has occurred the following error was returned: "+ e.getMessage());
	           throw new RemoteException("A problem has occurred the following error was returned: "+ e.getStackTrace().toString());
		}
  }

  
  /****
   * This method stored text data on the server in a file with a system generated name.
   * 
   * @return
   * @throws RemoteException
   */
  public org.cagrid.transfer.context.stubs.types.TransferServiceContextReference storeData(java.lang.String directory) throws RemoteException {
		try {

			// create a data descriptor
			logger.info("setting data descriptor");
			DataDescriptor dd = new DataDescriptor(null, "UserData");

			// create a callback to handle the data
			logger.info("setting the data staged callback function");
			DataStagedCallback dsc = new DataStagedCallback() {

				public void dataStaged(TransferServiceContextResource resource) {
					logger.info("setting delete on destroy equal to false");
					resource.getDataStorageDescriptor().setDeleteOnDestroy(
							false);
					logger.info("finshed setting delete on destroy equal to false");
					File dataFileUserSentMe = new File(resource
							.getDataStorageDescriptor().getLocation());

					// display file name
					logger.info("received file "
							+ dataFileUserSentMe.toString());
				}
			};

			// create the transfer resource that will handle receiving the data
			// and return the reference to the user	
			return TransferServiceHelper.createTransferContext(dd, dsc);

		} catch (Exception e) {
	           logger.error("A problem has occurred the following error was returned: "+ e.getMessage());
	           throw new RemoteException("A problem has occurred the following error was returned: "+ e.getMessage());
		}
  }

  
  /****
   * This method is used to retrieve text data.  At the moment this method returns a simple string of 
   * text but could be modified to return the result of a db query or a system call.
   * 
   * @return
   * @throws RemoteException
   */
  public org.cagrid.transfer.context.stubs.types.TransferServiceContextReference retrieveData() throws RemoteException {
		try {
			logger.info("inside of retrieveData");
			// create some data to sent
			byte[] data = new String("This is my bulk data baby baby")
					.getBytes();

			// create a descriptor for that data
			logger.info("creating data descriptor");
			DataDescriptor dd = new DataDescriptor(null, "My Data");

			// create the transfer resource that will handle delivering the data
			// and
			// return the reference to the user
			logger.info("creating transfer context");
			return TransferServiceHelper.createTransferContext(data, dd);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RemoteException("A problem has occurred downloadign data");
		}

  }

  
  /****
   * This method returns a listing of files in the directory requested directory.
   * The method checks to make sure that the requested directory exists under the 
   * configured storage directory.
   * 
   * @param directory
   * @return
   * @throws RemoteException
   */
  @SuppressWarnings("unchecked")
  public java.lang.String[] viewDirectory(java.lang.String directory) throws RemoteException {
    
	  Vector fileVect;
	try {
		
		// this can be used to check the top level directory, all directory checks 
		// are based on the storageDirectory
		if (directory == ".")
			directory ="";
		
		logger.info("directory = "+ directory);
		
		if (ccl.util.FileUtil.existsDir(TransferServiceConfiguration.getConfiguration().getStorageDirectory()+ "/" +directory)) {
			
			fileVect = ccl.util.FileUtil.getFiles(TransferServiceConfiguration.getConfiguration()
					.getStorageDirectory()
					+ "/" +directory);
			
			
			logger.info("file listing as vector = "+fileVect.toString());
			
		} else {
			logger.error("A problem has occurred the directory "+ directory + "does not exist");
	        throw new RemoteException("A problem has occurred the directory "+ directory + "does not exist");			
		}
	} catch (Exception e) {
		logger.error("A problem has occurred the following error was returned: "+ e.getStackTrace().toString());
        throw new RemoteException("A problem has occurred the following error was returned: "+ e.getStackTrace().toString());
	}
	  String[] fileListing = new String[fileVect.size()];
	  fileVect.toArray(fileListing);
	  
	  return fileListing;
	  
  }

  public boolean makeDirectory(java.lang.String directory) throws RemoteException {
    
	  boolean rt = false;
	  
	  //check if the directory already exist
	  try {
		if (ccl.util.FileUtil.existsDir(TransferServiceConfiguration.getConfiguration().getStorageDirectory()+ "/" + directory))
		{
			logger.error("A problem has occurred the directory "+ directory + " already exists");
		}
		else
		{
			rt = ccl.util.FileUtil.mkdir(TransferServiceConfiguration.getConfiguration().getStorageDirectory()+ "/" + directory);			
		}
	} catch (Exception e) {
		logger.error("A problem has occurred in the makedirectory method, error = "+ e.getMessage());
        throw new RemoteException("A problem has occurred in the makedirectory method, error = "+ e.getMessage());	  
	}
	
	return rt;
  }

  public boolean deleteDirectory(java.lang.String directory) throws RemoteException {
	  boolean rt = false;
	  
	  //check if the directory already exist
	  try {
		if (!ccl.util.FileUtil.existsDir(TransferServiceConfiguration.getConfiguration().getStorageDirectory()+ "/" + directory))
		{
			logger.error("A problem has occurred the directory "+ directory + " can not be deleted it does not exist");
	        throw new RemoteException("A problem has occurred the directory "+ directory + " can not be deleted it does not exist");	  
		}
		else
		{
			rt = ccl.util.FileUtil.deleteRecursively(TransferServiceConfiguration.getConfiguration().getStorageDirectory()+ "/" + directory);			
		}
	} catch (Exception e) {
		logger.error("A problem has occurred in the deleteDirectory method, error = "+ e.getMessage());
        throw new RemoteException("A problem has occurred in the deleteDirectory method, error = "+ e.getMessage());	  
	}
	
	return rt;   
  }

  public boolean deleteFile(java.lang.String filename,java.lang.String directory) throws RemoteException {
	  boolean rt = false;
	  String path = null;
	 
	  
	  //check if the directory already exist
	  try {
		  
		  if (directory == null)
			path = TransferServiceConfiguration.getConfiguration().getStorageDirectory();
		  else
			path = TransferServiceConfiguration.getConfiguration().getStorageDirectory()+ "/" + directory;
			  
		  
		if (!ccl.util.FileUtil.existsFile(path + "/" + filename))
		{
			logger.error("A problem has occurred the file "+ TransferServiceConfiguration.getConfiguration().getStorageDirectory()+ "/" + directory + "/" + filename + "does not exist");
	        throw new RemoteException("A problem has occurred the directory "+ TransferServiceConfiguration.getConfiguration().getStorageDirectory()+ "/" + directory + "/" + filename + "does not exist");	  
		}
		else
		{
			rt = ccl.util.FileUtil.delete(path + "/" + filename);			
		}
	} catch (Exception e) {
		logger.error("A problem has occurred in the makedirectory method, error = "+ e.getMessage());
        throw new RemoteException("A problem has occurred in the makedirectory method, error = "+ e.getMessage());	  
	}
	
	return rt;    
  }

}

