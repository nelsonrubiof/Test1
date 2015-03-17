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
 *  GetEvidenceDTOCommand.java
 * 
 *  Created on 13-12-2011, 12:42:59 PM
 * 
 */

package com.scopix.periscope.qualitycontrol.commands;

import com.scopix.periscope.evaluationmanagement.dto.EvidenceDTO;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.qualitycontrol.dao.QualityControlHibernateDAO;
import com.scopix.periscope.qualitycontrol.dao.QualityControlHibernateDAOImpl;

/**
 *
 * @author nelson
 */
public class GetEvidenceDTOCommand {
    private QualityControlHibernateDAO dao;

    public QualityControlHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(QualityControlHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(QualityControlHibernateDAO dao) {
        this.dao = dao;
    }

    public EvidenceDTO execute(Integer evidenceId, Integer evidenceEvaluationId) {
        EvidenceDTO dto = getDao().getEvidenceDTO(evidenceId, evidenceEvaluationId);
        return dto;
    }
}
