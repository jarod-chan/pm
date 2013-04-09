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
		$("#btn_save").click(function(){
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action");
			actionFrom.attr("action",oldAction+"/save").submit();
		});
    });
    </script>
</head>

<body>
	<h2>项目</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/project" method="post">
		编号：
		<input type="text" name="no" value=""/>
		
		<br>
		项目名称：
		<input type="text" name="name" value=""/>
		
		<br>
		项目负责人:
		<select name="user.key">
			<c:forEach var="user" items="${userList}">
				<option value="${user.key}">${user.realname}</option>
			</c:forEach>
		</select>
		<br>
		<input type="button" value="保存"  id="btn_save">
		
	</form>
	
</body>
</html>
