<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:choose>
	<c:when test="${not empty parma_no }">
	${parma_no}
	</c:when>
	<c:otherwise>
	系统自动生成
	</c:otherwise>
</c:choose>
	


