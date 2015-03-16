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
 * AddEvidenceCommand.java
 *
 * Created on 08-05-2008, 03:21:19 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.EvaluationHibernateDAOImpl;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class AddAutomaticMetricCommand {

    private static Logger log = Logger.getLogger(AddAutomaticMetricCommand.class);
    private EvaluationHibernateDAO evaluationHibernateDAO;
    private GenericDAO genericDAO;

    public Metric execute(Metric metric, Integer evidenceProviderId) throws ScopixException {
        log.info("start");

        Metric result;
        Integer processId = metric.getSituation().getProcessId();
        Integer situationTemplateId = metric.getSituation().getSituationTemplate().getId();
        Integer metricTemplateId = metric.getMetricTemplate().getId();
        log.debug("processId:" + processId + ", situationTemplateId:" + situationTemplateId
                + ", metricTemplateId:" + metricTemplateId + ", evidenceProviderId:" + evidenceProviderId);
        Integer metricId = getEvaluationHibernateDAO().getMetricIdForAProcessId(processId, situationTemplateId, metricTemplateId,
                evidenceProviderId);
//        result = getEvaluationHibernateDAO().getMetricForAProcessId(processId, situationTemplateId, metricTemplateId,
//                evidenceProviderId);
        if (metricId != null) {
            //si existe levantamos desde base de datos
            result = getGenericDAO().get(metricId, Metric.class);
        } else { 
            //si no existe la creamos
            log.debug("almacenando new metric");
            metric.setId(getEvaluationHibernateDAO().getNextIdForMetric());
            getEvaluationHibernateDAO().saveMetric(metric);
            getGenericDAO().save(metric);
            result = metric;
        }
        log.info("end [metric:" + metric + "]");
        return result;
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
