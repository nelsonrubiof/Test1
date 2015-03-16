<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="metricTemplateSave" namespace="/templatemanagement" action="metricTemplate!saveMetricTemplate"/>
<s:url id="metricTemplateCancel" namespace="/templatemanagement" action="metricTemplate!cancel"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="tab.templateManager.metricTemplate"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="metricTemplate!save" id="saveMTForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="metricTemplate.id"/>
    <s:textfield key="label.name" name="metricTemplate.name" required="true"/>
    <s:textfield key="label.description" name="metricTemplate.description" required="true"/>
    <s:textfield key="label.operatorDescription" name="metricTemplate.operatorDescription"/>
    <s:textfield key="label.evaluationInstruction" name="metricTemplate.evaluationInstruction" required="true"/> 
    <s:select id="metricType" key="label.metricType" name="metricTypeElement" list="metricTypes" listKey="name" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true" onchange="disableYesNoType();"/>
    <s:select key="label.yesNoType" name="yesNoType" list="yesNoTypes" listKey="name" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true" id="yesNoType" disabled="%{disabled}"/>
    <s:select key="label.evidenceType" name="evidenceTypeElement" list="evidenceTypes" listKey="name" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
    <s:select key="label.metricSpringBeanEvaluatorName" name="metricTemplate.metricSpringBeanEvaluatorName" list="metricEvaluators" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
    <s:select key="label.evidenceSpringBeanEvaluatorName" name="metricTemplate.evidenceSpringBeanEvaluatorName" list="evidenceEvaluators" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
    <tr>        
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{metricTemplateCancel}" showLoadingText="false" indicator="waitDiv" targets="metricDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{metricTemplateSave}" showLoadingText="false" formId="saveMTForm" indicator="waitDiv" targets="metricDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    </s:form>
    
    