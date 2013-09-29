<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/x-jquery-tmpl" id="template">
	<tr>
		<td>\${no}</td>
		<td>\${plandate}</td>
		<td>\${state}</td>
		<td>\${creater_name}</td>
		<td>\${createdate}</td>
		<td><input class="chkreq {id:'\${id}',purchaseKeyId:'\${purchaseKeyId}',no:'\${no}'}" type="button" value="选中" /> </td>
	</tr>
</script>

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
	       			newItemdom.find("td:eq(7)").html(item.uptypeName+"["+item.upno+"]采购");
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
			
			var container=$("#dialog-container");
			
			$("#btn_selreq").click(function(){
				container.dialog( "open" );
			});
			container.dialog({
				autoOpen: false,
				position: ["center", 100],
				width: 600
			})
			
			var chkreqFn=function(){
				var param=$(this).metadata();
				$("#spanReq").html(param.no);
				$("#spanReq").next().val(param.purchaseKeyId);
				fetchReqItem(param.purchaseKeyId);
				container.dialog("close");
			}
			var clearFn=function() {
				$("#spanReq").html("");
				$("#spanReq").next().val("");
				fetchReqItem('');
				container.dialog( "close" );
			}
			
		　  function Qdata(page,no){
		　　　　this.page = page;
			   this.no=no;
		　　}
		   
		   function appendData(pagedata){
				var content=pagedata.content;
				var renderTd =$("#template").tmpl(content);
				renderTd.find(".chkreq").click(chkreqFn);
				renderTd.highColor().appendTo(".dialog-table tbody");
				$("#dlg_more").autoLock(pagedata.lastPage);
			}
		   
			
			var qdata=new Qdata(0,"");//当前查询状态
			
			$("#dlg_query").click(function(){
				qdata=new Qdata(0,$("#qy_no").val());
				$(".dialog-table tbody").empty();
				$.getJSON('${ctx}/${projectId}/purchasereq/select.json',qdata,appendData);
			})
			
			$("#dlg_clear").click(clearFn)
			
			$("#dlg_more").click(function(){
				qdata.page++;
				$.getJSON('${ctx}/${projectId}/purchasereq/select.json',qdata,appendData);
			})
			
			$("#dlg_query").triggerHandler("click");
			
		})
	</script>
	
	<div id="dialog-container" title="采购申请单" >
		<div class="dialog-query">
			编号:<input type="text" id="qy_no"><input type="button" value="查询" id="dlg_query"><input type="button" value="清空" id="dlg_clear"> 
		</div>
		<table class="hctable deftable dialog-table">
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
			</tbody>
		</table>
		<div class="dialog-more">
			<input type="button" value="更多" id="dlg_more" disabled="disabled">
		</div>
	</div>