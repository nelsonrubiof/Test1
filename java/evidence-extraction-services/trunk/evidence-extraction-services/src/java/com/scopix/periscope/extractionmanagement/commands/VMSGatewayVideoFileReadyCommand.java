/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * VMSGatewayVideoFileReadyCommand.java
 * 
 * Created on 13-09-2011, 11:55:56 AM
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

import com.scopix.periscope.exception.HttpClientInitializationException;
import com.scopix.periscope.exception.HttpGetException;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.VMSGatewayEvidenceProvider;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.http.HttpSupport;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Gustavo Alvarez
 */
public class VMSGatewayVideoFileReadyCommand extends VideoFileReadyCommand {

    private HttpSupport httpSupport;
    private static String uploadLocalDir;
    private static final String CONVERTER_WAIT_CALLBACK = "converter.wait.callback";
    private static Logger log = Logger.getLogger(VMSGatewayVideoFileReadyCommand.class);

    public VMSGatewayVideoFileReadyCommand() {
        log.info("start");
        try {
            // se inicaliza en la partida (ExtractionManager) HttpSupport.initInstance(maxTotal, maxDefaultPerRoute);
            httpSupport = HttpSupport.getInstance();

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
        } catch (HttpClientInitializationException e) {
            log.error(e.getMessage(), e);
        }
        log.info("end");
    }

    /**
     * This method obtain file from VMS and save it to local upload folder
     *
     * @param fileName File name valid on VMS
     */
    public void execute(Integer evidenceFileId, String fileName) {
        log.info("start, evidenceFileId: [" + evidenceFileId + "], fileName: [" + fileName + "]");

        try {
            EvidenceFileDAO dao = HibernateSupport.getInstance().findDao(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.get(evidenceFileId);

            if (evidenceFile != null) {
                VMSGatewayEvidenceProvider evidenceProvider = (VMSGatewayEvidenceProvider) evidenceFile
                        .getEvidenceExtractionRequest().getEvidenceProvider();

                String vmsGatewayIP = evidenceProvider.getIpAddress();
                String protocol = evidenceProvider.getProtocol();
                String port = evidenceProvider.getPort();
                String getFileURL = protocol + "://" + vmsGatewayIP + ":" + port + "/vmsgateway/getFile.php?fileName=" + fileName;
                log.debug("getFileURL: [" + getFileURL + "]");

                String extension = FilenameUtils.getExtension(fileName);
                // the name of the clip must be in local time
                String name = DateFormatUtils.format(evidenceFile.getEvidenceDate(), "yyyyMMdd");
                name = name + "_" + evidenceFile.getEvidenceExtractionRequest().getId() + "." + extension;

                String outFile = uploadLocalDir + name;
                File tmpFile = getFile(getFileURL, outFile);
                log.debug("video descargado (tmp), fileName: [" + fileName + "]");

                // indica si debe esperar callback del converter para finalizar fileReady
                String waitForConverter = getConfiguration().getString(CONVERTER_WAIT_CALLBACK);
                boolean convert = super.executeConverter(tmpFile, waitForConverter, evidenceFile.getId(), name);

                log.debug("convert: [" + convert + "], waitForConverter: [" + waitForConverter + "]");
                if (!convert || (waitForConverter != null && "N".equalsIgnoreCase(waitForConverter))) {
                    log.debug("no debe convertir o no debe esperar callback de converter, realizara update de evidenceFile en base de datos y movera el temporal a processed, fileName: ["
                            + fileName + "]");
                    //solo actualizamos si no hay q convertir o no hay q esperar
                    super.finishFileReady(evidenceFile, tmpFile, outFile);

                }
                //una vez recuperado el file desde vms solicitamos que se borre desde el 
                finishFileReady(fileName, evidenceFile, vmsGatewayIP, protocol, port, name, outFile, tmpFile);

            } else {
                throw new ScopixException("No existe evidence file asociado al id: [" + evidenceFileId + "]");
            }
        } catch (ScopixException pex) {
            log.error(pex.getMessage(), pex);
        }
        log.info("end");
    }

    /**
     * @param fileName
     * @param evidenceFile
     * @param vmsGatewayIP
     * @param protocol
     * @param port
     * @param name
     * @param outFile
     * @param tmpFile
     * @throws ScopixException
     */
    public void finishFileReady(String fileName, EvidenceFile evidenceFile, String vmsGatewayIP, String protocol, String port,
            String name, String outFile, File tmpFile) throws ScopixException {

        log.info("start, fileName: [" + fileName + "], outFile: [" + outFile + "]");
//        super.finishFileReady(evidenceFile, tmpFile, outFile);

        String deleteFileURL = protocol + "://" + vmsGatewayIP + ":" + port + "/vmsgateway/deleteFile.php?fileName=" + fileName;
        log.debug("deleteFileURL: [" + deleteFileURL + "]");
        deleteFile(deleteFileURL);

        // si llegamos aqui intentamos borrar el temporal
        try {
            FileUtils.forceDelete(tmpFile);
        } catch (IOException e) {
            log.warn("Not deleting file " + tmpFile.getAbsolutePath() + " Exception: " + e);
        }
        log.info("end");
    }

    /**
     *
     * @param urlStr
     * @param outFileName
     * @return
     * @throws ScopixException
     */
    private File getFile(String urlStr, String outFileName) throws ScopixException {
        log.info("start, urlStr: [" + urlStr + "], outFileName: [" + outFileName + "]");
        // this.allowCertificatesHTTPSConnections();
        // URL url = new URL(urlStr);
        // URLConnection conn = url.openConnection();
        // conn.setAllowUserInteraction(false);
        //
        // File f = File.createTempFile("temp", "." + FilenameUtils.getExtension(outFileName));
        // log.debug("create temp file: " + f.getAbsolutePath());
        // FileOutputStream fs = new FileOutputStream(f);
        //
        // byte[] buf = new byte[1024];
        // int len;
        // while ((len = conn.getInputStream().read(buf)) > 0) {
        // fs.write(buf, 0, len);
        // }
        //
        // fs.close();
        // log.debug("move file to : " + outFileName);
        File tmpFile = null;
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpSupport.httpGet(urlStr, null);
            if (httpResponse != null) {
                log.debug("response status code: [" + httpResponse.getStatusLine().getStatusCode() + "]");
                tmpFile = httpSupport.downloadFile(httpResponse.getEntity(), outFileName);
            }
        } catch (HttpGetException e) {
            throw new ScopixException(e.getMessage(), e);
        } finally {
            if (httpResponse != null) {
                log.debug("close connectons");
                httpSupport.closeHttpEntity(httpResponse.getEntity());
                httpSupport.closeHttpResponse(httpResponse);
            }
        }
        log.info("end, tmpFile: [" + tmpFile + "]");
        return tmpFile;
    }

    /**
     *
     * @param urlStr
     */
    private void deleteFile(String urlStr) {
        log.info("start, urlStr: [" + urlStr + "]");

        // this.allowCertificatesHTTPSConnections();
        // URL url = new URL(urlStr);
        // URLConnection conn = url.openConnection();
        // conn.setAllowUserInteraction(false);
        // conn.getInputStream();
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = httpSupport.httpGet(urlStr, null);

        } catch (HttpGetException e) {
            log.error(e.getMessage(), e);
        } finally {
            if (httpResponse != null) {
                log.debug("close connectons");
                httpSupport.closeHttpEntity(httpResponse.getEntity());
                httpSupport.closeHttpResponse(httpResponse);
            }
        }
        log.info("end");
    }
}
