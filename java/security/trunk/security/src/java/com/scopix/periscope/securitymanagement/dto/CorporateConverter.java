/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.scopix.periscope.securitymanagement.dto;

import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.AreaType;
import com.scopix.periscope.securitymanagement.Corporate;
import com.scopix.periscope.securitymanagement.Store;
import com.scopix.periscope.securitymanagement.commands.GetCorporatesForUserCommand;
import com.scopix.periscope.securitymanagement.dao.SecurityManagementHibernateDAO;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 */
public class CorporateConverter {

    Logger log = Logger.getLogger(GetCorporatesForUserCommand.class);

    /**
     * transformar el Corporate a CorporateDTO
     *
     * @param corporate
     * @return
     */
    public CorporateDTO convertToDTO(Corporate corporate) {
        log.debug("start");

        CorporateDTO corporateDTO = new CorporateDTO();
        corporateDTO.setCorporateId(corporate.getId());
        corporateDTO.setName(corporate.getName());
        corporateDTO.setDescription(corporate.getDescription());

        //obtener los stores para el corporate
        SecurityManagementHibernateDAO dao = SpringSupport.getInstance().
            findBeanByClassName(SecurityManagementHibernateDAO.class);

        Store store = new Store();
        store.setCorporate(corporate);
        List<Store> stores = dao.getStoreList(store);
        corporateDTO.setStores(getStoreDTOs(stores));

        AreaType areaType = new AreaType();
        areaType.setCorporate(corporate);
        List<AreaType> areaTypes = dao.getAreaTypeList(areaType);
        corporateDTO.setAreaTypes(getAreaTypeDTOs(areaTypes));

        log.debug("end");

        return corporateDTO;
    }

    /**
     * Convertir AreaTypes a AreaTypesDTOs
     *
     * @param areaTypes
     * @return
     */
    private List<AreaTypeDTO> getAreaTypeDTOs(List<AreaType> areaTypes) {
        log.debug("start");

        List<AreaTypeDTO> areaTypeDTOs = new ArrayList<AreaTypeDTO>();

        for (AreaType at : areaTypes) {
            AreaTypeDTO areaTypeDTO = new AreaTypeDTO();
            areaTypeDTO.setName(at.getName());
            areaTypeDTO.setAreaTypeId(at.getId());
            areaTypeDTO.setDescription(at.getDescriptionWithCorporate());
            areaTypeDTOs.add(areaTypeDTO);
        }

        Collections.sort(areaTypeDTOs, areaTypeDTOComparator);
        log.debug("end");

        return areaTypeDTOs;
    }

    /**
     * Convertir Stores en StoreDTOs
     * @param stores
     * @return
     */
    private List<StoreDTO> getStoreDTOs(List<Store> stores) {
        log.debug("start");

        List<StoreDTO> storeDTOs = new ArrayList<StoreDTO>();

        for (Store s: stores){
            StoreDTO storeDTO = new StoreDTO();
            storeDTO.setStoreId(s.getId());
            storeDTO.setName(s.getName());
            storeDTO.setDescription(s.getDescriptionWithCorporate());
            storeDTOs.add(storeDTO);
        }
        Collections.sort(storeDTOs, storeDTOComparator);
        log.debug("end");

        return storeDTOs;
    }

    private Comparator<AreaTypeDTO> areaTypeDTOComparator = new Comparator<AreaTypeDTO>(){

        public int compare(AreaTypeDTO o1, AreaTypeDTO o2) {
            return o1.getDescription().compareTo(o2.getDescription());
        }
    };

    private Comparator<StoreDTO> storeDTOComparator = new Comparator<StoreDTO>(){

        public int compare(StoreDTO o1, StoreDTO o2) {
            return o1.getDescription().compareTo(o2.getDescription());
        }
    };

}
