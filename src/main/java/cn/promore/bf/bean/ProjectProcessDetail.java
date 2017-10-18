package cn.promore.bf.bean;
/**
 * @author wangjg 2017-05-09
 *  项目流程节点处理明细  此表数据为后台存储过程p_project_process_detail加工
 */
import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sys_project_process_detail")
public class ProjectProcessDetail implements Serializable{
	
	private static final long serialVersionUID = -222264077837159765L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)	
	private Integer id;			   		//ID
	private String dealDate;       		//处理时间
	private Integer projectId;     		//项目ID
	private Integer moduleId;      		//模块ID
	private Integer userId;        		//用户ID
	private String deploymentId;   		//工作流部署ID
	private String developKey;			//工作流引擎key
	private String pProcessInstanceId;  //工作流引擎实例ID
	private String type;				//1节点2流程
	private String taskId;	       		//工作节点实例ID
	private String taskName;       		//工作节点名称
	private Date startDate;        		//开始时间
	private Date endDate;          		//配置完成时间
	private Date realityEndDate;   		//实际完成时间
	private Integer whetherFinish; 		//0未完成1归档
	private Integer whetherDelay;  		//是否延迟0正常1延迟
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDealDate() {
		return dealDate;
	}
	public void setDealDate(String dealDate) {
		this.dealDate = dealDate;
	}
	public Integer getProjectId() {
		return projectId;
	}
	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getpProcessInstanceId() {
		return pProcessInstanceId;
	}
	public void setpProcessInstanceId(String pProcessInstanceId) {
		this.pProcessInstanceId = pProcessInstanceId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
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
	public Integer getWhetherFinish() {
		return whetherFinish;
	}
	public void setWhetherFinish(Integer whetherFinish) {
		this.whetherFinish = whetherFinish;
	}
	public Integer getModuleId() {
		return moduleId;
	}
	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}
	public String getDeploymentId() {
		return deploymentId;
	}
	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}
	public String getDevelopKey() {
		return developKey;
	}
	public void setDevelopKey(String developKey) {
		this.developKey = developKey;
	}
	public Date getRealityEndDate() {
		return realityEndDate;
	}
	public void setRealityEndDate(Date realityEndDate) {
		this.realityEndDate = realityEndDate;
	}
	public Integer getWhetherDelay() {
		return whetherDelay;
	}
	public void setWhetherDelay(Integer whetherDelay) {
		this.whetherDelay = whetherDelay;
	}
	public String getTaskName() {
		return taskName;
	}
	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}
	@Override
	public String toString() {
		return "项目流程执行明细 [编码=" + id + ", 处理时间=" + dealDate + ", 项目ID=" + projectId + ", 操作人ID=" + userId + ", 流程ID=" + pProcessInstanceId + ", 节点ID=" + taskId + ", 开始时间=" + startDate + ", 结束时间=" + endDate + ",是否完成=" + whetherFinish+ "]";
	}
}
