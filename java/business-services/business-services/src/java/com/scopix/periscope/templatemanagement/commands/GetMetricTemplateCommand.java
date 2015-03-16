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
public class GetMetricTemplateCommand {

    private Logger log = Logger.getLogger(GetMetricTemplateCommand.class);

    public MetricTemplate execute(Integer id) throws ScopixException {
        log.debug("start");
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        MetricTemplate metricTemplate = null;

        try {
            metricTemplate = dao.get(id, MetricTemplate.class);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("Error = " + objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
            //"periscopeexception.entity.validate.elementNotFound",new String[]{
            throw new ScopixException("label.matricTemplate");
        }
        log.debug("end");
        return metricTemplate;
    }
}
