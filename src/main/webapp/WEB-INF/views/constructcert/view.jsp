<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	


    <script type="text/javascript">
    $(function() {

		$("#btn_back").click(function(){
			window.open('${ctx}/constructcert/list','_self');
			return false;
		})
		
		$("#btn_cont").click(function(){
    		window.open('${ctx}/constructcont/${constructCont.id}/view?notback=true','_blank');
			return false;
    	})
		
		$('#tabmain tr').find('td:eq(0)').css("text-align","right");
    })
    
    
    </script>
</head>

<body>
	<h2>施工签证单</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<table id="tabmain">
		<tr>
			<td>编号：</td><td>${constructCert.no}</td>
		</tr>
		<tr>
			<td>项目：</td><td>${constructCert.constructKey.project.name}</td>
		</tr>
		<tr>
			<td>项目负责人：</td><td>${constructCert.leader.name}</td>
		</tr>
		<tr>
			<td>施工联系单：</td>
			<td>
				${constructCont.no}<input type="button" id="btn_cont" value="查看施工联系单"/>
			</td>
		</tr>
		<tr>
			<td>施工承包方：</td>
			<td>${constructCont.constructKey.contract.supplier.name}</td>
		</tr>
		<tr>
			<td style="vertical-align: top">原因：</td><td>${constructCert.reason}</td>
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
			<td>签发人：</td><td>${constructCert.signer.name}</td>
		</tr>
		<tr>
			<td>签发日期：</td><td><fmt:formatDate value="${constructCert.signdate}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
		</tr>
		
		<tr>
			<td>结算人：</td>
			<td>
			${constructCert.settler.name}
			</td>
		</tr>
		<tr>
			<td>结算日期：</td>
			<td>
				<fmt:formatDate value="${constructCert.settledate}" pattern="yyyy-MM-dd"/>
			</td>
		</tr>
	</table>
		
		<br>
		签证项目
		<table border="1" id="tabitem">
		<thead>
			<tr>
				<th>序号</th><th>内容</th><th>结算单价</th><th>结算数量</th><th>单位</th><th>结算价格	</th>
				<th>签证依据	</th>
				<th>监理意见	</th>
				<th>现场管理人员意见	</th>
				<th>项目负责人意见	</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${constructCert.constructCertItems}" var="item">
				<tr>
					<td>${item.sn}</td>
					<td>${item.content}</td>
					<td>${item.price}</td>
					<td>${item.numb}</td>
					<td>${item.unit}</td>
					<td>${item.amount}</td>
					<td>
						<c:choose>
							<c:when test="${not empty item.imgPath}">
								 <a href="${ctx}/img/${item.imgPath}" target="_blank" >示例图片</a>
							</c:when>
							<c:otherwise>
									----
							</c:otherwise>
						</c:choose>
					</td>
					<td>
						${item.superOpinion.name}
					</td>
					<td>
						${item.manageOpinion.name}
					</td>
					<td>
						${item.leaderOpinion.name}
					</td>
				</tr>
			</c:forEach>
		</tbody>
		</table>

		<br>
		<input type="button" value="返回"  id="btn_back">

</body>
</html>
