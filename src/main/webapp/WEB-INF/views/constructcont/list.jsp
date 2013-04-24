<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	


    <script type="text/javascript">
    $(function() {
    	
    	$("#btn_new").click(function(){
			window.open('${ctx}/constructcont/edit','_self');
			return false;
		});
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/constructcont/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'constructContId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    });
    </script>
</head>

<body>
	<h2>施工联系单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<div style="width: 800px;text-align: right;">
		<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table border="1">
		<tr>
			<td>编号</td><td>项目</td><td>项目负责人</td><td>合同</td><td>施工承包方</td><td>原因</td><td>状态</td><td>制单人</td><td>制单日期</td><td>签发人</td><td>签发日期</td><td>操作</td>
		</tr>
		<c:forEach var="constructCont" items="${constructContList}">
			<tr>
				<td>${constructCont.no}</td>
				<td>${constructCont.constructKey.project.name}</td>
				<td>${constructCont.leader.name}</td>
				<td>${constructCont.constructKey.contract.name}</td>
				<td>${constructCont.constructKey.contract.supplier.name}</td>
				<td>${constructCont.reason}</td>
				<td>${constructCont.state.name}</td>
				<td>${constructCont.creater.name}</td>
				<td><fmt:formatDate value="${constructCont.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${constructCont.signer.name}</td>
				<td><fmt:formatDate value="${constructCont.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td><input type="button" param='{"id":"${constructCont.id}"}' value="删除"  class="btn_delete"></td>
			</tr>
		</c:forEach>
		
	</table>
	
</body>
</html>
