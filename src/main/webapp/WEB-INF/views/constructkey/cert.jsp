<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	


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
		  .append($("<input type='hidden' name='constructCertItems_sn'   value='' />"))
		  .css("display","none")
		  .appendTo(trdom);
		
		$("<td>").appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructCertItems_content' style='width:300px' />"))
		  .appendTo(trdom);
		
		$("<td>").append($("<input type='text' name='constructCertItems_cost'/>"))
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
    });
    
    
    
    
    
    </script>
</head>

<body>
	<h2>施工签证单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/project/${constructKey.project.id}/constructkey/${constructKey.id}/cert" method="post">
		编号：
		<input type="text" name="no" value=""/>
		<br>
		项目：${constructKey.project.name }
		<br>
		结算对象：${constructKey.contract.payname }
		<input type="hidden" name="constructKey.id" value="${constructKey.id}">
		<br>
		用户：
		${user.name}
		<br>
		
		<br>
		签证项目
		<table border="1">
		<thead>
			<tr>
				<th>序号</th><th>内容</th><th>费用</th><th>操作<input type="button" class="addLast" value="+"  /></th>
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
