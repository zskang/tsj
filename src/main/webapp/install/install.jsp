<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>iPortal自助安装</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<link rel="stylesheet" type="text/css" href="./install/css/install.css">
	<script type="text/javascript" src="./js/jquery.js"></script>
	<script type="text/javascript" src="./js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="./js/validate/jquery.validate.method.js"></script>
	<script type="text/javascript" src="./js/validate/jquery.validate.message_cn.js"></script>
  </head>
  <body>
  	<div class="install_box">
  	  <div class="index_mian_right_two_ly title">
  	  	<span><img src="./install/images/logo.png" width="85px" height="91px"/></span>
  	  	<span style="text-align: center; padding:0px 40px;font-weight: bold; font-size: 20px;line-height: 25px;height:50px;">iPortal 自助安装</span>
  	  </div>
  	<form action="/siteInit" id="initSiteForm">
		  <div class="index_mian_right_two_ly">
		   <div class="index_mian_right_two_one_ly"><span>系统设定</span></div>
		   <div class="index_mian_right_two_two_ly">
		   
		     <div class="index_mian_right_two_two_o_ly"><b>系统名称：</b><input class="index_mian_right_two_two_text_ly" name="siteName" type="text"/><span>中文名称，简短达意，建议不超过8个字。</span></div>
		     <div class="index_mian_right_two_two_o_ly"><b>英文名称：</b><input class="index_mian_right_two_two_text_ly" name="siteEnName" type="text"/><span>英文名称，简短达意，建议不超过8个单词。</span></div>
		     <div class="index_mian_right_two_two_o_ly"><b>系统地址：</b><input class="index_mian_right_two_two_text_ly" name="siteUrl" type="text"/><span>系统网址或IP地址，例如：http://localhost</span></div>
		     <div class="index_mian_right_two_two_o_ly"><b>测试数据：</b><input id="siteInit" name="siteInit" value="1" type="checkbox">导入<span>导入初始化模板数据。管理员账号：admin 密码：123456</span></div>
		   </div>
		  </div>
		  <div class="index_mian_right_two_ly">
		   <div class="index_mian_right_two_one_ly"><span>管理员设定</span></div>
		   <div class="index_mian_right_two_two_ly">
		   
		     <div class="index_mian_right_two_two_o_ly"><b>初始账号：</b><input class="index_mian_right_two_two_text_ly" id="username" name="username" type="text"/><span>字母数字下划线、中文</span></div>
		     <div class="index_mian_right_two_two_o_ly"><b>初始密码：</b><input class="index_mian_right_two_two_text_ly" id="password" name="password" type="password"/><span>大写、小写、符号</span></div>
		     <div class="index_mian_right_two_two_o_ly"><b>确认密码：</b><input class="index_mian_right_two_two_text_ly" id="repassword" name="repassword" type="password"/><span>确认密码</span></div>
		   </div>
		  </div>
		  <div style="float:right;margin:10px 0px;">
		 	 <a href="javascript:;"><input id="submitBtn" name="" class="index_mian_right_seven_Forward_ly" value="提交" type="button"></a>
		  </div>
	  </form>	  
	 </div> 
	 
  </body>
  <script type="text/javascript">
  	$(function(){
		$("#initSiteForm").validate({   
			rules:{'siteName':{required:true,cnCharset:true},
				   'siteEnName':{required:true},
				   'siteUrl':{required:true,minlength:6},
				   'username':{required:true,isMobile:true},
				   'password':{required:true,minlength:6},
				   'repassword':{required:true,minlength:6,equalTo:'#password2'}},
			errorPlacement:function(error, element) {
			 	error.appendTo(element.next()); 
			},errorElement:'span',
			success:function(label){
				label.html("<font color='green'>正确!</font>");
			}
		});	
		
		$("#siteInit").click(function(){
			if($("#siteInit:checked").val()){
				$("#username").val("admin").attr("disabled","disabled");
				$("#password").val("123456").attr("disabled","disabled");
				$("#repassword").val("123456").attr("disabled","disabled");
			}else{
				$("#username").val("").removeAttr("disabled");
				$("#password").val("").removeAttr("disabled");
				$("#repassword").val("").removeAttr("disabled");
			}
		});
		
		$("#submitBtn").click(function(){
			if($("#initSiteForm").valid()){
				console.log("submit");
				$("#initSiteForm").submit();
			}
		});
  	});
  </script>
</html>
