<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="area" action="pendingQueueManagement!getAreas" namespace="/queuemanagement"/>
<s:url id="filter" action="pendingQueueManagement!list" namespace="/queuemanagement"/>
<table>
    <tr>
        <td>
            <table>
                <s:textfield required="true" id="filterDate" key="filter.date" name="filters.date" value="%{today}" size="10" readonly="true" onclick="if(self.gfPop)gfPop.fPopCalendar(document.pendingForm.filterDate);return false;"/>
            </table>
        </td>
        <td>
            <table>
                <s:select id="filterStore" name="filters.store" list="storeArray" key="filter.store" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" onchange="publishTopic('/refreshArea');"/>
            </table>
        </td>
        <td>           
            <s:div href="%{area}" id="areaDiv" listenTopics="/refreshArea" formId="pendingForm" theme="ajax" showLoadingText="false"/>
        </td>
        <td colspan="3">               
                <s:a href="%{filter}" showLoadingText="false" formId="pendingForm" indicator="waitDiv" targets="pendingListTable" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>            
        </td>
    </tr>
</table>
