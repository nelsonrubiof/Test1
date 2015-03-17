<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<s:actionerror />
<s:fielderror />
<br>
<s:set name="target" value="%{'deletedListTable'}" scope="request"/>
<s:set name="formId" value="%{'deletedForm'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
    <s:hidden name="pageDeleted"/>
    &nbsp;&nbsp;&nbsp;
    <s:url id="queueManagementShowAll" namespace="/queuemanagement" action="deletedQueueManagement!showAll"/>
    <s:a href="%{queueManagementShowAll}" formId="deletedForm" targets="deletedListTable" showLoadingText="false" indicator="waitDiv">
        <s:if test="%{pageDeleted == '30'}">
            <s:text name="display.showAll"/>           
        </s:if>
        <s:else>
            <s:text name="display.showPaginated"/>
        </s:else>
    </s:a>
    <s:url value="%{pageContext.request.contextPath}/img/change.PNG" id="img"/>
    <display:table name="pendingEvaluationDTOs" id="brc" pagesize="${pageDeleted}" requestURI="queuemanagement/deletedQueueManagement!readyList.action" sort="list" defaultsort="2" export="true"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.brc != null">
        <s:push value="#attr.brc">
            <display:column title="<img src='${img}' onclick='changeSelection(\"checks\")'/>" media="html">
                <s:checkbox  name="checks" fieldValue="%{pendingEvaluationId}" theme="simple" disabled="%{!priorityChangeable}" value="%{checked}" />
            </display:column>                        
            <display:column titleKey="label.position" property="priority" sortable="true"/>
            <display:column titleKey="label.product" property="product" sortable="true"/>
            <display:column titleKey="label.store" property="store" sortable="true"/>
            <display:column titleKey="label.area" property="area" sortable="true"/>
            <display:column titleKey="label.metric" property="description" sortable="true"/>
            <display:column titleKey="label.metricType"  property="type" sortable="true"/>
            <display:column titleKey="label.date" property="date" format="{0,date,dd-MM-yyyy HH:mm}" sortable="true"/>
            <display:column titleKey="label.situationTemplate" property="situationTemplate" sortable="true"/>
        </s:push>
    </s:if>        
    </display:table>

