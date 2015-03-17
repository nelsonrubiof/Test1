/**
 *
 * Copyright © 2014, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 *
 *
 */
package com.scopix.periscope.frontend.dto;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sebastian
 *
 */
@XmlRootElement(name = "EvidenceRegionTransferStatsDTO")
public class EvidenceRegionTransferStatsDTO {

    private Double transferredPercentage;
    private Double notTransferredPercentage;
    private Integer totalEvidences;
    private Integer transferredEvidences;
    private Integer notTransferredEvidences;

    /**
     * @return the transferredPercentage
     */
    public Double getTransferredPercentage() {
        return transferredPercentage;
    }

    /**
     * @param transferredPercentage the transferredPercentage to set
     */
    public void setTransferredPercentage(Double transferredPercentage) {
        this.transferredPercentage = transferredPercentage;
    }

    /**
     * @return the notTransferredPercentage
     */
    public Double getNotTransferredPercentage() {
        return notTransferredPercentage;
    }

    /**
     * @param notTransferredPercentage the notTransferredPercentage to set
     */
    public void setNotTransferredPercentage(Double notTransferredPercentage) {
        this.notTransferredPercentage = notTransferredPercentage;
    }

    /**
     * @return the totalEvidences
     */
    public Integer getTotalEvidences() {
        return totalEvidences;
    }

    /**
     * @param totalEvidences the totalEvidences to set
     */
    public void setTotalEvidences(Integer totalEvidences) {
        this.totalEvidences = totalEvidences;
    }

    /**
     * @return the transferredEvidences
     */
    public Integer getTransferredEvidences() {
        return transferredEvidences;
    }

    /**
     * @param transferredEvidences the transferredEvidences to set
     */
    public void setTransferredEvidences(Integer transferredEvidences) {
        this.transferredEvidences = transferredEvidences;
    }

    /**
     * @return the notTransferredEvidences
     */
    public Integer getNotTransferredEvidences() {
        return notTransferredEvidences;
    }

    /**
     * @param notTransferredEvidences the notTransferredEvidences to set
     */
    public void setNotTransferredEvidences(Integer notTransferredEvidences) {
        this.notTransferredEvidences = notTransferredEvidences;
    }
}
