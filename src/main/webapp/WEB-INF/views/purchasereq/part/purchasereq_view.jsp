<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<script type="text/javascript">
    $(function(){
    	$('#tabmain tr').find('td:eq(0)').css("text-align","right");
    })
	</script>

	<table id="tabmain">

		<c:set var="parma_no" value="${purchaseReq.no}" />
		<c:set var="parma_busino" value="${purchaseReq.busino}" />
		<%@ include file="/component/noShowBill.jsp" %>	

		<tr>
			<td>项目负责人：</td><td>${purchaseReq.leader.name}</td>
		</tr>
		<tr>
			<td style="vertical-align: top">说明：</td><td>${purchaseReq.descrp}</td>
		</tr>
			<tr>
			<td>计划进场日期：</td>
			<td>
				<fmt:formatDate value="${purchaseReq.plandate}" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
		<tr>
			<td>状态：</td><td>${purchaseReq.state.name}</td>
		</tr>
		<tr>
			<td>制单人：</td><td>${purchaseReq.creater.name}</td>
		</tr>
		<tr>
			<td>制单日期：</td><td><fmt:formatDate value="${purchaseReq.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>签发人：</td><td>${purchaseReq.signer.name}</td>
		</tr>
		<tr>
			<td>签发日期：</td><td><fmt:formatDate value="${purchaseReq.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>接收人：</td>
			<td>
				${purchaseReq.receiver.name}
			</td>
		</tr>
		<tr>
			<td>接收日期：</td>
			<td>
				${purchaseReq.receivedate}
			</td>
		</tr>
	

	</table>
	<br>
	采购项目
	<table id="tabitem" border="1">
	<thead>
		<tr>
			<th>序号</th><th>材料名称</th><th>型号规格和技术指标</th><th>单位</th><th>数量</th><th>推荐品牌</th><th>执行结果</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${purchaseReq.purchaseReqItems}" var="item">
		<tr>

			<td>${item.sn}</td>
			<td>${item.metername}</td>
	
			<td>${item.spec}</td>
	
		    <td>${item.unit}</td>
	
			<td><fmt:formatNumber value="${item.numb}" pattern="#.#"/></td>
			
			<td>${item.brand}</td>
	  		
	  		<td>
	  			<c:if test="${not empty item.upid}">	  			
	  			${item.uptype.name}[${item.upno}]采购
	  			</c:if>
	  		</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	<%@ include file="/component/opinionDiv.jsp" %>