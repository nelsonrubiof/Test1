/*
 *
 * Copyright 2007, SCOPIX. All rights reserved.
 *
 * This software and its documentation contains proprietary information and can
 * only be used under a license agreement containing restrictions on its use and
 * disclosure. It is protected by copyright, patent and other intellectual and
 * industrial property laws. Copy, reverse engineering, disassembly or
 * decompilation of all or part of it, except to the extent required to obtain
 * interoperability with other independently created software as specified by a
 * license agreement, is prohibited.
 *
 */
package com.scopix.periscope.frameworksfoundation.xstream.serializer;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

/**
 * This class manages singleton instance of {@link XStream} api. Here we
 * configure which classes has aliases. <br>
 * It means that {@link XStream} which look for {@link XStreamAlias} annotation
 * on each class that is processing.
 *
 * @author ricardo.Catalfo
 */
public class XStreamFactory {

    /**
     * Holder to allow singleton instances.
     *
     * @author ricardo.Catalfo
     */
    //CHECKSTYLE:OFF
    private static class LazyHolder {

        private static final XStream xstream;

        static {
            xstream = new XStream(new DomDriver());
            /*try {
            // TODO
            Annotations.configureAliases(xstream, Class.forName("com.scopix.periscope.businessrulemanagement.view.ComplianceConditionView"));
            Annotations.configureAliases(xstream, Class.forName("com.scopix.periscope.businessrulemanagement.view.RectangleView"));
            Annotations.configureAliases(xstream, Class.forName("com.scopix.periscope.controlcentermanagement.views.BusinessRuleCheckView"));
            Annotations.configureAliases(xstream, Class.forName("com.scopix.periscope.controlcentermanagement.views.ComplianceConditionCheckView"));
            Annotations.configureAliases(xstream, Class.forName("com.scopix.periscope.controlcentermanagement.views.GetBusinessRuleCheckResult"));
            Annotations.configureAliases(xstream, Class.forName("com.scopix.periscope.controlcentermanagement.views.ProcessBRCheckView"));
            Annotations.configureAliases(xstream, Class.forName("com.scopix.periscope.controlcentermanagement.views.WasView"));
            Annotations.configureAliases(xstream, Class.forName("com.scopix.periscope.corporatemanagement.views.CameraView"));
            Annotations.configureAliases(xstream, Class.forName("com.scopix.periscope.corporatemanagement.views.EvidenceProviderView"));
            Annotations.configureAliases(xstream, Class.forName("com.scopix.periscope.corporatemanagement.views.PlaceView"));
            } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error configuring Aliases", e);
            }*/

        }
    }
    //CHECKSTYLE:ON

    public static XStream getInstance() {
        return LazyHolder.xstream;
    }

    /**
     * Creates a new XStreamFactory object.
     */
    private XStreamFactory() {
    }
}
