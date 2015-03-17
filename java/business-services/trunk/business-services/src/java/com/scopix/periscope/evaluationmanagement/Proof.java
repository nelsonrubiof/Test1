/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * Proof.java
 *
 * Created on 03-06-2008, 02:28:16 PM
 *
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.businesswarehouse.transfer.TransferProofFilesLog;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.validator.NotNull;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class Proof extends BusinessObject implements Comparable<Proof> {

    @Lob
    private String pathWithMarks;
    @Lob
    private String pathWithoutMarks;
    private Integer proofOrder;
    @Temporal(TemporalType.TIMESTAMP)
    private Date proofDate;
    @ManyToOne
    private EvidenceEvaluation evidenceEvaluation;
    @OneToMany(mappedBy = "proof", fetch = FetchType.LAZY)
    private List<Marquis> marquis;
    @ManyToOne
    private Evidence evidence;
    private Integer proofResult;
    @NotNull
    private boolean sentToMIS;
    @NotNull 
    private boolean sentToMISData;
    @ManyToOne
    private TransferProofFilesLog transferProofFileLog;
    private static Comparator<Proof> comparatorByProofOrder;

    public static Comparator<Proof> getComparatorByProofOrder() {
        comparatorByProofOrder = new Comparator<Proof>() {

            public int compare(Proof o1, Proof o2) {
                return o1.getProofOrder() - o2.getProofOrder();
            }
        };

        return comparatorByProofOrder;
    }

    public static void setComparatorByProofOrder(Comparator<Proof> aComparatorByProofOrder) {
        comparatorByProofOrder = aComparatorByProofOrder;
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

    public Date getProofDate() {
        return proofDate;
    }

    public void setProofDate(Date proofDate) {
        this.proofDate = proofDate;
    }

    public EvidenceEvaluation getEvidenceEvaluation() {
        return evidenceEvaluation;
    }

    public void setEvidenceEvaluation(EvidenceEvaluation evidenceEvaluation) {
        this.evidenceEvaluation = evidenceEvaluation;
    }

    public List<Marquis> getMarquis() {
        return marquis;
    }

    public void setMarquis(List<Marquis> marquis) {
        this.marquis = marquis;
    }

    public Integer getProofOrder() {
        return proofOrder;
    }

    public void setProofOrder(Integer proofOrder) {
        this.proofOrder = proofOrder;
    }

    /**
     * @return the evidence
     */
    public Evidence getEvidence() {
        return evidence;
    }

    /**
     * @param evidence the evidence to set
     */
    public void setEvidence(Evidence evidence) {
        this.evidence = evidence;
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

    public int compareTo(Proof o) {
        return this.getId() - o.getId();
    }

    public TransferProofFilesLog getTransferProofFileLog() {
        return transferProofFileLog;
    }

    public void setTransferProofFileLog(TransferProofFilesLog transferProofFileLog) {
        this.transferProofFileLog = transferProofFileLog;
    }

    public boolean isSentToMIS() {
        return sentToMIS;
    }

    public void setSentToMIS(boolean sentToMIS) {
        this.sentToMIS = sentToMIS;
    }

    public boolean isSentToMISData() {
        return sentToMISData;
    }

    public void setSentToMISData(boolean sentToMISData) {
        this.sentToMISData = sentToMISData;
    }

    /*
    public static Comparator<Proof> compratorByProofOrder = new Comparator<Proof>() {

    public int compare(Proof o1, Proof o2) {
    return o1.getProofOrder() - o2.getProofOrder();
    }
    };
     */
}
