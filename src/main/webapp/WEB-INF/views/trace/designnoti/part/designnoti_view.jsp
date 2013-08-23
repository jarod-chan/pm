<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<%@ include file="/script/fmttable.jsp" %>

	<table id="tabmain" class="fmttable">
		<c:set var="parma_no" value="${designNoti.no}" />
		<c:set var="parma_busino" value="${designNoti.busino}" />
		<%@ include file="/component/noShowBill.jsp" %>	
	
		<tr>
			<td>项目负责人：</td>
			<td>${designNoti.leader.name}</td>
			<td>状态：</td>
			<td><span class="state state-${designNoti.state}" >${designNoti.state.name}</span></td>
		</tr>
		
		<tr>
			<td>图号：</td>
			<td>
				${designNoti.graphno}
			</td>
		</tr>
		
		<tr>
			<td>变更部位：</td>
			<td colspan="3">
				${designNoti.postion}
			</td>
		</tr>
		
		<tr>
			<td>制单人：</td><td>${designNoti.creater.name}</td>
			<td>制单日期：</td><td><fmt:formatDate value="${designNoti.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>签发人：</td><td>${designNoti.signer.name}</td>
			<td>签发日期：</td><td><fmt:formatDate value="${designNoti.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>接收人：</td>	<td>${designNoti.receiver.name}</td>
			<td>接收日期：</td><td><fmt:formatDate value="${designNoti.receivedate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	
	</table>
	
	<%@ include file="/component/fileDnload.jsp" %>
	
	<h3>问题项目</h3>
	<table id="tabitem" class="deftable">
	<thead>
		<tr>
				<th>序号</th><th>内容</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${designNoti.designNotiItems}" var="item">
		<tr class="datacls {itemId: '${item.id}'} ">

			<td>${item.sn}</td>
			<td>${item.content}</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	<%@ include file="/component/opinionDiv.jsp" %>