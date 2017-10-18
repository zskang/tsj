<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告添加</title>
<link rel="stylesheet" type="text/css" href="${_theme_}css/style.css" />
<link rel="stylesheet" type="text/css"  href="js/artDialog/skins/opera.css"/>
</head>

<body>
    <div class="formbody">
    <div class="formtitle">
    	<span>基本信息</span>
    	<div class="right"><a href="sys-anno.jsp">返回</a></div>
    </div>
    	<form id="addDataForm">
    	<input type="hidden" name="annou.id" value="" id="annouId"/>
	    <ul class="forminfo">
		    <li>
		    	<label>公告名称</label>
		    	<input id="annouTitle" name="annou.title" type="text" class="dfinput" />
		    	<i>标题2~30个字符</i>
		    </li>
		    <li>
		    	<label>过期时间</label>
		    	<input id="annouExpireData" name="annou.expireData" type="text" class="dfinput Wdate" onclick="WdatePicker()" style="height:32px"/>
		    	<i>公告内容过期时间</i>
		    </li>
		    <li>
		    	<label>公告内容</label>
		    	<textarea id="annouContent" 
		    	          name="annou.content" 
		    	          cols="100" rows="15"  
		    	          class="textinput xheditor {upImgUrl:'uploadAction.huzd',html5Upload:false,upMultiple:'1}" 
		    	          style="width:80%;height:300px;"></textarea>
		    	<i>&nbsp;</i>
		    </li>
		    <li><label>&nbsp;</label><input id="addBtn" type="button" class="btn" value="确认保存"/></li>
	    </ul>
	    </form>
    </div>
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/xhEditor/xheditor-1.1.14-zh-cn.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.message_cn.js"></script>	
	<script type="text/javascript" src="js/validate/jquery.validate.method.js"></script>	    
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>
    
<script type="text/javascript">
		$(function(){
			$("#addDataForm").validate({	            
				rules:{'annou.expireData':{required:true},
					   'annou.title':{required:true,minlength:2,maxlength:30,cnCharset:true},
					   'annou.content':{required:function(){return false;}}},
					   errorElement:'b',success:function(label){
							label.remove();
						}
			});				
			if('${param.aId}'!=null&&'${param.aId}'!=""){
				$.ajax({type:'POST',url:'annouAction!get.huzd',data:{'annou.id':'${param.aId}'},async:false,dataType:'json',success:function(msg){
					if(msg.annou != null){
						$("#annouId").val(msg.annou.id);
						$("#annouTitle").val(msg.annou.title);
						$("#annouExpireData").val(msg.annou.expireData);
						$("#annouContent").val(msg.annou.content);
					}else{
						art.dialog({id:'ID-ERROR',title:'系统提示',content:'查询出错请重试！',okValue:'确定',ok:function(){
					    	window.location.href='sys-anno.jsp'; 
					    	return true;
						}});
					}
				   }
				});						
			}
			$("#addBtn").click(function(){
				var content = $("#annouContent").val();
				var url = ('${param.aId}'!=null&&'${param.aId}'!="")?'annouAction!update.huzd':'annouAction!add.huzd';
				if($("#addDataForm").valid()&&(null!=content&&""!=content)){
				$.ajax({type:'POST',url:url,data:$("#addDataForm").serialize(),async:false,dataType:'json',success:function(msg){
					if(true==msg.flag){
						art.dialog({
						    title: '系统提示',
						    content: '操作成功！',okValue:'继续添加',
						    ok: function () {
						    	$("#addDataForm")[0].reset();
						    	return true;
						    },cancelValue:'返回列表',cancel:function(){
						    	window.location.href='sys-anno.jsp'; 
						    	return true;
						    }
						 });
						
					}else{
						art.dialog({title:'系统提示',content:'添加出错请重试！',time:2000});
					}
				 }
				});	
				}else{art.dialog({title:'系统提示',content:'公告正文必填',time:2000});;return false;}
			});			
		});
		</script>    
</body>
</html>
