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
 * ProofDTO.java
 *
 * Created on 03-06-2008, 06:18:23 PM
 *
 */
package com.scopix.periscope.evaluationmanagement.dto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Gustavo Alvarez
 */
public class ProofDTO implements Comparable<ProofDTO> {

    private Integer proofId;
    private List<MarquisDTO> marquis;
    private String pathWithMarks;
    private String pathWithoutMarks;
    private Integer order;
    private Integer evidenceId;
    private Integer proofResult;
    private static Comparator<ProofDTO> comparatorByOrder;

    public static Comparator<ProofDTO> getComparatorByOrder() {
        comparatorByOrder = new Comparator<ProofDTO>() {

            public int compare(ProofDTO o1, ProofDTO o2) {
                return o1.getOrder().compareTo(o2.getOrder());
            }
        };
        
        return comparatorByOrder;
    }

    public static void setComparatorByOrder(Comparator<ProofDTO> aComparatorByOrder) {
        comparatorByOrder = aComparatorByOrder;
    }

    public List<MarquisDTO> getMarquis() {
        if (marquis == null) {
            marquis = new ArrayList<MarquisDTO>();
        }
        return marquis;
    }

    public void setMarquis(List<MarquisDTO> marquis) {
        this.marquis = marquis;
    }

    public String getPathWithMarks() {
        return pathWithMarks;
    }

    public void setPathWithMarks(String pathWithMarks) {
        this.pathWithMarks = pathWithMarks;
    }

    public String getPathWithoutMarks() {
        return pathWithoutMarks;
    }

    public void setPathWithoutMarks(String pathWithoutMarks) {
        this.pathWithoutMarks = pathWithoutMarks;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    /**
     * @return the proofId
     */
    public Integer getProofId() {
        return proofId;
    }

    /**
     * @param proofId the proofId to set
     */
    public void setProofId(Integer proofId) {
        this.proofId = proofId;
    }

    public int compareTo(ProofDTO o) {
        return this.getProofId() - o.getProofId();
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
     * @return the proofResult
     */
    public Integer getProofResult() {
        return proofResult;
    }

    /**
     * @param proofResult the proofResult to set
     */
    public void setProofResult(Integer proofResult) {
        this.proofResult = proofResult;
    }
}
