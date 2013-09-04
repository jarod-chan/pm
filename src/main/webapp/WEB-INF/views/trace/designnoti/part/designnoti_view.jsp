<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<script type="text/javascript">
	 $(function(){
		 function openItemFile(){
			$(this).parent().prev().addClass("open_td");
			$( ".uploadify_item" ).dialog( "open" );
		 }
		 $(".btn_itemfile").bind("click",openItemFile);
	 })
	</script>

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
			<td>技术分类：</td>
			<td>${designNoti.techType.name}</td>
		</tr>
		
		<tr>
			<td style="vertical-align: top">原因说明：</td><td colspan="3" class="viewtextarea_td">${designNoti.reason}</td>
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
	
	<%@ include file="viewFileItem.jsp" %>
	
	<h3>问题项目</h3>
	<table id="tabitem" class="deftable">
	<thead>
		<tr>
				<th>序号</th><th>内容</th><th>图号</th><th colspan="2" style="width:200px;">附件</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${designNoti.designNotiItems}" var="item">
		<tr class="datacls {itemId: '${item.id}'} ">

			<td>${item.sn}</td>
			<td>${item.content}</td>
			<td>${item.graphno}</td>
	  		
	  		<td style="width: 150px;">
	  			<c:forEach items="${fileMap[item.sn]}" var="file" varStatus="status"><span class='sp_itemfile'><a href="${ctx}/uploadify/filestore/${file.id}">${status.count}</a><input type='hidden' name='itemfileName' value="${file.filename}.${file.suffix}" /><input type='hidden' name='itemfileSn' /><input type='hidden' name='itemfileId' value="${file.id}"/></span></c:forEach>
	  		</td>
	  		<td>
	  			<c:if test="${not empty  fileMap[item.sn]}"><input type='button' class='btn_itemfile'  value='打开'   /></c:if>
	  		</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	<%@ include file="/component/opinionDiv.jsp" %>