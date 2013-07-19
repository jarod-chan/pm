<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty opinions}">			
<div class="chk_opinions" style="margin-top: 5px;">
	流程历史审批意见
	<div>
		<c:forEach var="opinion" items="${opinions}" varStatus="status">
			${status.count}.<span style="font-weight: bold">${opinion.userName}</span>执行任务<span style="font-weight: bold">${opinion.taskName}</span>【${opinion.result.name}】<c:if test="${not empty opinion.content}">:${opinion.content}</c:if><br/>
		</c:forEach>
	</div>
</div>
</c:if>





