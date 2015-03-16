<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'metricList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.description')}"/>
<s:hidden name="pageSize"/>
<s:url id="showAll" namespace="/businessrulesmanagement" action="metric!showAll"/>
&nbsp;&nbsp;&nbsp;<s:a href="%{showAll}" formId="findMetricForm" targets="metricList" showLoadingText="false" indicator="waitDiv" cssStyle="color:blue;">
    <s:if test="%{pageSize == '30'}">
        <s:text name="display.showAll"/>
    </s:if>
    <s:else>
        <s:text name="display.showPaginated"/>
    </s:else>
</s:a>
<display:table name="metrics" id="at" pagesize="${pageSize}"  requestURI="businessrulesmanagement/metric!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column title="${title}" sortProperty="description" sortable="true">
                <s:url id="editMetric" action="metric" method="editMetric" namespace="/businessrulesmanagement">
                    <s:param name="metric.id" value="%{id}"/>
                </s:url>
                <s:a href="%{editMetric}" showLoadingText="false" indicator="waitDiv" targets="metricTabDiv" cssStyle="color:blue;">
                    <s:text name="%{description}"/>
                </s:a>
            </display:column> 
            <display:column titleKey="label.metricTemplate" property="metricTemplate.name" sortable="true"/>
            <display:column titleKey="label.store" property="store.name" sortable="true"/>
            <display:column titleKey="label.area" property="area.name" sortable="true"/>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'metricId', 'deleteMetricBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
        </s:push>
    </s:if>
</display:table>