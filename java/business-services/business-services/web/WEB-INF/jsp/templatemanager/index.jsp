<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style media="all" type="text/css">
@import url("css/displaytag/maven-base.css");

@import url("css/displaytag/maven-theme.css");

@import url("css/displaytag/site.css");

@import url("css/displaytag/screen.css");
</style>
<title>The Grand Scopix Periscope Customizer</title>
<s:head theme="ajax" debug="false" />
<link rel="stylesheet" href="/business-services/css/styles.css" type="text/css"/>
<link rel="stylesheet" href="/business-services/css/displaytag/displaytag.css" type="text/css"/>
</head>
<body>

<%-- 
<s:debug /> <hr>
--%>


<script type="text/javascript">
<!-- 
<%-- Convenience method to publish a topic --%>
function publishTopic(name) {
  dojo.event.topic.publish(name, null);
}


function confirmDelete(){    
    var resp = confirm('<s:text name="confirm.delete"/>');
    alert(resp);
    return resp;
}

<%--
An attempt at doing double column layout with javascipt.

function performLayout(formId, columns) {
   var formElm = document.getElementById(formId);
   var table = formElm.childNodes[1];
   var rows = table.rows;
   for (var i = 0; i < rows.length - 1; i++) {
     var row1 = rows[i];
     var row2 = rows[i+1];
     var cells2 = row2.cells;
     while (cells2.length) {
        row1.appendChild(cells2[0]);
     }
     table.deleteRow(i+1);
   }
}

function layoutForm(formId, columns) {
    window.setTimeout( function() { performLayout(formId, columns);  }, 0);
}
--%>

// -->
</script>

<s:url id="templateManager" namespace="/templatemanager" action="templateManagerIndex!index"/>
<s:url id="businessRuleTemplateList" namespace="/templatemanager" action="businessRuleTemplate!list"/>

<h1><s:text name="periscope.welcome" /></h1>

<s:tabbedPanel id="mainTabs" doLayout="false" cssClass="tabs" cssStyle="width:100%;">
    <s:div id="templateManagerTabDiv" key="tab.templateManager" cssClass="optionTabs" href="%{templateManager}" listenTopics="refreshIndex"/>
</s:tabbedPanel>

</body>
</html>