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
	    		<shiro:hasPermission name="completion:move">
		        	<li class="click btn_add" id="addBtn">归卷资料平移</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="completion:delete">
		        	<li class="click btn_delete" id="deleteBtn">竣工资料删除</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="completion:download">
		        	<li class="click btn_download" id="downloadBtn">竣工资料下载</li>
		        </shiro:hasPermission>
	        </ul>
	    </div>
	    <div class="content">
	    	<div class="content_left" style="width:35%;">
	    		<div class="widget_box">
	    			<div class="widget_title"><span>归卷资料</span></div>
	    			<div class="widget_body">
	    				<div class="ztree" id="moduleTree"></div>
	    			</div>
	    		</div>
	    	</div>
	    	<div class="content_left" id="completionInfoInfo" style="width:35%;margin-left:30px;">
	    		<div class="widget_box">
	    			<div class="widget_title"><span>竣工资料</span></div>
	    			<div class="widget_body">
	    				<div class="ztree" id="completionInfoTree"></div>
		  				
	    			</div>
	    		</div>
	    	</div>
	    	<div class="hide" id="addDataDiv">
	    		<form class="form-horizontal" id="addDataForm" name="addDataForm">
	    			<input type="hidden" name="completionInfo.id" id="id" value=""/>
		  			<input type="hidden" name="completionInfo.project.id" id="projectId" value="${sessionScope.project.id}"/>
		  			<input type="hidden" name="completionInfo.level" id="level" value="0"/>
		  			<input type="hidden" name="completionInfo.parent.id" id="pId" value=""/>
		  			<input type="hidden" name="completionInfo.path" id="path" value=""/>	
		  		</form>
	    	</div>
	    </div>
    </div>
    <a class="hide" href="" id="downloadUrl" target="_blank">d</a>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/ztree/jquery.ztree.all-3.5.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.plugins.min.js"></script>
  	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.message_cn.js"></script>	
	<script type="text/javascript" src="js/validate/jquery.validate.method.js"></script>  
	<script type="text/javascript" src="js/validate/resetFormHelper.js"></script>
	<script type="text/javascript">
	var moduleSetting = {check:{enable:true,nocheckInherit: false},data: {simpleData: {enable: true,rootPId:null}}};
	var completionInfoSetting = {check:{enable:true,nocheckInherit: false},data: {simpleData: {enable: true,rootPId:null}}};
	var moduleTree = null;
	var completionInfoTree = null;
	$(function(){
		$.post('completionInfoAction!getModuleFile.huzd',{'projectId':$("#projectId").val()},function(msg){
			var result = (new Function("return " + msg))();
			if(result.flag){
				moduleTree = $.fn.zTree.init($("#moduleTree"),moduleSetting,result.module);
			}else{
				art.dialog({title:'系统提示',content:result.message,time:2000});
			}
		},'json');
		
		$.post('completionInfoAction!getCompletionInfoFile.huzd',{'projectId':$("#projectId").val()},function(msg){
			var result = (new Function("return " + msg))();
			if(result.flag){
				completionInfoTree = $.fn.zTree.init($("#completionInfoTree"),completionInfoSetting,result.completionInfo);
			}else{
				art.dialog({title:'系统提示',content:result.message,time:2000});
			}
		},'json');
		
		//竣工资料下载
		$("#downloadBtn").click(function(){
			var selectNodes = completionInfoTree.getCheckedNodes(true);
			if(selectNodes.length ==0){
				art.dialog({title:'系统提示',content:'请选择竣工文档！',time:2000});
				return false;
			}else{
				var completionInfoIds = "";
				$.each(selectNodes,function(i,n){
					completionInfoIds+=n.id;
					if(i<selectNodes.length - 1)completionInfoIds+=",";
				});
				
				var d_ = art.dialog({title:'系统提示',content:'文档后台压缩中...',lock:true});
				$.post("completionInfoAction!zipDoc.huzd",{'completionInfoIds':completionInfoIds},function(msg){
					if(msg.flag){
						d_.content("服务器压缩完成！立刻下载...<a style='color:red;' target='_blank' href='completionInfoAction!download.huzd?message="+msg.message+"'>浏览器未响应请点击此处</a>");
						$("#downloadUrl").attr("href","completionInfoAction!download.huzd?message="+msg.message);
						document.getElementById("downloadUrl").click(); 
					}else{
						d_.content(msg.message);
					}
				},"json");
			}
		});
		
		//删除竣工资料
		$("#deleteBtn").click(function(){
			var selectNodes = completionInfoTree.getCheckedNodes(true);
			if(selectNodes.length ==0){
				art.dialog({title:'系统提示',content:'请选择竣工文档！',time:2000});
				return false;
			}else{
				var completionInfoIds = "";
				$.each(selectNodes,function(i,n){
					completionInfoIds+=n.id;
					if(i<selectNodes.length - 1)completionInfoIds+=",";
				});
				$.ajax({type:'POST',url:'completionInfoAction!deleteCompletionInfoFile.huzd',data:{'completionInfoIds':completionInfoIds},async:false,dataType:'json',success:function(msg){
					if(true==msg.flag){
						$.post('completionInfoAction!getCompletionInfoFile.huzd',{'projectId':$("#projectId").val()},function(msg){
							var result = (new Function("return " + msg))();
							if(result.flag){
								completionInfoTree = $.fn.zTree.init($("#completionInfoTree"),completionInfoSetting,result.completionInfo);
							}else{
								art.dialog({title:'系统提示',content:result.message,time:2000});
							}
						},'json');
						art.dialog({title:'系统提示',content:'删除成功！',time:2000});
					}else{
						art.dialog({title:'系统提示',content:'删除出错请重试！',time:2000});
					}}
				});
			}
		});
		
		//平移资料
		$("#addBtn").click(function(){
			var selectNodes = moduleTree.getCheckedNodes(true);
			if(selectNodes.length ==0){
				art.dialog({title:'系统提示',content:'请选择文档！',time:2000});
				return false;
			}else{
				if(completionInfoTree.getSelectedNodes().length ==0 ){
					art.dialog({title:'系统提示',content:'请选择父资源！',time:2000});
					return false;
				}else if(completionInfoTree.getSelectedNodes().length >1){
					art.dialog({title:'系统提示',content:'只能选择一个父节点！',time:2000});
					return false;
				}else{
					var completionInfoIds = "";
					$.each(selectNodes,function(i,n){
						completionInfoIds+=n.id;
						if(i<selectNodes.length - 1)completionInfoIds+=",";
					});
					var selectNode = completionInfoTree.getSelectedNodes()[0];
					$("#pId").val(selectNode.id);
					$("#level").val(parseInt(selectNode.level)+1);
					$("#path").val(completionInfoIds);
					$.ajax({type:'POST',url:'completionInfoAction!moveModuleFile.huzd',data:$("#addDataForm").serialize(),async:false,dataType:'json',success:function(msg){
						if(true==msg.flag){
							$("#addDataForm")[0].reset();resetValidatorClass();
							$.post('completionInfoAction!getCompletionInfoFile.huzd',{'projectId':$("#projectId").val()},function(msg){
								var result = (new Function("return " + msg))();
								if(result.flag){
									completionInfoTree = $.fn.zTree.init($("#completionInfoTree"),completionInfoSetting,result.completionInfo);
								}else{
									art.dialog({title:'系统提示',content:result.message,time:2000});
								}
							},'json');
							art.dialog({title:'系统提示',content:'平移成功！',time:2000});
						}else{
							art.dialog({title:'系统提示',content:'平移出错请重试！',time:2000});
						}}
					});
				}
			}
		});
	});
	
	function treeClickEvent(event,treeId,treeNode){
	}
	</script>    
</body>

</html>
  