<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>公告添加</title>
<link rel="stylesheet" type="text/css"  href="${_theme_}css/style.css"/>
<link rel="stylesheet" type="text/css"  href="js/artDialog/skins/opera.css"/>
</head>

<body>
    <div class="formbody">
    <div class="formtitle"><span>系统设置</span></div>
    	<form id="addDataForm">
    	<input type="hidden" name="siteInfo.id" id="siteInfo.id" value="${siteinfo.id}"/>
	    <ul class="forminfo">
		    <li>
		    	<label>系统名称</label>
		    	<input id="systemName" name="siteInfo.systemName" value="${siteinfo.systemName}" class="dfinput normal" />
		    	<i>系统中文名称</i>
		    </li>
		    <li>
		    	<label>英文名称</label>
		    	<input id="systemEnName" name="siteInfo.systemEnName" value="${siteinfo.systemEnName}" class="dfinput normal" />
		    	<i>系统英文名称</i>
		    </li>
		    <li>
		    	<label>公司名称</label>
		    	<input id="companyName" name="siteInfo.companyName" value="${siteinfo.companyName}" class="dfinput normal" />
		    	<i>系统英文名称</i>
		    </li>
		    <li>
		    	<label>网站网址</label>
		    	<input  id="systemAddress" name="siteInfo.systemAddress" value="${siteinfo.systemAddress}" class="dfinput normal"/>
		    	<i>互联网或者对外访问网址</i>
		    </li>
		    <li>
		    	<label>备案编号</label>
		    	<input type="text" id="ipcNo" name="siteInfo.ipcNo" value="${siteinfo.ipcNo}" class="dfinput normal"/>
		    	<i>互联网备案号</i>
		    </li>
		    <li>
		    	<label>图片水印</label>
		    	<input type="text" type="text" id="watermarkContent" name="siteInfo.watermarkContent" value="${siteinfo.watermarkContent}" class="dfinput normal"/>
		    	<input name="siteInfo.watermark" id="watermark" type="checkbox" checked="${siteinfo.watermark}" value="true"/>
		    	<i>互联网水印文字</i>
		    </li>
		    <li>
		    	<label>使用Tabs</label>
		    	<select id="useTabs" name="siteInfo.useTabs" class="dfinput small">
		    		<option value="true" ${siteinfo.useTabs == true?"selected='selected'":""}>是</option>
		    		<option value="false" ${siteinfo.useTabs == false?"selected='selected'":""}>否</option>
		    	</select>
		    	<i>系统打开集成的菜单时候使用多tab页的方式</i>
		    </li>
		    <li>
		    	<label>主题皮肤</label>
		    	<select id="theme" name="siteInfo.theme" class="dfinput small">
		    		<option value="skins/default/" ${siteinfo.theme == 'skins/default/'?"selected='selected'":""}>默认</option>
		    	</select>
		    	<i>系统展示皮肤</i>
		    </li>
		    <li>
		    	<label>登录验证码</label>
		    	<select id="showValidateCode" name="siteInfo.showValidateCode" class="dfinput small">
		    		<option value="true"  ${siteinfo.showValidateCode == true?"selected='selected'":""}>启用</option>
		    		<option value="false"  ${siteinfo.showValidateCode == false?"selected='selected'":""}>禁用</option>
		    	</select>
		    	<i>是否在登录界面展示验证码</i>
		    </li>
		    <li>
		    	<label>验证码类型</label>
		    	<select id="validateCodeType" name="siteInfo.validateCodeType" class="dfinput small">
		    		<option value="1"  ${siteinfo.validateCodeType == 1?"selected='selected'":""}>数字</option>
		    		<option value="2"  ${siteinfo.validateCodeType == 2?"selected='selected'":""}>字母</option>
		    		<option value="3"  ${siteinfo.validateCodeType == 3?"selected='selected'":""}>混合</option>
		    	</select>
		    	<i>登录界面验证码的展示类型</i>
		    </li>
		    <li>
		    	<label>登录失败锁定</label>
		    	<select id="loginFailLockTimes" name="siteInfo.loginFailLockTimes" class="dfinput small">
		    		<option value="-1"  ${siteinfo.loginFailLockTimes == -1?"selected='selected'":""}>不锁定</option>
		    		<option value="3"  ${siteinfo.loginFailLockTimes == 3?"selected='selected'":""}>3</option>
		    		<option value="5"  ${siteinfo.loginFailLockTimes == 5?"selected='selected'":""}>5</option>
		    		<option value="10"  ${siteinfo.loginFailLockTimes == 10?"selected='selected'":""}>10</option>
		    	</select>
		    	<i>登录失败次数设置；达到失败次数；账号被锁定10分钟！</i>
		    </li>
			<shiro:hasPermission name="siteInfo:update">
		    <li><label>&nbsp;</label><input id="submitFormBtn" type="button" class="btn" value="确认保存"/></li>
		    </shiro:hasPermission>
	    </ul>
	    </form>
    </div>
    <script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.message_cn.js"></script>	
	<script type="text/javascript" src="js/validate/jquery.validate.method.js"></script>	    
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/option.js"></script> 
    
	<script type="text/javascript">
	 $(function(){
	 	$("#submitFormBtn").click(function(){
			if($("#addDataForm").valid()){
				$.ajax({type:'POST',url:'siteInfoAction!add.huzd',data:$("#addDataForm").serialize(),async:false,dataType:'json',success:function(msg){
					art.dialog({title:'系统提示',content:msg.flag?"保存成功！":"保存失败，请重试！",time:2000});
				}});
			};
	 	});
	 	$("#addDataForm").validate({   
			rules:{'siteInfo.systemName':{required:true,minlength:4},
				   'siteInfo.systemEnName':{required:true,minlength:4},
				   'siteInfo.companyName':{required:true}
				   },
			errorElement:'b',success:function(label){
				label.remove();
			}
		});	
	 });			
	</script>  
</body>
</html>
