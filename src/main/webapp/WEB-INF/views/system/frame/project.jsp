<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	
	
	<%@ include file="menucss.jsp" %>

    <script type="text/javascript">
    $(function() {
    	myLayout=$('body').layout({ 
    		applyDemoStyles: true, 
    		north__size:95,//pane的大小
    		north__closable: false,
    		west__size:200
    	});
    	
    	
    	
    	$("#menudiv ul li ul li").click(function(){
    		tx1=$(this).text();
    		tx2=$(this).parent().next().text();
    		var link=$(this).find("a");
    		var index=$("#menudiv a.no_show").index(link);
    		$.cookie('menuIdx',index,{path:'${ctx}'});
    		$("#menu-nav").html(tx2+">"+tx1);
    	})
    	
    	var menuIdx=$.cookie('menuIdx');
    	
    	if(menuIdx){
    		li=$("#menudiv .no_show").eq(menuIdx).parent()
    	}else{
    		li=$("#menudiv ul:last").find(".no_show:first").parent();
    	}
    	
    	li.trigger("click");
    	var src=li.find("a").attr("href");
    	$("#mainFrame").attr("src",src);
     	
    	
       	$('#btn_task').click(function(){
       		window.open('${ctx}/fm/task','_self');
    		return false;
    	})
    	
    	$('#sel_project').change(function(){
        	$('<form/>',{action:'${ctx}/fm/changeProject',method:'post'})
	    		.append($('<input/>',{type:'hidden',name:'projectId',value:$(this).val()}))
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
			<div style="width: 50%;float: left;"><%@ include file="title.jsp" %></div>
			<div style="width: 50%;float: left; text-align: right;">
				<div style="margin-bottom: 3px;"><%@ include file="/component/logout.jsp" %></div>
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
				
				<shiro:hasPermission name="system:menu">
				<ul style="float: right" onmouseover="myLayout.allowOverflow(this)" onmouseout="myLayout.resetOverflow('center')">
					<li>
						<ul>
							<shiro:hasPermission name="project:menu"> 
								<li><a href="${ctx}/project/list" class="no_show" target="mainFrame"  >项目信息</a></li>
							</shiro:hasPermission> 
							<shiro:hasPermission name="spmember:menu"> 
								<li><a href="${ctx}/spmember/list" class="no_show" target="mainFrame"  >供应商用户</a></li>
							</shiro:hasPermission> 
							<shiro:hasPermission name="user:menu"> 
								<li><a href="${ctx}/user/list" class="no_show" target="mainFrame"  >系统用户</a></li>
							</shiro:hasPermission> 
							<shiro:hasPermission name="user:info"> 
								<li><a href="${ctx}/user/${user.key}/info" class="no_show" target="mainFrame"  >用户信息</a></li>
							</shiro:hasPermission> 
						</ul>
						<span> 系统管理</span>
					</li>
				</ul>
				</shiro:hasPermission>
				
				
				<shiro:hasPermission name="supplier:menu">
				<ul style="float: right;width: 13ex;" onmouseover="myLayout.allowOverflow(this)" onmouseout="myLayout.resetOverflow('center')">
					<li>
						<ul>
						<shiro:hasPermission name="supplier-contra:menu"> 
							<li><a href="${ctx}/supplier/contra/list" class="no_show" target="mainFrame" >内部承包人</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="supplier-construct:menu"> 
							<li><a href="${ctx}/supplier/construct/list" class="no_show" target="mainFrame" >工程服务商</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="supplier-meter:menu"> 
							<li><a href="${ctx}/supplier/meter/list" class="no_show" target="mainFrame" >材料供应商</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="supplier-design:menu"> 	
							<li><a href="${ctx}/supplier/design/list" class="no_show" target="mainFrame" >设计服务商</a></li>
						</shiro:hasPermission>
						</ul>
						<span> 供应商管理</span>
					</li>
				</ul>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="contract:menu">
				<ul style="float: right;width: 15ex;" onmouseover="myLayout.allowOverflow(this)" onmouseout="myLayout.resetOverflow('center')">
					<li>
						<ul>
						<shiro:hasPermission name="contract-construct:menu"> 
							<li><a href="${ctx}/${project.id}/contract/construct/list" class="no_show" target="mainFrame" >施工服务合同</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="contract-meter:menu"> 
							<li><a href="${ctx}/${project.id}/contractmeter/list" class="no_show" target="mainFrame" >材料采购合同</a></li>
						</shiro:hasPermission>
						<shiro:hasPermission name="contract-design:menu"> 
							<li><a href="${ctx}/${project.id}/contract/design/list" class="no_show" target="mainFrame" >设计策划合同</a></li>
						</shiro:hasPermission>
						</ul>
						<span>合同管理</span>
					</li>
				</ul>
				</shiro:hasPermission>
				
				<shiro:hasPermission name="trace:menu">
				<ul style="float: right;width: 16ex;" onmouseover="myLayout.allowOverflow(this)" onmouseout="myLayout.resetOverflow('center')">
					<li>
						<ul>
							<shiro:hasPermission name="constructcont:menu"> 
							<li class="firstSelect"><a href="${ctx}/${project.id}/constructcont/list" class="no_show" target="mainFrame" >施工联系单</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="constructcert:menu"> 
							<li><a href="${ctx}/${project.id}/constructcert/list" class="no_show" target="mainFrame" >工程签证单</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="purchasereq:menu"> 
							<li><a href="${ctx}/${project.id}/purchasereq/list" class="no_show" target="mainFrame" >采购申请单</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="purchasecert:menu"> 
							<li><a href="${ctx}/${project.id}/purchasecert/list" class="no_show" target="mainFrame" >价格确认单</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="designnoti:menu"> 
							<li><a href="${ctx}/${project.id}/designnoti/list" class="no_show" target="mainFrame" >问题通知书</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="designcont:menu"> 
							<li><a href="${ctx}/${project.id}/designcont/list" class="no_show" target="mainFrame" >技术变更通知单</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="spconstructcont:menu">
							<li><a href="${ctx}/${project.id}/contractor/${supplier.id}/constructcont/list" class="no_show" target="mainFrame" >施工联系单</a></li>
							</shiro:hasPermission>
							<shiro:hasPermission name="spconstructcert:menu">
							<li><a href="${ctx}/${project.id}/contractor/${supplier.id}/constructcert/list" class="no_show" target="mainFrame" >工程签证单</a></li>
							</shiro:hasPermission>
						</ul>
						<span>项目跟踪</span>
					</li>
				</ul>
				</shiro:hasPermission>
				
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
	<div class="ui-layout-west">
		<h2>${project.name}</h2>
		编号：${project.no}<br>
		开工日期：<br>
		状态：${project.state.name}<br>
		<h3>项目人员组成</h3>
		<c:forEach var="item" items="${userRole}" varStatus="status">
			<c:if test="${not empty item.value.name}">${item.value.name}:</c:if>${item.key.name}<br>
		</c:forEach>
		<br>
	</div>
	
	<iframe id="mainFrame" name="mainFrame" class="ui-layout-center" width="100%" height="600" frameborder="0" scrolling="auto" ></iframe> 
		
	
	
	
</body>
</html>
