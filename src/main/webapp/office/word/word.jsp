<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*"
	pageEncoding="gb2312"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
String filePath = request.getParameter("filePath");
System.out.println("####"+filePath);
int len = filePath.lastIndexOf("/");
String tempFilePath = filePath.substring(0,len);
String idEdit = request.getParameter("idEdit");
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//设置服务器页面
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
//添加自定义按钮
poCtrl.addCustomToolButton("保存","Save",1);
//设置保存页面
poCtrl.setSaveFilePage("SaveFile.jsp?filePath="+tempFilePath );
//打开Word文档
filePath = filePath.replaceAll("/","\\\\\\\\");
System.out.print("##4"+filePath);
poCtrl.webOpen(filePath,OpenModeType.docNormalEdit,"liuxh");
poCtrl.setTagId("PageOfficeCtrl1");//此行必需
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html>
<head>
<meta charset="utf-8">
<title>在线编辑word</title>
<link rel="stylesheet" href="css/style.css"  type="text/css">
<script type="text/javascript">
document.createElement("section");
document.createElement("article");
document.createElement("footer");
document.createElement("header");
document.createElement("hgroup");
document.createElement("nav");
document.createElement("menu");
</script>
</head>
<body>
    <script type="text/javascript">
        function Save() {
            document.getElementById("PageOfficeCtrl1").WebSave();
        }
    </script>
 
 <section class="main w12">

    <div style=" width:auto; height:900px;">
        <po:PageOfficeCtrl id="PageOfficeCtrl1">
        </po:PageOfficeCtrl>
    </div>

 </section>
 <br /><br />
 <div style=" text-align:center; height:80px; border-top: solid 1px #666; line-height:70px;">Copyright &copy 2015 北京卓正志远软件有限公司</div>
</body>
</html>

