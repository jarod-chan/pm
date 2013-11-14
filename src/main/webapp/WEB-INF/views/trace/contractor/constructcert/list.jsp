<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	
	<%@ include file="/common/jqui.jsp" %>


    <script type="text/javascript">
    $(function() {
    	
    	$(".datePK").datepicker();
    	    	
    	
    	$("#btn_new").click(function(){
			window.open('${ctx}/${projectId}/contractor/${supplierId}/constructcert/-1/edit','_self');
			return false;
		});
    	
    	$(".btn_edit").click(function(){
    		var param=$(this).metadata();
			window.open('${ctx}/${projectId}/contractor/${supplierId}/constructcert/'+param.id+'/edit','_self');
			return false;
		});
    	
    	$('.btn_delete').click(function(){
    		var param=$(this).metadata();
        	$('<form/>',{action:'${ctx}/${projectId}/contractor/${supplierId}/constructcert/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'constructCertId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	
    	$('.btn_view').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/${projectId}/contractor/${supplierId}/constructcert/{id}/view'.replace('{id}',param.id),'_self');
        	return false;
    	});
    	
    	$(".btn_trace").click(function(){
 	   		var param=$(this).metadata();
			window.open('${ctx}/trace/'+param.processDefKey+'/'+param.id,'_blank');
			return false;
		})
    	
    });
    </script>
</head>

<body>
	<h2>工程签证单</h2>
	
	<%@ include file="/common/message.jsp" %>	
	<div style="text-align: right;" id="headdiv">
		<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain"  class="hctable deftable">
		<thead>
		<tr>
			<th>序号</th><th>业务编号</th><th>施工联系单</th><th>专业分类</th><th>状态</th>
			<th>总金额</th>
			<th>制单人</th><th>制单日期</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="constructCertDto" items="${ConstructCertDtoList}">
			<tr>
				<td>${constructCertDto.constructCert.no}</td>
				<td>${constructCertDto.constructCert.busino}</td>
				<td>${constructCertDto.constructCont.no}</td>
				<td>${constructCertDto.constructCert.constructKey.contract.specialty.name}</td>
				<td><span class="state state-${constructCertDto.constructCert.state}" >${constructCertDto.constructCert.state.name}</span></td>
				<td>${constructCertDto.constructCert.tolsum}</td>
				<td>${constructCertDto.constructCert.creater.name}</td>
				<td><fmt:formatDate value="${constructCertDto.constructCert.createdate}" pattern="yyyy-MM-dd"/></td>
				<td>
					<c:if test="${constructCertDto.constructCert.state=='saved'}">	
					<input type="button" value="修改"  class="btn_edit {id:'${constructCertDto.constructCert.id}'}">
					<input type="button" value="删除"  class="btn_delete {id:'${constructCertDto.constructCert.id}'}">
					</c:if>
					<input type="button" value="查看"  class="btn_view {id:'${constructCertDto.constructCert.id}'}">
					<c:if test="${constructCertDto.constructCert.state=='commit'}">
						<input type="button" value="跟踪"  class="btn_trace {id:'${constructCertDto.constructCert.id}',processDefKey:'pm-construct-cert'}">
					</c:if>
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<script type="text/javascript">
		$(function(){
			$("#headdiv").css("width",$("#tblmain").css("width"));
		})
	</script>
	



	
</body>
</html>
