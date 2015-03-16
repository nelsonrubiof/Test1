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
 * VadaroPeopleCountingExtractionCommand.java
 * 
 * Created on 21-04-2014, 18:08:00 PM
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.evidencemanagement.EvidenceRequestType;
import com.scopix.periscope.extractionmanagement.EvidenceExtractionRequest;
import com.scopix.periscope.extractionmanagement.EvidenceFile;
import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.extractionmanagement.SituationRequest;
import com.scopix.periscope.extractionmanagement.dao.EvidenceFileDAO;
import com.scopix.periscope.extractionmanagement.dto.EvidenceRequestVO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.exceptions.DaoInstanceNotFoundException;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.vadaro.VadaroEvent;
import com.scopix.periscope.vadaro.VadaroParser;

/**
 * Vadaro listener event injection
 *
 * @author EmO
 */
public class VadaroEvidenceInjectCommand {

    private static final Logger log = Logger.getLogger(VadaroEvidenceInjectCommand.class);

    /**
     * Ejecuta comando de extracción
     *
     * @param event
     * @param uploadDir
     *
     * @throws ScopixException
     */
    public void execute(VadaroEvent event, String uploadDir) throws ScopixException {

        log.info("start");
        prepareEvidence(event, uploadDir);
        log.info("end");
    }

    /**
     * Prepara extracción de evidencia
     *
     * @param event
     * @param uploadDir
     *
     * @throws ScopixException
     */
    public void prepareEvidence(VadaroEvent event, String uploadDir) throws ScopixException {

        log.info("start, event: [" + event + "]");
        try {

            FindSituationsSensorDateCommand command = new FindSituationsSensorDateCommand();
            List<SituationRequest> listSituation = command.execute(event.getName(), event.getTime());

            ExtractionManager extractionManager = getExtractionManager();
            List<EvidenceRequestVO> preEvidenceRequestDTOs = extractionManager.getPreEvidenceRequestDTOsInjection(listSituation,
                    event.getTime(), false, event.getName(), null, false);

            String storeName = listSituation.get(0).getExtractionPlan().getStoreName();

            List<EvidenceExtractionRequest> evidenceExtractionRequests = extractionManager.scheduleEvidenceRequest(
                    preEvidenceRequestDTOs, event.getTime(), storeName, EvidenceRequestType.AUTO_GENERATED, null, false);

            // con cada request generamos un evidence file asociado con el xml asociado como file
            String xml = VadaroParser.generateXml(event);
            File tmp = File.createTempFile("xml", ".xml");
            FileUtils.writeStringToFile(tmp, xml);
            EvidenceFileDAO dao = HibernateSupport.getInstance().findDao(EvidenceFileDAO.class);

            for (EvidenceExtractionRequest request : evidenceExtractionRequests) {
                String fileName = DateFormatUtils.format(event.getTime(), "yyyyMMdd");
                fileName = fileName + "_" + request.getId() + ".xml";
                EvidenceFile evidenceFile = dao.registerEvidenceFile(fileName, request, event.getTime());
                dao.updateEvidenceFile(evidenceFile, fileName);

                FileUtils.copyFile(tmp,
                        new File(FilenameUtils.separatorsToSystem(extractionManager.getStringProperties(uploadDir) + fileName)));
            }
            
            if (tmp.exists()) {
                FileUtils.forceDelete(tmp);
            }
        } catch (IOException | DaoInstanceNotFoundException e) {
            log.error(e, e);
            throw new ScopixException(e);
        }

        log.info("end");
    }

    public ExtractionManager getExtractionManager() {
        return SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
    }

}
