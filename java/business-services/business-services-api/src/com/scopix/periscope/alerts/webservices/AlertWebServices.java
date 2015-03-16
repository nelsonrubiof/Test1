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
 *  AlertWebServices.java
 * 
 *  Created on 29-05-2012, 10:38:57 AM
 * 
 */
package com.scopix.periscope.alerts.webservices;

import com.scopix.periscope.alerts.webservices.dto.AlertDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author nelson
 */
@WebService(name = "AlertWebServices")
public interface AlertWebServices {

    /**
     * @deprecated retorna las ultimas evaluaciones para una fecha en
     * particular, SituationTemplates, store La estructura del map es Hora,
     * <idMetric, Valor>
     */
    List<AlertDTO> getLastEvaluation(List<Integer> situationTemplateId, Integer storeId, Integer cantGroup) throws ScopixWebServiceException;


    List<AlertDTO> getAllLastEvaluation(List<Integer> situationTemplateId, List<Integer> storeId) throws ScopixWebServiceException;
}