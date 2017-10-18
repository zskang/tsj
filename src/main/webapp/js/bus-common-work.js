/*
 * 工作流处理 js
 * 
 */

/**
 * 点击下载按钮
 */
function download22(id) {
	window.location.href = "busBaseAction!download.huzd?fileEntity.id=" + id;
}

var k = 0;
/** 表格填充函数* */
function fillTable4jsjd(data) {
	var innerHtml = "";
	if (data.entityList && data.entityList.length > 0) {
		$("#tablelist > tbody > tr[class*='tip_']").hide();
		var cellVal = "";
		$
				.each(
						data.entityList,
						function(i, n) {
							var id = new Date().getTime();
							innerHtml += ("<tr>");
							innerHtml += ("<td style=\"height: 32px; border: 1px solid gray;\" colspan=\"2\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].gcdwmc\" id=\"gcdwmc_"
									+ id
									+ "\" type=\"text\" value='"
									+ (n.gcdwmc.length > 6 ? (n.gcdwmc
											.substring(0, 6) + "...")
											: n.gcdwmc) + "' alt='" + n.gcdwmc + "'  class=\"dfinput\" style=\"width:100%\" readonly=\"readonly\"/></td>");
							innerHtml += "<td  style=\"height: 32px; border: 1px solid gray;\"  colspan=\"2\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].fbfxmc\" id=\"fbfxmc_"
									+ id
									+ "\" type=\"text\" value='"
									+ (n.fbfxmc.length > 6 ? (n.fbfxmc
											.substring(0, 6) + "...")
											: n.fbfxmc)
									+ "' alt='"
									+ n.fbfxmc
									+ "' class=\"dfinput\" style=\"width:100%\" /></td>";
							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\"  colspan=\"2\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].jsjdmc\" id=\"jsjdmc_"
									+ id
									+ "\" type=\"text\" value='"
									+ (n.jsjdmc.length > 6 ? (n.jsjdmc
											.substring(0, 6) + "...")
											: n.jsjdmc) + "'  alt='" + n.jsjdmc + "'  class=\"dfinput\" style=\"width:100%\"  readonly=\"readonly\"/></td>");
							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\">"
									+ "<select name=\"busBaseEntity["
									+ (i + 1)
									+ "].zrrenid\" id=\"zrrenid_"
									+ id
									+ "\"  class=\"dfinput\" style=\"height:32px;\"  onchange=\"getLxfs("
									+ id
									+ ")\" >"
									+ setValue(n.zrrenid, data.projectUsers) + "</select></td>");
							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\" colspan=\"2\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].lxfs\" id=\"lxfs_"
									+ id
									+ "\" type=\"text\" value='"
									+ (n.lxfs.length > 6 ? (n.lxfs.substring(0,
											6) + "...") : n.lxfs)
									+ "'  alt='"
									+ n.lxfs + "'   class=\"dfinput\" style=\"width:100%\"  readonly=\"readonly\"/></td>");
							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\"  colspan=\"2\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].jhwcsj2\" id=\"jhwcsj2_"
									+ id
									+ "\" type=\"text\"  value='"
									+ (n.jhwcsj2.length > 10 ? (n.jhwcsj2
											.substring(0, 10) + "...")
											: n.jhwcsj2)
									+ "'   alt='"
									+ n.jhwcsj2 + "' class=\"dfinput\" style=\"height:32px;width:100%\"  readonly=\"readonly\"/></td>");
							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].remark2\" id=\"remark2_"
									+ id
									+ "\" type=\"text\" value='"
									+ (n.remark2.length > 10 ? (n.remark2
											.substring(0, 10) + "...")
											: n.remark2)
									+ "'   alt='"
									+ n.remark2 + "'   class=\"dfinput\" style=\"width:100%\"  readonly=\"readonly\"/></td>");
							innerHtml += ("<td class='tool_btns'  style=\"height: 32px; border: 1px solid gray;\"></td></tr>");
						});
	} else {
		if (data.message)
			art.dialog({
				title : '系统提示',
				content : data.message
			});
		innerHtml += "<tr><td colspan='13'>无数据</td></tr>";
	}
	k = data.entityList.length;
	$("#tablelist>tbody").html(innerHtml);
	$('#tablelist tbody tr:odd').addClass('odd');
}

