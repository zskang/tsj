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
<title>用户管理</title>
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
	    		<shiro:hasPermission name="user:add">
		        <li class="click btn_add" id="addBtn">添加</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="user:update">
		        <li class="click btn_edit" id="updateBtn">修改</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="user:delete">
		        <li class="click btn_delete" id="deleteBtn">删除</li>
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
	    	<div class="content_right">
	    		<div class="widget_box">
	    			<div class="widget_title"><span>用户列表</span></div>
	    			<div class="widget_body" style="padding:10px 5px 10px 5px;">
	    				<div class="condition">
							用户名：<input class="dfinput small" type="text" id="q_username"/>
							姓名：<input class="dfinput small" type="text" id="q_chinesename"/>
							手机：<input class="dfinput small" type="text" id="q_phone"/>
							<button id="queryBtn" class="btn_small">查询</button>
						</div>	    				
					    <table class="tablelist">
					    	<thead>
					    	<tr>
					        <th style="width:5%;"><a href="javascript:void(0)" id="selectAll">全选</a></th>
					        <th style="width:10%;">用户名</th>
					        <th style="width:10%;">姓名</th>
					        <th style="width:12%;">手机</th>
					        <th style="width:12%;">座机</th>
					        <th style="width:15%;">组织</th>
					        <th style="width:31%;">角色</th>
					        <th style="width:5%;">状态</th>
					        </tr>
					        </thead>
					        <tbody>
					        <tr>
					        <td colspan="8">数据加载中...</td>
					        </tr>        
					        </tbody>
					    </table>
	    			</div>
	    			
	    		</div>
	    	</div>
	    
	    
		<!-- add user form -->
		<div class="hide" id="addDataDiv" style="width:600px;height:500px;overflow-x:hidden;overflow-y:auto;">
		  <form id="addDataForm" name="addDataForm">
		  	<input type="hidden" name="user.org.id" id="orgId" value=""/>
		  	<input type="hidden" name="user.id" id="userId" value=""/>
		  	<div id="usual1" class="usual">
		  		<div class="itab">
			  		<ul> 
				    	<li><a href="#tab1" class="  selected">用户基本信息</a></li> 
				    	<li><a href="#tab2">角色	</a></li> 
				  	</ul>
		  		</div>
		  		<div id="tab1" class="tabson" style="display:block;">
				  	<ul class="forminfo">
					    <li>
					    	<label>组织名称：</label>
					    	<input type="text" value="" id="orgName" name="user.org.name" class="dfinput normal" readonly="readonly"/>
					    	<i>&nbsp;</i>
					    </li>		  		
					    <li>
					    	<label>用户名：</label>
					    	<input type="text" value="" id="username" name="user.username" class="dfinput normal"/>
					    	<i>&nbsp;</i>
					    </li>		  		
					    <li>
					    	<label>姓名：</label>
					    	<input type="text" value="" id="chinesename" name="user.chinesename" class="dfinput normal"/>
					    	<i>&nbsp;</i>
					    </li>		  		
					    <li>
					    	<label>密码：</label>
					    	<input type="password" id="password" name="user.password" class="dfinput normal"/>
					    	<i>&nbsp;</i>
					    </li>		  		
					    <li>
					    	<label>确认密码：</label>
					    	<input  type="password" id="repassword" name="repassword" class="dfinput normal"/>
					    	<i>&nbsp;</i>
					    </li>		  		
					    <li>
					    	<label>手机：</label>
					    	<input  type="text" id="mobilephone" name="user.mobilephone" class="dfinput normal"/>
					    	<i>&nbsp;</i>
					    </li>		  		
					    <li>
					    	<label>座机：</label>
					    	<input  type="text" id="telephone" name="user.telephone" class="dfinput normal"/>
					    	<i>&nbsp;</i>
					    </li>
					    <li>
					    	<label>邮箱：</label>
					    	<input  type="text" id="email" name="user.email" class="dfinput normal"/>
					    	<i>&nbsp;</i>
					    </li>
					    <!--   		
					    <li>
					    	<label>角色：</label>
					    	<select name="user.role.id" id="roleId" class="select1" style="width:150px;"></select>
					    	<i>&nbsp;</i>
					    </li> -->			    
					    <li>
					    	<label>状态：</label>
					    	<select name="user.status" id="status" class="select1" style="width:100px;">
					    		<option value="1">正常</option>
					    		<option value="0">禁用</option>
					    	</select>
					    	<i>&nbsp;</i>
					    </li>			    
				  	</ul>		  		
		  		</div>
		  		<div id="tab2" class="tabson">
				<table class="tablelist1" id="roleList">
				    	<thead>
				    	<tr>
				        <th style="width:5%;"><input name="" value="" checked="checked" type="checkbox"/></th>
				        <th style="width:45%;">名称</th>
				        <th style="width:50%;">描述</th>
				        </tr>
				        </thead>
				        <tbody>
				        <tr>
				        <td colspan="3">角色加载中...</td>
				        </tr> 
				        </tbody>
				    </table>		  		
		  		</div>
		  	</div>
		  	

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
	<script type="text/javascript" src="js/jquery.idTabs.min.js"></script>  
	<script type="text/javascript" src="js/easyPage/jquery.easypage.js"></script>
	<script type="text/javascript" src="js/javascript.extend.js"></script>
	<script type="text/javascript" src="js/pagehelper.js"></script>
	<script type="text/javascript" src="js/validate/resetFormHelper.js"></script>
		<script type="text/javascript">
		var setting = {data: {simpleData: {enable: true,rootPId:null}},callback:{onClick:treeClickEvent,onDblClick:treeDblClickEvent},async:{enable:true,url:"orgAction!list.huzd",autoParam:["id=orgId","name=name","level=level"]}};
		var orgTree = null;
		var treeDialog = null;
		var tableList = null;
		function canOperate(id,region,roleIds){
			var returnHtml = "";
			if(region==20){
				returnHtml = "";
			}else{
				if($.inArray(id,roleIds)>-1)returnHtml = "";
				else returnHtml = "disabled='disabled'";
				
			}
			return returnHtml;
		}
			$(function(){
				orgTree = $.fn.zTree.init($("#orgTree"),setting);
				$("#usual1 ul").idTabs(); 
				//加载角色列表
				$.ajax({type:'POST',url:'roleAction!roleList.huzd',dataType:'json',async:false,success:function(msg){
					if(msg.flag&&msg.roles&&msg.roles.length>0){
						var innerHtml = "";
						$.each(msg.roles,function(i,n){
							innerHtml += "<tr><td><input type='checkbox' "+canOperate(n.id,n.region,msg.authRoles)+" name='user.roles["+i+"].id' value='"+n.id+"'/></td><td>"+n.name+"</td><td>"+n.desc+"</td></tr>";
						});
						$("#roleList").html(innerHtml);
					}else{
						art.dialog({title:'系统提示',content:msg.message,time:2000});
					}
				}});				

				tableList = $(".tablelist").page({
					             prefix:'',
					             url:'userAction!list.huzd',
					             pageSize:[10,5,20,30],
					             queryBtn:'queryBtn',
					             param:getQueryParam,
					             fillTable:fillTable
					   		});
				
				$("#orgName").click(function(){
					treeDialog = art.dialog({
						title:'发布部门(双击节点)',
					    follow: document.getElementById('orgName'),
					    content: document.getElementById('orgTree')
					});
				});
				
				$("#addDataForm").validate({   
					rules:{'user.username':{required:true,userName:true},
						   'user.chinesename':{required:true,unameValid:true},
						   'user.password':{required:true,minlength:6},
						   'user.email':{required:true,email:6},
						   'user.mobilephone':{required:true,isMobile:true},
						   'user.telephone':{checkTel:true},
						   'repassword':{required:true,minlength:6,equalTo:'#password'}},
	 				errorElement:'b',success:function(label){
						label.remove();
					}
				});	

				
				$("#addBtn").click(function(){
					if(orgTree.getSelectedNodes().length ==0 ){
						art.dialog({title:'系统提示',content:'请选择归宿组织节点！',time:2000});
						return false;
					}else if(orgTree.getSelectedNodes().length >1){
						art.dialog({title:'系统提示',content:'只能选择一个归属组织！',time:2000});
						return false;
					}else{
						var treeNode = orgTree.getSelectedNodes()[0];
						$("#orgId").val(treeNode.id);
						$("#orgName").val(treeNode.name);
						$("#username").removeAttr("readonly");
						art.dialog({title:'用户添加',content:document.getElementById("addDataDiv"),lock:true,padding:0,
							okValue:'添加',ok:function(){
								if($("#addDataForm").valid()&&checkRoles()){
									$.ajax({type:'POST',url:'userAction!add.huzd',data:$("#addDataForm").serialize(),async:false,dataType:'json',success:function(msg){
										if(true==msg.flag){
											$("#addDataForm")[0].reset();resetValidatorClass();
											tableList.refresh();									
											art.dialog({title:'系统提示',content:'添加成功！',time:2000});
										}else{
											art.dialog({title:'系统提示',content:msg.message,time:2000});
										}}
									});
								}else{return false;}
							},cancelValue:'关闭',cancel:function(){$("#addDataForm")[0].reset();resetValidatorClass()}});							
					}
			
				});	
				
				$("#updateBtn").click(function(){
					if($('input[name="dataIds"]:checked').length!=1){
						art.dialog({title:'系统提示',content:'请选择一个需要修改的用户！',time:2000});
					}else{
						$.post('userAction!get.huzd',{'user.id':$('input[name="dataIds"]:checked').val()},function(msg){
							if(msg.user != null){
								$("#orgId").val(msg.user.org.id);
								$("#orgName").val(msg.user.org.name);
								$("#userId").val(msg.user.id);
								$("#username").val(msg.user.username);
								$("#username").attr("readonly","readonly");
								$("#password").val(msg.user.password);
								$("#repassword").val(msg.user.password);
								$("#chinesename").val(msg.user.chinesename);
								$("#mobilephone").val(msg.user.mobilephone);
								$("#telephone").val(msg.user.telephone);
								$("#email").val(msg.user.email);
								$("#status option[value='"+msg.user.status+"']").attr("selected","selected");
								if(msg.user.roles.length>0){
									$("input[name*='user.roles']").removeAttr("checked","checked");
									$.each(msg.user.roles,function(i,n){
										$("input[name*='user.roles'][value='"+n.id+"']").attr("checked","checked");
									});
								}
								art.dialog({title:'用户修改',content:document.getElementById('addDataDiv'),lock:true,okValue:'提交',cancelValue:'关闭',cancel:function(){$("#addDataForm")[0].reset();resetValidatorClass();return true;},ok:function(){
									if($("#addDataForm").valid()&&checkRoles()){
									$.post('userAction!update.huzd',$("#addDataForm").serialize(),function(msg){
										if(msg.flag == true){
											art.dialog({id:'ID-ALERT-ROLE7',title:'系统提示',content:'修改成功',time:2000});
											$("#addDataForm")[0].reset();resetValidatorClass();
											tableList.refresh();
										}else art.dialog({title:'系统提示',content:'修改失败，请重试',time:2000});
									},'json');	
									}else{return false;}
								}});
							}else{
								art.dialog({title:'系统提示',content:'用户信息加载查询失败，请重试',time:2000});
							}
						},'json');	
					}			
				});	
				
				//删除功能
				$("#deleteBtn").click(function(){
					if($('input[name="dataIds"]:checked').length==0){
						art.dialog({title:'系统提示',content:'请选择需要删除的用户！',time:2000});
					}else{
						var userIds = "";
						$.each($('input[name="dataIds"]:checked'),function(i,n){
							userIds+=$(this).val();
							if(i<$('input[name="dataIds"]:checked').length - 1)userIds+=",";
						});	
						$.post('userAction!delete.huzd',{dataIds:userIds},function(msg){
							if(msg.flag == true){
								art.dialog({id:'ID-DELETE-USER-S',title:'系统提示',content:'删除成功',time:2000});
								$("#totalRecords_").html("0");
								tableList.refresh();
							}else art.dialog({title:'系统提示',content:'删除失败或者部分删除失败，请重试',time:2000});
						},'json');	
					}
				});		
				
			});
			
			/**获取查询条件参数**/
			function getQueryParam(){
				var treeNodes = orgTree.getSelectedNodes();
				var userOrgId = treeNodes.length>0?treeNodes[0].id:'';
				return {'user.org.id':userOrgId,'user.org.code':'${sessionScope.user.org.code}','user.username':$("#q_username").val(),'user.chinesename':$("#q_chinesename").val(),'user.mobilephone':$("#q_phone").val()};
			}
			/**表格填充函数**/
			function fillTable(data){
				var innerHtml = "";
				if(data.flag&&data.users&&data.users.length>0){
					$.each(data.users,function(i,n){
						innerHtml +=("<tr><td><input type='checkbox' name='dataIds' value='"+n.id+"'/></td>");
						innerHtml +=("<td>"+n.username+"</td>");
						innerHtml +=("<td>"+n.chinesename+"</td>");
						innerHtml +=("<td>"+n.mobilephone+"</td>");
						innerHtml +=("<td>"+n.telephone+"</td>");
						innerHtml +=("<td>"+((null==n.org)?"无组织":n.org.name)+"</td>");
						innerHtml +=("<td>"+convertRoles(n.roles)+"</td>");
						innerHtml +=("<td>"+((1==n.status)?"正常":"禁用")+"</td></tr>");
					});
				}else{
					innerHtml += "<tr><td colspan='8'>无数据</td></tr>";
				}
				$(".tablelist>tbody").html(innerHtml);
				$('.tablelist tbody tr:odd').addClass('odd');
			}	
			function treeClickEvent(event,treeId,treeNode){
				$("#currentPage_").html("1");
				$("#queryBtn").click();
			}
			function treeDblClickEvent(event,treeId,treeNode){
				$("#orgId").val(treeNode.id);
				$("#orgName").val(treeNode.name); 
				$("#policyOrgTree").addClass("hide");
				treeDialog.close();
			}	
			function convertRoles(roles){
				if(null!=roles&&roles.length>0){
					var ret = "";
					$.each(roles,function(i,n){
						ret += n.name;
						if(i<roles.length-1)ret+=",";
					});
					return ret;
				}else{
					return "-";
				}
			}
			function checkRoles(){
				if($("input[name*='user.roles']:checked").length>0){
					return true;
				}else{
					$("a[href='#tab2']").click();
					art.dialog({title:'系统提示',content:'角色必选！',time:3000});
					return false;
				}
			}			
		</script>    
</body>

</html>
  