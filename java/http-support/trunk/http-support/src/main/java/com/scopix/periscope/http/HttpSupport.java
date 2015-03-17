/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * HttpSupport.java
 * 
 * Created on 08-01-2014, 03:11:29 PM
 */
package com.scopix.periscope.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.exception.HttpClientInitializationException;
import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.exception.HttpPostException;
import com.scopix.periscope.exception.HttpPutException;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 * Clase encargada de manejar instancia del httpClient así como las operaciones de la misma
 *
 * @author carlos polo
 */
@SuppressWarnings("static-access")
public class HttpSupport {

    private static int maxRoutes;
    private static int maxPerRoute;
    private static PropertiesConfiguration configuration;
    private static volatile HttpSupport instance;
    private static CloseableHttpClient httpClient;
    private static IdleConnectionEvictor connectionEvictor;
    private static Logger log = Logger.getLogger(HttpSupport.class);

    public HttpSupport(int maxRoute, int maxPerRoute) {
        instance.maxRoutes = maxRoute;
        instance.maxPerRoute = maxPerRoute;
        log.debug("maxRoute: [" + maxRoute + "], maxPerRoute: [" + maxPerRoute + "]");
    }

    public static void initInstance(int maxRoutes, int defaultMaxPerRoute) {
        if (instance != null) {
            return;
        }

        synchronized (HttpSupport.class) {
            if (instance == null) {
                instance = new HttpSupport(maxRoutes, defaultMaxPerRoute);
                try {
                    if (getConfiguration() == null) {
                        throw new HttpClientInitializationException("Error en " + "lectura del archivo httpsupport.properties");
                    }
                    instance.initHttpClient();

                } catch (HttpClientInitializationException e) {
                    log.error("Initialization failed: [" + e.getMessage() + "]", e);
                }
            }
        }
    }

    public static HttpSupport getInstance() throws HttpClientInitializationException {
        if (instance == null) {
            throw new HttpClientInitializationException("HttpSupport has not been initialized");
        }

        return instance;
    }

    /**
     * Inicializa y configura instancia httpClient
     *
     * @throws HttpClientInitializationException
     *
     * @throws PeriscopeException
     */
    public static void initHttpClient() throws HttpClientInitializationException {
        log.info("start, maxTotal: [" + maxRoutes + "], defaultMaxPerRoute: [" + maxPerRoute + "]");
        try {
            SSLContextBuilder sslContextBuilder = SSLContexts.custom();
            sslContextBuilder.loadTrustMaterial(null, new TrustStrategy() {

                @Override
                public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
                    // FIXME Obligatory warning: you shouldn't really do this,
                    // trusting all certificates is a bad thing
                    return true;
                }
            });

            SSLContext sslContext = sslContextBuilder.build();
            SSLConnectionSocketFactory sslConnSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    new X509HostnameVerifier() {

                        @Override
                        public void verify(String host, SSLSocket ssl) throws IOException {
                        }

                        @Override
                        public void verify(String host, X509Certificate cert) throws SSLException {
                        }

                        @Override
                        public void verify(String host, String[] cns, String[] subjectAlts) throws SSLException {
                        }

                        @Override
                        public boolean verify(String s, SSLSession sslSession) {
                            return true;
                        }
                    });

            log.debug("creando y estableciendo configuraciones para el httpClient");
            // The provided solution with the SocketFactoryRegistry works when
            // using PoolingHttpClientConnectionManager.
            // However, connections via plain http don't work any longer then
            // you have to add a PlainConnectionSocketFactory for the http
            // protocol additionally to make them work again
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("https", sslConnSocketFactory).register("http", new PlainConnectionSocketFactory()).build();

