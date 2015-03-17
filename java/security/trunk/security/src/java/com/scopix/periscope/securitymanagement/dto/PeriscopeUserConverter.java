package com.scopix.periscope.securitymanagement.dto;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.securitymanagement.AreaType;
import com.scopix.periscope.securitymanagement.Corporate;
import com.scopix.periscope.securitymanagement.PeriscopeUser;
import com.scopix.periscope.securitymanagement.RolesGroup;
import com.scopix.periscope.securitymanagement.Store;
import com.scopix.periscope.securitymanagement.UserState;
import com.scopix.periscope.securitymanagement.commands.GetAreaTypeListCommand;
import com.scopix.periscope.securitymanagement.commands.GetCorporateListCommand;
import com.scopix.periscope.securitymanagement.commands.GetRolesGroupCommand;
import com.scopix.periscope.securitymanagement.commands.GetStoreListCommand;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 */
public class PeriscopeUserConverter {

    Logger log = Logger.getLogger(PeriscopeUserConverter.class);

    public PeriscopeUser convertFromDTO(PeriscopeUserDTO periscopeUserDTO)  throws ScopixException {
        log.debug("start");

        PeriscopeUser periscopeUser = new PeriscopeUser();
        periscopeUser.setId(periscopeUserDTO.getUserId());
        periscopeUser.setStartDate(periscopeUserDTO.getStartDate());
        periscopeUser.setModificationDate(periscopeUserDTO.getModificationDate());
        periscopeUser.setName(periscopeUserDTO.getUserName());
        periscopeUser.setFullName(periscopeUserDTO.getFullName());
        periscopeUser.setEmail(periscopeUserDTO.getEmail());
        periscopeUser.setJobPosition(periscopeUserDTO.getJobPosition());
        periscopeUser.setPassword(periscopeUserDTO.getPassword());
        periscopeUser.setCorporates(getCorporates(periscopeUserDTO.getCorporates()));
        periscopeUser.setAreaTypes(getAreaTypes(periscopeUserDTO.getAreaTypes()));
        periscopeUser.setStores(getStores(periscopeUserDTO.getStores()));
        periscopeUser.setRolesGroups(getRolesGroups(periscopeUserDTO.getRolesGroups()));
        periscopeUser.setMainCorporate(getCorporate(periscopeUserDTO.getMainCorporateId()));
        periscopeUser.setUserState(UserState.valueOf(periscopeUserDTO.getUserState()));
        periscopeUser.setDeleted(periscopeUserDTO.isDelete());

        log.debug("end");
        return periscopeUser;
    }

    /**
     *
     * @param corporateDTO
     * @return
     */
    private Corporate getCorporate(Integer corporateId) throws ScopixException {
        Corporate corporate = new Corporate();
        corporate.setId(corporateId);
        GetCorporateListCommand command = new GetCorporateListCommand();

        List<Corporate> corporates = command.execute(corporate);

        return corporates.get(0);
    }

    /**
     * Obtiene una lista de Corporates a partir de una lista de CorporateDTOs
     *
     * @param corporateDTOs
     * @return
     * @throws PeriscopeException
     */
    private List<Corporate> getCorporates(List<CorporateDTO> corporateDTOs) throws ScopixException {
        log.debug("start");
        List<Corporate> corporates = new ArrayList<Corporate>();
        GetCorporateListCommand command = new GetCorporateListCommand();
        Corporate filter = new Corporate();

        for (CorporateDTO corporateDTO : corporateDTOs) {
            filter.setId(corporateDTO.getCorporateId());
            corporates.addAll(command.execute(filter));
        }

        log.debug("end");
        return corporates;
    }

    /**
     * Obtiene una lista de AreaTypes a partir de una lista de AreaTypeDTOs
     *
     * @param areaTypeDTOs
     * @return
     * @throws PeriscopeException
     */
    private List<AreaType> getAreaTypes(List<AreaTypeDTO> areaTypeDTOs) throws ScopixException {
        log.debug("start");
        List<AreaType> areaTypes = new ArrayList<AreaType>();
        GetAreaTypeListCommand command = new GetAreaTypeListCommand();
        AreaType filter = new AreaType();

        for (AreaTypeDTO areaTypeDTO : areaTypeDTOs) {
            filter.setId(areaTypeDTO.getAreaTypeId());
            areaTypes.addAll(command.execute(filter));
        }

        log.debug("end");
        return areaTypes;
    }

    /**
     * Obtiene una lista de Stores a partir de una lista de StoreDTOs
     *
     * @param storeDTOs
     * @return
     * @throws PeriscopeException
     */
    private List<Store> getStores(List<StoreDTO> storeDTOs) throws ScopixException {
        log.debug("start");
        List<Store> stores = new ArrayList<Store>();
        GetStoreListCommand command = new GetStoreListCommand();
        Store filter = new Store();

        for (StoreDTO storeDTO : storeDTOs) {
            filter.setId(storeDTO.getStoreId());
            stores.addAll(command.execute(filter));
        }

        log.debug("end");
        return stores;
    }

