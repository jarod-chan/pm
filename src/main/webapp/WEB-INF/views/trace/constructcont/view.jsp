<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>
	<%@ include file="/common/jqui.jsp" %>		


    <script type="text/javascript">
    
    $(function(){
		$("#btn_back").click(function(){
			window.open('${ctx}/${projectId}/constructcont/list','_self');
			return false;
		});
    	
    })
    
    </script>
</head>

<body>
	<h2>施工联系单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<%@ include file="part/constructcont_view.jsp" %>		

	<br>
	<c:if test="${notback!=true}">
		<input type="button" value="返回"  id="btn_back">
	</c:if>
</body>
</html>
