<%@ taglib prefix="s" uri="/struts-tags"%>
<s:url id="addStoreAreaGroup" namespace="/operatorqueuemanagement" action="operatorQueueManagement" method="addStoreAreaGroup"/>
<s:url id="addStoreSituationTemplateGroup" namespace="/operatorqueuemanagement" action="operatorQueueManagement" method="addStoreSituationTemplateGroup"/>


<s:url id="listStoreAreaGroup" namespace="/operatorqueuemanagement" action="operatorQueueManagement!storeAreaReadyList"/>
<s:actionerror/>
<s:fielderror/>
<table width="100%">
    <tr>
        <td align="center" width="40%">
            <table><s:select name="storeGroup" list="storeGroupList" key="label.store" labelposition="top" listKey="id" 
                      listValue="description" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td>
        <td align="center" width="20%">
        <!--td align="center" width="40%">
            <table><s:select name="areaGroup" list="areaGroupList" key="label.areaType" labelposition="top" listKey="id" 
                      listValue="description" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/></table>
        </td-->
        <td align="center" width="40%">
            <table>
                <s:select name="situationTemplateGroup" list="situationTemplatePojoList" key="label.situationTemplate" 
                          labelposition="top" listKey="id" 
                          listValue="description" size="10" multiple="true" cssStyle="width:100%;" theme="ajax"/>

            </table>
        </td>
    </tr>
    <tr>
        <td>
            <table>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr><td></td></tr>
                <tr>
                    <td>
                        <s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{addStoreSituationTemplateGroup}" 
                             indicator="waitDiv" targets="storeAreaGroupListDiv" formId="saveSForm">
                            <span><s:text name="label.add"/></span>
                        </s:a>
                        <!--<s:a cssClass="button" onclick="this.blur();" showLoadingText="false" href="%{addStoreAreaGroup}" 
                             indicator="waitDiv" targets="storeAreaGroupListDiv" formId="saveSForm">
                            <span><s:text name="label.add"/></span>
                        </s:a>-->
                    </td>
                </tr>
            </table>
        </td>
    </tr>
</table>
<s:div id="storeAreaGroupListDiv" href="%{listStoreAreaGroup}" indicator="waitDiv" showLoadingText="false"/>

