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
	.state-invalid{
		background-color:#FF8080;
	}
	</style>


    <script type="text/javascript">
    $(function() {
    	
    	$(".datePK").datepicker();
    	
     	var optColorArr=['#FFFFFF','#FFFFFF','#999999','#3A87AD','#F89406','#B94A48','#FF8080'];
    	$("select[name='state']").each(function(){
			$(this).find("option").each(function(idx){
				$(this).css("background-color",optColorArr[idx]);
			});
			$(this).bind("change",function(){
				$(this).css("background-color",optColorArr[this.selectedIndex])
			}).triggerHandler("change");
		}); 
    	
    	$("#btn_new").click(function(){
			window.open('${ctx}/purchasecert/-1/edit','_self');
			return false;
		});
    	

    	$('.btn_edit').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/purchasecert/{id}/edit'.replace('{id}',param.id),'_self');
        	return false;
    	});
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/purchasecert/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'purchaseCertId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	
    	$('.btn_view').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/purchasecert/{id}/view'.replace('{id}',param.id),'_self');
        	return false;
    	});
    	
    	$('#btn_query').click(function(){
			var actionFrom=$("form:eq(0)");
			var oldAction=actionFrom.attr("action"); 
			actionFrom.attr("action",oldAction+"/list").submit();
    	});
    	
    	$('#btn_clear').click(function(){
    		window.open('${ctx}/purchasecert/list','_self');
			return false;
    	});
    });
    </script>
</head>

<body>
	<h2>价格确认单</h2>
	<div style="text-align: left;">
	<form action="${ctx}/purchasecert" method="post">
		序号:<input type="text" name="no" value="${query.no}">
		制单日期:<input type="text" name="createdate_beg" class="datePK" value="<fmt:formatDate value="${query.createdate_beg}" pattern="yyyy-MM-dd"/>" >--<input type="text" name="createdate_end" class="datePK" value="<fmt:formatDate value="${query.createdate_end}" pattern="yyyy-MM-dd"/>">
		<br>
				状态:<select name="state" >
				<c:forEach var="state" items="${query.stateList}">
					<option value="${state.value}" <c:if test="${state.value==query.state}">selected="true"</c:if> >${state.name}</option>
				</c:forEach>
			</select>
		&nbsp;&nbsp;&nbsp;&nbsp;
		排序:<select name="orderAttribute">
				<c:forEach var="attr" items="${query.orderAttributeList}">
					<option value="${attr.value}" <c:if test="${attr.value== query.orderAttribute}">selected="true"</c:if> >${attr.name}</option>
				</c:forEach>
			</select> 
			<select name="orderType">
				<c:forEach var="type" items="${query.orderTypeList}">
					<option value="${type.value}" <c:if test="${type.value== query.orderType}">selected="true"</c:if> >${type.name}</option>
				</c:forEach>
			</select>  
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="查询" id="btn_query"> 
		<input type="button" value="清空" id="btn_clear"> 
	</form>
	</div>
	
	<%@ include file="/common/message.jsp" %>	
	
	
	<div style="text-align: right;" id="headdiv">
		<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain" border="1">
		<tr>
			<td>序号</td>
			<td>业务编号</td>
			<td>采购申请单</td>
			<td>计划进场时间</td>
			<td>状态</td>
			<td>制单人</td>
			<td>制单日期</td>
			<td>签发人</td>
			<td>签发日期</td>
			<td>接收人</td>
			<td>接收日期</td>
			<td>操作</td>
		</tr>
		<c:forEach var="item" items="${purchaseCertDtoList}">
			<tr>
				<td>${item.purchaseCert.no}</td>
				<td>${item.purchaseCert.busino}</td>
				<td>${item.purchaseReq.no}</td>
				<td>${item.purchaseCert.plandate}</td>
				<td><span class="state state-${item.purchaseCert.state}" >${item.purchaseCert.state.name}</span></td>
				<td>${item.purchaseCert.creater.name}</td>
				<td><fmt:formatDate value="${item.purchaseCert.createdate}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>${item.purchaseCert.signer.name}</td>
				<td><fmt:formatDate value="${item.purchaseCert.signdate}" pattern="yyyy-MM-dd"/></td>
				<td>${item.purchaseCert.receiver.name}</td>
				<td><fmt:formatDate value="${item.purchaseCert.receivedate}" pattern="yyyy-MM-dd"/></td>
				<td>
					<c:if test="${item.purchaseCert.state=='saved'}">					
					<input type="button" param='{"id":"${item.purchaseCert.id}"}' value="修改"  class="btn_edit">
					<input type="button" param='{"id":"${item.purchaseCert.id}"}' value="删除"  class="btn_delete">
					</c:if>
					<input type="button" param='{"id":"${item.purchaseCert.id}"}' value="查看"  class="btn_view">
				</td>
			</tr>
		</c:forEach>
		
	</table>
	<br>
	<script type="text/javascript">
		$(function(){
			$("#headdiv").css("width",$("#tblmain").css("width"));
		})
	</script>
	
</body>
</html>
