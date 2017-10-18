<%@ page contentType="text/html;charset=GBK"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileInputStream"%>
<%@page import="java.io.OutputStream"%>
<%@page import="java.net.URLEncoder"%>
<%
		String template_name = request.getParameter("template_name");
		OutputStream o = response.getOutputStream();
		byte b[] = new byte[500];
	    File fileLoad = new File(this.getServletConfig().getServletContext().getRealPath("/")+"/download/"+template_name);
		response.reset();
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition","attachment;filename="+template_name);
		
		FileInputStream in = new FileInputStream(fileLoad);
		int n;
		while ((n = in.read(b)) != -1) {
			o.write(b, 0, n);
		}
		in.close();
		o.close();
		out = pageContext.pushBody();
%>