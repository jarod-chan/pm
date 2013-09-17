<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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
    		north__closable: false
    	});
    	
    	
    	
    	$("#menudiv ul li ul li").click(function(){
    		tx1=$(this).text();
    		tx2=$(this).parent().next().text();
    		var link=$(this).find("a");
    		var index=$("#menudiv a.no_show").index(link);
    		$.cookie('baseIdx',index,{path:'${ctx}'});
    		$("#menu-nav").html(tx2+"->"+tx1);
    	})
    	
    	var menuIdx=$.cookie('baseIdx');
    	
    	if(menuIdx){
    		li=$("#menudiv .no_show").eq(menuIdx).parent()
    	}else{
    		li=$(".firstSelect").eq(0);
    	}
    	
    	li.trigger("click");
    	var src=li.find("a").attr("href");
    	$("#mainFrame").attr("src",src);
    	
    	console.info(menuIdx);
     	
    	$('#btn_project').click(function(){
    		window.open('${ctx}/fm/company/project','_self');
    		return false;
    	});

       	$('#btn_task').click(function(){
       		window.open('${ctx}/fm/company/task','_self');
    		return false;
    	})
    	
    });
    </script>
</head>

<body>
	<div class="ui-layout-north" >
		<div style="width: 1024px;">
			<div style="width: 50%;float: left;"><%@ include file="../title.jsp" %></div>
			<div style="width: 50%;float: left; text-align: right;">
				<div style="margin-bottom: 3px;">用户:${user.name}<%@ include file="/component/logout.jsp" %></div>
				<div>
				<input type="button" id="btn_task" value="我的任务" >
				<c:if test="${not empty project}">
				<input type="button" id="btn_project" value="我的项目">
				</c:if>
				<input type="button" id="btn_base" value="基础信息"  disabled="disabled" >
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
							<li class="firstSelect"><a href="${ctx}/supplier/contra/list" class="no_show" target="mainFrame" >内部承包人</a></li>
							<li><a href="${ctx}/supplier/construct/list" class="no_show" target="mainFrame" >工程服务商</a></li>
							<li><a href="${ctx}/supplier/meter/list" class="no_show" target="mainFrame" >材料供应商</a></li>
							<li><a href="${ctx}/supplier/design/list" class="no_show" target="mainFrame" >设计服务商</a></li>
						</ul>
						<span> 供应商管理</span>
					</li>
				</ul>
					
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
	
	<iframe id="mainFrame" name="mainFrame" class="ui-layout-center" width="100%" height="600" frameborder="0" scrolling="auto" ></iframe> 
		
	
	
	
</body>
</html>
