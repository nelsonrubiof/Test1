<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>
<s:set name="target" value="%{'evidenceServicesList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.url')}"/>
<display:table name="evidenceServicesServers" id="at" pagesize="30" requestURI="corporatemanagement/evidenceServices!readyList.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column title="${title}" sortProperty="url" sortable="true">
                <s:url id="editESS" action="evidenceServices" method="editESS" namespace="/corporatemanagement">
                    <s:param name="evidenceServicesServer.id" value="id"/>
                </s:url>
                <s:a href="%{editESS}" showLoadingText="false" indicator="waitDiv" targets="evidenceServicesTabDiv" cssStyle="color:blue;">
                    <s:text name="%{url}"/>
                </s:a>
            </display:column>
            <display:column titleKey="label.evidencePath" property="evidencePath" sortable="true"/>
            <display:column titleKey="label.proofPath" property="proofPath" sortable="true" />
            <display:column titleKey="label.alternativeSFTPip" property="alternativeSFTPip" sortable="true" />
            <display:column titleKey="label.alternativeSFTPuser" property="alternativeSFTPuser" sortable="true" />
            <display:column titleKey="label.alternativeSFTPpassword" property="alternativeSFTPpassword" sortable="true" />
            <display:column titleKey="label.alternativeRemoteSFTPPathList" property="alternativeRemoteSFTPPath" sortable="true" />
            <display:column titleKey="label.localFilePathList" property="localFilePath" sortable="true" />
            <display:column titleKey="label.alternativeModeList" property="alternativeMode" sortable="true"/>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'evidenceServicesId', 'deleteEvidenceServicesBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
        </s:push>
    </s:if>
</display:table>
<s:form id="deleteESSForm" action="evidenceServices" cssStyle="display:none;">
    <s:hidden name="evidenceServicesServer.id" id="evidenceServicesId"/>
    <s:url id="deleteEvidenceServices" namespace="/corporatemanagement" action="evidenceServices" method="deleteESS"/>
    <s:submit href="%{deleteEvidenceServices}" targets="evidenceServicesList" id="deleteEvidenceServicesBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteESSForm"/>
</s:form>