/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  TransformToStoreFrontEndDTOCommand.java
 * 
 *  Created on Jul 29, 2014, 12:49:39 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.frontend.dto.StoreFrontEndDTO;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Sebastian
 */
public class TransformToStoreFrontEndDTOCommand {

    /**
     * Converts a list of Stores to a list of StoreFrontEndDTOs
     *
     * @param stores
     * @return List<StoreFrontEndDTO>
     * @throws ScopixException
     */
    public List<StoreFrontEndDTO> execute(List<Store> stores) throws ScopixException {
        if (stores == null) {
            throw new ScopixException("Store list can not be null");
        }
        List<StoreFrontEndDTO> storeModels = new ArrayList<StoreFrontEndDTO>();
        for (Store store : stores) {
            storeModels.add(storeModelDTORowMapper(store));
        }
        return storeModels;
    }

    /**
     * Converts a Stores to a StoreFrontEndDTOs
     *
     * @param store
     * @return StoreFrontEndDTO
     * @throws ScopixException
     */
    public StoreFrontEndDTO execute(Store store) throws ScopixException {
        if (store == null) {
            throw new ScopixException("Store can not be null");
        }
        return storeModelDTORowMapper(store);
    }

    private StoreFrontEndDTO storeModelDTORowMapper(Store store) {
        StoreFrontEndDTO storeModelDTO = new StoreFrontEndDTO();
        storeModelDTO.setId(store.getId());
        storeModelDTO.setName(store.getName());
        return storeModelDTO;
    }
}
