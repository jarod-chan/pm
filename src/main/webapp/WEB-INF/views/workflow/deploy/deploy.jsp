<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	

 <script type="text/javascript">
 $(function() {
 	$('.btn_deploy').click(function(){
 		var param=jQuery.parseJSON($(this).attr("param"));
 		$('<form/>',{action:'${ctx}/workflow/deploy/'+param.filename+'/',method:'post'})
 			.appendTo($("body"))
 			.submit();
 	});
 });
 </script>
 
</head>

<body class="tbody">
	<h2>流程文件</h2>
	<%@ include file="/common/message.jsp" %>
	
	<table border="1">
		<thead>
			<tr>
				<th >流程文件</th>
				<th >操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${processFiles}" var="file">
				<tr>
					<td>${file.name}</td>
					<td><button class="btn_deploy" param='{"filename":"${file.name}"}'>部署流程</button></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>
	<br>
	<input type="button" onclick="window.open('${ctx}/home','_self');" value="&lt;&lt;返回">
</body>

</html>
