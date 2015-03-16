/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.extractionplanmanagement.commands;

import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.evaluationmanagement.Indicator;
import com.scopix.periscope.evaluationmanagement.IndicatorProductAndAreaType;
import com.scopix.periscope.periscopefoundation.persistence.GenericDAO;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author Cesar Abarza
 */
public class EditFormulaCommand {

    public void execute(Formula formula) {
        GenericDAO dao = HibernateSupport.getInstance().findGenericDAO();
        if (formula.getIndicator() != null && formula.getIndicator().getId() != null && formula.getIndicator().getId() > 0) {
            Indicator indicator = formula.getIndicator();
            Indicator indicadorAux = dao.get(indicator.getId(), Indicator.class);
            for (IndicatorProductAndAreaType ipaat : indicadorAux.getIndicatorProductAndAreaTypes()) {
                dao.remove(ipaat.getId(), IndicatorProductAndAreaType.class);
            }
            indicadorAux.setEndingTime(indicator.getEndingTime());
            indicadorAux.setInitialTime(indicator.getInitialTime());
            indicadorAux.setLabelX(indicator.getLabelX());
            indicadorAux.setLabelY(indicator.getLabelY());
            indicadorAux.setName(indicator.getName());
            for (IndicatorProductAndAreaType ipaat : indicator.getIndicatorProductAndAreaTypes()) {
                ipaat.setIndicator(indicadorAux);
                dao.save(ipaat);
            }
        }

        Formula aux = dao.get(formula.getId(), Formula.class);

        aux.setCompliantType(formula.getCompliantType());
        aux.setDescription(formula.getDescription());
        aux.setFormula(formula.getFormula());
        aux.setSituationTemplates(formula.getSituationTemplates());
        aux.setStores(formula.getStores());
        aux.setType(formula.getType());
        aux.setVariables(formula.getVariables());
        aux.setDenominator(formula.getDenominator());
        aux.setObservations(formula.getObservations());
        aux.setIndicator(formula.getIndicator());
        aux.setTarget(formula.getTarget());
        aux.setStandard(formula.getStandard());
        aux.setIndicator(formula.getIndicator());
    }
}
