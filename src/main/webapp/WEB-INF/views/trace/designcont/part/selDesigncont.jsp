<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

	

	<script type="text/javascript">
		$(function(){
			
			$("#btn_selnoti").click(function(){
				$( "#selDesignNoti" ).dialog( "open" );
			});
			$( "#selDesignNoti" ).dialog({
				autoOpen: false,
				position: ["center", 100],
				width: 600,
				buttons: {
					'清空': function() {
						$("#spanNoti").html("");
						$("#spanNoti").next().val("");
						$(this).dialog( "close" );
					}
				}
			});
			$("#selDesignNoti .chkreq").click(function(){
				var param=$(this).metadata();
				$("#spanNoti").html(param.no);
				$("#spanNoti").next().val(param.id);
				$( "#selDesignNoti" ).dialog("close");
			})

		})
	</script>
	
	
	<div id="selDesignNoti" title="问题通知单" >
		<table class="hctable deftable">
		<thead>
			<tr>
				<th>序号</th>
				<th>业务编号</th>
				<th>状态</th>
				<th>制单人</th>
				<th>制单日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="designNoti" items="${designNotiList}">
			<tr>
				<td>${designNoti.no}</td>
				<td>${designNoti.busino}</td>
				<td><span class="state state-${designNoti.state}" >${designNoti.state.name}</span></td>
				<td>${designNoti.creater.name}</td>
				<td><f:formatDate value="${designNoti.createdate}" pattern="yyyy-MM-dd"/></td>
				<td><input type="button" value="选中" class="chkreq {id:'${designNoti.id}',no:'${designNoti.no}'}"> </td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</div>