<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<tr>
	<td>序号：</td>
	<td>${parma_no}<c:if test="${empty parma_no}">系统自动生成</c:if></td>
	<td>业务编号：</td>
	<td>${parma_busino}</td>
</tr>


