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
<title>流程部署管理</title>
<link rel="stylesheet" type="text/css" href="${_theme_}css/style.css" />
<link rel="stylesheet" type="text/css"
	href="js/artDialog/skins/opera.css" />
<link rel="stylesheet" type="text/css"
	href="js/easyPage/css/skins/jquery.easypage.css" />
<link rel="stylesheet" type="text/css"
	href="js/ztree/zTreeStyle/zTreeStyle.css" />
<style type="text/css">
span {
	display: inherit;
}
</style>
</head>
<body>
	<div class="rightinfo">
		<div class="tools">
			<ul class="toolbar">
				<shiro:hasPermission name="workflow:add">
					<li class="click btn_add" id="addBtn">新增流程部署</li>
				</shiro:hasPermission>
				<shiro:hasPermission name="workflow:delete">
					<li class="click btn_delete" id="deleteBtn">删除流程部署</li>
				</shiro:hasPermission>
			</ul>
		</div>
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
					<th style="width: 5%;"><a href="javascript:void(0)"
						id="selectAll">全选</a></th>
					<th>编号</th>
					<th>流程名称</th>
					<th>所属模块</th>
					<th>部署时间</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td colspan="9">数据加载中...</td>
				</tr>
			</tbody>
		</table>


		<!-- add category form -->
		<div id="imageUpload" class="hide">
			<form enctype="multipart/form-data" id="imageUploadForm"
				name="imageUploadForm" method="post">
				<input type="hidden" value="" id="selectNodeId" name="selectNodeId" />
				<ul class="forminfo">
					<li><input type="file" id="filedata" name="filedata" /></li>
				</ul>
				<ul class="forminfo">
					<ul class="toolbar">
						<li class="click btn_add" id="selectModule" name="selectModule">新增流程部署</li>
					</ul>

				</ul>
			</form>
		</div>

		<div id="selectModuleDiv" class="hide"
			style="height: 70%; width: 500px; padding: 5px 0;">
			<div class="widget_box">
				<div class="widget_title">
					<span>资源树</span>
				</div>
				<div class="widget_body">
					<div class="ztree" id="moduleTree"></div>
				</div>
			</div>
		</div>

	</div>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
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
	<script type="text/javascript"
		src="js/ztree/jquery.ztree.all-3.5.min.js"></script>
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
			if (data.flag && data.deployEntityList && data.deployEntityList.length > 0) {
				$
						.each(
								data.deployEntityList,
								function(i, n) {
									innerHtml += ("<tr><td><input type='checkbox' name='dataIds' value='"+n.id+"'/></td>");
									innerHtml += ("<td>" + n.id + "</td>");
									innerHtml += ("<td>" + n.name + "</td>");
									innerHtml += ("<td>" + n.moduleName + "</td>");
									innerHtml += ("<td>" + n.deploymentTime + "</td></tr>");
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
				url : 'workflowAction!deploylist.huzd',
				pageSize : [ 10, 5, 20, 30 ],
				queryBtn : 'queryBtn',
				param : getQueryParam,
				fillTable : fillTable
			});

			$("#selectModule").click(function() {
				art.dialog({
					title : '选择所属模块',
					content : document.getElementById('selectModuleDiv'),
					lock : true,
					okValue : '选择模块',
					ok : function() {

					},
					cancelValue : '取消',
					cancel : function() {
					}
				});
			});

			//添加事件
			$("#addBtn").click(function() {
				art.dialog({
					title : '上传',
					content : document.getElementById('imageUpload'),
					lock : true,
					okValue : '部署',
					ok : function() {
						var filevalue = $("#filedata").val();
						var selectNode = $("#selectNodeId").val();
						if (selectNode == "" || selectNode == null) {
							art.dialog({
								title : '系统提示',
								content : '请选择二级或以下模块！',
								time : 2000
							});
							return;
						}
						if (filevalue == "" || filevalue == null) {
							art.dialog({
								title : '系统提示',
								content : '请选择zip文件！',
								time : 2000
							});
							return;
						}
						var options = {
							url : 'workflowAction!saveDeploy.huzd',
							dataType : "json",
							success : function(result) {
								tableList.refresh();
								art.dialog({
									id : 'ID-SUCCESS',
									title : '系统提示',
									content : '部署成功！',
									time : 2000
								});
							},
							error : function(result) {
							}
						};
						$("#imageUploadForm").ajaxSubmit(options);
					},
					cancelValue : '取消',
					cancel : function() {
					}
				});
			});
			//删除功能
			$("#deleteBtn").click(function() {
				if ($('input[name="dataIds"]:checked').length == 0) {
					art.dialog({
						title : '系统提示',
						content : '请选择需要删除的信息！',
						time : 2000
					});
					return;
				}
				var dataIds = "";
				$.each($('input[name="dataIds"]:checked'), function(i, n) {
					dataIds += $(this).val();
					if (i < $('input[name="dataIds"]:checked').length - 1)
						dataIds += ",";
				});
				$.post('workflowAction!deleteDeploy.huzd', {
					dataIds : dataIds
				}, function(msg) {
					if (msg.flag == true) {
						tableList.refresh();
						art.dialog({
							title : '系统提示',
							content : '删除成功',
							time : 2000
						});
					} else
						art.dialog({
							title : '系统提示',
							content : '删除失败或者部分删除失败，请重试',
							time : 2000
						});
				}, 'json');
			});
		});

		var setting = {
			data : {
				simpleData : {
					enable : true,
					rootPId : null
				}
			},
			callback : {
				onClick : treeClickEvent
			},
			async : {
				enable : true,
				url : "moduleAction!listAll.huzd",
				autoParam : [ "id=moduleId", "name=name", "level=level" ]
			}
		};
		var moduleTree = null;
		$(function() {
			moduleTree = $.fn.zTree.init($("#moduleTree"), setting);
		});

		function treeClickEvent(event, treeId, treeNode) {
			//	alert("选择了：" + treeNode.id);
				alert("选择了：" + treeNode.level);
			if (treeNode.level >= 2) {
				$("#selectNodeId").val(treeNode.id);
			}
		}
	</script>
</body>

</html>
