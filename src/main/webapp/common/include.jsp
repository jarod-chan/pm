<%@ page language="java" pageEncoding="UTF-8"%>
<link href="${ctx}/css/base.css" type="text/css"  rel="stylesheet">

<script src="${ctx}/js/jquery.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${ctx}/js/pathie.js" type="text/javascript"></script>
<script src="${ctx}/js/myplug.js" type="text/javascript"></script>
<script src="${ctx}/js/myfloat.js" type="text/javascript"></script>

<link href="${ctx}/plu/uploadify/uploadify.css" type="text/css"  rel="stylesheet">
<script src="${ctx}/plu/uploadify/jquery.uploadify.js" type="text/javascript"></script>

<script src="${ctx}/plu/layout/jquery.layout-latest.js" type="text/javascript"></script>


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




