<%@ taglib prefix="s" uri="/struts-tags"%>

<script type="text/javascript">
<!--
if (<s:property value="executeScripts"/>) {
dojo.event.topic.publish("refreshBrTemplateList", null);	
}

//-->
</script>

<s:actionerror />
<s:actionmessage />
<s:form action="brTemplateDetail!save">
	<s:hidden name="brTemplateId" />
	<s:hidden name="persistent" />
	<s:hidden name="nameOrig" />

	<s:textfield name="name" label="Name" required="true" />
	<s:textarea theme="xhtml" cols="60" name="description"
		label="Description" />
	<s:checkbox label="Automatic" name="automaticBusinessRule"
		required="true" />
	<s:checkbox label="Real Time" name="realTime" required="true" />
	<s:checkbox label="Event Driven" name="eventDriven" required="true" />
	<s:datetimepicker label="Valid from" name="validFrom" required="true" />
	<s:datetimepicker label="Valid to" name="validTo" required="true" />
	<s:textfield name="defaultComplianceTargetTimespan"
		label="Default Comp. target timespan" />
	<s:textfield name="defaultCriticalNonComplianceTarget"
		label="Default CNC target" />
	<s:textfield name="defaultOverallNonCompTarget"
		label="Default Overall NC target" />

	<s:submit value="Save BR Template" targets="brTemplateDetailDiv"
		executeScripts="true" />

</s:form>

<s:if test="persistent">
	<s:iterator value="ccTemplates">
		<s:url id="openCcTemplateUrl" namespace="cctemplates"
			action="ccTemplateDetail!open">
			<s:param name="ccTemplateId" value="id" />
		</s:url>
		<s:a href="%{openCcTemplateUrl}" targets="brTemplateDetailDiv">
			<s:property value="name" />
			<br>
		</s:a>
	</s:iterator>
	<br>
	<s:url id="newCcTemplateUrl" namespace="cctemplates"
		action="ccTemplateDetail!openNew" />
	<s:a href="%{newCcTemplateUrl}" targets="brTemplateDetailDiv">
				New CC Template
			</s:a>
</s:if>
