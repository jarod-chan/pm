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
			window.open('${ctx}/contract/edit','_self');
			return false;
		});
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/contract/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'contractId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    });
    </script>
</head>

<body>
	<h2>合同</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<input type="button" value="新建"  id="btn_new">
	<table border="1px;">
		<tr>
			<td>编号</td><td>名称</td><td>项目</td><td>供应商</td><td>操作</td>
		</tr>
		<c:forEach var="contract" items="${contractList}">
			<tr>
				<td>${contract.no}</td><td>${contract.name}</td>
				<td>${contract.project.name}</td>
				<td>${contract.supplier.name}</td>
				<td><input type="button" param='{"id":"${contract.id}"}' value="删除"  class="btn_delete"></td>
			</tr>
		</c:forEach>
		
	</table>
	
</body>
</html>
