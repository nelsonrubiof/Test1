<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url id="operatorQueueSave" namespace="/operatorqueuemanagement" action="operatorQueueManagement!save"/>
<s:url id="operatorQueueCancel" namespace="/operatorqueuemanagement" action="operatorQueueManagement!cancel"/>
<s:url id="showStoresAreaType" namespace="/operatorqueuemanagement" action="operatorQueueManagement" method="showStoreAreaType"/>
<h5 align="center">
    <s:if test="editable">
        <s:text name="label.edit"/>
    </s:if>
    <s:else>
        <s:text name="label.add"/>
    </s:else>
    &nbsp;<s:text name="label.queueManagement.operatorQueue"/>
</h5>
<s:actionerror/>
<s:fielderror/>
<s:form action="operatorQueueManagement" id="saveSForm" cssStyle="border:1px;width:100%">
    <s:hidden name="editable"/>
    <s:hidden name="operatorQueue.id"/>
    <s:textfield key="label.name" name="operatorQueue.name" required="true"/>
    <tr>
        <td align="center" colspan="2" ><strong><s:text name="label.queueManagement.storeAreaGroup"/></strong></td>
    </tr>
    <tr>
        <td align="center" colspan="2"><s:div key="label.queueManagement.storeAreaGroup" labelposition="top" id="storeAreaDiv" 
               href="%{showStoresAreaType}" showLoadingText="false" indicator="waitDiv" cssStyle="text-align:center;"/></td>
    </tr>
    <tr>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{operatorQueueCancel}" showLoadingText="false" indicator="waitDiv" targets="principal" 
                     cssClass="button">
                    <span><s:text name="label.cancel"/></span>
                </s:a>
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{operatorQueueSave}" showLoadingText="false" formId="saveSForm" indicator="waitDiv"
                     targets="principal" cssClass="button">
                    <span><s:text name="label.save"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:form id="deleteOperatorQueueDetailForm" action="operatorQueueManagement" cssStyle="display:none;">
    <s:hidden name="operatorQueueDetail.id" id="operatorQueueDetailId"/>
    <s:url id="deleteOperatorQueueDetail" namespace="/operatorqueuemanagement" action="operatorQueueManagement" 
           method="deleteOperatorQueueDetail"/>
    <s:submit href="%{deleteOperatorQueueDetail}" formId="deleteOperatorQueueDetailForm" targets="storeAreaGroupListDiv"
              id="deleteOperatorQueueDetailBtn" showLoadingText="false" indicator="waitDiv" cssStyle="display:none;"/>
</s:form>