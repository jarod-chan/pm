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
	<h2>合同</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/contract" method="post">
		编号：
		<input type="text" name="no" value=""/>
		
		<br>
		合同名称：
		<input type="text" name="name" value=""/>
		
		<br>
		所属项目:
		<select name="project.id">
			<c:forEach var="project" items="${projectList}">
				<option value="${project.id}">${project.name}</option>
			</c:forEach>
		</select>
		<br>
		供应商:
		<select name="supplier.id">
			<c:forEach var="supplier" items="${supplierList}">
				<option value="${supplier.id}">${supplier.name}</option>
			</c:forEach>
		</select>
		<br>
		
		
		<input type="button" value="保存"  id="btn_save">
		
	</form>
	
</body>
</html>
