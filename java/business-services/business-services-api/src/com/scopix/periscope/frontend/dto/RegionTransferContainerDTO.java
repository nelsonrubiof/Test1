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
package com.scopix.periscope.frontend.dto;

import java.util.List;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author Sebastian
 *
 */
@XmlRootElement(name = "RegionTransferContainerDTO")
public class RegionTransferContainerDTO extends RestResponseDTO {
    
    private EvidenceRegionTransferStatsDTO evidenceRegionTransferStatsDTO;

    private List<EvidenceRegionTransferDTO> evidenceRegionTransferDTOs;

    /**
     * @return the evidenceRegionTransferStatsDTO
     */
    public EvidenceRegionTransferStatsDTO getEvidenceRegionTransferStatsDTO() {
        return evidenceRegionTransferStatsDTO;
    }

    /**
     * @param evidenceRegionTransferStatsDTO the evidenceRegionTransferStatsDTO to set
     */
    public void setEvidenceRegionTransferStatsDTO(EvidenceRegionTransferStatsDTO evidenceRegionTransferStatsDTO) {
        this.evidenceRegionTransferStatsDTO = evidenceRegionTransferStatsDTO;
    }

    /**
     * @return the evidenceRegionTransferDTOs
     */
    public List<EvidenceRegionTransferDTO> getEvidenceRegionTransferDTOs() {
        return evidenceRegionTransferDTOs;
    }

    /**
     * @param evidenceRegionTransferDTOs the evidenceRegionTransferDTOs to set
     */
    public void setEvidenceRegionTransferDTOs(List<EvidenceRegionTransferDTO> evidenceRegionTransferDTOs) {
        this.evidenceRegionTransferDTOs = evidenceRegionTransferDTOs;
    }
}
