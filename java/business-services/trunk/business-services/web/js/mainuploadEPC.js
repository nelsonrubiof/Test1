var BASE_URL = "";//http://173.204.188.250:8080/business-services/";
//var BASE_URL=""; //Localhost
$(document).ready(function() {
    $('.btn-file :file').on('fileselect', function(event, numFiles, label) {
        var exts = ['csv'];
        if (label) {
            var get_ext = label.split('.');
            get_ext = get_ext.reverse();
            if ($.inArray(get_ext[0].toLowerCase(), exts) > -1) {
                $('#fileName').text(label);
                $('#uploadButton').removeClass('disabled');
            } else {
                alert('Invalid File Format \n The file must be in csv format.');
            }
            console.log(numFiles);
            console.log(label);
        }
    });
    $(document).on('change', '.btn-file :file', function() {
        var input = $(this),
                numFiles = input.get(0).files ? input.get(0).files.length : 1,
                label = input.val().replace(/\\/g, '/').replace(/.*\//, '');
        input.trigger('fileselect', [numFiles, label]);
    });
});
$(window).ready(function() {
    $('#uploadCSVEpc').submit(function(event) {
        $('#loadingIndicator1').show();
        $('#uploadButton').addClass('disabled');
        event.stopPropagation();
        event.preventDefault();
        var data = new FormData();
        $.each($(this).find('input[type=file]'), function(key, value) {
            data.append($(value).attr('name'), value.files[0]);
            console.log(value.files[0]);
            console.log($(value).attr('name') + ': ' + value.files[0]);
        });

        $.ajax({
            url: BASE_URL + 'spring/services/rest/uploadExtractionPlanCustomizing',
            type: 'post',
            data: data,
            cache: false,
            contentType: 'multipart/form-data',
            processData: false,
            crossDomain: true,
            dataType: 'text',
            success: function(response) {
                $('#loadingIndicator1').hide();
                $('#resultDiv').show();
                $('#uploadButton').addClass('disabled');
                $('#resultUpload').html(response);
                $('#inputFile').val('');
                $('#fileName').text('');
                cargarFiles();
            }, error: function(request, status, error) {
                $('#loadingIndicator1').show();
                $('#resultDiv').show();
                $('#uploadButton').addClass('disabled');
                console.error("1" + request.responseText);
                console.log(status);
                console.error(error);
            }
        });
    });

    cargarFiles();
    function cargarFiles() {
        $('#loadingIndicator2').show();
        $.ajax({
            url: BASE_URL + 'spring/services/rest/allFiles',
            type: 'get',
            cache: false,
            processData: false,
            crossDomain: true,
            dataType: 'text',
            success: function(response) {
                $('#loadingIndicator2').hide();
                $('#tableFiles').html('<thead> <tr> <th>Number</th> <th>Folder</th> <th>File Name</th> </tr> </thead>');
                var obj = $.parseJSON(response.valueOf());
                var html = "";
                var counter = 1;
                if (obj.fileDownloadList.data instanceof Array) {
                    $.each(obj.fileDownloadList.data, function(key, value) {
                        //console.log(value.folder);
                        //console.log(value.fileName);
                        html += '<tr> <td>' + counter + '</td> <td>' + value.folder + '</td> <td><a href="' + BASE_URL + 'spring/services/rest/getFile/' + value.folder + '/' + value.fileName + '">' + value.fileName + '</a></td> </tr>';
                        counter++;
                    });
                } else {
                    var value = obj.fileDownloadList.data;
                    html += '<tr> <td>' + counter + '</td> <td>' + value.folder + '</td> <td><a href="' + BASE_URL + 'spring/services/rest/getFile/' + value.folder + '/' + value.fileName + '">' + value.fileName + '</a></td> </tr>';
                }
                $('#tableFiles').append(html);
            }, error: function(request, status, error) {
                $('#loadingIndicator2').hide();
                console.error("1" + request.responseText);
                console.log(status);
                console.error(error);
            }
        });
    }
});