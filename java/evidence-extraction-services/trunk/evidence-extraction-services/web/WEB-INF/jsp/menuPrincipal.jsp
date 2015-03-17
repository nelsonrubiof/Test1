<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<h1>Testing Upload Evidence</h1>
<p>
<a href="<s:url value="/spring/uploadevidencetoexternaldevice?op=mount" />">
    Montar
</a>
<p>
<a href="<s:url value="/spring/uploadevidencetoexternaldevice?op=unmount" />">
    Desmontar
</a>
<p>
<a href="<s:url value="/spring/uploadevidencetoexternaldevice?op=format" />">
    Format Device
</a>
