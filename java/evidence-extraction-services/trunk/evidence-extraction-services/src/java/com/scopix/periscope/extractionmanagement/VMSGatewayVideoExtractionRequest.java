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
 * VMSGatewayVideoExtractionRequest.java
 *
 * Created on 13-09-2011, 11:04:04 AM
 *
 */
package com.scopix.periscope.extractionmanagement;

import java.util.Date;
import javax.persistence.Entity;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class VMSGatewayVideoExtractionRequest extends EvidenceExtractionRequest {

    private int lengthInSecs;
    private boolean allowsExtractionPlanToPast;

    @Override
    public Date getExtractionStartTime() {
        return this.getRequestedTime();
    }

    public int getLengthInSecs() {
        return lengthInSecs;
    }

    public void setLengthInSecs(int lengthInSecs) {
        this.lengthInSecs = lengthInSecs;
    }

    @Override
    public boolean getAllowsExtractionPlanToPast() {
        return isAllowsExtractionPlanToPast();
    }

    public boolean isAllowsExtractionPlanToPast() {
        return allowsExtractionPlanToPast;
    }

    public void setAllowsExtractionPlanToPast(boolean allowsExtractionPlanToPast) {
        this.allowsExtractionPlanToPast = allowsExtractionPlanToPast;
    }
}
