<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net" %>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'userListDiv'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.username')}"/>
<display:table name="users" id="usr" pagesize="30" requestURI="securitymanagement/userSecurityManagement!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.usr != null">
        <s:push value="#attr.usr">
            <display:column title="${title}" sortProperty="name" sortable="true">
                <s:url id="editUser" action="userSecurityManagement" method="editUser" namespace="/securitymanagement">
                    <s:param name="periscopeUser.id" value="id"/>
                </s:url>
                <s:a href="%{editUser}" showLoadingText="false" indicator="waitDiv" targets="usersDiv" cssStyle="color:blue;">
                    <s:text name="%{name}"/>
                </s:a>
            </display:column>            
            <display:column titleKey="label.userState" property="userState.name" sortable="true"/>
            <display:column titleKey="label.fullName" property="fullName" sortable="true"/>
            <display:column titleKey="label.mainCorporate" property="mainCorporate.name" sortable="true"/>
            <display:column titleKey="label.jobPosition" property="jobPosition" sortable="true"/>
            <display:column titleKey="label.creationDate" property="startDate" sortable="true" format="{0, date, dd-MM-yyyy HH:mm}"/>
            <display:column>
                <s:url id="deleteUser" action="userSecurityManagement" method="deleteUser" namespace="/securitymanagement">
                    <s:param name="periscopeUser.id" value="id"/>
                </s:url>
                <s:a href="%{deleteUser}" showLoadingText="false" indicator="waitDiv" targets="userListDiv" cssStyle="color:blue;">
                    <s:text name="label.delete"/>
                </s:a>
            </display:column>
        </s:push>
    </s:if>        
</display:table>