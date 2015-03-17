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
 * This interface represents a queue type. It must specify if queue has blockant or non blockant queries. If a queue is blocker,
 * it means that when someone gets an element, this connection will remain blocked until an elements is available. If queue is non
 * blockant, when an elements is requested and queue is empty, request will finish and returns null.
 *
 * @author maximiliano.vazquez
 * @version 2.0.0
 *
 */
public interface QueueType {

    Boolean isBlocker();
}
