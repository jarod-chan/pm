<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<script type="text/javascript">
	$(function(){
		
		var seldom=$("<select>");
    	seldom.attr("name","constructCertItems_unit")
    		.append($("<option ></option>"))
    		.append($("<option value='元/立方米'>元/立方米</option>"))
    		.append($("<option value='元/立方米'>元/立方米</option>"))
    		.append($("<option value='元/工作日'>元/工作日</option>"));
		
		var trdom = $("<tr>");
		$("<td>")
		  .append($("<input type='hidden' name='constructCertItems_sn'   value='' />"))
		  .append($("<input type='hidden' name='constructCertItemsId'   value='-1' />"))
		  .css("display","none")
		  .appendTo(trdom);
		
		$("<td>").appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructCertItems_content' style='width:300px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructCertItems_price' style='width:50px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructCertItems_numb' style='width:50px' />"))
		  .appendTo(trdom);
		
		$("<td>").append(seldom)
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructCertItems_amount' style='width:100px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='button' value='上传文件'>"))
		  .appendTo(trdom);
		
		$("<td>").appendTo(trdom);
		
		$("<td>").appendTo(trdom);
		
		$("<td>").appendTo(trdom);
		  
		 $("<td>")
			.append($("<input type='button' class='add'  value='+'   />"))
			.append($("<input type='button' class='remove'  value='-'   />"))
			.appendTo(trdom);
		 
		 var computerAmount=function(tr){
			var price=$(tr).find("input[name='constructCertItems_price']").val();
			var numb=$(tr).find("input[name='constructCertItems_numb']").val();
			var tdAmt=$(tr).find("input[name='constructCertItems_amount']");
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
				var amt=$(this).find(":input[name='constructCertItems_amount']").val();
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
		}
		
		function cloneTR(){
			var newtr=trdom.clone();
			newtr
			  .find("input[name='constructCertItems_price']").bind("blur",priceBlur).end()
			  .find("input[name='constructCertItems_numb']").bind("blur",numbBlur).end()
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
				$(this).find("td").eq(0).find("[name='constructCertItems_sn']").val(index);			
				$(this).find("td").eq(1).html(index);
			});
		}
		
		
		$(".addLast").bind("click",function(){
			var tbody=$(this).parents("thead").next();
			tbody.append(cloneTR()); 
			reIndexTable(tbody);
		});
		
		var constructContIdList=[<c:forEach var="constructCont" items="${constructContList}" varStatus="status">${constructCont.id}<c:if test="${!status.last}">,</c:if></c:forEach>];
    	
    	$("#btn_cont").click(function(){
    		window.open('${ctx}/constructcont/'+constructContIdList[$("select:eq(0)").get(0).selectedIndex]+'/view?notback=true','_blank');
			return false;
    	})
		
	   	$("#btn_load").click(function(){
    		var constructContId=constructContIdList[$("select:eq(0)").get(0).selectedIndex];
    		$.getJSON('${ctx}/constructcont/'+constructContId+'/items',function(itemlist){
    			var tbody=$("#tabitem tbody");
    			tbody.empty();
    			for(i=0;i<itemlist.length;i++){
    				var tr=cloneTR();
    				tr.find("input[name=constructCertItems_content]").val(itemlist[i].content);
    				tr.find("input[name=constructCertItems_price]").val(itemlist[i].price);
    				tr.find("input[name=constructCertItems_numb]").val(itemlist[i].numb);
    				tr.find("input[name=constructCertItems_unit]").val(itemlist[i].unit);
    				tr.find("input[name=constructCertItems_amount]").val(itemlist[i].amount);
    				tbody.append(tr); 
    			}
    			reIndexTable(tbody);
    		})
    	})
    	
    	$("input[name='constructCertItems_price']").bind("blur",priceBlur);
		$("input[name='constructCertItems_numb']").bind("blur",numbBlur);
    	$(".add").bind("click",add);
		$(".remove").bind("click",remove);	
		
		$("select:eq(0)").change(function(){
    		var opt=$(this).find("option:selected").text();
    		var idx=opt.lastIndexOf("-");
    		$("#supplier_name").html(opt.substring(idx+1));
    	}).triggerHandler("change");
		
    	<c:if test="${fn:length(constructCert.constructCertItems)==0}">
    	$(".addLast").triggerHandler("click");
    	</c:if>
    	
    	$('#tabmain tr').find('td:eq(0)').css("text-align","right");
	})
