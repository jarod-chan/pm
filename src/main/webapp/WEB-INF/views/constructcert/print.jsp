<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/print.jsp" %>


</head>	

<body>
	<div class="pt_container">
		<div class="c_title">
			<%@ include file="/common/companyname.jsp" %>
			<h2>工程签证单</h2>
		</div>
		<div class="c_no">
			<span class="b_busino">业务编号：${constructCert.busino}(${constructCert.no})</span>
		</div>
		<div class="c_table">
			<table class="pt_table">
				<tr>
					<td>项目名称</td><td>${constructCert.constructKey.project.name}</td>
					<td>专业分类</td><td>${constructCert.constructKey.contract.specialty.name}</td>
				</tr>
				<tr>
					<td>施工承包方</td><td>${constructCert.constructKey.contract.supplier.name}</td>
					<td>合同</td><td>${constructCert.constructKey.contract.no}	</td>
				</tr>
				
				<tr >
					<td >施工联系单</td>
					<td >${constructCont.no}</td>
				</tr>
				
				<tr >
					<td >原因</td>
					<td colspan="3" class="td_text">${constructCert.reason}</td>
				</tr>
				<tr >
					<td >签证项目</td>
					<td colspan="3" class="td_item">
					<table class="item_table">
						<thead>
							<tr>
								<th style="width: 40px;">序号</th><th >内容</th><th style="width: 80px;">结算单价</th><th style="width: 80px;">结算数量</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${constructCert.constructCertItems}" var="item">
							<tr class="datacls {itemId: '${item.id}'} ">
								<td>${item.sn}</td><td>${item.content}</td><td>${item.price}</td><td>${item.numb}(${item.unit})</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
					<div style="text-align: right;padding-right: 5px;">
						总金额:${constructCert.tolsum}
					</div>
					</td>
				</tr>
				
				<tr>
					<td>制单人</td><td>${constructCert.creater.name}</td>
					<td>制单日期</td><td><fmt:formatDate value="${constructCert.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				
				<tr>
					<td>审核人</td><td>${flowChecker['shr'].userName}</td>
					<td>审核日期</td><td><fmt:formatDate value="${flowChecker['shr'].date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				
				<tr>
					<td>核准人</td><td>${flowChecker['hzr'].userName}</td>
					<td>核准日期</td><td><fmt:formatDate value="${flowChecker['hzr'].date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				
				<tr>
					<td>签发人</td><td>${flowChecker['qfr'].userName}</td>
					<td>签发日期</td><td><fmt:formatDate value="${flowChecker['qfr'].date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				
				<tr>
					<td>结算人</td><td>${constructCert.settler.name}</td>
					<td>结算日期</td><td ><fmt:formatDate value="${constructCert.settledate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</table>
		</div>
	</div>

</body>
</html>
