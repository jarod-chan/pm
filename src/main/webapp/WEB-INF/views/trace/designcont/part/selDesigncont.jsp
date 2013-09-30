<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/x-jquery-tmpl" id="designnoti">
<tr>
	<td>\${no}</td>
	<td>\${busino}</td>
	<td><span class="state state-\${state}" >\${state_name}</span></td>
	<td>\${creater_name}</td>
	<td>\${createdate}</td>
	<td><input type="button" value="选中" class="chkreq {id:'\${id}',no:'\${no}'}"> </td>
</tr>
</script>	
	

	<script type="text/javascript">
		$(function(){
			var container=$("#selDesignNoti");
			container.css("padding","0px");
			$("#btn_selnoti").click(function(){
				container.dialog("open");
			});
			$( "#selDesignNoti" ).dialog({
				autoOpen: false,
				position: ["center", 100],
				width: 600
			});
			
			var clearFn=function() {
				$("#spanNoti").html("");
				$("#spanNoti").next().val("");
				container.dialog( "close" );
			};
			var chkreqFn=function(){
				var param=$(this).metadata();
				$("#spanNoti").html(param.no);
				$("#spanNoti").next().val(param.id);
				container.dialog("close");
			}
			
		   function Qdata(page,no){
		　　　　this.page = page;
			   this.no=no;
		　　}
		   
		   function appendData(pagedata){
				var content=pagedata.content;
				var renderTd =$("#designnoti").tmpl(content);
				renderTd.find(".chkreq").click(chkreqFn);
				renderTd.highColor().appendTo(container.find(".dialog-table tbody"));
				$("#dag_more").autoLock(pagedata.lastPage);
			}
			
			var qdata=new Qdata(0,"");//当前查询状态
			
			$("#dag_query").click(function(){
				qdata=new Qdata(0,$("#qa_no").val());
				container.find(".dialog-table tbody").empty();
				$.getJSON('${ctx}/${projectId}/designnoti/select.json',qdata,appendData);
			})
			
			$("#dag_clear").click(clearFn)
			
			$("#dag_more").click(function(){
				qdata.page++;
				$.getJSON('${ctx}/${projectId}/designnoti/select.json',qdata,appendData);
			})
			
			$("#dag_query").triggerHandler("click");
		})
	</script>
	
	
	<div id="selDesignNoti" title="问题通知单" >
		<div class="dialog-query">
			编号:<input type="text" id="qa_no"><input type="button" value="查询" id="dag_query"><input type="button" value="清空" id="dag_clear"> 
		</div>
		<table class="hctable deftable dialog-table">
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
			</tbody>
		</table>
		<div class="dialog-more">
			<input type="button" value="更多" id="dag_more" disabled="disabled">
		</div>
	</div>