package cn.promore.bf.action;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.MDC;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import cn.promore.bf.bean.Announcement;
import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.User;
import cn.promore.bf.serivce.AnnouncementService;

@Controller
@Action(value="annouAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="result",type="json",params={"includeProperties","flag,message"}),
		  @Result(name="objectResult",type="json",params={"includeProperties","flag,message,annou\\.(id|title|createDate|expireData|author|hit|content)"}),
	      @Result(name="listResult",type="json",params={"includeProperties","annous\\[\\d+\\]\\.(id|title|createDate|expireData|author|hit),page\\.(currentPage|pageSize|totalRecords|totalPages),flag,message"})
		})
public class AnnouncementAction extends BaseAction{
	public static Logger LOG = LoggerFactory.getLogger(AnnouncementAction.class);
	private static final long serialVersionUID = -8613055080615406396L;
	
	@Resource 
	AnnouncementService announcementService;
	private Announcement annou;
	private boolean flag = true;
	private String message;
	private List<Announcement> annous;
	private Page page;
	private String dataIds;

	public AnnouncementAction() {
		MDC.put("operateModuleName","公告管理");
	}
	
	
	public String list(){
		SecurityUtils.getSubject().checkPermission("annou:get");
//		String webPath = SiteInfoInitListener.class.getResource("").getPath()+"SiteInfoInitListener.class";
//		System.out.println(webPath);
//		File file = new File(webPath);
//		 if (!file.exists() || !file.isFile()) {  
//	            return null;  
//	        }  
//	        MessageDigest digest = null;  
//	        FileInputStream in = null;  
//	        byte buffer[] = new byte[1024];  
//	        int len;  
//	        try {  
//	            digest = MessageDigest.getInstance("MD5");  
//	            in = new FileInputStream(file);  
//	            while ((len = in.read(buffer, 0, 1024)) != -1) {  
//	                digest.update(buffer, 0, len);  
//	            }  
//	            in.close();  
//	        } catch (Exception e) {  
//	            e.printStackTrace();  
//	            return null;  
//	        }  
//	        BigInteger bigInt = new BigInteger(1, digest.digest()); 
//	        
//	        //b8b1306b40de15d233bf4f16078ccb4d
//	        //655f544c6082eb7627760d6f324bc1ee
//	        
//	        //1613fea40b82af5c84df8cf0203f2c0a
//	        //1613fea40b82af5c84df8cf0203f2c0a
//	        System.out.println("==========>>"+bigInt.toString(16));
		page.setTotalRecords(announcementService.findDataNo(annou));
		annous = announcementService.findDatas(annou,page);
		MDC.put("operateContent","查询系统公告列表"); 
		LOG.info("");	
		return "listResult";
	}
	
	public String add(){
		SecurityUtils.getSubject().checkPermission("annou:add");
		try {
			User u = (User)request.getSession().getAttribute("user");
			annou.setCreateDate(new Date());
			annou.setAuthor(null!=u?u.getChinesename():"无");
			annou.setHit(0);
			announcementService.save(annou);
			flag = true;
			MDC.put("operateContent","添加系统公告："+annou.toString()); 
			LOG.info("");				
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "result";
	}
	
	public String delete(){
		SecurityUtils.getSubject().checkPermission("annou:delete");
		String[] aIds = dataIds.split(",");
		flag = true;
		try {
			for(String id:aIds){
				announcementService.deleteById(Integer.valueOf(id));
				flag = flag&&true;
				MDC.put("operateContent","删除系统公告：编码："+id); 
				LOG.info("");	
			}			
		} catch (Exception e) {
			flag = flag&&false;
			message = e.getMessage();
		}
		return "result";
	}
	
	public String update(){
		SecurityUtils.getSubject().checkPermission("annou:update");
		try {
			Announcement temp = announcementService.findById(annou.getId());
			temp.setTitle(annou.getTitle());
			temp.setExpireData(annou.getExpireData());
			temp.setContent(annou.getContent());
			announcementService.update(temp);
			flag = true;
			MDC.put("operateContent","更新系统公告："+annou.toString()); 
			LOG.info("");	
		} catch (Exception e) {
			flag= false;
			message = e.getMessage();
		}
		return "result";
	}
	
	public String get(){
		SecurityUtils.getSubject().checkPermission("annou:get");
		annou = announcementService.findById(annou.getId());
		String type = request.getParameter("type");
		if(StringUtils.isNotEmpty(type)){
			annou.setHit(annou.getHit()+1);
			announcementService.update(annou);
		}
		MDC.put("operateContent","查询公告信息，ID："+annou.getId()); 
		LOG.info("");	
		return "objectResult";
	}
	
	
	public String getDataIds() {
		return dataIds;
	}

	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}

	public Announcement getAnnou() {
		return annou;
	}
	public void setAnnou(Announcement annou) {
		this.annou = annou;
	}
	public List<Announcement> getAnnous() {
		return annous;
	}
	public void setAnnous(List<Announcement> annous) {
		this.annous = annous;
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
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
}
