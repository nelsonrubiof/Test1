/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.extractionplanmanagement.FormulaTypeEnum;
import com.scopix.periscope.evaluationmanagement.Indicator;
import com.scopix.periscope.evaluationmanagement.IndicatorProductAndAreaType;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author Cesar Abarza
 */
public class AddFormulaCommand {

    public void execute(Formula formula) {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        dao.save(formula);
        if (formula.getType().equals(FormulaTypeEnum.INDICATOR)) {
            Indicator indicator = formula.getIndicator();
            indicator.setFormula(formula);
            dao.save(indicator);
            for (IndicatorProductAndAreaType ipaat : indicator.getIndicatorProductAndAreaTypes()) {
                ipaat.setIndicator(indicator);
                dao.save(ipaat);
            }
        }
    }
}
