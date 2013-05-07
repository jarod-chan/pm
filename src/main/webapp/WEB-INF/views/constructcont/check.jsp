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


    <script type="text/javascript">
    
    $(function(){
		$("#btn_back").click(function(){
			window.open('${ctx}/task/list','_self');
			return false;
		});
		
		$("#btn_commit").click(function(){
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action"); 
			actionFrom.attr("action",oldAction+"/commit").submit();
		});
		
    	$('#tabmain tr').find('td:eq(0)').css("text-align","right");
    })
    
    </script>
</head>

<body>
	<h2>施工联系单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<table id="tabmain">
		<tr>
			<td>编号：</td><td>${constructCont.no}</td>
		</tr>
		<tr>
			<td>项目：</td><td>${constructCont.constructKey.project.no}</td>
		</tr>
		<tr>
			<td>项目负责人：</td><td>${constructCont.leader.name}</td>
		</tr>
		<tr>
			<td>合同：</td>
			<td>
				${constructCont.constructKey.contract.no}
			</td>
		</tr>
		<tr>
			<td>施工承包方：</td><td>${constructCont.constructKey.contract.supplier.name}</td>
		</tr>
		<tr>
			<td style="vertical-align: top">原因：</td><td>${constructCont.reason}</td>
		</tr>
		<tr>
			<td>状态：</td><td>${constructCont.state.name}</td>
		</tr>
		<tr>
			<td>制单人：</td><td>${constructCont.creater.name}</td>
		</tr>
		<tr>
			<td>制单日期：</td><td><fmt:formatDate value="${constructCont.createdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		<tr>
			<td>签发人：</td><td>${constructCont.signer.name}</td>
		</tr>
		<tr>
			<td>签发日期：</td><td><fmt:formatDate value="${constructCont.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
	</table>
	<br>

	联系项目
	<table border="1">
	<thead>
		<tr>
			<th>序号</th><th>内容</th><th>暂定单价</th><th>暂定数量</th><th>单位</th><th>暂定结算价</th><th>计划完成日期</th><th>实际完成日期</th><th>实际执行结果</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${constructCont.constructContItems}" var="item">
		<tr>
			<td>${item.sn}</td><td>${item.content}</td><td>${item.price}</td><td>${item.numb}</td><td>${item.unit}</td><td>${item.amount}</td><td>${item.plandate}</td><td>${item.realdate}</td><td>${item.result}</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>
	<br>
	
	<form action="${ctx}/constructcont/check" method="post">
		<input type="hidden" name="taskId" value="${task.id}"/>
		<input type="hidden" name="businessId" value="${constructCont.id}"/>
		<div class="chk_div" >
			<div class="chk_result">
				审批结果：<select name="result">
					<c:forEach var="result" items="${resultList}">
						<option value="${result}">${result.name}</option>
					</c:forEach>
				</select>
			</div>	
			<div class="chk_content">
				审批意见：<textarea name="content" style="vertical-align: top;height:180px;width: 400px; "></textarea>
			</div>
		</div>
		<br>
		<input type="button" value="提交流程"  id="btn_commit">
		<input type="button" value="返回"  id="btn_back">
	</form>

	
</body>
</html>
