<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	
	<%@ include file="/common/jqui.jsp" %>
	<%@ include file="/common/jqui2.jsp" %>	
	
	<style type="text/css">
		.div_block{
			text-align: center;
			margin-top: 10px;
		}	
		
		#message {
			display:inline;
			color: #FF0000;
		}
		
		.no-close .ui-dialog-titlebar-close {
		  display: none;
		}
		.span_btn{
			margin-top: 5px;
		}
	</style>

    <script type="text/javascript">
    $(function() {
    	
    	$( "#loginDiv" ).dialog({
    		dialogClass: "no-close",
			autoOpen: true,
			position: ["center", 100],
			width: 410
		});

	   	$("#btn_logout").click(function(){
	   	  	$('<form/>',{action:'${ctx}/logout',method:'post'})
	   			.appendTo($("body"))
	   			.submit();
	   		return false;
	  	})
	  	  
	  	$("#btn_home").click(function(){
			window.open('${ctx}/redirecthome','_self');
			return false;
		});
	   	
    });
    </script>
</head>
<body>


	
	
	<div id="loginDiv" title="方远房产项目管理系统">
	  	<div class="div_block">
				你已登录该系统!<br>
				如果你想继续使用请点击<span  id="btn_home" class="span_btn" >首页</span>，<br>
				如果你想重新登录请点击<span  id="btn_logout" class="span_btn" >退出</span>。
		</div>
		
	</div>
	
	
	
</body>
</html>
