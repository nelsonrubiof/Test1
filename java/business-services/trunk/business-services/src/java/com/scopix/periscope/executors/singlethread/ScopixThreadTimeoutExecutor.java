/*
 *  
 *  Copyright (C) 2013, SCOPIX. All rights reserved.
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
 *  ScopixThreadTimeoutExecutor.java
 * 
 *  Created on Sep 11, 2014, 10:37:03 AM
 * 
 */
package com.scopix.periscope.executors.singlethread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.apache.log4j.Logger;

/**
 *
 * @author Sebastian
 */
public class ScopixThreadTimeoutExecutor implements Runnable {

    private int timePeriod;
    private TimeUnit timeUnit;
    private Callable callable;
    private Runnable runnable;
    private static Logger log = Logger.getLogger(ScopixThreadTimeoutExecutor.class);

    private ScopixThreadTimeoutExecutor() {
    }

    public ScopixThreadTimeoutExecutor(Integer timePeriod, TimeUnit timeUnit, Runnable runnable) {
        this.timePeriod = timePeriod;
        this.timeUnit = timeUnit;
        this.runnable = runnable;
    }

    public ScopixThreadTimeoutExecutor(Integer timePeriod, TimeUnit timeUnit, Callable callable) {
        this.timePeriod = timePeriod;
        this.timeUnit = timeUnit;
        this.callable = callable;
    }

    @Override
    public void run() {
        log.info("Executing timeout task ");
        ExecutorService service = Executors.newSingleThreadExecutor();
        Future future;
        if (callable != null) {
            future = service.submit(callable);
        } else {
            future = service.submit(runnable);
        }
        try {
            future.get(timePeriod, timeUnit);
        } catch (InterruptedException ex) {
            log.error("An InterruptedException ocurred", ex);
        } catch (ExecutionException ex) {
            log.error("An ExecutionException ocurred", ex);
        } catch (TimeoutException ex) {
            log.error("An TimeoutException ocurred", ex);
        }
        service.shutdownNow();
        log.info("End");
    }
}
