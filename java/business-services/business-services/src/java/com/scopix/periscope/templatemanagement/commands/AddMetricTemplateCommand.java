package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.MetricTemplate;

/**
 *
 * @author Gustavo Alvarez
 */
public class AddMetricTemplateCommand {

    public void execute(MetricTemplate metricTemplate) {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        dao.save(metricTemplate);
    }
}
