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
    		north__size:95,//pane的大小
    		north__closable: false,
    		west__size:200
    	});
    	
    	var menuIdx=${menuIdx};
    	
    	$("#menudiv ul li ul li").click(function(){
    		tx1=$(this).text();
    		tx2=$(this).parent().next().text();
    		var link=$(this).find("a");
    		menuIdx=$("#menudiv a.no_show").index(link);
    		//console.info(menuIdx);
    		$("#menu-nav").html(tx2+"->"+tx1);
    	})
    	
    	if(menuIdx>0){
    		li=$("#menudiv .no_show").eq(${menuIdx}).parent()
    	}else{
    		li=$(".firstSelect").eq(0);
    	}
    	
    	li.trigger("click");
    	var src=li.find("a").attr("href");
    	$("#mainFrame").attr("src",src);
     	
    	
       	$('#btn_task').click(function(){
       		$('<form/>',{action:'${ctx}/fm/company/task',method:'post'})
    		.append($('<input/>',{type:'hidden',name:'menuIdx',value:menuIdx}))
			.appendTo($("body"))
			.submit();
    		return false;
    	})
    	
    	$('#sel_project').change(function(){
        	$('<form/>',{action:'${ctx}/fm/company/changeProject',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'projectId',value:$(this).val()}))
	    		.append($('<input/>',{type:'hidden',name:'menuIdx',value:menuIdx}))
				.appendTo($("body"))
			.submit();
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
				<div style="margin-bottom: 3px;">用户:${user.name}<%@ include file="/component/logout.jsp" %></div>
				<div>
				<input type="button" id="btn_task" value="我的任务" >
				<input type="button" id="btn_project" value="我的项目" disabled="disabled">:<select id="sel_project">
					<c:forEach var="item" items="${projectList}">
						<option value="${item.id}" <c:if test="${item.id==project.id}">selected="true"</c:if> >${item.name}</option>
					</c:forEach>
				</select>
				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
		<div style="width: 1024px;">
			<div style="width: 50%;float: left; ">
				<div id="menu-nav" style="margin-top: 15px;">项目首页</div>
			</div>
			<div id="menudiv" style="width: 50%;float: left; text-align: right;" >
					<ul style="float: right" onmouseover="myLayout.allowOverflow(this)" onmouseout="myLayout.resetOverflow('center')">
						<li>
							<ul>
								<li><a href="${ctx}/project/list" class="no_show" target="mainFrame"  >项目信息</a></li>
								<li><a href="${ctx}/spmember/list" class="no_show" target="mainFrame"  >供应商用户</a></li>
								<li><a href="${ctx}/user/list" class="no_show" target="mainFrame"  >系统用户</a></li>
							</ul>
							<span> 系统管理</span>
						</li>
					</ul>
			
					<ul style="float: right;width: 13ex;" onmouseover="myLayout.allowOverflow(this)" onmouseout="myLayout.resetOverflow('center')">
						<li>
							<ul>
								<li><a href="${ctx}/supplier/contra/list" class="no_show" target="mainFrame" >内部承包人</a></li>
								<li><a href="${ctx}/supplier/construct/list" class="no_show" target="mainFrame" >工程服务商</a></li>
								<li><a href="${ctx}/supplier/meter/list" class="no_show" target="mainFrame" >材料供应商</a></li>
								<li><a href="${ctx}/supplier/design/list" class="no_show" target="mainFrame" >设计服务商</a></li>
							</ul>
							<span> 供应商管理</span>
						</li>
					</ul>
					
					<ul style="float: right;width: 15ex;" onmouseover="myLayout.allowOverflow(this)" onmouseout="myLayout.resetOverflow('center')">
						<li>
							<ul>
								<li><a href="${ctx}/${project.id}/contract/construct/list" class="no_show" target="mainFrame" >施工服务合同</a></li>
								<li><a href="${ctx}/${project.id}/contractmeter/list" class="no_show" target="mainFrame" >材料采购合同</a></li>
								<li><a href="${ctx}/${project.id}/contract/design/list" class="no_show" target="mainFrame" >设计策划合同</a></li>
							</ul>
							<span>合同管理</span>
						</li>
					</ul>
					
					<ul style="float: right;width: 13ex;" onmouseover="myLayout.allowOverflow(this)" onmouseout="myLayout.resetOverflow('center')">
						<li>
							<ul>
								<li class="firstSelect"><a href="${ctx}/${project.id}/constructcont/list" class="no_show" target="mainFrame" >施工联系单</a></li>
								<li><a href="${ctx}/${project.id}/constructcert/list" class="no_show" target="mainFrame" >工程签证单</a></li>
								<li><a href="${ctx}/${project.id}/purchasereq/list" class="no_show" target="mainFrame" >采购申请单</a></li>
								<li><a href="${ctx}/${project.id}/purchasecert/list" class="no_show" target="mainFrame" >价格确认单</a></li>
								<li><a href="${ctx}/${project.id}/designnoti/list" class="no_show" target="mainFrame" >问题通知书</a></li>
							</ul>
							<span>项目跟踪</span>
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
		开工日期：<br>
		状态：${project.state.name}<br>
		联系单数：N/A<br>
		签证单数：N/A<br>
		项目成员<br>
		<c:forEach var="item" items="${userRole}" varStatus="status">
			<c:if test="${not empty item.value.name}">${item.value.name}:</c:if>${item.key.name}<br>
		</c:forEach>
		<br>
	</div>
	
	<iframe id="mainFrame" name="mainFrame" class="ui-layout-center" width="100%" height="600" frameborder="0" scrolling="auto" ></iframe> 
		
	
	
	
</body>
</html>
