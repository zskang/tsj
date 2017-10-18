<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String wfkey = (String) request.getParameter("wfkey");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">

<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" type="text/css" href="${_theme_}css/style.css" />
<link href="js/artDialog/skins/opera.css" rel="stylesheet"
	type="text/css" />
<link href="js/easyPage/css/skins/jquery.easypage.css" rel="stylesheet"
	type="text/css" />
</head>

<body>
	<div class="rightinfo">
		<input type="button" class="btn" onclick="history.go(-1)" value="返回" />
		<table class="tablelist">
			<thead>
				<tr>
					<th>编号</th>
					<th>节点id</th>
					<th>节点名称</th>
					<th>时限(天数)</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="4">数据加载中... <input id="wfkey" value="<%=wfkey%>"
						type="hidden" /></td>
				</tr>
			</tbody>
		</table>
	</div>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="js/validate/jquery.validate.message_cn.js"></script>
	<script type="text/javascript"
		src="js/validate/jquery.validate.method.js"></script>
	<script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
	<script type="text/javascript"
		src="js/artDialog/artDialog.plugins.min.js"></script>
	<script type="text/javascript" src="js/easyPage/jquery.easypage.js"></script>
	<script type="text/javascript" src="js/datahelper.js"></script>
	<script type="text/javascript" src="js/option.js"></script>
	<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/pagehelper.js"></script>
	<script type="text/javascript">
		var tableList = null;
		var wfkey = $("#wfkey").val();
		function fillTable(data) {
			var innerHtml = "";
			if (data.flag && data.nodeConfigEntityList && data.nodeConfigEntityList.length > 0) {
				$
					.each(
						data.nodeConfigEntityList,
						function(i, n) {
							innerHtml += ("<tr>");
							innerHtml += ("<td>" + n.id + "</td>");
							innerHtml += ("<td>" + n.nodeid + "</td>");
							innerHtml += ("<td>" + n.nodeName + "</td>");
							innerHtml += ("<td>" + n.datestr + "</td>");
							innerHtml += ("<td> "
								+ " <a href='javascript:editConfig(" + n.id + ")'> <img src='${_theme_}btnIcon/bj.png'  title='编辑'/> </a> </td>" + "</tr>");
							innerHtml += ("</tr>");
						});
			} else {
				innerHtml += "<tr><td colspan='9'>无数据</td></tr>";
			}
			$(".tablelist>tbody").html(innerHtml);
			$('.tablelist tbody tr:odd').addClass('odd');
		}
		/**获取查询条件参数**/
		function getQueryParam() {
			return {
				'wfkey' : wfkey
			};
		}
		function editConfig(id){
			window.location.href = "wf_node_config_edit.jsp?nodeConfigId=" + id;
		}
		
		
		$(document).ready(
			function() {
				/**
				 * 初始化申请列表
				 */
				tableList = $(".tablelist").page({
					prefix : '',
					url : 'workflowAction!nodeConfiglist.huzd',
					pageSize : [ 10, 5, 20, 30 ],
					queryBtn : '',
					param : getQueryParam,
					fillTable : fillTable
				});
			}
		);
	</script>
</body>

</html>