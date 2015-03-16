/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.scopix.periscope.extractionmanagement.commands;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.log4j.Logger;

/**
 *
 * @author marko.perich
 */
public class ExtractEvidencePoolExecutor extends ThreadPoolExecutor {

    private static Logger log = Logger.getLogger(ExtractEvidencePoolExecutor.class);
    private boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unpaused = pauseLock.newCondition();
    private LinkedBlockingQueue<Runnable> queue = null;
    private String poolName;

    public ExtractEvidencePoolExecutor(int maxPoolSize, LinkedBlockingQueue<Runnable> queue) {
        super(maxPoolSize, maxPoolSize, 10, TimeUnit.SECONDS, queue);
        this.queue = queue;
    }

    public ExtractEvidencePoolExecutor(int maxPoolSize, LinkedBlockingQueue<Runnable> queue, String poolName) {
        super(maxPoolSize, maxPoolSize, 10, TimeUnit.SECONDS, queue);
        this.queue = queue;
        this.poolName = poolName;
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        log.info("start");
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
        log.info("end");
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        super.afterExecute(r, t);
        log.debug("Pool: [" + poolName + "], Task in queue: [" + queue.size() + "]");
    }

    public void pause() {
        log.info("start");
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
        log.info("end");
    }

    public void resume() {
        log.info("start");
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
        log.info("end");
    }

    public void runTask(ExtractEvidenceThread task) {
        log.info("start");
        this.execute(task);
        log.info("end, Pool: [" + poolName + "], Task in queue: [" + queue.size() + "]");
    }
}