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
			window.open('${ctx}/constructcont/new','_self');
			return false;
		});
    	

    	$('.btn_edit').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/constructcont/{id}/edit'.replace('{id}',param.id),'_self');
        	return false;
    	});
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/constructcont/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'constructContId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	
    	$('.btn_view').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/constructcont/{id}/view'.replace('{id}',param.id),'_self');
        	return false;
    	});
    });
    </script>
</head>

<body>
	<h2>施工联系单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<div style="text-align: right;" id="headdiv">
		<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain" border="1">
		<tr>
			<td>编号</td><td>合同</td><td>施工承包方</td><td>原因</td><td>状态</td><td>制单人</td><td>制单日期</td><td>签发人</td><td>签发日期</td><td>操作</td>
		</tr>
		<c:forEach var="constructCont" items="${constructContList}">
			<tr>
				<td>${constructCont.no}</td>
				<td>${constructCont.constructKey.contract.name}</td>
				<td>${constructCont.constructKey.contract.supplier.name}</td>
				<td>${constructCont.reason}</td>
				<td>${constructCont.state.name}</td>
				<td>${constructCont.creater.name}</td>
				<td><fmt:formatDate value="${constructCont.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>${constructCont.signer.name}</td>
				<td><fmt:formatDate value="${constructCont.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td>
					<c:if test="${constructCont.state!='commit'}">					
					<input type="button" param='{"id":"${constructCont.id}"}' value="修改"  class="btn_edit">
					<input type="button" param='{"id":"${constructCont.id}"}' value="删除"  class="btn_delete">
					</c:if>
					<input type="button" param='{"id":"${constructCont.id}"}' value="查看"  class="btn_view">
				</td>
			</tr>
		</c:forEach>
		
	</table>
	<br>
	<div style="text-align: left;" id="footdiv">
		<input type="button" value="首页"  id="">
		<input type="button" value="上一页"  id="">
		<input type="button" value="下一页"  id="">
		<input type="button" value="尾页"  id="">
	</div>
	<script type="text/javascript">
		$(function(){
			$("#headdiv").css("width",$("#tblmain").css("width"));
		})
	</script>
	
</body>
</html>
