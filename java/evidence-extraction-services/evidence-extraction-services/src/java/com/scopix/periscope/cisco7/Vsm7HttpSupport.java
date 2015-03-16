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
 * Vsm7HttpSupport.java
 * 
 * Created on 08-10-2013, 03:13:41 PM
 */
package com.scopix.periscope.cisco7;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpEntity;
import org.apache.http.ParseException;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.exception.HttpClientInitializationException;
import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.exception.HttpPostException;
import com.scopix.periscope.http.HttpSupport;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

/**
 *
 * @author Gustavo Alvarez
 * @modified Carlos Polo
 */
public class Vsm7HttpSupport {

    private List<String> getTags;
    private List<String> postTags;
    private String sessionId = null;
    private HttpSupport httpSupport;
    private List<String> responseTags;
    private static final String boundaryString = "--myboundary";
    private static Logger log = Logger.getLogger(Vsm7HttpSupport.class);

    public Vsm7HttpSupport() throws ScopixException {
        log.info("start");
        try {
            postTags = new ArrayList<String>();
            postTags.add("HTTP_POST_ERROR");

            getTags = new ArrayList<String>();
            getTags.add("HTTP_GET_ERROR");

            responseTags = new ArrayList<String>();
            responseTags.add("RESPONSE_PROCESSING_ERROR");

            // se inicaliza en la partida (ExtractionManager) HttpSupport.initInstance(maxTotal, maxDefaultPerRoute);
            httpSupport = HttpSupport.getInstance();

        } catch (HttpClientInitializationException ex) {
            throw new ScopixException(ex);
        }
        log.info("end");
    }

    /**
     * 
     * @param url
     * @param dataIn
     * @param filePath
     * @param checkImages
     * @return
     * @throws ScopixException
     */
    public String httpPost2File(String url, StringEntity dataIn, String filePath, String checkImages) throws ScopixException {
        log.info("start");
        String response = null;
        HttpEntity httpEntity = null;
        CloseableHttpResponse httpResponse = null;

        try {
            HashMap<String, String> requestHeaders = null;
            // existe un sessionId, se agregará al request
            if (getSessionId() != null && !getSessionId().isEmpty()) {
                requestHeaders = new HashMap<String, String>();
                requestHeaders.put("x-ism-sid", getSessionId());
            }

            if (dataIn != null) {
                dataIn.setContentType("application/json");
            }
            // invoca post
            log.debug("invocando post, url: [" + url + "]");
            httpResponse = httpSupport.httpPost(url, requestHeaders, dataIn);

            if (httpResponse != null) {
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();
                log.debug("httpResponse status code: [" + statusCode + "]");

                if (statusCode < 200 || statusCode > 202) { // 200 OK, 201 Created, 202 Accepted
                    response = EntityUtils.toString(httpEntity);
                    throw new ScopixException("Error descargando archivo: [" + filePath + "], response: [" + response + "]",
                            postTags);
                } else {
                    // está dentro del rango de respuestas exitosas (200 OK, 201 Created, 202 Accepted)
                    response = processFile(filePath, httpEntity, url, checkImages);
                }
            } else {
                log.error("httpResponse nulo, url: [" + url + "]");
                throw new ScopixException("httpResponse nulo, url: [" + url + "]", postTags);
            }
        } catch (ClientProtocolException e) {
            throw new ScopixException(e.getMessage(), postTags, e);
        } catch (IOException e) {
            throw new ScopixException(e.getMessage(), postTags, e);
        } catch (HttpPostException e) {
            throw new ScopixException(e.getMessage(), postTags, e);
        } finally {
            log.debug("liberando recursos una vez finalizada la peticion");
            httpSupport.closeHttpEntity(httpEntity);
            httpSupport.closeHttpResponse(httpResponse);
        }
        log.info("end, output from server (response): [" + response + "]");
        return response;
    }

