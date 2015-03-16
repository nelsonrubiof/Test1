<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<h3 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="tab.templateManager.requiredSituationElementTemplate"/>
</h3>
<s:form action="requiredSituationElementTemplate!save" id="saveRSETForm">
    <s:hidden name="editable"/>
    <s:textfield key="label.id" name="requiredSituationElementTemplate.id" readonly="true"/>
    <s:textfield key="label.evidenceDurationInSec" name="requiredSituationElementTemplate.evidenceDurationInSec" required="true"/>
    <s:select key="label.evidenceType" name="requiredSituationElementTemplate.evidenceType.id" list="evidenceTypeList" listKey="id" listValue="description" value="requiredSituationElementTemplate.evidenceType.id" required="true"/>    
    <s:checkbox key="label.mustBeReviewedBySupervisor" name="requiredSituationElementTemplate.mustBeReviewedBySupervisor" required="true"/>
    <s:select key="label.situationTemplate" name="requiredSituationElementTemplate.situationTemplate.id" list="situationTemplates" listKey="id" listValue="id" value="requiredSituationElementTemplate.situationTemplate.id" required="true"/>
    <s:textfield key="label.springBeanEvaluatorName" name="requiredSituationElementTemplate.springBeanEvaluatorName" required="true"/>
    
    <s:url id="requiredSituationElementTemplateSave" namespace="/templatemanager" action="requiredSituationElementTemplate!save"/>
    <s:a key="label.save" formId="saveRSETForm" href="%{requiredSituationElementTemplateSave}" targets="templateManagerTabDiv">
         <img src="/business-services/img/save.PNG" border="0" style="vertical-align:middle;" alt='<s:text name="label.save"/>'>
    </s:a>
    &nbsp;
    <s:url id="requiredSituationElementTemplateCancel" namespace="/templatemanager" action="situationTemplate!edit">
        <s:param name="situationTemplate.id" value="requiredSituationElementTemplate.situationTemplate.id"/>
        <s:param name="editable" value="true"/>
    </s:url>
    <s:a href="%{requiredSituationElementTemplateCancel}" key="label.cancel" targets="templateManagerTabDiv">
        <img src="/business-services/img/cancel.PNG" border="0" style="vertical-align:middle;" alt='<s:text name="label.cancel"/>'>
    </s:a>
</s:form>


