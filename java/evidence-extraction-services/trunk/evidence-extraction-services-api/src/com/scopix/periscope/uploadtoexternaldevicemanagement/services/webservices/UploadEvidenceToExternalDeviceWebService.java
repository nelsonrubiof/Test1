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
 * UploadEvidenceToExternalDeviceWebService.java
 *
 * Created on 11-01-2010, 01:09:25 PM
 *
 */
package com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices;

import com.scopix.periscope.uploadtoexternaldevicemanagement.dto.UploadEvidenceToExternalDeviceDTO;
import com.scopix.periscope.uploadtoexternaldevicemanagement.dto.UploadEvidenceToExternalDeviceResponseDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import javax.jws.WebMethod;
import javax.jws.WebService;

/**
 *
 * @author Gustavo Alvarez
 */
//@CustomWebService
@WebService(name = "UploadEvidenceToExternalDeviceWebService")
public interface UploadEvidenceToExternalDeviceWebService {

    @WebMethod
    UploadEvidenceToExternalDeviceResponseDTO mountDevice(UploadEvidenceToExternalDeviceDTO dto) throws ScopixWebServiceException;

    @WebMethod
    UploadEvidenceToExternalDeviceResponseDTO unMountDevice(UploadEvidenceToExternalDeviceDTO dto, String storeName)
            throws ScopixWebServiceException;

    @WebMethod
    UploadEvidenceToExternalDeviceResponseDTO formatDevice(UploadEvidenceToExternalDeviceDTO dto) throws
            ScopixWebServiceException;

    @WebMethod
    UploadEvidenceToExternalDeviceResponseDTO getCopyInfo(UploadEvidenceToExternalDeviceDTO dto, String storeName) throws
            ScopixWebServiceException;
}
