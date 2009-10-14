package edu.columbia.dbmi.context.service.globus.resource;

import java.io.File;
import java.io.FileFilter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.namespace.QName;

import org.globus.wsrf.ResourceException;

import edu.columbia.dbmi.medlee.MedleeInputDocument;
import edu.columbia.dbmi.medlee.MedleeInputDocuments;
import edu.columbia.dbmi.medlee.MedleeInvocationConfiguration;
import edu.columbia.dbmi.medlee.MedleeOutputDocument;
import edu.columbia.dbmi.medlee.MedleeOutputDocuments;
import edu.columbia.dbmi.medlee.StatusOptions;
import edu.columbia.dbmi.service.GridMedleeConfiguration;
import gov.nih.nci.cagrid.common.StreamGobbler;
import gov.nih.nci.cagrid.common.Utils;


/**
 * The implementation of this GridMedleeContextResource type.
 * 
 * @created by Introduce Toolkit version 1.2
 */
public class GridMedleeContextResource extends GridMedleeContextResourceBase {

    private MedleeInputDocuments inputDocuments = null;

    private MedleeInvocationConfiguration medleeConf = null;

    File inputsDir = null;
    File outputsDir = null;


    public MedleeInputDocuments getInputDocuments() {
        return inputDocuments;
    }


    public void setInputDocuments(MedleeInputDocuments inputDocuments) {
        this.inputDocuments = inputDocuments;
    }


    public MedleeInvocationConfiguration getMedleeConf() {
        return medleeConf;
    }


    public void setMedleeConf(MedleeInvocationConfiguration medleeConf) {
        this.medleeConf = medleeConf;
    }


    @Override
    public void initialize(Object resourceBean, QName resourceElementQName, Object id) throws ResourceException {
        super.initialize(resourceBean, resourceElementQName, id);
        // write the documents to a folder
        try {
            String basedir = GridMedleeConfiguration.getConfiguration().getEtcDirectoryPath() + File.separator
                + GridMedleeContextResource.this.getID();
            File basedirF = new File(basedir);
            basedirF.mkdir();
            inputsDir = new File(basedirF.getAbsolutePath() + File.separator + "inputs");
            inputsDir.mkdir();
            outputsDir = new File(basedirF.getAbsolutePath() + File.separator + "outputs");
            outputsDir.mkdir();
        } catch (Exception e) {
            throw new ResourceException("Problem initializing resource");
        }
        
        setStatus(StatusOptions.READY);
    }


    public void invoke() throws ResourceException {
        Thread th = new Thread(new Runnable() {

            public void run() {
                // set the cleanup time to be 5 * number of documents in minutes
                if (GridMedleeContextResource.this.getInputDocuments() != null
                    && GridMedleeContextResource.this.getInputDocuments().getMedleeInputDocument() != null) {
                    Calendar cal = new GregorianCalendar();
                    cal.roll(Calendar.MINUTE, 5 * GridMedleeContextResource.this.getInputDocuments()
                        .getMedleeInputDocument().length);
                    GridMedleeContextResource.this.setTerminationTime(cal);

                    // write the documents to a folder
                    try {
                        for (int i = 0; i < GridMedleeContextResource.this.getInputDocuments().getMedleeInputDocument().length; i++) {
                            MedleeInputDocument doc = GridMedleeContextResource.this.getInputDocuments()
                                .getMedleeInputDocument()[i];
                            FileWriter writer = new FileWriter(inputsDir + File.separator + doc.getIdentifier());
                            writer.write(doc.getText());
                            writer.close();
                        }

                        // invoke medlee with the params
                        // create a run medlee
                        String cmd = getMedleeCall(medleeConf, inputsDir, outputsDir);
                        System.out.println("RUNNING MEDLEE WITH COMMAND:  " + cmd);

                        Process p = Runtime.getRuntime().exec(cmd);
                        setStatus(StatusOptions.RUNNING);
                        StreamGobbler outputStreamReader = new StreamGobbler(p.getInputStream(), StreamGobbler.TYPE_OUT,System.out);
                        outputStreamReader.start();

                        StreamGobbler errorStreamReader = new StreamGobbler(p.getErrorStream(), StreamGobbler.TYPE_ERR,System.err);
                        errorStreamReader.start();

                        // wait for it to finish;
                        int value = p.waitFor();
                        if (value != 0) {
                            setStatus(StatusOptions.ERROR);
                        } else {
                            setStatus(StatusOptions.COMPLETE);
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            setStatus(StatusOptions.ERROR);
                        } catch (ResourceException e1) {
                            e1.printStackTrace();
                        }
                    }

                }

            }

        });

        th.start();

        return;
    }


    public MedleeOutputDocuments getOutputDocuments() {
        if (getStatus().equals(StatusOptions.COMPLETE)) {
            FileFilter filter = new FileFilter() {

                public boolean accept(File pathname) {
                    if (pathname.isDirectory()) {
                        return false;
                    }
                    return true;
                }

            };

            File[] files = outputsDir.listFiles(filter);
            System.out.println("MEDLEE RAN AND CREATED " + files.length + " DOCUMENTS");
            MedleeOutputDocument[] outputDocs = new MedleeOutputDocument[files.length];
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                MedleeOutputDocument doc = new MedleeOutputDocument();
                doc.setIdentifier(file.getName());
                try {
                    StringBuffer sb = Utils.fileToStringBuffer(file);
                    doc.setXMLOutput(sb.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                outputDocs[i] = doc;
            }
            System.out.println("Returning array of output documents to the client.");
            return new MedleeOutputDocuments(outputDocs);
        } else {
            return null;
        }
    }


    private static String getMedleeCall(MedleeInvocationConfiguration medleeConf, File inputDir, File outputDir)
        throws Exception {
	//        String cmd = "xterm -e run_med -lex /parser/bin/medleelex/lexicon";
        String cmd = "run_med -lex /parser/bin/medleelex/lexicon";
        if (medleeConf.getParseMode() != null) {
            cmd += " -m " + medleeConf.getParseMode();
        }
        if (medleeConf.getCode() != null) {
            cmd += " -code " + medleeConf.getCode();
        }

        if (medleeConf.getOutputFormat() != null) {
            cmd += " -f " + medleeConf.getOutputFormat();
        }

        if (medleeConf.getOutputFormat() != null) {
            cmd += " -dir " + inputDir.getAbsolutePath();
        }

        if (medleeConf.getOutputFormat() != null) {
            cmd += " -out " + outputDir.getAbsolutePath();
        }

        return cmd;
    }

}
