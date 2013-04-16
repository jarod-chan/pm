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
	<h2>施工签证线索</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/constructkey" method="post">
		
		编号：
		<input type="text" name="no" value=""/>
		
		<br><input type="hidden" name="project.id" value="${project.id}"/>
		项目:
		${project.name}
		<br>
		原因：
		<input type="text" name="reason" value="" style="width: 300px;"/>
		
		<br>
		结算对象:
		<select name="contract.id">
			<c:forEach var="contract" items="${contractList}">
				<option value="${contract.id}">${contract.payname}</option>
			</c:forEach>
		</select>
		<br>
		

		
		<input type="button" value="保存"  id="btn_save">
		
	</form>
	
</body>
</html>
