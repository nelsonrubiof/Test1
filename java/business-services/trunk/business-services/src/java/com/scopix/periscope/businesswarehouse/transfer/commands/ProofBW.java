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
 *  ProofBW.java
 * 
 *  Created on 25-07-2011, 09:19:37 AM
 * 
 */
package com.scopix.periscope.businesswarehouse.transfer.commands;

import com.scopix.periscope.evaluationmanagement.Proof;
import javax.persistence.Entity;
import javax.persistence.Transient;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class ProofBW extends Proof {

    @Transient
    private String storeName;
    @Transient
    private boolean proofAvailable;
    @Transient
    private boolean rejected;

    public ProofBW() {
    }

    public ProofBW(Proof proof) {
        setId(proof.getId());
        setPathWithMarks(proof.getPathWithMarks());
        setPathWithoutMarks(proof.getPathWithoutMarks());
        setProofOrder(proof.getProofOrder());
        setProofResult(proof.getProofResult());
        proofAvailable = proof.isSentToMIS();
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public boolean isProofAvailable() {
        return proofAvailable;
    }

    public void setProofAvailable(boolean proofAvailable) {
        this.proofAvailable = proofAvailable;
    }

    public boolean isRejected() {
        return rejected;
    }

    public void setRejected(boolean rejected) {
        this.rejected = rejected;
    }
}
