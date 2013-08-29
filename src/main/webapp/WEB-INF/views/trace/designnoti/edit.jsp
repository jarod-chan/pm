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


    <script type="text/javascript">
    $(function() {
    	
    	var formatFileSn=function(){
    		$("#tabitem tbody tr").each(function(idx){
    			$(this).find(".sp_itemfile").find("input[name='itemfileSn']").val(idx+1);
    		})
    	}
    	
		$("#btn_save").click(function(){
			$("#tabitem tbody tr").formatName();
			formatFileSn(); 
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action"); 
			$("input[name=afteraction]").val("save");
			actionFrom.attr("action",oldAction+"/saveEdit").submit();
		});
		
		$("#btn_commit").click(function(){
			$("#tabitem tbody tr").formatName();
			formatFileSn();
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action"); 
			$("input[name=afteraction]").val("commit");
			actionFrom.attr("action",oldAction+"/saveEdit").submit();
		});
		
		$("#btn_back").click(function(){
			window.open('${ctx}/${projectId}/designnoti/list','_self');
			return false;
		});
		
    })
    </script>
</head>



<body>
	<h2>问题通知单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/${projectId}/designnoti" method="post">
	<input type="hidden" name="afteraction"  >
	
	<%@ include file="part/designnoti_edit.jsp" %>	
	
	<br>
	<input type="button" value="保存"  id="btn_save">
	<input type="button" value="提交流程"  id="btn_commit">
	<input type="button" value="返回"  id="btn_back">
		
	</form>
	
</body>
</html>
