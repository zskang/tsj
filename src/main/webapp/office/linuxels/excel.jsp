<%@ page language="java"
	import="java.util.*,com.zhuozhengsoft.pageoffice.*,com.zhuozhengsoft.pageoffice.excelwriter.*"
	pageEncoding="utf-8"%>
<%@ taglib uri="http://java.pageoffice.cn" prefix="po"%>
<%
String filePath = request.getParameter("filePath");
int len = filePath.lastIndexOf("/");
String tempFilePath = filePath.substring(0,len);
String idEdit = request.getParameter("idEdit");
PageOfficeCtrl poCtrl=new PageOfficeCtrl(request);
//���÷�����ҳ��
poCtrl.setServerPage(request.getContextPath()+"/poserver.zz");
//����Զ��尴ť
poCtrl.addCustomToolButton("保存","Save",1);
//���ñ���ҳ��
poCtrl.setSaveFilePage("SaveFile.jsp?filePath="+tempFilePath  );
//��Word�ĵ�
poCtrl.webOpen("file://"+filePath,OpenModeType.xlsNormalEdit,"liuxh");
poCtrl.setTagId("PageOfficeCtrl1");                        //���б���
%>
<script>

</script>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <head>
    <title>在线编辑Excel</title>
</head>

<body>
    <script type="text/javascript">
        function Save() {
            document.getElementById("PageOfficeCtrl1").WebSave();
        }
    </script>
    <form id="form1">
    <div style=" width:100%; height:700px;">
        <po:PageOfficeCtrl id="PageOfficeCtrl1">
        </po:PageOfficeCtrl>
    </div>
    </form>
</body>
</html>
