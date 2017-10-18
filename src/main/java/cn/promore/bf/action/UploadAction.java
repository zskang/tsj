package cn.promore.bf.action;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

@Controller
@Action(value="uploadAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="tojson",type="json",params={"excludeProperties","filedata,filedataFileName,filedataContentType"}),
		  @Result(name="uploadResult",type="json",params={"includeProperties","success\\[\\d+\\]\\.(path|fileName|flag),failed\\[\\d+\\]\\.(path|fileName|flag)"})
		 })
public class UploadAction extends BaseAction{
	public static Logger LOG = LoggerFactory.getLogger(UploadAction.class);
	private static final long serialVersionUID = -8613055080615406396L;
	//private static final String 	IMAGE_EXT 		= "image/jpg,image/jpeg,image/bmp,image/gif,image/png,image/pjpeg,image/png,image/x-png";  
	private static final String 	FILE_EXT 		= "application/vnd.ms-powerpoint,application/msword,application/excel,application/vnd.ms-excel,application/vnd.openxmlformats-officedocument.spreadsheetml.sheet,application/zip,application/x-xls,";  
    private static final int 		MAX_FILE_SIZE 	= 1024 * 1024 * 10; // 5MB
    // 0:不建目录 1:按天存入目录 2:按月存入目录 3:按扩展名存目录 建议使用按天存  
    //private static String dirType = "1";  
    
    private String err = "";
    private String msg = "";
    
    private File[] filedata;
    private String[] filedataFileName;
    private String[] filedataContentType;
    
    private JSONArray success = new JSONArray();
    private JSONArray failed  = new JSONArray();
   // private Attachment attachment = new Attachment();
    private Boolean flag = false;
    
    public UploadAction() {
    	MDC.put("operateModuleName","文件上传");
	}
    
    private Boolean checkFileCanUpload(File file,String contentType,List<String> allowTypes){
    	if(null==file||StringUtils.isEmpty(contentType))return false;
    	if(file.length()==0)return false;
    	if(!allowTypes.contains(contentType))return false;
    	if(file.length() > MAX_FILE_SIZE)return false;
    	return true;
    }
    
    /**
     * @param distDirPath  目标文件夹保存路径
     * @param fileName 目标文件名 如果为空会自动生成一个UUID名称
     * @param srcfile  用户上传原文件
     * @return 文件保存路径名
     * @throws IOException 
     */
    private String saveFile(String distDirPath,String fileName,File srcFile) throws IOException{
    	if(StringUtils.isEmpty(distDirPath))return null;
    	if(null==srcFile)return null;
    	fileName = StringUtils.isNotEmpty(fileName)?fileName:srcFile.getName();
    	//fileName = FilenameUtils.getExtension(fileName);
    	if(StringUtils.isEmpty(fileName))return null;
    	File dis_dir = new File(distDirPath);
    	if(!dis_dir.exists())dis_dir.mkdirs();
    	FileUtils.copyFile(srcFile, new File(distDirPath+fileName));
    	return distDirPath+fileName;
    }
    
    public String uploadFiles() throws IOException{
    	String distDir = "projectFile/";
    	String distFilePath = returnUploadBasePath() + distDir;
    	List<String> fileExt = Arrays.asList(StringUtils.split(FILE_EXT,","));
    	if(null!=filedata&&filedata.length>0){
    		String path = "";
    		Integer index = 0;
    		for(File uploadFile:filedata){
				JSONObject fileResult = new JSONObject();
				fileResult.put("fileName",filedataFileName[index]);
    			if(checkFileCanUpload(uploadFile,filedataContentType[index],fileExt)){
    				try {
    					path = saveFile(distFilePath,UUID.randomUUID()+"."+FilenameUtils.getExtension(filedataFileName[index]).toString(),uploadFile);
						fileResult.put("flag","true");
						fileResult.put("path",path);
						success.add(fileResult);
					} catch (IOException e) {
						fileResult.put("flag","false");
						fileResult.put("path",path);
						failed.add(fileResult);
						e.printStackTrace();
					}
    			}else{
					fileResult.put("flag","false");
					fileResult.put("path",path);
					failed.add(fileResult);
    			}
    			index++;
    		}
    	}
    	return "uploadResult";
    }

	public String getErr() {
		return err;
	}
	public void setErr(String err) {
		this.err = err;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public File[] getFiledata() {
		return filedata;
	}
	public void setFiledata(File[] filedata) {
		this.filedata = filedata;
	}
	public String[] getFiledataFileName() {
		return filedataFileName;
	}
	public void setFiledataFileName(String[] filedataFileName) {
		this.filedataFileName = filedataFileName;
	}
	public String[] getFiledataContentType() {
		return filedataContentType;
	}
	public void setFiledataContentType(String[] filedataContentType) {
		this.filedataContentType = filedataContentType;
	}
	public Boolean getFlag() {
		return flag;
	}
	public void setFlag(Boolean flag) {
		this.flag = flag;
	}
	public JSONArray getSuccess() {
		return success;
	}
	public void setSuccess(JSONArray success) {
		this.success = success;
	}
	public JSONArray getFailed() {
		return failed;
	}
	public void setFailed(JSONArray failed) {
		this.failed = failed;
	}
}
