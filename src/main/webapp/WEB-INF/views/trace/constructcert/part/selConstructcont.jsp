<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/x-jquery-tmpl" id="template">
<tr>
	<td>\${no}</td>
	<td>\${contract_name}</td>
	<td>\${supplier_name}</td>
	<td>\${contract_specialty}</td>
	<td>\${tolsum}</td>
	<td>\${creater_name}</td>
	<td>\${createdate}</td>
	<td><input class="chkreq {id:'\${id}',constructKeyId:'\${constructKey_id}',no:'\${no}',supplierName:'\${supplier_name}'}" type="button" value="选中" > </td>
</tr>
</script>


	<script type="text/javascript">
		$(function(){
			var container=$("#dialog-container");
			$("#btn_selConstructCont").click(function(){
				container.dialog( "open" );
			});
			container.dialog({
				autoOpen: false,
				position: ["center", 100],
				width: 800
			});
			
			
		    function Qdata(page,no){
		　　　　this.page = page;
			   this.no=no;
		　　 }
		    Qdata.prototype.constructcert_id="${constructCert.id}";
		    
		    var chkreqFn=function(){
				var param=$(this).metadata();
				$("#spanConstructCont").html(param.no);
				$("#spanConstructCont").next().val(param.constructKeyId);
				$("#constructContId").val(param.id);
				$("#supplier_name").html(param.supplierName);
				container.dialog("close");
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
				$.getJSON('${ctx}/${projectId}/constructcont/select.json',qdata,appendData);
			})
			
			$("#dlg_clear").click(function(){
				$("#spanConstructCont").html("");
				$("#spanConstructCont").next().val("");
				$("#constructContId").val("");
				$("#supplier_name").html("");
				container.dialog("close");
			})
			
			$("#dlg_more").click(function(){
				qdata.page++;
				$.getJSON('${ctx}/${projectId}/constructcont/select.json',qdata,appendData);
			})
			
			$("#dlg_query").triggerHandler("click");
		})
	</script>
		
		<div id="dialog-container" title="施工联系单" style="display: none;">
			<div class="dialog-query">
				编号:<input type="text" id="qy_no"><input type="button" value="查询" id="dlg_query"><input type="button" value="清空" id="dlg_clear"> 
			</div>
			<table  class="hctable deftable dialog-table">
				<thead>
					<tr>
						<th>序号</th>
						<th>合同</th>
						<th>施工承包方</th>
						<th>专业分类</th>
						<th>总金额</th>
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