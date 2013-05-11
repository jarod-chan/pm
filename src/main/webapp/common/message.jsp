<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty message}">
	<script type="text/javascript">
	$(function(){
		$("#message").fadeIn(1000);	
    	$("#message .close").click(function(){$("#message").hide();});
   	});
	</script>
	<div id="message" class="alert alert-${message.level}">
		<button data-dismiss="alert" class="close" type="button">×</button>
		<strong>${message.level.name}：</strong>${message.message}
	</div>
</c:if>

