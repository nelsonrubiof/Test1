/*
 * 
 * Copyright � 2013, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * Subscription.java
 *
 * Created on 28-10-2013, 03:46:21 PM
 *
 */
package com.scopix.periscope.subscription;

import com.scopix.periscope.operatorqueuepriority.OperatorQueuePriority;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

/**
 *
 * @author Sebastian
 */
@Entity
public class Subscription extends BusinessObject {

    private String userName;
    private Integer operatorsGroupId;
    @ManyToOne(fetch = FetchType.EAGER)
    private OperatorQueuePriority operatorQueuePriority;

    /**
     * @return the userName
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName the userName to set
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * @return the operatorsGroupId
     */
    public Integer getOperatorsGroupId() {
        return operatorsGroupId;
    }

    /**
     * @param operatorsGroupId the operatorsGroupId to set
     */
    public void setOperatorsGroupId(Integer operatorsGroupId) {
        this.operatorsGroupId = operatorsGroupId;
    }

    /**
     * @return the operatorQueuePriority
     */
    public OperatorQueuePriority getOperatorQueuePriority() {
        return operatorQueuePriority;
    }

    /**
     * @param operatorQueuePriority the operatorQueuePriority to set
     */
    public void setOperatorQueuePriority(OperatorQueuePriority operatorQueuePriority) {
        this.operatorQueuePriority = operatorQueuePriority;
    }
}