function changeZrr(id) {
	var a = $("#fadj_" + id).val();
	if (a == 3) {
		$("#zrrenid_" + id).html($("#jszg").html());
	} else {
		$("#zrrenid_" + id).html($("#gcbz").html());
	}
}
//
// function setFadj(v) {
// 	var options = "";
// 	options += "<option value=\"\">-请选择-</option>";
// 	if (v == 1) {
// 		options += "<option value=\"1\" selected=\"selected\">I级</option><option value=\"2\">II级</option><option value=\"3\">III级</option><option value=\"4\">特级</option>";
// 	} else if (v == 2) {
// 		options += "<option value=\"1\" >I级</option><option value=\"2\" selected=\"selected\">II级</option><option value=\"3\">III级</option><option value=\"4\">特级</option>";
// 	} else if (v == 3) {
// 		options += "<option value=\"1\" >I级</option><option value=\"2\">II级</option><option value=\"3\" selected=\"selected\">III级</option><option value=\"4\">特级</option>";
// 	} else if (v == 4) {
// 		options += "<option value=\"1\" >I级</option><option value=\"2\">II级</option><option value=\"3\">III级</option><option value=\"4\" selected=\"selected\">特级</option>";
// 	}
// 	return options;
// }




function setFadj(v,wfKey) {
    var options = "";
    if (wfKey == "szgl_18_index") {
        options += "<option value=\"\">-请选择-</option>";
        if (v == 1) {
            options += "<option value=\"1\" selected=\"selected\">I级</option><option value=\"2\">II级</option><option value=\"4\">特级</option>";
        } else if (v == 2) {
            options += "<option value=\"1\" >I级</option><option value=\"2\" selected=\"selected\">II级</option><option value=\"4\">特级</option>";
        } else if (v == 4) {
            options += "<option value=\"1\" >I级</option><option value=\"2\">II级</option><option value=\"4\" selected=\"selected\">特级</option>";
        }
    } else {
        options += "<option value=\"\">-请选择-</option>";
        if (v == 1) {
            options += "<option value=\"1\" selected=\"selected\">I级</option><option value=\"2\">II级</option><option value=\"3\">III级</option><option value=\"4\">特级</option>";
        } else if (v == 2) {
            options += "<option value=\"1\" >I级</option><option value=\"2\" selected=\"selected\">II级</option><option value=\"3\">III级</option><option value=\"4\">特级</option>";
        } else if (v == 3) {
            options += "<option value=\"1\" >I级</option><option value=\"2\">II级</option><option value=\"3\" selected=\"selected\">III级</option><option value=\"4\">特级</option>";
        } else if (v == 4) {
            options += "<option value=\"1\" >I级</option><option value=\"2\">II级</option><option value=\"3\">III级</option><option value=\"4\" selected=\"selected\">特级</option>";
        }
    }
    return options;
}


function setValue4szgl(zrrenid, projectUsers, id, fadj_v) {
    var options = "";
    // alert(fadj_v+"--"+zrrenid);
    $.each(projectUsers, function (i, n) {
        if (fadj_v === "3") {
        	// alert(n.role.name);
            if ("技术主管" == n.role.name) { 
            	// alert(n.user.id+"---"+zrrenid);
                if (n.user.id == zrrenid) { 
                    options += "<option value=" + n.user.id
                        + " selected=selected>" + n.user.chinesename
                        + "</option>"; 
                } else {
                    options += "<option value=" + n.user.id + ">"
                        + n.user.chinesename + "</option>";
                }
            }
        } else {
            if (n.role.name == "工程部长") {
                if (n.user.id == zrrenid) {
                    options += "<option value=" + n.user.id
                        + " selected=selected>" + n.user.chinesename
                        + "</option>";
                } else {
                    options += "<option value=" + n.user.id + ">"
                        + n.user.chinesename + "</option>";
                }
            }
        }

    });
   // alert(options);
    return options;
}
/*
function setValue4szgl(zrrenid, projectUsers, id) {
	var options = "";
	var fadj_v = $("#fadj_" + id).val();

	$.each(projectUsers, function(i, n) {
		if (fadj_v == "3") {
			if (n.role.name == '技术主管') {
				if (n.user.id == zrrenid) {
					options += "<option value=" + n.user.id
							+ " selected=selected>" + n.user.chinesename
							+ "</option>";
				} else {
					options += "<option value=" + n.user.id + ">"
							+ n.user.chinesename + "</option>";
				}
			}
		} else {
			if (n.role.name == '工程部长') {
				if (n.user.id == zrrenid) {
					options += "<option value=" + n.user.id
							+ " selected=selected>" + n.user.chinesename
							+ "</option>";
				} else {
					options += "<option value=" + n.user.id + ">"
							+ n.user.chinesename + "</option>";
				}
			}
		}
	});
	return options;
}*/

