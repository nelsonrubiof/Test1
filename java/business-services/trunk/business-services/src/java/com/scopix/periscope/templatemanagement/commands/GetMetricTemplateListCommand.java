package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import com.scopix.periscope.templatemanagement.dao.TemplateManagementHibernateDAOImpl;
import java.util.List;
import org.apache.log4j.Logger;

/**
 * 
 * @author Gustavo Alvarez
 */
public class GetMetricTemplateListCommand {

    private Logger log = Logger.getLogger(GetMetricTemplateListCommand.class);
    private TemplateManagementHibernateDAOImpl daoHibernate;

    public List<MetricTemplate> execute(MetricTemplate metricTemplate) throws ScopixException {
        log.debug("start");

        TemplateManagementHibernateDAOImpl dao = getDaoHibernate();
        List<MetricTemplate> metricTemplates = dao.getMetricTemplateList(metricTemplate);
        log.debug("end");
        return metricTemplates;
    }

    public TemplateManagementHibernateDAOImpl getDaoHibernate() {
        if (daoHibernate == null) {
            daoHibernate = SpringSupport.getInstance().findBeanByClassName(TemplateManagementHibernateDAOImpl.class);
        }
        return daoHibernate;
    }

    public void setDaoHibernate(TemplateManagementHibernateDAOImpl daoHibernate) {
        this.daoHibernate = daoHibernate;
    }
}
