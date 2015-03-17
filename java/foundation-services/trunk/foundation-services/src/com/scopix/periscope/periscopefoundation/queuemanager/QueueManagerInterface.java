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
 * Interface of queue manager. It defines methods to add or remove elements to a
 * queueu. These elements are reprensented by {@link QueuedObject}. When adding
 * an element to a queue, a queuetype must be specified, so it can manage more
 * than a queue.
 * 
 * @author maximiliano.vazquez
 * 
 */
public interface QueueManagerInterface<T extends QueueType, E extends QueuedObject<T>> {

  /**
   * Get next element from queue
   */
  E dequeue(T queueType);

  /**
   * Remove specified element from queue
   */
  void remove(E o);

}
