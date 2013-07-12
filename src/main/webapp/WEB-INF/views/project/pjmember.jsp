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
			$(".pltdiv").formatName(); 
			var oldAction=actionFrom.attr("action");
			actionFrom.attr("action",oldAction+"/save").submit();
		});
		$(".chk_plt").click(function(){
			if($(this).is(':checked')){
				$(this).next().val(true);
				$(this).parent().find("select").show();
			}else{
				$(this).next().val(false);
				$(this).parent().find("select").hide();
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
	<c:forEach var="pjmemberDto" items="${pjmemberDtos}">
		<div class="pltdiv" style="height:20px;margin-top: 5px;">
			<input type="checkbox" class="chk_plt" <c:if test="${pjmemberDto.checked}">checked="checked"</c:if> />
			<input type="hidden" name="plt_checked" value="${pjmemberDto.checked}">
			<input type="hidden" name="plt_user.key" value="${pjmemberDto.user.key}">
			${pjmemberDto.user.name}
			<select name="plt_pjrole.key"  <c:if test="${not pjmemberDto.checked}">style="display:none;"</c:if>>
				<c:forEach var="pjrole" items="${pjroles}">
					<option value="${pjrole.key}" <c:if test="${pjmemberDto.pjrole.key==pjrole.key}">selected="true"</c:if> >${pjrole.name}</option>
				</c:forEach>
			</select>
		</div>
	</c:forEach>
	<br/>
	<br/>
	<input type="button" value="保存"  id="btn_save">	
	<input type="button" value="返回"  id="btn_back">
	</form>
	
</body>
</html>
