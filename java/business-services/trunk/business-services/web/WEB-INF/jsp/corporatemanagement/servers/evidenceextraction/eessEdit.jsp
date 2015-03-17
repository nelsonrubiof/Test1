<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="evidenceExtractionServicesSave" namespace="/corporatemanagement" action="evidenceExtractionServices!saveEESS"/>
<s:url id="evidenceExtractionServicesCancel" namespace="/corporatemanagement" action="evidenceExtractionServices!cancel"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="label.evidenceExtractionServicesServer"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="evidenceExtractionServices!save" id="saveEvidenceExtractionServicesForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="evidenceExtractionServicesServer.id"/>
    <s:textfield key="label.name" name="evidenceExtractionServicesServer.name" required="true" cssStyle="width:400px;"/>
    <s:textfield key="label.address" name="evidenceExtractionServicesServer.url" required="true" cssStyle="width:400px;"/>
    <s:select name="evidenceExtractionServicesServer.evidenceServicesServer.id" key="label.evidenceServicesServer" list="evidenceExtractionServicesList" listKey="id" listValue="url" headerKey="-1" headerValue="%{getText('select.selecteOne')}" required="true"/>
    <s:checkbox key="label.useTunnel" name="evidenceServicesServer.useTunnel" onclick="enableUseTunnel(this.checked);" />
    <s:textfield key="label.sshAddress" name="evidenceExtractionServicesServer.sshAddress" required="true" cssStyle="width:200px;" id="sshAddress" disabled="%{!evidenceServicesServer.useTunnel}"/>
    <s:textfield key="label.sshPort" name="evidenceExtractionServicesServer.sshPort" required="true" cssStyle="width:200px;" id="sshPort" disabled="%{!evidenceServicesServer.useTunnel}"/>
    <s:textfield key="label.sshUser" name="evidenceExtractionServicesServer.sshUser" required="true" cssStyle="width:200px;" id="sshUser" disabled="%{!evidenceServicesServer.useTunnel}"/>
    <s:password key="label.sshPassword" name="evidenceExtractionServicesServer.sshPassword" required="true" cssStyle="width:200px;" showPassword="true" id="sshPassword" disabled="%{!evidenceServicesServer.useTunnel}"/>
    <s:textfield key="label.sshLocalTunnelPort" name="evidenceExtractionServicesServer.sshLocalTunnelPort" required="true" cssStyle="width:200px;" id="sshLocalTunnelPort" disabled="%{!evidenceServicesServer.useTunnel}"/>
    <s:textfield key="label.sshRemoteTunnelPort" name="evidenceExtractionServicesServer.sshRemoteTunnelPort" required="true" cssStyle="width:200px;" id="sshRemoteTunnelPort" disabled="%{!evidenceServicesServer.useTunnel}"/>
    <tr>        
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{evidenceExtractionServicesCancel}" showLoadingText="false" indicator="waitDiv" targets="evidenceExtractionTabDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{evidenceExtractionServicesSave}" showLoadingText="false" formId="saveEvidenceExtractionServicesForm" indicator="waitDiv" targets="evidenceExtractionTabDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    </s:form>
    
    