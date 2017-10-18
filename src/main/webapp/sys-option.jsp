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
<title>选项管理</title>
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
	    		<shiro:hasPermission name="option:add">
		        <li class="click btn_add" id="addBtn">添加</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="option:update">
		        <li class="click btn_edit" id="updateBtn">修改</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="option:delete">
		        <li class="click btn_delete" id="deleteBtn">删除</li>
		        </shiro:hasPermission>
	        </ul>
	    </div>
    
     	<div>
	    	<div class="content">
   				<div class="condition">
					分类：<input class="dfinput small" type="text" id="q_category"/>
					分类名称：<input class="dfinput small" type="text" id="q_categoryname"/>
					选项名称：<input class="dfinput small" type="text" id="q_name"/>
					<button id="queryBtn" class="btn_small">查询</button>
				</div>	    	
			   <table class="tablelist">
			    	<thead>
			    	<tr>
				        <th style="width:5%;"><a href="javascript:void(0)" id="selectAll">全选</a></th>
				        <th style="width:10%;">编码</th>
				        <th style="width:10%;">分类</th>
				        <th style="width:10%;">分类名称</th>
				        <th style="width:10%;">排序</th>
				        <th style="width:55%;">选项名称</th>
			        </tr>
			        </thead>
			        <tbody>
			        <tr>
			        <td colspan="6">数据加载中...</td>
			        </tr>        
			        </tbody>
			    </table>
   			</div>
   		</div>
   	</div>
    	
    	<!-- add option form -->
		<div id="addDataDiv" class="hide">
		  <form id="addDataForm">
		  	<input type="hidden" id="id" value="" name="option.id"/> 
		  	<ul class="forminfo">
			    <li>
			    	<label>分类</label>
			    	<input type="text" value="" id="category" name="option.category" class="dfinput small"/>
			    	<i>英文分类</i>
			    </li>	
			    <li>
			    	<label>分类名称</label>
			    	<input type="text" value="" id="categoryname" name="option.categoryname" class="dfinput small"/>
			    	<i>中文名称</i>
			    </li>
			    <li>
			    	<label>排序编码</label>
			    	<input type="text" value="" id="order" name="option.order" class="dfinput small"/>
			    	<i>数字</i>
			    </li>
			    <li>
			    	<label>选项名称</label>
			    	<input type="text" value="" id="name" name="option.name" class="dfinput small"/>
			    	<i>中文名称</i>
			    </li>
		  	</ul>
		  </form>
		 </div> 
		<!-- add Option form -->
		
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.plugins.min.js"></script>
  	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.message_cn.js"></script>	
	<script type="text/javascript" src="js/validate/jquery.validate.method.js"></script> 
	<script type="text/javascript" src="js/easyPage/jquery.easypage.js"></script> 
	<script type="text/javascript" src="js/pagehelper.js"></script>	
	<script type="text/javascript" src="js/validate/resetFormHelper.js"></script>
	<script type="text/javascript">
		/**获取查询条件参数**/
		function getQueryParam(){
			return {'option.category':$("#q_category").val(),'option.categoryname':$("#q_categoryname").val(),'option.name':$("#q_name").val()};
		}
		
		/**表格填充函数**/
		function fillTable(data){
			var innerHtml = "";
			if(data.flag&&null!=data.options&&data.options.length>0){
				$.each(data.options,function(i,n){
					innerHtml +=("<tr><td><input type='checkbox' name='dataIds' value='"+n.id+"'/></td>");
					innerHtml +=("<td>"+n.id+"</td>");
					innerHtml +=("<td>"+n.category+"</td>");
					innerHtml +=("<td>"+n.categoryname+"</td>");
					innerHtml +=("<td>"+n.order+"</td>");
					innerHtml +=("<td>"+n.name+"</td></tr>");
				});
			}else{
				if(data.message)art.dialog({title:'系统提示',content:data.message});
				innerHtml += "<tr><td colspan='6'>无数据</td></tr>";
			}
			$(".tablelist>tbody").html(innerHtml);
			$('.tablelist tbody tr:odd').addClass('odd');
		}
		
		var setting = {check:{enable:true},data: {simpleData: {enable: true,rootPId:null}}};
		var resourceTree = null;
		var tableList = null;
		
		$(document).ready(function(){
			tableList = $(".tablelist").page({
	             prefix:'',
	             url:'optionAction!list.huzd',
	             pageSize:[10,5,20,30],
	             queryBtn:'queryBtn',
	             param:getQueryParam,
	             fillTable:fillTable
	   		});
			
			/*角色添加表格校验*/
			$("#addDataForm").validate({   
				rules:{'option.category':{required:true,minlength:3},
					   'option.categoryname':{required:true,cnCharset:true},
					   'option.order':{required:true,digits:true},
					   'option.name':{required:true,cnCharset:true}},
 				errorElement:'b',success:function(label){
					label.remove();
				}
			});	
			
			//添加事件
			$("#addBtn").click(function(){
				art.dialog({title:'选项添加',content:document.getElementById('addDataDiv'),lock:true,
					okValue:'添加',ok:function(){
						if($("#addDataForm").valid()){
							$.ajax({type:'POST',url:'optionAction!add.huzd',
								    data:$("#addDataForm").serialize(),async:false,dataType:'json',
								    success:function(msg){
										if(true==msg.flag){
											art.dialog({title:'系统提示',content:'添加成功',time:2000});
											tableList.refresh();
											$("#addDataForm")[0].reset();resetValidatorClass();return true;
									}else{
										art.dialog({title:'系统提示',content:msg.message,time:9000});
									}
								 }
							});
						}else{return false;}
					},cancelValue:'关闭',cancel:function(){$("#addDataForm")[0].reset();resetValidatorClass();}});
			});				
			
			
			//删除功能
			$("#deleteBtn").click(function(){
				if($('input[name="dataIds"]:checked').length==0){
					art.dialog({title:'系统提示',content:'请选择需要删除的信息！',time:2000});
					return;
				}
				var dataIds = "";
				$.each($('input[name="dataIds"]:checked'),function(i,n){
					dataIds+=$(this).val();
					if(i<$('input[name="dataIds"]:checked').length - 1)dataIds+=",";
				});	
				$.post('optionAction!delete.huzd',{dataIds:dataIds},function(msg){
					if(msg.flag == true){
						art.dialog({title:'系统提示',content:'删除成功',time:2000});
						tableList.refresh();
					}else art.dialog({title:'系统提示',content:msg.message,time:2000});
				},'json');					
			});
			
			//更新按钮
			$("#updateBtn").click(function(){
				if($('input[name="dataIds"]:checked').length==0){
					art.dialog({title:'系统提示',content:'请选择一个需要修改的选项！',time:2000});
				}
				if($('input[name="dataIds"]:checked').length>1){
					art.dialog({title:'系统提示',content:'只能单个修改！',time:2000});
				}
				if($('input[name="dataIds"]:checked').length==1){
					$.post('optionAction!get.huzd',{'option.id':$('input[name="dataIds"]:checked').val()},function(msg){
						if(msg.option != null){
							$("#id").val(msg.option.id);
							$("#category").val(msg.option.category);
							$("#categoryname").val(msg.option.categoryname);
							$("#order").val(msg.option.order);
							$("#name").val(msg.option.name);
							art.dialog({title:'系统提示',content:document.getElementById('addDataDiv'),okValue:'提交',cancelValue:'关闭',cancel:function(){return true;},ok:function(){
								if($("#addDataForm").valid()){
								$.post('optionAction!update.huzd',$("#addDataForm").serialize(),function(msg){
									if(msg.flag == true){
										art.dialog({title:'系统提示',content:'修改成功',time:2000});
										tableList.refresh();
										$("#addDataForm")[0].reset();resetValidatorClass();
									}else art.dialog({title:'系统提示',content:msg.message,time:2000});
								},'json');	
								}else{return false;}
							}});
						}else{
							art.dialog({title:'系统提示',content:'选项查询失败，请重试',time:2000});
						}
					},'json');					
				}				
			});
			
		});
	</script>
</body>

</html>
