package com.scopix.periscope.evaluationmanagement.dao;

import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.extractionplanmanagement.dto.IndicatorValuesDTO;
import com.scopix.periscope.extractionplanmanagement.dto.ObservedSituationEvaluationDTO;
import java.util.Date;
import java.util.List;


/**
 *
 * @author marko.perich
 */
public interface ObservedSituationHibernateDAO {

    List<Formula> getFormulaList(Formula formula);

    List<IndicatorValuesDTO> getIndicatorValuesDTOs(Date startDate, Date endDate,
            List<Integer> situationTemplateIds, List<Integer> storeIds);

    List<ObservedSituationEvaluationDTO> getObservedSituationEvaluationDTOs(Date startDate, Date endDate,
            List<Integer> situationTemplateIds, List<Integer> storeIds);

    ObservedSituation getObservedSituationForADay(Integer situationId, Date day);

    List<ObservedSituation> getObservedSituationList(ObservedSituation observedSituation);

    List<ObservedSituation> getObservedSituationListSQL(ObservedSituation observedSituation);
    
    /**
     * Retorna lista de ObservedSituation sin pending Evaluation para la Fecha indica y el Store asociado
     */
    List<Integer> getObservedSituationListSQL(int storeId, Date observedSituationDate, Integer[] situationTemplateIds);

}
