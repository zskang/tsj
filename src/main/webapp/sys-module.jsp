<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
 <!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>资源管理</title>
<link rel="stylesheet" type="text/css"  href="${_theme_}css/style.css" />
<link rel="stylesheet" type="text/css"  href="js/artDialog/skins/opera.css" />
<link rel="stylesheet" type="text/css"  href="js/ztree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"  href="js/easyPage/css/skins/jquery.easypage.css" />
<style type="text/css">span{display:inherit;}</style>
</head>

<body>
    <div class="rightinfo">
	    <div class="tools">
	    	<ul class="toolbar">
	    		<shiro:hasPermission name="module:add">
		        <li class="click btn_add" id="addBtn">添加</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="module:update">
		        <li class="click btn_edit" id="updateBtn">修改</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="module:delete">
		        <li class="click btn_delete" id="deleteBtn">删除</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="module:refreshCode">
		        <li class="click btn_refresh" id="refreshCodeBtn">刷新编码</li>
		        </shiro:hasPermission>
	        </ul>
	    </div>
	    <div class="content">
	    	<div class="content_left" style="width:30%;">
	    		<div class="widget_box">
	    			<div class="widget_title"><span>资源树</span></div>
	    			<div class="widget_body">
	    				<div class="ztree" id="moduleTree"></div>
	    			</div>
	    		</div>
	    	</div>
	    	<div class="content_right hide" id="moduleInfo" style="width:69%;">
	    		<div class="widget_box">
	    			<div class="widget_title"><span>资源信息</span></div>
	    			<div class="widget_body" style="padding-top:10px;">
					    <ul class="forminfo">
						    <li>
						    	<label>资源编号：</label>
						    	<input id="moduleId_" type="text" class="dfinput small" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>模块名称：</label>
						    	<input id="moduleName_" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>模块路径：</label>
						    	<input id="modulePath_" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>资源类型：</label>
						    	<input id="moduleType_" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>资源图标：</label>
						    	<img id="moduleIcon_" src=""/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>显示顺序：</label>
						    	<input id="moduleOrder_" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>资源编码：</label>
						    	<input id="moduleDisplayCode_" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li id="id_moduleDocType">
						    	<label>文档类型：</label>
						    	<input id="moduleDocType_" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>状态：</label>
						    	<input id="moduleStatus_" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>资源描述：</label>
						    	<input id="moduleDesc_" type="text" class="dfinput large" readonly="readonly"/>
						    	<i></i>
						    </li>
					    </ul>
	    			</div>
	    		</div>
	    	</div>
	    	
		<!-- add Data form -->
		<div class="hide" id="addDataDiv">
		  <form class="form-horizontal" id="addDataForm" name="addDataForm">
		  	<input type="hidden" name="module.id" id="id" value=""/>
		  	<input type="hidden" name="module.level" id="level" value="0"/>
		  	<input type="hidden" name="module.parent.id" id="pId" value=""/>
		  	<input type="hidden" name="module.displayCode" id="displayCode" value=""/>
		  	<input type="hidden" name="module.parent.displayCode" id="parentDisplayCode" value=""/>
		  	<ul class="forminfo">
			    <li>
			    	<label>资源类型</label>
					<select  id="type" name="module.type" class="select1" style="width:100px;">
						<option value="1">模块</option>
						<option value="2">文档</option>
					</select>
			    	<i>菜单用于展示，权限用来约束操作</i>
			    </li>		  	
			    <li>
			    	<label id="name_label">菜单名称</label>
			    	<input type="text" value="" id="name" name="module.name" class="dfinput normal"/>
			    	<i>中文名称</i>
			    </li>		  		
			    <li>
			    	<label id="path_label">菜单路径</label>
			    	<input type="text"  id="path" name="module.path" class="dfinput normal"/>
			    	<i>URL</i>
			    </li>			    
			    <li>
			    	<label>显示顺序</label>
			    	<input type="text"  id="order" name="module.order" class="dfinput small"/>
			    	<i>资源在列表中的展示先后顺序，升序排列</i>
			    </li>			    
			    <li id="li_icon">
			    	<label>图标</label>
			    	<input type="text"  id="icon" name="module.icon" class="dfinput small"/>
			    	<i>图标</i>
			    </li>
			    <li id="li_docType">
			    	<label>文档类型</label>
			    	<select id="docType" name="module.docType" class="select1" style="width:100px;">
			    		<option value="">----</option>
						<option value="doc">Wrod2003</option>
						<option value="docx">Wrod2007</option>
						<option value="xls">Excel2003</option>
						<option value="xlsx">Excel2007</option>
						<option value="pptv">PPT</option>
						<option value="pdf">PDF</option>
						<option value="other">其他文件</option>
					</select>
			    	<i>文档类型</i>
			    </li>		
			    <li>
			    	<label>状态</label>
					<select  id="status" name="module.status" class="select1" style="width:100px;">
						<option value="N">正常</option>
						<option value="F">禁用</option>
					</select>
			    	<i>资源状态</i>
			    </li>				    		    
			    <li>
			    	<label>资源描述</label>
			    	<textarea rows="4" cols="5" style="width:300px;" id="desc" name="module.desc"></textarea>
			    	<i>&nbsp;</i>
			    </li>			    
		  	</ul>
		  </form>
		 </div> 
		<!-- add Data form -->		    
	   
	    </div>
    </div>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.plugins.min.js"></script>
  	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.message_cn.js"></script>	
	<script type="text/javascript" src="js/validate/jquery.validate.method.js"></script>  
	<script type="text/javascript" src="js/validate/resetFormHelper.js"></script>
		<script type="text/javascript">
			var setting = {data: {simpleData: {enable: true,rootPId:null}},callback:{onClick:treeClickEvent},async:{enable:true,url:"moduleAction!list.huzd",autoParam:["id=moduleId","name=name","level=level"]}};
			var moduleTree = null;
			$(function(){
				moduleTree = $.fn.zTree.init($("#moduleTree"), setting);
				$("#addBtn").click(function(){
					$(".help-inline").html("");
					$("#li_docType").hide();
					var resAllNodes = moduleTree.getNodes();
					var treeNode = moduleTree.getNodes()[0];
					if(resAllNodes.length == 1 && (treeNode.id == 0 && treeNode.pId == null)){//添加根节点
						art.dialog({title:'系统提示',content:'你将添加根节点！',okValue: '确定',ok:function(){showAddForm();},cancelValue:'取消'});
					}else{
						if(moduleTree.getSelectedNodes().length ==0 ){
							art.dialog({title:'系统提示',content:'请选择父资源！',time:2000});
							return false;
						}else if(moduleTree.getSelectedNodes().length >1){
							art.dialog({title:'系统提示',content:'只能选择一个父节点！',time:2000});
							return false;
						}else{
							var selectNode = moduleTree.getSelectedNodes()[0];
								$("#pId").val(selectNode.id);
								$("#parentDisplayCode").val(selectNode.code);
								$("#level").val(parseInt(selectNode.level)+1);
								if(selectNode.level==0){
									$("#icon").val("");
									$("#li_icon").show();
								}else{
									$("#icon").val("#");
									$("#li_icon").hide();
								}
								showAddForm();
						}
					}
				});
				//配置表单验证信息
			 	$("#addDataForm").validate({   
						rules:{'module.name':{required:true,minlength:2},
							   'module.desc':{required:true,minlength:2},
							   'module.type':{required:true,digits:true},
							   'module.path':{required:true},
							   'module.order':{required:true,digits:true},
							   'module.icon':{required:function(){
								   if($("#type").val() == 1 && null != $("#icon").val())return true;
								   else if($("#type").val() == 2 && null == $("#icon").val())return true;
								   else return false;
							   }}
							   },
							   errorElement:'b',success:function(label){
									label.remove();
								}
				});	
				
				$("#updateBtn").click(function(){
					$("#li_docType").hide();
					if(moduleTree.getSelectedNodes().length ==0 ){
						art.dialog({title:'系统提示',content:'请选择资源！',time:2000});
						return false;
					}else if(moduleTree.getSelectedNodes().length >1){
						art.dialog({title:'系统提示',content:'只能选择一个资源！',time:2000});
						return false;
					}else{
						var treeNode = moduleTree.getSelectedNodes()[0];
						if(treeNode.id == 0 && treeNode.pId == null){
							art.dialog({title:'系统提示',content:'无资源可删除！'});
						}else{
							if(treeNode.level==1){
								$("#icon").val("");
								$("#li_icon").show();
							}else{
								$("#icon").val("#");
								$("#li_icon").hide();
							}
							$.ajax({type:'POST',url:'moduleAction!get.huzd',data:{'module.id':treeNode.id},async:false,dataType:'json',success:function(msg){
								if(true==msg.flag){
									$("#id").val(msg.module.id);
									$("#pId").val((msg&&msg.module&&msg.module.parent)?msg.module.parent.id:"");
									$("#level").val(msg.module.level);
									$("#name").val(msg.module.name);
									$("#type option[value='"+msg.module.type+"']").attr("selected","selected");
									$("#status option[value='"+msg.module.status+"']").attr("selected","selected");
									$("#path").val(msg.module.path);
									$("#order").val(msg.module.order);
									$("#icon").val(msg.module.icon);
									$("#desc").val(msg.module.desc);
									$("#displayCode").val(msg.module.displayCode);
									$("#parentDisplayCode").val((msg&&msg.module&&msg.module.parent)?msg.module.parent.displayCode:"");
									if(msg.module.type ==1){
										$("#li_docType").hide();
										$("#docType").val("");
									}else{
										$("#li_docType").show();
										$("#docType option[value='"+msg.module.docType+"']").attr("selected","selected");
									}
								}else{
									art.dialog({title:'系统提示',content:'查询出错请重试！',time:2000});
								}}						
							});
						}
						art.dialog({title:'信息修改',content:document.getElementById("addDataDiv"),lock:true,
							okValue:'修改',ok:function(){
								if($("#addDataForm").valid()){
								$.ajax({type:'POST',url:'moduleAction!update.huzd',data:$("#addDataForm").serialize(),async:false,dataType:'json',success:function(msg){
									if(true==msg.flag){
										$("#addDataForm")[0].reset();resetValidatorClass();
										var pId = moduleTree.getSelectedNodes()[0].pId;
										moduleTree.reAsyncChildNodes(moduleTree.getNodeByParam("id", pId, null),"refresh");
										art.dialog({title:'系统提示',content:'修改成功！',time:2000});
									}else{
										art.dialog({title:'系统提示',content:'修改出错请重试！',time:2000});
									}}
								});
								}else{return false;}
							},cancelValue:'关闭',cancel:function(){$("#addDataForm")[0].reset();resetValidatorClass()}});							
					}			
				});	
				
				$("#deleteBtn").click(function(){
					if(moduleTree.getSelectedNodes().length ==0 ){
						art.dialog({title:'系统提示',content:'请选择资源！',time:2000});
						return false;
					}else if(moduleTree.getSelectedNodes().length >1){
						art.dialog({title:'系统提示',content:'只能选择一个资源！',time:2000});
						return false;
					}else{
						var treeNode = moduleTree.getSelectedNodes()[0];
						if(treeNode.id == 0 && treeNode.pId == null){
							art.dialog({title:'系统提示',content:'无资源可删除！'});
							return false;
						}else if(treeNode.level == 0){
							art.dialog({title:'系统提示',content:'当前资源不允许修改！',time:2000});
							return false;
						}else{
							art.confirm('你确定要删除？后果可能很严重...',function () {
								$.ajax({type:'POST',url:'moduleAction!delete.huzd',data:{moduleId:moduleTree.getSelectedNodes()[0].id},async:false,dataType:'json',success:function(msg){
									if(true==msg.flag){
										var pId = moduleTree.getSelectedNodes()[0].pId;
										moduleTree.reAsyncChildNodes(moduleTree.getNodeByParam("id", pId, null),"refresh");
										art.dialog({title:'系统提示',content:'删除成功！',time:2000});
									}else{
										art.dialog({title:'系统提示',content:'删除出错请重试！',time:2000});
									}}
								});
							}, function () {
								return true;
							});
						}
					}
				});		
				
				$("#refreshCodeBtn").click(function(){
 					art.confirm('本次操作将重新刷新所有资源节点的系统编码，确认操作？',function () {
 						var nodes = moduleTree.getNodesByParam("isRoot",true, null);
						$.ajax({type:'POST',url:'moduleAction!freshCode.huzd',data:{moduleId:nodes[0].id},dataType:'json',success:function(msg){
							if(true==msg.flag){
								art.dialog({title:'系统提示',content:'刷新成功，请重新打开本页面，查看最新组织机构编码！',time:2000});
							}else{
								art.dialog({title:'系统提示',content:'刷新出错请重试！',time:2000});
							}}
						});
						return true;
					}, function () {
						return true;
					});
				});
				
				$("#type").change(function(){
					var type = $("#type").find("option:selected").val();
					if("1"==type){
						$("#name_label").html("模块名称");
						$("#path_label").html("模块路径");
						$("#li_docType").hide();
						$("#docType").val("");
					}else{
						$("#name_label").html("文档名称");
						$("#path_label").html("文档路径");
						$("#li_docType").show();
					}
				});
			});
			//点击节点触发事件
			function treeClickEvent(event,treeId,treeNode){
				$.ajax({type:'POST',url:'moduleAction!get.huzd',data:{'module.id':treeNode.id},async:false,dataType:'json',success:function(msg){
					if(true==msg.flag){
						$("#moduleId_").val(msg.module.id);
						$("#moduleName_").val(msg.module.name);
						$("#modulePath_").val(msg.module.path);
						$("#moduleType_").val(msg.module.type==1?"模块":"文档");
						if(msg.module.type==1){
							$("#id_moduleDocType").hide();
						}else{
							$("#id_moduleDocType").show();
							$("#moduleDocType_").val(msg.module.docType);
						}
						if(msg.module.icon&&"#"!=msg.module.icon){
							$("#moduleIcon_").attr("src","${_theme_}"+msg.module.icon);	
						}else{
							$("#moduleIcon_").attr("src","");
						}
						$("#moduleOrder_").val(msg.module.order);
						$("#moduleDesc_").val(msg.module.desc);
						$("#moduleDisplayCode_").val(msg.module.displayCode);
						$("#moduleStatus_").val(msg.module.status=="N"?"正常":"禁用");
						$("#moduleInfo").removeClass("hide");
					}else{
						art.dialog({title:'系统提示',content:'查询出错请重试！',time:2000});
					}}
				});			
			}
			
			function showAddForm(){
				art.dialog({title:'资源添加',content:document.getElementById("addDataDiv"),lock:true,
					okValue:'添加',ok:function(){
						if($("#addDataForm").valid()){
						$.ajax({type:'POST',url:'moduleAction!add.huzd',data:$("#addDataForm").serialize(),async:false,dataType:'json',success:function(msg){
							if(true==msg.flag){
								$("#addDataForm")[0].reset();resetValidatorClass();
								var treeNode = moduleTree.getSelectedNodes()[0];
								if(!treeNode.isParent){
									treeNode.isParent = true;
									moduleTree.updateNode(treeNode);
								}
								moduleTree.reAsyncChildNodes(moduleTree.getSelectedNodes()[0], "refresh");
								art.dialog({title:'系统提示',content:'添加成功！',time:2000});
								
							}else{
								art.dialog({title:'系统提示',content:'添加出错请重试！',time:2000});
							}}
						});
						}else{return false;}
						},cancelValue:'关闭',cancel:function(){$("#addDataForm")[0].reset();resetValidatorClass();}});					
			}
		</script>    
</body>

</html>
  