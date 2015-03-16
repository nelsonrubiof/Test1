<%-- 
    Document   : usersonqueuesmonitor
    Created on : Aug 14, 2014, 2:23:57 PM
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
    <script src="js/dateformat.js"></script>
    <link rel="stylesheet" href="css/smoothness/jquery-ui-1.10.4.custom.css"/>
    <link rel="stylesheet" href="css/smoothness/jquery-ui-1.10.4.custom.min.css"/>
    <link rel="stylesheet" href="css/jquery.dynatable.css"/>
    <link rel="stylesheet" href="css/main.css"/>
    <link rel="stylesheet" href="css/bootstrap.css"/>
    <body> 
        <jsp:include page="../banner.jsp" />
        <div class="loading tabla"> </div>
        <div style="width:70%; text-align: center;" >
            <h2 class="text-muted"><s:text name="label.user.queue.monitor"/></h2>
        </div>  
        </br>
        <div style="width:70%; text-align: center;" >
            <div style="text-align: center; display: inline-block;">
                <label for="timeFilter" style="display: inline-block; padding-right: 20px;"><s:text name="label.time.period.min"/></label>
                <select id="timeFilter" name="timeFilter" class="form-control"  style="width:70px; display: inline-block;" >
                    <option value="1" >1</option>
                    <option value="2" >2</option>
                    <option value="3" >3</option>
                    <option value="4" >4</option>
                    <option value="5" >5 </option>
                    <option value="6" >6 </option>
                    <option value="7" >7</option>
                    <option value="8" >8</option>
                    <option value="9" >9</option>
                    <option value="10" selected="true" >10</option>
                </select>
            </div>
            <div id="requestDate" style=" padding-left: 20px; text-align: center; display: inline-block;">

            </div>
        </div>

        </br>
        </br>
        <div style="width:70%; text-align: center;" >
            <div style="text-align: center; display: inline-block;">
                <label for="corporateFilter" style=" padding-right: 20px; display: inline-block;"><s:text name="label.corporate"/></label>
                <select id="corporateFilter" style="display: inline-block;"  name="corporateFilter"></select>
            </div>

            <div style="text-align: center; display: inline-block;">
                <label for="queueFilter"  style= "padding-left:30px; padding-right: 20px; display: inline-block;"><s:text name="label.queue"/></label>
                <select id="queueFilter" name="queueFilter" style="display: inline-block;"></select>
            </div>  
        </div>
        </br>
        </br>
        <table  style="width: 70%;">   
            <tr>
                <td style="padding-left: 50px;">
                    <table id="data" class="table">
                        <thead>
                        <th data-dynatable-column="corporateName" > <s:text name="label.corporate"/></th>
                        <th data-dynatable-column="queueName"> <s:text name="label.queue.name"/></th>
                        <th data-dynatable-column="totalNumber"> <s:text name="label.total.user"/></th>
                        <th data-dynatable-column="users" > <s:text name="label.users"/></th>
                        </thead>
                        <tbody></tbody>
                    </table>  
                </td>
            </tr>
        </table> 
    </body>
    <script type="text/javascript">

        var login = <%= session.getAttribute("sessionId")%>;
        var jsonRecords = JSON.parse('[]');

        $('#data').dynatable({
            features: {
                paginate: false,
                search: false,
                recordCount: false,
                perPageSelect: false
            },
            dataset: {
                records: jsonRecords
            }
        });
        var dynatable = $('#data').data('dynatable');
        //$('.loading.tabla').fadeOut(500);
        $('.loading.tabla').show();
        $('#corporateFilter').append('<option value="">All</option>');
        $('#queueFilter').append('<option value="">All</option>');

        function getUsersOnQueues(isRefresh) {
            var timeFilter = $("#timeFilter").val() * 60;
            $.ajax({
                url: '<%= request.getContextPath()%>/spring/services/ActivityLogWebServices/getUsersOnQueues/' + timeFilter + '/' + login,
                type: 'post',
                crossDomain: true,
                dataType: 'json',
                success: function(response) {
                    console.log(response);
                    var corporates = new Array();
                    var queues = new Array();
                    var corporateFilterSelected = $("#corporateFilter").val();
                    var queueFilterSelected = $("#queueFilter").val();
                    var foundCorporateFilter = false;
                    var foundQueueFilter = false;
                    $("#corporateFilter").children().remove();
                    $("#queueFilter").children().remove();
                    $('#corporateFilter').append('<option value="">All</option>');
                    $('#queueFilter').append('<option value="">All</option>');
                    var records = response.UsersOnQueueDTOContainer.usersOnQueues;
                    var parsedData = {usersOnQueues: []};
                    if (typeof records != 'undefined') {
                        if (!(records instanceof Array)) {
                            var array = new Array();
                            array.push(records);
                            records = array;
                        }
                        for (var index = 0; index < records.length; ++index) {
                            var usersOnQueue = {};
                            usersOnQueue ['corporateName'] = records[index].corporateName;
                            usersOnQueue ['queueName'] = records[index].queueName;
                            usersOnQueue ['totalNumber'] = records[index].totalNumber;
                            var users = '';
                            if (typeof records[index].users && !(records[index].users instanceof Array)) {
                                var array = new Array();
                                array.push(records[index].users);
                                records[index].users = array;
                            }
                            var maxLine = 0;
                            for (var index2 = 0; index2 < records[index].users.length; ++index2) {
                                if (index2 == 0) {
                                    users = records[index].users[index2];
                                    maxLine = maxLine + 1;
                                } else {
                                    if (maxLine == 4) {
                                        users = users + '</br>' + records[index].users[index2];
                                        maxLine = 1;
                                    } else {
                                        users = users + ' : ' + records[index].users[index2];
                                        maxLine = maxLine + 1;
                                    }
                                }
                            }
                            usersOnQueue ['users'] = users;
                            if (corporates.length == 0) {
                                corporates.push(records[index].corporateName);
                                $('#corporateFilter').append('<option value="' + records[index].corporateName + '">' + records[index].corporateName + '</option>');
                            } else {
                                var found = false;
                                for (var index3 = 0; index3 < corporates.length; ++index3) {
                                    if (corporates[index3] == records[index].corporateName) {
                                        found = true;
                                    }
                                }
                                if (!found) {
                                    corporates.push(records[index].corporateName);
                                    $('#corporateFilter').append('<option value="' + records[index].corporateName + '">' + records[index].corporateName + '</option>');
                                }
                            }

                            if (queues.length == 0) {
                                queues.push(records[index].queueName);
                                $('#queueFilter').append('<option value="' + records[index].queueName + '">' + records[index].queueName + '</option>');
                            } else {
                                var found = false;
                                for (var index3 = 0; index3 < queues.length; ++index3) {
                                    if (queues[index3] == records[index].queueName) {
                                        found = true;
                                    }
                                }
                                if (!found) {
                                    queues.push(records[index].queueName);
                                    $('#queueFilter').append('<option value="' + records[index].queueName + '">' + records[index].queueName + '</option>');
                                }
                            }
                            parsedData.usersOnQueues.push(usersOnQueue);
                        }

                        if (corporateFilterSelected != '') {
                            for (var index = 0; index < corporates.length; ++index) {
                                if (corporates[index] == corporateFilterSelected) {
                                    foundCorporateFilter = true;
                                }
                            }
                            if (foundCorporateFilter) {
                                $('#corporateFilter').val(corporateFilterSelected);
                            } else {
                                $('#corporateFilter').val('');
                                dynatable.queries.remove("corporateName");
                            }
                        }

                        if (queueFilterSelected != '') {
                            for (var index = 0; index < queues.length; ++index) {
                                if (queues[index] == queueFilterSelected) {
                                    foundQueueFilter = true;
                                }
                            }
                            if (foundQueueFilter) {
                                $('#queueFilter').val(queueFilterSelected);
                            } else {
                                $('#queueFilter').val('');
                                dynatable.queries.remove("queueName");
                            }
                        }
                    }
                    dynatable.settings.dataset.originalRecords = parsedData.usersOnQueues;
                    if (!isRefresh) {
                        dynatable.sorts.clear();
                        dynatable.sorts.add('corporateName', -1);
                    }
                    dynatable.paginationPerPage.set(20);
                    dynatable.paginationPage.set(1);
                    dynatable.process();
                    $('.loading.tabla').fadeOut(500);
                }, error: function(request, status, error) {
                    console.error(request.responseText);
                    console.log(status);
                    console.error(error);
                    $('.loading.tabla').fadeOut(500);
                }
            });
            var now = new Date();
            $('#requestDate').html('<h4 class="text-muted"><s:text name="label.last.request"/>: <font color ="red">' + now.format('d/m/yy  HH:MM:ss') + '</font></h4>');
        }
        ;
        getUsersOnQueues(false);

        $('#corporateFilter').change(function() {
            var value = $(this).val();
            if (value === "") {
                dynatable.queries.remove("corporateName");
            } else {
                dynatable.queries.add("corporateName", value);
            }
            dynatable.process();
        });
        $('#queueFilter').change(function() {
            var value = $(this).val();
            if (value === "") {
                dynatable.queries.remove("queueName");
            } else {
                dynatable.queries.add("queueName", value);
            }
            dynatable.process();
        });

        window.setInterval(function() {
            getUsersOnQueues(true);
        }, 25000);

    </script>                   

</html>
