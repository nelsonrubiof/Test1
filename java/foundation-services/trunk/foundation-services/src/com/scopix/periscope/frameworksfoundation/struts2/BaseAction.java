package com.scopix.periscope.frameworksfoundation.struts2;

import java.util.List;
 
import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionSupport;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseAction extends ActionSupport {

    private boolean editable; 

//    public GenericDAO dao() {
//        return SpringSupport.getInstance().findGenericDAO();
//    }
//
//    /**
//     * Convenience method to save a new model
//     *
//     * @param model
//     */
//    public void daoSave(BusinessObject model) {
//        dao().save(model);
//    }

//    /**
//     * Convenience method to get a specific model
//     *
//     * @param <T>
//     * @param id
//     * @param clazz
//     * @return
//     */
//    public <T> T get(Integer id, Class<T> clazz) {
//        if (id == null) {
//            throw new NullPointerException("Id for loading a " + clazz.getName() + " is null");
//        }
//        T model = dao().get(id, clazz);
//        if (model == null) {
//            throw new RuntimeException("No model of class " + clazz.getName() + " with id " + id);
//        }
//        return model;
//    }
//
//    public <T> List<T> getAll(Class<T> clazz) {
//        Collection<T> all = dao().getAll(clazz);
//        Set<T> set = new HashSet<T>(all);
//        return new ArrayList<T>(set);
//    }

    public void storeTableData(String name, List<?> data) {
        HttpServletRequest request = ServletActionContext.getRequest();
        request.setAttribute(name, data);
    }

    @Override
    public String execute() throws Exception {
        throw new RuntimeException("execute should have been overridden");
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }

    public String getLastOKMethod(String actionName) {
        String lastMethod = null;
        if (ServletActionContext.getRequest().getSession() != null && ServletActionContext.getRequest().getSession().getAttribute(
                "lastOKMethod") != null) {
            lastMethod = ((Map<String, String>) ServletActionContext.getRequest().getSession().getAttribute("lastOKMethod")).get(
                    actionName);
        }
        return lastMethod;
    }

    public void setLastOKMethod(String actionName, String method) {
        if (ServletActionContext.getRequest().getSession() == null || ServletActionContext.getRequest().getSession().getAttribute(
                "lastOKMethod") == null) {
            ServletActionContext.getRequest().getSession(true).setAttribute("lastOKMethod", new HashMap<String, String>());
        }
        ((Map<String, String>) ServletActionContext.getRequest().getSession().
                getAttribute("lastOKMethod")).put(actionName, method);
    }
}
