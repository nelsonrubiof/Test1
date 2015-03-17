<%@ taglib prefix="s" uri="/struts-tags"%>
<h3 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="tab.templateManager.complianceConditionTemplate"/>
</h3>
<s:form action="complianceConditionTemplate!save" id="saveCCTForm" method="all">
    <s:hidden name="complianceConditionTemplate.id" />
    <s:hidden name="editable"/>
    <s:textfield key="label.name" name="complianceConditionTemplate.name" required="true"/>
    <s:textfield key="label.description" name="complianceConditionTemplate.description" required="true"/>
    <s:textfield key="label.defaultCriticalNonComplianceTarget" name="complianceConditionTemplate.defaultCriticalNonComplianceTarget" required="true"/>
    <s:textfield key="label.defaultNonComplianceTarget" name="complianceConditionTemplate.defaultNonComplianceTarget" required="true"/>
    <s:select key="label.businessRuleTemplate" name="complianceConditionTemplate.businessRuleTemplate.id" list="businessRuleTemplates" listKey="id" listValue="name" required="true"/>
    <s:textfield key="label.springBeanEvaluatorName" name="complianceConditionTemplate.springBeanEvaluatorName" />
    
    <s:url id="complianceConditionTemplateSave" namespace="/templatemanager" action="complianceConditionTemplate!save"/>
    <s:a key="label.save" formId="saveCCTForm" href="%{complianceConditionTemplateSave}" targets="templateManagerTabDiv">
        <img src="/business-services/img/save.PNG" border="0" style="vertical-align:middle;" alt='<s:text name="label.save"/>'>
    </s:a>
    &nbsp;
    <s:url id="complianceConditionTemplateCancel" namespace="/templatemanager" action="businessRuleTemplate!edit" >
        <s:param name="businessRuleTemplate.id" value="complianceConditionTemplate.businessRuleTemplate.id"/>
        <s:param name="editable" value="true"/>
    </s:url>
    <s:a href="%{complianceConditionTemplateCancel}" key="label.cancel" targets="templateManagerTabDiv">
        <img src="/business-services/img/cancel.PNG" border="0" style="vertical-align:middle;" alt='<s:text name="label.cancel"/>'>
    </s:a>
</s:form>
<s:if test="editable">
    <s:div>
        <h4><s:text name="label.situationTemplatesList"/></h4>
        <s:url id="situationTemplateList" namespace="/templatemanager" action="situationTemplate!list">
            <s:param name="situationTemplate.complianceConditionTemplate.id" value="complianceConditionTemplate.id"/>
        </s:url>    
        <s:div id="situationTemplateDiv" key="tab.templateManager.complianceConditionTemplate" href="%{situationTemplateList}" listenTopics="refreshSituationTemplateList"/>
    </s:div>
</s:if>
<br>
