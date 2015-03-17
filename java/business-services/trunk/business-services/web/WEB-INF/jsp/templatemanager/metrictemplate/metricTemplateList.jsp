<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'metricTemplateList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.name')}"/>
<display:table name="metricTemplates" id="mt" pagesize="30" requestURI="templatemanagement/metricTemplate!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.mt != null">
        <s:push value="#attr.mt">
            <display:column title="${title}" sortProperty="name" sortable="true">
                <s:url id="editMetricTemplate" action="metricTemplate" method="editMetricTemplate" namespace="/templatemanagement">
                    <s:param name="metricTemplate.id" value="id"/>
                </s:url>
                <s:a href="%{editMetricTemplate}" showLoadingText="false" indicator="waitDiv" targets="metricDiv" cssStyle="color:blue;">
                    <s:text name="%{name}"/>
                </s:a>
            </display:column> 
            <display:column titleKey="label.description" property="description" sortable="true"/>
            <display:column titleKey="label.metricType" property="metricTypeElement" sortable="true"/>
            <display:column titleKey="label.evidenceType" property="evidenceTypeElement" sortable="true"/>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'metricTemplateId', 'deleteMetricTemplateBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
        </s:push>
    </s:if>
</display:table>
<s:form id="deleteMTForm" action="metricTemplate" cssStyle="display:none;">
    <s:hidden name="metricTemplate.id" id="metricTemplateId"/>
    <s:url id="deleteMetricTemplate" namespace="/templatemanagement" action="metricTemplate" method="deleteMetricTemplate"/>
    <s:submit href="%{deleteMetricTemplate}" targets="metricTemplateList" listenTopics="/deleteMetricTemplate" id="deleteMetricTemplateBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteMTForm"/>
</s:form>