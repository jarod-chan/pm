<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Insert title here</title>
    <link href="/ct/resources/css/default.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/ct/resources/demo/swfupload.js"></script>
    <script type="text/javascript" src="/ct/resources/demo/swfupload.queue.js"></script>
    <script type="text/javascript" src="/ct/resources/demo/fileprogress.js"></script>
    <script type="text/javascript" src="/ct/resources/demo/handlers.js"></script>
    <script type="text/javascript" src="/ct/resources/demo/jquery-1.4.3.js"></script>
    <script type="text/javascript" src="/ct/resources/demo/jquery.swfupload.js"></script>

    <style type="text/css">
        #swfupload-control p {
            margin: 10px 5px;
            font-size: 0.9em;
        }

        #log {
            margin: 0;
            padding: 0;
            width: 500px;
        }

        #log li {
            list-style-position: inside;
            margin: 2px;
            border: 1px solid #ccc;
            padding: 10px;
            font-size: 15px;
            font-family: Arial, Helvetica, sans-serif;
            color: #333;
            background: #fff;
            position: relative;
        }

        #log li .progressbar {
            border: 1px solid #333;
            height: 18px;
            background: #fff;
        }

        #log li .progress {
            background: rgba(104, 133, 127, 0.52);
            width: 0%;
            height: 18px;
        }

        #log li p {
            margin: 0;
            line-height: 18px;
        }

        #log li.success {
            border: 1px solid #339933;
            background: #ccf9b9;
        }

        #log li span.stopUpload {
            position: absolute;
            top: 5px;
            right: 5px;
            width: 20px;
            height: 20px;
            background: url('/ct/resources/demo/cancel.png') no-repeat;
            cursor: pointer;
        }

        #img span.cancel {
            background: url('/ct/resources/demo/cancel.png') no-repeat;
        }
    </style>

    <script type="text/javascript">
        $(function () {
            $('#swfupload-control').swfupload({
                upload_url:"/ct/jquery/doupload4",
                file_post_name:'uploadfile',
                file_size_limit:"20000 MB",
                file_types:"*.*",
                file_types_description:"All files",
                flash_url:"/ct/resources/demo/swfupload.swf",
                button_image_url:'/ct/resources/demo/wdp_buttons_upload_114x29.png',
                button_width:114,
                button_height:29,
                button_placeholder:$('#button')[0]
            })
                    .bind('fileQueued', function (event, file) {
                        var listitem = '<li id="' + file.id + '" >' +
                                'File: <em>' + file.name + '</em> (' + Math.round(file.size / 1024) + ' KB) <span class="progressvalue" ></span>' +
                                '<div class="progressbar" ><div class="progress" ></div></div>' +
                                '<p class="status" >Pending</p>' +
                                '<span class="stopUpload" >&nbsp;</span>' +
                                '</li>';
                        $('#log').append(listitem);
                        $('li#' + file.id + ' .stopUpload').bind('click', function () { //Remove from queue on cancel click
                            var swfu = $.swfupload.getInstance('#swfupload-control');
                            swfu.cancelUpload(file.id);
                            $('#log li#' + file.id).find('p.status').text('Canceled');
                            $('#log li#' + file.id).find('span.stopUpload').css('background-image','none')
                            $('#log li#' + file.id).find('span.stopUpload').css('cursor','defult')
//                            $('li#' + file.id).slideUp('fast');
                        });
                        // start the upload since it's queued
                        $(this).swfupload('startUpload');
                    })
                    .bind('fileQueueError', function (event, file, errorCode, message) {
                        //alert('Size of the file '+file.name+' is greater than limit');
                        alert(errorCode+":"+message)

                    })
                    .bind('fileDialogComplete', function (event, numFilesSelected, numFilesQueued) {
                        $('#queuestatus').text('Files Selected: ' + numFilesSelected + ' / Queued Files: ' + numFilesQueued);
                    })
                    .bind('uploadStart', function (event, file) {
                        $('#log li#' + file.id).find('p.status').text('Uploading...');
                        $('#log li#' + file.id).find('span.progressvalue').text('0%');
                        $('#log li#' + file.id).find('span.cancel').hide();
                    })
                    .bind('uploadProgress', function (event, file, bytesLoaded) {
                        //Show Progress
                        var percentage = Math.round((bytesLoaded / file.size) * 100);
                        $('#log li#' + file.id).find('div.progress').css('width', percentage + '%');
                        $('#log li#' + file.id).find('span.progressvalue').text(percentage + '%');
                    })
                    .bind('uploadSuccess', function (event, file, serverData) {
                        var item = $('#log li#' + file.id);
                        item.find('div.progress').css('width', '100%');
                        item.find('span.progressvalue').text('100%');
                        item.addClass('success').find('p.status').html('Done!!! ');
                        $('#log li#' + file.id).find('span.stopUpload').css('background-image','none') ;
                        $('#log li#' + file.id).find('span.stopUpload').css('cursor','default') ;
                    })
                    .bind('uploadComplete', function (event, file) {
                        // upload has completed, try the next one in the queue
                        $(this).swfupload('startUpload');
                    })
        });
    </script>
</head>
<body>

<div id="swfupload-control">
	<p>Select files of any type, muliple files are allowed to select every time.</p>
    <input type="button" id="button"/>

    <p id="queuestatus"></p>
    <ol id="log"></ol>
</div>
</body>
</html>