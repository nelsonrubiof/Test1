<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="area" action="deletedQueueManagement" namespace="/queuemanagement" method="getAreas"/>
<s:url id="filter" action="deletedQueueManagement" namespace="/queuemanagement" method="list"/>
<table>
    <tr>
        <td>
            <table>
                <s:textfield required="true" id="filterDate" key="filter.date" name="filters.date" value="%{today}" size="10" readonly="true" onclick="if(self.gfPop)gfPop.fPopCalendar(document.deletedForm.filterDate);return false;"/>
            </table>
        </td>
        <td>
            <table>
                <s:select id="filterStore" name="filters.store" list="storeArray" key="filter.store" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" onchange="publishTopic('/refreshDeletedArea');"/>
            </table>
        </td>
        <td>           
            <s:div href="%{area}" id="areaDiv" listenTopics="/refreshDeletedArea" showLoadingText="false" formId="deletedForm" theme="ajax"/>
        </td>
        <td colspan="3">
            <table align="right" style="text-align:right;">
                <s:a href="%{filter}" showLoadingText="false" formId="deletedForm" indicator="waitDiv" targets="deletedListTable" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</table>
