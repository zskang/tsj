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
<title>组织管理</title>
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
	    		<shiro:hasPermission name="org:add">
		        <li class="click btn_add" id="addBtn">添加</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="org:update">
		        <li class="click btn_edit" id="updateBtn">修改</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="org:delete">
		        <li class="click btn_delete" id="deleteBtn">删除</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="org:refreshCode">
		        <li class="click btn_refresh" id="refreshCodeBtn">刷新编码</li>
		        </shiro:hasPermission>		        
	        </ul>
	    </div>
	    <div class="content">
	    	<div class="content_left">
	    		<div class="widget_box">
	    			<div class="widget_title"><span>组织结构</span></div>
	    			<div class="widget_body">
	    				<div class="ztree" id="orgTree"></div>
	    			</div>
	    		</div>
	    	</div>
	    	<div class="content_right hide" id="orgInfo">
	    		<div class="widget_box">
	    			<div class="widget_title"><span>资源信息</span></div>
	    			<div class="widget_body" style="padding-top:10px;">
					    <ul class="forminfo">
						    <li>
						    	<label>组织编码：</label>
						    	<input id="info_orgId" type="text" class="dfinput small" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>组织名称：</label>
						    	<input id="info_orgName" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>组织编码：</label>
						    	<input id="info_orgCode" type="text" class="dfinput normal" readonly="readonly"/>（系统自动生成）
						    	<i></i>
						    </li>
						    <li>
						    	<label>组织层次：</label>
						    	<input id="info_orgLevel" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
						    <li>
						    	<label>组织描述：</label>
						    	<input id="info_orgDesc" type="text" class="dfinput normal" readonly="readonly"/>
						    	<i></i>
						    </li>
					    </ul>
	    			</div>
	    		</div>
	    	</div>
	    	
		<!-- add Data form -->
		<div class="hide" id="addDataDiv">
		  <form id="addDataForm" name="addDataForm">
		  	<input type="hidden" name="org.id" id="id" value=""/>
		  	<input type="hidden" name="org.level" id="level" value="1"/>
		  	<input type="hidden" name="org.parent.id" id="pId" value=""/>
		  	<input type="hidden" name="org.parent.code" id="pCode" value=""/>
		  	<input type="hidden" name="org.code" id="code" value=""/>
		  	<ul class="forminfo">
			    <li>
			    	<label>组织名称</label>
			    	<input type="text" value="" id="name" name="org.name" class="dfinput small"/>
			    	<i>中文名称</i>
			    </li>		  		
			    <li>
			    	<label>组织描述</label>
			    	<textarea rows="4" cols="5" style="width:300px;" id="desc" name="org.desc"></textarea>
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
		var setting = {data: {simpleData: {enable: true,rootPId:null}},callback:{onClick:treeClickEvent},async:{enable:true,url:"orgAction!list.huzd",autoParam:["id=orgId","name=name","level=level"]}};
		var orgTree = null;	
			$(function(){
				orgTree = $.fn.zTree.init($("#orgTree"), setting);
				$("#addDataForm").validate({   
					rules:{'org.name':{required:true,minlength:2},
						   'org.desc':{required:true,minlength:2}},
						   errorElement:'b',success:function(label){
								label.remove();
							}
				});	
				$("#addBtn").click(function(){
					if(orgTree.getSelectedNodes().length ==0 ){
						art.dialog({title:'系统提示',content:'请选择归宿组织节点！',time:2000});
						return false;
					}else if(orgTree.getSelectedNodes().length >1){
						art.dialog({title:'系统提示',content:'只能选择一个父节点！',time:2000});
						return false;
					}else{
						var treeNode = orgTree.getSelectedNodes()[0];
						if(treeNode.id == 0 && treeNode.pId == null){
							art.dialog({title:'系统提示',content:'你将添加根节点！'});
						}else{
							$("#pId").val(treeNode.id);
							$("#level").val(parseInt(treeNode.level)+1);
							$("#pCode").val(treeNode.code);
						}
						if(orgTree.getSelectedNodes()[0].level >= ${sessionScope.user.org.level}){
							art.dialog({title:'组织添加',content:document.getElementById("addDataDiv"),lock:true,
								okValue:'添加',ok:function(){
									if($("#addDataForm").valid()){
									$.ajax({type:'POST',url:'orgAction!add.huzd',data:$("#addDataForm").serialize(),async:false,dataType:'json',success:function(msg){
										if(true==msg.flag){
											$("#addDataForm")[0].reset();resetValidatorClass();
											var treeNode = orgTree.getSelectedNodes()[0];
											if(!treeNode.isParent){
												treeNode.isParent = true;
												orgTree.updateNode(treeNode);
											}
											orgTree.reAsyncChildNodes(orgTree.getSelectedNodes()[0], "refresh");
											art.dialog({id:'ID-SUCCESS',title:'系统提示',content:'添加成功！',time:2000});
											
										}else{
											art.dialog({title:'系统提示',content:'添加出错请重试！',time:2000});
										}}
									});
									}else{return false;}
									},cancelValue:'关闭',cancel:function(){$("#addDataForm")[0].reset();resetValidatorClass();}});	
						}else{
							art.dialog({title:'系统提示',content:'您无法在所属组织机构的上级节点进行操作！',time:2000});
						}
					}
			
				});	
				
				$("#updateBtn").click(function(){
					if(orgTree.getSelectedNodes().length ==0 ){
						art.dialog({title:'系统提示',content:'请选择组织节点！',time:2000});
						return false;
					}else if(orgTree.getSelectedNodes().length >1){
						art.dialog({title:'系统提示',content:'只能选择一个父节点！',time:2000});
						return false;
					}else{
						var treeNode = orgTree.getSelectedNodes()[0];
						if(treeNode.id == 0 && treeNode.pId == null){
							art.dialog({title:'系统提示',content:'无节点可删除！'});
						}else{
							$("#pId").val(treeNode.pId);
							$("#code").val(treeNode.code);
							$("#id").val(treeNode.id);
							$("#level").val(treeNode.level);
							$("#name").val(treeNode.name);
							$("#desc").val(treeNode.desc);
							$("#pCode").val(treeNode.getParentNode().code);
						}
						art.dialog({id:'ID-ADDORGFORM',title:'组织修改',content:document.getElementById("addDataDiv"),lock:true,
							okValue:'修改',ok:function(){
								if($("#addDataForm").valid()){
								$.ajax({type:'POST',url:'orgAction!update.huzd',data:$("#addDataForm").serialize(),async:false,dataType:'json',success:function(msg){
									if(true==msg.flag){
										$("#addDataForm")[0].reset();resetValidatorClass();
										var pId = orgTree.getSelectedNodes()[0].pId;
										orgTree.reAsyncChildNodes(orgTree.getNodeByParam("id", pId, null),"refresh");
										art.dialog({id:'ID-SUCCESS',title:'系统提示',content:'修改成功！',time:2000});
									}else{
										art.dialog({id:'ID-ERROR',title:'系统提示',content:'修改出错请重试！',time:2000});
									}}
								});
								}else{return false;}
							},cancelValue:'关闭',cancel:function(){$("#addDataForm")[0].reset();resetValidatorClass();}});							
					}
			
				});
				
				$("#deleteBtn").click(function(){
					art.confirm('你确定要删除？后果可能很严重...', function () {
						$.ajax({type:'POST',url:'orgAction!delete.huzd',data:{orgId:orgTree.getSelectedNodes()[0].id},async:false,dataType:'json',success:function(msg){
							if(true==msg.flag){
								var pId = orgTree.getSelectedNodes()[0].pId;
								orgTree.reAsyncChildNodes(orgTree.getNodeByParam("id", pId, null),"refresh");
								art.dialog({id:'ID-SUCCESS',title:'系统提示',content:'删除成功！',time:2000});
							}else{
								art.dialog({id:'ID-ERROR',title:'系统提示',content:'删除出错请重试！',time:2000});
							}}
						});
					}, function () {
						return true;
					});
				});		
				
				$("#refreshCodeBtn").click(function(){
 					art.confirm('本次操作将重新刷新所有组织机构节点的系统内置编码，确认操作？',function () {
 						var nodes = orgTree.getNodesByParam("isRoot",true, null);
						$.ajax({type:'POST',url:'orgAction!freshCode.huzd',data:{orgId:nodes[0].id},async:false,dataType:'json',success:function(msg){
							if(true==msg.flag){
								art.dialog({title:'系统提示',content:'刷新成功，请重新打开本页面，查看最新组织机构编码！',time:2000});
							}else{
								art.dialog({title:'系统提示',content:'刷新出错请重试！',time:2000});
							}}
						});
					}, function () {
						return true;
					});
				});					
			});
			//点击节点触发事件
			function treeClickEvent(event,treeId,treeNode){
				$("#orgInfo").show();
				$("#info_orgId").val(treeNode.id);
				$("#info_orgName").val(treeNode.name);
				$("#info_orgDesc").val(treeNode.desc);
				$("#info_orgLevel").val(treeNode.level);
				$("#info_orgCode").val(treeNode.code);
			}	
			
			function showAddForm(){
				art.dialog({title:'资源添加',content:document.getElementById("addDataDiv"),lock:true,
					okValue:'添加',ok:function(){
						if($("#addDataForm").valid()){
						$.ajax({type:'POST',url:'resAction!add.huzd',data:$("#addDataForm").serialize(),async:false,dataType:'json',success:function(msg){
							if(true==msg.flag){
								$("#addDataForm")[0].reset();resetValidatorClass();
								var treeNode = resourceTree.getSelectedNodes()[0];
								if(!treeNode.isParent){
									treeNode.isParent = true;
									resourceTree.updateNode(treeNode);
								}
								resourceTree.reAsyncChildNodes(resourceTree.getSelectedNodes()[0], "refresh");
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
  