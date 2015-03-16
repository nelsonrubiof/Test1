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
 *  GetEvidencesAndProofsForTransferCommand.java
 * 
 *  Created on 22-07-2011, 12:28:42 PM
 * 
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.businesswarehouse.transfer.EvidencesAndProofs;
import com.scopix.periscope.businesswarehouse.transfer.dao.TransferHibernateDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.MetricType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class GetEvidencesAndProofsForTransferCommand {

    private static final String START = "start";
    private static final String END = "end";
    private TransferHibernateDAO dao;
    private static Logger log = Logger.getLogger(GetEvidencesAndProofsForTransferCommand.class);

    public List<EvidencesAndProofs> execute(Integer observedMetricId) throws ScopixException {
        log.info(START);
        List<EvidencesAndProofs> list = new ArrayList<EvidencesAndProofs>();
        String metricTypeName = getDao().getMetricTypeName(observedMetricId);

        ProofBW proof1 = null;
        ProofBW proof2 = null;
        EvidencesAndProofs vo = null;

        //obtener evidences con sus proofs
        List<Map<String, Object>> evidenceList = getDao().getEvidencesForObservedMetric(observedMetricId);
        List<Map<String, Object>> proofList = getDao().getProofsForObservedMetric(observedMetricId);

        log.debug("observedMetricId: " + observedMetricId);
        log.debug("evidenceList.size: " + evidenceList.size());
        log.debug("proofList.size: " + proofList.size());
        for (Map evidence : evidenceList) {
            vo = new EvidencesAndProofs();
            vo.setObservedMetricId(observedMetricId);
            vo.setEvidenceId((Integer) evidence.get("id"));
            vo.setMetricType(metricTypeName);
            vo.setCameraId((Integer) evidence.get("evidence_provider_id"));
            vo.setEvidenceDate((Date) evidence.get("evidence_date"));
            vo.setStoreId((Integer) evidence.get("store_id"));
            for (Map proof : proofList) {
                if (!proof.get("evidence_id").equals(evidence.get("id"))) {
                    continue;
                }
                log.debug("evidence_id (proof): " + proof.get("evidence_id") + ", evidence_id (evidence): " + evidence.get("id"));
                log.debug("rejected " + proof.get("rejected"));

                if (metricTypeName.equals(MetricType.COUNTING.getName())
                        || metricTypeName.equals(MetricType.YES_NO.getName())
                        || metricTypeName.equals(MetricType.NUMBER_INPUT.getName())) {
                    proof1 = generateProofBW(proof);

                    vo.getProofs().add(proof1);

                    list.add(vo);

                    vo = new EvidencesAndProofs();
                    vo.setObservedMetricId(observedMetricId);
                    vo.setEvidenceId((Integer) evidence.get("id"));
                    vo.setMetricType(metricTypeName);
                    vo.setCameraId((Integer) evidence.get("evidence_provider_id"));
                    vo.setEvidenceDate((Date) evidence.get("evidence_date"));
                    vo.setStoreId((Integer) evidence.get("store_id"));

                    //break;
                } else if (metricTypeName.equals(MetricType.MEASURE_TIME.getName())) {
                    if (proof.get("proof_order").equals(0)) {
                        proof1 = generateProofBW(proof);

                        vo.getProofs().add(proof1);

                    } else if (!vo.getProofs().isEmpty()) {
                        proof2 = generateProofBW(proof);

                        vo.getProofs().add(proof2);

                    } else {
                        proof2 = generateProofBW(proof);

                        vo.getProofs().add(proof2);
                    }
                }
            }

            if (vo.getProofs() != null && !vo.getProofs().isEmpty()) {
                list.add(vo);
            }
        }

        log.info(END);
        return list;
    }

    private ProofBW generateProofBW(Map proof) {
        ProofBW proof1 = new ProofBW();
        proof1.setId((Integer) proof.get("id"));
        proof1.setPathWithMarks((String) proof.get("path_with_marks"));
        proof1.setPathWithoutMarks((String) proof.get("path_without_marks"));
        proof1.setProofOrder((Integer) proof.get("proof_order"));
        proof1.setProofResult((Integer) proof.get("proof_result"));
        proof1.setProofAvailable((Boolean) proof.get("sent_tomis"));
        proof1.setRejected((Boolean) proof.get("rejected"));

        return proof1;
    }

    public TransferHibernateDAO getDao() {
        if (dao == null) {
            dao = SpringSupport.getInstance().findBeanByClassName(TransferHibernateDAO.class);
        }
        return dao;
    }

    public void setDao(TransferHibernateDAO dao) {
        this.dao = dao;
    }
}
