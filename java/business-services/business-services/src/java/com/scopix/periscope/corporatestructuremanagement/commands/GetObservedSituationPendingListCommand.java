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
 *  GetObservedSituationPendingListCommand.java
 * 
 *  Created on 23-08-2012, 12:16:00 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.evaluationmanagement.dao.ObservedSituationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.ObservedSituationHibernateDAOImpl;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author nelson
 */
public class GetObservedSituationPendingListCommand {

    private static Logger log = Logger.getLogger(GetObservedSituationPendingListCommand.class);
    private ObservedSituationHibernateDAO dao;
    private GenericDAO genericDao;

    public ObservedSituationHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ObservedSituationHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(ObservedSituationHibernateDAO dao) {
        this.dao = dao;
    }

    public List<ObservedSituation> execute(Integer storeId, Date closeSituationDate, Integer[] situationTemplateIds)
            throws ScopixException {
        log.info("start storeId:" + storeId + " date:" + closeSituationDate);
        List<ObservedSituation> observedSituations = new ArrayList<ObservedSituation>();
        try {
            List<Integer> osIds = getDao().getObservedSituationListSQL(storeId, closeSituationDate, situationTemplateIds);
            for (Integer osId : osIds) {
                ObservedSituation os = getGenericDao().get(osId, ObservedSituation.class);
                observedSituations.add(os);
            }
            log.debug("size " + observedSituations.size());
        } catch (ObjectRetrievalFailureException e) {
            throw new ScopixException("periscopeexception.evidenceManagement.observedSituation.elementNotFound", e);
        }
        log.info("end");
        return observedSituations;

    }

    public GenericDAO getGenericDao() {
        if (genericDao == null) {
            genericDao = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDao;
    }

    public void setGenericDao(GenericDAO genericDao) {
        this.genericDao = genericDao;
    }
}
