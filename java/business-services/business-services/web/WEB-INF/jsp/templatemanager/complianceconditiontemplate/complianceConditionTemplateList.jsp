<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:hidden name="businessRuleTemplateId" value="businessRuleTemplateId"/>
<display:table name="complianceConditionTemplates" id="cct">
        <s:if test="#attr.cct != null">
                <s:push value="#attr.cct">
                    <display:column titleKey="label.name" property="name"/>
                    <display:column titleKey="label.description" property="description"/>
                    <display:column titleKey="label.defaultCriticalNonComplianceTarget" property="defaultCriticalNonComplianceTarget"/>
                    <display:column titleKey="label.defaultNonComplianceTarget" property="defaultNonComplianceTarget"/>                    
                    <display:column >
                        <s:url id="complianceConditionTemplateEdit" namespace="/templatemanager" action="complianceConditionTemplate!edit">
                            <s:param name="complianceConditionTemplate.id" value="id" />
                            <s:param name="complianceConditionTemplate.businessRuleTemplate.id" value="businessRuleTemplateId"/>
                        </s:url>
                        <s:a href="%{complianceConditionTemplateEdit}" targets="templateManagerTabDiv">
                            <img src="/business-services/img/edit.PNG" height="20" width="20" border="0" style="vertical-align:middle;" alt='<s:text name="label.edit"/>'>
                        </s:a>
                    </display:column>
                    <display:column>
                        <s:url id="complianceConditionTemplateDelete" namespace="/templatemanager" action="complianceConditionTemplate!confirm">
                            <s:param name="complianceConditionTemplate.id" value="id" />
                            <s:param name="complianceConditionTemplate.businessRuleTemplate.id" value="businessRuleTemplateId"/>
                        </s:url>
                        <s:a href="%{complianceConditionTemplateDelete}" targets="complianceConditionTemplateDiv">
                            <img src="/business-services/img/delete.PNG" height="20" width="20" border="0" style="vertical-align:middle;" alt='<s:text name="label.delete"/>'>
                        </s:a>                        
                    </display:column>
                </s:push>
        </s:if>
</display:table>
<s:url id="complianceConditionTemplateAdd" namespace="/templatemanager" action="complianceConditionTemplate!openNew">
    <s:param name="complianceConditionTemplate.businessRuleTemplate.id" value="businessRuleTemplateId"/>
</s:url>
<s:submit key="label.add" align="left" href="%{complianceConditionTemplateAdd}" targets="templateManagerTabDiv" type="image" src="/business-services/img/new.PNG"/>