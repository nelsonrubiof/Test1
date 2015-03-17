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
 *  EvidenceServicesWebServiceClientMock.java
 * 
 *  Created on 27-09-2010, 11:11:13 AM
 * 
 */
package mockup;

import com.scopix.periscope.extractionservicesserversmanagement.services.webservices.EvidenceServicesWebService;
import com.scopix.periscope.extractionservicesserversmanagement.services.webservices.client.EvidenceServicesWebServiceClient;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.util.Map;

/**
 *
 * @author nelson
 */
public class EvidenceServicesWebServiceClientMock implements EvidenceServicesWebServiceClient {

    private EvidenceServicesWebService webService;

    @Override
    public EvidenceServicesWebService getWebServiceClient(String url) {

        return getWebService();
    }

//    @Override
//    public void initializeClients(Map<String, String> map) throws ScopixException {
//        //no hacemos nada
//    }

    public EvidenceServicesWebService getWebService() {
        if (webService == null) {
            webService = new EvidenceServicesWebServiceMock();
        }
        return webService;
    }

    public void setWebService(EvidenceServicesWebService webService) {
        this.webService = webService;
    }

    @Override
    public void initializeClients(Map<String, String> map) throws ScopixException {
        // no hace nada
    }
}
