<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ include file="/script/fmttable.jsp" %>	

<table id="tabmain" class="fmttable">

		<c:set var="parma_no" value="${constructCont.no}" />
		<c:set var="parma_busino" value="${constructCont.busino}" />
		<%@ include file="/component/noShowBill.jsp" %>
		
		<tr>
			<td>项目负责人：</td><td>${constructCont.leader.name}</td>
			<td>状态：</td><td>${constructCont.state.name}</td>
		</tr>
		
		<tr>
			<td>合同：</td>
			<td>
				${constructCont.constructKey.contract.no}
			</td>
			<td>施工承包方：</td><td>${constructCont.constructKey.contract.supplier.name}</td>
		</tr>
		<tr >
			<td style="vertical-align: top">原因：</td><td colspan="3" class="viewtextarea_td">${constructCont.reason}</td>
		</tr>
		
		<tr>
			<td>计划完成日期：</td>
			<td>
				${constructCont.plandate}
			</td>
			<td>实际完成日期：</td>
			<td>
				${constructCont.realdate}
			</td>
			
		</tr>
		
		<tr>
			<td>实际执行结果：</td>
			<td>
				${constructCont.result}
			</td>
			<td>总金额：</td><td>${constructCont.tolsum}</td>
		</tr>
		
		<tr>
			<td>制单人：</td><td>${constructCont.creater.name}</td>
			<td>制单日期：</td><td><fmt:formatDate value="${constructCont.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>签发人：</td><td>${constructCont.signer.name}</td>
			<td>签发日期：</td><td><fmt:formatDate value="${constructCont.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>接收人：</td>
			<td>${constructCont.receiver.name}
			</td>
			<td>接收日期：</td>
			<td >
				${constructCont.receivedate}
			</td>
		</tr>
	</table>
	<br>

	联系项目
	<table border="1">
	<thead>
		<tr>
			<th>序号</th><th>内容</th><th>暂定单价</th><th>暂定数量</th><th>单位</th><th>暂定结算价</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${constructCont.constructContItems}" var="item">
		<tr class="datacls {itemId: '${item.id}'} ">
			<td>${item.sn}</td><td>${item.content}</td><td>${item.price}</td><td>${item.numb}</td><td>${item.unit}</td><td>${item.amount}</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	
	<%@ include file="/component/opinionDiv.jsp" %>

	
	
