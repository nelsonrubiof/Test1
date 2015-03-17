<%@ taglib prefix="s" uri="/struts-tags"%>
<h1><s:text name="label.queueManagement.operatorQueues"/></h1>
<br>
<s:actionerror/>
<s:fielderror/>
<s:url id="filterOperatorQueue" namespace="/operatorqueuemanagement" action="operatorQueueManagement!list"/>
<s:url id="listOperatorQueue" namespace="/operatorqueuemanagement" action="operatorQueueManagement!readyList"/>
<s:url id="newOperatorQueue" namespace="/operatorqueuemanagement" action="operatorQueueManagement!newOperatorQueue"/>
<s:form name="findOperatorQueueForm" id="findOperatorQueueForm" action="operatorQueueManagement" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:textfield name="operatorQueue.name" key="label.name" />
            </table>
        </td>
        <td>
            &nbsp;
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterOperatorQueue}" showLoadingText="false" formId="findOperatorQueueForm" indicator="waitDiv" 
                     targets="operatorQueueListDiv" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="operatorQueueListDiv" href="%{listOperatorQueue}" indicator="waitDiv" showLoadingText="false" />
    <br>
<s:a cssClass="button" onclick="this.blur();" href="%{newOperatorQueue}" showLoadingText="false" indicator="waitDiv" 
     targets="principal" >
    <span><s:text name="label.add"/></span>
</s:a>

