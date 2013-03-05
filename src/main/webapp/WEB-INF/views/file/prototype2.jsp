<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="pragma" content="no-cache" />

<style type="text/css">
body, td, th {
	font-size: 12px;
	color: #000000;
}
* {
	margin:0;
	padding:0;
}

.innerBox {
	width:500px;
	overflow:auto;
}

.fileList {
	width:96%;
	margin:5px auto;
	list-style:none;
	margin:0 auto;
	padding:0;
}
.fileList li {
	border-bottom:1px dashed #E3E3E3;
	padding:5px 0;
}
.fileList li p {
	width:100%;
	clear:both;
	overflow:hidden;
	zoom:-1;
	margin:5px auto;
}
.fileList li span {
	display:inline-block;
	float: left;
}
.fileList li s {
	text-decoration:none;
	color:#F00;
	margin:0 3px;
}
.progress {
	width:350px;
	border:1px solid #D5D5D5;
	height:12px;
	margin-right:10px;
}
.fileList li .delFile {
	color:#C00;
	margin-left:10px;
}
.fileList li .progress s {
	display:inline-block;
	background-color:#9C0;
	width:0%;
	height:12px;
	margin:0;
}

.messagebox {
    height: 50px;
    line-height: 50px;
    text-align: center;
    border-bottom: 1px dashed #E3E3E3;
}


</style>

<script type="text/javascript" src="/ct/resources/js/jquery.js"></script>
<script type="text/javascript" src="/ct/resources/plugin/swfupload3/swfupload.js"></script>
<script type="text/javascript" src="/ct/resources/plugin/swfupload3/swfupload.queue.js"></script>
<script type="text/javascript" src="/ct/resources/plugin/swfupload3/handlers.js"></script>



<script type="text/javascript">
var swfu;

window.onload = function() {
	var settings = {
		flash_url : "/ct/resources/plugin/swfupload3/swfupload.swf",
		upload_url: "/ct/prototype/upload",
		post_params: {"PHPSESSID" : ""},
		file_size_limit : "0",
		file_types : "*.*",
		file_types_description : "All Files",
		file_upload_limit : 100,
		file_queue_limit : 2,
		custom_settings : {
			progressTarget : "innerBox",
			cancelButtonId : "btnCancel"
		},
		debug: false,

		// Button settings		
		button_image_url: "/ct/resources/plugin/swfupload2/XPButtonNoText_61x22.png",
		button_width: "61",
		button_height: "22",
		button_placeholder_id: "spanButtonPlaceHolder",
		button_text: '上传',
		button_text_left_padding: 15,
		button_text_top_padding: 0,
		
		// The event handler functions are defined in handlers.js
		file_queued_handler : fileQueued,
		file_queue_error_handler : fileQueueError,
		file_dialog_complete_handler : fileDialogComplete,
		upload_start_handler : uploadStart,
		upload_progress_handler : uploadProgress,
		upload_error_handler : uploadError,
		upload_success_handler : uploadSuccess,
		upload_complete_handler : uploadComplete
	};

	swfu = new SWFUpload(settings);
 };		 
 
 
 
 
 
		 
</script>
</head>

<body>
<span id="spanButtonPlaceHolder"></span> <span id="message"></span>

<div class="innerBox" id="innerBox">

</div>


</body>


</html>


