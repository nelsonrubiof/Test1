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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

import org.apache.log4j.Logger;

import com.scopix.periscope.periscopefoundation.exception.UnexpectedRuntimeException;

/**
 * Queue manager default implementation. It defines methods to add or remove
 * elements to a queueu. These elements are reprensented by {@link QueuedObject}.
 * When adding an element to a queue, a queuetype must be specified, so it can
 * manage more than a queue. *
 * 
 * @author maximiliano.vazquez
 * 
 */
public class AbstractQueueManager<T extends QueueType, E extends QueuedObject<T>> implements QueueManagerInterface<T, E> {

    Logger log = Logger.getLogger(AbstractQueueManager.class);

    /**
     * Creates a new {@link AbstractQueueManager}
     */
    public static AbstractQueueManager create() {
        return new AbstractQueueManager();
    }

    private AbstractQueueManager() {
    // Hide visible constructor
    }

    /**
     * This method is used to empty executors queue.
     */
    public void clearExecutorsQueue() {
        DirectExecutor a = this.getDirectExecutor();
        a.emptyQueue();
    }

    /**
     * This method is for debug purposes and it shows queue state on console.
     */
    public void debugQueue() {
        HashMap<T, Queue<E>> queues = this.queuesMap;
        Set<T> keySet = queues.keySet();

        for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
            T queueKey = (T) iterator.next();
            Queue<E> queue = queues.get(queueKey);
            String debug = queueKey + ":\n";
            for (E e : queue) {
                debug += e.getId() + "\n";
            }
            log.debug(debug);
        }
    }

    /**
     * Get next element from queue
     */
    @Override
    public E dequeue(T queueType) {
        log.debug("[dequeue] start");
        Queue<E> q = this.queuesMap.get(queueType);
        //log.debug("[dequeue] q: " + q);
        if (q == null) {
            throw new UnexpectedRuntimeException("Queue does not exist for type: " + queueType);
        }
        E element = this.getFromQueue(queueType, q);
		log.debug("[dequeue] element: " + element.getId() + " priority: " + element.getPriority());
        return element;
    }

    /**
     * This method is used to empty queue
     */
    public void emptyQueue() {
        HashMap<T, Queue<E>> queues = this.queuesMap;
        Set<T> keySet = queues.keySet();

        for (Iterator iterator = keySet.iterator(); iterator.hasNext();) {
            T queueKey = (T) iterator.next();
            Queue<E> queue = queues.get(queueKey);
            queue.clear();
        }
    }

    public void emptyQueue(QueueType queueType) {
        this.queuesMap.get(queueType).clear();        
    }

    /**
     * This method is used to add new element to queue
     */
    public void enqueue(E o) {
        Queue<E> q = this.queuesMap.get(o.getQueueType());
		log.debug("[queue] element: " + o.getId() + " priority: " + o.getPriority() + " onQueue: " + o.getQueueType() + " size: "
				+ q.size());
        q.add(o);
    }

    public Integer getExecutorsQueueSize() {
        DirectExecutor a = this.getDirectExecutor();
        return a.getQueueSize();
    }

    /**
     * Creates a new queue. It selects queue based on specified queue type.
     */
    public void newQueue(T type) {
        Queue queue = new PriorityBlockingQueue(11);
        this.queuesMap.put(type, queue);
    }

    /**
     * Remove specified element from queue
     */
    public void remove(E o) {
        Queue<E> q = this.queuesMap.get(o.getQueueType());
        q.remove(o);
    }

    /**
     * It returns queue size. It selects queue based on specified queue type.
     */
    public Integer size(T queueType) {
        Queue<E> q = this.queuesMap.get(queueType);
        if (q == null) {
            throw new UnexpectedRuntimeException("Queue does not exist for type: " + queueType);
        }
        return q.size();
    }

    private DirectExecutor getDirectExecutor() {
        if (this.directExecutor == null) {
            this.directExecutor = DirectExecutor.create();
        }
        return this.directExecutor;
    }

    private E getFromQueue(T queueType, Queue<E> q) {
        if (queueType.isBlocker()) {

            DeQueueRequest deQueueRequest = new DeQueueRequest(q);

            this.getDirectExecutor().execute(deQueueRequest);

            return (E) deQueueRequest.getResult();

        }

        return q.poll();
    }

    public boolean exist(T queueType) {
        return queuesMap.containsKey(queueType);
    }

    private DirectExecutor directExecutor;

    private HashMap<T, Queue<E>> queuesMap = new HashMap();
}
