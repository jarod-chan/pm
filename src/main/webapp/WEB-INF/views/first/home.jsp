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
			window.open('${ctx}/project/${project.id}/constructkey/edit','_self');
			return false;
		});
    	
   
    });
    </script>
</head>

<body>
	<h2>&nbsp;</h2>
	<%@ include file="/common/message.jsp" %>	
	<div style="font-size: 30px;font-weight: bold;margin-top: 100px;margin-left: 100px;">方远项目管理演示版本
		<br>点击菜单跳转相应功能
	</div>
</body>
</html>
