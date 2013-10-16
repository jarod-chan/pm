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
    	var validator=$("form").validate({
    		rules: {
    			name: {
    				required: true,
    				maxlength: 32
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
			window.open('${ctx}/${projectId}/contractmeter/list','_self');
			return false;
		})
		
		$('#tabmain tr').find('td:eq(0)').css("text-align","right");
		
		$(".datePK").datepicker();
    });
    </script>
    

	
	
</head>

<body>
	<h2>材料采购合同</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/${projectId}/contractmeter" method="post">
	
	<input type="hidden" name="id" value="${contractMeter.id}"/>	
	
	<table id="tabmain">
		
		<tr><td>
		编号：</td><td>
		<c:set var="parma_no" value="${contractMeter.no}" />
		<%@ include file="/component/noShow.jsp" %>	
		</td></tr>
		
		<tr><td>
		合同名称：</td><td>
		<input type="text" name="name" value="${contractMeter.name}"/>
		</td></tr>
		
		<tr>
			<td>采购申请单：</td> 
			<td>
				<span id="spanReq">${purchaseReq.no}</span><input type="hidden" name="purchaseKey.id" value="${purchaseReq.purchaseKey.id}">
				<input type="button" id="btn_selreq" value="选择" />
			</td>
		</tr>
		<tr>
			<td style="vertical-align: top">关联采购明细：</td>
			<td>
				<table id="purchaseReqItem"  class="deftable">
				<thead>
					<tr>
						<th>勾选</th><th>序号</th><th>材料名称</th><th>型号规格和技术指标</th><th>单位</th><th>数量</th><th>推荐品牌</th><th>执行结果</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
				</table>
			</td>
		</tr>
		
		
		<tr><td>
		供应商：</td><td>
		<select name="supplier.id">
			<c:forEach var="supplier" items="${supplierList}">
				<option value="${supplier.id}" <c:if test="${supplier.id== contractMeter.supplier.id}">selected="true"</c:if> >${supplier.name}</option>
			</c:forEach>
		</select>
		<tr><td>
		
		<tr><td>
		合同状态：</td><td>
		<select name="state">
			<c:forEach var="contractState" items="${contractStateList}">
				<option value="${contractState}" <c:if test="${contractState== contractMeter.state}">selected="true"</c:if> >${contractState.name}</option>
			</c:forEach>
		</select>
		<tr><td>
		
		<tr><td>
		专业分类：</td><td>
		<select name="specialty">
			<c:forEach var="specialty" items="${specialtyList}">
				<option value="${specialty}" <c:if test="${specialty== contractMeter.specialty}">selected="true"</c:if> >${specialty.name}</option>
			</c:forEach>
		</select>
		<tr><td>
		
		<tr><td>
		签订日期：</td><td>
		<input type="text" name="signDate" class="datePK" value="${contractMeter.signDate}"/>
		<td></tr>
		
		<tr><td>
		交付日期：</td><td>
		<input type="text" name="delvDate" class="datePK" value="${contractMeter.delvDate}"/>
		<td></tr>
		
		<tr><td>
		合同金额：</td><td>
		<input type="text" name="contractAmt"  value="${contractMeter.contractAmt}"/>
		<td></tr>
		
		<tr><td>
		首付比例：</td><td>
		<input type="text" name="dpscale"  value="${contractMeter.dpscale}"/>
		<td></tr>
		
		<tr><td>
		结算金额：</td><td>
		<input type="text" name="finalAmt"  value="${contractMeter.finalAmt}"/>
		<td></tr>
		
		<tr><td>
		来源形式：</td><td>
		<input type="text" name="origins"  value="${contractMeter.origins}"/>
		<td></tr>
		
		<tr><td>
		责任部门：</td><td>
		<input type="text" name="dept"  value="${contractMeter.dept}"/>
		<td></tr>
		
		<tr><td>
		风险等级：</td><td>
		<select name="riskLevel">
			<c:forEach var="contractRisk" items="${contractRiskList}">
				<option value="${contractRisk}" <c:if test="${contractRisk== contractMeter.riskLevel}">selected="true"</c:if> >${contractRisk.name}</option>
			</c:forEach>
		</select>
		<tr><td>
		
		<tr><td>
		风险提示：</td><td>
		<input type="text" name="riskPrompt"  value="${contractMeter.riskPrompt}"/>
		<td></tr>
		
		<tr><td>
		评审结论：</td><td>
		<input type="text" name="conclusion"  value="${contractMeter.conclusion}"/>
		<td></tr>
		
		<tr><td>
		签订责任人：</td><td>
		
		<select name="leader.key">
			<c:forEach var="user" items="${userList}">
				<option value="${user.key}" <c:if test="${user.key== contractMeter.leader.key}">selected="true"</c:if> >${user.name}</option>
			</c:forEach>
		</select>
		<td></tr>
		
		<tr><td>
		总份数：</td><td>
		<input type="text" name="totalCopies"  value="${contractMeter.totalCopies}"/>
		<td></tr>
		
		<tr><td>
		留存份数：</td><td>
		<input type="text" name="saveCopies"  value="${contractMeter.saveCopies}"/>
		<td></tr>
		
	    </table>
	    
	    <%@ include file="/component/fileUpload.jsp" %>	


		<input type="button" value="保存"  id="btn_save">
		<input type="button" value="返回"  id="btn_back">
		
	</form>
	
	<c:set var="certid" value="${contractMeter.id}" />
	<c:set var="uptype" value="pm_contractmeter" />
	<%@ include file="/component/selPurchaseReq.jsp" %>	

</body>
</html>
