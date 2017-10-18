<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String id = request.getParameter("id");

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>清单</title>
<link rel="stylesheet" type="text/css" href="${_theme_}css/style.css" />
<link rel="stylesheet" type="text/css"  href="js/artDialog/skins/opera.css"/>
</head>

<body>
    <div class="formbody">
    <div class="formtitle">
    	<span id="title"></span>
    	<div class="right"><a href="task-bill.html">返回</a></div>
    </div>
    	<div class="content">	    	
			    <table class="tablelist">
			    	<thead>
			    	<tr>
				        <th style="width:5%;">序号</th>
				        <th style="width:10%;">角色名称</th>
				        <th style="width:15%;">所属单位/部门</th>
				        <th style="width:15%;">工作任务列表</th>
				        <th style="width:20%;">对应工作流程</th>
			        </tr>
			        </thead>
			        <tbody id="bills">       
			        </tbody>
			    </table>
	    	</div>
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
		
				
		});
		
		fillTable();
		
		function fillTable(){
		var innerHtml = "";
		if("1" == "<%=id%>"){
			$("#title").text("公司技术负责人");
			innerHtml += "<tr><td>1</td><td rowspan=3>公司技术中心负责人</td><td rowspan=3>公司技术中心</td><td>施组审批</td><td>施组管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>2</td><td>特（I、II）级施工方案审批</td><td>特（I、II）级施工方案编制工作任务流程</td></tr>";
			innerHtml += "<tr><td>3</td><td>高风险重点工程施工方案       执行情况月报接收确认</td><td>高风险重点工程施工方案执行情况月报工作任务流程</td></tr>";
		}else if ("2" == "<%=id%>"){
			$("#title").text("代局指总工程师");
			innerHtml += "<tr><td>1</td><td>代局指总工程师</td><td>代局指</td><td>测量计算成果书审核</td><td>测量计算成果书工作任务流程</td></tr>";
		}else if ("3" == "<%=id%>"){
			$("#title").text("代局指工程部长");
			innerHtml += "<tr><td>1</td><td>代局指工程部长</td><td>代局指</td><td>测量计算成果书审核</td><td>测量计算成果书工作任务流程</td></tr>";
		}else if ("4" == "<%=id%>"){
			$("#title").text("代局指测量主管");
			innerHtml += "<tr><td>1</td><td>代局指测量主管</td><td>代局指</td><td>测量计算成果书审核</td><td>测量计算成果书工作任务流程</td></tr>";
		}else if ("5" == "<%=id%>"){
			$("#title").text("项目经理");
			innerHtml += "<tr><td>1</td><td rowspan=22>项目经理</td><td rowspan=22>项目经理部</td><td>施工调查提纲审核</td><td>施工调查工作任务流程</td></tr>";
			innerHtml += "<tr><td>2</td><td>施工调查报告审批</td><td>施工调查工作任务流程</td></tr>";
			innerHtml += "<tr><td>3</td><td>项目管理策划书审批</td><td>项目管理策划书工作任务流程</td></tr>";
			innerHtml += "<tr><td>4</td><td>开（复、停）工报告审批</td><td>开（复、停）工报告工作任务流程</td></tr>";
			innerHtml += "<tr><td>5</td><td>整体工期计划审批</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>6</td><td>年度计划及总结分析审批</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>7</td><td>季度计划及总结分析审批</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>8</td><td>月度计划及总结分析审批</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>9</td><td>周计划及总结分析审批</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>10</td><td>内部技术文件审批</td><td>内部技术文件工作任务流程</td></tr>";
			innerHtml += "<tr><td>11</td><td>技术服务合同审批</td><td>技术服务合同工作任务流程</td></tr>";
			innerHtml += "<tr><td>12</td><td>技术服务合同结算文件审批</td><td>技术服务合同工作任务流程</td></tr>";
			innerHtml += "<tr><td>13</td><td>审批物资总控计划台账</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>14</td><td>施组审核</td><td>施组管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>15</td><td>监控量测数据阅处</td><td>监控量测工作任务流程</td></tr>";
			innerHtml += "<tr><td>16</td><td>变更策划书审批</td><td>设计变更工作任务流程</td></tr>";
			innerHtml += "<tr><td>17</td><td>设计变更资料审核</td><td>设计变更工作任务流程</td></tr>";
			innerHtml += "<tr><td>18</td><td>六项制度执行情况检查记录审核</td><td>六项制度执行情况检查工作任务流程</td></tr>";
			innerHtml += "<tr><td>19</td><td>检验批审批</td><td>检验批工作任务流程</td></tr>";
			innerHtml += "<tr><td>20</td><td>小组注册登记表及课题登记表审核</td><td>QC小组活动工作任务流程</td></tr>";
			innerHtml += "<tr><td>21</td><td>QC小组活动成果报告总结审核</td><td>QC小组活动工作任务流程</td></tr>";
			innerHtml += "<tr><td>22</td><td>整改回复材料审核</td><td>管控检查整改回复工作任务流程</td></tr>";

		}else if ("6" == "<%=id%>"){
			$("#title").text("项目总工程师");
			innerHtml += "<tr><td>1</td><td rowspan=65>项目总工程师</td><td rowspan=65>项目经理部</td><td>施工调查提纲复核</td><td>施工调查工作任务流程</td></tr>";
			innerHtml += "<tr><td>2</td><td>施工调查报告审核</td><td>施工调查工作任务流程</td></tr>";
			innerHtml += "<tr><td>3</td><td>技术管理交底接收</td><td>技术管理交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>4</td><td>项目管理策划书复核</td><td>项目管理策划书工作任务流程</td></tr>";
			innerHtml += "<tr><td>5</td><td>开（复、停）工报告复核</td><td>开（复、停）工报告工作任务流程</td></tr>";
			innerHtml += "<tr><td>6</td><td>整体工期计划审核</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>7</td><td>年度计划及总结分析审核</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>8</td><td>季度计划及总结分析审核</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>9</td><td>月度计划及总结分析审核</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>10</td><td>周计划及总结分析审核</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>11</td><td>技术管理制度审核</td><td>技术管理制度工作任务流程</td></tr>";
			innerHtml += "<tr><td>12</td><td>技术培训计划审核</td><td>技术培训工作任务流程</td></tr>";
			innerHtml += "<tr><td>13</td><td>技术工作交接记录审批</td><td>技术工作交接记录工作流程</td></tr>";
			innerHtml += "<tr><td>14</td><td>外来技术文件阅处</td><td>外来技术文件工作任务流程</td></tr>";
			innerHtml += "<tr><td>15</td><td>内部技术文件审核</td><td>内部技术文件工作任务流程</td></tr>";
			innerHtml += "<tr><td>16</td><td>规范（图集）标准审核</td><td>规范（图集）标准台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>17</td><td>技术服务合同复核</td><td>技术服务合同工作任务流程</td></tr>";
			innerHtml += "<tr><td>18</td><td>技术服务合同结算文件复核</td><td>技术服务合同工作任务流程</td></tr>";
			innerHtml += "<tr><td>19</td><td>审核物资总控计划台账</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>20</td><td>审核物资年度需求计划</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>21</td><td>审核物资季度需求计划</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>22</td><td>审核物资月度需求计划</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>23</td><td>施组编制计划复核 </td><td>施组管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>24</td><td>施组复核</td><td>施组管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>25</td><td>施组检查情况整改验证</td><td>施组管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>26</td><td>专项方案编制计划复核</td><td>施工方案编制计划工作任务流程</td></tr>";
			innerHtml += "<tr><td>27</td><td>特（I、II）级施工方案审核</td><td>特（I、II）级施工方案编制工作任务流程</td></tr>";
			innerHtml += "<tr><td>28</td><td>III级施工方案审批</td><td>III级施工方案管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>29</td><td>高风险重点工程施工方案       执行情况月报审核</td><td>高风险重点工程施工方案执行情况月报工作任务流程</td></tr>";
			innerHtml += "<tr><td>30</td><td>关键工序（特殊过程）界定记录审核</td><td>关键工序（特殊过程）管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>31</td><td>作业指导书审核</td><td>作业指导书工作任务流程</td></tr>";
			innerHtml += "<tr><td>32</td><td>接收控制网成果</td><td>工程交接桩工作任务流程</td></tr>";
			innerHtml += "<tr><td>33</td><td>现场勘查记录审核</td><td>工程交接桩工作任务流程</td></tr>";
			innerHtml += "<tr><td>34</td><td>交接桩记录审核</td><td>工程交接桩工作任务流程</td></tr>";
			innerHtml += "<tr><td>35</td><td>测量计算成果书审核</td><td>测量计算成果书工作任务流程</td></tr>";
			innerHtml += "<tr><td>36</td><td>控制网复测记录审核</td><td>控制网复测管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>37</td><td>监控量测数据阅处</td><td>监控量测工作任务流程</td></tr>";
			innerHtml += "<tr><td>38</td><td>竣工测量审核</td><td>竣工测量工作任务流程</td></tr>";
			innerHtml += "<tr><td>39</td><td>设计文件审核计划复核</td><td>设计文件审核工作任务流程</td></tr>";
			innerHtml += "<tr><td>40</td><td>审批设计文件审核记录</td><td>设计文件审核工作任务流程</td></tr>";
			innerHtml += "<tr><td>41</td><td>变更策划书审核</td><td>设计变更工作任务流程</td></tr>";
			innerHtml += "<tr><td>42</td><td>设计变更资料复核</td><td>设计变更工作任务流程</td></tr>";
			innerHtml += "<tr><td>43</td><td>交底编制计划审核</td><td>工序（安全）技术交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>44</td><td>技术通知单审核</td><td>技术通知单及整改记录工作任务流程</td></tr>";
			innerHtml += "<tr><td>45</td><td>六项制度执行情况检查记录复核</td><td>六项制度执行情况检查工作任务流程</td></tr>";
			innerHtml += "<tr><td>46</td><td>检验批审核</td><td>检验批工作任务流程</td></tr>";
			innerHtml += "<tr><td>47</td><td>工程数量总控台账审核</td><td>工程数量总控台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>48</td><td>实耗工程数量总控台账审核</td><td>实耗工程数量管理台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>49</td><td>工序技术总结审核</td><td>工序技术总结工作任务流程</td></tr>";
			innerHtml += "<tr><td>50</td><td>施工技术总结审核</td><td>工序技术总结工作任务流程</td></tr>";
			innerHtml += "<tr><td>51</td><td>申请科研（工法）立项</td><td>科研（工法）管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>52</td><td>签订科研（工法）合同</td><td>科研（工法）管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>53</td><td>科研（工法）结题报告审核</td><td>科研（工法）管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>54</td><td>专利申请表复核</td><td>专利工作任务流程</td></tr>";
			innerHtml += "<tr><td>55</td><td>专利文本复核</td><td>专利工作任务流程</td></tr>";
			innerHtml += "<tr><td>56</td><td>成立QC活动小组文件</td><td>QC小组活动工作任务流程</td></tr>";
			innerHtml += "<tr><td>57</td><td>小组注册登记表及课题登记表复核</td><td>QC小组活动工作任务流程</td></tr>";
			innerHtml += "<tr><td>58</td><td>小组活动季度报表复核</td><td>QC小组活动工作任务流程</td></tr>";
			innerHtml += "<tr><td>59</td><td>QC小组活动成果报告总结复核</td><td>QC小组活动工作任务流程</td></tr>";
			innerHtml += "<tr><td>60</td><td>施工日志检查</td><td>施工日志工作任务流程</td></tr>";
			innerHtml += "<tr><td>61</td><td>整改回复材料复核</td><td>施工调查工作任务流程</td></tr>";
			innerHtml += "<tr><td>62</td><td>竣工资料编制计划审核</td><td>竣工资料管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>63</td><td>竣工资料审核</td><td>竣工资料管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>64</td><td>首件制自检资料复核</td><td>首件工程技术资料管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>65</td><td>首件检查验收整改回复资料复核</td><td>首件工程技术资料管理工作任务流程</td></tr>";
		}else if ("7" == "<%=id%>"){
			$("#title").text("项目工程部长");
			innerHtml += "<tr><td>1</td><td rowspan=79>项目工程部长</td><td rowspan=79>项目经理部</td><td>制定施工调查提纲</td><td>施工调查工作任务流程</td></tr>";
			innerHtml += "<tr><td>2</td><td>汇总编制现场情况调查表</td><td>施工调查工作任务流程</td></tr>";
			innerHtml += "<tr><td>3</td><td>编制施工调查报告</td><td>施工调查工作任务流程</td></tr>";
			innerHtml += "<tr><td>4</td><td>整理技术管理交底学习记录</td><td>技术管理交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>5</td><td>制定管理策划书编制提纲</td><td>项目管理策划书工作任务流程</td></tr>";
			innerHtml += "<tr><td>6</td><td>汇总编制项目管理策划书</td><td>项目管理策划书工作任务流程</td></tr>";
			innerHtml += "<tr><td>7</td><td>编制开（复、停）工报告</td><td>开（复、停）工报告工作任务流程</td></tr>";
			innerHtml += "<tr><td>8</td><td>制定整体工期计划</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>9</td><td>编制年度计划及总结分析</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>10</td><td>编制季度计划及总结分析</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>11</td><td>编制月度计划及总结分析</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>12</td><td>编制周计划及总结分析</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>13</td><td>制定技术管理制度</td><td>技术管理制度工作任务流程</td></tr>";
			innerHtml += "<tr><td>14</td><td>制定技术培训计划</td><td>技术培训工作任务流程</td></tr>";
			innerHtml += "<tr><td>15</td><td>技术工作交接记录审核</td><td>技术工作交接记录工作流程</td></tr>";
			innerHtml += "<tr><td>16</td><td>外来技术文件执行</td><td>外来技术文件工作任务流程</td></tr>";
			innerHtml += "<tr><td>17</td><td>内部技术文件核稿</td><td>内部技术文件工作任务流程</td></tr>";
			innerHtml += "<tr><td>18</td><td>批准文件借阅</td><td>文件借阅记录工作任务流程</td></tr>";
			innerHtml += "<tr><td>19</td><td>批准文件发放</td><td>文件发放记录工作任务流程</td></tr>";
			innerHtml += "<tr><td>20</td><td>规范（图集）标准复核汇总</td><td>规范（图集）标准台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>21</td><td>规范（图集）标准查新记录复核</td><td>规范（图集）标准台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>22</td><td>技术服务合同拟定</td><td>技术服务合同工作任务流程</td></tr>";
			innerHtml += "<tr><td>23</td><td>技术服务合同结算文件 </td><td>技术服务合同工作任务流程</td></tr>";
			innerHtml += "<tr><td>24</td><td>编制物资总控计划台账</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>25</td><td>编制物资年度需求计划</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>26</td><td>编制物资季度需求计划</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>27</td><td>编制物资月度需求计划</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>28</td><td>日物资计划表复核</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>29</td><td>制定施组编制计划</td><td>施组管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>30</td><td>施组编制</td><td>施组管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>31</td><td>整理施组交底会议纪要</td><td>施组管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>32</td><td>编制并分发施组检查记录</td><td>施组管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>33</td><td>制定专项方案编制计划</td><td>施工方案编制计划工作任务流程</td></tr>";
			innerHtml += "<tr><td>34</td><td>特（I、II）级施工方案编制</td><td>特（I、II）级施工方案编制工作任务流程</td></tr>";
			innerHtml += "<tr><td>35</td><td>整理特（I、II）级施工方案    交底会议纪要</td><td>特（I、II）级施工方案编制工作任务流程</td></tr>";
			innerHtml += "<tr><td>36</td><td>编制并分发特（I、II）级      施工方案检查记录</td><td>特（I、II）级施工方案编制工作任务流程</td></tr>";
			innerHtml += "<tr><td>37</td><td>特（I、II）级施工方案        检查情况整改验证</td><td>特（I、II）级施工方案编制工作任务流程</td></tr>";
			innerHtml += "<tr><td>38</td><td>III级施工方案审核</td><td>III级施工方案管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>39</td><td>整理III级施工方案           交底会议纪要</td><td>III级施工方案管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>40</td><td>编制并分发III级施工方案      检查记录</td><td>III级施工方案管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>41</td><td>III级施工方案检查情况       整改验证</td><td>III级施工方案管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>42</td><td>高风险重点工程施工方案       执行情况月报复核</td><td>高风险重点工程施工方案执行情况月报工作任务流程</td></tr>";
			innerHtml += "<tr><td>43</td><td>关键工序（特殊过程）界定记录</td><td>关键工序（特殊过程）管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>44</td><td>作业指导书复核</td><td>作业指导书工作任务流程</td></tr>";
			innerHtml += "<tr><td>45</td><td>仪器管理台账复核</td><td>测量仪器管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>46</td><td>现场勘查记录复核</td><td>工程交接桩工作任务流程</td></tr>";
			innerHtml += "<tr><td>47</td><td>交接桩记录复核</td><td>工程交接桩工作任务流程</td></tr>";
			innerHtml += "<tr><td>48</td><td>测量计算成果书复核</td><td>测量计算成果书工作任务流程</td></tr>";
			innerHtml += "<tr><td>49</td><td>放样图解记录复核</td><td>放样图解工作任务流程</td></tr>";
			innerHtml += "<tr><td>50</td><td>控制网复测记录复核</td><td>控制网复测管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>51</td><td>监控量测数据复核</td><td>监控量测工作任务流程</td></tr>";
			innerHtml += "<tr><td>52</td><td>竣工测量复核</td><td>竣工测量工作任务流程</td></tr>";
			innerHtml += "<tr><td>53</td><td>安排发放设计文件</td><td>设计文件收发台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>54</td><td>专制定设计文件审核计划</td><td>设计文件审核工作任务流程</td></tr>";
			innerHtml += "<tr><td>55</td><td>复核设计文件审核记录</td><td>设计文件审核工作任务流程</td></tr>";
			innerHtml += "<tr><td>56</td><td>接收设计单位回复</td><td>设计文件审核工作任务流程</td></tr>";
			innerHtml += "<tr><td>57</td><td>编制/输入变更策划书</td><td>设计变更工作任务流程</td></tr>";
			innerHtml += "<tr><td>58</td><td>编制设计变更资料</td><td>设计变更工作任务流程</td></tr>";
			innerHtml += "<tr><td>59</td><td>制定交底编制计划</td><td>工序（安全）技术交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>60</td><td>技术交底审核</td><td>工序（安全）技术交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>61</td><td>编制技术交底执行情况检查记录</td><td>工序（安全）技术交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>62</td><td>技术通知单复核</td><td>技术通知单及整改记录工作任务流程</td></tr>";
			innerHtml += "<tr><td>63</td><td>旁站记录检查签字</td><td>施工旁站工作任务流程</td></tr>";
			innerHtml += "<tr><td>64</td><td>编制六项制度执行情况检查记录</td><td>六项制度执行情况检查工作任务流程</td></tr>";
			innerHtml += "<tr><td>65</td><td>接收危险源管理资料</td><td>危险源管理资料归集工作任务流程</td></tr>";
			innerHtml += "<tr><td>66</td><td>检验批复核</td><td>检验批工作任务流程</td></tr>";
			innerHtml += "<tr><td>67</td><td>工程数量总控台账复核</td><td>工程数量总控台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>68</td><td>实耗工程数量总控台账复核</td><td>实耗工程数量管理台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>69</td><td>收方记录审核</td><td>收方管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>70</td><td>工序技术总结复核</td><td>工序技术总结工作任务流程</td></tr>";
			innerHtml += "<tr><td>71</td><td>编制施工技术总结</td><td>工序技术总结工作任务流程</td></tr>";
			innerHtml += "<tr><td>72</td><td>施工日志审核</td><td>施工日志工作任务流程</td></tr>";
			innerHtml += "<tr><td>73</td><td>输入管控检查资料</td><td>管控检查整改回复工作任务流程</td></tr>";
			innerHtml += "<tr><td>74</td><td>编制整改回复材料</td><td>管控检查整改回复工作任务流程</td></tr>";
			innerHtml += "<tr><td>75</td><td>编制项目竣工资料清单</td><td>竣工资料管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>76</td><td>制定竣工资料编制计划</td><td>竣工资料管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>77</td><td>竣工资料复核</td><td>竣工资料管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>78</td><td>形成首件制自检资料</td><td>首件工程技术资料管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>79</td><td>首件检查验收整改回复资料</td><td>首件工程技术资料管理工作任务流程</td></tr>";
		}else if ("8" == "<%=id%>"){
			$("#title").text("项目技术主管");
			innerHtml += "<tr><td>1</td><td rowspan=26>项目技术主管</td><td rowspan=26>项目经理部</td><td>规范（图集）标准收集</td><td>规范（图集）标准台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>2</td><td>编制日物资计划表</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>3</td><td>III级施工方案编制</td><td>III级施工方案管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>4</td><td>高风险重点工程施工方案      执行情况月报编制</td><td>高风险重点工程施工方案执行情况月报工作任务流程</td></tr>";
			innerHtml += "<tr><td>5</td><td>编制作业指导书</td><td>作业指导书工作任务流程</td></tr>";
			innerHtml += "<tr><td>6</td><td>作业指导书下发签字</td><td>作业指导书工作任务流程</td></tr>";
			innerHtml += "<tr><td>7</td><td>控制网复测记录接收</td><td>控制网复测管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>8</td><td>设计文件审核</td><td>设计文件审核工作任务流程</td></tr>";
			innerHtml += "<tr><td>9</td><td>设计文件复核</td><td>设计文件审核工作任务流程</td></tr>";
			innerHtml += "<tr><td>10</td><td>登记回复情况并订正</td><td>设计文件审核工作任务流程</td></tr>";
			innerHtml += "<tr><td>11</td><td>施工过程签证资料收集</td><td>设计变更工作任务流程</td></tr>";
			innerHtml += "<tr><td>12</td><td>技术交底复核</td><td>工序（安全）技术交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>13</td><td>技术交底执行情况检查记录通报</td><td>工序（安全）技术交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>14</td><td>技术交底执行情况整改验证</td><td>工序（安全）技术交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>15</td><td>编制技术通知单</td><td>技术工作交接记录工作流程</td></tr>";
			innerHtml += "<tr><td>16</td><td>技术通知单下发签字</td><td>技术通知单及整改记录工作任务流程</td></tr>";
			innerHtml += "<tr><td>17</td><td>现场整改验证</td><td>技术通知单及整改记录工作任务流程</td></tr>";
			innerHtml += "<tr><td>18</td><td>现场旁站</td><td>施工旁站工作任务流程</td></tr>";
			innerHtml += "<tr><td>19</td><td>接收试验检测资料</td><td>试验资料归集工作任务流程</td></tr>";
			innerHtml += "<tr><td>20</td><td>检验批复核</td><td>检验批工作任务流程</td></tr>";
			innerHtml += "<tr><td>21</td><td>填写工程数量总控台账</td><td>工程数量总控台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>22</td><td>填写实耗工程数量总控台账</td><td>实耗工程数量管理台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>23</td><td>收方记录复核 </td><td>收方管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>24</td><td>现场影像资料分类</td><td>施工影像工作任务流程</td></tr>";
			innerHtml += "<tr><td>25</td><td>编制工序技术总结</td><td>工序技术总结工作任务流程</td></tr>";
			innerHtml += "<tr><td>26</td><td>施工日志复核</td><td>施工日志工作任务流程</td></tr>";
		}else if ("9" == "<%=id%>"){
			$("#title").text("项目测量主管");
			innerHtml += "<tr><td>1</td><td rowspan=8>项目测量主管</td><td rowspan=8>项目经理部</td><td>登记仪器管理台账</td><td>测量仪器管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>2</td><td>编制现场勘查记录</td><td>工程交接桩工作任务流程</td></tr>";
			innerHtml += "<tr><td>3</td><td>编制交接桩记录</td><td>工程交接桩工作任务流程</td></tr>";
			innerHtml += "<tr><td>4</td><td>计算编制测量计算成果书</td><td>测量计算成果书工作任务流程</td></tr>";
			innerHtml += "<tr><td>5</td><td>施工放样/高程测量记录复核</td><td>施工放样/高程测量记录工作任务流程</td></tr>";
			innerHtml += "<tr><td>6</td><td>放样图解记录</td><td>放样图解工作任务流程</td></tr>";
			innerHtml += "<tr><td>7</td><td>输入控制网复测记录</td><td>控制网复测管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>8</td><td>开展竣工测量</td><td>竣工测量工作任务流程</td></tr>";
		}else if ("10" == "<%=id%>"){
			$("#title").text("项目技术员");
			innerHtml += "<tr><td>1</td><td rowspan=18>项目测量主管</td><td rowspan=18>项目经理部</td><td>施工放样/高程测量记录</td><td>施工放样/高程测量记录工作任务流程</td></tr>";
			innerHtml += "<tr><td>2</td><td>设计文件审核</td><td>设计文件审核工作任务流程</td></tr>";
			innerHtml += "<tr><td>3</td><td>设计文件复核</td><td>设计文件审核工作任务流程</td></tr>";
			innerHtml += "<tr><td>4</td><td>登记回复情况并订正</td><td>设计文件审核工作任务流程</td></tr>";
			innerHtml += "<tr><td>5</td><td>编制技术交底</td><td>工序（安全）技术交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>6</td><td>技术交底下发</td><td>工序（安全）技术交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>7</td><td>登记施工工序台账</td><td>施工工序台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>8</td><td>施工工序台账复核</td><td>施工工序台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>9</td><td>现场旁站</td><td>施工旁站工作任务流程</td></tr>";
			innerHtml += "<tr><td>10</td><td>编制检验批</td><td>检验批工作任务流程</td></tr>";
			innerHtml += "<tr><td>11</td><td>接收劳务队伍清单</td><td>收方管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>12</td><td>编制收方记录</td><td>收方管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>13</td><td>更新工程数量总控台账</td><td>收方管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>14</td><td>收集现场影像资料td><td>施工影像工作任务流程</td></tr>";
			innerHtml += "<tr><td>15</td><td>填写施工日志</td><td>施工日志工作任务流程</td></tr>";
			innerHtml += "<tr><td>16</td><td>施工日志修改完善</td><td>施工日志工作任务流程</td></tr>";
			innerHtml += "<tr><td>17</td><td>竣工资料编制</td><td>竣工资料管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>18</td><td>竣工资料修改</td><td>竣工资料管理工作任务流程</td></tr>";
		}else if ("11" == "<%=id%>"){
			$("#title").text("项目资料员");
			innerHtml += "<tr><td>1</td><td rowspan=85>项目资料员</td><td rowspan=85>项目经理部</td><td>施工调查提纲归卷</td><td>施工调查工作任务流程</td></tr>";
			innerHtml += "<tr><td>2</td><td>施工调查报告归卷</td><td>施工调查工作任务流程</td></tr>";
			innerHtml += "<tr><td>3</td><td>技术管理交底及                学习记录归卷</td><td>技术管理交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>4</td><td>项目管理策划书                编制提纲归卷</td><td>项目管理策划书工作任务流程图</td></tr>";
			innerHtml += "<tr><td>5</td><td>项目管理策划书归卷</td><td>项目管理策划书工作任务流程图</td></tr>";
			innerHtml += "<tr><td>6</td><td>开（复、停）工报告归卷</td><td>开（复、停）工报告工作任务流程</td></tr>";
			innerHtml += "<tr><td>7</td><td>整体工期计划归卷</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>8</td><td>年度计划及总结分析归卷</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>9</td><td>季度计划及总结分析归卷</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>10</td><td>月度计划及总结分析归卷</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>11</td><td>周计划及总结分析归卷</td><td>工期管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>12</td><td>技术管理制度归卷</td><td>技术管理制度工作任务流程</td></tr>";
			innerHtml += "<tr><td>13</td><td>技术培训计划归卷</td><td>技术培训工作任务流程</td></tr>";
			innerHtml += "<tr><td>14</td><td>技术培训资料编制</td><td>技术培训工作任务流程</td></tr>";
			innerHtml += "<tr><td>15</td><td>技术工作交接记录归卷</td><td>技术工作交接记录工作流程</td></tr>";
			innerHtml += "<tr><td>16</td><td>外来技术文件归卷</td><td>外来技术文件工作任务流程</td></tr>";
			innerHtml += "<tr><td>17</td><td>内部技术文件归卷</td><td>内部技术文件工作任务流程</td></tr>";
			innerHtml += "<tr><td>18</td><td>文件借阅登记</td><td>文件借阅记录工作任务流程</td></tr>";
			innerHtml += "<tr><td>19</td><td>文件归还登记</td><td>文件借阅记录工作任务流程</td></tr>";
			innerHtml += "<tr><td>20</td><td>文件发放登记</td><td>文件发放记录工作任务流程</td></tr>";
			innerHtml += "<tr><td>21</td><td>规范（图集）标准台账归卷</td><td>规范（图集）标准台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>22</td><td>规范（图集）标准查新记录</td><td>规范（图集）标准台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>23</td><td>规范（图集）标准台账更新归卷 </td><td>规范（图集）标准台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>24</td><td>技术服务合同归卷</td><td>技术服务合同工作任务流程</td></tr>";
			innerHtml += "<tr><td>25</td><td>技术服务合同结算文件归卷</td><td>技术服务合同工作任务流程</td></tr>";
			innerHtml += "<tr><td>26</td><td>物资总控计划台账归卷</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>27</td><td>物资年度需求计划台账归卷</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>28</td><td>物资季度需求计划台账归卷</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>29</td><td>物资月度需求计划台账归卷</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>30</td><td>日物资计划表归卷</td><td>物资总控计划台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>31</td><td>机械设备需求计划归卷</td><td>机械设备需求计划工作任务流程</td></tr>";
			innerHtml += "<tr><td>32</td><td>施组编制计划归卷</td><td>施组管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>33</td><td>施组及会议纪要归卷</td><td>施组管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>34</td><td>施组检查及整改记录归卷</td><td>施组管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>35</td><td>特（I、II）级施工方案审批</td><td>特（I、II）级施工方案编制工作任务流程</td></tr>";
			innerHtml += "<tr><td>36</td><td>特（I、II）级施工方案及      会议纪要归卷</td><td>特（I、II）级施工方案编制工作任务流</td></tr>";
			innerHtml += "<tr><td>37</td><td>特（I、II）级施工方案检查及  整改记录归卷</td><td>特（I、II）级施工方案编制工作任务流程</td></tr>";
			innerHtml += "<tr><td>38</td><td>III级施工方案及             会议纪要归卷</td><td>III级施工方案管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>39</td><td>III级施工方案检查整改       记录归卷</td><td>III级施工方案管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>40</td><td>高风险重点工程施工方案       执行情况月报归卷</td><td>高风险重点工程施工方案执行情况月报工作任务流程</td></tr>";
			innerHtml += "<tr><td>41</td><td>编制关键工序（特殊过程）清单</td><td>关键工序（特殊过程）管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>42</td><td>关键工序（特殊过程）界定记录及清单归卷</td><td>关键工序（特殊过程）管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>43</td><td>作业指导书归卷</td><td>作业指导书工作任务流程</td></tr>";
			innerHtml += "<tr><td>44</td><td>仪器检定资料及管理台账归卷</td><td>测量仪器管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>45</td><td>交接桩记录归卷</td><td>工程交接桩工作任务流程</td></tr>";
			innerHtml += "<tr><td>46</td><td>测量计算成果书归卷</td><td>测量计算成果书工作任务流程</td></tr>";
			innerHtml += "<tr><td>47</td><td>施工放样/高程测量记录归卷</td><td>施工放样/高程测量记录工作任务流程</td></tr>";
			innerHtml += "<tr><td>48</td><td>放样图解记录归卷</td><td>放样图解工作任务流程</td></tr>";
			innerHtml += "<tr><td>49</td><td>控制网复测记录归卷</td><td>控制网复测管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>50</td><td>监控量测数据归卷</td><td>监控量测工作任务流程</td></tr>";
			innerHtml += "<tr><td>51</td><td>竣工测量数据归卷</td><td>竣工测量工作任务流程</td></tr>";
			innerHtml += "<tr><td>52</td><td>设计文件归卷并登记台账</td><td>设计文件收发台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>53</td><td>登记设计文件发放台账</td><td>设计文件收发台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>54</td><td>设计文件审核计划归卷</td><td>设计文件审核工作任务流程</td></tr>";
			innerHtml += "<tr><td>55</td><td>设计文件审核记录归卷</td><td>设计文件审核工作任务流程</td></tr>";
			innerHtml += "<tr><td>56</td><td>变更策划书归卷</td><td>设计变更工作任务流程</td></tr>";
			innerHtml += "<tr><td>57</td><td>设计变更资料归卷</td><td>设计变更工作任务流程</td></tr>";
			innerHtml += "<tr><td>58</td><td>交底编制计划归卷</td><td>工序（安全）技术交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>59</td><td>技术交底归卷</td><td>工序（安全）技术交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>60</td><td>技术交底执行情况检查及      整改记录归卷</td><td>工序（安全）技术交底工作任务流程</td></tr>";
			innerHtml += "<tr><td>61</td><td>施工工序台账归卷</td><td>施工工序台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>62</td><td>技术通知单及整改验证记录归卷</td><td>技术通知单及整改记录工作任务流程</td></tr>";
			innerHtml += "<tr><td>63</td><td>旁站记录归卷</td><td>施工旁站工作任务流程</td></tr>";
			innerHtml += "<tr><td>64</td><td>六项制度执行情况检查记录归卷</td><td>六项制度执行情况检查工作任务流程</td></tr>";
			innerHtml += "<tr><td>65</td><td>危险源管理资料归卷</td><td>危险源管理资料归集工作任务流程</td></tr>";
			innerHtml += "<tr><td>66</td><td>试验资料分类归卷</td><td>试验资料归集工作任务流程</td></tr>";
			innerHtml += "<tr><td>67</td><td>检验批归卷</td><td>检验批工作任务流程</td></tr>";
			innerHtml += "<tr><td>68</td><td>工程数量总控台账归卷</td><td>工程数量总控台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>69</td><td>实耗工程数量总控台账归卷</td><td>实耗工程数量管理台账工作任务流程</td></tr>";
			innerHtml += "<tr><td>70</td><td>收方记录归卷</td><td>收方管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>71</td><td>现场影像资料归卷</td><td>施工影像工作任务流程</td></tr>";
			innerHtml += "<tr><td>72</td><td>工序技术总结归卷</td><td>工序技术总结工作任务流程</td></tr>";
			innerHtml += "<tr><td>73</td><td>施工技术总结归卷</td><td>工序技术总结工作任务流程</td></tr>";
			innerHtml += "<tr><td>74</td><td>科研（工法）立项报告及合同归卷</td><td>科研（工法）管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>75</td><td>科研（工法）过程资料归卷</td><td>科研（工法）管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>76</td><td>科研（工法）结题报告归卷</td><td>科研（工法）管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>77</td><td>专利申请表及文本归卷</td><td>专利工作任务流程</td></tr>";
			innerHtml += "<tr><td>78</td><td>小组注册登记表及课题登记表归卷</td><td>QC小组活动工作任务流程</td></tr>";
			innerHtml += "<tr><td>79</td><td>QC小组活动成果报告总结归卷</td><td>QC小组活动工作任务流程</td></tr>";
			innerHtml += "<tr><td>80</td><td>施工日志归卷</td><td>施工日志工作任务流程</td></tr>";
			innerHtml += "<tr><td>81</td><td>施工日志检查记录归卷</td><td>施工日志工作任务流程</td></tr>";
			innerHtml += "<tr><td>82</td><td>管控检查和整改回复资料归卷</td><td>管控检查整改回复工作任务流程</td></tr>";
			innerHtml += "<tr><td>83</td><td>竣工资料编制计划归卷</td><td>竣工资料管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>84</td><td>竣工资料归卷</td><td>竣工资料管理工作任务流程</td></tr>";
			innerHtml += "<tr><td>85</td><td>首件制技术资料归卷</td><td>竣工资料管理工作任务流程</td></tr>";
		}
		$("#bills").html(innerHtml);
	}
		
		
		</script>    
</body>
</html>
