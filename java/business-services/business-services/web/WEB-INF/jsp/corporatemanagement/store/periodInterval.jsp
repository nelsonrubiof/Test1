<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:url action="stores" namespace="/corporatemanagement" method="addPeriodInterval" id="addPeriodIntervalURL"/>
<s:actionerror/>
<s:fielderror/>
<table>
    <tr>
        <td><table>
                <s:textfield name="periodInterval.initTime" key="label.initTime" required="true"/>
        </table></td>
        <td><table>
                <s:textfield name="periodInterval.endTime" key="label.endTime" required="true"/>
        </table></td>
        <td><table>
                <s:select multiple="true" size="7" list="daysList" name="days" key="label.days" required="true"/>
        </table></td>
        <td>
            <s:a href="%{addPeriodIntervalURL}" showLoadingText="false" indicator="waitDiv" targets="addPeriodIntervalDIV" cssClass="button" formId="saveSForm">
                <span><s:text name="label.add"/></span>
            </s:a>
        </td>
    </tr>
    <tr>
        <td colspan="4">
            <s:set name="target" value="%{'addPeriodIntervalDIV'}" scope="request"/>
            <s:set name="indicator" value="%{'waitDiv'}" scope="request"/>
            <display:table name="periodIntervals" id="pe" pagesize="10" requestURI="corporatemanagement/store!readyListPeriodInterval.action" sort="list" defaultsort="1" export="false"  style="width:100%;" excludedParams="*">
                <display:setProperty name="basic.empty.showtable" value="true"/>
                <s:if test="#attr.pe != null">
                    <s:push value="#attr.pe">
                        <display:column titleKey="label.initTime" property="initTime"/>
                        <display:column titleKey="label.endTime" property="endTime"/>
                        <display:column titleKey="label.monday">
                            <s:if test="%{monday}">
                                <img src="<%= request.getContextPath() + "/img/ok_circle.PNG"%>" />
                            </s:if>
                            <s:else>
                                <img src="<%= request.getContextPath() + "/img/nook_circle.PNG"%>" />
                            </s:else>
                        </display:column>
                        <display:column titleKey="label.tuesday">
                            <s:if test="%{tuesday}">
                                <img src="<%= request.getContextPath() + "/img/ok_circle.PNG"%>" />
                            </s:if>
                            <s:else>
                                <img src="<%= request.getContextPath() + "/img/nook_circle.PNG"%>" />
                            </s:else>
                        </display:column>
                        <display:column titleKey="label.wednesday">
                            <s:if test="%{wednesday}">
                                <img src="<%= request.getContextPath() + "/img/ok_circle.PNG"%>" />
                            </s:if>
                            <s:else>
                                <img src="<%= request.getContextPath() + "/img/nook_circle.PNG"%>" />
                            </s:else>
                        </display:column>
                        <display:column titleKey="label.thursday">
                            <s:if test="%{thursday}">
                                <img src="<%= request.getContextPath() + "/img/ok_circle.PNG"%>" />
                            </s:if>
                            <s:else>
                                <img src="<%= request.getContextPath() + "/img/nook_circle.PNG"%>" />
                            </s:else>
                        </display:column>
                        <display:column titleKey="label.friday">
                            <s:if test="%{friday}">
                                <img src="<%= request.getContextPath() + "/img/ok_circle.PNG"%>" />
                            </s:if>
                            <s:else>
                                <img src="<%= request.getContextPath() + "/img/nook_circle.PNG"%>" />
                            </s:else>
                        </display:column>
                        <display:column titleKey="label.saturday">
                            <s:if test="%{saturday}">
                                <img src="<%= request.getContextPath() + "/img/ok_circle.PNG"%>" />
                            </s:if>
                            <s:else>
                                <img src="<%= request.getContextPath() + "/img/nook_circle.PNG"%>" />
                            </s:else>
                        </display:column>
                        <display:column titleKey="label.sunday">
                            <s:if test="%{sunday}">
                                <img src="<%= request.getContextPath() + "/img/ok_circle.PNG"%>" />
                            </s:if>
                            <s:else>
                                <img src="<%= request.getContextPath() + "/img/nook_circle.PNG"%>" />
                            </s:else>
                        </display:column>
                        <display:column>
                            <s:a cssStyle="color:blue;cursor:hand;" onclick="deleteItem(%{id}, 'periodIntervalId', 'deletePeriodIntervalBtn');">
                                <s:text name="label.delete"/>
                            </s:a>
                        </display:column>
                    </s:push>
                </s:if>
            </display:table>
        </td>
    </tr>
</table>

