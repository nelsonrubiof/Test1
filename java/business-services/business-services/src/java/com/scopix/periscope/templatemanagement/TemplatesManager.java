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
 * TemplatesManager.java
 *
 * Created on 27-03-2008, 01:37:54 PM
 *
 */
package com.scopix.periscope.templatemanagement;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringBean;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.securitymanagement.SecurityManager;
import com.scopix.periscope.securitymanagement.TemplatesManagerPermissions;
import com.scopix.periscope.templatemanagement.commands.AddMetricTemplateCommand;
import com.scopix.periscope.templatemanagement.commands.AddProductCommand;
import com.scopix.periscope.templatemanagement.commands.AddSituationTemplateCommand;
import com.scopix.periscope.templatemanagement.commands.GetMetricTemplateCommand;
import com.scopix.periscope.templatemanagement.commands.GetMetricTemplateListCommand;
import com.scopix.periscope.templatemanagement.commands.GetProductCommand;
import com.scopix.periscope.templatemanagement.commands.GetProductListCommand;
import com.scopix.periscope.templatemanagement.commands.GetSituationTemplateCommand;
import com.scopix.periscope.templatemanagement.commands.GetSituationTemplateListCommand;
import com.scopix.periscope.templatemanagement.commands.RemoveMetricTemplateCommand;
import com.scopix.periscope.templatemanagement.commands.RemoveProductCommand;
import com.scopix.periscope.templatemanagement.commands.RemoveSituationTemplateCommand;
import com.scopix.periscope.templatemanagement.commands.TransformSituationTemplateToDTOCommand;
import com.scopix.periscope.templatemanagement.commands.UpdateActiveStateSituationTemplateCommand;
import com.scopix.periscope.templatemanagement.commands.UpdateMetricTemplateCommand;
import com.scopix.periscope.templatemanagement.commands.UpdateProductCommand;
import com.scopix.periscope.templatemanagement.commands.UpdateSituationTemplateCommand;
import com.scopix.periscope.templatemanagement.dto.SituationTemplateDTO;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author C�sar Abarza Suazo
 */
@SpringBean(rootClass = TemplatesManager.class)
@Transactional(rollbackFor = {ScopixException.class})
public class TemplatesManager {

    private TransformSituationTemplateToDTOCommand transformSituationTemplateToDTOCommand;
    private GetSituationTemplateListCommand situationTemplateListCommand;
    private SecurityManager securityManager;

