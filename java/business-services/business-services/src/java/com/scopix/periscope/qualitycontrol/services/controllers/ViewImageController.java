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
 *  ViewImageController.java
 * 
 *  Created on 13-12-2011, 11:10:58 AM
 * 
 */
package com.scopix.periscope.qualitycontrol.services.controllers;

import com.scopix.periscope.evaluationmanagement.dto.EvidenceDTO;
import com.scopix.periscope.evaluationmanagement.dto.ProofDTO;
import com.scopix.periscope.periscopefoundation.util.FileUtils;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.periscopefoundation.util.config.SystemConfig;
import com.scopix.periscope.qualitycontrol.QualityControlManager;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.AbstractController;

/**
 *
 * @author nelson
 */
@SpringBean(rootClass = ViewImageController.class)
public class ViewImageController extends AbstractController {

    private static Logger log = Logger.getLogger(ViewImageController.class);

    @Override
    protected ModelAndView handleRequestInternal(HttpServletRequest request, HttpServletResponse response) throws Exception {
        log.info("start");
        Integer evidenceId = getIntegerParameter(request, "evidenceId");
        Integer evidenceEvaluationId = getIntegerParameter(request, "evidenceEvaluationId");
        Integer proofPosition = getIntegerParameter(request, "proofPosition");
        Boolean withMarks = Boolean.valueOf(request.getParameter("withMarks"));
        String proofPrePath = request.getParameter("proofPrePath");
        String evidencePrePath = request.getParameter("evidencePrePath");

        try {
            QualityControlManager manager = SpringSupport.getInstance().findBeanByClassName(QualityControlManager.class);
            log.debug("[evidenceId:" + evidenceId + "][evidenceEvaluationId:" + evidenceEvaluationId + "]");
            EvidenceDTO dto = manager.getEvidenceDTO(evidenceId, evidenceEvaluationId);

            if (proofPrePath != null) {
                getProof(dto, proofPosition, proofPrePath, withMarks, manager, response);
            } else if (evidencePrePath != null) {
                getEvidence(dto, evidencePrePath, manager, response);
            }
        } catch (IOException e) {
            log.info("No es posible recuperar archivo " + e);
        }
        log.info("end");
        return null;
    }

    private void getEvidence(EvidenceDTO dto, String evidencePrePath, QualityControlManager manager,
            HttpServletResponse response) throws IOException {

        String smbPath = evidencePrePath;
        String localPath = FilenameUtils.separatorsToUnix("/data/ftp/"
                + StringUtils.substring(evidencePrePath, StringUtils.indexOf(evidencePrePath, "evidence")));;

        String file = null;
        if (dto != null) {
            String date = FileUtils.getPathFromDate(dto.getEvidenceDate(), "/");
            localPath += date + "/";
            smbPath += date + "/";
            file = dto.getEvidencePath();
            localPath = FilenameUtils.separatorsToUnix(localPath + file);
            smbPath = FilenameUtils.separatorsToUnix(smbPath + file);
            smbPath = "smb:" + smbPath;
        }
        log.debug("localPath: " + localPath);
        log.debug("smbPath: " + smbPath);
        if (file != null && file.length() > 0) {

            response.setHeader("Expires", "-6000");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            if (FilenameUtils.getExtension(dto.getEvidencePath()).toLowerCase().equals("jpg")
                    || FilenameUtils.getExtension(dto.getEvidencePath()).toLowerCase().equals("jpeg")) {
                response.setContentType("image/jpeg");
            } else {
                response.setContentType("APPLICATION/OCTET-STREAM");
            }
            response.addHeader("Content-Disposition", "attachment; filename=" + FilenameUtils.getName(file));

            File localFile = new File(localPath);
            if (localFile.exists()) { //file local
                response.getOutputStream().write(org.apache.commons.io.FileUtils.readFileToByteArray(localFile));
            } else { //smb file
                Map result = manager.getFile(smbPath);
                int size = (Integer) result.get("size");
                InputStream is = (InputStream) result.get("inputStream");
                byte[] img = new byte[size];
                response.setContentLength(img.length);
                while (is.read(img) > -1) {
                    response.getOutputStream().write(img);
                }
            }
            //limpiamos el response
            response.getOutputStream().flush();

        }

//        String path = "";
//
//        if (dto != null) {
//            String date = FileUtils.getPathFromDate(dto.getEvidenceDate(), "/");
//            path = evidencePrePath; //+ (String) getSession().get("proofPrePath");
//            path += date + "/";
//            file = dto.getEvidencePath();
//            path = FilenameUtils.separatorsToUnix(path + file);
//            path = "smb:" + path;
//        }
//        log.debug("path: " + path);
//        if (file != null && file.length() > 0) {
//            Map result = manager.getFile(path);
//            int size = (Integer) result.get("size");
//            InputStream is = (InputStream) result.get("inputStream");
//            response.setHeader("Expires", "-6000");
//            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
//            response.setHeader("Pragma", "public");
//            if (FilenameUtils.getExtension(dto.getEvidencePath()).toLowerCase().equals("jpg")
//                    || FilenameUtils.getExtension(dto.getEvidencePath()).toLowerCase().equals("jpeg")) {
//                response.setContentType("image/jpeg");
//            } else {
//                response.setContentType("APPLICATION/OCTET-STREAM");
//            }
//            response.addHeader("Content-Disposition", "attachment; filename=" + FilenameUtils.getName(path));
//            byte[] img = new byte[size];
//            response.setContentLength(img.length);
//            while (is.read(img) > -1) {
//                response.getOutputStream().write(img);
//            }
//            response.getOutputStream().flush();
//        }
    }

