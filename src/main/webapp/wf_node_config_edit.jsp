<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String nodeConfigId = (String) request.getParameter("nodeConfigId");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${_theme_}css/style.css" />
<link rel="stylesheet" type="text/css"
	href="js/artDialog/skins/opera.css" />
<link rel="stylesheet" type="text/css"
	href="js/ztree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"
	href="js/easyPage/css/skins/jquery.easypage.css" />
</head>
<body>
	<div class="rightinfo">
		<div class="content">
			<div class="formbody">
				<div class="formtitle" style="height: 45px;">
					<span>修改流程节点配置时限信息</span>
				</div>
				<form id="addDataForm" method="post">
					<input id="nodeConfigId" name="nodeConfigEntity.id" class="dfinput"
						type="hidden" value="<%=nodeConfigId%>" /> <input id="wfkey"
						name="nodeConfigEntity.wfkey" class="dfinput" type="hidden" />
					<table class="tablelist" id="tablelist">
						<tr>
							<th style="height: 32px; border: 1px solid gray;">节点id:</th>
							<td style="height: 32px; border: 1px solid gray;"><input
								id="nodeid" name="nodeConfigEntity.nodeid" class="dfinput"
								readonly="readonly" /></td>
						</tr>
						<tr>
							<th style="height: 32px; border: 1px solid gray;">节点名称:</th>
							<td style="height: 32px; border: 1px solid gray;"><input
								id="nodeName" name="nodeConfigEntity.nodeName" class="dfinput"
								readonly="readonly" /></td>
						</tr>
						<tr>
							<th style="height: 32px; border: 1px solid gray;">时限天数:</th>
							<td style="height: 32px; border: 1px solid gray;"><input
								id="datestr" name="nodeConfigEntity.datestr" class="dfinput" /></td>
						</tr>
						<tr>
							<td align="center" colspan="2"
								style="height: 40px; height: 32px; border: 1px solid gray;"><input
								id="addBtn" type="button" class="btn" value="保存申请" /> <input
								type="button" class="btn" onclick="history.go(-1)" value="返回" /></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript"
		src="js/xhEditor/xheditor-1.1.14-zh-cn.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/easyPage/jquery.easypage.js"></script>
	<script type="text/javascript"
		src="js/validate/jquery.validate.message_cn.js"></script>
	<script type="text/javascript"
		src="js/validate/jquery.validate.method.js"></script>
	<script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
	<script type="text/javascript"
		src="js/artDialog/artDialog.plugins.min.js"></script>
	<script type="text/javascript">
		var nodeConfigId = $("#nodeConfigId").val();
	
		$(document).ready(function() {
			$.post("workflowAction!getNodeConfigDetail.huzd", {
				'nodeConfigEntityid' : nodeConfigId
			}, function(result) {
				if (result.flag) {
					$("#nodeConfigId").val(result.nodeConfigEntity.id);
					$("#wfkey").val(result.nodeConfigEntity.wfkey);
					$("#nodeid").val(result.nodeConfigEntity.nodeid);
					$("#nodeName").val(result.nodeConfigEntity.nodeName);
					$("#datestr").val(result.nodeConfigEntity.datestr);
				}
	
			});
	
			$("#addBtn").one("click", function() {
				var datestr = $("#datestr").val();
				var nodeConfigId = $("#nodeConfigId").val();
				if (datestr == "" || datestr == null) {
					art.dialog({
						id : 'ID-ERROR',
						title : '系统提示',
						content : '请填写时限！',
						time : 2000
					});
					return;
				}
				var uuurl = "workflowAction!editNodeConfig.huzd";
				$.post(uuurl, {
					'datestr' : datestr,
					'nodeConfigEntityid':nodeConfigId
				}, function(result) {
					if (result.flag) {
						art.dialog({
							id : 'ID-ERROR',
							title : '系统提示',
							content : result.message,
							time : 2000
						});
						history.go(-1);
					} else {
						art.dialog({
							id : 'ID-ERROR',
							title : '系统提示',
							content : result.message,
							time : 2000
						});
						history.go(-1);
					}
				});
			});
	
	
		});
	</script>
</body>
</html>