<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="evidenceServicesSave" namespace="/corporatemanagement" action="evidenceServices!saveESS"/>
<s:url id="evidenceServicesCancel" namespace="/corporatemanagement" action="evidenceServices!cancel"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="label.evidenceServicesServer"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="evidenceServices!saveESS" id="saveESSForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="evidenceServicesServer.id"/>
    <s:textfield key="label.url" name="evidenceServicesServer.url" required="true" cssStyle="width:500px;"/>
    <s:textfield key="label.evidencePath" name="evidenceServicesServer.evidencePath" required="true" cssStyle="width:500px;"/>
    <s:textfield key="label.proofPath" name="evidenceServicesServer.proofPath" required="true" cssStyle="width:500px;"/>
    <s:checkbox key="label.alternativeMode" name="evidenceServicesServer.alternativeMode" onclick="enableAlternativePath(this.checked);" />
    <s:textfield key="label.alternativeEvidencePath" name="evidenceServicesServer.alternativeEvidencePath" id="alternativePath" disabled="%{!evidenceServicesServer.alternativeMode}"  />
    <s:textfield key="label.alternativeSFTPip" name="evidenceServicesServer.alternativeSFTPip" id="alternativeIP" disabled="%{!evidenceServicesServer.alternativeMode}" />
    <s:textfield key="label.alternativeSFTPuser" name="evidenceServicesServer.alternativeSFTPuser" id="alternativeUser" disabled="%{!evidenceServicesServer.alternativeMode}" />
    <s:password key="label.alternativeSFTPpassword" name="evidenceServicesServer.alternativeSFTPpassword" id="alternativePass" disabled="%{!evidenceServicesServer.alternativeMode}" showPassword="true" />
    <s:textfield key="label.alternativeRemoteSFTPPath" name="evidenceServicesServer.alternativeRemoteSFTPPath" id="alternativeRemoteSFTPPath" disabled="%{!evidenceServicesServer.alternativeMode}" />
    <s:textfield key="label.localFilePath" name="evidenceServicesServer.localFilePath" id="localFilePath" disabled="%{!evidenceServicesServer.alternativeMode}" />
    <tr>        
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{evidenceServicesCancel}" showLoadingText="false" indicator="waitDiv" targets="evidenceServicesTabDiv" cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{evidenceServicesSave}" showLoadingText="false" formId="saveESSForm" onclick="enableAlternativePath(true);" indicator="waitDiv" targets="evidenceServicesTabDiv" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
    </s:form>
    
    