    /**
     *
     * @param url
     * @param filePath
     * @return
     * @throws ScopixException
     */
    public String httpGet2File(String url, String filePath) throws ScopixException {
        log.info("start");
        String response = null;
        HttpEntity httpEntity = null;
        CloseableHttpResponse httpResponse = null;

        try {
            HashMap<String, String> requestHeaders = null;
            // existe un sessionId, se agregará al request
            if (getSessionId() != null && !getSessionId().isEmpty()) {
                requestHeaders = new HashMap<String, String>();
                requestHeaders.put("x-ism-sid", getSessionId());
            }
            // invoca get
            log.debug("invocando get, url: [" + url + "]");
            httpResponse = httpSupport.httpGet(url, requestHeaders);

            if (httpResponse != null) {
                httpEntity = httpResponse.getEntity();

                int statusCode = httpResponse.getStatusLine().getStatusCode();
                log.debug("httpResponse status code: [" + statusCode + "]");

                if (statusCode != 200) {
                    response = EntityUtils.toString(httpEntity);

                } else if (filePath != null) {
                    log.debug("se descargara archivo: [" + filePath + "]");
                    downloadFile(httpEntity, filePath, null);
                }
            } else {
                log.error("httpResponse nulo, url: [" + url + "]");
                throw new ScopixException("httpResponse nulo, url: [" + url + "]", getTags);
            }
        } catch (ClientProtocolException e) {
            throw new ScopixException(e.getMessage(), getTags, e);
        } catch (IOException e) {
            throw new ScopixException(e.getMessage(), getTags, e);
        } catch (HttpGetException e) {
            throw new ScopixException(e.getMessage(), getTags, e);
        } finally {
            log.debug("liberando recursos una vez finalizada la peticion");
            httpSupport.closeHttpEntity(httpEntity);
            httpSupport.closeHttpResponse(httpResponse);
        }
        log.info("end, output from server (response): [" + response + "]");
        return response;
    }

    /**
     * 
     * @param filePath
     * @param httpEntity
     * @param url
     * @param checkImages
     * @return
     * @throws IOException
     */
    private String processFile(String filePath, HttpEntity httpEntity, String url, String checkImages) throws ScopixException {

        log.debug("start, filePath: [" + filePath + "]");
        String response = null;

        try {
            if (filePath != null) { // write into File
                // This very specific code for VSM JPEG stream parsing, it needs to be generic
                String contentType = ContentType.getOrDefault(httpEntity).getMimeType();
                if (contentType != null
                        && ("application/jpeg".equalsIgnoreCase(contentType) || "application/jpg".equalsIgnoreCase(contentType))) {
                    // descarga archivo de imágen
                    log.debug("se descargara archivo: [" + filePath + "]");
                    downloadFile(httpEntity, filePath, checkImages);
                } else {
                    throw new ScopixException("httpPost2File failure, image without " + "content type or its null, url: [" + url
                            + "], contentType: [" + contentType + "]", responseTags);
                }
            } else { // return string
                BufferedReader br = new BufferedReader(new InputStreamReader((httpEntity.getContent())));
                String jsonResponse = "";
                String output;
                while ((output = br.readLine()) != null) {
                    jsonResponse += output;
                }
                response = jsonResponse;
            }
        } catch (UnsupportedCharsetException e) {
            throw new ScopixException(e.getMessage(), responseTags, e);
        } catch (ParseException e) {
            throw new ScopixException(e.getMessage(), responseTags, e);
        } catch (IllegalStateException e) {
            throw new ScopixException(e.getMessage(), responseTags, e);
        } catch (IOException e) {
            throw new ScopixException(e.getMessage(), responseTags, e);
        }
        log.info("end, response: [" + response + "]");
        return response;
    }

