<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/x-jquery-tmpl" id="template">
<tr>
	<td>\${no}</td>
	<td>\${name}</td>
	<td><input class="chkreq {id:'\${id}',name:'\${name}'}" type="button" value="选中" > </td>
</tr>
</script>


	<script type="text/javascript">
		$(function(){
			var container=$("#dialog-container");
			var selButton=$("#selSupplier");
			selButton.click(function(){
				container.dialog( "open" );
			});
			
			container.dialog({
				autoOpen: false,
				position: ["center", 100],
				width: 400
			});
			
			var chkreqFn=function(){
				var param=$(this).metadata();
				selButton.html(param.name);
				selButton.next().val(param.id);
				container.dialog("close");
			}
			
			var clearFn=function(){
				selButton.html("所有");
				selButton.next().val("");
				container.dialog("close");
			}
			
			
			
		    function Qdata(page,no,name){
		　　　　this.page = page;
			   this.no=no;
			   this.name=name;
		　　 }
		    Qdata.prototype.types=["contra","construct"];
		    
		   function appendData(pagedata){
				var content=pagedata.content;
				var renderTd =$("#template").tmpl(content);
				renderTd.find(".chkreq").click(chkreqFn);
				renderTd.highColor().appendTo(".dialog-table tbody");
				$("#dlg_more").autoLock(pagedata.lastPage);
	 		}
			
		   var qdata=new Qdata(0,"");//当前查询状态
			
			$("#dlg_query").click(function(){
				qdata=new Qdata(0,$("#qy_no").val(),$("#qy_name").val());
				$(".dialog-table tbody").empty();
				$.getJSON('${ctx}/supplier/select.json',qdata,appendData);
			})
			
			$("#dlg_clear").click(clearFn)
			
			$("#dlg_more").click(function(){
				qdata.page++;
				$.getJSON('${ctx}/supplier/select.json',qdata,appendData);
			})
			
			$("#dlg_query").triggerHandler("click");
		})
	</script>
		
		<div id="dialog-container" title="施工承包方" style="display: none;">
			<div class="dialog-query">
				编号:<input type="text" id="qy_no"><br/>
				名称:<input type="text" id="qy_no"><input type="button" value="查询" id="dlg_query"><input type="button" value="清空" id="dlg_clear"> 
			</div>
			<table  class="hctable deftable dialog-table">
				<thead>
					<tr>
						<th>序号</th>
						<th>名称</th>
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