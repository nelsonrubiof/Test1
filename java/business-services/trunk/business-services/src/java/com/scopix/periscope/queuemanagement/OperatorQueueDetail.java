/*
 *
 * Copyright © 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * OperatorQueueDetail.java
 *
 * Created on 23-03-2010, 02:14:48 PM
 *
 */
package com.scopix.periscope.queuemanagement;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.periscopefoundation.BusinessObject;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 * @author Gustavo Alvarez
 */
@Entity
public class OperatorQueueDetail extends BusinessObject implements Comparable<OperatorQueueDetail>{

    @ManyToOne
    private OperatorQueue operatorQueue;
    @ManyToOne
    private Store store;
    @ManyToOne
    private SituationTemplate situationTemplate;
    //    private AreaType areaType;

    public OperatorQueue getOperatorQueue() {
        return operatorQueue;
    }

    public void setOperatorQueue(OperatorQueue operatorQueue) {
        this.operatorQueue = operatorQueue;
    }

    /**
     * @return the store
     */
    public Store getStore() {
        return store;
    }

    /**
     * @param store the store to set
     */
    public void setStore(Store store) {
        this.store = store;
    }

//    /**
//     * @return the areasType
//     */
//    public AreaType getAreaType() {
//        return areaType;
//    }
//
//    /**
//     * @param areasType the areasType to set
//     */
//    public void setAreaType(AreaType areasType) {
//        this.areaType = areasType;
//    }

    public int compareTo(OperatorQueueDetail o) {
        return this.getId() - o.getId();
    }

    /**
     * @return the situationTemplate
     */
    public SituationTemplate getSituationTemplate() {
        return situationTemplate;
    }

    /**
     * @param situationTemplate the situationTemplate to set
     */
    public void setSituationTemplate(SituationTemplate situationTemplate) {
        this.situationTemplate = situationTemplate;
    }
}
