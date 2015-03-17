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
 * CustomHref.java
 *
 * Created on 03-04-2008, 09:44:41 AM
 *
 */
package com.scopix.periscope.frameworksfoundation.struts2.displaytags;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.displaytag.util.DefaultHref;
import org.displaytag.util.TagConstants;

/**
 *
 * @author C�sar Abarza Suazo.
 */
public class CustomHref extends DefaultHref {

    private String anchor;
    private String formId;
    private String indicator;
    private String notifyTopics;

    public CustomHref(String baseUrl) {
        super(baseUrl);

    }

    @Override
    public void setAnchor(String arg0) {
        super.setAnchor(arg0);
        anchor = arg0;
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer(30);
        boolean exportLink = false;

        buffer.append(this.getBaseUrl());

        if (this.getParameterMap().size() > 0) {
            buffer.append('?');
            Set parameterSet = this.getParameterMap().entrySet();

            Iterator iterator = parameterSet.iterator();

            while (iterator.hasNext()) {
                Map.Entry entry = (Map.Entry) iterator.next();

                String key = (String) entry.getKey();
                Object value = entry.getValue();
                if (key.endsWith("-e")) {
                    exportLink = true;
                }
                if (value == null) {
                    buffer.append(key).append('='); // no value
                } else if (value.getClass().isArray()) {
                    Object[] values = (Object[]) value;
                    for (int i = 0; i < values.length; i++) {
                        if (i > 0) {
                            buffer.append(TagConstants.AMPERSAND);
                        }

                        buffer.append(key).append('=').append(values[i]);
                    }
                } else {
                    buffer.append(key).append('=').append(value);
                }

                if (iterator.hasNext()) {
                    buffer.append(TagConstants.AMPERSAND);
                }
            }
        }

        if (this.getAnchor() != null && !exportLink) {
            buffer.append("\" dojoType=\"struts:BindAnchor\" ");
            buffer.append(" showError=\"true\" ");
            buffer.append(" showLoading=\"false\" ");
            buffer.append(" targets=\"");
            buffer.append(this.getAnchor());
            if (this.getFormId() != null) {
                buffer.append("\" formId=\"");
                buffer.append(this.getFormId());
            }
            if (this.getIndicator() != null) {
                buffer.append("\" indicator=\"");
                buffer.append(this.getIndicator());
            }
            if (this.getNotifyTopics() != null) {
                buffer.append("\" notifyTopics=\"");
                buffer.append(this.getNotifyTopics());
            }
        } else if (anchor != null && !exportLink) {
            buffer.append("\" dojoType=\"struts:BindAnchor\" ");
            buffer.append(" showError=\"true\" ");
            buffer.append(" showLoading=\"false\" ");
            buffer.append(" targets=\"");
            buffer.append(anchor);
            if (this.getFormId() != null) {
                buffer.append("\" formId=\"");
                buffer.append(this.getFormId());
            }
            if (this.getIndicator() != null) {
                buffer.append("\" indicator=\"");
                buffer.append(this.getIndicator());
            }
            if (this.getNotifyTopics() != null) {
                buffer.append("\" notifyTopics=\"");
                buffer.append(this.getNotifyTopics());
            }
        }
        return buffer.toString();
    }

    public String getFormId() {
        return formId;
    }

    public void setFormId(String formId) {
        this.formId = formId;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getNotifyTopics() {
        return notifyTopics;
    }

    public void setNotifyTopics(String notifyTopics) {
        this.notifyTopics = notifyTopics;
    }
}
