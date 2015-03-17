/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.executors.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import org.apache.log4j.Logger;

/**
 *
 * @author sebastian
 */
public class ScopixPoolExecutor extends ThreadPoolExecutor {

    private static Logger log = Logger.getLogger(ScopixPoolExecutor.class);
    private boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unpaused = pauseLock.newCondition();
    private LinkedBlockingQueue<Runnable> queue = null;
    private String poolName;


    /**
     * constructor
     *
     * @param maxPoolSize
     * @param queue
     * @param poolName
     */
    public ScopixPoolExecutor(int maxPoolSize, LinkedBlockingQueue<Runnable> queue, String poolName) {
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
        log.debug("Pool: " + poolName + ", Task in queue:" + queue.size());
    }

    /**
     * sends pause signal to all threads
     */
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

    /**
     * sends resume signal to all threads
     */
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

    /**
     * Enqueues desired thread to be executed
     *
     * @param task
     */
    public void runTask(Thread task) {
        log.info("start");
        this.execute(task);
        log.debug("Pool: " + poolName + ", Task in queue:" + queue.size());
        log.info("end");
    }
}
