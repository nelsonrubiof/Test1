/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator;

import java.util.Calendar;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;

import com.scopix.periscope.evaluationmanagement.Evidence;
import com.scopix.periscope.evaluationmanagement.ObservedMetric;
import com.scopix.periscope.evaluationmanagement.evaluators.springbeanevaluator.dto.PeopleCountingDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;

/**
 *
 * @author Cesar
 */
@Evaluator(evaluatorType = Evaluator.EvaluatorType.EVIDENCE_MT, description
    = "Suma de la cantidad de personas que han ingresado en los últimos 15 minutos")
@SpringBean(rootClass = PeopleCountingInLastFifteenMinutesEvaluator.class)
public class PeopleCountingInLastFifteenMinutesEvaluator extends AbstractEvidenceEvaluatorForMT {

    private static final Logger log = Logger.getLogger(PeopleCountingInLastFifteenMinutesEvaluator.class);

    @Override
    public Boolean evaluate(ObservedMetric om, Integer pendingEvaluationId) throws ScopixException {
        log.info("start");

        //recorremos todas las evidenciad retornadas por la metrica
        for (Evidence ev : om.getEvidences()) {
            //verificamos que sea un xml
            log.debug("is xml " + FilenameUtils.isExtension(ev.getEvidencePath(), "xml"));
            if (FilenameUtils.isExtension(ev.getEvidencePath(), "xml")) {
                String path = getEvidencePath(ev, om);
                int suma = 0;
                String pathFile = FilenameUtils.separatorsToSystem(path + ev.getEvidencePath());
                try {
                    /**
                     * > ini e.getEvidenceDate() <= fin (e.getEvidenceDate()) + 15 Minutos se recorren todos los registros y se
                     * evaluan solo los que esten en el rango de los 15 minutos desde la fecha de la evidencia
                     */

                    log.debug("evidencePath " + pathFile);
                    List<PeopleCountingDTO> peopleCountingDTOs = PeopleCountingUtil.parserXmlPeopleCounting(pathFile);
                    if (peopleCountingDTOs != null) {
                        Calendar iniCiclo = Calendar.getInstance();
                        iniCiclo.setTime(ev.getEvidenceDate());
                        //iniCiclo.add(Calendar.MINUTE, -15);
                        Calendar finCiclo = Calendar.getInstance();
                        finCiclo.setTime(ev.getEvidenceDate());
                        finCiclo.add(Calendar.MINUTE, 15);

                        for (PeopleCountingDTO dto : peopleCountingDTOs) {
                            if (dto.getDate().getTime() > iniCiclo.getTimeInMillis()
                                && dto.getDate().getTime() <= finCiclo.getTimeInMillis()) {
                                suma = suma + dto.getValueIn();
                            }
                        }

                        saveEvidenceEvaluation(suma, ev, om, pendingEvaluationId, this.getClass().getSimpleName());
                    }
                } catch (ScopixException e) {
                    log.error(e, e);

                }
            }
        }
        log.info("end");
        return true;
    }

//    private void saveEvidenceEvaluation(int suma, Evidence ev, ObservedMetric om, Integer pendingEvaluationId, String evaluationUser) {
//        log.debug("suma:" + suma + ", Evidence:" + ev.getId() + " , ObservedMetric:" + om.getId()
//                + ", pendingEvaluationId:" + pendingEvaluationId + ", evaluationUser:" + evaluationUser);
//        EvidenceEvaluation ee = new EvidenceEvaluation();
//        ee.setEvaluationDate(new Date());
//        ee.setEvaluationUser(evaluationUser);
//        ee.setEvidenceResult(suma);
//        ee.getEvidences().add(ev);
//        ee.setObservedMetric(om);
//        ee.setPendingEvaluation(new PendingEvaluation());
//        ee.getPendingEvaluation().setId(pendingEvaluationId);
//        // se le agrega la fecha actual
//        ee.setInitEvaluation(new Date());
//        ee.setEndEvaluation(new Date());
//
//        getDao().save(ee);
//    }
}
