var wfKey = $("#wfKey").val();
var sfEdit = $("#sfEdit").val();
var sfUpload = $("#sfUpload").val();
var nextMan = $("#nextMan").val();
var isChild = $("#isChild").val();
var moduleId = $("#moduleId").val();
var projectId = $("#projectId").val();
var sfTz = $("#sfTz").val();
var nodes = $("#nodes").val();
var docLists = null;
var k = 0;

var options = {
	url : 'busCommonAction!saveCommonApply.huzd',
	type : 'post',
	dataType : 'json',
	data : $("#addDataForm").serialize(), // 带附件的提交表单
	success : function(data) {
		if (true == data.flag) {
			art.dialog({
				id : 'ID-SUCCESS',
				title : '系统提示',
				content : '操作成功！'
			});
			history.go(-1);
		} else {
			art.dialog({
				id : 'ID-ERROR',
				title : '系统提示',
				content : data.message
			});
		}
	}
};

var i = 0;
function addDetail4jsjd() {
	$("#tablelist > tbody > tr[class*='tip_']").hide();
	var id = new Date().getTime();
	i++;
	var html = "<tr id=\"" + id + "\">";
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"  colspan=\"2\"><input name=\"busBaseEntity["
			+ i
			+ "].gcdwmc\" id=\"gcdwmc_"
			+ id
			+ "\" type=\"text\" value=\"\"   class=\"dfinput\" style=\"width:100%\" /></td>";
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"  colspan=\"2\"><input name=\"busBaseEntity["
			+ i
			+ "].fbfxmc\" id=\"fbfxmc_"
			+ id
			+ "\" type=\"text\" value=\"\"   class=\"dfinput\" style=\"width:100%\" /></td>";
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"  colspan=\"2\"><input name=\"busBaseEntity["
			+ i
			+ "].jsjdmc\" id=\"jsjdmc_"
			+ id
			+ "\" type=\"text\" value=\"\"   class=\"dfinput\" style=\"width:100%\" /></td>";
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"><select name=\"busBaseEntity["
			+ i
			+ "].zrrenid\" id=\"zrrenid_"
			+ id
			+ "\"  class=\"dfinput\" style=\"height:32px;width:100%\" onchange=\"getLxfs("
			+ id + ")\">" + $("#p_xxxxx").html() + "</select></td>";
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"   colspan=\"2\"><input name=\"busBaseEntity["
			+ i
			+ "].lxfs\" id=\"lxfs_"
			+ id
			+ "\" type=\"text\" value=\"\"   class=\"dfinput\" style=\"width:100%\"/></td>";
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"   colspan=\"2\"><input name=\"busBaseEntity["
			+ i
			+ "].jhwcsj2\" id=\"jhwcsj2_"
			+ id
			+ "\" type=\"text\" value=\"\" class=\"dfinput Wdate\"  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',maxDate:'2025-10-01'})\" style=\"height:32px;width:100%\"/></td>";
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
			+ i
			+ "].remark2\" id=\"remark2_"
			+ id
			+ "\" type=\"text\" value=\"\"   class=\"dfinput\" style=\"width:100%\"/></td>";
	html += "<td   style=\"height: 32px; border: 1px solid gray;\" class='tool_btns'><a class='delete_btn'>&nbsp;</a></td></tr>";
	$("#tablelist tbody").append(html);
	getLxfs(id);
}

function changeZrr(id) {
	var a = $("#fadj_" + id).val();
	if (a == 3) {
		$("#zrrenid_" + id).html($("#jszg").html());
	} else {
		$("#zrrenid_" + id).html($("#gcbz").html());
	}
}

