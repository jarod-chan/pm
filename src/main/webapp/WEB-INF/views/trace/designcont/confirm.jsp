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
	
	  <script type="text/javascript">
    $(function() {
		
		$("#btn_confirm").click(function(){
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action"); 
			actionFrom.attr("action",oldAction+"/confirm").submit();
		});
		
		
		$("#btn_back").click(function(){
			window.open('${ctx}/task/list','_self');
			return false;
		})
    	
    });
    
    
    </script>	

</head>

<body>
	<h2>技术变更通知单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<%@ include file="part/designcont_view.jsp" %>
	
	<form action="${ctx}/${projectId}/designcont" method="post">
	
	<input type="hidden" name="taskId"  value="${task.id}">
	
	<input type="button" value="确认接收"  id="btn_confirm">
	<input type="button" value="返回"  id="btn_back">
	
	</form>
	
</body>
</html>
