<%-- 
    Document   : activitylog
    Created on : Jun 12, 2014, 4:28:35 PM
    Author     : Sebastian
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><s:text name="application.title"/> </title>
    </head>
    <script src="js/jquery.js"></script>
    <script src="js/jquery.getUrlParam.js"></script>
    <script src="js/bootstrap.js"></script>
    <script src="js/jquery-ui-1.10.4.custom.min.js"></script>
    <script src="js/jquery.dynatable.js"></script>
    <script src="js/bootstrap-datetimepicker.min.js"></script>
    <script src="js/jstz-1.0.4.min.js"></script>
    <link rel="stylesheet" href="css/smoothness/jquery-ui-1.10.4.custom.css"/>
    <link rel="stylesheet" href="css/smoothness/jquery-ui-1.10.4.custom.min.css"/>
    <link rel="stylesheet" href="css/jquery.dynatable.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <link rel="stylesheet" href="css/bootstrap.css"/>
    <link rel="stylesheet" href="css/bootstrap-datetimepicker.min.css"/>  
    <body> 
        <jsp:include page="../banner.jsp" />
        <div class="loading tabla" > </div>
        </br>
        </br>
        <table>
            <tr> 
                <td style="padding-right:10px; padding-left: 50px;" > 
                    <label for="userName" ><s:text name="label.user.name"/></label>
                </td>
                <td style="padding-right:10px;">
                    <select id="userName" name="userName" class="form-control" ></select>
                </td>
                <td style="padding-right:10px;" > 
                    <label for="selectDate" ><s:text name="label.date"/></label>
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
                    </div>
                </td>
                <td style="padding-right:10px;">
                    <button type="button" onclick=" getUserLog();" class="btn btn-default"><s:text name="label.get.act.log"/></button>
                </td>
            </tr>
        </table>
        </br>
        <table>
            <tr>
                <td style="padding-right:20px; padding-left: 50px;"> 
                    <label><s:text name="label.specify.time.range"/></label>
                </td>
                <td > 
                    <label for="selectFromTime"><s:text name="label.from"/></label>
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
                    <label for="selectToTime"><s:text name="label.to"/></label>
                </td>
                <td style="padding-right:20px;"> 
                    <div class="well-small">
                        <div id="selectToTime" class="input-append date">
                            <input data-format="hh:mm:00" type="text"></input>
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
        <table style="width: 70%;">
            <tr>
                <td style="padding-bottom: 20px; padding-left: 50px;"><s:text name="label.users.found"/></td>
                <td style="padding-bottom: 20px; padding-left: 50px;"><s:text name="label.queues.evaluated.stats"/></td>
            </tr>
            <tr>
                <td style=" vertical-align: top; padding-left: 50px;" >
                    <table id="dataUsers" class="table">
                        <thead>
                        <th data-dynatable-column="userName"> <s:text name="label.users"/></th>
                        </thead>
                        <tbody></tbody>
                    </table>  
                </td>
                <td style=" vertical-align: top; padding-left: 50px;" >
                    <table id="dataQueues" class="table">
                        <thead>
                        <th data-dynatable-column="corporateName"> <s:text name="label.corporate"/></th>
                        <th data-dynatable-column="queueName"> <s:text name="label.queue.name"/></th>
                        <th data-dynatable-column="evaluations"> <s:text name="label.completed.evals"/></th>
                        <th data-dynatable-column="avgTimePerEval"> <s:text name="label.avg.time.evals"/></th>
                        </thead>
                        <tbody></tbody>
                    </table>  
                </td>
            </tr>
        </table>
        </br>
        </br>
        <table  style="width: 90%;">
            <tr>
                <td style="padding-bottom: 30px; padding-left: 50px;"><s:text name="activity.log"/></td>
            </tr>
            <tr>
                <td style="padding-left: 50px;">
                    <table id="data" class="table">
                        <thead>
                        <th data-dynatable-column="userName"><s:text name="label.user.name"/></th>
                        <th data-dynatable-column="status"><s:text name="label.status"/></th>
                        <th data-dynatable-column="corporateName"><s:text name="label.corporate"/></th>
                        <th data-dynatable-column="queueName"><s:text name="label.queue.name"/></th>
                        <th data-dynatable-column="requestDate"><s:text name="label.request.date"/></th>
                        <th data-dynatable-column="sendDate"><s:text name="label.send.date"/></th>
                        <th data-dynatable-column="evalTimeSeconds"><s:text name="label.eval.time"/></th>
                        </thead>
                        <tbody></tbody>
                    </table>
                </td>
            </tr>
        </table>
    </body>
