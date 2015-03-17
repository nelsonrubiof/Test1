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
 * This class represents elements managed by {@link QueueManager}. It specifies
 * an id, priority and queue type.
 * 
 * @author maximiliano.vazquez
 * 
 */
public class QueuedObject<T extends QueueType> implements Prioritized, Comparable<QueuedObject> {

  /**
   * Creates a new {@link QueuedObject} object
   */
  public static <T extends QueueType> QueuedObject create(Integer id, Priority priority, T queueType) {
    QueuedObject queuedObject = new QueuedObject();
    queuedObject.id = id;
    queuedObject.priority = priority;
    queuedObject.queueType = queueType;
    return queuedObject;
  }

  /**
   * Determine how {@link QueuedObject} are compared.
   */
  public int compareTo(QueuedObject other) {
    return this.getPriority().compareTo(other.getPriority());
  }

  public Integer getId() {
    return this.id;
  }

  public Priority getPriority() {
    return this.priority;
  }

  public T getQueueType() {
    return this.queueType;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public void setPriority(Priority priority) {
    this.priority = priority;
  }

  /**
   * Retruns String representation ob this object.
   */
  @Override
  public String toString() {
    return this.id.toString() + "(priority: " + this.priority + ")";
  }

  private Integer id;

  private Priority priority;

  private T queueType;

    
}
