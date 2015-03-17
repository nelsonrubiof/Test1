<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'evidenceRequestList'}" scope="request"/>
<s:set name="formId" value="%{'findEvidenceRequestForm'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.hour')}"/>
<s:set name="titleDay" value="%{getText('label.days')}"/>
<s:hidden name="pagePendings"/>
<s:url id="showAll" namespace="/businessrulesmanagement" action="evidenceRequest!showAll"/>
&nbsp;&nbsp;&nbsp;<s:a href="%{showAll}" formId="findEvidenceRequestForm" targets="evidenceRequestList" showLoadingText="false" indicator="waitDiv" cssStyle="color:blue;">
    <s:if test="%{pagePendings == '30'}">
        <s:text name="display.showAll"/>
    </s:if>
    <s:else>
        <s:text name="display.showPaginated"/>
    </s:else>
</s:a>
<display:table name="evidenceRequestList" id="at" pagesize="${pagePendings}" requestURI="businessrulesmanagement/evidenceRequest!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column title="${title}" sortProperty="evidenceTime" sortable="true">
                <s:url id="editEvidenceRequest" action="evidenceRequest" method="editEvidenceRequest" namespace="/businessrulesmanagement">
                    <s:param name="evidenceRequest.id" value="id"/>
                </s:url>
                <s:a href="%{editEvidenceRequest}" showLoadingText="false" indicator="waitDiv" targets="evidenceRequestTabDiv" cssStyle="color:blue;">
                    <s:date name="%{evidenceTime}" format="HH:mm"/>
                </s:a>
            </display:column> 
            <display:column titleKey="label.evidenceProvider" property="evidenceProvider.name" sortable="true"/>
            <display:column titleKey="label.description" property="evidenceProvider.description" sortable="true"/>
            <display:column titleKey="label.evidenceType" property="type.name" sortable="true"/>
            <display:column title="${titleDay}" sortProperty="day" sortable="true">
                <s:if test="%{day==1}">
                    <s:text name="label.sunday"/>
                </s:if>
                <s:elseif test="%{day==2}">
                    <s:text name="label.monday"/>
                </s:elseif>
                <s:elseif test="%{day==3}">
                    <s:text name="label.tuesday"/>
                </s:elseif>
                <s:elseif test="%{day==4}">
                    <s:text name="label.thursday"/>
                </s:elseif>
                <s:elseif test="%{day==5}">
                    <s:text name="label.wednesday"/>
                </s:elseif>
                <s:elseif test="%{day==6}">
                    <s:text name="label.friday"/>
                </s:elseif>
                <s:elseif test="%{day==7}">
                    <s:text name="label.saturday"/>
                </s:elseif>
            </display:column>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'evidenceRequestId', 'deleteEvidencerequestBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
        </s:push>
    </s:if>
</display:table>
