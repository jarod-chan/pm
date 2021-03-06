<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	


    <script type="text/javascript">
    $(function() {
    	 $(function() {
 	    	$('.btn_execute').click(function(){
 	    		var param=$(this).metadata();
 	    		var url=param.formKey.replace('{businessId}',param.businessId);
 	    		$('<form/>',{action:'${ctx}/'+url})
 	    			.append($('<input/>',{type:'hidden',name:'taskId',value:param.taskId}))
 					.appendTo($("body"))
 				.submit();
 	    	});
 	    	
	 	   	$(".btn_trace").click(function(){
	 	   		var param=$(this).metadata();
				window.open('${ctx}/trace/'+param.executionId,'_blank');
				return false;
			})
 	    
    	 });
    	 

    });
    </script>
</head>

<body>
	<h2>任务列表</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<table border="1" class="hctable deftable">
		<thead>
			<tr>
				<th>业务流程</th>
				<th>序号</th>
				<th>项目名称</th>
				<th>待办任务</th>
				<th>发起时间</th>
				<th>已发起时间</th>
				<th>交办时间</th>
				<th>已交办时间</th>
				<th>任务期限</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="taskDto" items="${taskDtos}">
				<tr>
					<td>${taskDto.processName}</td>
					<td>${taskDto.businessNo}</td>
					<td>${taskDto.projectName}</td>
					<td>${taskDto.taskName}</td>
					<td><fmt:formatDate value="${taskDto.startTime}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td>${taskDto.startDuration.formatText}</td>
					<td><fmt:formatDate value="${taskDto.createDate}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td>${taskDto.createDuration.formatText}</td>
					<td><fmt:formatDate value="${taskDto.dueDate}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td>
						<button class="btn_execute {taskId:'${taskDto.taskId }',formKey:'${taskDto.formKey}',businessId:'${taskDto.businessId}'}" >处理</button>
						<button class="btn_trace {executionId:'${taskDto.executionId}'}" >流程跟踪</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	
</body>
</html>
