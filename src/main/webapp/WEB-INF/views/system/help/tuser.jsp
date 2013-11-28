<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<style type="text/css">
	.content_center{
		margin: 50 auto;
		width: 370px; 
	}
	.fullwidth{
		width: 100%;
	}
	.font_red{
		color: red;
		font-weight: bold;
	}
</style>

	<div class="content_center">
	<div class="font_red ">
		注意：默认密码只是临时测试用，请不要修改。
	</div>
	<%@ include file="/common/message.jsp" %>
	
	<table  class="hctable deftable fullwidth" >
		<thead>
			<tr>
				<th>用户名</th>
				<th>姓名</th>
				<th>默认密码</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${users}" var="user">
				<tr>
					<td>${user.key}</td>
					<td>${user.name}</td>
					<td>0</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	</div>


		
</body>
</html>