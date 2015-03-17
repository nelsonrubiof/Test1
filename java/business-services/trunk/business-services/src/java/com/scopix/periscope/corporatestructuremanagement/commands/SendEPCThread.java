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
 *  SendEPCThread.java
 * 
 *  Created on 29-11-2010, 03:49:09 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 */
public class SendEPCThread extends Thread {

    private static Logger log = Logger.getLogger(SendEPCThread.class);
    private List<Integer> extractionPlanCustomizingIds;
    private Integer storeId;
    private long sessionId;
    private Integer processId;
    private boolean initialized;
    private UpdateSendExtractionPlanProcessCommand updateSendExtractionPlanProcessCommand;

    public void init(List<Integer> epcIds, Integer stId, long sId, Integer processId) {
        setExtractionPlanCustomizingIds(epcIds);
        setStoreId(stId);
        setSessionId(sId);
        setProcessId(processId);
        setInitialized(true);
    }

    @Override
    public void run() {
        log.debug("start initialized:" + isInitialized());

        if (isInitialized()) {
            CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);

            manager.setSendExecutionEPCMessage("");
            manager.setSendExecutionEPCStatus(-1);
            try {
                if (getExtractionPlanCustomizingIds() != null && getExtractionPlanCustomizingIds().size() > 0
                        && getStoreId() != null) {
                    manager.sendExtractionPlanCustomizing(getExtractionPlanCustomizingIds(), getStoreId(), getSessionId());
                } else if (getStoreId() != null) {
                    manager.sendExtractionPlanCustomizingFull(getStoreId(), getSessionId());
                }
                //cerramos el proceso
                manager.setSendExecutionEPCStatus(0);
                manager.setSendExecutionEPCMessage("ENVIO_EXITOSO");
//            } catch (PeriscopeException e) {
//                log.error("Error " + e, e);
//                manager.setSendExecutionEPCMessage(e.getMessage());
//                manager.setSendExecutionEPCStatus(1);
            } catch (ScopixException e) {
                log.error("Error " + e, e);
                manager.setSendExecutionEPCMessage(e.getMessage());
                manager.setSendExecutionEPCStatus(1);
            } catch (RuntimeException e) {
                log.error("Error " + e, e);
                manager.setSendExecutionEPCMessage(e.getMessage());
                manager.setSendExecutionEPCStatus(1);
            } finally {
                manager.setSendExecutionEPC(false);
                setInitialized(false);
            }

            log.debug("actualizando Process");
            getUpdateSendExtractionPlanProcessCommand().execute(getProcessId(), manager.getSendExecutionEPCMessage(),
                    manager.getSendExecutionEPCStatus());
        }
        log.debug("end");
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public long getSessionId() {
        return sessionId;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public boolean isInitialized() {
        return initialized;
    }

    public void setInitialized(boolean initialized) {
        this.initialized = initialized;
    }

    public Integer getProcessId() {
        return processId;
    }

    public void setProcessId(Integer processId) {
        this.processId = processId;
    }

    public UpdateSendExtractionPlanProcessCommand getUpdateSendExtractionPlanProcessCommand() {
        if (updateSendExtractionPlanProcessCommand == null) {
            updateSendExtractionPlanProcessCommand = new UpdateSendExtractionPlanProcessCommand();
        }
        return updateSendExtractionPlanProcessCommand;
    }

    public void setUpdateSendExtractionPlanProcessCommand(UpdateSendExtractionPlanProcessCommand value) {
        this.updateSendExtractionPlanProcessCommand = value;
    }

    public List<Integer> getExtractionPlanCustomizingIds() {
        return extractionPlanCustomizingIds;
    }

    public void setExtractionPlanCustomizingIds(List<Integer> extractionPlanCustomizingIds) {
        this.extractionPlanCustomizingIds = extractionPlanCustomizingIds;
    }
}
