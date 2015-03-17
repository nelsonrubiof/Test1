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
 * CustomSmartListHelper.java
 *
 * Created on 02-04-2008, 05:45:16 PM
 *
 */
package com.scopix.periscope.frameworksfoundation.struts2.displaytags;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.displaytag.util.DefaultRequestHelper;
import org.displaytag.util.Href;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class CustomRequestHelper extends DefaultRequestHelper {

    /**
     * original HttpServletRequest.
     */
    private HttpServletRequest request;
    /**
     * original HttpServletResponse.
     */
    private HttpServletResponse response;

    public CustomRequestHelper(HttpServletRequest servletRequest, HttpServletResponse servletResponse) {
        super(servletRequest, servletResponse);
        this.request = servletRequest;
        this.response = servletResponse;
    }

    @Override
    public Href getHref() {
        String requestURI = this.request.getRequestURI();
        // call encodeURL to preserve session id when cookies are disabled
        Href href = new CustomHref(this.response.encodeURL(requestURI));
        href.setParameterMap(getParameterMap());
        String target = (String) request.getAttribute("target");
        if (target != null) {
            href.setAnchor(target);
        }
        String formId = (String) request.getAttribute("formId");
        if (formId != null) {
            ((CustomHref) href).setFormId(formId);
        }
        String indicator = (String) request.getAttribute("indicator");
        if (indicator != null) {
            ((CustomHref) href).setIndicator(indicator);
        }
        String notifyTopics = (String) request.getAttribute("notifyTopics");
        if (notifyTopics != null) {
            ((CustomHref) href).setNotifyTopics(notifyTopics);
        }
        return href;
    }
}
