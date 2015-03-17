<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url id="backToBrTemplate" action="brTemplateDetail!open" namespace="/brtemplates">
	<s:param name="brTemplateId" value="brTemplateId" />
</s:url>

<s:a href="%{backToBrTemplate}" targets="brTemplateDetailDiv">
	Cancel edit and to back to <em><s:property value="brTemplateName" /></em>
</s:a>

<s:actionerror />
<s:actionmessage />
<s:if test="%{persistent}">
	<h3>Editing CC template: <em><s:property value="name" /></em></h3>
</s:if>
<s:else>
	<h3>Creating new CC template</h3>
</s:else>
<s:form action="ccTemplateDetail!save" validate="true">
	<s:hidden name="brTemplateId" />
	<s:hidden name="ccTemplateId" />
	<s:hidden name="persistent" />

	<s:textfield name="name" label="Name" required="true" />
	<s:textarea theme="xhtml" cols="60" name="description"
		label="Description" required="true" />
	<s:textfield label="Weight" name="weight" required="true" />
	<s:textfield label="Measure unit" name="measureUnit" />
	<s:textfield name="defaultCriticalNonComplianceTarget"
		label="Default CNC target" required="true" />
	<s:textfield name="defaultOverallNonCompTarget"
		label="Default Overall NC target" required="true" />
	<s:select label="Evaluation type" list="enums.ccEvaluationType"
		name="ccEvaluationType" />
	<s:select label="CC type" list="enums.complianceConditionType"
		name="complianceConditionType" />
	<s:submit value="Save CC Template" targets="brTemplateDetailDiv" />
</s:form>
