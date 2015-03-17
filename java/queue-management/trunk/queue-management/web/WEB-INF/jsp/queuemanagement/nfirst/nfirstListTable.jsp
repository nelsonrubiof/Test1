<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror />
<s:fielderror />
<s:set name="target" value="%{'nfirstListTable'}" scope="request"/>
<s:set name="formId" value="%{'nfirstForm'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:hidden name="pages"/>
<s:url id="showAll" namespace="/queuemanagement" action="nFirst!showAll"/>
&nbsp;&nbsp;&nbsp;<s:a href="%{showAll}" formId="nfirstForm" targets="nfirstListTable" showLoadingText="false" indicator="waitDiv">
    <s:if test="%{pages == '30'}">
        <s:text name="display.showAll"/>
    </s:if>
    <s:else>
        <s:text name="display.showPaginated"/>
    </s:else>
</s:a>
<display:table name="pendingEvaluationDTOs" id="brc" pagesize="${pages}" requestURI="queuemanagement/nFirst!readyList.action" sort="list" defaultsort="1" export="true"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.brc != null">
        <s:push value="#attr.brc">
            <display:column titleKey="label.position" property="priority" sortable="true"/>
            <display:column titleKey="label.product" property="product" sortable="true"/>
            <display:column titleKey="label.store" property="store" sortable="true"/>
            <display:column titleKey="label.area" property="area" sortable="true"/>
            <display:column titleKey="label.situationTemplate" property="situationTemplate" sortable="true"/>
            
            <display:column titleKey="label.date" property="date" format="{0,date,dd-MM-yyyy HH:mm}" sortable="true"/>
        </s:push>
    </s:if>        
</display:table>
    