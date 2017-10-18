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
<title>角色管理</title>
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
	    		<shiro:hasPermission name="role:add">
		        <li class="click btn_add" id="addBtn">添加</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="role:update">
		        <li class="click btn_edit" id="updateBtn">修改</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="role:delete">
		        <li class="click btn_delete" id="deleteBtn">删除</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="role:config">
		        <li class="click btn_config" id="configBtn">配置</li>
		        </shiro:hasPermission>
	        </ul>
	    </div>
	    <div>
	    	<div class="content">
   				<div class="condition">
					角色名称：<input class="dfinput normal" type="text" id="q_name"/>
					<button id="queryBtn" class="btn_small">查询</button>
				</div>	    	
			    <table class="tablelist">
			    	<thead>
			    	<tr>
				        <th style="width:5%;"><a href="javascript:void(0)" id="selectAll">全选</a></th>
				        <th style="width:15%;">名称</th>
				        <th style="width:10%;">英文简称</th>
				        <th style="width:15%;">是否可继承</th>
				        <th style="width:10%;">角色类型</th>
				        <th style="width:45%;">描述</th>
			        </tr>
			        </thead>
			        <tbody>
			        <tr>
			        <td colspan="9">数据加载中...</td>
			        </tr>        
			        </tbody>
			    </table>
	    	</div>
	    </div>    
    </div>
    
    	<!-- add Role form -->
		<div id="addRoleDiv" class="hide">
		  <form id="addRoleForm">
		  	<input type="hidden" id="id" value="" name="role.id"/> 
		  	<ul class="forminfo">
			    <li>
			    	<label>角色名称</label>
			    	<input type="text" value="" id="name" name="role.name" class="dfinput small"/>
			    	<i>中文名称</i>
			    </li>
			    <li>
			    	<label>英文简称</label>
			    	<input type="text" value="" id="shortName" name="role.shortName" class="dfinput small"/>
			    	<i>英文简称 （工作流使用）</i>
			    </li>
			    <li>
			    	<label>是否可继承</label>
			    	<select id="canInherit" name="role.canInherit" class="dfinput small">
			    		<option value="false">否</option>
			    		<option value="true">是</option>
			    	</select>
			    	<i>如果可继承；被赋权的用户可以把该权限赋权给别人</i>
			    </li>	
			    <li>
			    	<label>角色类型</label>
			    	<select id="region" name="role.region" class="dfinput small">
			    		<option value="*">超级管理员</option>
			    		<option value="10">职务角色</option>
			    		<option value="20">任务角色</option>
			    	</select>
			    	<i></i>
			    </li>	
			    <li>
<pre>
	  系统内置三个类别的权限范围：超级管理员、职务角色、任务角色。
	  权限范围：超级管理员>职务角色>任务角色。
	  高类别可以添加、删除、修改 低类别的角色。反之则被禁止！
	  用户如果拥有多个角色，取最高类别做为用户的权限范围。
