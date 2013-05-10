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
			window.open('${ctx}/constructcert/new','_self');
			return false;
		});
    	
    	$(".btn_edit").click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
			window.open('${ctx}/constructcert/'+param.id+'/edit','_self');
			return false;
		});
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/constructcert/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'constructCertId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	
    	$('.btn_view').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/constructcert/{id}/view'.replace('{id}',param.id),'_self');
        	return false;
    	});
    });
    </script>
</head>

<body>
	<h2>施工签证单</h2>
	<%@ include file="/common/message.jsp" %>	
	<div style="text-align: right;" id="headdiv">
		<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain" border="1">
		<tr>
			<td>编号</td><td>施工联系单</td><td>施工承包方</td><td>原因</td><td>状态</td><td>制单人</td><td>制单日期</td><td>签发人</td><td>签发日期</td><td>操作</td>
		</tr>
		<c:forEach var="constructCertDto" items="${ConstructCertDtoList}">
			<tr>
				<td>${constructCertDto.constructCert.no}</td>
				<td>${constructCertDto.constructCont.no}</td>
				<td>${constructCertDto.constructCert.constructKey.contract.supplier.name}</td>
				<td>${constructCertDto.constructCert.reason}</td>
				<td>${constructCertDto.constructCert.state.name}</td>
				<td>${constructCertDto.constructCert.creater.name}</td>
				<td><fmt:formatDate value="${constructCertDto.constructCert.createdate}" pattern="yyyy-MM-dd"/></td>
				<td>${constructCertDto.constructCert.signer.name}</td>
				<td><fmt:formatDate value="${constructCertDto.constructCert.signdate}" pattern="yyyy-MM-dd"/></td>
				<td>
					<c:if test="${constructCertDto.constructCert.state=='saved'}">	
					<input type="button" param='{"id":"${constructCertDto.constructCert.id}"}' value="修改"  class="btn_edit">
					<input type="button" param='{"id":"${constructCertDto.constructCert.id}"}' value="删除"  class="btn_delete">
					</c:if>
					<input type="button" param='{"id":"${constructCertDto.constructCert.id}"}' value="查看"  class="btn_view">
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
