<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url action="formulaDefinition" namespace="/wizards" id="formulaDefinitionURL"/>
<s:actionerror/>
<s:fielderror/>
<br>
    <s:div id="formulaDefinitionDiv" key="tab.formula.definition" refreshOnShow="false" href="%{formulaDefinitionURL}" indicator="waitDiv" showLoadingText="false"/>
