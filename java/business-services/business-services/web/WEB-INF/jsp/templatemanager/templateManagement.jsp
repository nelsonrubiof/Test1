<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="metricTemplate" namespace="/templatemanagement" action="metricTemplate"/>
<s:url id="situationTemplate" namespace="/templatemanagement" action="situationTemplate"/>
<s:url id="product" namespace="/templatemanagement" action="product"/>

<h1><s:text name="tab.templateManager" /></h1>

<s:tabbedPanel id="templateTabs" doLayout="false" cssStyle="width:100%;">
    <s:div id="productDiv" key="tab.templateManager.product" href="%{product}"/>
    <s:div id="metricDiv" key="tab.templateManager.metricTemplate" href="%{metricTemplate}"/>
    <s:div id="situationDiv" key="tab.templateManager.situationTemplate" href="%{situationTemplate}"/>
</s:tabbedPanel>