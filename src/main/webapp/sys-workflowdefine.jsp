<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
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
<title>流程定义管理</title>
<link rel="stylesheet" type="text/css" href="${_theme_}css/style.css" />
<link href="js/artDialog/skins/opera.css" rel="stylesheet"
	type="text/css" />
<link href="js/easyPage/css/skins/jquery.easypage.css" rel="stylesheet"
	type="text/css" />
</head>
<body>
	<div class="condition">
		<div class="cond_item">
			流程名称：<input type="text" class="dfinput" id="s_name" />
		</div>
		<div class="cond_item">
			<button id="queryBtn" class="btn_small">查询</button> 
		</div>
	</div>
	<table class="tablelist">
		<thead>
			<tr>
				<th>编号</th>
			  	<th>流程名称</th> 
				<th>流程定义的key</th>
				<th>版本</th>
					<!-- <th>流程定义的规则文件名称</th>
			<th>流程定义的规则图片名称</th> -->
				<th>流程部署Id</th>
				<th>操作</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td colspan="9">数据加载中...</td>
			</tr>
		</tbody>
	</table>
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
				's_name' : $("#s_name").val()
			};
		}
		/**表格填充函数**/
		function fillTable(data) {
			var innerHtml = "";
			if (data.flag && data.processDefinitionList
					&& data.processDefinitionList.length > 0) {
				$
						.each(
								data.processDefinitionList,
								function(i, n) {
									innerHtml += ("<tr>");
									innerHtml += ("<td>" + n.id + "</td>"); 
									innerHtml += ("<td>" + n.name + "</td>");  
									innerHtml += ("<td>" + n.key + "</td>");
									innerHtml += ("<td>" + n.version + "</td>");
									/*	innerHtml += ("<td>" + n.resourceName + "</td>");
							 		innerHtml += ("<td>"
											+ n.diagramResourceName + "</td>"); */
									innerHtml += ("<td>" + n.deploymentId + "</td>");
									innerHtml += ("<td><a href='workflowAction!showView.huzd?deploymentId=" 
									+ n.deploymentId + "&diagramResourceName="
									 + n.diagramResourceName + 
									 "' target='_blank'>查看流程图</a> <a href='wf_node_config.jsp?wfkey=" 
									 + n.key + "'>配置节点时限</a> </td></tr>");
								});
			} else {
				if (data.message)
					art.dialog({
						title : '系统提示',
						content : data.message
					});
				innerHtml += "<tr><td colspan='9'>无数据</td></tr>";
			}
			$(".tablelist>tbody").html(innerHtml);
			$('.tablelist tbody tr:odd').addClass('odd');
		}

		$(document).ready(function() {
			tableList = $(".tablelist").page({
				prefix : '',
				url : 'workflowAction!definelist.huzd',
				pageSize : [ 10, 5, 20, 30 ],
				queryBtn : 'queryBtn',
				param : getQueryParam,
				fillTable : fillTable
			}); 
		});
	</script>
</body>

</html>
