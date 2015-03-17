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
 *  GetProcessFromAutomaticEvidenceAvailableCommand.java
 * 
 *  Created on 23-10-2012, 05:26:47 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.evaluationmanagement.Process;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAOImpl;
import com.scopix.periscope.evaluationmanagement.dto.AutomaticEvidenceAvailableDTO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author nelson
 */
public class GetProcessFromAutomaticEvidenceAvailableCommand {
    private EvaluationHibernateDAO dao;

    /**
     * @return the dao
     */
    public EvaluationHibernateDAO getDao() {
        if (dao ==  null){
            dao = SpringSupport.getInstance().findBeanByClassName(EvaluationHibernateDAOImpl.class);
        }
        return dao;
    }

    /**
     * @param dao the dao to set
     */
    public void setDao(EvaluationHibernateDAO dao) {
        this.dao = dao;
    }

    public Process execute(AutomaticEvidenceAvailableDTO evidenceAvailable) {
        Process process = getDao().getProcessFromAutomaticEvidenceAvailable(evidenceAvailable);
        if (process == null) {
            //si no existe lo creamos
            process = new Process();
            EvidenceExtractionServicesServer server = new EvidenceExtractionServicesServer();
            server.setId(evidenceAvailable.getEvidenceExtractionServicesServerId());
            process.setProcessIdEs(evidenceAvailable.getProcessId());
            process.setEess(server);
            HibernateSupport.getInstance().findGenericDAO().save(process);
        }
        return process;
    }
}
