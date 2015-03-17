/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  CiscoPeopleCountingFileReadyCommand.java
 * 
 *  Created on 06-02-2014, 04:55:23 PM
 * 
 */

package com.scopix.periscope.extractionmanagement.commands;

import java.io.IOException;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author carlos polo
 */
public class CiscoPeopleCountingFileReadyCommand {

    private String xmlDate;
    private String fileName;
    private String xmlValueIn;
    private String xmlValueOut;
    private Integer evidenceFileId;
	private static String uploadLocalDir;
    private ExtractEvidencePoolExecutor threadPool;
    private static Logger log = Logger.getLogger(CiscoPeopleCountingFileReadyCommand.class);

    public CiscoPeopleCountingFileReadyCommand() {
        if (uploadLocalDir == null) {
            Properties prop = null;
            ClassPathResource res = new ClassPathResource("system.properties");
            try {
                prop = new Properties();
                prop.load(res.getInputStream());
                uploadLocalDir = prop.getProperty("UploadJob.uploadLocalDir");
            } catch (IOException e) {
                log.warn("[convertValue]", e);
            }
        }
    }
    
    public void execute(){
        log.info("start, fileName: ["+getFileName()+"], xmlDate: ["+getXmlDate()+"], "
        		+ "xmlValueIn: ["+getXmlValueIn()+"], xmlValueOut: ["+getXmlValueOut()+"], evidenceFileId: ["+getEvidenceFileId()+"]");

        CiscoPeopleCountingFileReadyThread pcFileReadyThread = new CiscoPeopleCountingFileReadyThread();
        pcFileReadyThread.init(uploadLocalDir, getFileName(), getXmlDate(), getXmlValueIn(), getXmlValueOut(), getEvidenceFileId());

        //ejecuta en el pool de hilos
        getThreadPool().execute(pcFileReadyThread);
        log.info("end");
    }

    /**
     * @return the xmlDate
     */
    public String getXmlDate() {
        return xmlDate;
    }

    /**
     * @param xmlDate the xmlDate to set
     */
    public void setXmlDate(String xmlDate) {
        this.xmlDate = xmlDate;
    }

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param fileName the fileName to set
     */
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    /**
     * @return the xmlValueIn
     */
    public String getXmlValueIn() {
        return xmlValueIn;
    }

    /**
     * @param xmlValueIn the xmlValueIn to set
     */
    public void setXmlValueIn(String xmlValueIn) {
        this.xmlValueIn = xmlValueIn;
    }

    /**
     * @return the xmlValueOut
     */
    public String getXmlValueOut() {
        return xmlValueOut;
    }

    /**
     * @param xmlValueOut the xmlValueOut to set
     */
    public void setXmlValueOut(String xmlValueOut) {
        this.xmlValueOut = xmlValueOut;
    }

    /**
     * @return the threadPool
     */
    public ExtractEvidencePoolExecutor getThreadPool() {
        return threadPool;
    }

    /**
     * @param threadPool the threadPool to set
     */
    public void setThreadPool(ExtractEvidencePoolExecutor threadPool) {
        this.threadPool = threadPool;
    }
    
    public Integer getEvidenceFileId() {
		return evidenceFileId;
	}

	public void setEvidenceFileId(Integer evidenceFileId) {
		this.evidenceFileId = evidenceFileId;
	}
}