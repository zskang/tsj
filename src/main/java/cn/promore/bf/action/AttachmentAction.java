package cn.promore.bf.action;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.apache.shiro.codec.Base64;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

@Controller
@Action(value="attachmentAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="download",
				  type="stream",
				  params={"contentType","application/vnd.ms-excel;charset=utf-8","contentLength","${fileSize}",
			              "contentDisposition","attachment;filename=\"${fileDownloadName}\"",
			              "inputName","inputStream","bufferSize","4096"})
		})
public class AttachmentAction extends BaseAction{
	private static final long serialVersionUID = -86016804623833070L;
	public static Logger LOG = LoggerFactory.getLogger(AttachmentAction.class);
	
	private boolean flag;
	private String message;
	private Long  fileSize;
	private InputStream inputStream;
	private String fileDownloadName;

	public AttachmentAction() {
		MDC.put("operateModuleName","附件管理");
	}
	

	public InputStream getInputStream(){  
		
		try {
			String path = ServletActionContext.getServletContext().getRealPath("/download")+System.getProperty("file.separator")+"importTemplate.xls";
			System.out.println(path);
			File downloadFile = new File(path);
			fileSize = downloadFile.length();
			inputStream =new FileInputStream(downloadFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		return inputStream;  
    }
	  
	public String download(){
		String  fileName = "基本信息导入模板";
		String  fileExt  = ".xls";
		String agent = request.getHeader("USER-AGENT");
		try {
			if (null != agent){ 
	            if (-1 != agent.indexOf("Firefox")) {//Firefox  
					fileDownloadName = "=?UTF-8?B?" + (new String(Base64.encode(fileName.getBytes("UTF-8"))))+ "?=";
					fileDownloadName = fileDownloadName+fileExt;  
	            }else if (-1 != agent.indexOf("Chrome")) {//Chrome  
	            	fileDownloadName = new String(fileName.getBytes(), "ISO8859-1");  
	            	fileDownloadName = fileDownloadName+fileExt;  
	            } else {//IE7+  
	            	fileDownloadName = java.net.URLEncoder.encode(fileName, "UTF-8");  
	            	fileDownloadName = StringUtils.replace(fileDownloadName, "+", "%20");//替换空格  
	            	fileDownloadName = fileDownloadName+fileExt;  
	            }  
	        } else {  
	        	fileDownloadName = fileName+fileExt;  
	        }  
		} catch (UnsupportedEncodingException e) {
			fileDownloadName = "downloadFile";
			e.printStackTrace();
		}  
		return "download";
	}

	public boolean isFlag() {
		return flag;
	}
	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	public String getFileDownloadName() {
		return fileDownloadName;
	}
	public void setFileDownloadName(String fileDownloadName) {
		this.fileDownloadName = fileDownloadName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}
}
