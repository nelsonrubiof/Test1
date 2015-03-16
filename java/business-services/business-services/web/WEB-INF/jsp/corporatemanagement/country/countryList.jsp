<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'countryList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.name')}"/>
<display:table name="countries" id="at" pagesize="30" requestURI="corporatemanagement/country!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column title="${title}" sortProperty="name" sortable="true">
                <s:url id="editCountry" action="country" method="editCountry" namespace="/corporatemanagement">
                    <s:param name="country.id" value="id"/>
                </s:url>
                <s:a href="%{editCountry}" showLoadingText="false" indicator="waitDiv" targets="countryTabDiv" cssStyle="color:blue;">
                    <s:text name="%{name}"/>
                </s:a>
            </display:column> 
            <display:column titleKey="label.description" property="description" sortable="true"/>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'countryId', 'deleteCountryBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
        </s:push>
    </s:if>
</display:table>
<s:form id="deleteCountryForm" action="country" cssStyle="display:none;">
    <s:hidden name="country.id" id="countryId"/>
    <s:url id="deleteCountry" namespace="/corporatemanagement" action="country" method="deleteCountry"/>
    <s:submit href="%{deleteCountry}" targets="countryList" id="deleteCountryBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteCountryForm"/>
</s:form>