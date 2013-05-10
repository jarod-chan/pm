<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>
	<%@ include file="/common/jqui.jsp" %>
	
	<style type="text/css">
		.chk_div{
			border: 2px dashed   #000000;
			padding: 10px;
			width: 500px;
			margin-top: 10px;
		}
	</style>		


    <script type="text/javascript">
    $(function() {
		$("#btn_save").click(function(){
			$("#tabitem tbody tr").formatName();
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action"); 
			$("input[name=afteraction]").val("save");
			actionFrom.attr("action",oldAction+"/save").submit();
		});
		
		$("#btn_commit").click(function(){
			$("#tabitem tbody tr").formatName();
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action"); 
			$("input[name=afteraction]").val("commit");
			actionFrom.attr("action",oldAction+"/save").submit();
		});
		
		$("#btn_back").click(function(){
			window.open('${ctx}/task/list','_self');
			return false;
		});
		
		var trdom = $("<tr>");
		$("<td>")
		  .append($("<input type='hidden' name='constructContItems_sn'   value='' />"))
		  .append($("<input type='hidden' name='constructContItemsId'   value='-1' />"))
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
		
		$(".add").bind("click",add);
	 	$(".remove").bind("click",remove);
		
    })
    
    $(function(){
    	$("select:eq(0)").change(function(){
    		var opt=$(this).find("option:selected").text();
    		var idx=opt.lastIndexOf("-");
    		$("#supplier_name").html(opt.substring(idx+1));
    	})
    	$("select:eq(0)").triggerHandler("change");
    	
    	<c:if test="${fn:length(constructCont.constructContItems)==0}">
    	$(".addLast").triggerHandler("click");
    	</c:if>
    	
		$(".datePK").datepicker();
    	
    	$('#tabmain tr').find('td:eq(0)').css("text-align","right");
    })
    
    </script>
</head>



<body>
	<h2>施工联系单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/constructcont/checkedit" method="post">
	
	<input type="hidden" name="id"  value="${constructCont.id}">
	<input type="hidden" name="afteraction"  >
	
	<table id="tabmain">
		<tr>
			<td>编号：</td><td>${constructCont.no}<input type="hidden" name="no" value="${constructCont.no}"/></td>
		</tr>
		<tr>
			<td>项目：</td><td>${constructCont.constructKey.project.name}</td>
		</tr>
		<tr>
			<td>项目负责人：</td><td>${constructCont.leader.name}</td>
		</tr>
		<tr>
			<td>合同：</td> 
			<td>
				<select name="constructKey.contract.id">
					<c:forEach var="contract" items="${contractList}">
						<option value="${contract.id}" <c:if test="${constructCont.constructKey.contract.id==contract.id}">selected="true"</c:if> >${contract.name}-${contract.supplier.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>施工承包方：</td><td><span id="supplier_name"></span></td>
		</tr>
		<tr>
			<td style="vertical-align: top">原因：</td><td><textarea name="reason" rows="6" cols="30" style="vertical-align: top">${constructCont.reason}</textarea></td>
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
		<tr>
			<td>接收人：</td>
			<td>
				<select name="receiver.key">
					<c:forEach var="user" items="${userList}">
						<option value="${user.key}" <c:if test="${constructCont.receiver.key==user.key}">selected="true"</c:if> >${user.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>接收日期：</td>
			<td>
				${constructCont.receivedate}
			</td>
		</tr>
		<tr>
			<td>计划完成日期：</td>
			<td>
				<input type="text" class="datePK" name="plandate" value="${constructCont.plandate}">
			</td>
		</tr>
		<tr>
			<td>实际完成日期：</td>
			<td>
				${constructCont.realdate}
			</td>
		</tr>
		<tr>
			<td>实际执行结果：</td>
			<td>
				<input type="text"  name="result" value="${constructCont.result}">
			</td>
		</tr>
		
	</table>
	<br>
	<br>
		联系项目
		<table id="tabitem" border="1">
		<thead>
			<tr>
				<th>序号</th><th>内容</th><th>暂定单价</th><th>暂定数量</th><th>单位</th><th>暂定结算价</th><th>操作<input type="button" class="addLast" value="+"  /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${constructCont.constructContItems}" var="item">
			<tr>
				<td style="display: none">
					<input type='hidden' name='constructContItems_sn'   value='${item.sn}' />
					<input type='hidden' name='constructContItemsId'   value='${item.id}' />
				</td>
				<td>${item.sn}</td>
				<td><input type='text' name='constructContItems_content' value='${item.content}'  style='width:300px' /></td>
		
				<td><input type='text' name='constructContItems_price' value='${item.price}' style='width:50px' /></td>
		
				<td><input type='text' name='constructContItems_numb' value='${item.numb}' style='width:50px' /></td>
		
				<td><input type='text' name='constructContItems_unit' value='${item.unit}' style='width:50px' /></td>
		
				<td><input type='text' name='constructContItems_amount' value='${item.amount}' style='width:100px' /></td>
		
		  
				<td><input type='button' class='add'  value='+'   /><input type='button' class='remove'  value='-'   /></td>
			</tr>
			</c:forEach>
		</tbody>
		</table>
		<br>
		<h3>领导意见</h3>
		<c:forEach var="opinion" items="${opinionList}">
			<div class="chk_div">
				<div class="chk_result">
					${opinion.userName}：${opinion.result.name}
				</div>
				<c:if test="${not empty opinion.content}">
					<div class="chk_content">
							审批意见：${opinion.content}
					</div>	
				</c:if>	
			</div>
		</c:forEach>
		
		<br>
		<input type="hidden" name="taskId"  value="${taskId}">
		
		<input type="button" value="保存"  id="btn_save">
		<input type="button" value="提交流程"  id="btn_commit">
		<input type="button" value="返回"  id="btn_back">
		
	</form>
	
</body>
</html>
