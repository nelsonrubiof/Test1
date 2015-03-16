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
 * RejectEvidenceFinishedCommand.java
 *
 * Created on 11-03-2009, 09:37:17 AM
 *
 */
package com.scopix.periscope.qualitycontrol.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.qualitycontrol.dao.QualityControlHibernateDAO;
import com.scopix.periscope.qualitycontrol.dao.QualityControlHibernateDAOImpl;
import java.util.Set;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class RejectEvidenceByObservedSituationsCommand {

    private Logger log = Logger.getLogger(RejectEvidenceByObservedSituationsCommand.class);
    private QualityControlHibernateDAO dao;

    public void execute(Set<Integer> observedSituationIds, String comments) throws ScopixException {
        log.debug("start");
        getDao().rejectsEvaluationsByObservedSituation(observedSituationIds, comments);
        log.debug("end");
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
