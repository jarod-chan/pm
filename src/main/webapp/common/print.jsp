<%@ page language="java" pageEncoding="UTF-8"%>

<script src="${ctx}/js/jquery.js" type="text/javascript"></script>

<script type="text/javascript">
	$(function(){
 		$('.pt_table>tbody>tr').each(function(){
			$(this).children('td:eq(0),td:eq(2)').css({"text-align":"right","width":"105px","padding-right":"5px"}).end()
		  		.children('td:eq(1):not([colspan]),td:eq(3)').css({"width":"215px","padding":"2px 5px 2px 5px"}).end()
		  		.children('td[colspan=3]').css("width","535px");
		}) 
	
	})
</script>


<style type="text/css">
/* body默认边距为左20px */
body {
    font-family:'Verdana','宋体';
    font-size:15px;
    margin:0 0 0 20px;
}

/*ie6 默认form 有距离*/
form {
	margin-top: 0px;
	margin-bottom: 0px;
}

h2,h3{
	margin:0 auto;
}



.pt_container {
	position: absolute;
	left: 50%;
	top: 0px;
	margin-left: -320px;
	width: 640px;
}

.c_title{
	text-align: center;
	font-size: 20px;
	padding-top:20px;
	padding-bottom:20px;
}

.c_no{
	text-align: right;
	padding-top:5px;
	padding-bottom:5px;
}

.c_no span{
	margin-left: 50px;
}

.c_table table.pt_table{
	border: 1px solid #000000;
	border-collapse: collapse;
	width: 100%
}

.c_table table.pt_table>thead>tr>th {
    background-color: #000000;
    border: 1px solid #AAAAAA;
    vertical-align: baseline;
}
.c_table table.pt_table>tbody>tr>td {
    border: 1px solid #000000;
}
.c_table table.item_table{
	border: 1px solid #000000;
	border-collapse: collapse;
	width: 100%
}
.c_table table.item_table>thead>tr>th {
    background-color: #FFFFFF;
    border: 1px solid #000000;
    vertical-align: baseline;
}
.c_table table.item_table>tbody>tr>td {
    border: 1px solid #000000;
}

.c_table  table.pt_table>tbody>tr>.td_text{
	height: 35px;
	vertical-align: top;
	padding: 2px 5px 2px 5px;
}

.c_table  table.pt_table>tbody>tr>.td_item{
	height: 350px;
	vertical-align: top;
}
</style>

