<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

	
	<style type="text/css">
		.high-color{
			background-color: #C8C8C8;
		}
	</style>
	<script type="text/javascript">
		$(function(){
			
			$("#btn_selContract").click(function(){
				$( "#selContract" ).dialog( "open" );
			});
			$( "#selContract" ).dialog({
				autoOpen: false,
				position: ["center", 100],
				width: 600,
				buttons: {
					'清空': function() {
						$("#spanContract").html("");
						$("#spanContract").next().val("");
						$("#supplier_name").html("");
						$(this).dialog( "close" );
					}
				}
			});
			$(".chkreq").click(function(){
				var param=jQuery.parseJSON($(this).attr("param"));
				$("#spanContract").html(param.no);
				$("#spanContract").next().val(param.id);
				$("#supplier_name").html(param.supplierName);
				$( "#selContract" ).dialog("close");
			})
			$("#selContract tbody tr").mouseover(function() {
			  	$(this).addClass("high-color");
			  }).mouseout(function(){
			    $(this).removeClass("high-color");
			  });
		})
	</script>
	
	
	<div id="selContract" title="施工服务合同" >
		<table border="1">
			<thead>
				<tr>
					<td>编号</td>
					<td>名称</td>
					<td>供应商</td>
					<td>状态</td>
					<td>专业分类</td>
					<td>操作</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="contract" items="${contractList}">
				<tr>
					<td>${contract.no}</td>
					<td>${contract.name}</td>
					<td>${contract.supplier.name}</td>
					<td>${contract.state.name}</td>
					<td>${contract.specialty.name}</td>
					<td><input class="chkreq" type="button" value="选中" param='{"id":"${contract.id}","no":"${contract.no}","supplierName":"${contract.supplier.name}"}'> </td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>