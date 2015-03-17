<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="saveSituationTemplate" namespace="/templatemanagement" action="situationTemplate!saveSituationTemplateEvaluator"/>
<s:url id="situationTemplateCancel" namespace="/templatemanagement" action="situationTemplate!cancel"/>
<h5 align="center">
    <s:text name="label.changeEvaluator"/>
    &nbsp;<s:text name="tab.templateManager.situationTemplate"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="situationTemplate!saveSituationTemplate" id="saveSTForm" namespace="/templatemanagement" cssStyle="width:100%;border:1px">
    <s:hidden name="editable"/>
    <s:hidden name="situationTemplate.id"/>
    <s:hidden name="situationTemplate.active"/>
    <s:textfield name="situationTemplate.name" key="label.name" readonly="true"/>
    <s:select key="label.product" name="situationTemplate.product.id" list="products" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" disabled="true"/>
    <s:select key="label.area" name="situationTemplate.areaType.id" list="areaTypes" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" disabled="true"/>
    <s:select key="label.evidenceSpringBeanEvaluatorName" name="situationTemplate.evidenceSpringBeanEvaluatorName" list="evidenceEvaluators" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
    <tr>
        <td>
            <s:a href="%{situationTemplateCancel}" key="label.cancel" targets="situationDiv" cssClass="button">
                <span><s:text name="label.cancel"/></span>
            </s:a>
        </td>
        <td style="text-align:right">
            <s:a key="label.save" formId="saveSTForm" href="%{saveSituationTemplate}" targets="situationDiv" cssClass="button" indicator="waitDiv">
                <span><s:text name="label.save"/></span>
            </s:a>
        </td>
    </tr>
</s:form>
<br>

