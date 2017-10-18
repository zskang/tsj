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
	    		<shiro:hasPermission name="completionInfo:add">
		        <li class="click btn_add" id="addBtn">添加</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="completionInfo:update">
		        <li class="click btn_edit" id="updateBtn">修改</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="completionInfo:delete">
		        <li class="click btn_delete" id="deleteBtn">删除</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="completionInfo:refreshCode">
		        <li class="click btn_refresh" id="refreshCodeBtn">刷新编码</li>
		        </shiro:hasPermission>
	        </ul>
	    </div>
	    <div class="content">
	    	<div class="content_left" style="width:30%;">
	    		<div class="widget_box">
	    			<div class="widget_title"><span>竣工树管理</span></div>
	    			<div class="widget_body">
	    				<div class="ztree" id="completionInfoTree"></div>
	    			</div>
	    		</div>
	    	</div>
	    	<div class="content_right hide" id="completionInfoInfo" style="width:69%;">
	    		<div class="widget_box">
	    			<div class="widget_title"><span>竣工树管理</span></div>
	    			<div class="widget_body" style="padding-top:10px;">
					    <ul class="forminfo">
						    <li>
						    	<label>资源编号：</label>
						    	<input id="completionInfoId_" type="text" class="dfinput small" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>资源名称：</label>
						    	<input id="completionInfoName_" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>资源类型：</label>
						    	<input id="completionInfoType_" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>显示顺序：</label>
						    	<input id="completionInfoOrder_" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						     <li>
						    	<label>资源编码：</label>
						    	<input id="completionInfoDisplayCode_" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						     <li>
						    	<label>状态：</label>
						    	<input id="completionInfoStatus_" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						    
						    <li>
						    	<label>资源描述：</label>
						    	<input id="completionInfoDesc_" type="text" class="dfinput large" readonly="readonly"/>
						    	<i></i>
						    </li>
					    </ul>
	    			</div>
	    		</div>
	    	</div>
		<div class="hide" id="addDataDiv">
		  <form class="form-horizontal" id="addDataForm" name="addDataForm">
		  	<input type="hidden" name="completionInfo.id" id="id" value=""/>
		  	<input type="hidden" name="completionInfo.project.id" id="projectId" value="${sessionScope.project.id}"/>
		  	<input type="hidden" name="completionInfo.level" id="level" value="0"/>
		  	<input type="hidden" name="completionInfo.parent.id" id="pId" value=""/>
		  	<input type="hidden" name="completionInfo.displayCode" id="displayCode" value=""/>
		  	<input type="hidden" name="completionInfo.parent.displayCode" id="parentDisplayCode" value=""/>
		  	<ul class="forminfo">
			    <li>
			    	<label>资源类型</label>
					<select  id="type" name="completionInfo.type" class="select1" style="width:100px;">
						<option value="1">资源</option>
					</select>
			    	<i>资源用于展示</i>
			    </li>		  	
			    <li>
			    	<label id="name_label">资源名称</label>
			    	<input type="text" value="" id="name" name="completionInfo.name" class="dfinput normal"/>
			    	<i>中文名称</i>
			    </li>		  		
			    <li>
			    	<label>显示顺序</label>
			    	<input type="text"  id="order" name="completionInfo.order" class="dfinput small"/>
			    	<i>资源在列表中的展示先后顺序，升序排列</i>
			    </li>
			    <li>
			    	<label>状态</label>
					<select  id="status" name="completionInfo.status" class="select1" style="width:100px;">
						<option value="N">正常</option>
						<option value="F">禁用</option>
					</select>
			    	<i>资源状态</i>
			    </li>
			    <li>
			    	<label>资源描述</label>
			    	<textarea rows="4" cols="5" style="width:300px;" id="desc" name="completionInfo.desc"></textarea>
			    	<i>&nbsp;</i>
			    </li>			    
		  	</ul>
		  </form>
		 </div> 
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
	var setting = {data: {simpleData: {enable: true,rootPId:null}},callback:{onClick:treeClickEvent},async:{enable:true,url:"completionInfoAction!list.huzd?projectId="+$("#projectId").val(),autoParam:["id=completionInfoId","name=name","level=level"]}};
	var completionInfoTree = null;
	$(function(){
		completionInfoTree = $.fn.zTree.init($("#completionInfoTree"), setting);
		$("#addBtn").click(function(){
			$(".help-inline").html("");
			var resAllNodes = completionInfoTree.getNodes();
			var treeNode = completionInfoTree.getNodes()[0];
			if(resAllNodes.length == 1 && (treeNode.id == 0 && treeNode.pId == null)){//添加根节点
				art.dialog({title:'系统提示',content:'你将添加根节点！',okValue: '确定',ok:function(){showAddForm();},cancelValue:'取消'});
			}else{
				if(completionInfoTree.getSelectedNodes().length ==0 ){
					art.dialog({title:'系统提示',content:'请选择父资源！',time:2000});
					return false;
				}else if(completionInfoTree.getSelectedNodes().length >1){
					art.dialog({title:'系统提示',content:'只能选择一个父节点！',time:2000});
					return false;
				}else{
					var selectNode = completionInfoTree.getSelectedNodes()[0];
					$("#pId").val(selectNode.id);
					$("#parentDisplayCode").val(selectNode.code);
					$("#level").val(parseInt(selectNode.level)+1);
					showAddForm();
				}
			}
		});
		//配置表单验证信息
		$("#addDataForm").validate({   
			rules:{'completionInfo.name':{required:true,minlength:2},
					'completionInfo.desc':{required:true,minlength:2},
					'completionInfo.type':{required:true,digits:true},
					'completionInfo.order':{required:true,digits:true}},
			errorElement:'b',success:function(label){label.remove();}
		});	
				
		$("#updateBtn").click(function(){
			if(completionInfoTree.getSelectedNodes().length ==0 ){
				art.dialog({title:'系统提示',content:'请选择资源！',time:2000});
				return false;
			}else if(completionInfoTree.getSelectedNodes().length >1){
				art.dialog({title:'系统提示',content:'只能选择一个资源！',time:2000});
				return false;
			}else{
				var treeNode = completionInfoTree.getSelectedNodes()[0];
				if(treeNode.id == 0 && treeNode.pId == null){
					art.dialog({title:'系统提示',content:'无资源可删除！'});
					return false;
				}else if(treeNode.level == 0){
					art.dialog({title:'系统提示',content:'当前资源不允许修改！',time:2000});
					return false;
				}else{
					$.ajax({type:'POST',url:'completionInfoAction!get.huzd',data:{'completionInfo.id':treeNode.id},async:false,dataType:'json',success:function(msg){
						if(true==msg.flag){
							$("#id").val(msg.completionInfo.id);
							$("#pId").val((msg&&msg.completionInfo&&msg.completionInfo.parent)?msg.completionInfo.parent.id:"");
							$("#level").val(msg.completionInfo.level);
							$("#name").val(msg.completionInfo.name);
							$("#type option[value='"+msg.completionInfo.type+"']").attr("selected","selected");
							$("#status option[value='"+msg.completionInfo.status+"']").attr("selected","selected");
							$("#order").val(msg.completionInfo.order);
							$("#desc").val(msg.completionInfo.desc);
							$("#displayCode").val(msg.completionInfo.displayCode);
							$("#parentDisplayCode").val((msg&&msg.completionInfo&&msg.completionInfo.parent)?msg.completionInfo.parent.displayCode:"");
						}else{
							art.dialog({title:'系统提示',content:'查询出错请重试！',time:2000});
						}}						
					});
				}
				art.dialog({title:'信息修改',content:document.getElementById("addDataDiv"),lock:true,okValue:'修改',ok:function(){
					if($("#addDataForm").valid()){
						$.ajax({type:'POST',url:'completionInfoAction!update.huzd',data:$("#addDataForm").serialize(),async:false,dataType:'json',success:function(msg){
							if(true==msg.flag){
								$("#addDataForm")[0].reset();resetValidatorClass();
								var pId = completionInfoTree.getSelectedNodes()[0].pId;
								completionInfoTree.reAsyncChildNodes(completionInfoTree.getNodeByParam("id", pId, null),"refresh");
								art.dialog({title:'系统提示',content:'修改成功！',time:2000});
							}else{
								art.dialog({title:'系统提示',content:'修改出错请重试！',time:2000});
							}}
						});
					}else{return false;}
				},cancelValue:'关闭',cancel:function(){$("#addDataForm")[0].reset();resetValidatorClass();}});							
			}			
		});	
				
		$("#deleteBtn").click(function(){
			if(completionInfoTree.getSelectedNodes().length ==0 ){
				art.dialog({title:'系统提示',content:'请选择资源！',time:2000});
				return false;
			}else if(completionInfoTree.getSelectedNodes().length >1){
				art.dialog({title:'系统提示',content:'只能选择一个资源！',time:2000});
				return false;
			}else{
				var treeNode = completionInfoTree.getSelectedNodes()[0];
				if(treeNode.id == 0 && treeNode.pId == null){
					art.dialog({title:'系统提示',content:'无资源可删除！'});
					return false;
				}else if(treeNode.level == 0){
					art.dialog({title:'系统提示',content:'当前资源不允许修改！',time:2000});
					return false;
				}else{
					art.confirm('你确定要删除该目录和目录下的所有文件？',function () {
						$.ajax({type:'POST',url:'completionInfoAction!delete.huzd',data:{completionInfoId:completionInfoTree.getSelectedNodes()[0].id},async:false,dataType:'json',success:function(msg){
							if(true==msg.flag){
								var pId = completionInfoTree.getSelectedNodes()[0].pId;
								completionInfoTree.reAsyncChildNodes(completionInfoTree.getNodeByParam("id", pId, null),"refresh");
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
		
		//刷新资源
		$("#refreshCodeBtn").click(function(){
			art.confirm('本次操作将重新刷新所有资源节点的系统编码，确认操作？',function () {
				var nodes = completionInfoTree.getNodesByParam("isRoot",true, null);
				$.ajax({type:'POST',url:'completionInfoAction!freshCode.huzd',data:{completionInfoId:nodes[0].id},dataType:'json',success:function(msg){
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
	});
			//点击节点触发事件
	function treeClickEvent(event,treeId,treeNode){
		$.ajax({type:'POST',url:'completionInfoAction!get.huzd',data:{'completionInfo.id':treeNode.id},async:false,dataType:'json',success:function(msg){
			if(true==msg.flag){
				$("#completionInfoId_").val(msg.completionInfo.id);
				$("#completionInfoName_").val(msg.completionInfo.name);
				$("#completionInfoType_").val(msg.completionInfo.type==1?"模块":"文档");
				$("#completionInfoOrder_").val(msg.completionInfo.order);
				$("#completionInfoDesc_").val(msg.completionInfo.desc);
				$("#completionInfoDisplayCode_").val(msg.completionInfo.displayCode);
				$("#completionInfoStatus_").val(msg.completionInfo.status=="N"?"正常":"禁用");
				$("#completionInfoInfo").removeClass("hide");
			}else{
				art.dialog({title:'系统提示',content:'查询出错请重试！',time:2000});
			}}
		});			
	}
			
	function showAddForm(){
		art.dialog({title:'资源添加',content:document.getElementById("addDataDiv"),lock:true,okValue:'添加',ok:function(){
			if($("#addDataForm").valid()){
				$.ajax({type:'POST',url:'completionInfoAction!add.huzd',data:$("#addDataForm").serialize(),async:false,dataType:'json',success:function(msg){
					if(true==msg.flag){
						$("#addDataForm")[0].reset();resetValidatorClass();
						var treeNode = completionInfoTree.getSelectedNodes()[0];
						if(!treeNode.isParent){
							treeNode.isParent = true;
							completionInfoTree.updateNode(treeNode);
						}
						completionInfoTree.reAsyncChildNodes(completionInfoTree.getSelectedNodes()[0], "refresh");
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
  