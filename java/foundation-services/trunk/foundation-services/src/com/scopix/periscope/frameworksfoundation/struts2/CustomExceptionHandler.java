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
 * CustomExceptionHandler.java
 *
 * Created on 24-03-2008, 04:55:01 PM
 *
 */
package com.scopix.periscope.frameworksfoundation.struts2;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.config.entities.ExceptionMappingConfig;
import com.opensymphony.xwork2.interceptor.ExceptionHolder;
import com.opensymphony.xwork2.interceptor.ExceptionMappingInterceptor;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import java.util.Iterator;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author César Abarza Suazo
 * @version 1.0.0
 */
public class CustomExceptionHandler extends ExceptionMappingInterceptor {

    private Logger logger = Logger.getLogger(CustomExceptionHandler.class);

    @Override
    protected void publishException(ActionInvocation actionInvocation, ExceptionHolder exceptionHolder) {
        actionInvocation.getStack().push(exceptionHolder.getException());
        String message = exceptionHolder.getException().getMessage();
        String messageTranslate = ((BaseAction) actionInvocation.getAction()).getText(message);
        if (!messageTranslate.equals("")) {
            message = messageTranslate;
        }
        logger.warn(message, exceptionHolder.getException());
        ((BaseAction) actionInvocation.getAction()).addActionError(message);
    }

    protected void publishException(ActionInvocation actionInvocation, ExceptionHolder exceptionHolder, String[] attributes) {
        actionInvocation.getStack().push(exceptionHolder.getException());
        String message = exceptionHolder.getException().getMessage();
        String messageTranslate;
        if (attributes != null) {
            for (int i = 0; i < attributes.length; i++) {
                attributes[i] = ((BaseAction) actionInvocation.getAction()).getText(attributes[i]);
            }
            messageTranslate = ((BaseAction) actionInvocation.getAction()).getText(message, attributes);
        } else {
            messageTranslate = ((BaseAction) actionInvocation.getAction()).getText(message);
        }
        if (!messageTranslate.equals("")) {
            message = messageTranslate;
        }
        logger.warn(message, exceptionHolder.getException());
        ((BaseAction) actionInvocation.getAction()).addActionError(message);
    }

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        String result = null;
        try { 
            result = invocation.invoke();
            ((BaseAction) invocation.getAction()).setLastOKMethod(invocation.getAction().getClass().
                    getName(), invocation.getProxy().getMethod());
        } catch (Exception e) {
            logger.debug("Exception type: " + e.getCause(), e);
            if (isLogEnabled()) {
                handleLogging(e);
            }
            
//            if (e instanceof PeriscopeSecurityException) {
//                if (PeriscopeSecurityException.Type.USER_NOT_FOUND.equals(((PeriscopeSecurityException) e).getType())) {
//                    e = new PeriscopeSecurityException("security.exception.userNotFound",
//                            PeriscopeSecurityException.Type.USER_NOT_FOUND);
//                } else if (PeriscopeSecurityException.Type.ACCESS_DENIED.equals(((PeriscopeSecurityException) e).getType())) {
//                    e = new Exception("security.exception.accessDenied");
//                } else if (PeriscopeSecurityException.Type.NO_HAVE_PERMISSIONS.equals(
//                        ((PeriscopeSecurityException) e).getType())) {
//                    e = new Exception("security.exception.noHavePrivileges");
//                } else if (PeriscopeSecurityException.Type.SESSION_EXPIRED.equals(((PeriscopeSecurityException) e).getType())) {
//                    e = new Exception("security.exception.sessionExpired");
//                } else if (PeriscopeSecurityException.Type.SESSION_NOT_FOUND.equals(((PeriscopeSecurityException) e).getType())) {
//                    e = new Exception("security.exception.sessionNotFound");
//                }
//            }
            if (e instanceof ScopixException
                    && ((BaseAction) invocation.getAction()).getLastOKMethod(invocation.getAction().
                    getClass().getName()) != null) { // || e instanceof PeriscopeSecurityException) 
                String method = ((BaseAction) invocation.getAction()).getLastOKMethod(invocation.getAction().getClass().
                        getName());
                invocation.getProxy().setMethod(method);
//                if (e instanceof ScopixException) {
//                    publishException(invocation, new ExceptionHolder(e), ((ScopixException) e).getAttributes());
//                } else {
                publishException(invocation, new ExceptionHolder(e));
//                }
                result = invocation.invoke();
            } else {
                List exceptionMappings = invocation.getProxy().getConfig().getExceptionMappings();
                String mappedResult = this.findResultFromExceptions(exceptionMappings, e);
                if (mappedResult != null) {
                    result = mappedResult;
//                    if (e instanceof ScopixException) {
//                        publishException(invocation, new ExceptionHolder(e), ((ScopixException) e).getAttributes());
//                    } else {
                    publishException(invocation, new ExceptionHolder(e));
//                    }
                } else {
                    throw e;
                }
            }
        }
        return result;
    }

    private String findResultFromExceptions(List exceptionMappings, Throwable t) {
        String result = null;
        // Check for specific exception mappings.
        if (exceptionMappings != null) {
            int deepest = Integer.MAX_VALUE;
            for (Iterator iter = exceptionMappings.iterator(); iter.hasNext();) {
                ExceptionMappingConfig exceptionMappingConfig = (ExceptionMappingConfig) iter.next();
                int depth = getDepth(exceptionMappingConfig.getExceptionClassName(), t);
                if (depth >= 0 && depth < deepest) {
                    deepest = depth;
                    result = exceptionMappingConfig.getResult();
                }
            }
        }
        return result;
    }
}