</script>

	<input type="hidden" name="id" value="${constructCert.id}">
	<table id="tabmain">
		<tr>
			<td>编号：</td><td>${constructCert.no}<c:if test="${empty constructCert.no}">自动生成</c:if></td>
		</tr>
		<tr>
			<td>项目负责人：</td><td>${constructCert.leader.name}</td>
		</tr>
		<tr>
			<td>施工联系单：</td>
			<td>
				<select name="constructKey.id">
					<c:forEach var="constructCont" items="${constructContList}">
						<option value="${constructCont.constructKey.id}" <c:if test="${constructCert.constructKey.id==constructCont.constructKey.id}">selected="true"</c:if> >${constructCont.no}-${constructCont.constructKey.contract.supplier.name}</option>
					</c:forEach>
				</select>
				<input type="button" id="btn_cont" value="查看施工联系单"/>
				<input type="button" id="btn_load" value="加载联系单内容到当前明细"/>
			</td>
		</tr>
		<tr>
			<td>施工承包方：</td>
			<td><span id="supplier_name"></span></td>
		</tr>
		<tr>
			<td style="vertical-align: top">原因：</td><td><textarea name="reason" rows="6" cols="30" style="vertical-align: top">${constructCert.reason}</textarea></td>
		</tr>
		<tr>
			<td>状态：</td><td>${constructCert.state.name}</td>
		</tr>
		<tr>
			<td>总金额：</td><td><span id="sp_tolsum">${constructCert.tolsum}</span><input type="hidden" name="tolsum" value="${constructCert.tolsum}"></td>
		</tr>
		<tr>
			<td>制单人：</td><td>${constructCert.creater.name}</td>
		</tr>
		<tr>
			<td>制单日期：</td><td><fmt:formatDate value="${constructCert.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>签发人：</td><td>${constructCont.signer.name}</td>
		</tr>
		<tr>
			<td>签发日期：</td><td><fmt:formatDate value="${constructCont.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>结算人：</td>
			<td>
				${constructCert.settler.name}
			</td>
		</tr>
		<tr>
			<td>结算日期：</td>
			<td>
				<fmt:formatDate value="${constructCert.settledate}" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
		
	</table>
		
		<br>
		签证项目
		<table border="1" id="tabitem">
		<thead>
			<tr>
				<th>序号</th><th>内容</th><th>结算单价</th><th>结算数量</th><th>单位</th><th>结算价格	</th>
				<th>签证依据	</th>
				<th>监理意见	</th>
				<th>现场管理人员意见	</th>
				<th>项目负责人意见	</th>
				<th>操作<input type="button" class="addLast" value="+"  /></th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${constructCert.constructCertItems}" var="item">
				<tr>
					<td style="display: none"><input type='hidden' name='constructCertItems_sn'   value='${item.sn}' /><input type='hidden' name='constructCertItemsId'   value='${item.id}' /></td>
					<td>${item.sn}</td>
					<td><input type='text' name='constructCertItems_content' value='${item.content}' style='width:300px' /></td>
					<td><input type='text' name='constructCertItems_price' value='${item.price}' style='width:50px' /></td>
					<td><input type='text' name='constructCertItems_numb' value='<fmt:formatNumber value="${item.numb}" pattern="#.#"/>' style='width:50px' /></td>
					<td>
						<select name='constructContItems_unit'>
							<option <c:if test="${item.unit==''}">selected="true"</c:if> ></option>
							<option value="元/平方米" <c:if test="${item.unit=='元/平方米'}">selected="true"</c:if> >元/平方米</option>
							<option value="元/立方米" <c:if test="${item.unit=='元/立方米'}">selected="true"</c:if> >元/立方米</option>
							<option value="元/工作日" <c:if test="${item.unit=='元/工作日'}">selected="true"</c:if> >元/工作日</option>
						</select>
					</td>
					<td><input type='text' name='constructCertItems_amount' value='${item.amount}' style='width:100px' /></td>
					
					<td>
						<c:choose>
							<c:when test="${not empty item.imgPath}">
								 <a href="${ctx}/img/${item.imgPath}" target="_blank" >示例图片</a>
							</c:when>
							<c:otherwise>
									<input type="button" value="上传文件">
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						${item.superOpinion.name}
					</td>
					<td>
						${item.manageOpinion.name}
					</td>
					<td>
						${item.leaderOpinion.name}
					</td>
					<td><input type='button' class='add'  value='+'   /><input type='button' class='remove'  value='-'   /></td>
				</tr>
			</c:forEach>
		</tbody>
		</table>