<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:url id="addMetricToSituation" namespace="/businessrulesmanagement" action="situation" method="addMetricToSituation"/>
<s:url id="removeMetricToSituation" namespace="/businessrulesmanagement" action="situation" method="removeMetricToSituation"/>
<table width="100%" style="text-align:center;" align="center">
    <tr>
        <td align="center" width="40%">
            <table>
                <s:select name="metricAsigned" list="assignedMetrics" key="label.metric.selected" labelposition="top" listKey="id" listValue="description" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/>
            </table>
        </td>
        <td align="center" width="20%">
            <table>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{addMetricToSituation}" indicator="waitDiv" targets="mDiv" formId="saveSituationForm">
                            <span><s:text name="label.add"/></span>
                        </s:a>
                    </td>
                </tr>                
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{removeMetricToSituation}" indicator="waitDiv" targets="mDiv" formId="saveSituationForm">
                            <span><s:text name="label.remove"/></span>
                        </s:a>
                    </td>
                </tr>
            </table>
        </td>
        <td align="center" width="40%">
            <table>                
                <s:select name="noMetricAssigned" list="metrics" key="label.metric.noselected" labelposition="top" listKey="id" listValue="description" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/>
            </table>
        </td>
    </tr>
</table>