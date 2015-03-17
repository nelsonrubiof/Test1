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
 *  ScheduleRejectThread.java
 * 
 *  Created on 03-07-2014, 16:35:40 PM
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
public class ScheduleRejectThread extends Thread {

    private static Logger log = Logger.getLogger(ScheduleRejectThread.class);
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
            //llamamos al manager para que este realize la evaluacion de todos los osMetric del PE y determine que se hace
            RejectThread rejectThread = new RejectThread();
            rejectThread.setObservedSituationEvaluationList(observedSituationEvaluationList);
            rejectThread.setIndicatorValuesList(indicatorValuesList);
            rejectThread.setObservedMetricIds(observedMetricIds);
            rejectThread.init();

            QualityControlManager.getRejectPoolExecutor().pause();
            QualityControlManager.getRejectPoolExecutor().runTask(rejectThread);
            QualityControlManager.getRejectPoolExecutor().resume();
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
