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
			window.open('${ctx}/contractmeter/-1/edit','_self');
			return false;
		})
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/contractmeter/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'contractMeterId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	})
    	
    	$('.btn_edit').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/contractmeter/'+param.id+'/edit','_self');
    		return false;
    	})
    	
    	$('#btn_query').click(function(){
			var actionFrom=$("form:eq(0)");
			var oldAction=actionFrom.attr("action"); 
			actionFrom.attr("action",oldAction+"/list").submit();
    	});
    	
    	$('#btn_clear').click(function(){
    		window.open('${ctx}/contractmeter/list','_self');
			return false;
    	});
    	
    	$("#headdiv").css("width",$("#tblmain").css("width"));
    });
    </script>
</head>

<body>
	<h2>材料采购合同</h2>
	<div style="text-align: left;">
	<form action="${ctx}/contractmeter" method="post">
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
	<table id="tblmain" border="1">
		<tr>
			<td>编号</td>
			<td>名称</td>
			<td>采购申请</td>
			<td>供应商</td>
			<td>状态</td>
			<td>专业分类</td>
			<td>签订日期</td>
			<td>交付日期</td>
			<td>合同金额</td>
			<td>首付比例</td>
			<td>结算金额</td>
			<td>来源形式</td>
			<td>责任部门</td>
			<td>风险等级</td>
			<td>风险提示</td>
			<td>评审结论</td>
			<td>签订责任人</td>
			<td>总份数</td>
			<td>留存份数</td>
			<td>操作</td>
		</tr>
		<c:forEach var="dto" items="${contractMeterDtoList}">
			<tr>
				<td>${dto.contractMeter.no}</td>
				<td>${dto.contractMeter.name}</td>
				<td>${dto.purchaseReq.no}</td>
				<td>${dto.contractMeter.supplier.name}</td>
				<td>${dto.contractMeter.state.name}</td>
				<td>${dto.contractMeter.specialty.name}</td>
				<td>${dto.contractMeter.signDate}</td>
				<td>${dto.contractMeter.delvDate}</td>
				<td>${dto.contractMeter.contractAmt}</td>
				<td>${dto.contractMeter.dpscale}</td>
				<td>${dto.contractMeter.finalAmt}</td>
				<td>${dto.contractMeter.origins}</td>
				<td>${dto.contractMeter.dept}</td>
				<td>${dto.contractMeter.riskLevel.name}</td>
				<td>${dto.contractMeter.riskPrompt}</td>
				<td>${dto.contractMeter.conclusion}</td>
				<td>${dto.contractMeter.leader.name}</td>
				<td>${dto.contractMeter.totalCopies}</td>
				<td>${dto.contractMeter.saveCopies}</td>
				<td>
					<input type="button" param='{"id":"${dto.contractMeter.id}"}' value="编辑"  class="btn_edit">
					<input type="button" param='{"id":"${dto.contractMeter.id}"}' value="删除"  class="btn_delete">
				</td>
			</tr>
		</c:forEach>
		
	</table>
	
</body>
</html>
