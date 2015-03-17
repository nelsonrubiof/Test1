<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
 
<script type="text/javascript">
<!--
 
if (<s:property value="refreshList"/>) {
publishTopic('refreshBusinessRuleList');
}

//layoutForm('businessRuleDetailForm', 2);
//layoutForm('newCCFromTemplateForm', 2);


//-->
</script>

<s:actionerror />
<s:actionmessage />
<h3><s:property value="headerText" /></h3>

<s:form action="businessRuleDetail!save" id="businessRuleDetailForm">
	<s:select label="Place" name="placeId" list="placeMap"
		cssStyle="width: 200px" />
	<s:select label="Check result definer" name="checkResultDefiner"
		list="enums.checkResultDefiner" />
	<s:hidden name="brTemplateId" />
	<s:hidden name="businessRuleId" />
	<s:hidden name="persistent" />
	<s:hidden name="placeIdOrig" value="%{placeId}" />


	<s:textfield label="Comp. target timespan" name="compTargetTimespan" />
	<s:textfield label="CNC target" name="criticalNonCompTarget" />
	<s:textfield label="Overall NC target" name="overallNonCompTarget" />
	<s:submit targets="businessRuleDetailDiv" value="%{saveButtonLabel}"
		executeScripts="true" />
</s:form>

<s:if test="persistent">
	<display:table name="complianceConditions" class="isis" id="cc">
		<s:if test="#attr.cc != null">
			<s:push value="#attr.cc">
				<display:column property="complianceConditionTemplate.name"
					title="Template" />
				<display:column title="Evidence provider">
					<s:if test="evidenceProvInstruction != null">
						<s:if test="evidenceProvInstruction.evidenceProvider != null">
							<s:push value="evidenceProvInstruction.evidenceProvider">
								<s:property value="name" /> (at <s:property value="place.name" /> )
                             </s:push>
						</s:if>
					</s:if>
				</display:column>
				<display:column title="">
					<s:url id="editCcUrl" action="complianceConditionDetail!open">
						<s:param name="complianceConditionId" value="id" />

					</s:url>
					<s:a href="%{editCcUrl}" targets="businessRuleDetailDiv">edit</s:a>
				</display:column>
			</s:push>
		</s:if>
	</display:table>

	<s:form action="complianceConditionDetail!openNew"
		id="newCCFromTemplateForm">
		<s:hidden name="businessRuleId" />
		<s:select list="ccTemplates" name="ccTemplateId" listKey="id"
			listValue="name" />

		<s:submit value="New CC from Template" targets="businessRuleDetailDiv" />
	</s:form>
</s:if>





