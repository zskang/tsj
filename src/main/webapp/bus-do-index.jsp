<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String wfKey = (String) request.getParameter("wfKey");
	String sfEdit = (String) request.getParameter("sfEdit");//是否在线编辑文档
	String sfUpload = (String) request.getParameter("sfUpload");//是否上传附件 
	String nextMan = (String) request.getParameter("nextMan");//下一步操作人
	String isChild = (String) request.getParameter("isChild");// 
	String sfTz = (String) request.getParameter("sfTz");//下一步操作事件名称
	String nodes = (String) request.getParameter("nodes");//节点数量
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
<style type="text/css">
.mask {
	position: absolute;
	top: 0px;
	filter: alpha(opacity = 60);
	background-color: #777;
	z-index: 1002;
	left: 0px;
	opacity: 0.5;
	-moz-opacity: 0.5;
}
</style>
<script type="text/javascript">
	//兼容火狐、IE8   
	//显示遮罩层    
	function showMask() {
		$("#mask").css("height", $(document).height());
		$("#mask").css("width", $(document).width());
		$("#mask").show();
	}
	//隐藏遮罩层  
	function hideMask() {

		$("#mask").hide();
	}
</script>
</head>

<body>
	<div class="rightinfo">
		<input type="hidden" id="wfKey" value="<%=wfKey%>" /> <input
			type="hidden" id="moduleId" value="" />
		<table class="tablelist">
			<thead>
				<tr>
					<th>编号</th>
					<th>标题</th>
					<th>申请时间</th>
					<th>申请人</th>
					<th>所在项目</th>
					<!-- 
					<th>所属模块</th> -->
					<th>流程实例</th>
					<th>流程状态</th>
					<th>下一步操作人</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="6">数据加载中...<input type="hidden" id="wfKey"
						value="<%=wfKey%>" /> <input type="hidden" id="sfEdit"
						value="<%=sfEdit%>" /> <input type="hidden" id="sfUpload"
						value="<%=sfUpload%>" /> <input type="hidden" id="nextMan"
						value="<%=nextMan%>" /> <input type="hidden" id="isChild"
						value="<%=isChild%>" /> <input type="hidden" id="sfTz"
						value="<%=sfTz%>" /> <input type="hidden" id="nodes"
						value="<%=nodes%>" /> <input type="hidden" name="projectId"
						id="projectId" value="<s:property value='#session.project.id'/>" /></td>
				</tr>
			</tbody>
		</table>
	</div>
	<div id="mask" class="mask"></div>
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
		var key = $("#wfKey").val();
		/**表格填充函数**/
		function fillTable(data) {
			var innerHtml = "";
			if (data.flag && data.entityList && data.entityList.length > 0) {
				$
						.each(
								data.entityList,
								function(i, n) {
									innerHtml += ("<tr>");
									innerHtml += ("<td>" + n.id + "</td>");
									innerHtml += ("<td>" + n.ywName + "</td>");
									innerHtml += ("<td>" + n.pubTime + "</td>");
									innerHtml += ("<td>" + n.userid + "</td>");
									innerHtml += ("<td>" + n.projectName + "</td>");/* 
																		innerHtml += ("<td>" + n.moduleId + "</td>"); */
									innerHtml += ("<td>" + n.processInstanceId + "</td>");
									innerHtml += ("<td>" + n.state + "</td>");
									if (n.state == '已完毕') {
										innerHtml += ("<td>无</td>");
									} else {
										innerHtml += ("<td>" + n.taskExcuter + "</td>");
									}
									if (n.state == '待提交' || n.state == '已驳回') {
										innerHtml += ("<td> "
												+ " <a href='javascript:editApply("
												+ n.id
												+ ")'> <img src='${_theme_}btnIcon/bj.png'  title='编辑申请'/> </a>  "
												+ "  </td>" + "</tr>");
									} else {
										innerHtml += ("<td>");
										innerHtml += ("<a href='javascript:openListCommentDialog("
												+ n.processInstanceId + ")'> <img src='${_theme_}btnIcon/detail.png' title='查看操作历史'/></a>");
										if (n.state == "已完毕") {
										} else {
										/* 	innerHtml += ("  <a  target='_blank' href='${basePath}showViewByProcessInstanceIdAction.huzd?processInstanceId="
													+ n.processInstanceId
													+ "&ywName=" + n.ywName + "'><img src='${_theme_}btnIcon/search.png'  title='查看流程轨迹'/></a>  ");
											 */
										 	innerHtml += ("  <a href='javascript:foobar("
													+ n.processInstanceId
													+ ")'  ><img src='${_theme_}btnIcon/search.png'  title='查看流程轨迹'/></a>  "); 
										}
										innerHtml += ("</td>");
									}
									innerHtml += ("</tr>");
								});
			} else {
				innerHtml += "<tr><td colspan='9'>无数据</td></tr>";
			}
			$(".tablelist>tbody").html(innerHtml);
			$('.tablelist tbody tr:odd').addClass('odd');
		}
		
		function foobar(processInstanceId) { 
				var url = "${basePath}showViewByProcessInstanceIdAction.huzd?processInstanceId="
						+ processInstanceId;
				location.href = encodeURI(url);
			}

		function openListCommentDialog(processInstanceId) {
			window.location.href = 'common_wf_hislist.jsp?processInstanceId='
					+ processInstanceId + '&wfKey=' + key;
		}

		function getQueryParam() {
			return {
				'wfKey' : key,
				'projectId' : $("#projectId").val()
			};
		}
		$(document).ready(function() {
			/**
			 *初始化申请列表
			 */
			tableList = $(".tablelist").page({
				prefix : '',
				url : 'busBaseAction!listApplys.huzd',
				pageSize : [ 10, 5, 20, 30 ],
				queryBtn : '',
				param : getQueryParam,
				fillTable : fillTable
			});

			$.ajax({
				type : 'POST',
				url : 'busBaseAction!getModuleIdByWfKey.huzd',
				data : {
					'wfKey' : key
				},
				async : false,
				dataType : 'json',
				success : function(msg) {
					if (true == msg.flag) {
						$("#moduleId").val(msg.message);
					}
				}
			});
		});

		function editApply(id) {
			var moduleId = $("#moduleId").val();
			var wfKey = $("#wfKey").val();
			var sfEdit = $("#sfEdit").val();
			var sfUpload = $("#sfUpload").val();
			var nextMan = $("#nextMan").val();
			var isChild = $("#isChild").val();
			var taskId = $("#taskId").val();
			var nodes = $("#nodes").val();
			var sfTz = $("#sfTz").val(); 
			var url = "bus-common-edit.jsp?wfKey=" + wfKey + '&moduleId='
					+ moduleId + '&sfEdit=' + sfEdit + '&sfUpload=' + sfUpload
					+ '&nextMan=' + nextMan + '&isChild=' + isChild + '&sfTz='
					+ sfTz + '&nodes=' + nodes + '&taskId=' + taskId
					+ '&previosState=0&id=' + id;
			window.location.href = url;
		}
	</script>
</body>

</html>