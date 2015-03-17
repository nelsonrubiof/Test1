/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * PeopleCountingExtractionCommand.java
 *
 * Created on 22-05-2008, 06:01:14 PM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.exception.HttpClientInitializationException;
import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.PeopleCountingEvidenceProvider;
import com.scopix.periscope.extractionmanagement.PeopleCountingExtractionRequest;
import com.scopix.periscope.http.HttpSupport;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;

/**
 *
 * @author Cesar Abarza S.
 */
@SpringBean
public class PeopleCountingExtractionCommand implements
        ProviderAdaptor<PeopleCountingExtractionRequest, PeopleCountingEvidenceProvider> {

    private Date date;
    private static Logger log = Logger.getLogger(PeopleCountingExtractionCommand.class);

    public PeopleCountingExtractionCommand() {
    }

    public void execute(PeopleCountingExtractionRequest pcER, PeopleCountingEvidenceProvider evProv, Date date) throws
            ScopixException {
        this.date = date;
        prepareEvidence(pcER, evProv);
    }

    @Override
    public void prepareEvidence(PeopleCountingExtractionRequest evidenceRequest, PeopleCountingEvidenceProvider evidenceProvider)
            throws ScopixException {
        log.info("start");
        try {            
            Calendar dateEjecution = Calendar.getInstance();
            if (date != null) {
                dateEjecution.setTime(date);
            }
            //volvemos a la hora original de la evidencia
            dateEjecution.add(Calendar.MINUTE, -PeopleCountingExtractionRequest.DELAY);
            // se utiliza creationTimestamp cuando está presente para solicitar evidencia del día que corresponde
            // en evidencia Auto_Generada
            if (EvidenceRequestType.AUTO_GENERATED.equals(evidenceRequest.getType())
                    && evidenceRequest.getCreationTimestamp() != null) {
                dateEjecution.setTime(evidenceRequest.getEvidenceDate()); //getCreationTimestamp()
            }

            Calendar evidenceDate = Calendar.getInstance();
            evidenceDate.setTimeInMillis(dateEjecution.getTimeInMillis());
            if (evidenceRequest.getExtractionPlan().getTimeZoneId() != null
                    && evidenceRequest.getExtractionPlan().getTimeZoneId().length() > 0) {
                dateEjecution.setTimeZone(TimeZone.getTimeZone(evidenceRequest.getExtractionPlan().getTimeZoneId()));

                double d = TimeZoneUtils.getDiffInHoursTimeZone(evidenceRequest.getExtractionPlan().getTimeZoneId());
                Date newDateOfDiffHours = new Date(dateEjecution.getTimeInMillis());
                newDateOfDiffHours = DateUtils.addHours(newDateOfDiffHours, (int) d * -1);
                evidenceDate.setTimeInMillis(newDateOfDiffHours.getTime());
                configureDate(evidenceDate, evidenceRequest);
            }

            String name = DateFormatUtils.format(evidenceDate.getTime(), "yyyyMMdd");
            name = name + "_" + evidenceRequest.getId();
            String serverIP = evidenceProvider.getIpAddress();
            String user = evidenceProvider.getUserName();
            String pass = evidenceProvider.getPassword();

            String url = "http://" + serverIP + "/local/tvpcWebGui/appdata.xml";

            EvidenceFile evidenceFile = new EvidenceFile();
            evidenceFile.setEvidenceDate(new Date(evidenceDate.getTimeInMillis()));
            evidenceFile.setEvidenceExtractionRequest(evidenceRequest);
            evidenceFile.setFilename(name + ".xml");
            HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);

            callPeopleCounting(url, user, pass, name);
        } catch (Exception e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.debug("_prepareEvidence()");

    }

    private void callPeopleCounting(String urlString, String user, String password, String fileName) throws ScopixException {
        log.debug("callPeopleCounting().urlString: " + urlString);
        CloseableHttpResponse response =  null;
        HttpSupport httpSupport;
		try {
			httpSupport = HttpSupport.getInstance();
		} catch (HttpClientInitializationException e) {
			 throw new ScopixException("Cannot initialize Http Support.", e);
		}

        try {
            //this.allowCertificatesHTTPSConnections();
            HashMap<String, String> requestHeaders = new HashMap<String, String>();
            
            if (!user.equals("NOT_USED") && !password.equals("NOT_USED")) {
                String encoding = new sun.misc.BASE64Encoder().encode((user + ":" + password).getBytes());
                
                requestHeaders.put("Authorization", "Basic " + encoding);
            }
            response = httpSupport.httpGet(urlString, requestHeaders);

            PeopleCountingFileReadyCommand command = new PeopleCountingFileReadyCommand();
            long contentlength = 0;
            if (response != null && response.getEntity() != null){
            	contentlength = response.getEntity().getContentLength();
            	command.execute(fileName, response.getEntity().getContent());
            }

            log.debug("contentlength: " + contentlength);
        } catch (IOException e) {
            throw new ScopixException("Cannot create image.", e);
		} catch (HttpGetException e) {
			throw new ScopixException("Error fetching the image.", e);
		}finally{
			if (response != null){
				 httpSupport.closeHttpEntity(response.getEntity());
			     httpSupport.closeHttpResponse(response);
			}
		}
        log.debug("_callPeopleCounting()");
    
    }

    private Map<String, Map<String, String>> readPeopleCountingFile(InputStream is) throws ScopixException {
        log.info("start");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Map<String, Map<String, String>> data = new LinkedHashMap<String, Map<String, String>>();
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();

            docBuilderFactory.setValidating(true);
            docBuilderFactory.setNamespaceAware(true);
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            docBuilder.setEntityResolver(new LocalDTDEntityResolver("dtd/appdata.dtd"));
            Document doc = docBuilder.parse(is);
            doc.getDocumentElement().normalize();

            boolean firstPass = true;

            NodeList countList = doc.getElementsByTagName("cntset");
            int totalPeriods = countList.getLength();
            log.debug("totalPeriods= " + totalPeriods);
            long dateTime = 0;
            Calendar cal = Calendar.getInstance();
            for (int s = 0; s < totalPeriods; s++) {
                Node node = countList.item(s);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;
                    if (firstPass) {
                        log.debug("First Pass, datetime = " + element.getAttribute("starttime"));
                        dateTime = ((Long.parseLong(element.getAttribute("starttime"))) * 1000) - (cal.get(Calendar.DST_OFFSET)
                                + cal.get(Calendar.ZONE_OFFSET));
                        firstPass = false;
                        cal.setTimeInMillis(dateTime);
                    }
                    int interval = Integer.parseInt(element.getAttribute("delta"));
                    NodeList nodeList = element.getElementsByTagName("cnt");
                    log.debug("Period = " + (s + 1) + " modeList.length = " + nodeList.getLength());
                    for (int i = 0; i < nodeList.getLength(); i++) {
                        cal.add(Calendar.SECOND, interval);
                        Element elem = (Element) nodeList.item(i);
                        String valueIn = elem.getAttribute("valuein");
                        String valueOut = elem.getAttribute("valueout");

                        Map<String, String> dato = new HashMap<String, String>();
                        String dateKey = sdf.format(cal.getTime());

                        if (data.containsKey(dateKey)) {
                            Map<String, String> aux = data.get(dateKey);
                            valueIn = String.valueOf((Integer.parseInt(valueIn)) + (Integer.parseInt(aux.get("valueIn"))));
                            valueOut = String.valueOf((Integer.parseInt(valueOut)) + (Integer.parseInt(aux.get("valueOut"))));
                        }
                        dato.put("valueIn", valueIn);
                        dato.put("valueOut", valueOut);
                        data.put(dateKey, (dato));

                    }
                }
            }

        } catch (ParserConfigurationException e) {
            throw new ScopixException(e);
        } catch (SAXException e) {
            throw new ScopixException(e);
        } catch (IOException e) {
            throw new ScopixException(e);
        } catch (NumberFormatException e) {
            throw new ScopixException(e);
        } finally {
            try {
                is.close();
            } catch (IOException ex) {
                log.error(ex);
            }
        }
        log.info("end");
        return data;
    }

    private InputStream createNewXML(Map<String, Map<String, String>> data) {
        log.debug("createNewXML data = " + data);
        InputStream is = null;
        try {
            //Create new Document XML
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilderNew = docBuilderFactory.newDocumentBuilder();
            Document docNew = docBuilderNew.newDocument();
            Element root = docNew.createElement("root");
            docNew.appendChild(root);
            for (String key : data.keySet()) {
                Element childNode = docNew.createElement("value");
                Map<String, String> dato = data.get(key);
                childNode.setAttribute("date", key);
                childNode.setAttribute("valuein", dato.get("valueIn"));
                childNode.setAttribute("valueout", dato.get("valueOut"));
                root.appendChild(childNode);
            }
            DOMSource source = new DOMSource(docNew);
            StringWriter xmlAsWriter = new StringWriter();
            StreamResult result = new StreamResult(xmlAsWriter);
            TransformerFactory.newInstance().newTransformer().transform(source, result);
            is = new ByteArrayInputStream(xmlAsWriter.toString().getBytes());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        log.debug("_createNewXML");
        return is;
    }

    private void configureDate(Calendar dia, PeopleCountingExtractionRequest evidenceRequest) throws ParseException {
        dia.set(Calendar.HOUR_OF_DAY, 0);
        dia.set(Calendar.MINUTE, 0);
        dia.set(Calendar.SECOND, 0);
        dia.set(Calendar.MILLISECOND, 0);
        Date requestedTime = DateUtils.parseDate(
                DateFormatUtils.format(evidenceRequest.getRequestedTime(), "HH:mm:ss"),
                new String[]{"HH:mm:ss"});
        Calendar hora = Calendar.getInstance();
        hora.setTime(requestedTime);
        dia.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
        dia.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));
        dia.set(Calendar.SECOND, hora.get(Calendar.SECOND));
    }
}
