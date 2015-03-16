<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'evidenceExtractionServicesList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.url')}"/>
<display:table name="evidenceExtractionServicesServers" id="at" pagesize="30" requestURI="corporatemanagement/evidenceExtractionServices!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column title="${title}" sortProperty="url" sortable="true">
                <s:url id="editEvidenceExtractionServices" action="evidenceExtractionServices" method="editEESS" namespace="/corporatemanagement">
                    <s:param name="evidenceExtractionServicesServer.id" value="id"/>
                </s:url>
                <s:a href="%{editEvidenceExtractionServices}" showLoadingText="false" indicator="waitDiv" targets="evidenceExtractionTabDiv" cssStyle="color:blue;">
                    <s:text name="%{url}"/>
                </s:a>
            </display:column>
            <display:column titleKey="label.name" property="name" sortable="true"/>
            <display:column titleKey="label.sshAddress" property="sshAddress" sortable="true"/>
            <display:column titleKey="label.sshPort" property="sshPort" sortable="true"/>
            <display:column titleKey="label.sshLocalTunnelPort" property="sshLocalTunnelPort" sortable="true"/>
            <display:column titleKey="label.sshRemoteTunnelPort" property="sshRemoteTunnelPort" sortable="true"/>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'evidenceExtractionServicesServerId', 'deleteEvidenceExtractionServicesBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
        </s:push>
    </s:if>
</display:table>
<s:form id="deleteEvidenceExtractionServicesForm" action="evidenceExtractionServices" cssStyle="display:none;">
    <s:hidden name="evidenceExtractionServicesServer.id" id="evidenceExtractionServicesServerId"/>
    <s:url id="deleteEvidenceExtractionServices" namespace="/corporatemanagement" action="evidenceExtractionServices" method="deleteEESS"/>
    <s:submit href="%{deleteEvidenceExtractionServices}" targets="evidenceExtractionServicesList" id="deleteEvidenceExtractionServicesBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteEvidenceExtractionServicesForm"/>
</s:form>