function fillTable4szgl(data) {
	var wfKey=$("#wfKey").val();
	var innerHtml = "";
	if (data.entityList && data.entityList.length > 0) {
		$("#tablelist > tbody > tr[class*='tip_']").hide();
		var cellVal = "";
		$
				.each(
						data.entityList,
						function(i, n) {
							var id = new Date().getTime();
							innerHtml += ("<tr>");

							innerHtml += ("<td style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].gcdwmc\" id=\"gcdwmc_"
									+ id
									+ "\" type=\"text\" value='"
									+ (n.gcdwmc.length > 6 ? (n.gcdwmc
											.substring(0, 10) + "...")
											: n.gcdwmc) + "' alt='" + n.gcdwmc + "'   class=\"dfinput\" style=\"width:100%\"  disabled=\"disabled\"/></td>");

							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\">"
									+ "<select name=\"busBaseEntity["
									+ (i + 1)
									+ "].fadj\" id=\"fadj_"
									+ id
									+ "\"  class=\"dfinput\" style=\"height:32px;width:80px\" onchange=\"changeZrr("
									+ id
									+ ")\"  disabled=\"disabled\">"
									+ setFadj(n.fadj,wfKey) + "</select></td>");

							/*innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\">"
									+ "<select name=\"busBaseEntity["
									+ (i + 1)
									+ "].zrrenid\" id=\"zrrenid_"
									+ id
									+ "\"  disabled=\"disabled\" class=\"dfinput\" style=\"height:32px;width:80px\">"
									+ setValue4szgl(n.zrrenid,
											data.projectUsers, id) + "</select></td>");*/
                            innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\">"
                                + "<select name=\"busBaseEntity["
                                + (i + 1)
                                + "].zrrenid\" id=\"zrrenid_"
                                + id
                                + "\"  disabled=\"disabled\" class=\"dfinput\" style=\"height:32px;width:180px\">"
                                + setValue4szgl(n.zrrenid,
                                    data.projectUsers, id, n.fadj) + "</select></td>");

							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].jhwcsj2\" id=\"jhwcsj2_"
									+ id
									+ "\" type=\"text\"  value='"
									+ (n.jhwcsj2.length > 10 ? (n.jhwcsj2
											.substring(0, 10) + "...")
											: n.jhwcsj2)
									+ "' alt='"
									+ n.jhwcsj2 + "' class=\"dfinput\"   readonly=\"readonly\" style=\"height:32px;width:100%\"/></td>");

							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\" ><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].sjwcsj2\" id=\"sjwcsj2_"
									+ id + "\" type=\"text\" class=\"dfinput\"  readonly=\"readonly\" style=\"height:32px;width:100%\"/></td>");
							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].xmjlb\" id=\"xmjlb_"
									+ id
									+ "\" type=\"text\"  value='"
									+ (n.xmjlb.length > 6 ? (n.xmjlb.substring(
											0, 6) + "...") : n.xmjlb)
									+ "' alt='" + n.xmjlb + "'  class=\"dfinput\" style=\"width:100%\"/></td>");

							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].jianli\" id=\"jianli_"
									+ id
									+ "\" type=\"text\" value='"
									+ (n.jianli.length > 6 ? (n.jianli
											.substring(0, 6) + "...")
											: n.jianli) + "' alt='" + n.jianli + "' class=\"dfinput\" style=\"width:100%\" readonly=\"readonly\"/></td>");

							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].yezhu\" id=\"yezhu_"
									+ id
									+ "\" type=\"text\"   value='"
									+ (n.yezhu.length > 6 ? (n.yezhu.substring(
											0, 6) + "...") : n.yezhu)
									+ "' alt='" + n.yezhu + "' class=\"dfinput\"  style=\"height:32px;width:100%\" readonly=\"readonly\"/></td>");

							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].zigs\" id=\"zigs_"
									+ id
									+ "\" type=\"text\"  value='"
									+ (n.zigs.length > 6 ? (n.zigs.substring(0,
											6) + "...") : n.zigs)
									+ "' alt='"
									+ n.zigs + "' class=\"dfinput\"  style=\"height:32px;width:100%\" readonly=\"readonly\"/></td>");
							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].gsi\" id=\"gsi_"
									+ id
									+ "\" type=\"text\"  value='"
									+ (n.gsi.length > 6 ? (n.gsi
											.substring(0, 6) + "...") : n.gsi)
									+ "' alt='" + n.gsi + "' class=\"dfinput\"  style=\"height:32px;width:100%\" readonly=\"readonly\"/></td>");
							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].gfgs\" id=\"gfgs_"
									+ id
									+ "\" type=\"text\"   value='"
									+ (n.gfgs.length > 6 ? (n.gfgs.substring(0,
											6) + "...") : n.gfgs)
									+ "' alt='"
									+ n.gfgs + "' class=\"dfinput\" style=\"width:100%\" readonly=\"readonly\"/></td>");
							innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
									+ (i + 1)
									+ "].remark2\" id=\"remark2_"
									+ id
									+ "\" type=\"text\"  value='"
									+ (n.remark2.length > 6 ? (n.remark2
											.substring(0, 6) + "...")
											: n.remark2)
									+ "' alt='"
									+ n.remark2 + "'  class=\"dfinput\" style=\"width:100%\" readonly=\"readonly\"/></td>");
							innerHtml += ("<td class='tool_btns'  style=\"height: 32px; border: 1px solid gray;\"></td></tr>");
						});
	} else {
		if (data.message)
			art.dialog({
				title : '系统提示',
				content : data.message
			});
		innerHtml += "<tr><td colspan='13'>无数据</td></tr>";
	}
	$("#tablelist>tbody").html(innerHtml);
	$('#tablelist tbody tr:odd').addClass('odd');
}

