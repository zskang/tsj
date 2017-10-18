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
		<!-- add Data form -->
		 <div class="formtitle"><span>专项信息</span><div style="float:right"><a href="app-project.html">返回</a></div></div>
		<div id="addDataDiv">
		  <form id="addDataForm">
		  	<input type="hidden" id="id" value="" name="project.id"/> 
		  	<ul class="forminfo_twocol">
			    <li>
			    	<label>项目名称</label>
			    	<input type="text" value="" id="name" name="project.name" class="dfinput smaller"/>
			    	<i>中文名称</i>
			    </li>
			    <li>
			    	<label>所属代局指</label>
			    	<select id="parent" name="project.parent.id" class="dfinput smaller">
			    	</select>
			    	<i>所属父项目</i>
			    </li>	
			    <li>
			    	<label>项目类型</label>
			    	<select id="type" name="project.type.id" class="dfinput smaller asynOptions" data="projectType"></select>
			    	<i></i>
			    </li>	
			    <li>
			    	<label>计划结束时间</label>
			    	<input type="text" value="" id="finishDate" name="project.finishDate" class="dfinput smaller Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-%d'})"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>合同工期</label>
			    	<input type="text" value="" id="startDate" name="project.startDate" class="dfinput smaller Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'endDate\')}'})"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>至</label>
			    	<input type="text" value="" id="endDate" name="project.endDate" class="dfinput smaller Wdate" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\')}'})"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>工程造价</label>
			    	<input type="text" value="" id="price" name="project.price" class="dfinput smaller"/>
			    	<i>单位：<b>千万元</b></i>
			    </li>
			    <li>
			    	<label>负责人</label>
			    	<input type="text" value="" id="master" name="project.master.chinesename" class="dfinput smaller"/>
			    	<input type="hidden" value="" id="masterId" name="project.master.id" class="dfinput smaller"/>
			    	<i></i>
			    </li>	
			    <li>
			    	<label>资料员</label>
			    	<input type="hidden" data="zly" id="pro_usr_zly_hide" name="pro_usr_zly_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="zly" id="pro_usr_zly" name="pro_usr_zly" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>				    		    		    
			    <li>
			    	<label>技术员</label>
			    	<input type="hidden" data="jsy"  id="pro_usr_jsy_hide" name="pro_usr_jsy_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="jsy"  id="pro_usr_jsy" name="pro_usr_jsy" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>				    		    		    
			    <li>
			    	<label>技术主管</label>
			    	<input type="hidden" data="jszg"   id="pro_usr_jszg_hide" name="pro_usr_jszg_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="jszg"   id="pro_usr_jszg" name="pro_usr_jszg" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>				    		    		    
			    <li>
			    	<label>工程部长</label>
			    	<input type="hidden" data="gcbz" id="pro_usr_gcbz_hide" name="pro_usr_gcbz_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="gcbz" id="pro_usr_gcbz" name="pro_usr_gcbz" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>				    		    		    
			    <li>
			    	<label>项目总工</label>
			    	<input type="hidden" data="xmzg" id="pro_usr_xmzg_hide" name="pro_usr_xmzg_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="xmzg" id="pro_usr_xmzg" name="pro_usr_xmzg" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>测量主管</label>
			    	<input type="hidden" data="jczzg" id="pro_usr_jczzg_hide" name="pro_usr_jczzg_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="jczzg" id="pro_usr_jczzg" name="pro_usr_jczzg" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>项目经理</label>
			    	<input type="hidden" data="xmjl" id="pro_usr_xmjl_hide" name="pro_usr_xmjl_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="xmjl" id="pro_usr_xmjl" name="pro_usr_xmjl" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>代局指测量主管</label>
			    	<input type="hidden" data="djzclzg" id="pro_usr_djzclzg_hide" name="pro_usr_djzclzg_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="djzclzg" id="pro_usr_djzclzg" name="pro_usr_djzclzg" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>代局指工程部长</label>
			    	<input type="hidden" data="djzgcbz" id="pro_usr_djzgcbz_hide" name="pro_usr_djzgcbz_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="djzgcbz" id="pro_usr_djzgcbz" name="pro_usr_djzgcbz" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>代局指项目总工</label>
			    	<input type="hidden" data="djzzgcs" id="pro_usr_djzzgcs_hide" name="pro_usr_djzzgcs_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="djzzgcs" id="pro_usr_djzzgcs" name="pro_usr_djzzgcs" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>授课人</label>
			    	<input type="hidden" data="syr" id="pro_usr_syr_hide" name="pro_usr_syr_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="syr" id="pro_usr_syr" name="pro_usr_syr" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>移交人</label>
			    	<input type="hidden" data="yjr" id="pro_usr_yjr_hide" name="pro_usr_yjr_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="yjr" id="pro_usr_yjr" name="pro_usr_yjr" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>接交人</label>
			    	<input type="hidden" data="jjr" id="pro_usr_jjr_hide" name="pro_usr_jjr_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="jjr" id="pro_usr_jjr" name="pro_usr_jjr" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>拟稿人</label>
			    	<input type="hidden" data="ngr" id="pro_usr_ngr_hide" name="pro_usr_ngr_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="ngr" id="pro_usr_ngr" name="pro_usr_ngr" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>借阅人</label>
			    	<input type="hidden" data="jyr" id="pro_usr_jyr_hide" name="pro_usr_jyr_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="jyr" id="pro_usr_jyr" name="pro_usr_jyr" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>文件接收人</label>
			    	<input type="hidden" data="wjjsr" id="pro_usr_wjjsr_hide" name="pro_usr_wjjsr_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="wjjsr" id="pro_usr_wjjsr" name="pro_usr_wjjsr" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>审核人</label>
			    	<input type="hidden" data="shr" id="pro_usr_shr_hide" name="pro_usr_shr_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="shr" id="pro_usr_shr" name="pro_usr_shr" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>复核人</label>
			    	<input type="hidden" data="fhr" id="pro_usr_fhr_hide" name="pro_usr_fhr_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="fhr" id="pro_usr_fhr" name="pro_usr_fhr" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>接收人</label>
			    	<input type="hidden" data="jsdw" id="pro_usr_jsdw_hide" name="pro_usr_jsdw_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="jsdw" id="pro_usr_jsdw" name="pro_usr_jsdw" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label  style="width:180px;">旁站管理负责人（项目总工）</label>
			    	<input type="hidden" data="pzglgzrzr" id="pro_usr_pzglgzrzr_hide" name="pro_usr_pzglgzrzr_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="pzglgzrzr" id="pro_usr_pzglgzrzr" name="pro_usr_pzglgzrzr" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>旁站安排人</label>
			    	<input type="hidden" data="pzapr" id="pro_usr_pzapr_hide" name="pro_usr_pzapr_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="pzapr" id="pro_usr_pzapr" name="pro_usr_pzapr" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label>旁站值班人</label>
			    	<input type="hidden" data="pzzbry" id="pro_usr_pzzbry_hide" name="pro_usr_pzzbry_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="pzzbry" id="pro_usr_pzzbry" name="pro_usr_pzzbry" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label style="width:150px;">研究小组主要成员</label>
			    	<input type="hidden" data="yjxz" id="pro_usr_yjxz_hide" name="pro_usr_yjxz_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="yjxz" id="pro_usr_yjxz" name="pro_usr_yjxz" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label style="width:150px;">监控量测负责人</label>
			    	<input type="hidden" data="jkclfzr" id="pro_usr_jkclfzr_hide" name="pro_usr_jkclfzr_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="jkclfzr" id="pro_usr_jkclfzr" name="pro_usr_jkclfzr" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label style="width:150px;">专利申请人</label>
			    	<input type="hidden" data="zlsqr" id="pro_usr_zlsqr_hide" name="pro_usr_zlsqr_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="zlsqr" id="pro_usr_zlsqr" name="pro_usr_zlsqr" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			    <li>
			    	<label style="width:150px;">质量小组最要成员</label>
			    	<input type="hidden" data="zlxzzycy" id="pro_usr_zlxzzycy_hide" name="pro_usr_zlxzzycy_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="zlxzzycy" id="pro_usr_zlxzzycy" name="pro_usr_zlxzzycy" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
			     <li>
			    	<label style="width:150px;">公司技术部门负责人</label>
			    	<input type="hidden" data="gsjsbfzr" id="pro_usr_gsjsbfzr_hide" name="pro_usr_gsjsbfzr_hide" class="dfinput huge" style="width:540px!important"/>
			    	<input type="text" data="gsjsbfzr" id="pro_usr_gsjsbfzr" name="pro_usr_gsjsbfzr" class="dfinput huge" style="width:540px!important"/>
			    	<i></i>
			    </li>
		  	</ul>
		  	<div style="clear:both;text-align: center;"><label>&nbsp;</label><input id="submitFormBtn" type="button" class="btn" value="确认保存"/></div>
		  </form>
		 </div> 
		<!-- add Data form -->    
    </div>
    
   	<div id="userList_" style="width:700px;height:400px;overflow-y:scroll;" class="hide">
	    <table class="tablelist">
	    	<thead>
	    	<tr>
		        <th style="width:10%;"><a href="javascript:void(0)" id="selectAll">全选</a></th>
		        <th style="width:20%;">姓名</th>
		        <th style="width:20%;">工号</th>
		        <th style="width:25%;">手机</th>
		        <th style="width:25%;">固话</th>
	        </tr>
	        </thead>
	        <tbody>
	        <tr>
	        <td colspan="5">数据加载中...</td>
	        </tr>        
	        </tbody>
	    </table>
   	</div>    
    
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
			
			$("#master").autocomplete({
			    serviceUrl: 'userAction!optionList.huzd',
			    paramName:'user.chinesename',
			    mustMatch:true,
			    minChars:1,
			    selectFirst:true,
			    dataType:'json',
			    onSelect: function (suggestion) {
 			    	$("#"+$(this).attr("id")+"Id").val(suggestion.data);
				},transformResult: function(response) {
			    	return {suggestions: $.map(response.users, function(dataItem) {return {value:dataItem.chinesename,mobilephone:dataItem.mobilephone,data:dataItem.id,label:dataItem.chinesename};})};
			    },formatResult: function (suggestion, currentValue){
			    	return suggestion.value+"["+suggestion.mobilephone+"]";
			    }
		 	}).focusin(function(){
		 		$(this).val("");
		 		$("#"+$(this).attr("id")+"Id").val("");
		 	});
		
			$("input[name^='pro_usr_']").click(function(){

				$this = $(this);
				var filledUsers = $("#"+$this.attr("id")+"_hide").val().split(",");
				$.post("userAction!findByRole.huzd",{dataIds:$(this).attr("data")},function(msg){
					if(msg&&msg.users&&msg.users.length>0){
						var innerHtml = "";
						$.each(msg.users,function(i,n){
							innerHtml += ("<tr><td><input "+($.inArray(n.id+"",filledUsers)!=-1?"checked='checked'":"")+" data='"+n.id+"@"+n.username+"@"+n.chinesename+"' type='checkbox' name='dataIds' value='"+n.id+"'/></td><td>"+n.chinesename+"</td><td>"+n.username+"</td><td>"+n.mobilephone+"</td><td>"+n.telephone+"</td></tr>");
						});
						$(".tablelist>tbody").html(innerHtml);
						art.dialog({title:'用户配置',content:document.getElementById('userList_'),lock:true,okValue:'添加',ok:function(){
							var $selected =  $("input[name='dataIds']:checked");
							if($selected.length>0){
								var showField = "";
								var hideFiele = "";
								$.each($selected,function(i,n){
									showField += $(this).attr("data").split("@")[2];
									hideFiele += $(this).attr("data").split("@")[0];
									if(i<$selected.length-1){
										showField+=",";
										hideFiele+=",";
									}
								});
								$this.val(showField);
								$("#"+$this.attr("id")+"_hide").val(hideFiele);
							}else{
								art.dialog({title:"系统提示",content:"请选择用户",time:5000});
								return false;
							}
						},cancelValue:'关闭',cancel:function(){return true;}});
					}else{
						art.dialog({title:"系统提示",content:"该角色无对应用户",lock:true,time:5000});
					}
				},"json");
			});

			/*角色添加表格校验*/
			$("#addDataForm").validate({ignore: [],
				rules:{'project.name':{required:true,minlength:3,remote:{url:'projectAction!checkName.huzd',type:'POST',dataType:'json',data:{'project.id':'${param.aId}'}}},
					   'project.finishDate':{required:true},
					   'project.startDate':{required:true},
					   'project.endDate':{required:true},
					   'project.price':{required:true,digits:true},
					   'project.master.chinesename':{required:true},
					   'project.master.id':{required:true}
				},errorElement:'b',success:function(label){
					label.remove();
				}
			});	
			
			if('${param.aId}'!=null&&'${param.aId}'!=""){
				$.ajax({type:'POST',url:'projectAction!find.huzd',data:{'project.id':'${param.aId}'},async:false,dataType:'json',success:function(msg){
					if(msg.project != null){
						$("#id").val(msg.project.id);
						$("#name").val(msg.project.name);
						$("#price").val(msg.project.price);
						$("#finishDate").val(msg.project.finishDate);
						$("#startDate").val(msg.project.startDate);
						$("#endDate").val(msg.project.endDate);
						$("#master").val(msg.project.master.chinesename);
						$("#masterId").val(msg.project.master.id);
						if(msg.project.parent)$("#parent option[value='"+msg.project.parent.id+"']").attr("selected","selected");
						$("#type option[value='"+msg.project.type+"']").attr("selected","selected");
						if(msg.project.projectUsers.length>0){
							$.each(msg.project.projectUsers,function(i,n){
								if(!$("#pro_usr_"+n.role.shortName+"_hide").val()){
									$("#pro_usr_"+n.role.shortName+"_hide").val(n.user.id);
									$("#pro_usr_"+n.role.shortName).val(n.user.chinesename);
								}else{
									$("#pro_usr_"+n.role.shortName+"_hide").val($("#pro_usr_"+n.role.shortName+"_hide").val()+","+n.user.id);
									$("#pro_usr_"+n.role.shortName).val($("#pro_usr_"+n.role.shortName).val()+","+n.user.chinesename);
								};
							});
						}
/* 						        pro_usr_zly_hide
						console.log(); */
					}else{
						art.dialog({title:'系统提示',content:'查询出错请重试！',okValue:'确定',ok:function(){
					    	window.location.href='sys-anno.jsp'; 
					    	return true;
						}});
					}
				   }
				});						
			}
			
			$("#submitFormBtn").click(function(){
				var url = ('${param.aId}'!=null&&'${param.aId}'!="")?'projectAction!update.huzd':'projectAction!add.huzd';
				if($("#addDataForm").valid()){
					$.ajax({type:'POST',url:url,data:$("#addDataForm").serialize(),async:false,dataType:'json',success:function(msg){
						if(true==msg.flag){
							art.dialog({
							    title: '系统提示',
							    content: '操作成功！',okValue:'继续添加',
							    ok: function () {
							    	resetForm();
							    	return true;
							    },cancelValue:'返回列表',cancel:function(){
							    	window.location.href='app-project.jsp'; 
							    	return true;
							    }
							 });
						}else{
							art.dialog({title:'系统提示',content:'添加出错请重试！',time:2000});
						}
					 }
					});	
				}
			});
			
		});		
			
		function resetForm(){
			$("#addDataForm")[0].reset();
			$("#parent option[value='']").attr("selected","selected");
			$("#type option:first").attr("selected","selected");
			resetValidatorClass();
		}
	</script>
</body>

</html>
