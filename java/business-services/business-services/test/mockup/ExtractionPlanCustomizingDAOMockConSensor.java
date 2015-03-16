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
 *  ExtractionPlanCustomizingDAOMockConSensor.java
 * 
 *  Created on 15-09-2010, 02:53:21 PM
 * 
 */
package mockup;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRangeDetail;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.evaluationmanagement.Indicator;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.templatemanagement.Product;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 *
 * @author nelson
 */
public class ExtractionPlanCustomizingDAOMockConSensor implements ExtractionPlanCustomizingDAO {

    private BusinessObject businessObject;

    public List<Indicator> getIndicatorNameList(AreaType areaType, Product product) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ExtractionPlanCustomizing getWizardCustomizing(ExtractionPlanCustomizing wizardCustomizing) {
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCUYSensores();

        return epc;
    }

    public void reprocess(Date startDate, Date endDate, List<Integer> situationTemplateIds,
            List<Integer> storeIds) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateOrder(ExtractionPlanCustomizing epc) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void updateOrder(ExtractionPlanMetric epm) throws ScopixException {
        setBusinessObject(businessObject);
    }

    public BusinessObject getBusinessObject() {
        return businessObject;
    }

    public void setBusinessObject(BusinessObject businessObject) {
        this.businessObject = businessObject;
    }

    public void cleanExtractionPlanMetrics(ExtractionPlanCustomizing customizing) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cleanExtractionPlanRanges(ExtractionPlanCustomizing customizing) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public void cleanDetailExtractionPlanRange(ExtractionPlanRange extractionPlanRange) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public ExtractionPlanCustomizing getExtractionPlanCustomizing(SituationTemplate st, Store store) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Metric> getMetricList(Metric metric) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<EvidenceRequest> getEvidenceRequestList(EvidenceRequest evidenceRequest, Date init, Date end) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<EvidenceRequest> getFreeEvidenceRequestList(EvidenceRequest evidenceRequest, Date init, Date end) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Metric> getFreeMetricList(Metric metric) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Situation> getSituationList(Situation situation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ExtractionPlanCustomizing> getExtractionPlanCustomizingActiveOrNullList(ExtractionPlanCustomizing wizardCustomizing) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ExtractionPlanCustomizing> getExtractionPlanCustomizingActiveList(ExtractionPlanCustomizing wizardCustomizing) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * retorna una lista con un unico ECP no Activo
     */
    public List<ExtractionPlanCustomizing> getExtractionPlanCustomizingNullList(ExtractionPlanCustomizing wizardCustomizing) {
        List<ExtractionPlanCustomizing> customizings = new ArrayList<ExtractionPlanCustomizing>();
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCUSTConMetricTemplate();
        epc.setActive(null);
        customizings.add(epc);
        return customizings;
    }

    public List<ExtractionPlanRange> getExtractionPlanRanges(ExtractionPlanCustomizing extractionPlanCustomizing)
            throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<ExtractionPlanRangeDetail> getExtractionPlanRangeDetails(ExtractionPlanRange extractionPlanCustomizing)
            throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public int getCantidadDetailesForEpcStore(Integer epcId, Integer storeId) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<Integer> getAllEPCFromStoreSituatioTemplateEvidenceProvider(Integer storeId, Integer situationTemplateId,
            Integer evidenceProviderId) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void disableExtractionPlanCustomizings(List<Integer> epcIds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<ExtractionPlanCustomizing> getExtractionPlanCustomizingNullListByIds(List<Integer> epcIds) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int getCantidadDetailesForEpcStoreByIds(List<Integer> epcIds, Integer storeId) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public int createRecordsForEPC(ExtractionPlanCustomizing epc) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cleanRangeDetailForExtractionPlanRanges(Set<Integer> idsRangeClear) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cleanSensors(List<Integer> sensorIds) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
