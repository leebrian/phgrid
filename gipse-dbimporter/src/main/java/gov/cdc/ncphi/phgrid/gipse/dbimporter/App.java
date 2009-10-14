package gov.cdc.ncphi.phgrid.gipse.dbimporter;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;

/**
 * Application for importing gipse extract files into an GIPSE Store database.
 * @project PHGrid (Apache License v2.0)
 * @created 2009.05.11 1445-04
 * @author Brian Alexander Lee (brianalee@gmail.com)
 */
public class App{
	private static final Logger LOGGER = Logger.getLogger(App.class);
	
    public static void main( String[] args ) throws IOException, SQLException, ClassNotFoundException{
    	int filesProcessed = 0; //used for displaying total files processed
    	ImporterJobResults totalResults = new ImporterJobResults("GIPSE Extract Job");
    	
    	System.out.println("Started...");
    	LOGGER.info("Starting import job.");
    	
    	processProperties();
    	processArguments(args);
    	
    	//process input files
    	File workingDirectory = new File(ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_WORKING_DIRECTORY));
    	if (workingDirectory.exists()){
    		if (workingDirectory.canWrite()){
    			File processingDirectory = new File(workingDirectory, "processing");
    			if (!processingDirectory.exists()){
    				processingDirectory.mkdir();
    			}
    			File outputDirectory = new File(workingDirectory, "output");
    			if (!outputDirectory.exists()){
    				outputDirectory.mkdir();
    			}
    			
    			Connection conn = null;
    			
    			try{
    				ImporterObject importer = new ImporterObject();
    				conn = DBUtils.getConnection();
    				long jobId = DBUtils.persistImportJob(0l, totalResults, GIPSEConstants.IMPORT_JOBS_STATUS_STARTED, conn);
    				LOGGER.debug("jobId<" + jobId + ">");
	    			Collection<File> inFiles = FileUtils.listFiles(workingDirectory, new String[]{"xml","csv"}, false);
	    			for (File inFile : inFiles){
	    				String fileName = inFile.getName();
	    				File processingFile = new File(processingDirectory,fileName);
	    				
	    				FileUtils.copyFile(inFile, processingFile);
	    				
	    				ImporterJobResults fileResults = importer.processGIPSEExtractFile(processingFile, conn); 
	    				totalResults.addResults(fileResults);
	    				DBUtils.persistImportJob(jobId, totalResults, GIPSEConstants.IMPORT_JOBS_STATUS_ACTIVE, conn);
	    				
	    				File completedFile = new File(outputDirectory, fileName);
	    				FileUtils.deleteQuietly(completedFile);
	    				FileUtils.moveFile(processingFile, completedFile);
	    				if (LOGGER.isDebugEnabled()){
	    					LOGGER.debug("Finished processing file <" + processingFile.getName() + ">." + fileResults.toString());
	    				}
	    				filesProcessed++;
	    			}
	    			totalResults.setCompleteDate(new Date());
	    			
	    			String importJobDescription = buildMessage(filesProcessed, totalResults);
	    			
	    			totalResults.setDescription(importJobDescription);
	    			
	    			DBUtils.persistImportJob(jobId, totalResults, GIPSEConstants.IMPORT_JOBS_STATUS_COMPLETE, conn);
	    			ImporterUtils.updateTwitter(importJobDescription);
    			}finally{
    				if (conn != null && !conn.isClosed()){
    					conn.close();
    				}
    			}
    			
    		}else{
    			throw new RuntimeException("Working directory <" + ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_WORKING_DIRECTORY) + "> is not writable.");
    		}
    	}else{
    		throw new RuntimeException("Working directory <" + ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_WORKING_DIRECTORY) + "> does not exist.");
    	}
    	
    	LOGGER.info("Import job complete. Files processed: <" + filesProcessed + ">." + totalResults.toString());
    	System.out.println("Finished. Thank you, please import again.");
    }
    
    /**
     * Builds a pretty job description.
     * @param filesProcessed
     * @param totalResults
     * @return
     */
    private static String buildMessage(long filesProcessed, ImporterJobResults totalResults){
    	long totalTime = totalResults.getCompleteDate().getTime() - totalResults.getStartDate().getTime();
    	StringBuilder sb = new StringBuilder(140).append("#phgrid Import Complete for env ")
		.append(ImporterUtils.getProperties().getProperty(GIPSEConstants.PROPERTY_ENVIRONMENT_LABEL)) 
		.append(". ")
		.append(filesProcessed)
		.append(" file");
		if (filesProcessed != 1){
			sb.append('s');
		}
		sb.append(", ")
		.append(totalResults.getRecordsTotal())
		.append(" record");
		if (totalResults.getRecordsTotal() != 1){
			sb.append('s');
		}
		sb.append(": ")
		.append(totalResults.getRecordsInserted())
		.append("inserted, ")
		.append(totalResults.getRecordsUpdated())
		.append("updated, ")
		.append(totalResults.getRecordsSkipped())
		.append(" skipped.Took ")
		.append(totalTime/1000)
		.append(" s.");
		
		return sb.toString();
    }
    
    /**
     * Checks for a properties file in the local directory. If it exists, it overwrites all the properties specified in the jar properties file with the values in the local properties file.
     * @throws FileNotFoundException
     * @throws IOException
     */
    private static void processProperties() throws FileNotFoundException, IOException{
    	File propertiesFile = new File(GIPSEConstants.PROPERTIES_FILE_NAME);
    	if (propertiesFile.exists()){
    		ImporterUtils.getProperties().load(new FileInputStream(propertiesFile));
    	}
    }
    
    /**
     * Look at the command line args, process help and reset any properties that are passed in.
     * @param args
     */
    private static void processArguments(String [] args){
    	int argsLength = args.length;
    	if (argsLength > 0){
    		if (args[0].equals("--help") || args[0].equals("?")){
    			System.out.println("usage: import data to db, using properties specified in <" + GIPSEConstants.PROPERTIES_FILE_NAME +">. Specify properties to overwrite with format property=value");
    			System.out.println("Example1: java -jar dbimporter-0.1-jar-with-dependencies.jar " + GIPSEConstants.PROPERTY_WORKING_DIRECTORY +"=c:\\foo " + GIPSEConstants.PROPERTY_USER + "=dbusername");
    			System.out.println("Supported properties are:");
    			System.out.println(GIPSEConstants.PROPERTY_WORKING_DIRECTORY);
    			System.out.println(GIPSEConstants.PROPERTY_DRIVER);
    			System.out.println(GIPSEConstants.PROPERTY_URL);
    			System.out.println(GIPSEConstants.PROPERTY_USER);
    			System.out.println(GIPSEConstants.PROPERTY_PASS);
    			System.out.println(GIPSEConstants.PROPERTY_TWITTER_USER);
    			System.out.println(GIPSEConstants.PROPERTY_TWITTER_PASS);
    			System.out.println(GIPSEConstants.PROPERTY_RESET_RANDOM);
    		}else{
    			for (String argument : args){
    				String [] property = argument.split("=");
    				if (property.length == 2){
    					ImporterUtils.getProperties().put(property[0], property[1]);
    				}else{
    					System.out.println("Ignoring <" + argument + "> because it isn't formatted properly (should be x=y, no spaces).");
    				}
    			}
    		}
    	}
    	
    }
}
