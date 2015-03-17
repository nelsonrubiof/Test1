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
 *  TransferProofFilesLog.java
 * 
 *  Created on 19-07-2011, 04:05:07 PM
 * 
 */
package com.scopix.periscope.businesswarehouse.transfer;

import com.scopix.periscope.evaluationmanagement.Proof;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class TransferProofFilesLog extends BusinessObject {

    @Lob
    private String fileName;
    @Enumerated(EnumType.STRING)
    private TransferProofFilesStatus status;
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date uploadDate;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "transferProofFileLog")
    @Cascade(CascadeType.PERSIST)
    private List<Proof> proofs;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public TransferProofFilesStatus getStatus() {
        return status;
    }

    public void setStatus(TransferProofFilesStatus status) {
        this.status = status;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public List<Proof> getProofs() {
        if (proofs == null) {
            proofs = new ArrayList<Proof>();
        }
        return proofs;
    }

    public void setProofs(List<Proof> proofs) {
        this.proofs = proofs;
    }

    public List<Integer> getProofsIds() {
        List<Integer> proofsIds = new ArrayList<Integer>();
        for (Proof p : getProofs()) {
            proofsIds.add(p.getId());
        }

        return proofsIds;
    }
}
