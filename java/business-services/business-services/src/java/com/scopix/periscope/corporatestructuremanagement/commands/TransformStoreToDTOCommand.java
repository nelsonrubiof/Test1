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
 *  TransformStoreToDTOCommand.java
 * 
 *  Created on 21-09-2010, 06:15:52 PM
 * 
 */
package com.scopix.periscope.corporatestructuremanagement.commands;

import com.scopix.periscope.corporatestructuremanagement.PeriodInterval;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.corporatestructuremanagement.dto.PeriodIntervalDTO;
import com.scopix.periscope.corporatestructuremanagement.dto.StoreDTO;
import java.util.ArrayList;
import java.util.List;

/**
 * @author nelson
 */
public class TransformStoreToDTOCommand {

    public List<StoreDTO> execute(List<Store> stores) {
        List<StoreDTO> storeDTOs = new ArrayList<StoreDTO>();
        for (Store s : stores) {
            StoreDTO dto = new StoreDTO();
            dto.setId(s.getId());
            dto.setName(s.getName());
            dto.setDescription(s.getDescription());
            if (s.getPeriodIntervals() != null && !s.getPeriodIntervals().isEmpty()) {
                dto.setPeriodIntervalDTOs(new ArrayList<PeriodIntervalDTO>());
                for (PeriodInterval interval : s.getPeriodIntervals()) {
                    PeriodIntervalDTO iDto = new PeriodIntervalDTO();
                    iDto.setId(interval.getId());
                    iDto.setStoreId(interval.getStore().getId());
                    iDto.setInitTime(interval.getInitTime());
                    iDto.setEndTime(interval.getEndTime());
                    iDto.setMonday(interval.isMonday());
                    iDto.setTuesday(interval.isTuesday());
                    iDto.setWednesday(interval.isWednesday());
                    iDto.setThursday(interval.isThursday());
                    iDto.setFriday(interval.isFriday());
                    iDto.setSaturday(interval.isSaturday());
                    iDto.setSunday(interval.isSunday());
                    dto.getPeriodIntervalDTOs().add(iDto);
                }
            }

            storeDTOs.add(dto);
        }
        return storeDTOs;
    }
}
