<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	 <script type="text/javascript">
    $(function(){
    	
    	var trdom = $("<tr>");
		$("<td>")
		  .append($("<input type='hidden' name='purchaseReqItems_sn'   value='' />"))
		  .append($("<input type='hidden' name='purchaseReqItemsId'   value='-1' />"))
		  .css("display","none")
		  .appendTo(trdom);
		
		$("<td>").appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='purchaseReqItems_metername' style='width:150px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='purchaseReqItems_spec' style='width:300px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='purchaseReqItems_unit' style='width:50px' />"))
		  .appendTo(trdom);

		$("<td>").append($("<input type='text' name='purchaseReqItems_numb' style='width:50px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='purchaseReqItems_brand' style='width:150px' />"))
		  .appendTo(trdom);
		
		  
		 $("<td>")
			.append($("<input type='button' class='add'  value='+'   />"))
			.append($("<input type='button' class='remove'  value='-'   />"))
			.appendTo(trdom);
	
		var numbBlur=function(){
			if(IsFloat($(this).val(),"+"))	{
				var point=hold($(this).val(),0);
				$(this).val(point);
			}else{
				$(this).val("");
			}
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
		}
		
		function cloneTR(){
			var newtr=trdom.clone();
			newtr
			  .find("input[name='purchaseReqItems_numb']").bind("blur",numbBlur).end()
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
				$(this).find("td").eq(0).find("[name='purchaseReqItems_sn']").val(index);			
				$(this).find("td").eq(1).html(index);
			});
		}
		
		
		$(".addLast").bind("click",function(){
			var tbody=$(this).parents("thead").next();
			tbody.append(cloneTR()); 
			reIndexTable(tbody);
		});
		
		$("input[name='purchaseReqItems_numb']").bind("blur",numbBlur);
		$(".add").bind("click",add);
	 	$(".remove").bind("click",remove);
    	
    	$("select:eq(0)").change(function(){
    		var opt=$(this).find("option:selected").text();
    		var idx=opt.lastIndexOf("-");
    		$("#supplier_name").html(opt.substring(idx+1));
    	})
    	$("select:eq(0)").triggerHandler("change");
    	
    	<c:if test="${fn:length(purchaseReq.purchaseReqItems)==0}">
    	$(".addLast").triggerHandler("click");
    	</c:if>
    	
    	$(".datePK").datepicker({
    		onSelect:function(dateText){
    			plandateCheck(dateText);
    		}
    	});
    	
    	var plandateCheck=function (dateText){
    		var seldate=new Date(dateText.replace(/\-/g,'/'));
			var now=new Date();
			var today=new Date(now.getFullYear(),now.getMonth(),now.getDate());
			var days = (seldate.getTime() - today.getTime()) / 86400000;    
			if(days>=${maxPurchaseReqDay}){
				$("#plandateError").hide();
			}else{
				$("#plandateError").show();
			}
    	}
    	<c:if test="${not empty purchaseReq.plandate}">
    	plandateCheck('${purchaseReq.plandate}');
    	</c:if>
    	
    })
	 </script>
	 
	 <%@ include file="/script/fmttable.jsp" %>

	<input type="hidden" name="id"  value="${purchaseReq.id}">
	<input type="hidden" name="purchaseKey.id"  value="${purchaseReq.purchaseKey.id}">
	<table id="tabmain" class="fmttable">

		<c:set var="parma_no" value="${purchaseReq.no}" />
		<c:set var="parma_busino" value="${purchaseReq.busino}" />
		<%@ include file="/component/noShowBill.jsp" %>	
	
		<tr>
			<td>项目负责人：</td>
			<td>${purchaseReq.leader.name}</td>
			<td>状态：</td>
			<td><span class="state state-${purchaseReq.state}" >${purchaseReq.state.name}</span></td>
		</tr>
		
		<tr>
			<td style="vertical-align: top">说明：</td>
			<td colspan="3"><textarea name="descrp" class="edittextarea">${purchaseReq.descrp}</textarea></td>
		</tr>
		
		<tr>
			<td>计划进场日期：</td>
			<td>
				<input type="text" class="datePK editdate" name="plandate" value="${purchaseReq.plandate}"><span id="plandateError" style="color: red;display: none;">计划进场日期小于${maxPurchaseReqDay}天，可能无法及时完成审批！</span>
			</td>
		</tr>
		
		<tr>
			<td>制单人：</td><td>${purchaseReq.creater.name}</td>
			<td>制单日期：</td><td><fmt:formatDate value="${purchaseReq.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>签发人：</td><td>${purchaseReq.signer.name}</td>
			<td>签发日期：</td><td><fmt:formatDate value="${purchaseReq.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>接收人：</td>
			<td>
				${purchaseReq.receiver.name}
			</td>
			<td>接收日期：</td>
			<td>
				${purchaseReq.receivedate}
			</td>
		</tr>
	

	</table>

	<h3>采购项目</h3>
	<table id="tabitem"  class="deftable">
	<thead>
		<tr>
			<th>序号</th><th>材料名称</th><th>型号规格和技术指标</th><th>单位</th><th>数量</th><th>推荐品牌</th><th>操作<input type="button" class="addLast" value="+"  /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${purchaseReq.purchaseReqItems}" var="item">
		<tr>
			<td style="display: none">
				<input type='hidden' name='purchaseReqItems_sn'   value='${item.sn}' />
				<input type='hidden' name='purchaseReqItemsId'   value='${item.id}' />
			</td>
			<td>${item.sn}</td>
			<td><input type='text' name='purchaseReqItems_metername' value='${item.metername}'  style='width:150px' /></td>
	
			<td><input type='text' name='purchaseReqItems_spec' value='${item.spec}' style='width:300px' /></td>
	
		    <td><input type='text' name='purchaseReqItems_unit' value='${item.unit}' style='width:50px' /></td>
	
			<td><input type='text' name='purchaseReqItems_numb' value='<fmt:formatNumber value="${item.numb}" pattern="#.#"/>' style='width:50px' /></td>
			
			<td><input type='text' name='purchaseReqItems_brand' value='${item.brand}'  style='width:150px' /></td>
	  
			<td><input type='button' class='add'  value='+'   /><input type='button' class='remove'  value='-'   /></td>
		</tr>
		</c:forEach>
	</tbody>
	</table>