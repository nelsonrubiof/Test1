<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<%
            request.getSession().removeAttribute("sessionId");
%>
<div align="center" style="width:100%">
    <s:form id="loginForm" action="customizing!login" namespace="/" cssStyle="text-align:center; width:60%" method="post">
        <tr>
            <td colspan="2">
                <p align="center" class="style3"><s:text name="customizing.welcome.p1"/></p>
                <p align="center" class="style3"><s:text name="customizing.welcome.p2"/></p>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                &nbsp;
            </td>
        </tr>
        <tr>
            <td colspan="2">
                &nbsp;
            </td>
        </tr>
        <s:textfield name="user" key="login.user" size="45" required="true"/>
        <s:password showPassword="false" name="password" id="password" key="login.password" size="45" required="true"/>

        <s:url value="%{pageContext.request.contextPath}/img/button_login.gif" id="login_img"/>
        <s:submit targets="principal" showLoadingText="false" indicator="waitDiv" label="login" formId="loginForm" type="image" src="%{login_img}" onclick="changePwdLogin();"/>
    </s:form>
</div>        
        