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
 *  ExtractionPlanCustomizingDAOMock.java
 * 
 *  Created on 13-09-2010, 11:51:17 AM
 * 
 */
package mockup;

import com.scopix.periscope.corporatestructuremanagement.AreaType;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.Indicator;
import com.scopix.periscope.extractionplanmanagement.EvidenceRequest;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanCustomizing;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanMetric;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRange;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanRangeDetail;
import com.scopix.periscope.extractionplanmanagement.Metric;
import com.scopix.periscope.extractionplanmanagement.Situation;
import com.scopix.periscope.extractionplanmanagement.dao.ExtractionPlanCustomizingDAO;
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
public class ExtractionPlanCustomizingDAOMock implements ExtractionPlanCustomizingDAO {

    private BusinessObject businessObject;

    @Override
    public List<EvidenceRequest> getEvidenceRequestList(EvidenceRequest evidenceRequest, Date init, Date end) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public ExtractionPlanCustomizing getExtractionPlanCustomizing(SituationTemplate st, Store store) {
        ExtractionPlanCustomizing epc = new ExtractionPlanCustomizing();
        epc.setSituationTemplate(st);
        epc.setStore(store);
        return epc;
    }

    @Override
    public ExtractionPlanCustomizing getWizardCustomizing(ExtractionPlanCustomizing wizardCustomizing) {
        ExtractionPlanCustomizing epc = new ExtractionPlanCustomizing();
        epc.setId(1);
        return epc;
    }

    @Override
    public List<Indicator> getIndicatorNameList(AreaType areaType, Product product) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void reprocess(Date startDate, Date endDate, List<Integer> situationTemplateIds,
            List<Integer> storeIds) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void updateOrder(ExtractionPlanMetric epm) throws ScopixException {
        setBusinessObject(epm);
    }

    public BusinessObject getBusinessObject() {
        return businessObject;
    }

    public void setBusinessObject(BusinessObject businessObject) {
        this.businessObject = businessObject;
    }

    @Override
    public List<EvidenceRequest> getFreeEvidenceRequestList(EvidenceRequest evidenceRequest, Date init, Date end) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Metric> getFreeMetricList(Metric metric) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Metric> getMetricList(Metric metric) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public List<Situation> getSituationList(Situation situation) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cleanExtractionPlanMetrics(ExtractionPlanCustomizing customizing) throws ScopixException {
        customizing.getExtractionPlanMetrics().clear();
    }

    @Override
    public void cleanExtractionPlanRanges(ExtractionPlanCustomizing customizing) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void cleanDetailExtractionPlanRange(ExtractionPlanRange extractionPlanRange) throws ScopixException {
        //throw new UnsupportedOperationException("Not supported yet.");
        extractionPlanRange.getExtractionPlanRangeDetails().clear();
    }

    /**
     * Retorna una lista con un solo ExtractionPlanCustomizing generado por
     * ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU
     */
    public List<ExtractionPlanCustomizing> getExtractionPlanCustomizingActiveOrNullList(
            ExtractionPlanCustomizing wizardCustomizing) {
        List<ExtractionPlanCustomizing> list = new ArrayList<ExtractionPlanCustomizing>();
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        epc.setId(1);
        list.add(epc);
        return list;
    }

    public List<ExtractionPlanCustomizing> getExtractionPlanCustomizingActiveList(ExtractionPlanCustomizing wizardCustomizing) {
        List<ExtractionPlanCustomizing> list = new ArrayList<ExtractionPlanCustomizing>();
        ExtractionPlanCustomizing epc = ManagerMock.genExtractionPlanCustomizing2Rangos1DetalleCU();
        epc.setId(1);
        epc.setActive(Boolean.TRUE);
        list.add(epc);
        return list;
    }

    public List<ExtractionPlanCustomizing> getExtractionPlanCustomizingNullList(ExtractionPlanCustomizing wizardCustomizing) {
        //retorna una lista vacia
        return new ArrayList<ExtractionPlanCustomizing>();
    }

    /**retorna una lista con 2 rangos*/
    public List<ExtractionPlanRange> getExtractionPlanRanges(ExtractionPlanCustomizing extractionPlanCustomizing)
            throws ScopixException {
        if (extractionPlanCustomizing == null) {
            throw new ScopixException("EPC null");
        }
        List<ExtractionPlanRange> planRanges = new ArrayList<ExtractionPlanRange>();
        if (extractionPlanCustomizing.getId() == 1) {
            planRanges.add(ManagerMock.genExtractionPlanRange(1, 1, 30, 5, 3));
            planRanges.add(ManagerMock.genExtractionPlanRange(2, 2, 15, 5, 1));
        }
        return planRanges;
    }

    public List<ExtractionPlanRangeDetail> getExtractionPlanRangeDetails(ExtractionPlanRange extractionPlanRange)
            throws ScopixException {
        if (extractionPlanRange == null) {
            throw new ScopixException("EPR null");
        }
        List<ExtractionPlanRangeDetail> planRangeDetails = new ArrayList<ExtractionPlanRangeDetail>();
        if (extractionPlanRange.getId() == 1) {
            planRangeDetails.add(ManagerMock.genExtractionPlanRangeDetail(1, new Date()));
            planRangeDetails.add(ManagerMock.genExtractionPlanRangeDetail(2, new Date()));
        }
        return planRangeDetails;

    }

    public int getCantidadDetailesForEpcStore(Integer epcId, Integer storeId) throws ScopixException {
        return 1;
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
        return 0;
    }

    @Override
    public void cleanRangeDetailForExtractionPlanRanges(Set<Integer> idsRangeClear) throws ScopixException {
        //NO hacemos nada es un test
    }

    @Override
    public void cleanSensors(List<Integer> sensorIds) throws ScopixException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
