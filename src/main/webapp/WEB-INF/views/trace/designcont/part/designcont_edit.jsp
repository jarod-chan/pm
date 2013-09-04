<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<style type="text/css">
.selectedSpan {
    background-color: #75BAFF;
    color: #FFFFFF;
}
.selectReason .reasonSpan {
    display: inline-block;
    line-height: 16px;
    padding: 2px 4px;
}
</style>
<script type="text/javascript">
$(function(){
	//todo this 
	$(".selectReason .reasonChk").click(function(){
	    if(this.checked){
	    	var haschkSpan=$(".selectReason .selectedSpan");
	    	if(haschkSpan[0]){
				haschkSpan.removeClass("selectedSpan");
				var chk=haschkSpan.find(".reasonChk");
				chk.removeAttr("checked").removeAttr("name");
			} 
	    	$(this).attr("name","reason");
	    	$(this).parent().addClass("selectedSpan");
	    }else{
	    	$(this).removeAttr("name");
	    	$(this).parent().removeClass("selectedSpan");
	    }
	})
	$(".selectReason .reasonChk[value='${designCont.reason}']").trigger("click");
})
</script>

	 <script type="text/javascript">
    $(function(){
    	
    	var trdom = $("<tr>");
		$("<td>")
		  .append($("<input type='hidden' name='designContItems_sn'   value='' />"))
		  .append($("<input type='hidden' name='designContItemsId'   value='-1' />"))
		  .css("display","none")
		  .appendTo(trdom);
		
		$("<td>").appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='designContItems_content' style='width:600px' />"))
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
			computerTolamount();
		}
		
		function cloneTR(){
			var newtr=trdom.clone();
			newtr
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
				$(this).find("td").eq(0).find("[name='designContItems_sn']").val(index);			
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
    	
    	<c:if test="${fn:length(designCont.designContItems)==0}">
    	$(".addLast").triggerHandler("click");
    	</c:if>
    	
        	
    	$(".datePK").datepicker();
    	
    })
    
 
	 </script>
	 
	  <%@ include file="/script/fmttable.jsp" %>

	<input type="hidden" name="id"  value="${designCont.id}">
	<table id="tabmain" class="fmttable">

		<c:set var="parma_no" value="${designCont.no}" />
		<c:set var="parma_busino" value="${designCont.busino}" />
		<%@ include file="/component/noShowBill.jsp" %>	

		<tr>
			<td>项目负责人：</td><td>${designCont.leader.name}</td>
			<td>状态：</td><td><span class="state state-${designCont.state}" >${designCont.state.name}</span></td>
		</tr>
		
		<tr>
			<td>问题通知单：</td> 
			<td>
				<span id="spanNoti">${designCont.designNoti.no}</span><input type="hidden" name="designNoti.id" value="${designCont.designNoti.id}">
				<input type="button" id="btn_selnoti" value="选择" />
			</td>
			<td>技术分类：</td>
			<td>
			<select name="techType">
				<option value="">--</option>
				<c:forEach var="techType" items="${techTypes}">
					<option value="${techType}" <c:if test="${techType== designCont.techType}">selected="true"</c:if> >${techType.name}</option>
				</c:forEach>
			</select>
			</td>
		</tr>
		
		<tr>
			<td>设计策划合同：</td>
			<td>
				<span id="spanContract">${designCont.contract.no}</span><input type="hidden" name="contract.id" value="${designCont.contract.id}">
				<input type="button" id="btn_selContract" value="选择" />
			</td>
			<td>设计服务商：</td>
			<td>
			 <span id="supplier_name">${designCont.contract.supplier.no}</span>
			</td>
		</tr>
		<tr>
			<td style="vertical-align: top">联系内容：</td>
			<td colspan="3"><textarea name="content" class="edittextarea">${designCont.content}</textarea></td>
		</tr>
		
		<tr>
			<td>技术变更原因：</td>
			<td colspan="3">
				<div class="selectReason">
					<c:forEach var="item" items="${reasonItems}">
						${item.type.en}.${item.type.name}<br/>
						<c:forEach var="reason" items="${item.reasons}">
						<span class="reasonSpan"><input type="checkbox" value="${reason}" class="reasonChk" />${reason.en}.${reason.name}</span>
						</c:forEach>
						<br/>
					</c:forEach>
				</div>
			</td>
		</tr>	
		
		<tr>
			<td style="vertical-align: top">相关文件：</td>
			<td colspan="3">
					<c:set var="upload_width" value="500" />
					<%@ include file="/component/fileUpload.jsp" %>	
			</td>
		</tr>
		
		<tr>
			<td style="vertical-align: top">文件说明：</td>
			<td colspan="3"><textarea name="filedesc" class="edittextarea">${designCont.filedesc}</textarea></td>
		</tr>
		
		<tr>
			<td>制单人：</td><td>${designCont.creater.name}</td>
			<td>制单日期：</td><td><fmt:formatDate value="${designCont.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>

		<tr>
			<td>签发人：</td><td>${designCont.signer.name}</td>
			<td>签发日期：</td><td><fmt:formatDate value="${designCont.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>接收人：</td>	<td>${designCont.receiver.name}</td>
			<td>接收日期：</td><td><fmt:formatDate value="${designCont.receivedate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	

	</table>
	
	<%@ include file="selDesigncont.jsp" %>	
	<%@ include file="selContract.jsp" %>	