<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
			<h2>施工联系单</h2>
		</div>
		<div class="c_no">
			<span class="b_busino">业务编号：${constructCont.busino}(${constructCont.no})</span>
		</div>
		<div class="c_table">
			<table class="pt_table">
				<tr>
					<td>项目名称</td><td>${constructCont.constructKey.project.name}</td>
					<td>专业分类</td><td>${constructCont.constructKey.contract.specialty.name}</td>
				</tr>
				<tr>
					<td>施工承包方</td><td>${constructCont.constructKey.contract.supplier.name}</td>
					<td>合同</td><td>${constructCont.constructKey.contract.no}	</td>
				</tr>
				<tr >
					<td >原因</td>
					<td colspan="3" class="td_text">${constructCont.reason}</td>
				</tr>
				<tr >
					<td >联系项目</td>
					<td colspan="3" class="td_item">
					<table class="item_table">
						<thead>
							<tr>
								<th style="width: 40px;">序号</th><th >内容</th><th style="width: 80px;">暂定单价</th><th style="width: 80px;">暂定数量</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach items="${constructCont.constructContItems}" var="item">
							<tr class="datacls {itemId: '${item.id}'} ">
								<td>${item.sn}</td><td>${item.content}</td><td>${item.price}</td><td>${item.numb}(${item.unit})</td>
							</tr>
							</c:forEach>
						</tbody>
					</table>
					<div style="text-align: right;padding-right: 5px;">
						总金额:${constructCont.tolsum}
					</div>
					</td>
				</tr>
				<tr>
					<td>计划完成日期</td><td>${constructCont.plandate}</td>
					<td>实际完成日期</td><td>${constructCont.realdate}</td>
				</tr>
				
				<tr>
					<td>实际执行结果</td><td colspan="3" class="td_text">${constructCont.result}</td>
				</tr>
				
				<tr>
					<td>制单人</td><td>${constructCont.creater.name}</td>
					<td>制单日期</td><td><fmt:formatDate value="${constructCont.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
				
				<tr>
					<td>审核人</td><td>${checkerOpinion['shr'].userName}@<fmt:formatDate value="${checkerOpinion['shr'].date}" pattern="yyyy-MM-dd"/></td>
					<td>审批意见</td>
					<td>[${checkerOpinion['shr'].result.name}]${checkerOpinion['shr'].content}</td>
				</tr>
				
				<tr>
					<td>核准人</td><td>${checkerOpinion['hzr'].userName}@<fmt:formatDate value="${checkerOpinion['hzr'].date}" pattern="yyyy-MM-dd"/></td>
					<td>审批意见</td>
					<td>[${checkerOpinion['hzr'].result.name}]${checkerOpinion['hzr'].content}</td>
				</tr>
				
				<tr>
					<td>签发人</td><td>${checkerOpinion['qfr'].userName}@<fmt:formatDate value="${checkerOpinion['qfr'].date}" pattern="yyyy-MM-dd"/></td>
					<td>审批意见</td>
					<td>[${checkerOpinion['qfr'].result.name}]${checkerOpinion['qfr'].content}</td>
				</tr>
				
				<tr>
					<td>接收人</td><td>${constructCont.receiver.name}</td>
					<td>接收日期</td><td ><fmt:formatDate value="${constructCont.receivedate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				</tr>
			</table>
		</div>
	</div>
	
</body>
</html>
