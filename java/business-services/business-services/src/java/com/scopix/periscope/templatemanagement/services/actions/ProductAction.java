/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.templatemanagement.services.actions;

import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;

import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.Product;
import com.scopix.periscope.templatemanagement.TemplatesManager;
import java.util.List;
import java.util.Map;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Gustavo Alvarez
 * @version 1.0.0
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/templatemanager/product/productManagement.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/templatemanager/product/productList.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/templatemanager/product/productEdit.jsp")
})
@ParentPackage(value = "default")
@Namespace("/templatemanagement")
public class ProductAction extends BaseAction implements SessionAware {

    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private Map session;
    private Product product;
    private List<Product> products;
    private boolean disabled;

    /**
     *
     * @return String con SUCCESS
     * @throws Exception en caso de no poder levantar los Filtros
     */
    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    /**
     *
     * @return String 
     * @throws PeriscopeException en caso de error conocido
     * @throws PeriscopeSecurityException en caso de problemas de seguridad
     */
    public String list() throws ScopixException {
        if (product == null || product.getId() != null) {
            product = (Product) session.get("productFilter");
        }

        if (product != null) {
            products = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).getProductList(product, session.
                    containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
            session.put("productFilter", product);
        }
        return LIST;
    }

    /**
     *
     * @return String EDIT para indicar q se esta editando un Producto
     */
    public String newProduct() {
        this.setEditable(false);
        setDisabled(true);
        product = new Product();
        return EDIT;
    }

    /**
     *
     * @return String EDIT para indicar que se esta editando un Producto
     * @throws PeriscopeException en caso de error conocido
     * @throws PeriscopeSecurityException en caso de problemas de seguridad
     */
    public String editProduct() throws ScopixException {
        if (product != null && product.getId() != null && product.getId() > 0) {
            this.setEditable(true);
            long sessionId = session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0;
            product = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).getProduct(product.getId(),
                    sessionId);
        } else {
            this.addActionError(this.getText("error.general.edit", new String[]{this.getText("label.product")}));
        }
        return EDIT;
    }

    /**
     *
     * @return String LIST para indicar que se debe mostrar una lista de Productos
     * @throws PeriscopeException en caso de error conocido
     * @throws PeriscopeSecurityException en caso de problemas de seguridad
     */
    public String deleteProduct() throws ScopixException {
        if (product != null && product.getId() != null && product.getId() > 0) {
            long sessionId = session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0;
            SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).removeProduct(product.getId(), sessionId);
        } else {
            this.addActionError(this.getText("error.general.delete", new String[]{this.getText("label.product")}));
        }
        this.list();
        return LIST;
    }

    /**
     *
     * @return String SUCCESS para indicar que se ha almacenado correctamente un Producto
     * @throws PeriscopeException en caso de error conocido
     * @throws PeriscopeSecurityException en caso de problemas de seguridad
     */
    public String saveProduct() throws ScopixException {
        if (product.getName() == null || product.getName().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.name")}));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }
        long sessionId = session.containsKey("sessionId") ? (Long) session.get("sessionId") : 0;
        if (this.isEditable()) {
            SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).updateProduct(product, sessionId);
        } else {
            SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class).addProduct(product, sessionId);
        }
        this.loadFilters();
        return SUCCESS;
    }

    /**
     *
     * @return SUCCESS y carga los filtros
     */
    public String cancel() {
        this.loadFilters();
        return SUCCESS;
    }

    /**
     *
     * @return LIST inidicando que la lista esta lista para ser utilizada
     * @throws PeriscopeException en caso de error conocido
     * @throws PeriscopeSecurityException en caso de problemas de seguridad
     */
    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    /**
     *
     * @return Map con la session 
     */
    public Map getSession() {
        return session;
    }

    /**
     *
     * @param session
     */
    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    /**
     *
     * @return
     */
    public Product getProduct() {
        return product;
    }

    /**
     *
     * @param product
     */
    public void setProduct(Product product) {
        this.product = product;
    }

    /**
     *
     * @return
     */
    public List<Product> getProducts() {
        return products;
    }

    /**
     *
     * @param products
     */
    public void setProducts(List<Product> products) {
        this.products = products;
    }

    private void loadFilters() {
        product = (Product) getSession().get("productFilter");
    }

    /**
     *
     * @return
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     *
     * @param disabled
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
