<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<h4><s:text name="label.businessRuleTemplateList"/></h4>
<s:actionerror />
<display:table name="businessRuleTemplates" id="brt" defaultsort="1" defaultorder="ascending" excludedParams="*"> 
        <s:if test="#attr.brt != null">
                <s:push value="#attr.brt">
                    <display:column titleKey="label.name" property="name" />
                    <display:column titleKey="label.description" property="description" />
                    <display:column titleKey="label.defaultCriticalNonComplianceTarget" property="defaultCriticalNonComplianceTarget" />
                    <display:column titleKey="label.defaultNonComplianceTarget" property="defaultNonComplianceTarget" />
                    <display:column titleKey="label.state" property="businessRuleTemplateState" />
                    <display:column >
                        <s:url id="businessRuleTemplateEdit" namespace="/templatemanager" action="businessRuleTemplate!edit">
                            <s:param name="businessRuleTemplate.id" value="id" />
                        </s:url>
                        <s:a href="%{businessRuleTemplateEdit}" targets="templateManagerTabDiv">
                            <img src="/business-services/img/edit.PNG" height="20" width="20" border="0" style="vertical-align:middle;" alt='<s:text name="label.edit"/>'>
                        </s:a>
                    </display:column>
                    <display:column>
                        <s:if test="businessRuleTemplateState==null || businessRuleTemplateState.name != 'ACTIVE'">
                            <s:url id="businessRuleTemplateDelete" namespace="/templatemanager" action="businessRuleTemplate!confirm">
                                <s:param name="businessRuleTemplate.id" value="id" />
                            </s:url>
                            <s:a href="%{businessRuleTemplateDelete}" targets="templateManagerTabDiv" >
                                <img src="/business-services/img/delete.PNG" height="20" width="20" border="0" style="vertical-align:middle;" alt='<s:text name="label.delete"/>'>
                            </s:a>
                        </s:if>
                    </display:column>
                </s:push>
        </s:if>        
</display:table>
<s:url id="businessRuleTemplateAdd" namespace="/templatemanager" action="businessRuleTemplate!openNew"/>
<s:submit key="label.add" align="left" href="%{businessRuleTemplateAdd}" targets="templateManagerTabDiv" type="image" src="/business-services/img/new.PNG"/>