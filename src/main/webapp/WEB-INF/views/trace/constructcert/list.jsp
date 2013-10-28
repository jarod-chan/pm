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
	<%@ include file="/common/jqui2.jsp" %>	


    <script type="text/javascript">
    $(function() {
    	
    	$(".datePK").datepicker();
    	
    	$("#btn_new").click(function(){
			window.open('${ctx}/${projectId}/constructcert/-1/edit','_self');
			return false;
		});
    	
    	$(".btn_edit").click(function(){
    		var param=$(this).metadata();
			window.open('${ctx}/${projectId}/constructcert/'+param.id+'/edit','_self');
			return false;
		});
    	
    	$('.btn_delete').click(function(){
    		var param=$(this).metadata();
        	$('<form/>',{action:'${ctx}/${projectId}/constructcert/delete',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'constructCertId',value:param.id}))
				.appendTo($("body"))
			.submit();
    	});
    	
    	$('.btn_view').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/${projectId}/constructcert/{id}/view'.replace('{id}',param.id),'_self');
        	return false;
    	});
    	
    	$('.btn_print').click(function(){
    		var param=$(this).metadata();
    		window.open('${ctx}/${projectId}/constructcert/{id}/print'.replace('{id}',param.id),'_blank');
        	return false;
    	});
    	
    	$(".btn_trace").click(function(){
 	   		var param=$(this).metadata();
			window.open('${ctx}/trace/'+param.processDefKey+'/'+param.id,'_blank');
			return false;
		})
    	
    	$('#btn_query').click(function(){
			var actionFrom=$("form:eq(0)");
			var oldAction=actionFrom.attr("action"); 
			actionFrom.attr("action",oldAction+"/list").submit();
    	})
    	
    	$('#btn_reset').click(function(){
    		window.open('${ctx}/${projectId}/constructcert/list','_self');
			return false;
    	})
    });
    </script>
</head>

<body>
	<h2>工程签证单</h2>
	
	<div style="text-align: left;">
	<form action="${ctx}/${projectId}/constructcert" method="post">
		序号:<input type="text" name="no" value="${query.no}">
		施工承包方:<span class="span_btn" id="selSupplier"><c:if test="${empty query.supplier.name}">所有</c:if>${query.supplier.name}</span>
		<input type="hidden" name="supplier.id" value="${query.supplier.id}">
		制单日期:<input type="text" name="createdate_beg" class="datePK" value="<fmt:formatDate value="${query.createdate_beg}" pattern="yyyy-MM-dd"/>" >--<input type="text" name="createdate_end" class="datePK" value="<fmt:formatDate value="${query.createdate_end}" pattern="yyyy-MM-dd"/>">
		专业分类:<select name="specialty" >
					<option value="" >-所有-</option>
					<c:forEach var="contractSpec" items="${query.specialtyList}">
						<option value="${contractSpec}" <c:if test="${contractSpec==query.specialty}">selected="true"</c:if> >${contractSpec.name}</option>
					</c:forEach>
				</select>
		<br>
		状态:<select name="state" class="bkcolor-state">
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
		<input type="button" value="重置" id="btn_reset"> 
	</form>
	</div>
	<%@ include file="/component/selSupplier.jsp" %>	
	<%@ include file="/common/message.jsp" %>	
	<div style="text-align: right;" id="headdiv">
		<input type="button" value="新建"  id="btn_new">
	</div>
	<br>
	<table id="tblmain" class="hctable deftable">
		<thead>
			<tr>
				<th>序号</th>
				<th>业务编号</th>
				<th>施工承包方</th>
				<th>专业分类</th>
				<th>状态</th>
				<th>总金额</th>
				<th>制单人</th>
				<th>制单日期</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach var="constructCertDto" items="${ConstructCertDtoList}">
			<tr>
				<td>${constructCertDto.constructCert.no}</td>
				<td>${constructCertDto.constructCert.busino}</td>
				<td>${constructCertDto.constructCert.constructKey.supplier.name}</td>
				<td>${constructCertDto.constructCert.constructKey.contract.specialty.name}</td>
				<td><span class="state state-${constructCertDto.constructCert.state}" >${constructCertDto.constructCert.state.name}</span></td>
				<td>${constructCertDto.constructCert.tolsum}</td>
				<td>${constructCertDto.constructCert.creater.name}</td>
				<td><fmt:formatDate value="${constructCertDto.constructCert.createdate}" pattern="yyyy-MM-dd"/></td>
				<td>
					<c:if test="${constructCertDto.constructCert.state=='saved'}">	
					<input type="button" value="修改"  class="btn_edit {id:'${constructCertDto.constructCert.id}'}">
					<input type="button" value="删除"  class="btn_delete {id:'${constructCertDto.constructCert.id}'}">
					</c:if>
					<input type="button" value="查看"  class="btn_view {id:'${constructCertDto.constructCert.id}'}">
					<input type="button" value="打印"  class="btn_print {id:'${constructCertDto.constructCert.id}'}">
					<c:if test="${constructCertDto.constructCert.state=='commit'}">
						<input type="button" value="跟踪"  class="btn_trace {id:'${constructCertDto.constructCert.id}',processDefKey:'pm-construct-cert'}">
					</c:if>
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
