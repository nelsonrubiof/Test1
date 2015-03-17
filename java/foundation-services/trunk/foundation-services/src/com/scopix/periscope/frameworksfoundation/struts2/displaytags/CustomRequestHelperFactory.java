/*
 * 
 * Copyright � 2007, SCOPIX. All rights reserved.
 * 
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 * CustomRequestHelperFactory.java
 *
 * Created on 03-04-2008, 09:30:15 AM
 *
 */
package com.scopix.periscope.frameworksfoundation.struts2.displaytags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.PageContext;
import org.displaytag.util.RequestHelper;
import org.displaytag.util.RequestHelperFactory;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class CustomRequestHelperFactory implements RequestHelperFactory {

    public RequestHelper getRequestHelperInstance(PageContext pageContext) {
        return new CustomRequestHelper((HttpServletRequest) pageContext.getRequest(), (HttpServletResponse) pageContext.
                getResponse());
    }
}
