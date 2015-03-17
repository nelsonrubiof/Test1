<%@ taglib prefix="s" uri="/struts-tags"%>
<h3 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="tab.templateManager.businessRuleTemplate"/>
</h3>
<s:actionerror/>
<s:form action="businessRuleTemplate!save" id="saveBRTForm">
    <s:hidden name="editable"/>
    <s:hidden name="businessRuleTemplate.id"/>
    <s:if test="editable">
        <s:hidden name="businessRuleTemplateState" value="%{businessRuleTemplate.businessRuleTemplateState.name}"/>
    </s:if>    
    <s:textfield key="label.name" name="businessRuleTemplate.name" required="true"/>
    <s:textfield key="label.description" name="businessRuleTemplate.description" required="true"/>
    <s:textfield key="label.defaultCriticalNonComplianceTarget" name="businessRuleTemplate.defaultCriticalNonComplianceTarget" required="true"/>
    <s:textfield key="label.defaultNonComplianceTarget" name="businessRuleTemplate.defaultNonComplianceTarget" required="true"/>
    <s:textfield key="label.springBeanEvaluatorName" name="businessRuleTemplate.springBeanEvaluatorName" />    
    <s:if test="editable">
        <s:if test="businessRuleTemplate.businessRuleTemplateState!=null && businessRuleTemplate.businessRuleTemplateState.name != 'ACTIVE'">
            <s:url id="businessRuleTemplateActivate" namespace="/templatemanager" action="businessRuleTemplate!activate">
                <s:param name="businessRuleTemplate.id" value="businessRuleTemplate.id"/>                
            </s:url>
            <s:a href="%{businessRuleTemplateActivate}" targets="templateManagerTabDiv">
                <img src="/business-services/img/ok_circle.PNG" border="0" style="vertical-align:middle;" alt='<s:text name="label.activate"/>'>
            </s:a>
        </s:if>
        <s:else>
            <s:url id="businessRuleTemplateDeactivate" namespace="/templatemanager" action="businessRuleTemplate!deactivate">
                <s:param name="businessRuleTemplate.id" value="businessRuleTemplate.id"/>
            </s:url>
            <s:a href="%{businessRuleTemplateDeactivate}" targets="templateManagerTabDiv">
                <img src="/business-services/img/nook_circle.PNG" border="0" style="vertical-align:middle;" alt='<s:text name="label.deactivate"/>'>
            </s:a>
        </s:else>
    </s:if>
    &nbsp;
    <s:url id="businessRuleTemplateSave" namespace="/templatemanager" action="businessRuleTemplate!save"/>
    <s:a key="label.save" formId="saveBRTForm"  href="%{businessRuleTemplateSave}" labelposition="left" targets="templateManagerTabDiv" >
        <img src="/business-services/img/save.PNG" border="0" style="vertical-align:middle;" alt='<s:text name="label.save"/>'>
    </s:a>
    &nbsp;
    <s:url id="businessRuleTemplateCancel" namespace="/templatemanager" action="businessRuleTemplate!list"/>
<s:a key="label.cancel" notifyTopics="refreshIndex"labelposition="left" href="%{businessRuleTemplateCancel}" targets="templateManagerTabDiv">
    <img src="/business-services/img/cancel.PNG" border="0" style="vertical-align:middle;" alt='<s:text name="label.cancel"/>'>
</s:a>
</s:form>
<s:if test="editable">
    <s:div>
        <h4><s:text name="label.complianceConditionTemplateList"/></h4>
        <s:url id="complianceConditionTemplateList" namespace="/templatemanager" action="complianceConditionTemplate!list">
            <s:param name="complianceConditionTemplate.businessRuleTemplate.id" value="businessRuleTemplate.id"/>
        </s:url>    
        <s:div id="complianceConditionTemplateDiv" key="tab.templateManager.complianceConditionTemplate" href="%{complianceConditionTemplateList}" listenTopics="refreshComplianceConditionTemplateList"/>
    </s:div>
</s:if>
<br>
