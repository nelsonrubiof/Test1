<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror />
<s:if test="businessRuleTemplate.id != null">
<br>
<h1 align="center"><s:text name="confirm.deleteBRT"/></h1>
<br>
    <table style="align:center" border="0" width="40%">
        <tr>
            <td align="center">
                <s:url id="cancel" namespace="/templatemanager" action="businessRuleTemplate!list"/>
                <s:a href="%{cancel}" targets="templateManagerTabDiv">
                    <img src="/business-services/img/nook_circle.PNG" height="15" width="15" border="0" style="vertical-align:middle;" alt='<s:text name="label.cancel"/>'>
                </s:a>
            </td>
            <td align="center">
                <s:url id="accept" namespace="/templatemanager" action="businessRuleTemplate!delete">
                    <s:param name="businessRuleTemplate.id" value="businessRuleTemplate.id"/>
                </s:url>
                <s:a href="%{accept}" targets="templateManagerTabDiv">
                    <img src="/business-services/img/ok_circle.PNG" height="15" width="15" border="0" style="vertical-align:middle;" alt='<s:text name="label.accept"/>'>
                </s:a>
            </td>
        </tr>
    </table>
</s:if>
<s:elseif test="complianceConditionTemplate.id != null">
<br>
<h1 align="center"><s:text name="confirm.deleteCCT"/></h1>
<br>    
    <table style="align:center" border="0" width="40%">
        <tr>
            <td align="center">
                <s:url id="cancel" namespace="/templatemanager" action="complianceConditionTemplate!list">
                    <s:param name="complianceConditionTemplate.businessRuleTemplate.id" value="complianceConditionTemplate.businessRuleTemplate.id"/>
                </s:url>
                <s:a href="%{cancel}" targets="complianceConditionTemplateDiv">
                    <img src="/business-services/img/nook_circle.PNG" height="15" width="15" border="0" style="vertical-align:middle;" alt='<s:text name="label.cancel"/>'>
                </s:a>
            </td>
            <td align="center">
                <s:url id="accept" namespace="/templatemanager" action="complianceConditionTemplate!delete">
                    <s:param name="complianceConditionTemplate.id" value="complianceConditionTemplate.id"/>
                </s:url>
                <s:a href="%{accept}" targets="complianceConditionTemplateDiv">
                    <img src="/business-services/img/ok_circle.PNG" height="15" width="15" border="0" style="vertical-align:middle;" alt='<s:text name="label.accept"/>'>
                </s:a>
            </td>
        </tr>
    </table>
</s:elseif>
<s:elseif test="situationTemplate.id != null">
<br>
<h1 align="center"><s:text name="confirm.deleteST"/></h1>
<br>    
    <table style="align:center" border="0" width="40%">
        <tr>
            <td align="center">
                <s:url id="templateManager" namespace="/templatemanager" action="situationTemplate!list">
                    <s:param name="situationTemplate.complianceConditionTemplate.id" value="situationTemplate.complianceConditionTemplate.id"/>
                </s:url>
                <s:a href="%{templateManager}" targets="situationTemplateDiv">
                    <img src="/business-services/img/nook_circle.PNG" height="15" width="15" border="0" style="vertical-align:middle;" alt='<s:text name="label.cancel"/>'>
                </s:a>
            </td>
            <td align="center">
                <s:url id="templateManager" namespace="/templatemanager" action="situationTemplate!delete">
                    <s:param name="situationTemplate.id" value="situationTemplate.id"/>
                </s:url>
                <s:a href="%{templateManager}" targets="situationTemplateDiv">
                    <img src="/business-services/img/ok_circle.PNG" height="15" width="15" border="0" style="vertical-align:middle;" alt='<s:text name="label.accept"/>'>
                </s:a>
            </td>
        </tr>
    </table>
</s:elseif>
<s:elseif test="requiredSituationElementTemplate.id != null">
<br>
<h1 align="center"><s:text name="confirm.deleteRSET"/></h1>
<br>    
    <table style="align:center" border="0" width="40%">
        <tr>
            <td align="center">
                <s:url id="cancel" namespace="/templatemanager" action="requiredSituationElementTemplate!list">
                     <s:param name="requiredSituationElementTemplate.situationTemplate.id" value="requiredSituationElementTemplate.situationTemplate.id"/>
                </s:url>
                <s:a href="%{cancel}" targets="requiredSituationElementTemplateDiv">
                    <img src="/business-services/img/nook_circle.PNG" height="15" width="15" border="0" style="vertical-align:middle;" alt='<s:text name="label.cancel"/>'>
                </s:a>
            </td>
            <td align="center">
                <s:url id="accept" namespace="/templatemanager" action="requiredSituationElementTemplate!delete">
                    <s:param name="requiredSituationElementTemplate.id" value="requiredSituationElementTemplate.id"/>
                </s:url>
                <s:a href="%{accept}" targets="requiredSituationElementTemplateDiv">
                    <img src="/business-services/img/ok_circle.PNG" height="15" width="15" border="0" style="vertical-align:middle;" alt='<s:text name="label.accept"/>'>
                </s:a>
            </td>
        </tr>
    </table>
</s:elseif>



