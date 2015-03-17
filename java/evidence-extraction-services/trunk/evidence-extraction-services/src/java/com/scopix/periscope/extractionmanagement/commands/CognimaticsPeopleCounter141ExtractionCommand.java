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
 * CognimaticsPeopleCounter141ExtractionCommand.java
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
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TimeZone;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.digester.Digester;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.exception.HttpClientInitializationException;
import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter141EvidenceProvider;
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter141ExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.commands.parser.CntGroupXmlDefinition;
import com.scopix.periscope.extractionmanagement.commands.parser.CntSetXmlDefinition;
import com.scopix.periscope.extractionmanagement.commands.parser.CntXmlDefinition;
import com.scopix.periscope.extractionmanagement.commands.parser.CognimaticsPeopleCounter141XmlDefinition;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.http.HttpSupport;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author Cesar Abarza S.
 */
@SpringBean
public class CognimaticsPeopleCounter141ExtractionCommand implements
        ProviderAdaptor<CognimaticsPeopleCounter141ExtractionRequest, CognimaticsPeopleCounter141EvidenceProvider> {

    private Date date;
    private static Logger log = Logger.getLogger(CognimaticsPeopleCounter141ExtractionCommand.class);

    public CognimaticsPeopleCounter141ExtractionCommand() {
    }

    public void execute(CognimaticsPeopleCounter141ExtractionRequest pcER, CognimaticsPeopleCounter141EvidenceProvider evProv,
            Date date) throws
            ScopixException {
        this.date = date;
        prepareEvidence(pcER, evProv);
    }

    @Override
    public void prepareEvidence(CognimaticsPeopleCounter141ExtractionRequest evidenceRequest,
            CognimaticsPeopleCounter141EvidenceProvider evidenceProvider)
            throws ScopixException {
        log.info("start");
        try {
            Calendar dateEjecution = Calendar.getInstance();
            if (date != null) {
                dateEjecution.setTime(date);
            }

            //volvemos a la hora original de la evidencia
            dateEjecution.add(Calendar.MINUTE, -CognimaticsPeopleCounter141ExtractionRequest.DELAY);

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

            // the name of the clip must be in local time
            String name = DateFormatUtils.format(evidenceDate.getTime(), "yyyyMMdd");
            name = name + "_" + evidenceRequest.getId();

            String serverIP = evidenceProvider.getIpAddress();
            String user = evidenceProvider.getUserName();
            String pass = evidenceProvider.getPassword();
            String protocol = evidenceProvider.getProtocol();
            String port = evidenceProvider.getPort();

            String url = protocol + "://" + serverIP + ":" + port + "/local/getxml.cgi?day=all&res=15m";

            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.findEvidenceFileByFileName(name + ".xml");
            //Este evidence file es creado aunque no se genere el archivo fisico
            if (evidenceFile != null) {
                evidenceFile.setFileCreationDate(new Date());
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            } else {
                evidenceFile = new EvidenceFile();
                evidenceFile.setEvidenceDate(new Date(evidenceDate.getTimeInMillis()));
                evidenceFile.setEvidenceExtractionRequest(evidenceRequest);
                evidenceFile.setFilename(name + ".xml");
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            }

            callPeopleCounting(url, user, pass, name);
        } catch (Exception e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end");

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
            long contentlength = 0;

            if (response != null && response.getEntity() != null){
            	contentlength = response.getEntity().getContentLength();
            	
            	//Read The xml document
                Map<String, Map<String, Integer>> data = this.readPeopleCountingFile(response.getEntity().getContent());

                //Create the new xml with different format
                InputStream newIs = this.createNewXML(data);

            	
                CognimaticsPeopleCounter141FileReadyCommand cognimaticsPeopleCounter141FileReadyCommand =
                        new CognimaticsPeopleCounter141FileReadyCommand();
                cognimaticsPeopleCounter141FileReadyCommand.execute(fileName, newIs);
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
        log.info("end");
    }

    private CognimaticsPeopleCounter141XmlDefinition parserInputStream(InputStream is) throws ScopixException {
        log.info("start");
        CognimaticsPeopleCounter141XmlDefinition cpcxd = null;
        try {
            /**
             * parseamos el is para el xml de la camara
             */
            Digester dg = new Digester();
            dg.setValidating(false);
            //debido a que el xml tiene un dtd de definicion el cual no se tiene acceso directo ya qye no es una url
            dg.setEntityResolver(new LocalDTDEntityResolver("dtd/appdata1_41.dtd"));
            dg.push(new CognimaticsPeopleCounter141XmlDefinition());
            dg.addObjectCreate("*/cntset", CntSetXmlDefinition.class);
            dg.addSetProperties("*/cntset");
            dg.addSetNestedProperties("*/cntset", "cntgroup", null);
            dg.addSetNext("*/cntset", "addCntset");
            dg.addObjectCreate("*/cntset/cntgroup", CntGroupXmlDefinition.class);
            dg.addSetProperties("*/cntset/cntgroup");
            dg.addSetNestedProperties("*/cntset/cntgroup", "cnt", null);
            dg.addSetNext("*/cntset/cntgroup", "addCntGroup");
            dg.addObjectCreate("*/cntset/cntgroup/cnt", CntXmlDefinition.class);
            dg.addSetProperties("*/cntset/cntgroup/cnt");
            dg.addCallMethod("*/cntset/cntgroup/cnt", "setValue", 0, new Class[]{Integer.class});
            dg.addSetNestedProperties("*/cntset/cntgroup/cnt");
            dg.addSetNext("*/cntset/cntgroup/cnt", "addCnt");
            cpcxd = (CognimaticsPeopleCounter141XmlDefinition) dg.parse(is);

        } catch (IOException e) {
            log.error("error recuperando inputStream " + e, e);
            throw new ScopixException(e);
        } catch (SAXException e) {
            log.error("error parseando xml de camara " + e, e);
            throw new ScopixException(e);
        }
        log.info("end");
        return cpcxd;
    }

    private Map<String, Map<String, Integer>> readPeopleCountingFile(InputStream is) throws ScopixException {
        log.debug("readPeopleCountingFile");
        Map<String, Map<String, Integer>> ret = new LinkedHashMap<String, Map<String, Integer>>();

        CognimaticsPeopleCounter141XmlDefinition cpcxd = parserInputStream(is);

        //ordenamos por starttime
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("starttime", Boolean.FALSE);
        SortUtil.sortByColumn(cols, cpcxd.getCntset());

        long dateTime = 0;
        Calendar cal = Calendar.getInstance();
        Calendar initCarga = Calendar.getInstance();
        initCarga.add(Calendar.DATE, -7);
        for (CntSetXmlDefinition cntset : cpcxd.getCntset()) {
            log.debug("First Pass, datetime = " + cntset.getStarttime());
            dateTime = (cntset.getStarttime() * 1000) - (cal.get(Calendar.DST_OFFSET) + cal.get(Calendar.ZONE_OFFSET));
            cal.setTimeInMillis(dateTime);
            log.debug("cal = " + cal.getTime());
            for (CntGroupXmlDefinition cntgroup : cntset.getCntGroups()) {
                //aumentamos el delta al principio ya que el xml parseado viene con los calculos de 0-15 antes de ser generado
                cal.add(Calendar.SECOND, cntset.getDelta());
                Integer valueIn = 0;
                Integer valueOut = 0;
                for (CntXmlDefinition cnt : cntgroup.getCnts()) {
                    switch (cnt.getTypeid()) {
                        case 3: //IN
                            valueIn = cnt.getValue();
                            break;
                        case 4: //OUT
                            valueOut = cnt.getValue();
                            break;
                        default:
                            break;
                    }
                }
                //solo se agregaran resultados para fechas mayores que 1 semana atras
                if (initCarga.getTime().before(cal.getTime())) {
                    Map<String, Integer> dato = new HashMap<String, Integer>();
                    String dateKey = DateFormatUtils.format(cal.getTime(), "yyyy-MM-dd HH:mm");
                    if (ret.containsKey(dateKey)) {
                        Map<String, Integer> aux = ret.get(dateKey);
                        valueIn = valueIn + aux.get("valueIn");
                        valueOut = valueOut + aux.get("valueOut");
                    }
                    dato.put("valueIn", valueIn);
                    dato.put("valueOut", valueOut);
                    ret.put(dateKey, (dato));
                }
            }
        }

        return ret;
    }

    private InputStream createNewXML(Map<String, Map<String, Integer>> data) {
        log.info("start"); //= " + data
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
                Map<String, Integer> dato = data.get(key);
                childNode.setAttribute("date", key);
                childNode.setAttribute("valuein", dato.get("valueIn").toString());
                childNode.setAttribute("valueout", dato.get("valueOut").toString());
                root.appendChild(childNode);
            }
            DOMSource source = new DOMSource(docNew);
            StringWriter xmlAsWriter = new StringWriter();
            StreamResult result = new StreamResult(xmlAsWriter);
            TransformerFactory.newInstance().newTransformer().transform(source, result);
            is = new ByteArrayInputStream(xmlAsWriter.toString().getBytes());
        } catch (Exception e) {
            //ex.printStackTrace();
            log.error("Error creando xml" + e, e);
        }
        log.info("end");
        return is;
    }

    private void allowCertificatesHTTPSConnections() {
        try {
            TrustManager[] trustAllCerts = new TrustManager[]{
                new X509TrustManager() {
                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }

                    public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }

                    public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                    }
                }};
            // Install the all-trusting trust manager
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            };

            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
        } catch (Exception e) {
            log.error("Error when try to set the defaults certificates handlers", e);
        }
    }

    private void configureDate(Calendar dia, CognimaticsPeopleCounter141ExtractionRequest evidenceRequest) throws ParseException {
        dia.set(Calendar.HOUR_OF_DAY, 0);
        dia.set(Calendar.MINUTE, 0);
        dia.set(Calendar.SECOND, 0);
        dia.set(Calendar.MILLISECOND, 0);
        log.debug("dia " + dia.getTime());
        Date requestedTime = DateUtils.parseDate(
                DateFormatUtils.format(evidenceRequest.getRequestedTime(), "HH:mm:ss"),
                new String[]{"HH:mm:ss"});
        Calendar hora = Calendar.getInstance();
        hora.setTime(requestedTime);
        log.debug("hora " + hora.getTime());
        dia.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY));
        dia.set(Calendar.MINUTE, hora.get(Calendar.MINUTE));
        dia.set(Calendar.SECOND, hora.get(Calendar.SECOND));
    }
}
