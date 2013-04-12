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
			window.open('${ctx}/constructcert/edit','_self');
			return false;
		});
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/constructcert/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'constructCertId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    });
    </script>
</head>

<body>
	<h2>施工签证单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<input type="button" value="新建"  id="btn_new">
	<table border="1">
		<tr>
			<td>编号</td><td>项目</td><td>合同</td><td>操作</td>
		</tr>
		<c:forEach var="constructCert" items="${constructCertList}">
			<tr>
				<td>${constructCert.no}</td><td>${constructCert.project.no}</td><td>${constructCert.contract.no}</td>
				<td><input type="button" param='{"id":"${constructCert.id}"}' value="删除"  class="btn_delete"></td>
			</tr>
		</c:forEach>
		
	</table>
	
</body>
</html>
