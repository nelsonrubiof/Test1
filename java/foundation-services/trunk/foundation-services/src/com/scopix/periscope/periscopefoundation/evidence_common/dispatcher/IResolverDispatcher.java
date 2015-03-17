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
 * IResolverDispatcher.java
 *
 * Created on May 7, 2007, 4:58 PM
 *
 */

package com.scopix.periscope.periscopefoundation.evidence_common.dispatcher;

import com.scopix.periscope.periscopefoundation.evidence_common.exception.DispatcherNotFound;
import com.scopix.periscope.periscopefoundation.evidence_common.message.MessageAbstract;

/**
 * <p>
 * Resolves the dispatcher needed to dispatch a message.
 * </p>
 * <p>
 * The idea is to delegate the responsability of determining "who" should send
 * a specific message to this type of objects.
 * </p>
 *
 * @author jorge
 */
public interface IResolverDispatcher {
    IDispatcher getDispatcher(MessageAbstract message) throws DispatcherNotFound;
}
