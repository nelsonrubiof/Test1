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
 * ReadUrlPHPXmlExtractionCommand.java
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
import com.scopix.periscope.extractionmanagement.CognimaticsPeopleCounter212ExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.ReadUrlPHPEvidenceProvider;
import com.scopix.periscope.extractionmanagement.ReadUrlPHPXmlExtractionRequest;
import com.scopix.periscope.extractionmanagement.commands.parser.driver212.CntGroupXmlDefinition212;
import com.scopix.periscope.extractionmanagement.commands.parser.driver212.CntSetXmlDefinition212;
import com.scopix.periscope.extractionmanagement.commands.parser.driver212.CntXmlDefinition212;
import com.scopix.periscope.extractionmanagement.commands.parser.driver212.CognimaticsPeopleCounter212XmlDefinition;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.http.HttpSupport;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.TimeZoneUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.schedulermanagement.SchedulerManager;

/**
 *
 * @author Nelson Rubio
 * @version 1.0.0
 */
@SpringBean
public class ReadUrlPHPXmlExtractionCommand implements
        ProviderAdaptor<ReadUrlPHPXmlExtractionRequest, ReadUrlPHPEvidenceProvider> {

    private Date date;
    private static Logger log = Logger.getLogger(ReadUrlPHPXmlExtractionCommand.class);
    private SchedulerManager schedulerManager;

    public ReadUrlPHPXmlExtractionCommand() {
    }

    public void execute(ReadUrlPHPXmlExtractionRequest pcER, ReadUrlPHPEvidenceProvider evProv, Date date) 
            throws ScopixException {
        this.date = date;
        prepareEvidence(pcER, evProv);
    }

    @Override
    public void prepareEvidence(ReadUrlPHPXmlExtractionRequest evidenceRequest, ReadUrlPHPEvidenceProvider evidenceProvider)
            throws ScopixException {
        log.info("start");
        try {

            Calendar dateEjecution = Calendar.getInstance();
            if (date != null) {
                dateEjecution.setTime(date);
            }
            //volvemos a la hora original de la evidencia
            dateEjecution.add(Calendar.MINUTE, -CognimaticsPeopleCounter212ExtractionRequest.DELAY);

            // se utiliza creationTimestamp cuando est� presente para solicitar evidencia del d�a que corresponde
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

            String range = DateFormatUtils.format(DateUtils.addDays(new Date(evidenceDate.getTimeInMillis()), -7), "yyyyMMdd")
                    + "-" + DateFormatUtils.format(evidenceDate, "yyyyMMdd");

            // the name of the clip must be in local time
            String name = DateFormatUtils.format(evidenceDate.getTime(), "yyyyMMdd");
            name = name + "_" + evidenceRequest.getId();

            String serverIP = evidenceProvider.getIpAddress();
            String user = evidenceProvider.getUserName();
            String pass = evidenceProvider.getPassword();
            String protocol = evidenceProvider.getProtocol();
            String port = evidenceProvider.getPort();

            //Se modifica por uso de recursos, el xml almacenado por al camara es muy extenso
//            String url = protocol + "://" + serverIP + ":" + port + "/local/people-counter/.api?export-xml&date=all&res=15m";
            //se cambia string base de url por 
            String query = evidenceProvider.getQuery() + "/local/people-counter/.api?export-xml&res=15m&date=" + range;
            String url = protocol + "://" + serverIP + ":" + port + "/read/read_url.php?"
                    + "user=" + user
                    + "&pwd=" + pass
                    + "&type=XML"
                    + "&query=" + query;
            //String url = protocol + "://" + serverIP + ":" + port + 
            //"/local/people-counter/.api?export-xml&&res=15m&&date=" + range + "";

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

            callXML(url, user, pass, name, evidenceRequest);
        } catch (Exception e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end");

    }

    private void callXML(String urlString, String user, String password, String fileName,
            ReadUrlPHPXmlExtractionRequest evidenceRequest) throws ScopixException {

    	log.debug("callPeopleCounting().urlString: " + urlString);
        CloseableHttpResponse response =  null;
        HttpSupport httpSupport;
		try {
			httpSupport = HttpSupport.getInstance();
		} catch (HttpClientInitializationException e) {
			 throw new ScopixException("Cannot initialize Http Support.", e);
		}

        try {
            this.allowCertificatesHTTPSConnections();
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
                //Se modifica por cambio de parserXml
                //Map<String, Map<String, String>> data = this.readXmlFile(conn.getInputStream());
                Map<String, Map<String, Integer>> data = this.readXmlFile(response.getEntity().getContent(), evidenceRequest);

                //Create the new xml with different format
                InputStream newIs = this.createNewXML(data);

                CognimaticsPeopleCounter212FileReadyCommand cognimaticsPeopleCounter212FileReadyCommand =
                        new CognimaticsPeopleCounter212FileReadyCommand();
                cognimaticsPeopleCounter212FileReadyCommand.execute(fileName, newIs);
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

    private CognimaticsPeopleCounter212XmlDefinition parserInputStream(InputStream is) throws ScopixException {
        try {
            /**
             * parseamos el is para el xml de la camara
             */
            Digester dg = new Digester();
            dg.setValidating(false);
            //debido a que el xml tiene un dtd de definicion el cual no se tiene acceso directo ya qye no es una url
            dg.setEntityResolver(new LocalDTDEntityResolver("dtd/appdata1_41.dtd"));
            dg.push(new CognimaticsPeopleCounter212XmlDefinition());
            dg.addObjectCreate("*/cntset", CntSetXmlDefinition212.class);
            dg.addSetProperties("*/cntset");
            dg.addSetNestedProperties("*/cntset", "cntgroup", null);
            dg.addSetNext("*/cntset", "addCntset");
            dg.addObjectCreate("*/cntset/cntgroup", CntGroupXmlDefinition212.class);
            dg.addSetProperties("*/cntset/cntgroup");
            dg.addSetNestedProperties("*/cntset/cntgroup", "cnt", null);
            dg.addSetNext("*/cntset/cntgroup", "addCntGroup");
            dg.addObjectCreate("*/cntset/cntgroup/cnt", CntXmlDefinition212.class);
            dg.addSetProperties("*/cntset/cntgroup/cnt");
            dg.addCallMethod("*/cntset/cntgroup/cnt", "setValue", 0, new Class[]{Integer.class});
            dg.addSetNestedProperties("*/cntset/cntgroup/cnt");
            dg.addSetNext("*/cntset/cntgroup/cnt", "addCnt");
            CognimaticsPeopleCounter212XmlDefinition cpcxd = (CognimaticsPeopleCounter212XmlDefinition) dg.parse(is);
            return cpcxd;
        } catch (IOException e) {
            log.error("error recuperando inputStream " + e, e);
            throw new ScopixException(e);
        } catch (SAXException e) {
            log.error("error parseando xml de camara " + e, e);
            throw new ScopixException(e);
        }
    }

    public Map<String, Map<String, Integer>> readXmlFile(InputStream is,
            ReadUrlPHPXmlExtractionRequest evidenceRequest) throws ScopixException {
        log.debug("readPeopleCountingFile");
        Map<String, Map<String, Integer>> ret = new LinkedHashMap<String, Map<String, Integer>>();

        CognimaticsPeopleCounter212XmlDefinition cpcxd = parserInputStream(is);
        LinkedHashMap<String, Boolean> cols = new LinkedHashMap<String, Boolean>();
        cols.put("starttime", Boolean.FALSE);
        SortUtil.sortByColumn(cols, cpcxd.getCntset());

        Calendar cal = Calendar.getInstance();
        Calendar initCarga = Calendar.getInstance();
        boolean existDiff = false;

        //if (getSchedulerManager().getTimeZoneId() != null && getSchedulerManager().getTimeZoneId().length() > 0) {
        if (evidenceRequest.getExtractionPlan().getTimeZoneId() != null
                && evidenceRequest.getExtractionPlan().getTimeZoneId().length() > 0) {
            existDiff = true;
        }
        initCarga.add(Calendar.DATE, -7);
        for (CntSetXmlDefinition212 cntset : cpcxd.getCntset()) {
            //ordenamos el grupo por endTime
            LinkedHashMap<String, Boolean> cols2 = new LinkedHashMap<String, Boolean>();
            cols2.put("endtime", Boolean.FALSE);
            SortUtil.sortByColumn(cols2, cntset.getCntGroups());

            for (CntGroupXmlDefinition212 cntgroup : cntset.getCntGroups()) {
                //aumentamos el delta al principio ya que el xml parseado viene con los calculos de 0-15 antes de ser generado
                long endTime = (cntgroup.getEndtime() * 1000);
                //si existe diferencia horaria se debe aplicar en este punto para que la hora corresponda a la del cliente
                //y no a la del server
                cal.setTimeInMillis(endTime);

                if (existDiff) {                    
                    double d = TimeZoneUtils.getDiffInHoursTimeZone(evidenceRequest.getExtractionPlan().getTimeZoneId());
                    Date newDateOfDiffHours = new Date(cal.getTimeInMillis());
                    newDateOfDiffHours = DateUtils.addHours(newDateOfDiffHours, (int) d * -1);
                    cal.setTime(newDateOfDiffHours);
                }
                Integer valueIn = 0;
                Integer valueOut = 0;

                for (CntXmlDefinition212 cnt : cntgroup.getCnts()) {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        log.debug("_createNewXML");
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

    public SchedulerManager getSchedulerManager() {
        if (schedulerManager == null) {
            schedulerManager = SpringSupport.getInstance().findBeanByClassName(SchedulerManager.class);
        }
        return schedulerManager;
    }

    public void setSchedulerManager(SchedulerManager schedulerManager) {
        this.schedulerManager = schedulerManager;
    }

    private void configureDate(Calendar dia, ReadUrlPHPXmlExtractionRequest evidenceRequest) throws ParseException {
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
