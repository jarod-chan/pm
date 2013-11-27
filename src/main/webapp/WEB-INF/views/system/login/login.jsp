<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<shiro:notAuthenticated>
	<%@ include file="notAuthenticated.jsp" %>
</shiro:notAuthenticated>	
    	
<shiro:authenticated>
	<%@ include file="authenticated.jsp" %>
</shiro:authenticated>

