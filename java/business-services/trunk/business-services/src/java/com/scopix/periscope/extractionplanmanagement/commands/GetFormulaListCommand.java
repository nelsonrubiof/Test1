/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.evaluationmanagement.dao.ObservedSituationHibernateDAO;
import com.scopix.periscope.evaluationmanagement.dao.ObservedSituationHibernateDAOImpl;
import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;

/**
 *
 * @author Cesar Abarza
 */
public class GetFormulaListCommand {

    public List<Formula> execute(Formula formula) {
        ObservedSituationHibernateDAO dao = SpringSupport.getInstance().findBeanByClassName(
                ObservedSituationHibernateDAOImpl.class);
        List<Formula> formulas = dao.getFormulaList(formula);
        for (Formula f: formulas) {
            f.getSituationTemplates().isEmpty();
            f.getStores().isEmpty();
        }
        return formulas;
    }
}
