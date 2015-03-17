/*
 *  
 *  Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 *  This software and its documentation contains proprietary information and can
 *  only be used under a license agreement containing restrictions on its use and
 *  disclosure. It is protected by copyright, patent and other intellectual and
 *  industrial property laws. Copy, reverse engineering, disassembly or
 *  decompilation of all or part of it, except to the extent required to obtain
 *  interoperability with other independently created software as specified by a
 *  license agreement, is prohibited.
 * 
 * 
 *  AnalyticsUCPoolExcecutor.java
 * 
 *  Created on 26-01-2012, 04:38:56 PM
 * 
 */
package com.scopix.periscope.evaluationmanagement.evaluators;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public class AnalyticsUCPoolExcecutor extends ThreadPoolExecutor {

    private static Logger log = Logger.getLogger(AnalyticsUCPoolExcecutor.class);
    private boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unpaused = pauseLock.newCondition();
    private LinkedBlockingQueue<Runnable> queue = null;

    public AnalyticsUCPoolExcecutor(int maxPoolSize, LinkedBlockingQueue<Runnable> queue) {
        super(maxPoolSize, maxPoolSize, 10, TimeUnit.SECONDS, queue);
        this.queue = queue;
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        pauseLock.lock();
        try {
            while (isPaused) {
                unpaused.await();
            }
        } catch (InterruptedException ie) {
            t.interrupt();
        } finally {
            pauseLock.unlock();
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        log.info("Task in queue:" + queue.size());
    }

    public void pause() {
        log.info("pause");
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    public void resume() {
        log.info("resume");
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    public void runTask(Thread task) {
        log.info("runTask()");
        this.execute(task);
        log.info("Task in queue:" + queue.size());
        log.info("_runTask()");
    }
}
