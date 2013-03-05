/* Demo Note:  This demo uses a FileProgress class that handles the UI for displaying the file name and percent complete.
The FileProgress class is not part of SWFUpload.
*/


/* **********************
   Event Handlers
   These are my custom event handlers to make my
   web application behave the way I went when SWFUpload
   completes different tasks.  These aren't part of the SWFUpload
   package.  They are part of my application.  Without these none
   of the actions SWFUpload makes will show up in my application.
   ********************** */
function fileQueued(file) {
	
	try {
		var  innerBox= $(document.getElementById(this.customSettings.progressTarget));
		var fileList = "<ul class='fileList'>";
		fileList += "<li id='" + file.id + "'>";
		fileList += "<p class='fileName'>" + file.name + "</p>";
		fileList += "<p><span class='progress'><s></s></span><span class='jdNum'>0%</span> <a href='#' class='delFile'>取消</a></p>";
		fileList += "<p class='info'>等待上传...</p>";
		fileList += "</li>";
		fileList += "</ul>";
		
		innerBox.prepend($(fileList));
		
		var self = this;
		
		$("a.delFile").click(function(event) {
			event.preventDefault();
			var p = $(this).parent().parent(),
				fid = p.attr("id");

			self.cancelUpload(fid, false);
			var stats = $("#" + file.id).find("p.info");
			stats.html("<s>上传文件被取消</s>");
			 $(this).hide();
			 $("#" + file.id).find(".progress s").css("background-color","#C0C0C0");
		});

	} catch (ex) {
		this.debug(ex);
	}

}

function fileQueueError(file, errorCode, message) {
	try {
		var  innerBox= $(document.getElementById(this.customSettings.progressTarget));
		var messagebox=$("<p class='messagebox'></p>");
		
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			messagebox.text("你一次选择太多的文件，一次最多上传"+message+"个文件");
			innerBox.prepend(messagebox);
			return;
		}
		
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			messagebox.text(file.name+"上传失败，文件太大");
			this.debug("Error Code: File too big, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			messagebox.text(file.name+"上传失败，不能上传0kb的文件");
			this.debug("Error Code: Zero byte file, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			messagebox.text(file.name+"上传失败，错误文件类型");
			this.debug("Error Code: Invalid File Type, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		default:
			if (file !== null) {
				messagebox.text(file.name+"上传失败，未知错误");
			}
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		}
		innerBox.prepend(messagebox);
		
	} catch (ex) {
        this.debug(ex);
    }
}

function fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		this.startUpload();
	} catch (ex)  {
        this.debug(ex);
	}
}

function uploadStart(file) {
	try {
		var stats = $("#" + file.id).find("p.info");
		stats.html("已上传<s class='yscSize'>0</s>Kb");
	}
	catch (ex) {}
	
	return true;
}

function uploadProgress(file, bytesLoaded, bytesTotal) {
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
}

function uploadSuccess(file, serverData) {
	try {
		var stats = $("#" + file.id).find("p.info");
		stats.html("<s>上传成功</s>");
		$("#" + file.id).find(".delFile").hide();
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	try {
		var stats = $("#" + file.id).find("p.info");

		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			stats.text("上传错误");
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
		
		$("#" + file.id).find(".delFile").hide();
		$("#" + file.id).find(".progress s").css("background-color","#C0C0C0");
	} catch (ex) {
        this.debug(ex);
    }
}

function uploadComplete(file) {
	this.startUpload();
}


