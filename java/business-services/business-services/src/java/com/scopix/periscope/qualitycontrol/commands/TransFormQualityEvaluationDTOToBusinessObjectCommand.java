/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  TransFormQualityEvaluationDTOToBusinessObjectCommand.java
 * 
 *  Created on 14-12-2011, 03:31:04 PM
 * 
 */
package com.scopix.periscope.qualitycontrol.commands;

import com.scopix.periscope.evaluationmanagement.ObservedSituation;
import com.scopix.periscope.qualitycontrol.Clasificacion;
import com.scopix.periscope.qualitycontrol.MotivoRejected;
import com.scopix.periscope.qualitycontrol.QualityEvaluation;
import com.scopix.periscope.qualitycontrol.QualityEvaluationType;
import com.scopix.periscope.qualitycontrol.dto.QualityEvaluationDTO;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.lang.time.DateUtils;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class TransFormQualityEvaluationDTOToBusinessObjectCommand {

    private static Logger log = Logger.getLogger(TransFormQualityEvaluationDTOToBusinessObjectCommand.class);

    public List<QualityEvaluation> execute(List<QualityEvaluationDTO> qedtos) {
        List<QualityEvaluation> result = new ArrayList<QualityEvaluation>();
        for (QualityEvaluationDTO dto : qedtos) {
            try {
                QualityEvaluation qe = new QualityEvaluation();
                qe.setFechaRevision(new Date());
                qe.setEvaluationUser(dto.getUser());
                qe.setMessageOperator(dto.getMessageOperator());
                qe.setObservaciones(dto.getObservaciones());
                qe.setOperador(dto.getOperador());
                qe.setFechaEvidencia(DateUtils.parseDate(dto.getFechaEvidencia(), new String[]{"yyyy-MM-dd HH:mm"}));
                qe.setQualityEvaluationType(QualityEvaluationType.valueOf(dto.getResult()));
                if (dto.getClasificacion() != null && dto.getClasificacion() > 0) {
                    Clasificacion clasificacion = new Clasificacion();
                    clasificacion.setId(dto.getClasificacion());
                    qe.setClasificacion(clasificacion);
                }
                if (dto.getMotivoRechazo() != null && dto.getMotivoRechazo() > 0) {
                    MotivoRejected motivoRejected = new MotivoRejected();
                    motivoRejected.setId(dto.getMotivoRechazo());
                    qe.setMotivoRechazo(motivoRejected);
                }

                ObservedSituation os = new ObservedSituation();
                os.setId(dto.getObservedSituationId());
                qe.setObservedSituation(os);
                result.add(qe);
            } catch (ParseException e) {
                log.error("No es posible transformar dto " + e, e);
            }
        }
        return result;
    }
}
