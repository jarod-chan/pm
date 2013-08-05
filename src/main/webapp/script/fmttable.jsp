<%@ page language="java" pageEncoding="UTF-8"%>

<style type="text/css">
	.fmttable{
		border-style: none; 
	}
</style>
<script type="text/javascript">
	$(function(){
	//	$('.fmttable tr').children('td:eq(0),td:eq(2)').css("text-align","right").css("width","100px").end()
  	//	.children('td:eq(1):not([colspan]),td:eq(3)').css("width","200px").end()
  	//	.children('td[colspan=3]').css("width","500px");
	//	$('.fmttable .longtd').css("width","700px");
	//	$('.fmttable>tr>td:even').css("text-align","right").css("width","100px");
	//	$('.fmttable>tr>td:odd:not([colspan])').css("width","200px");
 		$('.fmttable>tbody>tr').each(function(){
			$(this).children('td:eq(0),td:eq(2)').css("text-align","right").css("width","100px").end()
		  		.children('td:eq(1):not([colspan]),td:eq(3)').css("width","200px").css("background-color","#DDDDDD").end()
		  		.children('td[colspan=3]').css("width","500px").css("background-color","#DDDDDD");
		}) 
	
	})
</script>


