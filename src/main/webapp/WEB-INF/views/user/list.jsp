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
			window.open('${ctx}/user/-1/edit','_self');
			return false;
		});
    	
    	$('.btn_edit').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/user/'+param.key+'/edit','_self');
    		return false;
    	});
    	
    	$('.btn_delete').click(function(){
    		var param=$(this).metadata();
        	$('<form/>',{action:'${ctx}/project/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'projectId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	

    	
    	$("#headdiv").css("width",$("#tblmain").css("width"));
    });
    </script>
    

</head>

<body>
	<h2>系统用户</h2>
	<%@ include file="/common/message.jsp" %>	
	
	
	<div id="headdiv" style="text-align: right;">
	<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain" class="hctable deftable">
		<thead>
		<tr>
			<th>用户名</th>
			<th>真实姓名</th>
			<th>公司邮箱</th>
			<th>电话</th>
			<th>密码</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="user" items="${users}">
			<tr>
				<td>${user.key}</td>
				<td>${user.name}</td>
				<td>${user.email}</td>
				<td>${user.cellphone}</td>
				<td>${user.password}</td>
				<td>${user.enabled.name}</td>
				<td>
					<input type="button" value="修改"  class="btn_edit {key:'${user.key}'}">
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	
</body>
</html>
