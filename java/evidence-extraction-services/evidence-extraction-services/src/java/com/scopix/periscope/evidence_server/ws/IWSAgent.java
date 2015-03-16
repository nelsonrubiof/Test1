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
 * IWSAgent.java
 *
 * Created on May 3, 2007, 9:20 AM
 *
 */

package com.scopix.periscope.evidence_server.ws;

import com.scopix.periscope.evidence_server.ws.impl.EvidenceAvailableDto;
import java.util.List;

/**
 *
 * @author jorge
 */
public interface IWSAgent {
    WSResult newEvidenceAvailable(List<EvidenceAvailableDto> listEvidenceAvilable);
}
