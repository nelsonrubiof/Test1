/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * ProofsThread.java
 * 
 * Created on 2/09/2014
 */
package com.scopix.periscope.thread;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.scopix.periscope.operatorimages.MarksContainer;
import com.scopix.periscope.operatorimages.MarksContainerDTO;
import com.scopix.periscope.operatorimages.OperatorImageManager;
import com.scopix.periscope.operatorimages.ResultMarks;
import com.scopix.periscope.operatorimages.ResultMarksContainerDTO;
import com.scopix.periscope.operatorimages.ResultMarksDTO;
import com.scopix.periscope.operatorimages.services.ConvertDTOToObject;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

public class ProofsThread extends Thread {

    private MarksContainerDTO containerDto;
    private OperatorImageManager imageManager;
    private static Logger log = Logger.getLogger(ProofsThread.class);

    @Override
    public void run() {
        log.info("start");
        int retry = 2; // por defecto
        Boolean error = false;
        String errorMsg = null;
        Long sessionId = getContainerDto().getSessionId();
        Integer situationId = getContainerDto().getSituationId();
        List<ResultMarksDTO> lRet = new ArrayList<ResultMarksDTO>();

        try {
            MarksContainer container = new MarksContainer();
            log.debug("generando proofs para situationId: [" + situationId + "], sessionId: [" + sessionId + "]");

            if (getContainerDto().getMarks() != null && !getContainerDto().getMarks().isEmpty()) {
                // convertimos el dto a Objecto de nuestro modelo
                container = (MarksContainer) ConvertDTOToObject.convert(getContainerDto(), container);
                List<ResultMarks> list = getImageManager().generateProof(container.getMarks());

                for (ResultMarks resultMark : list) {
                    ResultMarksDTO resultMarkDTO = new ResultMarksDTO();
                    resultMarkDTO = (ResultMarksDTO) ConvertDTOToObject.convert(resultMark, resultMarkDTO);
                    log.debug("resultMarkDTO fileName: [" + resultMarkDTO.getFileName() + "]");
                    lRet.add(resultMarkDTO);
                }
            }

        } catch (ScopixException e) {
            log.error(e.getMessage(), e);
            error = true;
            errorMsg = e.getMessage();
        }

        ResultMarksContainerDTO resultMarksContainer = new ResultMarksContainerDTO();
        resultMarksContainer.setError(error);
        resultMarksContainer.setResults(lRet);
        resultMarksContainer.setErrorMsg(errorMsg);
        resultMarksContainer.setSessionId(sessionId);
        resultMarksContainer.setSituationId(situationId);

        // invoca web service de operator-web
        log.debug("invocando web service para callback a operator-web, sessionId: [" + sessionId + "], situationId: ["
                + situationId + "], error: [" + error + "]");
        boolean exitoCallback = callbackToOperatorWeb(resultMarksContainer, sessionId, situationId, retry);

        log.info("end, exitoCallback: [" + exitoCallback + "], sessionId: [" + sessionId + "], situationId: [" + situationId
                + "], error: [" + error + "]");
    }

    /**
     * 
     * @param resultMarksContainer
     * @param sessionId
     * @param situationId
     * @param retry
     * @return
     */
    private boolean callbackToOperatorWeb(ResultMarksContainerDTO resultMarksContainer, Long sessionId, Integer situationId,
            int retry) {
        log.info("start, sessionId: [" + sessionId + "], situationId: [" + situationId + "]");
        boolean exito = false;
        try {
            getImageManager().getServiceClient().post(resultMarksContainer, ResultMarksContainerDTO.class);
            exito = true;

        } catch (Exception e) {
            log.error("error invocando callback a operator-web: [" + e.getMessage() + "], sessionId: [" + sessionId
                    + "], situationId: [" + situationId + "]");
            if (retry > 0) {
                retry--;
                callbackToOperatorWeb(resultMarksContainer, sessionId, situationId, retry);
            }
        }
        log.info("end, exito: [" + exito + "]");
        return exito;
    }

    public MarksContainerDTO getContainerDto() {
        return containerDto;
    }

    public void setContainerDto(MarksContainerDTO containerDto) {
        this.containerDto = containerDto;
    }

    /**
     * @return the imageManager
     */
    public OperatorImageManager getImageManager() {
        if (imageManager == null) {
            imageManager = SpringSupport.getInstance().findBeanByClassName(OperatorImageManager.class);
        }
        return imageManager;
    }
}
