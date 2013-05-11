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
			window.open('${ctx}/contract/${contractType}/-1/edit','_self');
			return false;
		})
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/contract/${contractType}/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'contractId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	})
    	
    	$('.btn_edit').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/contract/${contractType}/'+param.id+'/edit','_self');
    		return false;
    	})
    	
    	$("#headdiv").css("width",$("#tblmain").css("width"));
    });
    </script>
</head>

<body>
	<h2>${contractType.name}</h2>
	<%@ include file="/common/message.jsp" %>	
	<div id="headdiv" style="text-align: right;">
		<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain" border="1">
		<tr>
			<td>编号</td><td>名称</td><td>供应商</td>

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
		<c:forEach var="contract" items="${contractList}">
			<tr>
				<td>${contract.no}</td>
				<td>${contract.name}</td>
				<td>${contract.supplier.name}</td>
				<td>${contract.state.name}</td>
				<td>${contract.specialty.name}</td>
				<td>${contract.signDate}</td>
				<td>${contract.delvDate}</td>
				<td>${contract.contractAmt}</td>
				<td>${contract.dpscale}</td>
				<td>${contract.finalAmt}</td>
				<td>${contract.origins}</td>
				<td>${contract.dept}</td>
				<td>${contract.riskLevel.name}</td>
				<td>${contract.riskPrompt}</td>
				<td>${contract.conclusion}</td>
				<td>${contract.leader.name}</td>
				<td>${contract.totalCopies}</td>
				<td>${contract.saveCopies}</td>
				<td>
					<input type="button" param='{"id":"${contract.id}"}' value="编辑"  class="btn_edit">
					<input type="button" param='{"id":"${contract.id}"}' value="删除"  class="btn_delete">
				</td>
			</tr>
		</c:forEach>
		
	</table>
	
</body>
</html>
