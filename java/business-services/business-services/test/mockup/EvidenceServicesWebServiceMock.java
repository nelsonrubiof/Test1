/*
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
 *  EvidenceExtractionWebServiceMock.java
 *  
 *  Created on 14-09-2010, 04:43:00 PM
 */
package mockup;

import com.scopix.periscope.corporatestructuremanagement.dto.EvidenceExtractionServicesServerDTO;
import com.scopix.periscope.extractionservicesserversmanagement.services.webservices.EvidenceServicesWebService;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.util.List;

/**
 *
 * @author desarrollo
 */
public class EvidenceServicesWebServiceMock implements EvidenceServicesWebService {

    private List<EvidenceExtractionServicesServerDTO> eessdtos;

    public EvidenceServicesWebServiceMock() {
    }

    @Override
    public void newExtractionPlan(List<EvidenceExtractionServicesServerDTO> list) throws ScopixWebServiceException {
        setEessdtos(list);
    }

    @Override
    public List<Integer> extractionPlanToPast(String string, Integer intgr, String string1) throws ScopixWebServiceException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public List<EvidenceExtractionServicesServerDTO> getEessdtos() {
        return eessdtos;
    }

    public void setEessdtos(List<EvidenceExtractionServicesServerDTO> eessdtos) {
        this.eessdtos = eessdtos;
    }

}
