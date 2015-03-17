<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:hidden name="situationTemplateId" value="situationTemplateId"/>
<display:table name="requiredSituationElementTemplates" id="rset">
        <s:if test="#attr.rset != null">
                <s:push value="#attr.rset">
                    <display:column titleKey="label.id" property="id"/>
                    <display:column titleKey="label.evidenceDurationInSec" property="evidenceDurationInSec"/>
                    <display:column titleKey="label.evidenceType" property="evidenceType.description"/>
                    <display:column titleKey="label.mustBeReviewedBySupervisor">
                        <s:if test="mustBeReviewedBySupervisor">
                            <img src="/business-services/img/ok_circle.PNG" height="15" width="15" border="0" style="vertical-align:middle;" alt='<s:text name="label.true"/>'>
                        </s:if>
                        <s:else>
                            <img src="/business-services/img/nook_circle.PNG" height="15" width="15" border="0" style="vertical-align:middle;" alt='<s:text name="label.false"/>'>
                        </s:else>
                    </display:column>
                    <display:column titleKey="label.situationTemplate" property="situationTemplate.id"/>
                    <display:column titleKey="label.springBeanEvaluatorName" property="springBeanEvaluatorName"/>
                    <display:column >
                        <s:url id="requiredSituationElementTemplateEdit" namespace="/templatemanager" action="requiredSituationElementTemplate!edit">
                            <s:param name="requiredSituationElementTemplate.id" value="id" />
                            <s:param name="requiredSituationElementTemplate.situationTemplate.id" value="situationTemplateId"/>
                        </s:url>
                        <s:a href="%{requiredSituationElementTemplateEdit}" targets="templateManagerTabDiv">
                            <img src="/business-services/img/edit.PNG" height="20" width="20" border="0" style="vertical-align:middle;" alt='<s:text name="label.edit"/>'>
                        </s:a>
                    </display:column>
                    <display:column>
                        <s:url id="requiredSituationElementTemplateDelete" namespace="/templatemanager" action="requiredSituationElementTemplate!confirm">
                            <s:param name="requiredSituationElementTemplate.id" value="id" />
                            <s:param name="requiredSituationElementTemplate.situationTemplate.id" value="situationTemplateId"/>
                        </s:url>
                        <s:a href="%{requiredSituationElementTemplateDelete}" targets="requiredSituationElementTemplateDiv">
                            <img src="/business-services/img/delete.PNG" height="20" width="20" border="0" style="vertical-align:middle;" alt='<s:text name="label.delete"/>'>
                        </s:a>
                    </display:column>
                </s:push>
        </s:if>
</display:table>
<s:url id="requiredSituationElementTemplateAdd" namespace="/templatemanager" action="requiredSituationElementTemplate!openNew">
    <s:param name="requiredSituationElementTemplate.situationTemplate.id" value="situationTemplateId"/>
</s:url>
<s:submit key="label.add" align="left" href="%{requiredSituationElementTemplateAdd}" targets="templateManagerTabDiv" type="image" src="/business-services/img/new.PNG"/>