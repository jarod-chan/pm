<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
	 #file_upload{
	 	margin-bottom: 0px;
	 }
	 
	 #uploadify_result,#uploadify_queue{
	 	margin-top: 5px;
	 }
	 
	 .uploadify_unit{
	 	margin-bottom: 5px;
	 	width: 600px;
	 }
	 
	 .fl_span{
	 	float:left;
	 	display:block;
	 	margin:5px 5px  0px  0px;
	 }
	 
	 .fl_name {
	 	background-color: #C0C0C0;
	 }
	 
	 .fl_name a {
	    background-color: #C0C0C0;  
	    text-decoration: none;
	    padding:0px 5px 0px 5px;
	    color:#000000;
	    white-space: nowrap;
	}
	.fl_name a:hover {
	    background-color:  #5BADFF;
	}
	.fl_name a:visited {
	    color: #000000;
	}
	 
	 .fl_remove a {
	    background-color:  #AAAAAA;  
	    text-decoration: none;
	    color:#000000;
	    white-space: nowrap;
	}
	.fl_remove a:hover {
	    background-color:  #5BADFF;
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
		        uploader      : '${ctx}/uploadify/upload;jsessionid=<%=session.getId()%>',
		        queueID : 'uploadify_queue',
		        removeTimeout : 0,
		        height        : 15,
		        width         : 80,
		        debug         : false,  
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
		          	$("#uploadify_result").append(mkSpan(file.name,data));
		        }
		    });
		    
 		    var fileSpan=$("<span class='fl_span'></span>");
	    	$("<span class='fl_name'></span>").appendTo(fileSpan);
	    	$("<span class='fl_remove'><a href='javascript:void(0);''>--</a></span>").appendTo(fileSpan);
	    	$("<span class='fl_id'><input type='hidden' name='filestore_id' /></span>").appendTo(fileSpan);
		    
		    function mkSpan(filename,file_id){
		    	var newspan=fileSpan.clone();
		    	var href=$("<a/>").html(filename).attr("href","${ctx}/uploadify/filestore/"+file_id);
		    	newspan.find(".fl_name").append(href).end()
		    		.find(".fl_remove a").click(function(){
		    			$(this).parent().parent().remove();
		    		}).end()
		    		.find(".fl_id input").val(file_id).end();
		    	return newspan;
		    } 
		    
		    $(".fl_span").find(".fl_remove a").click(function(){
	   			$(this).parent().parent().remove();
	   		});
		   
		});
	 	 
	</script>
		<div class="uploadify_unit"  <c:if test="${not empty upload_width}"> style="width:${upload_width}px;"</c:if> >
				<div ><input id="file_upload" type="button" /></div>
				<div id="uploadify_result">
					<c:forEach var="filestore" items="${filestores}">
						<span class="fl_span"> 
							<span class="fl_name"><a href="${ctx}/uploadify/filestore/${filestore.id}">${filestore.filename}.${filestore.suffix}</a></span><span class="fl_remove"><a href="javascript:void(0);">--</a></span>
							<span class="fl_id"><input type="hidden" name="filestore_id" value="${filestore.id}"></span>
						</span>
					</c:forEach>
				</div>
				<div id="uploadify_queue" style="clear: both;">
				</div>
		</div>