<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

${parma_no}<c:if test="${empty parma_no}">系统自动生成</c:if><c:if test="${not empty parma_busino}">&nbsp;&nbsp;业务编号:${parma_busino}</c:if>





