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


    <script type="text/javascript">
    $(function() {

		$("#btn_back").click(function(){
			window.open('${ctx}/purchasereq/list','_self');
			return false;
		})

    })
    </script>
</head>

<body>
	<h2>采购申请单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<%@ include file="part/purchasereq_view.jsp" %>	

	<br>
	<input type="button" value="返回"  id="btn_back">

</body>
</html>
