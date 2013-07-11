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
    	
    	$("#btn_login").click(function(){
			var actionFrom=$("form");
			actionFrom.submit();
		});
    });
    </script>
</head>
<body>
	
	<%@ include file="/common/message.jsp" %>	
	<form action="${ctx}/login2" method="post">
	<div style="top:150px;left: 300px;position: absolute;">
		<div><h2>用户登录</h2></div>
		<div>
			用户：
			<select name="userKey" style="width: 100px;">
				<c:forEach var="user" items="${userList}">
					<option value="${user.key}">${user.name}</option>
				</c:forEach>
			</select>
		</div>
		<div>
			密码：
			<input type="text"  style="width: 100px;" value="">
		</div>
		<div>
			<input type="button" id="btn_login" value="登录">
		</div>
	</div>
	</form>
	
</body>
</html>
