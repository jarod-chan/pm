<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="Tue, 01 Jan 1980 1:00:00 GMT" />
<meta http-equiv="pragma" content="no-cache" />

</head>
<body>
	<table>
		<tr>
			<td>uuid</td><td>reanlname</td>
		</tr>
		<c:forEach var="map" items="${userMap}">
			<tr>
				<td>${map.uuid}</td><td>${map.realname}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
