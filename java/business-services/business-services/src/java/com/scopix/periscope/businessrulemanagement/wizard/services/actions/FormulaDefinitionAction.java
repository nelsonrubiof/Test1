/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.scopix.periscope.businessrulemanagement.wizard.services.actions;

import com.scopix.periscope.extractionplanmanagement.CompliantTypeEnum;
import com.scopix.periscope.extractionplanmanagement.Formula;
import com.scopix.periscope.extractionplanmanagement.FormulaTypeEnum;
import com.scopix.periscope.extractionplanmanagement.ExtractionPlanManager;
import com.scopix.periscope.corporatestructuremanagement.CorporateStructureManager;
import com.scopix.periscope.corporatestructuremanagement.Store;
import com.scopix.periscope.evaluationmanagement.Indicator;
import com.scopix.periscope.evaluationmanagement.IndicatorProductAndAreaType;
import com.scopix.periscope.frameworksfoundation.struts2.BaseAction;
import com.scopix.periscope.periscopefoundation.exception.ScopixException;
import com.scopix.periscope.periscopefoundation.util.config.SpringSupport;
import com.scopix.periscope.templatemanagement.SituationTemplate;
import com.scopix.periscope.templatemanagement.TemplatesManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.config.Namespace;
import org.apache.struts2.config.ParentPackage;
import org.apache.struts2.config.Result;
import org.apache.struts2.config.Results;
import org.apache.struts2.interceptor.SessionAware;

/**
 *
 * @author Cesar Abarza
 */
@Results({
    @Result(name = "success", value = "/WEB-INF/jsp/businessrules/wizards/formula/formulaDefinition.jsp"),
    @Result(name = "list", value = "/WEB-INF/jsp/businessrules/wizards/formula/list.jsp"),
    @Result(name = "add", value = "/WEB-INF/jsp/businessrules/wizards/formula/addEdit.jsp"),
    @Result(name = "edit", value = "/WEB-INF/jsp/businessrules/wizards/formula/addEdit.jsp")
})
@Namespace("/wizards")
@ParentPackage(value = "default")
public class FormulaDefinitionAction extends BaseAction implements SessionAware {

    private static final String LIST = "list";
    private static final String ADD = "add";
    private static final String EDIT = "edit";
    private Map<String, Object> session;
    private List<Store> stores;
    private List<Integer> storeIds;
    private List<Integer> situationTemplateIds;
    private List<Formula> formulas;
    private Formula formula;
    private String description;
    private Integer sId;
    private Integer stId;
    private List<String> variables;
    private String formulaType;
    private String compliantType;
    private String var;
    private String varDelete;
    private Integer indicatorId;
    private String standard;
    private String target;
    private Indicator indicator;
    private String formulaResult;
    private String denominatorResult;

    @Override
    public String execute() throws Exception {
        this.loadFilters();
        return SUCCESS;
    }

    public String readyList() throws ScopixException {
        this.list();
        return LIST;
    }