</pre>
			    	
			    </li>			    
		  	</ul>
		  	<ul class="forminfo">
			    <li>
			    	<label>角色描述</label>
			    	<textarea rows="4" cols="5" style="width:300px;" id="desc" name="role.desc"></textarea>
			    	<i>角色使用人群和权限描述</i>
			    </li>	
		  	</ul>
		  </form>
		 </div> 
		<!-- add Role form -->
		
		<!-- config Role form -->
		<div id="configRoleDiv" class="hide"  style="width:600px;height:435px;overflow-x:hidden;overflow-y:auto;">
			<div id="resTree" class="ztree">
				加载中...
			</div>
		</div> 
		<!-- config Role form -->
    
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.plugins.min.js"></script>
  	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.message_cn.js"></script>	
	<script type="text/javascript" src="js/validate/jquery.validate.method.js"></script> 
	<script type="text/javascript" src="js/ztree/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="js/easyPage/jquery.easypage.js"></script>
    <script type="text/javascript" src="js/pagehelper.js"></script>
	<script type="text/javascript" src="js/convert.js"></script>
	<script type="text/javascript" src="js/validate/resetFormHelper.js"></script>
	<script type="text/javascript">
		function convertRegion(val){
			if("*"==val)return "超级管理员";
			if(10==val)return "职务角色";
			if(20==val)return "任务角色";
			else return "--";
		}
		/**获取查询条件参数**/
		function getQueryParam(){
			return {'role.name':$("#q_name").val()};
		}
		/**表格填充函数**/
		function fillTable(data){
			var innerHtml = "";
			if(data.flag&&data.roles&&data.roles.length>0){
				$.each(data.roles,function(i,n){
					innerHtml +=("<tr><td><input type='checkbox' name='dataIds' value='"+n.id+"'/></td>");
					innerHtml +=("<td>"+n.name+"</td>");
					innerHtml +=("<td>"+((n.shortName)?n.shortName:"")+"</td>");
					innerHtml +=("<td>"+convertBoolean(n.canInherit)+"</td>");
					innerHtml +=("<td>"+convertRegion(n.region)+"</td>");
					innerHtml +=("<td>"+n.desc+"</td></tr>");
				});
			}else{
				if(data.message)art.dialog({title:'系统提示',content:data.message});
				innerHtml += "<tr><td colspan='9'>无数据</td></tr>";
			}
			$('.tablelist>tbody').html(innerHtml);
			$('.tablelist tbody tr:odd').addClass('odd');
		}
		
		
		function submitRoleResource(){
			var roleId = $('input[name="dataIds"]:checked').val();
			var selectNodes = resourceTree.getCheckedNodes(true);
			var resIds = "";
			$.each(selectNodes,function(i,n){
				resIds+=n.id;
				if(i<selectNodes.length - 1)resIds+=",";
			});
			$.ajax({type:'POST',url:'roleAction!config.huzd',data:{'role.id':roleId,resIds:resIds},async:false,
				dataType:'json',success:function(msg){
				if(msg&&msg.flag==true){art.dialog({title:'系统提示',content:'权限配置成功！',time:2000});return true;
				}else{art.dialog({title:'系统提示',content:msg.message,time:9000});}}
			});
		}	
		
		var setting = {check:{enable:true},data: {simpleData: {enable: true,rootPId:null}}};
		var resourceTree = null;
		var tableList = null;
		$(document).ready(function(){
		
			tableList = $(".tablelist").page({
	             prefix:'',
	             url:'roleAction!list.huzd',
	             pageSize:[10,5,20,30],
	             queryBtn:'queryBtn',
	             param:getQueryParam,
	             fillTable:fillTable
	   		});
			
			/*角色添加表格校验*/
			$("#addRoleForm").validate({ 
				rules:{'role.name':{required:true,minlength:3,maxlength:10,cnCharset:true},
					   'role.shortName':{required:true,lettersonly:true},
					   'role.desc':{required:true,minlength:3}},
				errorElement:'b',success:function(label){
					label.remove();
				}
			});	
			
			//添加事件
			$("#addBtn").click(function(){
				art.dialog({title:'角色添加',content:document.getElementById('addRoleDiv'),lock:true,
					okValue:'添加',ok:function(){
						if($("#addRoleForm").valid()){
						$.ajax({type:'POST',url:'roleAction!add.huzd',
							    data:$("#addRoleForm").serialize(),async:false,dataType:'json',
							    success:function(msg){
									if(true==msg.flag){
										art.dialog({title:'系统提示',content:msg.message,time:3000});
										tableList.refresh();
										$("#addRoleForm")[0].reset();
										resetValidatorClass();
										return true;
								}else{
									art.dialog({title:'系统提示',content:msg.message,time:3000});
								}
							}
						});
						}else{return false;}
					},cancelValue:'关闭',cancel:function(){$("#addRoleForm")[0].reset();resetValidatorClass();}});
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
				$.post('roleAction!delete.huzd',{dataIds:dataIds},function(msg){
					if(msg.flag == true){
						art.dialog({title:'系统提示',content:(msg.message?msg.message:"删除成功！"),time:2000});
					}else art.dialog({title:'系统提示',content:msg.message,time:3000});
					tableList.refresh();
				},'json');					
			});
			
			//更新按钮
			$("#updateBtn").click(function(){
				if($('input[name="dataIds"]:checked').length==0){
					art.dialog({title:'系统提示',content:'请选择一个需要修改的角色！',time:2000});
				}
				if($('input[name="dataIds"]:checked').length>1){
					art.dialog({title:'系统提示',content:'只能单个修改！',time:2000});
				}
				if($('input[name="dataIds"]:checked').length==1){
					$.post('roleAction!get.huzd',{'role.id':$('input[name="dataIds"]:checked').val()},function(msg){
						if(msg.role != null){
							$("#id").val(msg.role.id);
							$("#name").val(msg.role.name); 
							$("#shortName").val(msg.role.shortName); 
							if(msg.role.id==1)$("#name").attr("readonly","readonly");
							else $("#name").removeAttr("readonly");
							$("#canInherit option[value='"+msg.role.canInherit+"']").attr("selected","selected");
							$("#region option[value='"+msg.role.region+"']").attr("selected","selected");
							$("#desc").val(msg.role.desc);
							art.dialog({title:'系统提示',content:document.getElementById('addRoleDiv'),okValue:'提交',cancelValue:'关闭',cancel:function(){return true;},ok:function(){
								if($("#addRoleForm").valid()){
								$.post('roleAction!update.huzd',$("#addRoleForm").serialize(),function(msg){
									if(msg.flag == true){
										art.dialog({title:'系统提示',content:'修改成功',time:2000});
										tableList.refresh();
										$("#addRoleForm")[0].reset();resetValidatorClass();
									}else art.dialog({title:'系统提示',content:msg.message,time:2000});
								},'json');	
								}else{return false;}
							}});
						}else{
							art.dialog({title:'系统提示',content:'角色查询失败，请重试',time:2000});
						}
					},'json');					
				}				
			});
			
			//配置事件
			$("#configBtn").click(function(){
				if($('input[name="dataIds"]:checked').length==0){
					art.dialog({title:'系统提示',content:'请选择一个需要配置的角色！',time:2000});
				}
				if($('input[name="dataIds"]:checked').length>1){
					art.dialog({title:'系统提示',content:'只能单个配置',time:2000});
				}
				if($('input[name="dataIds"]:checked').length==1){
					$.post('roleAction!config.huzd',{'role.id':$('input[name="dataIds"]:checked').val(),type:'load'},function(msg){
						var result = (new Function("return " + msg))();
						if(result.flag){
							resourceTree = $.fn.zTree.init($("#resTree"), setting,result.resources);
							art.dialog({title:'权限配置',content:document.getElementById('configRoleDiv'),okValue:'提交',cancelValue:'关闭',lock:true,cancel:function(){return true;},ok:submitRoleResource});			
						}else{
							art.dialog({title:'系统提示',content:result.message,time:2000});
						}
					},'json');
					
				}
			});			
			
		});
	</script>
</body>

</html>
