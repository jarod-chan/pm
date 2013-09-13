<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
	
	<style type="text/css">
		 #file_upload{
		 	margin-bottom: 0px;
		 }
		 
		 #uploadify_result,#uploadify_queue{
		 	margin-top: 5px;
		 }
		 
		 .uploadify_unit{
		 	margin-bottom: 5px;
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
		.fl_span .fl_id{
		 display: none;
		}
		.fl_span .fl_sn{
		  background-color:  #AAAAAA;
		  color: #FFFFFF;
		}
		
		.sp_itemfile{
			margin-right: 2px;
		}
		
		.sp_itemfile a{
			padding:0px 5px 0px 5px;
			background-color:  #AAAAAA;  
		    text-decoration: none;
		    color:#FFFFFF;
		    white-space: nowrap;
		}
		
		.sp_itemfile a:hover {
		    background-color:  #5BADFF;
		}
		.sp_itemfile a:visited {
		    color: #FFFFFF;
		}
	
		.open_td span{
			display: none;
		}
	</style>
	
	<script type="text/javascript">
	$(function(){
		
		$( ".uploadify_item" ).dialog({
			autoOpen: false,
			position: ["center", 100],
			width: 600,
			modal: true,
			open: function( event, ui ) {
				$("#uploadify_result").empty();
				$(".open_td .sp_itemfile").each(function(){
					var file_id = $(this).find("input[name='itemfileId']").val();
					var file_name = $(this).find("input[name='itemfileName']").val();
					var filedata={id:file_id,name:file_name};
		          	$("#uploadify_result").append(mkSpan(filedata));
				})
			},
			close: function( event, ui ) {
				$(".open_td").removeClass("open_td");
				$(this).dialog("close");
			},
			buttons: {
				'确定': function() {
					$(".open_td").empty();
					$(".fl_span").each(function(){
						var span=mkItemSpan($(this).data("fd"),$(this).find(".fl_sn").html());
						$(".open_td").append(span);
	    			})
	    			$(".open_td").removeClass("open_td");
					$(this).dialog("close");
				},
				'取消': function() {
					$(".open_td").removeClass("open_td");
					$(this).dialog("close");
				}
			}
		});
		
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
	        	var filedata={id:data,name:file.name};
	          	$("#uploadify_result").append(mkSpan(filedata));
	        }
	    });
	    
	    var fileSpan=$("<span class='fl_span'></span>");
		$("<span class='fl_sn'></span>").appendTo(fileSpan);
    	$("<span class='fl_name'></span>").appendTo(fileSpan);
    	$("<span class='fl_remove'><a href='javascript:void(0);''>--</a></span>").appendTo(fileSpan);
    	$("<span class='fl_id'><input type='hidden' name='filestore_id' /></span>").appendTo(fileSpan);
	    
	    function mkSpan(filedata){
	    	var sn=$(".fl_span").size()+1;
	    	var newspan=fileSpan.clone();
	    	var href=$("<a/>").html(filedata.name).attr("href","${ctx}/uploadify/filestore/"+filedata.id);
	    	newspan.data("fd",filedata)
	    		.find(".fl_sn").html(sn).end()
	    		.find(".fl_name").append(href).end()
	    		.find(".fl_remove a").click(function(){
	    			$(this).parent().parent().remove();
	    			$(".fl_span").each(function(idx){
						$(this).find(".fl_sn").html(idx+1);
	    			})
	    		}).end()
	    		.find(".fl_id").html(filedata.id).end();
	    	return newspan;
	    } 
	    
	    var itemfileSpan=$("<span class='sp_itemfile'></span>");
	    $("<a/>").appendTo(itemfileSpan);
	    $("<input type='hidden' name='itemfileName' />").appendTo(itemfileSpan);//缓存文件名
    	$("<input type='hidden' name='itemfileSn' />").appendTo(itemfileSpan);
    	$("<input type='hidden' name='itemfileId' />").appendTo(itemfileSpan);
    	
    	function mkItemSpan(filedata,sn){
    		var newSpan=itemfileSpan.clone();
    		var href="${ctx}/uploadify/filestore/"+filedata.id;
    		newSpan
    			.find("a").attr("href",href).html(sn).end()
    			.find("input[name='itemfileName']").val(filedata.name).end()
    			.find("input[name='itemfileId']").val(filedata.id);
    		return newSpan;
    	}
		
		
	})
	</script>
	
	<div class="uploadify_item" title="关联附件" style="display: none;">
		<div ><input id="file_upload" type="button" /></div>
		<div id="uploadify_result"></div>
		<div id="uploadify_queue" style="clear: both;"></div>
	</div>
	