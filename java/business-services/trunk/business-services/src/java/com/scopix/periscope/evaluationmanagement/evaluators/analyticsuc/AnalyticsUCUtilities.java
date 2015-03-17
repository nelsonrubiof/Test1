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
 *  AnalyticsUCUtilities.java
 * 
 *  Created on 09-03-2012, 04:15:07 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.evaluators.analyticsuc;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.digester.Digester;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

/**
 *
 * @author nelson
 */
@SpringBean(rootClass = AnalyticsUCUtilities.class)
public class AnalyticsUCUtilities {

    private static Logger log = Logger.getLogger(AnalyticsUCUtilities.class);
    private HttpClient httpClient;
    private IdleConnectionEvictor connEvictor;

    public List<PeopleCountingAnaliticsUCDetection> parseXmlUC(String xml) {
        log.info("start");
        List<PeopleCountingAnaliticsUCDetection> detections = null;
        try {
            Digester dg = new Digester();
            dg.setValidating(false);
            dg.push(new ArrayList<PeopleCountingAnaliticsUCDetection>());
            dg.addObjectCreate("*/detection", PeopleCountingAnaliticsUCDetection.class);
            dg.addSetNestedProperties("*/detection",
                    new String[]{"max_response", "num_responses", "average_response"},
                    new String[]{"maxResponse", "numResponses", "averageResponse"});
            dg.addSetProperties("*/detection");
            dg.addSetNext("*/detection", "add");
            detections = (List<PeopleCountingAnaliticsUCDetection>) dg.parse(new StringReader(xml));

        } catch (IOException e) {
            log.error("IOException " + e, e);
        } catch (SAXException e) {
            log.error("SAXException " + e, e);
        }
        log.info("end");
        return detections;
    }

    /**
     * retorna un file temporal generado a partir de la ruta
     *
     * @param urlStr
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public File readFile(String urlStr) throws ScopixException {        
        log.info("start " + urlStr);
        HttpGet get = new HttpGet(urlStr);
        File tmp;
        try {

            HttpResponse response = getHttpClient().execute(get);
            HttpEntity resEntity = response.getEntity();
            
            //creamos el file temportal donde recibiremos el reponse
            tmp = File.createTempFile("tempUCFile", ".jpg");
            OutputStream out = new FileOutputStream(tmp);

            byte[] buf = new byte[1];
            int len;
            while ((len = resEntity.getContent().read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            out.close();
            EntityUtils.consume(resEntity);
        } catch (IOException e) {
            log.error("Error en peticion getFile " + e);
            tmp = null;
        } finally {
            get.releaseConnection();
        }
        log.info("end");
        return tmp;
    }

    public void sendUrl(String url) throws ScopixException {
        log.info("start url:" + url);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = getHttpClient().execute(get);
            HttpEntity resEntity = response.getEntity();

            byte[] buf = new byte[1];
            int len;
            while ((len = resEntity.getContent().read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            EntityUtils.consume(resEntity);
        } catch (IOException e) {
            throw new ScopixException(e);
        } finally {
            get.releaseConnection();
        }
        log.info("end response:" + out.toString());
    }

    public String readUrl(String url) throws ScopixException {
        log.info("start url:" + url);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        HttpGet get = new HttpGet(url);
        try {
            HttpResponse response = getHttpClient().execute(get);
            HttpEntity resEntity = response.getEntity();

            byte[] buf = new byte[1];
            int len;
            while ((len = resEntity.getContent().read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            EntityUtils.consume(resEntity);            
        } catch (IOException e) {
            throw new ScopixException(e);
        } finally {
            get.releaseConnection();
        }
        log.info("end response:" + out.toString());
        return out.toString();
    }

    public synchronized String genUniqueFile(String absolutePath, String fileName, String extension, Integer iCurrent) {
        Integer current = iCurrent == null ? 1 : iCurrent + 1;
        File filename = new File(absolutePath, fileName + "_" + current + "." + extension);

        if (filename.exists()) {
            return genUniqueFile(absolutePath, fileName, extension, current);
        } else {
            return filename.getName();
        }
    }

    /**
     * <pre>
     * &#60;?xml version="1.0" encoding="UTF-8"?&#62;
     * &#60;results open_counters="1"&#62;
     * &#60;checkout id="6" number="6"&#62;
     * &#60;checkout_state&#62;closed&#60;/checkout_state&#62;
     * &#60;detected_number&#62;-1&#60;/detected_number&#62;
     * &#60;/checkout&#62;
     * &#60;checkout id="7" number="7"&#62;
     * &#60;checkout_state&#62;open&#60;/checkout_state&#62;
     * &#60;detected_number&#62;-1&#60;/detected_number&#62;
     * &#60;/checkout&#62;
     * &#60;checkout id="8" number="8"&#62;
     * &#60;checkout_state&#62;closed&#60;/checkout_state&#62;
     * &#60;detected_number&#62;-1&#60;/detected_number&#62;
     * &#60;/checkout&#62;
     * &#60;/results&#62;
     * </pre>
     *
     * @param xml
     * @return
     */
    public CounterSafewayAnalyticsUCDetection parseXmlCounterSafewayAnalytics(String xml) {
        log.info("start");
        CounterSafewayAnalyticsUCDetection ret = null;
        try {
            Digester dg = new Digester();
            //cuando encontramos results creamos una respuesta
            dg.addObjectCreate("*/results", CounterSafewayAnalyticsUCDetection.class);
            dg.addSetProperties("*/results", "open_counters", "openCounters");
            dg.addObjectCreate("*/checkout", CounterSafewayAnalyticsUCCheckOut.class);
            dg.addSetProperties("*/checkout");
            dg.addSetNestedProperties("*/checkout",
                    new String[]{"checkout_state", "detected_number"},
                    new String[]{"checkoutState", "detectedNumber"});

            dg.addSetNext("*/checkout", "addCheckOut");

            ret = (CounterSafewayAnalyticsUCDetection) dg.parse(new StringReader(xml));
        } catch (IOException e) {
            log.error("IOException " + e, e);
        } catch (SAXException e) {
            log.error("SAXException " + e, e);
        }
        log.info("end");
        return ret;
    }

    /**
     * @return the httpClient
     */
    public HttpClient getHttpClient() {
        if (httpClient == null) {
            PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
            cm.setMaxTotal(100);
            cm.setDefaultMaxPerRoute(20);
            httpClient = new DefaultHttpClient(cm);

            HttpParams params = new BasicHttpParams();
            params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 1000L);
            connEvictor = new IdleConnectionEvictor(cm);
            connEvictor.setName("connEvictor_analyticsUC");
            connEvictor.start();

        }
        return httpClient;
    }

    /**
     * @param httpClient the httpClient to set
     */
    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }
}
