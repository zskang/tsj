package cn.promore.bf.action;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.MDC;
import org.apache.shiro.SecurityUtils;
import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.SiteInfo;
import cn.promore.bf.serivce.SiteInfoService;

@Controller
@Action(value="siteInfoAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="siteinfos",type="json",params={"includeProperties","siteInfo\\.(id|systemName|systemEnName|systemAddress|ipcNo|watermark|watermarkContent),flag,message"})
		})
public class SiteInfoAction extends BaseAction{
	
	private static final long serialVersionUID = -682556675754068440L;
	public static Logger LOG = LoggerFactory.getLogger(SiteInfoAction.class);
	
	@Resource
	SiteInfoService siteInfoService;
	private SiteInfo siteInfo;
	private boolean flag;
	private String message;
	private List<SiteInfo> siteInfos;
	private Page page;

	public SiteInfoAction() {
		MDC.put("operateModuleName","系统设置");
	}
	
	public String add(){
		SecurityUtils.getSubject().checkPermission("siteInfo:update");
		try {
			siteInfoService.saveOrUpdate(siteInfo);
			ServletActionContext.getServletContext().setAttribute("siteinfo",siteInfo);
			ServletActionContext.getServletContext().setAttribute("_theme_",siteInfo.getTheme());
			flag = true;
			MDC.put("operateContent",siteInfo.toString()); 
			LOG.info("");
		} catch (Exception e) {
			flag = false;
			message=e.getMessage();
		}
		return	"siteinfos";
	}
	
	public String list(){
		SecurityUtils.getSubject().checkPermission("siteInfo:get");
		page.setTotalRecords(siteInfoService.findAllNo());
		siteInfos = siteInfoService.findSiteInfos(page);
		MDC.put("operateContent","站点信息列表查询"); 
		LOG.info("");
		return "siteinfos";
	}

	public SiteInfoService getSiteInfoService() {
		return siteInfoService;
	}

	public void setSiteInfoService(SiteInfoService siteInfoService) {
		this.siteInfoService = siteInfoService;
	}

	public SiteInfo getSiteInfo() {
		return siteInfo;
	}

	public void setSiteInfo(SiteInfo siteInfo) {
		this.siteInfo = siteInfo;
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

	public List<SiteInfo> getSiteInfos() {
		return siteInfos;
	}

	public void setSiteInfos(List<SiteInfo> siteInfos) {
		this.siteInfos = siteInfos;
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
	}
}
