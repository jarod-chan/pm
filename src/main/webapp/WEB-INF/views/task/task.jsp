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
    	 $(function() {
 	    	$('.btn_execute').click(function(){
 	    		var param=jQuery.parseJSON($(this).attr("param"));
 	    		var url=param.formKey.replace('{businessId}',param.businessId);
 	    		$('<form/>',{action:'${ctx}/'+url})
 	    			.append($('<input/>',{type:'hidden',name:'taskId',value:param.taskId}))
 					.appendTo($("body"))
 				.submit();
 	    	});
 	    	
	 	   	$(".btn_trace").click(function(){
	 	   		var param=jQuery.parseJSON($(this).attr("param"));
				window.open('${ctx}/task/trace/'+param.executionId,'_blank');
				return false;
			})
 	    
    	 });
    	 

    });
    </script>
</head>

<body>
	<h2>任务列表</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<table border="1">
		<thead>
			<tr>
				<th style="width: 300px;">业务流程</th>
				<th style="width: 300px;">待办任务</th>
				<th style="width: 150px;">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="processTask" items="${processTasks}">
				<tr>
					<td>${processTask.processName}</td>
					<td>${processTask.taskName}</td>
					<td>
						<button class="btn_execute" param='{"taskId":"${processTask.taskId }","formKey":"${processTask.formKey}","businessId":"${processTask.businessId}"}'>处理</button>
						<button class="btn_trace" param='{"executionId":"${processTask.executionId}"}'>流程跟踪</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	
</body>
</html>
