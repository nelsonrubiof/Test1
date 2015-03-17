package com.scopix.periscope.frameworksfoundation.struts2;

import org.springframework.transaction.annotation.Transactional;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;

@Transactional
public class TransactionInterceptor implements Interceptor {

    private static final long serialVersionUID = -5413262025771477619L;

    public void destroy() {
    }

    public void init() {
    }

    public String intercept(ActionInvocation invocation) throws Exception {
        return getTransactionBean().invokeReadWrite(invocation);

    }

    private TransactionBean getTransactionBean() {
        return (TransactionBean) SpringSupport.getInstance().findBean(
                "transactionBean");
    }
}
