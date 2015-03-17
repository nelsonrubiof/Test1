/**
 *
 * Copyright © 2014, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 *
 *
 */
package com.scopix.periscope.frontend.services.webservices;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.jws.WebService;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;

import com.scopix.periscope.evaluationmanagement.EvaluationManager;
import com.scopix.periscope.frontend.dto.EvidenceRegionTransferDTO;
import com.scopix.periscope.frontend.dto.EvidenceRegionTransferStatsDTO;
import com.scopix.periscope.frontend.dto.RegionTransferContainerDTO;
import com.scopix.periscope.frontend.dto.RequestRegionTransferDTO;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import javax.ws.rs.PathParam;

/**
 * @author Sebastian
 *
 */
@WebService(endpointInterface = "com.scopix.periscope.frontend.services.webservices.EvidenceRegionTransferWebServices")
@SpringBean(rootClass = EvidenceRegionTransferWebServices.class)
@Path("/evidenceRegionTransfer")
public class EvidenceRegionTransferWebServicesImpl implements EvidenceRegionTransferWebServices {

    private static final Logger log = Logger.getLogger(EvidenceRegionTransferWebServices.class);
    private EvaluationManager evaluationManager;

    /*
     * (non-Javadoc)
     * 
     * @see com.scopix.periscope.frontend.services.webservices.
     * EvidenceRegionTransferWebServices
     * #getTransferByCriteria(java.lang.Integer, java.lang.Integer,
     * java.lang.String, java.lang.String, java.lang.Long)
     */
    @Override
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/getTransferByCriteria/{sessionId}")
    @Produces(MediaType.APPLICATION_JSON)
    public RegionTransferContainerDTO getTransferByCriteria(RequestRegionTransferDTO request, @PathParam(value = "sessionId") Long sessionId) {

        try {
            log.info("Proccesing incoming request");
            Integer situationTemplateId = null;
            if (request.getSituationTemplateId() != null && !request.getSituationTemplateId().equals(-1)) {
                situationTemplateId = request.getSituationTemplateId();
            }
            Integer storeId = null;
            if (request.getStoreId() != null && !request.getStoreId().equals(-1)) {
                storeId = request.getStoreId();
            }
            Integer completed = 0;
            if (request.getCompleted() != null) {
                completed = request.getCompleted();
            }

            DateFormat fullDateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            DateFormat fullDayFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date day = null;
            if (request.getDayDate() != null && !request.getDayDate().trim().isEmpty()) {
                day = fullDayFormat.parse(request.getDayDate());
            }

            Date from = null;
            if (day != null && request.getStartTime() != null && !request.getStartTime().trim().isEmpty()) {
                from = fullDateFormat.parse(request.getDayDate().trim() + " " + request.getStartTime().trim());
            }
            Date to = null;
            if (day != null && request.getEndTime() != null && !request.getEndTime().trim().isEmpty()) {
                to = fullDateFormat.parse(request.getDayDate().trim() + " " + request.getEndTime().trim());
            }
            List<EvidenceRegionTransferDTO> list = getEvaluationManager().getEvidenceRegionTransfers(storeId,
                    situationTemplateId, completed, day, from, to);
            EvidenceRegionTransferStatsDTO evidenceRegionTransferStatsDTO  =  getEvaluationManager()
                    .getEvidenceRegionTransfersStats(storeId, situationTemplateId, completed, day, from, to);
            RegionTransferContainerDTO containerDTO = new RegionTransferContainerDTO();
            containerDTO.setEvidenceRegionTransferDTOs(list);
            containerDTO.setEvidenceRegionTransferStatsDTO(evidenceRegionTransferStatsDTO);
            return containerDTO;
        } catch (Exception ex) {
            log.error("An error ocurred processing request", ex);
            RegionTransferContainerDTO containerDTO = new RegionTransferContainerDTO();
            containerDTO.setError(true);
            containerDTO.setErrorMessage(ex.getMessage());
            return containerDTO;
        }
    }

    /**
     * @return the evaluationManager
     */
    public EvaluationManager getEvaluationManager() {
        if (evaluationManager == null) {
            evaluationManager = SpringSupport.getInstance().findBeanByClassName(EvaluationManager.class);
        }
        return evaluationManager;
    }

    /**
     * @param evaluationManager the evaluationManager to set
     */
    public void setEvaluationManager(EvaluationManager evaluationManager) {
        this.evaluationManager = evaluationManager;
    }
}
