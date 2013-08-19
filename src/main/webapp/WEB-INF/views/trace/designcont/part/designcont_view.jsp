<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	 <%@ include file="/script/fmttable.jsp" %>

	<input type="hidden" name="id"  value="${purchaseCert.id}">
	<table id="tabmain" class="fmttable">
	<c:set var="parma_no" value="${designCont.no}" />
	
		<c:set var="parma_busino" value="${designCont.busino}" />
		<%@ include file="/component/noShowBill.jsp" %>	

		<tr>
			<td>项目负责人：</td><td>${designCont.leader.name}</td>
			<td>状态：</td><td><span class="state state-${designCont.state}" >${designCont.state.name}</span></td>
		</tr>
		
		<tr>
			<td>问题通知单：</td> 
			<td>
				<span id="spanNoti">${designCont.designNoti.no}</span>
			</td>
		</tr>
		
		<tr>
			<td>设计策划合同：</td>
			<td>
				<span id="spanContract">${designCont.contract.no}</span>
			</td>
			<td>设计服务商：</td>
			<td>
			 <span id="supplier_name">${designCont.contract.supplier.no}</span>
			</td>
		</tr>
		
		<tr>
			<td>技术变更原因：</td>
			<td colspan="3">
				${designCont.reason.type.name}:${designCont.reason.en}.${designCont.reason.name}
			</td>
		</tr>	
		
		<tr>
			<td>制单人：</td><td>${designCont.creater.name}</td>
			<td>制单日期：</td><td><fmt:formatDate value="${designCont.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>

		<tr>
			<td>签发人：</td><td>${designCont.signer.name}</td>
			<td>签发日期：</td><td><fmt:formatDate value="${designCont.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>接收人：</td>	<td>${designCont.receiver.name}</td>
			<td>接收日期：</td><td><fmt:formatDate value="${designCont.receivedate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
			

	</table>
	
	<h3>联系内容</h3>
	<table id="tabitem" class="deftable">
	<thead>
		<tr>
			<th>序号</th><th>变更内容</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${designCont.designContItems}" var="item">
		<tr>
			
			<td>${item.sn}</td>
			
			<td>${item.content}</td>
	  
		</tr>
		</c:forEach>
	</tbody>
	</table>
	<%@ include file="/component/opinionDiv.jsp" %>