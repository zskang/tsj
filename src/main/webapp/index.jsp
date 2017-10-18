<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String websocketPath = "ws://"+request.getServerName()+":"+request.getServerPort()+path+"/";

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<base href="<%=basePath%>"/>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<title>${siteinfo.systemName}</title>
	<link href="${_theme_}css/style.css" rel="stylesheet" type="text/css" />
	<link href="${_theme_}css/tabs.css" rel="stylesheet" type="text/css" />
	<link rel="stylesheet" type="text/css"  href="js/artDialog/skins/mobile.css" />
	<link href="js/iziToast/iziToast.min.css" rel="stylesheet" type="text/css" />
	<link href="js/tipsy/tipsy.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript">var _theme_ = '${_theme_}';var _wspath_='<%=websocketPath%>';var _basePath_ = '<%=basePath%>';</script>
</head>
<body>
	<!-- 整个页面总容器 -->
	<div class="mainContainer">
		<!-- 页面top容器 -->
		<div class="topContainer">
		    <div class="topleft">
		    <a href="javascript:void(0)" target="_parent" class="index_systemName">
		    	<img src="${_theme_}images/login/logo.png" title="系统首页" />
		    </a>
		    </div>
		    <!-- 一级菜单 -->   
		    <ul class="nav">
			    <s:if test="null != #session.resources && #session.resources.size() > 0">
			    	<s:iterator value="#session.resources" var="resource" status="st">
			    		 <li>
			    		 	<a parent="<s:property value='#resource.childNo>0?true:false'/>" id="<s:property value="#resource.id"/>"
			    		 	 class="firstLevelFunction <s:if test="#st.isFirst()">selected</s:if>" href='javascript:void(0)' data="<s:property value="#resource.path"/>"><img src="${_theme_}<s:property value="#resource.icon"/>" title="<s:property value="#resource.name"/>" /><span class="topmenu"><s:property value="#resource.name"/></span></a></li>
			    	</s:iterator>
			    </s:if>
		    </ul>
		    <!-- 一级菜单 END  --> 
		        
		    <div class="topright">    
		    <ul>
		    <!--  
		    <li><span><img src="images/help.png" title="帮助"  class="helpimg"/></span><a href="#">帮助</a></li>
		    <li><a href="#">关于</a></li>
		    -->
		    <s:if test="#session.project!=null"><li id="currentProjetShow"><a>当前项目：</a><a href="javascript:;" id="projectShowName"><s:property value="#session.project.name"/></a></li></s:if>
		    <li id="onlineUserFeather"><a href="javascript:;">在线人数：</a><a href="javascript:void(0)" id="onlineUserNumbers">1</a></li>
		    <li><a href="userAction!logout.huzd">退出</a></li>
		    </ul>
		    <div class="user">
		    	<s:if test="#application.siteinfo.useTabs">
			       <span>
			       	<a data="sys-userinfo.html" 
			       	   href="javascript:;" 
			       	   id="userInfo_btn"><s:property value="#session.user.chinesename"/></a> 
			       	   </span>
		    	</s:if>
		    	<s:else>
			       <span><a target="mainiframe" href="sys-userinfo.html" ><s:property value="#session.user.chinesename"/></a></span>
		    	</s:else>
			    <i>欢迎使用</i>
			    <!--<b>5</b>-->
		    </div>    
		    </div>		
		</div>
		<!-- 页面top容器 END-->
		
		<!-- 页面内容容器 END-->
		<div class="con">
              <div class="leftContainer hide">
					<!--   <div class="lefttop"><span></span><label class="funTitle"><s:property value="#session.resources[0].name"/></label></div>  -->
				    <dl class="leftmenu">
						 <dd>
						    <div class="title">
						    	<span>
						    		<img width="20" height="20" src="${_theme_}<s:property value="#session.resources[0].icon"/>"/>
						    	</span>
						    	<label><s:property value="#session.resources[0].name"/></label>
						    </div>
						    <div class="menuContainer">
						    	<ul class="menuson">
							    	<s:iterator value="#session.resources[0].child" var="resource" status="st">
								    	<s:if test="#resource.status==\"N\"">
									     <li class="<s:property value="#resource.childNo>0?'menu_dir':''"/>" hasChild="<s:property value="#resource.childNo>0?true:false"/>">
									     	<cite></cite>
									     	<s:if test="#application.siteinfo.useTabs">
										     	<a class='<s:property value="#resource.name.length()>9?'needTip_':''"/>' data='<s:property value="#resource.path"/>' href='javascript:;' title='<s:property value="#resource.name"/>' resId='<s:property value="#resource.id"/>'><s:property value="#resource.name"/></a>
									     	</s:if>
									     	<s:else>
										     	<a href='<s:property value="#resource.childNo>0?'javascript:;':#resource.path"/>' title='<s:property value="#resource.name"/>' target='mainiframe' resId='<s:property value="#resource.id"/>'><s:property value="#resource.name"/></a>
									     	</s:else>
									     	<i></i>
									     </li>
									     </s:if>
							        </s:iterator>
						        </ul> 
					        </div>   
						  </dd>				    	
				    </dl>
				</div>
				<div class="rightContainer tabs place_new"  id="tabs_nav" style="width:100%;">
					<s:if test="#application.siteinfo.useTabs==false">
						<div class="place">
							<span>位置：</span>
							<ul class="placeul">
							  <li><a href="home.html" target="mainiframe" class="breadOfHomePage">我的工作台</a></li>
							</ul>
						</div>
						<div class="mainindex">
						  <iframe id="mainiframe" name="mainiframe" frameborder="0" width="100%" src="home.html"></iframe>
						</div>
					</s:if>
					<!--    border: 1px solid #b7d5df; -->
					<span class="fullScreen">&nbsp;</span>	
				</div>
		</div>
	</div>
		
    <div class="dflist1 hide" style="margin-top:0px!important;width:488px!important;height:auto;" id="res_info">
	    <ul class="newlist" style="margin-bottom:10px;">
		    <li><i style="width:80px;"><a>功能名称：</a></i><a id="res_name"></a></li>
		    <li><i style="width:80px;"><a>功能路径：</a></i><a id="res_path"></a></li>
		    <li><i style="width:80px;"><a>功能描述：</a></i><a id="res_desc"></a></li>
	    </ul>        
    </div>
    
    <div id="projectlists_" class="hide">
	    <table class="tablelist" id="projectsTable_" style="width:600px;">
	    	<thead>
	    	<tr>
		        <th style="width:8%;">请选择</th>
		        <th style="width:42%;">项目名称</th>
		        <th style="width:20%;">负责人</th>
		        <th style="width:15%;">类型</th>
		        <th style="width:15%;">计划结束时间</th>
	        </tr>
	        </thead>
	        <tbody>
	        <tr>
	        <td colspan="5">数据加载中...</td>
	        </tr>        
	        </tbody>
	    </table>		
    </div>
	
	<!-- 整个页面总容器 END-->
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
	<script src="js/artDialog/artDialog.plugins.min.js" type="text/javascript"></script>
	<!--<script src="js/artDialog/jquery.artDialog.min.js" type="text/javascript"></script>-->
	<script type="text/javascript" src="js/tabs/core.js"></script>
	<script type="text/javascript" src="js/tabs/tabs.js"></script>
	<script type="text/javascript" src="js/iziToast/iziToast.min.js"></script>
	<script type="text/javascript" src="js/sockjs-1.1.1.js"></script>
	<script type="text/javascript" src="js/tipsy/jquery.tipsy.js"></script>
	<script type="text/javascript" src="js/common.js"></script>
	<script type="text/javascript" src="js/webSocket.js"></script>
	<script type="text/javascript" src="js/project.js"></script>
	<script type="text/javascript">
	
	var isTabs = ${siteinfo.useTabs};
	var tabs = null;
	var resInfo_dialog = null;
	$(function(){
		if(isTabs==true){
			tabs = $("#tabs_nav");
			tabs.mac('tabs', {
				items: [{code: 'fullScreen_117',title: '我的工作台',closeable: false,url: 'home.html',autoLoad: true}],
				onLoadPage: function(me, a, b, p){
					//alert('onLoadPage: ' + p.code + ' be loaded.');
				},
				onCloseTab: function(me, c, a){
					return true;
				},onDBClick:function(a){
					var b = a.attr("name").split("_")[1];
					if(!isNaN(b)){
						$.post("resAction!get.huzd",{'resource.id':b},function(msg){
							$("#res_name").html(msg.resource.name);
							$("#res_path").html(msg.resource.parent.name+" > "+msg.resource.name);
							$("#res_desc").html(msg.resource.desc);
							if(null!=resInfo_dialog)resInfo_dialog.close();
							resInfo_dialog = art.dialog({title:"<img src='skins/mobile/images/info.png'> 功能信息",content:document.getElementById("res_info"),padding:0});
						},"json");

					}
				}
			}).selectFirst();
		}

		//顶部导航切换(一级菜单)
		$(".nav li a").live("click",function(){
			$this = $(this);
			$(".funTitle").html($this.text());
			$(".nav li a.selected").removeClass("selected");
			$this.addClass("selected");
			var path = $this.attr("data");
			if($this.attr("parent")=="false"){
				openFirstLevelPage("fullScreen_"+$this.attr("id"),$this.text(),path,isTabs);
				collapseMainLeft();
			}else{
				
				if(null!=path&&path!="#"){
					openFirstLevelPage("fullScreen_"+$this.attr("id"),$this.text(),path,isTabs);
				}
				loadNavigate($this.attr("id"),null,isTabs);
			}
		});

		$("#userInfo_btn").click(function(){
			addTab_("tab_userinfo","个人信息",$(this).attr("data"));
		});
		
		//导航切换
		$(".menuson>li,.subMenu>li").live("click",function(){
			var $this = $(this);
			if(resInfo_dialog)resInfo_dialog.close();
			$("li.active").removeClass("active");
			$this.addClass("active");
			var hasChild = $this.attr("hasChild");
			if(hasChild=="true"){
				var isOpen = $this.attr("isOpen");
				if(!isOpen){
					loadNavigate($(this).children("a").attr("resId"),$this,isTabs);
					$this.attr("isOpen","true").addClass("menu_dir_open");
				}else{
					$this.next(".menuContainer").remove();
					$this.removeAttr("isOpen").removeClass("menu_dir_open");
				}
			}else{
				var resource = $(this).children("a").text();
				if(isTabs==true){
					var existTabNo = $(".tt>div[name='tab_"+ $(this).children("a").attr("resId")+"']").length;
					if(existTabNo==0){
			 			tabs.addTab({code: 'tab_' + $(this).children("a").attr("resId"),title:resource,closeable:true,url:$(this).children("a").attr("data"),autoLoad:true});
					}
					tabs.select('tab_' + $(this).children("a").attr("resId"));
				}else{
					var parentResource = $(this).parent().parent().children("div").children("label").html();
					var  innerHtml = "<li><a href='javascript:;'>"+parentResource+"</a></li>";
					var secondLevelName;
					console.log($this.parent("ul[class*='subMenu']").html());
					if($this.parent("ul[class*='subMenu']").html()){
						innerHtml = "<li><a href='javascript:;'>"+$this.parentsUntil("menuContainer").prev().children("label").html()+"</a></li>";
						secondLevelName = $this.parent("ul[class*='subMenu']").parent().prev().children("a").text();
						innerHtml += ("<li><a href='javascript:;'>"+secondLevelName+"</a></li>");
					}else{
						innerHtml = "<li><a href='javascript:;'>"+$this.parentsUntil("menuContainer").prev().children("label").html()+"</a></li>";
					}
					innerHtml += "<li><a href="+$this.children("a").attr("href")+" target=\"mainiframe\">"+resource+"</a></li>";
					$(".placeul>li").first().siblings().remove();
					$(".placeul").append(innerHtml);
				}
			}
		});
		
		$(".title").live('click',function(){
			var $ul = $(this).next('ul');
			$('dd').find('.menuson').slideUp();
			if($ul.is(':visible')){
				$(this).next('.menuson').slideUp();
			}else{
				$(this).next('.menuson').slideDown();
			}
		});
		
		$("div [name='tab_homepager']").click(function(){
			collapseMainLeft();
		});
		
		//单击面包屑首页时；移除追加的功能点链接；
		$(".breadOfHomePage").click(function(){
			$(".placeul>li").first().siblings().remove();
		});
		
		
		$(".needTip_").live("mouseover",function(){
			$(".needTip_").tipsy({gravity: 'w'});
		});
		
		$(".fullScreen").toggle(function(){
			collapseMainLeft();
		},function(){
			expandMainLeft();
		});
	});	
	function closeCurrentTab(){
		tabs.closeTab($(".tt>.selected[class*='closeable']").attr("name"));
	}
	
	function addTab_(tabId,title,url){
		if(isTabs==true){
			try{
				tabs.select(tabId);
			}catch(e){
		 		tabs.addTab({code:tabId,title:title,closeable:true,url:url,autoLoad:true});
				tabs.select(tabId);
			}
		}
	}
	</script>
</body>
</html>
