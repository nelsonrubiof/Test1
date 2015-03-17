<%@ taglib prefix="s" uri="/struts-tags"%>
<s:actionerror/>
<s:fielderror/>
<s:url id="corporate" namespace="/" action="corporateManagement"/>
<s:url id="template" namespace="/" action="templateManagement"/>
<s:url id="situation" namespace="/" action="businessRuleManagement"/>
<s:url id="operatorqueuemanagement" namespace="/operatorqueuemanagement" action="operatorQueueManagement"/>
<s:url id="regionTransferMonitor" namespace="/" action="regionTransferMonitor"/>
<table width="100%" align="center" style="text-align:center;vertical-align:middle;">
    <tr>
        <td style="vertical-align:middle;text-align:right;">
            <s:a href="%{corporate}" indicator="waitDiv" showLoadingText="false" targets="principal" cssStyle="color:blue;">
                <s:text name="customizing.home.corporateManagement"/>
            </s:a>
        </td>
        <td style="vertical-align:middle;text-align:left;">
            <s:a href="%{template}" indicator="waitDiv" showLoadingText="false" targets="principal">
                <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" />
            </s:a>
        </td>
        <td style="vertical-align:middle;text-align:right;">
            <s:a href="%{template}" indicator="waitDiv" showLoadingText="false" targets="principal" cssStyle="color:blue;">
                <s:text name="customizing.home.templateManagement"/>
            </s:a>
        </td>
        <td style="vertical-align:middle;text-align:left;">
            <s:a href="%{template}" indicator="waitDiv" showLoadingText="false" targets="principal">
                <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" />
            </s:a>
        </td>
    </tr>
    <tr>
        <td style="vertical-align:middle;text-align:right;">
            <s:a href="%{situation}" indicator="waitDiv" showLoadingText="false" targets="principal" cssStyle="color:blue;">
                <s:text name="customizing.home.situationManagement"/>
            </s:a>
        </td>
        <td style="vertical-align:middle;text-align:left;">
            <s:a href="%{situation}" indicator="waitDiv" showLoadingText="false" targets="principal">
                <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" />
            </s:a>
        </td>
        <td style="vertical-align:middle;text-align:right;">
            <s:a href="%{operatorqueuemanagement}" indicator="waitDiv" showLoadingText="false" targets="principal" cssStyle="color:blue;">
                <s:text name="customizing.home.operatorQueueManagement"/>
            </s:a>
        </td>
        <td style="vertical-align:middle;text-align:left;">
            <s:a href="%{queuemanagement}" indicator="waitDiv" showLoadingText="false" targets="principal">
                <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" />
            </s:a>
        </td>
    </tr>
    <tr>
        <td style="vertical-align:middle;text-align:right;">
            <a style="color:blue;" href="uploadEPC.html">upload csv Extraction Plan</a>
        </td>
        <td style="vertical-align:middle;text-align:left;">
            <s:a href="uploadEPC.html" indicator="waitDiv" showLoadingText="false" targets="principal">
                <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" />
            </s:a>
        </td>
        <td style="vertical-align:middle;text-align:right;">

            <a style="color:blue;" href="evidenceregiontransfer.html"><s:text name="customizing.home.evidenceRegionTransferMonitor"/> </a>

        </td>
        <td style="vertical-align:middle;text-align:left;">

            <a style="color:blue;" href="evidenceregiontransfer.html"> <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" /> </a>
        </td>
    </tr>
    <tr>
        <td style="vertical-align:middle;text-align:right;">
            <a style="color:blue;" href="evidencetransmstrategy.html"><s:text name="customizing.home.evidencetransmissionstrategymonitor"/> </a>
        </td>
        <td style="vertical-align:middle;text-align:left;">
            <a style="color:blue;" href="evidencetransmstrategy.html"> <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" /> </a>
        </td>
        <td style="vertical-align:middle;text-align:right;">
            <a style="color:blue;" href="regionserver.html"><s:text name="customizing.home.regionservermonitor"/> </a>
        </td>
        <td style="vertical-align:middle;text-align:left;">
            <a style="color:blue;" href="regionserver.html"> <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" /> </a>
        </td>
    </tr>
</table>