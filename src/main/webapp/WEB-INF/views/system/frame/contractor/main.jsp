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
