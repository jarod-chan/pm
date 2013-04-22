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
 	    		var url=//param.formKey.replace('{personId}',${person.id}).replace('{businessId}',param.businessId);
 	    		$('<form/>',{action:'/${ctx}/'+url})
 	    			.append($('<input/>',{type:'hidden',name:'taskId',value:param.taskId}))
 					.appendTo($("body"))
 				.submit();
 	    	});
 	    });
    	 
     	$('#btn_project').click(function(){
    		window.open('${ctx}/first','_self');
    		return false;
    	});
    });
    </script>
</head>

<body>
	<div style="width:800px;text-align: right;">
		<input type="button" id="btn_project" value="我的项目" >
		<input type="button" id="btn_task" value="我的任务" disabled="disabled">
	</div>
	<h2>我的任务</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<table border="1">
		<thead>
			<tr>
				<th style="width: 300px;">业务流程</th>
				<th style="width: 300px;">待办任务</th>
				<th style="width: 100px;">操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="processTask" items="${processTasks}">
				<tr>
					<td>${processTask.processName}</td>
					<td>${processTask.taskName}</td>
					<td>
						<button class="btn_execute" param='{"taskId":"${processTask.taskId }","formKey":"${processTask.formKey}","businessId":"${processTask.businessId}"}'>处理</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
	
</body>
</html>
