<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	
	
    <script type="text/javascript">
	
    </script>
   
</head>
<body>
	
	<h2>运行状态</h2>
	<%@ include file="/common/message.jsp" %>
	
	<table border="1">
		<thead>
			<tr>
				<th >流程Id</th>
				<th >流程实例Id</th>
				<th >流程定义ID</th>
				<th >是否挂起</th>
				<th >运行状态</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${processInstances}" var="processInstance">
				<tr>
					<td>${processInstance.id }</td>
					<td>${processInstance.processInstanceId }</td>
					<td>${processInstance.processDefinitionId }</td>
					<td>${processInstance.suspended }</td>
					<td>
						<button class="btn_trace" param='{"processInstanceId":"${processInstance.processInstanceId }","processDefinitionId":"${processInstance.processDefinitionId }"}'>跟踪流程</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<input type="button" onclick="window.open('${ctx}/home','_self');" value="&lt;&lt;返回">
</body>
</html>