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
    	
    	var optColorArr=['#FFFFFF','#FFFFFF','#999999','#3A87AD','#F89406','#B94A48','#FF8080'];
    	$("select[name='state']").each(function(){
			$(this).find("option").each(function(idx){
				$(this).css("background-color",optColorArr[idx]);
			});
			$(this).bind("change",function(){
				$(this).css("background-color",optColorArr[this.selectedIndex])
			}).triggerHandler("change");
		}); 
    	
    	
    	$("#btn_new").click(function(){
			window.open('${ctx}/constructcert/-1/edit','_self');
			return false;
		});
    	
    	$(".btn_edit").click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
			window.open('${ctx}/constructcert/'+param.id+'/edit','_self');
			return false;
		});
    	
    	$('.btn_delete').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
        	$('<form/>',{action:'${ctx}/constructcert/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'constructCertId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	
    	$('.btn_view').click(function(){
    		var param=jQuery.parseJSON($(this).attr("param"));
    		window.open('${ctx}/constructcert/{id}/view'.replace('{id}',param.id),'_self');
        	return false;
    	});
    	
    	$('#btn_query').click(function(){
			var actionFrom=$("form:eq(0)");
			var oldAction=actionFrom.attr("action"); 
			actionFrom.attr("action",oldAction+"/list").submit();
    	})
    	
    	$('#btn_clear').click(function(){
    		window.open('${ctx}/constructcert/list','_self');
			return false;
    	})
    });
    </script>
</head>

<body>
	<h2>工程签证单</h2>
	
	<div style="text-align: left;">
	<form action="${ctx}/constructcert" method="post">
		序号:<input type="text" name="no" value="${query.no}">
		施工承包方:<select name="supplier.id" >
					<option value="" >-所有-</option>
					<c:forEach var="supplier" items="${supplierList}">
						<option value="${supplier.id}" <c:if test="${supplier.id==query.supplier.id}">selected="true"</c:if> >${supplier.name}</option>
					</c:forEach>
				</select>
		制单日期:<input type="text" name="createdate_beg" class="datePK" value="<fmt:formatDate value="${query.createdate_beg}" pattern="yyyy-MM-dd"/>" >--<input type="text" name="createdate_end" class="datePK" value="<fmt:formatDate value="${query.createdate_end}" pattern="yyyy-MM-dd"/>">
		专业分类:<select name="specialty" >
					<option value="" >-所有-</option>
					<c:forEach var="contractSpec" items="${query.specialtyList}">
						<option value="${contractSpec}" <c:if test="${contractSpec==query.specialty}">selected="true"</c:if> >${contractSpec.name}</option>
					</c:forEach>
				</select>
		<br>
		状态:<select name="state" >
					<c:forEach var="state" items="${query.stateList}">
						<option value="${state.value}" <c:if test="${state.value==query.state}">selected="true"</c:if> >${state.name}</option>
					</c:forEach>
				</select>
		排序:<select name="orderAttribute">
				<c:forEach var="attr" items="${query.orderAttributeList}">
					<option value="${attr.value}" <c:if test="${attr.value== query.orderAttribute}">selected="true"</c:if> >${attr.name}</option>
				</c:forEach>
			</select> 
			<select name="orderType">
				<c:forEach var="type" items="${query.orderTypeList}">
					<option value="${type.value}" <c:if test="${type.value== query.orderType}">selected="true"</c:if> >${type.name}</option>
				</c:forEach>
			</select>  
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" value="查询" id="btn_query"> 
		<input type="button" value="清空" id="btn_clear"> 
	</form>
	</div>
	
	<%@ include file="/common/message.jsp" %>	
	<div style="text-align: right;" id="headdiv">
		<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain" border="1" class="hctable">
		<thead>
			<tr>
				<td>序号</td><td>业务编号</td><td>施工联系单</td><td>施工承包方</td><td>专业分类</td><td>状态</td>
				<td>总金额</td>
				<td>制单人</td><td>制单日期</td><td>签发人</td><td>签发日期</td><td>结算人</td><td>结算日期</td><td>操作</td>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="constructCertDto" items="${ConstructCertDtoList}">
			<tr>
				<td>${constructCertDto.constructCert.no}</td>
				<td>${constructCertDto.constructCert.busino}</td>
				<td>${constructCertDto.constructCont.no}</td>
				<td>${constructCertDto.constructCert.constructKey.supplier.name}</td>
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
