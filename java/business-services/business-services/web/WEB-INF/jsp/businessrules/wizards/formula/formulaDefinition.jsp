<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url action="formulaDefinition" namespace="/wizards" method="readyList" id="readyListURL"/>
<s:url action="formulaDefinition" namespace="/wizards" method="list" id="listURL"/>
<s:url action="formulaDefinition" namespace="/wizards" method="addFormula" id="addFormulaURL"/>
<s:url action="formulaDefinition" namespace="/wizards" method="refreshCompliantTypeFilter" id="refreshCompliantTypeURL"/>
<s:actionerror/>
<s:fielderror/>
<s:form name="formulaForm" id="formulaForm" action="formulaDefinition" namespace="/wizards" cssStyle="width:100%;">
    <tr><td><table>
                <s:select name="sId"
                          list="stores" key="label.store" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
            </table></td>
        <td><table>
                <s:select name="stId"
                          list="situationTemplates" key="label.situationTemplate" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
            </table></td>
        <td><table>
                <s:textfield name="formula.description" key="label.description" />
            </table></td>
    </tr>
    <tr>
        <td>
            <table>
                <s:select name="formulaType" list="formulaTypes" key="label.formulaType" listKey="name" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" onchange="refresh('refreshCompliantTypesBtn');"/>
            </table>
        </td>
        <td>
            <table>
                <s:select name="compliantType" list="compliantTypes" key="label.compliantType" listKey="name" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
            </table>
        </td>
        <td><table>
                <s:a href="%{listURL}" showLoadingText="false" indicator="waitDiv" targets="formulaListDiv" cssClass="button" formId="formulaForm">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table></td>
    </tr>
    <tr>
        <td colspan="3">
            <table width="100%">
                <s:div href="%{readyListURL}" showLoadingText="false" indicator="waitDiv" id="formulaListDiv" listenTopics="/refreshEPC" cssStyle="width:100%"/>
            </table>
        </td>
    </tr>
</s:form>
<s:a href="%{addFormulaURL}" showLoadingText="false" indicator="waitDiv" targets="formulaDefinitionDiv" cssClass="button" formId="formulaForm">
    <span><s:text name="label.add"/></span>
</s:a>
<s:form id="deleteFormulaForm" action="formulaDefinition" cssStyle="display:none;">
    <s:hidden name="formula.id" id="formulaId"/>
    <s:url id="deleteFormulaURL" namespace="/wizards" action="formulaDefinition" method="deleteFormula"/>
    <s:submit href="%{deleteFormulaURL}" targets="formulaListDiv" id="deleteFormulaBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteFormulaForm"/>
</s:form>
<s:submit href="%{refreshCompliantTypeURL}" showLoadingText="false" formId="formulaForm" indicator="waitDiv" targets="formulaDefinitionDiv" id="refreshCompliantTypesBtn" cssStyle="display:none"/>