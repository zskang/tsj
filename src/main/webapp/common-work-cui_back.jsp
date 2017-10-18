<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/"; 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>催办任务列表</title>
<link rel="stylesheet" type="text/css" href="${_theme_}css/style.css" />
<link href="js/artDialog/skins/opera.css" rel="stylesheet" type="text/css" />
<link href="js/easyPage/css/skins/jquery.easypage.css" rel="stylesheet" type="text/css" />
</head>

<body>
	<div class="rightinfo"> 
		<input type="button" class="btn" id="cuiBtn" value="催办" /> 
		<table class="tablelist" id="commmentss">
			<thead>
				<tr>
					<th>时间</th> 
					<th>时间</th> 
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
		var wfkey=$("#wfKey").val(); 
		var processInstanceId=$("#processInstanceId").val(); 
		function getQueryParam() {
			return {
				'wfkey' :wfkey,
				'processInstanceId' :processInstanceId
			};
		}

		function fillTable(data) {
			var innerHtml = ""; 
			if (data.flag && data.commentsList && data.commentsList.length > 0) {
				$.each(data.commentsList, function(i, n) {
					innerHtml += ("<tr>");
					innerHtml += ("<td>" + n.time + "</td>"); 
					innerHtml += ("<td>" + n.message + "</td></tr>");
				});
			} else {
				innerHtml += "<tr><td colspan='3'>无数据</td></tr>";
			} 
			$("#commmentss>tbody").html(innerHtml);
			$('#commmentss tbody tr:odd').addClass('odd');
		}

		$(document).ready(function() {
				 tableList = $("#commmentss").page({
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