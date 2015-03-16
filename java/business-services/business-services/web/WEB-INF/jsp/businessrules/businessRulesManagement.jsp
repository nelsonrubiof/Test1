<%@ taglib prefix="s" uri="/struts-tags"%>

<s:url id="wizardUrl" namespace="/" action="wizards"/>

<h1><s:text name="label.situation"/></h1>
<br>
<s:tabbedPanel id="mainTabs" doLayout="false" cssStyle="width:100%" >
    <s:div id="wizardsTabDiv" key="tab.businessRuleManagement.wizards" refreshOnShow="false" href="%{wizardUrl}" indicator="waitDiv" showLoadingText="false"/>
</s:tabbedPanel>
