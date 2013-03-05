<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="pragma" content="no-cache" />

<style type="text/css">
<!--
body, td, th {
	font-size: 12px;
	color: #333;
}
* {
	margin:0;
	padding:0;
}
.upLoadBox {
	position:absolute;
	width:460px;
	height:300px;
	left:50%;
	margin-left:-230px;
	top:50%;
	margin-top:-150px;
	border:1px solid #000;
	background-color:#FFF;
	z-index:9999;
}
.upLoadTitle {
	height:28px;
	line-height:28px;
	background-color:#333;
	color:#FFF;
	cursor:move
}
.upLoadTitle span {
	float:left;
	margin-left:10px;
}
#upLoadForm {
	padding:10px;
	border-bottom:1px solid #D5D5D5;
}
#upLoadForm span {
	float:right;
	color:#666
}
.colseUplaodBox {
	color:#FFF;
	float:right;
	margin-right:10px;
	text-decoration:none
}
.innerBox {
	width:460px;
	height:226px;
	overflow:auto
}
#wrapBox {
	position:fixed;
	width:100%;
	height:100%;
	left:0;
	top:0;
	bottom:auto;
	background-color:#000;
	filter:alpha(opacity=50);
	-moz-opacity: 0.5;
	opacity: 0.5;
	z-index:9998;
}
/* 上面的是除了IE6的主流浏览器通用的方法 */
*html #wrapBox {/*ie6 hack*/
	position:absolute;
	bottom:auto;
 top:expression(eval(document.documentElement.scrollTop));
	zoom:-1;
}
/* 修正IE6振动bug */
* html, * html body {
	background-image:url(about:blank);
	background-attachment:fixed;
	height:100%;
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
.welcome {
	height:50px;
	line-height:50px;
	text-align:center;
}
-->
</style>

<script type="text/javascript" src="/ct/resources/js/jquery.js"></script>
<script type="text/javascript" src="/ct/resources/js/drag.js"></script>
<script type="text/javascript" src="/ct/resources/plugin/swfupload2/swfupload.js"></script>
<script type="text/javascript" src="/ct/resources/plugin/swfupload2/swfupload.queue.js"></script>
<script type="text/javascript" src="/ct/resources/plugin/swfupload2/handlers.js"></script>
<script type="text/javascript" src="/ct/resources/plugin/swfupload2/fileprogress.js"></script>



<script type="text/javascript">
var upLoadBox = {
		swfu: null,
		init: function(options) {
			var self = this;
			var defaults = {
				flash_url: "/ct/resources/plugin/swfupload2/swfupload.swf",
				flash9_url: "../swfupload/swfupload_fp9.swf",
				upload_url: "/ct/resources/plugin/swfupload2/upload",                               //asp上传程序
				post_params: {
					"PHPSESSID": ""
				},
				file_size_limit: 0,
				file_types: "*.*",
				file_types_description: "All Files",
				file_upload_limit: 100,
				file_queue_limit: 0,
				debug: false,

				// Button settings
				button_image_url: "/ct/resources/plugin/swfupload2/XPButtonNoText_61x22.png",
				button_width: "61",
				button_height: "22",
				button_placeholder_id: "spanButtonPlaceHolder",
				button_text: '浏览',
				button_text_left_padding: 15,
				button_text_top_padding: 0,

				// The event handler functions are defined 
				swfupload_preload_handler: self.preLoad,
				swfupload_load_failed_handler: self.loadFailed,
				file_queued_handler: self.fileQueued,
				file_queue_error_handler: self.fileQueueError,
				file_dialog_complete_handler: self.fileDialogComplete,
				upload_start_handler: self.uploadStart,
				upload_progress_handler: self.uploadProgress,
				upload_error_handler: self.uploadError,
				upload_success_handler: self.uploadSuccess,
				upload_complete_handler: self.uploadComplete
				//queue_complete_handler : queueComplete	// Queue plugin event
			}

			var opts = $.extend({}, defaults, options);

			var fileSizeLimit = !opts.file_size_limit ? "单文件大小无限制" : "单文件大小不超过" + opts.file_size_limit + "M";

			var boxHtml = "<div id='wrapBox'></div>";
			boxHtml += "<div class='upLoadBox'>";
			boxHtml += "<div class='upLoadTitle'><span>上传文件</span> <a href='#' class='colseUplaodBox'>[关闭]</a></div>";
			boxHtml += "<form id='upLoadForm' action='index.asp' method='post' enctype='multipart/form-data'>";
			boxHtml += "<span id='spanButtonPlaceHolder'></span> <span>(" + fileSizeLimit + ")</span>";
			boxHtml += "</form>";
			boxHtml += "<div class='innerBox'>";
			boxHtml += "<p class='welcome'>欢迎使用批量上传组件</p>";
			boxHtml += "</div>";
			boxHtml += "</div>";


			$("body").append($(boxHtml));

			var upLoadBox = $("div.upLoadBox"),
				colseUplaodBox = upLoadBox.find("a.colseUplaodBox");

			//拖曳窗口
			upLoadBox.drag({
				handler: $('.upLoadTitle'),
				opacity: 0.8
			});

			//关闭窗口
			colseUplaodBox.click(function(event) {
				event.preventDefault();
				self.colseUploadBox();
			});


			//初始化swfupland组件
			self.swfu = new SWFUpload(options);
		},
		//关闭窗口	
		colseUploadBox: function() {
			var upLoadBox = $("div.upLoadBox"),
				colseUplaodBox = upLoadBox.find("a.colseUplaodBox"),
				wrapBox = $("#wrapBox");
			upLoadBox.empty().remove();
			wrapBox.remove();
		},
		preLoad: function() {
			if (!this.support.loading) {
				alert("需要安装9.028以上的flashPlayer");
				return false;
			}
		},
		loadFailed: function() {
			function loadFailed() {
				alert("加载失败");
			}
		},
		fileQueued: function(file) {
			try {
				var innerBox = $("div.innerBox"),
					welcome = $("p.welcome");
				var fileList = "<ul class='fileList'>";
				fileList += "<li id='" + file.id + "'>";
				fileList += "<p class='fileName'>" + file.name + "</p>";
				fileList += "<p><span class='progress'><s></s></span><span class='jdNum'>0%</span> <a href='#' class='delFile'>删除</a></p>";
				fileList += "<p class='info'>等待上传...</p>";
				fileList += "</li>";
				fileList += "</ul>";

				welcome.remove();
				innerBox.append($(fileList));

				$("a.delFile").click(function(event) {
					event.preventDefault();
					var p = $(this).parent().parent(),
						fid = p.attr("id");

					upLoadBox.swfu.cancelUpload(fid, false);
					p.remove();
				});

			} catch (ex) {
				this.debug(ex);
			}
		},
		fileQueueError: function(file, errorCode, message) {
			var welcome = $("p.welcome");
			try {
				if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
					alert("你一次上传的文件超过设置");
					return;
				}

				switch (errorCode) {
				case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
					welcome.text("错误提示：文件太大，文件名： " + file.name + ", 文件大小： " + file.size + ", Message: " + message);
					this.debug("错误提示：文件太大，文件名： " + file.name + ", 文件大小： " + file.size + ", Message: " + message);
					break;
				case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
					welcome.text("错误提示：零字节文件，文件名：" + file.name + ", 文件大小： " + file.size + ", Message: " + message);
					this.debug("错误提示：零字节文件，文件名：" + file.name + ", 文件大小： " + file.size + ", Message: " + message);
					break;
				case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
					welcome.text("错误提示：无效的文件类型，文件名：" + file.name + ", 文件大小：" + file.size + ", Message: " + message);
					this.debug("错误提示：无效的文件类型，文件名：" + file.name + ", 文件大小：" + file.size + ", Message: " + message);
					break;
				default:
					if (file !== null) {
						welcome.text("未处理的错误");
					}
					this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
					break;
				}
			} catch (ex) {
				this.debug(ex);
			}
		},
		fileDialogComplete: function(numFilesSelected, numFilesQueued) {
			try {
				this.startUpload();
			} catch (ex) {
				this.debug(ex);
			}
		},
		uploadStart: function(file) {
			try {
				var stats = $("#" + file.id).find("p.info");
				stats.html("已上传<s class='yscSize'>0</s>Kb");
			} catch (ex) {}
			return true;
		},
		uploadProgress: function(file, bytesLoaded, bytesTotal) {
			try {
				var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
				var fileList = $("#" + file.id),
					progress = fileList.find(".progress"),
					jdNum = fileList.find(".jdNum"),
					yscSize = fileList.find("s.yscSize");

				progress.find("s").css("width", percent + "%");
				jdNum.html(percent + "%");
				yscSize.text(bytesLoaded);

			} catch (ex) {
				this.debug(ex);
			}
		},
		uploadSuccess: function(file, serverData) {
			try {
				var stats = $("#" + file.id).find("p.info");
				stats.html("<s>上传成功</s>");

			} catch (ex) {
				this.debug(ex);
			}
		},
		uploadError: function(file, errorCode, message) {
			try {
				var stats = $("#" + file.id).find("p.info");

				switch (errorCode) {
				case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
					stats.text("上传错误: " + message);
					this.debug("错误提示：http错误，文件名：" + file.name + ", 信息： " + message);
					break;
				case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
					stats.text("上传失败");
					this.debug("错误提示：上传失败，文件名称: " + file.name + ", 文件大小：" + file.size + ", 信息：" + message);
					break;
				case SWFUpload.UPLOAD_ERROR.IO_ERROR:
					stats.text("服务器错误");
					this.debug("错误提示：服务器错误, 文件名称: " + file.name + ", 信息： " + message);
					break;
				case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
					progress.setStatus("安全性错误");
					this.debug("错误提示： 安全性错误, 文件名称: " + file.name + ", 信息： " + message);
					break;
				case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
					progress.setStatus("上传超过限制。");
					this.debug("错误提示：上传超过限制,文件名称:  " + file.name + ", 文件大小： " + file.size + ", 信息：" + message);
					break;
				case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
					progress.setStatus("验证失败。上传跳过。");
					this.debug("错误提示： 验证失败，上传跳过。文件名称: " + file.name + ", 文件大小： " + file.size + ", 信息：" + message);
					break;
				case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
					break;
				case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
					stats.text("停止");
					break;
				default:
					stats.text("未知错误: " + errorCode);
					this.debug("错误提示： " + errorCode + ", 文件名称: " + file.name + ", 文件大小：" + file.size + ", 信息： " + message);
					break;
				}
			} catch (ex) {
				this.debug(ex);
			}
		},
		uploadComplete: function(file) {
			console.log(this);
			var self = this;
			if (file.filestatus != -5) {
				$.ajax({
					type: "POST",
					url: "/ct/prototype/uploadAjax",
					data: file,
					success: function(ret) {
						if (ret != "" && self.getStats().files_queued !== 0) {
							self.startUpload();
						}
					}
				});

			} else {
				self.startUpload();
			}
		}
	};

	  
	 
	 window.onload = function() {
		       //swfupload 设置
				var settings = {
					flash_url : "/ct/resources/plugin/swfupload2/swfupload.swf",
					flash9_url : "../swfupload/swfupload_fp9.swf",
					upload_url: "/ct/prototype/upload",
					post_params: {"PHPSESSID" : ""},
					file_size_limit :0,
					file_types : "*.*",
					file_types_description : "All Files",
					file_upload_limit : 100,
					file_queue_limit : 0,
					debug:false,

					// Button settings
					button_image_url: "/ct/resources/plugin/swfupload2/XPButtonNoText_61x22.png",
					button_width: "61",
					button_height: "22",
					button_placeholder_id: "spanButtonPlaceHolder",
					button_text: '浏览',
					button_text_left_padding: 15,
					button_text_top_padding: 0,
					
					// The event handler functions are defined in handlers.js
					swfupload_preload_handler : upLoadBox.preLoad,
					swfupload_load_failed_handler : upLoadBox.loadFailed,
					file_queued_handler : upLoadBox.fileQueued,
					file_queue_error_handler : upLoadBox.fileQueueError,
					file_dialog_complete_handler : upLoadBox.fileDialogComplete,
					upload_start_handler : upLoadBox.uploadStart,
					upload_progress_handler : upLoadBox.uploadProgress,
					upload_error_handler : upLoadBox.uploadError,
					upload_success_handler : upLoadBox.uploadSuccess,
					upload_complete_handler : upLoadBox.uploadComplete
					//queue_complete_handler : queueComplete	// Queue plugin event
				};
	    		
	 	     $("#button").click(function(){
							           upLoadBox.init(settings);   
												   });
			
	  	     };
			 
		 
	</script>
	</head>
	<body>
	<label>
	  <input type="button" name="button" id="button" value="上传文件" />
	</label>
	</body>
	</html>


