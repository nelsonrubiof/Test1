<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:actionmessage/>
<s:set name="target" value="%{'storeAreaGroupListDiv'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.name')}"/>
<display:table name="operatorQueueDetails" id="at"  requestURI="operatorqueuemanagement/operatorQueueManagement!storeAreaReadyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column titleKey="label.store" property="store.description" sortable="true"/>
            <!--display:column titleKey="label.areaType" property="areaType.description" sortable="true"/-->
            <display:column titleKey="label.situationTemplate" property="situationTemplate.name" sortable="true"/>
            <display:column titleKey="label.areaType" property="situationTemplate.areaType.description" sortable="true"/>
            <display:column>
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'operatorQueueDetailId', 'deleteOperatorQueueDetailBtn');">
                    <s:text name="label.delete"/>
                </s:a>
            </display:column>
        </s:push>
    </s:if>
</display:table>
