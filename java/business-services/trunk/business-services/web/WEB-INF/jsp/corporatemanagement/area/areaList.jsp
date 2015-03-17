<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'areaList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.name')}"/>
<s:set name="storeTitle" value="%{getText('label.store')}"/>
<display:table name="areas" id="at" pagesize="30" requestURI="corporatemanagement/areas!readyList.action" 
               sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column title="${title}" sortProperty="name" sortable="true">
                <s:url id="editArea" action="areas" method="editArea" namespace="/corporatemanagement">
                    <s:param name="area.id" value="id"/>
                </s:url>
                <s:a href="%{editArea}" showLoadingText="false" indicator="waitDiv" targets="areasTabDiv" cssStyle="color:blue;">
                    <s:text name="%{name}"/>
                </s:a>
            </display:column> 
            <display:column titleKey="label.description" property="description" sortable="true"/>
            <display:column title="${storeTitle}" property="store.description" sortable="true"/>
            <display:column titleKey="label.areaType" property="areaType.description" sortable="true"/>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'areaId', 'deleteAreaBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
        </s:push>
    </s:if>
</display:table>
<s:form id="deleteAForm" action="areas" cssStyle="display:none;">
    <s:hidden name="area.id" id="areaId"/>
    <s:url id="deleteArea" namespace="/corporatemanagement" action="areas" method="deleteArea"/>
    <s:submit href="%{deleteArea}" targets="areaList" id="deleteAreaBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteAForm"/>
</s:form>