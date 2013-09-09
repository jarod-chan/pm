<%@ page language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

	<script type="text/javascript">
		$(function(){
          
          $('#tabcheck tr').find('td:eq(0)').css({"text-align":"right","vertical-align": "top"});
    	  
	 	  $("#btn_back").click(function(){
			window.open('${ctx}/task/list','_self');
			return false;
		  })
			
		  $("#btn_commit").click(function(){
				var actionFrom=$("form");
				var oldAction=actionFrom.attr("action"); 
				actionFrom.attr("action",oldAction+"/commit").submit();
		  })
		});
	</script>
	
	<form action="${ctx}/task/check" method="post">
		<input type="hidden" name="taskId" value="${task.id}"/>
		<input type="hidden" name="busiCode" value="${busiCode}"/>
		<input type="hidden" name="businessId" value="${parma_businessId}"/>
		<input type="hidden" name="ignoreItem" id="ignoreItem" value="true"/>
		
		<table id="tabcheck" style="margin-top: 10px;">
			<tbody>
				<tr>
					<td>审批结果：</td>
					<td>
						<select name="result">
							<c:forEach var="result" items="${resultList}">
								<option value="${result}">${result.name}</option>
							</c:forEach>
						</select>
					</td>
				</tr>
				<tr>
					<td>审批意见：</td>
					<td>
						<textarea name="content" style="height:180px;width: 470px; "></textarea>
					</td>
				</tr>
               <c:if test="${not empty  parma_itemLength}">
				<script type="text/javascript">
				$(function(){
				  
					  var tarInput=$(".opionionItem_itemId");
			          $(".datacls").each(function(index){
			        	  tarInput.eq(index).val($(this).metadata().itemId);
			          });
			          
			          $("#btn_opinionitem").hover(
							function () {
								this.style.cursor='pointer';
			    			    $(this).css("background-color","#1E8EFF");
			    			 },
			    			function () {
			    				$(this).css("background-color","#FFFFFF");
			    			}
			    	  ).click(function(){
			    		  $("#spn_opinionitem").toggle();
			    		  $("#tab_opinionitem").toggle();
			    		  $("#ignoreItem").val($('#tab_opinionitem').is(":hidden"));
			    	  })
			    	  
				});
				</script>
				
				
				<tr>
					<td><span id="btn_opinionitem" >分项意见：</span></td>
					<td>
							<span id="spn_opinionitem">&lt;-点击右侧文字输入分项意见,再次点击取消</span>
							<table id="tab_opinionitem" border="1" style="display: none;">
								<thead>
									<tr>
										<th>序号</th>
										<th>意见</th>
										<th>结果</th>
									</tr>
								</thead>
								<tbody>
									<c:forEach begin="0" step="1" end="${parma_itemLength-1}" varStatus="status">
										<tr>
											<td>
												${status.count}
												<input class="opionionItem_itemId" name="opinionItems[${status.index}].itemId" type="hidden">
												<input name="opinionItems[${status.index}].itemSn" value="${status.count}" type="hidden">
											</td>
											<td>
												<select name="opinionItems[${status.index}].result">
													<c:forEach var="result" items="${resultList}">
														<option value="${result}">${result.name}</option>
													</c:forEach>
												</select>
											</td>
											<td>
												<input name="opinionItems[${status.index}].content" type="text" style="width: 350px;">
											</td>
										</tr>
									</c:forEach>
								</tbody>
							</table>
					</td>
				</tr>
				</c:if>
			</tbody>
		</table>
		<br>
		<input type="button" value="提交流程"  id="btn_commit">
		<input type="button" value="返回"  id="btn_back">
		
	</form>






