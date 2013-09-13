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
		    display:none;
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
		<div id="uploadify_result"></div>
		<div id="uploadify_queue" style="clear: both;"></div>
	</div>
	