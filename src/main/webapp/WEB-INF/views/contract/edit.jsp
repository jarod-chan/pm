<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	
	<%@ include file="/common/jqui.jsp" %>	

    <script type="text/javascript">
    $(function() {
    	var validator=$("form").validate({
    		rules: {
    			name: {
    				required: true,
    				maxlength: 12
    			}
    		}
    	});
    	
		$("#btn_save").click(function(){
			if(!validator.form()){return;}
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action");
			actionFrom.attr("action",oldAction+"/save").submit();
		});
		
		$("#btn_back").click(function(){
			window.open('${ctx}/${projectId}/contract/${contractType}/list','_self');
			return false;
		})
		
		$('#tabmain tr').find('td:eq(0)').css("text-align","right");
		
		$(".datePK").datepicker();
    });
    </script>
    
	
</head>

<body>
	<h2>${contractType.name}</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/${projectId}/contract/${contractType}" method="post">
	
	<input type="hidden" name="id" value="${contract.id}"/>	
	<input type="hidden" name="project.id" value="${contract.project.id}"/>	
	<input type="hidden" name="type" value="${contractType}"/>	
	
	<table id="tabmain">
		
		<tr><td>
		编号：</td><td>
		<c:set var="parma_no" value="${contract.no}" />
		<%@ include file="/component/noShow.jsp" %>	
		</td></tr>
		
		<tr><td>
		合同名称：</td><td>
		<input type="text" name="name" value="${contract.name}"/>
		</td></tr>
		
		
		<tr><td>
		供应商：</td><td>
		<select name="supplier.id">
			<c:forEach var="supplier" items="${supplierList}">
				<option value="${supplier.id}" <c:if test="${supplier.id== contract.supplier.id}">selected="true"</c:if> >${supplier.name}</option>
			</c:forEach>
		</select>
		<tr><td>
		
		<tr><td>
		合同状态：</td><td>
		<select name="state">
			<c:forEach var="contractState" items="${contractStateList}">
				<option value="${contractState}" <c:if test="${contractState== contract.state}">selected="true"</c:if> >${contractState.name}</option>
			</c:forEach>
		</select>
		<tr><td>
		
		<tr><td>
		专业分类：</td><td>
		<select name="specialty">
			<c:forEach var="specialty" items="${specialtyList}">
				<option value="${specialty}" <c:if test="${specialty== contract.specialty}">selected="true"</c:if> >${specialty.name}</option>
			</c:forEach>
		</select>
		<tr><td>
		
		<tr><td>
		签订日期：</td><td>
		<input type="text" name="signDate" class="datePK" value="${contract.signDate}"/>
		<td></tr>
		
		<tr><td>
		交付日期：</td><td>
		<input type="text" name="delvDate" class="datePK" value="${contract.delvDate}"/>
		<td></tr>
		
		<tr><td>
		合同金额：</td><td>
		<input type="text" name="contractAmt"  value="${contract.contractAmt}"/>
		<td></tr>
		
		<tr><td>
		首付比例：</td><td>
		<input type="text" name="dpscale"  value="${contract.dpscale}"/>
		<td></tr>
		
		<tr><td>
		结算金额：</td><td>
		<input type="text" name="finalAmt"  value="${contract.finalAmt}"/>
		<td></tr>
		
		<tr><td>
		来源形式：</td><td>
		<input type="text" name="origins"  value="${contract.origins}"/>
		<td></tr>
		
		<tr><td>
		责任部门：</td><td>
		<input type="text" name="dept"  value="${contract.dept}"/>
		<td></tr>
		
		<tr><td>
		风险等级：</td><td>
		<select name="riskLevel">
			<c:forEach var="contractRisk" items="${contractRiskList}">
				<option value="${contractRisk}" <c:if test="${contractRisk== contract.riskLevel}">selected="true"</c:if> >${contractRisk.name}</option>
			</c:forEach>
		</select>
		<tr><td>
		
		<tr><td>
		风险提示：</td><td>
		<input type="text" name="riskPrompt"  value="${contract.riskPrompt}"/>
		<td></tr>
		
		<tr><td>
		评审结论：</td><td>
		<input type="text" name="conclusion"  value="${contract.conclusion}"/>
		<td></tr>
		
		<tr><td>
		签订责任人：</td><td>
		
		<select name="leader.key">
			<c:forEach var="user" items="${userList}">
				<option value="${user.key}" <c:if test="${user.key== contract.leader.key}">selected="true"</c:if> >${user.name}</option>
			</c:forEach>
		</select>
		<td></tr>
		
		<tr><td>
		总份数：</td><td>
		<input type="text" name="totalCopies"  value="${contract.totalCopies}"/>
		<td></tr>
		
		<tr><td>
		留存份数：</td><td>
		<input type="text" name="saveCopies"  value="${contract.saveCopies}"/>
		<td></tr>
		
	    </table>
		
		 <%@ include file="/component/fileUpload.jsp" %>	
		
		<input type="button" value="保存"  id="btn_save">
		<input type="button" value="返回"  id="btn_back">
		
	</form>
	
</body>
</html>
