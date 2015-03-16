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
 *  AlertWebServicesImpl.java
 * 
 *  Created on 31-05-2012, 05:06:53 PM
 * 
 */
package com.scopix.periscope.alert.services;

import com.scopix.periscope.alerts.webservices.AlertWebServices;
import com.scopix.periscope.alerts.webservices.dto.AlertDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.ReportingManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.jws.WebService;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author nelson
 * @version 2.0.0
 */
@WebService(endpointInterface = "com.scopix.periscope.alerts.webservices.AlertWebServices")
@SpringBean(rootClass = AlertWebServices.class)
public class AlertWebServicesImpl implements AlertWebServices {

    /**
     * Recupera la ultimas evaluciones registradas en Reporting Data
     *
     * @param situationTemplatesIds situaciones de las cuales se necesitan los
     * datos
     * @param storeId store para el cual se necesita la data
     * @param cantGroup cantidad de grupos deseados
     * @param sessionId id de session del usuario conectado
     * @return List<AlertDTO> lista de datos solicitados
     * @throws
     * com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException
     */
    @Override
    public List<AlertDTO> getLastEvaluation(List<Integer> situationTemplatesIds, Integer storeId,
            Integer cantGroup) throws ScopixWebServiceException {
        List<AlertDTO> ret = null;
        try {
            Map<String, Map<String, Double>> map = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).
                    getLastEvaluation(situationTemplatesIds, storeId, cantGroup);
            ret = new ArrayList<AlertDTO>();
            for (String key : map.keySet()) {
                AlertDTO dto = new AlertDTO();
                dto.setEvidenceDate(key);
                dto.setData(map.get(key));
                ret.add(dto);
            }
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return ret;
    }

    /**
     * Recupera la ultimas evaluciones registradas en Reporting Data
     *
     * @param situationTemplatesIds situaciones de las cuales se necesitan los
     * datos
     * @param storeId store para el cual se necesita la data
     * @param cantGroup cantidad de grupos deseados
     * @return List<AlertDTO> lista de datos solicitados
     * @throws
     * com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException
     */
    @Override
    public List<AlertDTO> getAllLastEvaluation(List<Integer> situationTemplateId, List<Integer> storeId) throws ScopixWebServiceException {
                List<AlertDTO> ret = null;
        try {
            
            Map<String, Map<String, Double>> map = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).
                    getAllLastEvaluation(situationTemplateId, storeId);
            ret = new ArrayList<AlertDTO>();
            for (String key : map.keySet()) {
                AlertDTO dto = new AlertDTO();
                String[] data = StringUtils.split(key, ".");
                dto.setEvidenceDate(data[0]);
                dto.setStoreId(Integer.valueOf(data[1]));
                if(data.length > 2){
                    dto.setAreaId(Integer.valueOf(data[2]));
                }
                dto.setData(map.get(key));
                ret.add(dto);
            }
        } catch (NumberFormatException e) {
            throw new ScopixWebServiceException(e);
        }
        return ret;
    }
}
