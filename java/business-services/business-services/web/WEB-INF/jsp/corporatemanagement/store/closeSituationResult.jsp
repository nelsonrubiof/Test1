<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'situationListDIV'}" scope="request"/>
<s:set name="formId" value="%{'closeSituationForm'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>

<s:hidden name="pageResumenCloseSituation"/>    
<s:url id="showAllResume" namespace="/corporatemanagement" action="stores!showAllResumenCloseSituation"/>
&nbsp;&nbsp;&nbsp;<s:a href="%{showAllResume}" formId="closeSituationForm" targets="situationListDIV" showLoadingText="false" indicator="waitDiv">
    <s:if test="%{pageResumenCloseSituation == '20'}">
        <s:text name="display.showAll"/>
    </s:if>
    <s:else>
        <s:text name="display.showPaginated"/>
    </s:else>
</s:a>


<display:table name="resumenStore" id="rs" pagesize="${pageResumenCloseSituation}" requestURI="corporatemanagement/stores!goToCloseSituationResult.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.rs != null">
        <s:push value="#attr.rs">
            <display:column property="description" titleKey="label.store" sortable="true" />
            <display:column property="numbersResult" titleKey="label.resumeStore"/>
        </s:push>
    </s:if>
</display:table>

<s:hidden name="pagePendingsCloseSituation"/>    
<s:url id="showAll" namespace="/corporatemanagement" action="stores!showAllCloseSituation"/>
&nbsp;&nbsp;&nbsp;<s:a href="%{showAll}" formId="closeSituationForm" targets="situationListDIV" showLoadingText="false" indicator="waitDiv">
    <s:if test="%{pagePendingsCloseSituation == '20'}">
        <s:text name="display.showAll"/>
    </s:if>
    <s:else>
        <s:text name="display.showPaginated"/>
    </s:else>
</s:a>


<display:table name="observedSituations" id="os" pagesize="${pagePendingsCloseSituation}"  requestURI="corporatemanagement/stores!goToCloseSituationResult.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.os != null">
        <s:push value="#attr.os">
            <display:column property="observedMetrics[0].metric.store.description" titleKey="label.store" sortable="true" />
            <display:column property="situation.description" titleKey="label.situation"/>
        </s:push>
    </s:if>
</display:table>
