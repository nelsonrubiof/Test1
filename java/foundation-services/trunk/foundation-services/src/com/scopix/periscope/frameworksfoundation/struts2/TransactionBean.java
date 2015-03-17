package com.scopix.periscope.frameworksfoundation.struts2;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionInvocation;

/**
 * Bean to execute an action invocation in a transaction.
 *
 * @author jan.sorensen@aragost.com
 */
public class TransactionBean {

    @Transactional
    public String invokeReadWrite(ActionInvocation invocation) throws Exception {
        String result = invocation.invoke();
        return result;
    }
}
