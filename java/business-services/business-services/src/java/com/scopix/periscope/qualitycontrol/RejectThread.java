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
 *  RejectThread.java
 * 
 *  Created on 03-07-2014, 15:01:40 PM
 * 
 */
package com.scopix.periscope.qualitycontrol;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author Gustavo Alvarez
 */
public class RejectThread extends Thread {

    private static Logger log = Logger.getLogger(RejectThread.class);
    private static final String START = "start";
    private static final String END = "end";
    private boolean initialized;
    private List<Integer> observedSituationEvaluationList;
    private List<Integer> indicatorValuesList;
    private List<Integer> observedMetricIds;

    public void init() {
        log.info(START);
        initialized = true;
        this.setName(this.getClass().getSimpleName());
        log.info(END);
    }

    @Override
    public void run() {
        log.info(START);
        log.debug("initialized: " + initialized);
        if (initialized) {
            QualityControlManager manager = SpringSupport.getInstance().findBeanByClassName(QualityControlManager.class);
            manager.rejectToBW(observedSituationEvaluationList, indicatorValuesList, observedMetricIds);
        }
        log.info(END);
    }

    /**
     * @return the observedSituationEvaluationList
     */
    public List<Integer> getObservedSituationEvaluationList() {
        return observedSituationEvaluationList;
    }

    /**
     * @param observedSituationEvaluationList the observedSituationEvaluationList to set
     */
    public void setObservedSituationEvaluationList(List<Integer> observedSituationEvaluationList) {
        this.observedSituationEvaluationList = observedSituationEvaluationList;
    }

    /**
     * @return the indicatorValuesList
     */
    public List<Integer> getIndicatorValuesList() {
        return indicatorValuesList;
    }

    /**
     * @param indicatorValuesList the indicatorValuesList to set
     */
    public void setIndicatorValuesList(List<Integer> indicatorValuesList) {
        this.indicatorValuesList = indicatorValuesList;
    }

    /**
     * @return the observedMetricIds
     */
    public List<Integer> getObservedMetricIds() {
        return observedMetricIds;
    }

    /**
     * @param observedMetricIds the observedMetricIds to set
     */
    public void setObservedMetricIds(List<Integer> observedMetricIds) {
        this.observedMetricIds = observedMetricIds;
    }
}