function addDetail4szgl(wfKey) {
	$("#tablelist > tbody > tr[class*='tip_']").hide();
	var id = new Date().getTime();
	i++;
	var html = "<tr id=\"" + id + "\">";
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
			+ i
			+ "].gcdwmc\" id=\"gcdwmc_"
			+ id
			+ "\" type=\"text\" value=\"\"   class=\"dfinput\" style=\"width:100%\"/></td>";
	if (wfKey == "szgl_18_index") {
		html += "<td  style=\"height: 32px; border: 1px solid gray;\"><select name=\"busBaseEntity["
				+ i
				+ "].fadj\" id=\"fadj_"
				+ id
				+ "\"  class=\"dfinput\" style=\"height:32px;width:100%\" onchange=\"changeZrr("
				+ id
				+ ")\" ><option value=\"\">-请选择-</option><option value=\"1\">I级</option><option value=\"2\">II级</option><option value=\"4\">特级</option></select></td>";

	} else {
		html += "<td  style=\"height: 32px; border: 1px solid gray;\"><select name=\"busBaseEntity["
				+ i
				+ "].fadj\" id=\"fadj_"
				+ id
				+ "\"  class=\"dfinput\" style=\"height:32px;width:100%\" onchange=\"changeZrr("
				+ id
				+ ")\" ><option value=\"\">-请选择-</option><option value=\"1\">I级</option><option value=\"2\">II级</option><option value=\"3\">III级</option><option value=\"4\">特级</option></select></td>";

	}
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"><select name=\"busBaseEntity["
			+ i
			+ "].zrrenid\" id=\"zrrenid_"
			+ id
			+ "\"  class=\"dfinput\" style=\"height:32px;width:100%\"></select></td>";
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
			+ i
			+ "].jhwcsj2\" id=\"jhwcsj2_"
			+ id
			+ "\" type=\"text\" value=\"\"  class=\"dfinput Wdate\"  onclick=\"WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d',maxDate:'2025-10-01'})\"  style=\"width:100%\"/></td>";
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
			+ i
			+ "].sjwcsj2\" id=\"sjwcsj2_"
			+ id
			+ "\" type=\"text\" readonly=\"readonly\" value=\"\"    style=\"width:100%\"/></td>";
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
			+ i
			+ "].xmjlb\" id=\"xmjlb_"
			+ id
			+ "\" type=\"text\" value=\"\" class=\"dfinput\" style=\"width:100%\"/></td>";
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
			+ i
			+ "].jianli\" id=\"jianli_"
			+ id
			+ "\" type=\"text\" value=\"\"   class=\"dfinput\" style=\"width:100%\"/></td>";
	html += "<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
			+ i
			+ "].yezhu\" id=\"yezhu_"
			+ id
			+ "\" type=\"text\" value=\"\"   class=\"dfinput\" style=\"width:100%\"/></td>";

	html += "<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
			+ i
			+ "].zigs\" id=\"zigs_"
			+ id
			+ "\" type=\"text\" value=\"\"   class=\"dfinput\" style=\"width:100%\"/></td>";

	html += "<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
			+ i
			+ "].gsi\" id=\"gsi_"
			+ id
			+ "\" type=\"text\" value=\"\"   class=\"dfinput\" style=\"width:100%\"/></td>";

	html += "<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
			+ i
			+ "].gfgs\" id=\"gfgs_"
			+ id
			+ "\" type=\"text\" value=\"\"   class=\"dfinput\" style=\"width:100%\"/></td>";

	html += "<td  style=\"height: 32px; border: 1px solid gray;\"><input name=\"busBaseEntity["
			+ i
			+ "].remark2\" id=\"remark2_"
			+ id
			+ "\" type=\"text\" value=\"\"   class=\"dfinput\" style=\"width:100%\"/></td>";
	html += "<td   style=\"height: 32px; border: 1px solid gray;\" class='tool_btns'><a class='delete_btn'>&nbsp;</a></td></tr>";
	$("#tablelist tbody").append(html);
}

$(".delete_btn").live("click", function() {
	$(this).parent().parent().remove();
	if ($("#tablelist > tbody > tr").length == 1) {
		$("#tablelist > tbody > tr[class*='tip_']").show();
	}
});

function initTempFiles4add(moduleId, projectId, wfKey, state, ywid) {
	var innerHtml = "";
	$
			.ajax({
				type : 'POST',
				url : 'tempFileAction!getTmpFiles4Add.huzd',
				data : {
					moduleId : moduleId,
					wfKey : wfKey,
					projectId : projectId,
					state : state,
					ywid : ywid
				},
				async : false,
				dataType : 'json',
				success : function(msg) {
					if (msg != '') {
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
													+ " <a  href=\"#\" onclick=\"deleteFileByFileName4Temp('"
													+ n.fileName + "','" + n.id + "');return false;\">删除</a> </td></tr>");

										});

					} else {
						innerHtml += "<tr><td colspan='4'>无数据</td></tr>";
					}
				}
			});

	$("#docLists>tbody").html(innerHtml);
	$('#docLists tbody tr:odd').addClass('odd');
}
/**
 * 初始
 */
