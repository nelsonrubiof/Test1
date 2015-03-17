<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror />
<s:fielderror />
<br>
<s:set name="target" value="%{'nfirstListTable'}" scope="request"/>
<s:set name="formId" value="%{'nfirstForm'}" scope="request"/>
<s:set name="indicator" value="%{'waitDiv'}" scope="request"/>

<s:form id="nfirstForm" name="nfirstForm" action="nFirst!readyList" cssStyle="border:0px;width:100%" namespace="/queuemanagement" method="post" >
    <tr><td>
            <s:url namespace="/queuemanagement" action="nFirst" method="filters" id="filter1"/>
            <s:div href="%{filter1}" id="filtersNfirstDiv" showLoadingText="false" cssStyle="width:100%" />
    </td></tr>
    <tr><td>
            <s:url namespace="/queuemanagement" action="nFirst!readyList" id="table"/>
            <s:div href="%{table}" id="nfirstListTable" showLoadingText="false" cssStyle="width:100%" listenTopics="/refreshNFirstListTable"/>
    </td></tr>
    <br><br>
</s:form>
            