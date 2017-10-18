<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>工程施工技术资料信息系统</title>
<link rel="stylesheet" type="text/css" href="skins/index/style.css" />
<link rel="stylesheet" type="text/css" href="js/jOrgChart/jquery.jOrgChart.css" />
<style>
.jOrgChart .node a{text-decoration:none;color:#fff;border-radius:3px;display:block;padding:10px;}
.jOrgChart table{width: 100%;border-collapse:collapse;}
.jOrgChart .level_0{font-family:仿宋体;height:auto;width:50px;border-radius:2px;margin:0 5px;text-align:center;font-size:16px;background-color:#5B5B5B;overflow:hidden;}
.jOrgChart .level_1{font-family:仿宋体;width:50px;border-radius:2px;margin:0 5px;text-align:center;font-size:16px;background-color:#5A5AAD;overflow:hidden;}
.jOrgChart .level_2{font-family:仿宋体;width:50px;border-radius:2px;margin:0 5px;text-align:center;font-size:16px;background-color:#268bd1;overflow:hidden;}
.jOrgChart .line {height: 20px;width: 2px;}
.jOrgChart .top {border-top: 2px solid black;}
.jOrgChart .height_4{height:120px;}
.jOrgChart .height_5{height:140px;}
.jOrgChart .height_6{height:160px;}
.jOrgChart .height_7{height:180px;}
.jOrgChart .height_8{height:200px;}
.jOrgChart .height_9{height:225px;}
.jOrgChart .height_10{height:250px;}
.jOrgChart .height_11{height:275px;}
.jOrgChart .height_12{height:290px;}
.jOrgChart .height_13{height:315px;}
.jOrgChart .height_14{height:290px;}
.jOrgChart .height_15{height:250px;}
.jOrgChart .height_16{height:260px;}
.jOrgChart .right { border-left: 2px solid black;margin: 0 auto;}
.btn_small{background:url("./skins/default/images/btnbg-small.png") no-repeat;color: #fff;cursor: pointer;font-size: 14px;font-weight: bold;height: 35px;width: 70px;}
.hide{display: none;}
.site_main{position: absolute;}
.tablelist{border:solid 1px #cbcbcb; width:100%; clear:both;}
.tablelist th{background:url(./skins/default/images/th.gif) repeat-x; height:34px; line-height:34px; border-bottom:solid 1px #b6cad2; text-indent:11px; text-align:left;}
.tablelist td{line-height:35px; text-indent:11px; border-right: dotted 1px #c7c7c7;}
.tablelist tbody tr.odd{background:#f5f8fa;}
.tablelist tbody tr:hover{background:#e5ebee;}
.tablelist a {color:#3eafe0;}
</style>
</head>
<body>
	<dl class="site-nav" id="siteNav">
	</dl>
	<div class="site-content">
		<div id="returnDiv" style="text-align:right;height:50px;padding: 10px 30px 0 30px;" class="hide">
			<button id="returnBtn" class="btn_small">返回</button>
			<input type="hidden" id="btn_small" value="0"/>
		</div>
		<div class="main" id="jOrgChart"></div>
	</div>
	<script type="text/javascript" src="js/jOrgChart/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.plugins.min.js"></script>
	<script type="text/javascript" src="js/jOrgChart/jquery.jOrgChart.js"></script> 
	<script type="text/javascript">
	
  	setWidth();
	$(window).resize(function(){
		setWidth();	
	});
	function setWidth(){
		var width = ($('.leftinfos').width()-12)/2;
		$('.infoleft,.inforight').width(width);
	}
    $(function() {
    	//var siteNav = $('#siteNav');
      	var firstColum = $('#siteNav .first-colum');
      	var secondColum = $('#siteNav .second-column');
      	var thirdColum = $('#siteNav .third-column');
      	firstColum.live('click', function() {
	        var $this = $(this);
	        if ($this.hasClass('active')) {
	        	$('#siteNav .first-colum').removeClass('active');
	        } else {
	        	$('#siteNav .first-colum').removeClass('active');
	          	$this.addClass("active");
	        }
	        var id = $this.attr("id");
	        loadPhoto(id);
      	});
	
      	secondColum.live('click', function() {
	        var $this = $(this);
	        if ($this.hasClass('active')) {
	        	$('#siteNav .second-column').removeClass('active');
	        } else {
	        	$('#siteNav .second-column').removeClass('active');
	          	$this.addClass("active");
	        }
	        var id = $this.attr("id");
	        showModuleFileList(id);
		});

		thirdColum.live('click', function() {
	      	var $this = $(this);
	        $('#siteNav .third-column').removeClass('active');
	        $this.addClass("active");
	        var id = $this.attr("id");
	        showModuleFileList(id);
		});
		
		$("#returnBtn").live('click', function() {
			var ids = $("#btn_small").val();
			if(ids.substring(0,1) == '1'){
				activeTask();
			}else{
				loadPhoto(ids.substring(2));
			}
		});
		
      	//加载左侧的菜单栏
		$.ajax({type:'POST',url:'moduleAction!queryModuleListById.huzd',data:{moduleId:1},async:true,dataType:'json',success:function(msg){
      		if(msg!=''){
    			var html="";
    			$.each(msg.module,function(i,n){
    				html += "<dd class='item'>";
    				html += "<a class='first-colum' id='"+n.id+"'> <em class='icon icon-"+n.icon+"'></em> <span>"+n.name+"</span></a>";
    				html += "<ul class='sub-nav'>";
					$.each(n.child,function(j,m){
						html += "<li><a class='second-column' id='"+m.id+"'>"+m.name+"</a>";
						if(m.child){
							html += "<ul>";
							$.each(m.child,function(o,p){
								html += "<li><a class='third-column' id='"+p.id+"'>"+p.name+"</a></li>";
							});
							html += "</ul>";
						}
						html += "</li>";
					});
					html += "</ul>";
					html += "</dd>";
				});
				$("#siteNav").html(html);
			}else{
				art.dialog({title:'系统提示',content:'查询模块出错请重试！',time:2000});
			}
      	}});
		//加载执行中的任务
      	activeTask();
    });
    
    function activeTask(){
    	$("#returnDiv").addClass("hide");
    	$("#jOrgChart").addClass("site_main");
      	$.ajax({type:'POST',url:'statisticsAction!queryModuleTaskList.huzd',data:{moduleId:1},async:true,dataType:'json',success:function(msg){
    		if(msg!=''){
    			var innerHtml="<ul>";
    			var projectId = '${sessionScope.project.id}';
    			$.each(msg.data,function(i,n){
    				innerHtml += "<li class='item item-"+n.icon+"'>"; 
    				innerHtml += "<div class='card'>";
    				innerHtml += "<div onclick='loadPhoto("+n.id+")'>";
    				innerHtml += "<em class='icon' ></em>";
    				innerHtml += "<p class='title'>"+n.name+"</p>";
    				innerHtml += "</div>";
    				innerHtml += "<p class='icon-box'>";
    				if(n.exec1 !='0' || n.exec2 !='0'|| n.exec3 !='0'){
    					innerHtml += "<a href='#' onclick=\"openActivehtml("+n.id+","+projectId+",\'normal\')\"><span class='view icon-view'> <em id='"+n.id+"123' class='num'>"+n.exec1+"</em></span></a>";
        				innerHtml += "<a href='#' onclick=\"openActivehtml("+n.id+","+projectId+",\'due\')\"><span class='view icon-view2'> <em class='num'>"+n.exec2+"</em></span></a>";
        				innerHtml += "<a href='#' onclick=\"openActivehtml("+n.id+","+projectId+",\'overtime\')\"><span class='view icon-view3'> <em class='num'>"+n.exec3+"</em></span></a>";
    				}
    				innerHtml += "</p>";
    				innerHtml += "</div>";
    				innerHtml += "</li>";
				});
    			innerHtml += "</ul>";
    			$("#jOrgChart").empty();
    			$("#jOrgChart").html(innerHtml);
				setInterval(function(){eachEm();},500);
			}else{
				art.dialog({title:'系统提示',content:'查询模块出错请重试！',time:2000});
			}
      	}});
    }
    
    function eachEm(){
    	$.each($("#jOrgChart").find("em"),function(i){
    			if(parseInt($(this).text())>0){
    				showImg($(this));   			
    			}
    		});
    }
    
    function showImg(ths) {  
    	var display = ths.css('display');
 		if(display == 'none'){
    		ths.show();   
 		}else{
 			ths.hide();
 		}          		
	} 
    
    function openActivehtml(moduleId,projectId,searchType){
    	var useTab = '${siteinfo.useTabs}';
    	var basePath = '${basePath}';
    	if(!useTab){
			window.location.href=basePath+"app-mytask.jsp?moduleId="+moduleId+"&projectId="+projectId+"&searchType="+searchType;
		}else{
			window.parent.addTab_(181,"待办事项",basePath+"app-mytask.jsp?moduleId="+moduleId+"&projectId="+projectId+"&searchType="+searchType);
		}
    }
    
    function loadPhoto(id){
    	$.ajax({type:'POST',url:'moduleAction!queryModuleListById.huzd',data:{'moduleId':id},async:true,dataType:'json',success:function(msg){
    		var showlist = $("<ul id='org' style='display:none'></ul>");
            showall(msg.module, showlist);
            $("#jOrgChart").empty();
            $("#jOrgChart").append(showlist);
            $("#org").jOrgChart( {
                chartElement : '#jOrgChart',//指定在某个dom生成jorgchart
                dragAndDrop : false 	    //设置是否可拖动
            });
            $("#returnDiv").removeClass("hide");
            $("#jOrgChart").removeClass("site_main");
            $("#btn_small").val("1_"+id);
        }});
    }
    
    function showall(menu_list, parent) {
        $.each(menu_list, function(index, val) {
            if(val.childrens.length > 0){
                var li = $("<li></li>");
                if(val.pid =='null'){
                	li.append("<a href='javascript:void(0)'>"+val.name+"</a>").append("<ul></ul>").appendTo(parent);
                }else{
                	if(val.length !=null){
                		li.append("<a href='#' date='aa"+val.length+"bb' onclick='showModuleFileList("+val.id+")'>"+val.name+"</a>").append("<ul></ul>").appendTo(parent);
                	}else{
                		li.append("<a href='#' onclick='showModuleFileList("+val.id+")'>"+val.name+"</a>").append("<ul></ul>").appendTo(parent);
                	}
                	
                }
                //递归显示
                showall(val.childrens, $(li).children().eq(1));
            }else{
            	if(val.length !=null){
            		$("<li></li>").append("<a href='#' date='aa"+val.length+"bb' onclick='showModuleFileList("+val.id+")'>"+val.name+"</a>").appendTo(parent);
            	}else{
            		$("<li></li>").append("<a href='#' onclick='showModuleFileList("+val.id+")'>"+val.name+"</a>").appendTo(parent);
            	}
                
            }
        });
    }
    
    function openFile(filecode,filePath){
    	var url;
    	if(filecode == 1){
    		url = "http://218.106.82.252:8090/portal/office/word/word.jsp?filePath="+encodeURI(filePath)+"&isEdit=N";
    	}else{
    		url = "http://218.106.82.252:8090/portal/office/excel/excel.jsp?filePath="+encodeURI(filePath)+"&isEdit=N";
    	}
    	window.open(url);
    }
    
    function showModuleFileList(id){
    	var ids = $("#btn_small").val();
    	$("#btn_small").val("2_"+ids.substring(2));
    	var innerHtml = "<table class='tablelist'>"; 
		innerHtml += "<thead>"; 
		innerHtml += "<tr>"; 
		innerHtml += "<th width='10%' style='height: 32px; border: 1px solid gray;'>模块名称</th>"; 
		innerHtml += "<th width='10%' style='height: 32px; border: 1px solid gray;'>归卷时间</th>"; 
		innerHtml += "<th width='35%' style='height: 32px; border: 1px solid gray;'>文档名称</th>";
		innerHtml += "<th width='35%' style='height: 32px; border: 1px solid gray;'>操作</th>"; 
		innerHtml += "</tr>"; 
		innerHtml += "</thead>"; 
		innerHtml += "<tbody>"; 
    	$.ajax({type:'POST',url:'statisticsAction!showModuleFileList.huzd',data:{message:id},async:true,dataType:'json',success:function(msg){
        	if(msg!=''){
        		$.each(msg.data,function(i,n){
        			innerHtml += "<tr>"; 
        			innerHtml += "<td style='height: 32px; border: 1px solid gray;'>"+n.moduleName+"</td>"; 
        			innerHtml += "<td style='height: 32px; border: 1px solid gray;'>"+n.createTime+"</td>"; 
        			var filePath = n.docPath;
        			filePath = filePath.replace(/\\/g,"\/");
        			if(n.docType == 'doc' || n.docType == 'docx'){
        				innerHtml += '<td style="height: 32px; border: 1px solid gray;"><a href="#" onclick="openFile(1,\''+filePath+'\');">'+n.docName+'</a></td>'; 
        			}else if(n.docType == 'xlsx' || n.docType == 'xls'){
        				innerHtml += '<td style="height: 32px; border: 1px solid gray;"><a href="#" onclick="openFile(2,\''+filePath+'\');">'+n.docName+'</a></td>'; 
        			}else{
        				innerHtml += "<td style='height: 32px; border: 1px solid gray;'>"+n.docName+"</td>"; 
        			}
        			innerHtml += "<td style='height: 32px; border: 1px solid gray;'><a style='color:red;' target='_blank' href='completionInfoAction!downloadFile.huzd?completionInfoId="+n.id+"'>下载</a></td>"; 
        			innerHtml += "</tr>"; 
				});
        		innerHtml += "</tbody>"; 
            	innerHtml += "</table>"; 
        	}else{
        		innerHtml += "<tr>"; 
        		innerHtml += "<td colspan='4'>暂无归卷文档...</td>";
		        innerHtml += "</tr>"; 
		        innerHtml += "</tbody>"; 
	        	innerHtml += "</table>"; 
        	}
        	$("#jOrgChart").empty();
            $("#jOrgChart").append(innerHtml);
    	}});
    }
  </script>
</body>
</html>