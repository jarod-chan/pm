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
    	
    	$('#btn_save').click(function(){
    		$("#tblmain tbody tr").formatName();
			var actionFrom=$("form:eq(0)");
			var oldAction=actionFrom.attr("action"); 
			actionFrom.attr("action",oldAction+"/save").submit();
    	});
    	
    	$("#headdiv").css("width",$("#tblmain").css("width"));
    });
    </script>
    

</head>

<body>
	<h2>供应商用户</h2>
	<%@ include file="/common/message.jsp" %>	
	
	
	
	<br>
	<form action="${ctx}/spmember" method="post">
	<table id="tblmain" class="deftable">
		<thead>
			<tr>
				<th>系统用户</th>
				<th>供应商[内部承包人]</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="spmemberDto" items="${spmemberDtos}">
			<tr>
				<td>${spmemberDto.user.name}</td>
				<td>
					<input type="hidden" name="spmembers_user.key" value="${spmemberDto.user.key}">
					<select name="spmembers_supplier.id">
							<option value="">--</option>
							<c:forEach var="supplier" items="${constructSupplier}">
								<option value="${supplier.id}" <c:if test="${supplier.id==spmemberDto.supplier.id}">selected="true"</c:if>>${supplier.name}</option>
							</c:forEach>
					</select>
				</td>				
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br>
	<div style="text-align: right;" id="headdiv">
		<input type="button" value="保存"  id="btn_save">
	</div>
	</form>
</body>
</html>
