<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'situationList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.description')}"/>
<s:hidden name="pageSize"/>
<s:url id="showAll" namespace="/businessrulesmanagement" action="situation!showAll"/>
&nbsp;&nbsp;&nbsp;<s:a href="%{showAll}" formId="findSituationForm" targets="situationList" showLoadingText="false" indicator="waitDiv" cssStyle="color:blue;">
    <s:if test="%{pageSize == '30'}">
        <s:text name="display.showAll"/>
    </s:if>
    <s:else>
        <s:text name="display.showPaginated"/>
    </s:else>
</s:a>
<display:table name="situations" id="at" pagesize="${pageSize}" requestURI="businessrulesmanagement/situation!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column title="${title}" sortProperty="description" sortable="true">
                <s:url id="editSituation" action="situation" method="editSituation" namespace="/businessrulesmanagement">
                    <s:param name="situationForm.id" value="%{id}"/>
                </s:url>
                <s:a href="%{editSituation}" showLoadingText="false" indicator="waitDiv" targets="situationTabDiv" cssStyle="color:blue;">
                    <s:text name="%{description}"/>
                </s:a>
            </display:column> 
            <display:column titleKey="label.situationTemplate" property="situationTemplate.name" sortable="true"/>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'situationId', 'deleteSituationBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
        </s:push>
    </s:if>
</display:table>