<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'operatorQueueListDiv'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.name')}"/>
<display:table name="operatorQueues" id="at"
               requestURI="operatorqueuemanagement/operatorQueueManagement!readyList.action" 
               sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column title="${title}" sortProperty="name" sortable="true">
                <s:url id="editOperatorQueue" action="operatorQueueManagement" method="editOperatorQueue"
                       namespace="/operatorqueuemanagement">
                    <s:param name="operatorQueue.id" value="id"/>
                </s:url>
                <s:a href="%{editOperatorQueue}" showLoadingText="false" indicator="waitDiv" targets="principal" cssStyle="color:blue;">
                    <s:text name="%{name}"/>
                </s:a>
            </display:column>
            <display:column titleKey="label.creationDate" property="creationDate" sortable="true"/>
            <display:column titleKey="label.modifiedDate" property="modifiedDate" sortable="true"/>
            <display:column>
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'operatorQueueId', 'deleteOperatorQueueBtn');">
                    <s:text name="label.delete"/>
                </s:a>
            </display:column>
        </s:push>
    </s:if>
</display:table>
<s:form id="deleteOperatorQueueForm" action="operatorQueueManagement" cssStyle="display:none;">
    <s:hidden name="operatorQueue.id" id="operatorQueueId"/>
    <s:url id="deleteOperatorQueue" namespace="/operatorqueuemanagement" action="operatorQueueManagement" method="deleteQueue"/>
    <s:submit href="%{deleteOperatorQueue}" targets="operatorQueueListDiv" id="deleteOperatorQueueBtn" showLoadingText="false" 
              indicator="waitDiv" cssStyle="display:none;" formId="deleteOperatorQueueForm"/>
</s:form>