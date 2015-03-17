package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import org.apache.log4j.Logger;
import org.springframework.orm.ObjectRetrievalFailureException;

/**
 * 
 * @author Gustavo Alvarez
 */
public class GetSituationTemplateCommand {

    private Logger log = Logger.getLogger(GetSituationTemplateCommand.class);

    public SituationTemplate execute(Integer id) throws ScopixException {
        log.debug("start");
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();

        SituationTemplate situationTemplate = null;

        try {
            situationTemplate = dao.get(id, SituationTemplate.class);
        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("Error = " + objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
            //"periscopeexception.entity.validate.elementNotFound", new String[]{
            throw new ScopixException("label.situationTemplate");
        }
        log.debug("end");
        return situationTemplate;
    }
}
