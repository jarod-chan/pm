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
	<%@ include file="/common/jqui2.jsp" %>

    <script type="text/javascript">
    $(function() {
    	
    	$(".datePK").datepicker();     	
    	
    	$("#btn_new").click(function(){
			window.open('${ctx}/${projectId}/constructcont/-1/edit','_self');
			return false;
		});
    	

    	$('.btn_edit').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/${projectId}/constructcont/{id}/edit'.replace('{id}',param.id),'_self');
        	return false;
    	});
    	
    	$('.btn_delete').click(function(){
    		var param=$(this).metadata();
        	$('<form/>',{action:'${ctx}/${projectId}/constructcont/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'constructContId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	
    	$('.btn_view').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/${projectId}/constructcont/{id}/view'.replace('{id}',param.id),'_self');
        	return false;
    	});
    	
    	$('.btn_print').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/${projectId}/constructcont/{id}/print'.replace('{id}',param.id),'_blank');
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
    		window.open('${ctx}/${projectId}/constructcont/list','_self');
			return false;
    	});
    });
    </script>
</head>

<body>
	<h2>施工联系单</h2>
	<div style="text-align: left;">
	<form action="${ctx}/${projectId}/constructcont" method="post">
		序号:<input type="text" name="no" value="${query.no}">
		施工承包方:<span class="span_btn" id="selSupplier"><c:if test="${empty query.supplier.name}">所有</c:if>${query.supplier.name}</span>
		<input type="hidden" name="supplier.id" value="${query.supplier.id}">
		制单日期:<input type="text" name="createdate_beg" class="datePK" value="<fmt:formatDate value="${query.createdate_beg}" pattern="yyyy-MM-dd"/>" >--<input type="text" name="createdate_end" class="datePK" value="<fmt:formatDate value="${query.createdate_end}" pattern="yyyy-MM-dd"/>">
		专业分类:<select name="specialty" >
					<option value="" >-所有-</option>
					<c:forEach var="spec" items="${query.specialtyList}">
						<option value="${spec}" <c:if test="${spec==query.specialty}">selected="true"</c:if> >${spec.name}</option>
					</c:forEach>
				</select><br>
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
	
	<%@ include file="/component/selSupplier.jsp" %>
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
			<th>施工承包方</th>
			<th>专业分类</th>
			<th>状态</th>
			<th>制单人</th>
			<th>制单日期</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="constructCont" items="${constructContList}">
			<tr>
				<td>${constructCont.no}</td>
				<td>${constructCont.busino}</td>
				<td>${constructCont.constructKey.supplier.name}</td>
				<td>${constructCont.constructKey.contract.specialty.name}</td>
				<td><span class="state state-${constructCont.state}" >${constructCont.state.name}</span></td>
				<td>${constructCont.creater.name}</td>
				<td><fmt:formatDate value="${constructCont.createdate}" pattern="yyyy-MM-dd"/></td>
				<td>
					<c:if test="${constructCont.state=='saved'}">					
					<input type="button" value="修改"  class="btn_edit {id:'${constructCont.id}'}">
					<input type="button" value="删除"  class="btn_delete {id:'${constructCont.id}'}">
					</c:if>
					<input type="button" value="查看"  class="btn_view {id:'${constructCont.id}'}">
					<input type="button" value="打印"  class="btn_print {id:'${constructCont.id}'}">
					
					<c:if test="${constructCont.state=='commit'}">
						<input type="button" value="跟踪"  class="btn_trace {id:'${constructCont.id}',processDefKey:'pm-construct-cont'}">
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
		
	</table>
	
	<script type="text/javascript">
		$(function(){
			$("#headdiv").css("width",$("#tblmain").css("width"));
		})
	</script>
	
</body>
</html>
