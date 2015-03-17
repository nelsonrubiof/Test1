<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror />
<s:fielderror />
<br>
<s:set name="target" value="%{'pendingListTable'}" scope="request"/>
<s:set name="formId" value="%{'pendingForm'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>

<s:form id="pendingForm" name="pendingForm" action="pendingQueueManagement!readyList" cssStyle="border:0px;width:100%" namespace="/queuemanagement" method="post" >
    <tr><td>
            <s:url namespace="/queuemanagement" action="pendingQueueManagement" method="filters" id="filter1"/>
            <s:div href="%{filter1}" id="filtersDiv" showLoadingText="false" cssStyle="width:100%" />
    </td></tr>
    <tr><td>
            <s:url namespace="/queuemanagement" action="pendingQueueManagement!list" id="table"/>
            <s:div href="%{table}" id="pendingListTable" showLoadingText="false" cssStyle="width:100%" listenTopics="/refreshPendingListTable"/> 
    </td></tr>
    <br><br>
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
                        <table style="text-align:center;">
                            <s:select required="true" id="toOperatorQueue" key="label.queueManagement.operatorQueues"
                                      name="toOperatorQueue" list="OperatorQueues" listKey="id" listValue="name" headerKey="-1" headerValue="%{getText('select.selecteOne')}"/>
                        </table>
                    </td>
                    <td>
                        <table style="text-align:center;">
                            <s:url id="moveToQueue" namespace="/queuemanagement" action="pendingQueueManagement!moveToQueue"/>
                            <s:a href="%{moveToQueue}" formId="pendingForm" targets="pendingListTable" showLoadingText="false" indicator="waitDiv" cssClass="button" notifyTopics="/refreshDeletedList">
                                <span><s:text name="label.moveToQueue"/></span>
                            </s:a>
                        </table>
                    </td>
                    <td>
                            <s:url id="changePriority" namespace="/queuemanagement" action="pendingQueueManagement!changePriority" />
                            <s:a href="%{changePriority}" formId="pendingForm" targets="pendingListTable" showLoadingText="false" indicator="waitDiv" cssClass="button" notifyTopics="/refreshPendingList">
                                <span><s:text name="label.changePosition"/></span>
                            </s:a>
                    </td>
                    <td>
                        <table style="text-align:center;">
                            <s:url id="changeState" namespace="/queuemanagement" action="pendingQueueManagement!changeState"/>
                            <s:a href="%{changeState}" formId="pendingForm" targets="pendingListTable" showLoadingText="false" indicator="waitDiv" cssClass="button" notifyTopics="/refreshDeletedList">
                                <span><s:text name="label.delete"/></span>
                            </s:a>
                        </table>
                    </td>
                    <td colspan="5">&nbsp;</td>
                </tr>
            </table>
        </td>
    </tr>
    </s:form>
            