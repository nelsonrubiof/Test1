<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="saveSituationTemplate" namespace="/templatemanagement" action="situationTemplate!saveSituationTemplate"/>
<s:url id="situationTemplateCancel" namespace="/templatemanagement" action="situationTemplate!cancel"/>
<s:url id="situationTemplateDetail" namespace="/templatemanagement" action="situationTemplate!showMetrics"/>
<s:url id="activateSituationTemplate" namespace="/templatemanagement" action="situationTemplate!activate"/>
<s:url id="deactivateSituationTemplate" namespace="/templatemanagement" action="situationTemplate!deactivate"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="tab.templateManager.situationTemplate"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="situationTemplate!saveSituationTemplate" id="saveSTForm" namespace="/templatemanagement" cssStyle="width:100%;border:1px">
    <s:hidden name="editable"/>
    <s:hidden name="situationTemplate.id"/>
    <s:hidden name="situationTemplate.active"/>
    <s:checkbox name="situationTemplate.live" key="label.live" required="true"  />
    <s:textfield name="situationTemplate.delayInMinutes" key="label.delay"/>
    
    <s:textfield name="situationTemplate.name" key="label.name" required="true"/>
    <s:select key="label.product" name="situationTemplate.product.id" list="products" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
    <s:select key="label.area" name="situationTemplate.areaType.id" list="areaTypes" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
    <s:select key="label.evidenceSpringBeanEvaluatorName" name="situationTemplate.evidenceSpringBeanEvaluatorName" list="evidenceEvaluators" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
    <tr>
        <td align="center" colspan="2"><s:div id="stDetailDiv" href="%{situationTemplateDetail}" showLoadingText="false" indicator="waitDiv" cssStyle="text-align:center;"/></td>
    </tr>
    <tr>
        <td>
            <s:a href="%{situationTemplateCancel}" key="label.cancel" targets="situationDiv" cssClass="button">
                <span><s:text name="label.cancel"/></span>
            </s:a>
        </td>
        <s:if test="%{noSave}">
            <td style="text-align:right">
                <s:if test="%{situationTemplate.active!=null && !situationTemplate.active }">
                    <s:a key="label.activate" formId="saveSTForm" href="%{activateSituationTemplate}" targets="situationDiv" cssClass="button" indicator="waitDiv">
                        <span><s:text name="label.activate"/></span>
                    </s:a>
                </s:if>
                <s:else>
                    <s:a key="label.deactivate" formId="saveSTForm" href="%{deactivateSituationTemplate}" targets="situationDiv" cssClass="button" indicator="waitDiv">
                        <span><s:text name="label.deactivate"/></span>
                    </s:a>
                </s:else>
            </td>
        </s:if>
        <s:else>
            <td style="text-align:right">
                <s:a key="label.save" formId="saveSTForm" href="%{saveSituationTemplate}" targets="situationDiv" cssClass="button" indicator="waitDiv">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </td>   
        </s:else>
    </tr>
</s:form>
<br>

