<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	  
	// normal due  overtime  一个是正常的任务  1个是即将到期的任务  1个是超时的
	String moduleId = (String) request.getParameter("moduleId");// 模块ID
	String projectId = (String) request.getParameter("projectId");// 模块ID
	System.out.print(moduleId); 
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>我的代办</title>
<link rel="stylesheet" type="text/css" href="${_theme_}css/style.css" />
<link href="js/artDialog/skins/opera.css" rel="stylesheet"
	type="text/css" />
<link href="js/easyPage/css/skins/jquery.easypage.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div class="rightinfo">
		<div class="condition">
			<div class="cond_item"> 
			     <input id="moduleId" value="<%=moduleId %>" type="hidden"/>
				任务名称：<input type="text" class="dfinput small" id="queryTaskName" name="queryTaskName" />
			</div>
			<div class="cond_item">
				开始时间：<input type="text" class="dfinput small Wdate"
					id="queryStartDate"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'queryEndDate\')}'})" />
			</div>
			<div class="cond_item">
				结束时间：<input type="text" class="dfinput small Wdate"
					id="queryEndDate"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'queryStartDate\')}'})" />
			</div>
			<div class="cond_item">
			     <input type="hidden" name="projectId" id="projectId" value="<s:property value='#session.project.id'/>" />
				<button id="queryBtn" class="btn_small">查询</button>
			</div>
		</div>

		<table class="tablelist">
			<thead>
				<tr>
					<th>任务ID</th>
					<th>任务名称</th>
					<th>创建时间</th>
					<th>状态</th> 
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="9">数据加载中...</td>
				</tr>
			</tbody>
		</table>
	</div>


	<!-- add Data form -->
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="js/validate/jquery.validate.message_cn.js"></script>
	<script type="text/javascript"
		src="js/validate/jquery.validate.method.js"></script>
	<script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
	<script type="text/javascript" src="js/easyPage/jquery.easypage.js"></script>
	<script type="text/javascript" src="js/datahelper.js"></script>
	<script type="text/javascript" src="js/option.js"></script>
	<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/pagehelper.js"></script>
	<script type="text/javascript">
		var tableList = null;

		/**获取查询条件参数**/
		function getQueryParam() {
			return { 
				'type':'faqi',
				'moduleId' : $("#moduleId").val(),
				'projectId' : $("#projectId").val(),
				'taskName' : $("#queryTaskName").val(),
				'startDate' : $("#queryStartDate").val(),
				'endDate' : $("#queryEndDate").val()
			};
		}

	 

		/**表格填充函数**/
		function fillTable(data) {
			var innerHtml = "";
			if (data.flag && data.myTaskList && data.myTaskList.length > 0) {
				$
						.each(
								data.myTaskList,
								function(i, n) {
									innerHtml += ("<tr>");
									innerHtml += ("<td>" + n.id + "</td>");
									innerHtml += ("<td>" + n.title + "</td>");
									innerHtml += ("<td>" + n.createTime + "</td>");
									innerHtml += ("<td>" + n.name + "</td>");
									innerHtml += ("<td>"
											+ "<a href='javascript:openListCommentDialog("
											+ n.id + ")'> <img src='${_theme_}btnIcon/detail.png' title='查看操作历史'/></a> <a href='javascript:foobar("
											+ n.id
											+ ")'  ><img src='${_theme_}btnIcon/search.png'  title='查看流程轨迹'/></a> </td></tr>");
								});
			} else {
				if (data.message)
					art.dialog({
						title : '系统提示',
						content : data.message
					});
				innerHtml += "<tr><td colspan='5'>无数据</td></tr>";
			}
			$(".tablelist>tbody").html(innerHtml);
			$('.tablelist tbody tr:odd').addClass('odd');
		}

		function foobar(processInstanceId) { 
			var url = "${basePath}showViewByProcessInstanceIdAction.huzd?processInstanceId="
					+ processInstanceId;
			location.href = encodeURI(url);
		}
		$(document).ready(function() {
			tableList = $(".tablelist").page({
				prefix : '',
				url : 'myWorksAction!taskPage4other.huzd',
				pageSize : [ 10, 5, 20, 30 ],
				queryBtn : 'queryBtn',
				param : getQueryParam,
				fillTable : fillTable
			});

		});
	</script>
</body>

</html>
