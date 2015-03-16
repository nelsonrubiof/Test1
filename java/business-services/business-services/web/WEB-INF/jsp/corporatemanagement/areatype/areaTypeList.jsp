<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'areaTypeList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.name')}"/>
<display:table name="areaTypes" id="at" pagesize="30" requestURI="corporatemanagement/areaType!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column title="${title}" sortProperty="name" sortable="true">
                <s:url id="editAreaType" action="areaType" method="editAreaType" namespace="/corporatemanagement">
                    <s:param name="areaType.id" value="id"/>
                </s:url>
                <s:a href="%{editAreaType}" showLoadingText="false" indicator="waitDiv" targets="areaTypeTabDiv" cssStyle="color:blue;">
                    <s:text name="%{name}"/>
                </s:a>
            </display:column> 
            <display:column titleKey="label.description" property="description" sortable="true"/>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'areaTypeId', 'deleteAreaTypeBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
        </s:push>
    </s:if>
</display:table>
<s:form id="deleteATForm" action="areaType" cssStyle="display:none;">
    <s:hidden name="areaType.id" id="areaTypeId"/>
    <s:url id="deleteAreaType" namespace="/corporatemanagement" action="areaType" method="deleteAreaType"/>
    <s:submit href="%{deleteAreaType}" targets="areaTypeList" id="deleteAreaTypeBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteATForm"/>
</s:form>