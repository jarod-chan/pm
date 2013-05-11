<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	
	
</head>
<body>
	<h2>流程历史</h2>
	<%@ include file="/common/message.jsp"%> 
	
		<table border="1" >
			<thead>
				<tr>
					<th class="noborder">id</th>
					<th class="title">businessKey</th>
					<th class="title">processDefinitionId</th>
					<th class="title">startTime</th>
					<th class="title">endTime</th>
					<th class="title">durationInMillis</th>
					<th class="title">endActivityId</th>
					<th class="title">startUserId</th>
					<th class="title">startActivityId</th>
					<th class="title">deleteReason</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="itme" items="${history}">
					<tr>
						<td>${itme.id}</td>
						<td>${itme.businessKey}</td>
						<td>${itme.processDefinitionId}</td>
						<td>${itme.startTime}</td>
						<td>${itme.endTime}</td>
						<td>${itme.durationInMillis}</td>
						<td>${itme.endActivityId}</td>
						<td>${itme.startUserId}</td>
						<td>${itme.startActivityId}</td>
						<td>${itme.deleteReason}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
		<br>
		<input type="button" onclick="window.open('${ctx}/home','_self');" value="&lt;&lt;返回">
</body>
</html>