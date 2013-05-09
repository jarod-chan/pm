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
    	
    	$("#btn_new").click(function(){
			window.open('${ctx}/supplier/${supptype}/-1/edit','_self');
			return false;
		})
    	
    	$('.btn_edit').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/supplier/${supptype}/'+param.id+'/edit','_self');
    		return false;
    	})
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/supplier/${supptype}/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'supplierId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	})
    	
    	$("#headdiv").css("width",$("#tblmain").css("width"));
    });
    </script>
</head>

<body>
	<h2>${supptype.name}</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<div id="headdiv" style="text-align: right;">	
	  <input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain" border="1">
		<tr>
			<td>编号</td><td>名称</td>
			<td>信用等级</td>
			<td>营业执照</td>
			<td>企业代码</td>
			<td>公司地址</td>
			<td>法人代表</td>
			<td>公司电话</td>
			<td>联系人</td>
			<td>联系电话</td>
			<td>资金帐户</td>
			<td>操作</td>
		</tr>
		<c:forEach var="supplier" items="${supplierList}">
			<tr>
				<td>${supplier.no}</td><td>${supplier.name}</td>
				<td>${supplier.creditRank}</td>
				<td>${supplier.busiLicense}</td>
				<td>${supplier.busiCode}</td>
				<td>${supplier.address}</td>
				<td>${supplier.lealPerson}</td>
				<td>${supplier.compPhone}</td>
				<td>${supplier.contact}</td>
				<td>${supplier.contPhone}</td>
				<td>${supplier.account}</td>
				<td>
				<input type="button" param='{"id":"${supplier.id}"}' value="编辑"  class="btn_edit">
				<input type="button" param='{"id":"${supplier.id}"}' value="删除"  class="btn_delete">
				</td>
			</tr>
		</c:forEach>
		
	</table>
	
</body>
</html>
