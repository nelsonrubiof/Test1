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
 * ExtractionPlanWebService.java
 *
 * Created on 02-07-2008, 01:58:44 AM
 *
 */
package com.scopix.periscope.extractionplanmanagement.services.webservices;

import com.scopix.periscope.extractionplanmanagement.dto.ExtractionServerDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.util.List;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author marko.perich
 */
//@CustomWebService
@WebService(name = "ExtractionPlanWebService")
public interface ExtractionPlanWebService {

    @WebMethod
    void newExtractionPlan(ExtractionServerDTO extractionServerDTO) throws ScopixWebServiceException;

    @WebMethod
    List<Integer> extractionPlanToPast(String date, String storeName) throws ScopixWebServiceException;
}