<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'situationTemplateList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.name')}"/>
<display:table name="situationTemplates" id="st" pagesize="30" requestURI="templatemanagement/situationTemplate!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.st != null">
        <s:push value="#attr.st">
            <display:column title="${title}" sortProperty="name" sortable="true">
                <s:url id="editSituationTemplate" action="situationTemplate" method="editSituationTemplate" namespace="/templatemanagement">
                    <s:param name="situationTemplate.id" value="id"/>
                </s:url>
                <s:a href="%{editSituationTemplate}" showLoadingText="false" indicator="waitDiv" targets="situationDiv" cssStyle="color:blue;">
                    <s:text name="%{name}"/>
                </s:a>
            </display:column>
            <display:column titleKey="label.product" property="product.description" sortable="true"/>
            <display:column titleKey="label.area" property="areaType.description" sortable="true"/>
            <display:column titleKey="label.state" sortProperty="active" sortable="true">
                <s:if test="active">
                    <img src="/business-services/img/ok_circle.PNG" height="15" width="15" border="0" style="vertical-align:middle;" alt='<s:text name="label.true"/>'>
                </s:if>
                <s:else>
                    <img src="/business-services/img/nook_circle.PNG" height="15" width="15" border="0" style="vertical-align:middle;" alt='<s:text name="label.false"/>'>
                </s:else>
            </display:column>
            <display:column>
                <s:if test="active">
                    <s:url id="deactivateSituationTemplateList" namespace="/templatemanagement" action="situationTemplate!deactivateList">
                        <s:param name="situationTemplate.id" value="id"/>
                    </s:url>
                    <s:a href="%{deactivateSituationTemplateList}" showLoadingText="false" indicator="waitDiv" targets="situationTemplateList" cssStyle="color:blue;">
                        <s:text name="label.deactivate"/>
                    </s:a>
                </s:if>
                <s:else>
                    <s:url id="activateSituationTemplateList" namespace="/templatemanagement" action="situationTemplate!activateList">
                        <s:param name="situationTemplate.id" value="id"/>
                    </s:url>
                    <s:a href="%{activateSituationTemplateList}" showLoadingText="false" indicator="waitDiv" targets="situationTemplateList" cssStyle="color:blue;">
                        <s:text name="label.activate"/>
                    </s:a>
                </s:else>
            </display:column>
            <display:column>
                <s:url id="changeEvaluatorName" action="situationTemplate" method="changeEvaluatorName" namespace="/templatemanagement">
                    <s:param name="situationTemplate.id" value="id"/>
                </s:url>
                <s:a href="%{changeEvaluatorName}" showLoadingText="false" indicator="waitDiv" targets="situationDiv" cssStyle="color:blue;">
                    <s:text name="label.changeEvaluator"/>
                </s:a>
            </display:column>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'situationTemplateId', 'deleteSituationTemplateBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
        </s:push>
    </s:if>
</display:table>
<s:form id="deleteSTForm" action="metricTemplate" cssStyle="display:none;">
    <s:hidden name="situationTemplate.id" id="situationTemplateId"/>
    <s:url id="deleteSituationTemplate" namespace="/templatemanagement" action="situationTemplate" method="deleteSituationTemplate"/>
    <s:submit href="%{deleteSituationTemplate}" targets="situationTemplateList" id="deleteSituationTemplateBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteSTForm"/>
</s:form>