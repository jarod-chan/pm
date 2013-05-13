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
			window.open('${ctx}/task/list','_self');
			return false;
		});
		
		$("#btn_commit").click(function(){
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action"); 
			actionFrom.attr("action",oldAction+"/commit").submit();
		});
		
    })
    
    
    </script>
</head>

<body>
	<h2>施工签证单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<%@ include file="part/constructcert_view.jsp" %>

		<br>
		<form action="${ctx}/constructcert/check" method="post">
		<input type="hidden" name="taskId" value="${task.id}"/>
		<input type="hidden" name="businessId" value="${constructCert.id}"/>
		<div class="chk_div" >
			<div class="chk_result">
				审批结果：<select name="result">
					<c:forEach var="result" items="${resultList}">
						<option value="${result}">${result.name}</option>
					</c:forEach>
				</select>
			</div>	
			<div class="chk_content">
				审批意见：<textarea name="content" style="vertical-align: top;height:180px;width: 400px; "></textarea>
			</div>
		</div>
		</form>
		<br>
		<input type="button" value="提交流程"  id="btn_commit">
		<input type="button" value="返回"  id="btn_back">

</body>
</html>
