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
 * LazyEnqueue.java
 *
 * Created on 07-05-2008, 11:05:49 AM
 *
 */
package com.scopix.periscope.periscopefoundation.queuemanager;

import com.scopix.periscope.periscopefoundation.lazymanager.Lazy;

/**
 *
 * @author César Abarza Suazo.
 */
public class LazyEnqueue implements Lazy {

    private QueuedObject object;

    private QueueManager queueManager;

    public static LazyEnqueue create(QueueManager queueManager, QueuedObject object) {

        LazyEnqueue lazyEnqueue = new LazyEnqueue();
        lazyEnqueue.queueManager = queueManager;
        lazyEnqueue.object = object;

        return lazyEnqueue;
    }

    protected LazyEnqueue() {
    }

    /**
     * Resolves lazy operation. It performs real queue
     */
    public void resolve() {
        queueManager.enqueueInmediately(object);
    }
}
