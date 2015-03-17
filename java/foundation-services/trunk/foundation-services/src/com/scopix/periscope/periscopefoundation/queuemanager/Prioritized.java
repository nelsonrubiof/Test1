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
package com.scopix.periscope.periscopefoundation.queuemanager;


/**
 *
 * This interface represents an object which participate on priority queues.
 *
 * @author maximiliano.vazquez
 *
 */
public interface Prioritized {

    /**
     * Get priority of this {@link Prioritized} object
     */
    Priority getPriority();

    /**
     * Set priority to this {@link Prioritized} object
     */
    void setPriority(Priority priority);
}
