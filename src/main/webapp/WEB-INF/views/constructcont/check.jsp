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
	<h2>施工联系单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<%@ include file="part/constructcont_view.jsp" %>
	<br>
	
	<form action="${ctx}/constructcont/check" method="post">
		<input type="hidden" name="taskId" value="${task.id}"/>
		<input type="hidden" name="businessId" value="${constructCont.id}"/>
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
		<br>
		<input type="button" value="提交流程"  id="btn_commit">
		<input type="button" value="返回"  id="btn_back">
	</form>

	
</body>
</html>
