<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'groupListDiv'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.name')}"/>
<display:table name="rolesGroups" id="rg" pagesize="30" requestURI="securitymanagement/groupSecurityManagement!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.rg != null">
        <s:push value="#attr.rg">
            <display:column title="${title}" sortProperty="name" sortable="true">
                <s:url id="editGroup" action="groupSecurityManagement" method="editGroup" namespace="/securitymanagement">
                    <s:param name="rolesGroup.id" value="id"/>
                </s:url>
                <s:a href="%{editGroup}" showLoadingText="false" indicator="waitDiv" targets="groupsDiv" cssStyle="color:blue;">
                    <s:text name="%{name}"/>
                </s:a>
            </display:column>            
            <display:column titleKey="label.description" property="description" sortable="true"/>
            <display:column>
                <s:url id="deleteGroup" action="groupSecurityManagement" method="deleteGroup" namespace="/securitymanagement">
                    <s:param name="rolesGroup.id" value="id"/>
                </s:url>
                <s:a href="%{deleteGroup}" showLoadingText="false" indicator="waitDiv" targets="groupListDiv" cssStyle="color:blue;">
                    <s:text name="label.delete"/>
                </s:a>
            </display:column>
        </s:push>
    </s:if>        
</display:table>