<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html>
<head>
	<%@ include file="/common/setting.jsp" %>
	<%@ include file="/common/meta.jsp" %>
	<%@ include file="/common/include.jsp" %>	
	<%@ include file="/common/jqui.jsp" %>	
	<%@ include file="/common/jqui2.jsp" %>	
   
    <script type="text/javascript">
    $(function() {
		$("#btn_save").click(function(){
			var actionFrom=$("form");
			var oldAction=actionFrom.attr("action");
			actionFrom.attr("action",oldAction+"/save").submit();
		});
		
		$("#btn_back").click(function(){
			window.open('${ctx}/contractmeter/list','_self');
			return false;
		})
		
		$('#tabmain tr').find('td:eq(0)').css("text-align","right");
		
		$(".datePK").datepicker();
    });
    </script>
    
	<style type="text/css">
	 .uploadify{
	 	float: left;
	 }
	 
	 .fl_span{
	 	padding-right: 5px;
	 }
	 
	 .fl_name {
	 	background-color: #5BADFF;
	 }
	 
	 .fl_name a {
	    background-color: #5BADFF;  
	    text-decoration: none;
	    color:#000000;
	    white-space: nowrap;
	}
	.fl_name a:hover {
	    background-color:  #FF8080;
	}
	.fl_name a:visited {
	    color: #000000;
	}
	 
	 .fl_remove a {
	    background-color: #FECF78;  
	    text-decoration: none;
	    color:#000000;
	    white-space: nowrap;
	}
	.fl_remove a:hover {
	    background-color:  #FF8080;
	}
	.fl_remove a:visited {
	    color: #000000;
	}
	</style>
    
    <script type="text/javascript">
		$(function() {
		    $("#file_upload").uploadify({
		    	buttonText    : '上传文件',
		        swf           : '${ctx}/plu/uploadify/uploadify.swf',
		        uploader      : '${ctx}/uploadify/upload',
		        queueID : 'uploadify_queue',
		        removeTimeout : 0,
		        height        : 15,
		        width         : 80,
		        //返回一个错误，选择文件的时候触发
		        onSelectError:function(file, errorCode, errorMsg){
		            switch(errorCode) {
		                case -100:
		                    alert("上传的文件数量已经超出系统限制的"+$('#file_upload').uploadify('settings','queueSizeLimit')+"个文件！");
		                    break;
		                case -110:
		                    alert("文件 ["+file.name+"] 大小超出系统限制的"+$('#file_upload').uploadify('settings','fileSizeLimit')+"大小！");
		                    break;
		                case -120:
		                    alert("文件 ["+file.name+"] 大小异常！");
		                    break;
		                case -130:
		                    alert("文件 ["+file.name+"] 类型不正确！");
		                    break;
		            }
		        },
		        //检测FLASH失败调用
		        onFallback:function(){
		            alert("您未安装FLASH控件，无法上传文件！请安装FLASH控件后再试。");
		        },
		        //上传到服务器，服务器返回相应信息到data里
		        onUploadSuccess:function(file, data, response){
		          	$("#uploadify_result").append(mkSpan(file.name,data));
		        }
		    });
		    
 		    var fileSpan=$("<span class='fl_span'></span");
	    	$("<span class='fl_name'></span>").appendTo(fileSpan);
	    	$("<span class='fl_remove'><a href='javascript:void(0);''>--</a></span>").appendTo(fileSpan);
	    	$("<span class='fl_id'><input type='hidden' name='filestore_id' /></span>").appendTo(fileSpan);
		    
		    function mkSpan(filename,file_id){
		    	var newspan=fileSpan.clone();
		    	newspan.find(".fl_name").html(filename).end()
		    		.find(".fl_remove a").click(function(){
		    			$(this).parent().parent().remove();
		    		}).end()
		    		.find(".fl_id input").val(file_id).end();
		    	return newspan;
		    } 
		    
		    $(".fl_span").find(".fl_remove a").click(function(){
	   			$(this).parent().parent().remove();
	   		});
		   
		});
	 	 
	</script>
	
	
</head>