/**
 * 查询条件
 * 
 * @returns {{[busBaseEntity[0].id]: jQuery}}
 */
function getQueryParam() {
	return {
		'busBaseEntity[0].id' : $("#id").val()
	};
}

function initTempFiles4EditAndWork(ywid, state) {
	$
			.ajax({
				type : 'POST',
				url : 'tempFileAction!getTmpFiles4EditAndWork.huzd',
				data : {
					ywid : ywid,
					state : state
				},
				async : false,
				dataType : 'json',
				success : function(msg) {
					if (msg != '') {
						var innerHtml = "";
						$
								.each(
										msg.data,
										function(i, n) {
											var filePath = n.filePath;
											filePath = filePath.replace(/\\/g,
													"\/");
											filePath = encodeURI(filePath);
											innerHtml += ("<tr>");
											innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ i + "</td>");
											innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ "<a  href=\'"
													+ filePath
													+ "' target=\"_blank\"\"> "
													+ n.fileName + " </a></td>");
											innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ n.createTime + "</td>");
											innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ " <a  href=\"#\" onclick=\"deleteFileByFileName4Temp2('"
													+ n.fileName + "','" + n.id + "','"+ywid+"');return false;\">删除</a> </td></tr>");
										});
						//alert(innerHtml);
						$("#docLists>tbody").html(innerHtml);
						$('#docLists tbody tr:odd').addClass('odd');
					} else {
						art.dialog({
							title : '系统提示',
							content : '查询出错请重试！',
							time : 2000
						});
					}
				}
			});
}

