<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="summaryUrl" namespace="/queuemanagement"
       action="summary"/>
<s:url id="nfirstUrl" namespace="/queuemanagement"
       action="nFirst"/>
<s:url id="pendingUrl" namespace="/queuemanagement"
       action="pendingQueueManagement"/>
<s:url id="deletedUrl" namespace="/queuemanagement"
       action="deletedQueueManagement"/>
<s:url id="checkingUrl" namespace="/queuemanagement"
       action="checkingQueueManagement"/>
<s:url id="updateSelectedOperatorQueue" namespace="/"
       action="queueManagementIndex" method="updateSelectedOperatorQueue"/>

<h1><s:text name="queuemanagement.welcome"/></h1>
<h5><strong>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<s:label key="label.corporate" name="corporate"/></strong></h5>

<s:form id="changeOperatorqueueForm" name="changeOperatorqueueForm">
    <s:hidden name="queue" id="queue"/>
    <s:submit href="%{updateSelectedOperatorQueue}" targets="changeOperatorqueueDiv" id="updateSelectedOperatorQueueLink" cssStyle="display:none;"
          showLoadingText="false" indicator="waitDiv" formId="changeOperatorqueueForm"/>
</s:form>



<s:select required="true" id="operatorQueues" 
          key="label.queueManagement.operatorQueues" name="queue" list="OperatorQueues" listKey="id" listValue="name"
          headerKey="-1" headerValue="%{getText('select.selecteOne')}" onchange="publishTopic('/refreshOperatorQueue');"/>
<br><br>
<s:tabbedPanel id="mainTabs" doLayout="false" cssStyle="width:100%" >    
    <s:div id="summaryTabDiv" key="tab.queueManagement.summary" refreshOnShow="true" href="%{summaryUrl}" indicator="waitDiv" showLoadingText="false"/>
    <s:div id="nfirstTabDiv" key="tab.queueManagement.nfirst" refreshOnShow="false" href="%{nfirstUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/refreshNfirts"/>
    <s:div id="pendingTabDiv" key="tab.queueManagement.pending" refreshOnShow="false" href="%{pendingUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/refreshPending"/>
    <s:div id="deletedTabDiv" key="tab.queueManagement.eliminated" refreshOnShow="false" href="%{deletedUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/refreshDeleted"/>
    <s:div id="checkingTabDiv" key="tab.queueManagement.checking" refreshOnShow="false" href="%{checkingUrl}" indicator="waitDiv" showLoadingText="false" listenTopics="/checkingDeleted"/>
</s:tabbedPanel>
<s:hidden id="updateSelectedOperatorQueueUrl" value="%{updateSelectedOperatorQueue}"/>
