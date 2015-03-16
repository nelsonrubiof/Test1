<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'evidenceRequestListDIV'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<display:table name="evidenceRequests" id="ers" pagesize="50" requestURI="corporatemanagement/stores!gotoExtractionPlanResult.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.ers != null">
        <s:push value="#attr.ers">
            <display:column property="evidenceProvider.store.description" titleKey="label.store" sortable="true" format="{0, time, HH:mm}"/>
            <display:column property="evidenceTime" titleKey="label.evidenceTime" sortable="true" format="{0, time, HH:mm}"/>
            <display:column property="metric.description" titleKey="label.metric" sortable="true"/>
            <display:column property="evidenceProvider.description" titleKey="label.evidenceProvider" sortable="true"/>
        </s:push>
    </s:if>
</display:table>