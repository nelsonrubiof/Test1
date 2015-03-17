<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterMetricTemplate" namespace="/templatemanagement" action="metricTemplate!list"/>
<s:url id="listMetricTemplate" namespace="/templatemanagement" action="metricTemplate!readyList"/>
<s:url id="newMetricTemplate" namespace="/templatemanagement" action="metricTemplate!newMetricTemplate"/>
<s:form name="findMetricTemplateForm" id="findMetricTemplateForm" action="metricTemplate" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:textfield name="metricTemplate.name" key="label.name" />
            </table>
        </td>
        <td>
            <table>
                <s:textfield name="metricTemplate.description" key="label.description" />
            </table>
        </td>
        <td>
            <table>
                <s:select name="metricTypeElement" key="label.metricType" list="metricTypes" listKey="name" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
            </table>
        </td>
        <td>
            <table>
                <s:select name="evidenceTypeElement" key="label.evidenceType" list="evidenceTypes" listKey="name" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" />
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterMetricTemplate}" showLoadingText="false" formId="findMetricTemplateForm" indicator="waitDiv" targets="metricTemplateList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="metricTemplateList" href="%{listMetricTemplate}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newMetricTemplate}" showLoadingText="false" indicator="waitDiv" targets="metricDiv" >
    <span><s:text name="label.add"/></span>
</s:a>