    private void getProof(EvidenceDTO dto, Integer proofPosition, String proofPrePath, Boolean withMarks,
            QualityControlManager manager, HttpServletResponse response) throws IOException {
        String smbPath = proofPrePath;
        String localPath = FilenameUtils.separatorsToUnix("/data/ftp/"
                + StringUtils.substring(proofPrePath, StringUtils.indexOf(proofPrePath, "proofs")));
        String file = null;
        if (dto != null && dto.getProofs() != null && !dto.getProofs().isEmpty() && StringUtils.isEmpty(dto.getCantDoReason())) {
            log.debug("search dto position " + proofPosition);
            Collections.sort(dto.getProofs(), ProofDTO.getComparatorByOrder());
            ProofDTO proof = new ProofDTO();
            proof.setOrder(proofPosition);
            int index = Collections.binarySearch(dto.getProofs(), proof, ProofDTO.getComparatorByOrder());
            log.debug("index to dto " + index);
            if (index >= 0) {
                String date = FileUtils.getPathFromDate(dto.getEvidenceDate(), "/");
                proof = dto.getProofs().get(index);
                localPath += date + "/";
                smbPath += date + "/";
                if (withMarks == null || withMarks) {
                    file = proof.getPathWithMarks();
                } else {
                    file = proof.getPathWithoutMarks();
                }
                localPath = FilenameUtils.separatorsToUnix(localPath + file);
                smbPath = "smb:" + FilenameUtils.separatorsToUnix(smbPath + file);
            } else {
                log.debug("index < 0");
                localPath = "/data/ftp/evidence/No_Proof_Available.jpg";
            }
        } else {
            log.debug("dto is null");
            localPath = "/data/ftp/evidence/No_Proof_Available.jpg";
        }

        log.debug("smbPath: " + smbPath);
        log.debug("localPath: " + localPath);

        if (file != null && file.length() > 0) {
            //verificamos si existe el local path de lo contrario enviamos el smb path
            File localFile = new File(localPath);
            response.setHeader("Expires", "-6000");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("image/jpeg");
            response.addHeader("Content-Disposition", "filename='" + FilenameUtils.getName(file) + "'");
            if (localFile.exists()) { // localFile
                response.getOutputStream().write(org.apache.commons.io.FileUtils.readFileToByteArray(localFile));
            } else { //file smb
                Map result = manager.getFile(smbPath);
                int size = (Integer) result.get("size");
                InputStream is = (InputStream) result.get("inputStream");
                byte[] img = new byte[size];
                response.setContentLength(img.length);
                while (is.read(img) > -1) {
                    response.getOutputStream().write(img);
                }
            }
            response.getOutputStream().flush();
        } else {//No existe Archivo
            File localFile = new File(localPath);
            response.setHeader("Expires", "-6000");
            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
            response.setHeader("Pragma", "public");
            response.setContentType("image/jpeg");
            response.addHeader("Content-Disposition", "filename='" + FilenameUtils.getName(localPath) + "'");
            response.getOutputStream().write(org.apache.commons.io.FileUtils.readFileToByteArray(localFile));
            response.getOutputStream().flush();
        }
//        String path = "";
//
//        if (dto != null && dto.getProofs() != null && !dto.getProofs().isEmpty() && StringUtils.isEmpty(dto.getCantDoReason())) {
//            log.debug("search dto position " + proofPosition);
//            Collections.sort(dto.getProofs(), ProofDTO.getComparatorByOrder());
//            ProofDTO proof = new ProofDTO();
//            proof.setOrder(proofPosition);
//            int index = Collections.binarySearch(dto.getProofs(), proof, ProofDTO.getComparatorByOrder());
//            log.debug("index to dto " + index);
//            if (index >= 0) {
//                String date = FileUtils.getPathFromDate(dto.getEvidenceDate(), "/");
//                proof = dto.getProofs().get(index);
//                path = proofPrePath; //+ (String) getSession().get("proofPrePath");
//                path += date + "/";
//                if (withMarks == null || withMarks) {
//                    file = proof.getPathWithMarks();
//                } else {
//                    file = proof.getPathWithoutMarks();
//                }
//                path = FilenameUtils.separatorsToUnix(path + file);
//                path = "smb:" + path;
//            } else {
//                log.debug("index < 0");
//                path = "smb:" + SystemConfig.getStringParameter("NO_PROOF_AVAILABLE");
//            }
//        } else {
//            log.debug("dto is null");
//            path = "smb:" + SystemConfig.getStringParameter("NO_PROOF_AVAILABLE");
//        }
//        log.debug("path: " + path);
//        if (file != null && file.length() > 0) {
//            Map result = manager.getFile(path);
//            int size = (Integer) result.get("size");
//            InputStream is = (InputStream) result.get("inputStream");
//            response.setHeader("Expires", "-6000");
//            response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
//            response.setHeader("Pragma", "public");
//            response.setContentType("image/jpeg");
//            response.addHeader("Content-Disposition", "filename='" + FilenameUtils.getName(path) + "'");
//            byte[] img = new byte[size];
//            response.setContentLength(img.length);
//            while (is.read(img) > -1) {
//                response.getOutputStream().write(img);
//            }
//            response.getOutputStream().flush();
//        }
    }

    private Integer getIntegerParameter(HttpServletRequest request, String parameter) {
        Integer ret = null;
        try {
            ret = Integer.valueOf(request.getParameter(parameter));
        } catch (NumberFormatException e) {
            ret = 0;
        }
        return ret;
    }
}
