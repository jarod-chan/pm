<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
  $(function() {
	
	$("#btn_cont").click(function(){
	  		window.open('${ctx}/constructcont/${constructCont.id}/view?notback=true','_blank');
		return false;
	  	})
  })
  </script>

<%@ include file="/script/fmttable.jsp" %>

<table id="tabmain"  class="fmttable">
		<c:set var="parma_no" value="${constructCert.no}" />
		<c:set var="parma_busino" value="${constructCert.busino}" />
		<%@ include file="/component/noShowBill.jsp" %>	
		
		<tr>
			<td>项目负责人：</td><td>${constructCert.leader.name}</td>
			<td>状态：</td><td>${constructCert.state.name}</td>
		</tr>
		
		<tr>
			<td>施工联系单：</td>
			<td>
				${constructCont.no}<input type="button" id="btn_cont" value="查看施工联系单"/>
			</td>
		</tr>
		
		<tr>
			<td style="vertical-align: top">原因：</td><td colspan="3" class="viewtextarea_td">${constructCert.reason}</td>
		</tr>
		
		<tr>
			<td>总金额：</td><td>${constructCert.tolsum}</td>
		</tr>
		
		<tr>
			<td>制单人：</td><td>${constructCert.creater.name}</td>
			<td>制单日期：</td><td><fmt:formatDate value="${constructCert.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	
		<tr>
			<td>签发人：</td><td>${constructCert.signer.name}</td>
			<td>签发日期：</td><td><fmt:formatDate value="${constructCert.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>结算人：</td>
			<td>
			${constructCert.settler.name}
			</td>
			<td>结算日期：</td>
			<td>
				<fmt:formatDate value="${constructCert.settledate}" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
		
	</table>
		
	<br>
	签证项目
	<table border="1" id="tabitem">
	<thead>
		<tr>
			<th>序号</th><th>内容</th><th>结算单价</th><th>结算数量</th><th>单位</th><th>结算价格	</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${constructCert.constructCertItems}" var="item">
			<tr>
				<td>${item.sn}</td>
				<td>${item.content}</td>
				<td>${item.price}</td>
				<td>${item.numb}</td>
				<td>${item.unit}</td>
				<td>${item.amount}</td>
			</tr>
		</c:forEach>
	</tbody>
	</table>
	
	<%@ include file="/component/opinionDiv.jsp" %>