/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * AddAreaCommand.java
 *
 * Created on 07-05-2008, 03:36:14 PM
 *
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.dao.CorporateStructureManagementHibernateDAOImpl;
import com.scopix.periscope.extractionservicesserversmanagement.services.webservices.EvidenceServicesWebService;
import com.scopix.periscope.extractionservicesserversmanagement.services.webservices.client.EvidenceServicesWebServiceClient;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class SendExtractionPlanToPastCommand {

    private Logger log = Logger.getLogger(SendExtractionPlanToPastCommand.class);

    public List<EvidenceRequest> execute(Integer storeId, Date date) throws ScopixException {
        log.debug("execute start");
        GenericDAO genericDAO = HibernateSupport.getInstance().findGenericDAO();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Store store = genericDAO.get(storeId, Store.class);
        log.debug("Store Id = " + store.getId());
        log.debug("date = " + date);
        
        log.debug("ES Url = " + store.getEvidenceServicesServer().getUrl());
        log.debug("EESS Id = " + store.getEvidenceExtractionServicesServer().getId());
        EvidenceServicesWebService extractionPlanWebService = SpringSupport.getInstance().
                findBeanByClassName(EvidenceServicesWebServiceClient.class).
                getWebServiceClient(store.getEvidenceServicesServer().getUrl());
        
        List<Integer> evidenceRequestIds = extractionPlanWebService.extractionPlanToPast(sdf.format(date),
                store.getEvidenceExtractionServicesServer().getId(), store.getName());
        log.debug("evidenceRequestIds = " + evidenceRequestIds);
        List<EvidenceRequest> evidenceRequests = this.getEvidenceRequest(evidenceRequestIds);
        log.debug("execute end, evidenceRequest = " + evidenceRequests);
        return evidenceRequests;
    }

    private List<EvidenceRequest> getEvidenceRequest(List<Integer> ids) throws ScopixException {
        CorporateStructureManagementHibernateDAOImpl dao = SpringSupport.getInstance().findBeanByClassName(
                CorporateStructureManagementHibernateDAOImpl.class);
        List<EvidenceRequest> evidenceRequests = null;
        if (ids != null && !ids.isEmpty()) {
            try {
                evidenceRequests = dao.getEvidenceRequestsList(ids);
            } catch (Exception ex) {
                throw new ScopixException(ex);
            }
        }
        return evidenceRequests;
    }
}
