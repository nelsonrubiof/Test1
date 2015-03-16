/**
 *
 * Copyright © 2014, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 *
 *
 */
package com.scopix.periscope.frontend.services.webservices;

import com.scopix.periscope.frontend.dto.RegionTransferContainerDTO;
import com.scopix.periscope.frontend.dto.RequestRegionTransferDTO;
import javax.jws.WebService;

/**
 * @author Sebastian
 *
 */
@WebService(name = "EvidenceRegionTransferWebServices")
public interface EvidenceRegionTransferWebServices {

    /**
     * Gets all evidences region transfers based on a given criteria
     *
     * @param request
     * @param sessionId
     * @return RegionTransferContainerDTO
     */
    RegionTransferContainerDTO getTransferByCriteria(RequestRegionTransferDTO request,
            Long sessionId);
}
