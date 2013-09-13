<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>
	<%@ include file="/common/jqui.jsp" %>	
	<%@ include file="/common/jqui2.jsp" %>		

</head>

<body>
	<h2>问题通知单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<%@ include file="part/designnoti_view.jsp" %>
	
	<c:set var="parma_businessId" value="${designNoti.id}" />
	<c:set var="parma_itemLength" value="${fn:length(designNoti.designNotiItems)}" />
	<%@ include file="/component/opinionCommit.jsp" %>

	
</body>
</html>
