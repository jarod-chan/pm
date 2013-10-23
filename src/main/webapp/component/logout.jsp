<%@ page language="java" pageEncoding="UTF-8"%>
&nbsp;&nbsp;<span  id="btn_logout" class="span_btn" >退出</span>
<script type="text/javascript">
 $(function() {
 	 $("#btn_logout").click(function(){
  		$('<form/>',{action:'${ctx}/login/out',method:'post'})
		  .appendTo($("body"))
		.submit();
		return false;
  	  })
 })
</script>






