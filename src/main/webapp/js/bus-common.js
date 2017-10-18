/**
 * 
 * 公共方法js
 * 
 */

function setValue(zrrenid, projectUsers) {
	var options = "";
	$.each(projectUsers, function(i, n) {
		if (n.role.name == '技术员') {
			if (n.user.id == zrrenid) {
				options += "<option value=" + n.user.id + " selected=selected>"
						+ n.user.chinesename + "</option>";
			} else {
				options += "<option value=" + n.user.id + ">"
						+ n.user.chinesename + "</option>";
			}
		}
	});
	return options;
}

/*
 * 自动查询责任人的联系电话
 */
function getLxfs(id) {
	// alert(id);
	var zrrenid = $("#zrrenid_" + id).val();
	// alert(zrrenid);
	$.ajax({
		type : 'POST',
		url : 'userAction!queryLxfsByUserId.huzd',
		data : {
			zrrenid : zrrenid
		},
		async : true,
		dataType : 'json',
		success : function(msg) {
			if (msg.flag) {
				$("#lxfs_" + id).val(msg.message);
				return true;
			}
		}
	});
}
/**
 * 点击下载按钮
 */
function download22(id) {
	window.location.href = "busBaseAction!download.huzd?fileEntity.id=" + id;
}

/**
 * 设置下拉菜单的值
 * 
 * @param selectId
 * @param checkValue
 */