$(function() {
	var sfEdit = $("#sfEdit").val();
	var sfUpload = $("#sfUpload").val();
	if (sfEdit == "Y") {
		document.getElementById("docListsDiv").style.display = "block";
		// alert(moduleId + "----" + projectId + "----" + wfKey);
		initTempFiles4add(moduleId, projectId, wfKey);
	}
	if (sfUpload == "Y") {
		document.getElementById("fujianListsDiv").style.display = "block";
	}

	// $("#addBtn").one("click", function() {
	$("#addBtn").on("click", function() {

		if ("jxsbxqjh_17_do" != wfKey && "swtz_33_process" != wfKey) {
			var taskExcuter = $("#taskExcuter").val();
			if (taskExcuter == "" || taskExcuter == null) {
				art.dialog({
					id : 'ID-ERROR',
					title : '系统提示',
					content : '请选择下一步操作人！',
					time : 2000
				});
				return;
			}

			var jhkssj = $("#jhkssj").val();
			if (jhkssj == "" || jhkssj == null) {
				art.dialog({
					id : 'ID-ERROR',
					title : '系统提示',
					content : '计划开始时间不能为空！',
					time : 2000
				});
				return;
			}

			var jhwcsj1 = $("#jhwcsj1").val();
			if (jhwcsj1 == "" || jhwcsj1 == null) {
				art.dialog({
					id : 'ID-ERROR',
					title : '系统提示',
					content : '计划完成时间不能为空！',
					time : 2000
				});
				return;
			}
			if (wfKey == "jsgljd_02") {
				var jsgljdfl = $("#jsgljdfl").val();
				if (jsgljdfl == "" || jsgljdfl == null) {
					art.dialog({
						id : 'ID-ERROR',
						title : '系统提示',
						content : '请选择技术管理交底分类！',
						time : 2000
					});
					return;
				}
			}
		}

		// var remark = $("#remark").val();
		// if (remark == "" || remark == null) {
		// art.dialog({
		// id : 'ID-ERROR',
		// title : '系统提示',
		// content : '备注不能为空！',
		// time : 2000
		// });
		// return;
		// }
		$("#doType").val("add");
		$("#addDataForm").ajaxSubmit(options);
	});

	$("#submitWfBtn").on("click", function() {
		var taskExcuter = $("#taskExcuter").val();
		if ("jxsbxqjh_17_do" != wfKey && "swtz_33_process" != wfKey) {
			if (taskExcuter == "" || taskExcuter == null) {
				art.dialog({
					id : 'ID-ERROR',
					title : '系统提示',
					content : '请选择下一步操作人！',
					time : 2000
				});
				return;
			}
			var jhkssj = $("#jhkssj").val();
			if (jhkssj == "" || jhkssj == null) {
				art.dialog({
					id : 'ID-ERROR',
					title : '系统提示',
					content : '计划开始时间不能为空！',
					time : 2000
				});
				return;
			}

			var jhwcsj1 = $("#jhwcsj1").val();
			if (jhwcsj1 == "" || jhwcsj1 == null) {
				art.dialog({
					id : 'ID-ERROR',
					title : '系统提示',
					content : '计划完成时间不能为空！',
					time : 2000
				});
				return;
			}
			if (wfKey == "jsgljd_02") {
				var jsgljdfl = $("#jsgljdfl").val();
				if (jsgljdfl == "" || jsgljdfl == null) {
					art.dialog({
						id : 'ID-ERROR',
						title : '系统提示',
						content : '请选择技术管理交底分类！',
						time : 2000
					});
					return;
				}
			}
		}

		// var remark = $("#remark").val();
		// if (remark == "" || remark == null) {
		// art.dialog({
		// id : 'ID-ERROR',
		// title : '系统提示',
		// content : '备注不能为空！',
		// time : 2000
		// });
		// return;
		// }
		$("#doType").val("create");
		$("#addDataForm").ajaxSubmit(options);
	});

	$("#addFiles").click(function() {
		addFile();
	});

	$("#addDetail4jsjd").click(function() {
		addDetail4jsjd();
	});

	$("#addDetail4szgl").click(function() {
		addDetail4szgl(wfKey);
	});

});