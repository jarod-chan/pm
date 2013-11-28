<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<style type="text/css">
.div_help {
	text-align: right;
	padding-top: 10px;
	vertical-align: bottom;
}
</style>
<script type="text/javascript">
			$(function(){
				$("#btn_tuser").click(function(){
					$.get("${ctx}/help/tuser", function(data){
					  $("#div_content").html(data);
					  $("#btn_hide").show();
					});
				})
				$("#btn_update").click(function(){
					$.get("${ctx}/help/update", function(data){
					  $("#div_content").html(data);
					  $("#btn_hide").show();
					});
				})
				$("#btn_hide").click(function(){
					$("#div_content").html("");
					$("#btn_hide").hide();
				})
			})
		</script>
<div class="div_help">
	<span id="btn_hide" class="span_btn" style="display: none;">&uarr;</span>&nbsp;<span id="btn_tuser" class="span_btn">查看测试用户</span>&nbsp;<span id="btn_update" class="span_btn">更新说明</span>
</div>
<div id="div_content"></div>