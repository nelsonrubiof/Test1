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
 *  PendingEvaluationHibernateDAO.java
 * 
 *  Created on 05-06-2013, 03:16:27 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.dao;

import com.scopix.periscope.evaluationmanagement.PendingEvaluation;
import java.util.List;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public interface PendingEvaluationHibernateDAO {

    /**
     * Busca una lista de PendingEvaluation
     * @param pendingEvaluation filtros
     * @return List<PendingEvaluation> con pending evaluation para filtros entregados
     * @deprecated revisar si es necesario sino eliminar
     */
    @Deprecated
    List<PendingEvaluation> findPendingEvaluationListSQL(PendingEvaluation pendingEvaluation);

    /**
     * Busca un Pending Evaluacion para un observedSituation asociado
     *
     * @param observedSituationId id observedsituation
     * @return PendingEvaluation
     */
    PendingEvaluation getPendingEvaluationFromObservedSituationId(Integer observedSituationId);
    
    /**
     * retorna la cantidad de pending evaluation existen para un observed_situation
     *
     * @param observedSituationId id observedsituation
     * @return PendingEvaluation
     */
    Integer getCountPendingEvaluationFromObservedSituationId(Integer observedSituationId);
    
}
