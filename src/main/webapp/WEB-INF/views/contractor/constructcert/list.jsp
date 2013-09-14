<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	
	<%@ include file="/common/jqui.jsp" %>
	
	<style type="text/css">
	.state{
		color: #FFFFFF;
		display: inline-block;
		line-height: 16px; 
		padding: 2px 4px;
	}
	
	.state-new_{
		background-color: #999999;  
	}
	.state-saved{
		background-color: #3A87AD;
	}
	.state-commit{
		background-color: #F89406;
	}
	.state-finish{
		background-color:#B94A48;
	}
	.state-invalid{
		background-color:#FF8080;
	}
	</style>


    <script type="text/javascript">
    $(function() {
    	
    	$(".datePK").datepicker();
    	
    	var optColorArr=['#FFFFFF','#FFFFFF','#999999','#3A87AD','#F89406','#B94A48'];
    	$("select[name='state']").each(function(){
			$(this).find("option").each(function(idx){
				$(this).css("background-color",optColorArr[idx]);
			});
			$(this).bind("change",function(){
				$(this).css("background-color",optColorArr[this.selectedIndex])
			}).triggerHandler("change");
		}); 
    	
    	
    	$("#btn_new").click(function(){
			window.open('${ctx}/${projectId}/contractor/${supplierId}/constructcert/-1/edit','_self');
			return false;
		});
    	
    	$(".btn_edit").click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
			window.open('${ctx}/${projectId}/contractor/${supplierId}/constructcert/'+param.id+'/edit','_self');
			return false;
		});
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/${projectId}/contractor/${supplierId}/constructcert/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'constructCertId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	
    	$('.btn_view').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/${projectId}/contractor/${supplierId}/constructcert/{id}/view'.replace('{id}',param.id),'_self');
        	return false;
    	});
    	
    });
    </script>
</head>

<body>
	<c:set var="selectmenu" value="constructcert" />
	<%@ include file="/component/contractorMenu.jsp" %>	
	
	<%@ include file="/common/message.jsp" %>	
	<div style="text-align: right;" id="headdiv">
		<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain"  class="hctable deftable">
		<thead>
		<tr>
			<th>序号</th><th>业务编号</th><th>施工联系单</th><th>专业分类</th><th>状态</th>
			<th>总金额</th>
			<th>制单人</th><th>制单日期</th><th>签发人</th><th>签发日期</th><th>结算人</th><th>结算日期</th><th>操作</th>
		</tr>
		</thead>
		<tbody>
		<c:forEach var="constructCertDto" items="${ConstructCertDtoList}">
			<tr>
				<td>${constructCertDto.constructCert.no}</td>
				<td>${constructCertDto.constructCert.busino}</td>
				<td>${constructCertDto.constructCont.no}</td>
				<td>${constructCertDto.constructCert.constructKey.contract.specialty.name}</td>
				<td><span class="state state-${constructCertDto.constructCert.state}" >${constructCertDto.constructCert.state.name}</span></td>
				<td>${constructCertDto.constructCert.tolsum}</td>
				<td>${constructCertDto.constructCert.creater.name}</td>
				<td><fmt:formatDate value="${constructCertDto.constructCert.createdate}" pattern="yyyy-MM-dd HH:mm"/></td>
				<td>${constructCertDto.constructCert.signer.name}</td>
				<td><fmt:formatDate value="${constructCertDto.constructCert.signdate}" pattern="yyyy-MM-dd"/></td>
				<td>
					${constructCert.settler.name}
				</td>
				<td>
					<fmt:formatDate value="${constructCert.settledate}" pattern="yyyy-MM-dd"/>
				</td>
				<td>
					<c:if test="${constructCertDto.constructCert.state=='saved'}">	
					<input type="button" param='{"id":"${constructCertDto.constructCert.id}"}' value="修改"  class="btn_edit">
					<input type="button" param='{"id":"${constructCertDto.constructCert.id}"}' value="删除"  class="btn_delete">
					</c:if>
					<input type="button" param='{"id":"${constructCertDto.constructCert.id}"}' value="查看"  class="btn_view">
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<script type="text/javascript">
		$(function(){
			$("#headdiv").css("width",$("#tblmain").css("width"));
		})
	</script>
	



	
</body>
</html>