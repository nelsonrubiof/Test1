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
 * EvidenceAvailableDto.java
 *
 * Created on May 3, 2007, 9:29 AM
 *
 */

package com.scopix.periscope.evidence_server.ws.impl;

/**
 * Data transfer object for evidence available
 * @author jorge
 */
public class EvidenceAvailableDto {
    public static final int EVIDENCE_TYPE_REALTIME = 2;
    
    /** Id in the agent **/
    private int id;
    /** RequestId received by the agent **/
    private int requestId;
    /** URI where the evidence can be located **/
    private String evidenceUri;
    /** type of evidence **/
    private int evidenceType;
    /** Timestamp when the evidence was informed **/
    private long timestamp;
    
    /** Creates a new instance of EvidenceAvailableDto */
    public EvidenceAvailableDto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEvidenceUri() {
        return evidenceUri;
    }

    public void setEvidenceUri(String evidenceUri) {
        this.evidenceUri = evidenceUri;
    }

    public int getEvidenceType() {
        return evidenceType;
    }

    public void setEvidenceType(int evidenceType) {
        this.evidenceType = evidenceType;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
}
