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
 * OperatorQueuePriority.java
 *
 * Created on 28-10-2013, 03:46:21 PM
 *
 */
package com.scopix.periscope.operatorqueuepriority;

import com.scopix.periscope.subscription.Subscription;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

/**
 *
 * @author Sebastian
 */

@Entity
public class OperatorQueuePriority extends BusinessObject  {
    
    
    private Integer corporateId;
    
    private String corporateName;
    
    private String operatorQueueName;
  
    private Integer priority;
   
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "operatorQueuePriority")
    @Cascade({CascadeType.DELETE_ORPHAN, CascadeType.REMOVE, CascadeType.SAVE_UPDATE})
    private List <Subscription> userSubscriptions;

    /**
     * getter
     * @return corporateId
     */
    public Integer getCorporateId() {
        return corporateId;
    }

    /**
     * setter
     * @param corporateId
     */
    public void setCorporateId(Integer corporateId) {
        this.corporateId = corporateId;
    }
    /**
     * getter
     * @return corporateId
     */
    public String getCorporateName() {
        return corporateName;
    }
    /**
     * setter
     * @param corporateName 
     */
    public void setCorporateName(String corporateName) {
        this.corporateName = corporateName;
    }

    /**
     * getter
     * @return operatorQueueName
     */
    public String getOperatorQueueName() {
        return operatorQueueName;
    }

    /**
     * setter
     * @param operatorQueueName
     */
    public void setOperatorQueueName(String operatorQueueName) {
        this.operatorQueueName = operatorQueueName;
    }

    /**
     * getter
     * @return priority
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * setter
     * @param priority
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    /**
     * getter
     * @return list of objects userSubscription
     */
    public List <Subscription> getUserSubscriptions() {
        return userSubscriptions;
    }

    /**
     * setter
     * @param userSubscriptions 
     */
    public void setUserSubscriptions(List <Subscription> userSubscriptions) {
        this.userSubscriptions = userSubscriptions;
    }

    
}
