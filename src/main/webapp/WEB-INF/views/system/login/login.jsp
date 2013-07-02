<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
			color: #FF0000;
		}
		
		.no-close .ui-dialog-titlebar-close {
		  display: none;
		}
	</style>

    <script type="text/javascript">
    $(function() {
    	
    	$( "#loginDiv" ).dialog({
    		dialogClass: "no-close",
			autoOpen: true,
			position: ["center", 100],
			width: 400
		});
    	
    	$("#btn_login").click(function(){
			var actionFrom=$("form");
			actionFrom.submit();
		});
    });
    </script>
</head>
<body>
	
	
	
	<div id="loginDiv" title="方远房产项目管理系统">
		<form action="${ctx}/login" method="post">
		<c:if test="${not empty message}">
		<div class="div_block">
				<div id="message">${message.message}</div>
		</div>
		</c:if>
		<div class="div_block">
			用&nbsp;&nbsp;&nbsp;&nbsp;户&nbsp;:&nbsp;<input type="text" id="username" name="username" value="${loginBean.username}" />
		</div>
		<div class="div_block">
			密&nbsp;&nbsp;&nbsp;&nbsp;码&nbsp;:&nbsp;<input type="password" id="password" name="password" value="" />
		</div>
		<div class="div_block">
			<button type="button" id="btn_login">登录系统</button>
			<button type="button" id="btn_fetchpwd">取回密码</button>
		</div>
		</form>
	</div>
	
	
</body>
</html>
