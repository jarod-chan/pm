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
    	
    	$('.btn_set').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/session',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'userKey',value:param.key}))
				.appendTo($("body"))
			.submit();
    	});
    });
    </script>
</head>
<body>
	<h2>Session设置</h2>
	<%@ include file="/common/message.jsp" %>	
	<table border="1">
		<tr>
			<td>用户关键字</td><td>用户名</td><td>操作</td>
		</tr>
		<c:forEach var="user" items="${userList}">
			<tr>
				<td>${user.key}</td><td>${user.name}</td>
				<td><input type="button" param='{"key":"${user.key}"}' value="设置session"  class="btn_set"></td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
