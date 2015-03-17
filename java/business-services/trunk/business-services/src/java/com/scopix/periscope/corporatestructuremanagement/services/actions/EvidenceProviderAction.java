/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package com.scopix.periscope.corporatestructuremanagement.services.actions;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FilenameUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.dispatcher.StreamResult;
import org.apache.struts2.interceptor.SessionAware;

import com.scopix.periscope.corporatestructuremanagement.Area;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProvider;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProviderTemplate;
import com.scopix.periscope.corporatestructuremanagement.EvidenceProviderType;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;

/**
 *
 * @author Gustavo Alvarez
 */
@Results({
        @Result(name = "success", value = "/WEB-INF/jsp/corporatemanagement/evidenceprovider/evidenceProviderManagement.jsp"),
        @Result(name = "list", value = "/WEB-INF/jsp/corporatemanagement/evidenceprovider/evidenceProviderList.jsp"),
        @Result(name = "edit", value = "/WEB-INF/jsp/corporatemanagement/evidenceprovider/evidenceProviderEdit.jsp"),
        @Result(name = "areaFilter", value = "/WEB-INF/jsp/corporatemanagement/evidenceprovider/areaFilter.jsp"),
        @Result(name = "template", value = "/WEB-INF/jsp/corporatemanagement/evidenceprovider/templateUpload.jsp"),
        @Result(name = "fileUploadOk", value = "/WEB-INF/jsp/corporatemanagement/evidenceprovider/fileUploadOk.jsp"),
        @Result(name = "preview", value = "/WEB-INF/jsp/corporatemanagement/evidenceprovider/previewImage.jsp"),
        @Result(name = "stream", type = StreamResult.class, value = "inputStream", params = { "inputName", "inputStream",
                "contentType", "image/JPEG" }) })
@Namespace("/corporatemanagement")
@ParentPackage(value = "default")
public class EvidenceProviderAction extends BaseAction implements SessionAware {

