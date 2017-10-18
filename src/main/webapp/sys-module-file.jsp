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
	    <div class="content">
			<div class="widget_box">
	    		<div class="condition">
					文档名称：<input class="dfinput smaller"  style="height:32px" type="text" id="key"/>
							<button id="queryUserBtn" class="btn_small">查询</button>
							<button id="submitBtn" class="btn_small">提交</button>
				</div>	 
	    		<div class="widget_body">
	    			<div class="ztree" id="moduleTree"></div>
	    		</div>
	    	</div>
	    </div>
    </div>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/ztree/jquery.ztree.core-3.5.min.js"></script>
    <script type="text/javascript" src="js/ztree/jquery.ztree.exhide-3.5.min.js"></script>
    <script type="text/javascript" src="js/ztree/jquery.ztree.excheck-3.5.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.plugins.min.js"></script>
	<script type="text/javascript">
	var setting = {check: {enable: true, nocheckInherit: false },data : {simpleData : {enable : true,rootPId : null}},callback : {onClick : treeClickEvent},async : {enable : true,url : "moduleAction!listFile.huzd?message=16",autoParam : [ "id=moduleId", "name=name", "level=level" ]}};
	var moduleTree = null;
	var hiddenNodes=[];
	$(function() {
		//加载ztree树
		moduleTree = $.fn.zTree.init($("#moduleTree"), setting);
		//查询按钮
		$("#queryUserBtn").bind("click",function(){
			//显示上次搜索后背隐藏的结点
			moduleTree.showNodes(hiddenNodes);
			//查找不符合条件的叶子节点
			var _keywords=$("#key").val();
			function filterFunc(node){
				if(node.isParent||node.name.indexOf(_keywords)!=-1) return false;
				return true;		
			};
		    //用于展开树 原来的点击搜索不会展开
			if(_keywords.length>0){
				moduleTree.expandAll(true); 
			}else{
				moduleTree.expandAll(false); 
			}
			//获取不符合条件的叶子结点
			hiddenNodes=moduleTree.getNodesByFilter(filterFunc);
			//隐藏不符合条件的叶子结点
			moduleTree.hideNodes(hiddenNodes);	
		});
		//提交按钮
		$("#submitBtn").bind("click",function(){
			var selectNodes = moduleTree.getCheckedNodes(true);
			if(selectNodes.length ==0){
				art.dialog({title:'系统提示',content:'请选择文档！',time:2000});
				return false;
			}
			var modules = "";
			$.each(selectNodes,function(i,n){
				modules+=n.id;
				if(i<selectNodes.length - 1)modules+=",";
			});
			$.ajax({type:'POST',url:'moduleAction!moduleFile.huzd',data:{message:modules},async:false,dataType:'json',success:function(msg){
				alert(msg);
			}});
		});
		
	});
	function treeClickEvent() {}
    </script>    
</body>

</html>
  