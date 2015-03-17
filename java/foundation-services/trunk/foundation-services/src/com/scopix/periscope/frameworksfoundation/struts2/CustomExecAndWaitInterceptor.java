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
 * CustomExecAndWaitInterceptor.java
 *
 * Created on 17-04-2008, 10:45:26 AM
 *
 */
package com.scopix.periscope.frameworksfoundation.struts2;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.ActionProxy;
import com.opensymphony.xwork2.config.entities.ResultConfig;
import java.util.Collections;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.interceptor.BackgroundProcess;
import org.apache.struts2.interceptor.ExecuteAndWaitInterceptor;

/**
 *
 * @author César Abarza Suazo.
 * @version 1.0.0
 */
public class CustomExecAndWaitInterceptor extends ExecuteAndWaitInterceptor {

    private static final Log LOG = LogFactory.getLog(ExecuteAndWaitInterceptor.class);

    private int threadPriority = Thread.NORM_PRIORITY;

    @Override
    public String intercept(ActionInvocation actionInvocation) throws Exception {
        //Get showLoading parameter        
        if (actionInvocation.getInvocationContext().getParameters().get("showLoading") != null) {
            ActionProxy proxy = actionInvocation.getProxy();
            String name = proxy.getActionName();
            String method = proxy.getMethod();
            ActionContext context = actionInvocation.getInvocationContext();
            Map session = context.getSession();
            synchronized (session) {                
                BackgroundProcess bp = (BackgroundProcess) session.get(KEY + name+method);
                if (bp == null) {
                    bp = getNewBackgroundProcess(name, actionInvocation, threadPriority);
                    session.put(KEY + name+method, bp);
                    performInitialDelay(bp); // first time let some time pass before showing wait page
                }
                if (!bp.isDone()) {
                    actionInvocation.getStack().push(bp.getAction());
                    Map results = proxy.getConfig().getResults();
                    if (!results.containsKey(WAIT)) {
                        LOG.warn("ExecuteAndWait interceptor has detected that no result named 'wait' is available. " +
                                "Defaulting to a plain built-in wait page. It is highly recommend you " +
                                "provide an action-specific or global result named '" + WAIT +
                                "'! This requires FreeMarker support and won't work if you don't have it installed");
                        // no wait result? hmm -- let's try to do dynamically put it in for you!
                        ResultConfig rc = new ResultConfig(WAIT, "com.opensymphony.webwork.views.freemarker.FreemarkerResult",
                                Collections.singletonMap("location", "com/opensymphony/webwork/interceptor/wait.ftl"));
                        results.put(WAIT, rc);
                    }
                    return WAIT;
                } else {
                    session.remove(KEY + name+method);
                    actionInvocation.getStack().push(bp.getAction());
                    // if an exception occured during action execution, throw it here
                    if (bp.getException() != null) {
                        throw bp.getException();
                    }
                    return bp.getResult();
                }
            }
        } else {
            return actionInvocation.invoke();
        }
    }

    @Override
    public void setThreadPriority(int threadPriority) {
        this.threadPriority = threadPriority;
    }
}
