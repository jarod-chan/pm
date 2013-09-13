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
    	
    	var validator=$("form").validate({
    		rules: {
    			"receiver": {
    				required: true
    			},
    			"sendnumb":{
    				required: true
    			}
    		}
    	});
    	
    	
    	var numbBlur=function(){
			if(IsFloat($(this).val(),"+"))	{
				var point=hold($(this).val(),0);
				$(this).val(point);
			}else{
				$(this).val("");
			}
		}
    	$("input[name=sendnumb]").bind("blur",numbBlur);
		
		$("#btn_confirm").click(function(){
			if(!validator.form()){return;}
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action"); 
			actionFrom.attr("action",oldAction+"/sendlog").submit();
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
	
	<input type="hidden" name="id"  value="${designCont.id}">
	<input type="hidden" name="taskId"  value="${task.id}">
	
	<h3>文件发放</h3>
	
	<table class="fmttable">
		<tr>
			<td>文件接收人：</td>
			<td colspan="3"><input type="text" name="receiver" ></td>
		</tr>
		
		<tr>
			<td>发放份数：</td>
			<td colspan="3"><input type="text" name="sendnumb" ></td>
		</tr>
	</table>
	
	<input type="button" value="确认发放"  id="btn_confirm">
	<input type="button" value="返回"  id="btn_back">
	
	</form>
	
</body>
</html>
