package cn.promore.bf.action;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.MDC;
import org.apache.log4j.spi.LoggerRepository;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.ehcache.EhCacheCache;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Controller;
import cn.promore.bf.bean.Log;
import cn.promore.bf.bean.Page;
import cn.promore.bf.serivce.LogService;
import net.sf.ehcache.management.CacheStatistics;

@Controller
@Action(value="logAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="result",type="json"),
	      @Result(name="list",type="json",params={"includeProperties","logs\\[\\d+\\]\\.(id|username|clientIp|operateModule|operateModuleName|operateType|operateTime|operateContent|operateResult),page\\.(\\w+),flag,message","excludeNullProperties","true"})
		})
public class LogAction extends BaseAction{
	
	public static Logger LOG = LoggerFactory.getLogger(LogAction.class);
	private static final long serialVersionUID = -8613055080615406396L;
	@Resource 
	LogService logService;
	@Resource
	EhCacheCacheManager ehCacheManager;
	private Log log;
	private boolean flag = true;
	private String message;
	private Page page;
	private List<Log> logs;
	private Date startDate;
	private Date endDate;
	
	public LogAction() {
		MDC.put("operateModuleName","日志管理");
	}
	
	@RequiresPermissions("log:get")
	public String list(){
		if(null==page)page=new Page();
		page.setTotalRecords(logService.findDatasNo(log, startDate, endDate));
		logs = logService.findDatas(log, page, startDate, endDate);
		MDC.put("operateContent","日志列表查询"); 
		LOG.info("");
		return "list";
	}

	public String changeLogLevel(){
		LoggerRepository loggerRepository = org.apache.log4j.LogManager.getLoggerRepository();
		System.out.println("===========getCurrentCategories============");
		Enumeration<?> allCategories =  loggerRepository.getCurrentCategories();
		while(allCategories.hasMoreElements()){
			org.apache.log4j.Logger logger = (org.apache.log4j.Logger)allCategories.nextElement();  
			if(logger.getName().indexOf("cn.promore")!=-1)System.out.println("------------"+logger.getName());
		}
		System.out.println("=================getCurrentLoggers======Threshold:" + loggerRepository.getThreshold());
		Enumeration<?> allLoggers =  loggerRepository.getCurrentLoggers();
		while(allLoggers.hasMoreElements()){
			org.apache.log4j.Logger logger = (org.apache.log4j.Logger)allLoggers.nextElement();  
			if(logger.getName().indexOf("cn.promore")!=-1){
				System.out.println("------------"+logger.getName());
			}else {
				LogManager.getLogger(logger.getName()).setLevel(Level.OFF);
			}
		}
		System.out.println("======================="+LogManager.getRootLogger().getLevel());
		//LogManager.getLogger(DocumentAction.class.getName()).setLevel(Level.INFO);
		
		return "result";
	}
	
	
	public String cache(){
		Collection<String> caches = ehCacheManager.getCacheNames();
		for(String cacheName:caches){
			System.out.println("缓存名称："+cacheName);
			EhCacheCache cache  =  (EhCacheCache)ehCacheManager.getCache(cacheName);
			CacheStatistics cacheStatistics = new CacheStatistics(cache.getNativeCache());
			System.out.println("缓存命中率:缓存命中："+cacheStatistics.getCacheHits()+"，缓存未命中："+cacheStatistics.getCacheMisses()+"["+cacheStatistics.getCacheHitPercentage()+"]");
			System.out.println("缓存存储对象数："+cacheStatistics.getMemoryStoreObjectCount());
			System.out.println("getInMemoryHitPercentage"+cacheStatistics.getInMemoryHitPercentage());
			System.out.println("getOffHeapHitPercentage"+cacheStatistics.getOffHeapHitPercentage());
			System.out.println("getOnDiskHitPercentage"+cacheStatistics.getOnDiskHitPercentage());
		}
		return null;
	}
	
	public String cleanCache(){
		Collection<String> caches = ehCacheManager.getCacheNames();
		for(String cacheName:caches){
			System.out.println("缓存名称："+cacheName);
			EhCacheCache cache  =  (EhCacheCache)ehCacheManager.getCache(cacheName);
			cache.clear();
		}
		return null;
	}
	
	public Log getLog() {
		return log;
	}

	public void setLog(Log log) {
		this.log = log;
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

	public List<Log> getLogs() {
		return logs;
	}

	public void setLogs(List<Log> logs) {
		this.logs = logs;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
}
