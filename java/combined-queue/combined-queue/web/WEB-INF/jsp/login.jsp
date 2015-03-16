<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:text name="application.title"/> </title>
    </head>
    <body>
        <jsp:include page="banner.jsp" />
        <div align="center" style="width:100%">
            <s:form id="loginForm" action="customizing!login" namespace="/" cssStyle="text-align:center; width:60%" method="post">
                <tr>
                    <td colspan="2">
                        <p align="center" class="style3"><s:text name="welcome.p1"/></p>
                        <p align="center" class="style3"><s:text name="welcome.p2"/></p>
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        &nbsp;
                    </td>
                </tr>
                <tr>
                    <td colspan="2">
                        <p style="color:red"> 
                            <s:property value="%{exception.message}"/>
                        </p>  
                        &nbsp;
                    </td>
                </tr>

                <s:textfield name="user" key="login.user" size="45" required="true"/>
                <s:password showPassword="false" name="password" id="password" key="login.password" size="45" required="true"/>
                <s:url value="%{pageContext.request.contextPath}/img/button_login.gif" id="login_img"/>
                <s:submit targets="principal" showLoadingText="false" indicator="waitDiv" label="login" formId="loginForm" type="image" src="%{login_img}" />
            </s:form>
        </div>        
    </body>
</html>


        