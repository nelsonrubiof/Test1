package mockup.evaluationmanagement.dao;

import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.evaluationmanagement.dao.ObservedSituationHibernateDAO;
import com.scopix.periscope.extractionplanmanagement.CompliantTypeEnum;
import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.extractionplanmanagement.FormulaTypeEnum;
import com.scopix.periscope.extractionplanmanagement.dto.IndicatorValuesDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ObservedSituationEvaluationDTO;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author marko.perich
 */
public class ObservedSituationHibernateDAOMock implements ObservedSituationHibernateDAO{

    @Override
    public List<Formula> getFormulaList(Formula filter) {
        List<Formula> formulas = new ArrayList<Formula>();


        Formula formula = new Formula();
        formula.setType(FormulaTypeEnum.COMPLIANCE);
        formula.setCompliantType(CompliantTypeEnum.COMPLIANT);
        formula.setFormula("1");
        formula.setStandard(new Double(0));
        formula.setTarget(new Double(1));
        formulas.add(formula);

        formula = new Formula();
        formula.setType(FormulaTypeEnum.COMPLIANCE);
        formula.setCompliantType(CompliantTypeEnum.NOT_COMPLIANT);
        formula.setFormula("1");
        formula.setStandard(new Double(0));
        formula.setTarget(new Double(1));
        formulas.add(formula);

        formula = new Formula();
        formula.setType(FormulaTypeEnum.INDICATOR);
        formula.setFormula("1");
        formula.setFormula("10");
        formula.setDenominator("2");
        formulas.add(formula);

        return formulas;
    }

    @Override
    public List<IndicatorValuesDTO> getIndicatorValuesDTOs(Date startDate, Date endDate, List<Integer> situationTemplateIds, List<Integer> storeIds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ObservedSituationEvaluationDTO> getObservedSituationEvaluationDTOs(Date startDate, Date endDate, List<Integer> situationTemplateIds, List<Integer> storeIds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ObservedSituation getObservedSituationForADay(Integer situationId, Date day) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ObservedSituation> getObservedSituationList(ObservedSituation observedSituation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ObservedSituation> getObservedSituationListSQL(ObservedSituation observedSituation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Integer> getObservedSituationListSQL(int storeId, Date observedSituationDate, Integer[] situationTemplateIds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