    /**
     * 
     * @param situationTemplate
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public void addSituationTemplate(SituationTemplate situationTemplate, long sessionId) throws ScopixException {

        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.ADD_SITUATION_TEMPLATE_PERMISSION);
        if (situationTemplate == null) {
            throw new ScopixException("periscopeexception.templateManagement.situationTemplate.objectNotNull");
        }

        AddSituationTemplateCommand command = new AddSituationTemplateCommand();
        command.execute(situationTemplate);
    }

    /**
     * 
     * @param metricTemplate
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public void addMetricTemplate(MetricTemplate metricTemplate, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.ADD_METRIC_TEMPLATE_PERMISSION);
        if (metricTemplate == null) {
            throw new ScopixException("periscopeexception.templateManagement.metricTemplate.objectNotNull");
        }

        AddMetricTemplateCommand command = new AddMetricTemplateCommand();
        command.execute(metricTemplate);
    }

    /**
     * Add Product entity
     * 
     * @param product
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeSecurityException
     */
    public void addProduct(Product product, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.ADD_PRODUCT_PERMISSION);
        if (product == null) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.product");
        }

        AddProductCommand command = new AddProductCommand();
        command.execute(product);
    }

    /**
     * 
     * @param idSituationTemplate
     * @return SituationTemplate
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public SituationTemplate getSituationTemplate(int idSituationTemplate, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.GET_SITUATION_TEMPLATE_PERMISSION);
        GetSituationTemplateCommand command = new GetSituationTemplateCommand();
        SituationTemplate situationTemplateResult = command.execute(idSituationTemplate);

        return situationTemplateResult;
    }

    /**
     * Get Metric Template by ID.
     * 
     * @param idMetricTemplate
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeSecurityException
     */
    public MetricTemplate getMetricTemplate(int idMetricTemplate, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.GET_METRIC_TEMPLATE_PERMISSION);
        GetMetricTemplateCommand command = new GetMetricTemplateCommand();
        MetricTemplate metricTemplate = command.execute(idMetricTemplate);

        return metricTemplate;
    }

    /**
     * 
     * @param situationTemplate 
     * @return List<SituationTemplate>
     */
    public List<SituationTemplate> getSituationTemplateList(SituationTemplate situationTemplate, long sessionId) throws
            ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.LIST_SITUATION_TEMPLATE_PERMISSION);

        List<SituationTemplate> situationTemplateList = getSituationTemplateListCommand().execute(situationTemplate);

        return situationTemplateList;
    }

    public List<SituationTemplateDTO> getSituationTemplateDTOs(SituationTemplate situationTemplate, long sessionId) throws
            ScopixException {

        List<SituationTemplate> situationTemplateList = getSituationTemplateList(situationTemplate, sessionId);

        List<SituationTemplateDTO> dTOs = getTransformSituationTemplateToDTOCommand().execute(situationTemplateList);
        return dTOs;
    }

    /**
     * Get a Metric Template List
     * 
     * @param metricTemplate Object that contains filters.
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeSecurityException
     */
    public List<MetricTemplate> getMetricTemplateList(MetricTemplate metricTemplate, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.LIST_METRIC_TEMPLATE_PERMISSION);
        GetMetricTemplateListCommand command = new GetMetricTemplateListCommand();
        List<MetricTemplate> metricTemplates = command.execute(metricTemplate);

        return metricTemplates;
    }

    /**
     * Get a product by ID
     * 
     * @param idProduct
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeSecurityException
     */
    public Product getProduct(int idProduct, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.GET_PRODUCT_PERMISSION);
        GetProductCommand command = new GetProductCommand();
        Product product = command.execute(idProduct);

        return product;
    }

    /**
     * Get a list of products
     * 
     * @param product
     * @param sessionId
     * @return
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeSecurityException
     */
    public List<Product> getProductList(Product product, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.LIST_PRODUCT_PERMISSION);
        GetProductListCommand command = new GetProductListCommand();
        List<Product> products = command.execute(product);

        return products;
    }

    /**
     * 
     * @param idSituationTemplate
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public void removeSituationTemplate(int idSituationTemplate, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.REMOVE_SITUATION_TEMPLATE_PERMISSION);
        RemoveSituationTemplateCommand command = new RemoveSituationTemplateCommand();
        command.execute(idSituationTemplate);
    }

    /**
     * 
     * @param idMetricTemplate
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public void removeMetricTemplate(int idMetricTemplate, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.REMOVE_METRIC_TEMPLATE_PERMISSION);
        RemoveMetricTemplateCommand command = new RemoveMetricTemplateCommand();
        command.execute(idMetricTemplate);
    }

    /**
     * Remove a product
     * 
     * @param idProduct
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeSecurityException
     */
    public void removeProduct(int idProduct, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.REMOVE_PRODUCT_PERMISSION);
        RemoveProductCommand command = new RemoveProductCommand();
        command.execute(idProduct);
    }

    /**
     * 
     * @param situationTemplate
     * @param sessionId 
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeSecurityException 
     */
    public void updateSituationTemplate(SituationTemplate situationTemplate, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.UPDATE_SITUATION_TEMPLATE_PERMISSION);
        if (situationTemplate == null) {
            throw new ScopixException("periscopeexception.templateManagement.situationTemplate.objectNotNull");
        }

        UpdateSituationTemplateCommand command = new UpdateSituationTemplateCommand();
        command.execute(situationTemplate);
    }

    public void updateActiveState(SituationTemplate situationTemplate, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.UPDATE_SITUATION_TEMPLATE_PERMISSION);
        if (situationTemplate == null) {
            throw new ScopixException("periscopeexception.templateManagement.situationTemplate.objectNotNull");
        }

        UpdateActiveStateSituationTemplateCommand command = new UpdateActiveStateSituationTemplateCommand();
        command.execute(situationTemplate);
    }

    /**
     * 
     * @param metricTemplate
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     */
    public void updateMetricTemplate(MetricTemplate metricTemplate, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.UPDATE_METRIC_TEMPLATE_PERMISSION);
        if (metricTemplate == null) {
            throw new ScopixException("periscopeexception.templateManagement.metricTemplate.objectNotNull");
        }

        UpdateMetricTemplateCommand command = new UpdateMetricTemplateCommand();
        command.execute(metricTemplate);
    }

    /**
     * Update a product
     * 
     * @param product
     * @param sessionId
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeException
     * @throws com.scopix.periscope.periscopefoundation.exception.PeriscopeSecurityException
     */
    public void updateProduct(Product product, long sessionId) throws ScopixException {
        getSecurityManager().checkSecurity(sessionId, TemplatesManagerPermissions.UPDATE_PRODUCT_PERMISSION);
        if (product == null) {
            //"periscopeexception.entity.validate.notNullEntity", new String[]{
            throw new ScopixException("label.product");
        }

        UpdateProductCommand command = new UpdateProductCommand();
        command.execute(product);
    }

    public TransformSituationTemplateToDTOCommand getTransformSituationTemplateToDTOCommand() {
        if (transformSituationTemplateToDTOCommand == null) {
            transformSituationTemplateToDTOCommand = new TransformSituationTemplateToDTOCommand();
        }
        return transformSituationTemplateToDTOCommand;
    }

    public void setTransformSituationTemplateToDTOCommand(
            TransformSituationTemplateToDTOCommand transformSituationTemplateToDTOCommand) {
        this.transformSituationTemplateToDTOCommand = transformSituationTemplateToDTOCommand;
    }

    public GetSituationTemplateListCommand getSituationTemplateListCommand() {
        if (situationTemplateListCommand == null) {
            situationTemplateListCommand = new GetSituationTemplateListCommand();
        }
        return situationTemplateListCommand;
    }

    public void setSituationTemplateListCommand(GetSituationTemplateListCommand situationTemplateListCommand) {
        this.situationTemplateListCommand = situationTemplateListCommand;
    }

    public SecurityManager getSecurityManager() {
        if (securityManager == null) {
            securityManager = SpringSupport.getInstance().findBeanByClassName(SecurityManager.class);
        }
        return securityManager;
    }

    public void setSecurityManager(SecurityManager securityManager) {
        this.securityManager = securityManager;
    }
}
