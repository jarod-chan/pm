<%@ page language="java" pageEncoding="UTF-8"%>
&nbsp;&nbsp;<span  id="btn_logout" >退出</span>
<style type="text/css">
 #btn_logout{
 	padding: 0px 5px 0px 5px;
 	background: #C9C9C9;
 	color: #000000;
 }
 #btn_logout.mouseon{
 	cursor: pointer;
 	background-color: #1E8EFF;
 	color: #FFFFFF;
 }
</style>
<script type="text/javascript">
 $(function() {
 	 $("#btn_logout").hover(
		function () {
			$(this).addClass("mouseon");
  			 },
  			function () {
  				$(this).removeClass("mouseon")
  			}
  	  ).click(function(){
  		$('<form/>',{action:'${ctx}/login/out',method:'post'})
		  .appendTo($("body"))
		.submit();
		return false;
  	  })
 })
</script>






