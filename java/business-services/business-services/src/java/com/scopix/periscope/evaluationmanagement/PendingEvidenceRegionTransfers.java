/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  PendingEvidenceRegionTransfers.java
 * 
 *  Created on Sep 4, 2014, 11:51:41 AM
 * 
 */
package com.scopix.periscope.evaluationmanagement;

import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Sebastian
 */
@SpringBean(rootClass = PendingEvidenceRegionTransfers.class)
public class PendingEvidenceRegionTransfers {

    private static Map<Integer, Integer> pendingMap = new HashMap<Integer, Integer>();

    /**
     * Adds transfer 
     * @param evidenceRegionTransferId
     */
    public void addTransfer(Integer evidenceRegionTransferId) {
        pendingMap.put(evidenceRegionTransferId, evidenceRegionTransferId);

    }

    /** 
     *  checks if there is a transfer in queue
     *
     * @param evidenceRegionTransferId
     * @return
     */
    public boolean existTransfer(Integer evidenceRegionTransferId) {

        return pendingMap.containsKey(evidenceRegionTransferId);
    }

    /**
     * remove transfer because its completed or failed(
     * @param evidenceRegionTransfer
     */
    public void removeTransfer(Integer evidenceRegionTransfer) {
        pendingMap.remove(evidenceRegionTransfer);

    }
}
