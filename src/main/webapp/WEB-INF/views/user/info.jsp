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
    			}
    		}
    	});
		
    	$("#btn_save").click(function(){
    			if($("#p").is(":visible")){
    				$("#p").rules("add", {  
    					 required: true,  
    					 minlength: 6
    				})
    			}else{
    				$("#p").rules("remove","required minlength")
    			}
    			if(!validator.form()){return;}    			
				var actionFrom=$("form");
				var oldAction=actionFrom.attr("action");
				if($("#p").is(":visible")){
					var p=$("#p").val();
					$("#set-password").val(hex_sha1(hex_sha1(p)));
				}else{
					$("#set-password").val("");
				}
				
				actionFrom.attr("action",oldAction+"/save").submit(); 
		});
		
		$("#btn_reset").click(function(){
			if($("#p").is(":visible")){
				$(this).prev("label").remove();
				$(this).html(">重置密码");
				$("#p").hide();
			}else{
				$(this).html("<取消重置");
				$("#p").show();
			}
		})
		
		$('#tabmain tr').find('td:eq(0)').css("text-align","right");
		
	 });
    </script>
</head>

<body>
	<h2>用户信息</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/user/${userInfo.key}/info" method="post">
	
	
	<table id="tabmain">	
		
		<tr><td>
		用户名：</td><td>
			<input type="hidden" name="key" value="${userInfo.key}" />${userInfo.key}
		</td></tr>
		
		<tr><td>
		真实姓名：</td><td>
		${userInfo.name}
		</td></tr>
		
		<tr><td>
		公司邮箱：</td><td>
		<input type="text" name="email" value="${userInfo.email}" />
		</td></tr>
		
		<tr>
		<td>电话：</td>
		<td>
		<input type="text" name="cellphone" value="${userInfo.cellphone}"/>
		</td>
		</tr>
		
		<tr>
		<td>密码：</td>
		<td>
			<input type="text" id="p" name="p"  value="" style="display: none" /><span  id="btn_reset" class="span_btn" >&gt;重置密码</span>
			<input type="hidden" id="set-password" name="set-password" value="" />
		</td>
		</tr>
		
		<tr>
		<td>状态：</td>
		<td>
			${userInfo.enabled.name}
		</td>
		</tr>
		
		<tr>
		<td>系统角色：</td>
		<td>
			${userRole.name}
		</td>
		</tr>
		
		
		</table>
		
		<br>
	 	<input type="button" value="保存"  id="btn_save">
		

	</form>
	
</body>
</html>
