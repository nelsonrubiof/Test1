<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="metricSave" namespace="/businessrulesmanagement" action="metric!saveMetric"/>
<s:url id="metricCancel" namespace="/businessrulesmanagement" action="metric!cancel"/>
<s:url id="showArea" namespace="/businessrulesmanagement" action="metric!showArea"/>
<s:url id="showERs" namespace="/businessrulesmanagement" action="metric!showErList"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="label.metric"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="metric!saveMetric" id="saveMetricForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="metric.id"/>
    <s:textfield key="label.description" name="metric.description" required="true"/>
    <s:select key="label.metricTemplate" name="metric.metricTemplate.id" list="metricTemplates" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
    <s:select key="label.store" name="metric.store.id" list="stores" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true" onchange="refresh('refreshAreaBtn');"/>
    <s:select key="label.area" name="metric.area.id" list="areas" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
    <tr>
        <td align="center" colspan="2">
            <s:div id="erDiv" href="%{showERs}" showLoadingText="false" indicator="waitDiv" cssStyle="text-align:center;"/>
        </td>
    </tr>
    <tr>        
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{metricCancel}" showLoadingText="false" indicator="waitDiv" targets="metricTabDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{metricSave}" showLoadingText="false" formId="saveMetricForm" indicator="waitDiv" targets="metricTabDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    </s:form>
<s:submit href="%{showArea}" showLoadingText="false" formId="saveMetricForm" indicator="waitDiv" targets="metricTabDiv" id="refreshAreaBtn" cssStyle="display:none"/>
    