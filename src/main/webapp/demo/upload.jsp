<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    <title>Upload Page Demo</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript" src="js/jquery.js"></script>
	<script type="text/javascript" src="js/jquery.form.js"></script>
	<script type="text/javascript">
	$(function(){
        var options = {
            url: 'uploadAction!uploadFiles.huzd',
            type: 'post',
            dataType: 'json',
            data: $("#testForm").serialize(),
            success: function (data) {
                if (data.length > 0)
                   console.log(data);
            }
        };
		$("#btnAjaxSubmit").click(function () {
			$("#testForm").ajaxSubmit(options);
        });
	});
	</script>
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  <body>
		<form id="testForm" action="#" method="post" enctype="multipart/form-data">
		    <table>
		        <tr>
		            <td>附件:</td>
		        </tr>
		        <tr>
		        	<td>
		                <input type="file" name="filedata" />
		            </td>
		        </tr>    
		        <tr>
		        	<td>
		                <input type="file" name="filedata" />
		            </td>
		        </tr>    
		        <tr>
		        	<td>
		                <input type="file" name="filedata" />
		            </td>
		        </tr>    
		        <tr>
		        	<td>
		                <input type="file" name="filedata" />
		            </td>
		        </tr>    
		        <tr>
		            <td  style="align-content: center">
		                <a id="btnAjaxSubmit">Submit</a>
		            </td>
		        </tr>
		    </table>
		</form>  
  </body>
</html>