    public String list() throws ScopixException {
        if (formula == null || formula.getDescription() == null) {
            formula = (Formula) getSession().get("formulaFilter");
        } else {
            if (stId != null && stId > -1) {
                SituationTemplate st = new SituationTemplate();
                st.setId(stId);
                formula.getSituationTemplates().add(st);
            }
            if (sId != null && sId > -1) {
                Store s = new Store();
                s.setId(sId);
                formula.getStores().add(s);
            }
            if (formulaType != null && !formulaType.equals("-1")) {
                formula.setType(FormulaTypeEnum.valueOf(formulaType));
            }
            if (compliantType != null && !compliantType.equals("-1")) {
                formula.setCompliantType(CompliantTypeEnum.valueOf(compliantType));
            }
        }
        if (formula != null) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            ExtractionPlanManager manager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
            formulas = manager.getFormulaList(formula, sessionId);
            getSession().put("formulaFilter", formula);
        }
        return LIST;
    }

    public String addFormula() {
        formula = new Formula();
        this.setEditable(false);
        session.remove("VARIABLES");
        session.remove("showCompliantTypes");
        compliantType = null;
        formulaType = null;
        return ADD;
    }

    public String editFormula() throws ScopixException {
        if (formula.getId() != null && formula.getId() > 0) {
            ExtractionPlanManager manager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            formula = manager.getFormula(formula.getId(), sessionId);
            if (formula != null) {
                situationTemplateIds = new ArrayList<Integer>();
                for (SituationTemplate st : formula.getSituationTemplates()) {
                    situationTemplateIds.add(st.getId());
                }
                storeIds = new ArrayList<Integer>();
                for (Store s : formula.getStores()) {
                    storeIds.add(s.getId());
                }
                if (formula.getVariables() != null && formula.getVariables().length() > 0) {
                    variables = new ArrayList<String>();
                    String[] vars = formula.getVariables().split(";;");
                    for (int i = 0; i < vars.length; i++) {
                        variables.add(vars[i]);
                    }
                }
                formulaType = formula.getType().getName();
                if (formula.getCompliantType() != null) {
                    compliantType = formula.getCompliantType().getName();
                }
                if (formula.getTarget() != null) {
                    target = formula.getTarget().toString();
                }
                if (formula.getStandard() != null) {
                    standard = formula.getStandard().toString();
                }
                if (formula.getType().equals(FormulaTypeEnum.INDICATOR)) {
                    indicator = formula.getIndicator();
                }
                session.put("VARIABLES", variables);
            }
            session.remove("showCompliantTypes");
            this.setEditable(true);
        }
        return EDIT;
    }

    public FormulaTypeEnum[] getFormulaTypes() {
        return FormulaTypeEnum.values();
    }

    public CompliantTypeEnum[] getCompliantTypes() {
        if (this.isEditable()) {
            this.refreshCompliantType();
        }
        if (session.containsKey("showCompliantTypes")) {
            return CompliantTypeEnum.values();
        } else {
            return new CompliantTypeEnum[0];
        }
    }

    public String refreshCompliantType() {
        if (formulaType != null && !formulaType.equals("-1") && FormulaTypeEnum.valueOf(formulaType).equals(
                FormulaTypeEnum.COMPLIANCE)) {
            session.put("showCompliantTypes", "true");
        } else {
            session.remove("showCompliantTypes");
        }
        return EDIT;
    }

    public String refreshCompliantTypeFilter() {
        if (formulaType != null && !formulaType.equals("-1") && FormulaTypeEnum.valueOf(formulaType).equals(
                FormulaTypeEnum.COMPLIANCE)) {
            session.put("showCompliantTypes", "true");
        } else {
            session.remove("showCompliantTypes");
        }
        return SUCCESS;
    }

    public String addVariable() {
        if (var != null && var.length() > 0) {
            variables = this.getVariables();
            Collections.sort(variables);
            if (Collections.binarySearch(variables, var) < 0) {
                variables.add(var);
                session.put("VARIABLES", variables);
                var = "";
            } else {
                this.addActionError(this.getText("formulas.addVariableExist"));
            }
        } else {
            this.addActionError(this.getText("formulas.addVariableRequired"));
        }
        return EDIT;
    }

    public String deleteVariable() {
        if (varDelete != null && varDelete.length() > 0) {
            variables = this.getVariables();
            Collections.sort(variables);
            int index = Collections.binarySearch(variables, varDelete);
            if (index >= 0) {
                variables.remove(index);
                session.put("VARIABLES", variables);
            }
        }
        return EDIT;
    }

    public String testFormula() {
        variables = this.getVariables();
        if (!variables.isEmpty()) {
            HashMap<String, Double> vars = new HashMap<String, Double>();
            String expresion = formula.getFormula();
            for (String varx : variables) {
                try {
                    Double value = new Double(ServletActionContext.getRequest().getParameter(varx + "_value"));
                    vars.put(varx, value);
                    expresion = expresion.replace(varx, value.toString());
                } catch (Exception e) {
                    this.addActionError(this.getText("formulas.variableValueInvalid"));
                    break;
                }
            }
            if (!this.hasActionErrors()) {
                try {
                    long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
                    ExtractionPlanManager manager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
                    Double result = manager.testFormula(formula.getFormula(), vars, sessionId);
                    formulaResult = this.getText("formula.testResult", new String[]{expresion, result.toString()});
                } catch (Exception e) {
                    this.addActionError(this.getText("formulas.invalid", new String[]{e.getMessage()}));
                }
            }
        } else {
            this.addActionError(this.getText("formulas.variablesRequired"));
        }
        return EDIT;
    }

    public String testDenominatorFormula() {
        variables = this.getVariables();
        if (!variables.isEmpty()) {
            HashMap<String, Double> vars = new HashMap<String, Double>();
            String expresion = formula.getDenominator();
            for (String varx : variables) {
                try {
                    Double value = new Double(ServletActionContext.getRequest().getParameter(varx + "_value"));
                    vars.put(varx, value);
                    expresion = expresion.replace(varx, value.toString());
                } catch (Exception e) {
                    this.addActionError(this.getText("formulas.variableValueInvalid"));
                    break;
                }
            }
            if (!this.hasActionErrors()) {
                try {
                    long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
                    ExtractionPlanManager manager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
                    Double result = manager.testFormula(formula.getDenominator(), vars, sessionId);
                    denominatorResult = this.getText("formula.testResult", new String[]{expresion, result.toString()});
                } catch (Exception e) {
                    this.addActionError(this.getText("formulas.invalid", new String[]{e.getMessage()}));
                }
            }
        } else {
            this.addActionError(this.getText("formulas.variablesRequired"));
        }
        return EDIT;
    }

    private void validateIndicatorCreation() {
        if (formulaType != null && formulaType.equals(FormulaTypeEnum.INDICATOR.name()) && indicator != null) {
            if (indicator.getName() == null || indicator.getName().length() == 0) {
                this.addActionError(this.getText("error.general.requiredField",
                        new String[]{this.getText("label.indicatorName")}));
            }
            if (indicator.getInitialTime() == null || indicator.getInitialTime() < 0) {
                this.addActionError(this.getText("error.general.requiredField",
                        new String[]{this.getText("label.initialTime")}));
            }
            if (indicator.getEndingTime() == null || indicator.getEndingTime() < 0) {
                this.addActionError(this.getText("error.general.requiredField",
                        new String[]{this.getText("label.endingTime")}));
            }
            if (indicator.getLabelX() == null || indicator.getLabelX().length() == 0) {
                this.addActionError(this.getText("error.general.requiredField",
                        new String[]{this.getText("label.labelX")}));
            }
            if (indicator.getLabelY() == null || indicator.getLabelY().length() == 0) {
                this.addActionError(this.getText("error.general.requiredField",
                        new String[]{this.getText("label.labelY")}));
            }

        }
    }

    public String saveFormula() throws ScopixException {
        if (formula != null) {
            if (formula.getDescription() == null || formula.getDescription().length() == 0) {
                this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.description")}));
            }
            if (formulaType == null || formulaType.equals("-1")) {
                this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.formulaType")}));
            } else if (FormulaTypeEnum.valueOf(formulaType).equals(FormulaTypeEnum.COMPLIANCE)) {
                if (compliantType == null || compliantType.equals("-1")) {
                    this.addActionError(this.getText("error.general.invalidField", new String[]{this.getText(
                                "label.complianceType")}));
                }
                if (target == null || target.length() == 0) {
                    this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.target")}));
                } else {
                    try {
                        Double.parseDouble(target);
                    } catch (Exception e) {
                        this.addActionError(this.getText("error.general.invalidField",
                                new String[]{this.getText("label.target")}));
                    }
                }
                if (standard == null || standard.length() == 0) {
                    this.addActionError(this.getText("error.general.requiredField",
                            new String[]{this.getText("label.standard")}));
                } else {
                    try {
                        Double.parseDouble(standard);
                    } catch (Exception e) {
                        this.addActionError(this.getText("error.general.invalidField",
                                new String[]{this.getText("label.standard")}));
                    }
                }
            } else {
                if (formula.getDenominator() == null || formula.getDenominator().length() == 0) {
                    this.addActionError(this.getText("error.general.requiredField",
                            new String[]{this.getText("label.denominator")}));
                }
            }
            if (formula.getObservations() == null || formula.getObservations().length() == 0) {
                this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.observation")}));
            }
            if (situationTemplateIds == null || situationTemplateIds.isEmpty()) {
                this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText(
                            "label.situationTemplate")}));
            }
            if (storeIds == null || storeIds.isEmpty()) {
                this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.store")}));
            }
            if (getVariables() == null || getVariables().isEmpty()) {
                this.addActionError(this.getText("error.general.requiredField",
                        new String[]{this.getText("label.variableName")}));
            }
            if (formula.getFormula() == null || formula.getFormula().length() == 0) {
                this.addActionError(this.getText("error.general.requiredField", new String[]{this.getText("label.formula")}));
            }

            this.validateIndicatorCreation();

            if (!this.hasActionErrors() && !this.hasActionMessages()) {
                for (Integer id : situationTemplateIds) {
                    SituationTemplate st = new SituationTemplate();
                    st.setId(id);
                    formula.getSituationTemplates().add(st);
                }
                for (Integer id : storeIds) {
                    Store s = new Store();
                    s.setId(id);
                    formula.getStores().add(s);
                }
                StringBuilder vars = new StringBuilder();
                for (String variable : variables) {
                    vars.append(variable).append(";;");
                }
                formula.setVariables(vars.substring(0, vars.length() - 2));
                formula.setType(FormulaTypeEnum.valueOf(formulaType));
                if (!compliantType.equals("-1")) {
                    formula.setCompliantType(CompliantTypeEnum.valueOf(compliantType));
                    formula.setTarget(Double.parseDouble(target));
                    formula.setStandard(Double.parseDouble(standard));
                }
                long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
                //Add indicators
                TemplatesManager templatesManager = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class);
                indicator.setIndicatorProductAndAreaTypes(new ArrayList<IndicatorProductAndAreaType>());
                for (Integer id : situationTemplateIds) {
                    SituationTemplate st = templatesManager.getSituationTemplate(id, sessionId);
                    IndicatorProductAndAreaType ipaat = new IndicatorProductAndAreaType();
                    ipaat.setIndicator(indicator);
                    ipaat.setAreaType(st.getAreaType());
                    ipaat.setProduct(st.getProduct());
                    indicator.getIndicatorProductAndAreaTypes().add(ipaat);
                }
                indicator.setFormula(formula);
                formula.setIndicator(indicator);

                ExtractionPlanManager manager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
                if (this.isEditable()) {
                    manager.editFormula(formula, sessionId);
                } else {
                    manager.addFormula(formula, sessionId);
                }
                formula = new Formula();
                formulaType = null;
                compliantType = null;
                description = null;
            } else {
                return EDIT;
            }
        }
        return SUCCESS;
    }

    public String deleteFormula() throws ScopixException {
        if (formula != null && formula.getId() != null && formula.getId() > 0) {
            try {
                long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
                ExtractionPlanManager manager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
                manager.deleteFormula(formula.getId(), sessionId);
                this.list();
            } catch (Exception e) {
                this.addActionError(this.getText("periscopeexception.entity.validate.constraintViolation",
                        new String[]{this.getText("label.formula")}));
            }

        }
        return LIST;
    }

    public List<Integer> getAvailableTimes() {
        List<Integer> times = new ArrayList<Integer>();
        times.add(0);
        times.add(1);
        times.add(2);
        times.add(3);
        times.add(4);
        times.add(5);
        times.add(6);
        times.add(7);
        times.add(8);
        times.add(9);
        times.add(10);
        times.add(11);
        times.add(12);
        times.add(13);
        times.add(14);
        times.add(15);
        times.add(16);
        times.add(17);
        times.add(18);
        times.add(19);
        times.add(20);
        times.add(21);
        times.add(22);
        times.add(23);
        return times;
    }

    @Override
    public void setSession(Map value) {
        this.session = value;
    }

    public Map<String, Object> getSession() {
        return this.session;
    }

    public List<SituationTemplate> getSituationTemplates() throws ScopixException {
        long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
        TemplatesManager manager = SpringSupport.getInstance().findBeanByClassName(TemplatesManager.class);
        SituationTemplate situationTemplate = new SituationTemplate();
        situationTemplate.setActive(true);
        List<SituationTemplate> situationTemplates = manager.getSituationTemplateList(situationTemplate, sessionId);
        return situationTemplates;
    }

    /**
     * @return the stores
     */
    public List<Store> getStores() throws ScopixException {
        if (stores == null) {
            long sessionId = getSession().containsKey("sessionId") ? (Long) getSession().get("sessionId") : 0;
            CorporateStructureManager manager = SpringSupport.getInstance().findBeanByClassName(CorporateStructureManager.class);
            stores = manager.getStoreList(null, sessionId);
        }

        return stores;
    }

    public List<Indicator> getIndicatorNames() {
        ExtractionPlanManager manager = SpringSupport.getInstance().findBeanByClassName(ExtractionPlanManager.class);
        List<Indicator> indicators = manager.getIndicatorList(null, null);
        return indicators;
    }

    /**
     * @param stores the stores to set
     */
    public void setStores(List<Store> stores) {
        this.stores = stores;
    }

    /**
     * @return the storeIds
     */
    public List<Integer> getStoreIds() {
        if (storeIds == null) {
            storeIds = new ArrayList<Integer>();
        }

        return storeIds;
    }

    /**
     * @param storeIds the storeIds to set
     */
    public void setStoreIds(List<Integer> storeIds) {
        this.storeIds = storeIds;
    }

    /**
     * @return the situationTemplateIds
     */
    public List<Integer> getSituationTemplateIds() {
        if (situationTemplateIds == null) {
            situationTemplateIds = new ArrayList<Integer>();
        }

        return situationTemplateIds;
    }

    /**
     * @param situationTemplateIds the situationTemplateIds to set
     */
    public void setSituationTemplateIds(List<Integer> situationTemplateIds) {
        this.situationTemplateIds = situationTemplateIds;
    }

    /**
     * @return the formulas
     */
    public List<Formula> getFormulas() {
        if (formulas == null) {
            formulas = new ArrayList<Formula>();
        }

        return formulas;
    }

    /**
     * @param formulas the formulas to set
     */
    public void setFormulas(List<Formula> formulas) {
        this.formulas = formulas;
    }

    /**
     * @return the formula
     */
    public Formula getFormula() {
        return formula;
    }

    /**
     * @param formula the formula to set
     */
    public void setFormula(Formula formula) {
        this.formula = formula;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the sId
     */
    public Integer getsId() {
        return sId;
    }

    /**
     * @param sId the sId to set
     */
    public void setsId(Integer sId) {
        this.sId = sId;
    }

    /**
     * @return the stId
     */
    public Integer getStId() {
        return stId;
    }

    /**
     * @param stId the stId to set
     */
    public void setStId(Integer stId) {
        this.stId = stId;
    }

    /**
     * @return the variables
     */
    public List<String> getVariables() {        
        variables = (List<String>) session.get("VARIABLES");
        if (variables == null) {
            variables = new ArrayList<String>();
        }

        return variables;
    }

    /**
     * @param variables the variables to set
     */
    public void setVariables(List<String> variables) {
        this.variables = variables;
    }

    /**
     * @return the formulaType
     */
    public String getFormulaType() {
        return formulaType;
    }

    /**
     * @param formulaType the formulaType to set
     */
    public void setFormulaType(String formulaType) {
        this.formulaType = formulaType;
    }

    /**
     * @return the compliantType
     */
    public String getCompliantType() {
        return compliantType;
    }

    /**
     * @param compliantType the compliantType to set
     */
    public void setCompliantType(String compliantType) {
        this.compliantType = compliantType;
    }

    /**
     * @return the var
     */
    public String getVar() {
        return var;
    }

    /**
     * @param var the var to set
     */
    public void setVar(String var) {
        this.var = var;
    }

    /**
     * @return the varDelete
     */
    public String getVarDelete() {
        return varDelete;
    }

    /**
     * @param varDelete the varDelete to set
     */
    public void setVarDelete(String varDelete) {
        this.varDelete = varDelete;
    }

    private void loadFilters() {
        formula = (Formula) getSession().get("formulaFilter");
    }

    /**
     * @return the indicatorId
     */
    public Integer getIndicatorId() {
        return indicatorId;
    }

    /**
     * @param indicatorId the indicatorId to set
     */
    public void setIndicatorId(Integer indicatorId) {
        this.indicatorId = indicatorId;
    }

    /**
     * @return the standard
     */
    public String getStandard() {
        return standard;
    }

    /**
     * @param standard the standard to set
     */
    public void setStandard(String standard) {
        this.standard = standard;
    }

    /**
     * @return the target
     */
    public String getTarget() {
        return target;
    }

    /**
     * @param target the target to set
     */
    public void setTarget(String target) {
        this.target = target;
    }

    /**
     * @return the indicator
     */
    public Indicator getIndicator() {
        return indicator;
    }

    /**
     * @param indicator the indicator to set
     */
    public void setIndicator(Indicator indicator) {
        this.indicator = indicator;
    }

    /**
     * @return the formulaResult
     */
    public String getFormulaResult() {
        return formulaResult;
    }

    /**
     * @param formulaResult the formulaResult to set
     */
    public void setFormulaResult(String formulaResult) {
        this.formulaResult = formulaResult;
    }

    /**
     * @return the denominatorResult
     */
    public String getDenominatorResult() {
        return denominatorResult;
    }

    /**
     * @param denominatorResult the denominatorResult to set
     */
    public void setDenominatorResult(String denominatorResult) {
        this.denominatorResult = denominatorResult;
    }
}
