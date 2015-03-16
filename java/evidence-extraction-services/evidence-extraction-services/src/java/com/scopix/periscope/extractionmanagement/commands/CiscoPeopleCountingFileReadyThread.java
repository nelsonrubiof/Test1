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
 *  CiscoPeopleCountingFileReadyThread.java
 * 
 *  Created on 11-02-2014, 02:53:52 PM
 * 
 */

package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author carlos polo
 */
public class CiscoPeopleCountingFileReadyThread implements Runnable {

    private String valueIn;
    private String valueOut;
    private String fileName;
    private String requestDate;
    private String uploadLocalDir;
    private Integer evidenceFileId;
    private static Logger log = Logger.getLogger(CiscoPeopleCountingFileReadyThread.class);
    
    public void init(String uploadLocalDir, String fileName, String requestDate, String valueIn, String valueOut, Integer evidenceFileId){
    	log.info("start, uploadLocalDir: ["+uploadLocalDir+"], fileName: ["+fileName+"], requestDate: ["+requestDate+"], "
        		+ "valueIn: ["+valueIn+"], valueOut: ["+valueOut+"], evidenceFileId: ["+evidenceFileId+"]");

        setUploadLocalDir(uploadLocalDir);
        setFileName(fileName);
        setRequestDate(requestDate);
        setValueIn(valueIn);
        setValueOut(valueOut);
        setEvidenceFileId(evidenceFileId);
        log.info("end");
    }

    @Override
    public void run() {
        log.info("start, fechaXMLoriginal: [" + getRequestDate() + "]"); //ej 2014-02-2616:10
        try {
            //Parsing solo para verificar de que sea una fecha válida
            Date date = DateUtils.parseDate(getRequestDate(), new String[]{"yyyy-MM-ddHH:mm"});
            log.debug("date: [" + date + "]");

            setRequestDate(DateFormatUtils.format(date, "yyyy-MM-dd HH:mm"));
            log.debug("requestDate: ["+getRequestDate()+"], valueIn: ["+getValueIn()+"], valueOut: ["+getValueOut()+"]");

            String filePathName = FilenameUtils.separatorsToUnix(getUploadLocalDir()+getFileName());
            log.debug("filePathName: [" + filePathName + "]");

            generateXMLFile(filePathName);

            //actualiza en BD el evidence file luego de ser descargado
            EvidenceFile evidenceFile = getEvidenceFile();
            
            EvidenceFileDAO dao = HibernateSupport.getInstance().findDao(EvidenceFileDAO.class);
            dao.updateEvidenceFile(evidenceFile, getFileName());
            
        } catch (ScopixException ex) {
            log.error("error actualizando evidence file: [" + ex.getMessage() + "]", ex);
        } catch (ParseException ex) {
            log.error("error en parsing de fecha: [" + ex.getMessage() + "]", ex);
        }
        log.info("end");
    }
    
    /**
     * 
     * @param filePathName
     * @throws ScopixException 
     */
    private void generateXMLFile(String filePathName) throws ScopixException {
        log.info("start, filePathName: ["+filePathName+"]");
        InputStream xmlInputStream = null;
        try {
            //Create new Document XML
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilderNew = docBuilderFactory.newDocumentBuilder();
            Document docNew = docBuilderNew.newDocument();
            Element root = docNew.createElement("root");
            docNew.appendChild(root);
            
            Element childNode = docNew.createElement("value");
            childNode.setAttribute("date", getRequestDate());
            childNode.setAttribute("valuein", getValueIn());
            childNode.setAttribute("valueout", getValueOut());
            root.appendChild(childNode);
            log.debug("childNode creado");

            DOMSource source = new DOMSource(docNew);
            StringWriter xmlAsWriter = new StringWriter();
            StreamResult result = new StreamResult(xmlAsWriter);
            TransformerFactory.newInstance().newTransformer().transform(source, result);
            xmlInputStream = new ByteArrayInputStream(xmlAsWriter.toString().getBytes());
            
            byte[] buffer = new byte[65536];
            //filename is repeated as the filepath inside the repository
            FileOutputStream xml = new FileOutputStream(filePathName);
            int bytesRead = 1;
            while (bytesRead > 0) {
                bytesRead = xmlInputStream.read(buffer, 0, 65536);
                if (bytesRead > 0) {
                    xml.write(buffer, 0, bytesRead);
                }
            }
            xml.close();
            log.debug("xml creado, filePathName: [" + filePathName + "]");

        } catch (Exception ex) {
            throw new ScopixException(ex.getMessage(), ex);
        } finally {
            try {
                if(xmlInputStream!=null){
                    xmlInputStream.close();
                }
            } catch (IOException ex) {
                log.error("error cerrando xmlInputStream: [" + ex.getMessage() + "]", ex);
            }
        }
        log.info("end");
    }
    
    /**
     * Obtiene evidenceFile por id
     *
     * @return
     */
    private EvidenceFile getEvidenceFile() {
        log.debug("start");
        int retry = 3;
        int attempt = 1;

        EvidenceFile evidenceFile = null;
        EvidenceFileDAO evidenceFileDAO = HibernateSupport.getInstance().findDao(EvidenceFileDAO.class);

        do {
            try {
                evidenceFile = evidenceFileDAO.get(getEvidenceFileId());
            } catch (ObjectRetrievalFailureException ex) {
                log.error(ex.getMessage() + ", intento: [" + attempt + "]", ex);
                attempt++;
                threadSleep();
            }
        } while (attempt <= retry && evidenceFile == null);

        log.debug("end, evidenceFile: [" + evidenceFile + "]");
        return evidenceFile;
    }
    
    /**
     * Duerme hilo actual por 5 segundos
     */
    private void threadSleep() {
        try {
            log.debug("durmiendo hilo actual por 5 segundos para "
                    + "dar tiempo a persistencia de evidence file con id: [" + getEvidenceFileId() + "]");
            Thread.sleep(5000); // sleep for 5000 milliseconds,
        } catch (InterruptedException ex) {
            log.warn(ex.getMessage(), ex);
        }
    }

    /**
     * @return the uploadLocalDir
     */
    public String getUploadLocalDir() {
        return uploadLocalDir;
    }

    /**
     * @param uploadLocalDir the uploadLocalDir to set
     */
    public void setUploadLocalDir(String uploadLocalDir) {
        this.uploadLocalDir = uploadLocalDir;
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
     * @return the requestDate
     */
    public String getRequestDate() {
        return requestDate;
    }

    /**
     * @param requestDate the requestDate to set
     */
    public void setRequestDate(String requestDate) {
        this.requestDate = requestDate;
    }

    /**
     * @return the valueIn
     */
    public String getValueIn() {
        return valueIn;
    }

    /**
     * @param valueIn the valueIn to set
     */
    public void setValueIn(String valueIn) {
        this.valueIn = valueIn;
    }

    /**
     * @return the valueOut
     */
    public String getValueOut() {
        return valueOut;
    }

    /**
     * @param valueOut the valueOut to set
     */
    public void setValueOut(String valueOut) {
        this.valueOut = valueOut;
    }

	public Integer getEvidenceFileId() {
		return evidenceFileId;
	}

	public void setEvidenceFileId(Integer evidenceFileId) {
		this.evidenceFileId = evidenceFileId;
	}
}