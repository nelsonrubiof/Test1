<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterMetric" namespace="/businessrulesmanagement" action="metric!list"/>
<s:url id="listMetric" namespace="/businessrulesmanagement" action="metric!readyList"/>
<s:url id="newMetric" namespace="/businessrulesmanagement" action="metric!newMetric"/>
<s:url id="showAreaFilter" namespace="/businessrulesmanagement" action="metric!showAreaFilter"/>
<s:form name="findMetricForm" id="findMetricForm" action="metric" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:select key="label.areaType" name="metric.area.areaType.id" list="areaTypes" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
            </table>
        </td>
        <td>
            <table>
                <s:select key="label.store" name="metric.store.id" list="stores" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}" onchange="publishTopic('/refreshAreaFilter');" required="true"/>
            </table>
        </td>
        <td>
                <s:div href="%{showAreaFilter}" id="filterAreaDiv" listenTopics="/refreshAreaFilter" formId="findMetricForm" theme="ajax" showLoadingText="false"/>
        </td>
        <td>
            <table>
                <s:select key="label.metricTemplate" name="metric.metricTemplate.id" list="metricTemplates" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteAll')}" />
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterMetric}" showLoadingText="false" formId="findMetricForm" indicator="waitDiv" targets="metricList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    <tr>
        <td colspan="10">
            <s:div id="metricList" href="%{listMetric}" indicator="waitDiv" showLoadingText="false" cssStyle="width:100%;"/>
        </td>
    </tr>
</s:form>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newMetric}" showLoadingText="false" indicator="waitDiv" targets="metricTabDiv" >
    <span><s:text name="label.add"/></span>
</s:a>
<s:form id="deleteMetricForm" action="metric" cssStyle="display:none;">
    <s:hidden name="metric.id" id="metricId"/>
    <s:url id="deleteMetric" namespace="/businessrulesmanagement" action="metric" method="deleteMetric"/>
    <s:submit href="%{deleteMetric}" targets="metricList" id="deleteMetricBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteMetricForm"/>
</s:form>