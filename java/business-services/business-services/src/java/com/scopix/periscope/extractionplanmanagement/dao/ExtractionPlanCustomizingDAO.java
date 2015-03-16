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
 *  ExtractionPlanCustomizingDAO.java
 * 
 *  Created on 08-09-2010, 11:52:51 AM
 * 
 */
package com.scopix.periscope.extractionplanmanagement.dao;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.evaluationmanagement.Indicator;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRangeDetail;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.templatemanagement.Product;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author nelson
 */
public interface ExtractionPlanCustomizingDAO {

    List<Indicator> getIndicatorNameList(AreaType areaType, Product product);

    List<ExtractionPlanCustomizing> getExtractionPlanCustomizingActiveOrNullList(ExtractionPlanCustomizing wizardCustomizing);

    List<ExtractionPlanCustomizing> getExtractionPlanCustomizingActiveList(ExtractionPlanCustomizing wizardCustomizing);

    List<ExtractionPlanCustomizing> getExtractionPlanCustomizingNullList(ExtractionPlanCustomizing wizardCustomizing);

    List<ExtractionPlanCustomizing> getExtractionPlanCustomizingNullListByIds(List<Integer> epcIds);

    List<ExtractionPlanRange> getExtractionPlanRanges(ExtractionPlanCustomizing extractionPlanCustomizing)
            throws ScopixException;

    List<ExtractionPlanRangeDetail> getExtractionPlanRangeDetails(ExtractionPlanRange extractionPlanCustomizing)
            throws ScopixException;

    void reprocess(Date startDate, Date endDate, List<Integer> situationTemplateIds,
            List<Integer> storeIds) throws ScopixException;

    void updateOrder(ExtractionPlanMetric epm) throws ScopixException;

    void cleanExtractionPlanMetrics(ExtractionPlanCustomizing customizing) throws ScopixException;

    void cleanExtractionPlanRanges(ExtractionPlanCustomizing customizing) throws ScopixException;

    void cleanDetailExtractionPlanRange(ExtractionPlanRange extractionPlanRange) throws ScopixException;

    /**
     * retorna un ExtractionPlanCustomizing dado
     * SituationTemplate, MetricTemplate, Store, EvidenceProvider
     * Esto se genera por cambio de generacion y extraccion del EPC
     */
    ExtractionPlanCustomizing getWizardCustomizing(ExtractionPlanCustomizing wizardCustomizing);

    List<Integer> getAllEPCFromStoreSituatioTemplateEvidenceProvider(Integer storeId, Integer situationTemplateId,
            Integer evidenceProviderId);

    ExtractionPlanCustomizing getExtractionPlanCustomizing(SituationTemplate st, Store store);

    List<Metric> getMetricList(Metric metric);

    List<EvidenceRequest> getEvidenceRequestList(EvidenceRequest evidenceRequest, Date init, Date end);

    List<EvidenceRequest> getFreeEvidenceRequestList(EvidenceRequest evidenceRequest, Date init, Date end);

    List<Metric> getFreeMetricList(Metric metric);

    List<Situation> getSituationList(Situation situation);

    int getCantidadDetailesForEpcStore(Integer epcId, Integer storeId) throws ScopixException;

    int getCantidadDetailesForEpcStoreByIds(List<Integer> epcIds, Integer storeId) throws ScopixException;

    void disableExtractionPlanCustomizings(List<Integer> epcIds);

    int createRecordsForEPC(ExtractionPlanCustomizing epc);

    void cleanRangeDetailForExtractionPlanRanges(Set<Integer> idsRangeClear) throws ScopixException;
    
    void cleanSensors(List<Integer> sensorIds) throws ScopixException;
}
