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
<title>日志管理</title>
<link rel="stylesheet" type="text/css"  href="${_theme_}css/style.css" />
<link rel="stylesheet" type="text/css"  href="js/artDialog/skins/opera.css" />
<link rel="stylesheet" type="text/css"  href="js/ztree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" type="text/css"  href="js/easyPage/css/skins/jquery.easypage.css" />
<link rel="stylesheet" type="text/css" href="js/layer/skin/layer.css" />
<style type="text/css">
span{display:inherit;}
.hide{display:none;}
</style>
</head>
<body>
    <div class="rightinfo">
     	<div>
	    	<div class="content">
				<div class="content_left" style="width:25%;">
					<div class="widget_box" style="height:1000px;">
	    			<div class="widget_title"><span>个人排名</span></div>
	    			 <div style="white-space:nowrap;font-size:22px;height:10%;text-align:center;margin-top:30px;margin-bottom:10px;" class="hide" id="rankingmsg">
	    			 	您当前排名第 <span id="ranking" style="font-size:24px;display:inline-block;color:#FF0000;"></span> 名
	    			 </div>
	    			 <div style="white-space:nowrap;font-size:22px;height:10%;text-align:center;margin-top:30px;margin-bottom:10px;" class="hide" id="noRankingmsg">
	    			 	当前没有您的排名数据
	    			 </div>
	    			 <div id="myChart"  style="height:350px;border-bottom:0px;"> </div>
	    			</div>
				</div>
				<div class="content_right" style="width:74%;">
	    		<div class="widget_box">
	    			<div class="widget_title"><span>排名列表</span></div>
	    			<div class="widget_body" style="padding:10px 5px 10px 5px;">
	    				<div class="condition">
							开始时间：<input class="dfinput Wdate smaller" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'q_endDate\')}'})" style="height:32px" type="text" id="q_startDate" />
							结束时间：<input class="dfinput Wdate smaller" onclick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'q_startDate\')}'})" style="height:32px" type="text" id="q_endDate"/>
							项目名称：<select id="q_projectId" class="dfinput Wdate smaller">
									</select>
							排名字段：<select id="q_ordercolumn" class="dfinput Wdate smaller">
										<option value="1">完成率</option>
										<option value="2">滞后率</option>
									</select>
							<button id="queryUserBtn" class="btn_small">查询</button>
							<button id="excelBtn" class="btn_small">导出</button>
						</div>	    				
					    <table class="tablelist">
					    	<thead>
					    	<tr>
					        	<th>排名</th>
								<th>用户姓名</th>
								<th>完成率</th>
								<th>滞后率</th>
					        </tr>
					        </thead>
					        <tbody>
					        <tr>
					        <td colspan="4">数据加载中...</td>
					        </tr>        
					        </tbody>
					    </table>
	    			</div>
	    			
	    		</div>
	    	</div>
   			</div>
   		</div>
   	</div>
    	
    <script type="text/javascript" src="js/jquery.js"></script>
    <script type="text/javascript" src="js/My97DatePicker/WdatePicker.js"></script>  
    <script type="text/javascript" src="js/artDialog/artDialog.min.js"></script>
    <script type="text/javascript" src="js/artDialog/artDialog.plugins.min.js"></script>
  	<script type="text/javascript" src="js/validate/jquery.validate.min.js"></script>
	<script type="text/javascript" src="js/validate/jquery.validate.message_cn.js"></script>	
	<script type="text/javascript" src="js/validate/jquery.validate.method.js"></script> 
	<script type="text/javascript" src="js/echarts/echarts-all.js"></script>
	<script type="text/javascript" src="js/jquery.idTabs.min.js"></script> 
	<script type="text/javascript" src="js/easyPage/jquery.easypage.js"></script> 
	<script type="text/javascript" src="js/layer/layer.js"></script>
	
	<script type="text/javascript">
		/**获取查询条件参数**/
		function getQueryParam(){
			return {startDate:$("#q_startDate").val(),endDate:$("#q_endDate").val(),'processDetail.projectId':$("#q_projectId").val(),message:$("#q_ordercolumn").val()};
		}
		/**表格填充函数**/
		function fillTable(data){
			var userId='${sessionScope.user.id}';
			var userRankFlag = false;
			var userRank = "";
			var innerHtml = "";
			var whether1Sum = "";
			var whether2Sum = "";
			var whether3Sum = "";
			var whether4Sum = "";
			if(data!=''){
				$.each(data.data,function(i,n){
					innerHtml +=("<tr>");
					innerHtml +=("<td>"+n.rank+"</td>");
					innerHtml +=("<td>"+n.username+"</td>");
					innerHtml +=("<td>"+n.finishRank+"</td>");
					innerHtml +=("<td>"+n.delayRank+"</td>");
					innerHtml +=("</tr>");
					if(n.userId == userId){
						userRankFlag = true;
						userRank = n.rank;
						whether1Sum = n.whether1Sum;
						whether2Sum = n.whether2Sum;
						whether3Sum = n.whether3Sum;
						whether4Sum = n.whether4Sum;
					}
				});
			}else{
				innerHtml += "<tr><td colspan='8'>无数据</td></tr>";
				if(data.message)art.dialog({title:'系统提示',content:data.message});
			}
			$(".tablelist>tbody").html(innerHtml);
			$('.tablelist tbody tr:odd').addClass('odd');
			
			if(userRankFlag){
				$("#rankingmsg").removeClass("hide");
				$("#noRankingmsg").addClass("hide");
				$("#ranking").text(userRank);
				var myChart = echarts.init(document.getElementById("myChart")); 
				var option = {
					    title : {
					        text: '个人统计',
					        x:'center'
					    },
					    legend: {
					        orient : 'vertical',
					        x : 'left',
					        data:['正常完成','超时完成','正常执行','超时执行']
					    },
					    calculable : true,
					    series : [
					        {
					            name:'访问来源',
					            type:'pie',
					            radius : '55%',
					            center: ['50%', '60%'],
					            itemStyle: {
					                emphasis: {
					                    shadowBlur: 10,
					                    shadowOffsetX: 0,
					                    shadowColor: 'rgba(0, 0, 0, 0.5)'
					                }
					            },
					            data:[
					                {value:whether1Sum, name:'正常完成'},
					                {value:whether2Sum, name:'超时完成'},
					                {value:whether3Sum, name:'正常执行'},
					                {value:whether4Sum, name:'超时执行'}
					            ]
					        }
					    ]
					};
				myChart.setOption(option); 
			}else{
				$("#rankingmsg").addClass("hide");
				$("#noRankingmsg").removeClass("hide");
				$("#myChart").html("");
			}
		}
		
		var tableList = null;
		$(document).ready(function(){
			//获取填充用户参与的项目数据
			$.post('projectAction!findUserAllProjects.huzd',{'dot':1},function(msg){
				var innerHtml = "<option value=''>--请选择--</option>";
				if(msg != ''){
					$.each(msg.projects,function(i,n){
						innerHtml +=("<option value='"+n.id+"'>"+n.name+"</option>");
					});
				}
				$("#q_projectId").empty(); 
				$("#q_projectId").append(innerHtml);
			});	
			
			tableList = $(".tablelist").page({
	             prefix:'',
	             url:'statisticsAction!userRankingList.huzd',
	             pageSize:[10,5,20,30],
	             queryBtn:'queryUserBtn',
	             param:getQueryParam,
	             fillTable:fillTable
	   		});
			
			$("#queryUserBtn").click(function() {
				if($("#q_startDate").val() ==""){
					layer.tips('开始时间不能为空!', '#q_startDate');
					return false;
				}
				if($("#q_endDate").val() ==""){
					layer.tips('结束时间不能为空!', '#q_endDate');
					return false;
				}
			});
			
			$("#excelBtn").click(function() {
				if($("#q_startDate").val() ==""){
					layer.tips('开始时间不能为空!', '#q_startDate');
					return false;
				}
				if($("#q_endDate").val() ==""){
					layer.tips('结束时间不能为空!', '#q_endDate');
					return false;
				}
			
				window.location.href = "statisticsAction!exportBasic.huzd?"+$.param(getQueryParam());
			});
		});
	</script>
</body>

</html>
