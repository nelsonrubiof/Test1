<%@ taglib prefix="s" uri="/struts-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <html xmlns="http://www.w3.org/1999/xhtml">
        <head>
            <title><s:text name="application.title"/> </title>
        </head>
        <link rel="stylesheet" href="css/smoothness/jquery-ui-1.10.4.custom.css"/>
        <link rel="stylesheet" href="css/smoothness/jquery-ui-1.10.4.custom.min.css"/>
        <link rel="stylesheet" href="css/jquery.dynatable.css"/>
        <link rel="stylesheet" href="css/main.css"/>
        <link rel="stylesheet" href="css/bootstrap.css"/>
        <script src="js/jquery.js"></script>
        <script src="js/jquery.getUrlParam.js"></script>
        <script src="js/bootstrap.js"></script>
        <script src="js/jquery-ui-1.10.4.custom.min.js"></script>
        <script src="js/jquery.dynatable.js"></script>
        <body>
            <jsp:include page="banner.jsp" />
            <s:url id="queuePriority" namespace="/" action="editOperatorQueuePriority"/>
            <s:url id="subscription" namespace="/" action="subscription"/>
            <s:url id="listSubscription" namespace="/" action="listSubscription"/>
            <s:url id="userGroup" namespace="/" action="userGroup"/>
            <s:url id="activityLog" namespace="/" action="activityLog"/>
            <s:url id="usersOnQueuesMonitor" namespace="/" action="usersOnQueuesMonitor"/>
            <s:url id="usersLastActivity" namespace="/" action="usersLastActivity"/>
            <table style="width: 60%;">
                <tr>                  
                    <td  style="text-align: center; padding-bottom: 20px;"><h3><s:text name="label.live.stats"/></h3></td>
                </tr>
            </table>
            <table style="width: 70%;">
                <tr >
                    <td style="padding-bottom: 20px; padding-left: 50px;"><h5><s:text name="label.active.users"/></h5></td>
                    <td style="padding-bottom: 20px; padding-left: 50px;"><h5><s:text name="label.queues.evaluated.stats"/></h5></td>
                </tr>
                <tr>
                    <td style="vertical-align: top; padding-left: 50px;" >
                        <table id="dataUsers" class="table">
                            <thead>
                                <th data-dynatable-column="userName"> <s:text name="label.user.name"/></th>
                            </thead>
                            <tbody></tbody>
                        </table>  
                    </td>
                    <td style="vertical-align: top; padding-left: 50px;" >
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
            <br/>
            <br/>   

            <table width="70%" align="center" style="text-align:center;vertical-align:middle;">
                <tr>
                    <td style="vertical-align:middle;text-align:right;">
                        <s:a href="%{queuePriority}" indicator="waitDiv" showLoadingText="false" targets="principal" cssStyle="color:blue;">
                            <s:text name="queue.priority.management"/>
                        </s:a>
                    </td>
                    <td style="vertical-align:middle;text-align:left;">
                        <s:a href="%{queuePriority}" indicator="waitDiv" showLoadingText="false" targets="principal">
                            <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" />
                        </s:a>
                    </td>
                    <td style="vertical-align:middle;text-align:right;">
                        <s:a href="%{subscription}" indicator="waitDiv" showLoadingText="false" targets="principal" cssStyle="color:blue;">
                            <s:text name="user.op.queue.subscription"/>
                        </s:a>
                    </td>
                    <td style="vertical-align:middle;text-align:left;">
                        <s:a href="%{subscription}" indicator="waitDiv" showLoadingText="false" targets="principal">
                            <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" />
                        </s:a>
                    </td>
                </tr>
                <tr>
                    <td style="vertical-align:middle;text-align:right;">
                        <s:a href="%{listSubscription}" indicator="waitDiv" showLoadingText="false" targets="principal" cssStyle="color:blue;">
                            <s:text name="user.subscription.listing"/>
                        </s:a>
                    </td>
                    <td style="vertical-align:middle;text-align:left;">
                        <s:a href="%{listSubscription}" indicator="waitDiv" showLoadingText="false" targets="principal">
                            <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" />
                        </s:a>
                    </td>
                    <td style="vertical-align:middle;text-align:right;">
                        <s:a href="%{userGroup}" indicator="waitDiv" showLoadingText="false" targets="principal" cssStyle="color:blue;">
                            <s:text name="user.group.management"/>
                        </s:a>
                    </td>
                    <td style="vertical-align:middle;text-align:left;">
                        <s:a href="%{userGroup}" indicator="waitDiv" showLoadingText="false" targets="principal">
                            <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" />
                        </s:a>
                    </td>

                </tr>
                <tr>
                    <td style="vertical-align:middle;text-align:right;">
                        <s:a href="%{activityLog}" indicator="waitDiv" showLoadingText="false" targets="principal" cssStyle="color:blue;">
                            <s:text name="activity.log"/>
                        </s:a>
                    </td>
                    <td style="vertical-align:middle;text-align:left;">
                        <s:a href="%{activityLog}" indicator="waitDiv" showLoadingText="false" targets="principal">
                            <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" />
                        </s:a>
                    </td>
                    <td style="vertical-align:middle;text-align:right;">
                        <s:a href="%{usersOnQueuesMonitor}" indicator="waitDiv" showLoadingText="false" targets="principal" cssStyle="color:blue;">
                            <s:text name="online.operators"/>
                        </s:a>
                    </td>
                    <td style="vertical-align:middle;text-align:left;">
                        <s:a href="%{usersOnQueuesMonitor}" indicator="waitDiv" showLoadingText="false" targets="principal">
                            <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" />
                        </s:a>
                    </td>
                </tr>
                <tr>
                    <td style="vertical-align:middle;text-align:right;">
                        <s:a href="%{usersLastActivity}" indicator="waitDiv" showLoadingText="false" targets="principal" cssStyle="color:blue;">
                            <s:text name="operators.last.activity"/>
                        </s:a>
                    </td>
                    <td style="vertical-align:middle;text-align:left;">
                        <s:a href="%{usersLastActivity}" indicator="waitDiv" showLoadingText="false" targets="principal">
                            <img src="<%= request.getContextPath() + "/img/scopix_logo_final_143_59.jpg"%>" />
                        </s:a>
                    </td>
                    <td>
                    </td>
                    <td>
                    </td>
                </tr>
            </table>
        </body>
        <script type="text/javascript">
            var login = <%= session.getAttribute("sessionId")%>;
            var jsonRecords = JSON.parse('[]');
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
            dynatableUsers.paginationPage.set(1);
            dynatableUsers.paginationPerPage.set(10);
            dynatableQueues.paginationPage.set(1);
            dynatableQueues.paginationPerPage.set(10);
            dynatableQueues.process();
            dynatableUsers.process();

            function getUserLog() {
                $.ajax({
                    url: '<%= request.getContextPath()%>/spring/services/ActivityLogWebServices/getUsersActivityLogStats/' + login,
                    type: 'post',
                    contentType: 'application/json; charset=UTF-8',
                    crossDomain: true,
                    dataType: 'json',
                    success: function(response) {
                        console.log(response);
                        var recordsUsers = response.ActivityLogDTOContainer.users;
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
                        dynatableQueues.settings.dataset.originalRecords = recordsQueues;
                        dynatableUsers.settings.dataset.originalRecords = usersData.employees;
                        dynatableQueues.process();
                        dynatableUsers.process();
                    }, error: function(request, status, error) {
                        console.error(request.responseText);
                        console.log(status);
                        console.error(error);
                    }
                });
            }
            ;
            getUserLog();
            window.setInterval(function() {
                getUserLog();
            }, 25000);
        </script>                   

    </html>