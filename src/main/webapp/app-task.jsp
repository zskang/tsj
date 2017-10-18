<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>任务管理</title>
<link rel="stylesheet" type="text/css"  href="${_theme_}css/style.css" />
<link href="js/artDialog/skins/opera.css" rel="stylesheet" type="text/css" />
<link href="js/easyPage/css/skins/jquery.easypage.css" rel="stylesheet" type="text/css" />
</head>
<body>
    <div class="rightinfo">
    <div class="tools">
    	<ul class="toolbar">
	        <shiro:hasPermission name="task:update">
	       		<li class="click btn_delete" id="deleteBtn">删除</li>
	        </shiro:hasPermission>
        </ul>
    </div>
	<div class="condition">
		<div class="cond_item">开始时间：<input type="text" class="dfinput small Wdate" id="queryStartDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'queryEndDate\')}'})"/></div>
		<div class="cond_item">结束时间：<input type="text" class="dfinput small Wdate" id="queryEndDate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'queryStartDate\')}'})"/></div>
		<div class="cond_item">
			任务状态：<select class="dfinput small"  id="queryStatus">
						<option value="">全部</option>
						<option value="N">新建</option>
						<option value="R">执行</option>
						<option value="F">完成</option>
						<option value="E">异常</option>
					</select>
		</div>
		<div class="cond_item"><button id="queryBtn" class="btn_small">查询</button></div>
	</div>	
						    
    <table class="tablelist">
    	<thead>
	    	<tr>
		        <th style="width:5%;"><a href="javascript:void(0)" id="selectAll">全选</a></th>
		        <th style="width:35%;">任务名称</th>
		        <th style="width:8%;">创建时间</th>
		        <th style="width:10%;">创建人</th>
		        <th style="width:8%;">结束时间</th>
		        <th style="width:8%;">记录条数</th>
		        <th style="width:8%;">状态</th>
		        <th style="width:10%;">备注</th>
		        <th style="width:8%;">操作</th>
	        </tr>
        </thead>
        <tbody>
	        <tr>
	        	<td colspan="9">数据加载中...</td>
	        </tr>        
        </tbody>
    </table>
    
    </div>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/common.js"></script>
  	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.message_cn.js"></script>	
	<script type="text/javascript" src="js/validate/jquery.validate.method.js"></script>      
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/easyPage/jquery.easypage.js"></script> 
    <script type="text/javascript" src="js/datahelper.js"></script> 
    <script type="text/javascript" src="js/option.js"></script> 
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script> 
 	<script type="text/javascript" src="js/pagehelper.js"></script>	   
	<script type="text/javascript">
		var tableList = null;
		function transStatus(status){
			if("N"==status)return "新建";
			if("R"==status)return "运行中";
			if("F"==status)return "完成";
			if("E"==status)return "异常";
		}
		
		/**获取查询条件参数**/
		function getQueryParam(){
			return {'startDate':$("#queryStartDate").val(),'endDate':$("#queryEndDate").val(),'task.status':$("#queryStatus").val()};
		}
		/**表格填充函数**/
		function fillTable(data){
			var innerHtml = "";
			if(data.flag&&data.tasks&&data.tasks.length>0){
				$.each(data.tasks,function(i,n){
					innerHtml +=("<tr><td>"+((null!=n.status&&(n.status=="F"||n.status=="E"))?"<input type='checkbox' name='dataIds' value='"+n.id+"'/>":" ")+"</td>");
					innerHtml +=("<td>"+n.name+"</td>");
					innerHtml +=("<td>"+n.createTime+"</td>");
					innerHtml +=("<td>"+n.author.chinesename+"</td>");
					innerHtml +=("<td>"+(n.finishTime?n.finishTime:"")+"</td>");
					innerHtml +=("<td>"+(n.totalRecords?n.totalRecords:"")+"</td>");
					innerHtml +=("<td>"+transStatus(n.status)+"</td>");
					innerHtml +=("<td>"+(n.remark?n.remark:"")+"</td>");
					innerHtml +=("<td>"+(n.status=="F"?"<a href='taskAction!download.huzd?task.id="+n.id+"'>下载</a>":" ")+"</td></tr>");
				});
			}else{
				if(data.message)art.dialog({title:'系统提示',content:data.message});
				innerHtml += "<tr><td colspan='9'>无数据</td></tr>";
			}
			$(".tablelist>tbody").html(innerHtml);
			$('.tablelist tbody tr:odd').addClass('odd');
		}
		
		$(document).ready(function(){
			tableList = $(".tablelist").page({
	             prefix:'',
	             url:'taskAction!list.huzd',
	             pageSize:[10,5,20,30],
	             queryBtn:'queryBtn',
	             param:getQueryParam,
	             fillTable:fillTable
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
				$.post('taskAction!delete.huzd',{dataIds:dataIds},function(msg){
					if(msg.flag == true){
						art.dialog({title:'系统提示',content:'删除成功',time:2000});
						tableList.refresh();
					}else art.dialog({title:'系统提示',content:'删除失败或者部分删除失败，请重试',time:2000});
				},'json');					
			});
		});
	</script>
</body>

</html>
