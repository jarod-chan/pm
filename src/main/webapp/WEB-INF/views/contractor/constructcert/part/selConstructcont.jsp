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
			
			$("#btn_selConstructCont").click(function(){
				$( "#selConstructCont" ).dialog( "open" );
			});
			$( "#selConstructCont" ).dialog({
				autoOpen: false,
				position: ["center", 100],
				width: 700,
				buttons: {
					'清空': function() {
						$("#spanConstructCont").html("");
						$("#spanConstructCont").next().val("");
						$(this).dialog( "close" );
					}
				}
			});
			$(".chkreq").click(function(){
				var param=jQuery.parseJSON($(this).attr("param"));
				$("#spanConstructCont").html(param.no);
				$("#spanConstructCont").next().val(param.id);
				$( "#selConstructCont" ).dialog("close");
			})
			$("#selConstructCont tbody tr").mouseover(function() {
			  	$(this).addClass("high-color");
			  }).mouseout(function(){
			    $(this).removeClass("high-color");
			  });
		})
	</script>
		
		<div id="selConstructCont" title="施工联系单" style="display: none;">
			<table border="1">
				<thead>
					<tr>
						<td>编号</td>
						<td>合同</td>
						<td>专业分类</td>
						<td>总金额</td>
						<td>制单人</td>
						<td>制单日期</td>
						<td>操作</td>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="constructCont" items="${constructContList}">
					<tr>
						<td>${constructCont.no}</td>
						<td>${constructCont.constructKey.contract.name}</td>
						<td>${constructCont.constructKey.contract.specialty.name}</td>
						<td>${constructCont.tolsum}</td>
						<td>${constructCont.creater.name}</td>
						<td><f:formatDate value="${constructCont.createdate}" pattern="yyyy-MM-dd HH:mm"/></td>
						<td><input class="chkreq" type="button" value="选中" param='{"id":"${constructCont.constructKey.id}","no":"${constructCont.no}"}'> </td>
					</tr>
				</c:forEach>
				</tbody>
			</table>
		</div>