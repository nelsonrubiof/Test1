<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror />
<s:fielderror />
<s:set name="target" value="%{'summaryTabDiv'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>

<h5>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label key="label.summary"  name="corporate"/></h5>
<display:table name="summary" id="s" requestURI="queuemanagement/summary!readyList.action" sort="list"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.s != null">
        <s:push value="#attr.s">
            <display:column titleKey="label.store" property="store" sortable="true"/>
            <display:column titleKey="label.state" property="state" sortable="true"/>
            <display:column titleKey="label.date" property="date" sortable="true" format="{0,date,dd-MM-yyyy}"/>
            <display:column titleKey="label.count" property="count" sortable="true"/>
        </s:push>
    </s:if>        
</display:table>
    