<%@ taglib prefix="s" uri="/struts-tags"%>

<s:iterator value="brTemplates">
	<s:url id="brTemplatepDetailUrl" action="brTemplateDetail!open">
		<s:param name="brTemplateId" value="id" />
	</s:url>

	<s:a href="%{brTemplatepDetailUrl}" targets="brTemplateDetailDiv">
		<s:property value="name" />
		<br>
	</s:a>
</s:iterator>
<br>

<s:url id="newBrTemplateUrl" action="brTemplateDetail!openNew" />
<s:a href="%{newBrTemplateUrl}" targets="brTemplateDetailDiv">
			New BR Template
		</s:a>

