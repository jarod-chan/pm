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
	
	<a href="${ctx}/project/list">项目</a><br>
	<a href="${ctx}/supplier/list">供应商</a><br>
	<a href="${ctx}/contract/list">合同</a><br>
	<a href="${ctx}/constructcont/list">施工联系单</a><br>
	<a href="${ctx}/constructcert/list">施工签证单</a><br>
</body>
</html>
