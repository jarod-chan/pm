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
    	
       	$('#btn_project').click(function(){
    		window.open('${ctx}/fm/project','_self');
    		return false;
    	});
       	
    });
    </script>
</head>

<body>
	<div class="ui-layout-north" >
		<div style="width: 1024px;">
			<div style="width: 50%;float: left;"><%@ include file="title.jsp" %>	</div>
			<div style="width: 50%;float: left; text-align: right;">
				<div style="margin-bottom: 3px;"><%@ include file="/component/logout.jsp" %></div>
				<div>
					<input type="button" id="btn_task" value="我的任务" disabled="disabled">
					<c:if test="${not empty project}">
					<input type="button" id="btn_project" value="我的项目" >:<select disabled="disabled">
							<option >${project.name}</option>
					</select>
					</c:if>
				</div>
			</div>
			<div style="clear: both;"></div>
		</div>
	</div>
	<div class="ui-layout-west">
		<a href="${ctx}/task/list" class="no_show" target="mainFrame" >我的任务</a><br>
	</div>
	
	<iframe id="mainFrame" name="mainFrame" class="ui-layout-center" width="100%" height="600" frameborder="0" scrolling="auto" src="${ctx}/task/list"></iframe> 
		
	
	
	
</body>
</html>
