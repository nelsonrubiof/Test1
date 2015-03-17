package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.SituationTemplate;

/**
 *
 * @author Gustavo Alvarez
 */
public class AddSituationTemplateCommand {

    public void execute(SituationTemplate situationTemplate) {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        dao.save(situationTemplate);
        for (MetricTemplate metricTemplate : situationTemplate.getMetricTemplate()) {
            MetricTemplate mt = dao.get(metricTemplate.getId(), MetricTemplate.class);
            mt.getSituationTemplates().add(situationTemplate);
        }
    }
}
