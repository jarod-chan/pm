<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	


    <script type="text/javascript">
    $(function() {
		$("#btn_save").click(function(){
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action");
			actionFrom.attr("action",oldAction+"/save").submit();
		});
    });
    </script>
    
	<style type="text/css">
	 .uploadify{
	 	float: left;
	 }
	 
	 .fl_name {
	 
	 	background-color: #5BADFF;
	 }
	 
	 .fl_remove a {
	    background-color: #8080FF;
	    text-decoration: none;
	    color:#000000;
	    white-space: nowrap;
	}
	.fl_remove a:hover {
	    background-color: #FECF78;
	}
	.fl_remove a:visited {
	    color: #000000;
	}
	</style>
    
    <script type="text/javascript">
		$(function() {
		    $("#file_upload").uploadify({
		    	buttonText    : '上传文件',
		        swf           : '${ctx}/plu/uploadify/uploadify.swf',
		        uploader      : '${ctx}/uploadify/upload',
		        queueID : 'uploadify_queue',
		        removeTimeout : 0,
		        height        : 15,
		        width         : 80,
		        //返回一个错误，选择文件的时候触发
		        onSelectError:function(file, errorCode, errorMsg){
		            switch(errorCode) {
		                case -100:
		                    alert("上传的文件数量已经超出系统限制的"+$('#file_upload').uploadify('settings','queueSizeLimit')+"个文件！");
		                    break;
		                case -110:
		                    alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#file_upload').uploadify('settings','fileSizeLimit')+"大小！");
		                    break;
		                case -120:
		                    alert("文件 ["+file.name+"] 大小异常！");
		                    break;
		                case -130:
		                    alert("文件 ["+file.name+"] 类型不正确！");
		                    break;
		            }
		        },
		        //检测FLASH失败调用
		        onFallback:function(){
		            alert("您未安装FLASH控件，无法上传文件！请安装FLASH控件后再试。");
		        },
		        //上传到服务器，服务器返回相应信息到data里
		        onUploadSuccess:function(file, data, response){
		          	$("#uploadify_result").html(file.name);
		        }
		    });
		    
		    function mkSpan(filename,file){
		    	//todo :  make span and append;
		    }
		    
		    $(".fl_remove a").click(function(){
		    	$(this).parent().parent().remove();
		    })
		});
	 
	 	 
	</script>
</head>

<body>
	<h2>合同</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/contract" method="post">
		编号：
		<input type="text" name="no" value=""/>
		
		<br>
		合同名称：
		<input type="text" name="name" value=""/>
		
		<br>
		所属项目:
		<select name="project.id">
			<c:forEach var="project" items="${projectList}">
				<option value="${project.id}">${project.name}</option>
			</c:forEach>
		</select>
		<br>
		供应商:
		<select name="supplier.id">
			<c:forEach var="supplier" items="${supplierList}">
				<option value="${supplier.id}">${supplier.name}</option>
			</c:forEach>
		</select>
		<br>
		
		结算对象：
		<input type="text" name="payname" value=""/>
		<br>
		
		<div style="float: left;width: 80px;">合同文件：</div>
		<input id="file_upload" type="button" >
		<div style="clear: both;"  id="uploadify_result">
			<span>
				<span class="fl_name">filename</span><span class="fl_remove"><a href="javascript:void(0);">--</a></span>
				<span><input type="hidden" name="file_id" value="2"></span>
			</span>
			
			<span>
				<span class="fl_name">filename</span><span class="fl_remove"><a href="javascript:void(0);">--</a></span>
				<span><input type="hidden" name="file_id" value="2"></span>
			</span>
		</div>
		<div id="uploadify_queue">
			
		</div>
		<br>
		<div></div>
		<input type="button" value="保存"  id="btn_save">
		
	</form>
	
</body>
</html>
