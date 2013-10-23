<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>
	<%@ include file="/common/jqui.jsp" %>	
	<%@ include file="/common/jqui2.jsp" %>	
	
	<style type="text/css">
		.selectTd{
			background-color: red;
		}
	</style>	


    <script type="text/javascript">
    $(function() {
		$("#btn_save").click(function(){
			var actionFrom=$("form");
			$("#tblmain tbody tr").formatName(); 
			var oldAction=actionFrom.attr("action");
			actionFrom.attr("action",oldAction+"/save").submit();
		});
		$(".chk_plt").click(function(){
			if($(this).is(':checked')){
				$(this).next().val(true);
				$(this).parents("tr").find("select").show();
			}else{
				$(this).next().val(false);
				$(this).parents("tr").find("select").hide();
			}
		});
		
		$("#btn_back").click(function(){
			window.open('${ctx}/project/list','_self');
			return false;
		});
    });
    
    $(function(){
    	
    	var trdom = $("<tr>");
		$("<td>")
		  .append($("<input type='hidden' name='' value='pjmembers_id'>"))
		  .css("display","none")
		  .appendTo(trdom);
		
		$("<td>").appendTo(trdom);
		
		var selDom=$("<select name='pjmembers_role.key' />");
		$("<option value=''>--</option>").appendTo(selDom);
		<c:forEach var="pjrole" items="${pjroles}">$("<option value='${pjrole.key}' >${pjrole.name}</option>").appendTo(selDom);</c:forEach>
		
		$("<td>").append(selDom)
		  .appendTo(trdom);
		
		$("<td>").append($("<span class='span_btn btn_selUser' >选择</span><input type='hidden' name='pjmembers_user.key' value=''>"))
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
			newtr.find(".span_btn").hover(
				function () {
					$(this).addClass("span_btn_mouseon");
		 		},
		 		function () {
		 			$(this).removeClass("span_btn_mouseon")
		 		}
	  	    ).click(selUserFun);
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
        	
    	$(".datePK").datepicker();
    	
    })
    </script>
</head>

<body>
	<h2>【${project.name}】项目成员</h2>
	<%@ include file="/common/message.jsp" %>	
	<br>
	<form action="${ctx}/project/${project.id}/pjmember" method="post">
	<table id="tblmain" class="deftable hctable" >
		<thead>
			<tr>
				<th>序号</th>
				<th>项目角色</th>
				<th>系统用户</th>
				<th>操作<input type="button" class="addLast" value="+"  /></th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="pjmember" items="${pjmembers}" varStatus="status">
			<tr>
				<td style="display: none">
					<input type="hidden" name="pjmembers_id" value="${pjmember.id}">
				</td>
				<td>
				  ${status.count}
				</td>
				<td>
					<select name="pjmembers_role.key" >
						<option value="">--</option>
						<c:forEach var="pjrole" items="${pjroles}">
							<option value="${pjrole.key}" <c:if test="${pjmember.role.key==pjrole.key}">selected="true"</c:if> >${pjrole.name}</option>
						</c:forEach>
					</select>
				</td>				
				<td>
					<span class="span_btn btn_selUser" >${pjmember.user.name}</span><input type="hidden" name="pjmembers_user.key" value="${pjmember.user.key}">
				</td>
				<td>
					<input type='button' class='add'  value='+'   /><input type='button' class='remove'  value='-'   />
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<br/>
	<br/>
	<input type="button" value="保存"  id="btn_save">	
	<input type="button" value="返回"  id="btn_back">
	</form>
	
	<%@ include file="/component/selUser.jsp" %>	
</body>
</html>
