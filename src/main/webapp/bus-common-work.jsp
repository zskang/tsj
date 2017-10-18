<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
	System.out.println("进入流程操作页........");
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
	String wfKey = (String) request.getParameter("wfKey");//流程key 
	String sfEdit = (String) request.getParameter("sfEdit");//流程中是否在线编辑文档 
	String sfUpload = (String) request.getParameter("sfUpload");//流程中是否上传附件 
	String wfName = (String) request.getParameter("wfName");//当前流程名称 
	if (wfName == null) {
		wfName = (String) request.getParameter("fName");
	}

	String doName = (String) request.getParameter("doName");//当前操作名称 
	String isChild = (String) request.getParameter("isChild");//是否有子流程 
	String sfTz = (String) request.getParameter("sfTz");//是否记录台账
	if ("NULL".equals(sfTz)) {
		sfTz = "N";
	}
	String moduleId = (String) request.getParameter("moduleId");//模块id 
	String taskId = (String) request.getParameter("taskId");//任务id 
	String id = (String) request.getParameter("id");// 业务id 
	String nextState = (String) request.getParameter("nextState");// 提交到下一节点 
	String sfSh = (String) request.getParameter("sfSh");// 是否审核 
	String sfBack = (String) request.getParameter("sfBack");// 是否驳回 
	String nextMan = (String) request.getParameter("nextMan");//下一步操作人类型
	if ("NULL".equals(nextMan)) {
		nextMan = "";
	}
	String nextName = (String) request.getParameter("nextName");//下一步操作人名称
	if ("NULL".equals(nextName)) {
		nextName = "";
	}
	String previoState = (String) request.getParameter("previoState");//返回到上一节点状态
	if (previoState == null || "".equals(previoState) || "NULL".equals(previoState)) {
		previoState = "0";
	}
	System.out.println("wfName=" + wfName);
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

