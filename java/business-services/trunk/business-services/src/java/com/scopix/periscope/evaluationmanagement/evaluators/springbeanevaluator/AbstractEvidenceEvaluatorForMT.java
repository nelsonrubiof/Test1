/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator;

import java.util.Date;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.configuration.reloading.FileChangedReloadingStrategy;
import org.apache.log4j.Logger;

import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.EvidenceEvaluation;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import com.scopix.periscope.evaluationmanagement.evaluators.EvidenceEvaluatorForMT;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.FileUtils;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author nelson
 */
public abstract class AbstractEvidenceEvaluatorForMT implements EvidenceEvaluatorForMT {

    private GenericDAO dao;
    public static final String SEPARATOR = "/";
    private static final Logger log = Logger.getLogger(AbstractEvidenceEvaluatorForMT.class);
    private PropertiesConfiguration propertiesConfiguration;

    public GenericDAO getDao() {
        if (dao == null) {
            dao = HibernateSupport.getInstance().findGenericDAO();
        }
        return dao;
    }

    @Override
    public void setDao(GenericDAO value) {
        this.dao = value;
    }

    public void saveEvidenceEvaluation(int suma, Evidence ev, ObservedMetric om, Integer pendingEvaluationId, String evaluationUser) {
        log.debug("suma:" + suma + ", Evidence:" + ev.getId() + " , ObservedMetric:" + om.getId() + ", pendingEvaluationId:"
                + pendingEvaluationId + ", evaluationUser:" + evaluationUser);
        EvidenceEvaluation ee = new EvidenceEvaluation();
        ee.setEvaluationDate(new Date());
        ee.setEvaluationUser(evaluationUser);
        ee.setEvidenceResult(suma);
        ee.getEvidences().add(ev);
        ee.setObservedMetric(om);
        ee.setPendingEvaluation(new PendingEvaluation());
        ee.getPendingEvaluation().setId(pendingEvaluationId);
        // se le agrega la fecha actual
        ee.setInitEvaluation(new Date());
        ee.setEndEvaluation(new Date());

        getDao().save(ee);
    }

    public String getEvidencePath(Evidence e, ObservedMetric om) {
        StringBuilder p = new StringBuilder();
        p.append("/data/ftp/evidence/");
        p.append(om.getMetric().getArea().getStore().getCorporate().getName());
        p.append(SEPARATOR).append(om.getMetric().getArea().getStore().getName());
        p.append(SEPARATOR).append(FileUtils.getPathFromDate(e.getEvidenceDate(), SEPARATOR));
        p.append(SEPARATOR);
        return p.toString();
    }
    public String getProofPath(Evidence e, ObservedMetric om) {
        StringBuilder p = new StringBuilder();
        p.append("/data/ftp/proofs/");
        p.append(om.getMetric().getArea().getStore().getCorporate().getName());
        p.append(SEPARATOR).append(om.getMetric().getArea().getStore().getName());
        p.append(SEPARATOR).append(FileUtils.getPathFromDate(e.getEvidenceDate(), SEPARATOR));
        p.append(SEPARATOR);
        return p.toString();
    }

    public PropertiesConfiguration getPropertiesConfiguration() throws ScopixException {
        if (propertiesConfiguration == null) {
            try {
                propertiesConfiguration = new PropertiesConfiguration("system.properties");
                propertiesConfiguration.setReloadingStrategy(new FileChangedReloadingStrategy());
            } catch (ConfigurationException ex) {
                log.error("ConfigurationException", ex);
                throw new ScopixException("Error", ex);
            }
        }
        return propertiesConfiguration;
    }

}
