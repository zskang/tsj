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
<title>日志管理</title>
<link rel="stylesheet" type="text/css"  href="${_theme_}css/style.css" />
<link rel="stylesheet" type="text/css"  href="js/artDialog/skins/opera.css" />
<link rel="stylesheet" type="text/css"  href="js/ztree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"  href="js/easyPage/css/skins/jquery.easypage.css" />
<style type="text/css">span{display:inherit;}</style>
</head>
<body>
    <div class="rightinfo">
     	<div>
	    	<div class="content">
   				<div class="condition">
					开始时间：<input class="dfinput Wdate smaller"   onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'q_endDate\')}'})" style="height:32px" type="text" id="q_startDate" />
					结束时间：<input class="dfinput Wdate smaller" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'q_startDate\')}'})" style="height:32px" type="text" id="q_endDate"/>
					操作工号：<input class="dfinput small" type="text" id="q_userName"/>
					<button id="queryBtn" class="btn_small">查询</button>
				</div>	    	
			   <table class="tablelist">
			    	<thead>
			    	<tr>
				        <th style="width:8%;">编码</th>
				        <th style="width:10%;">访问IP</th>
				        <th style="width:10%;">操作工号</th>
				        <th style="width:15%;">操作模块</th>
				        <th style="width:7%;">操作类型</th>
				        <th style="width:5%;">操作结果</th>
				        <th style="width:15%;">操作时间</th>
				        <th style="width:25%;">操作内容</th>
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
    	
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>  
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.plugins.min.js"></script>
  	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.message_cn.js"></script>	
	<script type="text/javascript" src="js/validate/jquery.validate.method.js"></script> 
	<script type="text/javascript" src="js/easyPage/jquery.easypage.js"></script> 
	<script type="text/javascript">
		/**获取查询条件参数**/
		function getQueryParam(){
			return {startDate:$("#q_startDate").val(),endDate:$("#q_endDate").val(),'log.username':$("#q_userName").val()};
		}
		
		/**表格填充函数**/
		function fillTable(data){
			var innerHtml = "";
			if(data&&data.flag&&data.logs&&data.logs.length>0){
				$.each(data.logs,function(i,n){
					innerHtml +=("<tr>");
					innerHtml +=("<td>"+n.id+"</td>");
					innerHtml +=("<td>"+n.clientIp+"</td>");
					innerHtml +=("<td>"+n.username+"</td>");
					innerHtml +=("<td>"+n.operateModuleName+"</td>");
					innerHtml +=("<td>"+n.operateType+"</td>");
					innerHtml +=("<td>"+n.operateResult+"</td>");
					innerHtml +=("<td>"+n.operateTime+"</td>");
					innerHtml +=("<td title='"+n.operateContent+"'>"+(n.operateContent.length>20?(n.operateContent.substring(0,20)+"..."):n.operateContent)+"</td>");
					innerHtml +=("</tr>");
				});
			}else{
				innerHtml += "<tr><td colspan='8'>无数据</td></tr>";
				if(data.message)art.dialog({title:'系统提示',content:data.message});
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
	             url:'logAction!list.huzd',
	             pageSize:[10,5,20,30],
	             queryBtn:'queryBtn',
	             param:getQueryParam,
	             fillTable:fillTable
	   		});
			
			/*角色添加表格校验*/
			$("#addDataForm").validate({   
				rules:{'log.category':{required:true,minlength:3},
					   'log.categoryname':{required:true,cnCharset:true},
					   'log.order':{required:true,maxlength:2},
					   'log.name':{required:true,cnCharset:true}},
				errorElement:'i',	   
				errorPlacement:function(error, element) {
				 	error.appendTo(element.next()); 
				},
				success:function(label){
					label.html("<font color='green'>正确!</font>");
				}
			});	
			
			//添加事件
			$("#addBtn").click(function(){
				art.dialog({title:'选项添加',content:document.getElementById('addDataDiv'),lock:true,
					okValue:'添加',ok:function(){
						if($("#addDataForm").valid()){
						$.ajax({type:'POST',url:'logAction!add.huzd',
							    data:$("#addDataForm").serialize(),async:false,dataType:'json',
							    success:function(msg){
									if(true==msg.flag){
										art.dialog({title:'系统提示',content:'添加成功',time:2000});
										tableList.refresh();
										$("#addDataForm")[0].reset();return true;
								}else{art.dialog({title:'系统提示',content:'添加出错请重试！',time:2000});}}
						});
						}else{return false;}
					},cancelValue:'关闭',cancel:function(){$("#addDataForm")[0].reset();}});
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
				$.post('logAction!delete.huzd',{dataIds:dataIds},function(msg){
					if(msg.flag == true){
						art.dialog({title:'系统提示',content:'删除成功',time:2000});
						tableList.refresh();
					}else art.dialog({title:'系统提示',content:'删除失败或者部分删除失败，请重试',time:2000});
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
					$.post('logAction!get.huzd',{'log.id':$('input[name="dataIds"]:checked').val()},function(msg){
						if(msg.log != null){
							$("#id").val(msg.log.id);
							$("#category").val(msg.log.category);
							$("#categoryname").val(msg.log.categoryname);
							$("#order").val(msg.log.order);
							$("#name").val(msg.log.name);
							art.dialog({title:'系统提示',content:document.getElementById('addDataDiv'),okValue:'提交',cancelValue:'关闭',cancel:function(){return true;},ok:function(){
								if($("#addDataForm").valid()){
								$.post('logAction!update.huzd',$("#addDataForm").serialize(),function(msg){
									if(msg.flag == true){
										art.dialog({title:'系统提示',content:'修改成功',time:2000});tableList.refresh();$("#addDataForm")[0].reset();
									}else art.dialog({title:'系统提示',content:'修改失败，请重试',time:2000});
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