</html>
<script type="text/javascript">
                        var login = <%= session.getAttribute("sessionId")%>;
                        var noRecords = '<s:text name="label.no.records.found"/>';
                        var allLabel = '<s:text name="label.all.users"/>';
                        var manUserName = '<s:text name="alert.mandatory.user.name"/>';
                        var manDate = '<s:text name="alert.mandatory.date"/>';
                        var requiredDates = '<s:text name="alert.required.dates"/>';
                        $(function() {
                            $('#selectDate').datetimepicker({
                                pickTime: false,
                                language: 'pt-BR'
                            });
                        });

                        $(function() {
                            $('#selectFromTime').datetimepicker({
                                pickDate: false,
                                pickSeconds: false,
                                language: 'pt-BR'
                            });
                        });

                        $(function() {
                            $('#selectToTime').datetimepicker({
                                pickDate: false,
                                pickSeconds: false,
                                language: 'pt-BR'
                            });
                        });

                        $(function() {
                            $('#userName').append('<option value=""></option>');
                            $.ajax({
                                url: '<%= request.getContextPath()%>/spring/services/ActivityLogWebServices/getOperatorUsersList/' + login,
                                type: 'post',
                                crossDomain: true,
                                dataType: 'json',
                                success: function(response) {
                                    var data = response.OperatorUsersDTOContainer.operatorUsersDTO;
                                    $('#userName').append('<option value="ALL-USERS">' + allLabel + '</option>');
                                    if (response.OperatorUsersDTOContainer.operatorUsersDTO instanceof Array) {
                                        for (var i = 0; i < response.OperatorUsersDTOContainer.operatorUsersDTO.length; i++) {
                                            $('#userName').append('<option value="' + data[i] + '">' + data[i] + '</option>');
                                        }
                                    } else {
                                        $('#userName').append('<option value="' + data + '">' + data + '</option>');
                                    }

                                },
                                error: function(request, status, error) {
                                    console.error(request.responseText);
                                    console.log(status);
                                    console.error(error);
                                }
                            });
                        });

                        var jsonRecords = JSON.parse('[]');

                        $('#data').dynatable({
                            dataset: {
                                records: jsonRecords
                            }
                        });
                        $('#dataUsers').dynatable({
                            features: {
                                search: false
                            },
                            dataset: {
                                records: jsonRecords
                            }
                        });
                        $('#dataQueues').dynatable({
                            features: {
                                search: false
                            },
                            dataset: {
                                records: jsonRecords
                            }
                        });
                        var dynatableQueues = $('#dataQueues').data('dynatable');
                        var dynatableUsers = $('#dataUsers').data('dynatable');
                        var dynatable = $('#data').data('dynatable');
                        dynatable.paginationPerPage.set(20);
                        dynatable.paginationPage.set(1);
                        dynatableUsers.paginationPage.set(1);
                        dynatableUsers.paginationPerPage.set(10);
                        dynatableQueues.paginationPage.set(1);
                        dynatableQueues.paginationPerPage.set(10);
                        dynatableQueues.process();
                        dynatableUsers.process();
                        dynatable.process();
                        $('.loading.tabla').fadeOut(500);
                        function getUserLog() {
                            var selectDate = $('#selectDate').data().date;
                            var selectFromTime = $('#selectFromTime').data().date;
                            var selectToTime = $('#selectToTime').data().date;
                            var userSelect = $('#userName').val();
                            var message = '';
                            var cont = true;

                            if (typeof userSelect == 'undefined' || userSelect == '') {
                                message = message + manUserName + '\n';
                                cont = false;
                            }
                            if (typeof selectDate == 'undefined' || selectDate == '') {
                                message = message + manDate + '\n';
                                cont = false;
                            }
                            if (typeof selectFromTime == 'undefined' || selectFromTime == '') {
                                selectFromTime = "";
                            }
                            if (typeof selectToTime == 'undefined' || selectToTime == '') {
                                selectToTime = "";
                            }
                            if ((selectFromTime == '' && selectToTime != '') || (selectFromTime != '' && selectToTime == '')) {
                                cont = false;
                                message = message + requiredDates + '\n';
                            }
                            if (!cont) {
                                alert(message);
                                return false;
                            }
                            $('.loading.tabla').show();
                            var tz = jstz.determine();                           
                            $.ajax({
                                url: '<%= request.getContextPath()%>/spring/services/ActivityLogWebServices/getUserActivityLog/' + login,
                                type: 'post',
                                data: '{"RequestActivityLogDTO": { ' +
                                        '"userName":  "' + $('#userName').val() + '",' +
                                        '"dayDate":  "' + selectDate + '",' +
                                        '"fromTime":  "' + selectFromTime + '",' +
                                        '"timeZoneId":  "' + tz.name() + '",' +
                                        '"toTime":  "' + selectToTime + '"' +
                                        '}' +
                                        '}',
                                contentType: 'application/json; charset=UTF-8',
                                crossDomain: true,
                                dataType: 'json',
                                success: function(response) {
                                    console.log(response);
                                    var recordsUsers = response.ActivityLogDTOContainer.users;
                                    var records = response.ActivityLogDTOContainer.activityLogs;
                                    var recordsQueues = response.ActivityLogDTOContainer.queues;
                                    var usersData = {employees: []};
                                    if (typeof recordsUsers == 'undefined') {
                                        var array = new Array();
                                        recordsUsers = array;
                                    } else if (typeof recordsUsers != 'undefined' && !(recordsUsers instanceof Array)) {
                                        var array = new Array();
                                        array.push(recordsUsers);
                                        recordsUsers = array;
                                    }
                                    for (index = 0; index < recordsUsers.length; ++index) {
                                        var userObject = {};
                                        columnName = "userName";
                                        userObject [columnName] = recordsUsers[index];
                                        usersData.employees.push(userObject);
                                    }

                                    if (typeof recordsQueues != 'undefined' && !(recordsQueues  instanceof Array)) {
                                        var array = new Array();
                                        array.push(recordsQueues);
                                        recordsQueues = array;
                                    }

                                    if (typeof records != 'undefined' && !(records instanceof Array)) {
                                        var array = new Array();
                                        array.push(records);
                                        records = array;
                                    }
                                    dynatableQueues.settings.dataset.originalRecords = recordsQueues;
                                    dynatableUsers.settings.dataset.originalRecords = usersData.employees;
                                    dynatable.settings.dataset.originalRecords = records;
                                    dynatable.sorts.clear();
                                    dynatable.sorts.add('date', -1);
                                    dynatable.paginationPerPage.set(20);
                                    dynatable.paginationPage.set(1);
                                    dynatableUsers.sorts.clear();
                                    dynatableUsers.sorts.add('userName', 1);
                                    dynatableUsers.paginationPage.set(1);
                                    dynatableUsers.paginationPerPage.set(10);
                                    dynatableQueues.sorts.clear();
                                    dynatableQueues.sorts.add('queueName', 1);
                                    dynatableQueues.paginationPage.set(1);
                                    dynatableQueues.paginationPerPage.set(10);
                                    dynatableQueues.process();
                                    dynatableUsers.process();
                                    dynatable.process();
                                    $('.loading.tabla').fadeOut(500);
                                }, error: function(request, status, error) {
                                    console.error(request.responseText);
                                    console.log(status);
                                    console.error(error);
                                    $('.loading.tabla').fadeOut(500);
                                }
                            });
                        }
                        ;
</script>
