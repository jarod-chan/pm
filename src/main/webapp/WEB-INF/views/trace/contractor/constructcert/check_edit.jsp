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
	<%@ include file="/common/jqui.jsp" %>
	<%@ include file="/common/jqui2.jsp" %>		
	
	<style type="text/css">
		.chk_div{
			border: 2px dashed   #000000;
			padding: 10px;
			width: 500px;
			margin-top: 10px;
		}
	</style>		

    <script type="text/javascript">
    $(function() {
    	var validator=$("form").validate({
    		rules: {
    			"constructKey.id": {
    				required: true
    			}
    		}
    	});
    	$("#btn_save").click(function(){
    		if(!validator.form()){return;}
			$("#tabitem tbody tr").formatName();
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action"); 
			$("input[name=afteraction]").val("save");
			actionFrom.attr("action",oldAction+"/save").submit();
		});
		
		$("#btn_commit").click(function(){
			if(!validator.form()){return;}
			$("#tabitem tbody tr").formatName();
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action"); 
			$("input[name=afteraction]").val("commit");
			actionFrom.attr("action",oldAction+"/save").submit();
		});
		
		$("#btn_back").click(function(){
			window.open('${ctx}/task/list','_self');
			return false;
		})
    	
    });
    
    
    </script>
</head>

<body>
	<h2>工程签证单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/contractor/${projectId}/constructcert/checkedit" method="post">
	<input type="hidden" name="afteraction"  >
	
	<%@ include file="part/constructcert_edit.jsp" %>
	
	<br>
	<h3>领导意见</h3>
	<c:forEach var="opinion" items="${opinionList}">
		<div class="chk_div">
			<div class="chk_result">
				${opinion.userName}：${opinion.result.name}
			</div>
			<c:if test="${not empty opinion.content}">
				<div class="chk_content">
						审批意见：${opinion.content}
				</div>	
			</c:if>	
		</div>
	</c:forEach>
		
	<br>
	<input type="hidden" name="taskId"  value="${taskId}">
	
	<input type="button" value="保存"  id="btn_save">
	<input type="button" value="提交流程"  id="btn_commit">
	<input type="button" value="返回"  id="btn_back">
	</form>
	
</body>
</html>
