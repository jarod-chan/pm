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
			window.open('${ctx}/project/${project.id}/constructkey/edit','_self');
			return false;
		});
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/project/${project.id}/constructkey/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'constructKeyId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    });
    </script>
</head>

<body>
	<h2>施工签证</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<div style="text-align: right;width: 400px;"><input type="button" value="新建施工签证事务"  id="btn_new"></div>
	<br>
	<table border="1">
		<tr>
			<td>编号</td><td>结算对象</td><td>原因</td><td>操作</td>
		</tr>
		<c:forEach var="constructKey" items="${constructKeyList}">
			<tr>
				<td>${constructKey.no}</td><td>${constructKey.contract.payname}</td><td>${constructKey.reason}</td>
				<td><input type="button" param='{"id":"${constructKey.id}"}' value="删除"  class="btn_delete"></td>
			</tr>
		</c:forEach>
		
	</table>
	
</body>
</html>
