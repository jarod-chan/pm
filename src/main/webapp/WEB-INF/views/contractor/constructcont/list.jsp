<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	
	<%@ include file="/common/jqui.jsp" %>
	
	<style type="text/css">
	.state{
		color: #FFFFFF;
		display: inline-block;
		line-height: 16px; 
		padding: 2px 4px;
	}
	
	.state-new_{
		background-color: #999999;  
	}
	.state-saved{
		background-color: #3A87AD;
	}
	.state-commit{
		background-color: #F89406;
	}
	.state-finish{
		background-color:#B94A48;
	}
	</style>


    <script type="text/javascript">
    $(function() {
    	
    	$(".datePK").datepicker();
    	
     	var optColorArr=['#FFFFFF','#FFFFFF','#999999','#3A87AD','#F89406','#B94A48'];
    	$("select[name='state']").each(function(){
			$(this).find("option").each(function(idx){
				$(this).css("background-color",optColorArr[idx]);
			});
			$(this).bind("change",function(){
				$(this).css("background-color",optColorArr[this.selectedIndex])
			}).triggerHandler("change");
		}); 
    	
    	$("#btn_new").click(function(){
			window.open('${ctx}/contractor/${projectId}/constructcont/-1/edit','_self');
			return false;
		});
    	

    	$('.btn_edit').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/contractor/${projectId}/constructcont/{id}/edit'.replace('{id}',param.id),'_self');
        	return false;
    	});
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/contractor/${projectId}/constructcont/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'constructContId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	
    	$('.btn_view').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/contractor/${projectId}/constructcont/{id}/view'.replace('{id}',param.id),'_self');
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
			<td>编号</td>
			<td>合同</td>
			<td>专业分类</td>
			<td>原因</td>
			<td>状态</td>
			<td>总金额</td>
			<td>制单人</td>
			<td>制单日期</td>
			<td>签发人</td>
			<td>签发日期</td>
			<td>接收人</td>
			<td>接收日期</td>
			<td>计划完成日期</td>
			<td>实际完成日期</td>
			<td>实际执行结果</td>
			<td>操作</td>
		</tr>
		<c:forEach var="constructCont" items="${constructContList}">
			<tr>
				<td>${constructCont.no}</td>
				<td>${constructCont.constructKey.contract.name}</td>
				<td>${constructCont.constructKey.contract.specialty.name}</td>
				<td>${constructCont.reason}</td>
				<td><span class="state state-${constructCont.state}" >${constructCont.state.name}</span></td>
				<td>${constructCont.tolsum}</td>
				<td>${constructCont.creater.name}</td>
				<td><fmt:formatDate value="${constructCont.createdate}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>${constructCont.signer.name}</td>
				<td><fmt:formatDate value="${constructCont.signdate}" pattern="yyyy-MM-dd"/></td>
				<td>${constructCont.receiver.name}</td>
				<td><fmt:formatDate value="${constructCont.receivedate}" pattern="yyyy-MM-dd"/></td>
				<td>${constructCont.plandate}</td>
				<td>${constructCont.realdate}</td>
				<td>${constructCont.result}</td>
				<td>
					<c:if test="${constructCont.state=='saved'}">					
					<input type="button" param='{"id":"${constructCont.id}"}' value="修改"  class="btn_edit">
					<input type="button" param='{"id":"${constructCont.id}"}' value="删除"  class="btn_delete">
					</c:if>
					<input type="button" param='{"id":"${constructCont.id}"}' value="查看"  class="btn_view">
				</td>
			</tr>
		</c:forEach>
		
	</table>
	
	<script type="text/javascript">
		$(function(){
			$("#headdiv").css("width",$("#tblmain").css("width"));
		})
	</script>
	
	<c:set var="selectmenu" value="constructcont" />
	<%@ include file="/component/contractorMenu.jsp" %>	
</body>
</html>
