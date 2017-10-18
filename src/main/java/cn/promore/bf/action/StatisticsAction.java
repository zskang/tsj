package cn.promore.bf.action;
/**
 * 项目信息统计处理Action
 * @author wangjg
 * @date   2017-06-21
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.MDC;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import cn.promore.bf.bean.Page;
import cn.promore.bf.bean.Project;
import cn.promore.bf.bean.ProjectProcessDetail;
import cn.promore.bf.bean.User;
import cn.promore.bf.serivce.StatisticsService;
import cn.promore.bf.unit.ExcelUtil;

@Controller
@Action(value="statisticsAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="tojson",type="json",params={"root","message","excludeNullProperties","true"}),
	      @Result(name="mapJson",type="json")
	    })
public class StatisticsAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static Logger LOG = LoggerFactory.getLogger(StatisticsAction.class);
	
	@Resource 
	private StatisticsService statisticsService;
	
	private Page page;
	private boolean flag = true;
	private String message;
	private List<Map<String, Object>> data;
	private String startDate;
	private String endDate;
	private ProjectProcessDetail processDetail;
	
	
	public String showModuleFileList(){
		Map<String, String> paramMap = new HashMap<String, String>();
		Project project = (Project)request.getSession().getAttribute("project");
		if(project != null){
			paramMap.put("projectId", project.getId()+"");
		}
		paramMap.put("moduleId", message);
		data = statisticsService.showModuleFileList(paramMap);
		MDC.put("operateContent","模块模块文件列表查询"); 
		LOG.info("");
		return "mapJson";
	}
	
	public String queryModuleTaskList(){
		Map<String, String> paramMap = new HashMap<String, String>();
		User user = (User)request.getSession().getAttribute("user");
		paramMap.put("userId", user.getId()+"");
		Project project = (Project)request.getSession().getAttribute("project");
		if(project != null){
			paramMap.put("projectId", project.getId()+"");
		}
		data = statisticsService.queryModuleTaskList(paramMap);
		MDC.put("operateContent","首页按模块统计执行中任务"); 
		LOG.info("");
		return "mapJson";
	}
	
	
	
	public String projectRankingList(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		paramMap.put("ordercolumn", message);
		page.setTotalRecords(statisticsService.queryProjectRankingCount(paramMap));
		data = statisticsService.queryProjectRankingList(page,paramMap);
		MDC.put("operateContent","项目排名列表查询"); 
		LOG.info("");
		return "mapJson";
	}
	
	public void exportProjectBasic (){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		paramMap.put("ordercolumn", message);
		data = statisticsService.queryProjectRankingLists(paramMap);
		String[] title = {"排名","项目名称","完成率","滞后率"};
		Object[][] datas = new Object[data.size()][title.length];
		if(null!=data&&data.size()>0){
			int index_ = 0;
			for(Map<String,Object> d:data){
				datas[index_][0] = d.get("rank");
				datas[index_][1] = d.get("name");
				datas[index_][2] = d.get("finishRank");
				datas[index_][3] = d.get("delayRank");
				index_++;
			}
		}
		try {
			MDC.put("operateContent","项目排名excel导出！"); 
			LOG.info("");
			ExcelUtil.exportExcel(request,response,"","项目排名","项目排名信息",title,datas,0);
		} catch (Exception e) {
			System.out.println(e);
		}
	} 
	
	public String userRankingList(){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		paramMap.put("ordercolumn", message);
		paramMap.put("projectId", processDetail.getProjectId());
		page.setTotalRecords(statisticsService.queryUserRankingCount(paramMap));
		data = statisticsService.queryUserRankingList(page,paramMap);
		MDC.put("operateContent","个人排名列表查询"); 
		LOG.info("");
		return "mapJson";
	}
	
	public void exportBasic (){
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("startDate", startDate);
		paramMap.put("endDate", endDate);
		paramMap.put("ordercolumn", message);
		paramMap.put("projectId", processDetail.getProjectId());
		data = statisticsService.queryUserRankingLists(paramMap);
		String[] title = {"排名","用户姓名","完成率","滞后率"};
		Object[][] datas = new Object[data.size()][title.length];
		if(null!=data&&data.size()>0){
			int index_ = 0;
			for(Map<String,Object> d:data){
				datas[index_][0] = d.get("rank");
				datas[index_][1] = d.get("username");
				datas[index_][2] = d.get("finishRank");
				datas[index_][3] = d.get("delayRank");
				index_++;
			}
		}
		try {
			MDC.put("operateContent","用户个人排名excel导出！"); 
			LOG.info("");
			ExcelUtil.exportExcel(request,response,"","个人排名","个人排名信息",title,datas,0);
		} catch (Exception e) {
			System.out.println(e);
		}
	} 
	
	
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
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
	
	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public ProjectProcessDetail getProcessDetail() {
		return processDetail;
	}

	public void setProcessDetail(ProjectProcessDetail processDetail) {
		this.processDetail = processDetail;
	}

	public List<Map<String, Object>> getData() {
		return data;
	}

	public void setData(List<Map<String, Object>> data) {
		this.data = data;
	}
	
	
}