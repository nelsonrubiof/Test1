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
 * QueueManager.java
 *
 * Created on 07-05-2008, 10:40:47 AM
 *
 */
package com.scopix.periscope.periscopefoundation.queuemanager;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.scopix.periscope.periscopefoundation.lazymanager.LazyManager;
import com.scopix.periscope.periscopefoundation.util.config.HibernateSupport;

/**
 *
 * @author César Abarza Suazo.
 * @version 1.0.0
 */
public class QueueManager {

    Logger log = Logger.getLogger(QueueManager.class);

    private static AbstractQueueManager queue = AbstractQueueManager.create();

    private QueueType queueType;

    public QueueManager(QueueType queueType) {
        super();
        this.queueType = queueType;
        this.initQueueForSpecificType(queueType);
    }

    public void createQueue(){
        if(!this.queue.exist(queueType)){
            this.queue.newQueue(queueType);
        }
    }
    
    private Priority selectPriority(Prioritization object) {
        Priority priority = null;
        if (object.getPriority() != null) {
            priority = Priority.create(object.getPriority());
        } else {
            priority = Priority.create(object.getId());
        }
        return priority;
    }

    private QueueType selectQueueType() {
        return this.queueType;
    }

    /**
     * This method is used to empty executors queue.
     */
    public void clearExecutorsQueue() {
        this.queue.clearExecutorsQueue();
    }

    /**
     * This method is for debug purposes and it shows queue state on console.
     */
    public void debugQueue() {
        this.queue.debugQueue();
    }

    /**
     * Get next element from queue
     * @return 
     */
    public Object dequeue() {
        return this.queue.dequeue(queueType);
    }

    /**
     * Get next element from queue on specified queue type
     * @param clazz
     * @return 
     */
    public Prioritization dequeueObject(Class<? extends Prioritization> clazz) {
        log.debug("[dequeueObject] start");
        log.debug("[dequeueObject] Class: " + clazz);
        QueuedObject<QueueType> p = this.queue.dequeue(queueType);
        log.debug("[dequeueObject] p: " + p);
        if (p == null) {
            return null;
        }
        Prioritization object = HibernateSupport.getInstance().findGenericDAO().get(p.getId(), clazz);
        log.debug("[dequeueObject] object: " + object);
        return object;
    }

    /**
     * This method is used to empty queue
     */
    public void emptyQueue() {
        this.queue.emptyQueue();
    }

    public void emptyQueue(QueueType queueType) {
        this.queue.emptyQueue(queueType);
    }

    /**
     * returns the queue size
     */
    public int getQueueSize(QueueType type) {
        return this.queue.size(type);
    }

    /**
     * This method is used to add new element to queue
     */
    public Prioritization enqueue(Prioritization object) {
        queueType = selectQueueType();
        Priority priority = selectPriority(object);
        object.setPriority(priority.getValue());
        QueuedObject p = QueuedObject.create(object.getId(), priority, queueType);
        this.enqueueDeferred(p);
        return object;
    }

    /**
     * This method is used to add new element to queue using {@link LazyManager}
     */
    public void enqueueDeferred(QueuedObject o) {
        LazyEnqueue lazyEnqueue = LazyEnqueue.create(this, o);
        LazyManager.getInstance().register(lazyEnqueue);
    }

    /**
     * This method is used to add new element to queue inmediately, instead of
     * {@link #enqueueDeferred(QueuedObject)}
     */
    public void enqueueInmediately(QueuedObject object) {
		log.debug("[enqueue] id: " + object.getId() + " priority: " + object.getPriority());
        this.queue.enqueue(object);
    }

    public Integer getExecutorsQueueSize() {
        return this.queue.getExecutorsQueueSize();
    }

    /**
     * Changes priority to existent element on queue
     */
    private void reEnqueue(QueuedObject<QueueType> o, Priority newPriority) {
        this.queue.remove(o);
        o.setPriority(newPriority);
        this.enqueueDeferred(o);
    }

    /**
     * Regenerates queue based on specified list of elements
     */
    private void refresh(List<QueuedObject<QueueType>> o) {
        this.emptyQueue(queueType);
        for (QueuedObject<QueueType> e : o) {
            this.enqueueDeferred(e);
        }
    }

    /**
     * Regenerates queue based on specified list of elements
     * @param list 
     */
    public void refreshQueue(List<? extends Prioritization> list) {
        List<QueuedObject<QueueType>> queuedObjects = new ArrayList();
        for (Prioritization obj : list) {
            Priority priority = this.selectPriority(obj);
            queueType = this.selectQueueType();
            QueuedObject qo = QueuedObject.create(obj.getId(), priority, queueType);
            queuedObjects.add(qo);
        }
        this.refresh(queuedObjects);
    }

    /**
     * Init different queues based on all different queue types
     */
    private synchronized void initQueueForSpecificType(QueueType type) {
        if (!queue.exist(type)) {
            queue.newQueue(type);
        }
    }
}
