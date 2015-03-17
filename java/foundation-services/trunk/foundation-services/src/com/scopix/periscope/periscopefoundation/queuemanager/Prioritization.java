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
 * Prioritization.java
 *
 * Created on 07-05-2008, 10:47:28 AM
 *
 */
package com.scopix.periscope.periscopefoundation.queuemanager;

import com.scopix.periscope.periscopefoundation.states.configs.StateTransition;
import com.scopix.periscope.periscopefoundation.states.exception.InvalidOperationException;

/**
 *
 * @author César Abarza Suazo.
 * @version 1.0.0
 */
public interface Prioritization {

    Integer getId();

    void setPriority(Integer priority);

    Integer getPriority();

    @StateTransition(enumType = "BACK_TO_QUEUE")
    void backToQueue() throws InvalidOperationException;

    @StateTransition(enumType = "ASIGN_TO_CHECK")
    void asignToCheck() throws InvalidOperationException;

    @StateTransition(enumType = "DELETE")
    void delete() throws InvalidOperationException;

    @StateTransition(enumType = "COMPUTE_RESULTS")
    void computeResult() throws InvalidOperationException;

    @StateTransition(enumType = "FAIL")
    void fail() throws InvalidOperationException;
}
