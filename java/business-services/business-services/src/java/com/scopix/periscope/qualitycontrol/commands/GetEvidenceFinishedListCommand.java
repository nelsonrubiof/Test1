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
 * GetEvidenceFinishedCommand.java
 *
 * Created on 04-06-2008, 06:37:17 PM
 *
 */
package com.scopix.periscope.qualitycontrol.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.qualitycontrol.dao.QualityControlHibernateDAO;
import com.scopix.periscope.qualitycontrol.dao.QualityControlHibernateDAOImpl;
import com.scopix.periscope.qualitycontrol.dto.EvidenceFinishedDTO;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class GetEvidenceFinishedListCommand {

    private Logger log = Logger.getLogger(GetEvidenceFinishedListCommand.class);
    private QualityControlHibernateDAO dao;

    public List<EvidenceFinishedDTO> execute(Date start, Date end, boolean rejected) throws ScopixException {
        log.debug("start");
        List<EvidenceFinishedDTO> evidenceFinishedDTOs = null;
        if ((start != null && end != null) && (start.before(end) || start.equals(end))) {

            evidenceFinishedDTOs = getDao().getEvidenceFinishedList(start, end, rejected);
            log.debug("end, result = " + evidenceFinishedDTOs);
        } else {
            throw new ScopixException("Dates are required");
        }
        return evidenceFinishedDTOs;
    }

    public QualityControlHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(QualityControlHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(QualityControlHibernateDAO dao) {
        this.dao = dao;
    }
}
