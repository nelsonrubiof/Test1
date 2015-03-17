/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * IDispatcher.java
 *
 * Created on May 04, 2007, 2:59 PM
 *
 */
package com.scopix.periscope.periscopefoundation.evidence_common.dispatcher;

import com.scopix.periscope.periscopefoundation.evidence_common.exception.DispatcherException;
import com.scopix.periscope.periscopefoundation.evidence_common.message.MessageAbstract;

/**
 * Defines the interface a dispatcher should implement, so it can be used by 
 * a dispatcher resolver.
 *
 * @author jorge
 */
public interface IDispatcher {
    void beamMeUpScotty(MessageAbstract message) throws DispatcherException;
}