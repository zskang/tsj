<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";

	String wfKey = (String) request.getParameter("wfKey");
	System.out.println("进入编辑页........wfKey====" + wfKey);
	String id = (String) request.getParameter("id");
	String sfEdit = (String) request.getParameter("sfEdit");//是否在线编辑文档
	String sfUpload = (String) request.getParameter("sfUpload");//是否上传附件
	String wfName = (String) request.getParameter("wfName");//流程申请名称 */
	String nextMan = (String) request.getParameter("nextMan");//下一步操作人类型
	System.out.println("nextMan........" + nextMan);
	String doName = (String) request.getParameter("doName");//下一步操作事件名称 */
	String taskId = (String) request.getParameter("taskId");//任务id
	String state = (String) request.getParameter("state");//状态
	String busstate = (String) request.getParameter("busstate");//状态
	System.out.println(taskId);
	if (!"undefined".equals(taskId)) {
		System.out.println("当前是驳回的修改页面...");
	} else {
		System.out.println("进入编辑页........");
	}
	String isChild = (String) request.getParameter("isChild");// 
	String sfTz = (String) request.getParameter("sfTz");// 
	String moduleId = (String) request.getParameter("moduleId");// 模块ID
	String nextState = (String) request.getParameter("nextState");// 提交到下一节点
	String sfSh = (String) request.getParameter("sfSh");// 是否审核
	String sfBack = (String) request.getParameter("sfBack");// 是否驳回
	String previosState = (String) request.getParameter("previosState");//返回到上一节点状态
	String nextName = (String) request.getParameter("nextName");//下一步操作人名称
	if ("NULL".equals(nextName)) {
		nextName = "";
	}
	String processInstanceId = (String) request.getParameter("processInstanceId");//processInstanceId

	System.out.println("processInstanceId=" + processInstanceId);
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
<style type="text/css">
/******中铁四局 流程专用表格样式******/
.approveTable {
	width: 100%;
	border: 1px solid gray;
}

.approveTable td a {
	margin: 0 auto;
	display: block;
	height: 30px;
}

.approveTable tfoot th {
	text-align: center;
}

.tool_btns {
	text-align: center;
}

.tablelist td {
	text-indent: 0px !important;
}

.delete_btn {
	background: transparent url('${_theme_}images/btn_icon/delete.png')
		no-repeat scroll center/16px auto;
	cursor: pointer;
	display: block;
}
</style>
<style type="text/css">
span {
	display: inherit;
}
</style>

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

</head>

