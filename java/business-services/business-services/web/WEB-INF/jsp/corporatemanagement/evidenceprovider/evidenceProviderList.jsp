<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:actionerror/>
<s:fielderror/>

<s:set name="target" value="%{'evidenceProviderList'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:set name="title" value="%{getText('label.name')}"/>
<display:table name="evidenceProviders" id="at" pagesize="30" requestURI="corporatemanagement/evidenceProvider!readyList.action" sort="list" defaultsort="3"    export="false"  style="width:100%;" excludedParams="*">
    <display:setProperty name="basic.empty.showtable" value="true"/>
    <s:if test="#attr.at != null">
        <s:push value="#attr.at">
            <display:column titleKey="label.store" property="store.description" sortable="true"/>
            <display:column titleKey="label.area">
                <s:iterator value="areas" id="evidenceProviderAreas">
                    <s:text name="%{description}"/><br/>
                </s:iterator>
            </display:column>
            <display:column title="${title}" sortProperty="name" sortable="true">
                <s:url id="editEvidenceProvider" action="evidenceProvider" method="editEvidenceProvider" namespace="/corporatemanagement">
                    <s:param name="evidenceProvider.id" value="id"/>
                </s:url>
                <s:a href="%{editEvidenceProvider}" showLoadingText="false" indicator="waitDiv" targets="evidenceProviderTabDiv" cssStyle="color:blue;">
                    <s:text name="%{name}"/>
                </s:a>
            </display:column> 
            <display:column titleKey="label.description" property="description" sortable="true"/>
            <display:column titleKey="label.evidenceProviderType" property="evidenceProviderType.description" sortable="true"/>
            <display:column titleKey="label.templatePath">
                <s:iterator value="%{evidenceProviderTemplates}" id="providerTemplate">
                    <s:text name="%{situationTemplate.name}"/>: <s:text name="%{templatePath}"/><br/>
                </s:iterator>
            </display:column>
            <display:column>                
                <s:a cssStyle="color:blue;" onclick="deleteItem(%{id}, 'evidenceProviderId', 'deleteEvidenceProviderBtn');">
                    <s:text name="label.delete"/>
                </s:a>                        
            </display:column>
            <display:column>
                <s:url id="modifyTemplateUrl" action="evidenceProvider" method="modifyTemplatePath" namespace="/corporatemanagement">
                    <s:param name="evidenceProvider.id" value="id"/>
                </s:url>
                <s:a cssStyle="color:blue;" onclick="uploadImage(%{id});">
                    <s:text name="label.modifyTemplate"/>
                </s:a>
            </display:column>
        </s:push>
    </s:if>
</display:table>
<s:form id="deleteEPForm" action="evidenceProvider" cssStyle="display:none;">
    <s:hidden name="evidenceProvider.id" id="evidenceProviderId"/>
    <s:url id="deleteEvidenceProvider" namespace="/corporatemanagement" action="evidenceProvider" method="deleteEvidenceProvider"/>
    <s:submit href="%{deleteEvidenceProvider}" targets="evidenceProviderList" id="deleteEvidenceProviderBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;" formId="deleteEPForm"/>
</s:form>