function deleteFileByFileName4Temp2(fileName, id,ywid) {
	//alert(ywid + "--222---" + fileName);
	$.ajax({
		type : 'POST',
		url : 'tempFileAction!deleteFileByFileName.huzd',
		data : {
			'moduleId' : $("#moduleId").val(),
			'projectId' : $("#projectId").val(),
			'tempFileEntity.fileName' : fileName,
			'tempFileEntity.id' : id
		},
		async : false,
		dataType : 'json',
		success : function(msg) {
			if (msg.flag) {
				initTempFiles4EditAndWork(ywid, 'progress');
			}
		}
	});
}

function print() {
	$("#tablelist").jqprint();
}
$(function() {
	var wfKey = $("#wfKey").val();
	var sfEdit = $("#sfEdit").val();
	var sfUpload = $("#sfUpload").val();
	var wfName = $("#wfName").val();
	var nextMan = $("#nextMan").val();
	var doName = $("#doName").val();
	var isChild = $("#isChild").val();
	var moduleId = $("#moduleId").val();
	var projectId = $("#projectId").val();
	var nextState = $("#nextState").val();
	var previoState = $("#previoState").val();
	var processInstanceId = $("#processInstanceId").val();
	var sfTz = $("#sfTz").val();
	var id = $("#id").val();
	var taskId = $("#taskId").val();
	if ("kzwfcgl_30_process" == wfKey && "1" == nextState
			&& "techofficor" == nextMan) {
		$("#selectTr").hide();
	}

	$("#addFiles").click(function() {
		addFile();
	});

	var tableList = null;
	var docLists = null;
	var fujianLists = null;
	var historyLists = null;
	var k = 0;
	var options = {
		url : 'busCommonAction!doWork.huzd',
		type : 'post',
		dataType : 'json',
		data : $("#addDataForm").serialize(),
		success : function(data) {
			if (true == data.flag) {
				art.dialog({
					id : 'ID-SUCCESS',
					title : '系统提示',
					content : '操作成功！',
					time : 2000
				});
				history.go(-1);
			} else {
				art.dialog({
					id : 'ID-SUCCESS',
					title : '系统提示',
					content : '操作失败！',
					time : 2000
				});
				history.go(-1);
			}
		}
	};

	$("#okBtn1").on("click", function() {
		art.confirm('你确定提交？', function() {
			if (nextState != '99') {
				var taskExcuter = $("#taskExcuter").val();
				if ($("#selectTr").is(":hidden") == false) {
					if (taskExcuter == "" || taskExcuter == null) {
						art.dialog({
							id : 'ID-ERROR',
							title : '系统提示',
							content : '请选择下一步操作人！',
							time : 2000
						});
						return;
					}
				}

			}
			var comment = $("#comment").val();
			if (comment == "" || comment == null) {
				art.dialog({
					id : 'ID-ERROR',
					title : '系统提示',
					content : '请填写意见！！',
					time : 2000
				});
				return;
			}
			$("#doType").val("ok");
			$("#addDataForm").ajaxSubmit(options);
		});
	});

	$("#okBtn2").on("click", function() {
		art.confirm('你确定驳回？', function() {
			var comment = $("#comment").val();
			if (comment == "" || comment == null) {
				art.dialog({
					id : 'ID-ERROR',
					title : '系统提示',
					content : '请填写驳回原因！！',
					time : 2000
				});
				return;
			}
			$("#doType").val("back");
			$("#addDataForm").ajaxSubmit(options);
		});
	});

	var sfEdit = $("#sfEdit").val();
	var sfUpload = $("#sfUpload").val();
	// if (sfEdit == "Y") {
	document.getElementById("docListsDiv").style.display = "block";
	initTempFiles4EditAndWork(id, "progress");
	// }
	// if (sfUpload == "Y") {
	document.getElementById("fujianListsDiv").style.display = "block";
	initFileExt(id);
	// alert(wfKey + "-----" + processInstanceId);
	initDoneHistorys(wfKey, processInstanceId);
	// }
	if (wfKey == "gxjsjd_36_index" || wfKey == "aqjsjd_36_index") {
		tableList = $("#detailTable").page({
			prefix : '',
			url : 'busBaseAction!listDetails.huzd?' + $.param(getQueryParam()),
			queryBtn : '',
			param : function() {
				return null;
			},
			fillTable : fillTable4jsjd
		});
	}

	if (wfKey == "szgl_18_index" || wfKey == "sgfabzjh_19_do") {
		tableList = $("#detailTable").page({
			prefix : 't2',
			url : 'busBaseAction!listDetails.huzd?' + $.param(getQueryParam()),
			queryBtn : '',
			param : function() {
				return null;
			},
			fillTable : fillTable4szgl
		});
	}

	$
			.post(
					"busBaseAction!getDetail.huzd",
					{
						'busBaseEntity[0].id' : $("#id").val()
					},
					function(result) {
						if (result.flag) {
							// alert(wfKey);
							$("#remark1").val(result.entityInfo.remark1);
							$("#jhkssj").val(result.entityInfo.jhkssj);
							$("#jhwcsj1").val(result.entityInfo.jhwcsj1);
							if ('99' != nextState) {
								setSelectChecked("taskExcuter",
										result.entityInfo.taskExcuter);
							} else {
								$("#taskExcuter").val("");
							}
							if (wfKey == "gxjsjd_36_index"
									|| wfKey == "aqjsjd_36_index") {
								$("#wdbh").val(result.entityInfo.wdbh);
								$("#bgbh").val(result.entityInfo.bgbh);
							}
							if (wfKey == "gxjsjd_36_do"
									|| wfKey == "aqjsjd_36_do") {
								$("#gcdwmc").val(result.entityInfo.gcdwmc);
								$("#jsjdmc").val(result.entityInfo.jsjdmc);
								setSelectChecked("zrrenid",
										result.entityInfo.zrrenid);
								$("#lxfs").val(result.entityInfo.lxfs);
								$("#jhwcsj2").val(result.entityInfo.jhwcsj2);
								$("#remark2").val(result.entityInfo.remark2);
							}
							if (wfKey == "wlwjgl_10_index") {
								$("#fwdw").val(result.entityInfo.fwdw);
							}
							if (wfKey == 'jsgljd_02') {
								setSelectChecked("jsgljdfl",
										result.entityInfo.jsgljdfl);
							}
							// if ("jsgzjj_09_index" == wfKey) {
							// setSelectChecked("bjjr",
							// result.entityInfo.bjjr);
							// }
							if (wfKey == "szgl_18_do"
									|| wfKey == "tjsgfa_20_index"
									|| wfKey == "IIIjsgfa_21_index") {
								$("#wdbh").val(result.entityInfo.wdbh);
								$("#jhwcsj2").val(result.entityInfo.jhwcsj2);
								$("#sjwcsj2").val(result.entityInfo.sjwcsj2);
								if (wfKey == "szgl_18_do") {
									$("#zrrenid").html($("#gcbz").html());
								}
								if (wfKey == "tjsgfa_20_index"
										|| wfKey == "IIIjsgfa_21_index") {
									if (result.entityInfo.fadj == '3') {
										$("#zrrenid").html($("#jszg").html());
									} else {
										$("#zrrenid").html($("#gcbz").html());
									}
								}
								setSelectChecked("zrrenid",
										result.entityInfo.zrrenid);
								setSelectChecked("fadj", result.entityInfo.fadj);
								$("#bgbh").val(result.entityInfo.bgbh);
								$("#gcdwmc").val(result.entityInfo.gcdwmc);
								$("#xmjlb").val(result.entityInfo.xmjlb);
								// $("#jianli").val(result.entityInfo.jianli);
								// $("#yezhu").val(result.entityInfo.yezhu);
								// $("#zigs").val(result.entityInfo.zigs);
								// $("#gsi").val(result.entityInfo.gsi);
								// $("#gfgs").val(result.entityInfo.gfgs);
								$("#remark2").val(result.entityInfo.remark2);
							}
						}
					});

	$(".delete_btn").live("click", function() {
		$(this).parent().parent().remove();
		if ($("#fujianLists > tbody > tr").length == 1) {
			$("#fujianLists > tbody > tr[class*='tip_']").show();
		}
	});

	$("#addDetail").click(function() {
		addFile();
	});

	$("#printBtn").click(function() {
		print();
	});

});