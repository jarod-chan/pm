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
			window.open('${ctx}/${projectId}/contract/${contractType}/-1/edit','_self');
			return false;
		})
    	
    	$('.btn_delete').click(function(){
    		var param=$(this).metadata();
        	$('<form/>',{action:'${ctx}/${projectId}/contract/${contractType}/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'contractId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	})
    	
    	$('.btn_edit').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/${projectId}/contract/${contractType}/'+param.id+'/edit','_self');
    		return false;
    	})
    	
    	$('#btn_query').click(function(){
			var actionFrom=$("form:eq(0)");
			var oldAction=actionFrom.attr("action"); 
			actionFrom.attr("action",oldAction+"/list").submit();
    	});
    	
    	$('#btn_clear').click(function(){
    		window.open('${ctx}/${projectId}/contract/${contractType}/list','_self');
			return false;
    	});
    	
    	$("#headdiv").css("width",$("#tblmain").css("width"));
    });
    </script>
</head>

<body>
	<h2>${contractType.name}</h2>
	<div style="text-align: left;">
	<form action="${ctx}/${projectId}/contract/${contractType}" method="post">
		编号:<input type="text" name="no" value="${query.no}">
		
		名称:<input type="text" name="name" value="${query.name}">
		
		专业分类:<select name="specialty">
			<option value="" >-所有-</option>
			<c:forEach var="contractSpec" items="${contractSpecList}">
				<option value="${contractSpec}" <c:if test="${contractSpec== query.specialty}">selected="true"</c:if> >${contractSpec.name}</option>
			</c:forEach>
		</select>
		
		供应商:<select name="supplier.id">
			<option value="" >-所有-</option>
			<c:forEach var="supplier" items="${supplierList}">
				<option value="${supplier.id}" <c:if test="${supplier.id== query.supplier.id}">selected="true"</c:if> >${supplier.name}</option>
			</c:forEach>
		</select>
		
		<br>
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
	<div id="headdiv" style="text-align: right;">
		<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain" class="hctable deftable">
		<thead>
			<tr>
				<th>编号</th>
				<th>名称</th>
				<th>供应商</th>
				<th>状态</th>
				<th>专业分类</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="contract" items="${contractList}">
			<tr>
				<td>${contract.no}</td>
				<td>${contract.name}</td>
				<td>${contract.supplier.name}</td>
				<td>${contract.state.name}</td>
				<td>${contract.specialty.name}</td>
				<td>
					<input type="button" value="编辑"  class="btn_edit {id:'${contract.id}'}">
					<input type="button" value="删除"  class="btn_delete {id:'${contract.id}'}">
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
</body>
</html>
