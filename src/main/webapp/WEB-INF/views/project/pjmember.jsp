<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	


    <script type="text/javascript">
    $(function() {
		$("#btn_save").click(function(){
			var actionFrom=$("form");
			$("span").formatName(); 
			var oldAction=actionFrom.attr("action");
			actionFrom.attr("action",oldAction+"/save").submit();
		});
		$(".chk_plt").click(function(){
			if($(this).is(':checked')){
				$(this).next().val(true);
			}else{
				$(this).next().val(false);
			}
		});
		
		$("#btn_back").click(function(){
			window.open('${ctx}/project/list','_self');
			return false;
		});
    });
    </script>
</head>

<body>
	<h2>项目成员</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/project/${project.id}/pjmember" method="post">
	<c:forEach var="pjmemberDto" items="${pjmemberPage.plt}">
	<span>
		<input type="checkbox" class="chk_plt" <c:if test="${pjmemberDto.checked}">checked="checked"</c:if> />
		<input type="hidden" name="plt_checked" value="${pjmemberDto.checked}">
		<input type="hidden" name="plt_pjmember.user.key" value="${pjmemberDto.pjmember.user.key}">
		<input type="hidden" name="plt_pjmember.project.id" value="${pjmemberDto.pjmember.project.id}">
		${pjmemberDto.pjmember.user.name}
	</span>
	<br/>
	</c:forEach>
	<input type="button" value="保存"  id="btn_save">	
	<input type="button" value="返回"  id="btn_back">
	</form>
	
</body>
</html>
