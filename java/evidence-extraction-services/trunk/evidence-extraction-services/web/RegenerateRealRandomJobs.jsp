<%-- 
    Document   : RegenerateRealRandomJobs
    Created on : 12-01-2015, 01:08:38 PM
    Author     : Admin
--%>

<%@page import="com.scopix.periscope.extractionmanagement.RequestTimeZone"%>
<%@page import="com.scopix.periscope.extractionmanagement.ScopixJob"%>
<%@page import="java.util.List"%>
<%@page import="com.scopix.periscope.extractionmanagement.commands.GetRealRandomJobsCommand"%>
<%@page import="com.scopix.periscope.extractionmanagement.SituationRequestRealRandom"%>
<%@page import="com.scopix.periscope.periscopefoundation.util.config.SpringSupport"%>
<%@page import="com.scopix.periscope.extractionmanagement.ExtractionManager"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Regenerate RealRandom</h1>
        <%
            ExtractionManager manager = SpringSupport.getInstance().findBeanByClassName(ExtractionManager.class);
            List<ScopixJob> list = null;
            GetRealRandomJobsCommand command = new GetRealRandomJobsCommand();
            list = command.execute();
            //recorro los jobs y y utilizo solo los del dia 2 y hora 10:00:00
            for (ScopixJob job : list) {
                if (job.getDayOfWeek() == 2 && job.getExecution().equals("10:00:00")) {
        %>
        <%= job.getDayOfWeek()%> <%=job.getExecution()%>           
        <%
        
        manager.extractEvidenceRealRandom(job.getRequestTimeZones(), 2);

                }
            }
        %>
    </body>
</html>
