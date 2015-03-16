<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url action="formulaDefinition" namespace="/wizards" method="refreshCompliantType" id="refreshCompliantTypeURL"/>
<s:url action="formulaDefinition" namespace="/wizards" method="addVariable" id="addVariableURL"/>
<s:url action="formulaDefinition" namespace="/wizards" method="testFormula" id="testFormulaURL"/>
<s:url action="formulaDefinition" namespace="/wizards" method="testDenominatorFormula" id="testDenominatorFormulaURL"/>
<s:url action="formulaDefinition" namespace="/wizards" method="saveFormula" id="saveFormulaURL"/>
<s:url action="formulaDefinition" namespace="/wizards" id="cancelFormulaURL"/>
<s:actionerror/>
<s:fielderror/>
<s:form name="formulaAEForm" id="formulaAEForm" action="formulaDefinition" namespace="/wizards" cssStyle="width:100%;">
    <s:hidden name="formula.id"/>
    <s:hidden name="indicator.id"/>
    <s:hidden name="editable"/>
    <tr>
        <td>
            <table>
                <tr>
                    <td>
                        <table>
                            <s:textfield name="formula.description" key="label.description" required="true"/>
                        </table>
                    </td>
                    <td>
                        <table>
                            <s:select name="formulaType" list="formulaTypes" key="label.formulaType" listKey="name" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true" onchange="refresh('refreshCompliantTypesBtn');"/>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table>
                            <s:select name="compliantType" list="compliantTypes" key="label.compliantType" listKey="name" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <td>
                                    <table>
                                        <s:label required="true" key="label.observation"/>
                                    </table>
                                </td>
                                <td>
                                    <s:textarea cols="30" rows="10" name="formula.observations" theme="simple"/>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table>
                            <s:select name="situationTemplateIds" key="label.situationTemplate" list="situationTemplates" listKey="id" listValue="name" multiple="true" size="10" cssStyle="width:300px;" required="true"/>
                        </table>
                    </td>
                    <td>
                        <table>
                            <s:select name="storeIds" key="label.store" list="stores" listKey="id" listValue="description" multiple="true" size="10" cssStyle="width:300px;" required="true"/>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td>
                        <table>
                            <tr>
                                <td>
                                    <table>
                                        <s:textfield name="var" key="label.variableName"/>
                                    </table>
                                </td>
                                <td>
                                    <table>
                                        <s:a href="%{addVariableURL}" showLoadingText="false" indicator="waitDiv" targets="formulaDefinitionDiv" cssClass="button" formId="formulaAEForm">
                                            <span><s:text name="label.add"/></span>
                                        </s:a>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                    <td>
                        <table>
                            <tr>
                                <th><s:text name="label.variableName"/></th>
                                <th><s:text name="label.variableValueForTest"/></th>
                                <th>&nbsp;</th>
                            </tr>
                            <s:iterator value="%{variables}" id="vars">
                                <tr>
                                    <td>
                                        <s:property value="vars"/>
                                    </td>
                                    <td>
                                        <input type="text" name="${vars}_value"/>
                                    </td>
                                    <td>
                                        <s:url action="formulaDefinition" namespace="/wizards" method="deleteVariable" id="deleteVariableURL">
                                            <s:param name="varDelete" value="vars"/>
                                        </s:url>
                                        <s:a href="%{deleteVariableURL}" showLoadingText="false" indicator="waitDiv" targets="formulaDefinitionDiv" formId="formulaAEForm" cssStyle="color:blue;">
                                            <s:text name="label.delete"/>
                                        </s:a>
                                    </td>
                                </tr>
                            </s:iterator>
                        </table>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <table>
                            <tr>
                                <td>
                                    <s:if test="formulaType=='INDICATOR'">
                                        <s:textfield name="formula.formula" key="label.indicatorFormulaDefinition" required="true" size="100"/>
                                    </s:if>
                                    <s:else>
                                        <s:textfield name="formula.formula" key="label.compliantFormulaDefinition" required="true" size="100"/>
                                    </s:else>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td>
                                    <table>
                                        <s:a href="%{testFormulaURL}" showLoadingText="false" indicator="waitDiv" targets="formulaDefinitionDiv" cssClass="button" formId="formulaAEForm">
                                            <span><s:text name="label.evaluate"/></span>
                                        </s:a>
                                    </table>
                                </td>
                                <td>
                                    <table>
                                        <s:text name="%{formulaResult}"/>
                                    </table>
                                </td>
                            </tr>
                        </table>
                    </td>
                </tr>
                <s:if test="formulaType=='INDICATOR'">
                    <tr id="denominator">
                        <td colspan="2">
                            <table>
                                <tr>
                                    <td>
                                        <s:textfield name="formula.denominator" key="label.denominator" required="true" size="100"/>
                                    </td>
                                    <td>
                                        &nbsp;
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <table>
                                            <s:a href="%{testDenominatorFormulaURL}" showLoadingText="false" indicator="waitDiv" targets="formulaDefinitionDiv" cssClass="button" formId="formulaAEForm">
                                                <span><s:text name="label.evaluate"/></span>
                                            </s:a>
                                        </table>
                                    </td>
                                    <td>
                                        <table>
                                            <s:text name="%{denominatorResult}"/>
                                        </table>
                                    </td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table>
                                <s:textfield name="indicator.name" key="label.indicatorName" required="true" size="100"/>
                            </table>
                        </td>
                        <td>
                            &nbsp;
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table>
                                <s:select name="indicator.initialTime" key="label.initialTime" list="availableTimes" required="true" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
                            </table>
                        </td>
                        <td>
                            <table>
                                <s:select name="indicator.endingTime" key="label.endingTime" list="availableTimes" required="true" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <table>
                                <s:textfield name="indicator.labelX" key="label.labelX" required="true"/>
                            </table>
                        </td>
                        <td>
                            <table>
                                <s:textfield name="indicator.labelY" key="label.labelY" required="true"/>
                            </table>
                        </td>
                    </tr>
                </s:if>
                <s:if test="formulaType=='COMPLIANCE'">
                    <tr>
                        <td>
                            <table>
                                <s:textfield name="target" key="label.target" required="true"/>
                            </table>
                        </td>
                        <td>
                            <table>
                                <s:textfield name="standard" key="label.standard" required="true"/>
                            </table>
                        </td>
                    </tr>
                </s:if>
                <tr>
                    <td>
                        <table>
                            <s:a href="%{saveFormulaURL}" showLoadingText="false" indicator="waitDiv" targets="formulaDefinitionDiv" cssClass="button" formId="formulaAEForm">
                                <span><s:text name="label.save"/></span>
                            </s:a>
                        </table>
                    </td>
                    <td>
                        <table>
                            <s:a href="%{cancelFormulaURL}" showLoadingText="false" indicator="waitDiv" targets="formulaDefinitionDiv" cssClass="button">
                                <span><s:text name="label.cancel"/></span>
                            </s:a>
                        </table>
                    </td>
                </tr>
            </table>

        </td>
    </tr>
</s:form>
<s:submit href="%{refreshCompliantTypeURL}" showLoadingText="false" formId="formulaAEForm" indicator="waitDiv" targets="formulaDefinitionDiv" id="refreshCompliantTypesBtn" cssStyle="display:none"/>