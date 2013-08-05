<%@ page language="java" pageEncoding="UTF-8"%>
<link href="${ctx}/css/base.css" type="text/css"  rel="stylesheet">

<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctx}/js/pathie.js" type="text/javascript"></script>
<script src="${ctx}/js/myplug.js" type="text/javascript"></script>
<script src="${ctx}/js/myfloat.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.metadata.js" type="text/javascript"></script>

<link href="${ctx}/plu/uploadify/uploadify.css" type="text/css"  rel="stylesheet">
<script src="${ctx}/plu/uploadify/jquery.uploadify.js" type="text/javascript"></script>

<script src="${ctx}/plu/layout/jquery.layout-latest.js" type="text/javascript"></script>

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
	})
</script>

<style type="text/css">
	.high-color{
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

<!-- 业务界面统一css  -->
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
</style>

<!-- 表格统一样式处理 -->
<style type="text/css">

table.deftable{
	border: 1px solid #AAAAAA;
}

table.deftable>thead>tr>th {
    background-color: #D5D5D5;
    border: 1px solid #AAAAAA;
    vertical-align: baseline;
}
table.deftable>tbody>tr>td {
    border: 1px solid #AAAAAA;
}
</style>		



