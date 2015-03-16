/*
 * 
 * Copyright (C) 2013, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 *
 * Created on 07/11-2013, 03:36:14 PM
 *
 */
package com.scopix.periscope.interceptors;

/**
 *
 * @author Sebastian
 */
import javax.servlet.http.HttpServletResponse;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;

/**
 *
 * @author Sebastian
 */
public class BrowserCachingInterceptor implements Interceptor {

    private static final Logger LOGGER = Logger.getLogger(BrowserCachingInterceptor.class);

    /**
     * destroy implementation
     */
    @Override
    public void destroy() {
        LOGGER.info("Destroying BrowserCachingInterceptor");
    }

    /**
     * init implementation
     */
    @Override
    public void init() {
        LOGGER.info("Initializing BrowserCachingInterceptor");
    }

    /**
     * interceptor implementation, prevents caching on browsers
     *
     * @param actionInvocation
     * @return
     * @throws Exception
     */
    @Override
    public String intercept(ActionInvocation actionInvocation)
            throws Exception {

        HttpServletResponse response = ServletActionContext.getResponse();
        LOGGER.debug("noCache set");
        response.setHeader("Cache-control", "no-cache,no-store");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "-1");
        return actionInvocation.invoke();
    }
}