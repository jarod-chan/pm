<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

	 <script type="text/javascript">
    $(function(){
    	
    	var trdom = $("<tr>");
		$("<td>")
		  .append($("<input type='hidden' name='designNotiItems_sn'   value='' />"))
		  .append($("<input type='hidden' name='designNotiItemsId'   value='-1' />"))
		  .css("display","none")
		  .appendTo(trdom);
		
		$("<td>").appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='designNotiItems_content' style='width:600px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='designNotiItems_graphno'  />"))
		  .appendTo(trdom);
		
		$("<td>").css("width","150px").appendTo(trdom);
		$("<td>").append($("<input type='button' class='btn_itemfile'  value='打开'   />")).appendTo(trdom);
		  
		 $("<td>")
			.append($("<input type='button' class='add'  value='+'   />"))
			.append($("<input type='button' class='remove'  value='-'   />"))
			.appendTo(trdom);
		
		function openItemFile(){
			$(this).parent().prev().addClass("open_td");
			$( ".uploadify_item" ).dialog( "open" );
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
			  .find(".btn_itemfile").bind("click",openItemFile).end()
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
				$(this).find("td").eq(0).find("[name='designNotiItems_sn']").val(index);			
				$(this).find("td").eq(1).html(index);
			});
		}
		
		
		$(".addLast").bind("click",function(){
			var tbody=$(this).parents("thead").next();
			tbody.append(cloneTR()); 
			reIndexTable(tbody);
		});
		
		$(".btn_itemfile").bind("click",openItemFile);
		$(".add").bind("click",add);
	 	$(".remove").bind("click",remove);
    	
    	
    	<c:if test="${fn:length(designNoti.designNotiItems)==0}">
    	$(".addLast").triggerHandler("click");
    	</c:if>
    	
    	
    })
	 </script>
	 
	 <%@ include file="/script/fmttable.jsp" %>

	<input type="hidden" name="id"  value="${designNoti.id}">
	<table id="tabmain" class="fmttable">

		<c:set var="parma_no" value="${designNoti.no}" />
		<c:set var="parma_busino" value="${designNoti.busino}" />
		<%@ include file="/component/noShowBill.jsp" %>	
	
		<tr>
			<td>项目负责人：</td>
			<td>${designNoti.leader.name}</td>
			<td>状态：</td>
			<td><span class="state state-${designNoti.state}" >${designNoti.state.name}</span></td>
		</tr>
		
		<tr>
			<td>技术分类：</td>
			<td>
			<select name="techType">
				<option value="">--</option>
				<c:forEach var="techType" items="${techTypes}">
					<option value="${techType}" <c:if test="${techType== designNoti.techType}">selected="true"</c:if> >${techType.name}</option>
				</c:forEach>
			</select>
			</td>
		</tr>
		
		<tr>
			<td style="vertical-align: top">原因说明：</td>
			<td colspan="3"><textarea name="reason" class="edittextarea">${designNoti.reason}</textarea></td>
		</tr>
		
		<tr>
			<td>制单人：</td><td>${designNoti.creater.name}</td>
			<td>制单日期：</td><td><fmt:formatDate value="${designNoti.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>签发人：</td><td>${designNoti.signer.name}</td>
			<td>签发日期：</td><td><fmt:formatDate value="${designNoti.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>接收人：</td>	<td>${designNoti.receiver.name}</td>
			<td>接收日期：</td><td><fmt:formatDate value="${designNoti.receivedate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	
	</table>
	
	<%@ include file="selFileItem.jsp" %>
	
	<h3>问题项目</h3>
	<table id="tabitem"  class="deftable">
	<thead>
		<tr>
			<th>序号</th><th>内容</th><th>图号</th><th colspan="2" style="width:200px;">附件</th><th>操作<input type="button" class="addLast" value="+"  /></th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${designNoti.designNotiItems}" var="item">
		<tr>
			<td style="display: none">
				<input type='hidden' name='designNotiItems_sn'   value='${item.sn}' />
				<input type='hidden' name='designNotiItemsId'   value='${item.id}' />
			</td>
			<td>${item.sn}</td>
			<td><input type='text' name='designNotiItems_content' value='${item.content}'  style='width:600px' /></td>
			<td><input type='text' name='designNotiItems_graphno' value='${item.graphno}'  /></td>
	  		
	  		<td style="width: 150px;">
	  			<c:forEach items="${fileMap[item.sn]}" var="file" varStatus="status"><span class='sp_itemfile'><a href="${ctx}/uploadify/filestore/${file.id}">${status.count}</a><input type='hidden' name='itemfileName' value="${file.filename}.${file.suffix}" /><input type='hidden' name='itemfileSn' /><input type='hidden' name='itemfileId' value="${file.id}"/></span></c:forEach>
	  		</td>
	  		<td>
	  			<input type='button' class='btn_itemfile'  value='打开'   />
	  		</td>
	  		
			<td><input type='button' class='add'  value='+'   /><input type='button' class='remove'  value='-'   /></td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	
