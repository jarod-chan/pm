<%@ page language="java" pageEncoding="UTF-8"%>
<link href="${ctx}/css/base.css" type="text/css"  rel="stylesheet">
<link href="${ctx}/css/message.css" type="text/css"  rel="stylesheet">
<link href="${ctx}/css/component.css" type="text/css"  rel="stylesheet">

<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctx}/js/pathie.js" type="text/javascript"></script>
<script src="${ctx}/js/myplug.js" type="text/javascript"></script>
<script src="${ctx}/js/myfloat.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.metadata.js" type="text/javascript"></script>

<link href="${ctx}/plu/uploadify/uploadify.css" type="text/css"  rel="stylesheet">
<script src="${ctx}/plu/uploadify/jquery.uploadify.js" type="text/javascript"></script>

<script src="${ctx}/plu/layout/jquery.layout-latest.js" type="text/javascript"></script>

<script src="${ctx}/plu/template/jquery.tmpl.js" type="text/javascript"></script>

<!-- 校验处理 -->
<script src="${ctx}/plu/validation/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/plu/validation/messages_zh.js" type="text/javascript"></script>
<style type="text/css">
	label.vlderror {
		color: #ea5200;
	}
</style>
<script type="text/javascript">
	$(function() {
		$.validator.setDefaults({
			ignore: "",//处理必填字段
			errorClass : "vlderror"
		});
		//ajax参数全局设置
		jQuery.ajaxSettings.traditional = true; 
	})
</script>

<!-- 表格高亮 -->
<style type="text/css">
	.hctable .high-color{
		background-color: #C8C8C8;
	}
</style>
<script type="text/javascript">
	$(function(){
		$(".hctable tbody tr").mouseover(function() {
			$(this).addClass("high-color");
		}).mouseout(function() {
			$(this).removeClass("high-color");
		})		
	})
</script>

<!-- 业务界面输入文本框，日期控件样式  -->
<style type="text/css">
	.edittextarea{
	 	width:500px;
	 	height: 140px;
	}
	.viewtextarea_td{
		vertical-align: top;
		height: 35px;
	}
	.editdate{
		width:198px;
	}
	.edittext{
		width:198px;
	}
	.editlntext{
		width:500px;
	}
</style>

<!-- 表格统一样式处理 -->
<style type="text/css">
	table.deftable{
		border: 1px solid #AAAAAA;
		border-collapse: collapse;
	}
	
	table.deftable>thead>tr>th {
	    background-color: #D5D5D5;
	    border: 1px solid #AAAAAA;
	    vertical-align: baseline;
	}
	table.deftable>tbody>tr>td {
	    border: 1px solid #AAAAAA;
	    padding-left: 2px;
	    padding-right: 2px;
	}
</style>		

<!-- 单据状态显示不同颜色处理 -->
<style type="text/css">
	span.state {
		color: #FFFFFF;
		display: inline-block;
		line-height: 16px;
		padding: 2px 4px;
	}
	
	span.state-new_ {
		background-color:#75BAFF;
	}
	
	span.state-saved {
		background-color:  #0080FF;
	}
	
	span.state-commit {
		background-color: #00BF60;
	}
	
	span.state-finish {
		background-color: #B94A48;
	}
	
	span.state-invalid {
		background-color: #999999;
	}

</style>
<script type="text/javascript">
	$(function(){
		var optColorArr=['#FFFFFF','#FFFFFF','#75BAFF','#0080FF','#00BF60','#B94A48','#999999'];
    	$("select.bkcolor-state").each(function(){
			$(this).find("option").each(function(idx){
				$(this).css("background-color",optColorArr[idx]);
			});
			$(this).bind("change",function(){
				$(this).css("background-color",optColorArr[this.selectedIndex])
			}).triggerHandler("change");
		}); 
	})
</script>

<!-- span按钮处理 -->
<style type="text/css">
 .span_btn ,.span_btn_def{
 	padding: 0px 5px 0px 5px;
 	background: #C9C9C9;
 	color: #000000;
 	border: 1px #000 solid;
 	display: inline-block;
 }
.span_btn_mouseon{
 	cursor: pointer;
 	background-color: #1E8EFF;
 	color: #FFFFFF;
 }
 
 .span_btn_mouseon_red{
 	cursor: pointer;
 	background-color: #FF0000;
 	color: #FFFFFF;
 }
</style>
<script type="text/javascript">
 $(function() {
 	 $(".span_btn").hover(
		function () {
			$(this).addClass("span_btn_mouseon");
 		},
 		function () {
 			$(this).removeClass("span_btn_mouseon")
 		}
  	  )
  	   $(".span_btn_def").hover(
		function () {
			$(this).addClass("span_btn_mouseon_red");
 		},
 		function () {
 			$(this).removeClass("span_btn_mouseon_red")
 		}
  	  )
 })
</script>
