<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/x-jquery-tmpl" id="template">
<tr>
	<td>\${key}</td>
	<td>\${name}</td>
	<td><input class="chkreq {key:'\${key}',name:'\${name}'}" type="button" value="选中" > </td>
</tr>
</script>


	<script type="text/javascript">
		var selUserFun;
		
		$(function(){
			var container=$("#dialog-user");
			var selButton;
			
			selUserFun=function(event){
				container.dialog("open");
				selButton=$(event.target);
				$(".selectTd").removeClass("selectTd");
				selButton.parents("tr").find("td").eq(1).addClass("selectTd");
			}
			
			$(".btn_selUser").click(selUserFun);
		
			container.dialog({
				autoOpen: false,
				position: ["center", 100],
				width: 400,
				close:function(event, ui){
					selButton.parents("tr").find("td").eq(1).removeClass("selectTd");
				}
			});
			
			var chkreqFn=function(){
				var param=$(this).metadata();
				selButton.html(param.name);
				selButton.next().val(param.key);
				container.dialog("close");
			}
			
			var clearFn=function(){
				selButton.html("选择");
				selButton.next().val("");
				container.dialog("close");
			}
			
			
			
		    function Qdata(page,key,name){
		　　　　this.page = page;
			   this.key=key;
			   this.name=name;
		　　 }
		    Qdata.prototype.enabled="y";
		    
		   function appendData(pagedata){
				var content=pagedata.content;
				var renderTd =$("#template").tmpl(content);
				renderTd.find(".chkreq").click(chkreqFn);
				renderTd.highColor().appendTo(container.find(".dialog-table tbody"));
				$("#dlg_more").autoLock(pagedata.lastPage);
	 		}
			
		   var qdata=new Qdata(0,"");//当前查询状态
			
			$("#dlg_query").click(function(){
				qdata=new Qdata(0,$("#qy_key").val(),$("#qy_name").val());
				$(".dialog-table tbody").empty();
				$.getJSON('${ctx}/user/select.json',qdata,appendData);
			})
			
			$("#dlg_clear").click(clearFn)
			
			$("#dlg_more").click(function(){
				qdata.page++;
				$.getJSON('${ctx}/user/select.json',qdata,appendData);
			})
			
			$("#dlg_query").triggerHandler("click");
		})
	</script>
		
		<div id="dialog-user" title="系统用户" style="display: none;">
			<div class="dialog-query">
				用户名:<input type="text" id="qy_key"><br/>
				真实姓名:<input type="text" id="qy_name"><input type="button" value="查询" id="dlg_query"><input type="button" value="清空" id="dlg_clear"> 
			</div>
			<table  class="hctable deftable dialog-table">
				<thead>
					<tr>
						<th>用户名</th>
						<th>真实姓名</th>
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