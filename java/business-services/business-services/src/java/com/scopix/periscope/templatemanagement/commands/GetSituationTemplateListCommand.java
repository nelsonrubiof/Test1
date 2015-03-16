package com.scopix.periscope.templatemanagement.commands;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.dao.TemplateManagementHibernateDAO;
import com.scopix.periscope.templatemanagement.dao.TemplateManagementHibernateDAOImpl;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetSituationTemplateListCommand {

    private TemplateManagementHibernateDAO dao;
    private static Logger log = Logger.getLogger(GetSituationTemplateListCommand.class);

    public List<SituationTemplate> execute(SituationTemplate situationTemplate) throws ScopixException {
        log.info("start");

        List<SituationTemplate> situationTemplateList = getDao().getSituationTemplateList(situationTemplate);
        log.info("end");
        return situationTemplateList;
    }

    public TemplateManagementHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(TemplateManagementHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(TemplateManagementHibernateDAO dao) {
        this.dao = dao;
    }
}
