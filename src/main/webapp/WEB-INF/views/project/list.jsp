<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	


    <script type="text/javascript">
    $(function() {
    	
    	$("#btn_new").click(function(){
			window.open('${ctx}/project/-1/edit','_self');
			return false;
		});
    	
    	$('.btn_delete').click(function(){
    		var param=$(this).metadata();
        	$('<form/>',{action:'${ctx}/project/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'projectId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	
    	$('.btn_edit').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/project/'+param.id+'/edit','_self');
    		return false;
    	});
    	
    	$('.btn_pjmember').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/project/'+param.id+'/pjmember','_self');
			return false;
    	});
    	
    	$('#btn_query').click(function(){
			var actionFrom=$("form:eq(0)");
			var oldAction=actionFrom.attr("action"); 
			actionFrom.attr("action",oldAction+"/list").submit();
    	});
    	
    	$('#btn_clear').click(function(){
    		window.open('${ctx}/project/list','_self');
			return false;
    	});
    	
    	$("#headdiv").css("width",$("#tblmain").css("width"));
    });
    </script>
    

</head>

<body>
	<h2>项目</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<div style="text-align: left;">
	<form action="${ctx}/project" method="post">
		编号:<input type="text" name="no" value="${query.no}">
		
		名称:<input type="text" name="name" value="${query.name}">
		
		<br>
		排序:<select name="orderAttribute">
				<c:forEach var="attr" items="${query.orderAttributeList}">
					<option value="${attr.value}" <c:if test="${attr.value== query.orderAttribute}">selected="true"</c:if> >${attr.name}</option>
				</c:forEach>
			</select> 
			<select name="orderType">
				<c:forEach var="type" items="${query.orderTypeList}">
					<option value="${type.value}" <c:if test="${type.value== query.orderType}">selected="true"</c:if> >${type.name}</option>
				</c:forEach>
			</select>  
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="查询" id="btn_query"> 
		<input type="button" value="清空" id="btn_clear"> 
	</form>
	</div>	
	
	<div id="headdiv" style="text-align: right;">
	<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain" class="hctable deftable">
		<thead>
		<tr>
			<th>编号</th>
			<th>名称</th>
			<th>状态</th>
			<th>开工日期</th>
			<th>竣工日期</th>
			<th>所占股份</th>
			<th>总投资</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="project" items="${projectList}">
			<tr>
				<td>${project.no}</td>
				<td>${project.name}</td>
				<td>${project.state.name}</td>
				<td>${project.begDate}</td>
				<td>${project.endDate}</td>
				<td>${project.stock}</td>
				<td>${project.totalinve}</td>
				<td>
					<input type="button"  value="项目成员"  class="btn_pjmember {id:'${project.id}'}">
					<input type="button"  value="修改"  class="btn_edit {id:'${project.id}'}">
					<input type="button"  value="删除"  class="btn_delete {id:'${project.id}'}">
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
</body>
</html>
