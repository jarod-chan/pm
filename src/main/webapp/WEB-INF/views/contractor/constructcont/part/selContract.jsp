<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>



<script type="text/x-jquery-tmpl" id="template">
<tr>
	<td>\${no}</td>
	<td>\${name}</td>
	<td>\${state}</td>
	<td>\${specialty}</td>
	<td><input class="chkreq {id:'\${id}',no:'\${no}'}" type="button" value="选中" /> </td>
</tr>
</script>

	<script type="text/javascript">
		$(function(){
			
			var container=$("#dialog-container");
			
			$("#btn_selContract").click(function(){
				container.dialog( "open" );
			});
			container.dialog({
				autoOpen: false,
				position: ["center", 100],
				width: 600
			});
			
			var clearFn=function() {
				$("#spanContract").html("");
				$("#spanContract").next().val("");
				container.dialog( "close" );
			}
			var chkreqFn=function(){
				var param=$(this).metadata();
				$("#spanContract").html(param.no);
				$("#spanContract").next().val(param.id);
				container.dialog("close");
			}
			
		   function Qdata(page,no){
		　　　　this.page = page;
			   this.no=no;
		　　}
		   Qdata.prototype.contractType="construct";
		   Qdata.prototype.supplierId="${supplierId}";
		   
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
				$.getJSON('${ctx}/${projectId}/contract/select.json',qdata,appendData);
			})
			
			$("#dlg_clear").click(clearFn);
			
			$("#dlg_more").click(function(){
				qdata.page++;
				$.getJSON('${ctx}/${projectId}/contract/select.json',qdata,appendData);
			})
			
			$("#dlg_query").triggerHandler("click");
		})
	</script>
	
	
	<div id="dialog-container" title="施工服务合同" >
		<div class="dialog-query">
			编号:<input type="text" id="qy_no"><input type="button" value="查询" id="dlg_query"><input type="button" value="清空" id="dlg_clear"> 
		</div>
		<table class="hctable deftable dialog-table">
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
			</tbody>
		</table>
		<div class="dialog-more">
			<input type="button" value="更多" id="dlg_more" disabled="disabled">
		</div>
	</div>