<body>
	<div class="rightinfo">
		<div class="content">
			<div class="formbody">
				<div class="formtitle" style="height: 45px;">
					<%-- 	<span><%=wfName%>-<%=doName%></span> --%>
					<span>编辑流程申请</span>
					<div class="right">
						<ul class="toolbar">
							<%
								if ("Y".equals(sfEdit)) {
							%>
							<li class="click btn_add" id="jsPswEdit">选择相关文档进行编辑</li>
							<%
								}
							%>
							<%
								if ("Y".equals(sfUpload)) {
							%>
							<li class="click btn_add" id="addFiles">上传附件</li>
							<%
								}
							%>
							<%
								if ("gxjsjd_36_index".equals(wfKey) || "aqjsjd_36_index".equals(wfKey)) {
							%>
							<li class="click btn_add" id="addDetail4jsjd">添加条目</li>
							<%
								}
							%>
							<%
								if ("szgl_18_index".equals(wfKey) || "sgfabzjh_19_do".equals(wfKey)) {
							%>
							<li class="click btn_add" id="addDetail4szgl">添加条目</li>
							<%
								}
							%>
						</ul>
					</div>
				</div>
				<form id="addDataForm" action="#" method="post"
					enctype="multipart/form-data">
					<input type="hidden" name="busBaseEntity[0].id" value="<%=id%>"
						id="id" /> <input type="hidden" value="<%=processInstanceId%>"
						name="processInstanceId" id="processInstanceId" /> <input
						type="hidden" name="projectId" id="projectId"
						value="<s:property value='#session.project.id'/>" /> <input
						type="hidden" name="projectName" id="projectName"
						value="<s:property value='#session.project.name'/>" /> <input
						type="hidden" name="projectType" id="projectType"
						value="<s:property value='#session.project.type.id'/>" /> <input
						type="hidden" value="<%=moduleId%>" name="moduleId" id="moduleId" />
					<input type="hidden" value="<%=wfKey%>" name="wfKey" id="wfKey" />
					<input type="hidden" value="" name="tempFilePath" id="tempFilePath" />
					<input type="hidden" value="" name="doType" id="doType" /> <input
						type="hidden" value="" name="state" id="state" /> <input
						type="hidden" value="<%=taskId%>" name="taskId" id="taskId" /> <input
						type="hidden" value="<%=sfTz%>" name="sfTz" id="sfTz" /> <input
						type="hidden" value="<%=nextMan%>" name="nextMan" id="nextMan" />
					<input type="hidden" value="<%=previosState%>" name="previosState"
						id="previosState" /> <input type="hidden" value="<%=sfEdit%>"
						name="sfEdit" id="sfEdit" /> <input type="hidden"
						value="<%=nextState%>" name="nextState" id="nextState" /><input
						type="hidden" value="<%=sfUpload%>" name="sfUpload" id="sfUpload" />
					<input type="hidden" value="提交" name="comment" id="comment" />

					<table class="tablelist" id="tablelist">
						<%
							if ("gxjsjd_36_index".equals(wfKey) || "aqjsjd_36_index".equals(wfKey)) {
								String title = "";
								String wdbh = "";
								String bgbh = "";
								if ("gxjsjd_36_index".equals(wfKey)) {
									title = "工序技术交底编制计划";
									wdbh = "W10010101";
								}

								if ("aqjsjd_36_index".equals(wfKey)) {
									title = "安全技术交底编制计划";
									wdbh = "W10020101";
								}
						%><thead>
							<tr>
								<td rowspan="1" style="height: 32px; border: 1px solid gray;"><img
									src="${_theme_}images/common/logo.png" alt="logo" /></td>
								<td rowspan="1" style="height: 32px; border: 1px solid gray;"
									colspan="8" align="center"><%=title%></td>
								<td style="height: 32px; border: 1px solid gray;" colspan="2"
									align="center">系统文档编号</td>
								<td style="height: 32px; border: 1px solid gray;" colspan="2"
									align="center">表格编号</td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;" align="center">项目名称</th>
								<th style="height: 32px; border: 1px solid gray;" colspan="8"
									align="center"><s:property value="#session.project.name" /></th>
								<th style="height: 32px; border: 1px solid gray;" colspan="2"
									align="center"><input id="wdbh"
									name="busBaseEntity[0].wdbh" class="dfinput" value="<%=wdbh%>"
									style="width: 100px" /></th>
								<th style="height: 32px; border: 1px solid gray;" colspan="2"
									align="center"><input id="bgbh"
									name="busBaseEntity[0].bgbh" class="dfinput" value="<%=bgbh%>"
									style="width: 100px" /></th>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;" colspan="2">单位名称</th>
								<th style="height: 32px; border: 1px solid gray;" colspan="2">分布分项名称</th>
								<th style="height: 32px; border: 1px solid gray;" colspan="2">技术交底名称</th>
								<th style="height: 32px; border: 1px solid gray;">责任人</th>
								<th style="height: 32px; border: 1px solid gray;" colspan="2">联系方式</th>
								<th style="height: 32px; border: 1px solid gray;" colspan="2">任务完成时间</th>
								<th style="height: 32px; border: 1px solid gray;">备注</th>
								<th width="10%" style="height: 32px; border: 1px solid gray;">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr class="tip_">
								<td colspan="13"
									style="text-align: center; color: red; height: 32px; border: 1px solid gray;">尚未添加任何条目</td>
							</tr>
						</tbody>
						<%
							}
						%>
						<%
							if ("szgl_18_index".equals(wfKey) || "sgfabzjh_19_do".equals(wfKey)) {
								String title = "";
								String s = "";
								String wdbh = "";
								String bgbh = "";
								if ("szgl_18_index".equals(wfKey)) {
									title = "施组编制计划";
									s = "施组";
									wdbh = "W07010101";
									bgbh = "1308";
								} else {
									title = "施工方案编制计划";
									s = "方案";
									wdbh = "W07020101";
									bgbh = "1310";
								}
						%>
						<thead>
							<tr>
								<td rowspan="1" style="height: 32px; border: 1px solid gray;"><img
									src="${_theme_}images/common/logo.png" alt="logo" /></td>
								<td rowspan="1" style="height: 32px; border: 1px solid gray;"
									colspan="10" align="center"><%=title%></td>
								<td style="height: 32px; border: 1px solid gray;">系统文档编号</td>
								<td style="height: 32px; border: 1px solid gray;">表格编号</td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">项目名称</th>
								<th style="height: 32px; border: 1px solid gray;" colspan="10"><s:property
										value="#session.project.name" /></th>
								<th style="height: 32px; border: 1px solid gray;"><input
									id="wdbh" name="busBaseEntity[0].wdbh" class="dfinput"
									value="<%=wdbh%>" style="width: 100px" readonly="readonly" /></th>
								<th style="height: 32px; border: 1px solid gray;"><input
									id="bgbh" name="busBaseEntity[0].bgbh" class="dfinput"
									value="<%=bgbh%>" style="width: 100px" readonly="readonly" /></th>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">名称</th>
								<th style="height: 32px; border: 1px solid gray;">方案等级</th>
								<th style="height: 32px; border: 1px solid gray;">编制人</th>
								<th style="height: 32px; border: 1px solid gray;">计划完成时间</th>
								<th style="height: 32px; border: 1px solid gray;">实际完成时间</th>
								<th style="height: 32px; border: 1px solid gray;">项目经理部</th>
								<th style="height: 32px; border: 1px solid gray;">监理</th>
								<th style="height: 32px; border: 1px solid gray;">业主</th>
								<th style="height: 32px; border: 1px solid gray;">子公司</th>
								<th style="height: 32px; border: 1px solid gray;">公司</th>
								<th style="height: 32px; border: 1px solid gray;">股份公司</th>
								<th style="height: 32px; border: 1px solid gray;">备注</th>
								<th style="height: 32px; border: 1px solid gray;">操作</th>
							</tr>
						</thead>
						<tbody>
							<tr class="tip_">
								<td colspan="13"
									style="text-align: center; color: red; height: 32px; border: 1px solid gray;">尚未添加任何条目</td>
							</tr>
						</tbody>
						<%
							}
						%>
						<tfoot>
							<%
								if ("gxjsjd_36_do".equals(wfKey) || "aqjsjd_36_do".equals(wfKey)) {
							%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">工程单位名称:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="gcdwmc" name="busBaseEntity[0].gcdwmc" class="dfinput" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">技术交底名称:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="jsjdmc" name="busBaseEntity[0].jsjdmc" class="dfinput" /></td>
							</tr>

							<tr>
								<th style="height: 32px; border: 1px solid gray;">责任人:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;">
									<select id="zrrenid" name="busBaseEntity[0].zrrenid"
									disabled="disabled">
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='jsy'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
								</select>
								</td>
							</tr>

							<tr>
								<th style="height: 32px; border: 1px solid gray;">联系方式:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="lxfs" name="busBaseEntity[0].lxfs" class="dfinput"
									readonly="readonly" /></td>
							</tr>
							<%
								}
							%>


							<%
								if ("szgl_18_do".equals(wfKey) || "tjsgfa_20_index".equals(wfKey) || "IIIjsgfa_21_index".equals(wfKey)) {
							%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">名称:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="gcdwmc" name="busBaseEntity[0].gcdwmc" class="dfinput" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">方案等级:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><select
									id="fadj" name="busBaseEntity[0].fadj"><option
											value="1">I级</option>
										<option value="2">II级</option>
										<option value="3">III级</option>
										<option value="4">特级</option></select></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">编制人:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><select
									id="zrrenid" name="busBaseEntity[0].zrrenid"></select></td>
							</tr>
							<!-- 	<tr>
								<th style="height: 32px; border: 1px solid gray;">计划完成时间:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="jhwcsj2" name="busBaseEntity[0].jhwcsj2" class="dfinput" /></td>
							</tr>

							<tr>
								<th style="height: 32px; border: 1px solid gray;">实际完成时间:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="sjwcsj2" name="busBaseEntity[0].sjwcsj2" class="dfinput"
									disabled="disabled" /></td>
							</tr> -->
							<tr>
								<th style="height: 32px; border: 1px solid gray;">项目经理部:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="xmjlb" name="busBaseEntity[0].xmjlb" class="dfinput" /></td>
							</tr>
							<!-- 	<tr>
								<th style="height: 32px; border: 1px solid gray;">监理:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="jianli" name="busBaseEntity[0].jianli" class="dfinput" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">业主:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="yezhu" name="busBaseEntity[0].yezhu" class="dfinput" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">子公司:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="zigs" name="busBaseEntity[0].zigs" class="dfinput" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">公司:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="gsi" name="busBaseEntity[0].gsi" class="dfinput" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">股份公司:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="gfgs" name="busBaseEntity[0].gfgs" class="dfinput" /></td>
							</tr> -->
							<tr>
								<th style="height: 32px; border: 1px solid gray;">备注:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="remark2" name="busBaseEntity[0].remark2" class="dfinput" /></td>
							</tr>
							<%
								}
							%>
							<%
								//技术管理交底流程
								if ("jsgljd_02".equals(wfKey)) {
							%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">技术管理交底分类:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;">
									<select id="jsgljdfl" name="busBaseEntity[0].jsgljdfl">
										<option value="">--请选择--</option>
										<option value="公司">公司</option>
										<option value="业主监理单位">业主监理单位</option>
										<option value="设计单位">设计单位</option>
								</select>
								</td>
							</tr>

							<%
								}
							%>
							<!-- 外来技术文件 -->
							<%
								if ("wlwjgl_10_index".equals(wfKey)) {
							%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">发文单位:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="fwdw" name="busBaseEntity[0].fwdw" class="dfinput" /></td>
							</tr>
							<%
								}
							%>



							<%-- 	<%
								if (!"gxjsjd_36_do".equals(wfKey) && !"aqjsjd_36_do".equals(wfKey) && !"szgl_18_do".equals(wfKey)
										&& !"tjsgfa_20_index".equals(wfKey) && !"IIIjsgfa_21_index".equals(wfKey)) {
							%> --%>

							<% if (!"jxsbxqjh_17_do".equals(wfKey) && !"swtz_33_process".equals(wfKey)){ %>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">请选择<%
									if ("documenter".equals(nextMan)) {
										out.print("资料员");
									}
									if ("technician".equals(nextMan)) {
										out.print("技术员");
									}
									if ("techofficor".equals(nextMan)) {
										out.print("技术主管");
									}
									if ("workMinister".equals(nextMan)) {
										out.print("工程部长");
									}
									if ("projectEngineer".equals(nextMan) || "ProjectEngineer".equals(nextMan)
											|| "projectEnginee".equals(nextMan)) {
										out.print("项目总工");
									}
									if ("projectManager".equals(nextMan) || "ProjectManager".equals(nextMan)) {
										out.print("项目经理");
									}
									if ("jjr".equals(nextMan)) {
										out.print("接交人");
									}
									if ("ngr".equals(nextMan)) {
										out.print("拟稿人");
									}
									if ("Borrower".equals(nextMan)) {
										out.print("借阅人");
									}
									if ("TechnicalSupervisor".equals(nextMan)) {
										out.print("公司技术部负责人");
									}
									/* 	测量主管、项目经理、代局指测量主管、代局指工程部长、代局指项目总工、授课人、移交人、
										接交人、拟稿人、借阅人、文件接收人、审核人、复核人、接收人、旁站管理负责人（项目总工）、
										旁站安排人、旁站值班人、研究小组主要成员 */
									if ("cltechofficor".equals(nextMan)) {
										out.print("测量主管");
									}
									if ("ALL".equals(nextMan)) {
										out.print("项目参与人");
									}
								%>
								</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><select
									id="taskExcuter" name="busBaseEntity[0].taskExcuter"
									style="width: 200px">
										<option value="">--请选择--</option>
										<%
											if ("documenter".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='zly'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
											if ("technician".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='jsy'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
											if ("techofficor".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='jszg'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
											if ("workMinister".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='gcbz'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
											if ("projectEngineer".equals(nextMan) || "ProjectEngineer".equals(nextMan)
													|| "projectEnginee".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='xmzg'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
											if ("projectManager".equals(nextMan) || "ProjectManager".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='xmjl'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
											if ("cltechofficor".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='jczzg'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
											if ("jjr".equals(nextMan)) {
										%>
										<%-- 	<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='jjr'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator> --%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<option value="<s:property value='#man.user.id' />">
												<s:property value='#man.user.chinesename' />[
												<s:property value='#man.role.name' />]
											</option>
										</s:iterator>
										<%
											}
											if ("ngr".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='ngr'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
											if ("TechnicalSupervisor".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='gsjsbfzr'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											} else if ("ALL".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<option value="<s:property value='#man.user.id' />">
												<s:property value='#man.user.chinesename' />[
												<s:property value='#man.role.name' />]
											</option>
										</s:iterator>
										<%
											} else if ("Borrower".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='jyr'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
										%>
								</select></td>
							</tr>
							<% 
								}
							%>  
							<%-- 			<%
								//技术交接流程
								if ("jsgzjj_09_index".equals(wfKey)) {
							%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">选择交接人:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;">
									<select id="bjjr" name="busBaseEntity[0].bjjr">
										<s:iterator value="#session.project.projectUsers" var="man">
											<option value="<s:property value='#man.user.id' />">
												<s:property value='#man.user.chinesename' />[<s:property value='#man.role.name' />]
											</option>
										</s:iterator>
								</select>
								</td>
							</tr>
							<%
								}
							%> --%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">计划开始时间:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="jhkssj" name="busBaseEntity[0].jhkssj"
									class="dfinput Wdate"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',maxDate:'#F{$dp.$D(\'jhwcsj1\')||\'2028-10-01\'}'})" /></td>
							</tr>

							<%
								if ("gxjsjd_36_do".equals(wfKey) || "aqjsjd_36_do".equals(wfKey) || "szgl_18_do".equals(wfKey)
										|| "tjsgfa_20_index".equals(wfKey) || "IIIjsgfa_21_index".equals(wfKey)) {
							%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">计划完成时间:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="jhwcsj2" name="busBaseEntity[0].jhwcsj2"
									class="dfinput Wdate"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'jhkssj\')}',maxDate:'2028-10-01'})" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">备注</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><textarea
										style="height: 30px; width: 80%; border: 1px solid #d3dbde;"
										cols="80" rows="6" id="remark2"
										name="busBaseEntity[0].remark2"></textarea></td>
							</tr>
							<%
								} else {
							%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">计划完成时间:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="jhwcsj1" name="busBaseEntity[0].jhwcsj1"
									class="dfinput Wdate"
									onFocus="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'jhkssj\')}',maxDate:'2028-10-01'})" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">备注</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><textarea
										style="height: 30px; width: 80%; border: 1px solid #d3dbde;"
										cols="80" rows="6" id="remark1"
										name="busBaseEntity[0].remark1"></textarea></td>
							</tr>
							<%
								}
							%>
							<tr>
								<td colspan="13" align="center"
									style="height: 40px; border: 1px solid gray;"><input
									id="okBtn" type="button" class="btn" value="保存" /> <%
 	if (!"undefined".equals(taskId)) {
 		System.out.println(taskId);
 %> <input id="okBtn2" type="button" class="btn" value="提交流程" /> <%
 	} else {
 %> <input id="okBtn3" type="button" class="btn" value="发起流程" /> <%
 	}
 %> <input type="button" class="btn" onclick="history.go(-1)" value="返回" /></td>
							</tr>
						</tfoot>
					</table>
					<div id="docListsDiv" style="display: none;">
						<table class="tablelist" id="docLists">
							<thead>
								<tr>
									<th
										style="height: 32px; width: 80px; border: 1px solid gray; text-align: center">编号</th>
									<th
										style="height: 32px; width: 580px; border: 1px solid gray; text-align: center">文档名称</th>
									<th
										style="height: 32px; width: 280px; border: 1px solid gray; text-align: center">编辑时间</th>
									<th
										style="height: 32px; border: 1px solid gray; text-align: center">操作</th>
								</tr>
							</thead>
							<tbody>
								<tr class="tip_">
									<td colspan="4"
										style="height: 32px; border: 1px solid gray; text-align: center">尚未选择任何文档</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div id="fujianListsDiv" style="display: none;">
						<div>
							<p>
								<font color="red">ps:上传文件格式支持：doc,docx,zip,
									xlsx,xls,png,gif,jpeg,ppt </font>
							</p>
						</div>
						<table class="tablelist" id="fujianLists">
							<thead>
								<tr>
									<th
										style="height: 32px; width: 80px; border: 1px solid gray; text-align: center">编号</th>
									<th
										style="height: 32px; width: 580px; border: 1px solid gray; text-align: center">附件名称</th>
									<th
										style="height: 32px; width: 280px; border: 1px solid gray; text-align: center">上传时间</th>
									<th
										style="height: 32px; border: 1px solid gray; text-align: center">操作</th>
								</tr>
							</thead>
							<tbody>
								<tr class="tip_">
									<td colspan="4"
										style="height: 32px; border: 1px solid gray; text-align: center">尚未上传任何文件</td>
								</tr>
							</tbody>
						</table>
					</div>
					<%
						if (!"undefined".equals(taskId)) {
					%>
					<div>
						<p>操作历史：</p>
					</div>
					<div id="historylistDiv">
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

							</tbody>
						</table>
					</div>
					<%
						}
					%>
				</form>
			</div>
		</div>

		<div id="selectModuleDiv" class="hide"
			style="height: 70%; width: 500px; padding: 5px 0;">
			<div class="widget_box">
				<div class="condition">
					文档名称：<input class="dfinput smaller" style="height: 32px"
						type="text" id="key" />
					<button id="queryUserBtn" class="btn_small">查询</button>
				</div>
				<div class="widget_body">
					<div class="ztree" id="moduleTree"></div>
				</div>
			</div>
		</div>
	</div>
	<select id="p_xxxxx" class="hide">
		<s:property value="#session.project.projectUsers.length" />
		<s:iterator value="#session.project.projectUsers" var="man">
			<s:if test="#man.role.shortName=='jsy'">
				<option value="<s:property value='#man.user.id' />"><s:property
						value='#man.user.chinesename' /></option>
			</s:if>
		</s:iterator>
	</select>

	<select id="gcbz" class="hide">
		<s:property value="#session.project.projectUsers.length" />
		<s:iterator value="#session.project.projectUsers" var="man">
			<s:if test="#man.role.shortName=='gcbz'">
				<option value="<s:property value='#man.user.id' />"><s:property
						value='#man.user.chinesename' /></option>
			</s:if>
		</s:iterator>
	</select>
	<select id="jszg" class="hide">
		<s:property value="#session.project.projectUsers.length" />
		<s:iterator value="#session.project.projectUsers" var="man">
			<s:if test="#man.role.shortName=='jszg'">
				<option value="<s:property value='#man.user.id' />"><s:property
						value='#man.user.chinesename' /></option>
			</s:if>
		</s:iterator>
	</select>
	 <select id="gsjsbfzr" class="hide">
		<s:property value="#session.project.projectUsers.length" />
		<s:iterator value="#session.project.projectUsers" var="man">
			<s:if test="#man.role.shortName=='gsjsbfzr'">
				<option value="<s:property value='#man.user.id' />"><s:property
						value='#man.user.chinesename' /></option>
			</s:if>
		</s:iterator>
	</select>
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript"
		src="js/xhEditor/xheditor-1.1.14-zh-cn.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript"
		src="js/validate/jquery.validate.message_cn.js"></script>
	<script type="text/javascript"
		src="js/validate/jquery.validate.method.js"></script>
	<script type="text/javascript"
		src="js/ztree/jquery.ztree.core-3.5.min.js"></script>
	<script type="text/javascript" src="js/easyPage/jquery.easypage.js"></script>
	<script type="text/javascript"
		src="js/ztree/jquery.ztree.exhide-3.5.min.js"></script>
	<script type="text/javascript"
		src="js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
	<script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
	<script type="text/javascript"
		src="js/artDialog/artDialog.plugins.min.js"></script>
	<script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
	<script type="text/javascript" src="js/bus-common.js"></script>
	<script type="text/javascript" src="js/bus-common-edit.js"></script>
	<script type="text/javascript" src="js/bus-common-edit-tree.js"></script>
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
		var state = '<%=state%>';
		var busstate = '<%=busstate%>';
	</script>
</body>

</html>