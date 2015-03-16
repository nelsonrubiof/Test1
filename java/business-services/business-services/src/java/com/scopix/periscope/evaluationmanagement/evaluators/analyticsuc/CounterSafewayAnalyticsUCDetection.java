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
 *  CounterSafewayAnalyticsUCDetection.java
 * 
 *  Created on 12-02-2013, 04:19:32 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.evaluators.analyticsuc;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author nelson
 */
public class CounterSafewayAnalyticsUCDetection {

    private Integer openCounters;
    private List<CounterSafewayAnalyticsUCCheckOut> checkOuts;

    /**
     * @return the openCounters
     */
    public Integer getOpenCounters() {
        return openCounters;
    }

    /**
     * @param openCounters the openCounters to set
     */
    public void setOpenCounters(Integer openCounters) {
        this.openCounters = openCounters;
    }

    /**
     * @return the checkOuts
     */
    public List<CounterSafewayAnalyticsUCCheckOut> getCheckOuts() {
        if (checkOuts == null){
            checkOuts = new ArrayList<CounterSafewayAnalyticsUCCheckOut>();
        }
        return checkOuts;
    }

    /**
     * @param checkOuts the checkOuts to set
     */
    public void setCheckOuts(List<CounterSafewayAnalyticsUCCheckOut> checkOuts) {
        this.checkOuts = checkOuts;
    }
    
    public void addCheckOut(CounterSafewayAnalyticsUCCheckOut checkOut) {
        getCheckOuts().add(checkOut);
    }
    
    

}