    /**
     * Descarga archivo
     * 
     * @param httpEntity
     * @param filePath
     * @param checkImages
     * @return archivo descargado
     * @throws IOException
     */
    private File downloadFile(HttpEntity httpEntity, String filePath, String checkImages) throws ScopixException {
        log.info("start, descargando archivo, filePath: [" + filePath + "], checkImages: [" + checkImages + "]");

        File file = null;
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

            if ("S".equalsIgnoreCase(checkImages)) {
                // Revisa contenido de la imágen
                originalTmpFile = checkImageTmpFile(originalTmpFile);
            }

            log.debug("moviendo archivo temporal a: [" + filePath + "]");
            file = new File(filePath);
            FileUtils.moveFile(originalTmpFile, file);

        } catch (IllegalStateException e) {
            deleteTmpFile(originalTmpFile);
            throw new ScopixException("Error en descarga de archivo: [" + filePath + "]", e);

        } catch (IOException e) {
            deleteTmpFile(originalTmpFile);
            throw new ScopixException("Error en descarga de archivo: [" + filePath + "]", e);
        }

        log.info("end, archivo temporal movido exitosamente a: [" + filePath + "], size: [" + file.length() + "]");
        return file;
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
     * Revisa contenido de la imágen para verificar de que no tenga encabezados que corrompan el contenido, si el contenido está
     * ok no hace nada
     * 
     * @param originalTmpFile
     * @return
     * @throws IOException
     */
    private File checkImageTmpFile(File originalTmpFile) throws IOException {
        log.info("start, originalTmpFile: [" + originalTmpFile.getAbsolutePath() + "], length: [" + originalTmpFile.length()
                + "]");

        BufferedOutputStream bufferedOutStream = null;

        boolean borrar = false;
        byte[] bFile = new byte[(int) originalTmpFile.length()];

        String newFileName = FilenameUtils.separatorsToUnix(FilenameUtils.getFullPath(originalTmpFile.getAbsolutePath()) + "/tmp"
                + FilenameUtils.getName(originalTmpFile.getAbsolutePath()));

        log.debug("newFileName: [" + newFileName + "]");
        File newFile = new File(newFileName);

        FileInputStream fileInputStream = new FileInputStream(originalTmpFile);
        fileInputStream.read(bFile);
        fileInputStream.close();

        StringBuilder sBuilder = new StringBuilder();
        bufferedOutStream = new BufferedOutputStream(new FileOutputStream(newFile));

        int lineCount = 0;

        for (int i = 0; i < bFile.length; i++) {
            char text = (char) bFile[i];

            if (text != '\r' && text != '\n') {
                sBuilder.append(text);
            }

            if (text == '\n') {
                // terminó de recorrer la primera línea, valida si tiene la cadena que indica contenido corrupto
                if (lineCount == 0 && !sBuilder.toString().equals(boundaryString)) {
                    borrar = true;
                    break;
                }
                lineCount++;

                if (lineCount == 6) {
                    lineCount++;
                    continue;
                }
            }

            if (lineCount > 6) {
                bufferedOutStream.write(bFile[i]);
            }
        } // fin for

        bufferedOutStream.close();
        log.debug("borrar: [" + borrar + "]");
        if (borrar) {
            log.debug("borrando archivo (ya que no es necesario dado que la " + "imagen original tiene contenido correcto): ["
                    + newFile.getAbsolutePath() + "]");
            FileUtils.forceDelete(newFile);
        } else {
            log.debug("reemplazando archivo (originalTmpFile): [" + originalTmpFile.getAbsolutePath() + "] por (newFile): ["
                    + newFile.getAbsolutePath() + "]. originalTmpFile.length(): [" + originalTmpFile.length()
                    + "], newFile.length(): [" + newFile.length() + "]");
            FileUtils.forceDelete(originalTmpFile);
            FileUtils.moveFile(newFile, originalTmpFile);
        }
        log.info("end");
        return originalTmpFile;
    }

    public void shutdown() {
        log.debug("start");
        httpSupport.closeHttpClient();
        httpSupport.closeConnectionEvictor();
        log.debug("end");
    }

    /**
     * @return the httpSupport
     */
    public HttpSupport getHttpSupport() {
        return httpSupport;
    }

    /**
     * @param httpSupport the httpSupport to set
     */
    public void setHttpSupport(HttpSupport httpSupport) {
        this.httpSupport = httpSupport;
    }

    /**
     * @return the sessionId
     */
    public String getSessionId() {
        return sessionId;
    }

    /**
     * @param sessionId the sessionId to set
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
}