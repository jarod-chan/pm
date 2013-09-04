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
			<td>技术分类：</td>
			<td>${designCont.techType.name}</td>
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
			<td style="vertical-align: top">联系内容：</td>
			<td colspan="3" class="viewtextarea_td" >${designCont.content}</td>
		</tr>
		
		<tr>
			<td>技术变更原因：</td>
			<td colspan="3">
				${designCont.reason.type.name}:${designCont.reason.en}.${designCont.reason.name}
			</td>
		</tr>	
		
		<tr>
			<td style="vertical-align: top">相关文件：</td>
			<td colspan="3">
					<c:set var="upload_width" value="500" />
					<%@ include file="/component/fileDnload.jsp" %>
			</td>
		</tr>

		<tr>
			<td style="vertical-align: top">文件说明：</td>
			<td colspan="3" class="viewtextarea_td" >${designCont.filedesc}</td>
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
	
	<%@ include file="/component/opinionDiv.jsp" %>