/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * EvidenceAndProofsDTO.java
 *
 * Created on 28-09-2008, 11:12:47 AM
 *
 */
package com.scopix.periscope.evaluationmanagement.dto;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author Cesar Abarza Suazo.
 */
public class EvidencesAndProofsDTO {

    private Integer evidenceId;
    private List<Integer> proofsId;
    private List<Integer> proofsResult;
    private String metricType;
    private String cameraName;
    private List<Integer> evaluationTimes;
    private static Comparator<EvidencesAndProofsDTO> comparatorByEvidenceId;
    private static Comparator<EvidencesAndProofsDTO> comparatorByCameraName;

    public static Comparator<EvidencesAndProofsDTO> getComparatorByEvidenceId() {
        comparatorByEvidenceId = new Comparator<EvidencesAndProofsDTO>() {

            public int compare(EvidencesAndProofsDTO o1, EvidencesAndProofsDTO o2) {
                return o1.getEvidenceId() - o2.getEvidenceId();
            }
        };
        return comparatorByEvidenceId;
    }

    public static void setComparatorByEvidenceId(Comparator<EvidencesAndProofsDTO> aComparatorByEvidenceId) {
        comparatorByEvidenceId = aComparatorByEvidenceId;
    }

    public static Comparator<EvidencesAndProofsDTO> getComparatorByCameraName() {
        comparatorByCameraName = new Comparator<EvidencesAndProofsDTO>() {

            public int compare(EvidencesAndProofsDTO o1, EvidencesAndProofsDTO o2) {
                return o1.getCameraName().compareTo(o2.getCameraName());
            }
        };
        return comparatorByCameraName;
    }

    public static void setComparatorByCameraName(Comparator<EvidencesAndProofsDTO> aComparatorByCameraName) {
        comparatorByCameraName = aComparatorByCameraName;
    }

    /*
    public static Comparator<EvidencesAndProofsDTO> compratorByEvidenceId = new Comparator<EvidencesAndProofsDTO>() {

    public int compare(EvidencesAndProofsDTO o1, EvidencesAndProofsDTO o2) {
    return o1.getEvidenceId() - o2.getEvidenceId();
    }
    };


    public static Comparator<EvidencesAndProofsDTO> compratorByCameraName = new Comparator<EvidencesAndProofsDTO>() {

    public int compare(EvidencesAndProofsDTO o1, EvidencesAndProofsDTO o2) {
    return o1.getCameraName().compareTo(o2.getCameraName());
    }
    };
     */
    public List<Integer> getProofsId() {
        if (proofsId == null) {
            proofsId = new LinkedList<Integer>();
        }
        return proofsId;
    }

    public void setProofsId(List<Integer> proofsId) {
        this.proofsId = proofsId;
    }

    /**
     * @return the evidenceId
     */
    public Integer getEvidenceId() {
        return evidenceId;
    }

    /**
     * @param evidenceId the evidenceId to set
     */
    public void setEvidenceId(Integer evidenceId) {
        this.evidenceId = evidenceId;
    }

    /**
     * @return the metricType
     */
    public String getMetricType() {
        return metricType;
    }

    /**
     * @param metricType the metricType to set
     */
    public void setMetricType(String metricType) {
        this.metricType = metricType;
    }

    /**
     * @return the proofsResult
     */
    public List<Integer> getProofsResult() {
        if (proofsResult == null) {
            proofsResult = new LinkedList<Integer>();
        }
        return proofsResult;
    }

    /**
     * @param proofsResult the proofsResult to set
     */
    public void setProofsResult(List<Integer> proofsResult) {
        this.proofsResult = proofsResult;
    }

    /**
     * @return the cameraName
     */
    public String getCameraName() {
        return cameraName;
    }

    /**
     * @param cameraName the cameraName to set
     */
    public void setCameraName(String cameraName) {
        this.cameraName = cameraName;
    }

    /**
     * @return the evaluationTime
     */
    public List<Integer> getEvaluationTimes() {
        if (evaluationTimes == null) {
            evaluationTimes = new LinkedList<Integer>();
        }
        return evaluationTimes;
    }

    /**
     * @param evaluationTime the evaluationTime to set
     */
    public void setEvaluationTimes(List<Integer> evaluationTimes) {
        this.evaluationTimes = evaluationTimes;
    }
}
