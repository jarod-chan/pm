<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	


</head>

<body>
	<h2>工程签证单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<%@ include file="part/constructcert_view.jsp" %>

	<c:set var="parma_businessId" value="${constructCert.id}" />
	<c:set var="parma_itemLength" value="${fn:length(constructCert.constructCertItems)}" />
	<%@ include file="/component/opinionCommit.jsp" %>
	
</body>
</html>
