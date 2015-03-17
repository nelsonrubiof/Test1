package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class UpdateMetricTemplateCommand {

    private Logger log = Logger.getLogger(UpdateMetricTemplateCommand.class);

    public void execute(MetricTemplate metricTemplate) throws ScopixException {
        log.debug("start");
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        try {
            MetricTemplate mt = dao.get(metricTemplate.getId(), MetricTemplate.class);
            mt.setName(metricTemplate.getName());
            mt.setDescription(metricTemplate.getDescription());
            mt.setOperatorDescription(metricTemplate.getOperatorDescription());
            mt.setEvaluationInstruction(metricTemplate.getEvaluationInstruction());
            mt.setEvidenceSpringBeanEvaluatorName(metricTemplate.getEvidenceSpringBeanEvaluatorName());
            mt.setEvidenceTypeElement(metricTemplate.getEvidenceTypeElement());
            mt.setMetricSpringBeanEvaluatorName(metricTemplate.getMetricSpringBeanEvaluatorName());
            mt.setMetricTypeElement(metricTemplate.getMetricTypeElement());
            mt.setYesNoType(metricTemplate.getYesNoType());
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("Error = " + objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
            //"periscopeexception.entity.validate.elementNotFound",new String[]{
            throw new ScopixException("label.metricTemplate");
        }
        log.debug("end");
    }
}
