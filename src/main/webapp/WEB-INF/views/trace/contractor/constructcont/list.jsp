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
			window.open('${ctx}/${projectId}/contractor/${supplierId}/constructcont/-1/edit','_self');
			return false;
		});
    	

    	$('.btn_edit').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/${projectId}/contractor/${supplierId}/constructcont/{id}/edit'.replace('{id}',param.id),'_self');
        	return false;
    	});
    	
    	$('.btn_delete').click(function(){
    		var param=$(this).metadata();
        	$('<form/>',{action:'${ctx}/${projectId}/contractor/${supplierId}/constructcont/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'constructContId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	
    	$('.btn_view').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/${projectId}/contractor/${supplierId}/constructcont/{id}/view'.replace('{id}',param.id),'_self');
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
	
	<h2>施工联系单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<div style="text-align: right;" id="headdiv">
		<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain"  class="hctable deftable">
		<thead>
			<tr>
				<th>序号</th>
				<th>业务编号</th>
				<th>合同</th>
				<th>专业分类</th>
				<th>状态</th>
				<th>制单人</th>
				<th>制单日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="constructCont" items="${constructContList}">
				<tr>
					<td>${constructCont.no}</td>
					<td>${constructCont.busino}</td>
					<td>${constructCont.constructKey.contract.name}</td>
					<td>${constructCont.constructKey.contract.specialty.name}</td>
					<td><span class="state state-${constructCont.state}" >${constructCont.state.name}</span></td>
					<td>${constructCont.creater.name}</td>
					<td><fmt:formatDate value="${constructCont.createdate}" pattern="yyyy-MM-dd"/></td>
					<td>
						<c:if test="${constructCont.state=='saved'}">					
						<input type="button"  value="修改"  class="btn_edit {id:'${constructCont.id}'}">
						<input type="button"  value="删除"  class="btn_delete {id:'${constructCont.id}'}">
						</c:if>
						<input type="button" value="查看"  class="btn_view  {id:'${constructCont.id}'}">
						<c:if test="${constructCont.state=='commit'}">
							<input type="button" value="跟踪"  class="btn_trace {id:'${constructCont.id}',processDefKey:'pm-construct-cont'}">
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
