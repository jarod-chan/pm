<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style type="text/css">
	 #file_upload{
	 	margin-bottom: 0px;
	 }
	 
	 .uploadify_unit{
	 	margin-bottom: 5px;
	 	width: 600px;
	 }
	 
	 .fl_span{
	 	float:left;
	 	display:block;
	 	margin:5px 5px  0px  0px;
	 }
	 
	 .fl_name {
	 	background-color: #C0C0C0;
	 }
	 
	 .fl_name a {
	    background-color: #C0C0C0;  
	    text-decoration: none;
	    padding:0px 5px 0px 5px;
	    color:#000000;
	    white-space: nowrap;
	}
	.fl_name a:hover {
	    background-color:  #5BADFF;
	}
	.fl_name a:visited {
	    color: #000000;
	}
	 

</style>
    	<c:if test="${not empty filestores}">
		<div class="uploadify_unit" <c:if test="${not empty upload_width}"> style="width:${upload_width}px;"</c:if> >
				<div><h4>文件列表</h4></div>
				<div id="uploadify_result">
					<c:forEach var="filestore" items="${filestores}">
						<span class="fl_span"> 
							<span class="fl_name"><a href="${ctx}/uploadify/filestore/${filestore.id}">${filestore.filename}.${filestore.suffix}</a></span>
						</span>
					</c:forEach>
				</div>
				<div id="uploadify_queue" style="clear: both;"></div>
		</div>
		</c:if>