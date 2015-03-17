<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<s:actionerror />
<s:fielderror />
<br>
<s:set name="target" value="%{'deletedListTable'}" scope="request"/>
<s:set name="formId" value="%{'deletedForm'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:form id="deletedForm" name="deletedForm" action="deletedQueueManagement!readyDeletedList" cssStyle="border:0px;width:100%;" namespace="/queuemanagement" method="post">
    <tr><td>
            <s:url namespace="/queuemanagement" action="deletedQueueManagement!filters" id="filter2"/>
            <s:div href="%{filter2}" id="filtersDiv" showLoadingText="false" indicator="waitDiv" cssStyle="width:100%"/>
    </td></tr>
    <tr><td>
            <s:url namespace="/queuemanagement" action="deletedQueueManagement!list" id="tableDeleted"/>
            <s:div href="%{tableDeleted}" id="deletedListTable" showLoadingText="false" cssStyle="width:100%" listenTopics="/refreshDeletedListTable"/>
    </td></tr>
    <tr>
        <td>
            <table>
                <tr>
                    <td>
                        <table style="text-align:right;">
                            <s:textfield name="position" key="label.startPosition" required="true" value=""/>
                        </table>
                    </td>
                    <td>
                        <s:url id="queueManagementBakToQueue" namespace="/queuemanagement" action="deletedQueueManagement!recover"/>
                        <s:a href="%{queueManagementBakToQueue}" formId="deletedForm" targets="deletedListTable" showLoadingText="false" indicator="waitDiv" cssClass="button" notifyTopics="/refreshPendingList">
                            <span><s:text name="label.recover"/></span>
                        </s:a>
                    </td>
                    <td colspan="5">
                        &nbsp;&nbsp;&nbsp;&nbsp;
                    </td>
                </tr>
            </table>
        </td>
    </tr>    
    </s:form>
    
    