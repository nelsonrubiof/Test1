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
 *  ReportingWebServicesImpl.java
 *  
 *  Created on 11-01-2011, 03:25:24 PM
 */
package com.scopix.periscope.reporting.services.webservices;

import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.dto.AreaTypeDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.exception.ScopixWebServiceException;
import com.scopix.periscope.periscopefoundation.util.SortUtil;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.reporting.ReportingManager;
import com.scopix.periscope.reporting.dto.UploadProcessDTO;
import com.scopix.periscope.reporting.dto.UploadProcessDetailAddDTO;
import com.scopix.periscope.reporting.dto.UploadProcessDetailDTO;
import java.util.LinkedHashMap;
import java.util.List;
import javax.jws.WebService;

/**
 *
 * @author Nelson Rubio
 */
@WebService(endpointInterface = "com.scopix.periscope.reporting.services.webservices.ReportingWebServices")
//@CustomWebService(serviceClass = ReportingWebServices.class)
@SpringBean(rootClass = ReportingWebServices.class)
public class ReportingWebServicesImpl implements ReportingWebServices {

    @Override
    public UploadProcessDTO getUploadProcess(long sessionId) throws ScopixWebServiceException {
        UploadProcessDTO dto = null;
        try {
            dto = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).
                    getUploadProcess(sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return dto;
    }

    @Override
    public List<StoreDTO> getStores(long sessionId) throws ScopixWebServiceException {
        List<StoreDTO> storeDTOs = null;
        try {
            storeDTOs = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).
                    getStoreDTOs(null, sessionId);

            //ordenar por name, descripcion
            LinkedHashMap<String, Boolean> colsSort = new LinkedHashMap<String, Boolean>();
            colsSort.put("name", Boolean.FALSE);
            colsSort.put("description", Boolean.FALSE);
            SortUtil.sortByColumn(colsSort, storeDTOs);
            
            StoreDTO dtoDummy = new StoreDTO();
            storeDTOs.add(dtoDummy);
            
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return storeDTOs;
    }

    @Override
    public List<AreaTypeDTO> getAreasType(long sessionId) throws ScopixWebServiceException {
        List<AreaTypeDTO> lista = null;
        try {
            lista = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).
                    getAreaTypeDTOList(sessionId);
            
            AreaTypeDTO dtoDummy = new AreaTypeDTO();
            lista.add(dtoDummy);
            
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return lista;
    }

    @Override
    public List<UploadProcessDetailDTO> getUploadProcessDetail(long sessionId) throws ScopixWebServiceException {
        List<UploadProcessDetailDTO> dtos = null;
        try {
            dtos = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).
                    getUploadProcessDetails(sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return dtos;
    }

    @Override
    public UploadProcessDetailAddDTO addUploadProcessDetail(List<Integer> storesId, List<Integer> areasTypeId, String endDate,
            long sessionId) throws ScopixWebServiceException {
        UploadProcessDetailAddDTO dto = null;
        try {
            dto = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).
                    addUploadProcessDetail(storesId, areasTypeId, endDate, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return dto;
    }

    @Override
    public List<UploadProcessDetailDTO> deleteUploadProcessDetail(List<Integer> updId, long sessionId) throws ScopixWebServiceException {
        List<UploadProcessDetailDTO> dtos = null;
        try {
            dtos = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).
                    deleteUploadProcessDetail(updId, sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return dtos;
    }

    @Override
    public UploadProcessDTO uploadNow(long sessionId) throws ScopixWebServiceException {
        UploadProcessDTO dto = null;
        try {
            dto = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).
                    uploadNow(sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return dto;
    }

    @Override
    public UploadProcessDTO cancelUpload(long sessionId) throws ScopixWebServiceException {
        UploadProcessDTO dto = null;
        try {
            dto = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).cancelUpload(sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
        return dto;
    }

    @Override
    public Boolean getStateUploadingAutomatic(long sessionId) throws ScopixWebServiceException {
        Boolean ret = SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).
                getStateUploadingAutomatic(sessionId);
        return ret;
    }

    @Override
    public void enabledUploadingAutomatic(long sessionId) throws ScopixWebServiceException {
        try {
            SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).enabledUploadingAutomatic(sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
    }

    @Override
    public void disabledUploadingAutomatic(long sessionId) throws ScopixWebServiceException {
        try {
            SpringSupport.getInstance().findBeanByClassName(ReportingManager.class).disabledUploadingAutomatic(sessionId);
        } catch (ScopixException e) {
            throw new ScopixWebServiceException(e);
        }
    }
}
