<%@ page language="java" pageEncoding="UTF-8"%>
用户:${user.name}&nbsp;&nbsp;<span  id="btn_logout" class="span_btn_def">退出</span>
<script type="text/javascript">
 $(function() {
 	 $("#btn_logout").click(function(){
  		$('<form/>',{action:'${ctx}/logout',method:'post'})
		  .appendTo($("body"))
		.submit();
		return false;
  	  })
 })
</script>






