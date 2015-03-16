package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.MetricTemplate;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class RemoveMetricTemplateCommand {

    private Logger log = Logger.getLogger(RemoveMetricTemplateCommand.class);

    public void execute(Integer id) throws ScopixException {
        log.debug("start");
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        try {

            //verificamos que no tenga una relacion con sitation template
            MetricTemplate metricTemplate = dao.get(id, MetricTemplate.class);
            if (metricTemplate.getSituationTemplates() == null || metricTemplate.getSituationTemplates().isEmpty()) {
                dao.remove(metricTemplate);
            } else {
                //"periscopeexception.entity.validate.constraintViolation", new String[]{
                throw new ScopixException("label.metricTemplate");
            }
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("Error = " + objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
            //"periscopeexception.entity.validate.elementNotFound",new String[]{
            throw new ScopixException("label.metricTemplate");
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            log.error("Error = " + dataIntegrityViolationException.getMessage(), dataIntegrityViolationException);
            //"periscopeexception.entity.validate.constraintViolation", new String[]{
            throw new ScopixException("label.metricTemplate");
        }
        log.debug("end");
    }
}
