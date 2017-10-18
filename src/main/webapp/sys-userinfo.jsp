<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>个人信息</title>
<link rel="stylesheet" type="text/css"  href="${_theme_}css/style.css"/>
<link rel="stylesheet" type="text/css"  href="js/artDialog/skins/opera.css"/>
</head>

<body>
    <div class="formbody">
    <div class="formtitle"><span>个人信息修改</span></div>
    	<form id="addDataForm">
	    	<input type="hidden" name="user.org.id" id="orgId" value="<s:property value="#session.user.org.id"/>"/>
			<input type="hidden" name="user.id" id="userId" value="<s:property value="#session.user.id"/>"/>
			<input type="hidden" name="user.status" id="status" value="<s:property value="#session.user.status"/>"/>
	    <ul class="forminfo">
		    <li>
		    	<label>组织名称：</label>
		    	<input id="orgName" name="user.org.name" value="<s:property value="#session.user.org.name"/>" class="dfinput normal" readonly="readonly"/>
		    	<i>用户所在组织</i>
		    </li>
		    <li>
		    	<label>帐号：</label>
		    	<input  type="text" id="username" name="user.username" value="<s:property value="#session.user.username"/>" readonly="readonly" class="dfinput normal"/>
		    	<i>登录帐号</i>
		    </li>
		    <li>
		    	<label>姓名：</label>
		    	<input type="text" id="chinesename" name="user.chinesename" class="dfinput normal" value="<s:property value="#session.user.chinesename"/>"/>
		    	<i>中文名</i>
		    </li>
		    <li>
		    	<label>密码：</label>
		    	<input type="password" id="password" name="user.password" value="<s:property value="#session.user.password"/>" class="dfinput normal"/>
		    	<i>登录密码</i>
		    </li>
		    <li>
		    	<label>确认密码：</label>
		    	<input  type="password" id="repassword" name="repassword" value="<s:property value="#session.user.password"/>" class="dfinput normal"/>
		    	<i>登录密码确认</i>
		    </li>
		    <li>
		    	<label>手机：</label>
		    	<input type="text" id="mobilephone" name="user.mobilephone" value="<s:property value="#session.user.mobilephone"/>" class="dfinput normal"/>
		    	<i></i>
		    </li>
		    <li>
		    	<label>座机：</label>
		    	<input type="text" id="telephone" name="user.telephone" value="<s:property value="#session.user.telephone"/>"  class="dfinput normal"/>
		    	<i></i>
		    </li>
		    <li>
		    	<label>角色：</label>	
			    <s:if test="#session.user.roles.size>0">
			    	<s:iterator value="#session.user.roles" var="role" status="st">
			    		<input type="hidden" name="user.roles[<s:property value="#st.index"/>].id"  value="<s:property value="#role.id"/>"/>
		    			<input type="text" value="<s:property value="#role.name"/>"  class="dfinput small" readonly="readonly"/>
			    	</s:iterator>
			    </s:if>
		    	<i></i>
		    </li>

		    <li><label>&nbsp;</label><input id="saveBtn" type="button" class="btn" value="确认保存"/></li>
	    </ul>
	    </form>
    </div>
    <script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.message_cn.js"></script>	
	<script type="text/javascript" src="js/validate/jquery.validate.method.js"></script>	    
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    
	<script type="text/javascript">
	$(function(){
		$("#addDataForm").validate({  
			rules:{'user.chinesename':{required:true,unameValid:true},
				   'user.password':{required:true,minlength:6},
				   'user.mobilephone':{required:true,isMobile:true},
				   'user.telephone':{checkTel:true},
				   'repassword':{required:true,minlength:6,equalTo:'#password'}},
	 				errorElement:'b',success:function(label){
						label.remove();
					}
		});				
		$("#saveBtn").click(function(){
			if($("#addDataForm").valid()){
				$.post('userAction!update.huzd',$("#addDataForm").serialize(),function(msg){
					if(msg.flag == true){
						art.dialog({title:'系统提示',content:'修改成功',time:2000});
					}else art.dialog({title:'系统提示',content:'修改失败，请重试',time:2000});
				},'json');	
			}else{
				art.dialog({title:'系统提示',content:'信息填写错误，请检查！',time:2000});
				return false;
			}
		});			
	});			
	</script>  
</body>
</html>
