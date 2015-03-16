/*
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * MetricEvaluatorManager.java
 *
 * Created on 10-06-2008, 02:11:14 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.dao.ObservedSituationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.ObservedSituationHibernateDAOImpl;
import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 */
public class FormulasBySTAndStoreCommand {

   private ObservedSituationHibernateDAO dao;
   private static Logger log = Logger.getLogger(FormulasBySTAndStoreCommand.class);


    public List<Formula> execute(SituationTemplate situationTemplate, Store store){
        log.debug("storeId: " + store.getId() + ", situationTemplateId: " + situationTemplate.getId());
        List<Formula> formulas = null;

        Formula filter = new Formula();
        filter.getStores().add(store);
        filter.getSituationTemplates().add(situationTemplate);

        formulas = getDao().getFormulaList(filter);

        return formulas;
    }

    public ObservedSituationHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(ObservedSituationHibernateDAOImpl.class);
        }
        return dao;
    }

    public void setDao(ObservedSituationHibernateDAO dao) {
        this.dao = dao;
    }


}
