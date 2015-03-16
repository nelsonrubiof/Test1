<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:actionmessage/>
<s:set name="target" value="%{'storeList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.name')}"/>
<display:table name="stores" id="at"  requestURI="corporatemanagement/stores!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column title="${title}" sortProperty="name" sortable="true">
                <s:url id="editStore" action="stores" method="editStore" namespace="/corporatemanagement">
                    <s:param name="store.id" value="id"/>
                </s:url>
                <s:a href="%{editStore}" showLoadingText="false" indicator="waitDiv" targets="storesTabDiv" cssStyle="color:blue;">
                    <s:text name="%{name}"/>
                </s:a>
            </display:column> 
            <display:column titleKey="label.description" property="description" sortable="true"/>
            <display:column titleKey="label.address" property="address" sortable="true"/>
            <display:column titleKey="label.corporate" property="corporate.name"/>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'storeId', 'deleteStoreBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
            <display:column>
                <s:url action="stores" method="goToExtractionPlanToPast" namespace="/corporatemanagement" id="goToExtractionPlanToPastUrl">
                    <s:param name="store.id" value="%{id}"/>
                </s:url>
                <s:a cssStyle="color:blue;" href="%{goToExtractionPlanToPastUrl}" targets="storesTabDiv" showLoadingText="false" indicator="waitDiv">
                    <s:text name="label.goToExtractionPlanToPast"/>
                </s:a>
                <s:url action="stores" method="goToCloseSituation" namespace="/corporatemanagement" id="goToCloseSituationUrl">
                    <s:param name="store.id" value="%{id}"/>
                </s:url>
                <s:a cssStyle="color:blue;" href="%{goToCloseSituationUrl}" targets="storesTabDiv" showLoadingText="false" indicator="waitDiv">
                    <s:text name="label.goToCloseSituation"/>
                </s:a>
            </display:column>
        </s:push>
    </s:if>
</display:table>
<s:form id="deleteSForm" action="stores" cssStyle="display:none;">
    <s:hidden name="store.id" id="storeId"/>
    <s:url id="deleteStore" namespace="/corporatemanagement" action="stores" method="deleteStore"/>
    <s:submit href="%{deleteStore}" targets="storeList" id="deleteStoreBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteSForm"/>
</s:form>