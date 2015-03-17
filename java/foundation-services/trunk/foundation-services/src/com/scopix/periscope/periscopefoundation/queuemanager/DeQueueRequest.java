/*
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

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

/**
 * This class is used to perform synch dequeues. It works in conjunction with
 * {@link DirectExecutor}
 * 
 * @author maximiliano.vazquez
 * 
 */
public class DeQueueRequest<T extends QueueType, E extends QueuedObject<T>> implements Runnable {

  public DeQueueRequest(Queue<E> q) {
    this.q = q;
  }

  public E getResult() {
    return this.result;
  }

  /**
   * Performs synchronized dequeue operation.
   */
  public synchronized void run() {
    try {

      BlockingQueue<E> bq = (BlockingQueue) this.q;
      E take = bq.take();

      this.result = take;
    } catch (InterruptedException e) {
      throw new UnexpectedRuntimeException(e);
    }

  }

  private Queue<E> q;

  private E result;

}
