<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:actionmessage/>
<s:url id="filterStore" namespace="/corporatemanagement" action="stores!list"/>
<s:url id="listStore" namespace="/corporatemanagement" action="stores!readyList"/>
<s:url id="newStore" namespace="/corporatemanagement" action="stores!newStore"/>
<s:form name="findStoreForm" id="findStoreForm" action="stores" cssStyle="width:100%">
    <tr>
        <td>
            <table>
                <s:textfield name="store.name" key="label.name" />
            </table>
        </td>
        <td>
            <table>
                <s:textfield name="store.description" key="label.description" />
            </table>
        </td>
        <td>
            <table>
                <s:textfield name="store.address" key="label.address" />
            </table>
        </td>
        <td>
            <table align="right" style="text-align:right;">
                <s:a href="%{filterStore}" showLoadingText="false" formId="findStoreForm" indicator="waitDiv" targets="storeList" cssClass="button">
                    <span><s:text name="filter.filter"/></span>
                </s:a>
            </table>
        </td>
    </tr>
</s:form>
<s:div id="storeList" href="%{listStore}" indicator="waitDiv" showLoadingText="false"/>
<br>
<s:a cssClass="button" onclick="this.blur();" href="%{newStore}" showLoadingText="false" indicator="waitDiv" targets="storesTabDiv" >
    <span><s:text name="label.add"/></span>
</s:a>