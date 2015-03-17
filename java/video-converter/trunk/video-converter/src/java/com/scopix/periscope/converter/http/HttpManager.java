/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * HttpManager.java
 * 
 * Created on 14/08/2014
 */
package com.scopix.periscope.converter.http;

import java.io.IOException;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.http.HttpSupport;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;

/**
 * Clase encargada de realizar peticiones get
 *
 * @author carlos polo
 * @version 1.0.0
 * @since 6.0
 */
@SpringBean
public class HttpManager implements InitializingBean {

    private HttpSupport httpSupport;
    private PropertiesConfiguration systConfiguration;
    public static final String MAX_ROUTES = "httpClient.maxRoutes";
    private static Logger log = Logger.getLogger(HttpManager.class);
    public static final String MAX_PER_ROUTE = "httpClient.maxPerRoute";

    @Override
    public void afterPropertiesSet() throws Exception {
        // Inicializando el pool http
        HttpSupport.initInstance(getIntegerProperties(MAX_ROUTES), getIntegerProperties(MAX_PER_ROUTE));
        setHttpSupport(HttpSupport.getInstance());
    }

    public String executeHttpGet(String url) throws ScopixException {
        log.info("start");
        String srtResponse = null;
        HttpEntity httpEntity = null;
        CloseableHttpResponse httpResponse = null;
        try {
            // realiza petición
            httpResponse = getHttpSupport().httpGet(url, null);

            if (httpResponse != null) {
                httpEntity = httpResponse.getEntity();
                srtResponse = EntityUtils.toString(httpEntity);

                int statusCode = httpResponse.getStatusLine().getStatusCode();
                log.debug("httpResponse status code: [" + statusCode + "], response: [" + srtResponse + "]");

                if (statusCode != 200) {
                    throw new ScopixException("la peticion no fue exitosa, URL: [" + url + "]");
                }
            } else {
                log.error("httpResponse nulo, url: [" + url + "]");
                throw new ScopixException("httpResponse nulo, url: [" + url + "]");
            }
        } catch (HttpGetException ex) {
            throw new ScopixException("error invocando get: [" + ex.getMessage() + "]", ex);
        } catch (IOException ex) {
            throw new ScopixException("error obteniendo response: [" + ex.getMessage() + "]", ex);
        } catch (ParseException ex) {
            throw new ScopixException("error obteniendo response: [" + ex.getMessage() + "]", ex);
        } finally {
            log.debug("liberando recursos una vez finalizada la peticion");
            getHttpSupport().closeHttpEntity(httpEntity);
            getHttpSupport().closeHttpResponse(httpResponse);
        }
        log.info("end");
        return srtResponse;
    }

    /**
     * @return the systConfiguration
     */
    public PropertiesConfiguration getSystConfiguration() {
        if (systConfiguration == null) {
            try {
                systConfiguration = new PropertiesConfiguration("system.properties");
                systConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
                log.debug("systConfiguration.basePath: [" + systConfiguration.getBasePath() + "]");
            } catch (ConfigurationException e) {
                log.error("ConfigurationException: [" + e.getMessage() + "]", e);
            }
        }
        return systConfiguration;
    }

    public Integer getIntegerProperties(String key) {
        return getSystConfiguration().getInteger(key, -1);
    }

    public HttpSupport getHttpSupport() {
        return httpSupport;
    }

    public void setHttpSupport(HttpSupport httpSupport) {
        this.httpSupport = httpSupport;
    }
}