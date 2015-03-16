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
 *  SendExtractionPlanProcess.java
 * 
 *  Created on 03-12-2010, 03:33:30 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement;

import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Temporal;
import org.apache.commons.lang.StringUtils;

/**
 *
 * @author nelson
 */
@Entity
public class SendExtractionPlanProcess extends BusinessObject {

    private long sessionId;
    private Integer storeId;
    @Lob
    private String extracionPlanCustomizingIds;
    @Temporal(value = javax.persistence.TemporalType.TIMESTAMP)
    private Date ini;
    @Temporal(value = javax.persistence.TemporalType.TIMESTAMP)
    private Date fin;
    private Integer status;
    @Lob
    private String message;

    public SendExtractionPlanProcess() {
    }

    public SendExtractionPlanProcess(long sId, Integer stId, List<Integer> eId) {
        this.sessionId = sId;
        this.storeId = stId;
        this.extracionPlanCustomizingIds = StringUtils.join(eId, ",");
    }


    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Date getIni() {
        return ini;
    }

    public void setIni(Date ini) {
        this.ini = ini;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getExtracionPlanCustomizingIds() {
        return extracionPlanCustomizingIds;
    }

    public void setExtracionPlanCustomizingIds(String extracionPlanCustomizingIds) {
        this.extracionPlanCustomizingIds = extracionPlanCustomizingIds;
    }
}
