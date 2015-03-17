<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="security" namespace="/" action="securityManagement"/>
<table width="100%" align="center" style="text-align:center;vertical-align:middle;">
    <tr>
        <td style="vertical-align:middle;text-align:right;">
            <s:a href="%{security}" indicator="waitDiv" showLoadingText="false" targets="principal" cssStyle="color:blue;">
                <s:text name="customizing.home.securityManagement"/>
            </s:a>
        </td>
        <td style="vertical-align:middle;text-align:left;">
            <s:a href="%{security}" indicator="waitDiv" showLoadingText="false" targets="principal">
                <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" />
            </s:a>
        </td>
    </tr>
    
</table>