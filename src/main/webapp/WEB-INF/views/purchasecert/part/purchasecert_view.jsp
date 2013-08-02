<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	 <%@ include file="/script/fmttable.jsp" %>

	<input type="hidden" name="id"  value="${purchaseCert.id}">
	<table id="tabmain" class="fmttable">

		<c:set var="parma_no" value="${purchaseCert.no}" />
		<c:set var="parma_busino" value="${purchaseReq.busino}" />
		<%@ include file="/component/noShowBill.jsp" %>	

		<tr>
			<td>项目负责人：</td><td>${purchaseCert.leader.name}</td>
			<td>状态：</td><td>${purchaseCert.state.name}</td>
		</tr>
		
		
		<tr>
			<td>采购申请单：</td> 
			<td colspan="3" >
				${purchaseReq.no}
			</td>
		</tr>
		<tr>
			<td>关联采购明细：</td> 
			<td colspan="3">
				<table id="purchaseReqItem" border="1">
				<thead>
					<tr>
						<th>序号</th><th>材料名称</th><th>型号规格和技术指标</th><th>单位</th><th>数量</th><th>推荐品牌</th><th>执行结果</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach  items="${purchaseReq.purchaseReqItems}" var="item">
						<tr  <c:if test="${item.upid==purchaseCert.id}">style="background-color:#A6CAF0;"</c:if>>
							<td>${item.sn}</td>
							<td>${item.metername}</td>
							<td>${item.spec}</td>
							<td>${item.unit}</td>
							<td>${item.numb}</td>
							<td>${item.brand}</td>
							<td>
							<c:if test="${not empty item.upid}">
							${item.uptype.name}[${item.upno}]执行采购
							</c:if>
							</td>
						</tr>
					</c:forEach>
				</tbody>
				</table>
			</td>
		</tr>
		
		<tr>
			<td style="vertical-align: top">说明：</td>
			<td colspan="3" >${purchaseCert.descrp}</td>
		</tr>
		
		<tr>
			<td>总金额：</td><td>${purchaseCert.tolsum}</td>
		</tr>
		
		<tr>
			<td>制单人：</td><td>${purchaseCert.creater.name}</td>
			<td>制单日期：</td><td><fmt:formatDate value="${purchaseCert.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>签发人：</td><td>${purchaseCert.signer.name}</td>
			<td>签发日期：</td><td><fmt:formatDate value="${purchaseCert.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>接收人：</td>
			<td>
				${purchaseCert.receiver.name}
			</td>
			<td>接收日期：</td>
			<td>
				${purchaseCert.receivedate}
			</td>
		</tr>
	

	</table>
	<br>
	确认项目
	<table id="tabitem" border="1">
	<thead>
		<tr>
			<th>序号</th><th>材料名称</th><th>型号规格和技术指标</th><th>品牌</th><th>单位</th><th>数量</th><th>单价</th><th>金额</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${purchaseCert.purchaseCertItems}" var="item">
		<tr class="datacls {itemId: '${item.id}'} ">
			<td>${item.sn}</td>
			
			<td>${item.metername}</td>
	
			<td>${item.spec}</td>
			
			<td>${item.brand}</td>
	
		    <td>${item.unit}</td>
	
			<td><fmt:formatNumber value="${item.numb}" pattern="#.#"/></td>
			
			<td>${item.price}</td>
			
			<td>${item.amount}</td>
	  
		</tr>
		</c:forEach>
	</tbody>
	</table>
	<%@ include file="/component/opinionDiv.jsp" %>