<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:url id="stAddMetricTemplate" namespace="/templatemanagement" action="situationTemplate!addMetricTemplate" />
<s:url id="stRemoveMetricTemplate" namespace="/templatemanagement" action="situationTemplate!removeMetricTemplate" />
<table width="100%" style="text-align:center;" align="center">
    <tr>
        <td align="center" width="40%">
            <table><s:select name="metricTemplateAssigned" list="assignedMetricTemplate" key="label.metrictemplate.selected" labelposition="top" listKey="id" listValue="description" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td>
        <td align="center" width="20%">
            <table>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{stAddMetricTemplate}" indicator="waitDiv" targets="stDetailDiv" formId="saveSTForm">
                            <span><s:text name="label.add"/></span>
                        </s:a>
                    </td>
                </tr>                
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{stRemoveMetricTemplate}" indicator="waitDiv" targets="stDetailDiv" formId="saveSTForm">
                            <span><s:text name="label.remove"/></span>
                        </s:a>
                    </td>
                </tr>
            </table>
        </td>
        <td align="center" width="40%">
            <table><s:select name="metricTemplateNotAssigned" list="metricTemplates" key="label.metrictemplate.noselected" labelposition="top" listKey="id" listValue="description" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td>
    </tr>
</table>