<body>
	<h2>材料采购合同</h2>
	<%@ include file="/common/message.jsp" %>	
	
	<form action="${ctx}/contractmeter" method="post">
	
	<input type="hidden" name="id" value="${contractMeter.id}"/>	
	
	<table id="tabmain">
		
		<tr><td>
		编号：</td><td>
		<c:set var="parma_no" value="${contractMeter.no}" />
		<%@ include file="/component/noShow.jsp" %>	
		</td></tr>
		
		<tr><td>
		合同名称：</td><td>
		<input type="text" name="name" value="${contractMeter.name}"/>
		</td></tr>
		
		<tr>
			<td>采购申请单：</td> 
			<td>
				<span id="spanReq">${purchaseReq.no}</span><input type="hidden" name="purchaseKey.id" value="${purchaseReq.purchaseKey.id}">
				<input type="button" id="btn_selreq" value="选择" />
			</td>
		</tr>
		<tr>
			<td style="vertical-align: top">关联采购明细：</td>
			<td>
				<table id="purchaseReqItem" border="1">
				<thead>
					<tr>
						<th>勾选</th><th>序号</th><th>材料名称</th><th>型号规格和技术指标</th><th>执行结果</th>
					</tr>
				</thead>
				<tbody>
				</tbody>
				</table>
			</td>
		</tr>
		
		
		<tr><td>
		供应商：</td><td>
		<select name="supplier.id">
			<c:forEach var="supplier" items="${supplierList}">
				<option value="${supplier.id}" <c:if test="${supplier.id== contractMeter.supplier.id}">selected="true"</c:if> >${supplier.name}</option>
			</c:forEach>
		</select>
		<tr><td>
		
		<tr><td>
		合同状态：</td><td>
		<select name="state">
			<c:forEach var="contractState" items="${contractStateList}">
				<option value="${contractState}" <c:if test="${contractState== contractMeter.state}">selected="true"</c:if> >${contractState.name}</option>
			</c:forEach>
		</select>
		<tr><td>
		
		<tr><td>
		专业分类：</td><td>
		<select name="specialty">
			<c:forEach var="specialty" items="${specialtyList}">
				<option value="${specialty}" <c:if test="${specialty== contractMeter.specialty}">selected="true"</c:if> >${specialty.name}</option>
			</c:forEach>
		</select>
		<tr><td>
		
		<tr><td>
		签订日期：</td><td>
		<input type="text" name="signDate" class="datePK" value="${contractMeter.signDate}"/>
		<td></tr>
		
		<tr><td>
		交付日期：</td><td>
		<input type="text" name="delvDate" class="datePK" value="${contractMeter.delvDate}"/>
		<td></tr>
		
		<tr><td>
		合同金额：</td><td>
		<input type="text" name="contractAmt"  value="${contractMeter.contractAmt}"/>
		<td></tr>
		
		<tr><td>
		首付比例：</td><td>
		<input type="text" name="dpscale"  value="${contractMeter.dpscale}"/>
		<td></tr>
		
		<tr><td>
		结算金额：</td><td>
		<input type="text" name="finalAmt"  value="${contractMeter.finalAmt}"/>
		<td></tr>
		
		<tr><td>
		来源形式：</td><td>
		<input type="text" name="origins"  value="${contractMeter.origins}"/>
		<td></tr>
		
		<tr><td>
		责任部门：</td><td>
		<input type="text" name="dept"  value="${contractMeter.dept}"/>
		<td></tr>
		
		<tr><td>
		风险等级：</td><td>
		<select name="riskLevel">
			<c:forEach var="contractRisk" items="${contractRiskList}">
				<option value="${contractRisk}" <c:if test="${contractRisk== contractMeter.riskLevel}">selected="true"</c:if> >${contractRisk.name}</option>
			</c:forEach>
		</select>
		<tr><td>
		
		<tr><td>
		风险提示：</td><td>
		<input type="text" name="riskPrompt"  value="${contractMeter.riskPrompt}"/>
		<td></tr>
		
		<tr><td>
		评审结论：</td><td>
		<input type="text" name="conclusion"  value="${contractMeter.conclusion}"/>
		<td></tr>
		
		<tr><td>
		签订责任人：</td><td>
		
		<select name="leader.key">
			<c:forEach var="user" items="${userList}">
				<option value="${user.key}" <c:if test="${user.key== contractMeter.leader.key}">selected="true"</c:if> >${user.name}</option>
			</c:forEach>
		</select>
		<td></tr>
		
		<tr><td>
		总份数：</td><td>
		<input type="text" name="totalCopies"  value="${contractMeter.totalCopies}"/>
		<td></tr>
		
		<tr><td>
		留存份数：</td><td>
		<input type="text" name="saveCopies"  value="${contractMeter.saveCopies}"/>
		<td></tr>
		
		<tr><td>
		合同文件：<br><br>
		</td><td>
			<input id="file_upload" type="button" >
		</td>
		</tr>
		<tr>
			<td></td>
			<td>
				<div style="clear: both;"  id="uploadify_result">
					<c:forEach var="filestore" items="${filestores}">
						<span class="fl_span"> 
							<span class="fl_name"><a href="${ctx}/uploadify/filestore/${filestore.id}">${filestore.filename}.${filestore.suffix}</a></span><span class="fl_remove"><a href="javascript:void(0);">--</a></span>
							<span class="fl_id"><input type="hidden" name="filestore_id" value="${filestore.id}"></span>
						</span>
					</c:forEach>
				</div>
				<div id="uploadify_queue">
				</div>
			</td>
		</tr>
		
		
	    </table>
		<br>
		<input type="button" value="保存"  id="btn_save">
		<input type="button" value="返回"  id="btn_back">
		
	</form>
	
	<c:set var="certid" value="${contractMeter.id}" />
	<c:set var="uptype" value="pm_contractmeter" />
	<%@ include file="/component/selPurchaseReq.jsp" %>	

</body>
</html>
