<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	

 <script type="text/javascript">
 $(function() {
 	
	 $('.btn_delete').click(function(){
 		var param=jQuery.parseJSON($(this).attr("param"));
 		$('<form/>',{action:'${ctx}/workflow/manage/'+param.deploymentId+'/delete',method:'post'})
		 	.appendTo($("body"))
		 	.submit();
 	});
 	

 });
 </script>
 
</head>

<body>
	<h2>流程管理</h2>
	<%@ include file="/common/message.jsp" %>
	
	<table border="1">
		<thead>
			<tr>
				<th>id</th>
				<th>部署id【deploymentId】</th>
				<th>名称【name】</th>
				<th>关键字【KEY】</th>
				<th >版本【version】</th>
				<th>XML</th>
				<th >image</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${processes }" var="process">
				<tr>
					<td>${process.id }</td>
					<td>${process.deploymentId }</td>
					<td>${process.name }</td>
					<td>${process.key }</td>
					<td>${process.version }</td>
					<td><a target="_blank" href='${ctx}/workflow/manage/${process.deploymentId}/resource?resourceName=${process.resourceName }'>${process.resourceName }</a></td>
					<td><a target="_blank" href='${ctx}/workflow/manage/${process.deploymentId}/resource?resourceName=${process.diagramResourceName }'>${process.diagramResourceName }</a></td>
					<td>
						<button class="btn_delete" param='{"deploymentId":"${process.deploymentId}"}' >删除</button>
					</td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<input type="button" onclick="window.open('${ctx}/home','_self');" value="&lt;&lt;返回">
		
</body>
</html>