    /**
     * Obtiene una lista de RolesGroups a partir de una lista de RolesGroupDTOs
     *
     * @param rolesGroupDTOs
     * @return
     * @throws PeriscopeException
     */
    private List<RolesGroup> getRolesGroups(List<RolesGroupDTO> rolesGroupDTOs) throws ScopixException {
        log.debug("start");
        List<RolesGroup> rolesGroups = new ArrayList<RolesGroup>();
        GetRolesGroupCommand command = new GetRolesGroupCommand();

        for (RolesGroupDTO rolesGroupDTO : rolesGroupDTOs) {
            rolesGroups.add(command.execute(rolesGroupDTO.getRolesGroupId()));
        }

        log.debug("end");
        return rolesGroups;
    }


    public PeriscopeUserDTO convertToDTO(PeriscopeUser periscopeUser){
        PeriscopeUserDTO periscopeUserDTO = new PeriscopeUserDTO();

        periscopeUserDTO.setUserId(periscopeUser.getId());
        periscopeUserDTO.setUserName(periscopeUser.getName());
        periscopeUserDTO.setDelete(periscopeUser.isDeleted());
        periscopeUserDTO.setEmail(periscopeUser.getEmail());
        periscopeUserDTO.setFullName(periscopeUser.getFullName());
        periscopeUserDTO.setJobPosition(periscopeUser.getJobPosition());
        periscopeUserDTO.setMainCorporateId(periscopeUser.getMainCorporate().getId());
        periscopeUserDTO.setPassword(periscopeUser.getPassword());
        periscopeUserDTO.setUserState(periscopeUser.getUserState().getName());
        periscopeUserDTO.setStartDate(periscopeUser.getStartDate());
        periscopeUserDTO.setAreaTypes(getAreaTypeDTOs(periscopeUser.getAreaTypes()));
        periscopeUserDTO.setCorporates(getCorporateDTOs(periscopeUser.getCorporates()));
        periscopeUserDTO.setStores(getStoreDTOs(periscopeUser.getStores()));
        periscopeUserDTO.setRolesGroups(getRolesGroupsDTO(periscopeUser.getRolesGroups()));

        return periscopeUserDTO;
    }

    private List<AreaTypeDTO> getAreaTypeDTOs(List<AreaType> areaTypes){
        List<AreaTypeDTO> areaTypeDTOs = new ArrayList<AreaTypeDTO>();

        for (AreaType areaType: areaTypes){
            AreaTypeDTO areaTypeDTO = new AreaTypeDTO();
            areaTypeDTO.setAreaTypeId(areaType.getId());
            areaTypeDTO.setName(areaType.getName());
            areaTypeDTO.setDescription(areaType.getDescription());
            areaTypeDTOs.add(areaTypeDTO);
        }

        return areaTypeDTOs;
    }

    private List<CorporateDTO> getCorporateDTOs(List<Corporate> corporates){
        List<CorporateDTO> corporateDTOs = new ArrayList<CorporateDTO>();

        for (Corporate corporate: corporates){
            CorporateDTO corporateDTO = new CorporateDTO();
            corporateDTO.setCorporateId(corporate.getId());
            corporateDTO.setName(corporate.getName());
            corporateDTO.setDescription(corporate.getDescription());
            corporateDTOs.add(corporateDTO);
        }

        return corporateDTOs;
    }

    private List<StoreDTO> getStoreDTOs(List<Store> stores){
        List<StoreDTO> storeDTOs = new ArrayList<StoreDTO>();

        for (Store store: stores){
            StoreDTO storeDTO = new StoreDTO();
            storeDTO.setStoreId(store.getId());
            storeDTO.setName(store.getName());
            storeDTO.setDescription(store.getDescription());
            storeDTOs.add(storeDTO);
        }

        return storeDTOs;
    }

    private List<RolesGroupDTO> getRolesGroupsDTO(List<RolesGroup> rolesGroups) {
        log.debug("start");
        List<RolesGroupDTO> rolesGroupsDTO = new ArrayList<RolesGroupDTO>();

        for (RolesGroup rolesGroup : rolesGroups) {
            RolesGroupDTO rolesGroupDTO = new RolesGroupDTO();
            rolesGroupDTO.setRolesGroupId(rolesGroup.getId());
            rolesGroupDTO.setName(rolesGroup.getName());
            rolesGroupDTO.setDescription(rolesGroup.getDescription());

            rolesGroupsDTO.add(rolesGroupDTO);
        }

        log.debug("end");
        return rolesGroupsDTO;
    }
}
