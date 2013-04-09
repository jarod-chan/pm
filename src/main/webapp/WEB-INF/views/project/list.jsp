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
    	
    	$("#btn_new").click(function(){
			window.open('${ctx}/project/edit','_self');
			return false;
		});
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/project/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'projectId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    });
    </script>
</head>

<body>
	<h2>项目</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<input type="button" value="新建"  id="btn_new">
	<table border="1px;">
		<tr>
			<td>编号</td><td>名称</td><td>项目负责人</td><td>操作</td>
		</tr>
		<c:forEach var="project" items="${projectList}">
			<tr>
				<td>${project.no}</td><td>${project.name}</td><td>${project.user.realname}</td>
				<td><input type="button" param='{"id":"${project.id}"}' value="删除"  class="btn_delete"></td>
			</tr>
		</c:forEach>
		
	</table>
	
</body>
</html>
