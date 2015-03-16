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
 *  ReportingHibernateDAO.java
 * 
 *  Created on 13-01-2011, 06:00:26 PM
 * 
 */
package com.scopix.periscope.reporting.dao;

import com.scopix.periscope.businesswarehouse.transfer.dto.ProductAndAreaDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.reporting.ProcessState;
import com.scopix.periscope.reporting.ReportingData;
import com.scopix.periscope.reporting.UploadProcess;
import com.scopix.periscope.reporting.UploadProcessDetail;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author nelson
 */
public interface ReportingHibernateDAO {

    UploadProcess getUploadProcessInExecutionOrLastSend(ProcessState processState) throws ScopixException;

    List<UploadProcessDetail> getUploadProcessDetailList(ProcessState processState) throws ScopixException;

    Integer getTotalRecords(UploadProcessDetail detail) throws ScopixException;

    List<ProductAndAreaDTO> getProductsAndAreasByAreaTypes(Integer[] areaTypeIds);

    List<Map> getMetricNamesFromBS();

    List<ReportingData> getReportingDataFormDetail(UploadProcessDetail detail);

    Date getDateOfEvidence(Integer id);

    void saveReportingData(ReportingData data);

    void rejectReportingDataByObservedMetricIds(List<Integer> observedMetricIds, String userName);

    void rejectReportingDataByObservedSituationIds(Set<Integer> osIds, String evaluationUser);

    void genereDataFromObservedSituation() throws ScopixException;

    Double getTargetFromObservedSituationEvaluation(Integer osId);

    ReportingData getReportingDataByObservedSituation(Integer osId);

    List<ReportingData> getReportingDataListByObservedSituation(Integer osId);

    List<Map<String, Object>> getAreaTypeFromBS();

    void addReportingData(ReportingData data);

    void generateReportingDataFromDataOld() throws ScopixException;

    Map<String, Map<String, Double>> getLastEvaluations(List<Integer> situationTemplatesIds, Integer storeId, Integer cantGrupos);

    /**
     * Retorna la cantida de Registros no subidos a Reporting
     *
     * @return Integer numero de registros
     * @throws PeriscopeException
     */
    Integer getCantNewDataNoSending() throws ScopixException;

    Map<String, Map<String, Double>> getAllLastEvaluations(List<Integer> situationTemplatesIds, List<Integer> storeIds);
}
