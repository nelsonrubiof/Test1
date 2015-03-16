package com.scopix.periscope.extractionmanagement;

import java.util.Date;
import javax.persistence.Entity;
import org.apache.commons.lang.time.DateUtils;

/*
 * 
 * Copyright (c) 2012, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 * 
 * Clase que extiende propiedades de la tabla EvidenceExtractionRequest para la nueva versión
 * de integración entre EES y NextLevel
 *
 * @author    carlos polo
 * @created   19-oct-2012
 *
 */
@Entity
public class NextLevel3VideoExtractionRequest extends EvidenceExtractionRequest {

    private int lengthInSecs;

    @Override
    public Date getExtractionStartTime() {
        Date ret = this.getRequestedTime();
        ret = DateUtils.addSeconds(ret, getLengthInSecs());
        ret = DateUtils.addMinutes(ret, 3);
        return ret;
    }

    public int getLengthInSecs() {
        return lengthInSecs;
    }

    public void setLengthInSecs(int lengthInSecs) {
        this.lengthInSecs = lengthInSecs;
    }

    @Override
    public boolean getAllowsExtractionPlanToPast() {
        return true;
    }
}