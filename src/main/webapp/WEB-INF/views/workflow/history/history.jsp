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
	
		<table class="hctable deftable" >
			<thead>
				<tr>
					<th>id</th>
					<th>businessKey</th>
					<th>processDefinitionId</th>
					<th>startTime</th>
					<th>endTime</th>
					<th>durationInMillis</th>
					<th>endActivityId</th>
					<th>startUserId</th>
					<th>startActivityId</th>
					<th>deleteReason</th>
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