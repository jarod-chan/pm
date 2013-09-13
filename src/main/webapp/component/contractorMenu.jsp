<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="f" uri="http://java.sun.com/jsp/jstl/fmt" %>

	<style type="text/css">
	
	#rightmenu ul {
		list-style: none;
		padding: 0px;
		margin: 0px
	}
	
	#rightmenu ul li a
	{
	  color:#000000;
	  background:#9c9c9c;
	  border:1px solid #cccccc;
	  display:block;
	  width:100px;
	  text-align:center;
	  text-decoration:none;
	  margin-right: 2px;
	  float: left;
	  border-top: 5px solid #cccccc; 
	  border-bottom: 0px;
	}
	
	#rightmenu  ul li a:hover
	{
	    color:#000000;
	  background:#FFEFC6;
	  font-weight:normal;
	  text-decoration:none;
	  display:block;
	  width:100px;
	  text-align:center;
	}
	
	#rightmenu ul li .selected
	{
		background-color: #FFF;
		border-color:#F89406;
		border-top: 5px solid #F89406; 
	}
	
	</style>

	<div id="rightmenu">
		<ul>
			<li>
				<a href="${ctx}/${projectId}/contractor/${supplierId}/projectinfo" <c:if test="${selectmenu=='projectinfo'}">class="selected"</c:if> >项目首页</a>
				<a href="${ctx}/${projectId}/contractor/${supplierId}/constructcont/list" <c:if test="${selectmenu=='constructcont'}">class="selected"</c:if> >施工联系单</a>
				<a href="${ctx}/${projectId}/contractor/${supplierId}/constructcert/list" <c:if test="${selectmenu=='constructcert'}">class="selected"</c:if> >工程签证单</a>
			</li>
		</ul>
	</div>
	
	<div style="clear: both;"></div>
	