.delete_btn {
	background: transparent url('${_theme_}images/btn_icon/delete.png')
		no-repeat scroll center/16px auto;
	cursor: pointer;
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
					<span><%=wfName%>-<%=doName%></span>
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
						</ul>
					</div>
				</div>
				<form id="addDataForm" action="#" method="post"
					enctype="multipart/form-data">
					<input type="hidden" name="busBaseEntity[0].id" value="<%=id%>"
						id="id" /> <input type="hidden" name="projectId" id="projectId"
						value="<s:property value='#session.project.id'/>" /> <input
						type="hidden" name="projectName" id="projectName"
						value="<s:property value='#session.project.name'/>" /> <input
						type="hidden" name="projectType" id="projectType"
						value="<s:property value='#session.project.type.id'/>" /> <input
						type="hidden" value="<%=moduleId%>" name="moduleId" id="moduleId" />
					<input type="hidden" value="<%=wfKey%>" name="wfKey" id="wfKey" />
					<input type="hidden" value="" name="tempFilePath" id="tempFilePath" />
					<input type="hidden" value="<%=nextState%>" name="nextState"
						id="nextState" /> <input type="hidden" value="<%=taskId%>"
						name="taskId" id="taskId" /> <input type="hidden"
						value="<%=sfSh%>" name="sfSh" id="sfSh" /> <input type="hidden"
						value="<%=sfBack%>" name="sfBack" id="sfBack" /> <input
						type="hidden" value="<%=sfTz%>" name="sfTz" id="sfTz" /> <input
						type="hidden" value="<%=nextMan%>" name="nextMan" id="nextMan" />
					<input type="hidden" value="<%=previoState%>" name="previoState"
						id="previoState" /> <input type="hidden" value="" name="doType"
						id="doType" /> <input type="hidden" value="<%=sfEdit%>"
						name="sfEdit" id="sfEdit" /> <input type="hidden"
						value="<%=sfUpload%>" name="sfUpload" id="sfUpload" /> <input
						type="hidden" value="<%=isChild%>" name="isChild" id="isChild" />
					<input type="hidden" value="<%=nextName%>" name="nextName"
						id="nextName" /> <input type="hidden"
						value="<%=processInstanceId%>" name="processInstanceId"
						id="processInstanceId" />

					<table class="tablelist" id="tablelist">
						<%
							if ("gxjsjd_36_index".equals(wfKey) || "aqjsjd_36_index".equals(wfKey)) {
								String title = "";
								if ("gxjsjd_36_index".equals(wfKey)) {
									title = "工序技术交底编制计划";
								}

								if ("aqjsjd_36_index".equals(wfKey)) {
									title = "安全技术交底编制计划";
								}
						%>
						<thead>
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
									name="busBaseEntity[0].wdbh" class="dfinput"
									style="width: 100px" disabled="disabled" /></th>
								<th style="height: 32px; border: 1px solid gray;" colspan="2"
									align="center"><input id="bgbh"
									name="busBaseEntity[0].bgbh" class="dfinput"
									style="width: 100px" disabled="disabled" /></th>
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
								<th style="height: 32px; border: 1px solid gray;"><%=s%>等级</th>
								<th style="height: 32px; border: 1px solid gray;">编制人</th>
								<th style="height: 32px; border: 1px solid gray;">任务完成时间</th>
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
									id="gcdwmc" name="busBaseEntity[0].gcdwmc" class="dfinput"
									disabled="disabled" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">技术交底名称:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="jsjdmc" name="busBaseEntity[0].jsjdmc" class="dfinput"
									disabled="disabled" /></td>
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
									disabled="disabled" /></td>
							</tr>
							<%
								}
							%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">计划开始时间:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="jhkssj" name="busBaseEntity[0].jhkssj"
									class="dfinput Wdate" disabled="disabled" /></td>
							</tr>
							<%
								if ("gxjsjd_36_do".equals(wfKey) || "aqjsjd_36_do".equals(wfKey) || "szgl_18_do".equals(wfKey)
										|| "tjsgfa_20_index".equals(wfKey) || "IIIjsgfa_21_index".equals(wfKey)) {
							%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">计划完成时间:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="jhwcsj2" name="busBaseEntity[0].jhwcsj2"
									class="dfinput Wdate" disabled="disabled" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">备注</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><textarea
										readonly="readonly"
										style="height: 30px; width: 80%; border: 1px solid #d3dbde;"
										cols="80" rows="6" id="remark2" disabled="disabled"
										name="busBaseEntity[0].remark2"></textarea></td>
							</tr>
							<%
								} else {
							%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">计划完成时间:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="jhwcsj1" name="busBaseEntity[0].jhwcsj1"
									class="dfinput Wdate" disabled="disabled" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">备注</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><textarea
										readonly="readonly"
										style="height: 30px; width: 80%; border: 1px solid #d3dbde;"
										cols="80" rows="6" id="remark1" disabled="disabled"
										name="busBaseEntity[0].remark1"></textarea></td>
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
									id="gcdwmc" name="busBaseEntity[0].gcdwmc" class="dfinput"
									disabled="disabled" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">方案等级:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><select
									id="fadj" disabled="disabled">
										<option value="1">I级</option>
										<option value="2">II级</option>
										<option value="3">III级</option>
										<option value="4">特级</option>
								</select></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">编制人:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><select
									id="zrrenid" name="busBaseEntity[0].zrrenid"
									disabled="disabled" style="width: 200px"></select></td>
							</tr>
							<!-- <tr>
								<th style="height: 32px; border: 1px solid gray;">计划完成时间:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									readonly="readonly" id="jhwcsj2"
									name="busBaseEntity[0].jhwcsj2" class="dfinput" /></td>
							</tr>

							<tr>
								<th style="height: 32px; border: 1px solid gray;">实际完成时间:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input 
									readonly="readonly" id="sjwcsj2"
									name="busBaseEntity[0].sjwcsj2" class="dfinput" /></td>
							</tr> -->

							<tr>
								<th style="height: 32px; border: 1px solid gray;">项目经理部:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									disabled="disabled" id="xmjlb" name="busBaseEntity[0].xmjlb"
									class="dfinput" readonly="readonly" /></td>
							</tr>
							<!-- <tr>
								<th style="height: 32px; border: 1px solid gray;">监理:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									readonly="readonly" id="jianli" name="busBaseEntity[0].jianli"
									class="dfinput" readonly="readonly" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">业主:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									readonly="readonly" id="yezhu" name="busBaseEntity[0].yezhu"
									class="dfinput" readonly="readonly" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">子公司:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									readonly="readonly" id="zigs" name="busBaseEntity[0].zigs"
									class="dfinput" readonly="readonly" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">公司:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									readonly="readonly" id="gsi" name="busBaseEntity[0].gsi"
									class="dfinput" /></td>
							</tr>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">股份公司:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="gfgs" name="busBaseEntity[0].gfgs" class="dfinput"
									readonly="readonly" /></td>
							</tr> -->
							<tr>
								<th style="height: 32px; border: 1px solid gray;">备注:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><input
									id="remark2" name="busBaseEntity[0].remark2" class="dfinput"
									disabled="disabled" /></td>
							</tr>
							<%
								}
							%>

							<%
								if (!"99".equals(nextState)) {
							%>
							<tr id="selectTr">
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
										if ("yjxz".equals(nextMan)) {
											out.print("研究小组");
										}
										if ("zlsqr".equals(nextMan)) {
											out.print("专利申请人");
										}
										if ("zlxzzycy".equals(nextMan)) {
											out.print("质量小组主要成员");
										}
										if ("Borrower".equals(nextMan)) {
											out.print("借阅人");
										}
										if ("jjr".equals(nextMan)) {
											out.print("接交人");
										}
										if ("ngr".equals(nextMan)) {
											out.print("拟稿人");
										}
										if ("TechnicalSupervisor".equals(nextMan)) {
											out.print("公司技术部负责人");
										}
										if ("ALL".equals(nextMan)) {
											out.print("项目参与人");
										}
										if ("gsjsbfzr".equals(nextMan)) {
											out.print("公司技术部门负责人");
										}
										if ("Departmentresponsiblepers".equals(nextMan)) {
											out.print("公司技术部门负责人");
										}
										if ("djz_cltechofficor".equals(nextMan)) {
											out.print("代局指测量主管");
										}
										if ("djz_workMinister".equals(nextMan)) {
											out.print("代局指工程部长");
										}
										if ("djz_projectEngineer".equals(nextMan)) {
											out.print("代局指项目总工");
										}
										if ("cltechofficor".equals(nextMan)) {
											out.print("测量主管");
										}
										if ("shr".equals(nextMan)) {
											out.print("设计文件审核人");
										}
								%>
								</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;">
									<select id="taskExcuter" name="busBaseEntity[0].taskExcuter"
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
												if ("yjxz".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='yjxz'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
												if ("zlsqr".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='zlsqr'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
												if ("zlxzzycy".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='zlxzzycy'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
												if ("jjr".equals(nextMan)) {
										%>
										<%-- 		<s:iterator value="#session.project.projectUsers" var="man">
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
											}
												if ("ALL".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<option value="<s:property value='#man.user.id' />"><s:property
													value='#man.user.chinesename' />[
												<s:property value='#man.role.name' />]
											</option>
										</s:iterator>

										<%
											}
												if ("Borrower".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='jyr'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
												if ("gsjsbfzr".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='gsjsbfzr'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
												if ("Departmentresponsiblepers".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='gsjsbfzr'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											} else if ("djz_cltechofficor".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='djzclzg'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											} else if ("djz_workMinister".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='djzgcbz'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											} else if ("djz_projectEngineer".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='djzzgcs'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											} else if ("cltechofficor".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='jczzg'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											} else if ("shr".equals(nextMan)) {
										%>
										<s:iterator value="#session.project.projectUsers" var="man">
											<s:if test="#man.role.shortName=='shr'">
												<option value="<s:property value='#man.user.id' />"><s:property
														value='#man.user.chinesename' /></option>
											</s:if>
										</s:iterator>
										<%
											}
										%>

								</select>
								</td>
							</tr>
							<%
								//技术交接流程
									if ("jsgljd_02".equals(wfKey)) {
							%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">技术管理交底分类:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;">
									<select id="jsgljdfl" name="busBaseEntity[0].jsgljdfl"
									disabled="disabled">
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
							<%-- <%
								//技术交接流程
									if ("jsgzjj_09_index".equals(wfKey)) {
							%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">选择交接人:</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;">
									<select id="bjjr" name="busBaseEntity[0].bjjr" disabled>
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
							<%
								}
							%>
							<%
								if ("kzwfcgl_30_process".equals(wfKey) && "1".equals(nextState) && !"documenter".equals(nextMan)) {
							%>
							<tr>
								<th>请选择技术主管：</th>
								<td colspan="12" style="height: 32px; border: 1px solid gray;"><s:iterator
										value="#session.project.projectUsers" var="man">
										<s:if test="#man.role.shortName=='jszg'">
											<input type="checkbox" name="users"
												value="<s:property value='#man.user.id' />" />
											<s:property value='#man.user.chinesename' />
										</s:if>
									</s:iterator></td>
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
									disabled="disabled" id="fwdw" name="busBaseEntity[0].fwdw"
									class="dfinput" /></td>
							</tr>
							<%
								}
							%>
							<tr>
								<th style="height: 32px; border: 1px solid gray;">意见:</th>
								<td colspan="12" style="border: 1px solid gray;"><textarea
										id="comment" name="comment" cols="80" rows="6"></textarea></td>
							</tr>
							<tr>
								<td colspan="13" align="center"
									style="height: 40px; border: 1px solid gray;"><input
									id="okBtn1" type="button" class="btn" value="提交" /> <%
 	if ("Y".equals(sfBack)) {
 %> <input id="okBtn2" type="button" class="btn" value="驳回" /> <%
 	}
 %> <input type="button" class="btn" onclick="history.go(-1)" value="返回" />
									<%
										if ("gxjsjd_36_index".equals(wfKey) || "aqjsjd_36_index".equals(wfKey) || "szgl_18_index".equals(wfKey)
												|| "sgfabzjh_19_do".equals(wfKey)) {
											if ("99".equals(nextState)) {
									%> <input type="button" class="btn" id="printBtn" value="打印" />
									<%
										}
										}
									%></td>
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
									<td colspan="4" style="height: 32px; border: 1px solid gray;">尚未选择任何文档</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div id="fujianListsDiv" style="display: none;">
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
									<td colspan="4" style="height: 32px; border: 1px solid gray;">尚未上传任何文件</td>
								</tr>
							</tbody>
						</table>
					</div>

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

	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery-migrate-1.2.1.min.js"></script>
	<script type="text/javascript" src="js/jquery.jqprint-0.3.js"></script>
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
	<script type="text/javascript" src="js/bus-common-work.js"></script>
	<script type="text/javascript" src="js/bus-common-work-tree.js"></script>
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
</body>

</html>