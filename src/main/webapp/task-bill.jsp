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

.site-content {
    position: absolute;
    left: 5px;
    top: 0;
    right: 0;
    bottom: 0;
}
</style>
</head>
<body>
	<div class="site-content" >
    <div class="main">
      <ul>
        <li class="item" >
          <div class="card1" onclick="queryBill('1')">
            <em class="icon1"></em>
            <p class="title1">公司技术负责人</p>
          
          </div>
        </li>
        <li class="item item-tech">
          <div class="card1" onclick="queryBill('2')">
            <em class="icon1"></em>
            <p class="title1">代局指总工程师</p>
            
          </div>
        </li>
        <li class="item item-file">
          <div class="card1" onclick="queryBill('3')">
            <em class="icon1"></em>
            <p class="title1">代局指工程部长</p>
            
          </div>
        </li>
        <li class="item item-service">
          <div class="card1" onclick="queryBill('4')">
            <em class="icon1"></em>
            <p class="titl1e">代局指测量主管</p>
            
          </div>
        </li>
        <li class="item item-plan">
          <div class="card1" onclick="queryBill('5')">
            <em class="icon1"></em>
            <p class="title1">项目经理</p>
            
          </div>
        </li>
        <li class="item item-solution">
          <div class="card1" onclick="queryBill('6')">
            <em class="icon1"></em>
            <p class="title1">项目总工程师</p>
            
          </div>
        </li>
        <li class="item item-process">
          <div class="card1" onclick="queryBill('7')">
            <em class="icon1"></em>
            <p class="title1">项目工程部长</p>
            
          </div>
        </li>
        <li class="item item-measure">
          <div class="card1" onclick="queryBill('8')">
            <em class="icon1"></em>
            <p class="title1">项目技术主管</p>
            
          </div>
        </li>
        <li class="item item-design">
          <div class="card1" onclick="queryBill('9')">
             <em class="icon1"></em>
            <p class="title1">项目测量主管</p>
            
          </div>
        </li>
        <li class="item item-handle">
          <div class="card1" onclick="queryBill('10')">
             <em class="icon1"></em>
            <p class="title1">项目技术员</p>
            
          </div>
        </li>
        <li class="item item-data">
          <div class="card1" onclick="queryBill('11')">
            <em class="icon1"></em>
            <p class="title1">项目资料员</p>
            
          </div>
        </li>
        
      </ul>
    </div>
  </div>
	<script type="text/javascript" src="js/jOrgChart/jquery.min.js"></script>
	<script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.plugins.min.js"></script>
	<script type="text/javascript" src="js/jOrgChart/jquery.jOrgChart.js"></script> 
	<script type="text/javascript">
	
	
	function queryBill(id) {
			window.location.href = 'task-bill-detail.jsp?id='
				+ id;
		}
  </script>
</body>
</html>