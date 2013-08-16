<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<script type="text/javascript">
		$(function(){
			
			var itemdom = $("<tr>");
	   		
	   		$("<td>").append($("<input type='checkbox' class='inp_chk' name='reqItemIds'  />"))
	   		  .appendTo(itemdom);
	   		
	   		$("<td>").appendTo(itemdom);
	   		
	   		$("<td>").appendTo(itemdom);
	   		
	   		$("<td>").appendTo(itemdom);
	   		
	   		$("<td>").appendTo(itemdom);
	   		
	   		$("<td>").appendTo(itemdom);
	    	
	   		$("<td>").appendTo(itemdom);
	   		
	   		$("<td>").appendTo(itemdom);
	    		 
	       	var warpLi=function(item){
	       		var newItemdom=itemdom.clone();
	       		var inpChk=newItemdom.find(".inp_chk");                    
	       		inpChk.val(item.id).attr("checked",item.check);
	       		if(item.readonly==true){
	       			inpChk.attr("disabled","true");
	       		}
	       		
	       		newItemdom.find("td:eq(1)").html(item.sn);
	       		newItemdom.find("td:eq(2)").html(item.metername);
	       		newItemdom.find("td:eq(3)").html(item.spec);
	       		newItemdom.find("td:eq(4)").html(item.unit);
	       		newItemdom.find("td:eq(5)").html(item.numb);
	       		newItemdom.find("td:eq(6)").html(item.brand);
	       		if(item.upid!=null){       			
	       			newItemdom.find("td:eq(7)").html(item.uptypeName+"["+item.upno+"]执行采购");
	       		}
	       		$("#purchaseReqItem tbody").append(newItemdom);
	       	}
			
			function fetchReqItem(purchaseKeyId){
	       		if(purchaseKeyId==''){
	       			$("#purchaseReqItem tbody").empty();
	       			return;
	       		}
	       		var certid="-1";
	       		<c:if test="${not empty certid}">certid="${certid}";</c:if>
	       		$.getJSON('${ctx}/${projectId}/purchasereq/'+purchaseKeyId+'/items/${uptype}/'+certid,function(itemlist){
	       			$("#purchaseReqItem tbody").empty();
	       			for(i=0;i<itemlist.length;i++){
	       				warpLi(itemlist[i]);
	       			}
	       		})
	       	}
			//初次加载
			fetchReqItem("${purchaseReq.purchaseKey.id}");
			
			$("#btn_selreq").click(function(){
				$( "#selReq" ).dialog( "open" );
			});
			$( "#selReq" ).dialog({
				autoOpen: false,
				position: ["center", 100],
				width: 600,
				buttons: {
					'清空': function() {
						$("#spanReq").html("");
						$("#spanReq").next().val("");
						fetchReqItem('');
						$(this).dialog( "close" );
					}
				}
			});
			$(".chkreq").click(function(){
				var param=jQuery.parseJSON($(this).attr("param"));
				$("#spanReq").html(param.no);
				$("#spanReq").next().val(param.id);
				fetchReqItem(param.id);
				$( "#selReq" ).dialog("close");
			})
		})
	</script>
	
	<div id="selReq" title="采购申请单" >
		<table class="hctable deftable">
			<thead>
				<tr>
					<th>序号</th>
					<th>计划进场时间</th>
					<th>状态</th>
					<th>制单人</th>
					<th>制单日期</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="purchaseReq" items="${purchaseReqList}">
				<tr>
					<td>${purchaseReq.no}</td>
					<td>${purchaseReq.plandate}</td>
					<td>${purchaseReq.state.name}</td>
					<td>${purchaseReq.creater.name}</td>
					<td><fmt:formatDate value="${purchaseReq.createdate}" pattern="yyyy-MM-dd HH:mm"/></td>
					<td><input class="chkreq" type="button" value="选中" param='{"id":"${purchaseReq.purchaseKey.id}","no":"${purchaseReq.no}"}'> </td>
				</tr>
			</c:forEach>
			</tbody>
		</table>
	</div>