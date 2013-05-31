<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	 <script type="text/javascript">
    $(function(){
    	
    	var trdom = $("<tr>");
		$("<td>")
		  .append($("<input type='hidden' name='purchaseCertItems_sn'   value='' />"))
		  .append($("<input type='hidden' name='purchaseCertItemsId'   value='-1' />"))
		  .css("display","none")
		  .appendTo(trdom);
		
		$("<td>").appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='purchaseCertItems_metername' style='width:150px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='purchaseCertItems_spec' style='width:300px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='purchaseCertItems_brand' style='width:150px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='purchaseCertItems_unit' style='width:50px' />"))
		  .appendTo(trdom);

		$("<td>").append($("<input type='text' name='purchaseCertItems_numb' style='width:50px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='purchaseCertItems_price' style='width:50px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='purchaseCertItems_amount' style='width:50px' />"))
		  .appendTo(trdom);
		  
		 $("<td>")
			.append($("<input type='button' class='add'  value='+'   />"))
			.append($("<input type='button' class='remove'  value='-'   />"))
			.appendTo(trdom);
		
		 var computerAmount=function(tr){
				var price=$(tr).find("input[name='purchaseCertItems_price']").val();
				var numb=$(tr).find("input[name='purchaseCertItems_numb']").val();
				var tdAmt=$(tr).find("input[name='purchaseCertItems_amount']");
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
					var amt=$(this).find(":input[name='purchaseCertItems_amount']").val();
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
			  .find("input[name='purchaseCertItems_numb']").bind("blur",numbBlur).end()
			  .find("input[name='purchaseCertItems_price']").bind("blur",priceBlur).end()
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
				$(this).find("td").eq(0).find("[name='purchaseCertItems_sn']").val(index);			
				$(this).find("td").eq(1).html(index);
			});
		}
		
		
		$(".addLast").bind("click",function(){
			var tbody=$(this).parents("thead").next();
			tbody.append(cloneTR()); 
			reIndexTable(tbody);
		});
		
		$("input[name='purchaseCertItems_numb']").bind("blur",numbBlur);
		$(".add").bind("click",add);
	 	$(".remove").bind("click",remove);
    	
    	$("select:eq(0)").change(function(){
    		var opt=$(this).find("option:selected").text();
    		var idx=opt.lastIndexOf("-");
    		$("#supplier_name").html(opt.substring(idx+1));
    	})
    	$("select:eq(0)").triggerHandler("change");
    	
    	<c:if test="${fn:length(purchaseCert.purchaseCertItems)==0}">
    	$(".addLast").triggerHandler("click");
    	</c:if>
    	
    	$(".datePK").datepicker();
    	
    	$('#tabmain tr').find('td:eq(0)').css("text-align","right");
    })
	 
	 </script>

	<input type="hidden" name="id"  value="${purchaseCert.id}">
	<table id="tabmain">
		<tr>
			<td>编号：</td><td>${purchaseCert.no}<c:if test="${empty purchaseCert.no}">自动生成</c:if></td>
		</tr>
		<tr>
			<td>项目负责人：</td><td>${purchaseCert.leader.name}</td>
		</tr>
		<tr>
			<td>采购申请单：</td> 
			<td>
				<select name="purchaseKey.id">
					<c:forEach var="purchaseReq" items="${purchaseReqList}">
						<option value="${purchaseReq.purchaseKey.id}" <c:if test="${purchaseCert.purchaseKey.id==purchaseReq.purchaseKey.id}">selected="true"</c:if> >${purchaseReq.no}-${purchaseReq.purchaseKey.supplier.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr>
			<td>供应商：</td><td><span id="supplier_name"></span></td>
		</tr>
		<tr>
			<td style="vertical-align: top">说明：</td><td><textarea name="descrp" rows="6" cols="30" style="vertical-align: top">${purchaseCert.descrp}</textarea></td>
		</tr>
			<tr>
			<td>计划进场日期：</td>
			<td>
				<input type="text" class="datePK" name="plandate" value="${purchaseCert.plandate}">
			</td>
		</tr>
		<tr>
			<td>状态：</td><td>${purchaseCert.state.name}</td>
		</tr>
		<tr>
			<td>总金额：</td><td><span id="sp_tolsum">${purchaseCert.tolsum}</span><input type="hidden" name="tolsum" value="${purchaseCert.tolsum}"></td>
		</tr>
		<tr>
			<td>制单人：</td><td>${purchaseCert.creater.name}</td>
		</tr>
		<tr>
			<td>制单日期：</td><td><fmt:formatDate value="${purchaseCert.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>签发人：</td><td>${purchaseCert.signer.name}</td>
		</tr>
		<tr>
			<td>签发日期：</td><td><fmt:formatDate value="${purchaseCert.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>接收人：</td>
			<td>
				${purchaseCert.receiver.name}
			</td>
		</tr>
		<tr>
			<td>接收日期：</td>
			<td>
				${purchaseCert.receivedate}
			</td>
		</tr>
	

	</table>
	<br>
	<br>
	<table id="tabitem" border="1">
	<thead>
		<tr>
			<th>序号</th><th>材料名称</th><th>型号规格和技术指标</th><th>品牌</th><th>单位</th><th>数量</th><th>单价</th><th>金额</th><th>操作<input type="button" class="addLast" value="+"  /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${purchaseCert.purchaseCertItems}" var="item">
		<tr>
			<td style="display: none">
				<input type='hidden' name='purchaseCertItems_sn'   value='${item.sn}' />
				<input type='hidden' name='purchaseCertItemsId'   value='${item.id}' />
			</td>
			<td>${item.sn}</td>
			
			<td><input type='text' name='purchaseCertItems_metername' value='${item.metername}'  style='width:150px' /></td>
	
			<td><input type='text' name='purchaseCertItems_spec' value='${item.spec}' style='width:300px' /></td>
			
			<td><input type='text' name='purchaseCertItems_brand' value='${item.brand}'  style='width:150px' /></td>
	
		    <td><input type='text' name='purchaseCertItems_unit' value='${item.unit}' style='width:50px' /></td>
	
			<td><input type='text' name='purchaseCertItems_numb' value='<fmt:formatNumber value="${item.numb}" pattern="#.#"/>' style='width:50px' /></td>
			
			<td><input type='text' name='purchaseCertItems_price' value='${item.price}' style='width:50px' /></td>
			
			<td><input type='text' name='purchaseCertItems_amount' value='${item.amount}' style='width:50px' /></td>
	  
			<td><input type='button' class='add'  value='+'   /><input type='button' class='remove'  value='-'   /></td>
		</tr>
		</c:forEach>
	</tbody>
	</table>