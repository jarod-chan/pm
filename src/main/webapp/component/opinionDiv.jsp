<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty opinions}">			

<script type="text/javascript">
		$(function(){
          
          $(".btn_opinionitem").hover(
				function () {
					this.style.cursor='pointer';
    			    $(this).css("background-color","#1E8EFF");
    			 },
    			function () {
    				$(this).css("background-color","#FFFFFF");
    			}
    	  ).click(function(){
    		  var nextDiv= $(this).parent().next();
    		  nextDiv.toggle();
    		  $(this).text(nextDiv.is(":hidden")?"+":"-");
    	  })
		});
</script>
<style type="text/css">
	span.btn_opinionitem,span.btn_opinionitem_empty {
		width:15px;
		text-align:center;
		display:-moz-inline-box;
		display:inline-block;
	}
</style>

<div class="chk_opinions" style="margin-top: 10px;">
	流程历史审批意见
	<div>
		<c:forEach var="opinion" items="${opinions}" varStatus="status">
			<div>
				${status.count}.
				<c:if test="${not empty opinion.opinionItems}">[<span class="btn_opinionitem">+</span>]</c:if>
				<span style="font-weight: bold">${opinion.userName}</span>执行任务<span style="font-weight: bold">${opinion.taskName}</span>【${opinion.result.name}】<c:if test="${not empty opinion.content}">:${opinion.content}</c:if>
			</div>
			<div style="display: none;margin-left: 19px;">
				<c:forEach var="item" items="${opinion.opinionItems}">
					[<span class="btn_opinionitem_empty">${item.itemSn}</span>]
					<span style="font-weight: bold">序号${item.itemSn}</span>内容【${item.result.name}】<c:if test="${not empty item.content}">:${item.content}</c:if><br/>
				</c:forEach>
			</div>
		</c:forEach>
	</div>
</div>
</c:if>





