<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="area" action="checkingQueueManagement" namespace="/queuemanagement" method="getAreas"/>
<s:url id="filter" action="checkingQueueManagement" namespace="/queuemanagement" method="list"/>
<table>
    <tr>
        <td>
            <table>
                <s:textfield required="true" id="filterDate" key="filter.date" name="filters.date" value="%{today}" size="10" readonly="true" onclick="if(self.gfPop)gfPop.fPopCalendar(document.checkingForm.filterDate);return false;"/>
            </table>
        </td>
        <td>
            <table>
                <s:select id="filterStore" name="filters.store" list="storeArray" key="filter.store" listKey="id" listValue="description" headerKey="-1" headerValue="%{getText('select.selecteOne')}" onchange="publishTopic('/refreshCheckingArea');"/>
            </table>
        </td>
        <td>           
            <s:div href="%{area}" id="areaDiv" listenTopics="/refreshCheckingArea" showLoadingText="false" formId="checkingForm" theme="ajax"/>
        </td>
        <td colspan="3">
            <table align="right" style="text-align:right;">
                <s:a href="%{filter}" showLoadingText="false" formId="checkingForm" indicator="waitDiv" targets="checkingListTable" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</table>
