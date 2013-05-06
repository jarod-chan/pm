<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	
<style type="text/css">


	/*
	 *	Rules below are for simulated drop-down/pop-up lists
	 */

	ul {
		/* rules common to BOTH inner and outer UL */
		z-index:	100000;
		margin:		1ex 0;
		padding:	0;
		list-style:	none;
		cursor:		pointer;
		border:		1px solid Black;
		/* rules for outer UL only */
		width:		12ex;
		position:	relative;
		margin-left: 5px;
	}
	ul li {
		background-color: #EEE;
		padding: 0.15em 1em 0.3em 5px;
	}
	ul ul {
		display:	none;
		position:	absolute;
		width:		100%;
		left:		-1px;
		/* Pop-Up */
		bottom:		0;
		margin:		0;
		margin-bottom: 1.55em;
	}
	.ui-layout-north ul ul {
		/* Drop-Down */
		bottom:		auto;
		margin:		0;
		margin-top:	1.45em;
	}
	ul ul li		{ padding: 3px 1em 3px 5px; }
	ul ul li:hover	{ background-color: #1E8EFF; }
	ul li:hover ul	{ display:	block; background-color: #EEE; }
	
	
	 li .no_show{color: #000}
	 li .no_show:link { text-decoration: none;color: #000}
　　 li .no_show:active { text-decoration:none;color: #000}
　　 li .no_show:hover { text-decoration:none;color: #000} 
　　 li .no_show:visited { text-decoration: none;color: #000}

	</style>


    <script type="text/javascript">
    $(function() {
    	myLayout=$('body').layout({ 
    		applyDemoStyles: true, 
    		north__size:83,//pane的大小
    		north__closable: false,
    		west__size:200
    	});
    	
    	$("#menudiv ul li ul li").click(function(){
    		tx1=$(this).text();
    		tx2=$(this).parent().next().text();
    		$("#menu-nav").html("项目首页"+"->"+tx2+"->"+tx1);
    	})
    	
    	$("a[href='${ctx}/${target}']:eq(0)").parent().triggerHandler("click");
    	
       	$('#btn_task').click(function(){
    		window.open('${ctx}/task/first','_self');
    		return false;
    	})
    	
    	$('#sel_project').change(function(){
    		window.open('${ctx}/first/project/'+$(this).val()+'?target=first/home','_self');
    		return false;
    	});
    });
    </script>
</head>

<body>
	<div class="ui-layout-north" >
		<div style="width: 1024px;">
			<div style="width: 50%;float: left;"><div style="width:300px;font-size: 30px; ">方远房产项目管理系统</div></div>
			<div style="width: 50%;float: left; text-align: right;">
				
				<input type="button" id="btn_task" value="我的任务" >
				<input type="button" id="btn_project" value="我的项目" disabled="disabled">:<select id="sel_project">
					<c:forEach var="item" items="${projectList}">
						<option value="${item.id}" <c:if test="${item.id==project.id}">selected="true"</c:if> >${item.name}</option>
					</c:forEach>
				</select>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div style="width: 1024px;">
			<div style="width: 50%;float: left; ">
				<div id="menu-nav" style="margin-top: 15px;">项目首页</div>
			</div>
			<div style="width: 50%;float: left; text-align: right;" id="menudiv">
					<ul style="float: right" onmouseover="myLayout.allowOverflow(this)" onmouseout="myLayout.resetOverflow('center')">
						<li>
							<ul>
								<li>用户信息</li>
								<li><a href="javascript:window.open('${ctx}/login','_self');" class="no_show" >退出系统</a></li>
							</ul>
							<span> 系统管理</span>
						</li>
					</ul>
			
					<ul style="float: right" onmouseover="myLayout.allowOverflow(this)" onmouseout="myLayout.resetOverflow('center')">
						<li>
							<ul>
								<li><a href="${ctx}/supplier/list" class="no_show" target="mainFrame" >材料供应商</a></li>
								<li><a href="${ctx}/first/notfinish" class="no_show" target="mainFrame" >施工承包商</a></li>
								<li><a href="${ctx}/first/notfinish" class="no_show" target="mainFrame" >其它供应商</a></li>
							</ul>
							<span> 供应商管理</span>
						</li>
					</ul>
					
					<ul style="float: right" onmouseover="myLayout.allowOverflow(this)" onmouseout="myLayout.resetOverflow('center')">
						<li>
							<ul>
								<li><a href="${ctx}/contract/list" class="no_show" target="mainFrame" >施工合同</a></li>
								<li><a href="${ctx}/first/notfinish" class="no_show" target="mainFrame" >采购合同</a></li>
								<li><a href="${ctx}/first/notfinish" class="no_show" target="mainFrame" >其它合同</a></li>
							</ul>
							<span> 合同管理</span>
						</li>
					</ul>
					
					<ul style="float: right" onmouseover="myLayout.allowOverflow(this)" onmouseout="myLayout.resetOverflow('center')">
						<li>
							<ul>
								<li><a href="${ctx}/constructcont/list" class="no_show" target="mainFrame" >施工联系单</a></li>
								<li><a href="${ctx}/constructcert/list" class="no_show" target="mainFrame" >施工签证单</a></li>
								<li><a href="${ctx}/first/notfinish" class="no_show" target="mainFrame" >采购申请单</a></li>
								<li><a href="${ctx}/first/notfinish" class="no_show" target="mainFrame" >材料签证单</a></li>
								<li><a href="${ctx}/first/notfinish" class="no_show" target="mainFrame" >问题报告单</a></li>
								<li><a href="${ctx}/first/notfinish" class="no_show" target="mainFrame" >技术签证单</a></li>
							</ul>
							<span> 项目管理</span>
						</li>
					</ul>
					
					
					
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
	<div class="ui-layout-west">
		<h2>项目信息</h2>
		<br>
		名称：${project.name}<br>
		编号：${project.no}<br>
		负责人：${project.user.name}<br>
		开工日期：<br>
		成员：<c:forEach var="item" items="${pjmemberList}" varStatus="status">${item.user.name}<c:if test="${!status.last}">,</c:if></c:forEach>
		<br>
		状态：N/A<br>
		联系单数：N/A<br>
		签证单数：N/A<br>
	</div>
	
	<iframe id="mainFrame" name="mainFrame" class="ui-layout-center" width="100%" height="600" frameborder="0" scrolling="auto" src="${ctx}/${target}"></iframe> 
		
	
	
	
</body>
</html>
