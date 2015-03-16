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
 *  BusinessCommonsServices.java
 * 
 *  Created on 27-05-2014, 05:20:31 PM
 * 
 */
package com.scopix.periscope.businessservices.commons.webservices;

import com.scopix.periscope.businessservices.commons.FileDownloadList;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import java.io.OutputStream;
import java.util.List;
import javax.jws.WebService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;
import org.apache.cxf.jaxrs.ext.multipart.Attachment;

/**
 *
 * @author Nelson
 */
@WebService(name = "BusinessCommonsServices")
public interface BusinessCommonsServices {

    Response expirePendingEvaluations(String date) throws ScopixWebServiceException;

    Response uploadExtractionPlanCustomizing(List<Attachment> csv, HttpServletRequest request) throws ScopixWebServiceException;

    FileDownloadList getAllFiles(HttpServletRequest request) throws ScopixWebServiceException;

    OutputStream getFile(String folder,String fileName, HttpServletRequest request, HttpServletResponse response) 
        throws ScopixWebServiceException;
}
