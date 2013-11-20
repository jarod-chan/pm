<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	
	<script src="${ctx}/plu/jshash-2.2/sha1-min.js" type="text/javascript"></script>

    <script type="text/javascript">
    $(function() {
    	
    	var validator=$("form").validate({
    		rules: {
    			key:{
    				required: true,
    				minlength:2,
    				maxlength:12
    			},
    			name: {
    				required: true,
    				maxlength: 6
    			},
    			p:{
    				required: true,
    				minlength:6
    			}
    		}
    	});
		
    	$("#btn_save").click(function(){
    			if(!validator.form()){return;}    			
				var actionFrom=$("form");
				var oldAction=actionFrom.attr("action");
				var p=$("#p").val();
				$("#set-password").val(hex_sha1(hex_sha1(p)));
				$("#p").val("******");//防止密码被明文传输
				actionFrom.attr("action",oldAction+"/save").submit(); 
		});
		
		$("#btn_back").click(function(){
			window.open('${ctx}/user/list','_self');
			return false;
		});
		
		$('#tabmain tr').find('td:eq(0)').css("text-align","right");
		
	 });
    </script>
</head>

<body>
	<h2>系统用户</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/user" method="post">
	
	<table id="tabmain">	
		
		<tr><td>
		用户名：</td><td>
		<input type="text" name="key" value="${user.key}"/>
		</td></tr>
		
		<tr><td>
		真实姓名：</td><td>
		<input type="text" name="name" value="${user.name}"  type="text" />
		</td></tr>
		
		<tr><td>
		公司邮箱：</td><td>
		<input type="text" name="email" value="${user.email}" />
		</td></tr>
		
		<tr>
		<td>电话：</td>
		<td>
		<input type="text" name="cellphone" value="${user.cellphone}"/>
		</td>
		</tr>
		
		<tr>
		<td>密码：</td>
		<td>
		<input type="text" id="p" name="p"  value="${user.password}" />
		<input type="hidden" id="set-password" name="set-password" value="" />
		</td>
		</tr>
		
		<tr>
		<td>状态：</td>
		<td>
			<select name="enabled">
				<c:forEach var="enabled" items="${enableds}">
					<option value="${enabled}" <c:if test="${user.enabled==enabled}">selected="true"</c:if> >${enabled.name}</option>
				</c:forEach>
			</select>
		</td>
		</tr>
		
		<tr>
		<td>系统角色：</td>
		<td>
			<select name="roleKey">
				<option value="">--</option>
				<c:forEach var="role" items="${roles}">
					<option value="${role.key}" <c:if test="${role.key==userRole.key}">selected="true"</c:if> >${role.name}</option>
				</c:forEach>
			</select>
		</td>
		</tr>
		
		
		</table>
		
		<br>
	 	<input type="button" value="保存"  id="btn_save">
		<input type="button" value="返回"  id="btn_back"> 
		

	</form>
	
</body>
</html>
