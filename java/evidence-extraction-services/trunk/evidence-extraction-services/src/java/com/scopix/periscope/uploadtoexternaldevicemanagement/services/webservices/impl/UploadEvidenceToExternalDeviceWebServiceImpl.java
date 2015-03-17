/*
 *
 * Copyright (c) 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * UploadEvidenceToExternalDeviceWebServiceImpl.java
 *
 * Created on 11-01-2010, 01:20:39 PM
 *
 */
package com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.impl;

import com.scopix.periscope.extractionmanagement.ExtractionManager;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.uploadtoexternaldevicemanagement.dto.UploadEvidenceToExternalDeviceDTO;
import com.scopix.periscope.uploadtoexternaldevicemanagement.dto.UploadEvidenceToExternalDeviceResponseDTO;
import com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.UploadEvidenceToExternalDeviceWebService;
import javax.jws.WebService;

/**
 *
 * @author Gustavo Alvarez
 * @version 1.0.0
 */
//@CustomWebService(serviceClass = UploadEvidenceToExternalDeviceWebService.class)
@WebService(endpointInterface =
        "com.scopix.periscope.uploadtoexternaldevicemanagement.services.webservices.UploadEvidenceToExternalDeviceWebService")
@SpringBean
public class UploadEvidenceToExternalDeviceWebServiceImpl implements UploadEvidenceToExternalDeviceWebService {

    @Override
    public UploadEvidenceToExternalDeviceResponseDTO mountDevice(UploadEvidenceToExternalDeviceDTO dto) 
            throws ScopixWebServiceException {
        UploadEvidenceToExternalDeviceResponseDTO responseDTO = null;
        try {
            responseDTO = new UploadEvidenceToExternalDeviceResponseDTO();
            
            ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
            boolean resp = extractionManager.mountExternalDevice();
            
            responseDTO.setRespuesta(resp);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }

        return responseDTO;
    }

    @Override
    public UploadEvidenceToExternalDeviceResponseDTO unMountDevice(UploadEvidenceToExternalDeviceDTO dto, String storeName) throws
            ScopixWebServiceException {
        UploadEvidenceToExternalDeviceResponseDTO responseDTO = null;
        try {
            responseDTO = new UploadEvidenceToExternalDeviceResponseDTO();
            
            ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
            boolean resp = extractionManager.unMountExternalDevice(storeName);
            
            responseDTO.setRespuesta(resp);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return responseDTO;
    }

    @Override
    public UploadEvidenceToExternalDeviceResponseDTO formatDevice(UploadEvidenceToExternalDeviceDTO dto) throws
            ScopixWebServiceException {

        UploadEvidenceToExternalDeviceResponseDTO responseDTO = null;
        try {
            responseDTO = new UploadEvidenceToExternalDeviceResponseDTO();
            
            ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
            boolean resp = extractionManager.formatDevice();
            
            responseDTO.setRespuesta(resp);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }

        return responseDTO;
    }

    @Override
    public UploadEvidenceToExternalDeviceResponseDTO getCopyInfo(UploadEvidenceToExternalDeviceDTO dto, String storeName)
            throws ScopixWebServiceException {
        UploadEvidenceToExternalDeviceResponseDTO responseDTO = new UploadEvidenceToExternalDeviceResponseDTO();

        ExtractionManager extractionManager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
        Integer resp = extractionManager.getCopyInfo(storeName);

        responseDTO.setArchivosPorCopiar(resp);

        return responseDTO;
    }
}
