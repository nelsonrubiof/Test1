package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.dao.TemplateManagementHibernateDAOImpl;
import java.util.Collections;
import java.util.List;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class UpdateSituationTemplateCommand {

    private Logger log = Logger.getLogger(UpdateSituationTemplateCommand.class);

    public void execute(SituationTemplate situationTemplate) throws ScopixException {
        log.debug("start");
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        TemplateManagementHibernateDAOImpl templateDAO = SpringSupport.getInstance().findBeanByClassName(
                TemplateManagementHibernateDAOImpl.class);
        try {
            SituationTemplate aux = dao.get(situationTemplate.getId(), SituationTemplate.class);
            aux.setName(situationTemplate.getName());
            aux.setProduct(situationTemplate.getProduct());
            aux.setAreaType(situationTemplate.getAreaType());
            aux.setActive(situationTemplate.getActive());
            aux.setLive(situationTemplate.isLive());
            aux.setDelayInMinutes(situationTemplate.getDelayInMinutes());
            aux.setEvidenceSpringBeanEvaluatorName(situationTemplate.getEvidenceSpringBeanEvaluatorName());
            List<MetricTemplate> metricTemplates = templateDAO.getMetricTemplateForASituationTemplate(situationTemplate.getId());
            for (MetricTemplate metricTemplate : metricTemplates) {
                Collections.sort(metricTemplate.getSituationTemplates());
                int index = Collections.binarySearch(metricTemplate.getSituationTemplates(), situationTemplate);
                if (index >= 0) {
                    metricTemplate.getSituationTemplates().remove(index);
                }
            }
            for (MetricTemplate metricTemplate : situationTemplate.getMetricTemplate()) {
                MetricTemplate mt = dao.get(metricTemplate.getId(), MetricTemplate.class);
                mt.getSituationTemplates().add(situationTemplate);
            }

        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("Error = " + objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
            //"periscopeexception.entity.validate.elementNotFound", new String[]{
            throw new ScopixException("label.situationTemplate");
        }
        log.debug("end");
    }
}
