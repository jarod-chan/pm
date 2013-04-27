<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	


    <script type="text/javascript">
    $(function() {
		$("#btn_save").click(function(){
			$("#tabitem tbody tr").formatName();
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action");// return;
			actionFrom.attr("action",oldAction+"/save").submit();
		})
		
		$("#btn_back").click(function(){
			window.open('${ctx}/constructcert/list','_self');
			return false;
		})
		
		var trdom = $("<tr>");
		$("<td>")
		  .append($("<input type='hidden' name='constructCertItems_sn'   value='' />"))
		  .css("display","none")
		  .appendTo(trdom);
		
		$("<td>").appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructCertItems_content' style='width:300px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructCertItems_price' style='width:50px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructCertItems_numb' style='width:50px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructCertItems_unit' style='width:50px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructCertItems_amount' style='width:100px' />"))
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
    		window.open('${ctx}/constructcont/'+constructContIdList[$("select:eq(0)").get(0).selectedIndex]+'/view','_blank');
			return false;
    	})
		
	   	$("#btn_load").click(function(){
    		var constructContId=constructContIdList[$("select:eq(0)").get(0).selectedIndex];
    		$.getJSON('${ctx}/constructcont/'+constructContId+'/items',function(itemlist){
    			var tbody=$("#tabitem tbody");
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
    });
    
    
    $(function(){
    	
    	$("select:eq(0)").change(function(){
    		var opt=$(this).find("option:selected").text();
    		var idx=opt.lastIndexOf("-");
    		$("#supplier_name").html(opt.substring(idx+1));
    	}).triggerHandler("change");
		
    	$(".addLast").triggerHandler("click");
    	
    	$('#tabmain tr').find('td:eq(0)').css("text-align","right");
    	
    	
		
		
    	
 
    	
    })
    
    
    </script>
</head>

<body>
	<h2>施工签证单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/constructcert" method="post">
	<table id="tabmain">
		<tr>
			<td>编号：</td><td>${constructCert.no}<input type="hidden" name="no" value="${constructCert.no}"/></td>
		</tr>
		<tr>
			<td>项目：</td><td><input type="hidden" name="constructKey.project.id" value="${project.id}"> ${project.name}</td>
		</tr>
		<tr>
			<td>项目负责人：</td><td><input type="hidden" name="leader.key" value="${project.user.key}">${project.user.name}</td>
		</tr>
		<tr>
			<td>施工联系单：</td>
			<td>
				<select name="constructKey.id">
					<c:forEach var="constructCont" items="${constructContList}">
						<option value="${constructCont.constructKey.id}">${constructCont.no}-${constructCont.constructKey.contract.supplier.name}</option>
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
			<td style="vertical-align: top">原因：</td><td><textarea name="reason" rows="6" cols="30" style="vertical-align: top"></textarea></td>
		</tr>
		<tr>
			<td>状态：</td><td>${constructCert.state.name}</td>
		</tr>
		<tr>
			<td>制单人：</td><td>${constructCert.creater.name}</td>
		</tr>
		<tr>
			<td>制单日期：</td><td><fmt:formatDate value="${constructCert.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>签发人：</td><td></td>
		</tr>
		<tr>
			<td>签发日期：</td><td></td>
		</tr>
	</table>
		
		<br>
		签证项目
		<table border="1" id="tabitem">
		<thead>
			<tr>
				<th>序号</th><th>内容</th><th>结算单价</th><th>结算数量</th><th>单位	</th><th>结算价格	</th><th>操作<input type="button" class="addLast" value="+"  /></th>
			</tr>
		</thead>
		<tbody>
		</tbody>
		</table>
		<br>
		<input type="button" value="保存"  id="btn_save">
		<input type="button" value="返回"  id="btn_back">
		
	</form>
	
</body>
</html>