function setSelectChecked(selectId, checkValue) {
	var select = document.getElementById(selectId);
	for (var i = 0; i < select.options.length; i++) {
		if (select.options[i].value == checkValue) {
			select.options[i].selected = true;
			break;
		}
	}
}
function initFileExt(baseId) {
	$
			.ajax({
				type : 'POST',
				url : 'busBaseAction!getFileExtList.huzd',
				data : {
					'busBaseEntity[0].id' : baseId
				},
				async : true,
				dataType : 'json',
				success : function(msg) {
					if (msg != '') {
						var innerHtml = "";
						$
								.each(
										msg.data,
										function(i, n) {
											innerHtml += ("<tr>");
											innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ n.id + "</td>");
											innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ n.fileName + "</td>");
											innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ n.scsj + "</td>");
											innerHtml += ("<td  style=\"height: 32px; border: 1px solid gray;text-align:center\"><a href=\"#\" onclick=\"javascript:download22("
													+ n.id
													+ ");\">下载</a> "
													+ "<a href=\"#\" onclick=\"javascript:deleteItem("
													+ n.id + "," + baseId + ");\">删除</a></td></tr>");
										});
						$("#fujianLists>tbody").html(innerHtml);
						$('#fujianLists tbody tr:odd').addClass('odd');
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

function initDoneHistorys(wfkey, processInstanceId) {
	$
			.ajax({
				type : 'POST',
				url : 'workflowAction!listHistoryCommentWithProcessInstanceId.huzd',
				data : {
					'wfkey' : wfkey,
					'processInstanceId' : processInstanceId
				},
				async : true,
				dataType : 'json',
				success : function(msg) {
					if (msg.commentsList != '') {
						var innerHtml = "";
						$
								.each(
										msg.commentsList,
										function(i, n) {
											innerHtml += ("<tr>");
											innerHtml += ("<td style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ n.time + "</td>");
											innerHtml += ("<td style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ n.userid + "</td>");
											innerHtml += ("<td style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ n.taskid + "</td>");
											innerHtml += ("<td style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ n.act_name_ + "</td>");
											innerHtml += ("<td style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ n.assingneeName_ + "</td>");
											innerHtml += ("<td style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ n.start_time_ + "</td>");
											innerHtml += ("<td style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ n.end_time_ + "</td>");
											innerHtml += ("<td title='"
													+ n.message
													+ "' style=\"height: 32px; border: 1px solid gray;text-align:center\">"
													+ (n.message.length > 20 ? (n.message
															.substring(0, 20) + "...")
															: n.message) + "</td>");
											/*
											 * <td style=\"height: 32px; border: 1px solid gray;text-align:center\">" +
											 * n.message + "</td> innerHtml
											 * +=("<td   style=\"height: 32px; border: 1px solid gray;text-align:center\">"+n.message+"</td>");
											 */
											innerHtml += ("</tr>");
										});
						$("#commmentss>tbody").html(innerHtml);
						$('#commmentss tbody tr:odd').addClass('odd');
					}
				}
			});
}
/**
 * 物理删除附件及关系
 */
function deleteItem(fileId, baseId) {
	art.confirm('你确定要删除？后果可能很严重...', function() {
		$.ajax({
			type : 'POST',
			url : 'busBaseAction!deleteExtFile.huzd',
			data : {
				'fileId' : fileId
			},
			async : false,
			dataType : 'json',
			success : function(msg) {
				if (true == msg.flag) {
					initFileExt(baseId);
					art.dialog({
						id : 'ID-SUCCESS',
						title : '系统提示',
						content : '删除成功！',
						time : 2000
					});
				} else {
					art.dialog({
						id : 'ID-ERROR',
						title : '系统提示',
						content : '删除出错请重试！',
						time : 2000
					});
				}
			}
		});
	}, function() {
		return true;
	});
}
/**
 * 获取当前操作的时间
 */
function getNowFormatDate() {
	var date = new Date();
	var seperator1 = "-";
	var seperator2 = ":";
	var month = date.getMonth() + 1;
	var strDate = date.getDate();
	if (month >= 1 && month <= 9) {
		month = "0" + month;
	}
	if (strDate >= 0 && strDate <= 9) {
		strDate = "0" + strDate;
	}
	var currentdate = date.getFullYear() + seperator1 + month + seperator1
			+ strDate + " " + date.getHours() + seperator2 + date.getMinutes()
			+ seperator2 + date.getSeconds();
	return currentdate;
}

/**
 * 点击下载按钮
 */
function download(id) {
	window.location.href = "busBaseAction!download.huzd?fileEntity.id=" + id;
}
/**
 * 点击查看流程审批轨迹
 */
function openListCommentDialog(processInstanceId) {
	window.location.href = 'common_wf_hislist.jsp?processInstanceId='
			+ processInstanceId + '&wfKey=' + wfKey;
}
/**
 * 打开在线编辑器
 */
function openFile4Edit(url) {
	window.open(url, '_blank');
}
/**
 * 点击文档树提示
 */
function treeClickEvent() {
	// alert("请☑️");
}

/**
 * 点击增加附件
 */
function addFile() {
	$("#fujianLists > tbody > tr[class*='tip_']").hide();
	var id = new Date().getTime();
	var html = "<tr id=\"" + id + "\">";
	html += "<td style=\"height: 32px; border: 1px solid gray;\" colspan=\"3\"><input type=\"file\" name=\"filedata\"/></td>";
	html += "<td style=\"height: 32px; border: 1px solid gray;\" class='tool_btns'><a class='delete_btn'>&nbsp;</a></td></tr>";
	$("#fujianLists tbody").append(html);
}

/**
 * 只适用于新增页面 用户删除临时 在线 编辑文件
 * 
 * @param fileName
 * @param id
 */
function deleteFileByFileName4Temp(fileName, id) {

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
				// window.location.reload();
				initTempFiles4add( $("#moduleId").val(), $("#projectId").val(), $("#wfKey").val(), "add", "");
			}
		}
	});
}
 

/**
 * 适用于 新增页面 选择模版文档之后 将文档文件 直接复制到项目的 临时文件夹
 * 
 * @param filePath
 * @param fileName
 * @param fileType
 */
function saveToTemp(filePath, fileName, fileType) {
	$.ajax({
		type : 'POST',
		url : 'tempFileAction!saveEntity.huzd',
		data : {
			'moduleId' : moduleId,
			'projectId' : projectId,
			'tempFileEntity.filePath' : filePath,
			'tempFileEntity.fileType' : fileType,
			'tempFileEntity.fileName' : fileName
		},
		async : false,
		dataType : 'json',
		success : function(msg) {
			if (msg.flag) {
				$("#tempFilePath").val(msg.message);
			}
		}
	});
}