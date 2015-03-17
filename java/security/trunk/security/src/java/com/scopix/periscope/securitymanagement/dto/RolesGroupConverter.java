
package com.scopix.periscope.securitymanagement.dto;

import com.scopix.periscope.securitymanagement.RolesGroup;
import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 */
public class RolesGroupConverter {

    Logger log = Logger.getLogger(RolesGroupConverter.class);

    public RolesGroupDTO convertToDTO(RolesGroup rolesGroup){
        log.debug("start");
        RolesGroupDTO rolesGroupDTO = new RolesGroupDTO();

        rolesGroupDTO.setRolesGroupId(rolesGroup.getId());
        rolesGroupDTO.setName(rolesGroup.getName());
        rolesGroupDTO.setDescription(rolesGroup.getDescription());

        log.debug("end");
        return rolesGroupDTO;
    }

}
