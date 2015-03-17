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
 *  VadaroPeopleCountingFileReadyCommand.java
 * 
 *  Created on 21-04-2014, 18:08:00 PM
 * 
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.ByteArrayInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.scopix.periscope.VadaroData;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 * Integración people-counting para el provider Vadaro
 * @author Carlos
 */
public class VadaroPeopleCountingFileReadyCommand {

    private static String uploadLocalDir;
    private static Logger log = Logger.getLogger(VadaroPeopleCountingFileReadyCommand.class);

    public VadaroPeopleCountingFileReadyCommand() {
    	log.info("start");
        if (uploadLocalDir == null) {
            Properties prop = null;
            ClassPathResource res = new ClassPathResource("system.properties");
            try {
                prop = new Properties();
                prop.load(res.getInputStream());
                uploadLocalDir = prop.getProperty("UploadJob.uploadLocalDir");
            } catch (IOException e) {
                log.warn(e.getMessage(), e);
            }
        }
        log.info("end, uploadLocalDir: [" + uploadLocalDir + "]");
    }

    /**
     * Ejecuta generación de archivo XML
     * 
     * @param  xmlContent
     * @param  evidenceFile
     * @param  fileName
     * @param  timezoneId
     * @throws ScopixException
     */
    public void execute(String xmlContent, EvidenceFile evidenceFile, String fileName, String timezoneId) throws ScopixException {
    	log.info("start, fileName: [" + fileName + "]");
    	String filePathName = FilenameUtils.separatorsToUnix(getUploadLocalDir() + fileName);
    	
    	log.debug(xmlContent);
    	//genera archivo XML en ruta de upload
    	generateXMLFile(xmlContent, filePathName, timezoneId);

    	//actualiza en BD el evidence file luego de ser descargado
        EvidenceFileDAO dao = HibernateSupport.getInstance().findDao(EvidenceFileDAO.class);
        dao.updateEvidenceFile(evidenceFile, fileName);
    	log.info("end");
    }
    
    /**
     * Genera archivo XML en ruta de upload
     * 
     * @param  xmlContent
     * @param  filePathName
     * @param  timezoneId
     * @throws ScopixException 
     */
    private void generateXMLFile(String xmlContent, String filePathName, String timezoneId) throws ScopixException {
        log.info("start, filePathName: [" + filePathName + "], timezoneId: [" + timezoneId + "]");
		InputStream xmlInputStream = null;

		try {
			DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
			//inicia documento XML scopix
			Document newScopixXMLDocument = docBuilder.newDocument();
            Element root = newScopixXMLDocument.createElement("root");
            newScopixXMLDocument.appendChild(root);
			//inicia procesamiento XML cámara
			Document cameraCountingXMLDoc = docBuilder.parse(new InputSource(new ByteArrayInputStream(xmlContent.getBytes())));
			cameraCountingXMLDoc.getDocumentElement().normalize();

			//procesa estructura XML retornada por la cámara
			processCameraXML(newScopixXMLDocument, cameraCountingXMLDoc, root, timezoneId);

			//escribe documento XML formato scopix
            DOMSource source = new DOMSource(newScopixXMLDocument);
            StringWriter xmlAsWriter = new StringWriter();
            StreamResult result = new StreamResult(xmlAsWriter);
            TransformerFactory.newInstance().newTransformer().transform(source, result);
            xmlInputStream = new ByteArrayInputStream(xmlAsWriter.toString().getBytes());
            
            byte[] buffer = new byte[65536];
            FileOutputStream xmlOutputStream = new FileOutputStream(filePathName);
            int bytesRead = 1;
            while (bytesRead > 0) {
                bytesRead = xmlInputStream.read(buffer, 0, 65536);
                if (bytesRead > 0) {
                    xmlOutputStream.write(buffer, 0, bytesRead);
                }
            }
            xmlOutputStream.close();
			
		} catch (ParserConfigurationException e) {
			throw new ScopixException(e.getMessage(), e);
		} catch (SAXException e) {
			throw new ScopixException(e.getMessage(), e);
		} catch (IOException e) {
			throw new ScopixException(e.getMessage(), e);
		} catch (ParseException e) {
			throw new ScopixException(e.getMessage(), e);
		} catch (TransformerConfigurationException e) {
			throw new ScopixException(e.getMessage(), e);
		} catch (TransformerException e) {
			throw new ScopixException(e.getMessage(), e);
		} catch (TransformerFactoryConfigurationError e) {
			throw new ScopixException(e.getMessage(), e);
		} finally {
            try {
                if(xmlInputStream!=null){
                    xmlInputStream.close();
                }
            } catch (IOException ex) {
                log.warn("error cerrando xmlInputStream: [" + ex.getMessage() + "]");
            }
        }
        log.info("end");
    }
    
