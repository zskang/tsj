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
<title>公告管理</title>
<link rel="stylesheet" type="text/css" href="${_theme_}css/style.css" />
<link rel="stylesheet" type="text/css" href="js/artDialog/skins/opera.css"/>
<link rel="stylesheet" type="text/css" href="js/easyPage/css/skins/jquery.easypage.css"/>
<link rel="stylesheet" type="text/css" href="js/fakeloader/fakeLoader.css"/>
</head>
<body>
	<div class="fakeloader"></div>
    <div class="rightinfo">
    <div class="tools">
    	<ul class="toolbar">
    		<shiro:hasPermission name="annou:add">
	        <li class="click btn_add" id="addBtn">添加</li>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="annou:update">
	        <li class="click btn_edit" id="updateBtn">修改</li>
	        </shiro:hasPermission>
	        <shiro:hasPermission name="annou:delete">
	        <li class="click btn_delete" id="deleteBtn">删除</li>
	        </shiro:hasPermission>
        </ul>
    </div>
    <div>
    	<div class="content">
	  	    <table class="tablelist">
		    	<thead>
		    	<tr>
		        <th><a href="javascript:void(0)" id="selectAll">全选</a></th>
		        <th style="width:25%;">标题</th>
		        <th>创建时间</th>
		        <th>过期时间</th>
		        <th>创建人</th>
		        <th>点击次数</th>
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
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/easyPage/jquery.easypage.js"></script> 
    <script type="text/javascript" src="js/pagehelper.js"></script>
    <script type="text/javascript" src="js/fakeloader/fakeLoader.min.js"></script> 
	<script type="text/javascript">
		/**获取查询条件参数**/
		function getQueryParam(){
			return {};
		}
		/**表格填充函数**/
		function fillTable(data){
			var innerHtml = "";
			if(data.flag&&data.annous&&data.annous.length>0){
				$.each(data.annous,function(i,n){
					innerHtml +=("<tr><td><input type='checkbox' name='dataIds' value='"+n.id+"'/></td>");
					innerHtml +=("<td>"+n.title+"</td>");
					innerHtml +=("<td>"+n.createDate+"</td>");
					innerHtml +=("<td>"+((null!=n.expireData)?n.expireData:"永久有效")+"</td>");
					innerHtml +=("<td>"+n.author+"</td>");
					innerHtml +=("<td>"+n.hit+"</td></tr>");
				});
			}else{
				if(data.message)art.dialog({title:'系统提示',content:data.message});
				innerHtml += "<tr><td colspan='6'>无数据</td></tr>";
			}
			$(".tablelist>tbody").html(innerHtml);
			$('.tablelist tbody tr:odd').addClass('odd');
		}
		
		var tableList = null;
		$(document).ready(function(){
/*             $(".fakeloader").fakeLoader({
                timeToHide:1200,
                bgColor:"#fff",
                imagePath:"http://img4.imgtn.bdimg.com/it/u=2046131979,615497905&fm=23&gp=0.jpg"
            }); */
			tableList = $(".tablelist").page({
				             prefix:'',
				             url:'annouAction!list.huzd',
				             pageSize:[10,5,20,30],
				             queryBtn:'queryBtn',
				             param:getQueryParam,
				             fillTable:fillTable
				   		});
			
			$("#addBtn").click(function(){
				 window.location.href='sys-anno-edit.jsp';
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
				$.post('annouAction!delete.huzd',{dataIds:dataIds},function(msg){
					if(msg.flag == true){
						art.dialog({title:'系统提示',content:'删除成功',time:2000});
						tableList.refresh();
					}else art.dialog({title:'系统提示',content:'删除失败或者部分删除失败，请重试',time:2000});
				},'json');					
			});
			
			$("#updateBtn").click(function(){
				if($('input[name="dataIds"]:checked').length!=1){
					art.dialog({title:'系统提示',content:'请选择一个需要修改的信息！',time:2000});
				}else{
					window.location.href='sys-anno-edit.jsp?aId='+$('input[name="dataIds"]:checked').val();
				};				
			});			
			
		});
	</script>
</body>

</html>
