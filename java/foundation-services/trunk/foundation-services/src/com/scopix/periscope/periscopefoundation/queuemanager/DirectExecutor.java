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

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 
 * This class is used to perform sync access to queue manager.
 * {@link DeQueueRequest}. <br>
 * It allows us to know about queue state, how many executors are waiting on
 * queue. <br>
 * This class is based on {@link ExecutorService}
 * 
 * @author maximiliano.vazquez
 * @version 1.0.0
 */
public class DirectExecutor {

  /**
   * Creates new {@link DirectExecutor}
   */
  public static synchronized DirectExecutor create() {
    DirectExecutor directExecutor = new DirectExecutor();
    return directExecutor;
  }

  /**
   * It removes all executors waiting.
   */
  public void emptyQueue() {
    this.blockingQueue.clear();
  }

  /**
   * It performs executor operation
   */
  public void execute(Runnable command) {
    this.blockingQueue.add(command);

    command.run();
    this.blockingQueue.remove(command);
  }

  /**
   * Get how many executors are waiting.
   */
  public Integer getQueueSize() {
    return this.blockingQueue.size();
  }

  /**
   * Queue to store waiting executors
   */
  private BlockingQueue<Runnable> blockingQueue = new LinkedBlockingQueue();

}
