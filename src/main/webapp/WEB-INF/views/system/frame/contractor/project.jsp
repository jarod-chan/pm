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
    	myLayout=$('body').layout({ 
    		applyDemoStyles: true, 
    		north__size:65,//pane的大小
    		north__closable: false,
    		west__size:200
    	}); 
    	
    	var projectId=${project.id};
    	$("#mainFrame").attr("src","${ctx}/"+projectId+"/contractor/${supplier.id}/projectinfo");
    	
       	$('#btn_task').click(function(){
    		$('<form/>',{action:'${ctx}/fm/contractor/task',method:'post'})
    		.append($('<input/>',{type:'hidden',name:'projectId',value: projectId}))
			.appendTo($("body"))
			.submit();
    		return false;
    	})
    	
    	
    	
    	$("#leftmenu .no_show").click(function(){
    	 	projectId=$(this).metadata().id;
    	 	$("#mainFrame").attr("src","${ctx}/"+projectId+"/contractor/${supplier.id}/projectinfo");
    	 	
    	});

    });
    </script>
</head>

<body>
	<div class="ui-layout-north" >
		<div style="width: 1024px;">
			<div style="width: 50%;float: left;"><%@ include file="../title.jsp" %></div>
			<div style="width: 50%;float: left; text-align: right;">
				<div style="margin-bottom: 3px;">供应商:${supplier.name}&nbsp;&nbsp;用户:${user.name}<%@ include file="/component/logout.jsp" %></div>
				<div>
				<input type="button" id="btn_task" value="我的任务" >
				<input type="button" id="btn_project" value="我的项目" disabled="disabled">
				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
	<div class="ui-layout-west">
		<div id="leftmenu">
			<c:forEach var="project" items="${projectList}">
				<a class="no_show {id: '${project.id}'} "  href="javascript:void(0);"   >${project.name}</a><br>
			</c:forEach>
		</div>
	</div>

	
	<iframe id="mainFrame" name="mainFrame" class="ui-layout-center" width="100%" height="600" frameborder="0" scrolling="auto" ></iframe> 
		
	
	
	
</body>
</html>
