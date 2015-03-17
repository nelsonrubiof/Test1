/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * SendExtractionPlanToExtractionServerCommand.java
 *
 * Created on 02-07-2008, 03:43:10 AM
 *
 */
package com.scopix.periscope.extractionservicesserversmanagement.commands;

import com.jcraft.jsch.JSchException;
import com.scopix.periscope.extractionplanmanagement.services.webservices.ExtractionPlanWebService;
import com.scopix.periscope.extractionplanmanagement.services.webservices.client.ExtractionPlanWebServiceClient;
import com.scopix.periscope.extractionservicesserversmanagement.EvidenceExtractionServicesServer;
import com.scopix.periscope.extractionservicesserversmanagement.dao.EvidenceExtractionServicesServerDAO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.SSHUtil;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 */
public class GetExtractionPlanToPastCommand {

    Logger log = Logger.getLogger(GetExtractionPlanToPastCommand.class);

    public List<Integer> execute(String date, Integer extractionServicesServerId, String storeName) throws ScopixException {
        log.debug("[execute] start");
        List<Integer> data = null;
        try {
            EvidenceExtractionServicesServerDAO dao = SpringSupport.getInstance().findBeanByClassName(
                    EvidenceExtractionServicesServerDAO.class);
            // Get evidence Extraction Server
            EvidenceExtractionServicesServer evidenceExtractionServicesServer
                    = dao.getEvidenceExtractionServicesServerByEvidenceExtractionServicesIdInBusinessServices(
                            extractionServicesServerId, storeName);

            if (evidenceExtractionServicesServer != null) {
                ExtractionPlanWebService extractionPlanWebService = ExtractionPlanWebServiceClient.getInstance().getWebServiceClient(
                        evidenceExtractionServicesServer.getUrl());
                // open ssh connection
                SSHUtil sshutil = new SSHUtil(evidenceExtractionServicesServer.getSshAddress(),
                        Integer.parseInt(evidenceExtractionServicesServer.getSshPort()),
                        evidenceExtractionServicesServer.getSshUser(),
                        evidenceExtractionServicesServer.getSshPassword());
                log.debug("SshLocalTunnelPort=" + evidenceExtractionServicesServer.getSshLocalTunnelPort()
                        + ", SshRemoteTunnelPort=" + evidenceExtractionServicesServer.getSshRemoteTunnelPort());
                sshutil.connect();
                log.debug("tunnel...");
                sshutil.addTunnel(Integer.parseInt(evidenceExtractionServicesServer.getSshLocalTunnelPort()),
                        "127.0.0.1",
                        Integer.parseInt(evidenceExtractionServicesServer.getSshRemoteTunnelPort()));
                try {
                    // call the webservice
                    log.debug("call webservice. StoreName: " + storeName);
                    data = extractionPlanWebService.extractionPlanToPast(date, storeName);
                    //revisamos la lista que llega y sacamos los null si existen
                    if (data != null) {
                        data = cleanNullData(data);
                        data = this.getEvidenceRequestIds(data);
                    }
                    log.debug("end webservice call");
                } finally {
                    sshutil.disconnect();
                }
            }
        } catch (NumberFormatException e) {
            throw new ScopixException(e);
        } catch (JSchException e) {
            throw new ScopixException(e);
        } catch (ScopixWebServiceException e) {
            throw new ScopixException(e);
        }
        log.debug("[execute] end, data = " + data);
        return data;
    }

    public List<Integer> getEvidenceRequestIds(List<Integer> evidenceServicesRequestIds) throws ScopixException {
        log.debug("[getEvidenceRequestIds] start, evidenceServicesRequestIds = " + evidenceServicesRequestIds);
        List<Integer> evidenceRequests = null;
        if (evidenceServicesRequestIds != null && !evidenceServicesRequestIds.isEmpty()) {
            EvidenceExtractionServicesServerDAO dao = SpringSupport.getInstance().findBeanByClassName(
                    EvidenceExtractionServicesServerDAO.class);
            evidenceRequests = dao.getRemoteEvidenceRequestIdList(evidenceServicesRequestIds);

            log.debug("[getEvidenceRequestIds] end, evidenceRequests = " + evidenceRequests);
        }
        return evidenceRequests;
    }

    private List<Integer> cleanNullData(List<Integer> data) {
        List<Integer> ret = new ArrayList<Integer>();
        for (Integer d : data) {
            if (d != null) {
                ret.add(d);
            }
        }
        return ret;
    }
}
