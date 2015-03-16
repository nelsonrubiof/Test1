/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 */
public class DeleteBroadwareEvidenceThread extends Thread {
    
    private static Logger log = Logger.getLogger(DeleteBroadwareEvidenceThread.class);
    private String fileName;
    private String ipAndPort;
    private boolean initialized = false;
    
    public void init(String fileName, String ipAndPort) {
        this.fileName = fileName;
        this.ipAndPort = ipAndPort;
        this.initialized = true;
        this.setName(this.getClass().getSimpleName() + "-" + this.fileName);
    }

    /**
     * Calls to broadware to delete the file from local folder
     */
    @SuppressWarnings("static-access")
    public void run() {
        log.info("start [filename:" + fileName + "]");
        
        try {
            String host = "localhost";
            if (ipAndPort != null) {
                host = ipAndPort;
            }
            
            String url = "http://" + host
                    + "/cgi-bin/smanager.bwt?command=remove&name=" + fileName;
            if (initialized) {
                try {
                    Thread.currentThread().sleep(10000);
                    callBroadware(url);
                    
                } catch (ScopixException ex) {
                    log.error("Error calling Broadware.", ex);
                } catch (InterruptedException ex) {
                    log.error("Error Interrupting the Thread.", ex);
                }
                
            } else {
                throw new RuntimeException("Thread not initialized. Call to init() before run()");
            }
        } catch (Exception e) {
            log.error("No es posible termiar ejecución " + e, e);
        }
        log.info("end");
    }
    
    private void callBroadware(String urlString) throws ScopixException {
        log.info("start [urlString:" + urlString + "]");
        try {
            
            URL url = new URL(urlString);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            
            
            int contentlength = con.getContentLength();
            int responseCode = con.getResponseCode();
            
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            
            String inputLine;
            
            while ((inputLine = in.readLine()) != null) {
                log.debug("inputLine: " + inputLine);
            }
            in.close();
            
            log.debug("contentlength: " + contentlength + " responseCode" + responseCode);
        } catch (IOException ex) {
            throw new ScopixException("Cannot delete file.", ex);
        }
        log.info("end");
    }
}
