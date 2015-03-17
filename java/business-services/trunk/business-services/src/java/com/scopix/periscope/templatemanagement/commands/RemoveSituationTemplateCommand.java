package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import org.apache.log4j.Logger;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 *
 * @author Gustavo Alvarez
 */
public class RemoveSituationTemplateCommand {

    private Logger log = Logger.getLogger(RemoveSituationTemplateCommand.class);

    public void execute(Integer id) throws ScopixException {
        log.debug("start");
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        try {
            //validamos que no contenga metricTemplate Asociados
            SituationTemplate situationTemplate = dao.get(id, SituationTemplate.class);
            if (situationTemplate.getMetricTemplate() == null || situationTemplate.getMetricTemplate().isEmpty()) {
                dao.remove(situationTemplate);
            } else {
                //"periscopeexception.entity.validate.constraintViolation", new String[]{
                throw new ScopixException("label.situationTemplate");
            }
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("Error = " + objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
            //"periscopeexception.entity.validate.elementNotFound", new String[]{
            throw new ScopixException("label.situationTemplate");
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            log.error("Error = " + dataIntegrityViolationException.getMessage(), dataIntegrityViolationException);
            //"periscopeexception.entity.validate.constraintViolation", new String[]{
            throw new ScopixException("label.situationTemplate");
        }
        log.debug("end");
    }
}
