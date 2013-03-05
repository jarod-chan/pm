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
${message}
<form action="/ct/general/upload" method="post" enctype="multipart/form-data">
	<input type="text" name="name"><br>
	<input type="file" name="file"><br>
	<input type="submit" value="upload">
</form>

<form action="/ct/general/save" method="post">
	<input type="text" name="name"><br>
	<input type="submit" value="submit">
</form>

</body>
</html>
