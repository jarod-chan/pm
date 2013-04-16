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
    	$("#btn_back").click(function(){
    		window.open('${ctx}/project/${constructCert.constructKey.project.id}/constructkey/list','_self');
			return false;
		});	
	});
		
	
    
    
    
    </script>
</head>

<body>
	<h2>施工签证单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/project/${constructKey.project.id}/constructkey/${constructKey.id}/cert" method="post">
		编号：
		${constructCert.no }
		<br>
		项目：${constructCert.constructKey.project.name }
		<br>
		结算对象：${constructCert.constructKey.contract.payname }
		<br>
		用户：
		${constructCert.creater.name}
		<br>
		
		<br>
		签证项目
		<table border="1">
		<thead>
			<tr>
				<th>序号</th><th>内容</th><th>费用</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="constructCertItem" items="${constructCert.constructCertItems}">
				<tr>
					<td>${constructCertItem.sn}</td>
					<td>${constructCertItem.content}</td>
					<td>${constructCertItem.cost}</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
		<br>
		<input type="button" value="返回"  id="btn_back">
		
	</form>
	
</body>
</html>
