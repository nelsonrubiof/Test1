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
public class UpdateActiveStateSituationTemplateCommand {

    private Logger log = Logger.getLogger(UpdateActiveStateSituationTemplateCommand.class);

    public void execute(SituationTemplate situationTemplate) throws ScopixException {
        log.debug("start");
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        try {
            SituationTemplate aux = dao.get(situationTemplate.getId(), SituationTemplate.class);
            aux.setActive(situationTemplate.getActive());

        } catch (ObjectRetrievalFailureException objectRetrievalFailureException) {
            log.error("Error = " + objectRetrievalFailureException.getMessage(), objectRetrievalFailureException);
            //"periscopeexception.entity.validate.elementNotFound", new String[]{
            throw new ScopixException("label.situationTemplate");
        }
        log.debug("end");
    }
}
