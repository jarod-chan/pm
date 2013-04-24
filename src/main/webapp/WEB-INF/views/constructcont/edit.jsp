<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>
	<%@ include file="/common/jqui.jsp" %>		


    <script type="text/javascript">
    $(function() {
		$("#btn_save").click(function(){
			$("table tbody tr").formatName();
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action"); //return;
			actionFrom.attr("action",oldAction+"/save").submit();
		});
		
		var trdom = $("<tr>");
		$("<td>")
		  .append($("<input type='hidden' name='constructContItems_sn'   value='' />"))
		  .css("display","none")
		  .appendTo(trdom);
		
		$("<td>").appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructContItems_content' style='width:300px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructContItems_price' style='width:50px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructContItems_numb' style='width:50px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructContItems_unit' style='width:50px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructContItems_amount' style='width:100px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' class='item_plandate' name='constructContItems_plandate' style='width:100px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' class='item_realdate' name='constructContItems_realdate' style='width:100px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructContItems_result' style='width:100px' />"))
		  .appendTo(trdom);
		  
		 $("<td>")
			.append($("<input type='button' class='add'  value='+'   />"))
			.append($("<input type='button' class='remove'  value='-'   />"))
			.appendTo(trdom);
		
		function add() {
			rowAction($(this),function(tr){
				tr.after(cloneTR()); 
			})
		}

		function remove() {
			rowAction($(this),function(tr){
				tr.remove();
			})
		}
		
		function cloneTR(){
			var newtr=trdom.clone();
			newtr.find(".item_plandate").datepicker().end()
				.find(".item_realdate").datepicker().end();
			newtr.find("td:last :button")
			  .filter(".add").bind("click",add).end()
			  .filter(".remove").bind("click",remove).end();
			return newtr;
		};
		
		function rowAction(obj,funcAction){
			var tr = obj.parents("tr:eq(0)");
			tbody=tr.parents("tbody:eq(0)");
			funcAction(tr);
			reIndexTable(tbody);
		}
		
		function reIndexTable(tbody){
			var index=0;
			tbody.find("tr").each(function(){
				index++;
				$(this).find("td").eq(0).find("[name='constructContItems_sn']").val(index);			
				$(this).find("td").eq(1).html(index);
			});
		}
		
		
		$(".addLast").bind("click",function(){
			var tbody=$(this).parents("thead").next();
			tbody.append(cloneTR()); 
			reIndexTable(tbody);
		});
		
    })
    
    $(function(){
    	$("select:eq(0)").change(function(){
    		var opt=$(this).find("option:selected").text();
    		var idx=opt.lastIndexOf("-");
    		$("#supplier_name").html(opt.substring(idx+1));
    	})
    	$("select:eq(0)").triggerHandler("change");
    	
    	$(".addLast").triggerHandler("click");
    	
    	
    	$('#tabmain tr').find('td:eq(0)').css("text-align","right");
    })
    
    </script>
</head>

<body>
	<h2>施工联系单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/constructcont" method="post">
	<table id="tabmain">
		<tr>
			<td>编号：</td><td>${constructCont.no}<input type="hidden" name="no" value="${constructCont.no}"/></td>
		</tr>
		<tr>
			<td>项目：</td><td><input type="hidden" name="constructKey.project.id" value="${project.id}"> ${project.name}</td>
		</tr>
		<tr>
			<td>项目负责人：</td><td><input type="hidden" name="leader.key" value="${project.user.key}">${project.user.name}</td>
		</tr>
		<tr>
			<td>合同：</td>
			<td>
				<select name="constructKey.contract.id">
					<c:forEach var="contract" items="${contractList}">
						<option value="${contract.id}">${contract.name}-${contract.supplier.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>施工承包方：</td><td><span id="supplier_name"></span></td>
		</tr>
		<tr>
			<td style="vertical-align: top">原因：</td><td><textarea name="reason" rows="6" cols="30" style="vertical-align: top"></textarea></td>
		</tr>
		<tr>
			<td>状态：</td><td>${constructCont.state.name}</td>
		</tr>
		<tr>
			<td>制单人：</td><td>${constructCont.creater.name}</td>
		</tr>
		<tr>
			<td>制单日期：</td><td><fmt:formatDate value="${constructCont.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>签发人：</td><td></td>
		</tr>
		<tr>
			<td>签发日期：</td><td></td>
		</tr>
	</table>
	<br>
	<br>
		联系项目
		<table border="1">
		<thead>
			<tr>
				<th>序号</th><th>内容</th><th>暂定单价</th><th>暂定数量</th><th>单位</th><th>暂定结算价</th><th>计划完成日期</th><th>实际完成日期</th><th>实际执行结果</th><th>操作<input type="button" class="addLast" value="+"  /></th>
			</tr>
		</thead>
		<tbody>
		</tbody>
		</table>
		<br>
		<input type="button" value="保存"  id="btn_save">
		
	</form>
	
</body>
</html>
