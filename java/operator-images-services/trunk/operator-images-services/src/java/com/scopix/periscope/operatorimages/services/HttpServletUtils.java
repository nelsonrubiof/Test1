/*
 * 
 * Copyright (C) 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can only be used under a license agreement containing
 * restrictions on its use and disclosure. It is protected by copyright, patent and other intellectual and industrial property
 * laws. Copy, reverse engineering, disassembly or decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a license agreement, is prohibited.
 * 
 * 
 * HttpServletUtils.java
 * 
 * Created on 16-05-2013, 05:02:29 PM
 */
package com.scopix.periscope.operatorimages.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.cxf.phase.PhaseInterceptorChain;

/**
 *
 * @author nelson
 * @version 1.0.0
 */
public class HttpServletUtils {

    /**
     *
     * @return HttpServletResponse para la llamada REST actual
     */
    public HttpServletResponse getResponse() {
        return (HttpServletResponse) PhaseInterceptorChain.getCurrentMessage().get("HTTP.RESPONSE");
    }

    public HttpServletRequest getRequest() {
        return (HttpServletRequest) PhaseInterceptorChain.getCurrentMessage().get("HTTP.REQUEST");
    }
}
