<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url action="baseWizard" namespace="/wizards" id="baseWizardURL"/>
<s:url action="reProcessWizard" namespace="/wizards" id="reProcessWizardURL"/>
<s:url action="formulaWizard" namespace="/wizards" id="formulaWizardURL"/>
<s:url action="transferWizard" namespace="/wizards" id="transferWizardURL"/>
<br>
<s:tabbedPanel id="wizardsTabs" doLayout="false" cssStyle="width:100%" >
    <s:div id="formulaDiv" key="tab.businessRuleManagement.formulaWizard" refreshOnShow="false" indicator="waitDiv" showLoadingText="false" href="%{formulaWizardURL}"/>
    <s:div id="reprocessDiv" key="tab.businessRuleManagement.reprocessWizard" refreshOnShow="false" indicator="waitDiv" showLoadingText="false" href="%{reProcessWizardURL}"/>
    <s:div id="transferDiv" key="tab.businessRuleManagement.transferWizard" refreshOnShow="false" indicator="waitDiv" showLoadingText="false" href="%{transferWizardURL}"/>
</s:tabbedPanel>
