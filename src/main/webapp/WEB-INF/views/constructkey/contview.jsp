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
    		window.open('${ctx}/project/${constructCont.constructKey.project.id}/constructkey/list','_self');
			return false;
		});
		
	
    });
    
    
    
    
    
    </script>
</head>

<body>
	<h2>施工联系单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/project/${constructKey.project.id}/constructkey/${constructKey.id}/cont" method="post">
		编号：
		${constructCont.no}
		<br>
		项目：${constructCont.constructKey.project.name }
		<br>
		结算对象：${constructCont.constructKey.contract.payname }
		<br>
		原因：
		${constructCont.reason}
		<br>
		用户：
		${constructCont.creater.name}
		<br>
		
		
		<br>
		联系项目
		<table border="1">
		<thead>
			<tr>
				<th>序号</th><th>内容</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="constructContItem" items="${constructCont.constructContItems}">
				<tr>
					<td>${constructContItem.sn}</td>
					<td>${constructContItem.content}</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>
		<br>
		<input type="button" value="返回"  id="btn_back">
		
	</form>
	
</body>
</html>
