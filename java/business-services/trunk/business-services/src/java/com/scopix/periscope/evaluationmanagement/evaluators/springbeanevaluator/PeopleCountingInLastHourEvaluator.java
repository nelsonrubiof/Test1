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
@Evaluator(evaluatorType = Evaluator.EvaluatorType.EVIDENCE_MT, description =
"Suma de la cantidad de personas que han ingresado la última hora")
@SpringBean(rootClass = PeopleCountingInLastHourEvaluator.class)
public class PeopleCountingInLastHourEvaluator extends AbstractEvidenceEvaluatorForMT {

    private static Logger log = Logger.getLogger(PeopleCountingInLastHourEvaluator.class);
    public static final String SEPARATOR = "/";

    @Override
	public Boolean evaluate(ObservedMetric om, Integer pendingEvaluationId) throws ScopixException {
        log.debug("inicio evaluacion PeopleCountingInLastHourEvaluator");

        //recorremos todas las evidenciad retornadas por la metrica
        for (Evidence ev : om.getEvidences()) {
            //verificamos que sea un xml
            log.debug("is xml " + FilenameUtils.isExtension(ev.getEvidencePath(), "xml"));
            if (FilenameUtils.isExtension(ev.getEvidencePath(), "xml")) {
                String path = getEvidencePath(ev, om);
                int suma = 0;
                String pathFile = FilenameUtils.separatorsToSystem(path + ev.getEvidencePath());
                try {
                    log.debug("evaluate] evidencePath " + pathFile);
                    List<PeopleCountingDTO> peopleCountingDTOs = PeopleCountingUtil.parserXmlPeopleCounting(pathFile);
                    if (peopleCountingDTOs != null) {
                        /**
                         * > ini e.getEvidenceDate()  <= fin (e.getEvidenceDate()) + 1 hora
                         * se recorren todos los registros y se evaluan solo los que esten en el rango de la hora desde
                         * la fecha de la evidencia
                         */
                        Calendar iniCiclo = Calendar.getInstance();
                        iniCiclo.setTime(ev.getEvidenceDate());
                        //iniCiclo.add(Calendar.HOUR_OF_DAY, -1);

                        Calendar finCiclo = Calendar.getInstance();
                        finCiclo.setTime(ev.getEvidenceDate());
                        finCiclo.add(Calendar.HOUR_OF_DAY, 1);

                        for (PeopleCountingDTO dto : peopleCountingDTOs) {
                            if (dto.getDate().getTime() > iniCiclo.getTimeInMillis()
                                    && dto.getDate().getTime() <= finCiclo.getTimeInMillis()) {
                                suma = suma + dto.getValueIn();
                            }
                        }

                        saveEvidenceEvaluation(suma, ev, om, pendingEvaluationId, this.getClass().getSimpleName());
                    }
                } catch (Exception e) {
                    log.error(e, e);

                }
            }
        }
		return true;
    }


}
