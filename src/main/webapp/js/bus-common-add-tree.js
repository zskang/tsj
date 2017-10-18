var processFlag = "";
$(function() {
	var moduleTree = null;
	var moduleId = $("#moduleId").val();
	var hiddenNodes = [];
	var url = "moduleAction!listFile.huzd?message=" + moduleId;
	
	if(wfKey == "jsyzlmb_process" || wfKey == "jszgzlmb_process" || wfKey == "gcbzzlmb_process" || wfKey == "xmzgzlmb_process"){
		url = "moduleAction!queryCompleteFiles.huzd?";
		processFlag = "1";
	}
	var setting = {
		check : {
			enable : true,
			nocheckInherit : false
		},
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
			url : url,
			autoParam : [ "id=moduleId", "name=name", "level=level" ]
		}
	};

	// 加载ztree树
	moduleTree = $.fn.zTree.init($("#moduleTree"), setting);
	flag = 0;
	// 查询按钮
	$("#queryUserBtn").bind("click", function() {
		// 显示上次搜索后背隐藏的结点
		moduleTree.showNodes(hiddenNodes);
		// 查找不符合条件的叶子节点
		var _keywords = $("#key").val();

		function filterFunc(node) {
			if (node.isParent || node.name.indexOf(_keywords) != -1)
				return false;
			return true;
		}

		// 用于展开树 原来的点击搜索不会展开
		if (_keywords.length > 0) {
			moduleTree.expandAll(true);
		} else {
			moduleTree.expandAll(false);
		}
		// 获取不符合条件的叶子结点
		hiddenNodes = moduleTree.getNodesByFilter(filterFunc);
		// 隐藏不符合条件的叶子结点
		moduleTree.hideNodes(hiddenNodes);
	});

	/*
	 * 选择文件之后 不管在新增页面或 编辑页面 只需刷新下当前页面 即可重新加载所选文件 不需要动态 添加页面行
	 */
	$("#jsPswEdit").click(function() {
		art.dialog({
			title : '选择文档进行在线编辑',
			content : document.getElementById('selectModuleDiv'),
			lock : true,
			okValue : '确认选择',
			ok : function() {
				var selectNodes = moduleTree.getCheckedNodes(true);
				if (selectNodes.length == 0) {
					art.dialog({
						title : '系统提示',
						content : '请选择文档！',
						time : 2000
					});
					return false;
				}
				var modules = ""; 
				$.each(selectNodes, function(i, n) {
					modules += n.id;
					if (i < selectNodes.length - 1)
						modules += ",";
				});
				$.ajax({
					type : 'POST',
					url : 'moduleAction!moduleFile.huzd',
					data : {
						message : modules,
						'processFlag' : processFlag
					},
					async : false,
					dataType : 'json',
					success : function(msg) {
						var fileUrl = "";
						$.each(msg.fileList, function(j, m) {
//						 	 alert(wfKey+"===="+m.fileName+"========"+m.filePath+"========"+m.docType+"========"+ $("#moduleId").val()+"========"+$("#projectId").val());
							if(m.filePath!="" &&  m.fileName!="" && m.docType!=""){
							//	saveToTemp(m.filePath, m.fileName, m.docType); 
								$.ajax({
									type : 'POST',
									url : 'tempFileAction!saveEntity.huzd',
									data : {
										'moduleId' :  $("#moduleId").val(),
										'projectId' : $("#projectId").val(),
										'tempFileEntity.filePath' : m.filePath,
										'tempFileEntity.fileType' : m.docType,
										'tempFileEntity.fileName' : m.fileName,
										'processFlag' : processFlag,
										'wfKey': wfKey,
										'state':'add',
										'ywid':'add'
									},
									async : false,
									dataType : 'json',
									success : function(msg) {
										if (msg.flag) {
											$("#tempFilePath").val(msg.message);
											art.dialog({
												title : '系统提示',
												content : '操作成功',
												time : 2000
											});
											
										}
									}
								});
							}else{
								art.dialog({
									title : '系统提示',
									content : '选择的文档参数配置不完整，可能存在问题，请联系管理员检查系统模板表配置！',
									time : 2000
								});
								return false;
							}
						});
						//alert(wfKey);
						initTempFiles4add($("#moduleId").val(), $("#projectId").val(),wfKey,'add','add');
					}
				});
			},
			cancelValue : '取消',
			cancel : function() {}
		});
	});
});