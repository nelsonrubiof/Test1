<%@ taglib prefix="s" uri="/struts-tags"%>

<s:actionerror />
<s:actionmessage />

<h3><s:property value="headerText" /></h3>
<s:form action="complianceConditionDetail!save">
	<s:hidden name="businessRuleId" />
	<s:hidden name="complianceConditionId" />
	<s:hidden name="ccTemplateId" />
	<s:hidden name="persistent" />

	<s:select name="criticalityId" label="Criticality"
		list='bos.allCriticalities' listKey="id" listValue="name" />

	<s:select label="Horizontal checker" name="horizontalChecker"
		list="enums.horizontalChecker" />

	<s:textfield label="Picture path" name="picturePath" required="true" />
	<s:textfield label="Should be measure" name="shouldBeMeasure" />

	<s:select label="Evidence Provider" list="evidenceProviderMap"
		name="evidenceProviderId" />
	<s:submit targets="businessRuleDetailDiv" value="%{saveButtonLabel}"
		executeScripts="true" />
</s:form>
