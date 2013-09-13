<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

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
						$(this).dialog( "close" );
					}
				}
			});
			$(".chkreq").click(function(){
				var param=jQuery.parseJSON($(this).attr("param"));
				$("#spanContract").html(param.no);
				$("#spanContract").next().val(param.id);
				$( "#selContract" ).dialog("close");
			})
		})
	</script>
	
	
	<div id="selContract" title="施工服务合同" >
		<table class="hctable deftable">
			<thead>
				<tr>
					<th>编号</th>
					<th>名称</th>
					<th>状态</th>
					<th>专业分类</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="contract" items="${contractList}">
				<tr>
					<td>${contract.no}</td>
					<td>${contract.name}</td>
					<td>${contract.state.name}</td>
					<td>${contract.specialty.name}</td>
					<td><input class="chkreq" type="button" value="选中" param='{"id":"${contract.id}","no":"${contract.no}"}'> </td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>