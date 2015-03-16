<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url id="businessRuleDetailUrl" namespace="businessrules"
	action="businessRuleDetail" />

<s:url id="openBusinessRuleListUrl" namespace="businessrules"
	action="businessRules!openBusinessRuleList" />
	
<table>
	<tr>
		<td><s:div listenTopics="refreshBusinessRuleList" 
			href="%{openBusinessRuleListUrl}" /> <br>
		<s:form action="businessRuleDetail!openNew" validate="true">
			<s:hidden name="placeId" value="2" />
			<s:select listKey="id" listValue="name" list="brTemplates"
				name="brTemplateId" />
			<s:submit value="New BR from Template"
				targets="businessRuleDetailDiv" />
		</s:form></td>
		<td valign="top"><s:div id="businessRuleDetailDiv"
			href="%{businessRuleDetailUrl}" /></td>
	</tr>
</table>





