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
 * SaveMetricCommand.java
 * 
 * Created on 11-07-2014, 03:59:27 PM
 */
package com.scopix.periscope.evaluationmanagement.commands;

import org.apache.log4j.Logger;

import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAOImpl;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

/**
 *
 * @author Nelson
 */
public class SaveMetricCommand {

    private static final Logger log = Logger.getLogger(SaveMetricCommand.class);

    private EvaluationHibernateDAO evaluationHibernateDAO;
    private GenericDAO genericDAO;

    public void execute(Metric m) throws ScopixException {
        log.debug("almacenando new metric");
        m.setId(getEvaluationHibernateDAO().getNextIdForMetric());
        getEvaluationHibernateDAO().saveMetric(m);

        // Why do we do this??
        // getGenericDAO().save(m);
    }

    public EvaluationHibernateDAO getEvaluationHibernateDAO() {
        if (evaluationHibernateDAO == null) {
            evaluationHibernateDAO = SpringSupport.getInstance().findBeanByClassName(EvaluationHibernateDAOImpl.class);
        }
        return evaluationHibernateDAO;
    }

    public void setEvaluationHibernateDAO(EvaluationHibernateDAO evaluationHibernateDAO) {
        this.evaluationHibernateDAO = evaluationHibernateDAO;
    }

    public GenericDAO getGenericDAO() {
        if (genericDAO == null) {
            genericDAO = HibernateSupport.getInstance().findGenericDAO();
        }
        return genericDAO;
    }

    public void setGenericDAO(GenericDAO genericDAO) {
        this.genericDAO = genericDAO;
    }
}