            PoolingHttpClientConnectionManager poolingConnManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);

            poolingConnManager.setMaxTotal(maxRoutes);
            poolingConnManager.setDefaultMaxPerRoute(maxPerRoute);

            Integer connectTimeout = getIntegerProperties("http.timeout");

            // inicializa httpClient y valida configuración del timeout
            configureHttpClient(connectTimeout, poolingConnManager);
            log.debug("httpClient creado");

            // inicializa el hilo que monitoreará las conexiones expiradas
            initConnectionEvictor(poolingConnManager);

        } catch (NoSuchAlgorithmException ex) {
            log.error(ex.getMessage(), ex);
            throw new HttpClientInitializationException(ex);
        } catch (KeyStoreException ex) {
            log.error(ex.getMessage(), ex);
            throw new HttpClientInitializationException(ex);
        } catch (KeyManagementException ex) {
            log.error(ex.getMessage(), ex);
            throw new HttpClientInitializationException(ex);
        }
        log.info("end");
    }

    /**
     * Inicializa httpClient y valida configuración del timeout
     *
     * @param connectTimeout
     * @param connRequestTimeout
     * @param socketTimeout
     * @param poolingConnManager
     */
    private static void configureHttpClient(Integer connectTimeout, PoolingHttpClientConnectionManager poolingConnManager) {

        log.info("start, connectTimeout: [" + connectTimeout + "]");

        if (connectTimeout == null || (connectTimeout != null && connectTimeout < 0)) {
            log.debug("inicializando httpClient sin timeout");
            httpClient = HttpClients.custom().setConnectionManager(poolingConnManager).build();
        } else {
            log.debug("inicializando httpClient con timeout");
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(connectTimeout * 1000)
                    .setConnectionRequestTimeout(connectTimeout * 1000)
                    .setSocketTimeout(connectTimeout * 1000)
                    .build();

            httpClient = HttpClients.custom()
                    .setConnectionManager(poolingConnManager)
                    .setDefaultRequestConfig(config)
                    .build();
        }
        log.info("end, httpClient: [" + httpClient + "]");
    }

    /**
     * Inicializa el hilo que monitoreará las conexiones expiradas
     *
     * @param poolingConnManager
     */
    private static void initConnectionEvictor(PoolingHttpClientConnectionManager poolingConnManager) {
        log.info("start, iniciando connectionEvictor");
        // Se ocupa para revisar las conexiones expiradas o idle
        connectionEvictor = new IdleConnectionEvictor(poolingConnManager);
        connectionEvictor.setName("connectionEvictor@" + Thread.currentThread().getName());
        connectionEvictor.start();
        log.info("end");
    }

    /**
     * Realiza petición post
     *
     * @param url
     * @param requestHeaders
     * @param dataIn
     * @return
     * @throws PeriscopeException
     */
    public CloseableHttpResponse httpPost(String url, HashMap<String, String> requestHeaders, StringEntity dataIn)
            throws HttpPostException {

        log.info("start");
        CloseableHttpResponse httpResponse = null;

        try {
            HttpPost postRequest = new HttpPost(url);
            postRequest = setPostRequestParams(postRequest, requestHeaders, dataIn);

            log.debug("antes de ejecutar post");
            httpResponse = httpClient.execute(postRequest);
            log.debug("despues de ejecutar post");

        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
            throw new HttpPostException(ex);
        }
        log.info("end, httpResponse: [" + httpResponse + "]");
        return httpResponse;
    }

    /**
     * Realiza petición get
     *
     * @param url
     * @param requestHeaders
     * @return
     * @throws HttpGetException
     */
    public CloseableHttpResponse httpGet(String url, HashMap<String, String> requestHeaders) throws HttpGetException {
        log.info("start");
        CloseableHttpResponse httpResponse = null;

        try {
            HttpGet getRequest = new HttpGet(url);
            getRequest = setGetRequestParams(getRequest, requestHeaders);

            log.debug("antes de ejecutar get");
            httpResponse = httpClient.execute(getRequest);
            log.debug("despues de ejecutar get");

        } catch (ClientProtocolException e) {
            log.error(e.getMessage(), e);
            throw new HttpGetException(e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new HttpGetException(e);
        }
        log.info("end, httpResponse: [" + httpResponse + "]");
        return httpResponse;
    }

    /**
     * Realiza petición put
     *
     * @param url
     * @param requestHeaders
     * @param xmlContent
     * @return
     * @throws HttpPutException
     */
    public CloseableHttpResponse httpPut(String url, HashMap<String, String> requestHeaders, String xmlContent)
            throws HttpPutException {
        log.info("start");
        CloseableHttpResponse httpResponse = null;

        try {
            HttpPut putRequest = new HttpPut(url);
            putRequest = setPutRequestParams(putRequest, requestHeaders);

            if (xmlContent != null) {
                StringEntity input = new StringEntity(xmlContent);
                input.setContentType("application/xml");
                putRequest.setEntity(input);
            }

            log.debug("antes de ejecutar put");
            httpResponse = httpClient.execute(putRequest);
            log.debug("despues de ejecutar put");

        } catch (ClientProtocolException e) {
            log.error(e.getMessage(), e);
            throw new HttpPutException(e);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            throw new HttpPutException(e);
        }
        log.info("end, httpResponse: [" + httpResponse + "]");
        return httpResponse;
    }

    /**
     * Determina si se establecen parámetros como headers y/o entity en el postRequest
     *
     * @param postRequest
     * @param requestHeaders
     * @param dataIn
     * @return
     */
    private HttpPost setPostRequestParams(HttpPost postRequest, HashMap<String, String> requestHeaders, StringEntity dataIn) {
        log.info("start");
        if (requestHeaders != null && !requestHeaders.isEmpty()) {
            log.debug("estableciendo headers de la peticion");
            for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                log.debug("header key: [" + key + "], header value: [" + value + "]");
                postRequest.addHeader(key, value);
            }
        }

        if (dataIn != null) {
            log.debug("estableciendo entidad de la peticion");
            postRequest.setEntity(dataIn);
        }
        log.info("end");
        return postRequest;
    }

    /**
     * Determina si se establecen headers en el getRequest
     *
     * @param getRequest
     * @param requestHeaders
     * @return
     */
    private HttpGet setGetRequestParams(HttpGet getRequest, HashMap<String, String> requestHeaders) {
        log.info("start");
        if (requestHeaders != null && !requestHeaders.isEmpty()) {
            log.debug("estableciendo headers de la peticion");
            for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                log.debug("header key: [" + key + "], header value: [" + value + "]");
                getRequest.addHeader(key, value);
            }
        }
        log.info("end");
        return getRequest;
    }

    /**
     * Determina si se establecen headers en el putRequest
     *
     * @param putRequest
     * @param requestHeaders
     * @return
     */
    private HttpPut setPutRequestParams(HttpPut putRequest, HashMap<String, String> requestHeaders) {
        log.info("start");
        if (requestHeaders != null && !requestHeaders.isEmpty()) {
            log.debug("estableciendo headers de la peticion");
            for (Map.Entry<String, String> entry : requestHeaders.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();

                log.debug("header key: [" + key + "], header value: [" + value + "]");
                putRequest.addHeader(key, value);
            }
        }
        log.info("end");
        return putRequest;
    }

    /**
     * Cierra el cliente y libera los respectivos recursos del sistema utilizados en �l
     */
    public void closeHttpClient() {
        log.info("start");
        try {
            if (getHttpClient() != null) {
                getHttpClient().close();
            }
        } catch (IOException ex) {
            log.warn("error cerrando httpClient: [" + ex.getMessage() + "]", ex);
        }
        log.info("end");
    }

    /**
     * Cierra el response y libera los respectivos recursos del sistema utilizados en �l
     *
     * @param httpResponse
     */
    public void closeHttpResponse(CloseableHttpResponse httpResponse) {
        log.info("start");
        try {
            if (httpResponse != null) {
                httpResponse.close();
            }
        } catch (IOException ex) {
            log.warn("error cerrando httpResponse: [" + ex.getMessage() + "]", ex);
        }
        log.info("end");
    }

    /**
     * Cierra la entidad y libera los respectivos recursos del sistema utilizados en ella
     *
     * @param httpEntity
     */
    public void closeHttpEntity(HttpEntity httpEntity) {
        log.info("start");
        try {
            if (httpEntity != null) {
                InputStream instream = httpEntity.getContent();
                instream.close();
                EntityUtils.consumeQuietly(httpEntity);
            }
        } catch (IOException ex) {
            log.warn("error cerrando httpEntity: [" + ex.getMessage() + "]", ex);
        }
        log.info("end");
    }

    /**
     * Cierra el hilo que monitorea las conexiones expiradas
     */
    public void closeConnectionEvictor() {
        log.info("start");
        try {
            if (connectionEvictor != null) {
                connectionEvictor.shutdown();
                connectionEvictor.join();
            }
        } catch (InterruptedException ex) {
            log.warn("error cerrando connectionEvictor: [" + ex.getMessage() + "]", ex);
        }
        log.info("end");
    }

    /**
     * Descarga archivo
     *
     * @param httpEntity
     * @param filePath
     * @param checkImages
     * @return archivo temporal descargado
     * @throws IOException
     */
    public File downloadFile(HttpEntity httpEntity, String filePath) throws ScopixException {
        log.info("start, descargando archivo, filePath: [" + filePath + "]");

        File originalTmpFile = null;

        try {
            originalTmpFile = File.createTempFile("temp", "." + FilenameUtils.getExtension(filePath));
            log.debug("originalTmpFile path: [" + originalTmpFile.getAbsolutePath() + "]");

            BufferedInputStream bufferedInStream = new BufferedInputStream(httpEntity.getContent());
            BufferedOutputStream bufferedOutStream = new BufferedOutputStream(new FileOutputStream(originalTmpFile));

            int read = 0;
            byte[] bArray = new byte[1048576];

            while ((read = bufferedInStream.read(bArray)) != -1) {
                bufferedOutStream.write(bArray, 0, read);
            }
            bufferedOutStream.close();

        } catch (IllegalStateException e) {
            deleteTmpFile(originalTmpFile);
            throw new ScopixException("Error en descarga de archivo: [" + filePath + "]", e);

        } catch (IOException e) {
            deleteTmpFile(originalTmpFile);
            throw new ScopixException("Error en descarga de archivo: [" + filePath + "]", e);
        }

        log.info("end");
        return originalTmpFile;
    }

    /**
     *
     * @param tmpFile
     * @throws ScopixException
     */
    private void deleteTmpFile(File tmpFile) throws ScopixException {
        log.info("start");
        try {
            if (tmpFile != null && tmpFile.exists()) {
                String tmpPath = tmpFile.getAbsolutePath();
                FileUtils.forceDelete(tmpFile);
                log.debug("archivo temporal borrado: [" + tmpPath + "]");
            }
        } catch (IOException e) {
            throw new ScopixException(e.getMessage(), e);
        }
        log.info("end");
    }

    /**
     * @return the maxTotal
     */
    public int getMaxTotal() {
        return maxRoutes;
    }

    /**
     * @return the defaultMaxPerRoute
     */
    public int getDefaultMaxPerRoute() {
        return maxPerRoute;
    }

    /**
     * @return the httpClient
     */
    public CloseableHttpClient getHttpClient() {
        return httpClient;
    }

    public static PropertiesConfiguration getConfiguration() {
        log.info("start");
        if (configuration == null) {
            try {
                configuration = new PropertiesConfiguration("httpsupport.properties");
                configuration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException e) {
                log.error("No es posible cargar propiedades http-support: [" + e + "]", e);
            }
        }
        log.info("end, configuration: [" + configuration + "]");
        return configuration;
    }

    public static Integer getIntegerProperties(String key) {
        return getConfiguration().getInteger(key, -1);
    }
}
