package cn.promore.bf.action;
/**
 * @author huzd@si-tech.com.cn or ahhzd@vip.qq.com
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
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

import cn.promore.bf.bean.Option;
import cn.promore.bf.bean.Page;
import cn.promore.bf.serivce.OptionService;

@Controller
@Action(value="optionAction")
@ParentPackage("huzdDefault")
@Results({@Result(name="result",type="json",params={"includeProperties",
		          "page\\.(currentPage|pageSize|totalPages|totalRecords),"
		          + "flag,message,"
		          + "options\\[\\d+\\]\\.(id|category|categoryname|name|order),"
		          + "option\\.(id|category|categoryname|name|order),dataIds,"
		          + "selectdOptions\\[\\d+\\]\\.(id|category|categoryname|name|order)"}),
	      @Result(name="list",type="json",params={"excludeProperties","options.*\\.content","excludeNullProperties","true"}),
	      @Result(name="selectOptions",type="json",params={"root","selectdOptions"})
		})
public class OptionAction extends BaseAction{
	public static Logger LOG = LoggerFactory.getLogger(OptionAction.class);
	private static final long serialVersionUID = -8613055080615406396L;
	
	@Resource 
	OptionService optionService;
	private Option option;
	private boolean flag = true;
	private String message;
	private Page page;
	private List<Option> options;
	private String dataIds;
	private List<List<Option>> selectdOptions = new ArrayList<List<Option>>();
	
	public OptionAction() {
		MDC.put("operateModuleName","选项管理");
	}
	
	public String add(){
		SecurityUtils.getSubject().checkPermission("option:add");
		try {
			optionService.save(option);
			ServletActionContext.getServletContext().setAttribute("option",option);
			flag = true;
			MDC.put("operateContent",option.toString()); 
			LOG.info("");
		} catch (Exception e) {
			flag = false;
			message=e.getMessage();
		}
		return "result";
	}
	
	public String get(){
		SecurityUtils.getSubject().checkPermission("option:get");
		try {
			option = optionService.findById(option.getId());
			flag = true;
			MDC.put("operateContent","查询选项，ID："+option.getId()); 
			LOG.info("");			
		} catch (Exception e) {
			flag = false;
			message = e.getMessage();
		}
		return "result";
	}
	
	public String delete(){
		SecurityUtils.getSubject().checkPermission("option:delete");
		String[] aIds = dataIds.split(",");
		flag = true;
		try {
			for(String id:aIds){
				optionService.deleteById(Integer.valueOf(id));
				flag = flag&&true;
				MDC.put("operateContent","删除码表，ID："+id); 
				LOG.info("");	
			}			
		} catch (Exception e) {
			flag = flag&&false;
			message = e.getMessage();
		}
		return "result";
	}
	
	public String update(){
		SecurityUtils.getSubject().checkPermission("option:update");
		try {
			Option temp = optionService.findById(option.getId());
			temp.setCategory(option.getCategory());
			temp.setCategoryname(option.getCategoryname());
			temp.setName(option.getName());
			temp.setOrder(option.getOrder());
			optionService.update(temp);
			flag = true;
			MDC.put("operateContent",option.toString()); 
			LOG.info("");	
		} catch (Exception e) {
			flag= false;
			message = e.getMessage();
		}
		return "result";
	}
	
	public String list(){
		SecurityUtils.getSubject().checkPermission("option:get");
		if(null==page)page=new Page();
		page.setTotalRecords(optionService.findDatasNo(option, null, null));
		options = optionService.findDatas(option, page, null, null);
		MDC.put("operateContent","码表列表查询"); 
		LOG.info("");
		return "result";
	}
	
	
	public String selectoption() throws IOException{
		if(StringUtils.isNotEmpty(dataIds)){
			for(String temp:dataIds.split(",")){
				selectdOptions.add(optionService.findByCategory(temp));
			}
		}
		MDC.put("operateContent","系统码表查询"); 
		LOG.info("");
		return "selectOptions";
	}
	
	public Option getOption() {
		return option;
	}

	public void setOption(Option option) {
		this.option = option;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	public Page getPage() {
		return page;
	}


	public void setPage(Page page) {
		this.page = page;
	}


	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public String getDataIds() {
		return dataIds;
	}

	public void setDataIds(String dataIds) {
		this.dataIds = dataIds;
	}

	public List<List<Option>> getSelectdOptions() {
		return selectdOptions;
	}
	public void setSelectdOptions(List<List<Option>> selectdOptions) {
		this.selectdOptions = selectdOptions;
	}
}
