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
	
    <script type="text/javascript">
    $(function() {
    	
    	$(".datePK").datepicker();
    	
    	$("#btn_new").click(function(){
			window.open('${ctx}/${projectId}/designcont/-1/edit','_self');
			return false;
		});
    	

    	$('.btn_edit').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/${projectId}/designcont/{id}/edit'.replace('{id}',param.id),'_self');
        	return false;
    	});
    	
    	$('.btn_delete').click(function(){
    		var param=$(this).metadata();
        	$('<form/>',{action:'${ctx}/${projectId}/designcont/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'designContId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	
    	$('.btn_view').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/${projectId}/designcont/{id}/view'.replace('{id}',param.id),'_self');
        	return false;
    	});
    	
    	$(".btn_trace").click(function(){
 	   		var param=$(this).metadata();
			window.open('${ctx}/trace/'+param.processDefKey+'/'+param.id,'_blank');
			return false;
		})
    	
    	$('#btn_query').click(function(){
			var actionFrom=$("form:eq(0)");
			var oldAction=actionFrom.attr("action"); 
			actionFrom.attr("action",oldAction+"/list").submit();
    	});
    	
    	$('#btn_reset').click(function(){
    		window.open('${ctx}/${projectId}/designcont/list','_self');
			return false;
    	});
    });
    </script>
</head>

<body>
	<h2>技术变更通知单</h2>
	<div style="text-align: left;">
	<form action="${ctx}/${projectId}/designcont" method="post">
		序号:<input type="text" name="no" value="${query.no}">
		制单日期:<input type="text" name="createdate_beg" class="datePK" value="<fmt:formatDate value="${query.createdate_beg}" pattern="yyyy-MM-dd"/>" >--<input type="text" name="createdate_end" class="datePK" value="<fmt:formatDate value="${query.createdate_end}" pattern="yyyy-MM-dd"/>">
		<br>
				状态:<select name="state" class="bkcolor-state">
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
		<input type="button" value="重置" id="btn_reset"> 
	</form>
	</div>
	
	<%@ include file="/common/message.jsp" %>	
	
	
	<div style="text-align: right;" id="headdiv">
		<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain" class="hctable deftable">
		<thead>
			<tr>
				<th>序号</th>
				<th>业务编号</th>
				<th>问题通知单</th>
				<th>设计专业</th>
				<th>设计服务商</th>
				<th>状态</th>
				<th>制单人</th>
				<th>制单日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="item" items="${designContList}">
			<tr>
				<td>${item.no}</td>
				<td>${item.busino}</td>
				<td>${item.designNoti.no}</td>
				<td>${item.techType.name}</td>
				<td>${item.contract.supplier.no}</td>
				<td><span class="state state-${item.state}" >${item.state.name}</span></td>
				<td>${item.creater.name}</td>
				<td><fmt:formatDate value="${item.createdate}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>
					<c:if test="${item.state=='saved'}">					
					<input type="button" value="修改"  class="btn_edit {id:'${item.id}'}">
					<input type="button" value="删除"  class="btn_delete {id:'${item.id}'}">
					</c:if>
					<input type="button" value="查看"  class="btn_view {id:'${item.id}'}">
					<c:if test="${item.state=='commit'}">
						<input type="button" value="跟踪"  class="btn_trace {id:'${item.id}',processDefKey:'pm-design-cont'}">
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br>
	<script type="text/javascript">
		$(function(){
			$("#headdiv").css("width",$("#tblmain").css("width"));
		})
	</script>
	
</body>
</html>