    /**
     * Procesa estructura XML retornada por la cámara
     * 
     * @param  newScopixXMLDocument
     * @param  cameraCountingXMLDoc
     * @param  root
     * @param  timezoneId
     * @throws ParseException
     */
	@SuppressWarnings("rawtypes")
	private void processCameraXML(Document newScopixXMLDocument, 
    		Document cameraCountingXMLDoc, Element root, String timezoneId) throws ParseException {
    	
    	log.info("start");
    	double diff = TimeZoneUtils.getDiffInHoursTimeZone("GMT0", timezoneId);
    	SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy", Locale.ENGLISH);
    	
    	NodeList nodes = cameraCountingXMLDoc.getElementsByTagName("lines");
    	HashMap<String, VadaroData> hmVadaroLine = new HashMap<String, VadaroData>();
    	
		for (int i = 0; i < nodes.getLength(); i++) {
		    Node linesNode = nodes.item(i);
		    if (linesNode.getNodeType() == Node.ELEMENT_NODE) {
		        Element linesElement = (Element) linesNode;
		        
		        NodeList elementNodes = linesElement.getElementsByTagName("line");
		        for (int j = 0; j < elementNodes.getLength(); j++) {
                    Node lineNode = elementNodes.item(j);
                    NamedNodeMap attributes = lineNode.getAttributes();
                    
                    Node inNode = attributes.getNamedItem("in");
                    Node outNode = attributes.getNamedItem("out");
                    
                    Node timeNode = attributes.getNamedItem("time");
                    String lineTime = timeNode.getNodeValue();
                    
                    int inValue  = Integer.parseInt(inNode.getNodeValue());
                    int outValue = Integer.parseInt(outNode.getNodeValue());
                    
                    VadaroData vadaroData = hmVadaroLine.get(lineTime);
                    if(vadaroData==null){
                    	vadaroData = new VadaroData();
                    	vadaroData.setIn(inValue);
                    	vadaroData.setOut(outValue);
                    }else{
                    	int oldInValue  = vadaroData.getIn();
                    	int oldOutValue = vadaroData.getOut();
                    	
                    	if(inValue > oldInValue){ //la entrada actual es mayor a la anterior
                    		vadaroData.setIn(inValue);
                    	}
                    	if(outValue > oldOutValue){ //la salida actual es mayor a la anterior
                    		vadaroData.setOut(outValue);
                    	}
                    }
                    hmVadaroLine.put(lineTime, vadaroData);
		        }//fin for nodos line
		    }
		}//fin for nodo lines

		if(!hmVadaroLine.isEmpty()){
			List<VadaroData> lstVadaroData = new ArrayList<>();
			
			for (Map.Entry e : hmVadaroLine.entrySet()) {
	            String lineTime = e.getKey().toString();
	            VadaroData vadaroData = hmVadaroLine.get(lineTime);
	            
	            //convierte a zona horaria del store
	            Date formattedDate = dateFormat.parse(lineTime);
	            formattedDate = DateUtils.addHours(formattedDate, (int) diff);
	            vadaroData.setFormattedDate(formattedDate);

	            lstVadaroData.add(vadaroData);
	        }
			
			Collections.sort(lstVadaroData); //ordena por fecha
			
            //Add seconds in xml generation 
			for (VadaroData vadData : lstVadaroData) {
	            String strDate = DateFormatUtils.format(vadData.getFormattedDate(), "yyyy-MM-dd HH:mm:ss");
	            
	            Element childNode = newScopixXMLDocument.createElement("value");
	            childNode.setAttribute("date", strDate);
	            childNode.setAttribute("valuein", vadData.getIn().toString());
	            childNode.setAttribute("valueout", vadData.getOut().toString());
	            root.appendChild(childNode);
			}			
		}
		log.info("end");
    }

    /**
     * @return the uploadLocalDir
     */
    public String getUploadLocalDir() {
        return uploadLocalDir;
    }
}