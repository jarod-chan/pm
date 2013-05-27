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
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action");
			actionFrom.attr("action",oldAction+"/save").submit();
		})
		
		$("#btn_back").click(function(){
			window.open('${ctx}/supplier/${supptype}/list','_self');
			return false;
		})
		
		$('#tabmain tr').find('td:eq(0)').css("text-align","right")
    });
    </script>
</head>

<body>
	<h2>${supptype.name}</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/supplier/${supptype}" method="post">
	
	<input type="hidden" name="id" value="${supplier.id}"/>
	
	<input type="hidden" name="type" value="${supptype}"/>
	
	<table id="tabmain">
		
		<tr><td>编号：</td><td>
		<input type="text" name="no" value="${supplier.no}"/>
		</td></tr>
		
		<tr><td>
		名称：</td><td>
		<input type="text" name="name" value="${supplier.name}"/>
		</td></tr>
		
		<tr>
			<td>
			专业分类:
			</td><td>
			<select name="creditRank" >
					<c:forEach var="creditRank" items="${creditRankList}">
						<option value="${creditRank}" <c:if test="${creditRank==supplier.creditRank}">selected="true"</c:if> >${creditRank.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		
		<tr>
			<td>
			营业执照：
			</td><td>
			<input type="text" name="busiLicense" value="${supplier.busiLicense}"/>
			</td>
		</tr>
		
		<tr>
			<td>
			企业代码：
			</td><td>
			<input type="text" name="busiCode" value="${supplier.busiCode}"/>
			</td>
		</tr>
		
		<tr>
			<td>
			公司地址：
			</td><td>
			<input type="text" name="address" value="${supplier.address}"/>
			</td>
		</tr>
		
		<tr>
			<td>
			法人代表：
			</td><td>
			<input type="text" name="lealPerson" value="${supplier.lealPerson}"/>
			</td>
		</tr>
		
		<tr>
			<td>
			公司电话：
			</td><td>
			<input type="text" name="compPhone" value="${supplier.compPhone}"/>
			</td>
		</tr>
		
		<tr>
			<td>
			联系人：
			</td><td>
			<input type="text" name="contact" value="${supplier.contact}"/>
			</td>
		</tr>
		
		<tr>
			<td>
			联系电话：
			</td><td>
			<input type="text" name="contPhone" value="${supplier.contPhone}"/>
			</td>
		</tr>
		
		<tr>
			<td>
			资金帐户：
			</td><td>
			<input type="text" name="account" value="${supplier.account}"/>
			</td>
		</tr>
		
		
	</table>
		
		<br>
		<input type="button" value="保存"  id="btn_save">
		<input type="button" value="返回"  id="btn_back">
		
	</form>
	
</body>
</html>
