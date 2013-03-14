<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/WEB-INF/layouts/taglib.jsp"%>

<html>
<head>

	<title>实例监控变更</title>
	
	<script>
		$(document).ready(function() {
			
			$("ul#navbar li#resource").addClass("active");
			
			$("#inputForm").validate({
				errorClass: "help-inline",
				errorElement: "span",
				highlight: function(element, errorClass, validClass) {
					$(element).closest('.control-group').addClass('error');
				},
				unhighlight: function(element, errorClass, validClass) {
					$(element).closest('.control-group').removeClass('error');
				}
			});
			
			$("#computeModalSave").click(function(){
				var ipAddress = $("#computeModal").find("input:radio:checked").closest("tr").find("td:eq(3)").text();
				$("#ipAddress").val(ipAddress);
			});
			
		});
	</script>
	
</head>

<body>
	
	<style>body{background-color: #f5f5f5;}</style>
	
	<form id="inputForm" action="${ctx}/resources/update/monitorCompute/" method="post" class="input-form form-horizontal" >
		
		<input type="hidden" name="id" value="${resources.id }">
		
		
		<fieldset>
			<legend><small>变更实例监控</small></legend>
			
			<div class="control-group">
				<label class="control-label" for="title">所属服务申请</label>
				<div class="controls">
					<p class="help-inline plain-text">${monitorCompute.apply.title}</p>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="identifier">标识符</label>
				<div class="controls">
					<p class="help-inline plain-text">${monitorCompute.identifier}</p>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="monitorMails">监控邮件列表</label>
				<div class="controls">
					<p class="help-inline plain-text">
						<c:forEach var="mail" items="${monitorCompute.apply.monitorMails }">${mail.email }<br></c:forEach>
					</p>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="monitorPhones">监控手机列表</label>
				<div class="controls">
					<p class="help-inline plain-text">
						<c:forEach var="phone" items="${monitorCompute.apply.monitorPhones }">${phone.telephone }<br></c:forEach>
					</p>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="identifier">服务器IP</label>
				<div class="controls">
					<input type="text" id="ipAddress" name="ipAddress" readonly="readonly" value="${monitorCompute.ipAddress}">
					<a class="btn" href="#computeModal" data-toggle="modal">实例相关资源</a>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="monitorPort">监控端口</label>
				<div class="controls">
					<table class="table table-bordered table-condensed"  >
						<tbody>
							<c:forEach var="item" items="${ports }">
								<tr class="clone">
									<td><input type="text" value="${item}" name="port" maxlength="45" placeholder="...监控端口"></td>
									<td><a class="btn clone">添加</a>&nbsp;<a class="btn clone disabled" >删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>	
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="monitorMaxProcess">监控进程</label>
				<div class="controls">
					<table class="table table-bordered table-condensed"  >
						<tbody>
							<c:forEach var="item" items="${processes }">
								<tr class="clone">
									<td><input type="text" value="${item}" name="process" maxlength="45" placeholder="...监控进程"></td>
									<td><a class="btn clone">添加</a>&nbsp;<a class="btn clone disabled" >删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>	
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="monitorMaxProcess">挂载路径</label>
				<div class="controls">
					<table class="table table-bordered table-condensed"  >
						<tbody>
							<c:forEach var="item" items="${mountPoints }">
								<tr class="clone">
									<td><input type="text" value="${item}"  name="mountPoint" maxlength="45" placeholder="...挂载路径"></td>
									<td><a class="btn clone">添加</a>&nbsp;<a class="btn clone disabled" >删除</a></td>
								</tr>
							</c:forEach>
						</tbody>
					</table>	
				</div>
			</div>
			
			<!-- cpu -->
			<div class="control-group threshold" id="cpu">
				<label class="control-label" for="cpu">CPU占用率</label>
				<div class="controls">
					<div class="span2">
			    		报警阀值&nbsp;
			    		<select class="input-small warn-threshold" name="cpuWarn">
							<c:forEach var="map" items="${thresholdGtMap }"><option value="${map.key }" <c:if test="${monitorCompute.cpuWarn == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
			    	<div class="span2">
			    		警告阀值&nbsp;
			    		<select class="input-small critical-threshold" name="cpuCritical">
							<c:forEach var="map" items="${thresholdGtMap }"><option value="${map.key }" <c:if test="${monitorCompute.cpuCritical == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
				</div>
			</div>
			
			<!-- Memory -->
			<div class="control-group threshold" id="memory">
				<label class="control-label" for="memory">内存占用率</label>
				<div class="controls">
					<div class="span2">
			    		报警阀值&nbsp;
			    		<select class="input-small warn-threshold" name="memoryWarn">
							<c:forEach var="map" items="${thresholdGtMap }"><option value="${map.key }" <c:if test="${monitorCompute.memoryWarn == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
			    	<div class="span2">
			    		警告阀值&nbsp;
			    		<select class="input-small critical-threshold" name="memoryCritical">
							<c:forEach var="map" items="${thresholdGtMap }"><option value="${map.key }" <c:if test="${monitorCompute.memoryCritical == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
				</div>
			</div>
			
			<!-- 网络丢包率 -->
			<div class="control-group threshold" id="pingLoss">
				<label class="control-label" for="pingLoss">网络丢包率</label>
				<div class="controls">
					<div class="span2">
			    		报警阀值&nbsp;
			    		<select class="input-small warn-threshold" name="pingLossWarn">
							<c:forEach var="map" items="${thresholdGtMap }"><option value="${map.key }" <c:if test="${monitorCompute.pingLossWarn == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
			    	<div class="span2">
			    		警告阀值&nbsp;
			    		<select class="input-small critical-threshold" name="pingLossCritical">
							<c:forEach var="map" items="${thresholdGtMap }"><option value="${map.key }" <c:if test="${monitorCompute.pingLossCritical == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
				</div>
			</div>
			
			<!-- 硬盘可用率 -->
			<div class="control-group threshold" id="disk">
				<label class="control-label" for="disk">硬盘可用率</label>
				<div class="controls">
					<div class="span2">
			    		报警阀值&nbsp;
			    		<select class="input-small warn-threshold" name="diskWarn">
							<c:forEach var="map" items="${thresholdLtMap }"><option value="${map.key }" <c:if test="${monitorCompute.diskWarn == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
			    	<div class="span2">
			    		警告阀值&nbsp;
			    		<select class="input-small critical-threshold" name="diskCritical">
							<c:forEach var="map" items="${thresholdLtMap }"><option value="${map.key }" <c:if test="${monitorCompute.diskCritical == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
				</div>
			</div>
			
			<!--  网络延时率 -->
			<div class="control-group threshold" id="pingDelay">
				<label class="control-label" for="pingDelay">网络延时率</label>
				<div class="controls">
					<div class="span2">
			    		报警阀值&nbsp;
			    		<select class="input-small warn-threshold" name="pingDelayWarn">
							<c:forEach var="map" items="${thresholdNetGtMap }"><option value="${map.key }" <c:if test="${monitorCompute.pingDelayWarn == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
			    	<div class="span2">
			    		警告阀值&nbsp;
			    		<select class="input-small critical-threshold" name="pingDelayCritical">
							<c:forEach var="map" items="${thresholdNetGtMap }"><option value="${map.key }" <c:if test="${monitorCompute.pingDelayCritical == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
				</div>
			</div>
			
    		<!-- 最大进程数 -->
			<div class="control-group threshold" id="maxProcess">
				<label class="control-label" for="maxProcess">最大进程数</label>
				<div class="controls">
					<div class="span2">
			    		报警阀值&nbsp;
			    		<select class="input-small warn-threshold" name="maxProcessWarn">
							<c:forEach var="map" items="${maxProcessMap }"><option value="${map.key }" <c:if test="${monitorCompute.maxProcessWarn == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
			    	<div class="span2">
			    		警告阀值&nbsp;
			    		<select class="input-small critical-threshold" name="maxProcessCritical">
							<c:forEach var="map" items="${maxProcessMap }"><option value="${map.key }" <c:if test="${monitorCompute.maxProcessCritical == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
				</div>
			</div>
			
    		<!-- 网卡流量 -->
			<div class="control-group threshold" id="networkFlow">
				<label class="control-label" for="networkFlow">网卡流量</label>
				<div class="controls">
					<div class="span2">
			    		报警阀值&nbsp;
			    		<select class="input-small warn-threshold" name="networkFlowWarn">
							<c:forEach var="map" items="${thresholdGtMap }"><option value="${map.key }" <c:if test="${monitorCompute.cpuWarn == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
			    	<div class="span2">
			    		警告阀值&nbsp;
			    		<select class="input-small critical-threshold" name="networkFlowCritical">
							<c:forEach var="map" items="${thresholdGtMap }"><option value="${map.key }" <c:if test="${monitorCompute.cpuCritical == map.key }">selected="selected"</c:if>>${map.value }</option></c:forEach>
						</select>
					</div>
				</div>
			</div>
			
			<hr>
			
			<div class="control-group">
				<label class="control-label" for="serviceTagId">服务标签</label>
				<div class="controls">
					<select id="serviceTagId" name="serviceTagId" class="required">
						<c:forEach var="item" items="${tags}">
							<option value="${item.id }" 
								<c:if test="${item.id == resources.serviceTag.id }">
									selected="selected"
								</c:if>
							>${item.name}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="usedby">运维人</label>
				<div class="controls">
					<select id="usedby" name="usedby" class="required">
						<c:forEach var="map" items="${assigneeMap}">
							<option value="${map.key}" 
								<c:if test="${map.key == resources.usedby }">
									selected="selected"
								</c:if>
							>${map.value}</option>
						</c:forEach>
					</select>
				</div>
			</div>
			
			<div class="control-group">
				<label class="control-label" for="changeDescription">变更描述</label>
				<div class="controls">
					<textarea rows="3" id="changeDescription" name="changeDescription" placeholder="...变更描述"
						maxlength="200" class="required">${change.description}</textarea>
				</div>
			</div>
				 
			<div class="form-actions">
				<input class="btn" type="button" value="返回" onclick="history.back()">
				<input class="btn btn-primary" type="submit" value="提交">
			</div>
			
		</fieldset>
		
	</form>
	
	<!-- 实例选择的Modal -->
	<form id="computeModalForm" action="#" >
		<div id="computeModal" class="modal container hide fade" tabindex="-1">
	
			<div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button><h4>实例</h4></div>
				
			<div class="modal-body">
				<table class="table table-striped table-bordered table-condensed">
					<thead><tr><th></th><th>实例标识符</th><th>用途信息</th><th>IP地址</th></tr></thead>
					<tbody id="resources-tbody">
						<c:forEach var="compute" items="${allComputes}">
							<tr>
								<td>
									<label><input type="radio" name="computeId" checked="checked" value="${compute.id }"></label>
								</td>
								<td>${compute.identifier}</td>
								<td>${compute.remark}</td>
								<td>${compute.innerIp}</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
				
			<div class="modal-footer">
				<button class="btn" data-dismiss="modal" aria-hidden="true">关闭</button>
				<a id="computeModalSave" href="#" class="btn btn-primary" data-dismiss="modal" >确定</a>
			</div>
		</div>
	</form><!-- 实例规格选择的Modal End -->
	
</body>
</html>
