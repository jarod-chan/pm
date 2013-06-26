<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	
	<%@ include file="/common/jqui.jsp" %>	


    <script type="text/javascript">
    $(function() {
		$("#btn_save").click(function(){
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action");
			actionFrom.attr("action",oldAction+"/save").submit();
		});
		
		$("#btn_back").click(function(){
			window.open('${ctx}/project/list','_self');
			return false;
		});
		
		$('#tabmain tr').find('td:eq(0)').css("text-align","right");
		
		$(".datePK").datepicker();
    });
    </script>
</head>

<body>
	<h2>项目</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<div id="headdiv" style="width: 600px;">
	<table id="tabmain">	
		
		<tr><td>
		编号：</td><td>
		${project.no}
		</td></tr>
		
		<tr><td>
		项目名称：</td><td>
		${project.name}
		</td></tr>
		
		<tr><td>
		项目负责人：</td><td>
		${project.leader.name}
		</td></tr>
		
		<tr><td>
		项目状态：</td><td>
		${project.state.name}
		</td></tr>
		
		<tr><td>
		开工日期：</td><td>
		<f:formatDate value="${project.begDate}" pattern="yyyy-MM-dd"/>
		<td></tr>
		
		<tr><td>
		人员：</td><td>
		<c:forEach var="item" items="${pjmemberList}" varStatus="status">${item.user.name}<c:if test="${!status.last}">,</c:if></c:forEach>
		<td></tr>
		
	</table>
	</div>	

	<c:set var="selectmenu" value="projectinfo" />
	<%@ include file="/component/contractorMenu.jsp" %>		
</body>
</html>
