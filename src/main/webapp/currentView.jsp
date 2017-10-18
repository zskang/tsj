<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String deploymentId = (String) request.getSession().getAttribute("deploymentId");
	String diagramResourceName = (String) request.getSession().getAttribute("diagramResourceName");
	//String ywName = (String) request.getSession().getAttribute("ywName");
%>
<html>
<head>
<base href="<%=basePath%>" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>查看流程图</title>
</head>
<body>

  <div id="returnDiv"
		style="text-align: right; height: 50px; padding: 10px 30px 0 30px;"
		class="hide">
		<button id="returnBtn" class="btn_small" onclick="javascript:history.back(-1);">返回</button>
	</div> 
	<br/>

	<div>
	<img style="position: absolute; top: 50px; left: 0px"
			src="${pageContext.request.contextPath}/workflowAction!showView.huzd?deploymentId=<%=deploymentId %>&diagramResourceName=<%=diagramResourceName%>">

	<div
		style="position: absolute;border: 3px solid red;top:${y+47}px;left:${x-3}px;width:${width+2}px;height:${height+3}px"></div></div>

</body>
</html>