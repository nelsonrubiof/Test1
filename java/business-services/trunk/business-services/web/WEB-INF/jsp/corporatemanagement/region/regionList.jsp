<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'regionList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.name')}"/>
<display:table name="regions" id="at" pagesize="30" requestURI="corporatemanagement/region!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column title="${title}" sortProperty="name" sortable="true">
                <s:url id="editRegion" action="region" method="editRegion" namespace="/corporatemanagement">
                    <s:param name="region.id" value="id"/>
                </s:url>
                <s:a href="%{editRegion}" showLoadingText="false" indicator="waitDiv" targets="regionTabDiv" cssStyle="color:blue;">
                    <s:text name="%{name}"/>
                </s:a>
            </display:column> 
            <display:column titleKey="label.description" property="description" sortable="true"/>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'regionId', 'deleteRegionBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
        </s:push>
    </s:if>
</display:table>
<s:form id="deleteRegionForm" action="region" cssStyle="display:none;">
    <s:hidden name="region.id" id="regionId"/>
    <s:url id="deleteRegion" namespace="/corporatemanagement" action="region" method="deleteRegion"/>
    <s:submit href="%{deleteRegion}" targets="regionList" id="deleteRegionBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteRegionForm"/>
</s:form>