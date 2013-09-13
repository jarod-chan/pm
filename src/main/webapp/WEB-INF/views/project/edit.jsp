<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	
	<%@ include file="/common/jqui.jsp" %>	


    <script type="text/javascript">
    $(function() {
    	var validator=$("form").validate({
    		rules: {
    			name: {
    				required: true,
    				maxlength: 8
    			}
    		}
    	});
    	
		$("#btn_save").click(function(){
			if(!validator.form()){return;}
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action");
			actionFrom.attr("action",oldAction+"/save").submit();
		});
		
		$("#btn_back").click(function(){
			window.open('${ctx}/project/list','_self');
			return false;
		});
		
		$('#tabmain tr').find('td:eq(0)').css("text-align","right");
		
		$(".datePK").datepicker();
    });
    </script>
</head>

<body>
	<h2>项目</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/project" method="post">
	
	<input type="hidden" name="id" value="${project.id}">
	
	<table id="tabmain">	
		
		<tr><td>
		编号：</td><td>
		<c:set var="parma_no" value="${project.no}" />
		<%@ include file="/component/noShow.jsp" %>	
		</td></tr>
		
		<tr><td>
		项目名称：</td><td>
		<input type="text" name="name" value="${project.name}"/>
		</td></tr>
		
		
		<tr><td>
		项目状态：</td><td>
		${project.state.name}
		</td></tr>
		
		<tr><td>
		项目投资计划批文：</td><td>
		<input type="text" name="inveplan" value="${project.inveplan}"/>
		<td></tr>
		
		<tr><td>
		土地使用权证：</td><td>
		<input type="text" name="landuseRight" value="${project.landuseRight}"/>
		<td></tr>
		
		<tr><td>
		用地许可证：</td><td>
		<input type="text" name="landusePermit" value="${project.landusePermit}"/>
		<td></tr>
		
		<tr><td>
		工程规划许可证：</td><td>
		<input type="text" name="projectPlan" value="${project.projectPlan}"/>
		<td></tr>
		
		<tr><td>
		工程施工许可证：</td><td>
		<input type="text" name="projectCnut" value="${project.projectCnut}"/>
		<td></tr>
		
		<tr><td>
		商品房预售许可证：</td><td>
		<input type="text" name="perSale" value="${project.perSale}"/>
		<td></tr>
		
		<tr><td>
		竣工验收规划确认书：</td><td>
		<input type="text" name="completionConfirm" value="${project.completionConfirm}"/>
		<td></tr>
		
		<tr><td>
		 竣工验收备案：</td><td>
		<input type="text" name="completionBackup" value="${project.completionBackup}"/>
		<td></tr>
		
		<tr><td>
		开工日期：</td><td>
		<input type="text" name="begDate" class="datePK" value="${project.begDate}"/>
		<td></tr>
		
		<tr><td>
		竣工日期：</td><td>
		<input type="text" name="endDate"  class="datePK" value="${project.endDate}"/>
		<td></tr>
		
		<tr><td>
		位置：</td><td>
		<input type="text" name="location" value="${project.location}"/>
		<td></tr>
		
		<tr><td>
		所占股份：</td><td>
		<input type="text" name="stock" value="${project.stock}"/>
		<td></tr>
		
		<tr><td>
		总投资：</td><td>
		<input type="text" name="totalinve" value="${project.totalinve}"/>
		<td></tr>
		</table>

		<input type="button" value="保存"  id="btn_save">
		<input type="button" value="返回"  id="btn_back">
		
	</form>
	
</body>
</html>
