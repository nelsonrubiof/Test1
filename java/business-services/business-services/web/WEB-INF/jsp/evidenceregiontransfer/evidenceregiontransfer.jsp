<%-- 
    Document   : evidenceregiontransfer
    Created on : Jul 29, 2014, 9:59:59 AM
    Author     : Sebastian
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <script src="js/jquery.js"></script>
        <script src="js/jquery.getUrlParam.js"></script>
        <script src="js/bootstrap.js"></script>
        <script src="js/jquery-ui-1.10.4.custom.min.js"></script>
        <script src="js/jquery.dynatable.js"></script>
        <script src="js/bootstrap-datetimepicker.min.js"></script>
        <script src="js/test.js"></script>
        <link rel="stylesheet" href="css/smoothness/jquery-ui-1.10.4.custom.css"/>
        <link rel="stylesheet" href="css/smoothness/jquery-ui-1.10.4.custom.min.css"/>
        <link rel="stylesheet" href="css/jquery.dynatable.css"/>
        <link rel="stylesheet" href="css/main.css"/>
        <link rel="stylesheet" href="css/bootstrap.css"/>
        <link rel="stylesheet" href="css/bootstrap.css"/>
        <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css"/> 

        <title><s:text name="application.title"/> </title>
    </head>

    <body> 
        <img src="img/scopix_header_compound_98.jpg" alt="map" width="1024" height="98" border="0" usemap="#Map" id="banner_img"/>
        <map name="Map" id="Map">
            <area shape="rect" coords="332,9,432,43" href="" alt="Log In" />
            <area shape="rect" coords="465,13,558,41" href="" alt="Log Out"/>
            <area shape="rect" coords="587,8,688,43" href="http://scopixsolutions.com" target="_blank" alt="About Scopix" />
        </map>
        <div class="loading tabla" > </div>
        </br>
        </br>
        <table >
            <tr> 
                <td style="padding-right:10px; padding-left: 50px;" > 
                    <label for="userName" >username</label>
                </td>
                <td style="padding-right:10px;">
                    <select id="userName" name="userName" class="form-control" ></select>
                </td>
                <td style="padding-right:10px;" > 
                    <label for="selectDate" >date</label>
                </td>
                <td style="padding-right:50px;">
                    <div class="well-small">
                        <div id="selectDate" class="input-append date">
                            <input data-format="dd/MM/yyyy" type="text"></input>
                            <span class="add-on">
                                <i data-time-icon="icon-time" data-date-icon="icon-calendar">
                                </i>
                            </span>
                        </div>
                </td>
                <td style="padding-right:10px;">
                    <button type="button" onclick=" getUserLog();" class="btn btn-default">get action log</button>
                </td>
            </tr>
        </table>
        </br>
        <table>
            <tr>
                <td style="padding-right:20px; padding-left: 50px;"> 
                    <label>specify range</label>
                </td>
                <td > 
                    <label for="selectFromTime">from</label>
                </td>
                <td style="padding-right:20px;"> 
                    <div class="well-small">
                        <div id="selectFromTime" class="input-append date">
                            <input data-format="hh:mm:00" type="text"></input>

                            <span class="add-on">
                                <i data-time-icon="icon-time" data-date-icon="icon-calendar">
                                </i>
                            </span>
                        </div>
                    </div>
                </td>
                <td > 
                    <label for="selectToTime">to</label>
                </td>
                <td style="padding-right:20px;"> 
                    <div class="well-small">
                        <div id="selectToTime" class="input-append date">
                            <input data-format="hh:mm:00" type="text"> </input>
                            <span class="add-on">
                                <i data-time-icon="icon-time" data-date-icon="icon-calendar">
                                </i>
                            </span>
                        </div>
                    </div>
                </td>
            </tr>   
        </table>

        </br>
        </br>
        <table  style="width: 90%;">
            <tr>
                <td style="padding-bottom: 30px; padding-left: 50px;">activity log</td>
            </tr>
            <tr>
                <td style="padding-left: 50px;">
                    <table id="data" class="table">
                        <thead>
                            
  
                        <th data-dynatable-column="evidenceId">Evidence Id</th>
                        <th data-dynatable-column="evidenceFileName">File Name</th>
                        <th data-dynatable-column="evidenceDate">Evidence Date</th>
                        <th data-dynatable-column="regionServerName">Server Name</th>
                        <th data-dynatable-column="completed">Transmitted</th>
                        <th data-dynatable-column="transmisitionDate">Transmission Date</th>
                        <th data-dynatable-column="errorMessage">Error Message</th>
                        </thead>
                        <tbody></tbody>
                    </table>
                </td>
            </tr>
        </table>
    </body>

