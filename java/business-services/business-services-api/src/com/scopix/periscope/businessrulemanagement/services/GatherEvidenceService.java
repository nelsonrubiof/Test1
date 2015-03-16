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
 */
package com.scopix.periscope.businessrulemanagement.services;

import com.scopix.periscope.businessrulemanagement.view.EvidenceView;
import com.scopix.periscope.periscopefoundation.services.Service;
import javax.jws.WebService;

/**
 *
 * Web Service which publishes operations related to Gather Evidence from Evidence Manager.
 *
 * @author maximiliano.vazquez
 *
 */
@WebService(name = "GatherEvidenceService")
public interface GatherEvidenceService extends Service {

    /**
     * {@inheritDoc} *
     */
    void gatherEvidence(EvidenceView evidenceView);
}