    /**
     * Definimos las siguientes constantes para parseos
     */
    public static final String IP_ADDRESS =
    // "^(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$";
    "^([a-zA-Z0-9._%+-]*)$";
    // CHECKSTYLE:ON
    public static final String STRING = "^([a-zA-Z0-9._!:%+-/\\s$]*)$";
    public static final String NUMBER = "^([-+]?[0-9]*)$";
    public static final String FLOAT_NUMBER = "^([-+]?[0-9]*\\.?[0-9]*)$";
    public static final String DATE = "^(0[1-9]|[12][0-9]|3[01])[- /.](0[1-9]|1[012])[- /.](19|20)\\d\\d$";
    public static final String EMAIL = "^([a-zA-Z0-9._%+-]*@[a-zA-Z0-9.-]*\\.[a-zA-Z]{2,4})$";
    public static final String HOUR = "^((0?[0-9]|1[0-9]|2[0123])(:[0-5]\\d){0,2})$";
    public static final String PROTOCOL = "^((ht|f)tp(s?)|(ws))$";
    public static final String PORT = "^(6553[0-5]|655[0-2][0-9]|65[0-4][0-9]{2}|6[0-4][0-9]{3}|[0-5]?([0-9]){0,3}[0-9])$";
    private static final String LIST = "list";
    private static final String EDIT = "edit";
    private static final String SHOW_AREA_FILTER = "areaFilter";
    private static final String TEMPLATE = "template";
    private static final String FILE_UPLOAD_OK = "fileUploadOk";
    private static final String STREAM_RESULT = "stream";
    private EvidenceProvider evidenceProvider;
    private Map session;
    private List<EvidenceProvider> evidenceProviders;
    private String pattern;
    private File upload; // The actual file
    private String uploadContentType; // The content type of the file
    private String uploadFileName; // The uploaded file name
    private File previewUpload; // The actual file
    private String previewUploadContentType; // The content type of the file
    private String previewUploadFileName; // The uploaded file name
    private String fileCaption; // The caption of the file entered by user
    private InputStream inputStream;
    private String contentType;
    private String fileClientPath;
    private String fileBGClientPath;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    public String list() throws ScopixException {
        if (evidenceProvider == null || evidenceProvider.getId() != null) {
            evidenceProvider = (EvidenceProvider) getSession().get("evidenceProviderFilter");
        }
        if (getEvidenceProvider() != null) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            evidenceProviders = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class)
                    .getEvidenceProviderList(evidenceProvider, sessionId);
            session.put("evidenceProviderFilter", evidenceProvider);
        }
        return LIST;
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String newEvidenceProvider() {
        this.setEditable(false);
        evidenceProvider = new EvidenceProvider();
        session.remove("idStore");
        return EDIT;
    }

    public String editEvidenceProvider() throws ScopixException {
        if (evidenceProvider != null && evidenceProvider.getId() != null && evidenceProvider.getId() > 0) {
            this.setEditable(true);
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            evidenceProvider = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class)
                    .getEvidenceProvider(evidenceProvider.getId(), sessionId);
            session.put("pattern", evidenceProvider.getEvidenceProviderType().getPattern());
            session.put("idStore", (evidenceProvider != null && evidenceProvider.getStore() != null ? evidenceProvider.getStore()
                    .getId() : null));

        } else {
            this.addActionError(this.getText("error.general.edit", new String[] { this.getText("label.evidenceProvider") }));
        }
        return EDIT;
    }

    public String deleteEvidenceProvider() throws ScopixException {
        if (evidenceProvider != null && evidenceProvider.getId() != null && evidenceProvider.getId() > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class)
                    .removeEvidenceProvider(evidenceProvider.getId(), sessionId);
        } else {
            this.addActionError(this.getText("error.general.delete", new String[] { this.getText("label.evidenceProvider") }));
        }
        this.list();
        return LIST;
    }

    public String saveEvidenceProvider() throws ScopixException {
        if (evidenceProvider.getName() == null || evidenceProvider.getName().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[] { this.getText("label.name") }));
        }
        if (evidenceProvider.getDescription() == null || evidenceProvider.getDescription().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[] { this.getText("label.description") }));
        }
        if (evidenceProvider.getDefinitionData() == null || evidenceProvider.getDefinitionData().length() == 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[] { this.getText("label.definitionData") }));
        } else if (!checkPattern(evidenceProvider.getDefinitionData(), (String) session.get("pattern"))) {
            this.addActionError(this.getText("error.general.invalidField", new String[] { this.getText("label.definitionData") }));
        }
        if (evidenceProvider.getEvidenceProviderType() == null || evidenceProvider.getEvidenceProviderType().getId() == null
                || evidenceProvider.getEvidenceProviderType().getId() <= 0) {
            this.addActionError(this.getText("error.general.requiredField",
                    new String[] { this.getText("label.evidenceProviderType") }));
        }
        if (evidenceProvider.getStore() == null || evidenceProvider.getStore().getId() == null
                || evidenceProvider.getStore().getId() <= 0) {
            this.addActionError(this.getText("error.general.requiredField", new String[] { this.getText("label.store") }));
        }
        if (evidenceProvider.getAreas() == null || evidenceProvider.getAreas().isEmpty()) {
            // == null || evidenceProvider.getArea().getId() <= 0
            this.addActionError(this.getText("error.general.requiredField", new String[] { this.getText("label.area") }));
        }
        if (!this.getActionErrors().isEmpty() || !this.getFieldErrors().isEmpty()) {
            return EDIT;
        }
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        if (this.isEditable()) {
            manager.updateEvidenceProvider(evidenceProvider, sessionId);
        } else {
            manager.addEvidenceProvider(evidenceProvider, sessionId);
        }

        this.loadFilters();
        return SUCCESS;
    }

    public String cancel() {
        this.loadFilters();
        return SUCCESS;
    }

    public String modifyTemplatePath() throws ScopixException {
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        ServletActionContext.getRequest().setAttribute("noShow", "true");
        String evidenceProviderId = ServletActionContext.getRequest().getParameter("evidenceProviderId");
        if (evidenceProviderId != null && evidenceProviderId.length() > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            evidenceProvider = manager.getEvidenceProvider(Integer.parseInt(evidenceProviderId), sessionId);
        } else {
            this.addActionError(this.getText("error.general.edit", new String[] { this.getText("label.evidenceProvider") }));
        }

        return TEMPLATE;
    }

    public List<SituationTemplate> getSituationTemplates() throws ScopixException {
        Set<Integer> ids = new HashSet<Integer>();
        for (Area a : evidenceProvider.getAreas()) {
            ids.add(a.getId());
        }
        List<SituationTemplate> situationTemplates = SpringSupport.getInstance()
                .findBeanByClassName(CorporateStructureManager.class).getSituationTemplateListByArea(ids);
        return situationTemplates;
    }

    public String uploadFile() throws ScopixException {
        CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
        if (upload != null && uploadFileName != null) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            evidenceProvider = manager.getEvidenceProvider(evidenceProvider.getId(), sessionId);

            String extension = "." + FilenameUtils.getExtension(uploadFileName);
            // uploadFileName.substring(uploadFileName.lastIndexOf("."), uploadFileName.length());
            Integer situatioTemplateId = Integer.parseInt(ServletActionContext.getRequest().getParameter("sitautionTemplateId"));
            EvidenceProviderTemplate aux = null;
            for (EvidenceProviderTemplate template : evidenceProvider.getEvidenceProviderTemplates()) {
                if (template.getSituationTemplate().getId().equals(situatioTemplateId)) {
                    aux = template;
                    break;
                }
            }

            if (aux == null) {
                // si no existe conbinacion agregamos la nueva
                aux = new EvidenceProviderTemplate();
                SituationTemplate st = new SituationTemplate();
                st.setId(situatioTemplateId);
                aux.setSituationTemplate(st);
                evidenceProvider.getEvidenceProviderTemplates().add(aux);
            }
            // se debe recuperar el situation template seleccionado
            // almacenamos la imagen
            String url = manager.saveTemplateFileOnDisk(upload, evidenceProvider.getStore().getId(), evidenceProvider, extension,
                    aux.getSituationTemplate());
            aux.setTemplatePath(url);
            // antes de guardar levantamos las areas por si no se han levantado
            evidenceProvider.getAreas().isEmpty();
            manager.updateEvidenceProvider(evidenceProvider, sessionId);
            this.addActionMessage(this.getText("message.fileUpload.OK"));
        } else {
            this.addActionError(this.getText("message.fileUpload.uploadRequired"));
            return TEMPLATE;
        }

        return FILE_UPLOAD_OK;
    }

    public String preview() throws ScopixException, IOException {
        if (upload != null && uploadFileName != null && previewUpload != null && previewUploadFileName != null) {
            this.modifyTemplatePath();
            InputStream isBack = new FileInputStream(previewUpload);
            ByteArrayOutputStream os = new ByteArrayOutputStream();
            byte[] array = new byte[2048];
            while (isBack.read(array) > 0) {
                os.write(array);
            }
            InputStream is1 = new ByteArrayInputStream(os.toByteArray());

            InputStream isFront = new FileInputStream(upload);
            os = new ByteArrayOutputStream();
            array = new byte[2048];
            while (isFront.read(array) > 0) {
                os.write(array);
            }
            InputStream is2 = new ByteArrayInputStream(os.toByteArray());

            session.put("BACK_GROUND_IMAGE", is1);
            session.put("FRONT_IMAGE", is2);
            ServletActionContext.getRequest().setAttribute("PREVIEW", "OK");
            os.close();
            isBack.close();
            isFront.close();
        } else {
            this.addActionError(this.getText("message.fileUpload.backgroundRequired"));
            this.addActionError(this.getText("message.fileUpload.uploadRequired"));
        }
        return TEMPLATE;
    }

    public String getBackgroundImage() throws Exception {
        try {
            if (session.get("BACK_GROUND_IMAGE") != null) {
                setInputStream((InputStream) session.get("BACK_GROUND_IMAGE"));
                setContentType("image/jpg");
            }
        } catch (Exception e) {
            this.addActionError("Error: " + e.getMessage());
        }
        return STREAM_RESULT;
    }

    public String getImage() throws Exception {
        try {
            if (session.get("FRONT_IMAGE") != null) {
                setInputStream((InputStream) session.get("FRONT_IMAGE"));
                setContentType("image/PNG");
            }
        } catch (Exception e) {
            this.addActionError("Error: " + e.getMessage());
        }
        return STREAM_RESULT;
    }

    public Map getSession() {
        return session;
    }

    @Override
    public void setSession(Map session) {
        this.session = session;
    }

    public List<EvidenceProvider> getEvidenceProviders() {
        return evidenceProviders;
    }

    public void setEvidenceProviders(List<EvidenceProvider> evidenceProviders) {
        this.evidenceProviders = evidenceProviders;
    }

    public EvidenceProvider getEvidenceProvider() {
        return evidenceProvider;
    }

    public void setEvidenceProvider(EvidenceProvider evidenceProvider) {
        this.evidenceProvider = evidenceProvider;
    }

    public List<EvidenceProviderType> getEvidenceProviderTypes() throws ScopixException {
        List<EvidenceProviderType> evidenceProviderTypes = (List) session.get("evidenceProviderTypes");
        if (evidenceProviderTypes != null && evidenceProviderTypes.size() > 0) {
            evidenceProviderTypes = (List<EvidenceProviderType>) session.get("evidenceProviderTypes");
        } else {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            evidenceProviderTypes = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class)
                    .getEvidenceProviderTypeList(null, sessionId);
            session.put("evidenceProviderTypes", evidenceProviderTypes);
        }
        return evidenceProviderTypes;
    }

    public List<Store> getStores() throws ScopixException {
        List<Store> stores = null;
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        stores = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getStoreList(null, sessionId);
        return stores;
    }

    public String showAreaFilter() {
        this.loadFilters();
        session.put("idStoreFilter", (evidenceProvider != null && evidenceProvider.getStore() != null ? evidenceProvider
                .getStore().getId() : null));
        return SHOW_AREA_FILTER;
    }

    public String showArea() {
        session.put("idStore", (evidenceProvider != null && evidenceProvider.getStore() != null ? evidenceProvider.getStore()
                .getId() : null));
        return EDIT;
    }

    public List<Area> getAreasFilter() throws ScopixException {
        List<Area> areas = null;
        Integer storeId = (Integer) session.get("idStoreFilter");
        if (storeId != null && storeId > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            Area area = new Area();
            area.setStore(new Store());
            area.getStore().setId(storeId);
            areas = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getAreaList(area, sessionId);
        } else {
            areas = new ArrayList<Area>();
        }
        return areas;
    }

    public List<Area> getAreas() throws ScopixException {
        List<Area> areas = null;
        Integer storeId = (Integer) session.get("idStore");
        if (storeId != null && storeId > 0) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            Area area = new Area();
            area.setStore(new Store());
            area.getStore().setId(storeId);
            areas = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class).getAreaList(area, sessionId);
        } else {
            areas = new ArrayList<Area>();
        }
        return areas;
    }

    public String showPattern() {
        List<EvidenceProviderType> evidenceProviderTypes = (List<EvidenceProviderType>) session.get("evidenceProviderTypes");
        Collections.sort(evidenceProviderTypes);
        int index = Collections.binarySearch(evidenceProviderTypes, evidenceProvider.getEvidenceProviderType());
        if (index >= 0) {
            pattern = evidenceProviderTypes.get(index).getPattern();
            evidenceProvider.setDefinitionData(pattern);
            session.put("pattern", pattern);
        } else {
            pattern = "";
            evidenceProvider.setDefinitionData(pattern);
        }
        session.put("pattern", pattern);
        return EDIT;
    }

    public String getPattern() {
        return pattern;
    }

    public void setPattern(String pattern) {
        this.pattern = pattern;
    }

    private void loadFilters() {
        evidenceProvider = (EvidenceProvider) getSession().get("evidenceProviderFilter");
    }

    /**
     * @return the fileCaption
     */
    public String getFileCaption() {
        return fileCaption;
    }

    /**
     * @param fileCaption
     *            the fileCaption to set
     */
    public void setFileCaption(String fileCaption) {
        this.fileCaption = fileCaption;
    }

    /**
     * @return the inputStream
     */
    public InputStream getInputStream() {
        return inputStream;
    }

    /**
     * @param inputStream
     *            the inputStream to set
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
     * @param contentType
     *            the contentType to set
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return the upload
     */
    public File getUpload() {
        return upload;
    }

    /**
     * @param upload
     *            the upload to set
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
     * @param uploadContentType
     *            the uploadContentType to set
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
     * @param uploadFileName
     *            the uploadFileName to set
     */
    public void setUploadFileName(String uploadFileName) {
        this.uploadFileName = uploadFileName;
    }

    /**
     * @return the previewUpload
     */
    public File getPreviewUpload() {
        return previewUpload;
    }

    /**
     * @param previewUpload
     *            the previewUpload to set
     */
    public void setPreviewUpload(File previewUpload) {
        this.previewUpload = previewUpload;
    }

    /**
     * @return the previewUploadContentType
     */
    public String getPreviewUploadContentType() {
        return previewUploadContentType;
    }

    /**
     * @param previewUploadContentType
     *            the previewUploadContentType to set
     */
    public void setPreviewUploadContentType(String previewUploadContentType) {
        this.previewUploadContentType = previewUploadContentType;
    }

    /**
     * @return the previewUploadFileName
     */
    public String getPreviewUploadFileName() {
        return previewUploadFileName;
    }

    /**
     * @param previewUploadFileName
     *            the previewUploadFileName to set
     */
    public void setPreviewUploadFileName(String previewUploadFileName) {
        this.previewUploadFileName = previewUploadFileName;
    }

    /**
     * @return the fileClientPath
     */
    public String getFileClientPath() {
        return fileClientPath;
    }

    /**
     * @param fileClientPath
     *            the fileClientPath to set
     */
    public void setFileClientPath(String fileClientPath) {
        this.fileClientPath = fileClientPath;
    }

    /**
     * @return the fileBGClientPath
     */
    public String getFileBGClientPath() {
        return fileBGClientPath;
    }

    /**
     * @param fileBGClientPath
     *            the fileBGClientPath to set
     */
    public void setFileBGClientPath(String fileBGClientPath) {
        this.fileBGClientPath = fileBGClientPath;
    }

    public static boolean checkPattern(String data, String pattern) {
        boolean result = true;
        if (data != null && pattern != null && data.length() > 0 && pattern.length() > 0) {
            String[] firstData = data.split(";");
            String[] firstPattern = pattern.split(";");
            for (int i = 0; i < firstPattern.length; i++) {
                String[] secondData = firstData[i].split("=");
                String[] secondPattern = firstPattern[i].split("=");
                result = (secondData[0].equals(secondPattern[0]));
                if (result) {
                    if ("[IP_ADDRESS]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(IP_ADDRESS));
                    } else if ("[STRING]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(STRING));
                    } else if ("[NUMBER]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(NUMBER));
                    } else if ("[FLOAT_NUMBER]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(FLOAT_NUMBER));
                    } else if ("[DATE]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(DATE));
                    } else if ("[EMAIL]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(EMAIL));
                    } else if ("[PROTOCOL]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(PROTOCOL));
                    } else if ("[PORT]".equalsIgnoreCase(secondPattern[1])) {
                        result = (secondData[1].matches(PORT));
                    }
                }
                if (!result) {
                    break;
                }
            }
        } else {
            result = false;
        }
        return result;
    }
}
