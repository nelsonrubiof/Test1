<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="display" uri="http://displaytag.sf.net"%>
<s:actionerror />
<s:fielderror />
<br>
<s:set name="target" value="%{'checkingListTable'}" scope="request"/>
<s:set name="formId" value="%{'checkingForm'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
<s:form id="checkingForm" name="checkingForm" action="checkingQueueManagement!readyList" cssStyle="border:0px;width:100%;" namespace="/queuemanagement" method="post">
    <tr><td>
            <s:url namespace="/queuemanagement" action="checkingQueueManagement!filters" id="filter2"/>
            <s:div href="%{filter2}" id="filtersDiv" showLoadingText="false" indicator="waitDiv" cssStyle="width:100%"/>
    </td></tr>
    <tr><td>
            <s:url namespace="/queuemanagement" action="checkingQueueManagement!list" id="tableChecking"/>
            <s:div href="%{tableChecking}" id="checkingListTable" showLoadingText="false" cssStyle="width:100%" listenTopics="/refreshCheckingListTable"/>
    </td></tr>
    <tr>
        <td>
            <table>
                <tr>
                    <td align="right">
                        <table style="text-align:right;">
                            <s:textfield name="position" key="label.startPosition" required="true" value=""/>
                        </table>
                    </td>
                    <td>
                        <s:url id="queueManagementBakToQueue" namespace="/queuemanagement" action="checkingQueueManagement!recover"/>
                        <s:a href="%{queueManagementBakToQueue}" formId="checkingForm" targets="checkingListTable" showLoadingText="false" indicator="waitDiv" cssClass="button" notifyTopics="/refreshCheckingList">
                            <span><s:text name="label.recover"/></span>
                        </s:a>
                    </td>
                    <td>
                        <table style="text-align:center;">
                            <s:url id="changeState" namespace="/queuemanagement" action="checkingQueueManagement!changeState"/>
                            <s:a href="%{changeState}" formId="checkingForm" targets="checkingListTable" showLoadingText="false" indicator="waitDiv" cssClass="button" notifyTopics="/refreshCheckingList">
                                <span><s:text name="label.delete"/></span>
                            </s:a>
                        </table>
                    </td>
                    <td colspan="5">
                        &nbsp;
                    </td>
                </tr>
            </table>
        </td>
    </tr>    
    </s:form>
    
    