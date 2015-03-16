<%@ taglib prefix="s" uri="/struts-tags"%>

<s:if test="#request.businessRuleTemplateId != null">
    <s:url id="businessRuleTemplateEdit" namespace="/templatemanager" action="businessRuleTemplate!edit">
        <s:param name="businessRuleTemplate.id" value="#request.businessRuleTemplateId" />    
    </s:url>
    <s:div id="templateManagerTabDivIndex" key="tab.templateManager" cssClass="optionTabs" href="%{businessRuleTemplateEdit}" />
</s:if>
<s:elseif test="#request.complianceConditionTemplateId != null">
    <s:url id="complianceConditionTemplateEdit" namespace="/templatemanager" action="complianceConditionTemplate!edit">
        <s:param name="complianceConditionTemplate.id" value="#request.complianceConditionTemplateId" />    
    </s:url>
    <s:div id="templateManagerTabDivIndex" key="tab.templateManager" cssClass="optionTabs" href="%{complianceConditionTemplateEdit}" />
</s:elseif>
<s:elseif test="#request.situationTemplateId != null">
    <s:url id="situationTemplateEdit" namespace="/templatemanager" action="situationTemplate!edit">
        <s:param name="situationTemplate.id" value="#request.situationTemplateId" />    
    </s:url>
    <s:div id="templateManagerTabDivIndex" key="tab.templateManager" cssClass="optionTabs" href="%{situationTemplateEdit}" />
</s:elseif>
<s:else>
    <s:url id="businessRuleTemplateList" namespace="/templatemanager" action="businessRuleTemplate!list"/>
    <s:div id="templateManagerTabDivIndex" key="tab.templateManager" cssClass="optionTabs" href="%{businessRuleTemplateList}" listenTopics="recMessagesNext"/>
</s:else>