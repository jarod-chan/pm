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
    	
    	$('#sel_project').change(function(){
    		window.open('${ctx}/first/project/'+$(this).val(),'_self');
    		return false;
    	});
    });
    </script>
</head>
<body>
	<h2>项目:${project.name}</h2>
	<%@ include file="/common/message.jsp" %>	
	<div style="width:800px;text-align: right;">
		切换项目：<select id="sel_project">
			<c:forEach var="item" items="${projectList}">
				<option value="${item.id}" <c:if test="${item.id==project.id}">selected="true"</c:if> >${item.name}</option>
			</c:forEach>
		</select>
	</div>
	<br/>
	<div>
	项目合同：	
	<c:forEach var="contract" items="${contractList}" varStatus="status">
		<span>${contract.name}</span> <c:if test="${!status.last}">,</c:if> 
	</c:forEach>
	</div>
	<br/>
	<div>
	结算对象：	
	<c:forEach var="contract" items="${contractList}" varStatus="status">
		<span>${contract.payname}</span> <c:if test="${!status.last}">,</c:if> 
	</c:forEach>
	</div>
	<br/>
	<div>
	功能操作：<br/>
	<a href="${ctx}/project/${project.id}/constructcont/list">施工签证</a><br/>
	<a href="">技术签证</a><br/>
	<a href="">采购签证</a><br/>
	<a href="">合同结算</a><br/>
	</div>

</body>
</html>
