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
<title>专项中心</title>
<link rel="stylesheet" type="text/css"  href="${_theme_}css/style.css" />
<link rel="stylesheet" type="text/css"  href="js/artDialog/skins/opera.css" />
<link rel="stylesheet" type="text/css"  href="js/ztree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"  href="js/easyPage/css/skins/jquery.easypage.css" />
<style type="text/css">span{display:inherit;}
.autocomplete-suggestions { border: 1px solid #999; background: #FFF; overflow: auto; }
.autocomplete-suggestion { padding: 2px 5px; white-space: nowrap; overflow: hidden; }
.autocomplete-selected { background: #F0F0F0; }
.autocomplete-suggestions strong { font-weight: normal; color: #3399FF; }
.autocomplete-group { padding: 2px 5px; }
.autocomplete-group strong { display: block; border-bottom: 1px solid #000;}
</style>
</head>
<body>
    <div class="rightinfo">
	    <div class="tools">
	    	<ul class="toolbar">
	    		<shiro:hasPermission name="project:add">
		        <li class="click btn_add" id="addBtn">添加</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="project:update">
		        <li class="click btn_edit" id="updateBtn">修改</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="project:delete">
		        <li class="click btn_delete" id="deleteBtn">删除</li>
		        </shiro:hasPermission>
		        <shiro:hasPermission name="project:config">
		        <li class="click btn_config" id="configBtn">配置</li>
		        </shiro:hasPermission>
	        </ul>
	    </div>
	    <div>
   				<div class="condition">
   				    专项名称：<input class="dfinput smaller" type="text" id="q_name"/>
   				    负责人：<input class="dfinput small" type="text" id="q_master"/>
   				   <input class="dfinput small" type="hidden" id="q_masterId"/>
   				    项目类型：<select id="q_type" class="dfinput smaller asynOptions" data="projectType"><option value="">--- 请选择 ---</option></select>
					<!-- 开始时间：<input class="dfinput Wdate smaller"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'q_endDate\')}'})" style="height:32px" type="text" id="q_startDate" />
					结束时间：<input class="dfinput Wdate smaller" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'q_startDate\')}'})" style="height:32px" type="text" id="q_endDate"/> -->
					<button id="queryBtn" class="btn_small">查询</button>
				</div>		    
	    	<div class="content">
			    <table class="tablelist">
			    	<thead>
			    	<tr>
				        <th style="width:5%;"><a href="javascript:void(0)" id="selectAll">全选</a></th>
				        <th style="width:30%;">项目名称</th>
				        <th style="width:8%;">项目类型</th>
				        <th style="width:15%;">负责人</th>
				        <th style="width:8%;">开始时间</th>
				        <th style="width:8%;">结束时间</th>
				        <th style="width:8%;">工程造价</th>
				        <th style="width:10%;">计划结束时间</th>
				        <th style="width:8%;">文档下载</th>
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
    <a class="hide" href="" id="downloadUrl" target="_blank">d</a>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/jquery.autocomplete.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.plugins.min.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
  	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.message_cn.js"></script>	
	<script type="text/javascript" src="js/validate/jquery.validate.method.js"></script> 
	<script type="text/javascript" src="js/ztree/jquery.ztree.all-3.5.min.js"></script>
	<script type="text/javascript" src="js/easyPage/jquery.easypage.js"></script>
    <script type="text/javascript" src="js/pagehelper.js"></script>
	<script type="text/javascript" src="js/option.js"></script>
	<script type="text/javascript" src="js/convert.js"></script>
	<script type="text/javascript" src="js/validate/resetFormHelper.js"></script>
	<script type="text/javascript">
		/**获取查询条件参数**/
		function getQueryParam(){
			return {'project.name':$("#q_name").val(),'project.type.id':$("#q_type").val(),'project.master.id':$("#q_masterId").val()};
		}
		/**表格填充函数**/
		function fillTable(data){
			var innerHtml = "";
			if(data.flag&&data.projects&&data.projects.length>0){
				$.each(data.projects,function(i,n){
					innerHtml +=("<tr><td><input type='checkbox' name='dataIds' value='"+n.id+"'/></td>");
					innerHtml +=("<td>"+n.name+"</td>");
					innerHtml +=("<td>"+n.type.name+"</td>");
					innerHtml +=("<td>"+(null!=n.master?(n.master.chinesename+"["+n.master.mobilephone+"]"):"-")+"</td>");
					innerHtml +=("<td>"+n.startDate+"</td>");
					innerHtml +=("<td>"+n.endDate+"</td><td>"+n.price+" (千万)</td><td>"+n.finishDate+"</td><td>"+(n.complete==1?"<a href='javascript:;' class='downloadDocBtn' id='"+n.id+"'>下载</a>":"流程未完成")+"</td></tr>");
				});
			}else{
				if(data.message)art.dialog({title:'系统提示',content:data.message});
				innerHtml += "<tr><td colspan='8'>无数据</td></tr>";
			}
			$('.tablelist>tbody').html(innerHtml);
			$('.tablelist tbody tr:odd').addClass('odd');
		}
		var tableList = null;
		$(document).ready(function(){
			
			$.post("projectAction!listAll.huzd",null,function(msg){
				var innerHtml = "<option value=''>---请选择---</option>";
				if(msg&&msg.projects&&msg.projects.length>0){
					$.each(msg.projects,function(i,n){
						innerHtml += ("<option value="+n.id+">"+n.name+"</option>");
					});
				}
				$("#parent").html(innerHtml);
			},"json");
			
			
			tableList = $(".tablelist").page({
	             prefix:'',
	             url:'projectAction!list.huzd',
	             pageSize:[10,5,20,30],
	             queryBtn:'queryBtn',
	             param:getQueryParam,
	             fillTable:fillTable
	   		});
			
			//添加事件
			$("#addBtn").click(function(){
				window.location.href="app-project-edit.jsp";
			});				
			
			$(".downloadDocBtn").live("click",function(){
				var id = $(this).attr("id");
				var d_ = art.dialog({title:'系统提示',content:'文档后台压缩中...',lock:true});
				$.post("projectAction!zipDoc.huzd",{'project.id':id},function(msg){
					if(msg.flag){
						d_.content("服务器压缩完成！立刻下载...<a style='color:red;' target='_blank' href='projectAction!download.huzd?project.id="+id+"'>浏览器未响应请点击此处</a>");
						$("#downloadUrl").attr("href","projectAction!download.huzd?project.id="+id);
						document.getElementById("downloadUrl").click(); 
					}else{
						d_.content(msg.message);
					}
				},"json");
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
				$.post('projectAction!delete.huzd',{dataIds:dataIds},function(msg){
					if(msg.flag == true){
						art.dialog({title:'系统提示',content:(msg.message?msg.message:"删除成功！"),time:2000});
					}else art.dialog({title:'系统提示',content:msg.message,time:3000});
					tableList.refresh();
				},'json');					
			});
			
			//更新按钮
			$("#updateBtn").click(function(){
				if($('input[name="dataIds"]:checked').length!=1){
					art.dialog({title:'系统提示',content:'请选择一个需要修改的信息！',time:2000});
				}else{
					window.location.href='app-project-edit.jsp?aId='+$('input[name="dataIds"]:checked').val();
				};	
			});
		});
	</script>
</body>

</html>
