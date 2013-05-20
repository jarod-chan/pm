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
    	
    	$("#btn_new").click(function(){
			window.open('${ctx}/constructcont/-1/edit','_self');
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
    	
    	$('#btn_query').click(function(){
			var actionFrom=$("form:eq(0)");
			var oldAction=actionFrom.attr("action"); 
			actionFrom.attr("action",oldAction+"/list").submit();
    	});
    	
    	$('#btn_clear').click(function(){
    		window.open('${ctx}/constructcont/list','_self');
			return false;
    	});
    });
    </script>
</head>

<body>
	<h2>施工联系单</h2>
	<div style="text-align: left;">
	<form action="${ctx}/constructcont" method="post">
		编号:<input type="text" name="no" value="${query.no}">
		施工承包方:<select name="supplier.id" >
					<option value="" >-所有-</option>
					<c:forEach var="supplier" items="${supplierList}">
						<option value="${supplier.id}" <c:if test="${supplier.id==query.supplier.id}">selected="true"</c:if> >${supplier.name}</option>
					</c:forEach>
				</select>
		制单日期:<input type="text" name="createdate_beg" class="datePK" value="<fmt:formatDate value="${query.createdate_beg}" pattern="yyyy-MM-dd"/>" >--<input type="text" name="createdate_end" class="datePK" value="<fmt:formatDate value="${query.createdate_end}" pattern="yyyy-MM-dd"/>"><br>
		<input type="checkbox" name="filterFinish" <c:if test="${query.filterFinish}">checked="true"</c:if> >过滤已完成单据
		<input type="hidden" name="_filterFinish"  /> 
		&nbsp;&nbsp;&nbsp;&nbsp;
		排序:<select name="orderAttribute">
				<option value="no"   <c:if test="${query.orderAttribute=='no'}">selected="true"</c:if> >编号</option>
				<option value="constructKey.supplier.id"  <c:if test="${query.orderAttribute=='constructKey.supplier.id'}">selected="true"</c:if>  >施工承包方</option>
				<option value="createdate" <c:if test="${query.orderAttribute=='createdate'}">selected="true"</c:if> >制单日期</option>
			</select> 
			<select name="orderType">
				<option value="asc" <c:if test="${query.orderType=='asc'}">selected="true"</c:if> >升序</option>
				<option value="desc" <c:if test="${query.orderType=='desc'}">selected="true"</c:if>  >降序</option>
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
			<td>编号</td><td>合同</td><td>施工承包方</td><td>原因</td><td>状态</td><td>制单人</td><td>制单日期</td><td>签发人</td><td>签发日期</td>
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
				<td>${constructCont.constructKey.supplier.name}</td>
				<td>${constructCont.reason}</td>
				<td><span class="state state-${constructCont.state}" >${constructCont.state.name}</span></td>
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
