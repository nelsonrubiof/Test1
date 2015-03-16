<%@ taglib prefix="s" uri="/struts-tags"%>

<s:iterator value="businessRules">
	<s:url id="openBusinessRuleDetailUrl" action="businessRuleDetail!open">
		<s:param name="businessRuleId" value="id" />
	</s:url>

	<s:a href="%{openBusinessRuleDetailUrl}"
		targets="businessRuleDetailDiv">
		<s:property value="businessRuleTemplate.name" /> [ <s:property value="place.name" /> ]
		<br>
	</s:a>
</s:iterator>
