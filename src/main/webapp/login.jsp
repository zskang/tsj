<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<base href="<%=basePath%>"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>${siteinfo.systemName}</title>
<link href="${_theme_}css/style.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="js/jquery.js"></script>
<script type="text/javascript" src="js/cloud.js" ></script>
<shiro:notAuthenticated>
	<script type="text/javascript">
	if (top.location != self.location){     
		top.location=self.location;     
	} 
	</script>
</shiro:notAuthenticated>
<script language="javascript">
$(function(){
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
	$(window).resize(function(){  
    $('.loginbox').css({'position':'absolute','left':($(window).width()-692)/2});
    }); 
	$("#chanageVC").click(function(){
		$("#validateCode").attr("src","${basePath}validAction.huzd?n="+Math.random());
	});    
});  
</script> 

</head>

<body style="background-color:#3eb3e5; background-image:url(${_theme_}images/login/light.png); background-repeat:no-repeat; background-position:center top; overflow:hidden;">



    <div id="mainBody">
      <div id="cloud1" class="cloud"></div>
      <div id="cloud2" class="cloud"></div>
    </div>  


<div class="logintop">    
    <span>欢迎使用  ${siteinfo.systemName}</span>   
    <!--  
    <ul>
    <li><a href="#">回首页</a></li>
    <li><a href="#">帮助</a></li>
    <li><a href="#">关于</a></li>
    </ul>   
    --> 
    </div>
    
    <div class="loginbody">
    
    <span class="systemlogo">
       <img class="loginlogo" src="${_theme_}images/login/logo.png" alt="logo" />
    </span> 
       
    <div class="loginbox">
    <form action="userAction!login.huzd" method="post">
    <ul>
    <li><input name="user.username" type="text" class="loginuser" value="帐号" onclick="JavaScript:this.value=''"/></li>
    <li><input name="user.password" type="password" class="loginpwd"/></li>
    <li> 
    	<s:if test="#application.siteinfo.showValidateCode==true"><input name="validateCode" type="text" class="loginvalidate" value="验证码" onclick="JavaScript:this.value=''"/><img class="validateImage" id="validateCode" src="${basePath}validAction.huzd"/>&nbsp;<a href="javascript:void(0)" id="chanageVC">看不清，换一个</a> </s:if>
    	<s:else>&nbsp;</s:else>
    </li>
    <li><input type="submit" class="loginbtn" value="登录"/><!-- <label><input name="remember" type="checkbox" value="true"/>记住密码</label> <label><a href="#">忘记密码？</a></label>--></li>
    </ul>
    </form>
	<s:if test="#session.loginError!=null">
		<div class="alert-error">${session.loginError}</div>
	</s:if>    
    </div>
    
    </div>
    
    
    
    <div class="loginbm"> ${siteinfo.companyName} 版权所有  &copy;<s:date name="#application.siteinfo.createTime" format="yyyy"/> </div>
	
    
</body>

</html>
