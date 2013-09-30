<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	


</head>
<body>
	<h2>功能</h2>
	<%@ include file="/common/message.jsp" %>	
	

	<a href="${ctx}/workflow/deploy">部署流程</a><br>
	<a href="${ctx}/workflow/manage">流程管理</a><br>
	<a href="${ctx}/workflow/running">流程运行</a><br>
	<a href="${ctx}/workflow/history">流程历史</a><br>
</body>
</html>
