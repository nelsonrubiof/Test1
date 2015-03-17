/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.services.actions;

import com.scopix.periscope.corporatestructuremanagement.Corporate;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import java.io.File;
import java.io.InputStream;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.SessionAware;
import org.apache.struts2.interceptor.validation.SkipValidation;

/**
 *
 * @author Gustavo Alvarez
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/corporatemanagement/corporate.jsp"),
    @Result(name = "fileUploadOk", value = "/WEB-INF/jsp/corporatemanagement/logoUpload.jsp"),
    @Result(name = "preview", value = "/WEB-INF/jsp/corporatemanagement/logoUpload.jsp"),
    @Result(name = "stream", type = StreamResult.class, value = "inputStream",
    params = {"inputName", "inputStream", "contentType", "image/JPEG"})
})
@Namespace("/corporatemanagement")
@ParentPackage(value = "default")
public class CorporateAction extends BaseAction implements SessionAware {

    private Corporate corporate;
    private Boolean editMode;
    private Map session;
    private File upload; //The actual file
    private String uploadContentType; //The content type of the file
    private String uploadFileName; //The uploaded file name
    private String fileClientPath;
    private InputStream inputStream;
    private String contentType;
    private static final String FILE_UPLOAD_OK = "fileUploadOk";
    private static final String STREAM_RESULT = "stream";

    @Override
    public String execute() throws Exception {
        setCorporate(SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getCorporate(session.
                containsKey("sessionId") ? (Long) session.get("sessionId") : 0));
        session.put("corporate", corporate);
        setEditMode((Boolean) false);
        return SUCCESS;
    }

    @SkipValidation
    public String newCorporate() throws Exception {
        if (!localValidation()) {
            setEditMode(false);
            return SUCCESS;
        }

        SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).addCorporate(corporate, session.
                containsKey("sessionId") ? (Long) session.get("sessionId") : 0);

        this.execute();
        return SUCCESS;
    }

    @SkipValidation
    public String enableEditMode() {
        corporate = (Corporate) session.get("corporate");
        setEditMode((Boolean) true);
        return SUCCESS;
    }

    public String showUploadLogo() {
        return FILE_UPLOAD_OK;
    }

    @SkipValidation
    public String uploadFile() throws ScopixException {
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        if (upload != null && uploadFileName != null) {
            try {
                long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
                String extension = uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
                manager.saveCorporateLogo(upload, extension, sessionId);
                this.addActionMessage(this.getText("message.fileUpload.OK"));
            } catch (Exception e) {
                this.addActionError(e.getMessage());
            }
        } else {
            this.addActionError(this.getText("message.fileUpload.uploadRequired"));
        }
        return FILE_UPLOAD_OK;
    }

    @SkipValidation
    public String previewFile() {
        ServletActionContext.getRequest().setAttribute("PREVIEW", "OK");
        return FILE_UPLOAD_OK;
    }

    @SkipValidation
    public String getLogoPreview() throws Exception {
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        Corporate c = manager.getCorporate(sessionId);
        try {
            if (c.getLogo() != null && c.getLogo().length() > 0) {
                Map map = manager.getLogoContent();
                setInputStream((InputStream) map.get("is"));
                setContentType("image/jpg");
            }
        } catch (Exception e) {
            this.addActionError("Error: " + e.getMessage());
        }
        return STREAM_RESULT;
    }

    @SkipValidation
    public String updateCorporate() throws Exception {

        if (!localValidation()) {
            setEditMode(true);
            return SUCCESS;
        }

        Corporate corporateTemp = (Corporate) session.get("corporate");
        corporate.setId(corporateTemp.getId());
        SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).updateCorporate(corporate, session.
                containsKey("sessionId") ? (Long) session.get("sessionId") : 0);
        setEditMode(false);
        this.execute();
        return SUCCESS;
    }

    private Boolean localValidation() {
        if (corporate == null) {
            corporate = new Corporate();
            return false;
        }

        if (corporate.getName().trim().equalsIgnoreCase("")) {
            this.addActionError(this.getText("error.corporatemanagement.nameempty"));
        }
        if (corporate.getDescription().trim().equalsIgnoreCase("")) {
            this.addActionError(this.getText("error.corporatemanagement.descriptionempty"));
        }
        if (!this.getActionErrors().isEmpty()) {
            return false;
        }

        return true;
    }

    public Corporate getCorporate() {
        return corporate;
    }

    public void setCorporate(Corporate corporate) {
        this.corporate = corporate;
    }

    public Boolean getEditMode() {
        return editMode;
    }

    public void setEditMode(Boolean editMode) {
        this.editMode = editMode;
    }

    public Map getSession() {
        return session;
    }

    public void setSession(Map session) {
        this.session = session;
    }

    /**
     * @return the upload
     */
    public File getUpload() {
        return upload;
    }

    /**
     * @param upload the upload to set
     */
    public void setUpload(File upload) {
        this.upload = upload;
    }

    /**
     * @return the uploadContentType
     */
    public String getUploadContentType() {
        return uploadContentType;
    }

    /**
     * @param uploadContentType the uploadContentType to set
     */
    public void setUploadContentType(String uploadContentType) {
        this.uploadContentType = uploadContentType;
    }

    /**
     * @return the uploadFileName
     */
    public String getUploadFileName() {
        return uploadFileName;
    }

    /**
     * @param uploadFileName the uploadFileName to set
     */
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    /**
     * @return the fileClientPath
     */
    public String getFileClientPath() {
        return fileClientPath;
    }

    /**
     * @param fileClientPath the fileClientPath to set
     */
    public void setFileClientPath(String fileClientPath) {
        this.fileClientPath = fileClientPath;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param inputStream the inputStream to set
     */
    public void setInputStream(InputStream inputStream) {
        this.inputStream = inputStream;
    }

    /**
     * @return the contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }
}
