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
 * AxisP3301ImageFileReadyCommand.java
 *
 * Created on 16-08-2010, 04:27:59 PM
 *
 */
package com.scopix.periscope.extractionmanagement.commands;

import com.scopix.periscope.MJPEGGenerator;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;
import javax.swing.ImageIcon;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.core.io.ClassPathResource;

/**
 *
 * @author Gustavo Alvarez
 */
public class AxisGenericVideoFileReadyCommand {

    private static Logger log = Logger.getLogger(AxisGenericVideoFileReadyCommand.class);
    private static String uploadLocalDir;
    private static final String EXTENSION = ".mpeg";

    public AxisGenericVideoFileReadyCommand() {
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
    }

    public void execute(String fileName, File tmpFile, double fps, Integer numFrames, String resolution) throws ScopixException {
        log.info("start");
        try {
            //dejamos toda la resolucion en minuscula para poder extraer el x
            String[] resolutions = StringUtils.split(resolution.toLowerCase(), "x");
            //default
            int width = 640;
            int height = 480;
            if (resolutions.length == 2) {
                width = Integer.parseInt(resolutions[0]);
                height = Integer.parseInt(resolutions[1]);
            } else {
                log.debug("la resolucion no se pudo convertir " + resolution);
            }
            //generamos el mpeg dato el archivo tmp que se recibe
            File tempFile = File.createTempFile("motion_jpeg", EXTENSION);
            MJPEGGenerator m = new MJPEGGenerator(tempFile, width, height, fps, numFrames);
            generateVideo(new FileInputStream(tmpFile), m);
            m.finishAVI();

            //movemos el archivo a la posicion final
            FileUtils.moveFile(tempFile, new File(uploadLocalDir + fileName + EXTENSION));

            //Save the date when file is created
            EvidenceFileDAO dao = SpringSupport.getInstance().findBeanByClassName(EvidenceFileDAO.class);
            EvidenceFile evidenceFile = dao.findEvidenceFileByFileName(fileName + EXTENSION);
            if (evidenceFile != null) {
                evidenceFile.setFileCreationDate(new Date());
                HibernateSupport.getInstance().findGenericDAO().save(evidenceFile);
            }
        } catch (IOException e) {
            log.error("Error extrayendo video de " + fileName + ": " + e, e);
        }
        log.info("end");
    }

    private void generateVideo(InputStream video, MJPEGGenerator m) throws IOException {
        log.info("start");
        int r = 0;
        String text = "";
        while ((r = video.read()) != -1) {
            char c = (char) r;
            if (r == 10) {
                text = text.replaceAll("\\r", "");
                if (text.startsWith("Content-Length:")) {
                    int size = Integer.parseInt(text.substring(text.indexOf(":") + 2, text.length()));
                    while ((r = video.read()) != -1) {
                        if (r == 10) {
                            break;
                        }
                    }
                    byte[] array = new byte[size];
                    int pos = 0;
                    int b = 0;

                    while (pos < size) {
                        //leemos el conternido 1 a 1
                        b = video.read();
                        array[pos] = (byte) b;
                        pos++;
                    }
                    if (b != -1) {
                        ImageIcon ii = new ImageIcon(array);
                        m.addImage(ii.getImage());
                    }
                }
                text = "";
            } else {
                text += Character.toString(c);
            }
        }
        log.info("end");
    }
}
