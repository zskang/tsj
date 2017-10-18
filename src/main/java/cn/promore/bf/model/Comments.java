/**
 * 
 */
package cn.promore.bf.model;

/**
 * @author : Administrator
 * @date 2017年7月7日 下午11:27:35
 */
public class Comments {
	private String id;
	private String time;
	private String message;
	private String taskid;
	private String processinstanceid;
	private String userid; 
	
	private String act_name_; 
	private String assignee_;
	private String assingneeName_;
	private String start_time_;
	private String end_time_;

	
	
	public String getAct_name_() {
		return act_name_;
	}

	public void setAct_name_(String act_name_) {
		this.act_name_ = act_name_;
	}

	public String getAssignee_() {
		return assignee_;
	}

	public void setAssignee_(String assignee_) {
		this.assignee_ = assignee_;
	}

	public String getStart_time_() {
		return start_time_;
	}

	public void setStart_time_(String start_time_) {
		this.start_time_ = start_time_;
	}

	public String getAssingneeName_() {
		return assingneeName_;
	}

	public void setAssingneeName_(String assingneeName_) {
		this.assingneeName_ = assingneeName_;
	}

	public String getEnd_time_() {
		return end_time_;
	}

	public void setEnd_time_(String end_time_) {
		this.end_time_ = end_time_;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getTaskid() {
		return taskid;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public String getProcessinstanceid() {
		return processinstanceid;
	}

	public void setProcessinstanceid(String processinstanceid) {
		this.processinstanceid = processinstanceid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getAssigneeName_() {
		return assingneeName_;
	}

	public void setAssigneeName_(String assigneeName) {
		this.assingneeName_ = assigneeName;
	}

}
