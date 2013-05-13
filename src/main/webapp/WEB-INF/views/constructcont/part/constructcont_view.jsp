<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


    <script type="text/javascript">
    
    $(function(){
    	$('#tabmain tr').find('td:eq(0)').css("text-align","right");
    })
    
    </script>

<table id="tabmain">
		<tr>
			<td>编号：</td><td>${constructCont.no}</td>
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
		<tr>
			<td>接收人：</td>
			<td>${constructCont.receiver.name}
			</td>
		</tr>
		<tr>
			<td>接收日期：</td>
			<td>
				${constructCont.receivedate}
			</td>
		</tr>
		<tr>
			<td>计划完成日期：</td>
			<td>
				${constructCont.plandate}
			</td>
		</tr>
		<tr>
			<td>实际完成日期：</td>
			<td>
				${constructCont.realdate}
			</td>
		</tr>
		<tr>
			<td>实际执行结果：</td>
			<td>
				${constructCont.result}
			</td>
		</tr>
	</table>
	<br>

	联系项目
	<table border="1">
	<thead>
		<tr>
			<th>序号</th><th>内容</th><th>暂定单价</th><th>暂定数量</th><th>单位</th><th>暂定结算价</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${constructCont.constructContItems}" var="item">
		<tr>
			<td>${item.sn}</td><td>${item.content}</td><td>${item.price}</td><td>${item.numb}</td><td>${item.unit}</td><td>${item.amount}</td>
		</tr>
		</c:forEach>
	</tbody>
	</table>