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
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/user/'+param.key+'/edit','_self');
    		return false;
    	});
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
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
	<table id="tblmain" border="1">
		<tr>
			<td>用户名</td>
			<td>真实姓名</td>
			<td>公司邮箱</td>
			<td>电话</td>
			<td>密码</td>
			<td>状态</td>
			<td>操作</td>
		</tr>
		<c:forEach var="user" items="${users}">
			<tr>
				<td>${user.key}</td>
				<td>${user.name}</td>
				<td>${user.email}</td>
				<td>${user.cellphone}</td>
				<td>${user.password}</td>
				<td>${user.enabled.name}</td>
				<td>
					<input type="button" param='{"key":"${user.key}"}' value="修改"  class="btn_edit">
				</td>
			</tr>
		</c:forEach>
		
	</table>
	
</body>
</html>
