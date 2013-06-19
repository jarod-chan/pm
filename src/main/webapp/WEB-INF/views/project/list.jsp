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
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/project/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'projectId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	
    	$('.btn_edit').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/project/'+param.id+'/edit','_self');
    		return false;
    	});
    	
    	$('.btn_pjmember').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
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
		
		项目负责人:<select name="leader.key">
			<option value="" >-所有-</option>
			<c:forEach var="user" items="${userList}">
				<option value="${user.key}" <c:if test="${user.key== query.leader.key}">selected="true"</c:if> >${user.name}</option>
			</c:forEach>
		</select>
	
		
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
	<table id="tblmain" border="1">
		<tr>
			<td>编号</td><td>名称</td><td>项目负责人</td>
			<td>状态</td>
			<td>项目投资计划批文</td>
			<td>土地使用权证</td>
			<td>用地许可证</td>
			<td>工程规划许可证</td>
			<td>工程施工许可证</td>
			<td>商品房预售许可证</td>
			<td>竣工验收规划确认书</td>
			<td>竣工验收备案</td>
			<td>开工日期</td>
			<td>竣工日期</td>
			<td>位置</td>
			<td>所占股份</td>
			<td>总投资</td>

			<td>操作</td>
		</tr>
		<c:forEach var="project" items="${projectList}">
			<tr>
				<td>${project.no}</td><td>${project.name}</td><td>${project.leader.name}</td>
				<td>${project.state.name}</td>
				<td>${project.inveplan}</td>
				<td>${project.landuseRight}</td>
				<td>${project.landusePermit}</td>
				<td>${project.projectPlan}</td>
				<td>${project.projectCnut}</td>
				<td>${project.perSale}</td>
				<td>${project.completionConfirm}</td>
				<td>${project.completionBackup}</td>
				<td>${project.begDate}</td>
				<td>${project.endDate}</td>
				<td>${project.location}</td>
				<td>${project.stock}</td>
				<td>${project.totalinve}</td>
				<td>
					<input type="button" param='{"id":"${project.id}"}' value="项目成员"  class="btn_pjmember">
					<input type="button" param='{"id":"${project.id}"}' value="修改"  class="btn_edit">
					<input type="button" param='{"id":"${project.id}"}' value="删除"  class="btn_delete">
				</td>
			</tr>
		</c:forEach>
		
	</table>
	
</body>
</html>
