<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	 <script type="text/javascript">
    $(function(){
    	
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
		 
		var computerAmount=function(tr){
			var price=$(tr).find("input[name='constructContItems_price']").val();
			var numb=$(tr).find("input[name='constructContItems_numb']").val();
			var tdAmt=$(tr).find("input[name='constructContItems_amount']");
			if(IsFloat(price,"+")&&IsFloat(numb,"+")){
				var result=FloatMul(price,numb);
				result=hold(result,2);
				tdAmt.val(result);
			}else{
				tdAmt.val("");
			}
		}
		
		var computerTolamount=function(){
			var tr=$("#tabitem tbody tr");
			var tolamount=0;
			tr.each(function(){
				var amt=$(this).find("input[name='constructContItems_amount']").val();
				amt=(IsFloat(amt,"+")?amt:0);
				tolamount=FloatAdd(amt,tolamount);
			})
			tolamount=hold(tolamount,2);
			$("#sp_tolsum").html(tolamount);
			$("input[name=tolsum]").val(tolamount);
		}
		 
		 var priceBlur=function(){
			if(IsFloat($(this).val(),"+"))	{
				var point=hold($(this).val(),2);
				$(this).val(point);
			}else{
				$(this).val("");
			}
			var tr = $(this).parents("tr:eq(0)");
			computerAmount(tr);
			computerTolamount();
		}
		 
		var numbBlur=function(){
			if(IsFloat($(this).val(),"+"))	{
				var point=hold($(this).val(),0);
				$(this).val(point);
			}else{
				$(this).val("");
			}
			var tr = $(this).parents("tr:eq(0)");
			computerAmount(tr);
			computerTolamount();
		}
		
		
		function add() {
			rowAction($(this),function(tr){
				tr.after(cloneTR()); 
			})
		}

		function remove() {
			rowAction($(this),function(tr){
				tr.remove();
			})
			computerTolamount();
		}
		
		function cloneTR(){
			var newtr=trdom.clone();
			newtr
			  .find("input[name='constructContItems_price']").bind("blur",priceBlur).end()
			  .find("input[name='constructContItems_numb']").bind("blur",numbBlur).end()
			  .find("td:last :button")
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
		
		$("input[name='constructContItems_price']").bind("blur",priceBlur);
		$("input[name='constructContItems_numb']").bind("blur",numbBlur);
		$(".add").bind("click",add);
	 	$(".remove").bind("click",remove);
    	
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
    	
    })
	 
	 </script>

	<%@ include file="/script/fmttable.jsp" %>	
	
	
	<input type="hidden" name="id"  value="${constructCont.id}">
	<table id="tabmain" class="fmttable">
		<c:set var="parma_no" value="${constructCont.no}" />
		<c:set var="parma_busino" value="${constructCont.busino}" />
		<%@ include file="/component/noShowBill.jsp" %>
		
		<tr>
			<td>项目负责人：</td><td>${constructCont.leader.name}</td>
			<td>状态：</td><td>${constructCont.state.name}</td>
		</tr>
		
		<tr>
			<td>合同：</td> 
			<td>
				<span id="spanContract">${contract.no}</span><input type="hidden" name="constructKey.contract.id" value="${contract.id}">
				<input type="button" id="btn_selContract" value="选择" />
			</td>
		</tr>
		<tr>
			<td style="vertical-align: top">原因：</td><td colspan="3"><textarea name="reason" class="edittextarea">${constructCont.reason}</textarea></td>
		</tr>
		
		<tr>
			<td>计划完成日期：</td>
			<td>
				<input type="text" class="datePK editdate" name="plandate" value="${constructCont.plandate}">
			</td>
			<td>实际完成日期：</td>
			<td>
				${constructCont.realdate}
			</td>
		</tr>
		
		<tr>
			<td>实际执行结果：</td>
			<td>
				${constructCont.result}
			</td>
			<td>总金额：</td><td><span id="sp_tolsum">${constructCont.tolsum}</span><input type="hidden" name="tolsum" value="${constructCont.tolsum}"></td>
		</tr>
		
		<tr>
			<td>制单人：</td><td>${constructCont.creater.name}</td>
			<td>制单日期：</td><td><fmt:formatDate value="${constructCont.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>签发人：</td><td>${constructCont.signer.name}</td>
			<td>签发日期：</td><td><fmt:formatDate value="${constructCont.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>接收人：</td>
			<td>
				${constructCont.receiver.name}
			</td>
			<td>接收日期：</td>
			<td>
				${constructCont.receivedate}
			</td>
		</tr>
	

	</table>

	<h3>联系项目</h3>
	<table id="tabitem" class="deftable">
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
	
			<td><input type='text' name='constructContItems_numb' value='<fmt:formatNumber value="${item.numb}" pattern="#.#"/>' style='width:50px' /></td>
	
			<td><input type='text' name='constructContItems_unit' value='${item.unit}' style='width:50px' /></td>
	
			<td><input type='text' name='constructContItems_amount' value='${item.amount}' style='width:100px' /></td>
	  
			<td><input type='button' class='add'  value='+'   /><input type='button' class='remove'  value='-'   /></td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	
	<%@ include file="selContract.jsp" %>	
