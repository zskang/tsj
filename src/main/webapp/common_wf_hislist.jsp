<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String processInstanceId = (String) request.getParameter("processInstanceId");
	String wfKey = (String) request.getParameter("wfKey");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>业务流程操作历史信息</title>
<link rel="stylesheet" type="text/css" href="${_theme_}css/style.css" />
<link href="js/artDialog/skins/opera.css" rel="stylesheet"
	type="text/css" />
<link href="js/easyPage/css/skins/jquery.easypage.css" rel="stylesheet"
	type="text/css" />
</head>

<body>
	<div class="rightinfo">
		<input type="button" class="btn" onclick="history.go(-1)" value="返回" />
		<input id="wfKey" name="wfKey" value="<%=wfKey%>" type="hidden" /> <input
			id="processInstanceId" name="processInstanceId" type="hidden"
			value="<%=processInstanceId%>" />
		<table class="tablelist" id="commmentss">
			<thead>
				<tr>
					<th>时间</th>
					<th>操作人id</th>
					<th>任务id</th>
					<th>任务名称</th>
					<th>执行人名称</th>
					<th>开始时间</th>
					<th>结束时间</th>
					<th>操作意见</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="8">数据加载中...</td>
				</tr>
			</tbody>
		</table>
	</div>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/easyPage/jquery.easypage.js"></script>
	<script type="text/javascript">
		var tableList = null;
		var wfkey = $("#wfKey").val();
		var processInstanceId = $("#processInstanceId").val();
		function getQueryParam() {
			return {
				'wfkey' : wfkey,
				'processInstanceId' : processInstanceId
			};
		}

		function fillTable(data) {
			var innerHtml = "";
			if (data.flag && data.commentsList && data.commentsList.length > 0) {
				$
						.each(
								data.commentsList,
								function(i, n) {
									innerHtml += ("<tr>");
									innerHtml += ("<td>" + n.time + "</td>");
									innerHtml += ("<td>" + n.userid + "</td>");
									innerHtml += ("<td>" + n.taskid + "</td>");
									innerHtml += ("<td>" + n.act_name_ + "</td>");
									innerHtml += ("<td>" + n.assingneeName_ + "</td>");
									innerHtml += ("<td>" + n.start_time_ + "</td>");
									innerHtml += ("<td>"
											+ (typeof (reValue) == "undefined" ? n.start_time_
													: n.end_time_) + "</td>");
									innerHtml += ("<td title='"+ n.message+ "' >"+ (n.message.length > 20 ? (n.message
													.substring(0, 20) + "...")
													: n.message) + "</td>");
									innerHtml += ("</tr>");
								});
			} else {
				innerHtml += "<tr><td colspan='8'>无数据</td></tr>";
			}
			$("#commmentss>tbody").html(innerHtml);
			$('#commmentss tbody tr:odd').addClass('odd');
		}

		$(document)
				.ready(
						function() {
							tableList = $("#commmentss")
									.page(
											{
												prefix : '',
												url : 'workflowAction!listHistoryCommentWithProcessInstanceId.huzd',
												pageSize : [ 10, 5, 20, 30 ],
												queryBtn : '',
												param : getQueryParam,
												fillTable : fillTable
											});
						});
	</script>
</body>

</html>