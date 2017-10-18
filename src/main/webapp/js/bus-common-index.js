var tableList = null;
var wfKey = $("#wfKey").val();
var sfEdit = $("#sfEdit").val();
var sfUpload = $("#sfUpload").val();
var nextMan = $("#nextMan").val();
var isChild = $("#isChild").val();
var nodes = $("#nodes").val();
var sfTz = $("#sfTz").val();
var taskId = $("#taskId").val();
var projectId = $("#projectId").val();
// alert(projectId);

/** 表格填充函数* */

function deleteApply(id, projectId) {
	art.confirm('你确定要删除？后果可能很严重...', function() {
		showMask();
		$.ajax({
			type : 'POST',
			url : 'busBaseAction!deleteApply.huzd',
			data : {
				id : id,
				projectId : projectId
			},
			async : false,
			dataType : 'json',
			success : function(msg) {
				hideMask(); 
				if (true == msg.flag) {
					art.dialog({
						id : 'ID-SUCCESS',
						title : '系统提示',
						content : '删除成功！',
						time : 2000
					});
					location.reload();
				} else {
					art.dialog({
						id : 'ID-ERROR',
						title : '系统提示',
						content : '删除出错请重试！',
						time : 2000
					});
					location.reload();
				}
			}
		});
	}, function() {
		return true;
	});
}

function startApply(id, projectId) {
	art.confirm('你确定提交流程吗...', function() {
		showMask();
		$.post("busCommonAction!startApply.huzd", {
			'busBaseEntity[0].id' : id,
			'projectId' : projectId
		}, function(result) {
			hideMask(); 
			location.reload(); 
			if (true == result.flag) {
				art.dialog({
					title : '系统提示',
					content : result.message
				});
			} else {
				art.dialog({
					title : '系统提示',
					content : result.message
				});
			}
		}, "json");
	});
}

// alert(wfKey);
function getQueryParam() {
	return {
		'wfKey' : wfKey,
		'projectId' : $("#projectId").val()
	};
}

$(document).ready(
		function() {
			$.ajax({
				type : 'POST',
				url : 'busBaseAction!getModuleIdByWfKey.huzd',
				data : {
					'wfKey' : wfKey
				},
				async : false,
				dataType : 'json',
				success : function(msg) {
					if (true == msg.flag) {
						$("#moduleId").val(msg.message);
					}
				}
			});
			/**
			 * 初始化申请列表
			 */
			tableList = $(".tablelist").page({
				prefix : '',
				url : 'busBaseAction!listApplys.huzd',
				pageSize : [ 10, 5, 20, 30 ],
				queryBtn : '',
				param : getQueryParam,
				fillTable : fillTable
			});

			$("#addddddd").click(
					function() {
						var url = "";
						var moduleId = $("#moduleId").val();
						url = "bus-common-add.jsp?wfKey=" + wfKey
								+ '&moduleId=' + moduleId + '&sfEdit=' + sfEdit
								+ '&sfUpload=' + sfUpload + '&nextMan='
								+ nextMan + '&isChild=' + isChild + '&sfTz='
								+ sfTz + '&nodes=' + nodes;
						window.location.href = url;
					});
		});

function editApply(id, projectId,state) {
	var moduleId = $("#moduleId").val();
	var taskId = $("#taskId").val();
	var url = 'bus-common-edit.jsp?wfKey=' + wfKey + '&moduleId=' + moduleId
			+ '&sfEdit=' + sfEdit + '&sfUpload=' + sfUpload + '&nextMan='
			+ nextMan + '&isChild=' + isChild + '&sfTz=' + sfTz + '&nodes='
			+ nodes + '&taskId=' + taskId + '&previosState=0&id=' + id
			+ '&projectId=' + projectId+'&state='+state;
	url = encodeURI(encodeURI(url));
	window.location.href = url;
}