<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:url id="addErToMetric" namespace="/businessrulesmanagement" action="metric" method="addErToMetric"/>
<s:url id="removeErToMetric" namespace="/businessrulesmanagement" action="metric" method="removeErToMetric"/>
<s:url id="filterER" namespace="/businessrulesmanagement" action="metric" method="filterER"/>
<table width="100%" style="text-align:center;" align="center">
    <tr>
        <td colspan="3" width="100%">
            <table align="center" width="100%">
                <tr>
                    <td>
                        <table>
                            <s:select name="evidenceRequest.evidenceProvider.id" key="label.evidenceProvider" list="evidenceProviderList" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteAll')}"/>
                        </table>
                    </td>
                    <td>
                        <table>
                            <s:select name="evidenceRequest.evidenceProvider.area.id" key="label.area" list="areaList" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" />
                        </table>
                    </td>
                    <td>
                        <table>
                            <s:textfield name="initHour" key="label.initHour" maxlength="8" cssStyle="width:60px"/>
                        </table>
                    </td>
                    <td>
                        <table>
                            <s:textfield name="endHour" key="label.endHour" maxlength="8" cssStyle="width:60px"/>
                        </table>
                    </td>
                    <td>&nbsp;</td>
                    <td align="right">
                        <table align="right" style="text-align:right;">
                            <s:a href="%{filterER}" showLoadingText="false" formId="saveMetricForm" indicator="waitDiv" targets="erDiv" cssClass="button">
                                <span><s:text name="filter.filter"/></span>
                            </s:a>
                        </table>
                    </td>
                </tr>
            </table>
        </td>
    </tr>
    <tr>
        <td align="center" width="40%">
            <table>
                <s:select name="erAsigned" list="assignEvidenceRequests" key="label.evidencerequest.selected" labelposition="top" listKey="id" listValue="formatDisplayName" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/>
            </table>
        </td>
        <td align="center" width="20%">
            <table>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{addErToMetric}" indicator="waitDiv" targets="erDiv" formId="saveMetricForm">
                            <span><s:text name="label.add"/></span>
                        </s:a>
                    </td>
                </tr>                
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{removeErToMetric}" indicator="waitDiv" targets="erDiv" formId="saveMetricForm">
                            <span><s:text name="label.remove"/></span>
                        </s:a>
                    </td>
                </tr>
            </table>
        </td>
        <td align="center" width="40%">
            <table>                
                <s:select name="noErAsigned" list="evidenceRequests" key="label.evidencerequest.noselected" labelposition="top" listKey="id" listValue="formatDisplayName" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/>
            </table>
        </td>
    </tr>
</table>