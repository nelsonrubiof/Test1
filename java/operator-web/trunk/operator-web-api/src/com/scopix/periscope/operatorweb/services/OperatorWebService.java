/*
 * 
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * OperatorWebService.java
 * 
 * Created on 2/09/2014
 */
package com.scopix.periscope.operatorweb.services;

import javax.jws.WebService;

import com.scopix.periscope.operatorimages.ResultMarksContainerDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;

@WebService(name = "OperatorWebService")
public interface OperatorWebService {

    void notifyProofsGeneration(ResultMarksContainerDTO container) throws ScopixException;

}