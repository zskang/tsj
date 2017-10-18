package cn.promore.bf.listener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.ServletContextAware;

import cn.promore.bf.bean.SiteInfo;
import cn.promore.bf.serivce.ResourceService;
import cn.promore.bf.serivce.SiteInfoService;

public class SiteInfoInitListener implements ServletContextAware, ApplicationListener<ApplicationEvent>{
	
	private static final Log LOG = LogFactory.getLog(SiteInfoInitListener.class);
	
	private static boolean isStarted = false;
	@Resource
	SiteInfoService siteInfoService;
	@Resource
	ResourceService resourceService;
	
	private SiteInfo siteInfo = new SiteInfo();
	private ServletContext servletContext;

	@Transactional(propagation=Propagation.REQUIRED)
	public void onApplicationEvent(ApplicationEvent applicationEvent) {
		if(!isStarted){
			long t1 = System.currentTimeMillis();
			LOG.debug("开始加载站点初始化信息...");
			List<SiteInfo> siteInfos = siteInfoService.findAll();
			if(siteInfos.size()>=1)siteInfo = siteInfos.get(0);
			if(StringUtils.isEmpty(siteInfo.getSystemName()))siteInfo.setSystemName("信息管理系统");
			servletContext.setAttribute("siteinfo",siteInfo);
			servletContext.setAttribute("_theme_",StringUtils.isNotEmpty(siteInfo.getTheme())?siteInfo.getTheme():"skins/default/");
			/***
			 * 初始化权限拦截错误提醒信息；
			 * 把权限 - 权限中文名称 保存起来
			 */
			List<cn.promore.bf.bean.Resource> resourceList =  resourceService.findPermissionNames();
			Map<String,String> permissionNames = new HashMap<String, String>();
			if(null!=resourceList&&resourceList.size()>0){
				for(cn.promore.bf.bean.Resource resource_:resourceList){
					if(ResourceService.RESOURCE_TYPE_FUNCTION == resource_.getType())permissionNames.put(resource_.getPath(),resource_.getName());
				}
			}
			servletContext.setAttribute("permissionNames",permissionNames);
			isStarted = true;
			long t2 = System.currentTimeMillis();
			LOG.debug("站点初始化信息加载完毕。耗时："+(t2 - t1)+"毫秒！");
		}
	}

	public void setServletContext(ServletContext servletContext) {
		this.servletContext = servletContext;
	}
	
}
