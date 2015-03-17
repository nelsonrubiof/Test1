/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
public class DeleteEvidencePoolExecutor extends ThreadPoolExecutor {

    private static Logger log = Logger.getLogger(DeleteEvidencePoolExecutor.class);
    private boolean isPaused;
    private ReentrantLock pauseLock = new ReentrantLock();
    private Condition unpaused = pauseLock.newCondition();
    private LinkedBlockingQueue<Runnable> queue = null;

    public DeleteEvidencePoolExecutor(int maxPoolSize, LinkedBlockingQueue<Runnable> queue) {
        super(maxPoolSize, maxPoolSize, 10, TimeUnit.SECONDS, queue);
        log.debug("DeleteEvidencePoolExecutor()." + "maxPoolSize=" + maxPoolSize);
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

    public void pause() {
        log.debug("pause");
        pauseLock.lock();
        try {
            isPaused = true;
        } finally {
            pauseLock.unlock();
        }
    }

    public void resume() {
        log.debug("resume");
        pauseLock.lock();
        try {
            isPaused = false;
            unpaused.signalAll();
        } finally {
            pauseLock.unlock();
        }
    }

    public void runTask(DeleteBroadwareEvidenceThread task) {
        log.debug("runTask()");
        this.execute(task);
        log.debug("Task in queue:" + queue.size());
        log.debug("_runTask()");
    }
}
